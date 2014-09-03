package com.gc.safety.po;

import java.io.Serializable;
import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccOutPsnPayPK implements Serializable {
	private Branch branch;
	
	private Integer accId;
	
	private Integer no;

	private Calendar payDate;
	
	public AccOutPsnPayPK() {}
	
	public AccOutPsnPayPK(Branch branch, Integer accId, Integer no, Calendar payDate) {
		this.branch = branch;
		this.accId = accId;
		this.no = no;
		this.payDate = payDate;
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}
	
	public Integer getBranchId() {
		return branch == null ? null : branch.getId();
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Calendar getPayDate() {
		return payDate;
	}

	public void setPayDate(Calendar payDate) {
		this.payDate = payDate;
	}
	
	public boolean equals(Object obj) {
		AccOutPsnPayPK po = (obj instanceof AccOutPsnPayPK) ? (AccOutPsnPayPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(branch, po.getBranch());
	}
	
	public static AccOutPsnPayPK getSafeObject(AccOutPsnPayPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutPsnPayPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutPsnPayPK(id.getBranch(), id.getAccId(), id.getNo(), id.getPayDate());
		}
	}
	
}
