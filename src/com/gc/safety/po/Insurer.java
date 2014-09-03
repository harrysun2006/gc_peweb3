package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class Insurer {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;

	public Insurer() {

	}

	public Insurer(Long id) {
		setId(id);
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
		Insurer po = (obj instanceof Insurer) ? (Insurer) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public static Insurer getSafeObject(Insurer insurer) {
		if (Hibernate.isInitialized(insurer)) {
			if (insurer instanceof HibernateProxy)
				return (Insurer) ((HibernateProxy) insurer).getHibernateLazyInitializer().getImplementation();
			else
				return insurer;
		} else {
			if (insurer == null) return null;
			else return new Insurer(insurer.getId());
		}
	}
}
