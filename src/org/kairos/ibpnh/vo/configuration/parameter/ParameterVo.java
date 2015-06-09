package org.kairos.ibpnh.vo.configuration.parameter;

import org.kairos.ibpnh.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.vo.AbstractVo;
import org.pojomatic.annotations.AutoProperty;

import java.text.ParseException;

/**
 * Value Object for the Parameter Entity
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
@AutoProperty
public class ParameterVo extends AbstractVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7273804276843128894L;

	/**
	 * Referenceable parameter's preffix.
	 */
	public final static String TEMPLATE_PREFFIX = "template.";
	public final static String REFERENCE_PREFFIX = "ref.";

	/**
	 * Parameter Names Constants
	 */
	public final static String SYSTEM_URL = "system.url";
	public final static String SYSTEM_TITLE = "system.title";
	public final static String SYSTEM_NAME = "system.name";
	public final static String HASH_COST = "hash.cost";
	public final static String LOGIN_MAX_ATTEMPTS = "login.max.attempts";
	public final static String PASSWORD_MIN_CHARACTERS = "password.min.characters";
	public final static String ITEMS_PER_PAGE = "items.per.page";
	public final static String FOOTER_CREDITS = "footer.credits";
	public final static String FOOTER_VERSION = "footer.version";
	public final static String FOOTER_KICKOFF_YEAR = "footer.kickoff.year";
	public final static String PASSWORD_GENERATOR_LENGTH = "password.generator.length";
	public final static String PASSWORD_REGEXP = "password.regexp";
	public final static String PASSWORD_GENERATOR_NUMERIC = "password.generator.numeric";
	public final static String PASSWORD_GENERATOR_LOWERCASE = "password.generator.lowercase";
	public final static String PUSHER_CODE = "pusher.code";
	public final static String NUMERIC_LOCALE = "numeric.locale";
	public final static String NUMERIC_SEPARATOR = "numeric.separator";
	public final static String DECIMAL_PATTERN = "decimal.pattern";
	public final static String LOCALE_LANGUAGE_TAG = "locale.language.tag";
	public final static String USER_REGISTRATION = "user.registration";
	public final static String DATE_FORMAT = "date.format";
	public final static String DATETIME_FORMAT = "date.time.format";
	public final static String DATETIME_FORMAT_WITHOUT_MILLISECONDS = "date.time.format.without.milliseconds";
	public final static String DATETIME_FORMAT_WITHOUT_SECONDS = "date.time.format.without.seconds";
	public final static String DATETIME_FORMAT_WITHOUT_SECONDS_AND_YEAR = "date.time.format.without.seconds.and.year";
	public final static String HOUR_FORMAT = "hour.format";
	public final static String NATIVE_SQL_DATE_FORMAT = "native.sql.date.format";
	public final static String NATIVE_SQL_DATE_TIME_FORMAT = "native.sql.date.time.format";
	public final static String SMS_WS_DATE_FORMAT = "sms.ws.date.format";
	public final static String JSON_DATE_TIME_EXCHANGE_FORMAT = "json.date.time.exchange.format";

	/**
	 * Parameter's name.
	 */
	private String name;

	/**
	 * Parameter's value.
	 */
	private String value;

	/**
	 * Description.
	 */
	private String description;

	/**
	 * Parameter's type.
	 */
	private E_ParameterType type;

	/**
	 * Global flag.
	 */
	private Boolean global;

	/**
	 * If this parameter is fixed. (name, type, and description cannot be
	 * changed).
	 */
	private Boolean fixed;

	/**
	 * Returns the value of the parameter properly formatted.
	 * 
	 * @param clazz
	 *            the destination class
	 * 
	 * @return casted parameter
	 * 
	 * @throws ParseException
	 *             if an error parsing the parameter occurred
	 */
	public <T> T getValue(Class<T> clazz) throws ParseException {
		return this.getType().formatParameter(this.getValue(), clazz);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public E_ParameterType getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(E_ParameterType type) {
		this.type = type;
	}

	/**
	 * @return the global
	 */
	public Boolean getGlobal() {
		return this.global;
	}

	/**
	 * @param global
	 *            the global to set
	 */
	public void setGlobal(Boolean global) {
		this.global = global;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the fixed
	 */
	public Boolean getFixed() {
		return this.fixed;
	}

	/**
	 * @param fixed
	 *            the fixed to set
	 */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}
}
