package com.gc.common.po;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.Constants;
import com.gc.hr.po.ChkGroup;
import com.gc.util.CommonUtil;

public class Department {

	private Integer id;

	private Branch branch;

	private String name;

	private String duty;

	private Calendar onDate;

	private Integer status;

	private Calendar downDate;

	private Person manager;

	private String national;

	private String state;

	private String city;

	private String zip;

	private String address;

	private String telephone;

	private String email;

	private String comment;

	private String officeTel;

	private String officeExt;

	private String officeFax;

	private String no;

	private List<Office> offices = new ArrayList<Office>();

	private List<Line> lines = new ArrayList<Line>();

	private List<EquOnline> equOnlines = new ArrayList<EquOnline>();

	private List<Equipment> equipments = new ArrayList<Equipment>();

	private List<ChkGroup> chkGroups = new ArrayList<ChkGroup>();

	public Department() {
	}

	public Department(Integer id) {
		setId(id);
	}

	public Department(Integer id, String name) {
		setId(id);
		setName(name);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
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

	public Integer getManagerId() {
		return (manager == null) ? null : manager.getId();
	}

	public Person getManager() {
		return Person.getSafeObject(manager);
	}

	public void setManager(Person manager) {
		this.manager = manager;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public List<Office> getOffices() {
		return offices;
	}

	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	public void addOffice(Office office) {
		offices.add(office);
	}

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public void addLine(Line line) {
		lines.add(line);
	}

	public List<EquOnline> getEquOnlines() {
		return equOnlines;
	}

	public void setEquOnlines(List<EquOnline> equOnlines) {
		this.equOnlines = equOnlines;
	}

	public void addEquOnline(EquOnline equOnline) {
		equOnlines.add(equOnline);
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public void addEquipment(Equipment equipment) {
		equipments.add(equipment);
	}

	public List<ChkGroup> getCheckGroups() {
		return chkGroups;
	}

	public void setCheckGroups(List<ChkGroup> chkGroups) {
		this.chkGroups = chkGroups;
	}

	public void addCheckGroup(ChkGroup group) {
		chkGroups.add(group);
	}

	public boolean equals(Object obj) {
		Department po = (obj instanceof Department) ? (Department) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getManagerId(), po.getManagerId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Department{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", name=").append(name)
			.append(", onDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, onDate))
			.append(", downDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, downDate)).append("}");
		return sb.toString();
	}

	public static Department getSafeObject(Department po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Department) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Department(po.getId());
		}
	}
}