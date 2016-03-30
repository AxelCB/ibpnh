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
	public final static String MUNICIPALITY_NAME = "municipality.name";
	public final static String TEMPLATE_MUNICIPALITY = "template.municipality";
	public final static String HASH_COST = "hash.cost";
	public final static String LOGIN_MAX_ATTEMPTS = "login.max.attempts";
	public final static String PASSWORD_MIN_CHARACTERS = "password.min.characters";
	public final static String ITEMS_PER_PAGE = "items.per.page";
	public final static String ITEMS_PER_PAGE_MOVEMENTS = "items.per.page.movements";
	public final static String ITEMS_PER_PAGE_RANKING_FINES = "items.per.page.ranking.fines";
	public final static String TOP_AMOUNT_RANKING_FINES = "top.amount.ranking.fines";
	public final static String ACCOUNT_MINIMUM_BALANCE = "account.minimum.balance";
	public final static String LICENSE_PLATE_REGEXP = "license.plate.regexp";
	public final static String LICENSE_PLATE_REGEXP_INSTANT_EM_FINE = "license.plate.regexp.instant.em.fine";
	public final static String LICENSE_PLATE_FORMATS = "license.plate.formats";
	public final static String LICENSE_PLATE_MULTIPLE_SIZE = "license.plate.multiple.size";
	public final static String FOOTER_CREDITS = "footer.credits";
	public final static String FOOTER_VERSION = "footer.version";
	public final static String FOOTER_KICKOFF_YEAR = "footer.kickoff.year";
	public final static String PUNCTUAL_PARKING_UNIT = "punctual.parking.unit";
	public final static String PUNCTUAL_PARKING_MINUTES_FRACTION = "punctual.parking.minutes.fraction";
	public final static String PUNCTUAL_PARKING_EXTENDED = "punctual.parking.extended";
	public final static String CELLPHONE_PARKING_MINUTES_FRACTION_START = "cellphone.parking.minutes.fraction.start";
	public final static String CELLPHONE_PARKING_MINUTES_FRACTION = "cellphone.parking.minutes.fraction";
	public final static String PASSWORD_GENERATOR_LENGTH = "password.generator.length";
	public final static String PASSWORD_REGEXP = "password.regexp";
	public final static String PASSWORD_GENERATOR_NUMERIC = "password.generator.numeric";
	public final static String PASSWORD_GENERATOR_LOWERCASE = "password.generator.lowercase";
	public final static String CELLPHONE_RECHARGE_LIMIT_MINUTES = "cellphone.recharge.limit.minutes";
	public final static String CELLPHONE_PERMANENCE_ALERT_MINUTES_LIST = "cellphone.permanence.alert.minutes.list";
	public final static String CELLPHONE_PERMANENCE_ALERT_TEMPLATE_LIST = "cellphone.permanence.alert.template.list";
	public final static String CELLPHONE_FORCED_END_MINUTES = "cellphone.forced.end.minutes";
	public final static String CELLPHONE_FORCED_END_TEMPLATE = "cellphone.forced.end.template";
	public final static String CELLPHONE_NEAR_END_MINUTES = "cellphone.near.end.minutes";
	public final static String FRONTAGE_EXEMPTION_MAX_VEHICLES = "frontage.exemption.max.vehicles";
	public final static String OPERABLE_DAYS_HOURS_STRING = "operable.days.hours.string";
	public final static String OPERABLE_DAYS_HOURS_STRING_SHORT = "operable.days.hours.string.short";
	public final static String PUSHER_CODE = "pusher.code";
	public final static String MANUAL_FINALIZATION_REASONS = "manual.finalization.reasons";
	public final static String USES_DEFAULT_ZONE = "uses.default.zone";
	public final static String FINE_PRINT_CSS = "fine.print.css";
	public final static String FINE_PRINT_HTML = "fine.print.html";
	public final static String FINE_PRINT_PAGE_BREAK_HTML = "fine.print.page.break.html";
	public final static String DAILY_REVENUE_SIPRI = "daily.revenue.sipri";
	public final static String USER_REGISTRATION_BONUS = "user.registration.bonus";
	public final static String CUIT_FORMAT_REGEXPS = "cuit.format.regexps";
	public final static String CUIT_FORMAT_EXAMPLES = "cuit.format.examples";
	public final static String LICENSE_PLATE_LAST_PARKING_BLOCK = "license.plate.last.parking.block";
	public final static String NUMERIC_LOCALE = "numeric.locale";
	public final static String NUMERIC_SEPARATOR = "numeric.separator";
	public final static String ABOUT_GENERAL = "about.general";
	public final static String COMPANY_URL = "company.url";
	public final static String DECIMAL_PATTERN = "decimal.pattern";
	public final static String LOCALE_LANGUAGE_TAG = "locale.language.tag";
	public final static String CELLPHONE_FORMAT_REGEXP = "cellphone.format.regexp";
	public final static String SEND_SMS_RESPONSES = "send.sms.responses";
	public final static String SEND_SMS_NOTIFICATIONS = "send.sms.notifications";
	public final static String SEND_SMS_PREFIX = "send.sms.prefix";
	public final static String SEND_MOBILE_NOTIFICATIONS = "send.mobile.notifications";
	public final static String SEND_EMAIL_NOTIFICATIONS = "send.email.notifications";
	public final static String SEND_MULTIPLE_NOTIFICATIONS = "send.multiple.notifications";
	public final static String CMI_JSON = "cmi.json";
	public final static String CMI_ENABLED = "cmi.enabled";
	public final static String FINE_CONTROL_MINUTE_STEP = "fine.control.minute.step";
	public final static String FINE_CONTROL_ADDRESS = "fine.control.address";
	public final static String NATIVE_UPDATE_SQL = "native.update.sql";
	public final static String PAYABLE_FINES_FOR_LICENSE_PLATE_DISCLAIMER ="payable.fines.for.license.plate.disclaimer";
	public final static String THOUSANDS_SEPARATOR = "thousands.separator";
	public final static String DECIMAL_SEPARATOR = "decimal.separator";
	public final static String DAILY_REVENUE_CURRENCY_CONVERSION = "daily.revenue.currency.conversion";
	public final static String DAILY_REVENUE_CURRENCY_CONVERSION_ENABLED = "daily.revenue.currency.conversion.enabled";
	public final static String CURRENCY_CONVERSION_FORM_CLARIFICATION = "currency.conversion.form.clarification";
	public final static String FINE_NOTIFICATION_HOUR = "fine.notification.hour";
	public final static String FINE_NOTIFICATION = "fine.notification";
	public final static String FINE_NOTIFICATION_DAYS_FROM_ISSUED = "fine.notification.days.from.issued";
	public final static String TOW_ENABLED = "tow.enabled";
	public final static String BLOCK_OCCUPATIONAL_FACTOR_ENABLED = "block.occupational.factor.enabled";
	public final static String FINE_MAX_CHECKS = "fine.max.checks";
	public final static String FINE_FLAG_TYPE = "fine.flag.type";
	public final static String BANNER_TEXT = "banner.text";
	public final static String USE_BAD_RECHECK_CACHING = "use.bad.recheck.caching";
	public final static String BAD_RECHECK_FETCH_MAX_LIMIT = "bad.recheck.fetch.max.limit";
	
	/**
	 * Operable Days Characters.
	 */
	public final static String OPERABLE_DAY_MONDAY_CHARACTER = "operable.day.monday.character";
	public final static String OPERABLE_DAY_TUESDAY_CHARACTER = "operable.day.tuesday.character";
	public final static String OPERABLE_DAY_WEDNESDAY_CHARACTER = "operable.day.wednesday.character";
	public final static String OPERABLE_DAY_THURSDAY_CHARACTER = "operable.day.thursday.character";
	public final static String OPERABLE_DAY_FRIDAY_CHARACTER = "operable.day.friday.character";
	public final static String OPERABLE_DAY_SATURDAY_CHARACTER = "operable.day.saturday.character";
	public final static String OPERABLE_DAY_SUNDAY_CHARACTER = "operable.day.sunday.character";

	/**
	 * Motorbike subscription  
	 */
	public final static String MONTHLY_PAYMENT_PRICE = "monthly.payment.price";
	
	/**
	 * SMS
	 */
	public final static String SMS_NUMBER = "sms.number";
	public final static String SMS_KEYWORD = "sms.keyword";
	public final static String SMS_REGISTRATION_KEYWORD = "sms.keyword.registration";
	public final static String SMS_BALANCE_INQUIRY_KEYWORD = "sms.keyword.balanceInquery";
	public final static String SMS_KEYWORD_END = "sms.keyword.end";
	public final static String SMS_KEYWORD_PASSWORD = "sms.keyword.password";
	public final static String SMS_KEYWORD_NEEDSTOSTARTWITHKEYWORD = "sms.keyword.needsToStartWithKeyword";
	public final static String SMS_KEYWORD_PLATFORMS = "sms.keyword.platforms";
	public final static String SMS_KEYWORD_PLATFORMS_URLS = "sms.keyword.platforms.urls";
	public final static String SMS_KEYWORD_PLATFORMS_PASSWORD = "sms.keyword.platforms.password";
	public final static String SMS_KEYWORD_ZONE_FETCH = "sms.keyword.zone.fetch";
	public final static String SMS_KEYWORD_ZONE_ID_TO_CODE = "sms.keyword.zone.id.to.code";
	
	public final static String XML_AXIS = "xml.axis";

	/**
	 * Email
	 */
	public final static String EMAIL_SYSTEM_ON_OFF_NOTIFICATION_TYPE = "email.system.on.off.notification.type";
	public final static String EMAIL_FOOTER = "email.footer";
	public final static String EMAIL_SENDER = "email.sender";
	public final static String EMAIL_NOTIFICATION_SUBJECT = "email.notification.subject";

	/**
	 * Check
	 */
	public final static String CHECK_BATCH_MAX = "check.batch.max";
	public final static String CHECK_AUTOMATIC_DELAY = "check.automatic.delay";
	public final static String CHECK_BLOCK_DURATION = "check.block.duration";
	public final static String CHECK_RESULT_COLOR_OK = "check.result.color.ok";
	public final static String CHECK_RESULT_COLOR_BAD = "check.result.color.bad";
	public final static String CHECK_RESULT_COLOR_OFF = "check.result.color.off";
	public final static String CHECK_RESULT_COLOR_DOWN = "check.result.color.down";
	public final static String CHECK_RESULT_COLOR_BLOCKED = "check.result.color.blocked";
	public final static String CHECK_RESULT_COLOR_FINED = "check.result.color.fined";
	public final static String CHECK_RESULT_COLOR_ERROR = "check.result.color.error";
	public final static String CHECK_CANCELLATION_REASONS = "check.cancellation.reasons";
	public final static String CHECK_CANCELLATION_ENABLED = "check.cancellation.enabled";
	public final static String CHECK_STRICT_ZONING = "check.strict.zoning";
	public final static String CHECK_ZONING_RANKING_INFRACTION_RANGE = "check.zoning.ranking.infraction.range";
	public final static String CHECK_ZONING_RANKING_RESET_IF_BAD = "check.zoning.ranking.reset.if.bad";
	public final static String CHECK_SHOW_PARKING_START_TIMESTAMP = "check.show.parking.start.timestamp";
	public final static String CHECK_INSPECTOR_CONTROL_VISIBLE = "check.inspector.control.visible";

	/**
	 * Inspector Control
	 */
	public final static String INSPECTOR_CONTROL_RANGES = "inspector.control.ranges";
	public final static String INSPECTOR_CONTROL_EXEMPTIONS = "inspector.control.exemptions";
	public final static String INSPECTOR_TRAFFIC_CONTROL_RANGES = "inspector.traffic.control.ranges";

	/**
	 * Credit Charge
	 */
	public final static String CELLPHONE_CREDIT_CHARGE_MIN = "cellphone.credit.charge.min";

	/**
	 * Credit Transfer
	 */
	public final static String CREDIT_TRANSFER_ENABLED = "credit.transfer.enabled";
	public final static String CREDIT_TRANSFER_MIN_PER_AGENT = "credit.transfer.min.per.agent";
	public final static String CREDIT_TRANSFER_MAX_PER_AGENT = "credit.transfer.max.per.agent";
	public final static String CREDIT_TRANSFER_PERIOD_MAX = "credit.transfer.period.max";
	public final static String CREDIT_TRANSFER_COMMISSION = "credit.transfer.commission";

	/**
	 * Clearing
	 */
	public final static String STOP_CLEARING = "stop.clearing";
	public final static String PAYMENT_CODE_PREFIX = "payment.code.prefix";
	public final static String CLEARING_INCLUDE_COMMISSION = "clearing.include.commission";

	/**
	 * Punctual Parking Control
	 */
	public final static String PUNCTUAL_PARKING_CONTROL_MINUTE_START = "punctual.parking.control.minute.start";
	public final static String PUNCTUAL_PARKING_CONTROL_MINUTE_END = "punctual.parking.control.minute.end";
	public final static String PUNCTUAL_PARKING_CONTROL_MINUTE_STEP = "punctual.parking.control.minute.step";
	public final static String PUNCTUAL_PARKING_CONTROL_LAST_MINUTES_RANGES = "punctual.parking.control.last.minutes.ranges";
	public final static String PUNCTUAL_PARKING_CONTROL_REFRESH_SECONDS = "punctual.parking.control.refresh.seconds";

	public final static String SMS_CONTROL_MINUTE_START = "sms.control.minute.start";
	public final static String SMS_CONTROL_MINUTE_END = "sms.control.minute.end";
	public final static String SMS_CONTROL_MINUTE_STEP = "sms.control.minute.step";
	public final static String SMS_CONTROL_LAST_MINUTES_RANGES = "sms.control.last.minutes.ranges";
	public final static String SMS_CONTROL_REFRESH_SECONDS = "sms.control.refresh.seconds";

	public final static String TEMPLATE_CURRENCYSYMBOL = "template.currencySymbol";

	public final static String TICKET_PRINT_WIDTH = "ticket.print.width";
	public final static String TICKET_PRINT_MARGIN_TOP = "ticket.print.margin.top";
	public final static String TICKET_PRINT_MARGIN_LEFT = "ticket.print.margin.left";
	public final static String TICKET_PRINT_FONT_SIZE = "ticket.print.font.size";
	public final static String TICKET_PRINT_FONT_FAMILY = "ticket.print.font.family";
	public final static String TICKET_PRINT_BODY_STYLE = "ticket.print.body.style";

	public final static String USER_REGISTRATION = "user.registration";
	public final static String USER_REGISTRATION_CAPTCHA = "user.registration.captcha";
	public final static String USER_REGISTRATION_ACCOUNT_ID_FORMAT = "user.registration.account.id.format";
	public final static String USER_REGISTRATION_COOKIE_EXPIRATION = "user.registration.cookie.expiration";

	public final static String CREDITCHARGE_IDENTIFIER_CELLPHONE = "creditcharge.identifier.cellphone";
	public final static String CREDITCHARGE_IDENTIFIER_ACCOUNT = "creditCharge.identifier.account";

	public final static String DEFAULT_ZONE_PERMITS_STAY = "default.zone.permits.stay";
	public final static String DEFAULT_ZONE_STAY_VALUE = "default.zone.stay.value";

	public final static String ESTIMATED_PARKING_LOTS_COUNT = "estimated.parking.lots.count";
	public final static String ESTIMATED_PARKING_BLOCKS_COUNT = "estimated.parking.blocks.count";

	public final static String ZONE_ASSIGNMENT_MAX_DAYS = "zone.assignment.max.days";

	public final static String PARKING_ANTICIPATED_FLAG = "parking.anticipated.flag";
	
	public final static String MINUTES_PUNCTUAL_PARKING_CACHE = "minutes.punctual.parking.cache";
	
	public final static String MINUTES_NOT_STARTED_PARKINGS_CACHE = "minutes.not.started.parkings.cache";

	/**
	 * Mobile.
	 */

	public final static String MOBILE_API_VERSION = "mobile.api.version";

	public final static String MOBILE_MIN_VERSION_PREFIX = "mobile.min.version.agent.";
	public final static String MOBILE_LAST_VERSION_PREFIX = "mobile.last.version.agent.";
	public final static String MOBILE_APP_DOWNLOAD_URL_PREFIX = "mobile.app.download.url.agent.";

	public final static String MOBILE_MIN_VERSION_AGENT_8 = "mobile.min.version.agent.8";
	public final static String MOBILE_LAST_VERSION_AGENT_8 = "mobile.last.version.agent.8";
	public final static String MOBILE_APP_DOWNLOAD_URL_AGENT_8 = "mobile.app.download.url.agent.8";

	public final static String MOBILE_MIN_VERSION_AGENT_10 = "mobile.min.version.agent.10";
	public final static String MOBILE_LAST_VERSION_AGENT_10 = "mobile.last.version.agent.10";
	public final static String MOBILE_APP_DOWNLOAD_URL_AGENT_10 = "mobile.app.download.url.agent.10";

	public final static String MOBILE_DESCRIPTION_PREFIX = "mobile.description.";
	public final static String MOBILE_DESCRIPTION_CREDIT_CHARGE = "mobile.description.credit.charge";
	public final static String MOBILE_DESCRIPTION_CREDIT_CHARGE_CANCELLATION = "mobile.description.credit.charge.cancellation";
	public final static String MOBILE_DESCRIPTION_CELLPHONE_PARKING = "mobile.description.cellphone.parking";
	public final static String MOBILE_DESCRIPTION_CELLPHONE_PARKING_START = "mobile.description.cellphone.parking.start";
	public final static String MOBILE_DESCRIPTION_CELLPHONE_PARKING_END = "mobile.description.cellphone.parking.end";
	public final static String MOBILE_DESCRIPTION_REGISTRATION_BONUS = "mobile.description.registration.bonus";
	public final static String MOBILE_DESCRIPTION_REGISTRATION_BONUS_CANCELLATION = "mobile.description.registration.bonus.cancellation";
	public final static String MOBILE_DESCRIPTION_ACCOUNT_COMPENSATION_CREDIT = "mobile.description.account.compensation.credit";
	public final static String MOBILE_DESCRIPTION_ACCOUNT_COMPENSATION_CREDIT_CANCELLATION = "mobile.description.account.compensation.credit.cancellation";
	public final static String MOBILE_DESCRIPTION_ACCOUNT_COMPENSATION_DEBIT = "mobile.description.account.compensation.debit";
	public final static String MOBILE_DESCRIPTION_ACCOUNT_COMPENSATION_DEBIT_CANCELLATION = "mobile.description.account.compensation.debit.cancellation";
	public final static String MOBILE_DESCRIPTION_CREDIT_RETURN = "mobile.description.credit.return";
	public final static String MOBILE_DESCRIPTION_CREDIT_RETURN_CANCELLATION = "mobile.description.credit.return.cancellation";
	public final static String MOBILE_DESCRIPTION_CREDIT_TRANSFER = "mobile.description.credit.transfer";
	public final static String MOBILE_DESCRIPTION_COMMISSION_CREDIT_TRANSFER = "mobile.description.commission.credit.transfer";
	public final static String MOBILE_AVAILABLE_ROLES = "mobile.available.roles";

	/**
	 * Service Fields Parameters.
	 */
	public final static String MOBILE_SERVICE_LAST_TRANSACTIONS_TOTAL = "mobile.service.last.transactions.total";
	public final static String MOBILE_SERVICE_FIELD_ACTION = "mobile.service.field.action";
	public final static String MOBILE_SERVICE_FIELD_CELLPHONE = "mobile.service.field.cellphone";
	public final static String MOBILE_SERVICE_FIELD_PASSWORD = "mobile.service.field.password";
	public final static String MOBILE_SERVICE_FIELD_AGENT = "mobile.service.field.agent";
	public final static String MOBILE_SERVICE_FIELD_VERSION = "mobile.service.field.version";
	public final static String MOBILE_SERVICE_FIELD_TOKEN = "mobile.service.field.token";
	public final static String MOBILE_SERVICE_FIELD_NEW_PASSWORD = "mobile.service.field.new.password";
	public final static String MOBILE_SERVICE_FIELD_LICENSE_PLATE = "mobile.service.field.license.plate";
	public final static String MOBILE_SERVICE_FIELD_LICENSE_PLATES = "mobile.service.field.license.plates";
	public final static String MOBILE_SERVICE_FIELD_MUNICIPALITY_CODE = "mobile.service.field.municipality.code";
	public final static String MOBILE_SERVICE_FIELD_ZONE = "mobile.service.field.zone";
	public final static String MOBILE_SERVICE_FIELD_LAST_PASSWORD = "mobile.service.field.last.password";
	public final static String MOBILE_SERVICE_FIELD_AMOUNT = "mobile.service.field.amount";
	public final static String MOBILE_SERVICE_FIELD_USER_TO = "mobile.service.field.user.to";
	public final static String MOBILE_TIME_OUT_SEM_INSPECTOR = "mobile.time.out.sem.inspector";

	/**
	 * Images
	 */
	public final static String IMAGE_PATH_PREFIX = "image.path.";
	public final static String IMAGE_PATH_LOGO = "image.path.logo";
	public final static String IMAGE_PATH_LOGO_HOME = "image.path.logo.home";
	public final static String IMAGE_PDF_REVENUE_PATH_LOGO = "image.pdf.revenue.path.logo";
	public final static String IMAGE_PATH_BANNER = "image.path.banner";

	/**
	 * Dashboard
	 */
	public final static String DASHBOARD_CSS = "dashboard.css";
	public final static String DASHBOARD_JS = "dashboard.js";
	public final static String DASHBOARD_HTML = "dashboard.html";
	public final static String DASHBOARD_REFRESH_INTERVAL = "dashboard.refresh.interval";
	public final static String FINE_DASHBOARD_REFRESH_INTERVAL = "fine.dashboard.refresh.interval";
	public final static String DASHBOARD_HISTORIC_MONTHS = "dashboard.historic.months";
	public final static String FINE_DASHBOARD_CSS = "fine.dashboard.css";
	public final static String FINE_DASHBOARD_JS = "fine.dashboard.js";
	public final static String FINE_DASHBOARD_HTML = "fine.dashboard.html";

	/**
	 * Weekly revenue
	 */
	public final static String WEEKLY_REVENUE_HTML = "weekly.revenue.html";
	public final static String WEEKLY_REVENUE_CSS = "weekly.revenue.css";
	
	/**
	 * Monthly bill.
	 */
	public final static String MONTHLY_BILLING_BRUTE = "monthly.billing.brute";
	public final static String MONTHLY_BILLING_PERCENTAGE = "monthly.billing.percentage";
	public final static String MONTHLY_BILLING_MINIMUM = "monthly.billing.minimum";
	public final static String MONTHLY_BILLING_MINIMUM_UNIT = "monthly.billing.minimum.unit";
	public final static String MONTHLY_BILLING_JS = "monthly.billing.js";
	public final static String MONTHLY_BILLING_CSS = "monthly.billing.css";
	public final static String MONTHLY_BILLING_ATTACHMENT_FILE_NAME = "monthly.billing.attachment.file.name";

	/**
	 * Max Concurrent Parkings.
	 */
	public final static String MAX_CONCURRENT_PARKINGS_NATIVE_QUERY = "max.concurrent.parkings.native.query";
	public final static String MAX_CONCURRENT_PARKINGS_NATIVE_QUERY_PARAMETER_ORDER = "max.concurrent.parkings.native.query.parameter.order";
	public final static String MAX_CONCURRENT_PARKINGS_VALID_PERIOD = "max.concurrent.parkings.valid.period";

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
	public final static String SMS_WS_DATE_FORMAT = "sms.ws.date.format";
	public final static String DATETIME_FORMAT_ISO8601 = "date.time.format.ISO8601";
	public final static String DATE_FORMAT_WITHOUT_DAYS_SHORT = "date.format.without.days.short";
	public final static String DATETIME_FORMAT_WITH_HYPHENS_WIHOUT_MILLISECONDS = "date.time.format.with.hyphens.without.milliseconds";
	
	/**
	 * Mercado Pago.
	 */
	public final static String MERCADO_PAGO_SANDBOX = "mercado.pago.sandbox";
	public final static String MERCADO_PAGO_CREDIT_CHARGE_ENABLED = "mercado.pago.credit.charge.enabled";
	public final static String MERCADO_PAGO_CREDIT_CHARGE_PREFERENCE_TITLE = "mercado.pago.credit.charge.preference.title";
	public final static String MERCADO_PAGO_CREDIT_CHARGE_PREFERENCE_DESCRIPTION = "mercado.pago.credit.charge.preference.description";
	public final static String MERCADO_PAGO_FINE_VOLUNTARY_PAYMENT_ENABLED = "mercado.pago.fine.voluntary.payment.enabled";
	public final static String MERCADO_PAGO_FINE_VOLUNTARY_PAYMENT_PREFERENCE_TITLE = "mercado.pago.fine.voluntary.payment.preference.title";
	public final static String MERCADO_PAGO_FINE_VOLUNTARY_PAYMENT_PREFERENCE_DESCRIPTION = "mercado.pago.fine.voluntary.payment.preference.description";
	public final static String MERCADO_PAGO_DEBT_POINT_OF_SALE_ENABLED = "mercado.pago.debt.point.of.sale.enabled";
	public final static String MERCADO_PAGO_DEBT_POINT_OF_SALE_PREFERENCE_TITLE = "mercado.pago.debt.point.of.sale.preference.title";
	public final static String MERCADO_PAGO_DEBT_POINT_OF_SALE_PREFERENCE_DESCRIPTION = "mercado.pago.debt.point.of.sale.preference.description";
	
	/**
	 * Currency Change Rates
	 */
	public final static String CURRENCY_CHANGE_RATES_UPDATE = "currency.change.rates.update";
	public final static String CURRENCY_CHANGE_RATES_UPDATE_PERIOD = "currency.change.rates.update.period";
	
	/**
	 * Graphic
	 */
	public final static String GET_DAYS_PARKING_PLACES_GRAPHIC = "get.days.parking.places.graphic";
	public final static String GET_MONTH_REVENUE_MONTHLY_GRAPHIC = "get.month.revenue.monthly.graphic";
	
	/**
	 * 
	 */
	public final static String UNCOMPLETED_CHECKS_CATEGORIES_THRESHOLD_DIFFERENCE_PERCENTAGE = "uncompleted.checks.categories.threshold.difference.percentage";
	public final static String UNCOMPLETED_CHECKS_AUTOMATIC_CANCELLATION_ENABLED = "uncompleted.checks.automatic.cancellation.enabled";

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
