package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class ChkFact {

	private ChkFactPK id;

	private Calendar date;

	private Department depart;

	private String office;

	private Person checker;

	private Calendar checkDate;

	private String comment;

	public ChkFact() {
	}

	public ChkFact(ChkFactPK id) {
		setId(id);
	}

	public ChkFact(Integer branchId, String no) {
		this(new ChkFactPK(branchId, no));
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getNo() {
		return (id == null) ? null : id.getNo();
	}

	public ChkFactPK getId() {
		return id;
	}

	public void setId(ChkFactPK id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
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

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public Integer getCheckerId() {
		return (checker == null) ? null : checker.getId();
	}

	public Person getChecker() {
		return Person.getSafeObject(checker);
	}

	public void setChecker(Person checker) {
		this.checker = checker;
	}

	public Calendar getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Calendar checkDate) {
		this.checkDate = checkDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		ChkFact po = (obj instanceof ChkFact) ? (ChkFact) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getDepartId(), po.getDepartId())
			&& CommonUtil.equals(getCheckerId(), po.getCheckerId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrChkFact{id=").append(id)
			.append(", depart=").append(getDepartId())
			.append(", office=").append(office)
			.append("}");
		return sb.toString();
	}

	public static ChkFact getSafeObject(ChkFact po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (ChkFact) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new ChkFact(po.getId());
		}
	}

}
