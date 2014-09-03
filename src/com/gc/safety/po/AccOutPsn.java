package com.gc.safety.po;

import java.util.LinkedHashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccOutPsn {
	private AccOutPsnPK id;
	
	private String name;
	
	private String sex;
	
	private Integer age;
	
	private String regAddress;
	
	private String address;
	
	private String tel;
	
	private AccDuty duty;
	
	private String desc;
	
	private Integer status;
	
	private Set<AccOutPsnPay> accOutPsnPays = new LinkedHashSet<AccOutPsnPay>(0);
	
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


	public AccOutPsn() {}
	
	public AccOutPsn(Branch branch,Integer accId, Integer no) {
		this(new AccOutPsnPK(branch,accId,no));
	}
	
	public AccOutPsn(AccOutPsnPK id) {
		setId(id);
	}

	public AccOutPsnPK getId() {
		return AccOutPsnPK.getSafeObject(id);
	}
	
	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}
	
	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public void setId(AccOutPsnPK id) {
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
	
	public Set<AccOutPsnPay> getAccOutPsnPays() {
		return accOutPsnPays;
	}

	public void setAccOutPsnPays(Set<AccOutPsnPay> accOutPsnPays) {
		this.accOutPsnPays = accOutPsnPays;
	}
	
	public Accident getFkAccident() {
		return Accident.getSafeObject(fkAccident);
	}

	public void setFkAccident(Accident fkAccident) {
		this.fkAccident = fkAccident;
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

	public boolean equals(Object obj) {
		AccOutPsn po = (obj instanceof AccOutPsn) ? (AccOutPsn) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getDutyId(), po.getDutyId());
//				&& CommonUtil.equals(getPayPsnId(), po.getPayPsnId())
	}
	
	public static AccOutPsn getSafeObject(AccOutPsn id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutPsn) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutPsn(id.getId());
		}
	}
	
}
