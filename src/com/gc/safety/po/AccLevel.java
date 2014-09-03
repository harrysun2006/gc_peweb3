package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccLevel {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;
	
	public AccLevel() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public AccLevel(Long id){
		setId(id);
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
		AccLevel po = (obj instanceof AccLevel) ? (AccLevel) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccLevel getSafeObject(AccLevel accLevel) {
		if (Hibernate.isInitialized(accLevel)) {
			if (accLevel instanceof HibernateProxy) return (AccLevel) ((HibernateProxy) accLevel).getHibernateLazyInitializer().getImplementation();
			else return accLevel;
		} else {
			if (accLevel == null) return null;
			else return new AccLevel(accLevel.getId());
		}
	}
}
