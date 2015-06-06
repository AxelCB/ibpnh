package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.model.user.RoleType;
import org.kairos.ibpnh.vo.user.RoleTypeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Dao for the user Entities
 * 
 * @author AxelCollardBovy ,created on 04/03/2015.
 */
public class RoleTypeDaoJDOImpl extends AbstractDao<RoleType,RoleTypeVo> implements I_RoleTypeDao {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(RoleTypeDaoJDOImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getClazz()
     */
    @Override
    protected Class<RoleType> getClazz() {
        return RoleType.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getVoClazz()
     */
    @Override
    public Class<RoleTypeVo> getVoClazz() {
        return RoleTypeVo.class;
    }

//    @Override
//    public RoleTypeVo getByUsername(JDOPersistenceManager pm, String username) {
//        this.logger.debug("attempting login for username: {}", username);
//
//        User user = null;
//
//        Query query = pm.newQuery(this.getClazz());
//        query.setFilter("deleted == FALSE");
//        query.setFilter("username == usernameParam");
//        query.declareParameters("String usernameParam");
//        query.setUnique(Boolean.TRUE);
//        try{
//            user = (User) query.execute(username);
//            query.closeAll();
//
//            return this.map(user);
//        }catch(Exception e){
//            this.logger.debug("could not get user by username");
//        }
//        return null;
//    }

    //@Override
    //public List<UserVo> findUsersByRoleTypeName(JDOPersistenceManager pm, RoleTypeVo roleTypeVo) {
    //  return null;
    //}

    @Override
    public RoleTypeVo getByRoleTypeEnum(PersistenceManager pm, E_RoleType roleTypeEnum) {
        this.logger.debug("attempting to get roleType by enum: {}", roleTypeEnum.toString());

        RoleType roleType = null;

        Query query = pm.newQuery(this.getClazz());
        query.setFilter("deleted == false && roleTypeEnum == roleTypeEnumParam");
        query.declareParameters("E_RoleType roleTypeEnumParam");
        query.setUnique(Boolean.TRUE);
        try{
            roleType = (RoleType) query.execute(roleTypeEnum);
            query.closeAll();

            return this.map(roleType);
        }catch(Exception e){
            this.logger.debug("could not get roleType by enum");
        }
        return null;
    }

    @Override
    public Boolean checkRoleTypeNameUniqueness(JDOPersistenceManager pm, String username, Long excludeId) {
        return null;
    }

    @Override
    public void orderMenu(RoleTypeVo roleTypeVo) {
        
    }
}
