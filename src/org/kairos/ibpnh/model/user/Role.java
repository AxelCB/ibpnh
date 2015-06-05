package org.kairos.ibpnh.model.user;

import com.google.appengine.datanucleus.annotations.Unowned;
import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import javax.jdo.annotations.*;
import java.io.Serializable;
import java.util.List;


/**
 * An instantiation of a Role Type assigned to a user.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@PersistenceCapable
@AutoProperty(policy= DefaultPojomaticPolicy.TO_STRING)
public class Role implements Serializable, I_Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8836267582588649317L;

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
	 * The parent Role Type of this Role.
	 */
    @Persistent
	@Unowned
	private RoleType roleType;
	
	/**
	 * Role-Function associations for this Role.
	 */
    @Persistent(mappedBy="role")
	private List<RoleFunction> roleFunctions;
	
	/**
	 * User that has this role.
	 */
    @Persistent
	@Property(policy= PojomaticPolicy.NONE)
	private User user;
	
	/**
	 * Fixes the collection created by Dozer.
	 */
	public void fixRoleFunctions() {
		if (this.getRoleFunctions() != null) {
			for (RoleFunction roleFunction : this.getRoleFunctions()) {
				//sets the deletion flag to false
				roleFunction.setDeleted(Boolean.FALSE);
				//sets the other side of the relationship if it is not present
				if (roleFunction.getRole() == null) {
					roleFunction.setRole(this);
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityCreationListener#beforeCreate()
	 *//*
	@Override
	public void beforeCreate(EntityManager em) {
		this.fixRoleFunctions();
	}
	
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityUpdateListener#beforeUpdate()
	 *//*
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixRoleFunctions();
	}
	
	*//*
	 * (non-Javadoc)
	 * @see org.universe.core.dao.I_EntityUpdateListener#beforeMap()
	 *//*
	@Override
	public void beforeMap() {
		//does nothing	
	}*/
	
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
	 * @return the roleFunctions
	 */
	public List<RoleFunction> getRoleFunctions() {
		return this.roleFunctions;
	}

	/**
	 * @param roleFunctions the roleFunctions to set
	 */
	public void setRoleFunctions(List<RoleFunction> roleFunctions) {
		this.roleFunctions = roleFunctions;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
