package org.kairos.ibpnh.dao;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.vo.AbstractVo;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Get's the actual class of the DAO.
     *
     * @return class
     */
    protected abstract Class<E> getClazz();

    @Override
    public VO persist(VO entityVo) {
        ofy().save().entity(entityVo).now();
        return entityVo;
    }

    @Override
    public VO getById(Long id) {
        return ofy().load().type(this.getClazz()).id(id).now();
    }

    @Override
    public List<VO> listAll() {
        return ofy().load().type(this.getClazz()).list();
    }

    @Override
    public PaginatedListVo<VO> listPage(PaginatedRequestVo paginatedRequest, Long itemsPerPage) {
        PaginatedListVo paginatedList = new PaginatedListVo<>();
        Query<VO> query = ofy().load().type(this.getClazz()).limit(itemsPerPage.intValue());
        if(!(paginatedRequest.getPreviousPage()==null || paginatedRequest.getPreviousPage().equals(paginatedRequest.getPage()))){
            if(paginatedRequest.getPreviousPage()<paginatedRequest.getPage()){
                query = query.startAt(Cursor.fromWebSafeString(paginatedRequest.getCursor()));
            }else if(paginatedRequest.getPreviousPage()>paginatedRequest.getPage()){
                query = query.endAt(Cursor.fromWebSafeString(paginatedRequest.getCursor()));
            }
        }
        QueryResultIterator<VO> iterator = query.iterator();
        while (iterator.hasNext()) {
            paginatedList.getItems().add(iterator.next());
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
}
