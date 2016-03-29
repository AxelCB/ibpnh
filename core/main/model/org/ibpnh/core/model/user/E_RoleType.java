package org.ibpnh.core.model.user;

import org.ibpnh.core.fx.I_Fx;

/**
 * The Role Types pre-loaded.
 * 
 * @author Axel Collard Bovy
 * 
 */
public enum E_RoleType {

	/**
	 * The role types enumerative.
	 */
	ADMIN(null, Boolean.FALSE),
	PASTOR(null, Boolean.TRUE),
	USER(null, Boolean.TRUE);

	/**
	 * FX for getting the personal data of a user.
	 */
	private Class<? extends I_Fx> getPersonalDataFx;

	/**
	 * Flag that indicates if a user can be created by an admin.
	 */
	private Boolean canBeCreatedByAdmin;

	/**
	 * Constructor with params
	 *
	 * @param getPersonalDataFx
	 * @param canBeCreatedByAdmin
	 */
	private E_RoleType(Class<? extends I_Fx> getPersonalDataFx, Boolean canBeCreatedByAdmin) {
		this.getPersonalDataFx = getPersonalDataFx;
		this.canBeCreatedByAdmin = canBeCreatedByAdmin;
	}

	/**
	 * @return the getPersonalDataFx
	 */
	public Class<? extends I_Fx> getGetPersonalDataFx() {
		return this.getPersonalDataFx;
	}

	/**
	 * @param getPersonalDataFx
	 *            the getPersonalDataFx to set
	 */
	public void setGetPersonalDataFx(Class<? extends I_Fx> getPersonalDataFx) {
		this.getPersonalDataFx = getPersonalDataFx;
	}

	/**
	 * @return the canBeCreatedByAdmin
	 */
	public Boolean getCanBeCreatedByAdmin() {
		return this.canBeCreatedByAdmin;
	}

	/**
	 * @param canBeCreatedByAdmin
	 *            the canBeCreatedByAdmin to set
	 */
	public void setCanBeCreatedByAdmin(Boolean canBeCreatedByAdmin) {
		this.canBeCreatedByAdmin = canBeCreatedByAdmin;
	}

}