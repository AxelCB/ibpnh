package org.kairos.ibpnh.web;

import org.kairos.ibpnh.json.JsonResponse;

/**
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
public interface I_MessageSolver {

	/**
	 * Get message with code.
	 * 
	 * @param code
	 *            the message to get
	 * 
	 * @return message string
	 */
	public String getMessage(String code);

	/**
	 * Get message with code and arguments.
	 * 
	 * @param code
	 *            the message to get
	 * @param args
	 *            the arguments to user
	 * 
	 * @return message string
	 */
	public String getMessage(String code, Object[] args);

	/**
	 * Get message with code, arguments and default fallback message code
	 * 
	 * @param code
	 *            the message to get
	 * @param args
	 *            the arguments tu use
	 * @param defaultMessage
	 *            the default message code
	 * 
	 * @return message string
	 */
	public String getMessage(String code, Object[] args, String defaultMessage);

	/**
	 * Get required parameter message.
	 * 
	 * @param parameter
	 *            the parameter code
	 * 
	 * @return message string
	 */
	public String getRequiredParameterMessage(String parameter);

	/**
	 * Get's a standard error message
	 * 
	 * @param errorCode
	 *            the error code
	 * 
	 * @return message string
	 */
	public String errorMessage(String errorCode);

	/**
	 * Get's a standard error Json Response
	 * 
	 * @return Json Response
	 */
	public JsonResponse unexpectedErrorResponse();

	/**
	 * Get's a standard error Json Response with the specified code
	 * 
	 * @param errorCode
	 *            the error code to use
	 * 
	 * @return Json Response
	 */
	public JsonResponse unexpectedErrorResponse(String errorCode);

}
