package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;


public class PsnOnline {

	private Integer id;

	private Branch branch;

	private Person person;

	private Calendar onDate;

	private String allotReason;

	private Department depart;

	private String office;

	private Line line;

	private Equipment bus;

	private String cert2No;

	private String cert2NoHex;

	private Calendar downDate;

	private Person alloter;

	public PsnOnline() {
	}

	public PsnOnline(Integer id) {
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

	public Integer getPersonId() {
		return (person == null) ? null : person.getId();
	}

	public Person getPerson() {
		return Person.getSafeObject(person);
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Calendar getOnDate() {
		return onDate;
	}

	public void setOnDate(Calendar onDate) {
		this.onDate = onDate;
	}

	public String getAllotReason() {
		return allotReason;
	}

	public void setAllotReason(String allotReason) {
		this.allotReason = allotReason;
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

	public Integer getLineId() {
		return (line == null) ? null : line.getId();
	}

	public Line getLine() {
		return Line.getSafeObject(line);
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Integer getBusId() {
		return (bus == null) ? null : bus.getId();
	}

	public Equipment getBus() {
		return Equipment.getSafeObject(bus);
	}

	public void setBus(Equipment bus) {
		this.bus = bus;
	}

	public String getCert2No() {
		return cert2No;
	}

	public void setCert2No(String cert2No) {
		this.cert2No = cert2No;
	}

	public String getCert2NoHex() {
		return cert2NoHex;
	}

	public void setCert2NoHex(String cert2NoHex) {
		this.cert2NoHex = cert2NoHex;
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public Integer getAlloterId() {
		return (alloter == null) ? null : alloter.getId();
	}

	public Person getAlloter() {
		return Person.getSafeObject(alloter);
	}

	public void setAlloter(Person alloter) {
		this.alloter = alloter;
	}

	public boolean equals(Object obj) {
		PsnOnline po = (obj instanceof PsnOnline) ? (PsnOnline) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId())
			&& CommonUtil.equals(getLineId(), po.getLineId())
			&& CommonUtil.equals(getBusId(), po.getBusId())
			&& CommonUtil.equals(getAlloterId(), po.getAlloterId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PsnStatus{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", person=").append(getPersonId())
			.append(", alloter=").append(getAlloterId()).append("}");
		return sb.toString();
	}

	public static PsnOnline getSafeObject(PsnOnline po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (PsnOnline) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new PsnOnline(po.getId());
		}
	}
}
