package com.gc.hr.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.gc.Constants;
import com.gc.common.po.Branch;
import com.gc.common.service.BaseServiceUtil;
import com.gc.hr.po.HrClose;
import com.gc.util.CacheUtil;
import com.gc.util.CommonUtil;
import com.gc.util.SpringUtil;

public class CommonServiceUtil {

	public static final String BEAN_NAME = "hrCommonServiceUtil";
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

//==================================== Voucher Head NO ====================================

	private static String getAccNo(Integer branchId, String type, String accHead) {
		return getCommonService().getAccNo(branchId, type, accHead );
	}

	public static final String HDNO_CHKLONGPLAN = "A";
	public static final String HDNO_CHKPLAN = "B";
	public static final String HDNO_CHKFACT = "C";
	public static final String HDNO_SALFACT = "D";

	public static String getChkLongPlanNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HDNO_CHKLONGPLAN + "'yy'-'", cal);
		return getAccNo(branchId, HDNO_CHKLONGPLAN, head);
	}

	public static String getChkPlanNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HDNO_CHKPLAN + "'yy'-'", cal);
		return getAccNo(branchId, HDNO_CHKPLAN, head);
	}

	public static String getChkFactNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HDNO_CHKFACT + "'yy'-'", cal);
		return getAccNo(branchId, HDNO_CHKFACT, head);
	}

	public static String getSalFactNo(Integer branchId, Calendar cal) {
		String head = CommonUtil.formatCalendar("'" + HDNO_SALFACT + "'yy'-'", cal);
		return getAccNo(branchId, HDNO_SALFACT, head);
	}

//==================================== Close ====================================

	public static List<HrClose> getCloseList(Integer branchId) {
		Map params = new Hashtable();
		params.put(Constants.PARAM_ORDER, "id.branch.id, id.date desc");
		params.put("id.branch.id", branchId);
		return BaseServiceUtil.getObjects(HrClose.class, params);
	}

	public static Date getLastCloseDate(Integer branchId) {
		String name = branchId + "." + Constants.CACHE_HR_LASTCLOSEDATE;
		Date date = null;
		synchronized(name) {
			date = (Date) CacheUtil.get(name);
			if (date == null) {
				date = getCommonService().closeAcc(new HrClose(new Branch(branchId), Calendar.getInstance()), Constants.CLOSE_TYPE_GETLAST, "");
				CacheUtil.put(name, date);
			}
		}
		return date;
	}

	public static Date closeAcc(HrClose close, String user) {
		Date date = getCommonService().closeAcc(close, Constants.CLOSE_TYPE_CLOSE, user);
		String name = close.getBranchId() + "." + Constants.CACHE_HR_LASTCLOSEDATE;
		CacheUtil.put(name, date);
		return date;
	}

	public static Date decloseAcc(HrClose close, String user) {
		Date date = getCommonService().closeAcc(close, Constants.CLOSE_TYPE_UNCLOSE, user);
		String name = close.getBranchId() + "." + Constants.CACHE_HR_LASTCLOSEDATE;
		CacheUtil.put(name, date);
		return date;
	}
}
