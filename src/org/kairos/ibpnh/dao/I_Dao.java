package org.kairos.ibpnh.dao;

import org.kairos.ibpnh.vo.AbstractVo;
import org.kairos.ibpnh.vo.PaginatedListVo;
import org.kairos.ibpnh.vo.PaginatedRequestVo;
import org.kairos.ibpnh.vo.PaginatedSearchRequestVo;

import java.util.List;

/**
 * General Interfaces for all Daos.
 *
 * @author AxelCollardBovy ,created on 22/09/2015.
 *
 *  * @param <VO>
 *            the  class that this DAO returns
 */
public interface I_Dao<VO extends AbstractVo> {

	public Class<VO> getVoClazz();

	public VO persist(VO entity);

	public VO getById(Long id);

	public List<VO> listAll();

	public PaginatedListVo<VO> listPage(PaginatedRequestVo paginatedRequest,Long itemsPerPage);

	public PaginatedListVo<VO> searchPage(PaginatedSearchRequestVo paginatedSearchRequest,Long itemsPerPage);

	public VO delete(VO entity);

}
