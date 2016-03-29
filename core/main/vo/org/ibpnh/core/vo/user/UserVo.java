package org.ibpnh.core.vo.user;

import java.io.Serializable;

import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.utils.HashUtils;
import org.ibpnh.core.vo.AbstractVo;

/**
 * Value Object for the USer entity.
 * 
 * @author Axel Collard Bovy
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
	private Integer loginAttempts;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled;

	/**
	 * First login flag.
	 */
	private Boolean firstLogin;

	/**
	 * Disabled cause.
	 */
	private String disabledCause;

	/**
	 * User Authentication Token
	 */
	private String token;

	/**
	 * For query purposes only
	 */
	private UserDetailsVo userDetails;

	/**
	 * Hash cost for the BCrypt algorithm.
	 */
	public Long hashCost;

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
	public Boolean canAcces(String uri) {
		RoleFunctionVo roleFunctionVo = this.getRole()
				.getRoleFunctionByUri(uri);

		return roleFunctionVo != null && roleFunctionVo.getEnabled();
	}

	/**
	 * Copies the order defined in the role type functions to the role
	 * functions.
	 */
	public void copyOrder() {
		if (this.getRole() != null && this.getRole().getRoleType() != null
				&& this.getRole().getRoleType().getRoleTypeFunctions() != null
				&& this.getRole().getRoleFunctions() != null) {
			for (RoleFunctionVo roleFunctionVo : this.getRole()
					.getRoleFunctions()) {
				RoleTypeFunctionVo roleTypeFunctionVo = this
						.getRole()
						.getRoleType()
						.getRoleTypeFunctionByFunctionId(roleFunctionVo.getId());
			}
		}
	}

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
	 * @return the userDetails
	 */
	public UserDetailsVo getUserDetails() {
		return this.userDetails;
	}

	/**
	 * @param userDetails
	 *            the userDetails to set
	 */
	public void setUserDetails(UserDetailsVo userDetails) {
		this.userDetails = userDetails;
	}

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
