package org.kairos.ibpnh.core.dao.user;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.vo.user.PasswordRecoverVo;

/**
 * Interface for the PasswordRecover DAO.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_PasswordRecoverDao extends I_Dao<PasswordRecoverVo> {

	/**
	 * Gets the password recover object from the hashes.
	 * 
	 * @param em
	 *            the entity manager
	 * @param privateHash
	 *            the private hash
	 * @param publicHash
	 *            the public hash
	 * 
	 * @return password recover or null
	 */
	public PasswordRecoverVo getByHashes(EntityManager em, String privateHash,
			String publicHash);

}
