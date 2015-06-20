package org.kairos.ibpnh.utils.singleton;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Factory Bean for Google Blobstore Service
 *
 * Created by Axel Collard Bovy on 6/20/15.
 */
public class BlobstoreServiceFactoryBean implements FactoryBean<BlobstoreService> {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(BlobstoreServiceFactoryBean.class);

    /**
     * Singleton Instance.
     */
    private BlobstoreService singleton = null;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public BlobstoreService getObject() throws Exception {
        if (this.singleton == null) {
            // we create the singleton instance
            singleton = BlobstoreServiceFactory.getBlobstoreService();
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
        return BlobstoreService.class;
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