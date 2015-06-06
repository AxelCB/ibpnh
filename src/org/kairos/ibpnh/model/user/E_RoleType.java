package org.kairos.ibpnh.model.user;

/**
 * The Role Types pre-loaded.
 * 
 * @author AxelCollardBovy ,created on 27/02/2015.
 * 
 */
public enum E_RoleType {

	/**
	 * The role types enumerative.
	 */
	ADMIN(Boolean.FALSE),
	PASTOR(Boolean.TRUE),
	USER(Boolean.TRUE);

	/**
	 * Flag that indicates if a user can be created by an admin.
	 */
	private Boolean canBeCreatedByAdmin;

	/**
	 * Default constructor.
	 */
	private E_RoleType() {
	}

	private E_RoleType(Boolean canBeCreatedByAdmin) {
		this.canBeCreatedByAdmin = canBeCreatedByAdmin;
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