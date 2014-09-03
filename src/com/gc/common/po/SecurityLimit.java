package com.gc.common.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class SecurityLimit {

	private SecurityLimitPK id;

	private String description;

	private Integer equLimit;

	private Integer softLimit;

	private Integer lineLimit;

	private Integer fittingLimit;

	private Integer networkLimit;

	private Integer serviceLimit;

	private Integer projectLimit;

	private Integer woLimit;

	private Integer invLimit;

	private Integer reportLimit;

	private Integer systemLimit;

	private Integer lineTktLimit;

	private Integer typeLimit;

	private Integer engineLimit;

	private Integer hrLimit;

	private Department hrLimitDepart;

	private Integer safetyLimit;

	private Department safetyLimitDepart;

	private String comment;

	public SecurityLimit() {
	}

	public SecurityLimit(Integer branchId, Integer groupId) {
		this(new SecurityLimitPK(branchId, groupId));
	}

	public SecurityLimit(SecurityLimitPK id) {
		setId(id);
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getBranchUseId() {
		return (id == null) ? null : id.getBranchUseId();
	}

	public SecurityGroup getGroup() {
		return (id == null) ? null : id.getGroup();
	}

	public Integer getGroupId() {
		return (id == null) ? null : id.getGroupId();
	}

	public SecurityLimitPK getId() {
		return id;
	}

	public void setId(SecurityLimitPK id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEquLimit() {
		return equLimit;
	}

	public void setEquLimit(Integer equLimit) {
		this.equLimit = equLimit;
	}

	public Integer getSoftLimit() {
		return softLimit;
	}

	public void setSoftLimit(Integer softLimit) {
		this.softLimit = softLimit;
	}

	public Integer getLineLimit() {
		return lineLimit;
	}

	public void setLineLimit(Integer lineLimit) {
		this.lineLimit = lineLimit;
	}

	public Integer getFittingLimit() {
		return fittingLimit;
	}

	public void setFittingLimit(Integer fittingLimit) {
		this.fittingLimit = fittingLimit;
	}

	public Integer getNetworkLimit() {
		return networkLimit;
	}

	public void setNetworkLimit(Integer networkLimit) {
		this.networkLimit = networkLimit;
	}

	public Integer getServiceLimit() {
		return serviceLimit;
	}

	public void setServiceLimit(Integer serviceLimit) {
		this.serviceLimit = serviceLimit;
	}

	public Integer getProjectLimit() {
		return projectLimit;
	}

	public void setProjectLimit(Integer projectLimit) {
		this.projectLimit = projectLimit;
	}

	public Integer getWoLimit() {
		return woLimit;
	}

	public void setWoLimit(Integer woLimit) {
		this.woLimit = woLimit;
	}

	public Integer getInvLimit() {
		return invLimit;
	}

	public void setInvLimit(Integer invLimit) {
		this.invLimit = invLimit;
	}

	public Integer getReportLimit() {
		return reportLimit;
	}

	public void setReportLimit(Integer reportLimit) {
		this.reportLimit = reportLimit;
	}

	public Integer getSystemLimit() {
		return systemLimit;
	}

	public void setSystemLimit(Integer systemLimit) {
		this.systemLimit = systemLimit;
	}

	public Integer getLineTktLimit() {
		return lineTktLimit;
	}

	public void setLineTktLimit(Integer lineTktLimit) {
		this.lineTktLimit = lineTktLimit;
	}

	public Integer getTypeLimit() {
		return typeLimit;
	}

	public void setTypeLimit(Integer typeLimit) {
		this.typeLimit = typeLimit;
	}

	public Integer getEngineLimit() {
		return engineLimit;
	}

	public void setEngineLimit(Integer engineLimit) {
		this.engineLimit = engineLimit;
	}

	public Integer getHrLimit() {
		return hrLimit;
	}

	public void setHrLimit(Integer hrLimit) {
		this.hrLimit = hrLimit;
	}

	public Integer getHrLimitDepartId() {
		return (hrLimitDepart == null) ? null : hrLimitDepart.getId();
	}

	public Department getHrLimitDepart() {
		return Department.getSafeObject(hrLimitDepart);
	}

	public Integer getSafetyLimitDepartId() {
		return (safetyLimitDepart == null) ? null : safetyLimitDepart.getId();
	}

	public Integer getSafetyLimit() {
		return safetyLimit;
	}

	public void setSafetyLimit(Integer safetyLimit) {
		this.safetyLimit = safetyLimit;
	}

	public Department getSafetyLimitDepart() {
		return Department.getSafeObject(safetyLimitDepart);
	}

	public void setSafetyLimitDepart(Department safetyLimitDepart) {
		this.safetyLimitDepart = safetyLimitDepart;
	}

	public void setHrLimitDepart(Department hrLimitDepart) {
		this.hrLimitDepart = hrLimitDepart;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		SecurityLimit po = (obj instanceof SecurityLimit) ? (SecurityLimit) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getHrLimitDepartId(), po.getHrLimitDepartId())
			&& CommonUtil.equals(getSafetyLimitDepartId(), po.getSafetyLimitDepartId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SecurityLimit{id=").append(id)
			.append(", description=").append(description).append("}");
		return sb.toString();
	}

	public static SecurityLimit getSafeObject(SecurityLimit po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SecurityLimit) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SecurityLimit(po.getId());
		}
	}
}
