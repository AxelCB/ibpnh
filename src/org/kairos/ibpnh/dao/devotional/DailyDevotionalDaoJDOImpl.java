package org.kairos.ibpnh.dao.devotional;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.devotional.DailyDevotional;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.Query;
import java.util.Date;
import java.util.List;

/**
 * Dao for the Daily Devotional Entities
 * 
 * @author AxelCollardBovy ,created on 04/03/2015.
 */
public class DailyDevotionalDaoJDOImpl extends AbstractDao<DailyDevotional,DailyDevotionalVo> implements I_DailyDevotionalDao {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(DailyDevotionalDaoJDOImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getClazz()
     */
    @Override
    protected Class<DailyDevotional> getClazz() {
        return DailyDevotional.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.universe.core.dao.AbstractDao#getVoClazz()
     */
    @Override
    public Class<DailyDevotionalVo> getVoClazz() {
        return DailyDevotionalVo.class;
    }

    @Override
    public DailyDevotionalVo getByDate(JDOPersistenceManager pm, Date date) {
        this.logger.debug("getting daily devotional by date: {}", date);

        DailyDevotional dailyDevotional = null;

        Query query = pm.newQuery(this.getClazz());


        query.setFilter("deleted == false && date == dateParam");
        query.declareParameters("java.util.Date dateParam");
        query.setUnique(Boolean.TRUE);
        try{
            dailyDevotional = (DailyDevotional) query.execute(date);
            query.closeAll();

            return this.map(dailyDevotional);
        }catch(Exception e){
            this.logger.debug("could not get dailyDevotional by date");
        }
        return null;
    }


    @Override
    public Boolean checkDateUniqueness(JDOPersistenceManager pm, Date date, String excludeId) {
        DailyDevotionalVo dailyDevotionalVo = this.getByDate(pm, date);
        if(dailyDevotionalVo==null || dailyDevotionalVo.getId().equals(excludeId)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public List<DailyDevotionalVo> listLastDevotionals(JDOPersistenceManager pm, Long amount, Date date) {
        this.logger.debug("getting daily devotional by date: {}", date);

        List<DailyDevotional> dailyDevotionals;

        Query query = pm.newQuery(this.getClazz());

        query.setFilter("deleted == false && date <= dateParam");
        query.declareParameters("java.util.Date dateParam");
        query.setRange(0,amount);
        try{
            dailyDevotionals = (List<DailyDevotional>) query.execute(date);
            query.closeAll();

            return this.map(dailyDevotionals);
        }catch(Exception e){
            this.logger.debug("could not list last daily devotionals for date: "+date);
        }
        return null;
    }

//    @Override
//    public DailyDevotionalVo persist(JDOPersistenceManager pm, DailyDevotionalVo entityVo) {
//        this.logger.debug("persisting user");
//
//        DailyDevotional entity = null;
//
//        if (entityVo.getId() == null) {
//            entity = this.map(entityVo);
//
//            RoleType roleType = null;
//
//            Query query = pm.newQuery(RoleType.class);
//            query.setFilter("deleted == false && roleTypeEnum == roleTypeEnumParam");
//            query.declareParameters("E_RoleType roleTypeEnumParam");
//            query.setUnique(Boolean.TRUE);
//
//            roleType = (RoleType) query.execute(entity.getRole().getRoleType().getRoleTypeEnum());
//            entity.setDeleted(Boolean.FALSE);
//            entity.getRole().setRoleType(roleType);
//            entity = (DailyDevotional) pm.makePersistent(entity);
//        } else {
//            entity = this.getEntityById(pm, entityVo.getId());
//            this.map(entityVo, entity);
//        }
//        return this.map(entity);
//    }
}
