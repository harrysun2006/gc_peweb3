package com.gc.hr.service;

import java.util.Map;

import com.gc.Constants;
import com.gc.Predecessor;
import com.gc.hr.po.ChkFact;
import com.gc.hr.po.ChkLongPlan;
import com.gc.hr.po.ChkPlan;
import com.gc.hr.po.SalFact;
import com.gc.util.CommonUtil;

public class NoPredecessor implements Predecessor {

	public Object process(Object obj, Map context) {
		String pno = (String) context.get(Constants.PARAM_NO);
		// Æ¾Ö¤±àºÅ
		Object value = CommonUtil.getValue(obj, pno);
		if (value != null) return value;
		String no = null;
		if (obj instanceof ChkFact) {
			ChkFact fact = (ChkFact) obj;
			no = CommonServiceUtil.getChkFactNo(fact.getBranchId(), fact.getCheckDate());
		} else if (obj instanceof ChkLongPlan) {
			ChkLongPlan clp = (ChkLongPlan) obj;
			no = CommonServiceUtil.getChkLongPlanNo(clp.getBranchId(), clp.getCheckDate());
		} else if (obj instanceof ChkPlan) {
			ChkPlan plan = (ChkPlan) obj;
			no = CommonServiceUtil.getChkPlanNo(plan.getBranchId(), plan.getCheckDate());
		} else if (obj instanceof SalFact) {
			SalFact fact = (SalFact) obj;
			no = CommonServiceUtil.getSalFactNo(fact.getBranchId(), fact.getIssueDate());
		}
		if (no != null) CommonUtil.setValue(obj, pno, no);
		return no;
	}
}
