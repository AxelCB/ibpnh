package org.ibpnh.core.services.caching.client.mock;

import java.util.HashMap;
import java.util.Map;

import org.ibpnh.core.services.caching.client.api.I_UserCacheManager;
import org.ibpnh.core.vo.user.UserVo;

/**
 * Mock Implementation of the User Cache Manager.
 * 
 * @author Axel Collard Bovy
 *
 */
public class UserCacheManagerMockImpl implements I_UserCacheManager {

	private Map<String, UserVo> users = new HashMap<>();
	
	/* (non-Javadoc)
	 * @see org.ibpnh.core.caching.client.I_UserCacheManager#getUser(java.lang.String)
	 */
	@Override
	public UserVo getUser(String key) {
		return this.users.get(key);
	}

	/* (non-Javadoc)
	 * @see org.ibpnh.core.caching.client.I_UserCacheManager#putUser(java.lang.String, org.ibpnh.core.vo.user.UserVo)
	 */
	@Override
	public void putUser(String key, UserVo userVo) {
		this.users.put(key, userVo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.ibpnh.core.services.caching.client.api.I_UserCacheManager#removeUser(java.lang.String)
	 */
	@Override
	public UserVo removeUser(String key) {
		return this.users.remove(key);
	}

}
