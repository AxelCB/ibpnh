package org.ibpnh.core.fx;

import javax.persistence.EntityManager;

import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.vo.AbstractVo;

/**
 * General Interface for Fx's.
 * 
 * @author Axel Collard Bovy
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
	 * Sets the Entity Manager.
	 * 
	 * @param em
	 */
	public void setEm(EntityManager em);
	
	/**
	 * Returns the entity manager being hold by the FX.
	 * 
	 * @return entity manager
	 */
	public EntityManager getEm();
	
	/**
	 * Sets the Value Object.
	 * 
	 * @param em
	 */
	public void setVo(AbstractVo vo);
	
	/**
	 * Disables the firing of alerts for this FX.
	 */
	public void disableAlert();
	
}
