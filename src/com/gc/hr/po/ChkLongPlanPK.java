package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class ChkLongPlanPK implements Serializable {

	private Branch branch;

	private String no;

	public ChkLongPlanPK() {
	}

	public ChkLongPlanPK(Integer branchId, String no) {
		this(new Branch(branchId), no);
	}

	public ChkLongPlanPK(Branch branch, String no) {
		setBranch(branch);
		setNo(no);
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public boolean equals(Object obj) {
		ChkLongPlanPK po = (obj instanceof ChkLongPlanPK) ? (ChkLongPlanPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", no=").append(no).append(")");
		return sb.toString();
	}

	public static ChkLongPlanPK getSafeObject(ChkLongPlanPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (ChkLongPlanPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new ChkLongPlanPK(id.getBranchId(), id.getNo());
		}
	}

}
