package org.kairos.ibpnh.core.services.caching.client.api;

import java.util.Set;

import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;

/**
 * Manages the cache for the parameters.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_ParameterCacheManager {
	
	/**
	 * Gets the ParameterVo using the parameter name.
	 * 
	 * @param name key to get the user
	 * 
	 * @return ParameterVo or null
	 */
	public ParameterVo getParameter(String name);
	
	/**
	 * Removes and returns the ParameterVo using the name.
	 * 
	 * @param name key to remove the user
	 * 
	 * @return ParameterVo or null
	 */
	public ParameterVo removeParameter(String name);
	
	/**
	 * Puts the parameter in the cache using the name.
	 * 
	 * @param name name of the parameter
	 * @param parameterVo the parameter VO
	 */
	public void putParameter(String name, ParameterVo parameterVo);
	
	/**
	 * Returns the keys of all stored parameters in the cache.
	 * 
	 * @return Set<String>
	 */
	public Set<String> keySet();
}
