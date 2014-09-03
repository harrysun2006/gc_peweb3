package com.gc.safety.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gc.common.dao.BaseDAOHibernate;
import com.gc.common.po.SecurityLimit;
import com.gc.exception.CommonRuntimeException;
import com.gc.safety.dao.GuaranteeDAOHibernate;
import com.gc.safety.po.GuarInfo;
import com.gc.safety.po.Guarantee;
import com.gc.safety.po.GuaType;
import com.gc.safety.po.Insurer;

class GuaranteeService {
	private GuaranteeDAOHibernate guaranteeDAO;
	
	private BaseDAOHibernate baseDAO;
	
	public void setGuaranteeDAO(GuaranteeDAOHibernate guaranteeDAO) {
		this.guaranteeDAO = guaranteeDAO;
	}
	
	public void setBaseDAO(BaseDAOHibernate baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	
//==================================== Guarantee ====================================

	public List<Guarantee> getCurrentGuarantee(Integer branchId) {
		return guaranteeDAO.getGuarantee(branchId);
	}
	
	public Guarantee getGuaranteeById(Long id) {
		return guaranteeDAO.getGuaranteeById(id);
	}
	
	public void saveGuarantee(Guarantee guarantee) {
		guaranteeDAO.saveGuarantee(guarantee);
	}
	
	@SuppressWarnings("unchecked")
	public int saveGua(Guarantee guarantee,Object obj) {
		Set<GuarInfo> guarInfos = guarantee.getGuarInfos();
		if (obj == null) {
			guaranteeDAO.saveGuarantee(guarantee);
		} else if(obj != null){
			Guarantee guarantee1 = (Guarantee)obj;
			Set<GuarInfo> guarInfos1 = guarantee1.getGuarInfos();
			if(guarInfos1 != null){
				ArrayList<GuarInfo> tempInfos = (ArrayList<GuarInfo>)guaranteeDAO.getGuarInfoByBN(guarantee.getId().getBranchId(), guarantee.getId().getAccNo());
				if (tempInfos != null) {
					for (int i = 0; i < tempInfos.size(); i++) {
						guaranteeDAO.deleteGuarInfo(tempInfos.get(i));
					}
				}
			}
		}
		if (guarInfos != null) {
			for (Iterator iterator = guarInfos.iterator(); iterator.hasNext();) {
				GuarInfo guarInfo = (GuarInfo) iterator.next();
				guaranteeDAO.addGuarInfo(guarInfo);
			}
		}
		
		return guarInfos.size();
	}
	
	//保存投保信息两张表
	public void saveGuarAndGuarInfo(Guarantee guarantee, List<GuarInfo> guarInfoList) {
		try {
			baseDAO.saveObject(guarantee);
		} catch (Exception e) {
			throw new CommonRuntimeException("saveGuarantee 异常");
		}
		try {
			for (Iterator<GuarInfo> iterator = guarInfoList.iterator(); iterator.hasNext();) {
				GuarInfo guarInfo = (GuarInfo) iterator.next();
				guaranteeDAO.saveGuarInfo(guarInfo);
			}
		} catch (Exception ex) {
			throw new CommonRuntimeException("saveGuarInfo 异常");
		}
	}
	
	
	public void addGuarantee(Guarantee guarantee) {
		guaranteeDAO.addGuarantee(guarantee);
	}
	
	public void updateGuarantee(Guarantee guarantee) {
		guaranteeDAO.updateGuarantee(guarantee);
	}
	
//	public void deleteGuarantee(Guarantee guarantee) {
//		guaranteeDAO.deleteGuarantee(guarantee);
//	}
	
	@SuppressWarnings("unchecked")
	public List findGuarList(Map obj){
		return guaranteeDAO.findGuarList(obj);
	}
	
	@SuppressWarnings("unchecked")
	public List findGuarAndInfoList(Map obj1,Map obj2){
		return guaranteeDAO.findGuarAndInfoList(obj1,obj2);
	}
	
		
	@SuppressWarnings("unchecked")
	public List findList(String accNo,Integer branchId){
		return guaranteeDAO.findList(accNo,branchId);
	}
	
	public List<Guarantee> getGuasByBCloseD(Integer branchId, Calendar closeDate) {
		return guaranteeDAO.getGuasByBCloseD(branchId, closeDate);
	}
	
	public List<Guarantee> getGuaByAccNo(Integer branchId, String accNo, Calendar closeDate) {
		return guaranteeDAO.getGuaByAccNo(branchId, accNo, closeDate);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteGuarantee(Guarantee guarantee) {
		Set<GuarInfo> guarInfos = guarantee.getGuarInfos();
		if (guarInfos != null) {
			for (Iterator iterator = guarInfos.iterator(); iterator.hasNext();) {
				GuarInfo guarInfo = (GuarInfo) iterator.next();
				guaranteeDAO.deleteGuarInfo(guarInfo);
			}
		}
		guaranteeDAO.deleteGuarantee(guarantee);
	}
	
//==================================== GuarInfo ====================================
	
	public List<GuarInfo> getGuarInfo(Integer branchId) {
		return guaranteeDAO.getGuarInfo(branchId);
	}
	
	public void addGuarInfo(GuarInfo guarInfo) {
		guaranteeDAO.addGuarInfo(guarInfo);
	}
	
	public void saveGuarInfo(GuarInfo guarInfo) {
		guaranteeDAO.saveGuarInfo(guarInfo);
	}
	
	public void updateGuarInfo(GuarInfo guarInfo) {
		guaranteeDAO.updateGuarInfo(guarInfo);
	}
	
	public void deleteGaurInfo(GuarInfo guarInfo) {
		guaranteeDAO.deleteGuarInfo(guarInfo);
	}
	
	public List<GuarInfo> getGuarInfoList(Integer branchId,Integer busId,Calendar accDate) {
		return guaranteeDAO.getGuarInfoList(branchId,busId, accDate);
	}
	
	public List<GuarInfo> getGuarInfoByBND(Integer branchId, String accNo, Calendar closeDate) {
		return guaranteeDAO.getGuarInfoByBND(branchId, accNo, closeDate);
	}
	
	public List<GuarInfo> getMatureGuas(Integer branchId, Calendar date1, Calendar date2) {
		return guaranteeDAO.getMatureGuas(branchId, date1, date2);
	}
	
	public List<GuarInfo> getGIsByBusIdAndAccDate(Integer branchId, Integer busId, Calendar accDate) {
		return guaranteeDAO.getGIsByBusIdAndAccDate(branchId, busId, accDate);
	}
	
	public List<GuarInfo> getGuaInfosByDateFrom(SecurityLimit limit,String[] orderColumns, Calendar dateFrom) {
		return guaranteeDAO.getGuaInfosByDateFrom(limit, orderColumns, dateFrom);
	}
	
	//单车保单查询
	public List<GuarInfo> getGIsByUid(Integer branchId,String useId,Calendar dateFrom,Calendar dateTo) {
		return guaranteeDAO.getGIsByUid(branchId, useId, dateFrom, dateTo);
	}
//==================================== GuaType ====================================
	
	public List<GuaType> getGuaTypes(Integer branchId) {
		return guaranteeDAO.getGuaTypes(branchId);
	}
	

	public List<GuaType> getCurrentGuaranteeTypes(Integer branchId) {
		return guaranteeDAO.getGuaTypes(branchId);
	}
	
	public void saveGuaranteeType(GuaType guaranteeType) {
		if(guaranteeType.getId() == 0){
			guaranteeDAO.addGuaType(guaranteeType);
		}else {
			guaranteeDAO.saveGuaType(guaranteeType);
		}
	}
	
	public void addGuaranteeType(GuaType guaranteeType) {
		guaranteeDAO.addGuaType(guaranteeType);
	}
	
	public void updateGuaranteeType(GuaType guaranteeType) {
		guaranteeDAO.updateGuaType(guaranteeType);
	}
	
	public void deleteGuaranteeType(GuaType guaranteeType) {
		guaranteeDAO.deleteGuaType(guaranteeType);
	}

//==================================== Insurer ====================================
	
	public List<Insurer> getInsurers(Integer branchId) {
		return guaranteeDAO.getInsurers(branchId);
	}
	
	public void saveInsurer(Insurer insurer) {
		if(insurer.getId() == 0){
			guaranteeDAO.addInsurer(insurer);
		} else{
			guaranteeDAO.saveInsurer(insurer);
		}
	}
	
	public void addInsurer(Insurer insurer) {
		guaranteeDAO.addInsurer(insurer);
	}
	
	public void updateInsurer(Insurer insurer) {
		guaranteeDAO.updateInsurer(insurer);
	}
	
	public void deleteInsurer(Insurer insurer) {
		guaranteeDAO.deleteInsurer(insurer);
	}
}
