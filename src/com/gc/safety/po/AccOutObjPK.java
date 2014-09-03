package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccOutObjPK implements Serializable {
	private static final long serialVersionUID = 5534730319099730185L;
	
	private Branch branch;
	
	private Integer accId;
	
	private Integer no;
	
	public AccOutObjPK() {}
	
	public AccOutObjPK(Branch branch, Integer accId, Integer no) {
		this.branch = branch;
		this.accId = accId;
		this.no = no;
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}
	
	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}
	
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	

	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	public boolean equals(Object obj) {
		AccOutObjPK po = (obj instanceof AccOutObjPK) ? (AccOutObjPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccOutObjPK getSafeObject(AccOutObjPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutObjPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccOutObjPK(id.getBranch(), id.getAccId(), id.getNo());
		}
	}

}
