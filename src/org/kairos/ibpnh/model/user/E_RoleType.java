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
	ADMIN(Boolean.FALSE,null,"Administrador del sistema"),
	PASTOR(Boolean.TRUE,ADMIN,"Pastor de la Iglesia"),
	USER(Boolean.TRUE,ADMIN,"Usuario/Miembro de la Iglesia");

	/**
	 * Flag that indicates if a user can be created by an admin.
	 */
	private Boolean canBeCreatedByAdmin;

	/**
	 * Password reseter
	 */
	private E_RoleType passwordReseter;

	/**
	 * RoleType Description
	 */
	private String description;

	/**
	 * Default constructor.
	 */
	private E_RoleType() {
	}

	E_RoleType(Boolean canBeCreatedByAdmin, E_RoleType passwordReseter, String description) {
		this.canBeCreatedByAdmin = canBeCreatedByAdmin;
		this.passwordReseter = passwordReseter;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public E_RoleType getPasswordReseter() {
		return passwordReseter;
	}

	public void setPasswordReseter(E_RoleType passwordReseter) {
		this.passwordReseter = passwordReseter;
	}
}