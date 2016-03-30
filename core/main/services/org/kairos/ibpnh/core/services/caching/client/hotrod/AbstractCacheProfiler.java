package org.kairos.ibpnh.core.services.caching.client.hotrod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kairos.ibpnh.core.services.caching.client.api.I_CacheProfiler;

/**
 * Abstract implementation of the cache profiler interface.
 * 
 * @author Axel Collard Bovy
 * 
 */
public abstract class AbstractCacheProfiler implements I_CacheProfiler {

	/**
	 * Gets the set of keys returned by the cache.
	 * 
	 * @return Set<String>
	 */
	protected abstract Map<String, Set<String>> _keySet();

	/*
	 * (non-Javadoc)
	 * @see I_CacheProfiler#getKeyListMap()
	 */
	@Override
	public Map<String, List<String>> getKeyListMap() {
		Map<String, List<String>> keyListMap = new HashMap<>();
		Map<String, Set<String>> keySetMap = this._keySet();
		
		for (String cacheName : this.cachesObserved()) {
			List<String> keyList = new ArrayList<>();
			
			keyList.addAll(keySetMap.get(cacheName));
	
			try {
				Collections.sort(keyList);
			} catch (NullPointerException npe) {
				// does nothing
			}
			
			keyListMap.put(cacheName, keyList);
		}
		
		return keyListMap;
	}
}
