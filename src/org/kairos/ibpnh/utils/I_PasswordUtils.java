package org.kairos.ibpnh.utils;


import org.kairos.ibpnh.model.user.User;

/**
 * Interface for Password utils
 * 
 * @author acollard
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
	 * @param user
	 * @param currentCost
	 * @return
	 */
	public Boolean checkPassword(String password, User user,
								 Long currentCost);

}
