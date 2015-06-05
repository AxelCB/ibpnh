package org.kairos.ibpnh.vo.user;

import org.kairos.ibpnh.vo.AbstractVo;

/**
 * Holds the user credentials for a login attempt. 
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
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
