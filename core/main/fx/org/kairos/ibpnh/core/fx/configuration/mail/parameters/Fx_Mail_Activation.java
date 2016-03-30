package org.kairos.ibpnh.core.fx.configuration.mail.parameters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.fx.AbstractMailFxImpl;
import org.kairos.ibpnh.core.utils.exception.CodedException;

/**
 * Test FX.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_Mail_Activation extends AbstractMailFxImpl implements I_MailFx {

	/**
	 * Activation Hash.
	 */
	private String activationHash;

	/**
	 * Username.
	 */
	private String username;

	/**
	 * Name.
	 */
	private String name;

	/**
	 * Surname.
	 */
	private String surname;

	/**
	 * Email.
	 */
	private String email;

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_MailFx#exposedParameters()
	 */
	@Override
	public List<String> exposedParameters() {
		return Arrays.asList(new String[] { "activationHash", "username",
				"name", "surname", "email" });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractMailFxImpl#_executeAndReturnParams()
	 */
	@Override
	protected Map<String, String> _executeAndReturnParams()
			throws CodedException {

		Map<String, String> map = new HashMap<String, String>();

		map.put("activationHash", this.getActivationHash());
		map.put("username", this.getUsername());
		map.put("name", this.getName());
		map.put("surname", this.getUsername());
		map.put("email", this.getEmail());

		return map;
	}

	/**
	 * @return the activationHash
	 */
	public String getActivationHash() {
		return this.activationHash;
	}

	/**
	 * @param activationHash
	 *            the activationHash to set
	 */
	public void setActivationHash(String activationHash) {
		this.activationHash = activationHash;
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
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
