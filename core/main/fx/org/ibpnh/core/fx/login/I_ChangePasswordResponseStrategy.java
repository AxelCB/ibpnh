package org.ibpnh.core.fx.login;

import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.vo.user.UserVo;

/**
 * Change Password Response Strategy
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_ChangePasswordResponseStrategy {
	
	/**
	 * Current password does not match.
	 * 
	 * @return
	 */
	public JsonResponse badCurrentPassword();

	/**
	 * New password equals current one.
	 * 
	 * @return
	 */
	public JsonResponse newPasswordEqualsCurrent();
	
	/**
	 * New password equals username.
	 * 
	 * @return
	 */
	public JsonResponse newPasswordEqualsUsername();
	
	/**
	 * New password has an alphanumeric error.
	 * 
	 * @param message the error message retrieved from the DB parameters
	 * @return
	 */
	public JsonResponse newPasswordAlphanumericError(String errorMessage);
	
	/**
	 * New password has not the minimum characters required.
	 * 
	 * @param minCharacters the minimum character required
	 * 
	 * @return
	 */
	public JsonResponse newPasswordMinimumCharactersError(Long minCharacters);
	
	/**
	 * Unexpected error.
	 * 
	 * @param errorCode the error code
	 * 
	 * @return
	 */
	public JsonResponse unexpectedError(String errorCode);
	
	/**
	 * Password successfully changed.
	 * 
	 * @param userVo the user VO
	 * 
	 * @return
	 */
	public JsonResponse passwordChanged(UserVo userVo);
	
}
