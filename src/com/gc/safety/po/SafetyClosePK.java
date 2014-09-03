package com.gc.safety.po;

import java.io.Serializable;
import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;

public class SafetyClosePK implements Serializable{
	private static final long serialVersionUID = -2455080958406486562L;

	private Branch branch;
	
	private Calendar date;
	
	public SafetyClosePK() {}
	
	public SafetyClosePK(Branch branch, Calendar date) {
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
	
	public static SafetyClosePK getSafeObject(SafetyClosePK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SafetyClosePK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SafetyClosePK();
		}
		
	}
}
