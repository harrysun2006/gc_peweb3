package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SalTemplateIx {

	private SalTemplateIxPK id;

	private Integer no;

	public SalTemplateIx() {
	}

	public SalTemplateIx(SalTemplateIxPK id) {
		setId(id);
	}

	public SalTemplateIx(Integer branchId, Integer templateId, Integer no, Integer itemId) {
		this(new Branch(branchId), new SalTemplate(templateId), no, new SalItem(itemId));
	}

	public SalTemplateIx(Branch branch, SalTemplate template, Integer no, SalItem item) {
		this(new SalTemplateIxPK(branch, template, item));
		setNo(no);
	}

	public SalTemplateIxPK getId() {
		return id;
	}

	public void setId(SalTemplateIxPK id) {
		this.id = id;
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public SalTemplate getTemplate() {
		return (id == null) ? null : id.getTemplate();
	}

	public Integer getTemplateId() {
		return (id == null) ? null : id.getTemplateId();
	}

	public SalItem getItem() {
		return (id == null) ? null : id.getItem();
	}

	public Integer getItemId() {
		return (id == null) ? null : id.getItemId();
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public boolean equals(Object obj) {
		SalTemplateIx po = (obj instanceof SalTemplateIx) ? (SalTemplateIx) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalTemplateIx{id=").append(id).append("}");
		return sb.toString();
	}

	public static SalTemplateIx getSafeObject(SalTemplateIx po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalTemplateIx) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalTemplateIx(po.getId());
		}
	}
}
