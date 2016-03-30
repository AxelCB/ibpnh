package org.kairos.ibpnh.core.vo.person;

import org.pojomatic.annotations.AutoProperty;

/**
 * Value Object for the PointOfSaleResponsible Entity
 *
 * @author Axel Collard Bovy
 *
 */
@AutoProperty
public class PointOfSaleResponsibleVo extends PersonVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7445735778536978917L;
	/**
	 * CUIT of the Responsible
	 */
	private String cuit;

	/**
	 * @return the cuit
	 */
	public String getCuit() {
		return this.cuit;
	}

	/**
	 * @param cuit the cuit to set
	 */
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
}
