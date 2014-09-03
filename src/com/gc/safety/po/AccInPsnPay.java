package com.gc.safety.po;

import java.util.Calendar;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccInPsnPay {
	private AccInPsnPayPK id;
	
	private Person payPsn;
	
	private Double mediFee;
	
	private Double other1;
	
	private Double other2;
	
	private String payDesc;
	
	private AccInPsn fkAccInPsn;

	public AccInPsnPay() {}
	
	public AccInPsnPay(Branch branch, Integer accId, Integer no, Calendar payDate) {
		setId(new AccInPsnPayPK(branch, accId, no, payDate));
	}

	public AccInPsnPayPK getId() {
		return AccInPsnPayPK.getSafeObject(id);
	}

	public void setId(AccInPsnPayPK id) {
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
	

	public AccInPsn getFkAccInPsn() {
		return AccInPsn.getSafeObject(fkAccInPsn);
	}

	public void setFkAccInPsn(AccInPsn fkAccInPsn) {
		this.fkAccInPsn = fkAccInPsn;
	}
	
	public boolean equals(Object obj) {
		AccInPsnPay po = (obj instanceof AccInPsnPay) ? (AccInPsnPay) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(payPsn, po.getPayPsn());
	}
	
}
