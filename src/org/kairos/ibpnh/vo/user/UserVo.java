package org.kairos.ibpnh.vo.user;

import org.kairos.ibpnh.utils.HashUtils;
import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

/**
 * Value Object for the USer entity.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
@AutoProperty
public class UserVo extends AbstractVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5799171366682426940L;

	/**
	 * User's username.
	 */
	private String username;

	/**
	 * User's password.
	 */
	private String password;

	/**
	 * RoleVo of this user.
	 */
	private RoleVo role;

	/**
	 * Total login Attempts
	 */
	private Integer loginAttempts = 0;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled = Boolean.TRUE;

	/**
	 * First login flag.
	 */
	private Boolean firstLogin = Boolean.FALSE;

	/**
	 * Disabled cause.
	 */
	private String disabledCause;

	/**
	 * User Authentication Token
	 */
	private String token;

	/**
	 * Menu Order (one-way).
	 */
	//private MenuOrderVo menuOrder;

	/**
	 * Hash cost for the BCrypt algorithm.
	 */
	public Long hashCost = 9L;

	/**
	 * Enabling hash.
	 */
	private String enablingHash;

	public UserVo() {
		super();
		this.setEnabled(true);
		this.setFirstLogin(true);
		this.setLoginAttempts(0);
		this.setRole(new RoleVo());
	}

	/**
	 * Checks if the user has permissions to access the specified URI
	 * 
	 * @param uri
	 *            the URI to check
	 * 
	 * @return true if it can access
	 */
//	public Boolean canAccess(String uri) {
//		RoleFunctionVo roleFunctionVo = this.getRole()
//				.getRoleFunctionByUri(uri);
//
//		return roleFunctionVo != null && roleFunctionVo.getEnabled();
//	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username
	 *            the username to set
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
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public RoleVo getRole() {
		return this.role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(RoleVo role) {
		this.role = role;
	}

	/**
	 * @return the loginAttempts
	 */
	public Integer getLoginAttempts() {
		return this.loginAttempts;
	}

	/**
	 * @param loginAttempts
	 *            the loginAttempts to set
	 */
	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	/**
	 * @return the firstLogin
	 */
	public Boolean getFirstLogin() {
		return this.firstLogin;
	}

	/**
	 * @param firstLogin
	 *            the firstLogin to set
	 */
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the disabledCause
	 */
	public String getDisabledCause() {
		return this.disabledCause;
	}

	/**
	 * @param disabledCause
	 *            the disabledCause to set
	 */
	public void setDisabledCause(String disabledCause) {
		this.disabledCause = disabledCause;
	}

	/**
	 * Reset the password of the user
	 * 
	 * username = password
	 */
	public void resetPassword(Long hashCost) {
		this.setPassword(HashUtils.hashPassword(this.getUsername(), hashCost));
		this.setFirstLogin(Boolean.TRUE);
		this.setLoginAttempts(0);
		this.setHashCost(hashCost);
	}

	/**
	 * Enable a user
	 */
	public void enable() {
		this.setEnabled(true);
	}

	/**
	 * Disable a user with cause
	 * 
	 * @param cause
	 */
	public void disable(String cause) {
		this.setEnabled(false);
		this.setDisabledCause(cause);
	}

	/**
	 * @return the menuOrder
	 */
	//public MenuOrderVo getMenuOrder() {
//		return this.menuOrder;
//	}

	/**
	 * @param menuOrder
	 *            the menuOrder to set
	 */
	//public void setMenuOrder(MenuOrderVo menuOrder) {
//		this.menuOrder = menuOrder;
//	}

	/**
	 * @return the hashCost
	 */
	public Long getHashCost() {
		return this.hashCost;
	}

	/**
	 * @param hashCost
	 *            the hashCost to set
	 */
	public void setHashCost(Long hashCost) {
		this.hashCost = hashCost;
	}

	/**
	 * @return the enablingHash
	 */
	public String getEnablingHash() {
		return this.enablingHash;
	}

	/**
	 * @param enablingHash
	 *            the enablingHash to set
	 */
	public void setEnablingHash(String enablingHash) {
		this.enablingHash = enablingHash;
	}

}
