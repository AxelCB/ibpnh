package org.ibpnh.core.model.configuration.parameter;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.ibpnh.core.model.I_Model;

/**
 * System Global Parameter
 * 
 * @author Axel Collard Bovy
 *
 */
@Entity
@AutoProperty(policy=DefaultPojomaticPolicy.TO_STRING)
public class Parameter implements Serializable, I_Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7553642112124826241L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parameter_seq")
	@SequenceGenerator(
			
			name="parameter_seq",
			sequenceName="parameter_seq",
			allocationSize=1)
	@Property(policy=PojomaticPolicy.EQUALS)	
	private Long id;
	
	/**
	 * Parameter's name.
	 */
	private String name;
	
	/**
	 * Comma-separated list of tags from a parameter.
	 */
	private String tags;
	
	/**
	 * Parameter's value.
	 */
	@Lob
	private String value;
	
	/**
	 * Description.
	 */
	private String description;
	
	/**
	 * Parameter's type.
	 */
	@Enumerated(EnumType.STRING)
	private E_ParameterType type;
	
	/**
	 * Global flag.
	 */
	private Boolean global;

	/**
	 * Parameter History.
	 */
	@OneToMany(mappedBy="parameter")
	private List<ParameterHistory> history;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * If this parameter is fixed. (name, type, and description cannot be changed). 
	 */
	private Boolean fixed;
	
	/**
	 * COMPLETA 
	 */
	private Boolean viewed;

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

	/**
	 * @return the history
	 */
	public List<ParameterHistory> getHistory() {
		return this.history;
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory(List<ParameterHistory> history) {
		this.history = history;
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

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return if viewed
	 */
	public Boolean getViewed() {
		return viewed;
	}

	/**
	 * @param viewed the viewed to set
	 */
	public void setViewed(Boolean viewed) {
		this.viewed = viewed;
	}
	
}
