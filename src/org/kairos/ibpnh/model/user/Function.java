package org.kairos.ibpnh.model.user;

import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;

import javax.jdo.annotations.*;
import java.io.Serializable;

/**
 * A Function is an abstract representation of an executable function in the system.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@PersistenceCapable
@AutoProperty(policy= DefaultPojomaticPolicy.TO_STRING)
public class Function implements Serializable, I_Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4873730519884775663L;


	/**
	 * Entity ID.
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	/**
	 * Logic deletion flag.
	 */
    @Persistent
	private Boolean deleted;
	
	/**
	 * Function name.
	 */
    @Persistent
	private String name;
	
	/**
	 * Function description.
	 */
    @Persistent
	private String description;
	
	/**
	 * Function menu name.
	 */
    @Persistent
	private String menuName;
	
	/**
	 * Function subMenu name.
	 */
    @Persistent
	private String submenuName;
	
	/**
	 * Function action name.
	 */
    @Persistent
	private String actionName;
	
	/**
	 * Function access URI.
	 */
    @Persistent
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
