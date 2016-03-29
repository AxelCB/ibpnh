package org.ibpnh.core.fx;

import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.utils.exception.CodedException;

/**
 * Coded Exception that holds a JsonResponse to the user.
 * 
 * @author Axel Collard Bovy
 *
 */
public class ResponseException extends CodedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2050148895656157798L;
	
	/**
	 * The response.
	 */
	private JsonResponse jsonResponse;

	/**
	 * @param message
	 * @param errorCode
	 * @param cause
	 */
	public ResponseException(String message, String errorCode, Throwable cause,JsonResponse jsonResponse) {
		super(message, errorCode, cause);
		this.jsonResponse = jsonResponse;
	}

	/**
	 * @return the jsonResponse
	 */
	public JsonResponse getJsonResponse() {
		return this.jsonResponse;
	}

	/**
	 * @param jsonResponse the jsonResponse to set
	 */
	public void setJsonResponse(JsonResponse jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	
}
