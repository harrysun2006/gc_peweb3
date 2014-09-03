package com.gc;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

import com.gc.util.CommonUtil;
import com.gc.util.DateUtil;
import com.gc.util.PropsUtil;

public interface Constants {

	public final static Calendar MAX_DATE = DateUtil.getMaxCalendar();

	public final static Calendar MIN_DATE = DateUtil.getMinCalendar();

	public final static Map SETTINGS = new Hashtable();

	public final static String VERSION = "1.0.110315";

	/**
	 * Property file and property names
	 */
	public final static String PROPERTIES_FILE = "gc.properties";

	/**
	 * Spring & Hibernate configuration constants
	 */
	public final static String PROP_SPRING_CONFIG_FILES = "spring2.config.files";

	public final static String PROP_SPRING_HIBERNATE_DATA_SOURCES = "spring.hibernate.data.sources";

	// public final static String PROP_SPRING_HIBERNATE_SESSION_FACTORIES = "spring.hibernate.session.factories";

	// public final static String GC_SESSION_FACTORY = "&gcSessionFactory";

	/**
	 * Common configuration constants
	 */
	public final static String PROP_DATE_FORMAT = "format.date";

	public final static String PROP_DATETIME_FORMAT = "format.datetime";

	public final static String DEFAULT_DATE_FORMAT = PropsUtil.get(PROP_DATE_FORMAT, 
			CommonUtil.getString(PROP_DATE_FORMAT, "yyyy-MM-dd"));

	public final static String DEFAULT_DATETIME_FORMAT = PropsUtil.get(PROP_DATETIME_FORMAT, 
			CommonUtil.getString(PROP_DATETIME_FORMAT, "yyyy-MM-dd HH:mm:ss"));

	public final static String PARAM_CLASS = "@class";

	public final static String PARAM_ORDER = "@order";

	public final static String PARAM_CLEAR = "@clear";

	public final static String PARAM_HEADER = "@header";

	public final static String PARAM_NO = "@no";

	public final static String PARAM_PREDECESSOR = "@pre";

	public final static String PARAM_SUCCESSOR = "@post";

	public final static String PARAM_FETCH = "@fetch";

	public final static Comparator ID_COMPARATOR = new IdentityComparator();

	public final static String CLOSE_TYPE_GETLAST = "0";

	public final static String CLOSE_TYPE_CLOSE = "1";

	public final static String CLOSE_TYPE_UNCLOSE = "9";

	public final static String CACHE_HR_LASTCLOSEDATE = "hrLastCloseDate";
	
	public final static String CACHE_SAFETY_LASTCLOSEDATE = "safetyLastCloseDate";

	/**
	 * upload file save path
	 */
	public final static String PROP_UPLOAD_PATH = "upload.path";

	public final static String DEFAULT_UPLOAD_PATH = PropsUtil.get(PROP_UPLOAD_PATH);

	public final static String PROP_TEMPLATE_PATH = "template.path";

	public final static String DEFAULT_TEMPLATE_PATH = "template";

	/** 车辆使用性质,区分是否营运车辆 */
	public final static String DEFAULT_EQU_NATURE = "公务";

}
