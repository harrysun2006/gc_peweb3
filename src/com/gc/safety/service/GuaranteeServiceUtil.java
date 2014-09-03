package com.gc.safety.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.gc.common.po.Branch;
import com.gc.common.po.SecurityLimit;
import com.gc.common.service.BaseServiceUtil;
import com.gc.safety.po.GuaType;
import com.gc.safety.po.GuarInfo;
import com.gc.safety.po.GuarInfoPK;
import com.gc.safety.po.Guarantee;
import com.gc.safety.po.Insurer;
import com.gc.util.SpringUtil;

public class GuaranteeServiceUtil {
	public static final String BEAN_NAME = "safetyGuaranteeServiceUtil";
	
	private GuaranteeService guaranteeService;
	
	public static GuaranteeService getGuaranteeService(){
		ApplicationContext ctx = SpringUtil.getContext();
		GuaranteeServiceUtil util = (GuaranteeServiceUtil)ctx.getBean(BEAN_NAME);
		GuaranteeService service = util.guaranteeService;
		return service;
	}
	
	public void setGuaranteeService(GuaranteeService guaranteeService) {
		this.guaranteeService = guaranteeService;
	}
	
//==================================== Guarantee ====================================

	public static Guarantee getGuaranteeById(Long id) {
		return getGuaranteeService().getGuaranteeById(id);
	}
	
	public static List<Guarantee> getCurrentGuarantee(Integer branchId) {
		return getGuaranteeService().getCurrentGuarantee(branchId);
	}
	
	public static void addGuarantee(Guarantee guarantee) {
		getGuaranteeService().addGuarantee(guarantee);
	}
		
	public static void updateGuarantee(Guarantee guarantee) {
		getGuaranteeService().updateGuarantee(guarantee);
	}
	
	public static void deleteGuarantee(Guarantee guarantee) {
		getGuaranteeService().deleteGuarantee(guarantee);
	}
	
	public static int saveGua(Guarantee guarantee,Object obj) {
		return getGuaranteeService().saveGua(guarantee,obj);
	}
	
	//条件查询凭证号
	@SuppressWarnings("unchecked")
	public static List findGuarList(Map obj) {
		return getGuaranteeService().findGuarList(obj);
	}
	
	//凭证号和条件 查询
	@SuppressWarnings("unchecked")
	public static List findGuarAndInfoList(Map obj1,Map obj2) {
		return getGuaranteeService().findGuarAndInfoList(obj1,obj2);
	}
	
	//查询将要到失效日期的明细list
	@SuppressWarnings("unchecked")
	public static List findDownDateList(String date1,String date2,String branchId){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List findList(String accNo,Integer branchId){
		return getGuaranteeService().findList(accNo,branchId);
	}
	
	//结账后的保单
	public static List<Guarantee> getGuasByBCloseD(Integer branchId, Calendar closeDate) {
		return getGuaranteeService().getGuasByBCloseD(branchId, closeDate);
	}
	
	public static List<Guarantee> getGuaByAccNo(Integer branchId, String accNo, Calendar closeDate) {
		return getGuaranteeService().getGuaByAccNo(branchId, accNo, closeDate);
	}
	
//==================================== GuarInfo ====================================
	
	public static List<GuarInfo> getGuarInfo(Integer branchId) {
		return getGuaranteeService().getGuarInfo(branchId);
	}
	
	public static void addGuarInfo(GuarInfo guarInfo) {
		getGuaranteeService().addGuarInfo(guarInfo);
	}
	
	public static void saveGuarInfo(GuarInfo guarInfo) {
		getGuaranteeService().saveGuarInfo(guarInfo);
	}
	
	public static void updateGuarInfo(GuarInfo guarInfo) {
		getGuaranteeService().updateGuarInfo(guarInfo);
	}
	
	@SuppressWarnings("unchecked")
	public static void deleteGuarInfo(Map obj) {
		Integer branchId = Integer.parseInt(obj.get("branchId").toString());
		Branch branch = BaseServiceUtil.getBranch(branchId);	//机务
		String accNo = obj.get("accNo").toString();
		String no = obj.get("no").toString();
		GuarInfoPK id = new GuarInfoPK();
		id.setBranch(branch);
		id.setAccNo(accNo);
		id.setNo(Integer.parseInt(no));
		GuarInfo guarInfo = new GuarInfo(id);
		getGuaranteeService().deleteGaurInfo(guarInfo);
	}
	
	public static List<GuarInfo> getGuarInfoList(Integer branchId,Integer busId,Calendar accDate) {
		return getGuaranteeService().getGuarInfoList(branchId,busId, accDate);
	}
	
	public static List<GuarInfo> getGuarInfoByBND(Integer branchId, String accNo, Calendar closeDate) {
		return getGuaranteeService().getGuarInfoByBND(branchId, accNo, closeDate);
	}
	
	//将要失效保单
	public static List<GuarInfo> getMatureGuas(Integer branchId, Calendar date1, Calendar date2) {
		return getGuaranteeService().getMatureGuas(branchId, date1, date2);
	}
	//通过车 和 事故日期 查有效保单 
	public static List<GuarInfo> getGIsByBusIdAndAccDate(Integer branchId, Integer busId, Calendar accDate) {
		return getGuaranteeService().getGIsByBusIdAndAccDate(branchId, busId, accDate);
	}
	
	public static List<GuarInfo> getGuaInfosByDateFrom(SecurityLimit limit,String[] orderColumns, Calendar dateFrom) {
		return getGuaranteeService().getGuaInfosByDateFrom(limit, orderColumns, dateFrom);
	}
	
	//单车保单查询
	public static List<GuarInfo> getGIsByUid(Integer branchId,String useId,Calendar dateFrom,Calendar dateTo) {
		return getGuaranteeService().getGIsByUid(branchId, useId, dateFrom, dateTo);
	}
//==================================== GuaranteeType ====================================
	
	public static List<GuaType> getGuaTypes(Integer branchId) {
		return getGuaranteeService().getGuaTypes(branchId);
	}

	public static List<GuaType> getCurrentGuaranteeTypes(Integer branchId) {
		return getGuaranteeService().getCurrentGuaranteeTypes(branchId);
	}
	
	public static void addGuaranteeType(GuaType guaranteeType) {
		getGuaranteeService().addGuaranteeType(guaranteeType);
	}
	
	public static void saveGuaranteeType(GuaType guaranteeType) {
		getGuaranteeService().saveGuaranteeType(guaranteeType);
	}
	
	public static void updateGuaranteeType(GuaType guaranteeType) {
		getGuaranteeService().updateGuaranteeType(guaranteeType);
	}
	
	public static void deleteGuaranteeType(GuaType guaranteeType) {
		getGuaranteeService().deleteGuaranteeType(guaranteeType);
	}

//==================================== Insurer ====================================

	public static List<Insurer> getInsurers(Integer branchId) {
		return getGuaranteeService().getInsurers(branchId);
	}

	public static void addInsurer(Insurer insurer) {
		getGuaranteeService().addInsurer(insurer);
	}

	public static void saveInsurer(Insurer insurer) {
		getGuaranteeService().saveInsurer(insurer);
	}

	public static void updateInsurer(Insurer insurer) {
		getGuaranteeService().updateInsurer(insurer);
	}

	public static void deleteInsurer(Insurer insurer) {
		getGuaranteeService().deleteInsurer(insurer);
	}
}
