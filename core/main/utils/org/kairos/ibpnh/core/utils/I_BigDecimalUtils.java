package org.kairos.ibpnh.core.utils;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Interface for BigDecimal Utils
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_BigDecimalUtils {

	/**
	 * Rounds down a BigDecimal to the specified scale.
	 * 
	 * @param number
	 * @param scale
	 * @return
	 */
	public BigDecimal floor(BigDecimal number, Integer scale);

	/**
	 * Rounds up a BigDecimal to the specified scale.
	 * 
	 * @param number
	 * @param scale
	 * @return
	 */
	public BigDecimal ceiling(BigDecimal number, Integer scale);

	/**
	 * Rounds a BigDecimal using traditional round up scaling.
	 * 
	 * @param number
	 * @param scale
	 * @return
	 */
	public BigDecimal halfUp(BigDecimal number, Integer scale);

	/**
	 * True if a is lower than b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Boolean lt(BigDecimal a, BigDecimal b);

	/**
	 * True if a is lower or equal than b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Boolean le(BigDecimal a, BigDecimal b);

	/**
	 * True if a is greater than b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Boolean gt(BigDecimal a, BigDecimal b);

	/**
	 * True if a is greater or equal than b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Boolean ge(BigDecimal a, BigDecimal b);

	/**
	 * True if a is between start and end
	 * 
	 * @param a
	 * @param start
	 * @param end
	 * @return
	 */
	public Boolean between(BigDecimal a, BigDecimal start, BigDecimal end);

	/**
	 * True if a is between start and end and it's not equal to neither start or
	 * end
	 * 
	 * @param a
	 * @param start
	 * @param end
	 * @return
	 */
	public Boolean strictBetween(BigDecimal a, BigDecimal start, BigDecimal end);

	/**
	 * Formats a Long with a pattern.
	 * 
	 * @param a
	 *            long to p
	 * @param pattern
	 *            pattern to use
	 * 
	 * @return formatted long
	 */
	public String toPlainStringFormat(Long a, String pattern);

	/**
	 * Formats a BigDecimal using the configuration stated by the parameters.
	 * 
	 * @param a
	 *            BigDecimal to format
	 * 
	 * @return the string representation
	 */
	public String toPlainStringFormat(BigDecimal a);

	/**
	 * Parses a BigDecimal from a string using the configuration stated by the
	 * parameters.
	 * 
	 * @param a
	 *            the string to parse
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal fromPlainStringFormat(String a);
	
	/**
	 * Parses a BigDecimal from a string using the configuration stated by the
	 * parameters.
	 * 
	 * @param a
	 *            the string to parse
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal fromPlainStringFormatThrowExceptionIfFails(String a) throws ParseException;

	/**
	 * Formats a BigDecimal using the configuration stated by the parameters,
	 * ignoring the decimal part.
	 * 
	 * @param a
	 *            BigDecimal to format
	 * 
	 * @return the string representation
	 */
	public String toPlainStringFormatWithoutDecimals(BigDecimal a);

	/**
	 * Parses a BigDecimal from a string using the configuration stated by the
	 * parameters, ignoring the decimal part.
	 * 
	 * @param a
	 *            the string to parse
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal fromPlainStringFormatWithoutDecimals(String a);

	/**
	 * True iif number is mathematically equal to zero.
	 * 
	 * @param number
	 * @return boolean
	 */
	public Boolean equalsZero(BigDecimal number);

}
