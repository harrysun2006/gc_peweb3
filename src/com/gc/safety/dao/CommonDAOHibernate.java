package com.gc.safety.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.safety.po.SafetyClose;

public class CommonDAOHibernate extends HibernateDaoSupport {
	
//==================================== Oracle Function ====================================

	public String getSafetyAccNo(Integer branchId, String typeNo, String accHead ) {
		Query q = getSession().getNamedQuery("SAFETY_NO")
			.setParameter("branchId", branchId)
			.setParameter("type", typeNo)
			.setParameter("accHead", accHead);
		return (String) q.uniqueResult();
	}

//==================================== SafetyClose Date ====================================

	@SuppressWarnings("unchecked")
	public Calendar getLastSafetyCloseDate(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select max(c.id.date) from SafetyClose c")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and c.id.branch.id = :bid");
		sb.append(" group by c.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		List<Calendar> list = q.list();
		return (list.size() == 0) ? null : (Calendar) list.get(0);
	}
	
	public Date closeAcc(SafetyClose close, String type, String user) {
		Query q = getSession().getNamedQuery("SAFETY_CLOSE")
			.setParameter("branchId", close.getBranch().getId())
			.setParameter("date", close.getDate())
			.setParameter("comment", close.getComment())
			.setParameter("type", type)
			.setParameter("user", user);
		return (Date) q.uniqueResult();
	}
//===============================now===============================
	public Calendar getNowDate() {
		Query q = getSession().getNamedQuery("NOW");
		Date sqlDate = (Date) q.uniqueResult();
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		Calendar now = Calendar.getInstance();
		now.setTime(utilDate);
		return now;
	}
}
