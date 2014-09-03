package com.gc.safety.dao;

import java.util.Date;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.common.po.EquOnline;
import com.gc.common.po.SecurityLimit;
import com.gc.safety.po.TransInfo;
import com.gc.safety.po.TransType;
import com.gc.util.ObjectUtil;

/**
 * safety Transgress detail dao
 * @author xyf
 *
 */
public class TransDAOHibernate extends HibernateDaoSupport {

//==================================== TransInfo ====================================

	/**
	 * 返回当前机构所有明细
	 */
	@SuppressWarnings("unchecked")
	public List<TransInfo> getTransInfos(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransInfo t")
			.append(" left outer join t.branch tb")
			.append(" where tb.id = :id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<TransInfo>) q.list();
	}
	
	public void addTransInfo(TransInfo transInfo) {
		getHibernateTemplate().save(transInfo);
	}
	
	public void saveTransInfo(TransInfo transInfo) {
		getHibernateTemplate().saveOrUpdate(transInfo);
	}
	
	public void updateTransInfo(TransInfo transInfo) {
		getHibernateTemplate().update(transInfo);
	}
	
	public void deleteTransInfo(TransInfo transInfo) {
		getHibernateTemplate().delete(transInfo);
	}
	
	public void flush() {
		getSession().flush();
	}

	public void deleteTransInfoById(Long id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete TransInfo t")
			.append(" where t.id = :id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", id);
	}
	
	/**
	 * 违章处理,按部门、结账日期查询违章信息
	 *
	 */
	public List<TransInfo> getTransListByDeptCloseD(Integer branchId,Integer departId,Calendar closeDate) {
		boolean lock=true;
		StringBuilder sb = new StringBuilder();
		sb.append("select ti from TransInfo ti")
			.append(" left outer join fetch ti.bus")
			.append(" left outer join fetch ti.driver")
			.append(" left outer join fetch ti.inputer")
			.append(" left outer join fetch ti.transType")
			.append(", EquOnline eo")
			.append(" where 1=1");
		if(branchId != null && branchId != -1)
		{
			sb.append(" and ti.id.branch.id = :bid")
				.append(" and ti.id.branch.id = eo.branch.id");
		}
		if(departId != null && departId != -1) sb.append(" and eo.depart.id = :departId");
		sb.append(" and ti.bus.id = eo.equipment.id")
			.append(" and ti.transDate >= eo.onDate")
			.append(" and (ti.transDate < eo.downDate or eo.downDate is null)");
		if(closeDate != null) sb.append(" and ti.inputDate >= :closeDate");
		sb.append(" order by ti.transDate");
		Query query = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != -1) query.setParameter("bid", branchId);
		if(departId != null && departId != -1) query.setParameter("departId", departId);
		if(closeDate != null) query.setParameter("closeDate", closeDate);
		if (lock) query.setLockMode("ti", LockMode.UPGRADE);
		return query.list();
	}
	
	/**
	 * 违章所有条件查询
	 * @param obj
	 * @return
	 */
	public List getTransListByAll(SecurityLimit limit, Map qo)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select ti,eo from TransInfo ti")
		.append(" left outer join fetch ti.bus b")
		.append(" left outer join fetch ti.transType ty")
		.append(" left outer join fetch ti.driver dr")
		.append(" left outer join fetch ti.inputer input")
		.append(", EquOnline eo")
		.append(" left outer join fetch eo.depart d")
		.append(" left outer join fetch eo.line l")
		.append(" where 1=1");
		if (limit != null)
		{
			sb.append(" and ti.id.branch.id = :bid")
				.append(" and ti.id.branch.id = eo.branch.id");
		}
		sb.append(" and ti.bus.id = eo.equipment.id")
			.append(" and d.id = eo.depart.id")
			.append(" and ti.transDate >= eo.onDate")
			.append(" and (ti.transDate < eo.downDate or eo.downDate is null)");
		String key, k1, k2, k3;
		Object value;
		int m1, m2;
		Map<String, String> tables = new Hashtable<String, String>();
		tables.put("bus", "b");
		tables.put("transType", "ty");
		tables.put("driver", "dr");
		tables.put("inputer", "inputer");
		Integer departId=0;
		if (qo != null) {
			/**
			 * birthday_from ==> p.birthday >= :birthday_from
			 * workerId ==> p.workerId like :workerId
			 * chkGroup.id ==> g.id = :chkGroup_id
			 */
			if(qo.containsKey("dept.id") && qo.get("dept.id") != null)
			{
				departId = Integer.valueOf(qo.get("dept.id").toString());
				qo.remove("dept.id");
			}
			for (Iterator it = qo.keySet().iterator(); it.hasNext(); ) {
				key = (String) it.next();
				value = qo.get(key);
				if(value == null) continue;
				m1 = key.indexOf('.');
				m2 = key.indexOf('_');
				if (m1 > 0) { // Process foriegn key object alias
					k1 = key.substring(0, m1);
					k1 = tables.get(k1) + "." + key.substring(m1+1);
				} else {
					k1 = (m2 > 0) ? "ti." + key.substring(0, m2) : "ti." + key;
				}
				if (m2 > 0 && value instanceof Date) {
					k2 = key.substring(m2+1);
					k2 = "from".equals(k2) ? " >= " : " <= ";
				} else if (value instanceof String) {
					k2 = " like ";
				} else if (value instanceof Number) {
					k2 = " = ";
				} else {
					k2 = " = ";
				}
				k3 = key.replace('.', '_');
				sb.append(" and ").append(k1).append(k2).append(":").append(k3);
			}
		}
		sb.append(" order by ti.transDate");
		Query q = getSession().createQuery(sb.toString());
		if (limit != null) q.setParameter("bid", limit.getBranchId());
		if (qo != null) {
			for (Iterator it = qo.keySet().iterator(); it.hasNext(); ) {
				key = (String) it.next();
				value = qo.get(key);
				if(value == null) continue;
				key = key.replace('.', '_');
				if (value instanceof Date) {
					value = ObjectUtil.toCalendar(value);
				}
				else if (value instanceof String) {
					value = "%" + value + "%";
				}
				q.setParameter(key, value);
			}
		}
		List list = q.list();
		if (departId.intValue() != 0) {
			for (int i=list.size()-1; i>=0; i--)
			{
				Object[] objs = (Object[])list.get(i);
				EquOnline equOnline = (EquOnline)objs[1];
				if (!equOnline.getDepartId().equals(departId)) {
					list.remove(i);
				}
			}
		}
		return list;
	}
	

	public List getTransInfoList(Map obj) {
//		String accNo = obj.get("accNo").toString();
//		String transDate1 = obj.get("transDate1").toString();
//		String transDate2 = obj.get("transDate2").toString();
//		String deptName = obj.get("deptName").toString();
//		String lineNo = obj.get("lineNo").toString();
//		String busNo = obj.get("busNo").toString();
//		String authNo = obj.get("authNo").toString();
//		String workNo = obj.get("workNo").toString();
//		String psnName = obj.get("psnName").toString();
//		String typeNo = obj.get("typeNo").toString();
//		String point = obj.get("point").toString();
//		Double penalty = 0D;
//		if(obj.get("penalty") != null)
//			try {
//				penalty = Double.valueOf(obj.get("penalty").toString());
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		String dealDate1 = obj.get("dealDate1").toString();
//		String dealDate2 = obj.get("dealDate2").toString();
//		String doPsn = obj.get("doPsn").toString();
//		String branchId = obj.get("branchId").toString();
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append("select t, o, l from Line l, EquOnline o")
//		.append(" left outer join fetch o.depart,")
//		.append(" TransInfo t")
//		.append(" left outer join fetch t.driver")
//		.append(" left outer join fetch t.bus")
//		.append(" left outer join fetch t.inputer")
//		.append(" left outer join fetch t.transType")
//		.append(" where t.bus.id = o.equipment.id")
//		.append(" and t.bus.branch.id = o.branch.id")
//		.append(" and o.branch.id = l.branch.id")
//		.append(" and o.line.id = l.id")
//		.append(" and t.id.branch.id = :branchId");
//		
//		if (accNo != "") sb.append(" and t.id.accno = '" + accNo + "'");					
//		if (busNo != "") sb.append(" and t.bus.useId = '" + busNo + "'");				
//		if (authNo != "") sb.append(" and t.bus.authNo = '" + authNo + "'");				
//		if (workNo != "") sb.append(" and t.driver.workerId = '" + workNo + "'");		
//		if (psnName != "") sb.append(" and t.driver.name = '" + psnName + "'");			
//		if (typeNo != "") sb.append(" and t.transType.name = '" + typeNo + "'");		
//		if (point != "") sb.append(" and t.point = '" + point+"'");							
//		if (penalty != 0) sb.append(" and t.penalty = " + penalty);					
//		if (doPsn != "") sb.append(" and t.inputer.workerId = '" + doPsn+"'");
//		if (lineNo != "") sb.append(" and l.no = '" + lineNo + "'");
//		if (transDate1 != "") {
//			sb.append(" and t.transDate >= :transDate1");
//			sb.append(" and o.onDate <= :transDate1");
//		}
//		if (transDate2 != "") {
//			sb.append(" and t.transDate <= :transDate2");
//			sb.append(" and (o.downDate >= :transDate2 or o.downDate is null)");
//		}
//		else {
//			sb.append(" and o.downDate is null");
//		}
//		if (dealDate1 != "") sb.append(" and t.doDate >= :doDate1");
//		if (dealDate2 != "") sb.append(" and t.doDate <= :doDate2");
//		if (deptName != "") {
//			sb.append(" and o.depart.name = '" + deptName + "'");
//		}
//		
//		sb.append(" order by t.id.accno, t.id.no");
//		
//		Query q = getSession().createQuery(sb.toString());
//		
//		q.setParameter("branchId", Integer.parseInt(branchId));
//		
//		if (transDate1 != "") {
//			try {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(Date.valueOf(transDate1));
//				q.setParameter("transDate1", cal);
//			} catch (Exception e) {
//				throw new CommonRuntimeException("违法日期格式不正确！");
//			}
//		}
//		if (transDate2 != "") {
//			try {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(Date.valueOf(transDate2));
//				q.setParameter("transDate2", cal);
//			} catch (Exception e) {
//				throw new CommonRuntimeException("违法日期格式不正确！");
//			}
//		}
//		if (dealDate1 != "") {
//			try {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(Date.valueOf(dealDate1));
//				q.setParameter("doDate1", cal);
//			} catch (Exception e) {
//				throw new CommonRuntimeException("受理日期格式不正确！");
//			}
//		}
//		if (dealDate2 != "") {
//			try {
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(Date.valueOf(dealDate2));
//				q.setParameter("doDate2", cal);
//			} catch (Exception e) {
//				throw new CommonRuntimeException("受理日期格式不正确！");
//			}
//		}
//		return q.list();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<TransInfo> getTransInfosForModify(Integer branchId, String accNo, String doDate, String dealDate, Calendar closeDate) {
		boolean lock = true;
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransInfo t")
			.append(" left outer join fetch t.driver")
			.append(" left outer join fetch t.bus")
			.append(" left outer join fetch t.transType")
			.append(" left outer join fetch t.inputer")
			.append(" where 1=1")
		;
		if (branchId != null) sb.append(" and t.id.branch.id = :bid");
		if (accNo != null && accNo != "") sb.append(" and t.id.accNo = :accNo");
		if (closeDate != null) sb.append(" and t.inputDate >= :closeDate"); 
		sb.append(" order by t.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (accNo != null && accNo != "") q.setParameter("accNo", accNo);
		if (closeDate != null) q.setParameter("closeDate", closeDate);
		if (lock) q.setLockMode("t", LockMode.UPGRADE);
		return (List<TransInfo>) q.list();
	}

//==================================== TransType ====================================

	@SuppressWarnings("unchecked")
	public List<TransType> getTransTypes(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransType t").append(
				" left outer join fetch t.branch tb").append(
				" where t.branch.id = :id");
		sb.append(" order by t.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<TransType>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TransType> getTransType1(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransType t where 1 = 1");
		if (branchId != null) sb.append(" and t.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return (List<TransType>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TransType> getTransType2(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransType t left outer join fetch t.branch b where 1 = 1");
		if (branchId != null) sb.append(" and b.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return (List<TransType>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TransType> getTransTypeByName(Integer branchId, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransType t")
			.append(" where t.branch.id = :bid")
			.append(" and t.name = :tname");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", branchId);
		q.setParameter("tname", name);
		return (List<TransType>) q.list();
	}
	
//==========================
	/**
	 * 安全主页面的事故树
	 * @param limit	权限,待扩展
	 * @param dateFrom 起始日期
	 */
	public List getTransInfoForSafetyTree(SecurityLimit limit, Calendar dateFrom) {
//		Long time1 = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("select ti,d from TransInfo ti")
		.append(" left outer join fetch ti.bus")
		.append(" left outer join fetch ti.transType")
		.append(", EquOnline eo, Department d")
		.append(" where 1=1");
		if (limit != null)
		{
			sb.append(" and ti.id.branch.id = :bid")
				.append(" and ti.id.branch.id = eo.branch.id");
		}
		if (dateFrom != null) sb.append(" and ti.transDate >= :dateFrom");
		sb.append(" and ti.bus.id = eo.equipment.id")
			.append(" and d.id = eo.depart.id")
			.append(" and ti.transDate >= eo.onDate")
			.append(" and (ti.transDate < eo.downDate or eo.downDate is null)");
		sb.append(" order by d.id");
		Query q = getSession().createQuery(sb.toString());
		if (limit != null) q.setParameter("bid", limit.getBranchId());
		if (dateFrom != null) q.setParameter("dateFrom", dateFrom);
		List list = q.list();
		
//		System.out.println("违章树查询时间："+(System.currentTimeMillis()-time1));
		
//		return q.list();
		return list;
	}

	/**
	 * 报表单车违章查询
	 * @param userId, dateFrom, dateTo
	 */
	public List<TransInfo> getTransByUIdOrWId(Integer branchId, String authNo, String workerId, Calendar dateFrom, Calendar dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from TransInfo t")
			.append(" left outer join fetch t.depart d")
			.append(" left outer join fetch t.inputer p")
			.append(" left outer join fetch t.bus b")
			.append(" left outer join fetch t.driver dr")
			.append(" left outer join fetch t.transType tt")
			.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and t.id.branch.id = :bid");
		if(authNo != null && authNo != "") sb.append(" and b.authNo = :authNo");
		if(workerId != null && workerId != "") sb.append(" and dr.workerId = :wid");
		if(dateFrom != null) sb.append(" and t.transDate >= :dateFrom");
		if(dateTo != null) sb.append(" and t.transDate <= :dateTo");
		sb.append(" order by t.transDate");
		Query q = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if(authNo != null && authNo != "") q.setParameter("authNo", authNo);
		if(workerId != null && workerId != "") q.setParameter("wid", workerId);
		if(dateFrom != null) q.setParameter("dateFrom", dateFrom);
		if(dateTo != null) q.setParameter("dateTo", dateTo);
		return q.list();
	}
	
	public void addTransType(TransType transType) {
		getHibernateTemplate().save(transType);
	}

	public void saveTransType(TransType transType) {
		getHibernateTemplate().saveOrUpdate(transType);
	}

	public void updateTransType(TransType transType) {
		getHibernateTemplate().update(transType);
	}
	
	public void deleteTransType(TransType transType) {
		getHibernateTemplate().delete(transType);
	}

}
