package org.kairos.ibpnh.core.fx;

/**
 * I_Fx factory class. Creates new I_Fx's ready to be executed.
 * 
 * @author Axel Collard Bovy
 *
 */
public interface I_FxFactory {

	/**
	 * Creates a new FX.
	 * 
	 * @param fxName the class of the FX to create
	 * 
	 * @return a newly created fxInstance
	 */
	public <T extends I_Fx> T getNewFxInstance(Class<T> clazz);
}
