package com.gc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gc.Constants;

/**
 * 
 * @date Apr 23, 2007
 */
public class PropsUtil {

	public final static String COMMA = ",";

	private static Properties props = null;

	private final static Log _log = LogFactory.getLog(PropsUtil.class);

	static {
		// load2(Constants.PROPERTIES_FILE + ".ser");
		if (props == null)
			load1(Constants.PROPERTIES_FILE);
	}

	public static boolean containsKey(String key) {
		return props.containsKey(key);
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static String[] getArray(String key) {
		String value = get(key);
		return StringUtil.isBlank(value) ? null : value.split(COMMA);
	}

	public static int getInt(String key) {
		return getInt(key, 0);
	}

	public static int getInt(String key, int defaultValue) {
		if (StringUtil.isBlank(get(key))) {
			return defaultValue;
		}
		return Integer.parseInt(get(key));
	}

	public static long getLong(String key) {
		return getLong(key, 0L);
	}

	public static long getLong(String key, long defaultValue) {
		if (StringUtil.isBlank(get(key))) {
			return defaultValue;
		}
		return Long.parseLong(get(key));
	}

	public static double getDouble(String key) {
		return getDouble(key, 0);
	}

	public static double getDouble(String key, int defaultValue) {
		if (StringUtil.isBlank(get(key))) {
			return defaultValue;
		}
		return Double.parseDouble(get(key));
	}

	public static boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = get(key);
		if (StringUtil.isBlank(value)) {
			return defaultValue;
		}
		value = value.trim();
		if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")
				|| value.equalsIgnoreCase("T") || value.equalsIgnoreCase("Y")
				|| Boolean.getBoolean(value))
			return true;
		else
			return false;
	}

	public static void set(String key, String value) {
		props.setProperty(key, value);
	}

	public static void remove(String key) {
		props.remove(key);
	}

	public static void clear() {
		props.clear();
	}

	public static Properties getProperties() {
		return props;
	}

	protected static void load1(String file) {
		props = new Properties();
		InputStream is = null;
		try {
			is = PropsUtil.class.getClassLoader().getResourceAsStream(file);
			if (is == null)
				is = new FileInputStream(file);
			props.load(is);
			if (_log.isInfoEnabled()) {
				_log.info("load properties file successfully: " + file);
			}
		} catch (IOException e) {
			if (_log.isErrorEnabled()) {
				_log.error("load properties file failed: " + file, e);
			}
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception e1) {
				}
		}
	}

	protected static void load2(String file) {
		InputStream ser = null;
		ObjectInputStream is = null;
		try {
			// ser = PropsUtil.class.getClassLoader().getResourceAsStream(file);
			ser = new FileInputStream(file);
			is = new ObjectInputStream(ser);
			props = (Properties) is.readObject();
			if (_log.isInfoEnabled()) {
				_log.info("load properties file successfully: " + file);
			}
		} catch (Exception e) {
			if (_log.isErrorEnabled()) {
				_log.error("load properties file failed: " + file, e);
			}
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception e1) {
				}
			if (ser != null)
				try {
					ser.close();
				} catch (Exception e2) {
				}
		}
	}

}