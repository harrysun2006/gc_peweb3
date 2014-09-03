/**
 * 
 */
package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class TransType {
	private Integer id;
	
	private Branch branch;
	
	private String name;
	
	private String desc;
	
	public TransType() {
		
	}
	
	public TransType(Integer id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
		TransType po = (obj instanceof TransType) ? (TransType) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}
	
	public static TransType getSafeObject(TransType transType) {
		if (Hibernate.isInitialized(transType)) {
			if (transType instanceof HibernateProxy) return (TransType) ((HibernateProxy) transType).getHibernateLazyInitializer().getImplementation();
			else return transType;
		} else {
			if (transType == null) return null;
			else return new TransType(transType.getId());
		}
	}

}
