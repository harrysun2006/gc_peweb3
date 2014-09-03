package com.gc.safety.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.gc.safety.po.Insurer;
import com.gc.util.SpringUtil;

public class ClaimsServiceUtil {
	public static final String BEAN_NAME = "safetyClaimsServiceUtil";
	
	private ClaimsService claimsService;

	public static ClaimsService getClaimsService() {
		ApplicationContext ctx = SpringUtil.getContext();
		ClaimsServiceUtil util = (ClaimsServiceUtil) ctx.getBean(BEAN_NAME);
		ClaimsService service = util.claimsService;
		return service;
	}

	public void setClaimsService(ClaimsService claimsService) {
		this.claimsService = claimsService;
	}

//==================================== Claims ====================================
	// 通过事故id 来查 三则理赔表 和 客伤理赔表
	public static Integer getOutAndInGuaByAccId(Integer branchId,Integer accId) {
		return getClaimsService().getOutAndInGuaByAccId(branchId, accId);
	}
	
	// 保存事故三责理赔凭证并返回凭证号
	public static String saveAccOutGua(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccOutGua(oldList, newList, branchId);
	}
	
	// 保存事故客伤理赔凭证并返回凭证号
	public static String saveAccInPsnGua(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccInPsnGua(oldList, newList, branchId);
	}
	
	// 查询待修改凭证列表,没有赔付、没有结帐的理赔凭证
	public static List getAccGuaForModify(Integer branchId) {
		return getClaimsService().getAccGuaForModify(branchId);
	}
	
	// 查询当前机构下所有理赔凭证
	public static List getAccGuaForQuery(Integer branchId) {
		return getClaimsService().getAccGuaForQuery(branchId);
	}
	
	// 赔付业务时查询客伤理赔凭证
	public static List getAccInPsnGua(Integer branchId, Insurer insurer) {
		return getClaimsService().getAccInPsnGua(branchId, insurer);
	}
	
	// 赔付业务时查询三责理赔凭证
	public static List getAccOutGua(Integer branchId, Insurer insurer) {
		return getClaimsService().getAccOutGua(branchId, insurer);
	}
	
	/** 保存三责赔付凭证并返回凭证号 */
	public static String saveAccOutGuaPay(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccOutGuaPay(oldList, newList, branchId);
	}
	
	/** 保存客伤赔付凭证并返回凭证号 */
	public static String saveAccInPsnGuaPay(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccInPsnGuaPay(oldList, newList, branchId);
	}
	
	/** 
	 * 查询赔赔付凭证
	 * @param branchId
	 * @return
	 */
	public static List getAccGuaPayForModify(Integer branchId) {
		return getClaimsService().getAccGuaPayForModify(branchId);
	}
	
	// 查询当前机构下所有赔付凭证
	public static List getAccPayForQuery(Integer branchId) {
		return getClaimsService().getAccPayForQuery(branchId);
	}
}
