package org.ibpnh.core.model.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.DefaultPojomaticPolicy;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;
import org.ibpnh.core.dao.I_EntityCreationListener;
import org.ibpnh.core.dao.I_EntityUpdateListener;
import org.ibpnh.core.model.I_Model;

/**
 * Represents an archetype role with its permissions and functions.
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class RoleType implements Serializable, I_Model,
		I_EntityCreationListener, I_EntityUpdateListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2014065357918906428L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roletype_seq")
	@SequenceGenerator(

	name = "roletype_seq", sequenceName = "roletype_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * The name of the role.
	 */
	private String name;

	/**
	 * A description for the role.
	 */
	private String description;

	/**
	 * The RoleType that can reset the user passwords with this RoleType.
	 */
	@Property(policy = PojomaticPolicy.NONE)
	@ManyToOne
	private RoleType passwordReseter;

	/**
	 * The enumerative of this role type, for ID purposes.
	 */
	@Enumerated(EnumType.ORDINAL)
	private E_RoleType roleTypeEnum;

	/**
	 * RoleType-Function associations for this role type.
	 */
	@OneToMany(mappedBy = "roleType", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RoleTypeFunction> roleTypeFunctions;

	/**
	 * Fixes the collection created by Dozer.
	 */
	public void fixRoleTypeFunctions() {
		if (this.getRoleTypeFunctions() != null) {
			for (RoleTypeFunction roleTypeFunction : this
					.getRoleTypeFunctions()) {
				// sets the deletion flag to false
				roleTypeFunction.setDeleted(Boolean.FALSE);
				// sets the other side of the relationship if it is not present
				if (roleTypeFunction.getRoleType() == null) {
					roleTypeFunction.setRoleType(this);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityCreationListener#beforeCreate()
	 */
	@Override
	public void beforeCreate(EntityManager em) {
		this.fixRoleTypeFunctions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityUpdateListener#beforeMap()
	 */
	@Override
	public void beforeMap() {
		if (this.getPasswordReseter() != null) {
			Long id = this.getPasswordReseter().getId();
			this.setPasswordReseter(new RoleType());
			this.getPasswordReseter().setId(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityUpdateListener#beforeUpdate()
	 */
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixRoleTypeFunctions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@Override
	public void setId(Long id) {
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
	 * @param deleted
	 *            the deleted to set
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
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the roleTypeFunctions
	 */
	public List<RoleTypeFunction> getRoleTypeFunctions() {
		return this.roleTypeFunctions;
	}

	/**
	 * @param roleTypeFunctions
	 *            the roleTypeFunctions to set
	 */
	public void setRoleTypeFunctions(List<RoleTypeFunction> roleTypeFunctions) {
		this.roleTypeFunctions = roleTypeFunctions;
	}

	/**
	 * @return the passwordReseter
	 */
	public RoleType getPasswordReseter() {
		return this.passwordReseter;
	}

	/**
	 * @param passwordReseter
	 *            the passwordReseter to set
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
	 * @param roleTypeEnum
	 *            the roleTypeEnum to set
	 */
	public void setRoleTypeEnum(E_RoleType roleTypeEnum) {
		this.roleTypeEnum = roleTypeEnum;
	}

}