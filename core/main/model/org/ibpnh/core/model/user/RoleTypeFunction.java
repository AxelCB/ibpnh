package org.ibpnh.core.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.ibpnh.core.model.I_Model;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

/**
 * Many-to-many union between Role Types and Functions. 
 * 
 * @author Axel Collard Bovy
 *
 */
@Entity
@AutoProperty(policy=DefaultPojomaticPolicy.TO_STRING)
public class RoleTypeFunction implements Serializable, I_Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3186492869741821008L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="roletypefunction_seq")
	@SequenceGenerator(
			
			name="roletypefunction_seq",
			sequenceName="roletypefunction_seq",
			allocationSize=1)
	@Property(policy=PojomaticPolicy.HASHCODE_EQUALS)
	private Long id;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * Enabled flag.
	 */
	private Boolean enabled;
	
	/**
	 * Disabled cause.
	 */
	private String disabledCause;
	
	/**
	 * RoleType side.
	 */
	@ManyToOne
	@Property(policy=PojomaticPolicy.NONE)
	private RoleType roleType;
	
	/**
	 * Function side.
	 */
	@ManyToOne
	@Property(policy=PojomaticPolicy.HASHCODE_EQUALS)
	private Function function;
	
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
	
	/*
	 * (non-Javadoc)
	 * @see I_Model#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see I_Model#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 * @see I_Model#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the roleType
	 */
	public RoleType getRoleType() {
		return this.roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the function
	 */
	public Function getFunction() {
		return this.function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the disabledCause
	 */
	public String getDisabledCause() {
		return this.disabledCause;
	}

	/**
	 * @param disabledCause the disabledCause to set
	 */
	public void setDisabledCause(String disabledCause) {
		this.disabledCause = disabledCause;
	}
}
