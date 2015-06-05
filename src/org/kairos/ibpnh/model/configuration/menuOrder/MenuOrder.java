package org.kairos.ibpnh.model.configuration.menuOrder;

import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.List;

/**
 * Defines a menu order.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@PersistenceCapable
@AutoProperty(policy= DefaultPojomaticPolicy.TO_STRING)
public class MenuOrder implements Serializable, I_Model {//, I_EntityCreationListener, I_EntityUpdateListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7523652730060812091L;

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
	 * Order name.
	 */
    @Persistent
	private String name;
	
	/**
	 * Order description.
	 */
    @Persistent
	private String description;
	
	/**
	 * MenuOrder-Function associations for this menu order.
	 */
    @Persistent(mappedBy = "menuOrder")
	private List<MenuOrderFunction> menuOrderFunctions;

	/**
	 * Fixes the collection created by Dozer.
	 */
	public void fixMenuOrderFunctions() {
		if (this.getMenuOrderFunctions() != null) {
			for (MenuOrderFunction menuOrderFunction : this.getMenuOrderFunctions()) {
				//sets the deletion flag to false
				menuOrderFunction.setDeleted(Boolean.FALSE);
				//sets the other side of the relationship if it is not present
				if (menuOrderFunction.getMenuOrder() == null) {
					menuOrderFunction.setMenuOrder(this);
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityCreationListener#beforeCreate(javax.persistence.EntityManager)
	 */
/*	@Override
	public void beforeCreate(EntityManager em) {
		this.fixMenuOrderFunctions();
	}
	
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityUpdateListener#beforeMap()
	 *//*
	@Override
	public void beforeMap() {
		//does nothing
	}
	
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityUpdateListener#beforeUpdate(javax.persistence.EntityManager)
	 *//*
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixMenuOrderFunctions();
	}*/
	
	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the deleted
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
	 * @return the menuOrderFunctions
	 */
	public List<MenuOrderFunction> getMenuOrderFunctions() {
		return this.menuOrderFunctions;
	}

	/**
	 * @param menuOrderFunctions the menuOrderFunctions to set
	 */
	public void setMenuOrderFunctions(List<MenuOrderFunction> menuOrderFunctions) {
		this.menuOrderFunctions = menuOrderFunctions;
	}
	
}
