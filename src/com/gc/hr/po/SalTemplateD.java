package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class SalTemplateD {

	private SalTemplateDPK id;

	private Person person;

	private Double amount;

	private String comment;

	public SalTemplateD() {
	}

	public SalTemplateD(SalTemplateDPK id) {
		setId(id);
	}

	public SalTemplateD(Integer branchId, Integer templateId, Integer no, Integer itemId) {
		this(new Branch(branchId), new SalTemplate(templateId), no, new SalItem(itemId));
	}

	public SalTemplateD(Branch branch, SalTemplate template, Integer no, SalItem item) {
		this(new SalTemplateDPK(branch, template, no, item));
	}

	public SalTemplateDPK getId() {
		return id;
	}

	public void setId(SalTemplateDPK id) {
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

	public Integer getNo() {
		return (id == null) ? null : id.getNo();
	}

	public SalItem getItem() {
		return (id == null) ? null : id.getItem();
	}

	public Integer getItemId() {
		return (id == null) ? null : id.getItemId();
	}

	public Integer getPersonId() {
		return (person == null) ? null : person.getId();
	}

	public Person getPerson() {
		return Person.getSafeObject(person);
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		SalTemplateD po = (obj instanceof SalTemplateD) ? (SalTemplateD) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getPersonId(), po.getPersonId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalTemplateD{id=").append(id)
			.append(", person=").append(getPersonId())
			.append(", amount=").append(amount)
			.append("}");
		return sb.toString();
	}

	public static SalTemplateD getSafeObject(SalTemplateD po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalTemplateD) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalTemplateD(po.getId());
		}
	}
}
