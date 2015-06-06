package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.vo.user.RoleTypeVo;
import org.kairos.ibpnh.vo.user.UserVo;

import java.util.List;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_UserDao extends I_Dao<UserVo> {

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
    public UserVo getByUsername(JDOPersistenceManager pm, String username);

    /**
     * Gets all users that have the specified role type.
     *
     * @param pm
     *            the persistence manager
     * @param roleTypeVo
     *            the role type to search
     * @return list of users
     */
    public List<UserVo> findUsersByRoleTypeName(JDOPersistenceManager pm,
                                                RoleTypeVo roleTypeVo);

    /**
     * Checks that a user username is only used once.
     *
     * @param pm
     *            the persistence manager
     * @param username
     *            the username to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkUsernameUniqueness(JDOPersistenceManager pm, String username,
                                           String excludeId);

    /**
     * Orders the functions in the User Object.
     *
     * @param userVo
     */
    public void orderMenu(UserVo userVo);

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

    /**
     * Get the user with the specified enabling hash.
     *
     * @param pm
     *            the persistence manager
     * @param enablingHash
     *            the enabling hash to search for
     *
     * @return UserVo or null
     */
    public UserVo getUserByEnablingHash(JDOPersistenceManager pm, String enablingHash);

}
