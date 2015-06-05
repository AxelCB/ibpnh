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

/**
 * Many-to-many union between Role Types and Functions.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@PersistenceCapable
@AutoProperty(policy= DefaultPojomaticPolicy.TO_STRING)
public class RoleTypeFunction implements Serializable, I_Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 3186492869741821008L;

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
	 * Enabled flag.
	 */
    @Persistent
	private Boolean enabled;

	/**
	 * Disabled cause.
	 */
    @Persistent
	private String disabledCause;

	/**
	 * RoleType side.
	 */
    @Persistent
	@Property(policy= PojomaticPolicy.NONE)
	@Unowned
	private RoleType roleType;
	
	/**
	 * Function side.
     */
    @Persistent
	@Property(policy= PojomaticPolicy.HASHCODE_EQUALS)
	@Unowned
	private Function function;
	
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
	 * @return the function
	 */
	public Function getFunction() {
		return this.function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the disabledCause
	 */
	public String getDisabledCause() {
		return this.disabledCause;
	}

	/**
	 * @param disabledCause the disabledCause to set
	 */
	public void setDisabledCause(String disabledCause) {
		this.disabledCause = disabledCause;
	}

}
