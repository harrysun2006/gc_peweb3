package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.util.CommonUtil;

public class SalTemplate {

	private Integer id;

	private Branch branch;

	private Department depart;

	private String name;

	private String comment;

	public SalTemplate() {
	}

	public SalTemplate(Integer id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Integer getDepartId() {
		return (depart == null) ? null : depart.getId();
	}

	public Department getDepart() {
		return Department.getSafeObject(depart);
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		SalTemplate po = (obj instanceof SalTemplate) ? (SalTemplate) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalTemplate{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", name=").append(name)
			.append("}");
		return sb.toString();
	}

	public static SalTemplate getSafeObject(SalTemplate po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalTemplate) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalTemplate(po.getId());
		}
	}
}
