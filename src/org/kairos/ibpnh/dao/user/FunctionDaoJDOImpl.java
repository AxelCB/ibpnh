package org.kairos.ibpnh.dao.user;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.user.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.Query;

/**
 * Dao for the Function Entities
 * 
 * @author AxelCollardBovy ,created on 04/03/2015.
 */
public class FunctionDaoJDOImpl extends AbstractDao<Function, org.kairos.ibpnh.vo.user.FunctionVo> implements I_FunctionDao {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(FunctionDaoJDOImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getClazz()
     */
    @Override
    protected Class<Function> getClazz() {
        return Function.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getVoClazz()
     */
    @Override
    public Class<org.kairos.ibpnh.vo.user.FunctionVo> getVoClazz() {
        return org.kairos.ibpnh.vo.user.FunctionVo.class;
    }

    @Override
    public org.kairos.ibpnh.vo.user.FunctionVo getByActionName(JDOPersistenceManager pm, String actionName) {
        this.logger.debug("attempting to search for function with actionName: {}", actionName);

        Function Function = null;

        Query query = pm.newQuery(this.getClazz());

        query.setFilter("deleted == FALSE");
        query.setFilter("actionName == actionNameParam");
        query.declareParameters("String actionNameParam");
        query.setUnique(Boolean.TRUE);
        try{
            Function = (org.kairos.ibpnh.model.user.Function) query.execute(actionName);
            query.closeAll();

            return this.map(Function);
        }catch(Exception e){
            this.logger.debug("could not get Function by actionName");
        }
        return null;
    }

    @Override
    public Boolean checkActionnameUniqueness(JDOPersistenceManager pm, String actionName, String excludeId) {
        return null;
    }
}
