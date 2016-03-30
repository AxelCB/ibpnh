package org.kairos.ibpnh.core.dao.user;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.vo.user.UserDetailsVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * User Details DAO.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_UserDetailsDao extends I_Dao<UserDetailsVo> {

	/**
	 * Gets the user details by the user.
	 * 
	 * @param em
	 *            the entity manager
	 * @param userVo
	 *            the user to search to
	 * 
	 * @return UserDetailsVo or null
	 */
	public UserDetailsVo findByUser(EntityManager em, UserVo userVo);

}
