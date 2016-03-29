package org.ibpnh.core.dao.person;

import javax.persistence.EntityManager;

import org.ibpnh.core.dao.I_Dao;
import org.ibpnh.core.vo.person.PersonVo;

/**
 * Interface for the Person DAO.
 *
 * @author Axel Collard Bovy
 *
 */
public interface I_PersonDao extends I_Dao<PersonVo> {
	
	/**
	 * Checks if there is anohter person with same email.
	 * 
	 * @param em the entity manager
	 * @param email the email
	 * @param excludeId the ID to exlcude
	 * 
	 * @return true iif the code is unique
	 */
	public Boolean checkEmailUniqueness(EntityManager em, String email, Long excludeId);

}
