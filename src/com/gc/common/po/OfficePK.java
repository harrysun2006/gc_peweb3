package com.gc.common.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class OfficePK implements Serializable {

	private Branch branch;

	private Department depart;

	private String name;

	public OfficePK() {
	}

	public OfficePK(Integer branchId, Integer departId, String name) {
		this(new Branch(branchId), new Department(departId), name);
	}

	public OfficePK(Branch branch, Department depart, String name) {
		setBranch(branch);
		setDepart(depart);
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

	public Integer getDepartId() {
		return (depart == null) ? null : depart.getId();
	}

	public Department getDepart() {
		return Department.getSafeObject(depart);
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj) {
		OfficePK po = (obj instanceof OfficePK) ? (OfficePK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", name=").append(name).append(")");
		return sb.toString();
	}

	public static OfficePK getSafeObject(OfficePK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (OfficePK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new OfficePK();
		}
	}
}
