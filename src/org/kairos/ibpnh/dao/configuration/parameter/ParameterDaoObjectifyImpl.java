package org.kairos.ibpnh.dao.configuration.parameter;

import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.model.devotional.DailyDevotional;

import java.util.Collection;
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
public class ParameterDaoObjectifyImpl extends AbstractDao<Parameter> implements I_ParameterDao{

	@Override
	public Class<Parameter> getClazz() {
		return Parameter.class;
	}

	@Override
	public Parameter getByName(String name) {
		return null;
	}

	@Override
	public List<Parameter> getsByName(String name) {
		return null;
	}

	@Override
	public List<Parameter> getByName(Collection<String> names) {
		return null;
	}

	@Override
	public void loadGlobalParameters() {

	}

	public void init(){}

	@Override
	public boolean checkNameUniqueness(String name, Long excludeId) {
		return false;
	}
}