package org.ibpnh.core.utils.exception.parameter;

import org.ibpnh.core.utils.exception.CodedException;
import org.ibpnh.core.utils.ErrorCodes;

/**
 * Exception thrown when an expected parameter cannot be parsed.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class ParameterParsingException extends CodedException {

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
	public ParameterParsingException(String message, Throwable cause) {
		super(message, ErrorCodes.ERROR_PARAMETER_PARSING, cause);
	}

	/**
	 * Constructor with message.
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public ParameterParsingException(String message) {
		this(message, null);
	}

}
