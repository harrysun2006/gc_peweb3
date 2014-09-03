package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class AccOutGuaPayPK implements Serializable {
	private static final long serialVersionUID = -572012128582732575L;

	private Branch branch;
	
	private String refNo;
	
	private String no;
	
	public AccOutGuaPayPK() {}
	
	public AccOutGuaPayPK(Branch branch, String refNo, String no) {
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
		AccOutGuaPayPK po = (obj instanceof AccOutGuaPayPK) ? (AccOutGuaPayPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static AccOutGuaPayPK getSafeObject(AccOutGuaPayPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (AccOutGuaPayPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
 		} else {
 			if (id == null) return null;
 			else return new AccOutGuaPayPK(id.getBranch(), id.getRefNo(), id.getNo());
 		}
	}

}
