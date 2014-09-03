package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * 用工类别
 * @author hsun
 *
 */
public class HireType {

	private HireTypePK id;

	private Double no;

	private Integer active;

	public HireType() {
	}

	public HireType(Integer branchId, String name) {
		this(new HireTypePK(branchId, name));
	}

	public HireType(HireTypePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public HireTypePK getId() {
		return id;
	}

	public void setId(HireTypePK id) {
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
		HireType po = (obj instanceof HireType) ? (HireType) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.HireType{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static HireType getSafeObject(HireType po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (HireType) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new HireType(po.getId());
		}
	}
}
