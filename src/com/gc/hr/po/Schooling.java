package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * 学历类别
 * @author hsun
 *
 */
public class Schooling {

	private SchoolingPK id;

	private Double no;

	private Integer active;

	public Schooling() {
	}

	public Schooling(Integer branchId, String name) {
		this(new SchoolingPK(branchId, name));
	}

	public Schooling(SchoolingPK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public SchoolingPK getId() {
		return id;
	}

	public void setId(SchoolingPK id) {
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
		Schooling po = (obj instanceof Schooling) ? (Schooling) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.Schooling{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static Schooling getSafeObject(Schooling po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Schooling) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Schooling(po.getId());
		}
	}
}
