package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccDuty {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;
	
	public AccDuty() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public AccDuty(Long id){
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
		AccDuty po = (obj instanceof AccDuty) ? (AccDuty) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccDuty getSafeObject(AccDuty accDuty) {
		if (Hibernate.isInitialized(accDuty)) {
			if (accDuty instanceof HibernateProxy) return (AccDuty) ((HibernateProxy) accDuty).getHibernateLazyInitializer().getImplementation();
			else return accDuty;
		} else {
			if (accDuty == null) return null;
			else return new AccDuty(accDuty.getId());
		}
	}
}
