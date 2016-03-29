package org.ibpnh.core.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * FX Factory implementation of the I_FxFactory interface.
 * 
 * @author Axel Collard Bovy
 *
 */
public class FxFactoryImpl implements I_FxFactory {
	
	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(FxFactoryImpl.class);
	
	/**
	 * Spring Application Context
	 */
	@Autowired
	private ApplicationContext applicationContext;

	/* 
	 * (non-Javadoc)
	 * @see I_FxFactory#getNewFxInstance(java.lang.Class)
	 */
	@Override
	public <F extends I_Fx> F getNewFxInstance(Class<F> clazz) {
		this.logger.debug("creating new FX: " + clazz.getName());
		
		return this.getApplicationContext().getBean(clazz);
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	

}
