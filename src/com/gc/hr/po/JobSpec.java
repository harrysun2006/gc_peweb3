package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * רҵ
 * @author hsun
 *
 */
public class JobSpec {

	private JobSpecPK id;

	private Double no;

	private Integer active;

	public JobSpec() {
	}

	public JobSpec(Integer branchId, String name) {
		this(new JobSpecPK(branchId, name));
	}

	public JobSpec(JobSpecPK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public JobSpecPK getId() {
		return id;
	}

	public void setId(JobSpecPK id) {
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
		JobSpec po = (obj instanceof JobSpec) ? (JobSpec) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.JobSpec{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static JobSpec getSafeObject(JobSpec po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (JobSpec) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new JobSpec(po.getId());
		}
	}
}
