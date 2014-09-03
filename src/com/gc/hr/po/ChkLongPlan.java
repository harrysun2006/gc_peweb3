package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class ChkLongPlan {

	private ChkLongPlanPK id;

	private Person person;

	private Calendar fromDate;

	private Calendar endDate;

	private ChkHoliday holiday;

	private Person lastModifier;

	private Person checker;

	private Calendar checkDate;

	private String checkDescription;

	public ChkLongPlan() {
	}

	public ChkLongPlan(ChkLongPlanPK id) {
		setId(id);
	}

	public ChkLongPlan(Integer branchId, String no) {
		this(new ChkLongPlanPK(branchId, no));
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getNo() {
		return (id == null) ? null : id.getNo();
	}

	public ChkLongPlanPK getId() {
		return id;
	}

	public void setId(ChkLongPlanPK id) {
		this.id = id;
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

	public Calendar getFromDate() {
		return fromDate;
	}

	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Integer getHolidayId() {
		return (holiday == null) ? null : holiday.getId();
	}

	public ChkHoliday getHoliday() {
		return ChkHoliday.getSafeObject(holiday);
	}

	public void setHoliday(ChkHoliday holiday) {
		this.holiday = holiday;
	}

	public Integer getLastModifierId() {
		return (lastModifier == null) ? null : lastModifier.getId();
	}

	public Person getLastModifier() {
		return Person.getSafeObject(lastModifier);
	}

	public void setLastModifier(Person lastModifier) {
		this.lastModifier = lastModifier;
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

	public String getCheckDescription() {
		return checkDescription;
	}

	public void setCheckDescription(String checkDescription) {
		this.checkDescription = checkDescription;
	}

	public boolean equals(Object obj) {
		ChkLongPlan po = (obj instanceof ChkLongPlan) ? (ChkLongPlan) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getHolidayId(), po.getHolidayId())
			&& CommonUtil.equals(getCheckerId(), po.getCheckerId())
			&& CommonUtil.equals(getLastModifierId(), po.getLastModifierId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrChkLongPlan{id=").append(id)
			.append(", person=").append(getPersonId()).append("}");
		return sb.toString();
	}

	public static ChkLongPlan getSafeObject(ChkLongPlan po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (ChkLongPlan) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new ChkLongPlan(po.getId());
		}
	}
}
