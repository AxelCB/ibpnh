package org.kairos.ibpnh.dao;

import com.google.appengine.api.datastore.KeyFactory;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.datanucleus.query.typesafe.TypesafeQuery;
import org.dozer.Mapper;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.utils.DozerUtils;
import org.kairos.ibpnh.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jdo.Query;
import java.util.List;

/**
 * Typed Abstract DAO with common functionality.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public abstract class AbstractDao<T extends I_Model, E extends AbstractVo>
        implements I_Dao<E> {

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
     * Search distinct flag.
     */
    private Boolean searchDistinct = Boolean.FALSE;

    /**
     * Get's the actual class of the DAO.
     *
     * @return class
     */
    protected abstract Class<T> getClazz();

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
    protected E map(T object) {
        Class<? extends E> clazz = this.getHierarchyVoClass(object.getClass());

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
    protected List<E> map(List<T> objects) {
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
    public Class<? extends E> getHierarchyVoClass(Class<? extends I_Model> clazz) {
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
    public Class<? extends T> getHierarchyClass(
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
    protected T map(E object) {
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
    protected void map(E voObject, T entityObject) {
        if (this.getMapId() != null) {
            this.getMapper().map(voObject, entityObject, this.getMapId());
        } else {
            this.getMapper().map(voObject, entityObject);
        }
    }

    /**
     * Computes the first result for a page.
     *
     * @param page
     *            the page to fetch
     * @param itemsPerPage
     *            the total items per page
     * @return
     */
    protected Long getPageFirstResult(Long page, Long itemsPerPage) {
        return ((page - 1) * itemsPerPage);
    }

    /**
     * Adds order to all the queries
     *
     * @param root
     *            the root element
     * @param builder
     *            the criteria builder
     * @param query
     *            the query
     */
    /*public void addDefaultOrder(Root<T> root, CriteriaBuilder builder,
                                CriteriaQuery<T> query) {
        // sub classes should override this method
        // default default-order is no-order
    }*/

    /**
     * Add's a defined order to the query.
     *
     * @param root
     *            the root element
     * @param builder
     *            the criteria builder
     * @param query
     *            the query
     * @param orderVo
     *            the defined order to add
     */
    /*public void addDefinedOrder(Root<T> root, CriteriaBuilder builder,
                                CriteriaQuery<T> query, OrderVo orderVo) {
        // orders list
        List<Order> orders = new ArrayList<>();

        for (OrderItemVo orderItemVo : orderVo.getItems()) {
            @SuppressWarnings("rawtypes")
            From finalPath = root;
            String[] propertyPath = orderItemVo.getProperty().split("\\.");
            for (int i = 0; i < (propertyPath.length - 1); i++) {
                finalPath = finalPath.join(propertyPath[i]);
            }

            // assumes safe get of the property
            if (orderItemVo.getAsc()) {
                orders.add(builder.asc(finalPath
                        .get(propertyPath[propertyPath.length - 1])));
            } else {
                orders.add(builder.desc(finalPath
                        .get(propertyPath[propertyPath.length - 1])));
            }
        }

        if (orders.size() > 0) {
            query.orderBy(orders);
        }
    }*/

    /**
     * Add's the defined order to the query
     *
     * @param root
     *            the root of the query
     * @param builder
     *            the query builder
     * @param query
     *            the query object
     * @param orderVo
     *            the order to add
     */
    /*public void addOrder(Root<T> root, CriteriaBuilder builder,
                         CriteriaQuery<T> query, OrderVo orderVo) {
        if (orderVo == null) {
            this.addDefaultOrder(root, builder, query);
        } else {
            this.addDefinedOrder(root, builder, query, orderVo);
        }
    }*/

    /**
     * Gets the query for listing all non-deleted entities.
     *
     *  @param pm
     *            the entity manager
     * @param orderVo
     *            the order to add
     *
     * @return a query
     */
    protected Query getListAllQuery(JDOPersistenceManager pm,OrderVo orderVo){
        this.logger.debug("generating list all query");

        Query query = pm.newQuery(this.getClazz());
        query.setFilter("deleted == false");

        return query;
    }

    /*protected CriteriaQuery<T> getListAllQuery(JDOPersistenceManager pm, OrderVo orderVo) {
        this.logger.debug("generating list all query");

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(this.getClazz());
        Root<T> root = query.from(this.getClazz());

        Predicate filters = builder.conjunction();
        // filters by the deleted flag
        filters = builder.and(filters, builder.equal(
                root.get("deleted").as(Boolean.class), Boolean.FALSE));
        // global filters
        filters = this.addGlobalFilters(root, builder, filters);

        query.where(filters);

        this.addOrder(root, builder, query, orderVo);

        return query;
    }*/

    /**
     * Gets the query for counting all non-deleted entities.
     *
     *  @param pm
     *            the entity manager
     * @return a query
     */
    /*protected CriteriaQuery<Long> getCountAllQuery(JDOPersistenceManager pm) {
        this.logger.debug("generating list all query");

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(this.getClazz());
        query.select(builder.count(root));

        Predicate filters = builder.conjunction();
        // filters by the deleted flag
        filters = builder.and(filters, builder.equal(
                root.get("deleted").as(Boolean.class), Boolean.FALSE));
        // global filters
        filters = this.addGlobalFilters(root, builder, filters);

        query.where(filters);

        return query;
    }*/

    /**
     * Makes the query for a filtered search.
     *
     *  @param pm
     *            the entity manager
     * @param vo
     *            the vo with the data to filter with
     * @param orderVo
     *            the order to add
     *
     * @return a CriteriaQuery
     */
    /*protected CriteriaQuery<T> getSearchAllQuery(JDOPersistenceManager pm, E vo,
                                                 OrderVo orderVo) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(this.getClazz());
        Root<T> root = query.from(this.getClazz());

        // conjunction to set the deleted filter and then the specific filters
        Predicate filters = builder.conjunction();

        // global filters
        filters = this.addGlobalFilters(root, builder, filters);

        // filters by the deleted flag
        filters = builder.and(filters, builder.equal(
                root.get("deleted").as(Boolean.class), Boolean.FALSE));

        // adds the subclass specific filters
        filters = this.addFilters(root, builder, filters, vo);

        query.where(filters);

        //adds the distinct clause if necessary
        if (this.getSearchDistinct()) {
            query.select(root).distinct(Boolean.TRUE);
        }

        this.addOrder(root, builder, query, orderVo);

        return query;
    }*/

    /**
     * Makes the query for counting the results of a filtered search.
     *
     *  @param pm
     *            the entity manager
     * @param vo
     *            the vo with the data to filter with
     *
     * @return a CriteriaQuery
     */
    /*protected CriteriaQuery<Long> getFilteredCountAllQuery(JDOPersistenceManager pm,
                                                           E vo) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(this.getClazz());

        // conjunction to set the deleted filter and then the specific filters
        Predicate filters = builder.conjunction();

        // global filters
        filters = this.addGlobalFilters(root, builder, filters);

        // filters by the deleted flag
        filters = builder.and(filters, builder.equal(
                root.get("deleted").as(Boolean.class), Boolean.FALSE));
        // adds the subclass specific filters
        filters = this.addFilters(root, builder, filters, vo);
        query.select(builder.count(root));
        query.where(filters);

        return query;
    }*/

    /**
     * Sets the filters to the query using the data in the VO.
     *
     * @param root
     *            the query root
     * @param builder
     *            the criteria builder
     * @param filters
     *            the predicate to add filters to
     * @param vo
     *            the VO with the data
     * @return the modified predicate
     */
    //protected abstract Predicate addFilters(Root<T> root,
    //                                       CriteriaBuilder builder, Predicate filters, E vo);

    /**
     * Sets the global filters to use always.
     *
     * Xparam root
     *            the query root
     * Xparam builder
     *            the criteria builder
     * Xparam filters
     *            the predicate to add filters to
     *
     * @return the modified predicate
     */
    /*protected Predicate addGlobalFilters(Root<T> root, CriteriaBuilder builder,
                                         Predicate filters) {
        return filters;
    };*/

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.I_Dao#search(javax.persistence.EntityManager,
     * org.universe.core.vo.AbstractVo, org.universe.core.vo.OrderVo)
     */
    @Override
    public List<E> search(JDOPersistenceManager pm, E vo, OrderVo orderVo) {
        this.logger.debug("searching entities");

        List<T> entities =null; //= pm.createQuery(
                //this.getSearchAllQuery(em, vo, orderVo)).getResultList();

        // transforms them to the VO entities
        List<E> entitiesVo = DozerUtils.map(entities, this.getMapMethod());

        return entitiesVo;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.I_Dao#listAll(javax.persistence.EntityManager,
     * org.universe.core.vo.OrderVo)
     */
    @Override
    public List<E> listAll(JDOPersistenceManager pm, OrderVo orderVo) {
        // gets the entities with the subclass query
        List<T> entities = null;
        TypesafeQuery typesafeQuery = null;
        Query query = null;
        try{
            query =  this.getListAllQuery(pm,orderVo);
            entities =(List<T>) query.execute();

        }catch(Exception e){
            this.logger.debug("Error listing all "+this.getClazz());
        }finally{
            query.closeAll();
        }
                //= em.createQuery(this.getListAllQuery(em, orderVo)).getResultList();
        // transforms them to the VO entities
        List<E> entitiesVo = DozerUtils.map(entities, this.getMapMethod());

        return entitiesVo;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.universe.core.dao.I_Dao#countAll(javax.persistence.EntityManager)
     */
    @Override
    public Long countAll(JDOPersistenceManager pm) {
        // gets the entities with the subclass query
        /*
        em.clear();

        Long total = em.createQuery(this.getCountAllQuery(em))
                .getSingleResult();

        return total;
        */
        return 0l;
    }

    /**
     * Counts all entities filtered for a search
     *
     *  @param pm
     *            the persistence manager
     * @param vo
     *            the data to filter the entities with
     *
     * @return total count of elements
     */
    @Override
    public Long filteredCountAll(JDOPersistenceManager pm, E vo) {
        /*em.clear();
        Long total = em.createQuery(this.getFilteredCountAllQuery(em, vo))
                .getSingleResult();

        return total;*/

        return 0l;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.universe.core.dao.I_Dao#listPage(javax.persistence.EntityManager,
     * org.universe.core.vo.PaginatedRequestVo, java.lang.Long,
     * org.universe.core.vo.OrderVo)
     */
    @Override
    public PaginatedListVo<E> listPage(JDOPersistenceManager pm,
                                       PaginatedRequestVo paginatedRequestVo, Long itemsPerPage,
                                       OrderVo orderVo) {
        this.logger.debug("listing page: {}", paginatedRequestVo.getPage());

        PaginatedListVo<E> paginatedListVo = new PaginatedListVo<>();

        paginatedListVo.setPage(paginatedRequestVo.getPage());
        paginatedListVo.setItemsPerPage(itemsPerPage);
        if (paginatedRequestVo.getFetchTotal()) {
            paginatedListVo.setTotalItems(this.countAll(pm));
        }

        Query query = this.getListAllQuery(pm, orderVo);
        query.setRange(this.getPageFirstResult(paginatedRequestVo.getPage(), itemsPerPage),
                this.getPageFirstResult(paginatedRequestVo.getPage(), itemsPerPage)+itemsPerPage);


        List<T> entities =(List<T>) query.execute();
        paginatedListVo.setItems(DozerUtils.map(entities, this.getMapMethod()));

        return paginatedListVo;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.universe.core.dao.I_Dao#searchPage(javax.persistence.EntityManager,
     * org.universe.core.vo.PaginatedSearchRequestVo, java.lang.Long,
     * org.universe.core.vo.OrderVo)
     */
    @Override
    public PaginatedListVo<E> searchPage(JDOPersistenceManager pm,
                                         PaginatedSearchRequestVo<E> paginatedSearchRequestVo,
                                         Long itemsPerPage, OrderVo orderVo) {
        this.logger.debug("searching entities, page: {}",
                paginatedSearchRequestVo.getPage());

        PaginatedListVo<E> paginatedListVo = new PaginatedListVo<>();

        /*
        paginatedListVo.setPage(paginatedSearchRequestVo.getPage());
        paginatedListVo.setItemsPerPage(itemsPerPage);
        if (paginatedSearchRequestVo.getFetchTotal()) {
            paginatedListVo.setTotalItems(this.filteredCountAll(em,
                    paginatedSearchRequestVo.getVo()));
        }

        TypedQuery<T> query = em.createQuery(this.getSearchAllQuery(em,
                paginatedSearchRequestVo.getVo(), orderVo));
        query.setFirstResult(this.getPageFirstResult(
                paginatedSearchRequestVo.getPage(), itemsPerPage));
        query.setMaxResults(itemsPerPage.intValue());

        List<T> entities = query.getResultList();
        paginatedListVo.setItems(DozerUtils.map(entities, this.getMapMethod()));
        */
        return paginatedListVo;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.I_Dao#listAll(javax.persistence.EntityManager)
     */
    @Override
    public List<E> listAll(JDOPersistenceManager pm) {
        return this.listAll(pm, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.universe.core.dao.I_Dao#listPage(javax.persistence.EntityManager,
     * org.universe.core.vo.PaginatedRequestVo, java.lang.Long)
     */
    @Override
    public PaginatedListVo<E> listPage(JDOPersistenceManager pm,
                                       PaginatedRequestVo paginatedRequestVo, Long itemsPerPage) {
        return this.listPage(pm, paginatedRequestVo, itemsPerPage, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.I_Dao#search(javax.persistence.EntityManager,
     * org.universe.core.vo.AbstractVo)
     */
    @Override
    public List<E> search(JDOPersistenceManager pm, E vo) {
        return this.search(pm, vo, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.universe.core.dao.I_Dao#searchPage(javax.persistence.EntityManager,
     * org.universe.core.vo.PaginatedSearchRequestVo, java.lang.Long)
     */
    @Override
    public PaginatedListVo<E> searchPage(JDOPersistenceManager pm,
                                         PaginatedSearchRequestVo<E> paginatedSearchRequestVo,
                                         Long itemsPerPage) {
        return this
                .searchPage(pm, paginatedSearchRequestVo, itemsPerPage, null);
    }

    /**
     * Gets an entity by its ID.
     *
     * @param id
     *            to get
     *  @param pm
     *            the persistence manager
     *
     * @return entity VO or null
     */
    @Override
    public E getById(JDOPersistenceManager pm, String id) {
        this.logger.debug("getting entity VO by id");

        T entity = this.getEntityById(pm, id);

        if (entity == null) {
            return null;
        } else {
            return this.map(entity);
        }
    }

    /**
     * Gets an entity by its ID.
     *
     * @param id
     *            to get
     *  @param pm
     *            the persistence manager
     *
     * @return entity VO or null
     */
    protected T getEntityById(JDOPersistenceManager pm, String id) {
        this.logger.debug("getting entity by id");

        // builds the query
//        Query query = pm.newQuery(this.getClazz());
//
//        query.setFilter("deleted == false");
//        query.setFilter("id == idParam");
//        query.declareParameters("Long idParam");
//        query.setUnique(Boolean.TRUE);
        /* Predicate filters = builder.conjunction();

        // filters by id
        filters = builder.and(filters,
                builder.equal(root.get("id").as(Long.class), id));
        // filters by the deleted flag
        filters = builder.and(filters, builder.equal(
                root.get("deleted").as(Boolean.class), Boolean.FALSE));

        query.where(filters);
        */
        try {
            // fetch the entity
//            T entity = (T)query.execute(Key.class.getName()+id);
//            em.refresh(entity);
            T entity = this.getEntityByIdAvoidDeletion(pm,id);
            return entity;
        }catch (Exception e) {
            // there was no entity with required name?
            return null;
        } //catch (NoResultException e) {
            // there was no entity with required name
//            return null;
        //}
    }

    /**
     * Gets an entity by its ID avoiding the deletion flag.
     *
     * @param id
     *            to get
     *  @param pm
     *            the persistence manager
     *
     * @return entity VO or null
     */
    protected T getEntityByIdAvoidDeletion(JDOPersistenceManager pm, String id) {
        this.logger.debug("getting entity by id");

        // builds the query
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<T> query = builder.createQuery(this.getClazz());
//        Root<T> root = query.from(this.getClazz());
//
//        query.where(builder.equal(root.get("id").as(Long.class), id));

        try {
            // fetch the entity
//            T entity = em.createQuery(query).getSingleResult();
//            em.refresh(entity);
            T entity = (T) pm.getObjectById(this.getClazz(), KeyFactory.stringToKey(id));
            return entity;
        }catch (Exception e) {
            // there was no entity with required name?
            return null;
        } //catch (NoResultException e) {
        // there was no entity with required name
//            return null;
        //}
    }

    /**
     * Persists a new entity.
     *
     * @param entityVo
     *            the entity to persist
     *
     *  @param pm
     *            the persistence manager
     */
    @Override
    public E persist(JDOPersistenceManager pm, E entityVo) {
        this.logger.debug("persisting entity");

        T entity = null;

        if (entityVo.getId() == null) {
            entity = this.map(entityVo);
            entity.setDeleted(Boolean.FALSE);
            entity = (T) pm.makePersistent(entity);

//            if (entity instanceof I_EntityCreationListener) {
//                ((I_EntityCreationListener) entity).beforeCreate(em);
//            }
        } else {

            entity = this.getEntityById(pm, entityVo.getId());


//            if (entity instanceof I_EntityUpdateListener) {
//                ((I_EntityUpdateListener) entity).beforeMap();
//            }
            this.map(entityVo, entity);

//            if (entity instanceof I_EntityUpdateListener) {
//                ((I_EntityUpdateListener) entity).beforeUpdate(em);
//            }
        }
        return this.map(entity);
    }

    /**
     * Deletes an entity.
     *
     * @param entityVo
     *            the entity to delete
     *  @param pm
     *            the persistence manager
     */
    @Override
    public void delete(JDOPersistenceManager pm, E entityVo) {
        this.logger.debug("deleting entity");

        T entity = this.getEntityById(pm, entityVo.getId());
        entity.setDeleted(Boolean.TRUE);
        pm.makePersistent(entity);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.I_Dao#searchDistinct(java.lang.Boolean)
     */
    @Override
    public void searchDistinct(Boolean distinct) {
        this.setSearchDistinct(distinct);
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
    public void setDeepMapping(Boolean deepMapping, Class<? extends E> clazz) {
        this.deepMapping = deepMapping;
        if (deepMapping) {
            this.setMapId(null);
        } else {
            this.setMapId("non-deep-" + clazz.getSimpleName());
        }
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
     * @return the searchDistinct
     */
    private Boolean getSearchDistinct() {
        return this.searchDistinct;
    }

    /**
     * @param searchDistinct
     *            the searchDistinct to set
     */
    private void setSearchDistinct(Boolean searchDistinct) {
        this.searchDistinct = searchDistinct;
    }

    /**
     * MapMethod to export mapping logic of this DAO.
     *
     * @author AxelCollardBovy ,created on 08/03/2015.
     *
     */
    public class MapMethod {

        /**
         * DAO reference.
         */
        private AbstractDao<T, E> dao;

        /**
         * Constructor with DAO.
         *
         * @param dao
         *            DAO
         */
        public MapMethod(AbstractDao<T, E> dao) {
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
        public E map(T element) {
            return this.dao.map(element);
        }
    }

}
