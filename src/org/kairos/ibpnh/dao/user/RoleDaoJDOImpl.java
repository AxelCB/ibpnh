package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.model.user.Role;
import org.kairos.ibpnh.vo.user.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dao for the user Entities
 * 
 * @author AxelCollardBovy ,created on 04/03/2015.
 */
public class RoleDaoJDOImpl extends AbstractDao<Role,RoleVo> implements I_RoleDao {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(RoleDaoJDOImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getClazz()
     */
    @Override
    protected Class<Role> getClazz() {
        return Role.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getVoClazz()
     */
    @Override
    public Class<RoleVo> getVoClazz() {
        return RoleVo.class;
    }


    @Override
    public void orderMenu(RoleVo roleVo) {

    }

    @Override
    public Integer countByRoleType(JDOPersistenceManager pm, Boolean countDisabled, E_RoleType... roleTypes) {
        return null;
    }
}
