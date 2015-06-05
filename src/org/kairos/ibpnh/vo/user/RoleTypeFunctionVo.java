package org.kairos.ibpnh.vo.user;

import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import java.io.Serializable;

/**
 * Value object for the roletype-function entity.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@AutoProperty
public class RoleTypeFunctionVo extends AbstractVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5730277911141020191L;

	/**
	 * Enabled flag.
	 */
	private Boolean enabled;

	/**
	 * Disabled cause.
	 */
	private String disabledCause;

	/**
	 * RoleType side.
	 */
	@Property(policy= PojomaticPolicy.NONE)
	private RoleTypeVo roleType;

	/**
	 * Function side.
	 */
	@Property(policy= PojomaticPolicy.HASHCODE_EQUALS)
	private FunctionVo function;

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

	public RoleTypeVo getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleTypeVo roleType) {
		this.roleType = roleType;
	}

	public FunctionVo getFunction() {
		return function;
	}

	public void setFunction(FunctionVo function) {
		this.function = function;
	}
}
