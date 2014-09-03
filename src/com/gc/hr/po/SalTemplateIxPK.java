package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SalTemplateIxPK implements Serializable {

	private Branch branch;

	private SalTemplate template;

	private SalItem item;

	public SalTemplateIxPK() {
	}

	public SalTemplateIxPK(Integer branchId, Integer templateId, Integer itemId) {
		this(new Branch(branchId), new SalTemplate(templateId), new SalItem(itemId));
	}

	public SalTemplateIxPK(Branch branch, SalTemplate template, SalItem item) {
		setBranch(branch);
		setTemplate(template);
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
		SalTemplateIxPK po = (obj instanceof SalTemplateIxPK) ? (SalTemplateIxPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getTemplateId(), po.getTemplateId())
			&& CommonUtil.equals(getItemId(), po.getItemId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", hd=").append(getTemplateId())
			.append(", item=").append(getItemId()).append(")");
		return sb.toString();
	}

	public static SalTemplateIxPK getSafeObject(SalTemplateIxPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SalTemplateIxPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SalTemplateIxPK(id.getBranch(), id.getTemplate(), id.getItem());
		}
	}
}
