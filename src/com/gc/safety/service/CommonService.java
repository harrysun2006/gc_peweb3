package com.gc.safety.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gc.Constants;
import com.gc.common.dao.BaseDAOHibernate;
import com.gc.exception.CommonRuntimeException;
import com.gc.safety.dao.CommonDAOHibernate;
import com.gc.safety.po.SafetyClose;
import com.gc.util.CommonUtil;

public class CommonService {

	private CommonDAOHibernate commonDAO;
	private BaseDAOHibernate baseDAO;

	public void setCommonDAO(CommonDAOHibernate commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	public void setBaseDAO(BaseDAOHibernate baseDAO) {
		this.baseDAO = baseDAO;
	}
//==================================== Oracle Function ====================================

	public String getSafetyAccNo(Integer branchId, String typeNo, String accHead ) {
		return commonDAO.getSafetyAccNo(branchId, typeNo, accHead);
	}
	
//==================================== SafetyClose Date ====================================
	public Calendar getLastSafetyCloseDate(Integer branchId) {
		return commonDAO.getLastSafetyCloseDate(branchId);
	}
	
	public void compareDirty(Object[] opos, Map params) {
		String clazz = (String) params.get(Constants.PARAM_CLASS);
		Arrays.sort(opos, Constants.ID_COMPARATOR);
		List pos = baseDAO.getObjects(clazz, params, true);
		Collections.sort(pos, Constants.ID_COMPARATOR);
		String error;
		if (pos.size() != opos.length || !(CommonUtil.equals(pos.toArray(), opos))) {
			error = CommonUtil.getString("error.dirty.data", new Object[]{clazz});
			throw new CommonRuntimeException(error);
		}
	}
	
	public Date closeAcc(SafetyClose close, String type, String user) {
		return commonDAO.closeAcc(close, type, user);
	}
	
	//==================================== now ========================
	public Calendar getNowDate() {
		return commonDAO.getNowDate();
	}
	
}
