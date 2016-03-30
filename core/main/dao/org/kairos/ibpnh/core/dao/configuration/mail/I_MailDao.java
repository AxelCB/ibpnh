package org.kairos.ibpnh.core.dao.configuration.mail;

import java.util.Map;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.vo.configuration.mail.MailVo;

/**
 * Interface for the Mail DAO.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_MailDao extends I_Dao<MailVo> {

	/**
	 * Gets a mail by its name.
	 * 
	 * @param em
	 *            the entity manager
	 * @param name
	 *            the name to search for
	 * 
	 * @return a MailVo or null
	 */
	public MailVo findByName(EntityManager em, String name);

	/**
	 * Checks that a mail name is only used once.
	 * 
	 * @param em
	 *            the entity manager
	 * @param name
	 *            the name to check
	 * @param excludeId
	 *            the id to exclude
	 * 
	 * @return true iif the name is unique
	 */
	public Boolean checkNameUniqueness(EntityManager em, String name,
									   Long excludeId);

	/**
	 * Compiles the body of the specified field.
	 * 
	 * @param em
	 *            entity manager
	 * @param field
	 *            the field
	 * @param compileTimeParameters
	 *            parameters
	 * 
	 * @return compiled string
	 */
	public String compileField(EntityManager em, String field,
							   Map<String, String> compileTimeParameters);

}
