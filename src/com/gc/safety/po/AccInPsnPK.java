package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccInPsnPK implements Serializable {
	private static final long serialVersionUID = 3132369485463342006L;
	
	private Branch branch;
	
	private Integer accId;
	
	private Integer no;
	
	public AccInPsnPK() {}
	
	public AccInPsnPK(Branch branch, Integer accId, Integer no) {
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
		AccInPsnPK po = (obj instanceof AccInPsnPK) ? (AccInPsnPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccInPsnPK getSafeObject(AccInPsnPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccInPsnPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccInPsnPK(id.getBranch(), id.getAccId(), id.getNo());
		}
	}

}
