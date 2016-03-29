package org.ibpnh.core.vo.user;

import org.ibpnh.core.vo.AbstractVo;

/**
 * Password recover entity for the user.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class PasswordRecoverVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6501280859018790250L;

	/**
	 * User that this recover is for.
	 */
	private UserVo user;

	/**
	 * Private hash generated for the view.
	 */
	private String privateHash;

	/**
	 * Public hash generated for the user email.
	 */
	private String publicHash;

	/**
	 * Recovered flag.
	 */
	private Boolean recovered;

	/**
	 * @return the user
	 */
	public UserVo getUser() {
		return this.user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserVo user) {
		this.user = user;
	}

	/**
	 * @return the privateHash
	 */
	public String getPrivateHash() {
		return this.privateHash;
	}

	/**
	 * @param privateHash
	 *            the privateHash to set
	 */
	public void setPrivateHash(String privateHash) {
		this.privateHash = privateHash;
	}

	/**
	 * @return the publicHash
	 */
	public String getPublicHash() {
		return this.publicHash;
	}

	/**
	 * @param publicHash
	 *            the publicHash to set
	 */
	public void setPublicHash(String publicHash) {
		this.publicHash = publicHash;
	}

	/**
	 * @return the recovered
	 */
	public Boolean getRecovered() {
		return this.recovered;
	}

	/**
	 * @param recovered
	 *            the recovered to set
	 */
	public void setRecovered(Boolean recovered) {
		this.recovered = recovered;
	}
}
