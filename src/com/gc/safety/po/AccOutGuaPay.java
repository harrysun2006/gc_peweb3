package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccOutGuaPay {
	private AccOutGuaPayPK id;
	
	private Calendar payDate;
	
	private Double objSum;
	
	private Double mediFee;
	
	private Double other1;
	
	private Double other2;
	
	private String payDesc;
	
	private Person payPsn;
	
	private String appRefNo;
	
	private String appNo;
	
	private AccOutGua fkAccOutGua;
	
	//Åâ¸¶×Ü½ð¶î
//	private Double paySum;

	public AccOutGuaPay() {}
	
	public AccOutGuaPay(AccOutGuaPayPK id) {
		setId(id);
	}

	public AccOutGuaPayPK getId() {
		return AccOutGuaPayPK.getSafeObject(id);
	}

	public void setId(AccOutGuaPayPK id) {
		this.id = id;
	}

	public Calendar getPayDate() {
		return payDate;
	}

	public void setPayDate(Calendar payDate) {
		this.payDate = payDate;
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

	public AccOutGua getFkAccOutGua() {
		return AccOutGua.getSafeObject(fkAccOutGua);
	}

	public AccOutGuaPK getFkAccOutGuaId() {
		return (fkAccOutGua == null) ? null : fkAccOutGua.getId();
	}
	
	public void setFkAccOutGua(AccOutGua fkAccOutGua) {
		this.fkAccOutGua = fkAccOutGua;
	}
	
//	public Double getPaySum() {
//		return paySum;
//	}
//
//	public void setPaySum(Double paySum) {
//		this.paySum = paySum;
//	}
	
	public boolean equals(Object obj) {
		AccOutGuaPay po = (obj instanceof AccOutGuaPay) ? (AccOutGuaPay) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getPayPsnId(), po.getPayPsnId())
				&& CommonUtil.equals(getFkAccOutGuaId(), po.getFkAccOutGuaId());
	}
	
	public static AccOutGuaPay getSafeObject(AccOutGuaPay id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutGuaPay) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutGuaPay(id.getId());
		}
	}
}
