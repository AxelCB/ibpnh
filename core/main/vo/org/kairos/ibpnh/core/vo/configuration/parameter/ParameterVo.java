package org.kairos.ibpnh.core.vo.configuration.parameter;

import java.text.ParseException;

import org.pojomatic.annotations.AutoProperty;
import org.kairos.ibpnh.core.model.configuration.parameter.E_ParameterType;
import org.kairos.ibpnh.core.vo.AbstractVo;

/**
 * Value Object for the Parameter Entity
 * 
 * @author Axel Collard Bovy
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
	public final static String SYSTEM_FULL_URL = "system.full.url";
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
	public final static String NUMERIC_LOCALE = "numeric.locale";
	public final static String NUMERIC_SEPARATOR = "numeric.separator";
	public final static String ABOUT_GENERAL = "about.general";
	public final static String COMPANY_URL = "company.url";
	public final static String DECIMAL_PATTERN = "decimal.pattern";
	public final static String LOCALE_LANGUAGE_TAG = "locale.language.tag";
	public final static String BANNER_TEXT = "banner.text";
	public final static String USER_REGISTRATION = "user.registration";
	public final static String USER_REGISTRATION_COOKIE_EXPIRATION = "user.registration.cookie.expiration";
	
	/**
	 * Email
	 */
	public final static String EMAIL_SYSTEM_ON_OFF_NOTIFICATION_TYPE = "email.system.on.off.notification.type";
	public final static String EMAIL_FOOTER = "email.footer";
	public final static String EMAIL_SENDER = "email.sender";
	public final static String EMAIL_NOTIFICATION_SUBJECT = "email.notification.subject";

	/**
	 * Images
	 */
	public final static String IMAGE_PATH_PREFIX = "image.path.";
	public final static String IMAGE_PATH_LOGO = "image.path.logo";
	public final static String IMAGE_PATH_LOGO_HOME = "image.path.logo.home";
	public final static String IMAGE_PDF_REVENUE_PATH_LOGO = "image.pdf.revenue.path.logo";
	public final static String IMAGE_PATH_BANNER = "image.path.banner";

	/**
	 * Date/Time Formats.
	 */
	public final static String JSON_DATE_TIME_EXCHANGE_FORMAT = "json.date.time.exchange.format";
	public final static String DATE_FORMAT = "date.format";
	public final static String DATETIME_FORMAT = "date.time.format";
	public final static String DATETIME_FORMAT_WITHOUT_MILLISECONDS = "date.time.format.without.milliseconds";
	public final static String DATETIME_FORMAT_WITHOUT_SECONDS = "date.time.format.without.seconds";
	public final static String DATETIME_FORMAT_WITHOUT_SECONDS_AND_YEAR = "date.time.format.without.seconds.and.year";
	public final static String HOUR_FORMAT = "hour.format";
	public final static String NATIVE_SQL_DATE_FORMAT = "native.sql.date.format";
	public final static String NATIVE_SQL_DATE_TIME_FORMAT = "native.sql.date.time.format";
	public final static String DATETIME_FORMAT_ISO8601 = "date.time.format.ISO8601";
	public final static String DATE_FORMAT_WITHOUT_DAYS_SHORT = "date.format.without.days.short";
	public final static String DATETIME_FORMAT_WITH_HYPHENS_WITHOUT_MILLISECONDS = "date.time.format.with.hyphens.without.milliseconds";

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
	 * Visado flag.
	 */
	private Boolean viewed;
	
	/**
	 * Tags.
	 */
	private String tags;

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

	/**
	 * @return the tags
	 */
	public String getTags() {
		return this.tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	public Boolean getViewed() {
		return this.viewed;
	}

	public void setViewed(Boolean viewed) {
		this.viewed = viewed;
	}
	
}
