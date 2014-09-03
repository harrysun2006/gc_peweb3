package com.gc.safety.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SafetyClose {
	private SafetyClosePK id;
	
	private String comment;
	
	public SafetyClose() {}
	
	public SafetyClose(SafetyClosePK id) {
		setId(id);
	}

	public SafetyClosePK getId() {
		return SafetyClosePK.getSafeObject(id);
	}

	public void setId(SafetyClosePK id) {
		this.id = id;
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Calendar getDate() {
		return (id == null) ? null : id.getDate();
	}
	
	public boolean equals(Object obj) {
		SafetyClose po = (obj instanceof SafetyClose) ? (SafetyClose) obj : null;
		return CommonUtil.equals(this, po);
	}
	
	public SafetyClose getSafeObject(SafetyClose id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SafetyClose) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SafetyClose(id.getId());
		}
	}
}
