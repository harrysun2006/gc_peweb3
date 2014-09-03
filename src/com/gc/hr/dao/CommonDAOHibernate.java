package com.gc.hr.dao;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.hr.po.HrClose;

public class CommonDAOHibernate extends HibernateDaoSupport {
	
//==================================== Voucher Head NO ====================================

	public String getAccNo(Integer branchId, String type, String accHead ) {
		Query q = getSession().getNamedQuery("HR_NO")
			.setParameter("branchId", branchId)
			.setParameter("type", type)
			.setParameter("accHead", accHead);
		return (String) q.uniqueResult();
	}

//==================================== Close ====================================

	public Date closeAcc(HrClose close, String type, String user) {
		Query q = getSession().getNamedQuery("HR_CLOSE")
			.setParameter("branchId", close.getBranch().getId())
			.setParameter("date", close.getDate())
			.setParameter("comment", close.getComment())
			.setParameter("type", type)
			.setParameter("user", user);
		return (Date) q.uniqueResult();
	}
}
