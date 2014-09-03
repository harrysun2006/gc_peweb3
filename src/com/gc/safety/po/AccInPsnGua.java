package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccInPsnGua {
	private AccInPsnGuaPK id;
	
	private Accident accident;
	
	private Insurer insurer;

	private Calendar appDate;
	
	private Double mediFee;
	
	private Double other1;
	
	private Double other2;
	
	private String appDesc;
	
	private Person appPsn;
	
	private GuaReport fkGuaReport;
	
	private AccInPsn fkAccInPsn;
	
	//客伤理赔信息显示
	private String cstmName;
	
//	private Double guaSum;

	public AccInPsnGua() {}
	
	public AccInPsnGua(AccInPsnGuaPK id) {
		setId(id);
	}

	public AccInPsnGuaPK getId() {
		return AccInPsnGuaPK.getSafeObject(id);
	}
	
	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}
	
	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public void setId(AccInPsnGuaPK id) {
		this.id = id;
	}
	
	public Integer getAccId() {
		return (accident == null) ? null : accident.getAccidentId();
	}
	
	public void setAccId(Integer accId) {
		this.accident = (accId == null || getBranchId() == null) ? null : new Accident(getBranch(), accId); 
	}

	public Accident getAccident() {
		return Accident.getSafeObject(accident);
	}
	
	public AccidentPK getAccidentId() {
		return (accident == null) ? null : accident.getId();
	}

	public void setAccident(Accident accident) {
		this.accident = accident;
	}

	public Insurer getInsurer() {
		return Insurer.getSafeObject(insurer);
	}
	
	public Long getInsurerId() {
		return (insurer == null) ? null : insurer.getId();
	}

	public void setInsurer(Insurer insurer) {
		this.insurer = insurer;
	}

	public Integer getCstmNo() {
		return (fkAccInPsn == null) ? null : fkAccInPsn.getId().getNo();
	}

	public void setCstmNo(Integer cstmNo) {
		// this.fkAccInPsn = (getBranch() == null || accident == null) ? null : new AccInPsn(getBranch(), getAccId(), cstmNo);
	}

	public Calendar getAppDate() {
		return appDate;
	}

	public void setAppDate(Calendar appDate) {
		this.appDate = appDate;
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
	
	public Integer getAppPsnId() {
		return (appPsn == null) ? null : appPsn.getId();
	}

	public void setAppPsn(Person appPsn) {
		this.appPsn = appPsn;
	}

	public GuaReport getFkGuaReport() {
		return GuaReport.getSafeObject(fkGuaReport);
	}
	
	public GuaReportPK getFkGuaReportId() {
		return (fkGuaReport == null) ? null : fkGuaReport.getId();
	}

	public void setFkGuaReport(GuaReport fkGuaReport) {
		this.fkGuaReport = fkGuaReport;
	}

	public AccInPsn getFkAccInPsn() {
		return AccInPsn.getSafeObject(fkAccInPsn);
	}
	
	public AccInPsnPK getFkAccInPsnId() {
		return (fkAccInPsn == null) ? null : fkAccInPsn.getId();
	}

	public void setFkAccInPsn(AccInPsn fkAccInPsn) {
		this.fkAccInPsn = fkAccInPsn;
	}
	
	public String getCstmName() {
		return cstmName;
	}

	public void setCstmName(String cstmName) {
		this.cstmName = cstmName;
	}

//	public Double getGuaSum() {
//		return guaSum;
//	}
//
//	public void setGuaSum(Double guaSum) {
//		this.guaSum = guaSum;
//	}
	
	public boolean equals(Object obj) {
		AccInPsnGua po = (obj instanceof AccInPsnGua) ? (AccInPsnGua) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getAccidentId(), po.getAccidentId())
				&& CommonUtil.equals(getInsurerId(), po.getInsurerId())
				&& CommonUtil.equals(getAppPsnId(), po.getAppPsnId())
				&& CommonUtil.equals(getFkGuaReportId(), po.getFkGuaReportId())
				&& CommonUtil.equals(getFkAccInPsnId(), po.getFkAccInPsnId());
	}
	
	public static AccInPsnGua getSafeObject(AccInPsnGua id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccInPsnGua) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccInPsnGua(id.getId());
		}
	}

}
