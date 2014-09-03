package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.Constants;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class ChkPlanD {

	private ChkPlanDPK id;

	private Person person;

	private Calendar date;

	private ChkHoliday holiday;

	private ChkWork work;

	private ChkExtra extra;

	public ChkPlanD() {
	}

	public ChkPlanD(ChkPlanDPK id) {
		setId(id);
	}

	public ChkPlanD(Integer branchId, String hdNo, Integer no) {
		this(new ChkPlanDPK(branchId, hdNo, no));
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

	public ChkPlanDPK getId() {
		return id;
	}

	public void setId(ChkPlanDPK id) {
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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
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

	public Integer getWorkId() {
		return (work == null) ? null : work.getId();
	}

	public ChkWork getWork() {
		return ChkWork.getSafeObject(work);
	}

	public void setWork(ChkWork work) {
		this.work = work;
	}

	public Integer getExtraId() {
		return (extra == null) ? null : extra.getId();
	}

	public ChkExtra getExtra() {
		return ChkExtra.getSafeObject(extra);
	}

	public void setExtra(ChkExtra extra) {
		this.extra = extra;
	}

	public boolean equals(Object obj) {
		ChkPlanD po = (obj instanceof ChkPlanD) ? (ChkPlanD) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getHolidayId(), po.getHolidayId())
			&& CommonUtil.equals(getWorkId(), po.getWorkId())
			&& CommonUtil.equals(getExtraId(), po.getExtraId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrChkPlanD{id=").append(id)
			.append(", person=").append(getPersonId())
			.append(", date=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, date))
			.append("}");
		return sb.toString();
	}

	public static ChkPlanD getSafeObject(ChkPlanD po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (ChkPlanD) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new ChkPlanD(po.getId());
		}
	}
}
