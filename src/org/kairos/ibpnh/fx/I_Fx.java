package org.kairos.ibpnh.fx;

import com.googlecode.objectify.Objectify;
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
	 * Sets the Persistence Manager.
	 * 
	 * @param ofy
	 */
	public void setOfy(Objectify ofy);
	
	/**
	 * Returns the persistence manager being hold by the FX.
	 * 
	 * @return persistence manager
	 */
	public Objectify getOfy();
	
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
