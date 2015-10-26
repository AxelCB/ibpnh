package org.kairos.ibpnh.fx;

import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.vo.user.UserVo;

/**
 * Login Response Strategy.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
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
	 * @param user the user
	 * 
	 * @return
	 */
	public JsonResponse userLogged(UserVo user);
	
	/**
	 * Unexpected error.
	 * 
	 * @param errorCode the error code
	 * 
	 * @return
	 */
	public JsonResponse unexpectedError(String errorCode);
}
