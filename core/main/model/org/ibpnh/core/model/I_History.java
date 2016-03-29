package org.ibpnh.core.model;

import java.util.Date;

/**
 * General History interface.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_History {

	/**
	 * Date and time of the operation getter.
	 * 
	 * @return dateTime
	 */
	public Date getTimestamp();
	
	/**
	 * Date and time of the operation setter.
	 * 
	 * @param timestamp the dateTime to set
	 */
	public void setTimestamp(Date timestamp);
	
	/**
	 * Username of the user who made the operation getter.
	 * 
	 * @return username
	 */
	public String getUsername();
	
	/**
	 * Username of the user who made the operation setter.
	 * 
	 * @param username
	 */
	public void setUsername(String username);
	
	/**
	 * Operation Type getter.
	 * 
	 * @return operation type
	 */
	public E_HistoricOperationType getOperationType();
	
	/**
	 * Operation Type setter.
	 * 
	 * @param operationType the operation type to set.
	 */
	public void setOperationType(E_HistoricOperationType operationType);
}
