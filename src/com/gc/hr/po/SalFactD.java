package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class SalFactD {

	private SalFactDPK id;

	private Person person;

	private Double amount;

	public SalFactD() {
	}

	public SalFactD(Integer branchId, String hdNo, Integer no, Integer itemId) {
		this(new SalFactDPK(branchId, hdNo, no, itemId));
	}

	public SalFactD(SalFactDPK id) {
		setId(id);
	}

	public SalFactDPK getId() {
		return id;
	}

	public void setId(SalFactDPK id) {
		this.id = id;
	}

	public SalFact getFact() {
		return (id == null) ? null : id.getFact();
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getHdNo() {
		return (id == null) ? null : id.getHdNo();
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

	public boolean equals(Object obj) {
		SalFactD po = (obj instanceof SalFactD) ? (SalFactD) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getPersonId(), po.getPersonId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalFactD{id=").append(id)
			.append(", person=").append(getPersonId()).append("}");
		return sb.toString();
	}

	public static SalFactD getSafeObject(SalFactD po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalFactD) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalFactD(po.getId());
		}
	}
}
