package org.ibpnh.core.dao;

import javax.persistence.EntityManager;

/**
 * Defines a callback method for the entities that will be called
 * before updating them.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_EntityUpdateListener {
	
	/**
	 * Method to be called before entity update.
	 * 
	 * @param em the entity manager
	 */
	public void beforeUpdate(EntityManager em);
	
	/**
	 * Method to be called once the entity being updated
	 * has been loaded but has not been mapped by Dozer.
	 * 
	 */
	public void beforeMap();
	
}
