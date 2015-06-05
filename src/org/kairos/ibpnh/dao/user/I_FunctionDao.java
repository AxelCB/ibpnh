package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.vo.user.FunctionVo;

/**
 * @author AxelCollardBovy ,created on 17/05/2015.
 */
public interface I_FunctionDao extends I_Dao<FunctionVo> {

    /**
     * Attempts to search a Function with Functionname.
     *
     * @param pm
     *            the persistence manager
     * @param actionName
     *            the Functionname to search for
     *
     * @return Function or null
     */
    public FunctionVo getByActionName(JDOPersistenceManager pm, String actionName);



    /**
     * Checks that a Function Functionname is only used once.
     *
     * @param pm
     *            the persistence manager
     * @param actionName
     *            the Functionname to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkActionnameUniqueness(JDOPersistenceManager pm, String actionName,
                                             String excludeId);

}
