package org.ibpnh.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds the functionality for EntityManager creation and access.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class EntityManagerHolder {

	/**
	 * Entity Manager Factory for getting new entity managers.
	 */
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Logger for this class.
	 */
	private Logger logger = LoggerFactory.getLogger(EntityManagerHolder.class);

	/**
	 * EntityManagerHolder Constructor.
	 * 
	 * @param persistenceFile
	 *            the path to the persistence file
	 * @param persistenceUnit
	 *            the name of the persistence unit
	 */
	public EntityManagerHolder(String persistenceFile, String persistenceUnit) {
		this.logger.debug("creating entity factory manager with persistence file: {} and persistence unit: {}", persistenceFile, persistenceUnit);

		// this tells the persistence unit file to the factory
		Map<String, String> properties = new HashMap<>();
		if (persistenceFile != null) {
			properties.put("eclipselink.persistencexml", persistenceFile);
		}

		this.entityManagerFactory = Persistence.createEntityManagerFactory(
				persistenceUnit, properties);
		// creates entity manager just to generate the DDL on the DDBB
		this.entityManagerFactory.createEntityManager();
	}

	/**
	 * Returns an EntityManager.
	 * 
	 * @return an EntityManager or null
	 */
	public EntityManager getEntityManager() {
		this.logger.debug("obtaining entity manager");
		if (this.entityManagerFactory != null) {
			EntityManager em = this.entityManagerFactory.createEntityManager();			
			return em;
		}
		this.logger
				.error("EntityManagerFactory was null, EntityManager couldn't be created");
		return null;
	}
	
	/**
	 * Closes an EntityManager.
	 * 
	 * @param em the entity manager to close.
	 */
	public void closeEntityManager(EntityManager em) {
		this.logger.debug("about to close an entity manager");
		
		try {
			if (em != null && em.isOpen()) {
				em.close();
			}
		} catch (Exception e) {
			this.logger.error("error trying to close Entity Manager");
		}
	}

}