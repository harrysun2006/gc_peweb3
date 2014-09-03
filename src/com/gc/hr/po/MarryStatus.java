package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * »éÒö×´Ì¬
 * @author hsun
 *
 */
public class MarryStatus {

	private MarryStatusPK id;

	private Double no;

	private Integer active;

	public MarryStatus() {
	}

	public MarryStatus(Integer branchId, String name) {
		this(new MarryStatusPK(branchId, name));
	}

	public MarryStatus(MarryStatusPK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public MarryStatusPK getId() {
		return id;
	}

	public void setId(MarryStatusPK id) {
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
		MarryStatus po = (obj instanceof MarryStatus) ? (MarryStatus) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.MarryStatus{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static MarryStatus getSafeObject(MarryStatus po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (MarryStatus) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new MarryStatus(po.getId());
		}
	}
}
