package com.gc.hr.service;

import java.util.Date;

import com.gc.hr.dao.CommonDAOHibernate;
import com.gc.hr.po.HrClose;

class CommonService {

	private CommonDAOHibernate commonDAO;

	public void setCommonDAO(CommonDAOHibernate commonDAO) {
		this.commonDAO = commonDAO;
	}

//==================================== Voucher Head NO ====================================

	public String getAccNo(Integer branchId, String type, String accHead ) {
		return commonDAO.getAccNo(branchId, type, accHead);
	}

//==================================== Close ====================================

	public Date closeAcc(HrClose close, String type, String user) {
		return commonDAO.closeAcc(close, type, user);
	}
}
