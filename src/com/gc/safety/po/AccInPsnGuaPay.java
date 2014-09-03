package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccInPsnGuaPay {
	private AccInPsnGuaPayPK id;
	
	private Calendar payDate;
	
	private Double mediFee;
	
	private Double other1;
	
	private Double other2;
	
	private String payDesc;
	
	private Person payPsn;
	
	private String appRefNo;
	
	private String appNo;
	
	private AccInPsnGua fkAccInPsnGua;
	
	// Åâ¸¶×Ü½ð¶î
//	private Double paySum;

	public AccInPsnGuaPay() {}
	
	public AccInPsnGuaPay(AccInPsnGuaPayPK id) {
		setId(id);
	}

	public AccInPsnGuaPayPK getId() {
		return AccInPsnGuaPayPK.getSafeObject(id);
	}

	public void setId(AccInPsnGuaPayPK id) {
		this.id = id;
	}

	public Calendar getPayDate() {
		return payDate;
	}

	public void setPayDate(Calendar payDate) {
		this.payDate = payDate;
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

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public Person getPayPsn() {
		return Person.getSafeObject(payPsn);
	}
	
	public Integer getPayPsnId() {
		return (payPsn == null) ? null : payPsn.getId();
	}

	public void setPayPsn(Person payPsn) {
		this.payPsn = payPsn;
	}

	public String getAppRefNo() {
		return appRefNo;
	}

	public void setAppRefNo(String appRefNo) {
		this.appRefNo = appRefNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public AccInPsnGua getFkAccInPsnGua() {
		return AccInPsnGua.getSafeObject(fkAccInPsnGua);
	}
	
	public AccInPsnGuaPK getFkAccInPsnGuaPK() {
		return (fkAccInPsnGua == null) ? null : fkAccInPsnGua.getId();
	}

	public void setFkAccInPsnGua(AccInPsnGua fkAccInPsnGua) {
		this.fkAccInPsnGua = fkAccInPsnGua;
	}

//	public Double getPaySum() {
//		return paySum;
//	}
//
//	public void setPaySum(Double paySum) {
//		this.paySum = paySum;
//	}
	
	public boolean equals(Object obj) {
		AccInPsnGuaPay po = (obj instanceof AccInPsnGuaPay) ? (AccInPsnGuaPay) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(payPsn.getId(), po.getPayPsnId())
				&& CommonUtil.equals(getFkAccInPsnGuaPK(), po.getFkAccInPsnGuaPK());
	}
	
	public static AccInPsnGuaPay getSafeObject(AccInPsnGuaPay id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccInPsnGuaPay) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccInPsnGuaPay(id.getId());
		}
	}
}
