package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class GuarInfoPK implements Serializable{
	private static final long serialVersionUID = 4939337791213095181L;

	private Branch branch;
	
	private String accNo;
	
	private Integer no;

	public GuarInfoPK(){}
	
	public GuarInfoPK(Branch branch,String accNo,Integer no) {
		this.branch = branch;
		this.accNo = accNo;
		this.no = no;
	}
	
	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}
	
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	public Branch getBranch() {
		return Branch.getSafeObject(branch);
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
		GuarInfoPK pk = (obj instanceof GuarInfoPK) ? (GuarInfoPK) obj : null;
		return CommonUtil.equals(getBranchId(), pk.getBranchId());
	}
	
	public static GuarInfoPK getSafeObject(GuarInfoPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (GuarInfoPK)((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new GuarInfoPK();
		}
	}
}