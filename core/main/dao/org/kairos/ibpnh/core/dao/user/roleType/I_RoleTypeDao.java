package org.kairos.ibpnh.core.dao.user.roleType;

import javax.persistence.EntityManager;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.model.user.E_RoleType;
import org.kairos.ibpnh.core.vo.user.RoleTypeVo;

/**
 * Interface for the RoleType DAO.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_RoleTypeDao extends I_Dao<RoleTypeVo> {
	
	/**
	 * Gets the role type with the specified roleTypeEnum.
	 * 
	 * @param em the entity manager
	 * @param roleTypeEnum the roleTypeEnum to search for
	 * 
	 * @return a RoleType VO or null
	 */
	public RoleTypeVo getByRoleTypeEnum(EntityManager em, E_RoleType roleTypeEnum);
	
	/**
	 * Checks that a role type name is only used once.
	 * 
	 * @param em the entity manager
	 * @param name the name to check
	 * @param excludeId the id to exclude
	 * 
	 * @return true if the name is unique
	 */
	public Boolean checkNameUniqueness(EntityManager em, String name, Long excludeId);
}
