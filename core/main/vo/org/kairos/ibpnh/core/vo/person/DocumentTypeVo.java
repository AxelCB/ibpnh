package org.kairos.ibpnh.core.vo.person;

import org.kairos.ibpnh.core.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;

/**
 * Value Object for the Document Type
 * 
 * @author Axel Collard Bovy
 *
 */
@AutoProperty
public class DocumentTypeVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1178440429250256717L;
	
	/**
	 * The acronym of the document type's description.
	 */
	private String acronym;
	
	/**
	 * The document type's description.
	 */
	private String description;

	/**
	 * @return the acronym
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @param acronym the acronym to set
	 */
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
