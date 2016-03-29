package org.ibpnh.core.events;

import java.util.Date;

/**
 * Abstract Guava Event.
 * 
 * @author Axel Collard Bovy
 *
 */
public abstract class GuavaEvent {
	
	/**
	 * Timestamp of creation of the event.
	 */
	private Date timestamp;
	
	/**
	 * Default constructor.
	 */
	public GuavaEvent() {
		this.timestamp = new Date();
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
