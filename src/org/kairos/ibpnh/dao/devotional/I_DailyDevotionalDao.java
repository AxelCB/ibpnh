package org.kairos.ibpnh.dao.devotional;

import org.kairos.ibpnh.dao.I_Dao;
import org.kairos.ibpnh.model.devotional.DailyDevotional;

import java.util.Date;
import java.util.List;

/**
 * @author AxelCollardBovy ,created on 03/03/2015.
 */
public interface I_DailyDevotionalDao extends I_Dao<DailyDevotional> {

    /**
     * Attempts to search a dailyDevotional with dailyDevotionalname.
     *
     * @param date
     *            the dailyDevotionalname to search for
     *
     * @return dailyDevotional or null
     */
    public DailyDevotional getByDate(Date date);

    /**
     * Checks that a dailyDevotional dailyDevotionalname is only used once.
     *
     * @param date
     *            the date to check
     * @param excludeId
     *            the id to exclude
     *
     * @return true if the code is unique
     */
    public Boolean checkDateUniqueness(Date date,
                                       Long excludeId);

    /**
     * List am amount of devotionals starting on a specified date.
     *
     * @param amount
     * @param date
     * @return
     */
    public List<DailyDevotional> listLastDevotionals(Long amount, Date date);
}
