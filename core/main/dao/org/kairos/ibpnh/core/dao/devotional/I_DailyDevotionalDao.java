package org.kairos.ibpnh.core.dao.devotional;

import org.kairos.ibpnh.core.dao.I_Dao;
import org.kairos.ibpnh.core.vo.devotional.DailyDevotionalVo;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Value object for bible study
 *
 * @author Axel Collard Bovy
 *
 * Created on 28/03/2016.
 */
public interface I_DailyDevotionalDao extends I_Dao<DailyDevotionalVo> {

    /**
     * Attempts to search a dailyDevotional with date.
     *
     * @param em
     * @param date
     *            the date to search for
     *
     * @return dailyDevotional or null
     */
    public DailyDevotionalVo getByDate(EntityManager em,Date date);

    /**
     * Checks that a dailyDevotional dailyDevotionalname is only used once.
     *
     * @param em
     * @param date
     *            the date to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkDateUniqueness(EntityManager em,Date date,
                                       Long excludeId);

    /**
     * List am amount of devotionals starting on a specified date.
     *
     * @param em
     * @param amount
     * @param date
     * @return
     */
    public List<DailyDevotionalVo> listLastDevotionals(EntityManager em,Long amount, Date date);
}
