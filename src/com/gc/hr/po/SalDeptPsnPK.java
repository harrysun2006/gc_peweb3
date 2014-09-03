package com.gc.hr.po;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class SalDeptPsnPK implements Serializable {

	private Branch branch;

	private Department depart;

	private Person person;

	public SalDeptPsnPK() {
	}

	public SalDeptPsnPK(Integer branchId, Integer departId, Integer personId) {
		this(new Branch(branchId), new Department(departId), new Person(personId));
	}

	public SalDeptPsnPK(Branch branch, Department depart, Person person) {
		setBranch(branch);
		setDepart(depart);
		setPerson(person);
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

	public boolean equals(Object obj) {
		SalDeptPsnPK po = (obj instanceof SalDeptPsnPK) ? (SalDeptPsnPK) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", person=").append(getPersonId()).append(")");
		return sb.toString();
	}

	public static SalDeptPsnPK getSafeObject(SalDeptPsnPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (SalDeptPsnPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new SalDeptPsnPK(id.getBranch(), id.getDepart(), id.getPerson());
		}
	}
}
