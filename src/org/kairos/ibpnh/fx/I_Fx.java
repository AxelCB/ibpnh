package org.kairos.ibpnh.fx;

import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.I_Model;

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
	 * Sets the Value Object.
	 * 
	 * @param object
	 */
	public void setEntity(I_Model object);

	/**
	 * Disables the firing of alerts for this FX.
	 */
	public void disableAlert();
	
}
