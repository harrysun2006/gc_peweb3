package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SalFactIxPK implements Serializable {

	private SalFact fact;

	private SalItem item;

	public SalFactIxPK() {
	}

	public SalFactIxPK(Integer branchId, String hdNo, Integer itemId) {
		this(new SalFact(branchId, hdNo), new SalItem(itemId));
	}

	public SalFactIxPK(SalFact fact, SalItem item) {
		setFact(fact);
		setItem(item);
	}

	public SalFactPK getFactId() {
		return (fact == null) ? null : fact.getId();
	}

	public Branch getBranch() {
		SalFactPK factId = getFactId();
		return (factId == null) ? null : factId.getBranch();
	}

	public Integer getBranchId() {
		SalFactPK factId = getFactId();
		return (factId == null) ? null : factId.getBranchId();
	}

	public String getHdNo() {
		SalFactPK factId = getFactId();
		return (factId == null) ? null : factId.getNo();
	}

	public SalFact getFact() {
		return SalFact.getSafeObject(fact); 
	}

	public void setFact(SalFact fact) {
		this.fact = fact;
	}

	public void setFact(Integer branchId, String hdNo) {
		this.fact = new SalFact(branchId, hdNo);
	}

	public Integer getItemId() {
		return (item == null) ? null : item.getId();
	}

	public SalItem getItem() {
		return SalItem.getSafeObject(item);
	}

	public void setItem(SalItem item) {
		this.item = item;
	}

	public boolean equals(Object obj) {
		SalFactIxPK po = (obj instanceof SalFactIxPK) ? (SalFactIxPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getHdNo(), po.getHdNo())
			&& CommonUtil.equals(getItemId(), po.getItemId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", hdNo=").append(getHdNo())
			.append(", item=").append(getItemId()).append(")");
		return sb.toString();
	}

	public static SalFactIxPK getSafeObject(SalFactIxPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SalFactIxPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SalFactIxPK(id.getFact(), id.getItem());
		}
	}
}
