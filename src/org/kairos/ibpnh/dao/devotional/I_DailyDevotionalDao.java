package org.kairos.ibpnh.dao.devotional;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.vo.devotional.DailyDevotionalVo;

import java.util.Date;
import java.util.List;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_DailyDevotionalDao extends I_Dao<DailyDevotionalVo> {

    /**
     * Attempts to search a dailyDevotional with dailyDevotionalname.
     *
     * @param pm
     *            the persistence manager
     * @param date
     *            the dailyDevotionalname to search for
     *
     * @return dailyDevotionalVo or null
     */
    public DailyDevotionalVo getByDate(JDOPersistenceManager pm, Date date);

    /**
     * Checks that a dailyDevotional dailyDevotionalname is only used once.
     *
     * @param pm
     *            the persistence manager
     * @param date
     *            the date to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkDateUniqueness(JDOPersistenceManager pm, Date date,
                                       String excludeId);

    /**
     * List am amount of devotionals starting on a specified date.
     *
     * @param pm
     * @param amount
     * @param date
     * @return
     */
    public List<DailyDevotionalVo> listLastDevotionals(JDOPersistenceManager pm,
                                       Long amount, Date date);
}
