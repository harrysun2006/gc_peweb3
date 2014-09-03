package com.gc.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.exception.CommonRuntimeException;

public class CommonUtil {

	public static final DateFormatSymbols NUMBER_FORMAT_SYMBOLS = new DateFormatSymbols();

	private static final String[] WEEKDAYS = { "6", "7", "1", "2", "3", "4", "5", "6" };
	
	private static final String RESOURCE_BUNDLE = "langs/gcs";

	static {
		NUMBER_FORMAT_SYMBOLS.setWeekdays(WEEKDAYS);
		NUMBER_FORMAT_SYMBOLS.setShortWeekdays(WEEKDAYS);
	}

	private CommonUtil() {}

	public static Date getDate(String format, String time) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		return df.parse(time);
	}

	public static String leftPadding(String pattern, int i) {
		return leftPadding(pattern, String.valueOf(i));
	}

	public static String leftPadding(String pattern, Object obj) {
		return (obj == null) ? leftPadding(pattern, "") : leftPadding(pattern, obj
				.toString());
	}

	public static String leftPadding(String pattern, String text) {
		return padding(pattern, text, true);
	}

	public static String rightPadding(String pattern, int i) {
		return rightPadding(pattern, String.valueOf(i));
	}

	public static String rightPadding(String pattern, Object obj) {
		return (obj == null) ? rightPadding(pattern, "") : rightPadding(pattern,
				obj.toString());
	}

	public static String rightPadding(String pattern, String text) {
		return padding(pattern, text, false);
	}

	private static String padding(String pattern, String text, boolean left) {
		if (pattern == null)
			return text;
		if (text == null)
			text = "";
		int tl = (text == null) ? 0 : text.getBytes().length;
		int pl = (pattern == null) ? 0 : pattern.getBytes().length;
		StringBuffer sb = new StringBuffer(pl);
		if (pl > tl) {
			if (left)
				sb.append(pattern.substring(0, pl - tl)).append(text);
			else
				sb.append(text).append(pattern.substring(tl));
		} else {
			sb.append(text);
		}
		return sb.toString();
	}

	public static String formatCalendar(String format, Calendar cal) {
		return formatDate(format, (cal == null) ? (Date) null : cal.getTime(),
				NUMBER_FORMAT_SYMBOLS);
	}

	public static String formatCalendar(String format, Calendar cal,
			DateFormatSymbols symbols) {
		return formatDate(format, (cal == null) ? (Date) null : cal.getTime(),
				symbols);
	}

	public static String formatDate(String format, Date date) {
		return formatDate(format, date, NUMBER_FORMAT_SYMBOLS);
	}

	public static String formatDate(String format, Date date,
			DateFormatSymbols symbols) {
		if (format == null || date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		if (symbols != null)
			df.setDateFormatSymbols(symbols);
		return df.format(date);
	}

	public static String formatNumber(String format, int i) {
		return formatNumber(format, new Integer(i));
	}

	public static String formatNumber(String format, long l) {
		return formatNumber(format, new Long(l));
	}

	public static String formatNumber(String format, double d) {
		return formatNumber(format, new Double(d));
	}

	public static String formatNumber(String format, Number number) {
		if (format == null || number == null)
			return null;
		NumberFormat nf = new DecimalFormat(format);
		return nf.format(number);
	}

	public static String repeat(CharSequence s, int count) {
		if (s == null || s.length() == 0)
			return s.toString();
		StringBuffer sb = new StringBuffer(count * s.length());
		for (int i = 0; i < count; i++)
			sb.append(s);
		return sb.toString();
	}

	public static String repeat(char c, int count) {
		StringBuffer sb = new StringBuffer(count);
		for (int i = 0; i < count; i++)
			sb.append(c);
		return sb.toString();
	}

	public static int getInt(Object value) {
		return getInt(value, Number.class);
	}

	public static int getInt(Object value, Class clazz) {
		if (value == null) {
			return 0;
		} else if (clazz != null && Number.class.isAssignableFrom(clazz)) {
			Number n = (Number) value;
			return n.intValue();
		} else {
			return getInt(value.toString());
		}
	}

	public static int getInt(String value) {
		return Integer.parseInt(value);
	}

	public static int getInt(String value, int defaultValue) {
		if (StringUtil.isBlank(value))
			return defaultValue;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static Integer getInteger(String value) {
		return new Integer(getInt(value));
	}

	public static Integer getInteger(String value, int defaultValue) {
		return new Integer(getInt(value, defaultValue));
	}

	public static double getDouble(String value) {
		return Double.parseDouble(value);
	}

	public static double getDouble(String value, double defaultValue) {
		if (StringUtil.isBlank(value))
			return defaultValue;
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static boolean getBoolean(Object value) {
		return getBoolean(value, Boolean.class);
	}

	public static boolean getBoolean(Object value, Class clazz) {
		if (value == null) {
			return false;
		} else if (clazz != null && Boolean.class.isAssignableFrom(clazz)) {
			Boolean b = (Boolean) value;
			return b.booleanValue();
		} else {
			return getBoolean(value.toString());
		}
	}

	public static boolean getBoolean(String value) {
		return (value != null && (value.equalsIgnoreCase("true")
				|| value.equalsIgnoreCase("T") || value.equalsIgnoreCase("Y") || value
				.equalsIgnoreCase("1")));
	}

	public static Long bytes2Long(byte[] b) {
		if (b == null || b.length <= 0)
			return null;
		long l = 0;
		for (int i = 0; i < 4; i++) {
			l = (l << 8) & (long) b[i];
		}
		return new Long(l);
	}

	public static Calendar bytes2Cal(byte[] b) {
		// HHmmssMdyyyy? or HHmmssMMddyy?
		return Calendar.getInstance();
	}

	/**
	 * get filtered properties from given properties
	 * 
	 * @param props
	 * @param fregex
	 *          used to filter keys
	 * @param kregex
	 *          get the renamed keys
	 * @return
	 */
	public static Properties getProperties(Properties props, String fregex,
			String kregex) {
		if (props == null)
			return null;
		Pattern fp = (fregex == null) ? null : Pattern.compile(fregex);
		Pattern kp = (kregex == null) ? null : Pattern.compile(kregex);
		Matcher fm, km;
		Properties r = new Properties();
		Set keys = props.keySet();
		Iterator it = keys.iterator();
		String key, value;
		while (it.hasNext()) {
			key = (String) it.next();
			value = props.getProperty(key);
			if (fp != null) {
				fm = fp.matcher(key);
				// filter keys using the fregex
				if (fm.matches()) {
					// rename keys using the kregex
					if (kp != null) {
						km = kp.matcher(key);
						if (km.find()) {
							if (km.groupCount() > 0)
								key = km.group(1);
							else
								key = km.group(0);
						}
					}
					r.put(key, value);
				}
			}
		}
		return r;
	}

	/**
	 * get text from resource bundle by code
	 * 
	 * @param code
	 * @return
	 */
	public static String getString(String code) {
		return getString(code, code);
	}

	public static String getString(String code, String defaultValue) {
		String r;
		try {
			ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_BUNDLE);
			r = rb.getString(code);
		} catch (Exception e) {
			r = defaultValue;
		}
		return r;
	}

	/**
	 * get text from resource bundle by code
	 * 
	 * @param code
	 * @param params
	 * @return
	 */
	public static String getString(String code, Object[] params) {
		String r;
		try {
			ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_BUNDLE);
			r = MessageFormat.format(rb.getString(code), params);
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer();
			sb.append(code).append("[");
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					sb.append(params[i]).append(", ");
				}
				if (params.length > 0)
					sb.delete(sb.length() - 2, sb.length());
			}
			sb.append("]");
			r = sb.toString();
		}
		return r;
	}

	public static void dumpMap(Map map, PrintStream ps) throws Exception {
		Object key;
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			key = it.next();
			ps.println(key + " = " + map.get(key));
		}
	}

	public static Throwable getRootCause(Throwable t) {
		Throwable r = t;
		while (r.getCause() != null)
			r = r.getCause();
		return r;
	}
	
	public static byte[] readFile(File f) throws IOException {
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
    ByteArrayOutputStream out = new ByteArrayOutputStream(102400);
    byte[] b = new byte[1024];
    int size = 0;
    while ((size = in.read(b)) != -1) {
    	out.write(b, 0, size);
    }
    in.close();
    return out.toByteArray();
	}

	/**
	 * 比较2个对象是否相等(比较对象中除Collection/Map/非序列化属性之外的所有属性) 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equals(Object o1, Object o2) {
		if ((o1 == null && o2 == null) || o1 == o2) return true;
		// Oracle数据库保存空字符串为null
		if ((o1 == null && "".equals(o2)) || (o2 == null && "".equals(o1))) return true;
		if (o1 == null || o2 == null) return false;
		if (o1.getClass() != o2.getClass()) return false;
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			Object[] o1s = (Object[]) o1;
			Object[] o2s = (Object[]) o2;
			if (o1s.length != o2s.length) return false;
			for (int i = 0; i < o1s.length; i++) {
				if (equals(o1s[i], o2s[i])) continue;
				else return false;
			}
		} else {
			Class c = o1.getClass();
			if (Number.class.isAssignableFrom(c) || Comparable.class.isAssignableFrom(c)) return o1.equals(o2);
			Field[] fds = c.getDeclaredFields();
			Object v1, v2;
			for (int i = 0; i < fds.length; i++) {
				try {
					fds[i].setAccessible(true);
					v1 = fds[i].get(o1);
					v2 = fds[i].get(o2);
					if (v1 == null && v2 == null) continue;
					// Oracle数据库保存空字符串为null
					if ((v1 == null && "".equals(v2)) || (v2 == null && "".equals(v1))) continue;
					if (v1 == null || v2 == null) return false;
					if (v1 instanceof Collection || v2 instanceof Map) continue;
					if (!(v1 instanceof Serializable) || !(v2 instanceof Serializable)) continue;
					if (!Hibernate.isInitialized(v1) || v1 instanceof HibernateProxy || !Hibernate.isInitialized(v2) || v2 instanceof HibernateProxy) continue;
					if (!v1.equals(v2)) return false;
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 返回对象的属性值, 支持多级属性, 如:id.branch.id
	 * @param obj
	 * @param name
	 * @return
	 * @throws CommonRuntimeException
	 */
	public static Object getValue(Object obj, String name) throws CommonRuntimeException {
		try {
			String[] names = name.split("\\.");
			Field f;
			for (int i = 0; i < names.length; i++) {
				f = obj.getClass().getDeclaredField(names[i]);
				f.setAccessible(true);
				obj = f.get(obj);
				if (obj == null) return null;
			}
			return obj;
		} catch (Throwable t) {
			throw new CommonRuntimeException(t.getMessage(), t);
		}
	}

	/**
	 * 设置对象的属性值, 支持多级属性, 如:id.branch.id
	 * @param obj
	 * @param name
	 * @param value
	 * @throws CommonRuntimeException
	 */
	public static void setValue(Object obj, String name, Object value) throws CommonRuntimeException {
		try {
			String[] names = name.split("\\.");
			Field f;
			Object v;
			for (int i = 0; i < names.length; i++) {
				f = obj.getClass().getDeclaredField(names[i]);
				f.setAccessible(true);
				if (i < names.length - 1) {
					v = f.get(obj);
					if (v == null) {
						v = f.getType().newInstance();
						f.set(obj, v);
					}
					obj = v;
				}
				else f.set(obj, value);
			}
		} catch (Throwable t) {
			throw new CommonRuntimeException(t.getMessage(), t);
		}
	}

	public static String base64Encode(byte[] b) {
		return new sun.misc.BASE64Encoder().encode(b);
	}

	public static byte[] base64Decode(String s) throws IOException {
		return new sun.misc.BASE64Decoder().decodeBuffer(s);  
	}
}
