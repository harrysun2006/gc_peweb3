package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class PolPartyPK implements Serializable {

	private Branch branch;

	private String name;

	public PolPartyPK() {
	}

	public PolPartyPK(Integer branchId, String name) {
		this(new Branch(branchId), name);
	}

	public PolPartyPK(Branch branch, String name) {
		setBranch(branch);
		setName(name);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj) {
		PolPartyPK po = (obj instanceof PolPartyPK) ? (PolPartyPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(branch=").append(getBranch())
			.append(", name=").append(name).append(")");
		return sb.toString();
	}

	public static PolPartyPK getSafeObject(PolPartyPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (PolPartyPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new PolPartyPK();
		}
	}
}
