package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class ChkExtra {

	private Integer id;

	private Branch branch;

	private String no;

	private String name;

	private String comment;

	public ChkExtra() {
	}

	public ChkExtra(Integer id) {
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		ChkExtra po = (obj instanceof ChkExtra) ? (ChkExtra) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrChkExtra{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", name=").append(name)
			.append("}");
		return sb.toString();
	}

	public static ChkExtra getSafeObject(ChkExtra po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (ChkExtra) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new ChkExtra(po.getId());
		}
	}
}
