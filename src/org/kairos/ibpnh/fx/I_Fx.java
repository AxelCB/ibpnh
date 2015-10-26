package org.kairos.ibpnh.fx;

import org.kairos.ibpnh.json.JsonResponse;
import org.kairos.ibpnh.model.I_Model;
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
