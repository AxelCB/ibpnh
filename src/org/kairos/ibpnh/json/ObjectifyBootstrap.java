package org.kairos.ibpnh.json;

import com.googlecode.objectify.ObjectifyService;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.model.devotional.DailyDevotional;
import org.kairos.ibpnh.model.user.User;

/**
 * Created on 9/24/15 by
 *
 * @author AxelCollardBovy.
 */
public class ObjectifyBootstrap {

	public void init(){
		ObjectifyService.register(User.class);
		ObjectifyService.register(DailyDevotional.class);
		ObjectifyService.register(Parameter.class);
	}
}
