package org.kairos.ibpnh.utils;

import org.kairos.ibpnh.dao.configuration.parameter.I_ParameterDao;
import org.kairos.ibpnh.json.E_DateFormat;
import org.kairos.ibpnh.model.configuration.parameter.Parameter;
import org.kairos.ibpnh.utils.CollectionUtils.CollectionToMapConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * I_DateUtils implementation.
 * 
 * @author sgarcia
 * 
 */
public class DateUtilsImpl implements I_DateUtils {

	/**
	 * Logger for this class.
	 */
	private Logger logger = LoggerFactory.getLogger(DateUtilsImpl.class);

	/**
	 * Parameter DAO.
	 */
	@Autowired
	private I_ParameterDao parameterDao;

	/**
	 * Date format.
	 */
	private DateFormat dateFormat = null;

	/**
	 * Date time format.
	 */
	private DateFormat dateTimeFormat = null;

	/**
	 * Date time format without milliseconds.
	 */
	private DateFormat dateTimeFormatWithoutMilliseconds = null;

	/**
	 * Date time format without seconds.
	 */
	private DateFormat dateTimeFormatWithoutSeconds = null;

	/**
	 * Date time format without seconds and year.
	 */
	private DateFormat dateTimeFormatWithoutSecondsAndYear = null;

	/**
	 * Hour format.
	 */
	private DateFormat hourFormat = null;

	/**
	 * Native SQL Date Format.
	 */
	private DateFormat nativeSqlDateFormat = null;

	/**
	 * Native SQL Date Time Format.
	 */
	private DateFormat nativeSqlDateTimeFormat = null;

	/**
	 * SMS WS Date Format.
	 */
	private DateFormat smsWsDateFormat = null;

	/**
	 * JSOn Date time exchange format.
	 */
	private DateFormat jsonDateTimeExchangeFormat = null;

	/**
	 * Time constants.
	 */
	private final static Long MILLIS_IN_SECOND = 1000L;
	private final static Long SECONDS_IN_MINUTE = 60L;

	/**
	 * Init method.
	 */
	public void init() {
		try {
			// gets all the format parameters
			List<String> parametersToGet = new ArrayList<>();
			parametersToGet.addAll(Arrays.asList(new String[] {
					Parameter.LOCALE_LANGUAGE_TAG, Parameter.DATE_FORMAT,
					Parameter.DATETIME_FORMAT,
					Parameter.DATETIME_FORMAT_WITHOUT_MILLISECONDS,
					Parameter.DATETIME_FORMAT_WITHOUT_SECONDS,
					Parameter.DATETIME_FORMAT_WITHOUT_SECONDS_AND_YEAR,
					Parameter.HOUR_FORMAT,
					Parameter.NATIVE_SQL_DATE_FORMAT,
					Parameter.NATIVE_SQL_DATE_TIME_FORMAT,
					Parameter.SMS_WS_DATE_FORMAT,
					Parameter.JSON_DATE_TIME_EXCHANGE_FORMAT }));
			List<Parameter> formats = this.getParameterDao().getByName(parametersToGet);

			// transform the collection into a map (key being parameter's name,
			// and value being parameter's value)
			Map<String, String> formatsMap = CollectionUtils
					.collectionToMap(
							formats,
							new CollectionToMapConverter<String, String, Parameter>() {

								@Override
								public String getKey(Parameter parameter) {
									return parameter.getName();
								}

								@Override
								public String getValue(Parameter parameter) {
									return parameter.getValue();
								}

							});

			// we get the locale
			Locale locale = Locale.forLanguageTag(formatsMap
					.get(Parameter.LOCALE_LANGUAGE_TAG));

			// create all simple date formats for every case
			this.setDateFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.DATE_FORMAT), locale));
			this.setDateTimeFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.DATETIME_FORMAT), locale));
			this.setDateTimeFormatWithoutMilliseconds(new SimpleDateFormat(
					formatsMap
							.get(Parameter.DATETIME_FORMAT_WITHOUT_MILLISECONDS),
					locale));
			this.setDateTimeFormatWithoutSeconds(new SimpleDateFormat(
					formatsMap.get(Parameter.DATETIME_FORMAT_WITHOUT_SECONDS),
					locale));
			this.setDateTimeFormatWithoutSecondsAndYear(new SimpleDateFormat(
					formatsMap
							.get(Parameter.DATETIME_FORMAT_WITHOUT_SECONDS_AND_YEAR),
					locale));
			this.setHourFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.HOUR_FORMAT), locale));
			this.setNativeSqlDateFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.NATIVE_SQL_DATE_FORMAT), locale));
			this.setNativeSqlDateTimeFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.NATIVE_SQL_DATE_TIME_FORMAT), locale));
			this.setSmsWsDateFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.SMS_WS_DATE_FORMAT), locale));
			this.setJsonDateTimeExchangeFormat(new SimpleDateFormat(formatsMap
					.get(Parameter.JSON_DATE_TIME_EXCHANGE_FORMAT), locale));
		} catch (Exception e) {
			this.logger.error("error getting all format parameters", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.utils.I_DateUtils#refreshFormats()
	 */
	@Override
	public void refreshFormats() {
		// just inits the bean again
		this.init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.universe.core.utils.I_DateUtils#format(java.util.Date,
	 * org.universe.core.json.E_DateFormat)
	 */
	@Override
	public String format(Date date, E_DateFormat dateFormat) {
		switch (dateFormat) {
		case DATE_FORMAT:
			return this.formateDate(date);
		case DATE_TIME_FORMAT:
			return this.formateDateTime(date);
		case DATE_TIME_FORMAT_WITHOUT_MILLISECONDS:
			return this.formateDateTimeWithoutMilliseconds(date);
		case DATE_TIME_FORMAT_WITHOUT_SECONDS:
			return this.formateDateTimeWithoutSeconds(date);
		case DATE_TIME_FORMAT_WITHOUT_SECONDS_AND_YEAR:
			return this.formateDateTimeWithoutSecondsAndYear(date);
		case HOUR_FORMAT:
			return this.formatHour(date);
		}

		// should never get here
		return null;
	}

	/**
	 * Zeroes the hour part of a date.
	 * 
	 * @param date
	 *            the to use
	 * 
	 * @return date with 0 in hour fields
	 */
	@Override
	public Date zeroHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Parses a String of format "dd/MM/yyyy HH:mm:ss.SSS" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseJsonDateTime(String f) {
		Date d = new Date();
		try {
			d = this.getJsonDateTimeExchangeFormat().parse(f);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Parses a String of format "dd/MM/yyyy" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseDate(String f) {
		Date d = new Date();
		try {
			if(this.getDateFormat()==null){
				this.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
			}
			d = this.getDateFormat().parse(f);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Parses a String of format "dd/MM/yyyy HH:mm:ss.SSS" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseDateTime(String f) {
		Date d = new Date();
		try {
			d = this.getDateTimeFormat().parse(f);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Parses a String of format "HH:mm" to a Date
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseHour(String h) {
		Date d = new Date();
		try {
			d = this.getHourFormat().parse(h);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Parses a String with format "dd/MM/yyyy HH:mm:ss" to a Date
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseDateTimeWithoutMilliseconds(String h) {
		Date d = new Date();
		try {
			d = this.getDateTimeFormatWithoutMilliseconds().parse(h);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Parses a String with format "dd/MM/yyyy HH:mm" to a Date
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseDateTimeWithoutSeconds(String h) {
		Date d = new Date();
		try {
			d = this.getDateTimeFormatWithoutSeconds().parse(h);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Parses a String with format used from the SMS gateway
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	@Override
	public Date parseSmsWsDate(String h) {
		Date d = new Date();
		try {
			d = this.getSmsWsDateFormat().parse(h);
		} catch (ParseException e) {
		}

		return d;
	}

	/**
	 * Formats a Date using "dd/MM/yyyy"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDate(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getDateFormat().format(f);
	}

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm:sss.SSS"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDateTime(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getDateTimeFormat().format(f);
	}

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm:sss.SSS"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateJsonDateTime(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getJsonDateTimeExchangeFormat().format(f);
	}

	/**
	 * Formats a Date using "HH:mm"
	 * 
	 * @param h
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formatHour(Date h) {
		if (h == null) {
			h = new Date();
		}
		return this.getHourFormat().format(h);
	}

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDateTimeWithoutMilliseconds(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getDateTimeFormatWithoutMilliseconds().format(f);
	}

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDateTimeWithoutSeconds(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getDateTimeFormatWithoutSeconds().format(f);
	}

	/**
	 * Formats a Date using "dd/MM HH:mm"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDateTimeWithoutSecondsAndYear(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getDateTimeFormatWithoutSecondsAndYear().format(f);
	}

	/**
	 * Formats a Date using "yyyy-MM-dd"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDateForNativeSql(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getNativeSqlDateFormat().format(f);
	}

	/**
	 * Formats a Date using "HH:mm:sss.SSS"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	@Override
	public String formateDateTimeForNativeSql(Date f) {
		if (f == null) {
			f = new Date();
		}
		return this.getNativeSqlDateTimeFormat().format(f);
	}

	/**
	 * Compares to Dates using only the hour and minute parts.
	 * 
	 * @param hi
	 *            date
	 * @param hf
	 *            date
	 * 
	 * @return int
	 */
	@Override
	public int compareHours(Date hi, Date hf) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(hi);
		Integer _hi = cal.get(Calendar.HOUR_OF_DAY);
		Integer _mi = cal.get(Calendar.MINUTE);

		cal.setTime(hf);
		Integer _hf = cal.get(Calendar.HOUR_OF_DAY);
		Integer _mf = cal.get(Calendar.MINUTE);

		// Are equals by default
		int resul = 0;

		if (_hi == _hf) {
			// Check minuts if hours are equal
			resul = _mi.compareTo(_mf);
		} else {
			// If hours are different only check between them
			resul = _hi.compareTo(_hf);
		}

		return resul;
	}

	/**
	 * Copies the our part of the "hour" parameter to the "date" parameter
	 * 
	 * @param date
	 *            date to
	 * @param hour
	 *            date from
	 * 
	 * @return Date
	 */
	@Override
	public Date copyHour(Date date, Date hour) {
		Calendar dateCalendar = Calendar.getInstance();
		Calendar hourCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		hourCalendar.setTime(hour);
		dateCalendar.set(Calendar.HOUR_OF_DAY,
				hourCalendar.get(Calendar.HOUR_OF_DAY));
		dateCalendar.set(Calendar.MINUTE, hourCalendar.get(Calendar.MINUTE));
		dateCalendar.set(Calendar.SECOND, hourCalendar.get(Calendar.SECOND));
		dateCalendar.set(Calendar.MILLISECOND,
				hourCalendar.get(Calendar.MILLISECOND));

		return dateCalendar.getTime();
	}

	/**
	 * True iif from lt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean lowerThanDate(Date from, Date to) {
		return this.zeroHour(from).compareTo(this.zeroHour(to)) == -1;
	}

	/**
	 * True iif from le to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean lowerOrEqualThanDate(Date from, Date to) {
		int result = this.zeroHour(from).compareTo(this.zeroHour(to));
		return result == -1 || result == 0;
	}

	/**
	 * True iif from gt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean greaterThanDate(Date from, Date to) {
		return this.zeroHour(from).compareTo(this.zeroHour(to)) == 1;
	}

	/**
	 * True iif from ge to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean greaterOrEqualThanDate(Date from, Date to) {
		int result = this.zeroHour(from).compareTo(this.zeroHour(to));
		return result == 1 || result == 0;
	}

	/**
	 * True iif from equals to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean equalDate(Date from, Date to) {
		return this.zeroHour(from).compareTo(this.zeroHour(to)) == 0;
	}

	/**
	 * True iif date is between start and end
	 * 
	 * @param date
	 *            the date to test
	 * @param start
	 *            the range start
	 * @param end
	 *            the end start
	 * @return
	 */
	@Override
	public Boolean betweenDates(Date date, Date start, Date end) {
		return this.lowerOrEqualThanDate(start, date)
				&& this.lowerOrEqualThanDate(date, end);
	}

	/**
	 * True iif from lt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean lowerThanDateTime(Date from, Date to) {
		return from.compareTo(to) == -1;
	}

	/**
	 * True iif from le to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean lowerOrEqualThanDateTime(Date from, Date to) {
		int result = from.compareTo(to);
		return result == -1 || result == 0;
	}

	/**
	 * True iif from gt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean greaterThanDateTime(Date from, Date to) {
		return from.compareTo(to) == 1;
	}

	/**
	 * True iif from ge to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean greaterOrEqualThanDateTime(Date from, Date to) {
		int result = from.compareTo(to);
		return result == 1 || result == 0;
	}

	/**
	 * True iif from equals to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Override
	public Boolean equalDateTime(Date from, Date to) {
		return from.compareTo(to) == 0;
	}

	/**
	 * True iif date is between start and end
	 * 
	 * @param date
	 *            the date to test
	 * @param start
	 *            the range start
	 * @param end
	 *            the end start
	 * @return
	 */
	@Override
	public Boolean betweenDateTimes(Date date, Date start, Date end) {
		return this.lowerOrEqualThanDateTime(start, date)
				&& this.lowerOrEqualThanDateTime(date, end);
	}

	/**
	 * Truncates (zeroes) the seconds and milliseconds of the Date.
	 * 
	 * @param date
	 *            date to use
	 * 
	 * @return Date
	 */
	@Override
	public Date truncateSeconds(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Truncates (zeroes) the milliseconds of the Date.
	 * 
	 * @param date
	 *            date to use
	 * 
	 * @return Date
	 */
	@Override
	public Date truncateMilliseconds(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Returns the difference in seconds between two dates.
	 * 
	 * @param from
	 *            the date from
	 * @param to
	 *            the date to
	 * @param blankMilliseconds
	 *            if true, milliseconds will be zeroed before comparison
	 * @param enforceOneMillisecond
	 *            if true, this difference will never return 0 (at least 1)
	 * 
	 * @return second difference between dates
	 */
	@Override
	public Long secondDifference(Date from, Date to, Boolean blankMilliseconds,
			Boolean enforceOneMillisecond, Boolean onlyHours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(from);
		if (blankMilliseconds) {
			calendar.set(Calendar.MILLISECOND, 0);
		}
		Long millisFrom = calendar.getTimeInMillis();

		Date toDateAux = null;

		if (onlyHours) {
			toDateAux = this.copyHour(calendar.getTime(), to);
		} else {
			toDateAux = to;
		}
		Long millisTo = toDateAux.getTime();

		Long millisDifference = ((Double) Math
				.ceil((double) (millisTo - millisFrom) / 1000)).longValue();

		return (millisDifference == 0L && enforceOneMillisecond) ? 1L
				: millisDifference;
	}

	/**
	 * Returns the difference in minutes between two dates.
	 * 
	 * @param from
	 *            the date from
	 * @param to
	 *            the date to
	 * @param blankMilliseconds
	 *            if true, milliseconds will be zeroed before comparison
	 * @param enforceOneMillisecond
	 *            if true, this difference will never return 0 (at least 1)
	 * @param onlyHours
	 *            if consider only the hours of the dates
	 * 
	 * @return minute difference between dates
	 */
	@Override
	public Long minuteDifference(Date from, Date to, Boolean blankMilliseconds,
			Boolean enforceOneMillisecond, Boolean onlyHours) {
		return ((Double) Math.ceil((double) this.secondDifference(from, to,
				blankMilliseconds, enforceOneMillisecond, onlyHours) / 60))
				.longValue();
	}

	/**
	 * Returns the difference in minutes between two dates.
	 * 
	 * @param from
	 *            the date from
	 * @param to
	 *            the date to
	 * @param blankMilliseconds
	 *            if true, milliseconds will be zeroed before comparisong
	 * @param enforceOneMillisecond
	 *            if true, this difference will never return 0 (at least 1)
	 * 
	 * @return minute difference between dates
	 */
	@Override
	public Long minuteDifference(Date from, Date to, Boolean blankMilliseconds,
			Boolean enforceOneMillisecond) {
		return this.minuteDifference(from, to, blankMilliseconds,
				enforceOneMillisecond, Boolean.TRUE);
	}

	/**
	 * Compares the hours without checking the the other date parts (day, month,
	 * year)
	 * 
	 * @param hourOne
	 *            first hour to compare two
	 * @param hourTwo
	 *            second hour to compare two
	 * 
	 * @return
	 */
	@Override
	public int compareFullHours(Date hourOne, Date hourTwo) {
		Date fixedHourTwo = this.copyHour(hourOne, hourTwo);

		return hourOne.compareTo(fixedHourTwo);
	}

	/**
	 * Adds a minute amount to a date.
	 * 
	 * @param date
	 *            the date to add to
	 * @param minutes
	 *            the minutes to add
	 * @param truncateSeconds
	 *            if true, truncates the seconds of the result
	 * 
	 * @return Date
	 */
	@Override
	public Date addMinutes(Date date, Long minutes, Boolean truncateSeconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes.intValue());

		if (truncateSeconds) {
			return this.truncateSeconds(calendar.getTime());
		} else {
			return calendar.getTime();
		}
	}

	/**
	 * Formats a minutes amount to the format used in the SMSs.
	 * 
	 * Examples For 55 minutes will return 55' For 72 minutes will return 1h12'
	 * For 152 minutes will return 2h32'
	 * 
	 * 
	 * @param minutes
	 * 
	 * @return
	 */
	@Override
	public String formatMinutes(Long minutes) {
		String minutesString = "";

		Long hours = minutes / 60L;
		if (hours > 0) {
			minutesString += hours.toString() + "h";
		}

		minutesString += minutes % 60 + "'";

		return minutesString;
	}

	/**
	 * Computes the total milliseconds in an amount of minutes.
	 * 
	 * @param minutes
	 * 
	 * @return
	 */
	@Override
	public Long minutesToMillis(Long minutes) {
		return minutes * SECONDS_IN_MINUTE * MILLIS_IN_SECOND;
	}

	/**
	 * Gets the first day of the month that the specified date falls in.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return date with first day of month
	 */
	@Override
	public Date firstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.zeroHour(date));
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	/**
	 * Gets the first day for the month and year specified.
	 * 
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @return date with first day of month
	 */
	@Override
	public Date firstDayOfMonth(Long month, Long year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year.intValue());
		calendar.set(Calendar.MONTH, month.intValue());
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return this.zeroHour(calendar.getTime());
	}

	/**
	 * Returns the next millisecond date for a given date.
	 * 
	 * @param date
	 *            date to move a millisecond to
	 * 
	 * @return new date
	 */
	@Override
	public Date nextMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, 1);

		return calendar.getTime();
	}

	/**
	 * Returns the most ancient date from a group of dates.
	 * 
	 * @param dates
	 *            dates to compare
	 * 
	 * @return minimum Date
	 */
	@Override
	public Date minDateTime(Date... dates) {
		if (dates.length < 1) {
			return null;
		} else {
			Date min = dates[0];
			for (Date date : dates) {
				if (this.lowerThanDateTime(date, min)) {
					min = date;
				}
			}

			return min;
		}

	}

	/**
	 * Gets the last minute for a specified timestamp.
	 * 
	 * @param timestamp
	 *            timestamp to change
	 * 
	 * @return timestamp with time changed to 23:59:00.000
	 */
	@Override
	public Date lastMinute(Date timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(timestamp);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
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

	/**
	 * @return the dateFormat
	 */
	private DateFormat getDateFormat() {
		return this.dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	private void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the dateTimeFormat
	 */
	private DateFormat getDateTimeFormat() {
		return this.dateTimeFormat;
	}

	/**
	 * @param dateTimeFormat
	 *            the dateTimeFormat to set
	 */
	private void setDateTimeFormat(DateFormat dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	/**
	 * @return the dateTimeFormatWithoutMilliseconds
	 */
	private DateFormat getDateTimeFormatWithoutMilliseconds() {
		return this.dateTimeFormatWithoutMilliseconds;
	}

	/**
	 * @param dateTimeFormatWithoutMilliseconds
	 *            the dateTimeFormatWithoutMilliseconds to set
	 */
	private void setDateTimeFormatWithoutMilliseconds(
			DateFormat dateTimeFormatWithoutMilliseconds) {
		this.dateTimeFormatWithoutMilliseconds = dateTimeFormatWithoutMilliseconds;
	}

	/**
	 * @return the dateTimeFormatWithoutSeconds
	 */
	private DateFormat getDateTimeFormatWithoutSeconds() {
		return this.dateTimeFormatWithoutSeconds;
	}

	/**
	 * @param dateTimeFormatWithoutSeconds
	 *            the dateTimeFormatWithoutSeconds to set
	 */
	private void setDateTimeFormatWithoutSeconds(
			DateFormat dateTimeFormatWithoutSeconds) {
		this.dateTimeFormatWithoutSeconds = dateTimeFormatWithoutSeconds;
	}

	/**
	 * @return the dateTimeFormatWithoutSecondsAndYear
	 */
	private DateFormat getDateTimeFormatWithoutSecondsAndYear() {
		return this.dateTimeFormatWithoutSecondsAndYear;
	}

	/**
	 * @param dateTimeFormatWithoutSecondsAndYear
	 *            the dateTimeFormatWithoutSecondsAndYear to set
	 */
	private void setDateTimeFormatWithoutSecondsAndYear(
			DateFormat dateTimeFormatWithoutSecondsAndYear) {
		this.dateTimeFormatWithoutSecondsAndYear = dateTimeFormatWithoutSecondsAndYear;
	}

	/**
	 * @return the hourFormat
	 */
	private DateFormat getHourFormat() {
		return this.hourFormat;
	}

	/**
	 * @param hourFormat
	 *            the hourFormat to set
	 */
	private void setHourFormat(DateFormat hourFormat) {
		this.hourFormat = hourFormat;
	}

	/**
	 * @return the nativeSqlDateFormat
	 */
	private DateFormat getNativeSqlDateFormat() {
		return this.nativeSqlDateFormat;
	}

	/**
	 * @param nativeSqlDateFormat
	 *            the nativeSqlDateFormat to set
	 */
	private void setNativeSqlDateFormat(DateFormat nativeSqlDateFormat) {
		this.nativeSqlDateFormat = nativeSqlDateFormat;
	}

	/**
	 * @return the nativeSqlDateTimeFormat
	 */
	private DateFormat getNativeSqlDateTimeFormat() {
		return this.nativeSqlDateTimeFormat;
	}

	/**
	 * @param nativeSqlDateTimeFormat
	 *            the nativeSqlDateTimeFormat to set
	 */
	private void setNativeSqlDateTimeFormat(DateFormat nativeSqlDateTimeFormat) {
		this.nativeSqlDateTimeFormat = nativeSqlDateTimeFormat;
	}

	/**
	 * @return the smsWsDateFormat
	 */
	private DateFormat getSmsWsDateFormat() {
		return this.smsWsDateFormat;
	}

	/**
	 * @param smsWsDateFormat
	 *            the smsWsDateFormat to set
	 */
	private void setSmsWsDateFormat(DateFormat smsWsDateFormat) {
		this.smsWsDateFormat = smsWsDateFormat;
	}


	/**
	 * @return the jsonDateTimeExchangeFormat
	 */
	public DateFormat getJsonDateTimeExchangeFormat() {
		return this.jsonDateTimeExchangeFormat;
	}

	/**
	 * @param jsonDateTimeExchangeFormat
	 *            the jsonDateTimeExchangeFormat to set
	 */
	public void setJsonDateTimeExchangeFormat(
			DateFormat jsonDateTimeExchangeFormat) {
		this.jsonDateTimeExchangeFormat = jsonDateTimeExchangeFormat;
	}
}