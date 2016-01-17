package org.kairos.ibpnh.utils.singleton;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory Bean for Google Image Service
 *
 * Created by Axel Collard Bovy on 6/20/15.
 */
public class ImagesServiceFactoryBean implements FactoryBean<ImagesService> {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(ImagesServiceFactoryBean.class);

    /**
     * Singleton Instance.
     */
    private ImagesService singleton = null;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public ImagesService getObject() throws Exception {
        if (this.singleton == null) {
            // we create the singleton instance
            singleton = ImagesServiceFactory.getImagesService();
        }

        // returns the singleton
        return this.singleton;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return ImagesService.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

}