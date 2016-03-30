package org.kairos.ibpnh.core.vo.messages;

import java.util.Date;

import org.kairos.ibpnh.core.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;

/**
 * Value Object for the message.
 * 
 * @author Axel Collard Bovy
 * 
 */
@AutoProperty
public class MessageVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7403308250013567770L;

	/**
	 * Reception timestamp of the SMS.
	 */
	private Date receptionTimestamp;

	/**
	 * Origin data.
	 */
	private String originData;
	
	/**
	 * The actual message.
	 */
	private String message;

	public MessageVo(String origin, String msg) {
		this.setMessage(msg);
		this.setReceptionTimestamp(new Date());
		this.setOriginData(origin);
	}

	/**
	 * @return the receptionTimestamp
	 */
	public Date getReceptionTimestamp() {
		return receptionTimestamp;
	}

	/**
	 * @param receptionTimestamp the receptionTimestamp to set
	 */
	public void setReceptionTimestamp(Date receptionTimestamp) {
		this.receptionTimestamp = receptionTimestamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the originData
	 */
	public String getOriginData() {
		return originData;
	}

	/**
	 * @param originData the originData to set
	 */
	public void setOriginData(String originData) {
		this.originData = originData;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}