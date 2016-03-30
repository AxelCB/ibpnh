package org.kairos.ibpnh.core.utils;

/**
 * Error Code Constants.
 * 
 * @author Axel Collard Bovy
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
	public static final String ERROR_MOVEMENT_DETAIL_NO_CREDIT_TRANSFER = "023";

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
	
	// mercado pago errors
	public static final String ERROR_MP_NOT_ENABLED = "500";
	public static final String ERROR_MP_JSON_EXCEPTION = "501";
	public static final String ERROR_MP_PAYABLE_THING_NOT_FOUND = "502";

	// Unknown cause errors
	public static final String ERROR_GETTING_PARKING_STATUS = "900";
	public static final String ERROR_GETTING_CURRENT_BALANCE = "901";
	public static final String ERROR_PRIVATE_HASH_MISSING = "902";

	// Mobile Error Codes.
	// Global
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_ALREADY_PARKED = Integer
			.valueOf("2");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_MAX_USED_ALREADY_PARKED = Integer
			.valueOf("76");
	public static final Integer MOBILE_ERROR_CODE_GLOBAL_MULTIPLE_ALREADY_PARKED = Integer
			.valueOf("77");
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
	public static final Integer MOBILE_ERROR_CODE_CHANGE_PASSWORD_SAME_AS_USERNAME = Integer
			.valueOf("24");
	public static final Integer MOBILE_ERROR_CODE_CHANGE_PASSWORD_ALPHANUMERIC_ERROR = Integer
			.valueOf("25");
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
	// Brands query
	public static final Integer MOBILE_ERROR_CODE_BRANDS_OK = Integer
			.valueOf("41");
	// Vehicle check
	public static final Integer MOBILE_ERROR_VEHICLE_CHECK_OK = Integer
			.valueOf("42");
	public static final Integer MOBILE_ERROR_CODE_PENDINGS_CHECKS_OK = Integer
			.valueOf("43");
	public static final Integer MOBILE_ERROR_CODE_PARKING_FINE_OK = Integer
			.valueOf("44");
	public static final Integer MOBILE_ERROR_CODE_PARKING_FINE_NO_BRAND = Integer
			.valueOf("45");
	public static final Integer MOBILE_ERROR_CODE_PARKING_FINE_NO_MODEL = Integer
			.valueOf("46");
	public static final Integer MOBILE_ERROR_CODE_PARKING_FINE_NO_STREET = Integer
			.valueOf("47");
	public static final Integer MOBILE_ERROR_CODE_TRAFFIC_FINES_OK = Integer
			.valueOf("48");
	public static final Integer MOBILE_ERROR_CODE_VEHICLE_DATA_OK = Integer
			.valueOf("49");
	public static final Integer MOBILE_ERROR_CODE_FRONTAGE_ADDRESS_CORRECT_OK = Integer
			.valueOf("52");
	public static final Integer MOBILE_ERROR_CODE_CANCEL_FRONTAGE_CHECK_OK = Integer
			.valueOf("53");
	public static final Integer MOBILE_ERROR_CODE_CHECK_FRONTAGE_PENDING = Integer
			.valueOf("51");
	public static final Integer MOBILE_ERROR_CODE_CHECK_SUCCESSFULLY_COMPLETED = Integer
			.valueOf("50");	
	
	// Credit Transfer
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_BAD_USER_TO = Integer
			.valueOf("54");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_USER_TO_SAME_AS_USER_FROM = Integer
			.valueOf("63");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_BAD_AMOUNT = Integer
			.valueOf("55");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_AGENT_MIN = Integer
			.valueOf("56");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_AGENT_MAX = Integer
			.valueOf("57");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_PERIOD_MAX = Integer
			.valueOf("58");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_CURRENTLY_PARKED = Integer
			.valueOf("59");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_NOT_ENOUGH_CREDIT = Integer
			.valueOf("60");
	public static final Integer MOBILE_ERROR_CODE_CREDIT_TRANSFER_OK = Integer
			.valueOf("61");
	public static final Integer MOBILE_ERROR_CODE_LICENSE_PLATE_CURRENT_INFRACTIONS_OK = Integer
			.valueOf("62");
	public static final Integer MOBILE_ERROR_CODE_GET_TURNOS_OK = Integer
			.valueOf("65");
	public static final Integer MOBILE_ERROR_CODE_PERMANENT_POINT_TYPE_OK = Integer
			.valueOf("66");
	public static final Integer MOBILE_ERROR_CODE_PERMANENT_POINTS_OK = Integer
			.valueOf("69");
	public static final Integer MOBILE_ERROR_CODE_COLORS_OK = Integer
			.valueOf("70");
	public static final Integer MOBILE_ERROR_NOT_EXISTS_CODE_MAIL = Integer
			.valueOf("73");
	public static final Integer MOBILE_ERROR_SEND_MAIL_OK = Integer
			.valueOf("74");
	
	// Register Log Inspector
	public static final Integer MOBILE_REGISTER_CORRECT = Integer
			.valueOf("75");
}
