package org.kairos.ibpnh.core.dao.user;

import java.util.List;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.model.user.E_RoleType;
import org.kairos.ibpnh.core.vo.user.RoleTypeVo;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * Interface for the User DAO.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_UserDao extends I_Dao<UserVo> {

	/**
	 * Attempts to search a user with username.
	 * 
	 * @param em
	 *            the entity manager
	 * @param username
	 *            the username to search for
	 * 
	 * @return userVo or null
	 */
	public UserVo getByUsername(EntityManager em, String username);

	/**
	 * Gets all users that have the specified role type.
	 * 
	 * @param em
	 *            the entity manager
	 * @param roleTypeVo
	 *            the role type to search
	 * @return list of users
	 */
	public List<UserVo> findUsersByRoleTypeName(EntityManager em,
			RoleTypeVo roleTypeVo);

	/**
	 * Checks that a user username is only used once.
	 * 
	 * @param em
	 *            the entity manager
	 * @param username
	 *            the username to check
	 * @param excludeId
	 *            the id to exclude
	 * 
	 * @return true if the code is unique
	 */
	public Boolean checkUsernameUniqueness(EntityManager em, String username,
			Long excludeId);

	/**
	 * Counts the users registered with certain role types.
	 * 
	 * @param em
	 *            the entity manager
	 * @param countDisabled
	 *            if it should include disabled users
	 * @param roleTypes
	 *            the role types to count
	 * 
	 * @return integer
	 */
	public Integer countByRoleType(EntityManager em, Boolean countDisabled,
			E_RoleType... roleTypes);

	/**
	 * Get the user with the specified enabling hash.
	 * 
	 * @param em
	 *            the entity manager
	 * @param enablingHash
	 *            the enabling hash to search for
	 * 
	 * @return UserVo or null
	 */
	public UserVo getUserByEnablingHash(EntityManager em, String enablingHash);

}
