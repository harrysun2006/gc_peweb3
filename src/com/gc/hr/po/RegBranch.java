package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * ËùÊô¹«Ë¾
 * @author hsun
 *
 */
public class RegBranch {

	private RegBranchPK id;

	private Double no;

	private Integer active;

	public RegBranch() {
	}

	public RegBranch(Integer branchId, String name) {
		this(new RegBranchPK(branchId, name));
	}

	public RegBranch(RegBranchPK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public RegBranchPK getId() {
		return id;
	}

	public void setId(RegBranchPK id) {
		this.id = id;
	}

	public Double getNo() {
		return no;
	}

	public void setNo(Double no) {
		this.no = no;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public boolean equals(Object obj) {
		RegBranch po = (obj instanceof RegBranch) ? (RegBranch) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.RegBranch{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static RegBranch getSafeObject(RegBranch po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (RegBranch) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new RegBranch(po.getId());
		}
	}
}
