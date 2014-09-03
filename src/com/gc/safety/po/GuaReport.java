package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class GuaReport {
	private GuaReportPK id;
	
	private String reportNo;
	
	private String closeNo;
	
	public GuaReport() {}
	
	public GuaReport(GuaReportPK id) {
		setId(id);
	}

	public GuaReportPK getId() {
		return GuaReportPK.getSafeObject(id);
	}

	public void setId(GuaReportPK id) {
		this.id = id;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getCloseNo() {
		return closeNo;
	}

	public void setCloseNo(String closeNo) {
		this.closeNo = closeNo;
	}
	
	public boolean equals(Object obj) {
		GuaReport po = (obj instanceof GuaReport) ? (GuaReport) obj : null;
		return CommonUtil.equals(this, po);
	}
	
	public static GuaReport getSafeObject(GuaReport id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (GuaReport) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new GuaReport(id.getId());
		}
	}
	
}
