package org.kairos.ibpnh.utils;

/**
 * Error Code Constants.
 *
 * @author AxelCollardBovy ,created on 08/03/2015.
 * 
 */
public class ErrorCodes {

	public static final String ERROR_UNEXPECTED = "000";
	public static final String ERROR_ENTITY_ID_UNDEFINED = "001";
	public static final String ERROR_PARAMETER_PARSING = "002";
	public static final String ERROR_MONEY_ACCOUNT_CREATION = "003";
	public static final String ERROR_CREDIT_ACCOUNT_CREATION = "004";
	public static final String ERROR_MOVEMENT_DETAIL_NO_FX = "005";
	public static final String ERROR_MOVEMENT_DETAIL_NO_PARKING = "006";
	public static final String ERROR_MOVEMENT_DETAIL_NO_CREDIT_CHARGE = "007";
	public static final String ERROR_MAIL_FX_EXPOSED_PARAMETERS_NULL = "008";
	public static final String ERROR_MAIL_FX_RETURNED_PARAMETERS_NULL = "009";
	public static final String ERROR_MAIL_FX_RETURNED_PARAMETERS_INCOMPLETE = "010";
	public static final String ERROR_MOVEMENT_DETAIL_NO_CREDIT_CHARGE_CANCELLATION = "011";
	public static final String ERROR_PARAMETER_MISSING = "012";
	public static final String ERROR_PAYMENT_ACCOUNT_INTEROPERABILITY = "013";
	public static final String ERROR_VO_CLAZZ_NOT_FOUND = "014";
	public static final String ERROR_DAO_FOR_VO_CLAZZ_NOT_FOUND = "015";
	public static final String ERROR_ACCOUNT_ID_MISSING = "016";
	public static final String ERROR_VIEW_MOVEMENTS_FX_MISSING = "017";
	public static final String ERROR_MOVEMENT_DETAIL_NO_PUNCTUAL_PARKING_CANCELLATION = "018";
	public static final String ERROR_MOVEMENT_DETAIL_NO_VOLUNTARY_PAYMENT = "019";
	public static final String ERROR_NON_UNIQUE_ZONE_ASSIGNMENT_SPECIFICATION = "020";
	public static final String ERROR_GET_PERSONAL_DATA_FX_MISSING = "021";
	public static final String ERROR_GENERATING_PDF = "022";

	// Cell phone related error codes start from 100
	public static final String ERROR_USER_NOT_REGISTERED = "100";
	public static final String ERROR_UPDATING_CARRIER = "101";
	public static final String ERROR_SMS_TEMPLATE_NOT_FOUND = "102";

	// fine related error codes start from 200
	public static final String ERROR_PARKING_FINE_NOT_FOUND = "200";
	public static final String ERROR_FRONTAGE_FINE_NOT_FOUND = "201";

	// quartz scheduler related errors start from 300
	public static final String ERROR_SCHEDULING_JOB = "300";
	public static final String ERROR_UNSCHEDULING_JOB = "301";
	public static final String ERROR_TRIGGERING_JOB = "302";
	public static final String ERROR_PARSING_CRON_EXPRESSION = "303";
	public static final String ERROR_PAUSING_JOB = "304";
	public static final String ERROR_RESUMING_JOB = "305";

	// registry error
	public static final String ERROR_REGISTRY = "400";

	// Unknown cause errors
	public static final String ERROR_GETTING_PARKING_STATUS = "900";
	public static final String ERROR_GETTING_CURRENT_BALANCE = "901";
	public static final String ERROR_PRIVATE_HASH_MISSING = "902";

	// Mobile Error Codes.
	// Global
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_ALREADY_PARKED = Integer
			.valueOf("2");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_ERROR = Integer
			.valueOf("7");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_NO_CURRENT_PARKING = Integer
			.valueOf("8");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_BAD_TOKEN = Integer
			.valueOf("11");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_BAD_VERSION = Integer
			.valueOf("12");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_NO_PARKING_NEEDED = Integer
			.valueOf("13");
	// Login
	public static final Integer MOBILE_ERROR_CODE_LOGIN_OK = Integer
			.valueOf("15");
	public static final Integer MOBILE_ERROR_CODE_LOGIN_OK_CHANGE_PASSWORD = Integer
			.valueOf("16");
	public static final Integer MOBILE_ERROR_CODE_LOGIN_BAD_USER = Integer
			.valueOf("17");
	public static final Integer MOBILE_ERROR_CODE_LOGIN_BAD_PASSWORD = Integer
			.valueOf("20");
	// Change password
	public static final Integer MOBILE_ERROR_CODE_CHANGE_PASSWORD_OK = Integer
			.valueOf("18");
	public static final Integer MOBILE_ERROR_CODE_CHANGE_PASSWORD_ERROR = Integer
			.valueOf("19");
	public static final Integer MOBILE_ERROR_CODE_CHANGE_PASSWORD_BAD_CURRENT = Integer
			.valueOf("29");
	// Status (uses all global error codes)
	// Parking Start
	public static final Integer MOBILE_ERROR_CODE_START_ALREADY_PUNCTUAL = Integer
			.valueOf("3");
	public static final Integer MOBILE_ERROR_CODE_START_ALREADY_OTHER = Integer
			.valueOf("4");
	public static final Integer MOBILE_ERROR_CODE_START_NO_MINIMUM = Integer
			.valueOf("5");
	public static final Integer MOBILE_ERROR_CODE_START_OK = Integer
			.valueOf("6");
	public static final Integer MOBILE_ERROR_CODE_START_BAD_LICENSE_PLATE = Integer
			.valueOf("21");
	// Parking End
	public static final Integer MOBILE_ERROR_CODE_END_CANCELLED = Integer
			.valueOf("9");
	public static final Integer MOBILE_ERROR_CODE_END_OK = Integer
			.valueOf("10");
	// Movements query
	public static final Integer MOBILE_ERROR_CODE_MOVEMENTS_OK = Integer
			.valueOf("14");
	// Points of sale query
	public static final Integer MOBILE_ERROR_CODE_POINTS_OF_SALE_OK = Integer
			.valueOf("22");
	// Parameters query
	public static final Integer MOBILE_ERROR_CODE_PARAMETERS_OK = Integer
			.valueOf("23");
	// Transit query
	public static final Integer MOBILE_ERROR_CODE_TRANSIT_OK = Integer
			.valueOf("27");
	public static final Integer MOBILE_ERROR_CODE_TRANSIT_FILTERS_OK = Integer
			.valueOf("28");
	// Municipalities query
	public static final Integer MOBILE_ERROR_CODE_MUNICIPALITIES_OK = Integer
			.valueOf("30");
	// Alarm configuration storing
	public static final Integer MOBILE_ERROR_CODE_ALARM_OK = Integer
			.valueOf("31");
	public static final Integer MOBILE_ERROR_CODE_ALARM_BAD_START = Integer
			.valueOf("32");
	public static final Integer MOBILE_ERROR_CODE_ALARM_BAD_END = Integer
			.valueOf("33");
	// Alarm query
	public static final Integer MOBILE_ERROR_CODE_ALARM_QUERY_OK = Integer
			.valueOf("34");
	// Notification configuration storing
	public static final Integer MOBILE_ERROR_CODE_NOTIFICATION_OK = Integer
			.valueOf("38");
	// Version query
	public static final Integer MOBILE_ERROR_CODE_VERSION_OK = Integer
			.valueOf("39");
	// Zones query
	public static final Integer MOBILE_ERROR_CODE_ZONES_OK = Integer
			.valueOf("40");

}
