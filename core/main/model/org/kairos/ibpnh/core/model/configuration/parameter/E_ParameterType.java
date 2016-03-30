package org.kairos.ibpnh.core.model.configuration.parameter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Parameter Type Enum
 * 
 * @author Axel Collard Bovy
 *
 */
public enum E_ParameterType {
	STRING(null),
	DATE("dd/MM/yyyy"),
	TIME("HH:mm:ss.SSS"),
	DATETIME("dd/MM/yyyy HH:mm:ss.SSS"),
	LONG(null),
	BIGDECIMAL(null),
	BOOLEAN(null);
	
	/**
	 * The format to use for the conversion.
	 */
	private String format;
	
	/**
	 * Format getter.
	 * 
	 * @return the format
	 */
	private String getFormat() {
		return this.format;
	}
	
	/**
	 * Private constructor.
	 * 
	 * @param format the format string
	 */
	private E_ParameterType(String format) {
		this.format = format;
	}
	
	/**
	 * Gets the parameter properly formatted.
	 * 
	 * @param parameter the parameter to format
	 * @param clazz the class type to return
	 * 
	 * @return an object formatted to the clazz
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public <T> T formatParameter(String parameter, Class<T> clazz) throws ParseException {
		switch (this) {
			case STRING:
				//no further conversion required
				return (T) parameter;
			case BIGDECIMAL:
				return (T) new BigDecimal(parameter.replaceAll(",", "."));
			case DATE: case DATETIME: case TIME:
				SimpleDateFormat sdf = new SimpleDateFormat(this.getFormat());
				return (T) sdf.parse(parameter);
			case LONG:
				return (T) (Long) Long.parseLong(parameter);
			case BOOLEAN:
				return (T) (Boolean) Boolean.parseBoolean(parameter);
		}
		
		return null;
	}
}