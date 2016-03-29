package org.ibpnh.core.services.caching.client.api;

import org.ibpnh.core.vo.user.UserVo;

/**
 * Manages the cache for the users.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_UserCacheManager {
	
	/**
	 * Gets the UserVo using the key.
	 * 
	 * @param key to get the user
	 * 
	 * @return userVo or null
	 */
	public UserVo getUser(String key);
	
	/**
	 * Removes and returns the UserVo using the key.
	 * 
	 * @param key to remove the user
	 * 
	 * @return userVo or null
	 */
	public UserVo removeUser(String key);
	
	/**
	 * Puts the user in the cache using a key.
	 * 
	 * @param key key for the user
	 * @param userVo the user VO
	 */
	public void putUser(String key, UserVo userVo);
	

}
