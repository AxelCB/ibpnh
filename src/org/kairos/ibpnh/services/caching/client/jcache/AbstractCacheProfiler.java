package org.kairos.ibpnh.services.caching.client.jcache;

import org.kairos.ibpnh.services.caching.client.api.I_CacheProfiler;

import java.util.*;

/**
 * Abstract implementation of the cache profiler interface.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
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
	 * @see org.universe.core.services.caching.client.api.I_CacheProfiler#getKeyListMap()
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
