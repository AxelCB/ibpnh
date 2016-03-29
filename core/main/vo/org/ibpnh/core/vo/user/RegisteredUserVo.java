package org.ibpnh.core.vo.user;

import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.vo.AbstractVo;
import org.ibpnh.core.vo.person.PersonVo;

/**
 * VO of the Carrier entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
@AutoProperty
public class RegisteredUserVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3433930121564709395L;

	/**
	 * The id of the user.
	 */
	private Long userId;

	/**
	 * The personal data of the user.
	 */
	private PersonVo person;

	/**
	 * The cell phone
	 */
	private String cellphone;

	/**
	 * The user account ID.
	 */
	private Long userAccountId;

	/**
	 * User name.
	 */
	private String username;
	
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the person
	 */
	public PersonVo getPerson() {
		return this.person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(PersonVo person) {
		this.person = person;
	}

	/**
	 * @return the cellphone
	 */
	public String getCellphone() {
		return this.cellphone;
	}

	/**
	 * @param cellphone
	 *            the cellphone to set
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * @return the userAccountId
	 */
	public Long getUserAccountId() {
		return this.userAccountId;
	}

	/**
	 * @param userAccountId
	 *            the userAccountId to set
	 */
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

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

}
