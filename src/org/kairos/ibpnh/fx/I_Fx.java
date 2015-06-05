package org.kairos.ibpnh.fx;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.vo.AbstractVo;

/**
 * General Interface for Fx's.
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
public interface I_Fx {

	/**
	 * Executes the FX
	 * 
	 * @return a JsonResponse
	 */
	public JsonResponse execute();
	
	/**
	 * Sets the Persistence Manager.
	 * 
	 * @param pm
	 */
	public void setPm(JDOPersistenceManager pm);
	
	/**
	 * Returns the persistence manager being hold by the FX.
	 * 
	 * @return persistence manager
	 */
	public JDOPersistenceManager getPm();
	
	/**
	 * Sets the Value Object.
	 * 
	 * @param vo
	 */
	public void setVo(AbstractVo vo);
	
	/**
	 * Disables the firing of alerts for this FX.
	 */
	public void disableAlert();
	
}
