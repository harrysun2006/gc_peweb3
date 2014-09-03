package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * ¼®¹á
 * @author hsun
 *
 */
public class NativePlace {

	private NativePlacePK id;

	private Double no;

	private Integer active;

	public NativePlace() {
	}

	public NativePlace(Integer branchId, String name) {
		this(new NativePlacePK(branchId, name));
	}

	public NativePlace(NativePlacePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public NativePlacePK getId() {
		return id;
	}

	public void setId(NativePlacePK id) {
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
		NativePlace po = (obj instanceof NativePlace) ? (NativePlace) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.NativePlace{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static NativePlace getSafeObject(NativePlace po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (NativePlace) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new NativePlace(po.getId());
		}
	}
}
