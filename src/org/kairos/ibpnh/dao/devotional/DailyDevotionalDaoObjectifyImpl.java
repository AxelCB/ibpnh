package org.kairos.ibpnh.dao.devotional;

import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.devotional.DailyDevotional;

import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Dao for the Daily Devotional's entities.
 *
 * Created on 9/25/15 by
 *
 * @author AxelCollardBovy.
 */
public class DailyDevotionalDaoObjectifyImpl extends AbstractDao<DailyDevotional> implements I_DailyDevotionalDao{

	@Override
	public Class<DailyDevotional> getClazz() {
		return DailyDevotional.class;
	}

	@Override
	public DailyDevotional getByDate(Date date) {
		return null;
	}

	@Override
	public Boolean checkDateUniqueness(Date date, Long excludeId) {
		return null;
	}

	@Override
	public List<DailyDevotional> listLastDevotionals(Long amount, Date date) {
		return ofy().load().type(this.getClazz()).filter("date =",date).limit(amount.intValue()).list();
	}
}