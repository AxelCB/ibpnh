package org.kairos.ibpnh.services.caching.client.api;


import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;

import java.util.List;

/**
 * Manages the cache for the devotionals.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_DevotionalCacheManager {
	
	/**
	 * Gets the Devotionals using the key.
	 * 
	 * @param key to get the devotionals
	 * 
	 * @return devotionals or null
	 */
	public List<DailyDevotionalVo> getDevotionals(String key);
	
	/**
	 * Removes and returns the Devotionals using the key.
	 * 
	 * @param key to remove the Devotionals
	 * 
	 * @return devotional list or null
	 */
	public List<DailyDevotionalVo> removeDevotionals(String key);
	
	/**
	 * Puts the devotionals in the cache using a key.
	 * 
	 * @param key key for the devotional
	 * @param devotionals the devotional VO
	 */
	public void putDevotionals(String key, List<DailyDevotionalVo> devotionals);
	

}
