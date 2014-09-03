package com.gc.common.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class SecurityLimitPK implements Serializable {

	private SecurityGroup group;

	private Branch branch;

	public SecurityLimitPK() {
	}

	public SecurityLimitPK(Integer branchId, Integer groupId) {
		this(new Branch(branchId), new SecurityGroup(groupId));
	}

	public SecurityLimitPK(Branch branch, SecurityGroup group) {
		setBranch(branch);
		setGroup(group);
	}

	public Integer getGroupId() {
		return (group == null) ? null : group.getId();
	}

	public SecurityGroup getGroup() {
		return SecurityGroup.getSafeObject(group);
	}

	public void setGroup(SecurityGroup group) {
		this.group = group;
	}

	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}

	public String getBranchUseId() {
		Branch b = getBranch();
		return (b == null) ? null : b.getUseId();
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public boolean equals(Object obj) {
		SecurityLimitPK po = (obj instanceof SecurityLimitPK) ? (SecurityLimitPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getGroupId(), po.getGroupId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(group=").append(getGroupId())
			.append(", belong=").append(getBranchId()).append(")");
		return sb.toString();
	}

	public static SecurityLimitPK getSafeObject(SecurityLimitPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SecurityLimitPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SecurityLimitPK(id.getBranchId(), id.getGroupId());
		}
	}
}
