package org.kairos.ibpnh.core.services.alert;

import org.kairos.ibpnh.core.vo.alert.AlertVo;

/**
 * Service for persisting alert asynchronously.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_AlertSrv {

	/**
	 * Saves an alert asynchronously.
	 * 
	 * @param alertVo the alert to save
	 */
	public void saveAlert(AlertVo alertVo);
	
}
