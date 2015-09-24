package org.kairos.ibpnh.services.caching.client.api;

import org.kairos.ibpnh.model.user.User;

/**
 * Manages the cache for the users.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_UserCacheManager {
	
	/**
	 * Gets the User using the key.
	 * 
	 * @param key to get the user
	 * 
	 * @return user or null
	 */
	public User getUser(String key);
	
	/**
	 * Removes and returns the User using the key.
	 * 
	 * @param key to remove the user
	 * 
	 * @return user or null
	 */
	public User removeUser(String key);
	
	/**
	 * Puts the user in the cache using a key.
	 * 
	 * @param key key for the user
	 * @param user the user VO
	 */
	public void putUser(String key, User user);
	

}
