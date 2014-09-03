package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class ChkPlanDPK implements Serializable {

	private ChkPlan plan;

	private Integer no;

	public ChkPlanDPK() {
	}

	public ChkPlanDPK(Integer branchId, String hdNo, Integer no) {
		this(new ChkPlan(branchId, hdNo), no);
	}

	public ChkPlanDPK(ChkPlan plan, Integer no) {
		setPlan(plan);
		setNo(no);
	}

	public ChkPlanPK getPlanId() {
		return (plan == null) ? null : plan.getId();
	}

	public Integer getBranchId() {
		ChkPlanPK planId = getPlanId();
		return (planId == null) ? null : planId.getBranchId();
	}

	public String getHdNo() {
		ChkPlanPK planId = getPlanId();
		return (planId == null) ? null : planId.getNo();
	}

	public ChkPlan getPlan() {
		return ChkPlan.getSafeObject(plan); 
	}

	public void setPlan(ChkPlan plan) {
		this.plan = plan;
	}

	public void setPlan(Integer branchId, String hdNo) {
		this.plan = new ChkPlan(branchId, hdNo);
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public boolean equals(Object obj) {
		ChkPlanDPK po = (obj instanceof ChkPlanDPK) ? (ChkPlanDPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getHdNo(), po.getHdNo());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", hdNo=").append(getHdNo())
			.append(", no=").append(no).append(")");
		return sb.toString();
	}

	public static ChkPlanDPK getSafeObject(ChkPlanDPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (ChkPlanDPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new ChkPlanDPK(id.getPlan(), id.getNo());
		}
	}
}
