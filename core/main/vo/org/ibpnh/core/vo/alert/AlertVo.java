package org.ibpnh.core.vo.alert;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ibpnh.core.vo.user.UserVo;
import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.json.E_DateFormat;
import org.ibpnh.core.json.GsonAddDateReadableFormat;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.vo.AbstractVo;

/**
 * VO for Alert entities.
 * 
 * @author Axel Collard Bovy
 *
 */
@GsonAddDateReadableFormat(fields = {"timestamp"}, formats = {E_DateFormat.DATE_TIME_FORMAT})
@AutoProperty
public class AlertVo extends AbstractVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2288077558400293295L;

	/**
	 * Alert priority
	 */
	@Enumerated(EnumType.STRING)
	private E_Priority priority;
	
	/**
	 * Date time of the alert.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	/**
	 * Description.
	 */
	private String description;
	
	/**
	 * Involved Object ID
	 */
	private Long objectId;
	
	/**
	 * Involved Object Class Name
	 */
	private String objectClassName;
	
	/**
	 * User that generated the alert
	 */
	private UserVo user;
	
	/**
	 * Flag provided to make the alert stoppable.
	 */
	private Boolean sendAlert = Boolean.TRUE;

	/**
	 * @return the priority
	 */
	public E_Priority getPriority() {
		return this.priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(E_Priority priority) {
		this.priority = priority;
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the objectId
	 */
	public Long getObjectId() {
		return this.objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the objectClassName
	 */
	public String getObjectClassName() {
		return this.objectClassName;
	}

	/**
	 * @param objectClassName the objectClassName to set
	 */
	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

	/**
	 * @return the user
	 */
	public UserVo getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserVo user) {
		this.user = user;
	}

	/**
	 * @return the sendAlert
	 */
	public Boolean getSendAlert() {
		return this.sendAlert;
	}

	/**
	 * @param sendAlert the sendAlert to set
	 */
	public void setSendAlert(Boolean sendAlert) {
		this.sendAlert = sendAlert;
	}

}
