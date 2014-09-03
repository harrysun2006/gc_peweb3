package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class AccOutObj {
	private AccOutObjPK id;
	
	private AccObject obj;
	
	private String tel;
	
	private String address;
	
	private AccDuty duty;
	
	private String desc;
	
	private Double payFee;
	
	private Calendar payDate;
	
	private String payDesc;
	
	private Person payPsn;
	
	private Accident fkAccident;
	
	public AccOutObj() {}
	
	public AccOutObj(AccOutObjPK id) {
		setId(id);
	}
	
	public AccOutObj(Branch branch,Integer accId, Integer no) {
		this(new AccOutObjPK(branch,accId,no));
	}
	
	public AccOutObjPK getId() {
		return AccOutObjPK.getSafeObject(id);
	}
	
	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}
	
	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public void setId(AccOutObjPK id) {
		this.id = id;
	}

	public AccObject getObj() {
		return AccObject.getSafeObject(obj);
	}

	public void setObj(AccObject obj) {
		this.obj = obj;
	}
	
	public Long getObjId() {
		return (obj == null) ? null : obj.getId();
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AccDuty getDuty() {
		return AccDuty.getSafeObject(duty);
	}

	public void setDuty(AccDuty duty) {
		this.duty = duty;
	}
	
	public Long getDutyId() {
		return (duty == null) ? null : duty.getId();
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee) {
		this.payFee = payFee;
	}

	public Calendar getPayDate() {
		return payDate;
	}

	public void setPayDate(Calendar payDate) {
		this.payDate = payDate;
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

	public void setPayPsn(Person payPsn) {
		this.payPsn = payPsn;
	}
	
	public Integer getPayPsnId() {
		return (payPsn == null) ? null : payPsn.getId();
	}
	
	public Accident getFkAccident() {
		return Accident.getSafeObject(fkAccident);
	}

	public void setFkAccident(Accident fkAccident) {
		this.fkAccident = fkAccident;
	}
	
	public boolean equals(Object obj) {
		AccOutObj po = (obj instanceof AccOutObj) ? (AccOutObj) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getObjId(), po.getObjId())
				&& CommonUtil.equals(getDutyId(), po.getDutyId())
				&& CommonUtil.equals(getPayPsnId(), po.getPayPsnId());
	}
	
	public static AccOutObj getSafeObject(AccOutObj id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutObj) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutObj(id.getId());
		}
	}
}
