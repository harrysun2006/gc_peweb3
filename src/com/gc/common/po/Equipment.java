package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.Constants;
import com.gc.util.CommonUtil;

public class Equipment {

	private Integer id;

	private Branch branch;

	private String useId;

	private EquType type;

	private String name;

	private String prodData;

	private Calendar buyDate;

	private Calendar insureDate;

	private Double buyPrice;

	private Integer manufacturer;

	private Integer provider;

	private Integer supporter;

	private Integer serviceProtocol;

	private String config;

	private String planUse;

	private Integer standByCheckPart;

	private String checkContain;

	private Integer maintainReq;

	private Integer sponsor;

	private Integer status;

	private Calendar downDate;

	private String comment;

	private Calendar authDate;

	private String authNo;

	private Calendar productDate;

	private String color;

	private String powerType;

	private Double inPower1;

	private Double inPower2;

	private String archNo;

	private Double length;

	private Double width;

	private Double height;

	private Double weight;

	private Double config1;

	private Double config2;

	private Double config3;

	private Double config4;

	private Double config5;

	private Double outPower1;

	private Double outPower2;

	private String spareUse1;

	private String spareUse2;

	private String spareUse3;

	private String spareUse4;

	private String spareUse5;

	private String acType;

	private String acNo;

	private Calendar checkDate;

	private Calendar nextCheckDate;

	private String regNo;

	private String regType;

	private String imports;

	private String brand;

	private String turnType;

	private Double innerSize;

	private Double springNum;

	private Double netWeight;

	private Double loading;

	private Double drawing;

	private String certName;

	private String certNo;

	private String impCertName;

	private String impCertNo;

	private String taxCertName;

	private String cmt1;

	private String cmt2;

	private String cmt3;

	private Double cmt4;

	private Double cmt5;

	private Double cmt6;

	private Calendar lastMntDate;

	private Double lastMntLc;

	private Double totalLc;

	private String equBelong;

	private String emission;

	private String roadWayNo;

	// È¡EquOnline.config
	private String shift; // 0/null:Ë«°à; 1:µ¥°à

	public Equipment() {
	}

	public Equipment(Integer id) {
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

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public Integer getTypeId() {
		return (type == null) ? null : type.getId();
	}

	public EquType getType() {
		return EquType.getSafeObject(type);
	}

	public void setType(EquType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProdData() {
		return prodData;
	}

	public void setProdData(String prodData) {
		this.prodData = prodData;
	}

	public Calendar getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Calendar buyDate) {
		this.buyDate = buyDate;
	}

	public Calendar getInsureDate() {
		return insureDate;
	}

	public void setInsureDate(Calendar insureDate) {
		this.insureDate = insureDate;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getProvider() {
		return provider;
	}

	public void setProvider(Integer provider) {
		this.provider = provider;
	}

	public Integer getSupporter() {
		return supporter;
	}

	public void setSupporter(Integer supporter) {
		this.supporter = supporter;
	}

	public Integer getServiceProtocol() {
		return serviceProtocol;
	}

	public void setServiceProtocol(Integer serviceProtocol) {
		this.serviceProtocol = serviceProtocol;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getPlanUse() {
		return planUse;
	}

	public void setPlanUse(String planUse) {
		this.planUse = planUse;
	}

	public Integer getStandByCheckPart() {
		return standByCheckPart;
	}

	public void setStandByCheckPart(Integer standByCheckPart) {
		this.standByCheckPart = standByCheckPart;
	}

	public String getCheckContain() {
		return checkContain;
	}

	public void setCheckContain(String checkContain) {
		this.checkContain = checkContain;
	}

	public Integer getMaintainReq() {
		return maintainReq;
	}

	public void setMaintainReq(Integer maintainReq) {
		this.maintainReq = maintainReq;
	}

	public Integer getSponsor() {
		return sponsor;
	}

	public void setSponsor(Integer sponsor) {
		this.sponsor = sponsor;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Calendar getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Calendar authDate) {
		this.authDate = authDate;
	}

	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	public Calendar getProductDate() {
		return productDate;
	}

	public void setProductDate(Calendar productDate) {
		this.productDate = productDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	public Double getInPower1() {
		return inPower1;
	}

	public void setInPower1(Double inPower1) {
		this.inPower1 = inPower1;
	}

	public Double getInPower2() {
		return inPower2;
	}

	public void setInPower2(Double inPower2) {
		this.inPower2 = inPower2;
	}

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getConfig1() {
		return config1;
	}

	public void setConfig1(Double config1) {
		this.config1 = config1;
	}

	public Double getConfig2() {
		return config2;
	}

	public void setConfig2(Double config2) {
		this.config2 = config2;
	}

	public Double getConfig3() {
		return config3;
	}

	public void setConfig3(Double config3) {
		this.config3 = config3;
	}

	public Double getConfig4() {
		return config4;
	}

	public void setConfig4(Double config4) {
		this.config4 = config4;
	}

	public Double getConfig5() {
		return config5;
	}

	public void setConfig5(Double config5) {
		this.config5 = config5;
	}

	public Double getOutPower1() {
		return outPower1;
	}

	public void setOutPower1(Double outPower1) {
		this.outPower1 = outPower1;
	}

	public Double getOutPower2() {
		return outPower2;
	}

	public void setOutPower2(Double outPower2) {
		this.outPower2 = outPower2;
	}

	public String getSpareUse1() {
		return spareUse1;
	}

	public void setSpareUse1(String spareUse1) {
		this.spareUse1 = spareUse1;
	}

	public String getSpareUse2() {
		return spareUse2;
	}

	public void setSpareUse2(String spareUse2) {
		this.spareUse2 = spareUse2;
	}

	public String getSpareUse3() {
		return spareUse3;
	}

	public void setSpareUse3(String spareUse3) {
		this.spareUse3 = spareUse3;
	}

	public String getSpareUse4() {
		return spareUse4;
	}

	public void setSpareUse4(String spareUse4) {
		this.spareUse4 = spareUse4;
	}

	public String getSpareUse5() {
		return spareUse5;
	}

	public void setSpareUse5(String spareUse5) {
		this.spareUse5 = spareUse5;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}

	public Calendar getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Calendar checkDate) {
		this.checkDate = checkDate;
	}

	public Calendar getNextCheckDate() {
		return nextCheckDate;
	}

	public void setNextCheckDate(Calendar nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getImports() {
		return imports;
	}

	public void setImports(String imports) {
		this.imports = imports;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getTurnType() {
		return turnType;
	}

	public void setTurnType(String turnType) {
		this.turnType = turnType;
	}

	public Double getInnerSize() {
		return innerSize;
	}

	public void setInnerSize(Double innerSize) {
		this.innerSize = innerSize;
	}

	public Double getSpringNum() {
		return springNum;
	}

	public void setSpringNum(Double springNum) {
		this.springNum = springNum;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getLoading() {
		return loading;
	}

	public void setLoading(Double loading) {
		this.loading = loading;
	}

	public Double getDrawing() {
		return drawing;
	}

	public void setDrawing(Double drawing) {
		this.drawing = drawing;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getImpCertName() {
		return impCertName;
	}

	public void setImpCertName(String impCertName) {
		this.impCertName = impCertName;
	}

	public String getImpCertNo() {
		return impCertNo;
	}

	public void setImpCertNo(String impCertNo) {
		this.impCertNo = impCertNo;
	}

	public String getTaxCertName() {
		return taxCertName;
	}

	public void setTaxCertName(String taxCertName) {
		this.taxCertName = taxCertName;
	}

	public String getCmt1() {
		return cmt1;
	}

	public void setCmt1(String cmt1) {
		this.cmt1 = cmt1;
	}

	public String getCmt2() {
		return cmt2;
	}

	public void setCmt2(String cmt2) {
		this.cmt2 = cmt2;
	}

	public String getCmt3() {
		return cmt3;
	}

	public void setCmt3(String cmt3) {
		this.cmt3 = cmt3;
	}

	public Double getCmt4() {
		return cmt4;
	}

	public void setCmt4(Double cmt4) {
		this.cmt4 = cmt4;
	}

	public Double getCmt5() {
		return cmt5;
	}

	public void setCmt5(Double cmt5) {
		this.cmt5 = cmt5;
	}

	public Double getCmt6() {
		return cmt6;
	}

	public void setCmt6(Double cmt6) {
		this.cmt6 = cmt6;
	}

	public Calendar getLastMntDate() {
		return lastMntDate;
	}

	public void setLastMntDate(Calendar lastMntDate) {
		this.lastMntDate = lastMntDate;
	}

	public Double getLastMntLc() {
		return lastMntLc;
	}

	public void setLastMntLc(Double lastMntLc) {
		this.lastMntLc = lastMntLc;
	}

	public Double getTotalLc() {
		return totalLc;
	}

	public void setTotalLc(Double totalLc) {
		this.totalLc = totalLc;
	}

	public String getEquBelong() {
		return equBelong;
	}

	public void setEquBelong(String equBelong) {
		this.equBelong = equBelong;
	}

	public String getEmission() {
		return emission;
	}

	public void setEmission(String emission) {
		this.emission = emission;
	}

	public String getRoadWayNo() {
		return roadWayNo;
	}

	public void setRoadWayNo(String roadWayNo) {
		this.roadWayNo = roadWayNo;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public boolean equals(Object obj) {
		Equipment po = (obj instanceof Equipment) ? (Equipment) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getTypeId(), po.getTypeId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Equipment{id=").append(id)
			.append(", belong=").append(getBranchId())
			.append(", authNo=").append(authNo)
			.append(", buyDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, buyDate))
			.append(", downDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, downDate)).append("}");
		return sb.toString();
	}

	public static Equipment getSafeObject(Equipment po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Equipment) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Equipment(po.getId());
		}
	}
}
