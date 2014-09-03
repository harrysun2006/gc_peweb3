package com.gc.common.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class EventLogD {

	private Integer id;

	private EventLog log;

	private String strRep;

	public EventLogD() {
	}

	public EventLogD(Integer id) {
		this.id = id;
	}

	public EventLogD(EventLog log, String strRep) {
		this.log = log;
		this.strRep = strRep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLogId() {
		return (log == null) ? null : log.getId();
	}

	public EventLog getLog() {
		return EventLog.getSafeObject(log);
	}

	public void setLog(EventLog log) {
		this.log = log;
	}

	public String getStrRep() {
		return strRep;
	}

	public void setStrRep(String strRep) {
		this.strRep = strRep;
	}

	public boolean equals(Object obj) {
		EventLogD po = (obj instanceof EventLogD) ? (EventLogD) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getLogId(), po.getLogId());
	}

	public static EventLogD getSafeObject(EventLogD po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (EventLogD) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new EventLogD(po.getId());
		}
	}

}
