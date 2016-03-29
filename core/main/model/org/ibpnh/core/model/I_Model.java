package org.ibpnh.core.model;

/**
 * General Model Interface.
 *
 * @author Axel Collard Bovy
 *
 */
public interface I_Model {

	/**
	 * Entity ID getter.
	 * 
	 * @return id
	 */
	public Long getId();
	
	/**
	 * Entity ID setter.
	 * 
	 * @param id to set
	 */
	public void setId(Long id);
	
	/**
	 * Entity Deletion Flag getter.
	 * 
	 * @return is deleted
	 */
	public Boolean getDeleted();
	
	/**
	 * Entity Deletion Flag setter.
	 * 
	 * @param deleted flag to set
	 */
	public void setDeleted(Boolean deleted);
	
}
