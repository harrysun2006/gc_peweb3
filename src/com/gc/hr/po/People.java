package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * Ãñ×å
 * @author hsun
 *
 */
public class People {

	private PeoplePK id;

	private Double no;

	private Integer active;

	public People() {
	}

	public People(Integer branchId, String name) {
		this(new PeoplePK(branchId, name));
	}

	public People(PeoplePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public PeoplePK getId() {
		return id;
	}

	public void setId(PeoplePK id) {
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
		People po = (obj instanceof People) ? (People) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.People{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static People getSafeObject(People po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (People) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new People(po.getId());
		}
	}
}
