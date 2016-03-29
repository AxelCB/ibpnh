package org.ibpnh.core.vo.user;

import org.ibpnh.core.vo.AbstractVo;

/**
 * VO for password changing function.
 * 
 * @author Axel Collard Bovy
 *
 */
public class ChangePasswordVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 528507289160086868L;

	/**
	 * Current password
	 */
	private String currentPassword;
	
	/**
	 * New password.
	 */
	private String newPassword;

	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return this.currentPassword;
	}

	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return this.newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
