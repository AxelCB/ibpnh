package org.kairos.ibpnh.core.dao;

import javax.persistence.EntityManager;

/**
 * Defines a callback method for the entities that will be called
 * before creating them.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_EntityCreationListener {
	
	/**
	 * Method to be called before entity creation.
	 */
	public void beforeCreate(EntityManager em);	
	
}
