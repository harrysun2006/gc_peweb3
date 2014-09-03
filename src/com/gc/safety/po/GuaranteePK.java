package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class GuaranteePK implements Serializable{
	private static final long serialVersionUID = 3667667859931124690L;

	private Branch branch;
	
	private String accNo;
	
	public GuaranteePK() {}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
	public GuaranteePK(Integer branchId,String accNO) {
		this(new Branch(branchId),accNO);
	}
	
	public GuaranteePK(Branch branch,String accNO) {
		setBranch(branch);
		setAccNo(accNO);
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
	
	public boolean equals(Object obj) {
		GuaranteePK pk = (obj instanceof GuaranteePK) ? (GuaranteePK) obj : null;
		return CommonUtil.equals(getBranchId(), pk.getBranchId());
	}
	
	public static GuaranteePK getSafeObject(GuaranteePK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (GuaranteePK)((HibernateProxy)id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new GuaranteePK(id.getBranch(), id.getAccNo());
		}
	}
}