package org.ibpnh.core.vo.user;

import org.ibpnh.core.vo.AbstractVo;
import org.ibpnh.core.vo.person.PersonVo;

/**
 *  Value Object for the UserDetail Entity
 *  
 * @author Axel Collard Bovy
 *
 */
public class UserDetailsVo extends AbstractVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3333365983919459917L;

	/**
	 * The personal data of the inspector.
	 */
	private PersonVo person;
	
	/**
	 * The user of the inspector.
	 */
	private Long userId;

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
	 * @return the userId
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
