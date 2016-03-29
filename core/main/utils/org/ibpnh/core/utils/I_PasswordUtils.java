package org.ibpnh.core.utils;

import org.ibpnh.core.vo.user.UserVo;

/**
 * Interface for Password utils
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_PasswordUtils {
	
	/**
	 * Checks potential password against parameter regExp
	 * 
	 * @param password
	 * @return
	 */
	public Boolean formatCheck(String password);
	
	/**
	 * Check password of a user.
	 * 
	 * @param password
	 * @param userVo
	 * @param currentCost
	 * @return
	 */
	public Boolean checkPassword(String password, UserVo userVo,
			Long currentCost);

}
