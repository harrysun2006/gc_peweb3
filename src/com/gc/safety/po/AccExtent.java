package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccExtent {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;
	
	public AccExtent() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public AccExtent(Long id){
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
		AccExtent po = (obj instanceof AccExtent) ? (AccExtent) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccExtent getSafeObject(AccExtent accExtent) {
		if (Hibernate.isInitialized(accExtent)) {
			if (accExtent instanceof HibernateProxy) return (AccExtent) ((HibernateProxy) accExtent).getHibernateLazyInitializer().getImplementation();
			else return accExtent;
		} else {
			if (accExtent == null) return null;
			else return new AccExtent(accExtent.getId());
		}
	}
}
