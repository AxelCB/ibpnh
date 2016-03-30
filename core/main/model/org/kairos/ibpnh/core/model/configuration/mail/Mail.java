package org.kairos.ibpnh.core.model.configuration.mail;

import org.kairos.ibpnh.core.model.I_Model;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Defines the rules and data for a certain mail.
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class Mail implements I_Model, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5330237639436291408L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_seq")
	@SequenceGenerator( name = "mail_seq", sequenceName = "mail_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

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
	@Lob
	private String toList;

	/**
	 * Mail CC list.
	 */
	@Lob
	private String ccList;

	/**
	 * Mail CCO list.
	 */
	@Lob
	private String ccoList;

	/**
	 * Body (parameterizable).
	 */
	@Lob
	private String body;

	/**
	 * Footer (parameterizable).
	 */
	@Lob
	private String footer;

	/**
	 * Repetition value.
	 */
	@Enumerated(EnumType.STRING)
	private E_Repetition repetition;

	/**
	 * Send timestamp for one instance of the mail. (will funftion as example
	 * for the repetition pattern)
	 */
	@Temporal(TemporalType.TIMESTAMP)
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

	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the deleted
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
