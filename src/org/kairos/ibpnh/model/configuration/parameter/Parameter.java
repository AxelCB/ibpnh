package org.kairos.ibpnh.model.configuration.parameter;

import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.Pojomatic;

/**
 * System Global Parameter
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public class Parameter implements I_Model{

	private String id;
	
	/**
	 * Parameter's name.
	 */
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
	private Boolean global;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * If this parameter is fixed. (name, type, and description cannot be changed).
	 */
	private Boolean fixed;

	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(String id) {
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
