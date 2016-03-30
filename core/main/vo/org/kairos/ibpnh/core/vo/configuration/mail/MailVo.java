package org.kairos.ibpnh.core.vo.configuration.mail;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.kairos.ibpnh.core.web.I_MessageSolver;
import org.pojomatic.annotations.AutoProperty;
import org.kairos.ibpnh.core.model.configuration.mail.E_Repetition;
import org.kairos.ibpnh.core.vo.AbstractVo;

/**
 * Mail VO.
 * 
 * @author Axel Collard Bovy
 * 
 */
@AutoProperty
public class MailVo extends AbstractVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3875482283594319660L;
	
	/**
	 * Activation Email Name.
	 */
	public static final String ACTIVATION_EMAIL_NAME = "ActivationEmail";
	
	/**
	 * Disabled system.
	 */
	public static final String SYSTEM_ON_OFF = "SystemOnOff";
	
	/**
	 * Send Inspector Mail.
	 */
	public static final String SEND_INSPECTOR_MAIL = "SendInspectorMail";
	
	/**
	 * License Plate Investigation.
	 */
	public static final String LICENSE_PLATE_INVESTIGATION = "LicensePlateInvestigationMail";
	
	/**
	 * Password Recover Email. 
	 */
	public static final String PASSWORD_RECOVER_NAME = "PasswordRecover";

	/**
	 * Identifier name.
	 */
	private String name;

	/**
	 * Mail subject.
	 */
	private String subject;

	/**
	 * Mail sender.
	 */
	private String sender;

	/**
	 * Mail recipients.
	 */
	private String toList;

	/**
	 * Mail CC list.
	 */
	private String ccList;

	/**
	 * Mail CCO list.
	 */
	private String ccoList;

	/**
	 * Body (parameterizable).
	 */
	private String body;

	/**
	 * Footer (parameterizable).
	 */
	private String footer;

	/**
	 * Repetition value.
	 */
	private E_Repetition repetition;

	/**
	 * Send timestamp for one instance of the mail. (will function as example
	 * for the repetition pattern)
	 */
	private Date sendTimestamp;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled;

	/**
	 * Mail FX Class that will resolve the parameters for this mail.
	 */
	private String mailFxClass;

	/**
	 * Flag that indicates if this mail includes Javascript in its body, to make
	 * the HTML.
	 */
	private Boolean bodyJavascript;
	
	/**
	 * If this email will be sent chronicly.
	 */
	private Boolean cron;

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractVo#validate(org.kairos.ibpnh.core.web.
	 * WebContextHolder)
	 */
	@Override
	public String validate(I_MessageSolver messageSolver) {
		if (StringUtils.isBlank(this.getName())) {
			String field = messageSolver.getMessage("fx.mail.field.name");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		if (StringUtils.isBlank(this.getSubject())) {
			String field = messageSolver.getMessage("fx.mail.field.subject");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		if (StringUtils.isBlank(this.getSender())) {
			String field = messageSolver.getMessage("fx.mail.field.sender");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		if (StringUtils.isBlank(this.getToList())) {
			String field = messageSolver.getMessage("fx.mail.field.toList");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		if (StringUtils.isBlank(this.getBody())) {
			String field = messageSolver.getMessage("fx.mail.field.body");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		if (StringUtils.isBlank(this.getFooter())) {
			String field = messageSolver.getMessage("fx.mail.field.footer");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		if (StringUtils.isBlank(this.getMailFxClass())) {
			String field = messageSolver.getMessage("fx.mail.field.mailFxClass");
			String message = messageSolver.getMessage(
					"default.fx.validation.parameter.required",
					new String[] { field });

			return message;
		}

		// everything ok
		return null;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return this.sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the ccList
	 */
	public String getCcList() {
		return this.ccList;
	}

	/**
	 * @param ccList
	 *            the ccList to set
	 */
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}

	/**
	 * @return the ccoList
	 */
	public String getCcoList() {
		return this.ccoList;
	}

	/**
	 * @param ccoList
	 *            the ccoList to set
	 */
	public void setCcoList(String ccoList) {
		this.ccoList = ccoList;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return this.body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the footer
	 */
	public String getFooter() {
		return this.footer;
	}

	/**
	 * @param footer
	 *            the footer to set
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}

	/**
	 * @return the repetition
	 */
	public E_Repetition getRepetition() {
		return this.repetition;
	}

	/**
	 * @param repetition
	 *            the repetition to set
	 */
	public void setRepetition(E_Repetition repetition) {
		this.repetition = repetition;
	}

	/**
	 * @return the sendTimestamp
	 */
	public Date getSendTimestamp() {
		return this.sendTimestamp;
	}

	/**
	 * @param sendTimestamp
	 *            the sendTimestamp to set
	 */
	public void setSendTimestamp(Date sendTimestamp) {
		this.sendTimestamp = sendTimestamp;
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
	 * @return the toList
	 */
	public String getToList() {
		return this.toList;
	}

	/**
	 * @param toList
	 *            the toList to set
	 */
	public void setToList(String toList) {
		this.toList = toList;
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
	 * @return the mailFxClass
	 */
	public String getMailFxClass() {
		return this.mailFxClass;
	}

	/**
	 * @param mailFxClass
	 *            the mailFxClass to set
	 */
	public void setMailFxClass(String mailFxClass) {
		this.mailFxClass = mailFxClass;
	}

	/**
	 * @return the bodyJavascript
	 */
	public Boolean getBodyJavascript() {
		return this.bodyJavascript;
	}

	/**
	 * @param bodyJavascript
	 *            the bodyJavascript to set
	 */
	public void setBodyJavascript(Boolean bodyJavascript) {
		this.bodyJavascript = bodyJavascript;
	}

	/**
	 * @return the cron
	 */
	public Boolean getCron() {
		return this.cron;
	}

	/**
	 * @param cron the cron to set
	 */
	public void setCron(Boolean cron) {
		this.cron = cron;
	}

}
