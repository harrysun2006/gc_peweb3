package com.gc.log4j;

import java.util.Calendar;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

public class HibernateAppender extends AppenderSkeleton implements Appender {

	private String loggerClass;

	private HibernateLogger logger;

	private String loggingEventClass;

	private Class loggingEventClazz;

	public HibernateAppender() {
		super();
	}

	protected void append(LoggingEvent event) {
		HibernateLoggingEvent log;
		Calendar logDate, startDate;
		String[] strReps;
		try {
			if (event.getMessage() instanceof HibernateLoggingEvent) {
				log = (HibernateLoggingEvent) event.getMessage();
			} else {
				log = (HibernateLoggingEvent) loggingEventClazz.newInstance();
				if (event.getMessage() != null)
					log.setMessage(event.getMessage().toString());
			}
			if (log.getMessage() == null && event.getThrowableInformation() != null) {
				log.setMessage(event.getThrowableInformation().getThrowable().toString());
			}
			if (log.getClassName() == null)
				log.setClassName(event.getLocationInformation().getClassName());
			log.setFileName(event.getLocationInformation().getFileName());
			log.setLineNumber(event.getLocationInformation().getLineNumber());
			logDate = Calendar.getInstance();
			logDate.getTime().setTime(event.timeStamp);
			log.setLogDate(logDate);
			log.setLoggerName(event.getLoggerName());
			if (log.getMethodName() == null)
				log.setMethodName(event.getLocationInformation().getMethodName());
			startDate = Calendar.getInstance();
			startDate.getTime().setTime(LoggingEvent.getStartTime());
			log.setStartDate(startDate);
			log.setThreadName(event.getThreadName());
			strReps = event.getThrowableStrRep();
			if (strReps != null) {
				for (int i = 0; i < strReps.length; i++)
					log.addThrowableMessage(i, strReps[i]);
			}
			log.setLevel(event.getLevel().toString());
			logger.saveEvent(log);
		} catch (Exception e) {
			errorHandler.error("Exception", e, ErrorCode.GENERIC_FAILURE);
		} 
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

	public String getLoggerClass() {
		return loggerClass;
	}

	public void setLoggerClass(String name) {
		this.loggerClass = name;
		Class clazz;
		try {
			clazz = Class.forName(name);
			logger = (HibernateLogger) clazz.newInstance();
		} catch (ClassNotFoundException cnfe) {
			errorHandler.error("Invalid HibernateAppenderSessionFactory class " + name, cnfe, ErrorCode.GENERIC_FAILURE);
		} catch (InstantiationException ie) {
			errorHandler.error("Unable to instantiate class " + name, ie, ErrorCode.GENERIC_FAILURE);
		} catch (IllegalAccessException iae) {
			errorHandler.error("Unable to instantiate class " + name, iae, ErrorCode.GENERIC_FAILURE);
		}
	}

	public String getLoggingEventClass() {
		return loggingEventClass;
	}

	public void setLoggingEventClass(String name) {
		this.loggingEventClass = name;
		try {
			loggingEventClazz = Class.forName(name);
		} catch (ClassNotFoundException cnfe) {
			errorHandler.error("Invalid LoggingEvent class " + name, cnfe, ErrorCode.GENERIC_FAILURE);
		}
	}

}