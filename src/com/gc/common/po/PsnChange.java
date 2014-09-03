package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class PsnChange {

	private Integer id;

	private Branch branch;

	private Person person;

	private Calendar date;

	private Integer change; // 变动类型, 0:注册, 1:人事调动, 2:状态变更, 3:人事调动&状态变更, 9:注销

	// T_PSN_ONLINE
	private Person alloter;

	private String allotReason;

	private Integer changedDepart;

	private Department oldDepart;
	
	private Department newDepart;

	private Integer changedOffice;

	private String oldOffice;
	
	private String newOffice;

	private Integer changedLine;

	private Line oldLine;

	private Line newLine;

	private Integer changedBus;

	private Equipment oldBus;

	private Equipment newBus;

	private Integer changedCert2No;

	private String oldCert2No;

	private String newCert2No;

	private Integer changedCert2NoHex;

	private String oldCert2NoHex;

	private String newCert2NoHex;

	// T_PSN_STATUS
	private Person upgrader;

	private String upgradeReason;

	private Integer changedRegType;

	private String oldRegType;

	private String newRegType;

	private Integer changedType;

	private String oldType;

	private String newType;

	private Integer changedSalaryType;

	private String oldSalaryType;

	private String newSalaryType;

	private Integer changedPosition;

	private Position oldPosition;

	private Position newPosition;

	private Integer changedWorkType;

	private String oldWorkType;

	private String newWorkType;

	private Integer changedRegBelong;

	private String oldRegBelong;

	private String newRegBelong;

	private Integer changedParty;

	private String oldParty;

	private String newParty;

	private Integer changedGrade;

	private String oldGrade;

	private String newGrade;

	private Integer changedSchooling;

	private String oldSchooling;

	private String newSchooling;

	private Integer changedContractNo;

	private String oldContractNo;

	private String newContractNo;

	private Integer changedContr1End;

	private Calendar oldContr1End;

	private Calendar newContr1End;

	public PsnChange() {
	}

	public PsnChange(Integer id) {
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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Integer getChange() {
		return change;
	}

	public void setChange(Integer change) {
		this.change = change;
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

	public String getAllotReason() {
		return allotReason;
	}

	public void setAllotReason(String allotReason) {
		this.allotReason = allotReason;
	}

	public Integer getChangedDepart() {
		return changedDepart;
	}

	public void setChangedDepart(Integer changedDepart) {
		this.changedDepart = changedDepart;
	}

	public Integer getOldDepartId() {
		return (oldDepart == null) ? null : oldDepart.getId();
	}

	public String getOldDepartName() {
		Department d = getOldDepart();
		return (d == null) ? "" : d.getName();
	}

	public Department getOldDepart() {
		return Department.getSafeObject(oldDepart);
	}

	public void setOldDepart(Department oldDepart) {
		this.oldDepart = oldDepart;
	}

	public Integer getNewDepartId() {
		return (newDepart == null) ? null : newDepart.getId();
	}

	public String getNewDepartName() {
		Department d = getNewDepart();
		return (d == null) ? "" : d.getName();
	}

	public Department getNewDepart() {
		return Department.getSafeObject(newDepart);
	}

	public void setNewDepart(Department newDepart) {
		this.newDepart = newDepart;
	}

	public Integer getChangedOffice() {
		return changedOffice;
	}

	public void setChangedOffice(Integer changedOffice) {
		this.changedOffice = changedOffice;
	}

	public String getOldOffice() {
		return oldOffice;
	}

	public void setOldOffice(String oldOffice) {
		this.oldOffice = oldOffice;
	}

	public String getNewOffice() {
		return newOffice;
	}

	public void setNewOffice(String newOffice) {
		this.newOffice = newOffice;
	}

	public Integer getChangedLine() {
		return changedLine;
	}

	public void setChangedLine(Integer changedLine) {
		this.changedLine = changedLine;
	}

	public Integer getOldLineId() {
		return (oldLine == null) ? null : oldLine.getId();
	}

	public String getOldLineName() {
		Line l = getOldLine();
		return (l == null) ? null : l.getName();
	}

	public Line getOldLine() {
		return Line.getSafeObject(oldLine);
	}

	public void setOldLine(Line oldLine) {
		this.oldLine = oldLine;
	}

	public Integer getNewLineId() {
		return (newLine == null) ? null : newLine.getId();
	}

	public String getNewLineName() {
		Line l = getNewLine();
		return (l == null) ? null : l.getName();
	}

	public Line getNewLine() {
		return Line.getSafeObject(newLine);
	}

	public void setNewLine(Line newLine) {
		this.newLine = newLine;
	}

	public Integer getChangedBus() {
		return changedBus;
	}

	public void setChangedBus(Integer changedBus) {
		this.changedBus = changedBus;
	}

	public Integer getOldBusId() {
		return (oldBus == null) ? null : oldBus.getId();
	}

	public String getOldBusAuthNo() {
		Equipment b = getOldBus();
		return (b == null) ? null : b.getAuthNo();
	}

	public Equipment getOldBus() {
		return Equipment.getSafeObject(oldBus);
	}

	public void setOldBus(Equipment oldBus) {
		this.oldBus = oldBus;
	}

	public Integer getNewBusId() {
		return (newBus == null) ? null : newBus.getId();
	}

	public String getNewBusAuthNo() {
		Equipment b = getNewBus();
		return (b == null) ? null : b.getAuthNo();
	}

	public Equipment getNewBus() {
		return Equipment.getSafeObject(newBus);
	}

	public void setNewBus(Equipment newBus) {
		this.newBus = newBus;
	}

	public Integer getChangedCert2No() {
		return changedCert2No;
	}

	public void setChangedCert2No(Integer changedCert2No) {
		this.changedCert2No = changedCert2No;
	}

	public String getOldCert2No() {
		return oldCert2No;
	}

	public void setOldCert2No(String oldCert2No) {
		this.oldCert2No = oldCert2No;
	}

	public String getNewCert2No() {
		return newCert2No;
	}

	public void setNewCert2No(String newCert2No) {
		this.newCert2No = newCert2No;
	}

	public Integer getChangedCert2NoHex() {
		return changedCert2NoHex;
	}

	public void setChangedCert2NoHex(Integer changedCert2NoHex) {
		this.changedCert2NoHex = changedCert2NoHex;
	}

	public String getOldCert2NoHex() {
		return oldCert2NoHex;
	}

	public void setOldCert2NoHex(String oldCert2NoHex) {
		this.oldCert2NoHex = oldCert2NoHex;
	}

	public String getNewCert2NoHex() {
		return newCert2NoHex;
	}

	public void setNewCert2NoHex(String newCert2NoHex) {
		this.newCert2NoHex = newCert2NoHex;
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

	public String getUpgradeReason() {
		return upgradeReason;
	}

	public void setUpgradeReason(String upgradeReason) {
		this.upgradeReason = upgradeReason;
	}

	public Integer getChangedRegType() {
		return changedRegType;
	}

	public void setChangedRegType(Integer changedRegType) {
		this.changedRegType = changedRegType;
	}

	public String getOldRegType() {
		return oldRegType;
	}

	public void setOldRegType(String oldRegType) {
		this.oldRegType = oldRegType;
	}

	public String getNewRegType() {
		return newRegType;
	}

	public void setNewRegType(String newRegType) {
		this.newRegType = newRegType;
	}

	public Integer getChangedType() {
		return changedType;
	}

	public void setChangedType(Integer changedType) {
		this.changedType = changedType;
	}

	public String getOldType() {
		return oldType;
	}

	public void setOldType(String oldType) {
		this.oldType = oldType;
	}

	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}

	public Integer getChangedSalaryType() {
		return changedSalaryType;
	}

	public void setChangedSalaryType(Integer changedSalaryType) {
		this.changedSalaryType = changedSalaryType;
	}

	public String getOldSalaryType() {
		return oldSalaryType;
	}

	public void setOldSalaryType(String oldSalaryType) {
		this.oldSalaryType = oldSalaryType;
	}

	public String getNewSalaryType() {
		return newSalaryType;
	}

	public void setNewSalaryType(String newSalaryType) {
		this.newSalaryType = newSalaryType;
	}

	public Integer getChangedPosition() {
		return changedPosition;
	}

	public void setChangedPosition(Integer changedPosition) {
		this.changedPosition = changedPosition;
	}

	public String getOldPositionNo() {
		return (oldPosition == null) ? null : oldPosition.getNo();
	}

	public String getOldPositionName() {
		Position p = getOldPosition();
		return (p == null) ? null : p.getName();
	}

	public Position getOldPosition() {
		return Position.getSafeObject(oldPosition);
	}

	public void setOldPosition(Position oldPosition) {
		this.oldPosition = oldPosition;
	}

	public String getNewPositionNo() {
		return (newPosition == null) ? null : newPosition.getNo();
	}

	public String getNewPositionName() {
		Position p = getNewPosition();
		return (p == null) ? null : p.getName();
	}

	public Position getNewPosition() {
		return Position.getSafeObject(newPosition);
	}

	public void setNewPosition(Position newPosition) {
		this.newPosition = newPosition;
	}

	public Integer getChangedWorkType() {
		return changedWorkType;
	}

	public void setChangedWorkType(Integer changedWorkType) {
		this.changedWorkType = changedWorkType;
	}

	public String getOldWorkType() {
		return oldWorkType;
	}

	public void setOldWorkType(String oldWorkType) {
		this.oldWorkType = oldWorkType;
	}

	public String getNewWorkType() {
		return newWorkType;
	}

	public void setNewWorkType(String newWorkType) {
		this.newWorkType = newWorkType;
	}

	public Integer getChangedRegBelong() {
		return changedRegBelong;
	}

	public void setChangedRegBelong(Integer changedRegBelong) {
		this.changedRegBelong = changedRegBelong;
	}

	public String getOldRegBelong() {
		return oldRegBelong;
	}

	public void setOldRegBelong(String oldRegBelong) {
		this.oldRegBelong = oldRegBelong;
	}

	public String getNewRegBelong() {
		return newRegBelong;
	}

	public void setNewRegBelong(String newRegBelong) {
		this.newRegBelong = newRegBelong;
	}

	public Integer getChangedParty() {
		return changedParty;
	}

	public void setChangedParty(Integer changedParty) {
		this.changedParty = changedParty;
	}

	public String getOldParty() {
		return oldParty;
	}

	public void setOldParty(String oldParty) {
		this.oldParty = oldParty;
	}

	public String getNewParty() {
		return newParty;
	}

	public void setNewParty(String newParty) {
		this.newParty = newParty;
	}

	public Integer getChangedGrade() {
		return changedGrade;
	}

	public void setChangedGrade(Integer changedGrade) {
		this.changedGrade = changedGrade;
	}

	public String getOldGrade() {
		return oldGrade;
	}

	public void setOldGrade(String oldGrade) {
		this.oldGrade = oldGrade;
	}

	public String getNewGrade() {
		return newGrade;
	}

	public void setNewGrade(String newGrade) {
		this.newGrade = newGrade;
	}

	public Integer getChangedSchooling() {
		return changedSchooling;
	}

	public void setChangedSchooling(Integer changedSchooling) {
		this.changedSchooling = changedSchooling;
	}

	public String getOldSchooling() {
		return oldSchooling;
	}

	public void setOldSchooling(String oldSchooling) {
		this.oldSchooling = oldSchooling;
	}

	public String getNewSchooling() {
		return newSchooling;
	}

	public void setNewSchooling(String newSchooling) {
		this.newSchooling = newSchooling;
	}

  public Integer getChangedContractNo() {
		return changedContractNo;
	}

	public void setChangedContractNo(Integer changedContractNo) {
		this.changedContractNo = changedContractNo;
	}

	public String getOldContractNo() {
		return oldContractNo;
	}

	public void setOldContractNo(String oldContractNo) {
		this.oldContractNo = oldContractNo;
	}

	public String getNewContractNo() {
		return newContractNo;
	}

	public void setNewContractNo(String newContractNo) {
		this.newContractNo = newContractNo;
	}

	public Integer getChangedContr1End() {
		return changedContr1End;
	}

	public void setChangedContr1End(Integer changedContr1End) {
		this.changedContr1End = changedContr1End;
	}

	public Calendar getOldContr1End() {
		return oldContr1End;
	}

	public void setOldContr1End(Calendar oldContr1End) {
		this.oldContr1End = oldContr1End;
	}

	public Calendar getNewContr1End() {
		return newContr1End;
	}

	public void setNewContr1End(Calendar newContr1End) {
		this.newContr1End = newContr1End;
	}

	public String getReason() {
    return (change == 0) ? allotReason
      : (change == 1) ? allotReason
      : (change == 2) ? upgradeReason
      : (change == 3) ? allotReason + ", " + upgradeReason
      : (change == 9) ? allotReason : "";
  }

  public void setReason(String v) {
  }

  public String getSource() {
    String r = "";
    if (change == 0)
      r = "招";
    else if (change == 9)
      r = "辞";
    else if (changedType == 1)
      r = getOldType();
    else if (changedPosition == 1)
      r = getOldPositionName();
    else if (changedDepart == 1)
      r = getOldDepartName();
    else if (changedRegBelong == 1)
    	r = getOldRegBelong();
    return r;
  }

  public void setSource(String v) {
  }

  public String getDest() {
    String r = "";
    if (change == 0)
      r = "招";
    else if (change == 9)
      r = "辞";
    else if (changedType == 1)
      r = getNewType();
    else if (changedPosition == 1)
      r = getNewPositionName();
    else if (changedDepart == 1)
      r = getNewDepartName();
    else if (changedRegBelong == 1)
    	r = getNewRegBelong();
    return r;
  }

  public void setDest(String v) {
  }

  public String getOn() {
  	return (change == 0) ? "√" : "";
  }

  public void setOn(String v) {
  }

  public String getDown() {
  	return (change == 9) ? "√" : "";
  }

  public void setDown(String v) {
  }

  public boolean equals(Object obj) {
		PsnChange po = (obj instanceof PsnChange) ? (PsnChange) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getAlloterId(), po.getAlloterId())
			&& CommonUtil.equals(getOldDepartId(), po.getOldDepartId())
			&& CommonUtil.equals(getNewDepartId(), po.getNewDepartId())
			&& CommonUtil.equals(getOldLineId(), po.getOldLineId())
			&& CommonUtil.equals(getNewLineId(), po.getNewLineId())
			&& CommonUtil.equals(getOldBusId(), po.getOldBusId())
			&& CommonUtil.equals(getNewBusId(), po.getNewBusId())
			&& CommonUtil.equals(getUpgraderId(), po.getUpgraderId())
			&& CommonUtil.equals(getOldPositionNo(), po.getOldPositionNo())
			&& CommonUtil.equals(getNewPositionNo(), po.getNewPositionNo());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PsnChange{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", person=").append(getPersonId())
			.append(", change=").append(getChange()).append("}");
		return sb.toString();
	}

	public static PsnChange getSafeObject(PsnChange po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (PsnChange) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new PsnChange(po.getId());
		}
	}
}
