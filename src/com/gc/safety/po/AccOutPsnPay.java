package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccOutPsnPay {
	private AccOutPsnPayPK id;
	
	private Person payPsn;
	
	private Double mediFee;
	
	private Double other1;
	
	private Double other2;
	
	private String payDesc;
	
	private AccOutPsn fkAccOutPsn;

	public AccOutPsnPay() {}
	
	public AccOutPsnPay(AccOutPsnPayPK id) {
		setId(id);
	}
	
	public AccOutPsnPay(Branch branch, Integer accId, Integer no, Calendar payDate) {
		setId(new AccOutPsnPayPK(branch, accId, no, payDate));
	}
	
	public AccOutPsnPayPK getId() {
		return AccOutPsnPayPK.getSafeObject(id);
	}
	
	public void setId(AccOutPsnPayPK id) {
		this.id = id;
	}

	public Person getPayPsn() {
		return Person.getSafeObject(payPsn);
	}

	public void setPayPsn(Person payPsn) {
		this.payPsn = payPsn;
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

	public AccOutPsn getFkAccOutPsn() {
		return AccOutPsn.getSafeObject(fkAccOutPsn);
	}

	public void setFkAccOutPsn(AccOutPsn fkAccOutPsn) {
		this.fkAccOutPsn = fkAccOutPsn;
	}
	
	
	public boolean equals (Object obj) {
		AccOutPsnPay po = obj instanceof AccOutPsnPay ? (AccOutPsnPay) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(payPsn, po.getPayPsn());
	}
	
	public AccOutPsnPay getSafeObject(AccOutPsnPay id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutPsnPay) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutPsnPay(id.getId());
		}
	}

}
