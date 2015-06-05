package org.kairos.ibpnh.web;

import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.utils.ErrorCodes;
import org.kairos.ibpnh.vo.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

/**
 * Holds web context specific objects 
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public class WebContextHolder implements I_MessageSolver {
	
	/**
	 * Web Context Holder proxy bean name.
	 */
	public static String WEB_CONTEXT_HOLDER_PROXY_BEAN_NAME = "scopedTarget.webContextHolder";
	
	/**
	 * Current Locale.
	 */
	private Locale locale;
	
	/**
	 * User Info
	 */
	private UserVo userVo;
	
	/**
	 * User VO that only holds the user's ID;
	 */
	private UserVo userIdReferenceVo;
	
	/**
	 * THe message solver.
	 */
	@Autowired
	private MessageSolver messageSolver;
	
	/**
	 * Empty Constructor.
	 */
	public WebContextHolder() {
		super();
	}

	/**
	 * Convenience method for getting the actual user token
	 * 
	 * @return user token
	 */
	public String getToken() {
		return this.getUserVo().getToken();
	}
	
	/**
	 * Gets a i18n message.
	 * 
	 * @param code message code
	 *
	 * @return string
	 */
	@Override
	public String getMessage(String code) {
		return this.getMessageSolver().getMessage(code, new String[]{}, this.getLocale());
	}
	
	/**
	 * Gets a i18n message.
	 * 
	 * @param code message code
	 * @param args message parameters
	 *
	 * @return string
	 */
	@Override
	public String getMessage(String code, Object[] args) {
		return this.getMessageSolver().getMessage(code, args, this.getLocale());
	}
	
	/**
	 * Gets a i18n message.
	 * 
	 * @param code message code
	 * @param args message parameters
	 * @param defaultMessage default message to use if lookup fail
	 *
	 * @return string
	 */
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage) {
		return this.getMessageSolver().getMessage(code, args, defaultMessage, this.getLocale());
	}
	
	/**
	 * Gets the default message for a required parameter that is missing.
	 * 
	 * @param parameter the parameter code
	 * 
	 * @return String
	 */
	@Override
	public String getRequiredParameterMessage(String parameter) {
		String field = this.getMessage(parameter);

		return this.getMessage(
				"default.fx.validation.parameter.required",
				new String[] { field });
	}
	
	/**
	 * Generates an error message with the specified code.
	 * 
	 * @param errorCode the error code
	 * 
	 * @return string
	 */
	@Override
	public String errorMessage(String errorCode) {
		String errorCodeMessage = this.getMessage("default.error.code", new Object[]{errorCode});
		
		return this.getMessage("default.error.message", new String[] {errorCodeMessage});
	}
	
	/**
	 * Generates a standard error response with a generic message.
	 * 
	 * @return jsonResponse
	 */
	@Override
	public JsonResponse unexpectedErrorResponse() {
		return this.unexpectedErrorResponse(ErrorCodes.ERROR_UNEXPECTED);
	}
	
	/**
	 * 
	 * Generates a standard error response with a generic message.
	 * 
	 * @param errorCode the error code to set
	 *  
	 * @return jsonResponse
	 */
	@Override
	public JsonResponse unexpectedErrorResponse(String errorCode) {
		return JsonResponse.error("", this.errorMessage(errorCode));
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return this.locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return this.userVo;
	}

	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
		UserVo userIdReferenceVo =  new UserVo();
		userIdReferenceVo.setId(userVo.getId());
		this.setUserIdReferenceVo(userIdReferenceVo);
	}
	
	/**
	 * @return the userIdReferenceVo
	 */
	public UserVo getUserIdReferenceVo() {
		return this.userIdReferenceVo;
	}

	/**
	 * @param userIdReferenceVo the userIdReferenceVo to set
	 */
	public void setUserIdReferenceVo(UserVo userIdReferenceVo) {
		this.userIdReferenceVo = userIdReferenceVo;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.getUserVo().getUsername();
	}

	/**
	 * @return the messageSolver
	 */
	public MessageSolver getMessageSolver() {
		return this.messageSolver;
	}

	/**
	 * @param messageSolver the messageSolver to set
	 */
	public void setMessageSolver(MessageSolver messageSolver) {
		this.messageSolver = messageSolver;
	}
	
}
