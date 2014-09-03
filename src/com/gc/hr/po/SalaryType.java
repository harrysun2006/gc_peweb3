package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * 用工类别
 * @author hsun
 *
 */
public class SalaryType {

	private SalaryTypePK id;

	private Double no;

	private Integer active;

	public SalaryType() {
	}

	public SalaryType(Integer branchId, String name) {
		this(new SalaryTypePK(branchId, name));
	}

	public SalaryType(SalaryTypePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public SalaryTypePK getId() {
		return id;
	}

	public void setId(SalaryTypePK id) {
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
		SalaryType po = (obj instanceof SalaryType) ? (SalaryType) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.SalaryType{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static SalaryType getSafeObject(SalaryType po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalaryType) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalaryType(po.getId());
		}
	}
}
