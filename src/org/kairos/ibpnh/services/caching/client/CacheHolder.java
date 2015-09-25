package org.kairos.ibpnh.services.caching.client;

/**
 * @author AxelCollardBovy ,created on 08/03/2015.
 */

import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.Caching;
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
     * @param userCacheName
     *            the name of the user cache
     * @param parameterCacheName
     *            the name of the parameter cache
     */
    public CacheHolder(String userCacheName,String parameterCacheName) {
        this.logger
                .debug("creating cache factory manager user cache: {} and parameter cache: {}",
                        userCacheName,parameterCacheName);

        // this tells the persistence unit file to the factory
        Map<String, String> properties = new HashMap<>();
        try{
            this.logger.info("user cache="+this.userCache);
            // creates empty user cache instance
            this.userCache = Caching.getCache(userCacheName,String.class,User.class);

            this.logger.info("user cache="+this.userCache);

            // creates empty parameter cache instance
            this.parameterCache = Caching.getCache(parameterCacheName, String.class, Parameter.class);
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