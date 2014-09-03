package com.gc.safety.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gc.common.dao.BaseDAOHibernate;
import com.gc.common.po.Department;
import com.gc.common.po.SecurityLimit;
import com.gc.exception.CommonRuntimeException;
import com.gc.safety.dao.AccidentDAOHibernate;
import com.gc.safety.po.AccDuty;
import com.gc.safety.po.AccExtent;
import com.gc.safety.po.AccInPsn;
import com.gc.safety.po.AccInPsnGua;
import com.gc.safety.po.AccInPsnGuaPay;
import com.gc.safety.po.AccInPsnPay;
import com.gc.safety.po.AccLevel;
import com.gc.safety.po.AccObject;
import com.gc.safety.po.AccOutGua;
import com.gc.safety.po.AccOutGuaPay;
import com.gc.safety.po.AccOutObj;
import com.gc.safety.po.AccOutPsn;
import com.gc.safety.po.AccOutPsnPay;
import com.gc.safety.po.AccProcessor;
import com.gc.safety.po.AccType;
import com.gc.safety.po.Accident;
import com.gc.safety.po.GuaReport;
import com.gc.safety.po.Insurer;

class AccidentService {

	private AccidentDAOHibernate accidentDAO;
	private BaseDAOHibernate baseDAO;
	
	public void setAccidentDAO(AccidentDAOHibernate accidentDAO) {
		this.accidentDAO = accidentDAO;
	}
	
	public void setBaseDAO(BaseDAOHibernate baseDAO) {
		this.baseDAO = baseDAO;
	}

//==================================== Accident ====================================
	public List getAccAndOutByInsurer(Integer branchId, Insurer insurer) {
		return accidentDAO.getAccAndOutByInsurer(branchId, insurer);
	}
	
	public List getAccAndInPsnListByInsurer(Integer branchId, Insurer insurer) {
		return accidentDAO.getAccAndInPsnListByInsurer(branchId, insurer);
	}
	
	public List<Accident> getAccidentListByInsurerForClaims(Integer branchId, Insurer insurer) {
		return accidentDAO.getAccidentListByInsurerForClaims(branchId, insurer);
	}
	
	public List<Accident> getAccidentByBranchId(Integer branchId) {
		return accidentDAO.getAccidentByBranchId(branchId);
	}
	
	public List<Accident> getAccidentList(Accident accident) {
		return accidentDAO.getAccidentList(accident);
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Accident> getAccidents(SecurityLimit limit, Integer isArchive) {
//		return accidentDAO.getAccidents(limit, isArchive);
//	}
	
	public List<Accident> getAccidents(SecurityLimit limit, String[] orderColumns) {
		return accidentDAO.getAccidents(limit, orderColumns);
	}
	
	public List<Accident> getAccidentsAndPayObject(Integer branchId) {
//		return accidentDAO.getAccidentsAndPayObject(branchId);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void addAccident(Accident accident) {
		accidentDAO.addAccident(accident);
	}

	public void mergeObject(Object po) {
		accidentDAO.mergeObject(po);
	}
	
	public List getAccsByDateFrom(SecurityLimit limit, Calendar dateFrom) {
		return accidentDAO.getAccsByDateFrom(limit, dateFrom);
	}
	
	public List getPOPs(SecurityLimit limit) {
		return accidentDAO.getPOPs(limit);
	}
	
	public List getPayList(SecurityLimit limit) {
		return accidentDAO.getPayList(limit);
	}
	
	//单车，单人，事故查询
	public List getAccsByUIdOrWId(Integer branchId, String useId, String workerId, Calendar dateFrom, Calendar dateTo) {
		return accidentDAO.getAccsByUIdOrWId(branchId, useId, workerId, dateFrom, dateTo);
	}
	
	//事故所有条件查询
	public List getAccsByAll(SecurityLimit limit, Map qo) {
		return accidentDAO.getAccsByAll(limit, qo);
	}
	
//----------------------------object-------------------
	
	public void addObject(Object po) {
		baseDAO.addObject(po);
	}
	
	public void updateObject(Object po) {
		baseDAO.updateObject(po);
	}

	public void deleteObject(Object po) {
		baseDAO.deleteObject(po);
	}

	public void flush() {
		baseDAO.flush();
	}
	
	/**
	 *  compare accident
	 */
	public void compareAccident(Accident oldData) {
		Set<AccOutPsn> outPsns = oldData.getAccOutPsns();
		Set<AccOutObj> outObjs = oldData.getAccOutObjs();
		Set<AccInPsn> inPsns = oldData.getAccInPsns();
		Set<GuaReport> guaReports = oldData.getGuaReports();
		Map params = new HashMap();
		params.put("@class", "com.gc.safety.po.Accident");
		params.put("id.branch.id", oldData.getId().getBranchId());
		params.put("id.id", oldData.getId().getId());
		Object[] opos = null;
		opos = new Object[]{oldData};
		CommonServiceUtil.compareDirty(opos, params);
		if (outPsns.size() > 0) {
			params.clear();
			params.put("@class", "com.gc.safety.po.AccOutPsn");
			params.put("id.branch.id", oldData.getId().getBranchId());
			params.put("id.accId", oldData.getId().getId());
			opos = new Object[outPsns.size()];
			int i = 0;
			for (Iterator iterator = outPsns.iterator(); iterator.hasNext();) {
				AccOutPsn accOutPsn = (AccOutPsn) iterator.next();
				opos[i] = accOutPsn;
				i++;
			}
			CommonServiceUtil.compareDirty(opos, params);
		}
		if (outObjs.size() > 0) {
			params.clear();
			params.put("@class", "com.gc.safety.po.AccOutObj");
			params.put("id.branch.id", oldData.getId().getBranchId());
			params.put("id.accId", oldData.getId().getId());
			opos = new Object[outObjs.size()];
			int i = 0;
			for(Iterator iterator = outObjs.iterator(); iterator.hasNext();) {
				AccOutObj accOutObj = (AccOutObj) iterator.next();
				opos[i] = accOutObj;
				i++;
			}
			CommonServiceUtil.compareDirty(opos, params);
		}
		if (inPsns.size() > 0) {
			params.clear();
			params.put("@class", "com.gc.safety.po.AccInPsn");
			params.put("id.branch.id", oldData.getId().getBranchId());
			params.put("id.accId", oldData.getId().getId());
			opos = new Object[inPsns.size()];
			int i = 0;
			for(Iterator iterator = inPsns.iterator(); iterator.hasNext();) {
				AccInPsn accInPsn = (AccInPsn) iterator.next();
				opos[i] = accInPsn;
				i++;
			}
			CommonServiceUtil.compareDirty(opos, params);
		}
		if (guaReports.size() > 0) {
			params.clear();
			params.put("@class", "com.gc.safety.po.GuaReport");
			params.put("id.branch.id", oldData.getId().getBranchId());
			params.put("id.accId", oldData.getId().getId());
			opos = new Object[guaReports.size()];
			int i = 0;
			for(Iterator iterator = guaReports.iterator(); iterator.hasNext();) {
				GuaReport guaReport = (GuaReport) iterator.next();
				opos[i] = guaReport;
				i++;
			}
			CommonServiceUtil.compareDirty(opos, params);
		}
	}
	
	/**
	 * 查脏数据比较，事故保存
	 * @param accident
	 * @param obj
	 * @return
	 */
	public Accident saveAccident1(Accident oldData, Accident newData) {
		if(oldData == null || newData == null) return null;
		compareAccident(oldData);
		Set<AccOutPsn> nOutPsns = newData.getAccOutPsns();
		Set<AccOutObj> nOutObjs = newData.getAccOutObjs();
		Set<AccInPsn> nInPsns = newData.getAccInPsns();
		Set<GuaReport> nGuaReports = newData.getGuaReports();
		// delete
//		deletePOPR(oldData);
		// save or update
		for (AccOutPsn accOutPsn : nOutPsns) {
			accOutPsn.getId().setAccId(oldData.getId().getId());
			mergeObject(accOutPsn);
//			accidentDAO.saveAccOutPsn(accOutPsn);
//			accidentDAO.addAccOutPsn(accOutPsn);
		}
		for(AccOutObj accOutObj : nOutObjs) {
			accOutObj.getId().setAccId(oldData.getId().getId());
//			accidentDAO.addAccOutObj(accOutObj);
			mergeObject(accOutObj);
		}
		for(AccInPsn accInPsn : nInPsns) {
			accInPsn.getId().setAccId(oldData.getId().getId());
//			accidentDAO.addAccInPsn(accInPsn);
			mergeObject(accInPsn);
		}
		for(GuaReport guaReport : nGuaReports){
			guaReport.getId().setAccId(oldData.getId().getId());
//			accidentDAO.addGuaReport(guaReport);
			mergeObject(guaReport);
		}
		newData.getId().setId(oldData.getId().getId());
//		accidentDAO.saveAccident(newData);
		mergeObject(newData);
		flush();
//		newData.setAccOutPsns(nOutPsns);
//		newData.setAccOutObjs(nOutObjs);
//		newData.setAccInPsns(nInPsns);
//		newData.setGuaReports(nGuaReports);
//		List<Accident> accs = accidentDAO.getAccidentByNo(oldData.getId().getBranchId(), oldData.getNo());
		return null;
	}
	// 建立 和处理 保存事故
	@SuppressWarnings("unchecked")
	public Accident saveAccident(Accident accident,Accident obj) {
		Set<AccOutPsn> outPsns = accident.getAccOutPsns();
		Set<AccOutObj> outObjs = accident.getAccOutObjs();
		Set<AccInPsn> inPsns = accident.getAccInPsns();
		Set<GuaReport> guaReports = accident.getGuaReports();
		Integer accId = 0;
		if (obj == null) {
//			List<Accident> accs = accidentDAO.getAccidentByNo(accident.getId().getBranchId(), accident.getNo());
//			if(accs.size() > 0)
//				throw new CommonRuntimeException("你填写的事故编号已经存在，请确认后重新填写");
			accidentDAO.addAccident(accident);
			List<Accident> list = accidentDAO.getAccidentByNo(accident.getId().getBranchId(),accident.getNo());
			accId = list.get(0).getId().getId();
			accident.getId().setId(accId);
		} else {
			accId = obj.getId().getId();
//			List<Accident> accs = accidentDAO.getAccidentByNo(accident.getId().getBranchId(), accident.getNo());
//			if (accs.size() > 0) {
//				if(accs.get(0).getId().getId().intValue() != accId.intValue())
//					throw new CommonRuntimeException("你填写的事故编号已经存在，请确认后重新填写");
//			}
			accident.getId().setId(accId);
			accidentDAO.saveAccident(accident);	
			deletePOPR(obj);
		}
		for (AccOutPsn accOutPsn : outPsns) {
			accOutPsn.getId().setAccId(accId);
			accidentDAO.addAccOutPsn(accOutPsn);
		}
		for(AccOutObj accOutObj : outObjs) {
			accOutObj.getId().setAccId(accId);
			accidentDAO.addAccOutObj(accOutObj);
		}
		for(AccInPsn accInPsn : inPsns) {
			accInPsn.getId().setAccId(accId);
			accidentDAO.addAccInPsn(accInPsn);
		}
		for(GuaReport guaReport : guaReports){
			guaReport.getId().setAccId(accId);
			accidentDAO.addGuaReport(guaReport);
		}
		accident.setAccOutPsns(outPsns);
		accident.setAccOutObjs(outObjs);
		accident.setAccInPsns(inPsns);
		accident.setGuaReports(guaReports);
		return accident;
	}
	
	public void deletePOPR(Accident accident) {
		if(accident == null) return;
		compareAccident(accident);
		Set<AccOutPsn> outPsns = accident.getAccOutPsns();
		Set<AccOutObj> outObjs = accident.getAccOutObjs();
		Set<AccInPsn> inPsns = accident.getAccInPsns();
		Set<GuaReport> guaReports = accident.getGuaReports();
		for (AccOutPsn accOutPsn2 : outPsns) {
			accidentDAO.deleteAccOutPsn(accOutPsn2);
		}
		for (AccOutObj accOutObj2 : outObjs) {
			accidentDAO.deleteAccOutObj(accOutObj2);
		}
		for (AccInPsn accInPsn2 : inPsns) {
			accidentDAO.deleteAccInPsn(accInPsn2);
		}
		for (GuaReport guaReport2 : guaReports) {
			accidentDAO.deleteGuaReport(guaReport2);
		}
	}
	
	//删除事故
	@SuppressWarnings("unchecked")
	public void deleteAccident(Accident oldData) {
		Set<AccOutPsn> outPsns = oldData.getAccOutPsns();
		Set<AccOutObj> outObjs = oldData.getAccOutObjs();
		Set<AccInPsn> inPsns = oldData.getAccInPsns();
		Set<GuaReport> guaReports = oldData.getGuaReports();
		for (AccOutPsn accOutPsn3 : outPsns) {
			for (AccOutPsnPay accOutPsnPay : accOutPsn3.getAccOutPsnPays()){
				accidentDAO.deleteAccOutPsnPay(accOutPsnPay);
			}
			accidentDAO.deleteAccOutPsn(accOutPsn3);
		}
		for (AccOutObj accOutObj3 : outObjs) {
			accidentDAO.deleteAccOutObj(accOutObj3);
		}
		for (AccInPsn accInPsn3 : inPsns) {
			for (AccInPsnPay accInPsnPay : accInPsn3.getAccInPsnPays()){
				accidentDAO.deleteAccInPsnPay(accInPsnPay);
			}
			accidentDAO.deleteAccInPsn(accInPsn3);
		}
		for (GuaReport guaReport3 : guaReports) {
			accidentDAO.deleteGuaReport(guaReport3);
		}
		accidentDAO.deleteAccident(oldData);
	}
	
	// 审核保存事故
	public void saveCAccident(Accident accident) {
		Set<AccOutPsn> outPsns = accident.getAccOutPsns();
		Set<AccOutObj> outObjs = accident.getAccOutObjs();
		Set<AccInPsn> inPsns = accident.getAccInPsns();
		Set<GuaReport> guaReports = accident.getGuaReports();
		accidentDAO.saveAccident(accident);	
		for (AccOutPsn accOutPsn : outPsns) {
			accidentDAO.saveAccOutPsn(accOutPsn);
		}
		for(AccOutObj accOutObj : outObjs) {
			accidentDAO.saveAccOutObj(accOutObj);
		}
		for(AccInPsn accInPsn : inPsns) {
			accidentDAO.saveAccInPsn(accInPsn);
		}
		for(GuaReport guaReport : guaReports){
			accidentDAO.saveGuaReport(guaReport);
		}
	}
	
	public void updateAccident(Accident accident){
		accidentDAO.updateAccident(accident);
	}
	
	public List<Accident> getAccsByStatus01(Integer branchId,Department dept) {
		return accidentDAO.getAccsByStatus01(branchId,dept);
	}
	// 车队处理、营运部审核 通过 No 查询事故
	public List<Accident> getAccidentByNo(Integer branchId,String no) {
		return accidentDAO.getAccidentByNo(branchId,no);
	}
	
	public List<Accident> getAccidentByNoForArch(Integer branchId, String no, Calendar closeDate) {
		List<Accident> list = accidentDAO.getAccidentByNo(branchId, no);
		if (list.size() > 0) {
			Accident accident = list.get(0);
			if (accident.getStatus() == 2 || accident.getStatus() == 3) {
				if (accident.getStatus() == 3) {
					if (accident.getArchDate().before(closeDate) || accident.getArchDate().equals(closeDate)) {
						throw new CommonRuntimeException("你输入的事故已存档且已结账，不能再处理，请确认后重新获取或填写");
					}	
				}
				Integer accId = accident.getId().getId();
				List<AccOutGua> outGuas = accidentDAO.getOutGuasByAccId(branchId, accId);
				if (outGuas.size() > 0) {
					Integer ogpsSize = 0;
					for (AccOutGua accOutGua : outGuas) {
						String appRefNo = accOutGua.getId().getRefNo();
						String appNo = accOutGua.getId().getNo();
						List<AccOutGuaPay> outGuaPays = accidentDAO.getOutGuaPaysByAppRefNo(branchId, appRefNo, appNo);
						if(outGuaPays.size() > 0) ogpsSize++;
					}
					if (ogpsSize != outGuas.size()) 
						throw new CommonRuntimeException("你输入的事故三则还没有赔付完，请确认后重新获取或填写");
				}
				List<AccInPsnGua> inPsnGuas = accidentDAO.getInPsnGuasByAccId(branchId, accId);
				if (inPsnGuas.size() > 0) {
					Integer ipgpSize = 0;
					for (AccInPsnGua accInPsnGua : inPsnGuas) {
						String appRefNo = accInPsnGua.getId().getRefNo();
						String appNo = accInPsnGua.getId().getNo();
						List<AccInPsnGuaPay> inPsnGuaPays = accidentDAO.getInPsnGuaPaysByAppRefNo(branchId, appRefNo, appNo);
						if(inPsnGuaPays.size() > 0) ipgpSize++;
					}
					if(ipgpSize != inPsnGuas.size())
						throw new CommonRuntimeException("你输入的事故客伤还没有赔付完，请确认后重新获取或填写");
				}
			} else {
				throw new CommonRuntimeException("你输入的事故编号不是当前处理状态的，请确认后重新获取或填写");
			}
		} else {
			throw new CommonRuntimeException("你输入的事故编号没有相应的事故信息，请确认后重新获取或填写");
		}
		return list;
	}
	
	public List<?> getAccsAndObjPsnGuas(Integer branchId,Calendar closeDate) {
		return accidentDAO.getAccsAndObjPsnGuas(branchId, closeDate);
	}
	
	// 通过事故 id 查询 事故 的理赔、赔付信息
	public List getOutInGuaAndPays(Integer branchId, Integer accId) {
		List list = new ArrayList();
		List<AccOutGua> outGuaList = new ArrayList<AccOutGua>();
		List<AccOutGuaPay> outGuaPayList = new ArrayList<AccOutGuaPay>();
		List<AccInPsnGua> inPsnGuaList = new ArrayList<AccInPsnGua>();
		List<AccInPsnGuaPay> inPsnGuaPayList = new ArrayList<AccInPsnGuaPay>();
		
		List<AccOutGua> outGuas = accidentDAO.getOutGuasByAccId(branchId, accId);
		if (outGuas.size() > 0) {
			for (AccOutGua accOutGua : outGuas) {
				String appRefNo = accOutGua.getId().getRefNo();
				String appNo = accOutGua.getId().getNo();
				List<AccOutGuaPay> outGuaPays = accidentDAO.getOutGuaPaysByAppRefNo(branchId, appRefNo, appNo);
				if(outGuaPays.size() > 0) {
					AccOutGuaPay outGuaPay = outGuaPays.get(0);
					outGuaPay.setFkAccOutGua(accOutGua);
					outGuaPayList.add(outGuaPay);
				} else {
					outGuaList.add(accOutGua);
				}
			}
		}
		List<AccInPsnGua> inPsnGuas = accidentDAO.getInPsnGuasByAccId(branchId, accId);
		if (inPsnGuas.size() > 0) {
			for (AccInPsnGua accInPsnGua : inPsnGuas) {
				String appRefNo = accInPsnGua.getId().getRefNo();
				String appNo = accInPsnGua.getId().getNo();
				List<AccInPsnGuaPay> inPsnGuaPays = accidentDAO.getInPsnGuaPaysByAppRefNo(branchId, appRefNo, appNo);
				if(inPsnGuaPays.size() > 0) {
					AccInPsnGuaPay inPsnGuaPay = inPsnGuaPays.get(0);
					inPsnGuaPay.setFkAccInPsnGua(accInPsnGua);
					inPsnGuaPayList.add(inPsnGuaPay);
				} else {
					inPsnGuaList.add(accInPsnGua);
				}
			}
		}
		list.add(outGuaList);
		list.add(outGuaPayList);
		list.add(inPsnGuaList);
		list.add(inPsnGuaPayList);
		return list;
	}
//==================================== AccType ====================================

	public void addAccType(AccType accType) {
		accidentDAO.addAccType(accType);
	}
	
	public void saveAccType(AccType accType) {
		if (accType.getId() == 0) {
			accidentDAO.addAccType(accType);
		} else {
			accidentDAO.saveAccType(accType);
		}
	}
	
	public void updateAccType(AccType accType) {
		accidentDAO.updateAccType(accType);
	}
	
	public void deleteAccType(AccType accType) {
		accidentDAO.deleteAccType(accType);
	}
	
	public List<AccType> getAccTypes(Integer branchId) {
		return accidentDAO.getAccTypes(branchId);
	}

//==================================== AccLevel ====================================

	public List<AccLevel> getAccLevels(Integer branchId) {
		return accidentDAO.getAccLevels(branchId);
	}
	
	public void saveAccLevel(AccLevel accLevel) {
		if(accLevel.getId() == 0){
			accidentDAO.addAccLevel(accLevel);
		}else{
			accidentDAO.saveAccLevel(accLevel);
		}
	}
	
	public void addAccLevel(AccLevel accLevel) {
		accidentDAO.addAccLevel(accLevel);
	}
	
	public void updateAccLevel(AccLevel accLevel) {
		accidentDAO.updateAccLevel(accLevel);
	}
	
	public void deleteAccLevel(AccLevel accLevel) {
		accidentDAO.deleteAccLevel(accLevel);
	}

//==================================== AccDuty ====================================

	public List<AccDuty> getAccDutys(Integer branchId) {
		return accidentDAO.getAccDutys(branchId);
	}
	
	public void saveAccDuty(AccDuty accDuty) {
		if (accDuty.getId() == 0) {
			accidentDAO.addAccDuty(accDuty);
		} else {
			accidentDAO.saveAccDuty(accDuty);
		}
	}
	
	public void addAccDuty(AccDuty accDuty) {
		accidentDAO.addAccDuty(accDuty);
	}
	
	public void updateAccDuty(AccDuty accDuty) {
		accidentDAO.updateAccDuty(accDuty);
	}
	
	public void deleteAccDuty(AccDuty accDuty) {
		accidentDAO.deleteAccDuty(accDuty);
	}

//==================================== AccExtent ====================================

	public List<AccExtent> getAccExtents(Integer branchId) {
		return accidentDAO.getAccExtents(branchId);
	}
	
	public void saveAccExtent(AccExtent accExtent) {
		if( accExtent.getId() == 0) {
			accidentDAO.addAccExtent(accExtent);
		}else{
			accidentDAO.saveAccExtent(accExtent);
		}
	}
	
	public void addAccExtent(AccExtent accExtent) {
		accidentDAO.addAccExtent(accExtent);
	}
	
	public void updateAccExtent(AccExtent accExtent) {
		accidentDAO.updateAccExtent(accExtent);
	}
	
	public void deleteAccExtent(AccExtent accExtent) {
		accidentDAO.deleteAccExtent(accExtent);
	}

//==================================== AccPsn ====================================

//	public List<AccPsn> getAccPsns(Integer branchId, String accidentNo) {
//		return accidentDAO.getAccPsns(branchId, accidentNo);
//	}
	
	@SuppressWarnings("unchecked")
	public List getAccPsnsAndAccident(Integer branchId) {
//		return accidentDAO.getAccPsnsAndAccident(branchId);
		return null;
	}
	
//	public void addAccPsn(AccPsn accPsn) {
//		accidentDAO.addAccPsn(accPsn);
//	}
	
//	public void saveAccPsn(AccPsn accPsn) {
//		if(accPsn.getId() == 0){
//			accidentDAO.addAccPsn(accPsn);
//		} else {
//			accidentDAO.saveAccPsn(accPsn);
//		}
//	}

//	public void updateAccPsn(AccPsn accPsn) {
//		accidentDAO.updateAccPsn(accPsn);
//	}
	
//	public void deleteAccPsn(AccPsn accPsn) {
//		accidentDAO.deleteAccPsn(accPsn);
//	}

//==================================== AccPayPsn ====================================

//	public void addAccPayPsn(AccPayPsn accPayPsn) {
//		accidentDAO.addAccPayPsn(accPayPsn);
//	}
	
//	public void saveAccPayPsn(AccPayPsn accPayPsn) {
//		if(accPayPsn.getId() == 0){
//			accidentDAO.addAccPayPsn(accPayPsn);
//		} else {
//			accidentDAO.saveAccPayPsn(accPayPsn);
//		}
//	}
	
//	public void updateAccPayPsn(AccPayPsn accPayPsn) {
//		accidentDAO.updateAccPayPsn(accPayPsn);
//	}
//	
//	public void deleteAccPayPsn(AccPayPsn accPayPsn) {
//		accidentDAO.deleteAccPayPsn(accPayPsn);
//	}

//==================================== AccObject ====================================

	public List<AccObject> getAccObjects(Integer branchId) {
		return accidentDAO.getAccObjects(branchId);
	}
	
	public void saveAccObject(AccObject accObject) {
		if (accObject.getId() == 0) {
			accidentDAO.addAccObject(accObject);
		} else {
			accidentDAO.saveAccObject(accObject);
		}
	}
	
	public void addAccObject(AccObject accObject) {
		accidentDAO.addAccObject(accObject);
	}
	
	public void updateAccObject(AccObject accObject) {
		accidentDAO.updateAccObject(accObject);
	}
	
	public void deleteAccObject(AccObject accObject) {
		accidentDAO.deleteAccObject(accObject);
	}

//==================================== AccPayObject ====================================

//	public List<AccPayObject> getAccPayObjects(Integer branchId) {
//		return accidentDAO.getAccPayObjects(branchId);
//	}
	
	@SuppressWarnings("unchecked")
	public List getAccPayObjectsAndAccident(Integer branchId) {
//		return accidentDAO.getAccPayObjectsAndAccident(branchId);
		return null;
	}
	
//	public void addAccPayObject(AccPayObject apo) {
//		accidentDAO.addAccPayObject(apo);
//	}
	
//	public void saveAccPayObject(AccPayObject apo) {
//		if(apo.getId() == 0){
//			accidentDAO.addAccPayObject(apo);
//		} else {
//			accidentDAO.saveAccPayObject(apo);
//		}
//	}
	
//	public void deleteAccPayObject(AccPayObject apo) {
//		accidentDAO.deleteAccPayObject(apo);
//	}

//==================================== AccProcessor ====================================

	public List<AccProcessor> getAccProcessors(Integer branchId) {
		return accidentDAO.getAccProcessors(branchId);
	}
	
	public void saveAccProcessor(AccProcessor accProcessor) {
		if(accProcessor.getId() == 0){
			accidentDAO.addAccProcessor(accProcessor);
		}else{
			accidentDAO.saveAccProcessor(accProcessor);
		}
	}
	
	public void addAccProcessor(AccProcessor accProcessor) {
		accidentDAO.addAccProcessor(accProcessor);
	}
	
	public void updateAccProcessor(AccProcessor accProcessor) {
		accidentDAO.updateAccProcessor(accProcessor);
	}
	
	public void deleteAccProcessor(AccProcessor accProcessor) {
		accidentDAO.deleteAccProcessor(accProcessor);
	}
//================================= AccOutPsn ==============================
	public void addAccOutPsn(AccOutPsn accOutPsn) {
		accidentDAO.addAccOutPsn(accOutPsn);
	}
	
	public void deleteAccOutPsn(AccOutPsn accOutPsn) {
		accidentDAO.deleteAccOutPsn(accOutPsn);
	}
	
	public void saveAccOutPsn(AccOutPsn accOutPsn) {
		accidentDAO.saveAccOutPsn(accOutPsn);
	}
	
	public List<AccOutPsn> getAOPByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		return accidentDAO.getAOPByAccIdAndNo(branchId, accNo, no);
	}
	
//================================= AccOutPsnPay ==============================
	public void addAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		accidentDAO.addAccOutPsnPay(accOutPsnPay);
	}
	
	public void deleteAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		accidentDAO.deleteAccOutPsnPay(accOutPsnPay);
	}
	
	public void saveAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		accidentDAO.saveAccOutPsnPay(accOutPsnPay);
	}
//================================== AccInPsn =============================
	public void addAccInPsn(AccInPsn accInPsn) {
		accidentDAO.addAccInPsn(accInPsn);
	}
	
	public void deleteAccInPsn(AccInPsn accInPsn) {
		accidentDAO.deleteAccInPsn(accInPsn);
	}
	
	public void saveAccInPsn(AccInPsn accInPsn) {
		accidentDAO.saveAccInPsn(accInPsn);
	}
	
	public List<AccInPsn> getAIPByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		return accidentDAO.getAIPByAccIdAndNo(branchId, accNo, no);
	}
//================================== AccInPsnPay =============================
	public void addAccInPsnPay(AccInPsnPay accInPsnPay) {
		accidentDAO.addAccInPsnPay(accInPsnPay);
	}
	
	public void deleteAccInPsnPay(AccInPsnPay accInPsnPay) {
		accidentDAO.deleteAccInPsnPay(accInPsnPay);
	}
	
	public void saveAccInPsnPay(AccInPsnPay accInPsnPay) {
		accidentDAO.saveAccInPsnPay(accInPsnPay);
	}
//================================== AccOutObj ==============================
	public void addAccOutObj(AccOutObj accOutObj) {
		accidentDAO.addAccOutObj(accOutObj);
	}
	
	public void deleteAccOutObj(AccOutObj accOutObj) {
		accidentDAO.deleteAccOutObj(accOutObj);
	}
	
	public void saveAccOutObj(AccOutObj accOutObj) {
		accidentDAO.saveAccOutObj(accOutObj);
	}
	
	public List<AccOutObj> getAOOByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		return accidentDAO.getAOOByAccIdAndNo(branchId, accNo, no);
	}
}
