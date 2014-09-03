package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * 用工类别
 * @author hsun
 *
 */
public class RegType {

	private RegTypePK id;

	private Double no;

	private Integer active;

	public RegType() {
	}

	public RegType(Integer branchId, String name) {
		this(new RegTypePK(branchId, name));
	}

	public RegType(RegTypePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public RegTypePK getId() {
		return id;
	}

	public void setId(RegTypePK id) {
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
		RegType po = (obj instanceof RegType) ? (RegType) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.RegType{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static RegType getSafeObject(RegType po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (RegType) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new RegType(po.getId());
		}
	}
}
