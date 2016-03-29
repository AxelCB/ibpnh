package org.ibpnh.core.vo.user;

import java.io.Serializable;
import java.util.List;

import org.pojomatic.annotations.AutoProperty;
import org.ibpnh.core.model.user.E_RoleType;
import org.ibpnh.core.vo.AbstractVo;

/**
 * Value Object for the Role Type entity.
 * 
 * @author Axel Collard Bovy
 *
 */
@AutoProperty
public class RoleTypeVo extends AbstractVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1107215769174052686L;

	/**
	 * The name of the role.
	 */
	private String name;
	
	/**
	 * A description for the role.
	 */
	private String description;
	
	/**
	 * THe Role Type Functions
	 */
	private List<RoleTypeFunctionVo> roleTypeFunctions;
	
	/**
	 * The RoleTypeId that can reset the user passwords with this RoleType.
	 */
	private Long passwordReseterId;
	
	/**
	 * The enumerative of this role type, for ID purposes.
	 */
	private E_RoleType roleTypeEnum;

	/**
	 * Gets a role type function by the function ID.
	 * 
	 * @param functionId to search
	 * 
	 * @return the role function or null
	 */
	public RoleTypeFunctionVo getRoleTypeFunctionByFunctionId(Long functionId) {
		RoleTypeFunctionVo roleTypeFunctionVo = null;
		
		for (RoleTypeFunctionVo roleTypeFunctionVoAux : this.getRoleTypeFunctions()) {
			if (roleTypeFunctionVoAux.getFunctionId().equals(functionId)) {
				roleTypeFunctionVo = roleTypeFunctionVoAux;
				break;
			}
		}
		
		return roleTypeFunctionVo;
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
	public List<RoleTypeFunctionVo> getRoleTypeFunctions() {
		return this.roleTypeFunctions;
	}

	/**
	 * @param roleTypeFunctions the roleTypeFunctions to set
	 */
	public void setRoleTypeFunctions(List<RoleTypeFunctionVo> roleTypeFunctions) {
		this.roleTypeFunctions = roleTypeFunctions;
	}

	/**
	 * @return the passwordReseterId
	 */
	public Long getPasswordReseterId() {
		return this.passwordReseterId;
	}

	/**
	 * @param passwordReseterId the passwordReseterId to set
	 */
	public void setPasswordReseterId(Long passwordReseterId) {
		this.passwordReseterId = passwordReseterId;
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
}
