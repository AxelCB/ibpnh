package org.kairos.ibpnh.core.dao.person;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.vo.person.DocumentTypeVo;

/**
 * Interface for the Document Type DAO.
 *
 * @author Axel Collard Bovy
 *
 */
public interface I_DocumentTypeDao extends I_Dao<DocumentTypeVo> {
	/**
	 * Finds a document type by its acronym.
	 * 
	 * @param em
	 *            the entity manager
	 * @param acronym
	 *            the acronym to search for
	 * 
	 * @return ZoneVo or null
	 * 
	 */
	public DocumentTypeVo findByAcronym(EntityManager em, String acronym);

	/**
	 * Checks that a document type acronym is only used once.
	 * 
	 * @param em
	 *            the entity manager
	 * @param acronym
	 *            the acronym to check
	 * @param excludeId
	 *            the id to exclude
	 * 
	 * @return true if the code is unique
	 */
	public Boolean checkAcronymUniqueness(EntityManager em, String acronym,
			Long excludeId);

}
