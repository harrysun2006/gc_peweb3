package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SalFactIx {

	private SalFactIxPK id;

	private Integer no;

	public SalFactIx() {
	}

	public SalFactIx(Integer branchId, String hdNo, Integer no, Integer itemId) {
		this(new SalFactIxPK(branchId, hdNo, itemId), no);
	}

	public SalFactIx(SalFactIxPK id, Integer no) {
		setId(id);
		setNo(no);
	}

	public SalFactIxPK getId() {
		return id;
	}

	public void setId(SalFactIxPK id) {
		this.id = id;
	}

	public SalFact getFact() {
		return (id == null) ? null : id.getFact();
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getHdNo() {
		return (id == null) ? null : id.getHdNo();
	}

	public SalItem getItem() {
		return (id == null) ? null : id.getItem();
	}

	public Integer getItemId() {
		return (id == null) ? null : id.getItemId();
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public boolean equals(Object obj) {
		SalFactIx po = (obj instanceof SalFactIx) ? (SalFactIx) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalFactIx{id=").append(id).append("}");
		return sb.toString();
	}

	public static SalFactIx getSafeObject(SalFactIx po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalFactIx) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalFactIx(po.getId(), po.getNo());
		}
	}
}
