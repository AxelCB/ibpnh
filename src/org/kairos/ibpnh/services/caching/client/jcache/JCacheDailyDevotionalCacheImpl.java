package org.kairos.ibpnh.services.caching.client.jcache;

import org.kairos.ibpnh.services.caching.client.CacheHolder;
import org.kairos.ibpnh.services.caching.client.api.I_CacheProfiler;
import org.kairos.ibpnh.services.caching.client.api.I_DevotionalCacheManager;
import org.kairos.ibpnh.vo.configuration.parameter.ParameterVo;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
import org.kairos.ibpnh.vo.user.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
public class JCacheDailyDevotionalCacheImpl implements I_CacheProfiler,//extends AbstractCacheProfiler
		I_DevotionalCacheManager {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(JCacheDailyDevotionalCacheImpl.class);

	/**
	 * Cache Holder
	 */
	private CacheHolder cacheHolder;

	/**
	 * A list of servers IPs and Ports.
	 */
	private String servers;

	/**
	 * The name of the User Cache.
	 */
	private String devotionalCacheName;

	/**
	 * Get's the Devotional cache from cache holder
	 * 
	 * @return Devotional cache
	 */
	private Cache getDevotionalCache() {
		return cacheHolder.getDevotionalCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_CacheProfiler#clear(java
	 * .lang.String)
	 */
	@Override
	public void clear(String cacheName) {
		if (cacheName.equals(this.getDevotionalCacheName())) {
			this.getDevotionalCache().clear();

			this.logger.debug("cleared cache {}", cacheName);
		} else {
			this.logger.error("cache {} not managed by this profiler",
					cacheName);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_CacheProfiler#getEntries
	 * (java.lang.String, java.util.List)
	 */
	@Override
	public Map<String, Object> getEntries(String cacheName,
			List<String> entryList) {
		if (cacheName.equals(this.getDevotionalCacheName())) {
			Map<String, Object> entryMap = new HashMap<>();
			
			for (String entry : entryList) {
//				entryMap.put(entry, this.getUserCache().getWithMetadata(entry));
			}

			this.logger.debug("entries getted for cache {}", cacheName);
			
			return entryMap;
		} else {
			this.logger.error("cache {} not managed by this profiler",
					cacheName);
		}
		
		return new HashMap<>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.universe.core.services.caching.client.api.I_CacheProfiler#removeEntries(java.lang.String, java.util.List)
	 */
	@Override
	public void removeEntries(String cacheName, List<String> entryList) {
		if (cacheName.equals(this.getDevotionalCacheName())) {
			for (String entry : entryList) {
				 this.getDevotionalCache().remove(entry);
			}

			this.logger.debug("entries getted for cache {}", cacheName);
		} else {
			this.logger.error("cache {} not managed by this profiler",
					cacheName);
		}
	}

	@Override
	public List<DailyDevotionalVo> getDevotionals(String key) {
		List<DailyDevotionalVo> devotionals = null;
		try {
			devotionals = (List<DailyDevotionalVo>) this.getDevotionalCache().get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return devotionals == null ? null : devotionals;
	}

	@Override
	public void putDevotionals(String key, List<DailyDevotionalVo> devotionalsToPutInCache) {
		try {
            this.getDevotionalCache().put(key,devotionalsToPutInCache);
			this.logger.info("Insertado Sync {}", key);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.universe.core.services.caching.client.api.I_DevotionalCacheManager#removeDevotional
	 * (java.lang.String)
	 */
	@Override
	public List<DailyDevotionalVo> removeDevotionals(String key) {
		List<DailyDevotionalVo> devotionals = null;
		try {
			devotionals = (List<DailyDevotionalVo>) this.getDevotionalCache().remove(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return devotionals;
	}

	/**
	 * @return the servers
	 */
	public String getServers() {
		return this.servers;
	}

	/**
	 * @param servers
	 *            the servers to set
	 */
	public void setServers(String servers) {
		this.servers = servers;
	}

	public String getDevotionalCacheName() {
		return devotionalCacheName;
	}

	public void setDevotionalCacheName(String devotionalCacheName) {
		this.devotionalCacheName = devotionalCacheName;
	}

	public CacheHolder getCacheHolder() {
		return cacheHolder;
	}

	public void setCacheHolder(CacheHolder cacheHolder) {
		this.cacheHolder = cacheHolder;
	}
}
