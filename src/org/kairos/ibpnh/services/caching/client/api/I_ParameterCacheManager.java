package org.kairos.ibpnh.services.caching.client.api;

import org.kairos.ibpnh.model.configuration.parameter.Parameter;

/**
 * Manages the cache for the parameters.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_ParameterCacheManager {
	
	/**
	 * Gets the Parameter using the parameter name.
	 * 
	 * @param name key to get the parameter
	 * 
	 * @return Parameter or null
	 */
	public Parameter getParameter(String name);
	
	/**
	 * Removes and returns the Parameter using the name.
	 * 
	 * @param name key to remove the parameter
	 * 
	 * @return Parameter or null
	 */
	public Parameter removeParameter(String name);
	
	/**
	 * Puts the parameter in the cache using the name.
	 * 
	 * @param name name of the parameter
	 * @param parameter the parameter VO
	 */
	public void putParameter(String name, Parameter parameter);
	
	/**
	 * Returns the keys of all stored parameters in the cache.
	 * 
	 * @return Set<String>
	 */
//	public Set<String> keySet();
}
