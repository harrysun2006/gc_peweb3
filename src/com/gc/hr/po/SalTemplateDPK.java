package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SalTemplateDPK implements Serializable {

	private Branch branch;

	private SalTemplate template;

	private Integer no;

	private SalItem item;

	public SalTemplateDPK() {
	}

	public SalTemplateDPK(Integer branchId, Integer templateId, Integer no, Integer itemId) {
		this(new Branch(branchId), new SalTemplate(templateId), no, new SalItem(itemId));
	}

	public SalTemplateDPK(Branch branch, SalTemplate template, Integer no, SalItem item) {
		setBranch(branch);
		setTemplate(template);
		setNo(no);
		setItem(item);
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

	public Integer getTemplateId() {
		return (template == null) ? null : template.getId();
	}

 	public SalTemplate getTemplate() {
		return SalTemplate.getSafeObject(template);
	}

	public void setTemplate(SalTemplate template) {
		this.template = template;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getItemId() {
		return (item == null) ? null : item.getId();
	}

	public SalItem getItem() {
		return SalItem.getSafeObject(item);
	}

	public void setItem(SalItem item) {
		this.item = item;
	}

	public boolean equals(Object obj) {
		SalTemplateDPK po = (obj instanceof SalTemplateDPK) ? (SalTemplateDPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getTemplateId(), po.getTemplateId())
			&& CommonUtil.equals(getItemId(), po.getItemId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", hd=").append(getTemplateId())
			.append(", no=").append(no)
			.append(", item=").append(getItemId()).append(")");
		return sb.toString();
	}

	public static SalTemplateDPK getSafeObject(SalTemplateDPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SalTemplateDPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SalTemplateDPK(id.getBranch(), id.getTemplate(), id.getNo(), id.getItem());
		}
	}
}
