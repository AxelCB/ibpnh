package org.kairos.ibpnh.core.services.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kairos.ibpnh.core.vo.alert.AlertVo;

/**
 * @author fgonzalez
 *
 */
public class AlertSrvMockImpl implements I_AlertSrv {
	
	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(AlertSrvMockImpl.class);

	/* (non-Javadoc)
	 * @see org.kairos.ibpnh.core.fx.alert.I_AlertSrv#saveAlert(AlertVo)
	 */
	@Override
	public void saveAlert(AlertVo alertVo) {
		this.logger.debug("MOCK IMPLEMENTATION: alert sended: " + alertVo.toString());
	}

}
