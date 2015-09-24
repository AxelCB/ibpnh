package org.kairos.ibpnh.dao.devotional;

import com.googlecode.objectify.Objectify;
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
     * @param pm
     *            the persistence manager
     * @param date
     *            the dailyDevotionalname to search for
     *
     * @return dailyDevotional or null
     */
    public DailyDevotional getByDate(Objectify ofy, Date date);

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
    public Boolean checkDateUniqueness(Objectify ofy, Date date,
                                       String excludeId);

    /**
     * List am amount of devotionals starting on a specified date.
     *
     * @param pm
     * @param amount
     * @param date
     * @return
     */
    public List<DailyDevotional> listLastDevotionals(Objectify ofy,Long amount, Date date);
}
