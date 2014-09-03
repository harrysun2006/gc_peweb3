package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * 用工类别
 * @author hsun
 *
 */
public class WorkType {

	private WorkTypePK id;

	private Double no;

	private Integer active;

	public WorkType() {
	}

	public WorkType(Integer branchId, String name) {
		this(new WorkTypePK(branchId, name));
	}

	public WorkType(WorkTypePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public WorkTypePK getId() {
		return id;
	}

	public void setId(WorkTypePK id) {
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
		WorkType po = (obj instanceof WorkType) ? (WorkType) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.WorkType{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static WorkType getSafeObject(WorkType po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (WorkType) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new WorkType(po.getId());
		}
	}
}
