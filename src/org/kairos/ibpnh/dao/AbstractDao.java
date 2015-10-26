package org.kairos.ibpnh.dao;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import org.dozer.Mapper;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.utils.DozerUtils;
import org.kairos.ibpnh.vo.AbstractVo;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Typed Abstract DAO with common functionality.
 *
 * @author AxelCollardBovy ,created on 22/09/2015.
 *
 */
public abstract class AbstractDao<E extends I_Model, VO extends AbstractVo>
        implements I_Dao<VO>{

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    /**
     * Dozer Mapper.
     */
    @Autowired
    private Mapper mapper;

    /**
     * Flag to set the policy that the DAO will take upon complex objects.
     *
     * If setted to true (default) it will map using the non-classified default
     * mappings on the dozer configuration file, i.e, it will bring all the
     * complex objects that the root object references.
     *
     * If setted to false, it will use the "non-deep" mapping ID and rely on it
     * to get only the object's primitive types mapped. (This will be subject to
     * the user's mapping in the file).
     */
    private Boolean deepMapping = Boolean.TRUE;

    /**
     * The mapID to use when mapping objects to VOs with Dozer.
     */
    private String mapId;

    /**
     * Map Method exporter of this DAO.
     */
    private MapMethod mapMethod;

    /**
     * Get's the actual class of the DAO.
     *
     * @return class
     */
    protected abstract Class<E> getClazz();

    /**
     * Default constructor.
     */
    public AbstractDao() {
        this.mapMethod = new MapMethod(this);
    }

    /**
     * Maps an object to the DAO VO Class.
     *
     * @param object
     *            the object to map
     *
     * @return mapped object
     */
    protected VO map(E object) {
        Class<? extends VO> clazz = this.getHierarchyVoClass(object.getClass());

        if (this.getMapId() != null) {
            return this.getMapper().map(object, clazz, this.getMapId());
        } else {
            return this.getMapper().map(object, clazz);
        }
    }

    /**
     * Maps a list of entity objects into a list of VO objects
     *
     * @param objects
     *            entity objects list
     *
     * @return VO list
     */
    protected List<VO> map(List<E> objects) {
        return DozerUtils.map(objects, this.getMapMethod());
    }

    /**
     * Gets the hierarchy VO class for a hierarchy entity.
     *
     * @param clazz
     *            the actual class
     *
     * @return the VO class to use to map to
     */
    public Class<? extends VO> getHierarchyVoClass(Class<? extends I_Model> clazz) {
        // for default, returns the configured DAO class
        return this.getVoClazz();
    }

    /**
     * Gets the hierarchy class for a hierarchy entity.
     *
     * @param clazz
     *            the actual class
     *
     * @return the class to use to map to
     */
    public Class<? extends E> getHierarchyClass(
            Class<? extends AbstractVo> clazz) {
        // for default, returns the configured DAO class
        return this.getClazz();
    }

    /**
     * Maps an object to the DAO Class.
     *
     * @param object
     *            the object to map
     *
     * @return mapped object
     */
    protected E map(VO object) {
        if (this.getMapId() != null) {
            return this.getMapper().map(object,
                    this.getHierarchyClass(object.getClass()), this.getMapId());
        } else {
            return this.getMapper().map(object,
                    this.getHierarchyClass(object.getClass()));
        }
    }

    /**
     * Maps an VO object to am entity object
     *
     * @param voObject
     *            the object to map from
     * @param entityObject
     *            the object to map to
     *
     */
    protected void map(VO voObject, E entityObject) {
        if (this.getMapId() != null) {
            this.getMapper().map(voObject, entityObject, this.getMapId());
        } else {
            this.getMapper().map(voObject, entityObject);
        }
    }

    @Override
    public VO persist(VO entityVo) {
        this.logger.debug("persisting entity");

        E entity = null;

        if (entityVo.getId() == null) {
            entity = this.map(entityVo);
        } else {
            entity = this.getEntityById(entityVo.getId());

            this.map(entityVo, entity);
        }

        entity.setDeleted(Boolean.FALSE);
        ofy().save().entity(entity).now();

        return this.map(entity);
    }

    @Override
    public VO getById(Long id) {
        return this.map(ofy().load().type(this.getClazz()).id(id).now());
    }

    public E getEntityById(Long id) {
        return ofy().load().type(this.getClazz()).id(id).now();
    }

    @Override
    public List<VO> listAll() {
        return this.map(ofy().load().type(this.getClazz()).list());
    }

    @Override
    public PaginatedListVo<VO> listPage(PaginatedRequestVo paginatedRequest, Long itemsPerPage) {
        PaginatedListVo paginatedList = new PaginatedListVo<>();
        Query<E> query = ofy().load().type(this.getClazz()).limit(itemsPerPage.intValue());
        if(!(paginatedRequest.getPreviousPage()==null || paginatedRequest.getPreviousPage().equals(paginatedRequest.getPage()))){
            if(paginatedRequest.getPreviousPage()<paginatedRequest.getPage()){
                query = query.startAt(Cursor.fromWebSafeString(paginatedRequest.getCursor()));
            }else if(paginatedRequest.getPreviousPage()>paginatedRequest.getPage()){
                query = query.endAt(Cursor.fromWebSafeString(paginatedRequest.getCursor()));
            }
        }
        QueryResultIterator<E> iterator = query.iterator();
        while (iterator.hasNext()) {
            paginatedList.getItems().add(this.map(iterator.next()));
        }
        paginatedList.setPreviousPage(paginatedRequest.getPreviousPage());
        paginatedList.setPage(paginatedRequest.getPage());
        paginatedList.setCursor(iterator.getCursor().toWebSafeString());
        paginatedList.setTotalItems((long) ofy().load().type(this.getClazz()).count());
        //Parameter.ITEMS_PER_PAGE
        paginatedList.setItemsPerPage(10L);
        return paginatedList;
    }

    @Override
    public PaginatedListVo<VO> searchPage(PaginatedSearchRequestVo paginatedSearchRequest, Long itemsPerPage) {
        return null;
    }

    @Override
    public VO delete(VO entityVo) {
        ofy().delete().entity(entityVo).now();
        return entityVo;
    }

    /**
     * @return the mapper
     */
    public Mapper getMapper() {
        return this.mapper;
    }

    /**
     * @param mapper
     *            the mapper to set
     */
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * @return the deepMapping
     */
    @Override
    public Boolean getDeepMapping() {
        return this.deepMapping;
    }

    /**
     * @param deepMapping
     *            the deepMapping to set
     */
    @Override
    public void setDeepMapping(Boolean deepMapping) {
        this.setDeepMapping(deepMapping, this.getVoClazz());
    }

    /**
     * @param deepMapping
     *            the deepMapping to set
     */
    @Override
    public void setDeepMapping(Boolean deepMapping, Class<? extends VO> clazz) {
        this.deepMapping = deepMapping;
        if (deepMapping) {
            this.setMapId(null);
        } else {
            this.setMapId("non-deep-" + clazz.getSimpleName());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.I_Dao#setCustomMap(java.lang.String)
     */
    @Override
    public void setCustomMap(String customMap) {
        this.setMapId(customMap);
    }

    /**
     * @return the mapId
     */
    public String getMapId() {
        return this.mapId;
    }

    /**
     * @param mapId
     *            the mapId to set
     */
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    /**
     * @return the mapMethod
     */
    public MapMethod getMapMethod() {
        return this.mapMethod;
    }

    /**
     * @param mapMethod
     *            the mapMethod to set
     */
    public void setMapMethod(MapMethod mapMethod) {
        this.mapMethod = mapMethod;
    }

    /**
     * MapMethod to export mapping logic of this DAO.
     *
     * @author acollard
     *
     */
    public class MapMethod {

        /**
         * DAO reference.
         */
        private AbstractDao<E, VO> dao;

        /**
         * Constructor with DAO.
         *
         * @param dao
         *            DAO
         */
        public MapMethod(AbstractDao<E, VO> dao) {
            this.dao = dao;
        }

        /**
         * Exported map method.
         *
         * @param element
         *            element to map
         *
         * @return mapped element
         */
        public VO map(E element) {
            return this.dao.map(element);
        }
    }


}
