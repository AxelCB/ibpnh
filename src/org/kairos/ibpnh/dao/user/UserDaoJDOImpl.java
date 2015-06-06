package org.kairos.ibpnh.dao.user;

import com.google.appengine.api.datastore.KeyFactory;
import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.model.user.RoleType;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.vo.user.RoleTypeVo;
import org.kairos.ibpnh.vo.user.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jdo.Query;
import java.util.List;

/**
 * Dao for the user Entities
 * 
 * @author AxelCollardBovy ,created on 04/03/2015.
 */
public class UserDaoJDOImpl extends AbstractDao<User,UserVo> implements I_UserDao {

    @Autowired
    private I_RoleTypeDao roleTypeDao;

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(UserDaoJDOImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getClazz()
     */
    @Override
    protected Class<User> getClazz() {
        return User.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getVoClazz()
     */
    @Override
    public Class<UserVo> getVoClazz() {
        return UserVo.class;
    }

    @Override
    public UserVo getByUsername(JDOPersistenceManager pm, String username) {
        this.logger.debug("attempting login for username: {}", username);

        User user = null;

        Query query = pm.newQuery(this.getClazz());


        query.setFilter("deleted == false && username == usernameParam");
        query.declareParameters("String usernameParam");
        query.setUnique(Boolean.TRUE);
        try{
            user = (User) query.execute(username);
            query.closeAll();

            return this.map(user);
        }catch(Exception e){
            this.logger.debug("could not get user by username");
        }
        return null;
    }

    @Override
    public List<UserVo> findUsersByRoleTypeName(JDOPersistenceManager pm, RoleTypeVo roleTypeVo) {
        return null;
    }

    @Override
    public Boolean checkUsernameUniqueness(JDOPersistenceManager pm, String username, String excludeId) {
        UserVo userVo = this.getByUsername(pm,username);
        if(userVo==null || userVo.getId().equals(excludeId)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public void orderMenu(UserVo userVo) {

    }

    @Override
    public Integer countByRoleType(JDOPersistenceManager pm, Boolean countDisabled, E_RoleType... roleTypes) {
        return 0;
    }

    @Override
    public UserVo getUserByEnablingHash(JDOPersistenceManager pm, String enablingHash) {
        return null;
    }

    @Override
    public UserVo persist(JDOPersistenceManager pm, UserVo entityVo) {
        this.logger.debug("persisting user");

        User entity = null;

        if (entityVo.getId() == null) {
            entity = this.map(entityVo);

            RoleType roleType = null;

            Query query = pm.newQuery(RoleType.class);
            query.setFilter("deleted == false && roleTypeEnum == roleTypeEnumParam");
            query.declareParameters("E_RoleType roleTypeEnumParam");
            query.setUnique(Boolean.TRUE);

            roleType = (RoleType) query.execute(entity.getRole().getRoleType().getRoleTypeEnum());
            entity.setDeleted(Boolean.FALSE);
            entity.getRole().setRoleType(roleType);
            entity = (User) pm.makePersistent(entity);
        } else {
            entity = this.getEntityById(pm, entityVo.getId());
            this.map(entityVo, entity);
        }
        return this.map(entity);
    }
}
