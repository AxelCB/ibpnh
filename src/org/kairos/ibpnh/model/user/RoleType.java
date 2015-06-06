package org.kairos.ibpnh.model.user;

import com.google.appengine.datanucleus.annotations.Unowned;
import org.kairos.ibpnh.model.I_Model;
import org.kairos.ibpnh.model.configuration.menuOrder.MenuOrder;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.List;


/**
 * Represents an archetype role with its permissions and functions.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 */
@PersistenceCapable
@AutoProperty(policy= DefaultPojomaticPolicy.TO_STRING)
public class RoleType implements Serializable, I_Model { //, I_EntityCreationListener, I_EntityUpdateListener {

    /**
     *
     */
    private static final long serialVersionUID = 2014065357918906428L;

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
    private Boolean deleted = Boolean.FALSE;

    /**
     * The name of the role.
     */
    @Persistent
    private String name;

    /**
     * A description for the role.
     */
    @Persistent
	private String description;
	
	/**
	 * The RoleType that can reset the user passwords with this RoleType.
	 */
	@Property(policy= PojomaticPolicy.NONE)
    @Persistent
	@Unowned
	private RoleType passwordReseter;

	/**
	 * The enumerative of this role type, for ID purposes.
	 */
    @Persistent
	private E_RoleType roleTypeEnum;

    /**
     * Menu order.
     */
    @Persistent
	@Unowned
    private MenuOrder menuOrder;

	/**
	 * Fixes the collection created by Dozer.
	 */
//	public void fixRoleTypeFunctions() {
//		if (this.getRoleTypeFunctions() != null) {
//			for (RoleTypeFunction roleTypeFunction : this.getRoleTypeFunctions()) {
//				//sets the deletion flag to false
//				roleTypeFunction.setDeleted(Boolean.FALSE);
//				//sets the other side of the relationship if it is not present
//				if (roleTypeFunction.getRoleType() == null) {
//					roleTypeFunction.setRoleType(this);
//				}
//			}
//		}
//	}
/*
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityCreationListener#beforeCreate()
	 *//*
	@Override
	public void beforeCreate(EntityManager em) {
		this.fixRoleTypeFunctions();
	}
	
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityUpdateListener#beforeMap()
	 *//*
	@Override
	public void beforeMap() {
		if (this.getPasswordReseter() != null) {
			Long id = this.getPasswordReseter().getId();
			this.setPasswordReseter(new RoleType());
			this.getPasswordReseter().setId(id);
		}
		if (this.getMenuOrder() != null) {
			Long id = this.getMenuOrder().getId();
			this.setMenuOrder(new MenuOrder());
			this.getMenuOrder().setId(id);
		}
	}
	
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityUpdateListener#beforeUpdate()
	 *//*
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixRoleTypeFunctions();
	}*/
	
	/**
	 * RoleType-Function associations for this role type.
	 */
//	@Persistent(mappedBy = "roleType")
//	private List<RoleTypeFunction> roleTypeFunctions;

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
	 * @return the roleTypeFunctions
	 */
//	public List<RoleTypeFunction> getRoleTypeFunctions() {
//		return this.roleTypeFunctions;
//	}

	/**
	 * @param roleTypeFunctions the roleTypeFunctions to set
	 */
//	public void setRoleTypeFunctions(List<RoleTypeFunction> roleTypeFunctions) {
//		this.roleTypeFunctions = roleTypeFunctions;
//	}

	/**
	 * @return the passwordReseter
	 */
	public RoleType getPasswordReseter() {
		return this.passwordReseter;
	}

	/**
	 * @param passwordReseter the passwordReseter to set
	 */
	public void setPasswordReseter(RoleType passwordReseter) {
		this.passwordReseter = passwordReseter;
	}

	/**
	 * @return the roleTypeEnum
	 */
	public E_RoleType getRoleTypeEnum() {
		return this.roleTypeEnum;
	}

	/**
	 * @param roleTypeEnum the roleTypeEnum to set
	 */
	public void setRoleTypeEnum(E_RoleType roleTypeEnum) {
		this.roleTypeEnum = roleTypeEnum;
	}

	/**
	 * @return the menuOrder
	 */
	public MenuOrder getMenuOrder() {
		return this.menuOrder;
	}

	/**
	 * @param menuOrder the menuOrder to set
	 */
	public void setMenuOrder(MenuOrder menuOrder) {
		this.menuOrder = menuOrder;
	}

}
