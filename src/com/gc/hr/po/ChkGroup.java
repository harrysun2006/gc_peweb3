package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.util.CommonUtil;

public class ChkGroup {

	private Integer id;

	private Branch branch;

	private String no;

	private String name;

	private Department depart;

	private String comment;

	public ChkGroup() {
	}

	public ChkGroup(Integer id) {
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		ChkGroup po = (obj instanceof ChkGroup) ? (ChkGroup) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Group{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", no=").append(no)
			.append(", name=").append(name);
		return sb.toString();
	}

	public static ChkGroup getSafeObject(ChkGroup po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (ChkGroup) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new ChkGroup(po.getId());
		}
	}

}
