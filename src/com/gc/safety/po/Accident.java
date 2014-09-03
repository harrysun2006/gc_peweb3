package com.gc.safety.po;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Equipment;
import com.gc.common.po.Line;
import com.gc.common.po.Person;
import com.gc.common.po.Weather;
import com.gc.util.CommonUtil;

public class Accident {
	private AccidentPK id;
	
	private String no;
	
	private Calendar date;
	
	private Department dept;
	
	private Line line;
	
	private Equipment bus;
	
	private Person driver;

	private AccLevel level;
	
	private AccType type;	
	
	private AccDuty duty;
	
	private Weather weather;
	
	private String address;
	
	private String roadFacility;
	
	private String desc1;
	
	private String desc2;
	
	private String desc3;
	
	private String reason;
	
	private String course;
	
	private String processInfo;

	private AccProcessor processor;
	
	private String policeNo;
	
	private Integer billNum;
	
	private Integer report;
	
	private AccExtent extent;
	
	private String destroy;
	
	private Double lost;
	
	private Integer status;

	private Person initor;
	
	private Calendar initDate;
	
	private String initDesc;
	
	private Person deptor;
	
	private Calendar deptDate;
	
	private String deptDesc;
	
	private Person compor;
	
	private Calendar compDate;
	
	private String compDesc;
	
	private Person archor;
	
	private Calendar archDate;
	
	private Set<AccOutPsn> accOutPsns = new HashSet<AccOutPsn>(0);
	
	private Set<AccInPsn> accInPsns = new HashSet<AccInPsn>(0);
	
	private Set<AccOutObj> accOutObjs = new HashSet<AccOutObj>(0);
	
	private Set<GuaReport> guaReports = new HashSet<GuaReport>(0);
	
	//状态
//	private String statusStr;
	
	//存档时显示事故列表 
//	private Double outPayFee;
//	private Double outGuaFee;
//	private Double outGuaPayFee;
//	private Double inPsnPayFee;
//	private Double inPsnGuaFee;
//	private Double inPsnGuaPayFee;
	
	//query condations
//	private Calendar dateFrom;
//	
//	private Calendar dateTo;
//	
//	private Calendar initDateFrom;
//	
//	private Calendar initDateTo;
//	
//	private Calendar deptDateFrom;
//	
//	private Calendar deptDateTo;
//	
//	private Calendar compDateFrom;
//	
//	private Calendar compDateTo;
//	
//	private Double lostFrom;
//	
//	private Double lostTo;
	
	public Accident() {}

	public Accident(Branch branch, Integer id) {
		setId(new AccidentPK(branch, id));
	}

	public Accident(Integer branchId, Integer id) {
		setId(new AccidentPK(new Branch(branchId), id));
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Integer getAccidentId() {
		return (id == null) ? null : id.getId();
	}

	public AccidentPK getId() {
		return AccidentPK.getSafeObject(id);
	}

	public void setId(AccidentPK id) {
		this.id = id;
	} 
	
	public Accident(AccidentPK id){
		setId(id);
	}
		
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Department getDept() {
		return Department.getSafeObject(dept);
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}
	
	public Integer getDeptId() {
		return (dept == null) ? null : dept.getId();
	}

	public Line getLine() {
		return Line.getSafeObject(line);
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
	public Integer getLineId() {
		return (line == null) ? null : line.getId();
	}

	public Equipment getBus() {
		return Equipment.getSafeObject(bus);
	}

	public void setBus(Equipment bus) {
		this.bus = bus;
	}
	
	public Integer getBusId() {
		return (bus == null) ? null : bus.getId();
	}

	public Person getDriver() {
		return Person.getSafeObject(driver);
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}
	
	public Integer getDriverId() {
		return (driver == null) ? null : driver.getId();
	}

	public AccLevel getLevel() {
		return AccLevel.getSafeObject(level);
	}
	
	public Long getLevelId() {
		return (level == null) ? null : level.getId();
	}

	public void setLevel(AccLevel level) {
		this.level = level;
	}

	public AccType getType() {
		return AccType.getSafeObject(type);
	}
	
	public Long getTypeId() {
		return (type == null) ? null : type.getId();
	}

	public void setType(AccType type) {
		this.type = type;
	}

	public AccDuty getDuty() {
		return AccDuty.getSafeObject(duty);
	}
	
	public Long getDutyId() {
		return (duty == null) ? null : duty.getId();
	}

	public void setDuty(AccDuty duty) {
		this.duty = duty;
	}

	public Weather getWeather() {
		return Weather.getSafeObject(weather);
	}
	
	public Long getWeatherId() {
		return (weather == null) ? null : weather.getId();
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoadFacility() {
		return roadFacility;
	}

	public void setRoadFacility(String roadFacility) {
		this.roadFacility = roadFacility;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getDesc3() {
		return desc3;
	}

	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}

	public AccProcessor getProcessor() {
		return AccProcessor.getSafeObject(processor);
	}
	
	public Long getProcessorId() {
		return (processor == null) ? null : processor.getId();
	}

	public void setProcessor(AccProcessor processor) {
		this.processor = processor;
	}

	public String getPoliceNo() {
		return policeNo;
	}

	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}

	public Integer getBillNum() {
		return billNum;
	}

	public void setBillNum(Integer billNum) {
		this.billNum = billNum;
	}

	public Integer getReport() {
		return report;
	}

	public void setReport(Integer report) {
		this.report = report;
	}

	public AccExtent getExtent() {
		return AccExtent.getSafeObject(extent);
	}
	
	public Long getExtentId() {
		return (extent == null) ? null : extent.getId();
	}

	public void setExtent(AccExtent extent) {
		this.extent = extent;
	}

	public String getDestroy() {
		return destroy;
	}

	public void setDestroy(String destroy) {
		this.destroy = destroy;
	}

	public Double getLost() {
		return lost;
	}

	public void setLost(Double lost) {
		this.lost = lost;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Person getInitor() {
		return Person.getSafeObject(initor);
	}
	
	public Integer getInitorId() {
		return (initor == null) ? null : initor.getId();
	}

	public void setInitor(Person initor) {
		this.initor = initor;
	}

	public Calendar getInitDate() {
		return initDate;
	}

	public void setInitDate(Calendar initDate) {
		this.initDate = initDate;
	}

	public String getInitDesc() {
		return initDesc;
	}

	public void setInitDesc(String initDesc) {
		this.initDesc = initDesc;
	}

	public Person getDeptor() {
		return Person.getSafeObject(deptor);
	}
	
	public Integer getDeptorId() {
		return (deptor == null) ? null : deptor.getId();
	}

	public void setDeptor(Person deptor) {
		this.deptor = deptor;
	}

	public Calendar getDeptDate() {
		return deptDate;
	}

	public void setDeptDate(Calendar deptDate) {
		this.deptDate = deptDate;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Person getCompor() {
		return Person.getSafeObject(compor);
	}
	
	public Integer getComporId() {
		return (compor == null) ? null : compor.getId();
	}

	public void setCompor(Person compor) {
		this.compor = compor;
	}

	public Calendar getCompDate() {
		return compDate;
	}

	public void setCompDate(Calendar compDate) {
		this.compDate = compDate;
	}

	public String getCompDesc() {
		return compDesc;
	}

	public void setCompDesc(String compDesc) {
		this.compDesc = compDesc;
	}

	public Person getArchor() {
		return Person.getSafeObject(archor);
	}
	
	public Integer getArchorId() {
		return (archor == null) ? null : archor.getId();
	}

	public void setArchor(Person archor) {
		this.archor = archor;
	}

	public Calendar getArchDate() {
		return archDate;
	}

	public void setArchDate(Calendar archDate) {
		this.archDate = archDate;
	}

	public Set<AccOutPsn> getAccOutPsns() {
		return accOutPsns;
	}
	
	public void setAccOutPsns(Set<AccOutPsn> accOutPsns) {
		this.accOutPsns = accOutPsns;
	}

	public Set<AccInPsn> getAccInPsns() {
		return accInPsns;
	}

	public void setAccInPsns(Set<AccInPsn> accInPsns) {
		this.accInPsns = accInPsns;
	}

	public Set<AccOutObj> getAccOutObjs() {
		return accOutObjs;
	}

	public void setAccOutObjs(Set<AccOutObj> accOutObjs) {
		this.accOutObjs = accOutObjs;
	}

	public Set<GuaReport> getGuaReports() {
		return guaReports;
	}

	public void setGuaReports(Set<GuaReport> guaReports) {
		this.guaReports = guaReports;
	}
	
	//--------------status
//	public String getStatusStr() {
//		return statusStr;
//	}
//
//	public void setStatusStr(String statusStr) {
//		this.statusStr = statusStr;
//	}
	
	//--------理赔，赔付费用
//	public Double getOutPayFee() {
//		return outPayFee;
//	}
//
//	public void setOutPayFee(Double outPayFee) {
//		this.outPayFee = outPayFee;
//	}
//
//	public Double getOutGuaFee() {
//		return outGuaFee;
//	}
//
//	public void setOutGuaFee(Double outGuaFee) {
//		this.outGuaFee = outGuaFee;
//	}
//
//	public Double getOutGuaPayFee() {
//		return outGuaPayFee;
//	}
//
//	public void setOutGuaPayFee(Double outGuaPayFee) {
//		this.outGuaPayFee = outGuaPayFee;
//	}
//
//	public Double getInPsnPayFee() {
//		return inPsnPayFee;
//	}
//
//	public void setInPsnPayFee(Double inPsnPayFee) {
//		this.inPsnPayFee = inPsnPayFee;
//	}
//
//	public Double getInPsnGuaFee() {
//		return inPsnGuaFee;
//	}
//
//	public void setInPsnGuaFee(Double inPsnGuaFee) {
//		this.inPsnGuaFee = inPsnGuaFee;
//	}
//
//	public Double getInPsnGuaPayFee() {
//		return inPsnGuaPayFee;
//	}
//
//	public void setInPsnGuaPayFee(Double inPsnGuaPayFee) {
//		this.inPsnGuaPayFee = inPsnGuaPayFee;
//	}
//	
//	//--------------query
//	public Calendar getDateFrom() {
//		return dateFrom;
//	}
//
//	public void setDateFrom(Calendar dateFrom) {
//		this.dateFrom = dateFrom;
//	}
//
//	public Calendar getDateTo() {
//		return dateTo;
//	}
//
//	public void setDateTo(Calendar dateTo) {
//		this.dateTo = dateTo;
//	}
//
//	public Calendar getInitDateFrom() {
//		return initDateFrom;
//	}
//
//	public void setInitDateFrom(Calendar initDateFrom) {
//		this.initDateFrom = initDateFrom;
//	}
//
//	public Calendar getInitDateTo() {
//		return initDateTo;
//	}
//
//	public void setInitDateTo(Calendar initDateTo) {
//		this.initDateTo = initDateTo;
//	}
//
//	public Calendar getDeptDateFrom() {
//		return deptDateFrom;
//	}
//
//	public void setDeptDateFrom(Calendar deptDateFrom) {
//		this.deptDateFrom = deptDateFrom;
//	}
//
//	public Calendar getDeptDateTo() {
//		return deptDateTo;
//	}
//
//	public void setDeptDateTo(Calendar deptDateTo) {
//		this.deptDateTo = deptDateTo;
//	}
//
//	public Calendar getCompDateFrom() {
//		return compDateFrom;
//	}
//
//	public void setCompDateFrom(Calendar compDateFrom) {
//		this.compDateFrom = compDateFrom;
//	}
//
//	public Calendar getCompDateTo() {
//		return compDateTo;
//	}
//
//	public void setCompDateTo(Calendar compDateTo) {
//		this.compDateTo = compDateTo;
//	}
//
//	public Double getLostFrom() {
//		return lostFrom;
//	}
//
//	public void setLostFrom(Double lostFrom) {
//		this.lostFrom = lostFrom;
//	}
//
//	public Double getLostTo() {
//		return lostTo;
//	}
//
//	public void setLostTo(Double lostTo) {
//		this.lostTo = lostTo;
//	}
	
	public boolean equals(Object obj) {
		Accident po = (obj instanceof Accident) ? (Accident) obj : null;
		return CommonUtil.equals(this, po)
				&& CommonUtil.equals(getBranchId(), po.getBranchId())
				&& CommonUtil.equals(getDeptId(), po.getDeptId())
				&& CommonUtil.equals(getBusId(), po.getBusId())
				&& CommonUtil.equals(getDriverId(), po.getDriverId())
				&& CommonUtil.equals(getLevelId(), po.getLevelId())
				&& CommonUtil.equals(getLineId(), po.getLineId())
				&& CommonUtil.equals(getTypeId(), po.getTypeId())
				&& CommonUtil.equals(getDutyId(), po.getDutyId())
				&& CommonUtil.equals(getWeatherId(), po.getWeatherId())
				&& CommonUtil.equals(getProcessorId(), po.getProcessorId())
				&& CommonUtil.equals(getExtentId(), po.getExtentId())
				&& CommonUtil.equals(getInitorId(), po.getInitorId())
				&& CommonUtil.equals(getDeptorId(), po.getDeptorId())
				&& CommonUtil.equals(getComporId(), po.getComporId())
				&& CommonUtil.equals(getArchorId(), po.getArchorId());
	}

	public static Accident getSafeObject(Accident id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (Accident) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new Accident(id.getId());
		}
	}
}
