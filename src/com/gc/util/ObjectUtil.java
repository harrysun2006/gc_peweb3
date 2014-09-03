package com.gc.util;

import java.util.Calendar;
import java.util.Date;

public class ObjectUtil {

	private ObjectUtil() {}

	public static Calendar toCalendar(Object value) {
		Calendar r = null;
		if (value instanceof Calendar) r = (Calendar) value;
		else if (value instanceof Date) {
			r = Calendar.getInstance();
			r.setTime((Date) value);
		}
		return r;
	}

	public static Date toDate(Object value) {
		Date r = null;
		if (value instanceof Calendar) r = ((Calendar) value).getTime();
		else if (value instanceof Date) r = (Date) value;
		return r;
	}

	public static java.sql.Date toSQLDate(Object value) {
		Date r = null;
		if (value instanceof Calendar) r = ((Calendar) value).getTime();
		else if (value instanceof Date) r = (Date) value;
		return (r == null || r.getTime() == 0) ? null : new java.sql.Date(r.getTime());
	}

	public static Double toDouble(Object value) {
		if (value instanceof Number) return ((Number) value).doubleValue();
		else return null;
	}

	public static Integer toInt(Object value) {
		if (value instanceof Number) return ((Number) value).intValue();
		else return null;
	}

	public static Long toLong(Object value) {
		if (value instanceof Number) return ((Number) value).longValue();
		else return null;
	}

	public static String toString(Object value) {
		return (value == null) ? "" : String.valueOf(value);
	}
}
