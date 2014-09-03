package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccidentPK implements Serializable {
	private static final long serialVersionUID = -2673237646999803947L;
	
	private Branch branch;
	
	private Integer id;
	
	public AccidentPK() {}
	
	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AccidentPK(Branch branch, Integer id) {
		this.branch = branch;
		this.id = id;
	}
	
	public AccidentPK(Integer branchId, Integer id) {
		this.branch = new Branch(branchId);
		this.id = id;
	}
	
	public boolean equals(Object obj) {
		AccidentPK po = (obj instanceof AccidentPK) ? (AccidentPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccidentPK getSafeObject(AccidentPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccidentPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccidentPK(id.getBranch(), id.getId());
		}
	}

}
