package com.gc.common.service;

import org.springframework.context.ApplicationContext;

import com.gc.common.po.EventLog;
import com.gc.log4j.HibernateLoggingEvent;
import com.gc.util.SpringUtil;

public class LogServiceUtil {

	public static final String BEAN_NAME = "commonLogServiceUtil";

	private LogService logService;

	public static LogService getLogService() {
		ApplicationContext ctx = SpringUtil.getContext();
		LogServiceUtil util = (LogServiceUtil) ctx.getBean(BEAN_NAME);
		LogService service = util.logService;
		return service;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

//==================================== EventLog ====================================

	public static EventLog getEventLog(Integer id) {
		return getLogService().getEventLog(id);
	}

	public static void addEventLog(EventLog log) {
		getLogService().addEventLog(log);
	}

	public static void saveEvent(HibernateLoggingEvent event) {
		getLogService().saveEvent(event);
	}
}
