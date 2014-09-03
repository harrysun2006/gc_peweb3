package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.Constants;
import com.gc.util.CommonUtil;

public class Line {

	private Integer id;

	private Branch branch;

	private String no;

	private String name;

	private String desNo;

	private String authNo;

	private String dealType;

	private String lineType;

	private String busModel;

	private String onReason;

	private Calendar onDate;

	private Calendar downDate;

	private String downReason;

	private Integer mustBus;

	private String local;

	private String lineDirect;

	private Double lc;

	private String workTime;

	private Integer intCrowd;

	private Integer intQuiet;

	private String ticketMode1;

	private String ticketMode2;

	private String ticketMode3;

	private Double lowPrice;

	private Double highPrice;

	private Department depart;

	private String team;

	private Integer responsor;

	private Integer attemper;

	private String userDef1;

	private String userDef2;

	private String userDef3;

	private Double length;

	private Double cycleTime;

	private String startTime;

	private String endTime;

	private Double emptyLc;

	public Line() {
	}

	public Line(Integer id) {
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesNo() {
		return desNo;
	}

	public void setDesNo(String desNo) {
		this.desNo = desNo;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getBusModel() {
		return busModel;
	}

	public void setBusModel(String busModel) {
		this.busModel = busModel;
	}

	public String getOnReason() {
		return onReason;
	}

	public void setOnReason(String onReason) {
		this.onReason = onReason;
	}

	public Calendar getOnDate() {
		return onDate;
	}

	public void setOnDate(Calendar onDate) {
		this.onDate = onDate;
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

	public Integer getMustBus() {
		return mustBus;
	}

	public void setMustBus(Integer mustBus) {
		this.mustBus = mustBus;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getLineDirect() {
		return lineDirect;
	}

	public void setLineDirect(String lineDirect) {
		this.lineDirect = lineDirect;
	}

	public Double getLc() {
		return lc;
	}

	public void setLc(Double lc) {
		this.lc = lc;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public Integer getIntCrowd() {
		return intCrowd;
	}

	public void setIntCrowd(Integer intCrowd) {
		this.intCrowd = intCrowd;
	}

	public Integer getIntQuiet() {
		return intQuiet;
	}

	public void setIntQuiet(Integer intQuiet) {
		this.intQuiet = intQuiet;
	}

	public String getTicketMode1() {
		return ticketMode1;
	}

	public void setTicketMode1(String ticketMode1) {
		this.ticketMode1 = ticketMode1;
	}

	public String getTicketMode2() {
		return ticketMode2;
	}

	public void setTicketMode2(String ticketMode2) {
		this.ticketMode2 = ticketMode2;
	}

	public String getTicketMode3() {
		return ticketMode3;
	}

	public void setTicketMode3(String ticketMode3) {
		this.ticketMode3 = ticketMode3;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
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

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Integer getResponsor() {
		return responsor;
	}

	public void setResponsor(Integer responsor) {
		this.responsor = responsor;
	}

	public Integer getAttemper() {
		return attemper;
	}

	public void setAttemper(Integer attemper) {
		this.attemper = attemper;
	}

	public String getUserDef1() {
		return userDef1;
	}

	public void setUserDef1(String userDef1) {
		this.userDef1 = userDef1;
	}

	public String getUserDef2() {
		return userDef2;
	}

	public void setUserDef2(String userDef2) {
		this.userDef2 = userDef2;
	}

	public String getUserDef3() {
		return userDef3;
	}

	public void setUserDef3(String userDef3) {
		this.userDef3 = userDef3;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(Double cycleTime) {
		this.cycleTime = cycleTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Double getEmptyLc() {
		return emptyLc;
	}

	public void setEmptyLc(Double emptyLc) {
		this.emptyLc = emptyLc;
	}

	public boolean equals(Object obj) {
		Line po = (obj instanceof Line) ? (Line) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Line{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", name=").append(name)
			.append(", onDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, onDate))
			.append(", downDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, downDate)).append("}");
		return sb.toString();
	}

	public static Line getSafeObject(Line po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Line) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Line(po.getId());
		}
	}
}
