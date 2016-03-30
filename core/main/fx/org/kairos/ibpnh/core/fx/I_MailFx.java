package org.kairos.ibpnh.core.fx;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kairos.ibpnh.core.utils.exception.CodedException;

/**
 * General Interface for Fx's.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_MailFx extends I_Fx {

	/**
	 * Sets the current fire time of the job calling this FX.
	 * 
	 * @param fireTime
	 */
	public void setFireTime(Date fireTime);

	/**
	 * Returns the map with the exposed parameters resolved.
	 * 
	 * @return map
	 */
	public Map<String, String> executeAndReturnParameters()
			throws CodedException;

	/**
	 * Returns a list of the exposed parameters of this FX.
	 * 
	 * @return string list
	 */
	public List<String> exposedParameters();
	
	/**
	 * Gets the list of attachments to send.
	 * 
	 * @return
	 */
	public List<AttachmentVo> getAttachments();

	/**
	 * Attachment VO.
	 * 
	 * @author Axel Collard Bovy
	 * 
	 */
	public class AttachmentVo {

		/**
		 * Byte Array with the attachment file data.
		 */
		private byte[] byteArray;

		/**
		 * File name for the attachment.
		 */
		private String fileName;

		/**
		 * Content type for the attachment.
		 */
		private String contentType;
		
		/**
		 * Identifier for inline attachements(inside html,is optional)
		 */
		private String inlineId;

		/**
		 * Constructor with fields.
		 * 
		 * @param byteArray
		 * @param fileName
		 * @param contentType
		 */
		public AttachmentVo(byte[] byteArray, String fileName,
				String contentType) {
			super();
			this.byteArray = byteArray;
			this.fileName = fileName;
			this.contentType = contentType;
		}
		
		/**
		 * Constructor with fields.
		 * 
		 * @param byteArray
		 * @param fileName
		 * @param contentType
		 * @param inlineId
		 */
		public AttachmentVo(byte[] byteArray, String fileName,
				String contentType,String inlineId) {
			super();
			this.byteArray = byteArray;
			this.fileName = fileName;
			this.contentType = contentType;
			this.inlineId = inlineId;
		}

		/**
		 * @return the byteArray
		 */
		public byte[] getByteArray() {
			return byteArray;
		}

		/**
		 * @param byteArray
		 *            the byteArray to set
		 */
		public void setByteArray(byte[] byteArray) {
			this.byteArray = byteArray;
		}

		/**
		 * @return the fileName
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * @param fileName
		 *            the fileName to set
		 */
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * @return the contentType
		 */
		public String getContentType() {
			return contentType;
		}

		/**
		 * @param contentType
		 *            the contentType to set
		 */
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		/**
		 * @return the inlineId
		 */
		public String getInlineId() {
			return inlineId;
		}

		/**
		 * @param inlineId the inlineId to set
		 */
		public void setInlineId(String inlineId) {
			this.inlineId = inlineId;
		}

	}

}
