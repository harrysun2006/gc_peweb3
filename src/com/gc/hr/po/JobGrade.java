package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * Ö°³ÆÀà±ð
 * @author hsun
 *
 */
public class JobGrade {

	private JobGradePK id;

	private Double no;

	private Integer active;

	public JobGrade() {
	}

	public JobGrade(Integer branchId, String name) {
		this(new JobGradePK(branchId, name));
	}

	public JobGrade(JobGradePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public JobGradePK getId() {
		return id;
	}

	public void setId(JobGradePK id) {
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
		JobGrade po = (obj instanceof JobGrade) ? (JobGrade) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.JobGrade{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static JobGrade getSafeObject(JobGrade po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (JobGrade) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new JobGrade(po.getId());
		}
	}
}
