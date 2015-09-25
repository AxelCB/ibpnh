package org.kairos.ibpnh.dao;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import org.apache.commons.lang3.StringUtils;
import org.kairos.ibpnh.model.I_Model;
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
        Query<E> query = ofy().load().type(this.getClazz()).limit(itemsPerPage.intValue());
        if(StringUtils.isNotBlank(paginatedRequest.getPage())){
            query = query.startAt(Cursor.fromWebSafeString(paginatedRequest.getPage()));
        }
        PaginatedListVo paginatedList = new PaginatedListVo<>();
        QueryResultIterator<E> iterator = query.iterator();
        while (iterator.hasNext()) {
            paginatedList.getItems().add(iterator.next());
        }
        paginatedList.setPage(iterator.getCursor().toWebSafeString());
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
