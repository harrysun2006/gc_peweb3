package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class HrClose {
	private HrClosePK id;
	
	private String comment;

	public HrClose() {
	}

	public HrClose(HrClosePK id) {
		setId(id);
	}

	public HrClose(Branch branch, Calendar date) {
		setId(new HrClosePK(branch, date));
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Calendar getDate() {
		return (id == null) ? null : id.getDate();
	}

	public HrClosePK getId() {
		return id;
	}

	public void setId(HrClosePK id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		HrClose po = (obj instanceof HrClose) ? (HrClose) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrClose{id=").append(id)
			.append(", comment=").append(comment).append("}");
		return sb.toString();
	}

	public HrClose getSafeObject(HrClose id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (HrClose) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new HrClose(id.getId());
		}
	}
}
