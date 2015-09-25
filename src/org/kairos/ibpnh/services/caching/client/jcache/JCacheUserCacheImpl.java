package org.kairos.ibpnh.services.caching.client.jcache;

import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.services.caching.client.CacheHolder;
import org.kairos.ibpnh.services.caching.client.api.I_CacheProfiler;
import org.kairos.ibpnh.services.caching.client.api.I_ParameterCacheManager;
import org.kairos.ibpnh.services.caching.client.api.I_UserCacheManager;
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
public class JCacheUserCacheImpl implements I_CacheProfiler,//extends AbstractCacheProfiler
		I_UserCacheManager, I_ParameterCacheManager {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(JCacheUserCacheImpl.class);

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
	private String userCacheName;

	/**
	 * Parameter Cache Name.
	 */
	private String parameterCacheName;
	
	/**
	 * User duration in cache
	 */
	private Long userDuration;
	
	/**
	 * TimeUnit of user duration in cache
	 */
	private String timeUnit;
	
	
	/**
	 * Get's the User cache from cache holder
	 * 
	 * @return User cache
	 */
	private Cache getUserCache() {
		return cacheHolder.getUserCache();
	}



	/**
	 * Get's the typed remote Parameter cache
	 * 
	 * @return remote Parameter cache
	 */
	private Cache getParameterCache() {
		return cacheHolder.getParameterCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_CacheProfiler#cachesObserved
	 * ()
	 */
//	@Override
//	public List<String> cachesObserved() {
//		return Arrays.asList(new String[] { this.getUserCacheName(),
//				this.getParameterCacheName() });
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.hotrod.AbstractCacheProfiler
	 * #_keySet()
	 */
//	@Override
//	protected Map<String, Set<String>> _keySet() {
//		Map<String, Set<String>> keySetMap = new HashMap<>();
//
//		keySetMap.put(this.getUserCacheName(), this.getUserCache().keySet());
//		keySetMap.put(this.getParameterCacheName(), this.getParameterCache()
//				.keySet());
//
//		return keySetMap;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_CacheProfiler#observes
	 * (java.lang.String)
	 */
//	@Override
//	public Boolean observes(String cacheName) {
//		return cacheName.equals(this.getUserCacheName())
//				|| cacheName.equals(this.getParameterCacheName());
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_CacheProfiler#clear(java
	 * .lang.String)
	 */
	@Override
	public void clear(String cacheName) {
		if (cacheName.equals(this.getUserCacheName())) {
			this.getUserCache().clear();

			this.logger.debug("cleared cache {}", cacheName);
		} else if (cacheName.equals(this.getParameterCacheName())) {
			this.getParameterCache().clear();

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
		if (cacheName.equals(this.getUserCacheName())) {
			Map<String, Object> entryMap = new HashMap<>();
			
			for (String entry : entryList) {
//				entryMap.put(entry, this.getUserCache().getWithMetadata(entry));
			}

			this.logger.debug("entries getted for cache {}", cacheName);
			
			return entryMap;
		} else if (cacheName.equals(this.getParameterCacheName())) {
			Map<String, Object> entryMap = new HashMap<>();
			
			for (String entry : entryList) {
//				entryMap.put(entry, this.getParameterCache().getWithMetadata(entry));
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
		if (cacheName.equals(this.getUserCacheName())) {
			for (String entry : entryList) {
				 this.getUserCache().remove(entry);
			}

			this.logger.debug("entries getted for cache {}", cacheName);
		} else if (cacheName.equals(this.getParameterCacheName())) {
			for (String entry : entryList) {
				this.getParameterCache().remove(entry);
			}

			this.logger.debug("entries getted for cache {}", cacheName);
		} else {
			this.logger.error("cache {} not managed by this profiler",
					cacheName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.caching.client.I_UserCacheManager#getUser(java.lang
	 * .String)
	 */
	@Override
	public User getUser(String key) {
		User user = null;
		try {
			user = (User) this.getUserCache().get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user == null ? null : user;
	}

	@Override
	public void putUser(String key, User userInCache) {
		try {
			//TODO: configurar valor y unidad
            this.getUserCache().put(key,userInCache);
			this.logger.info("Insertado Sync {}", key);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_UserCacheManager#removeUser
	 * (java.lang.String)
	 */
	@Override
	public User removeUser(String key) {
		User user = null;
		try {
			user = (User) this.getUserCache().get(key);
			if(!this.getUserCache().remove(key)){
				throw new Exception("Couldn't remove user from cache");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_ParameterCacheManager
	 * #getParameter(java.lang.String)
	 */
	@Override
	public Parameter getParameter(String name) {
		Parameter parameter = null;
		try {
			parameter = (Parameter) this.getParameterCache().get(name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parameter == null ? null : parameter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_ParameterCacheManager
	 * #putParameter(java.lang.String,
	 * org.universe.core.vo.configuration.parameter.ParameterVo)
	 */
	@Override
	public void putParameter(String name, Parameter parameterVo) {
		try {
            this.getParameterCache().put(name, parameterVo);
			this.logger.info("Insertado Sync {}", name);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_ParameterCacheManager
	 * #removeParameter(java.lang.String)
	 */
	@Override
	public Parameter removeParameter(String name) {
		Parameter parameter = null;
		try {
			parameter = (Parameter) this.getParameterCache().get(name);
			if(!this.getUserCache().remove(name)){
				throw new Exception("Couldn't remove parameter from cache");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parameter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universe.core.services.caching.client.api.I_ParameterCacheManager
	 * #keySet()
	 */
//	@Override
//	public Set<String> keySet() {
//		try {
//			return this.getParameterCache().keySet();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	/**
	 * @return the cacheContainer
	 */
/*	public RemoteCacheManager getCacheContainer() {
		if (this.cacheContainer == null) {
			// the configuration builder for running the hot rod client manager.
			ConfigurationBuilder b = new ConfigurationBuilder();
			b.addServers(this.getServers());

			b.tcpNoDelay(true);
			b.balancingStrategy(RoundRobinBalancingStrategy.class);
			b.pingOnStartup(true);
			b.socketTimeout(5000);
			b.marshaller(GenericJBossMarshaller.class);
			b.transportFactory(TcpTransportFactory.class);

			this.cacheContainer = new RemoteCacheManager(b.build());
		}

		return this.cacheContainer;
	}*/

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

	/**
	 * @return the userCacheName
	 */
	public String getUserCacheName() {
		return this.userCacheName;
	}

	/**
	 * @param userCacheName
	 *            the userCacheName to set
	 */
	public void setUserCacheName(String userCacheName) {
		this.userCacheName = userCacheName;
	}

	/**
	 * @return the parameterCacheName
	 */
	public String getParameterCacheName() {
		return this.parameterCacheName;
	}

	/**
	 * @param parameterCacheName
	 *            the parameterCacheName to set
	 */
	public void setParameterCacheName(String parameterCacheName) {
		this.parameterCacheName = parameterCacheName;
	}

	/**
	 * @return the userDuration
	 */
	public Long getUserDuration() {
		return userDuration;
	}

	/**
	 * @param userDuration the userDuration to set
	 */
	public void setUserDuration(Long userDuration) {
		this.userDuration = userDuration;
	}

	/**
	 * @return the timeUnit
	 */
	public String getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public CacheHolder getCacheHolder() {
		return cacheHolder;
	}

	public void setCacheHolder(CacheHolder cacheHolder) {
		this.cacheHolder = cacheHolder;
	}
}
