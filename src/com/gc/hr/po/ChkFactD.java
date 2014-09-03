package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class ChkFactD {

	private ChkFactDPK id;

	private Person person;

	private Calendar date;

	private ChkHoliday holiday;

	private ChkWork work;

	private ChkExtra extra;

	private ChkDisp disp;

	public ChkFactD() {
	}

	public ChkFactD(ChkFactDPK id) {
		setId(id);
	}

	public ChkFactD(Integer branchId, String hdNo, Integer no) {
		this(new ChkFactDPK(branchId, hdNo, no));
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

	public ChkFactDPK getId() {
		return id;
	}

	public void setId(ChkFactDPK id) {
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

	public String getHolidayNo() {
		return (holiday == null) ? null : holiday.getNo();
	}

	public String getHolidayName() {
		return (holiday == null) ? null : holiday.getName();
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

	public String getWorkNo() {
		return (work == null) ? null : work.getNo();
	}

	public String getWorkName() {
		return (work == null) ? null : work.getName();
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

	public String getExtraNo() {
		return (extra == null) ? null : extra.getNo();
	}

	public String getExtraName() {
		return (extra == null) ? null : extra.getName();
	}

	public ChkExtra getExtra() {
		return ChkExtra.getSafeObject(extra);
	}

	public void setExtra(ChkExtra extra) {
		this.extra = extra;
	}

	public Integer getDispId() {
		return (disp == null) ? null : disp.getId();
	}

	public String getDispNo() {
		return (disp == null) ? null : disp.getNo();
	}

	public String getDispName() {
		return (disp == null) ? null : disp.getName();
	}

	public ChkDisp getDisp() {
		return ChkDisp.getSafeObject(disp);
	}

	public void setDisp(ChkDisp disp) {
		this.disp = disp;
	}

	public boolean equals(Object obj) {
		ChkFactD po = (obj instanceof ChkFactD) ? (ChkFactD) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getHolidayId(), po.getHolidayId())
			&& CommonUtil.equals(getWorkId(), po.getWorkId())
			&& CommonUtil.equals(getExtraId(), po.getExtraId())
			&& CommonUtil.equals(getDispId(), po.getDispId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrChkFactD{id=").append(id)
			.append(", person=").append(getPersonId())
			.append(", date=").append(date)
			.append("}");
		return sb.toString();
	}

	public static ChkFactD getSafeObject(ChkFactD po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (ChkFactD) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new ChkFactD(po.getId());
		}
	}
}
