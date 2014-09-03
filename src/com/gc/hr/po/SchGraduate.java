package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * ±œ“µ‘∫–£
 * @author hsun
 *
 */
public class SchGraduate {

	private SchGraduatePK id;

	private Double no;

	private Integer active;

	public SchGraduate() {
	}

	public SchGraduate(Integer branchId, String name) {
		this(new SchGraduatePK(branchId, name));
	}

	public SchGraduate(SchGraduatePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public SchGraduatePK getId() {
		return id;
	}

	public void setId(SchGraduatePK id) {
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
		SchGraduate po = (obj instanceof SchGraduate) ? (SchGraduate) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.SchGraduate{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static SchGraduate getSafeObject(SchGraduate po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SchGraduate) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SchGraduate(po.getId());
		}
	}
}
