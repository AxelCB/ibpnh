package org.kairos.ibpnh.core.utils.exception.parameter;

import org.kairos.ibpnh.core.utils.exception.CodedException;
import org.kairos.ibpnh.core.utils.ErrorCodes;

/**
 * Exception thrown when an expected parameter is missing.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class ParameterMissingException extends CodedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4510865420469317087L;

	/**
	 * Constructor with message and cause.
	 * 
	 * @param message
	 *            the message of the exception
	 * @param cause
	 *            the cause of the exception
	 */
	public ParameterMissingException(String message, Throwable cause) {
		super(message, ErrorCodes.ERROR_PARAMETER_MISSING, cause);
	}

	/**
	 * Constructor with message.
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public ParameterMissingException(String message) {
		this(message, null);
	}

}
