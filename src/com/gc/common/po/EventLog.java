package com.gc.common.po;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.log4j.HibernateLoggingEvent;
import com.gc.util.CommonUtil;

public class EventLog implements HibernateLoggingEvent {

	private Integer id;

	private Branch branch;

	private Person person;

	private String type;

	private String table;

	private String key;

	private String level;

	private String className;

	private String fileName;

	private String lineNumber;

	private String loggerName;

	private String methodName;

	private String threadName;

	private String message;

	private Calendar logDate;

	private Calendar startDate;

	private Set<EventLogD> strReps = new LinkedHashSet<EventLogD>();

	public EventLog() {
	}

	public EventLog(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Integer getPersonId() {
		return (person == null) ? null : person.getId();
	}

	public Person getPerson() {
		return Person.getSafeObject(person);
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Calendar getLogDate() {
		return logDate;
	}

	public void setLogDate(Calendar logDate) {
		this.logDate = logDate;
	}

	public void setLogDate(Date logDate) {
		if (this.logDate == null) this.logDate = Calendar.getInstance();
		this.logDate.setTime(logDate);
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public void setStartDate(Date startDate) {
		if (this.startDate == null) this.startDate = Calendar.getInstance();
		this.startDate.setTime(startDate);
	}

	public Set<EventLogD> getStrReps() {
		return strReps;
	}

	public void addStrRep(String strRep) {
		this.strReps.add(new EventLogD(this, strRep));
	}

	public void setStrReps(Set<EventLogD> strReps) {
		this.strReps = strReps;
	}

	public void addThrowableMessage(int position, String throwableMessage) {
		addStrRep(throwableMessage);
	}

	public boolean equals(Object obj) {
		EventLog po = (obj instanceof EventLog) ? (EventLog) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EventLog{id=").append(id)
			.append(", class=").append(className)
			.append(", message=").append(message).append("}");
		return sb.toString();
	}

	public static EventLog getSafeObject(EventLog po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (EventLog) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new EventLog(po.getId());
		}
	}

}
