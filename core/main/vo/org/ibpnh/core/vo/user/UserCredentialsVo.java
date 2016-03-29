package org.ibpnh.core.vo.user;

import org.ibpnh.core.vo.AbstractVo;


/**
 * Holds the user credentials for a login attempt. 
 * 
 * @author Axel Collard Bovy
 *
 */
public class UserCredentialsVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7055427451172518613L;

	/**
	 * Username for login.
	 */
	private String username;
	
	/**
	 * Plain password for login.
	 */
	private String password;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
