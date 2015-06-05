package org.kairos.ibpnh.dao;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Holds the functionality for JDOPersistenceManager creation and access.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public class PersistenceManagerHolder {

    /**
     * Default Schema Constant
     */
    public final static String DEFAULT_PERSISTENCE_MANAGER_FACTORY_NAME = "transactions-optional";

    /**
     * Entity Manager Factory for getting new entity managers.
     */
    private PersistenceManagerFactory persistenceManagerFactory;

    /**
     * Logger for this class.
     */
    private Logger logger = LoggerFactory.getLogger(PersistenceManagerHolder.class);

    /**
     * PersistenceManagerHolder Constructor.
     *
     * @param persistenceFile
     *            the path to the persistence file
     * param persistenceUnit
     *            the name of the persistence unit
     */
    public PersistenceManagerHolder(String persistenceFile) {
        this.logger
                .debug("creating persistence factory manager with persistence file: {} and persistence unit: {}",
                        persistenceFile);

        // this tells the persistence manager factory name to the factory
        this.persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory(this.DEFAULT_PERSISTENCE_MANAGER_FACTORY_NAME);
                //.createEntityManagerFactory(
//                persistenceUnit, properties);
        // creates entity manager just to generate the DDL on the DDBB
        this.persistenceManagerFactory.getPersistenceManager().close();
    }

    /**
     * Returns an PersistenceManager.
     *
     * @return an JDOPersistenceManager or null
     */
    public JDOPersistenceManager getPersistenceManager() {
        this.logger.debug("obtaining entity manager");
        if (this.persistenceManagerFactory != null) {
            JDOPersistenceManager pm = (JDOPersistenceManager) this.persistenceManagerFactory.getPersistenceManager();
            return pm;
        }
        this.logger
                .error("PersistenceManagerFactory was null, JDOPersistenceManager couldn't be created");
        return null;
    }

    /**
     * Closes an PersistenceManager.
     *
     * @param pm the Persistence Manager to close.
     */
    public void closePersistenceManager(JDOPersistenceManager pm) {
        this.logger.debug("about to close an persistence manager");

        try {
            if (pm != null && ! pm.isClosed()) {
                pm.close();
            }
        } catch (Exception e) {
            this.logger.error("error trying to close Persistence Manager");
        }
    }

}