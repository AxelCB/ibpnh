package org.kairos.ibpnh.core.fx.login;

import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.vo.user.UserVo;

/**
 * Login Response Strategy.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_LoginResponseStrategy {

	/**
	 * The user was not found.
	 * 
	 * @return
	 */
	public JsonResponse userNotFound();
	
	/**
	 * The user is not enabled to log in to the system.
	 * 
	 * @param disabledCause cause of disabling
	 * 
	 * @return
	 */
	public JsonResponse disabledUser(String disabledCause);
	
	/**
	 * The user has reached the maximum failed attempts to login.
	 * 
	 * @return
	 */
	public JsonResponse maxAttempts();
	
	/**
	 * The password does not match.
	 *  
	 * @return
	 */
	public JsonResponse badPassword(Long totalAttempts);
	
	/**
	 * User correctly logged.
	 * 
	 * @param userVo the user
	 * 
	 * @return
	 */
	public JsonResponse userLogged(UserVo userVo); 
	
	/**
	 * Unexpected error.
	 * 
	 * @param errorCode the error code
	 * 
	 * @return
	 */
	public JsonResponse unexpectedError(String errorCode);
}
