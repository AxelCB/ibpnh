package org.ibpnh.core.model.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.ibpnh.core.model.I_Model;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.ibpnh.core.dao.I_EntityCreationListener;
import org.ibpnh.core.dao.I_EntityUpdateListener;

/**
 * An instantiation of a Role Type assigned to a user.
 * 
 * @author Axel Collard Bovy
 *
 */
@Entity
@AutoProperty(policy=DefaultPojomaticPolicy.TO_STRING)
public class Role implements Serializable, I_Model, I_EntityCreationListener, I_EntityUpdateListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8836267582588649317L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_seq")
	@SequenceGenerator(
			
			name="role_seq",
			sequenceName="role_seq",
			allocationSize=1)
	@Property(policy=PojomaticPolicy.EQUALS)
	private Long id;
	
	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;
	
	/**
	 * The parent Role Type of this Role.
	 */
	@ManyToOne(cascade=CascadeType.PERSIST)
	private RoleType roleType;
	
	/**
	 * Role-Function associations for this Role.
	 */
	@OneToMany(mappedBy="role", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<RoleFunction> roleFunctions;
	
	/**
	 * User that has this role.
	 */
	@OneToOne(mappedBy="role")
	@Property(policy=PojomaticPolicy.NONE)
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
	 * @see I_EntityCreationListener#beforeCreate()
	 */
	@Override
	public void beforeCreate(EntityManager em) {
		this.fixRoleFunctions();
	}
	
	/*
	 * (non-Javadoc)
	 * @see I_EntityUpdateListener#beforeUpdate()
	 */
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixRoleFunctions();
	}
	
	/*
	 * (non-Javadoc)
	 * @see I_EntityUpdateListener#beforeMap()
	 */
	@Override
	public void beforeMap() {
		//does nothing	
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
