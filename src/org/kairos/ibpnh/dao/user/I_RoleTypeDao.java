package org.kairos.ibpnh.dao.user;

import com.googlecode.objectify.Objectify;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.user.E_RoleType;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_RoleTypeDao extends I_Dao<RoleType> {

    /**
     * Attempts to search a user with username.
     *
     * @param pm
     *            the persistence manager
     * @param username
     *            the username to search for
     *
     * @return user or null
     */
    //public RoleType getByUsername(Objectify ofy, String username);

    /**
     * Gets the role type with the specified roleTypeEnum.
     *
     * @param ofy the objectify manager
     * @param roleTypeEnum the roleTypeEnum to search for
     *
     * @return a RoleType  or null
     */
    public RoleType getByRoleTypeEnum(Objectify ofy, E_RoleType roleTypeEnum);

    /**
     * Checks that a user username is only used once.
     *
     * @param ofy
     *            the objectify manager
     * @param roleTypeName
     *            the username to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkRoleTypeNameUniqueness(Objectify ofy, String roleTypeName,
                                               Long excludeId);

    /**
     * Orders the functions in the RoleType Object.
     *
     * @param roleType
     */
    public void orderMenu(RoleType roleType);
}
