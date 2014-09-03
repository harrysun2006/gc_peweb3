package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccObject {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;
	
	public AccObject() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public AccObject(Long id){
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
		AccObject po = (obj instanceof AccObject) ? (AccObject) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccObject getSafeObject(AccObject accObject) {
		if (Hibernate.isInitialized(accObject)) {
			if (accObject instanceof HibernateProxy) return (AccObject) ((HibernateProxy) accObject).getHibernateLazyInitializer().getImplementation();
			else return accObject;
		} else {
			if (accObject == null) return null;
			else return new AccObject(accObject.getId());
		}
	}
}
