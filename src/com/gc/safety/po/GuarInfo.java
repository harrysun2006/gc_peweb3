package com.gc.safety.po;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Equipment;
import com.gc.util.CommonUtil;

public class GuarInfo {
	private GuarInfoPK id;
	
	private Equipment bus;
	
	private String lineNo;
	
	private String useId;

	private String type;
	
	private Integer sits;
	
	private String archNo;
	
	private String powerNo;
	
	private String guaNo;
	
	private String guaDesc;
	
	private Double guaCost;
	
	private Double fee;
	
	private Guarantee fkGuarantee;
	
	private String reportNo;
	
	//本车的保单列表
	private List<GuarInfo> gis = new ArrayList<GuarInfo>();
	//本车的理赔列表
	private List ags = new ArrayList();
	//本车的赔付列表
	private List apgs = new ArrayList();

	public GuarInfo() {}
	
	public GuarInfo(GuarInfoPK id) {
		setId(id);
	}
	
	public GuarInfo(Branch branch, String accNo,Integer no) {
		setId(new GuarInfoPK(branch,accNo,no));
	}
	
	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}
	
	public GuarInfoPK getId() {
		return GuarInfoPK.getSafeObject(id);
	}

	public void setId(GuarInfoPK id) {
		this.id = id;
	}

	public Equipment getBus() {
		return Equipment.getSafeObject(bus);
	}

	public void setBus(Equipment bus) {
		this.bus = bus;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSits() {
		return sits;
	}

	public void setSits(Integer sits) {
		this.sits = sits;
	}

	public String getArchNo() {
		return archNo;
	}

	public void setArchNo(String archNo) {
		this.archNo = archNo;
	}

	public String getPowerNo() {
		return powerNo;
	}

	public void setPowerNo(String powerNo) {
		this.powerNo = powerNo;
	}

	public String getGuaNo() {
		return guaNo;
	}

	public void setGuaNo(String guaNo) {
		this.guaNo = guaNo;
	}

	public String getGuaDesc() {
		return guaDesc;
	}

	public void setGuaDesc(String guaDesc) {
		this.guaDesc = guaDesc;
	}

	public Double getGuaCost() {
		return guaCost;
	}

	public void setGuaCost(Double guaCost) {
		this.guaCost = guaCost;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	public Guarantee getFkGuarantee() {
		return Guarantee.getSafeObject(fkGuarantee);
	}

	public void setFkGuarantee(Guarantee fkGuarantee) {
		this.fkGuarantee = fkGuarantee;
	}
	
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public List<GuarInfo> getGis() {
		return gis;
	}

	public void setGis(List<GuarInfo> gis) {
		this.gis = gis;
	}
	
	public void addGi(GuarInfo gi) {
		gis.add(gi);
	}
	
	public List getAgs() {
		return ags;
	}

	public void setAgs(List ags) {
		this.ags = ags;
	}
	
	public void addag(Object ag) {
		ags.add(ag);
	}
	
	public List getApgs() {
		return apgs;
	}

	public void setApgs(List apgs) {
		this.apgs = apgs;
	}
	
	public void addapg(Object apg){
		apgs.add(apg);
	}
	
	public boolean equals(Object obj) {
		GuarInfo gInfo = (obj instanceof GuarInfo) ? (GuarInfo) obj : null;
		return CommonUtil.equals(this, gInfo)
			&& CommonUtil.equals(getId().getBranchId(), gInfo.getId().getBranchId())
			&& CommonUtil.equals(getBus().getId(), gInfo.getBus().getId());
	}
	
	public static GuarInfo getSafeObject(GuarInfo id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (GuarInfo)((HibernateProxy)id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null)	return null;
			else return new GuarInfo(id.getId());
		}
	}
}