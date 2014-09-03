package com.gc.safety.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.gc.Constants;
import com.gc.common.service.BaseServiceUtil;
import com.gc.safety.po.SafetyClose;
import com.gc.util.CacheUtil;
import com.gc.util.CommonUtil;
import com.gc.util.SpringUtil;

public class CommonServiceUtil {

	public static final String BEAN_NAME = "safetyCommonServiceUtil";

	private CommonService commonService;

	public static CommonService getCommonService() {
		ApplicationContext ctx = SpringUtil.getContext();
		CommonServiceUtil util = (CommonServiceUtil) ctx.getBean(BEAN_NAME);
		CommonService service = util.commonService;
		return service;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

//==================================== Oracle Function ====================================

	public static String getSafetyAccNo(Integer branchId, String typeNo, String accHead ) {
		return getCommonService().getSafetyAccNo(branchId, typeNo, accHead );
	}
	
	public static final String HD_TRANSGRESS = "A";
	public static final String HD_GUARANTEE = "B";
	public static final String HD_GUA = "C";
	public static final String HD_PAY = "D";
	
	public static String getTransGressNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HD_TRANSGRESS + "'yy'-'", cal);
		return getSafetyAccNo(branchId, HD_TRANSGRESS, head);
	}
	
	public static String getGuaranteeNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HD_GUARANTEE + "'yy'-'", cal);
		return getSafetyAccNo(branchId, HD_GUARANTEE, head);
	}
	
	public static String getGuaNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HD_GUA + "'yy'-'", cal);
		return getSafetyAccNo(branchId, HD_GUA, head);
	}
	
	public static String getPayNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HD_PAY + "'yy'-'", cal);
		return getSafetyAccNo(branchId, HD_PAY, head);
	}

//==================================== Close Date ====================================

	public static Calendar getLastSafetyCloseDate(Integer branchId) {
		return getCommonService().getLastSafetyCloseDate(branchId);
	}
	
	
	public static void compareDirty(Object[] opos, Map params) {
		getCommonService().compareDirty(opos,params);
	}
	
	public static List<SafetyClose> getCloseList(Integer branchId) {
		Map params = new Hashtable();
		params.put(Constants.PARAM_ORDER, "id.branch.id, id.date desc");
		params.put("id.branch.id", branchId);
		return BaseServiceUtil.getObjects(SafetyClose.class, params);
	}
	
	public static Date closeAcc(SafetyClose close, String user) {
		Date date = getCommonService().closeAcc(close, Constants.CLOSE_TYPE_CLOSE, user);
		String name = close.getBranchId() + "." + Constants.CACHE_SAFETY_LASTCLOSEDATE;
		CacheUtil.put(name, date);
		return date;
	}
	
	public static Date decloseAcc(SafetyClose close, String user) {
		Date date = getCommonService().closeAcc(close, Constants.CLOSE_TYPE_UNCLOSE, user);
		String name = close.getBranchId() + "." + Constants.CACHE_SAFETY_LASTCLOSEDATE;
		CacheUtil.put(name, date);
		return date;
	}
	
	//================================ now ==================================
	public static Calendar getNowDate() {
		return getCommonService().getNowDate();
	}
	
}
