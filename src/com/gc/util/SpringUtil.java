package com.gc.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Environment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.SpringVersion;

import com.gc.Constants;

/**
 * 程序的入口
 * @author hsun
 *
 */
public class SpringUtil {

	private final static Log _log = LogFactory.getLog(SpringUtil.class);

	private static String[] configs = PropsUtil.getArray(Constants.PROP_SPRING_CONFIG_FILES);

	private static Map _beans = new Hashtable();

	// private static ApplicationContext _ctx = new LazyClassPathApplicationContext(configs);
	private static ApplicationContext _ctx = null;

	static {
		// TimeZone UTC = TimeZone.getTimeZone("UTC");
		// TimeZone.setDefault(UTC);
		_log.info("TimeZone is using " + TimeZone.getDefault().getID());
		_log.info("Hibernate is using " + Environment.VERSION + "!");
		_log.info("Spring is using " + SpringVersion.getVersion() + "!");
		StringBuilder sb = new StringBuilder("System enviroment variables:\n");
		Map<String, String> env = System.getenv();
		String key;
		for (Iterator<String> it = env.keySet().iterator(); it.hasNext(); ) {
			key = it.next();
			sb.append(key).append(" = ").append(env.get(key)).append("\n");
		}
		Properties props = System.getProperties();
		sb.append("System property values:\n");
		for (Iterator<Object> it = props.keySet().iterator(); it.hasNext(); ) {
			key = (String) it.next();
			sb.append(key).append(" = ").append(props.get(key)).append("\n");
		}
		// _log.info(sb.toString());
		// System.out.println("测试中文信息!!");
		// _log.info("测试中文信息!!");
		loadSettings();
	}

	private static void loadSettings() {
		Constants.SETTINGS.put("VERSION", Constants.VERSION);
	}

	private static void initContext() {
		try {
			for (int i = 0; i < configs.length; i++) configs[i] = configs[i].trim();
			_ctx = new ClassPathXmlApplicationContext(configs);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void setContext(ApplicationContext ctx) {
		_ctx = ctx;
	}

	public static ApplicationContext getContext() {
		if (_ctx == null) initContext();
		return _ctx;
	}

	public static void refresh() {
		if (_ctx != null && _ctx instanceof ConfigurableApplicationContext) ((ConfigurableApplicationContext) _ctx).refresh();
		else initContext();
	}

	public static Object getBean(String name) {
		Object obj;
		if (_beans.containsKey(name)) {
			return _beans.get(name);
		} else {
			obj = getContext().getBean(name);
			if (obj != null)
				_beans.put(name, obj);
		}
		return obj;
	}

}
