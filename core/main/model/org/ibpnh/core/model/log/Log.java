package org.ibpnh.core.model.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ibpnh.core.model.I_Model;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;

/**
 * Log4J Generated entity.
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@Table(name = "log4j")
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class Log implements Serializable, I_Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7185544003365776768L;

	/**
	 * ID.
	 */
	@Id
	private Long id;
	
	/**
	 * Deleted flag.
	 */
	private Boolean deleted;

	/**
	 * Creation timestamp of the log.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTimestamp;

	/**
	 * Log date (provided by Log4j)
	 */
	@Column(name = "log_date")
	private String logDate;

	/**
	 * Log level (provided by Log4j)
	 */
	@Column(name = "log_level")
	private String logLevel;

	/**
	 * Log location (provided by Log4j)
	 */
	@Column(name = "log_location")
	private String logLocation;

	/**
	 * Log message (provided by Log4j)
	 */
	@Column(name = "log_message")
	private String logMessage;

	/**
	 * Log stack trace (provided by Log4j)
	 */
	@Column(name = "log_throwable")
	private String logStackTrace;

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
	 * @return the creationTimestamp
	 */
	public Date getCreationTimestamp() {
		return this.creationTimestamp;
	}

	/**
	 * @param creationTimestamp
	 *            the creationTimestamp to set
	 */
	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	/**
	 * @return the logDate
	 */
	public String getLogDate() {
		return this.logDate;
	}

	/**
	 * @param logDate
	 *            the logDate to set
	 */
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return this.logLevel;
	}

	/**
	 * @param logLevel
	 *            the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * @return the logLocation
	 */
	public String getLogLocation() {
		return this.logLocation;
	}

	/**
	 * @param logLocation
	 *            the logLocation to set
	 */
	public void setLogLocation(String logLocation) {
		this.logLocation = logLocation;
	}

	/**
	 * @return the logMessage
	 */
	public String getLogMessage() {
		return this.logMessage;
	}

	/**
	 * @param logMessage
	 *            the logMessage to set
	 */
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	/**
	 * @return the logStackTrace
	 */
	public String getLogStackTrace() {
		return this.logStackTrace;
	}

	/**
	 * @param logStackTrace
	 *            the logStackTrace to set
	 */
	public void setLogStackTrace(String logStackTrace) {
		this.logStackTrace = logStackTrace;
	}

	/**
	 * @return the deleted
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
