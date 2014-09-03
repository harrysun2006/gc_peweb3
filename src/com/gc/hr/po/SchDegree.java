package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * ѧλ
 * @author hsun
 *
 */
public class SchDegree {

	private SchDegreePK id;

	private Double no;

	private Integer active;

	public SchDegree() {
	}

	public SchDegree(Integer branchId, String name) {
		this(new SchDegreePK(branchId, name));
	}

	public SchDegree(SchDegreePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public SchDegreePK getId() {
		return id;
	}

	public void setId(SchDegreePK id) {
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
		SchDegree po = (obj instanceof SchDegree) ? (SchDegree) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.SchDegree{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static SchDegree getSafeObject(SchDegree po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SchDegree) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SchDegree(po.getId());
		}
	}
}
