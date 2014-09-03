package com.gc.common.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class PositionPK implements Serializable {

	private Branch branch;

	private String no;

	public PositionPK() {
	}

	public PositionPK(Integer branchId, String no) {
		this(new Branch(branchId), no);
	}

	public PositionPK(Branch branch, String no) {
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
		PositionPK po = (obj instanceof PositionPK) ? (PositionPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", no=").append(no).append(")");
		return sb.toString();
	}

	public static PositionPK getSafeObject(PositionPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (PositionPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new PositionPK(id.getBranchId(), id.getNo());
		}
	}
}
