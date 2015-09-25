package org.kairos.ibpnh.dao;

import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;

import java.util.List;

/**
 * General Interfaces for all Daos.
 *
 * @author AxelCollardBovy ,created on 22/09/2015.
 *
 *  * @param <E>
 *            the  class that this DAO returns
 */
public interface I_Dao<E extends I_Model> {

	public E persist(E entity);

	public E getById(String id);

	public List<E> listAll();

	public PaginatedListVo<E> listPage(PaginatedRequestVo paginatedRequest,Long itemsPerPage);

	public PaginatedListVo<E> searchPage(PaginatedSearchRequestVo paginatedSearchRequest,Long itemsPerPage);

	public E delete(E entity);

}
