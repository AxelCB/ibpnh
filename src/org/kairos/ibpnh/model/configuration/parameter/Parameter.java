package org.kairos.ibpnh.model.configuration.parameter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;

/**
 * System Global Parameter
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class Parameter implements I_Model,Serializable{

	private static final long serialVersionUID = -4980729949255825921L;

	@Id
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Parameter's name.
	 */
	@Index
	private String name;

	/**
	 * Parameter's value.
	 */
	private String value;

	/**
	 * Description.
	 */
	private String description;

	/**
	 * Parameter's type.
	 */
	private E_ParameterType type;

	/**
	 * Global flag.
	 */
	@Index
	private Boolean global;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * If this parameter is fixed. (name, type, and description cannot be changed).
	 */
	private Boolean fixed;

	/**
	 * Default Constructor
	 */
	public Parameter() {
	}

	/**
	 * Constructor using fields
	 *
	 * @param name
	 * @param value
	 * @param description
	 * @param type
	 * @param global
	 * @param deleted
	 * @param fixed
	 */
	public Parameter(String name, String value, String description, E_ParameterType type, Boolean global, Boolean deleted, Boolean fixed) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
		this.global = global;
		this.deleted = deleted;
		this.fixed = fixed;
	}

	/*
         * (non-Javadoc)
         * @see org.universe.core.model.I_Model#getId()
         */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public E_ParameterType getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(E_ParameterType type) {
		this.type = type;
	}


	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#setDeleted(java.lang.Boolean)
	 */
	@Override
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

	/**
	 * @return the global
	 */
	public Boolean getGlobal() {
		return this.global;
	}

	/**
	 * @param global the global to set
	 */
	public void setGlobal(Boolean global) {
		this.global = global;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the fixed
	 */
	public Boolean getFixed() {
		return this.fixed;
	}

	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

}
