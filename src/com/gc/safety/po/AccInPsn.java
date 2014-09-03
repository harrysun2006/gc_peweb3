package com.gc.safety.po;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccInPsn {
	private AccInPsnPK id;
	
	private String name;
	
	private String sex;
	
	private Integer age;
	
	private String regAddress;
	
	private String address;
	
	private String tel;
	
	private AccDuty duty;
	
	private String desc;
	
	private Integer status;
	
	private Set<AccInPsnPay> accInPsnPays = new LinkedHashSet<AccInPsnPay>(0);
	
	private Accident fkAccident;
	
//	private Double mediFee;
//	
//	private Double other1;
//	
//	private Double other2;
//	
//	private String payDesc;
//	
//	private Calendar payDate;
//	
//	private Person payPsn;
//	
//	private String statusStr;

	public AccInPsn() {}
	
	public AccInPsn(AccInPsnPK id) {
		setId(id);
	}
	
	public AccInPsn(Branch branch, Integer accId, Integer no) {
		setId(new AccInPsnPK(branch, accId, no));
	}

	public AccInPsnPK getId() {
		return AccInPsnPK.getSafeObject(id);
	}
	
	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}
	
	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public void setId(AccInPsnPK id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public AccDuty getDuty() {
		return AccDuty.getSafeObject(duty);
	}
	
	public Long getDutyId() {
		return (duty == null) ? null : duty.getId();
	}

	public void setDuty(AccDuty duty) {
		this.duty = duty;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

//	public Double getMediFee() {
//		return mediFee;
//	}
//
//	public void setMediFee(Double mediFee) {
//		this.mediFee = mediFee;
//	}
//
//	public Double getOther1() {
//		return other1;
//	}
//
//	public void setOther1(Double other1) {
//		this.other1 = other1;
//	}
//
//	public Double getOther2() {
//		return other2;
//	}
//
//	public void setOther2(Double other2) {
//		this.other2 = other2;
//	}
//
//	public String getPayDesc() {
//		return payDesc;
//	}
//
//	public void setPayDesc(String payDesc) {
//		this.payDesc = payDesc;
//	}
//
//	public Calendar getPayDate() {
//		return payDate;
//	}
//
//	public void setPayDate(Calendar payDate) {
//		this.payDate = payDate;
//	}
//
//	public Person getPayPsn() {
//		return Person.getSafeObject(payPsn);
//	}
//	
//	public Integer getPayPsnId() {
//		return (payPsn == null) ? null : payPsn.getId();
//	}
//
//	public void setPayPsn(Person payPsn) {
//		this.payPsn = payPsn;
//	}
//	
//	public String getStatusStr() {
//		return statusStr;
//	}
//
//	public void setStatusStr(String statusStr) {
//		this.statusStr = statusStr;
//	}

	public Set<AccInPsnPay> getAccInPsnPays() {
		return accInPsnPays;
	}

	public void setAccInPsnPays(Set<AccInPsnPay> accInPsnPays) {
		this.accInPsnPays = accInPsnPays;
	}

	public Accident getFkAccident() {
		return Accident.getSafeObject(fkAccident);
	}

	public void setFkAccident(Accident fkAccident) {
		this.fkAccident = fkAccident;
	}
	
	public boolean equals(Object obj) {
		AccInPsn po = (obj instanceof AccInPsn) ? (AccInPsn) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getDutyId(), po.getDutyId());
//				&& CommonUtil.equals(getPayPsnId(), po.getPayPsnId());
	}
	
	public static AccInPsn getSafeObject(AccInPsn id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccInPsn) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccInPsn(id.getId());
		}
	}
}
