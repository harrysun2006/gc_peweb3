package com.gc.safety.service;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.gc.common.po.SecurityLimit;
import com.gc.safety.po.TransInfo;
import com.gc.safety.po.TransType;
import com.gc.util.SpringUtil;

public class TransServiceUtil {
	public static final String BEAN_NAME = "safetyTransServiceUtil";
	
	private TransService transService;
	
	public static TransService getTransService() {
		ApplicationContext ctx = SpringUtil.getContext();
		TransServiceUtil util = (TransServiceUtil) ctx.getBean(BEAN_NAME);
		TransService service = util.transService;
		return service;
	}
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}

//==================================== TransInfo ====================================

	public static List<TransInfo> getCurrentTransInfo(Integer branchId) {
		return getTransService().getCurrentTransInfo(branchId);
	}
	
	public static String saveTransInfos(List<TransInfo> oldLists, List<TransInfo> lists) {
		return getTransService().saveTransInfos(oldLists, lists);
	}
	
	public static String saveTrans(List<TransInfo> lists, List<TransInfo> oldLists) {
		return getTransService().saveTransInfos(oldLists, lists);
	}
	
	public static String saveTrans2(Object[] opos, Object[] npos, Integer departId, Calendar closeDate) {
		return getTransService().saveTransInfos2(opos, npos, departId, closeDate);
	}
	
	/**
	 * 返回车辆违法查询列表
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public static List getTransInfoList(Map obj) {
		return getTransService().getTransInfoList(obj);
	}
	
	
	public static void addTransInfo(TransInfo transInfo) {
		getTransService().addTransInfo(transInfo);
	}
	
	public static void saveTransInfo(TransInfo transInfo) {
		getTransService().saveTransInfo(transInfo);
	}
	
	public static void updateTransInfo(TransInfo transInfo) {
		getTransService().updateTransInfo(transInfo);
	}

	public static void uploadTransInfo(File[] files, HttpServletResponse response) {
		getTransService().uploadTransInfo(files, response);
	}

	public static void deleteTransInfo(TransInfo transInfo) {
		getTransService().deleteTransInfo(transInfo);
	}
	
	public static List<TransInfo> getTransInfosForModify(Integer branchId, String accNo, String doDate, String dealDate, Calendar closeDate) {
		return getTransService().getTransInfosForModify(branchId, accNo, doDate, dealDate, closeDate);
	}
	
	//单车违章查询
	public static List<TransInfo> getTransByUIdOrWId(Integer branchId, String useId, String workerId, Calendar dateFrom, Calendar dateTo) {
		return getTransService().getTransByUIdOrWId(branchId, useId, workerId, dateFrom, dateTo);
	}
	
//违章处理、部门、结账日期 查询
	public static List<TransInfo> getTransListByDeptCloseD(Integer branchId,Integer departId,Calendar closeDate) {
		return getTransService().getTransListByDeptCloseD(branchId, departId, closeDate);
	}
	
	/**
	 * 违章所有条件查询
	 * @param obj
	 * @return
	 */
	public List getTransListByAll(SecurityLimit limit, Map qo)
	{
		return getTransService().getTransListByAll(limit, qo);
	}
//==================================== TransType ====================================

	public static List<TransType> getTransTypes(Integer branchId) {
		return getTransService().getTransTypes(branchId);
	}
	
	public static List<TransType> getTransTypeByName(Integer branchId, String name) {
		return getTransService().getTransTypeByName(branchId, name);
	}
	
	public List getTransInfoForSafetyTree(SecurityLimit limit, Calendar dateFrom) {
		return getTransService().getTransInfoForSafetyTree(limit, dateFrom);
	}
	
	public static List<TransType> getTransType1(Integer branchId) {
		return getTransService().getTransType1(branchId);
	}
	
	public static List<TransType> getTransType2(Integer branchId) {
		return getTransService().getTransType2(branchId);
	}
	
	public static void addTransType(TransType transType) {
		getTransService().addTransType(transType);
	}
	
	public static void saveTransType(TransType transType) {
		getTransService().saveTransType(transType);
	}
	
	public static void updateTransType(TransType transType) {
		getTransService().updateTransType(transType);
	}
	
	public static void deleteTransType(TransType transType) {
		getTransService().deleteTransType(transType);
	}
}
