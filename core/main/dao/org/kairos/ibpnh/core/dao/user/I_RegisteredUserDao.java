package org.kairos.ibpnh.core.dao.user;

import javax.persistence.EntityManager;
import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.vo.user.UserVo;
import org.kairos.ibpnh.core.vo.user.RegisteredUserVo;

/**
 * Registered User DAO.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_RegisteredUserDao extends I_Dao<RegisteredUserVo> {

	/**
	 * Finds a Registered User by an UserVo.
	 * 
	 * @param em
	 *            the entity manager
	 * @param userVo
	 *            the user VO
	 * 
	 * @return RegisteredUserVo or null
	 */
	public RegisteredUserVo findByUser(EntityManager em, UserVo userVo);

	/**
	 * Finds a Registered User by an username.
	 * 
	 * @param em
	 *            the entity manager
	 * @param username
	 *            the username to search for
	 * 
	 * @return RegisteredUserVo or null
	 */
	public RegisteredUserVo findByUsername(EntityManager em, String username);

	/**
	 * Finds a Registered User by it's registered email.
	 * 
	 * @param em
	 *            the entity manager
	 * @param email
	 *            the email to search
	 * 
	 * @return RegisteredUserVo or null
	 */
	public RegisteredUserVo findByEmail(EntityManager em, String email);
}
