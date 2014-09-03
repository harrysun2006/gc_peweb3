package com.gc.hr.po;

import java.io.Serializable;
import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class SalFixOnlinePK implements Serializable {

	private Branch branch;

	private Department depart;

	private Person person;

	private SalItem item;

	private Calendar onDate;

	public SalFixOnlinePK() {
	}

	public SalFixOnlinePK(Integer branchId, Integer departId, Integer personId, Integer itemId, Calendar onDate) {
		this(new Branch(branchId), new Department(departId), new Person(personId), new SalItem(itemId), onDate);
	}

	public SalFixOnlinePK(Branch branch, Department depart, Person person, SalItem item, Calendar onDate) {
		setBranch(branch);
		setDepart(depart);
		setPerson(person);
		setItem(item);
		setOnDate(onDate);
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

	public String getDepartNo() {
		Department d = getDepart();
		return (d == null) ? null : d.getNo();
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

	public String getPersonWorkerId() {
		Person p = getPerson();
		return (p == null) ? null : p.getWorkerId();
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

	public String getItemNo() {
		SalItem si = getItem();
		return (si == null) ? null : si.getNo();
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

	public boolean equals(Object obj) {
		SalFixOnlinePK po = (obj instanceof SalFixOnlinePK) ? (SalFixOnlinePK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getItemId(), po.getItemId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", person=").append(getPersonId())
			.append(", item=").append(getItemId()).append(")");
		return sb.toString();
	}

	public static SalFixOnlinePK getSafeObject(SalFixOnlinePK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SalFixOnlinePK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SalFixOnlinePK(id.getBranch(), id.getDepart(), id.getPerson(), id.getItem(), id.getOnDate());
		}
	}
}
