package org.kairos.ibpnh.vo;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;

/**
 * Abstract Value Object.
 * 
 * @author acollard
 *
 */
public abstract class AbstractVo implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7965505608380560059L;
	
	/**
	 * VO id.
	 */
	@Property(policy=PojomaticPolicy.ALL)
	private Long id;

	/**
	 * VO deleted.
	 */
	@Property(policy=PojomaticPolicy.NONE)
	private Boolean deleted;
	
	/**
	 * Validates this VO.
	 * 
	 * @return string iif is not valid
	 */
	public String validate() {
		return null;
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 *
	 * @param deleted
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
	
}
