package org.kairos.ibpnh.core.services.alert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.services.alert.producer.AlertProducerMq;
import org.kairos.ibpnh.core.vo.alert.AlertVo;

/**
 * This implementation uses MQ Asynchronous Messaging JMS Implementation.
 * 
 * @author gromero
 * 
 */
public class AlertSrvMqImpl implements I_AlertSrv {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(AlertSrvMqImpl.class);

	/**
	 * 
	 */
	@Autowired
	private AlertProducerMq alertProducerMq;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kairos.ibpnh.core.fx.alert.I_AlertSrv#saveAlert(org.kairos.ibpnh.core.vo.
	 * alert.AlertVo)
	 */
	@Override
	public void saveAlert(AlertVo alertVo) {
		this.logger.debug("sending alert: {} ", alertVo.toString());

		this.getAlertProducerMq().send(alertVo);
	}

	/**
	 * @return the alertProducerMq
	 */
	public AlertProducerMq getAlertProducerMq() {
		return this.alertProducerMq;
	}

	/**
	 * @param alertProducerMq
	 *            the alertProducerMq to set
	 */
	public void setAlertProducerMq(AlertProducerMq alertProducerMq) {
		this.alertProducerMq = alertProducerMq;
	}

}
