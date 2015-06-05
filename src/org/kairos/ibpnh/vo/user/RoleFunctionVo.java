package org.kairos.ibpnh.vo.user;

import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;

/**
 * Value object for the role-function entity.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@AutoProperty
public class RoleFunctionVo extends AbstractVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -250667366149917296L;

	/**
	 * Role side.
	 */
	@Property(policy= PojomaticPolicy.NONE)
	private RoleVo role;

	/**
	 * Function side.
	 */
	@Property(policy= PojomaticPolicy.HASHCODE_EQUALS)
	private FunctionVo function;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled;

	/**
	 * Cause of the disqualification of the user for this function.
	 */
	private String disabledCause;

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

	/**
	 * @return the role
	 */
	public RoleVo getRole() {
		return this.role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(RoleVo role) {
		this.role = role;
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

	/**
	 * @return the function
	 */
	public FunctionVo getFunction() {
		return this.function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(FunctionVo function) {
		this.function = function;
	}
}
