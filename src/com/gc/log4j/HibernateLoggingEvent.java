package com.gc.log4j;

import java.util.Calendar;

/**
 * Interface for logging events that are to be persisted using the
 * {@link HibernateAppenderEx}.
 * 
 * @author David Howe
 * 
 * @version 1.0
 * 
 */
public interface HibernateLoggingEvent {

	public Integer getId();

	public void setId(Integer id);

	public String getMessage();

	public String getClassName();

	public String getFileName();

	public String getLineNumber();

	public Calendar getLogDate();

	public String getLoggerName();

	public String getMethodName();

	public Calendar getStartDate();

	public String getThreadName();

	public String getLevel();

	public void setMessage(String message);

	public void setClassName(String className);

	public void setFileName(String fileName);

	public void setLineNumber(String lineNumber);

	public void setLogDate(Calendar logDate);

	public void setLoggerName(String loggerName);

	public void setMethodName(String methodName);

	public void setStartDate(Calendar startDate);

	public void setThreadName(String threadName);

	public void setLevel(String level);

	public void addThrowableMessage(int position, String throwableMessage);

}