package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class ChkFactDPK implements Serializable {

	private ChkFact fact;

	private Integer no;

	public ChkFactDPK() {
	}

	public ChkFactDPK(Integer branchId, String hdNo, Integer no) {
		this(new ChkFact(branchId, hdNo), no);
	}

	public ChkFactDPK(ChkFact fact, Integer no) {
		setFact(fact);
		setNo(no);
	}

	public ChkFactPK getFactId() {
		return (fact == null) ? null : fact.getId();
	}

	public Integer getBranchId() {
		ChkFactPK factId = getFactId();
		return (factId == null) ? null : factId.getBranchId();
	}

	public String getHdNo() {
		ChkFactPK factId = getFactId();
		return (factId == null) ? null : factId.getNo();
	}

	public ChkFact getFact() {
		return ChkFact.getSafeObject(fact); 
	}

	public void setFact(ChkFact fact) {
		this.fact = fact;
	}

	public void setFact(Integer branchId, String hdNo) {
		this.fact = new ChkFact(branchId, hdNo);
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public boolean equals(Object obj) {
		ChkFactDPK po = (obj instanceof ChkFactDPK) ? (ChkFactDPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getHdNo(), po.getHdNo());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", hdNo=").append(getHdNo())
			.append(", no=").append(no).append(")");
		return sb.toString();
	}

	public static ChkFactDPK getSafeObject(ChkFactDPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (ChkFactDPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new ChkFactDPK(id.getFact(), id.getNo());
		}
	}
}
