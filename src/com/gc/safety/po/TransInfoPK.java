package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class TransInfoPK implements Serializable {

	private static final long serialVersionUID = -4980766899839231435L;
	
	private Branch branch;
	
	private String accNo;
	
	private Integer no;
	
	public TransInfoPK() {
	}
	
	public TransInfoPK(Branch branch, String accNo, Integer no) {
		this.branch = branch;
		this.accNo = accNo;
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

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	public boolean equals(Object obj) {
		TransInfoPK po = (obj instanceof TransInfoPK) ? (TransInfoPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static TransInfoPK getSafeObject(TransInfoPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (TransInfoPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null)	return null;
			else return new TransInfoPK(id.getBranch(), id.getAccNo(), id.getNo());
		}
	}

}
