package org.ibpnh.core.services.caching.client.mock;

import org.ibpnh.core.services.caching.client.api.I_ParameterCacheManager;
import org.ibpnh.core.vo.configuration.parameter.ParameterVo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Mock Implementation of the Parameter Cache Manager.
 * 
 * @author Axel Collard Bovy
 *
 */
public class ParameterCacheManagerMockImpl implements I_ParameterCacheManager {

	private Map<String, ParameterVo> parameters = new HashMap<>();
	
	/* (non-Javadoc)
	 * @see org.ibpnh.core.caching.client.I_ParameterCacheManager#getParameter(java.lang.String)
	 */
	@Override
	public ParameterVo getParameter(String key) {
		return this.parameters.get(key);
	}

	/* (non-Javadoc)
	 * @see org.ibpnh.core.caching.client.I_ParameterCacheManager#putParameter(java.lang.String, org.ibpnh.core.vo.parameter.ParameterVo)
	 */
	@Override
	public void putParameter(String key, ParameterVo parameterVo) {
		this.parameters.put(key, parameterVo);
	}

	@Override
	public Set<String> keySet() {
		return parameters.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see org.ibpnh.core.services.caching.client.api.I_ParameterCacheManager#removeParameter(java.lang.String)
	 */
	@Override
	public ParameterVo removeParameter(String key) {
		return this.parameters.remove(key);
	}

}
