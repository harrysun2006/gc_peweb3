package com.gc.safety.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.gc.common.po.Department;
import com.gc.common.po.SecurityLimit;
import com.gc.safety.po.AccDuty;
import com.gc.safety.po.AccExtent;
import com.gc.safety.po.AccInPsn;
import com.gc.safety.po.AccInPsnPay;
import com.gc.safety.po.AccLevel;
import com.gc.safety.po.AccObject;
import com.gc.safety.po.AccOutObj;
import com.gc.safety.po.AccOutPsn;
import com.gc.safety.po.AccOutPsnPay;
import com.gc.safety.po.AccProcessor;
import com.gc.safety.po.AccType;
import com.gc.safety.po.Accident;
import com.gc.safety.po.Insurer;
import com.gc.util.SpringUtil;

public class AccidentServiceUtil {
	public static final String BEAN_NAME = "safetyAccidentServiceUtil";
	
	private AccidentService accidentService;

	public static AccidentService getAccidentService() {
		ApplicationContext ctx = SpringUtil.getContext();
		AccidentServiceUtil util = (AccidentServiceUtil) ctx.getBean(BEAN_NAME);
		AccidentService service = util.accidentService;
		return service;
	}

	public void setAccidentService(AccidentService accidentService) {
		this.accidentService = accidentService;
	}

//==================================== Accident ====================================
	public static List getAccAndOutByInsurer(Integer branchId, Insurer insurer) {
		return getAccidentService().getAccAndOutByInsurer(branchId, insurer);
	}
	
	public static List getAccAndInPsnListByInsurer(Integer branchId, Insurer insurer) {
		return getAccidentService().getAccAndInPsnListByInsurer(branchId, insurer);
	}
	
	public static List<Accident> getAccidentListByInsurerForClaims(Integer branchId, Insurer insurer) {
		return getAccidentService().getAccidentListByInsurerForClaims(branchId, insurer);
	}
	
	public static List<Accident> getAccidentByBranchId(Integer branchId) {
		return getAccidentService().getAccidentByBranchId(branchId);
	}
	
	public static List<Accident> getAccidentList(Accident accident) {
		return getAccidentService().getAccidentList(accident);
	}

//	@SuppressWarnings("unchecked")
//	public static List getAccidents(SecurityLimit limit, Integer isArchive) {
//		return getAccidentService().getAccidents(limit, isArchive);
//	}
	
	public static List<Accident> getAccidentsAndPayObject(Integer branchId) {
		return getAccidentService().getAccidentsAndPayObject(branchId);
	}
	
	public static void addAccident(Accident accident) {
		getAccidentService().addAccident(accident);
	}
	
	public static Accident saveAccident1(Accident oldData, Accident newData) {
		return getAccidentService().saveAccident1(oldData, newData);
	}
	
	// 建立 和 处理 保存事故
	public static Accident saveAccident(Accident accident, Accident oldData){
		return getAccidentService().saveAccident(accident, oldData);
	}
	//删除事故
	public static void deleteAccident(Accident oldData) {
		getAccidentService().deleteAccident(oldData);
	}
	
	//审核 保存事故
	public static void saveCAccident(Accident accident){
		getAccidentService().saveCAccident(accident);
	}
	
	public static void updateAccident(Accident accident){
		getAccidentService().updateAccident(accident);
	}
	
	
	public static List<Accident> getAccsByStatus01(Integer branchId,Department dept){
		return getAccidentService().getAccsByStatus01(branchId,dept);
	}
	
	// 车队处理、营运部审核 通过 No 查询事故
	public static List<Accident> getAccidentByNo(Integer branchId,String no) {
		return getAccidentService().getAccidentByNo(branchId,no);
	}
	
	// 存档 通过 事故 编号no ，closeDate 查询
	public List<Accident> getAccidentByNoForArch(Integer branchId, String no, Calendar closeDate) {
		return getAccidentService().getAccidentByNoForArch(branchId, no, closeDate);
	}
	
	public static List<?> getAccsAndObjPsnGuas(Integer branchId,Calendar closeDate) {
		return getAccidentService().getAccsAndObjPsnGuas(branchId,closeDate);
	}
	
	// 通过事故 id 查 理赔、赔付信息
	public static List getOutInGuaAndPays(Integer branchId, Integer accId) {
		return getAccidentService().getOutInGuaAndPays(branchId, accId);
	}
	
	//事故树1
	public static List<Accident> getAccidents(SecurityLimit limit,String[] ordersColumns) {
		return getAccidentService().getAccidents(limit, ordersColumns);
	}
	
	//事故树2
	public static List getAccsByDateFrom(SecurityLimit limit, Calendar dateFrom) {
		return getAccidentService().getAccsByDateFrom(limit, dateFrom);
	}
	
	public static List getPOPs(SecurityLimit limit) {
		return getAccidentService().getPOPs(limit);
	}
	
	public static List getPayList(SecurityLimit limit) {
		return getAccidentService().getPayList(limit);
	}
	
	//单车、单人，事故查询
	public static List getAccsByUIdOrWId(Integer branchId, String useId, String workerId, Calendar dateFrom, Calendar dateTo) {
		return getAccidentService().getAccsByUIdOrWId(branchId, useId, workerId, dateFrom, dateTo);
	}
	
	//所有事故条件查询
	public static List getAccsByAll(SecurityLimit limit, Map qo) {
		return getAccidentService().getAccsByAll(limit, qo);
	}
//==================================== AccType ====================================

	public static void addAccType(AccType accType) {
		getAccidentService().addAccType(accType);
	}

	public static void saveAccType(AccType accType) {
		getAccidentService().saveAccType(accType);
	}

	public static void updateAccType(AccType accType) {
		getAccidentService().updateAccType(accType);
	}

	public static void deleteAccType(AccType accType) {
		getAccidentService().deleteAccType(accType);
	}

	public static List<AccType> getAccTypes(Integer branchId) {
		return getAccidentService().getAccTypes(branchId);
	}

//==================================== AccLevel ====================================

	public static void addAccLevel(AccLevel accLevel) {
		getAccidentService().addAccLevel(accLevel);
	}
	public static List<AccLevel> getAccLevels(Integer branchId) {
		return getAccidentService().getAccLevels(branchId);
	}
	
	public static void saveAccLevel(AccLevel accLevel) {
		getAccidentService().saveAccLevel(accLevel);
	}
	
	public static void updateAccLevel(AccLevel accLevel) {
		getAccidentService().updateAccLevel(accLevel);
	}
	
	public static void deleteAccLevel(AccLevel accLevel) {
		getAccidentService().deleteAccLevel(accLevel);
	}

//==================================== AccDuty ====================================

	public static void addAccDuty(AccDuty accDuty) {
		getAccidentService().addAccDuty(accDuty);
	}

	public static List<AccDuty> getAccDutys(Integer branchId) {
		return getAccidentService().getAccDutys(branchId);
	}
	
	public static void saveAccDuty(AccDuty accDuty) {
		getAccidentService().saveAccDuty(accDuty);
	}
	
	public static void updateAccDuty(AccDuty accDuty) {
		getAccidentService().updateAccDuty(accDuty);
	}
	
	public static void deleteAccDuty(AccDuty accDuty) {
		getAccidentService().deleteAccDuty(accDuty);
	}

//==================================== AccExtent ====================================

	public static void addAccExtent(AccExtent accExtent) {
		getAccidentService().addAccExtent(accExtent);
	}
	public static List<AccExtent> getAccExtents(Integer branchId) {
		return getAccidentService().getAccExtents(branchId);
	}
	
	public static void saveAccExtent(AccExtent accExtent) {
		getAccidentService().saveAccExtent(accExtent);
	}
	
	public static void updateAccExtent(AccExtent accExtent) {
		getAccidentService().updateAccExtent(accExtent);
	}
	
	public static void deleteAccExtent(AccExtent accExtent) {
		getAccidentService().deleteAccExtent(accExtent);
	}

//==================================== AccPsn ====================================

//	public static List<AccPsn> getAccPsns(Integer branchId, String accidentNo) {
//		return getAccidentService().getAccPsns(branchId, accidentNo);
//	}
	
	@SuppressWarnings("unchecked")
	public static List getAccPsnsAndAccident(Integer branchId) {
		return getAccidentService().getAccPsnsAndAccident(branchId);
	}
	
//	public static void addAccPsn(AccPsn accPsn) {
//		getAccidentService().addAccPsn(accPsn);
//	}
	
//	public static void saveAccPsn(AccPsn accPsn) {
//		getAccidentService().saveAccPsn(accPsn);
//	}
	
//	public static void updateAccPsn(AccPsn accPsn) {
//		getAccidentService().updateAccPsn(accPsn);
//	}
	
//	public static void deleteAccPsn(AccPsn accPsn) {
//		getAccidentService().deleteAccPsn(accPsn);
//	}

//==================================== AccPayPsn ====================================

//	public static void addAccPayPsn(AccPayPsn accPayPsn) {
//		getAccidentService().addAccPayPsn(accPayPsn);
//	}
	
//	public static void saveAccPayPsn(AccPayPsn accPayPsn) {
//		getAccidentService().saveAccPayPsn(accPayPsn);
//	}
	
//	public static void updateAccPayPsn(AccPayPsn accPayPsn) {
//		getAccidentService().updateAccPayPsn(accPayPsn);
//	}
	
//	public static void deleteAccPayPsn(AccPayPsn accPayPsn) {
//		getAccidentService().deleteAccPayPsn(accPayPsn);
//	}

//==================================== AccObject ====================================

	public static void addAccObject(AccObject accObject) {
		getAccidentService().addAccObject(accObject);
	}

	public static List<AccObject> getAccObjects(Integer branchId) {
		return getAccidentService().getAccObjects(branchId);
	}

	public static void saveAccObject(AccObject accObject) {
		getAccidentService().saveAccObject(accObject);
	}

	public static void updateAccObject(AccObject accObject) {
		getAccidentService().updateAccObject(accObject);
	}

	public static void deleteAccObject(AccObject accObject) {
		getAccidentService().deleteAccObject(accObject);
	}

//==================================== AccPayObject ====================================

//	public static List<AccPayObject> getAccPayObjects(Integer branchId) {
//		return getAccidentService().getAccPayObjects(branchId);
//	}
	
	@SuppressWarnings("unchecked")
	public static List getAccPayObjectsAndAccident(Integer branchId) {
		return getAccidentService().getAccPayObjectsAndAccident(branchId);
	}
	
//	public static void addAccPayObject(AccPayObject apo) {
//		getAccidentService().addAccPayObject(apo);
//	}
	
//	public static void saveAccPayObject(AccPayObject apo) {
//		getAccidentService().saveAccPayObject(apo);
//	}
	
//	public static void deleteAccPayObject(AccPayObject apo) {
//		getAccidentService().deleteAccPayObject(apo);
//	}

//==================================== AccProcessor ====================================

	public static void addAccProcessor(AccProcessor accProcessor) {
		getAccidentService().addAccProcessor(accProcessor);
	}
	public static List<AccProcessor> getAccProcessors(Integer branchId) {
		return getAccidentService().getAccProcessors(branchId);
	}
	
	public static void saveAccProcessor(AccProcessor accProcessor) {
		getAccidentService().saveAccProcessor(accProcessor);
	}
	
	public static void updateAccProcessor(AccProcessor accProcessor) {
		getAccidentService().updateAccProcessor(accProcessor);
	}
	
	public static void deleteAccProcessor(AccProcessor accProcessor) {
		getAccidentService().deleteAccProcessor(accProcessor);
	}
	
// =============================== AccOutPsn =============================
	public static void addAccOutPsn(AccOutPsn accOutPsn) {
		getAccidentService().addAccOutPsn(accOutPsn);
	}
	
	public static void delAccOutPsn(AccOutPsn accOutPsn) {
		getAccidentService().deleteAccOutPsn(accOutPsn);
	}
	
	public static void saveAccOutPsn(AccOutPsn accOutPsn) {
		getAccidentService().saveAccOutPsn(accOutPsn);
	}
	
	public static List<AccOutPsn> getAOPByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		return getAccidentService().getAOPByAccIdAndNo(branchId, accNo, no);
	}
	
//=============================== AccOutPsnPay =============================
	public static void addAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		getAccidentService().addAccOutPsnPay(accOutPsnPay);
	}
	
	public static void delAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		getAccidentService().deleteAccOutPsnPay(accOutPsnPay);
	}
	
	public static void saveAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		getAccidentService().saveAccOutPsnPay(accOutPsnPay);
	}
//================================ AccInPsn ==============================
	public static void addAccInPsn(AccInPsn accInPsn) {
		getAccidentService().addAccInPsn(accInPsn);
	}
	
	public static void delAccInPsn(AccInPsn accInPsn) {
		getAccidentService().deleteAccInPsn(accInPsn);
	}
	
	public static void saveAccInPsn(AccInPsn accInPsn) {
		getAccidentService().saveAccInPsn(accInPsn);
	}
	
	public static List<AccInPsn> getAIPByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		return getAccidentService().getAIPByAccIdAndNo(branchId, accNo, no);
	}
	
//================================ AccInPsnPay ==============================
	public static void addAccInPsnPay(AccInPsnPay accInPsnPay) {
		getAccidentService().addAccInPsnPay(accInPsnPay);
	}
	
	public static void delAccInPsnPay(AccInPsnPay accInPsnPay) {
		getAccidentService().deleteAccInPsnPay(accInPsnPay);
	}
	
	public static void saveAccInPsnPay(AccInPsnPay accInPsnPay) {
		getAccidentService().saveAccInPsnPay(accInPsnPay);
	}
// ================================ AccOutObj ==============================
	public static void addAccOutObj(AccOutObj accOutObj) {
		getAccidentService().addAccOutObj(accOutObj);
	}
	
	public static void delAccOutObj(AccOutObj accOutObj) {
		getAccidentService().deleteAccOutObj(accOutObj);
	}
	
	public static void saveAccOutObj(AccOutObj accOutObj) {
		getAccidentService().saveAccOutObj(accOutObj);
	}
	
	public static List<AccOutObj> getAOOByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		return getAccidentService().getAOOByAccIdAndNo(branchId, accNo, no);
	}
}
