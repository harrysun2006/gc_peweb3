package com.gc.common.service;

import com.gc.common.dao.BaseDAOHibernate;
import com.gc.common.po.EventLog;
import com.gc.log4j.HibernateLoggingEvent;

class LogService {

	private BaseDAOHibernate logDAO;

	public void setLogDAO(BaseDAOHibernate logDAO) {
		this.logDAO = logDAO;
	}

//==================================== EventLog ====================================

	public EventLog getEventLog(Integer id) {
		return logDAO.getEventLog(id);
	}

	public void addEventLog(EventLog log) {
		logDAO.saveObject(log);
	}
	
	public void saveEvent(HibernateLoggingEvent event) { 
		logDAO.saveObject(event);
	}
}
