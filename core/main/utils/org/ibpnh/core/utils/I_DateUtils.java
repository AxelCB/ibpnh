package org.ibpnh.core.utils;

import java.util.Date;

import org.ibpnh.core.json.E_DateFormat;

/**
 * Date Utils and Helper methods.
 * 
 * @author Axel Collard Bovy
 * 
 */
public interface I_DateUtils {

	/**
	 * Zeroes the hour part of a date.
	 * 
	 * @param date
	 *            the to use
	 * 
	 * @return date with 0 in hour fields
	 */
	public Date zeroHour(Date date);

	/**
	 * Parses a String of format "dd/MM/yyyy" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDate(String f);

	/**
	 * Parses a String of format "dd/MM/yyyy HH:mm:ss.SSS" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDateTime(String f);
	
	/**
	 * Parses a String of format "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDateTimeISO8601(String f);
	
	/**
	 * Parses a String of format "MM/YYYY" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDateWithoutDaysShort(String f);
	
	/**
	 * Parses a String of format "yyyy-MM-dd HH:mm:ss" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDateTimeWithHyphensWithoutMilliseconds(String f);

	/**
	 * Parses a String of format "HH:mm" to a Date
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseHour(String h);

	/**
	 * Parses a String with format "dd/MM/yyyy HH:mm:ss" to a Date
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDateTimeWithoutMilliseconds(String h);

	/**
	 * Parses a String with format "dd/MM/yyyy HH:mm" to a Date
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseDateTimeWithoutSeconds(String h);

	/**
	 * Parses a String with format used from the SMS gateway
	 * 
	 * @param h
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseSmsWsDate(String h);

	/**
	 * Formats a Date using "dd/MM/yyyy"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDate(Date f);

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm:sss.SSS"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTime(Date f);
	
	/**
	 * Formats a Date using "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTimeISO8601(Date f);
	
	/**
	 * Formats a Date using "MM/YYYY"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateWithoutDaysShort(Date f);
	
	/**
	 * Formats a Date using "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTimeWithHyphensWithoutMilliseconds(Date f);
	
	

	/**
	 * Formats a Date using "HH:mm"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formatHour(Date h);

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm:ss"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTimeWithoutMilliseconds(Date f);

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTimeWithoutSeconds(Date f);

	/**
	 * Formats a Date using "dd/MM HH:mm"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTimeWithoutSecondsAndYear(Date f);

	/**
	 * Formats a Date using "yyyy-MM-dd"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateForNativeSql(Date f);

	/**
	 * Formats a Date using "HH:mm:sss.SSS"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateDateTimeForNativeSql(Date f);

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
	public int compareHours(Date hi, Date hf);

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
	public Date copyHour(Date date, Date hour);

	/**
	 * True iif from lt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean lowerThanDate(Date from, Date to);

	/**
	 * True iif from le to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean lowerOrEqualThanDate(Date from, Date to);

	/**
	 * True iif from gt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean greaterThanDate(Date from, Date to);

	/**
	 * True iif from ge to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean greaterOrEqualThanDate(Date from, Date to);

	/**
	 * True iif from equals to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean equalDate(Date from, Date to);

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
	public Boolean betweenDates(Date date, Date start, Date end);

	/**
	 * True iif from lt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean lowerThanDateTime(Date from, Date to);

	/**
	 * True iif from le to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean lowerOrEqualThanDateTime(Date from, Date to);

	/**
	 * True iif from gt to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean greaterThanDateTime(Date from, Date to);

	/**
	 * True iif from ge to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean greaterOrEqualThanDateTime(Date from, Date to);

	/**
	 * True iif from equals to
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public Boolean equalDateTime(Date from, Date to);

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
	public Boolean betweenDateTimes(Date date, Date start, Date end);

	/**
	 * Truncates (zeroes) the seconds and milliseconds of the Date.
	 * 
	 * @param date
	 *            date to use
	 * 
	 * @return Date
	 */
	public Date truncateSeconds(Date date);

	/**
	 * Truncates (zeroes) the milliseconds of the Date.
	 * 
	 * @param date
	 *            date to use
	 * 
	 * @return Date
	 */
	public Date truncateMilliseconds(Date date);

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
	public Long secondDifference(Date from, Date to, Boolean blankMilliseconds,
			Boolean enforceOneMillisecond, Boolean onlyHours);

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
	public Long minuteDifference(Date from, Date to, Boolean blankMilliseconds,
			Boolean enforceOneMillisecond, Boolean onlyHours);

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
	public Long minuteDifference(Date from, Date to, Boolean blankMilliseconds,
			Boolean enforceOneMillisecond);

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
	public int compareFullHours(Date hourOne, Date hourTwo);

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
	public Date addMinutes(Date date, Long minutes, Boolean truncateSeconds);

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
	public String formatMinutes(Long minutes);

	/**
	 * Computes the total milliseconds in an amount of minutes.
	 * 
	 * @param minutes
	 * 
	 * @return
	 */
	public Long minutesToMillis(Long minutes);

	/**
	 * Gets the first day of the month that the specified date falls in.
	 * 
	 * @param date
	 *            the date
	 * 
	 * @return date with first day of month
	 */
	public Date firstDayOfMonth(Date date);

	/**
	 * Gets the first day for the month and year specified.
	 * 
	 * @param month
	 *            the month
	 * @param year
	 *            the year
	 * @return date with first day of month
	 */
	public Date firstDayOfMonth(Long month, Long year);

	/**
	 * Returns the next millisecond date for a given date.
	 * 
	 * @param date
	 *            date to move a millisecond to
	 * 
	 * @return new date
	 */
	public Date nextMillisecond(Date date);

	/**
	 * Returns the most ancient date from a group of dates.
	 * 
	 * @param dates
	 *            dates to compare
	 * 
	 * @return minimum Date
	 */
	public Date minDateTime(Date... dates);

	/**
	 * Gets the last minute for a specified timestamp.
	 * 
	 * @param timestamp
	 *            timestamp to change
	 * 
	 * @return timestamp with time changed to 23:59:00.000
	 */
	public Date lastMinute(Date timestamp);

	/**
	 * Parses a String of format "dd/MM/yyyy HH:mm:ss.SSS" to a date
	 * 
	 * @param f
	 *            string to parse
	 * 
	 * @return Date
	 */
	public Date parseJsonDateTime(String f);

	/**
	 * Formats a Date using "dd/MM/yyyy HH:mm:sss.SSS"
	 * 
	 * @param f
	 *            Date to format
	 * 
	 * @return String
	 */
	public String formateJsonDateTime(Date f);

	/**
	 * Formats a Date based on the E_DateFormat given.
	 * 
	 * @param date
	 *            Date to format
	 * @param dateFormat
	 *            the E_DateFormat enum
	 * 
	 * @return string format
	 */
	public String format(Date date, E_DateFormat dateFormat);

	/**
	 * Refreshs the date formats configured for the date utils.
	 */
	public void refreshFormats();

	/**
	 * Calculates days between to dates (zeroes hours)
	 * 
	 * @param from
	 *            date from
	 * @param to
	 *            date to
	 * @param includeEnd
	 *            true to include counting the end date
	 * 
	 * @return long with the total days between dates
	 */
	public Long daysBetween(Date from, Date to, Boolean includeEnd);

}
