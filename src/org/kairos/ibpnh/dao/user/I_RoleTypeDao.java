package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.vo.user.RoleTypeVo;

import javax.jdo.PersistenceManager;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_RoleTypeDao extends I_Dao<RoleTypeVo> {

    /**
     * Attempts to search a user with username.
     *
     * @param pm
     *            the persistence manager
     * @param username
     *            the username to search for
     *
     * @return userVo or null
     */
    //public RoleTypeVo getByUsername(JDOPersistenceManager pm, String username);

    /**
     * Gets the role type with the specified roleTypeEnum.
     *
     * @param em the entity manager
     * @param roleTypeEnum the roleTypeEnum to search for
     *
     * @return a RoleType VO or null
     */
    public RoleTypeVo getByRoleTypeEnum(PersistenceManager pm, E_RoleType roleTypeEnum);

    /**
     * Checks that a user username is only used once.
     *
     * @param pm
     *            the persistence manager
     * @param roleTypeName
     *            the username to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkRoleTypeNameUniqueness(JDOPersistenceManager pm, String roleTypeName,
                                               Long excludeId);

    /**
     * Orders the functions in the RoleType Object.
     *
     * @param roleTypeVo
     */
    public void orderMenu(RoleTypeVo roleTypeVo);
}
