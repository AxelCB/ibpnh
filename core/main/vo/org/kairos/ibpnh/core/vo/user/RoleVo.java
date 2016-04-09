package org.kairos.ibpnh.core.vo.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.pojomatic.annotations.AutoProperty;
import org.kairos.ibpnh.core.vo.AbstractVo;

/**
 * Value Object for the Role entity.
 * 
 * @author Axel Collard Bovy
 *
 */
@AutoProperty
public class RoleVo extends AbstractVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2353788592034259074L;

	/**
	 * Role Type VO of this role.
	 */
	private RoleTypeVo roleType;
	
	/**
	 * Role functions
	 */
	private List<RoleFunctionVo> roleFunctions;
	
	/**
	 * Gets a role function by the function URI.
	 * 
	 * @param uri the URI to search
	 * 
	 * @return the role function or null
	 */
	public RoleFunctionVo getRoleFunctionByUri(String uri) {
		RoleFunctionVo roleFunctionVo = null;
		
		for (RoleFunctionVo roleFunctionVoAux : this.getRoleFunctions()) {
			if (roleFunctionVoAux.getUri().equals(uri)) {
				roleFunctionVo = roleFunctionVoAux;
				break;
			}
		}
		
		return roleFunctionVo;
	}
	
	/**
	 * Gets a role function by the function ID.
	 * 
	 * @param functionId to search
	 * 
	 * @return the role function or null
	 */
	public RoleFunctionVo getRoleFunctionByFunctionId(Long functionId) {
		RoleFunctionVo roleFunctionVo = null;
		
		if (this.getRoleFunctions() != null) {
			for (RoleFunctionVo roleFunctionVoAux : this.getRoleFunctions()) {
				if (roleFunctionVoAux.getId().equals(functionId)) {
					roleFunctionVo = roleFunctionVoAux;
					break;
				}
			}
		}
		return roleFunctionVo;
	}
	
	/**
	 * Copies or updates all functions from the Role Type to this Role.
	 * 
	 * @param roleTypeVo the role type to copy from. if null, uses it's current role type
	 */
	public void copyOrUpdateFromRoleType(RoleTypeVo roleTypeVo) {
 		if (roleTypeVo == null) {
			roleTypeVo = this.getRoleType();
		}
		
		if (this.getRoleFunctions() == null) {
			this.setRoleFunctions(new ArrayList<RoleFunctionVo>());
		}
		
		List<RoleFunctionVo> added = new ArrayList<>();
		List<RoleFunctionVo> deleted = new ArrayList<>();
		
		//adds the new functions or updates the actual functions
		for (RoleTypeFunctionVo roleTypeFunctionVo : roleTypeVo.getRoleTypeFunctions()) {
			RoleFunctionVo roleFunctionVo = null;
			
			roleFunctionVo = this.getRoleFunctionByFunctionId(roleTypeFunctionVo.getFunctionId());
			
			if (roleFunctionVo == null) {
				//we add it
				roleFunctionVo = new RoleFunctionVo();
				roleFunctionVo.setId(roleTypeFunctionVo.getFunctionId());
				
				added.add(roleFunctionVo);
			}
			
			//we update the function
			roleFunctionVo.setEnabled(roleTypeFunctionVo.getEnabled());
			roleFunctionVo.setDisabledCause(roleTypeFunctionVo.getDisabledCause());
		}
		//deletes the removed functions
		for (RoleFunctionVo roleFunctionVo : this.getRoleFunctions()) {
			if (roleTypeVo.getRoleTypeFunctionByFunctionId(roleFunctionVo.getId()) == null) {
				deleted.add(roleFunctionVo);
			}
		}
		
		this.getRoleFunctions().removeAll(deleted);
		this.getRoleFunctions().addAll(added);
	}
	
	/**
	 * Convenience method to update this role using the role type functions.
	 */
	public void copyOrUpdateFromRoleType() {
		this.copyOrUpdateFromRoleType(null);
	}

	/**
	 * @return the roleType
	 */
	public RoleTypeVo getRoleType() {
		return this.roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(RoleTypeVo roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the roleFunctions
	 */
	public List<RoleFunctionVo> getRoleFunctions() {
		return this.roleFunctions;
	}

	/**
	 * @param roleFunctions the roleFunctions to set
	 */
	public void setRoleFunctions(List<RoleFunctionVo> roleFunctions) {
		this.roleFunctions = roleFunctions;
	}
}
