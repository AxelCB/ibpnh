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

	/**
	 *
	 * @param entity
	 * @return
	 */
	public VO persist(VO entity);

	/**
	 *
	 * @param id
	 * @return
	 */
	public VO getById(Long id);

	/**
	 *
	 * @return
	 */
	public List<VO> listAll();

	/**
	 *
	 * @param paginatedRequest
	 * @param itemsPerPage
	 * @return
	 */
	public PaginatedListVo<VO> listPage(PaginatedRequestVo paginatedRequest,Long itemsPerPage);

	/**
	 *
	 * @param paginatedSearchRequest
	 * @param itemsPerPage
	 * @return
	 */
	public PaginatedListVo<VO> searchPage(PaginatedSearchRequestVo paginatedSearchRequest,Long itemsPerPage);

	/**
	 *
	 * @param entity
	 * @return
	 */
	public VO delete(VO entity);

	/**
	 * Gets the class of the VO that this DAO has as it parameterized type.
	 *
	 * @return
	 */
	public Class<VO> getVoClazz();

	/**
	 * Flag to set the policy that the DAO will take upon complex objects.
	 *
	 * If setted to true (default) it will map using the non-classified default
	 * mappings on the dozer configuration file, i.e, it will bring all the
	 * complex object that the root object references.
	 *
	 * If setted to false, it will use the "non-deep" mapping ID and rely on it
	 * to get only the object's primitive types mapped. (This will be subject to
	 * the user's mapping in the file).
	 */
	public void setDeepMapping(Boolean deepMapping);

	/**
	 * This one indicates a subclass of the specified VO to use as the mapping
	 * classificator.
	 *
	 * @param deepMapping
	 * @param clazz
	 */
	public void setDeepMapping(Boolean deepMapping, Class<? extends VO> clazz);

	/**
	 * Returns the current status of the flag.
	 */
	public Boolean getDeepMapping();

	/**
	 * Sets a custom map.
	 *
	 * @param customMap
	 */
	public void setCustomMap(String customMap);

}
