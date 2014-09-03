package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccProcessor {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;
	
	public AccProcessor() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public AccProcessor(Long id){
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
		AccProcessor po = (obj instanceof AccProcessor) ? (AccProcessor) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccProcessor getSafeObject(AccProcessor accProcessor) {
		if (Hibernate.isInitialized(accProcessor)) {
			if (accProcessor instanceof HibernateProxy) return (AccProcessor) ((HibernateProxy) accProcessor).getHibernateLazyInitializer().getImplementation();
			else return accProcessor;
		} else {
			if (accProcessor == null) return null;
			else return new AccProcessor(accProcessor.getId());
		}
	}
}
