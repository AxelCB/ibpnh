package org.kairos.ibpnh.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.kairos.ibpnh.core.vo.configuration.parameter.ParameterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.EntityManagerHolder;
import org.kairos.ibpnh.core.dao.configuration.parameter.I_ParameterDao;

/**
 * Utils Methods for Big Decimal Operations
 * 
 * @author Axel Collard Bovy
 * 
 */
public class BigDecimalUtils implements I_BigDecimalUtils {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(BigDecimalUtils.class);

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Entity Manager Holder
	 */
	@Autowired
	private EntityManagerHolder emh;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#floor(java.math.BigDecimal,
	 * java.lang.Integer)
	 */
	@Override
	public BigDecimal floor(BigDecimal number, Integer scale) {
		return number.setScale(scale, RoundingMode.FLOOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#ceiling(java.math.BigDecimal,
	 * java.lang.Integer)
	 */
	@Override
	public BigDecimal ceiling(BigDecimal number, Integer scale) {
		return number.setScale(scale, RoundingMode.CEILING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#halfUp(java.math.BigDecimal,
	 * java.lang.Integer)
	 */
	@Override
	public BigDecimal halfUp(BigDecimal number, Integer scale) {
		return number.setScale(scale, RoundingMode.HALF_UP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_BigDecimalUtils#lt(java.math.BigDecimal,
	 * java.math.BigDecimal)
	 */
	@Override
	public Boolean lt(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) < 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_BigDecimalUtils#le(java.math.BigDecimal,
	 * java.math.BigDecimal)
	 */
	@Override
	public Boolean le(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_BigDecimalUtils#gt(java.math.BigDecimal,
	 * java.math.BigDecimal)
	 */
	@Override
	public Boolean gt(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see I_BigDecimalUtils#ge(java.math.BigDecimal,
	 * java.math.BigDecimal)
	 */
	@Override
	public Boolean ge(BigDecimal a, BigDecimal b) {
		return a.compareTo(b) >= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#between(java.math.BigDecimal,
	 * java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public Boolean between(BigDecimal a, BigDecimal start, BigDecimal end) {
		return this.le(start, a) && this.le(a, end);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#strictBetween(java.math.BigDecimal
	 * , java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public Boolean strictBetween(BigDecimal a, BigDecimal start, BigDecimal end) {
		return this.lt(start, a) && this.lt(a, end);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#toPlainStringFormat(java.lang
	 * .Long, java.lang.String)
	 */
	@Override
	public String toPlainStringFormat(Long a, String pattern) {
		ParameterVo languageTag = this.getParameterDao().getByName(null,
				ParameterVo.LOCALE_LANGUAGE_TAG);

		DecimalFormat decimalFormat = new DecimalFormat(pattern,
				new DecimalFormatSymbols(Locale.forLanguageTag(languageTag
						.getValue())));

		return decimalFormat.format(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#toPlainStringFormat(java.math
	 * .BigDecimal)
	 */
	@Override
	public String toPlainStringFormat(BigDecimal a) {
		ParameterVo decimalPattern = this.getParameterDao().getByName(null,
				ParameterVo.DECIMAL_PATTERN);
		ParameterVo languageTag = this.getParameterDao().getByName(null,
				ParameterVo.LOCALE_LANGUAGE_TAG);

		DecimalFormat decimalFormat = new DecimalFormat(
				decimalPattern.getValue(), new DecimalFormatSymbols(
						Locale.forLanguageTag(languageTag.getValue())));

		return decimalFormat.format(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#fromPlainStringFormat(java.
	 * lang.String)
	 */
	@Override
	public BigDecimal fromPlainStringFormat(String a) {
		try {
			return this.fromPlainStringFormatThrowExceptionIfFails(a);
		} catch (ParseException pe) {
			// error parsing big decimal
			this.logger.error("FATAL error parsing big decimal", pe);

			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see I_BigDecimalUtils#fromPlainStringFormatThrowExceptionIfFails(java.lang.String)
	 */
	@Override
	public BigDecimal fromPlainStringFormatThrowExceptionIfFails(String a)
			throws ParseException {
		ParameterVo decimalPattern = this.getParameterDao().getByName(null,
				ParameterVo.DECIMAL_PATTERN);
		ParameterVo languageTag = this.getParameterDao().getByName(null,
				ParameterVo.LOCALE_LANGUAGE_TAG);

		DecimalFormat decimalFormat = new DecimalFormat(
				decimalPattern.getValue(), new DecimalFormatSymbols(
						Locale.forLanguageTag(languageTag.getValue())));
		decimalFormat.setParseBigDecimal(true);

		return (BigDecimal) decimalFormat.parse(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * I_BigDecimalUtils#equalsZero(java.math.BigDecimal
	 * )
	 */
	@Override
	public Boolean equalsZero(BigDecimal number) {
		return number.compareTo(BigDecimal.ZERO) == 0;
	}

	/**
	 * @return the parameterDao
	 */
	public I_ParameterDao getParameterDao() {
		return this.parameterDao;
	}

	/**
	 * @param parameterDao
	 *            the parameterDao to set
	 */
	public void setParameterDao(I_ParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	@Override
	public String toPlainStringFormatWithoutDecimals(BigDecimal a) {
		String decimalPattern = "#,##0";
		ParameterVo languageTag = this.getParameterDao().getByName(null,
				ParameterVo.LOCALE_LANGUAGE_TAG);

		DecimalFormat decimalFormat = new DecimalFormat(decimalPattern,
				new DecimalFormatSymbols(Locale.forLanguageTag(languageTag
						.getValue())));

		return decimalFormat.format(a);
	}

	@Override
	public BigDecimal fromPlainStringFormatWithoutDecimals(String a) {
		String decimalPattern = "#,##0";
		ParameterVo languageTag = this.getParameterDao().getByName(null,
				ParameterVo.LOCALE_LANGUAGE_TAG);

		DecimalFormat decimalFormat = new DecimalFormat(decimalPattern,
				new DecimalFormatSymbols(Locale.forLanguageTag(languageTag
						.getValue())));
		decimalFormat.setParseBigDecimal(true);

		try {
			return (BigDecimal) decimalFormat.parse(a);
		} catch (ParseException pe) {
			// error parsing big decimal
			this.logger.error("FATAL error parsing big decimal", pe);

			return null;
		}
	}

}