package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccOutGua {
	private AccOutGuaPK id;
	
	private Accident accident;
	
	// private Integer accId;
	
	private Insurer insurer;
	
	private Calendar appDate;
	
	private Double objSum;
	
	private Double mediFee;
	
	private Double other1;
	
	private Double other2;
	
	private String appDesc;
	
	private Person appPsn;
	
	private GuaReport fkGuaReport;
	
	//ÀíÅâ×Ü½ð¶î
	private Double guaSum = 0d;

	public AccOutGua() {}
	
	public AccOutGua(AccOutGuaPK id) {
		setId(id);
	}

	public AccOutGuaPK getId() {
		return AccOutGuaPK.getSafeObject(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public void setId(AccOutGuaPK id) {
		this.id = id;
	}

	/*
	public Integer getAccId() {
		return this.accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public Accident getAccident() {
		return (accId == null || getBranchId() == null) ? null : new Accident(getBranch(), accId);
	}

	public void setAccident(Accident accident) {
		this.accId = accident.getAccidentId();
	}
	*/
	
	public Integer getAccId() {
		return (accident == null) ? null : accident.getAccidentId();
	}

	public void setAccId(Integer accId) {
		this.accident = (accId == null || getBranchId() == null) ? null : new Accident(getBranch(), accId);
	}

	public Accident getAccident() {
		return Accident.getSafeObject(accident);
	}

	public void setAccident(Accident accident) {
		this.accident = accident;
	}
	
	public AccidentPK getAccidentId() {
		return (accident == null) ? null : accident.getId();
	}

	public Insurer getInsurer() {
		return Insurer.getSafeObject(insurer);
	}

	public void setInsurer(Insurer insurer) {
		this.insurer = insurer;
	}
	
	public Long getInsurerId() {
		return (insurer == null) ? null : insurer.getId();
	}

	public Calendar getAppDate() {
		return appDate;
	}

	public void setAppDate(Calendar appDate) {
		this.appDate = appDate;
	}

	public Double getObjSum() {
		return objSum;
	}

	public void setObjSum(Double objSum) {
		this.objSum = objSum;
	}

	public Double getMediFee() {
		return mediFee;
	}

	public void setMediFee(Double mediFee) {
		this.mediFee = mediFee;
	}

	public Double getOther1() {
		return other1;
	}

	public void setOther1(Double other1) {
		this.other1 = other1;
	}

	public Double getOther2() {
		return other2;
	}

	public void setOther2(Double other2) {
		this.other2 = other2;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Person getAppPsn() {
		return Person.getSafeObject(appPsn);
	}

	public void setAppPsn(Person appPsn) {
		this.appPsn = appPsn;
	}
	
	public Integer getAppPsnId() {
		return (appPsn == null) ? null : appPsn.getId();
	}

	public GuaReport getFkGuaReport() {
		return GuaReport.getSafeObject(fkGuaReport);
	}

	public void setFkGuaReport(GuaReport fkGuaReport) {
		this.fkGuaReport = fkGuaReport;
	}
	
	public GuaReportPK getFkGuaReportId() {
		return (fkGuaReport == null) ? null : fkGuaReport.getId();
	}

	public Double getGuaSum() {
		return guaSum;
	}

	public void setGuaSum(Double guaSum) {
		this.guaSum = guaSum;
	}
	
	public boolean equals(Object obj) {
		AccOutGua po = (obj instanceof AccOutGua) ? (AccOutGua) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getAccidentId(), po.getAccidentId())
				&& CommonUtil.equals(getAppPsnId(), po.getAppPsnId())
				&& CommonUtil.equals(getInsurerId(), po.getInsurerId())
				&& CommonUtil.equals(getFkGuaReportId(), po.getFkGuaReportId());
	}
	
	public static AccOutGua getSafeObject(AccOutGua id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutGua) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutGua(id.getId());
		}
	}
	
}
