package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccType {
	private Long id;
	
	private Branch branch;
	
	private String name;
	
	private String desc;

	public AccType() {}
	
	public AccType(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public boolean equals(Object obj) {
		AccType po = (obj instanceof AccType) ? (AccType) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccType getSafeObject(AccType accType) {
		if (Hibernate.isInitialized(accType)) {
			if (accType instanceof HibernateProxy) return (AccType) ((HibernateProxy) accType).getHibernateLazyInitializer().getImplementation();
			else return accType;
		} else {
			if (accType == null) return null;
			else return new AccType(accType.getId());
		}
	}
}
