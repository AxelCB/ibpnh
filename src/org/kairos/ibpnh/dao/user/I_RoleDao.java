package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.vo.user.RoleVo;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_RoleDao extends I_Dao<RoleVo> {

    /**
     * Orders the functions in the Role Object.
     *
     * @param roleVo
     */
    public void orderMenu(RoleVo roleVo);

    /**
     * Counts the users registered with certain role types.
     *
     * @param pm
     *            the persistence manager
     * @param countDisabled
     *            if it should include disabled users
     * @param roleTypes
     *            the role types to count
     *
     * @return integer
     */
    public Integer countByRoleType(JDOPersistenceManager pm, Boolean countDisabled,
                                   E_RoleType... roleTypes);
}
