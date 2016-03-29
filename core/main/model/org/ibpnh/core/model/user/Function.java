package org.ibpnh.core.model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.ibpnh.core.model.I_Model;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

/**
 * A Function is an abstract representation of an executable function in the system.
 * 
 * @author Axel Collard Bovy
 *
 */
@Entity
@AutoProperty(policy=DefaultPojomaticPolicy.TO_STRING)
public class Function implements Serializable, I_Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4873730519884775663L;


	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="function_seq")
	@SequenceGenerator(
			
			name="function_seq",
			sequenceName="function_seq",
			allocationSize=1)
	@Property(policy=PojomaticPolicy.HASHCODE_EQUALS)	
	private Long id;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * Function name.
	 */
	private String name;
	
	/**
	 * Function description.
	 */
	private String description;
	
	/**
	 * Function menu name.
	 */
	private String menuName;
	
	/**
	 * Function subMenu name.
	 */
	private String submenuName;
	
	/**
	 * Function action name.
	 */
	private String actionName;
	
	/**
	 * Function access URI.
	 */
	private String uri;
	
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
	 * @return the menuName
	 */
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the submenuName
	 */
	public String getSubmenuName() {
		return this.submenuName;
	}

	/**
	 * @param submenuName the submenuName to set
	 */
	public void setSubmenuName(String submenuName) {
		this.submenuName = submenuName;
	}

	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return this.actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
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
	 * @return the uri
	 */
	public String getUri() {
		return this.uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
