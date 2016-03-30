package org.kairos.ibpnh.core.json;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate to JSON to add a date formatted with another way.
 * 
 * @author Axel Collard Bovy
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GsonAddDateReadableFormat {

	/**
	 * List of fields to serialize. 
	 * 
	 * @return
	 */
	String[] fields() default {};
	
	/**
	 * List of formats.
	 * 
	 * @return
	 */
	E_DateFormat[] formats() default {};
	
}
