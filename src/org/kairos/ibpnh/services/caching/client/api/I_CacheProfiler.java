package org.kairos.ibpnh.services.caching.client.api;

import java.util.List;
import java.util.Map;

/**
 * Defines method for profiling caches and its persisted objects.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
public interface I_CacheProfiler {

	/**
	 * Gets the list of caches observed by this profiler.
	 * 
	 * @return
	 */
	public List<String> cachesObserved();

	/**
	 * Gets the current key list, ordered alphanumerically, mapped by cache
	 * name.
	 * 
	 * @return
	 */
	public Map<String, List<String>> getKeyListMap();

	/**
	 * Checks if this profiler observes the specified cache.
	 * 
	 * @param cacheName
	 *            the cache name to check
	 * 
	 * @return true iif this profiler observes the specified cache
	 */
	public Boolean observes(String cacheName);

	/**
	 * Clears all the entries in the specified cache.
	 * 
	 * WARNING: Use with caution.
	 * 
	 * @param cacheName
	 *            the cache to clear
	 */
	public void clear(String cacheName);

	/**
	 * Returns a Map that for every specified entry, gets the value for that
	 * entry (as an Object).
	 * 
	 * @param cacheName
	 *            the cache name to get the entries from
	 * @param entryList
	 *            the entry list to fetch
	 * @return map
	 */
	public Map<String, Object> getEntries(String cacheName,
										  List<String> entryList);

	/**
	 * Removes the entries on the specified cache.
	 * 
	 * @param cacheName
	 *            cache to remove the entries from
	 * @param entryList
	 *            entries to remove
	 */
	public void removeEntries(String cacheName, List<String> entryList);
}
