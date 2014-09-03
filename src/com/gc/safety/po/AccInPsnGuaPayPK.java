package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccInPsnGuaPayPK implements Serializable {
	private static final long serialVersionUID = 8932222417280061220L;
	
	private Branch branch;
	
	private String refNo;
	
	private String no;
	
	public AccInPsnGuaPayPK() {}
	
	public AccInPsnGuaPayPK(Branch branch, String refNo, String no) {
		this.branch = branch;
		this.refNo = refNo;
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

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public boolean equals(Object obj) {
		AccInPsnGuaPayPK po = (obj instanceof AccInPsnGuaPayPK) ? (AccInPsnGuaPayPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccInPsnGuaPayPK getSafeObject(AccInPsnGuaPayPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccInPsnGuaPayPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new AccInPsnGuaPayPK(id.getBranch(), id.getRefNo(), id.getNo());
		}
	}
}
