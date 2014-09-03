package com.gc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

	private DateUtil() {}

	public static Calendar getBeginCal(Calendar cal) {
		return getBeginCal(cal, Calendar.MONTH);
	}

  public static Calendar getBeginCal(Calendar cal, int field) {
    field=field < Calendar.ERA ? Calendar.ERA : field > Calendar.DST_OFFSET ? Calendar.DST_OFFSET : field;
    Calendar r = Calendar.getInstance();
    r.set(Calendar.MILLISECOND, 0);
    if (field == Calendar.YEAR)
    	r.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
    else if (field == Calendar.MONTH)
    	r.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
    else if (field == Calendar.DATE)
    	r.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
    else if (field == Calendar.HOUR)
    	r.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR), 0, 0);
    else if (field == Calendar.MINUTE)
    	r.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), 0);
    else
    	r.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    return r;
  }

	public static Calendar getEndCal(Calendar cal) {
		return getEndCal(cal, Calendar.MONTH);
	}

  public static Calendar getEndCal(Calendar cal, int field) {
    field=field < Calendar.ERA ? Calendar.ERA : field > Calendar.DST_OFFSET ? Calendar.DST_OFFSET : field;
    Calendar r = Calendar.getInstance();
    r.setTime(cal.getTime());
    if (field == Calendar.YEAR)
    	r.add(Calendar.YEAR, 1);
    else if (field == Calendar.MONTH)
    	r.add(Calendar.MONTH, 1);
    else if (field == Calendar.DATE)
    	r.add(Calendar.DATE, 1);
    else if (field == Calendar.HOUR)
    	r.add(Calendar.HOUR, 1);
    else if (field == Calendar.MINUTE)
    	r.add(Calendar.MINUTE, 1);
    else
    	r.add(Calendar.SECOND, 1);
    r=getBeginCal(r, field);
    r.add(Calendar.MILLISECOND, -1);
    return r;
  }

	public static Calendar getCalendar(String format, String time)
			throws ParseException {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format);
		cal.setTime(df.parse(time));
		return cal;
	}

	public static Calendar getCalendar(int hour, int minute) {
		return getCalendar(0, hour, minute, 0);
	}

	public static Calendar getCalendar(int hour, int minute, int second) {
		return getCalendar(0, hour, minute, second);
	}

	public static Calendar getCalendar(int dayOfWeek, int hour, int minute,
			int second) {
		Calendar time = Calendar.getInstance();
		time.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		time.set(Calendar.AM_PM, Calendar.AM);
		time.set(Calendar.HOUR, hour);
		time.set(Calendar.MINUTE, minute);
		time.set(Calendar.SECOND, second);
		return time;
	}

	public static Calendar getMaxCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(9999, 11, 31, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar getMinCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(1901, 0, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

}
