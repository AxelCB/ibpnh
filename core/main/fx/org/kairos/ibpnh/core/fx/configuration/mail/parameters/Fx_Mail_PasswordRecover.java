package org.kairos.ibpnh.core.fx.configuration.mail.parameters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kairos.ibpnh.core.fx.AbstractMailFxImpl;
import org.kairos.ibpnh.core.fx.I_MailFx;
import org.kairos.ibpnh.core.utils.exception.CodedException;

/**
 * Test FX.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class Fx_Mail_PasswordRecover extends AbstractMailFxImpl implements
		I_MailFx {

	/**
	 * Puclic Hash.
	 */
	private String publicHash;

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
		return Arrays.asList(new String[] { "email", "publicHash" });
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

		map.put("publicHash", this.getPublicHash());
		map.put("email", this.getEmail());

		return map;
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
