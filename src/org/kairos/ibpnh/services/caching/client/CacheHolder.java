package org.kairos.ibpnh.services.caching.client;

/**
 * @author AxelCollardBovy ,created on 08/03/2015.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the functionality for Cache creation and access.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public class CacheHolder {

    /**
     * Default Schema Constant
     */
    public final static String DEFAULT_SCHEMA = "universe";

    /**
     * Cache Factory.
     */
    private CacheFactory cacheFactory;

    /**
     * User Cache instance singleton.
     */
    private Cache userCache;

    /**
     * Parameter Cache instance singleton.
     */
    private Cache parameterCache;

    /**
     * Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(CacheHolder.class);

    /**
     * CacheHolder Constructor.
     *
     * @param cacheConfigFile
     *            the path to the cache configuration file
     */
    public CacheHolder(String cacheConfigFile) {
        this.logger
                .debug("creating cache factory manager with configuration file: {} ",
                        cacheConfigFile);

        // this tells the persistence unit file to the factory
        Map<String, String> properties = new HashMap<>();
        try{
            this.cacheFactory = CacheManager.getInstance().getCacheFactory() ;

            this.logger.info("user cache="+this.userCache);
            // creates empty user cache instance
            this.userCache = this.cacheFactory.createCache(properties);

            this.logger.info("user cache="+this.userCache);

            // creates empty parameter cache instance
            this.parameterCache = this.cacheFactory.createCache(properties);
        }catch(CacheException e){
            this.logger.debug("could not create cacheholder");
            e.printStackTrace();
        }
    }

    /**
     * Returns a Cache instance.
     *
     * @return Cache instance or null
     */
    public Cache getUserCache() {
        this.logger.debug("obtaining user cache");
        if (this.userCache != null) {
            return userCache;
        }
        this.logger
                .error("user cache was null");
        return null;
    }

    public Cache getParameterCache() {
        this.logger.debug("obtaining parameter cache");
        if (this.parameterCache != null) {
            return parameterCache;
        }
        this.logger
                .error("parameter cache was null");
        return null;
    }
}