package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Person;

class SalVarOnline {

	private Integer id;

	private Branch branch;

	private Department depart;

	private Person person;

	private SalItem item;

	private Calendar onDate;

	private Calendar downDate;

	private Double rate;

	private String comment;

	public SalVarOnline() {
	}

	public SalVarOnline(Integer id) {
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

	public Integer getPersonId() {
		return (person == null) ? null : person.getId();
	}

	public Person getPerson() {
		return Person.getSafeObject(person);
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public Calendar getOnDate() {
		return onDate;
	}

	public void setOnDate(Calendar onDate) {
		this.onDate = onDate;
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalVarOnline{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", person=").append(getPersonId())
			.append(", rate=").append(rate).append("}");
		return sb.toString();
	}

	public static SalVarOnline getSafeObject(SalVarOnline po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalVarOnline) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalVarOnline(po.getId());
		}
	}
}
