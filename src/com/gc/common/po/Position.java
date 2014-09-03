package com.gc.common.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class Position {

	private PositionPK id;

	private String name;

	private String responsibility;

	private String reqDescription;

	private String reqCert;

	private Integer reqPersonCount;

	private Integer genLevel;

	private Integer salaryLevel;

	private Integer techLevel;

	private Integer securityLevel;

	private String techDescription;

	private String securityDescription;

	private String comment;

	public Position() {
	}

	public Position(Integer branchId, String no) {
		this(new PositionPK(branchId, no));
	}

	public Position(PositionPK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getNo() {
		return (id == null) ? null : id.getNo();
	}

	public PositionPK getId() {
		return id;
	}

	public void setId(PositionPK id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getReqDescription() {
		return reqDescription;
	}

	public void setReqDescription(String reqDescription) {
		this.reqDescription = reqDescription;
	}

	public String getReqCert() {
		return reqCert;
	}

	public void setReqCert(String reqCert) {
		this.reqCert = reqCert;
	}

	public Integer getReqPersonCount() {
		return reqPersonCount;
	}

	public void setReqPersonCount(Integer reqPersonCount) {
		this.reqPersonCount = reqPersonCount;
	}

	public Integer getGenLevel() {
		return genLevel;
	}

	public void setGenLevel(Integer genLevel) {
		this.genLevel = genLevel;
	}

	public Integer getSalaryLevel() {
		return salaryLevel;
	}

	public void setSalaryLevel(Integer salaryLevel) {
		this.salaryLevel = salaryLevel;
	}

	public Integer getTechLevel() {
		return techLevel;
	}

	public void setTechLevel(Integer techLevel) {
		this.techLevel = techLevel;
	}

	public Integer getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getTechDescription() {
		return techDescription;
	}

	public void setTechDescription(String techDescription) {
		this.techDescription = techDescription;
	}

	public String getSecurityDescription() {
		return securityDescription;
	}

	public void setSecurityDescription(String securityDescription) {
		this.securityDescription = securityDescription;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		Position po = (obj instanceof Position) ? (Position) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Position{id=").append(id)
			.append(", name=").append(name).append("}");
		return sb.toString();
	}

	public static Position getSafeObject(Position po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Position) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Position(po.getId());
		}
	}
}
