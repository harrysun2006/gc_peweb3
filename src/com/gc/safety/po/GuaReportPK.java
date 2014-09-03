package com.gc.safety.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class GuaReportPK implements Serializable {
	private static final long serialVersionUID = 5583943927794945407L;
	
	private Branch branch;
	
	private Integer accId;
	
	private Insurer insurer;
	
	public GuaReportPK() {}
	
	public GuaReportPK(Branch branch, Integer accId, Insurer insurer) {
		this.branch = branch;
		this.accId = accId;
		this.insurer = insurer;
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

	public Insurer getInsurer() {
		return Insurer.getSafeObject(insurer);
	}
	
	public Long getInsurerId() {
		return (insurer == null) ? null : insurer.getId();
	}

	public void setInsurer(Insurer insurer) {
		this.insurer = insurer;
	}
	
	public boolean equals(Object obj) {
		GuaReportPK po = (obj instanceof GuaReportPK) ? (GuaReportPK) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getInsurerId(), po.getInsurerId());
	}

	public static GuaReportPK getSafeObject(GuaReportPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (GuaReportPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new GuaReportPK(id.getBranch(), id.getAccId(), id.getInsurer());
		}
	}
}
