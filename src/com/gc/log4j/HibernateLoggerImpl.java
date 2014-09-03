package com.gc.log4j;

import com.gc.common.service.LogServiceUtil;

public class HibernateLoggerImpl implements HibernateLogger {

	public void saveEvent(HibernateLoggingEvent event) {
		LogServiceUtil.saveEvent(event);
	}
}
