package org.kairos.ibpnh.vo.user;

import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.vo.AbstractVo;
import org.kairos.ibpnh.vo.configuration.menuOrder.MenuOrderVo;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Value Object for the Role Type entity.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
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
//	private List<RoleTypeFunctionVo> roleTypeFunctions = new ArrayList<RoleTypeFunctionVo>();
	
	/**
	 * The RoleType that can reset the user passwords with this RoleType.
	 */
	private RoleTypeVo passwordReseter;
	
	/**
	 * The enumerative of this role type, for ID purposes.
	 */
	private E_RoleType roleTypeEnum;
	
	/**
	 * The Menu Order.
	 */
	private MenuOrderVo menuOrder;
	
	/**
	 * Gets a role type function by the function ID.
	 *
	 * @param functionId to search
	 *
	 * @return the role function or null
	 */
//	public RoleTypeFunctionVo getRoleTypeFunctionByFunctionId(String functionId) {
//		RoleTypeFunctionVo roleTypeFunctionVo = null;
//
//		for (RoleTypeFunctionVo roleTypeFunctionVoAux : this.getRoleTypeFunctions()) {
//			if (roleTypeFunctionVoAux.getFunction().getId().equals(functionId)) {
//				roleTypeFunctionVo = roleTypeFunctionVoAux;
//				break;
//			}
//		}
//
//		return roleTypeFunctionVo;
//	}

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
//	public List<RoleTypeFunctionVo> getRoleTypeFunctions() {
//		return this.roleTypeFunctions;
//	}

	/**
	 * @param roleTypeFunctions the roleTypeFunctions to set
	 */
//	public void setRoleTypeFunctions(List<RoleTypeFunctionVo> roleTypeFunctions) {
//		this.roleTypeFunctions = roleTypeFunctions;
//	}

	public RoleTypeVo getPasswordReseter() {
		return passwordReseter;
	}

	public void setPasswordReseter(RoleTypeVo passwordReseter) {
		this.passwordReseter = passwordReseter;
	}

	public MenuOrderVo getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(MenuOrderVo menuOrder) {
		this.menuOrder = menuOrder;
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
