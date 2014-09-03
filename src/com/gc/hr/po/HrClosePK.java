package com.gc.hr.po;

import java.io.Serializable;
import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class HrClosePK implements Serializable {

	private Branch branch;
	
	private Calendar date;
	
	public HrClosePK() {
	}
	
	public HrClosePK(Branch branch, Calendar date) {
		this.branch = branch;
		this.date = date;
	}

	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean equals(Object obj) {
		HrClosePK po = (obj instanceof HrClosePK) ? (HrClosePK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", date=").append(date).append(")");
		return sb.toString();
	}

	public static HrClosePK getSafeObject(HrClosePK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (HrClosePK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new HrClosePK();
		}
		
	}
}
