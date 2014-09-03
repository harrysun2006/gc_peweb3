package com.gc.common.po;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.Constants;
import com.gc.safety.po.Accident;
import com.gc.util.CommonUtil;

public class EquOnline {

	private Branch branch;
	
	private Integer id;
	
	private Equipment equipment;
	
	private Person installer;
	
	private Person responsor;
	
	private String config;
	
	private Department depart;
	
	private String address;
	
	private String useLimits;
	
	private String configChange;
	
	private Integer isMainEqu;
	
	private Integer soft;
	
	private Calendar onDate;
	
	private Calendar downDate;
	
	private String onReason;
	
	private String downReason;
	
	private Integer checkPart;
	
	private String checkContain;
	
	private Calendar lastCheckDate;
	
	private String comment;
	
	private Integer fromStatus;
	
	private Integer toStatus;
	
	private Department downDepart;
	
	private Person installor;
	
	private String whither;
	
	private String downComment;
	
	private String team;
	
	private Line line;
	
	private Integer park;
	
	private String monthMNT;
	
	private String weekMNT;

	private Double fuel;
	
	private Double lube;
	
	private String type;

	private String planUse;
	
	//事故树使用
	private List<Accident> accs = new ArrayList<Accident>();
	
	//车的里程
	private Double mileage;


	public EquOnline() {
	}

	public EquOnline(Integer id) {
		setId(id);
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEquipmentId() {
		return (equipment == null) ? null : equipment.getId();
	}

	public Equipment getEquipment() {
		return Equipment.getSafeObject(equipment);
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Integer getInstallerId() {
		return (installer == null) ? null : installer.getId();
	}

	public Person getInstaller() {
		return Person.getSafeObject(installer);
	}

	public void setInstaller(Person installer) {
		this.installer = installer;
	}

	public Integer getResponsorId() {
		return (responsor == null) ? null : responsor.getId();
	}

	public Person getResponsor() {
		return Person.getSafeObject(responsor);
	}

	public void setResponsor(Person responsor) {
		this.responsor = responsor;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUseLimits() {
		return useLimits;
	}

	public void setUseLimits(String useLimits) {
		this.useLimits = useLimits;
	}

	public String getConfigChange() {
		return configChange;
	}

	public void setConfigChange(String configChange) {
		this.configChange = configChange;
	}

	public Integer getIsMainEqu() {
		return isMainEqu;
	}

	public void setIsMainEqu(Integer isMainEqu) {
		this.isMainEqu = isMainEqu;
	}

	public Integer getSoft() {
		return soft;
	}

	public void setSoft(Integer soft) {
		this.soft = soft;
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

	public String getOnReason() {
		return onReason;
	}

	public void setOnReason(String onReason) {
		this.onReason = onReason;
	}

	public String getDownReason() {
		return downReason;
	}

	public void setDownReason(String downReason) {
		this.downReason = downReason;
	}

	public Integer getCheckPart() {
		return checkPart;
	}

	public void setCheckPart(Integer checkPart) {
		this.checkPart = checkPart;
	}

	public String getCheckContain() {
		return checkContain;
	}

	public void setCheckContain(String checkContain) {
		this.checkContain = checkContain;
	}

	public Calendar getLastCheckDate() {
		return lastCheckDate;
	}

	public void setLastCheckDate(Calendar lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(Integer fromStatus) {
		this.fromStatus = fromStatus;
	}

	public Integer getToStatus() {
		return toStatus;
	}

	public void setToStatus(Integer toStatus) {
		this.toStatus = toStatus;
	}

	public Integer getDownDepartId() {
		return (downDepart == null) ? null : downDepart.getId();
	}

	public Department getDownDepart() {
		return Department.getSafeObject(downDepart);
	}

	public void setDownDepart(Department downDepart) {
		this.downDepart = downDepart;
	}

	public Integer getInstallorId() {
		return (installor == null) ? null : installor.getId();
	}

	public Person getInstallor() {
		return Person.getSafeObject(installor);
	}

	public void setInstallor(Person installor) {
		this.installor = installor;
	}

	public String getWhither() {
		return whither;
	}

	public void setWhither(String whither) {
		this.whither = whither;
	}

	public String getDownComment() {
		return downComment;
	}

	public void setDownComment(String downComment) {
		this.downComment = downComment;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
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

	public Integer getPark() {
		return park;
	}

	public void setPark(Integer park) {
		this.park = park;
	}

	public String getMonthMNT() {
		return monthMNT;
	}

	public void setMonthMNT(String monthMNT) {
		this.monthMNT = monthMNT;
	}

	public String getWeekMNT() {
		return weekMNT;
	}

	public void setWeekMNT(String weekMNT) {
		this.weekMNT = weekMNT;
	}

	public Double getFuel() {
		return fuel;
	}

	public void setFuel(Double fuel) {
		this.fuel = fuel;
	}

	public Double getLube() {
		return lube;
	}

	public void setLube(Double lube) {
		this.lube = lube;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlanUse() {
		return planUse;
	}

	public void setPlanUse(String planUse) {
		this.planUse = planUse;
	}
	
	public List<Accident> getAccs() {
		return accs;
	}

	public void setAccs(List<Accident> accs) {
		this.accs = accs;
	}
	
	public void addAcc(Accident accident) {
		accs.add(accident);
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	
	public boolean equals(Object obj) {
		EquOnline po = (obj instanceof EquOnline) ? (EquOnline) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getEquipmentId(), po.getEquipmentId())
			&& CommonUtil.equals(getInstallerId(), po.getInstallerId())
			&& CommonUtil.equals(getResponsorId(), po.getResponsorId())
			&& CommonUtil.equals(getDepartId(), po.getDepartId())
			&& CommonUtil.equals(getDownDepartId(), po.getDownDepartId())
			&& CommonUtil.equals(getInstallorId(), po.getInstallorId())
			&& CommonUtil.equals(getLineId(), po.getLineId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EquOnline{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", depart=").append(getDepartId())
			.append(", onDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, onDate))
			.append(", downDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, downDate)).append("}");
		return sb.toString();
	}

	public static EquOnline getSafeObject(EquOnline po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (EquOnline) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new EquOnline(po.getId());
		}
	}
}
