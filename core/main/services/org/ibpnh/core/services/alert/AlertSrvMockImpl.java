package org.ibpnh.core.services.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ibpnh.core.vo.alert.AlertVo;

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
	 * @see org.ibpnh.core.fx.alert.I_AlertSrv#saveAlert(org.ibpnh.core.vo.alert.AlertVo)
	 */
	@Override
	public void saveAlert(AlertVo alertVo) {
		this.logger.debug("MOCK IMPLEMENTATION: alert sended: " + alertVo.toString());
	}

}
