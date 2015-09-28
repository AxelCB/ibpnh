package org.kairos.ibpnh.dao;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
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
public abstract class AbstractDao<E extends I_Model> implements I_Dao<E> {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(AbstractDao.class);


    @Override
    public E persist(E entity) {
        ofy().save().entity(entity).now();
        return entity;
    }

    @Override
    public E getById(Long id) {
        return ofy().load().type(this.getClazz()).id(id).now();
    }

    @Override
    public List<E> listAll() {
        return ofy().load().type(this.getClazz()).list();
    }

    @Override
    public PaginatedListVo<E> listPage(PaginatedRequestVo paginatedRequest, Long itemsPerPage) {
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
    public PaginatedListVo<E> searchPage(PaginatedSearchRequestVo paginatedSearchRequest, Long itemsPerPage) {
        return null;
    }

    @Override
    public E delete(E entity) {
        ofy().delete().entity(entity).now();
        return entity;
    }
}
