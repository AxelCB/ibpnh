package org.ibpnh.core.model.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * User of the system. Has a Role with a set of the permited functions.
 * 
 * @author Axel Collard Bovy
 * 
 */
@Entity
@AutoProperty(policy = DefaultPojomaticPolicy.TO_STRING)
public class User implements Serializable, I_Model, I_EntityUpdateListener,
		I_EntityCreationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120163561077722195L;

	/**
	 * Entity ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator( name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	@Property(policy = PojomaticPolicy.EQUALS)
	private Long id;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted = false;

	/**
	 * User's role.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Role role;

	/**
	 * User's username.
	 */
	private String username;

	/**
	 * User's hashed password.
	 */
	private String password;

	/**
	 * Total login Attempts
	 */
	private Integer loginAttempts;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled;

	/**
	 * First login flag.
	 */
	private Boolean firstLogin;

	/**
	 * Disabled cause.
	 */
	private String disabledCause;
	
	/**
	 * Just for querying purposes.
	 */
	@Property(policy=PojomaticPolicy.NONE)
	@OneToOne(mappedBy="user")
	private UserDetails userDetails;
	
	/**
	 * Hash cost for the BCrypt algorithm.
	 */
	public Long hashCost;
	
	/**
	 * Enabling hash.
	 */
	private String enablingHash;

	/**
	 * Fixes the role object.
	 */
	public void fixRole() {
		if (this.getRole() != null) {
			if (this.getRole().getDeleted() == null) {
				this.getRole().setDeleted(Boolean.FALSE);
			}
			this.getRole().fixRoleFunctions();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityCreationListener#beforeCreate()
	 */
	@Override
	public void beforeCreate(EntityManager em) {
		this.fixRole();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityUpdateListener#beforeUpdate()
	 */
	@Override
	public void beforeUpdate(EntityManager em) {
		this.fixRole();
		if (this.getRole() != null && this.getRole().getRoleType() != null) {
			if (this.getRole().getRoleType().getPasswordReseter() != null
					&& this.getRole().getRoleType().getPasswordReseter()
							.getId() == null) {
				em.detach(this.getRole().getRoleType().getPasswordReseter());
				this.getRole().getRoleType().setPasswordReseter(null);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_EntityUpdateListener#beforeMap()
	 */
	@Override
	public void beforeMap() {
		// does nothing
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
	 * @return the role
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginAttempts
	 */
	public Integer getLoginAttempts() {
		return this.loginAttempts;
	}

	/**
	 * @param loginAttempts
	 *            the loginAttempts to set
	 */
	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the firstLogin
	 */
	public Boolean getFirstLogin() {
		return this.firstLogin;
	}

	/**
	 * @param firstLogin
	 *            the firstLogin to set
	 */
	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * @return the disabledCause
	 */
	public String getDisabledCause() {
		return this.disabledCause;
	}

	/**
	 * @param disabledCause
	 *            the disabledCause to set
	 */
	public void setDisabledCause(String disabledCause) {
		this.disabledCause = disabledCause;
	}

	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return this.userDetails;
	}

	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * @return the hashCost
	 */
	public Long getHashCost() {
		return this.hashCost;
	}

	/**
	 * @param hashCost the hashCost to set
	 */
	public void setHashCost(Long hashCost) {
		this.hashCost = hashCost;
	}

	/**
	 * @return the enablingHash
	 */
	public String getEnablingHash() {
		return this.enablingHash;
	}

	/**
	 * @param enablingHash the enablingHash to set
	 */
	public void setEnablingHash(String enablingHash) {
		this.enablingHash = enablingHash;
	}

}
