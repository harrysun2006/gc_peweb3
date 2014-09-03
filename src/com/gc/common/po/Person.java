package com.gc.common.po;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.hr.po.ChkFactD;
import com.gc.hr.po.ChkGroup;
import com.gc.hr.po.ChkLongPlan;
import com.gc.hr.po.ChkPlanD;
import com.gc.util.CommonUtil;

public class Person {

	private Integer id = 0;

	private Branch branch;

	private String workerId;

	private String name;

	private String pid;

	private String sex;

	private String people;

	private String nativePlace;

	private String regType;

	private String regAddress;

	private Calendar birthday;

	private String marryStatus;

	private String annuities;

	private String accumulation;

	private Calendar onDate;

	private Integer status;

	private Calendar downDate;

	private String downReason;

	private Calendar upgradeDate;

	private String upgradeReason;

	private String type;

	private String salaryType;

	private String position;

	private Position fkPosition;

	private String regBelong;

	private String party;

	private String grade;

	private String schooling;

	private Calendar allotDate;

	private String allotReason;

	private Department depart;

	private String office;

	private Line line;

	private Equipment bus;

	private String cert2No;

	private String cert2NoHex;

	private Calendar fillTableDate;

	private String commend;

	private Calendar workDate;

	private Calendar retireDate;

	private String serviceLength;

	private Calendar inDate;

	private Calendar outDate;

	private String workLength;

	private String groupNo;

	private String contractNo;

	private Calendar contr1From;

	private Calendar contr1End;

	private String contractReason;

	private Calendar contr2From;

	private Calendar contr2End;

	private String workType;

	private Integer level;

	private String techLevel;

	private String responsibility;

	private String cert1No;

	private Calendar cert1NoDate;

	private String serviceNo;

	private Calendar serviceNoDate;

	private String frontWorkResume;

	private String frontTrainingResume;

	private String specification;

	private String degree;

	private String graduate;

	private String skill;

	private String lanCom;

	private String national;

	private String state;

	private String city;

	private String address;

	private String zip;

	private String telephone;

	private String email;

	private String officeTel;

	private String officeExt;

	private String officeFax;

	private Person lastModifier;

	private String comment;

	private PsnPhoto psnPhoto;

	private ChkGroup chkGroup;

	private List<ChkLongPlan> chkLongPlans = new ArrayList<ChkLongPlan>();

	private List<ChkPlanD> chkPlanDs = new ArrayList<ChkPlanD>();

	private List<ChkFactD> chkFactDs = new ArrayList<ChkFactD>();

	// calculate properties

	private Calendar graduateDate;

	private Calendar partyOnDate;
	
	// 92年之前的工龄(单位为月，社保体系建立之前的工龄，系统按缴金工龄处理)
	private Integer paySocialInsInit;

	// 92年之后使用薪资系统之前的缴金工龄
	private Integer paySocialInsAdj;

	// 薪资系统中有社保项发生额的月份
	private Integer paySocialInsSal;

	public Person() {
	}

	public Person(Integer id) {
		setId(id);
	}

	public Person(Integer id, String workerId, String name) {
		this(id, workerId, name, null, null, null);
	}

	public Person(Integer id, String workerId, String name, Department depart, Line line, Equipment bus) {
		setId(id);
		setWorkerId(workerId);
		setName(name);
		setDepart(depart);
		setLine(line);
		setBus(bus);
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
		// if (position != null) position.getId().setBranch(branch);
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}

	public String getAnnuities() {
		return annuities;
	}

	public void setAnnuities(String annuities) {
		this.annuities = annuities;
	}

	public String getAccumulation() {
		return accumulation;
	}

	public void setAccumulation(String accumulation) {
		this.accumulation = accumulation;
	}

	public Calendar getOnDate() {
		return onDate;
	}

	public void setOnDate(Calendar onDate) {
		this.onDate = onDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public String getDownReason() {
		return downReason;
	}

	public void setDownReason(String downReason) {
		this.downReason = downReason;
	}

	public Calendar getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(Calendar upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

	public String getUpgradeReason() {
		return upgradeReason;
	}

	public void setUpgradeReason(String upgradeReason) {
		this.upgradeReason = upgradeReason;
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

	public void setPosition(String no) {
		this.position = no;
	}

	public String getPositionName() {
		Position p = getFkPosition();
		return (p == null) ? null : p.getName();
	}

	public Position getFkPosition() {
		return Position.getSafeObject(fkPosition);
	}

	public void setFkPosition(Position fkPosition) {
		this.fkPosition = fkPosition;
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

	public Calendar getAllotDate() {
		return allotDate;
	}

	public void setAllotDate(Calendar allotDate) {
		this.allotDate = allotDate;
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

	public String getDepartName() {
		Department d = getDepart();
		return (d == null) ? null : d.getName();
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

	public Calendar getFillTableDate() {
		return fillTableDate;
	}

	public void setFillTableDate(Calendar fillTableDate) {
		this.fillTableDate = fillTableDate;
	}

	public String getCommend() {
		return commend;
	}

	public void setCommend(String commend) {
		this.commend = commend;
	}

	public Calendar getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Calendar workDate) {
		this.workDate = workDate;
	}

	public Calendar getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Calendar retireDate) {
		this.retireDate = retireDate;
	}

	public String getServiceLength() {
		return serviceLength;
	}

	public void setServiceLength(String serviceLength) {
		this.serviceLength = serviceLength;
	}

	public Calendar getInDate() {
		return inDate;
	}

	public void setInDate(Calendar inDate) {
		this.inDate = inDate;
	}

	public Calendar getOutDate() {
		return outDate;
	}

	public void setOutDate(Calendar outDate) {
		this.outDate = outDate;
	}

	public String getWorkLength() {
		return workLength;
	}

	public void setWorkLength(String workLength) {
		this.workLength = workLength;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Calendar getContr1From() {
		return contr1From;
	}

	public void setContr1From(Calendar contr1From) {
		this.contr1From = contr1From;
	}

	public Calendar getContr1End() {
		return contr1End;
	}

	public void setContr1End(Calendar contr1End) {
		this.contr1End = contr1End;
	}

	public String getContractReason() {
		return contractReason;
	}

	public void setContractReason(String contractReason) {
		this.contractReason = contractReason;
	}

	public Calendar getContr2From() {
		return contr2From;
	}

	public void setContr2From(Calendar contr2From) {
		this.contr2From = contr2From;
	}

	public Calendar getContr2End() {
		return contr2End;
	}

	public void setContr2End(Calendar contr2End) {
		this.contr2End = contr2End;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTechLevel() {
		return techLevel;
	}

	public void setTechLevel(String techLevel) {
		this.techLevel = techLevel;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getCert1No() {
		return cert1No;
	}

	public void setCert1No(String cert1No) {
		this.cert1No = cert1No;
	}

	public Calendar getCert1NoDate() {
		return cert1NoDate;
	}

	public void setCert1NoDate(Calendar cert1NoDate) {
		this.cert1NoDate = cert1NoDate;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	public Calendar getServiceNoDate() {
		return serviceNoDate;
	}

	public void setServiceNoDate(Calendar serviceNoDate) {
		this.serviceNoDate = serviceNoDate;
	}

	public String getFrontWorkResume() {
		return frontWorkResume;
	}

	public void setFrontWorkResume(String frontWorkResume) {
		this.frontWorkResume = frontWorkResume;
	}

	public String getFrontTrainingResume() {
		return frontTrainingResume;
	}

	public void setFrontTrainingResume(String frontTrainingResume) {
		this.frontTrainingResume = frontTrainingResume;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGraduate() {
		return graduate;
	}

	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getLanCom() {
		return lanCom;
	}

	public void setLanCom(String lanCom) {
		this.lanCom = lanCom;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getOfficeExt() {
		return officeExt;
	}

	public void setOfficeExt(String officeExt) {
		this.officeExt = officeExt;
	}

	public String getOfficeFax() {
		return officeFax;
	}

	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public PsnPhoto getRawPsnPhoto() {
		return psnPhoto;
	}

	public PsnPhoto getPsnPhoto() {
		return PsnPhoto.getSafeObject(psnPhoto);
	}

	public void setPsnPhoto(PsnPhoto psnPhoto) {
		this.psnPhoto = psnPhoto;
	}

	public byte[] getPhoto() {
		PsnPhoto p = getPsnPhoto();
		return (p == null) ? null : p.getPhoto();
	}

	public void setPhoto(byte[] b) {
	}

	public Integer getChkGroupId() {
		return (chkGroup == null) ? null : chkGroup.getId();
	}

	public String getChkGroupName() {
		return (chkGroup == null) ? null : chkGroup.getName();
	}

	public ChkGroup getChkGroup() {
		return ChkGroup.getSafeObject(chkGroup);
	}

	public void setChkGroup(ChkGroup chkGroup) {
		this.chkGroup = chkGroup;
	}

	public List<ChkLongPlan> getChkLongPlans() {
		return chkLongPlans;
	}

	public void setChkLongPlans(List<ChkLongPlan> chkLongPlans) {
		this.chkLongPlans = chkLongPlans;
	}

	public void addChkLongPlan(ChkLongPlan clp) {
		chkLongPlans.add(clp);
	}

	public List<ChkPlanD> getChkPlanDs() {
		return chkPlanDs;
	}

	public void setChkPlanDs(List<ChkPlanD> chkPlanDts) {
		this.chkPlanDs = chkPlanDts;
	}

	public void addChkPlanD(ChkPlanD cpd) {
		chkPlanDs.add(cpd);
	}

	public List<ChkFactD> getChkFactDs() {
		return chkFactDs;
	}

	public void setChkFactDs(List<ChkFactD> chkFactDs) {
		this.chkFactDs = chkFactDs;
	}

	public void addChkFactD(ChkFactD cfd) {
		chkFactDs.add(cfd);
	}

	public Calendar getGraduateDate() {
		return graduateDate;
	}

	public void setGraduateDate(Calendar d) {
		this.graduateDate = d;
	}

	public Calendar getPartyOnDate() {
		return partyOnDate;
	}

	public void setPartyOnDate(Calendar d) {
		this.partyOnDate = d;
	}

	public Integer getPaySocialInsInit() {
		return paySocialInsInit;
	}

	public void setPaySocialInsInit(Integer paySocialInsInit) {
		this.paySocialInsInit = paySocialInsInit;
	}

	public Integer getPaySocialInsAdj() {
		return paySocialInsAdj;
	}

	public void setPaySocialInsAdj(Integer paySocialInsAdj) {
		this.paySocialInsAdj = paySocialInsAdj;
	}

	public Integer getPaySocialInsSal() {
		return paySocialInsSal;
	}

	public void setPaySocialInsSal(Integer paySocialInsSal) {
		this.paySocialInsSal = paySocialInsSal;
	}

	public void setPaySocialInsSal(Long paySocialInsSal) {
		this.paySocialInsSal = paySocialInsSal == null ? null : paySocialInsSal.intValue();
	}

  // 缴金工龄=92年之前的工龄+92年之后使用薪资系统之前的缴金工龄+薪资系统中有社保项发生额的月份
  public Integer getPaySocialIns()
  {
    return new Integer((paySocialInsInit == null ? 0 : paySocialInsInit.intValue()) 
    		+ (paySocialInsAdj == null ? 0 : paySocialInsAdj.intValue())
    		+ (paySocialInsSal == null ? 0 : paySocialInsSal.intValue()));
  }

	public String getPaySocialIns$() {
		int t = getPaySocialIns();
    int y = t/12;
    int m = t%12;
    return y > 0 ? CommonUtil.getString("person.paySocialIns.fmt.ym", new Integer[]{y,m}) 
    : CommonUtil.getString("person.paySocialIns.fmt.m", new Integer[]{m});
	}

	public boolean equals(Object obj) {
		Person po = (obj instanceof Person) ? (Person) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getBusId(), po.getBusId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId())
			&& CommonUtil.equals(getLineId(), po.getLineId())
			&& CommonUtil.equals(getLastModifierId(), po.getLastModifierId())
			&& CommonUtil.equals(getChkGroupId(), po.getChkGroupId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Person{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", workerId=").append(workerId)
			.append(", position=").append(getFkPosition())
			.append(", name=").append(name).append("}");
		return sb.toString();
	}

	public static Person getSafeObject(Person po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Person) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Person(po.getId());
		}
	}
}
