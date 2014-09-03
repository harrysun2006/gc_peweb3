package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;


public class PsnStatus {

	private Integer id;

	private Branch branch;

	private Person person;

	private Calendar onDate;

	private String upgradeReason;

	private String regType;

	private String type;

	private String salaryType;

	private String position;

	private Position fkPosition;

	private String workType;

	private String regBelong;

	private String party;

	private String grade;

	private String schooling;

	private Calendar downDate;

	private Person upgrader;

	private String isModContract;
	
	private Calendar contractEnd;
	
	public String getIsModContract() {
		return isModContract;
	}

	public void setIsModContract(String isModContract) {
		this.isModContract = isModContract;
	}

	public PsnStatus() {
	}

	public PsnStatus(Integer id) {
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

	public String getUpgradeReason() {
		return upgradeReason;
	}

	public void setUpgradeReason(String upgradeReason) {
		this.upgradeReason = upgradeReason;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Position getFkPosition() {
		return Position.getSafeObject(fkPosition);
	}

	public void setFkPosition(Position fkPosition) {
		this.fkPosition = fkPosition;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getRegBelong() {
		return regBelong;
	}

	public void setRegBelong(String regBelong) {
		this.regBelong = regBelong;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSchooling() {
		return schooling;
	}

	public void setSchooling(String schooling) {
		this.schooling = schooling;
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public Integer getUpgraderId() {
		return (upgrader == null) ? null : upgrader.getId();
	}

	public Person getUpgrader() {
		return Person.getSafeObject(upgrader);
	}

	public void setUpgrader(Person upgrader) {
		this.upgrader = upgrader;
	}

	public Calendar getContractEnd() {
		return contractEnd;
	}

	public void setContractEnd(Calendar contractEnd) {
		this.contractEnd = contractEnd;
	}

	public boolean equals(Object obj) {
		PsnStatus po = (obj instanceof PsnStatus) ? (PsnStatus) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getUpgraderId(), po.getUpgraderId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PsnStatus{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", person=").append(getPersonId())
			.append(", upgrader=").append(getUpgraderId()).append("}");
		return sb.toString();
	}

	public static PsnStatus getSafeObject(PsnStatus po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (PsnStatus) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new PsnStatus(po.getId());
		}
	}
}
