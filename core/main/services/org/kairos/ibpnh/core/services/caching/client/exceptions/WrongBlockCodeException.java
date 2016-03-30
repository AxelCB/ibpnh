package org.kairos.ibpnh.core.services.caching.client.exceptions;

/**
 * Represents an exception thrown due to wrong block code in block occupational factor cache manager
 * 
 * @author acollard
 * 
 */
public class WrongBlockCodeException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8148916080073953471L;

	/**
	 * Declares the only constructor of this base exception.
	 * 
	 * @param message message of the exception
	 * @param errorCode error code
	 * @param cause cause (could be null)
	 */
	public WrongBlockCodeException(String message, Throwable cause) {
		super(message, cause);
	}

}
