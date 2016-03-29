package org.ibpnh.core.vo.log;

import java.util.Date;

import org.ibpnh.core.json.E_DateFormat;
import org.ibpnh.core.json.GsonAddDateReadableFormat;
import org.ibpnh.core.vo.AbstractVo;

/**
 * Log4J Generated entity.
 * 
 * @author Axel Collard Bovy
 * 
 */
@GsonAddDateReadableFormat(fields = { "creationTimestamp" }, formats = { E_DateFormat.DATE_TIME_FORMAT })
public class LogVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930568702700376541L;

	/**
	 * Creation timestamp of the log.
	 */
	private Date creationTimestamp;

	/**
	 * Log date (provided by Log4j)
	 */
	private String logDate;

	/**
	 * Log level (provided by Log4j)
	 */
	private String logLevel;

	/**
	 * Log location (provided by Log4j)
	 */
	private String logLocation;

	/**
	 * Log message (provided by Log4j)
	 */
	private String logMessage;

	/**
	 * Log stack trace (provided by Log4j)
	 */
	private String logStackTrace;

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
}
