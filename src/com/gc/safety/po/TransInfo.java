package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Equipment;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class TransInfo {
	private TransInfoPK id;
	
	private Calendar inputDate;
	
	private Person inputer;
	
	private Calendar transDate;
	
	private Equipment bus;
	
	private Person driver;
	
	private TransType transType;
	
	private Double upFee;

	private String code;
	
	private String point;
	
	private Double penalty;
	
	private Calendar doDate;
	
	private String confirm;

	public TransInfo () {}

	public TransInfo(TransInfoPK id) {
		setId(id);
	}

	public TransInfo(Branch branch, String accno, Integer no) {
		this(new TransInfoPK(branch, accno, no));
	}

	public TransInfoPK getId() {
		return TransInfoPK.getSafeObject(id);
	}

	public void setId(TransInfoPK id) {
		this.id = id;
	}
	
	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Calendar getInputDate() {
		return inputDate;
	}

	public void setInputDate(Calendar inputDate) {
		this.inputDate = inputDate;
	}

	public Integer getInputerId() {
		return (inputer == null) ? null : inputer.getId();
	}
	
	public Person getInputer() {
		return (Hibernate.isInitialized(inputer)) ? inputer : new Person (inputer.getId());
	}

	public void setInputer(Person inputer) {
		this.inputer = inputer;
	}

	public Calendar getTransDate() {
		return transDate;
	}

	public void setTransDate(Calendar transDate) {
		this.transDate = transDate;
	}

	public Integer getBusId() {
		return (bus == null) ? null : bus.getId();
	}
	
	public Equipment getBus() {
		return (Hibernate.isInitialized(bus)) ? bus : new Equipment (bus.getId());
	}

	public void setBus(Equipment bus) {
		this.bus = bus;
	}

	public Integer getDriverId() {
		return driver.getId();
	}
	
	public Person getDriver() {
		return (Hibernate.isInitialized(driver)) ? driver : new Person (driver.getId());
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public Integer getTransTypeId() {
		return transType.getId();
	}
	
	public TransType getTransType() {
		return (Hibernate.isInitialized(transType)) ? transType : new TransType (transType.getId());
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	
	public Double getUpFee() {
		return upFee;
	}

	public void setUpFee(Double upFee) {
		this.upFee = upFee;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Calendar getDoDate() {
		return doDate;
	}

	public void setDoDate(Calendar doDate) {
		this.doDate = doDate;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public boolean equals(Object obj) {
		TransInfo po = (obj instanceof TransInfo) ? (TransInfo) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getBusId(), po.getBusId())
				&& CommonUtil.equals(getDriverId(), po.getDriverId())
				&& CommonUtil.equals(getInputer(), po.getInputerId())
				&& CommonUtil.equals(getTransTypeId(), po.getTransTypeId());
	}
		
	
	public static TransInfo getSafeObject(TransInfo id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (TransInfo) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new TransInfo(id.getId());
		}
	}
	
}
