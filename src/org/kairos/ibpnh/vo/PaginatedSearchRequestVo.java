package org.kairos.ibpnh.vo;


/**
 * Value Object for a search request that is used with pagination.
 * 
 * @author fgonzalez
 *
 * @param <E> the type of objects to be listed
 */
public class PaginatedSearchRequestVo<E> extends PaginatedRequestVo {

	/**
	 * The data to use to filter the entities (could be null in case of a full list)
	 */
	private E entity;

	/**
	 * Constructor with VO. 
	 * 
	 * @param entity
	 */
	public PaginatedSearchRequestVo(E entity) {
		super();
		this.setEntity(entity);
	}

	/**
	 * @return the entity
	 */
	public E getEntity() {
		return this.entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(E entity) {
		this.entity = entity;
	}
	
}
