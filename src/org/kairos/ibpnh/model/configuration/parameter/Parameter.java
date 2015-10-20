package org.kairos.ibpnh.model.configuration.parameter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.kairos.ibpnh.model.I_Model;
import org.pojomatic.Pojomatic;

import java.io.Serializable;

/**
 * System Global Parameter
 * 
 * @author AxelCollardBovy ,created on 08/03/2015.
 *
 */
@Entity
public class Parameter implements I_Model,Serializable{

	private static final long serialVersionUID = -4980729949255825921L;

	@Id
	private Long id;

	/**
	 * Parameter's name.
	 */
	@Index
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
	@Index
	private Boolean global;

	/**
	 * Logic deletion flag.
	 */
	private Boolean deleted;

	/**
	 * If this parameter is fixed. (name, type, and description cannot be changed).
	 */
	private Boolean fixed;


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
	 * Default Constructor
	 */
	public Parameter() {
	}

	/**
	 * Constructor using fields
	 *
	 * @param name
	 * @param value
	 * @param description
	 * @param type
	 * @param global
	 * @param deleted
	 * @param fixed
	 */
	public Parameter(String name, String value, String description, E_ParameterType type, Boolean global, Boolean deleted, Boolean fixed) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.type = type;
		this.global = global;
		this.deleted = deleted;
		this.fixed = fixed;
	}

	/*
         * (non-Javadoc)
         * @see org.universe.core.model.I_Model#getId()
         */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
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
	 * @param value the value to set
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
	 * @param type the type to set
	 */
	public void setType(E_ParameterType type) {
		this.type = type;
	}


	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return this.deleted;
	}

	/*
	 * (non-Javadoc)
	 * @see org.universe.core.model.I_Model#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}

	/**
	 * @return the global
	 */
	public Boolean getGlobal() {
		return this.global;
	}

	/**
	 * @param global the global to set
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
	 * @param description the description to set
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
	 * @param fixed the fixed to set
	 */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

}
