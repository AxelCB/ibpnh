package org.kairos.ibpnh.dao.user;

import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.vo.user.UserVo;

import java.util.List;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_UserDao extends I_Dao<UserVo> {

    /**
     * Attempts to search a user with username.
     *
     * @param username
     *            the username to search for
     *
     * @return user or null
     */
    public UserVo getByUsername(String username);

    /**
     * Gets all users that have the specified role type.
     *
     * @param roleType
     *            the role type to search
     * @return list of users
     */
    public List<UserVo> findUsersByRoleTypeName(E_RoleType roleType);

    /**
     * Checks that a user username is only used once.
     *
     * @param username
     *            the username to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkUsernameUniqueness(String username,Long excludeId);

    /**
     * Counts the users registered with certain role types.
     *
     * @param roleTypes
     *            the role types to count
     *
     * @return integer
     */
    public Integer countByE_RoleType(E_RoleType... roleTypes);

    /**
     * Get the user with the specified enabling hash.
     *
     * @param enablingHash
     *            the enabling hash to search for
     *
     * @return User or null
     */
    public UserVo getUserByEnablingHash(String enablingHash);

}
