package com.gc.hr.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.Constants;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.common.po.Position;
import com.gc.common.po.PositionPK;
import com.gc.hr.po.SalDeptPsn;
import com.gc.hr.po.SalFact;
import com.gc.hr.po.SalFactD;
import com.gc.hr.po.SalFixOnline;
import com.gc.hr.po.SalItem;
import com.gc.hr.po.SalTemplate;
import com.gc.hr.po.SalTemplateD;
import com.gc.util.CommonUtil;
import com.gc.util.ObjectUtil;

/**
 * HR Salary DAO类
 * @author hsun
 *
 */
@SuppressWarnings("deprecation")
public class SalaryDAOHibernate extends HibernateDaoSupport {

//==================================== SalItem ====================================

	public List<SalItem> getItems(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select si from SalItem si")
			.append(" left outer join fetch si.branch b");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch cfd.").append(fetchs[i].trim());
		}
		sb.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and si.branch.id = :bid");
			if (qo.containsKey("flag")) sb.append(" and si.flag = :flag");
			if (qo.containsKey("type")) sb.append(" and si.type = :type");
			if (qo.containsKey("print")) sb.append(" and si.print = :print");
			if (qo.containsKey("onDate_from")) sb.append(" and si.onDate >= :onDate_from");
			if (qo.containsKey("onDate_to")) sb.append(" and si.onDate <= :onDate_to");
			if (qo.containsKey("downDate_from")) sb.append(" and si.downDate >= :downDate_from");
			if (qo.containsKey("downDate_to")) sb.append(" and si.downDate <= :downDate_to");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "no";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" si.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("flag")) q.setParameter("flag", qo.get("flag"));
			if (qo.containsKey("type")) q.setParameter("type", qo.get("type"));
			if (qo.containsKey("print")) q.setParameter("print", qo.get("print"));
			if (qo.containsKey("onDate_from")) q.setParameter("onDate_from", ObjectUtil.toCalendar(qo.get("onDate_from")));
			if (qo.containsKey("onDate_to")) q.setParameter("onDate_to", ObjectUtil.toCalendar(qo.get("onDate_to")));
			if (qo.containsKey("downDate_from")) q.setParameter("downDate_from", ObjectUtil.toCalendar(qo.get("downDate_from")));
			if (qo.containsKey("downDate_to")) q.setParameter("downDate_to", ObjectUtil.toCalendar(qo.get("downDate_to")));
		}
		return (List<SalItem>) q.list();
	}

//==================================== SalDeptPsn ====================================

	/**
	 * 返回的列表元素为对象数组: [depart, count]
	 * depart: Department
	 * count: Long
	 * @param qo
	 * @return
	 */
	public List<Object[]> getDeptPsnStat(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select d, (select count(*) from SalDeptPsn sdp where sdp.id.depart.id = d.id");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sdp.id.branch.id = :bid");
		}
		sb.append(" group by sdp.id.depart.id")
			.append(") from Department d where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and d.branch.id = :bid");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "id";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" d.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
		}
		return (List<Object[]>) q.list();
	}

	public List<SalDeptPsn> getDeptPsns1(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sdp from SalDeptPsn sdp")
			.append(" left outer join fetch sdp.id.depart d")
			.append(" left outer join fetch sdp.id.person p")
			.append(" left outer join fetch p.fkPosition pp")
			.append(" left outer join fetch p.depart pd")
			.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sdp.id.branch.id = :bid");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and sdp.id.depart.id = :did");
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) sb.append(" and sdp.id.person.id = :pid");
		}
		sb.append(" order by sdp.id.person.workerId, sdp.id.depart.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) q.setParameter("pid", qo.get("person.id"));
		}
		return (List<SalDeptPsn>) q.list();
	}

	public List<Person> getDeptPsns0(Map qo) {
		return getDeptPsns0A(qo);
	}

	protected List<Person> getDeptPsns0A(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch p.").append(fetchs[i].trim());
		}
		sb.append(" where p.id not in (select sdp.id.person.id from SalDeptPsn sdp where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sdp.id.branch.id = :bid");
		}
		sb.append(")");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and p.branch.id = :bid");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "workerId";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" p.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
		}
		return (List<Person>) q.list();
	}

	protected List<Person> getDeptPsns0B(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT P.C_PERSONALID, P.C_WORKERID, P.C_PERSONALNAME, P.C_PERSONALONDATE, ")
			.append("P.C_PERSONALDOWNDATE, P.C_ALLOTDATE, P.C_TYPE, P.C_GRADE, P.C_UPGRADE_DATE, ")
			.append("D.C_DEPARTMENTID, D.C_NO, D.C_DEPARTMENTNAME, ")
			.append("POS.C_NO, POS.C_NAME, ")
			.append("L.C_NO, L.C_NAME, ")
			.append("E.C_EQUUSEID, E.C_AUTHORIZATIONNO ")
			.append("FROM T_PERSONAL P LEFT OUTER JOIN T_DEPARTMENT D ON P.C_BELONG = D.C_BELONG AND P.C_DEPART = D.C_DEPARTMENTID ")
			.append(" LEFT OUTER JOIN T_POSITION POS ON P.C_BELONG = POS.C_BELONG AND P.C_POSITION = POS.C_NO")
			.append(" LEFT OUTER JOIN T_RUNNING_LINE L ON P.C_BELONG = L.C_BELONG AND P.C_LINEID = L.C_ID")
			.append(" LEFT OUTER JOIN T_EQUIPMENT E ON P.C_BELONG = E.C_BELONG AND P.C_BUSID = E.C_EQUID");
		sb.append(" WHERE P.C_PERSONALID NOT IN (SELECT C_PERSON FROM T_HRSAL_DEPTPSN WHERE 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" AND C_BELONG = :bid");
		}
		sb.append(")");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" AND P.C_BELONG = :bid");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "C_WORKERID";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" ORDER BY");
			for (int i = 0; i < orders.length; i++) sb.append(" P.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Session session = getSession();
		Query q = session.createSQLQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
		}
		List<Object[]> l = q.list();
		List<Person> r = new ArrayList<Person>();
		Object[] o;
		Person person;
		Department depart;
		Position pos;
		for (Iterator<Object[]> it = l.iterator(); it.hasNext(); ) {
			o = it.next();
			person = new Person();
			person.setId(ObjectUtil.toInt(o[0]));
			person.setWorkerId((String) o[1]);
			person.setName((String) o[2]);
			person.setOnDate(ObjectUtil.toCalendar(o[3]));
			person.setDownDate(ObjectUtil.toCalendar(o[4]));
			person.setAllotDate(ObjectUtil.toCalendar(o[5]));
			person.setType((String) o[6]);
			person.setGrade((String) o[7]);
			person.setUpgradeDate(ObjectUtil.toCalendar(o[8]));
			depart = new Department();
			depart.setId(ObjectUtil.toInt(o[9]));
			depart.setNo((String) o[10]);
			depart.setName((String) o[11]);
			person.setDepart(depart);
			pos = new Position();
			pos.setId(new PositionPK(0, (String) o[12]));
			pos.setName((String) o[13]);
			person.setFkPosition(pos);
			session.evict(person);
			session.evict(depart);
			session.evict(pos);
			r.add(person);
		}
		return r;
	}

	protected List<Person> getDeptPsns0C(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select new Person(p.id, p.workerId, p.name, p.depart, p.line, p.bus) from Person p");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch p.").append(fetchs[i].trim());
		}
		sb.append(" where p.id not in (select sdp.id.person.id from SalDeptPsn sdp where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sdp.id.branch.id = :bid");
		}
		sb.append(")");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and p.branch.id = :bid");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "workerId";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" p.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
		}
		return (List<Person>) q.list();
	}

	/**
	 * 返回发薪人员在多个发薪部门的发薪部门人员
	 * @param qo
	 * @return
	 */
	public List<SalDeptPsn> getDeptPsns2(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sdp from SalDeptPsn sdp")
			.append(" left outer join fetch sdp.id.depart d")
			.append(" left outer join fetch sdp.id.person p")
			.append(" left outer join fetch p.fkPosition pp")
			.append(" left outer join fetch p.depart pd")
			.append(" where exists (")
			.append(" select sdp1.id.branch, sdp1.id.person, count(sdp1.id.depart) from SalDeptPsn sdp1")
			.append(" where sdp.id.branch.id = sdp1.id.branch.id")
			.append(" and sdp.id.person.id = sdp1.id.person.id")
			.append(" group by sdp1.id.branch.id, sdp1.id.person.id")
			.append(" having count(sdp1.id.depart) > 1")
			.append(")");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sdp.id.branch.id = :bid");
		}
		sb.append(" order by sdp.id.person.workerId, sdp.id.depart.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
		}
		return q.list();
	}

	public List<SalFixOnline> getFixOnlines(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sfo from SalFixOnline sfo")
			.append(" left outer join fetch sfo.id.item si");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch sfo.").append(fetchs[i].trim());
		}
		sb.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sfo.id.branch.id = :bid");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and sfo.id.depart.id = :did");
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) sb.append(" and sfo.id.person.id = :pid");
			if (qo.containsKey("sal.template.id")) sb.append(" and sfo.id.person.id in (select std.person.id from SalTemplateD std where std.id.branch.id = :bid and std.id.template.id = :stid)");
			else if (qo.containsKey("pwids") && qo.get("pwids") != null && ((Object[]) qo.get("pwids")).length > 0) sb.append(" and sfo.id.person.workerId in (:pwids)");
			else if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" and sfo.id.person.id in (:persons)");
			if (qo.containsKey("items") && qo.get("items") != null && ((List) qo.get("items")).size() > 0) sb.append(" and sfo.id.item.id in (:items)");
			if (qo.containsKey("onDate_from")) sb.append(" and sfo.id.onDate >= :onDate_from");
			if (qo.containsKey("onDate_to")) sb.append(" and sfo.id.onDate <= :onDate_to");
			if (qo.containsKey("downDate_from")) sb.append(" and sfo.downDate >= :downDate_from");
			if (qo.containsKey("downDate_to")) sb.append(" and sfo.downDate <= :downDate_to");
		}
		sb.append(" order by sfo.id.person.workerId, sfo.id.depart.id, si.no");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) q.setParameter("pid", qo.get("person.id"));
			if (qo.containsKey("sal.template.id")) q.setParameter("stid", qo.get("sal.template.id"));
			else if (qo.containsKey("pwids") && qo.get("pwids") != null && ((Object[]) qo.get("pwids")).length > 0) q.setParameterList("pwids", (Object[]) qo.get("pwids"));
			else if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q.setParameterList("persons", (List) qo.get("persons"));
			if (qo.containsKey("items") && qo.get("items") != null && ((List) qo.get("items")).size() > 0) q.setParameterList("items", (List) qo.get("items"));
			if (qo.containsKey("onDate_from")) q.setParameter("onDate_from", ObjectUtil.toCalendar(qo.get("onDate_from")));
			if (qo.containsKey("onDate_to")) q.setParameter("onDate_to", ObjectUtil.toCalendar(qo.get("onDate_to")));
			if (qo.containsKey("downDate_from")) q.setParameter("downDate_from", ObjectUtil.toCalendar(qo.get("downDate_from")));
			if (qo.containsKey("downDate_to")) q.setParameter("downDate_to", ObjectUtil.toCalendar(qo.get("downDate_to")));
		}
		return (List<SalFixOnline>) q.list();
	}

	public int changeDeptPsn(SalDeptPsn osdp, SalDeptPsn nsdp) {
		// if (nsdp.getId().equals(osdp.getId())) getHibernateTemplate().update(nsdp);
		StringBuilder sb = new StringBuilder();
		sb.append("update SalDeptPsn sdp")
			.append(" set sdp.id.depart.id = :newDepartId")
			.append(", sdp.bank = :bank")
			.append(", sdp.bankCard = :bankCard")
			.append(", sdp.comment = :comment")
			.append(" where sdp.id.branch.id = :bid")
			.append(" and sdp.id.depart.id = :did")
			.append(" and sdp.id.person.id = :pid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("newDepartId", nsdp.getDepartId());
		q.setParameter("bank", nsdp.getBank());
		q.setParameter("bankCard", nsdp.getBankCard());
		q.setParameter("comment", nsdp.getComment());
		q.setParameter("bid", osdp.getBranchId());
		q.setParameter("did", osdp.getDepartId());
		q.setParameter("pid", osdp.getPersonId());
		return q.executeUpdate();
	}

	public int deleteDeptPsn(SalDeptPsn sdp) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete SalDeptPsn sdp where sdp.id = :id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", sdp.getId());
		return q.executeUpdate();
	}

	protected void spFixOnline(Map params) {
		try {
			int branchId = ((Integer) params.get("branch.id")).intValue();
			String departNo = (String) params.get("depart.no");
			String type = (String) params.get("type");
			String personNo = (String) params.get("person.no");
			String itemNo = (String) params.get("item.no");
			String date = CommonUtil.formatCalendar("yyyy/MM/dd", ObjectUtil.toCalendar(params.get("date")));
			double value = ((Double) params.get("value")).doubleValue();
			String doPerson = (String) params.get("doPerson");
			String doDate = CommonUtil.formatCalendar("yyyy/MM/dd", ObjectUtil.toCalendar(params.get("doDate")));
			Connection conn = getSession().connection();
			CallableStatement cs = conn.prepareCall("{call SP_HRSAL_FIXONLINE(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			int index = 1;
			cs.setInt(index++, branchId);
			cs.setString(index++, departNo);
			cs.setString(index++, type);
			cs.setString(index++, personNo);
			cs.setString(index++, itemNo);
			cs.setString(index++, date);
			cs.setDouble(index++, value);
			cs.setString(index++, doPerson);
			cs.setString(index++, doDate);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		}
		/*
		Query q = getSession().getNamedQuery("HRSAL_FIXONLINE")
			.setParameter("branchId", params.get("branch.id"))
			.setParameter("departNo", params.get("depart.no"))
			.setParameter("type", params.get("type"))
			.setParameter("personNo", params.get("person.no"))
			.setParameter("itemNo", params.get("item.no"))
			.setParameter("date", CommonUtil.formatCalendar("yyyy/MM/dd", ObjectUtil.toCalendar(params.get("date"))))
			.setParameter("value", params.get("value"))
			.setParameter("doPerson", params.get("doPerson"))
			.setParameter("doDate", CommonUtil.formatCalendar("yyyy/MM/dd", ObjectUtil.toCalendar(params.get("doDate"))));
		q.uniqueResult();
		*/
	}

	public void addFixOnline(Map params) {
		params.put("type", "1");
		spFixOnline(params);
	}

	public void terminateFixOnline(Map params) {
		params.put("type", "2");
		spFixOnline(params);
	}

	public void changeFixOnline(Map params) {
		addFixOnline(params);
		terminateFixOnline(params);
	}

//==================================== SalTemplate ====================================

	public List<SalTemplate> getTemplates(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select st from SalTemplate st")
			.append(" left outer join fetch st.depart")
			.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and st.branch.id = :bid");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and st.depart.id = :did");
		}
		sb.append(" order by st.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
		}
		return (List<SalTemplate>) q.list();
	}

	public List<SalTemplateD> getTemplateDetails(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select std from SalTemplateD std")
			.append(" left outer join fetch std.id.branch b")
			.append(" left outer join fetch std.id.template st")
			.append(" left outer join fetch std.id.item si")
			.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and std.id.branch.id = :bid");
			if (qo.containsKey("template.id") && !qo.get("template.id").equals(0)) sb.append(" and st.id = :stid");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and st.depart.id = :did");
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) sb.append(" and std.person.id = :pid");
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" and std.person.id in (:persons)");
		}
		sb.append(" order by std.id.branch.id, st.depart.id, st.id, std.id.no, si.no");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("template.id") && !qo.get("template.id").equals(0)) q.setParameter("stid", qo.get("template.id"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) q.setParameter("pid", qo.get("person.id"));
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q.setParameterList("persons", (List) qo.get("persons"));
		}
		return (List<SalTemplateD>) q.list();
	}

	public void deleteTemplateD(SalTemplate st) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete SalTemplateD std where std.id.branch.id = :bid and std.id.template.id = :tid");
		Query q = getSession().createQuery(sb.toString())
							.setParameter("bid", st.getBranchId())
							.setParameter("tid", st.getId());
		q.executeUpdate();
	}

	public void deleteTemplateIx(SalTemplate st) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete SalTemplateIx stix where stix.id.branch.id = :bid and stix.id.template.id = :tid");
		Query q = getSession().createQuery(sb.toString())
							.setParameter("bid", st.getBranchId())
							.setParameter("tid", st.getId());
		q.executeUpdate();
	}

	public void deleteTemplate(SalTemplate st) {
		deleteTemplateD(st);
		deleteTemplateIx(st);
		getHibernateTemplate().delete(st);
	}

	public Integer[] getTemplateIx(SalTemplate st) {
		StringBuilder sb = new StringBuilder();
		sb.append("select stix.id.item.id from SalTemplateIx stix")
			.append(" where stix.id.branch.id = :bid and stix.id.template.id = :stid")
			.append(" order by stix.no");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", st.getBranchId());
		q.setParameter("stid", st.getId());
		List<Integer> r = q.list();
		return (Integer[]) r.toArray(new Integer[]{});
	}

//==================================== SalFact ====================================

	public List<SalFact> getFacts(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sf from SalFact sf")
			.append(" left outer join fetch sf.depart")
			.append(" left outer join fetch sf.issuer")
			.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sf.id.branch.id = :bid");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and sf.depart.id = :did");
			if (qo.containsKey("fact.no") && !qo.get("fact.no").equals("")) sb.append(" and sf.id.no = :sfno");
		}
		sb.append(" order by sf.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("fact.no") && !qo.get("fact.no").equals("")) q.setParameter("sfno", qo.get("fact.no"));
		}
		return (List<SalFact>) q.list();
	}

	public List<SalFactD> getFactDetails(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sfd from SalFactD sfd")
			.append(" left outer join fetch sfd.id.fact sf")
			.append(" left outer join fetch sfd.id.item si")
			.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sf.id.branch.id = :bid");
			if (qo.containsKey("fact.no") && !qo.get("fact.no").equals("")) sb.append(" and sf.id.no = :sfno");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and sf.depart.id = :did");
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) sb.append(" and sfd.person.id = :pid");
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" and sfd.person.id in (:persons)");
		}
		sb.append(" order by sf.id.branch.id, sf.depart.id, sf.id.no, sfd.id.no, si.no");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("fact.no") && !qo.get("fact.no").equals("")) q.setParameter("sfno", qo.get("fact.no"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("person.id") && !qo.get("person.id").equals(0)) q.setParameter("pid", qo.get("person.id"));
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q.setParameterList("persons", (List) qo.get("persons"));
		}
		return (List<SalFactD>) q.list();
	}

	public void deleteFactD(SalFact sf) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete SalFactD sfd where sfd.id.fact.id.branch.id = :bid and sfd.id.fact.id.no = :fno");
		Query q = getSession().createQuery(sb.toString())
							.setParameter("bid", sf.getBranchId())
							.setParameter("fno", sf.getNo());
		q.executeUpdate();
	}

	public void deleteFactIx(SalFact sf) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete SalFactIx sfix where sfix.id.fact.id.branch.id = :bid and sfix.id.fact.id.no = :fno");
		Query q = getSession().createQuery(sb.toString())
							.setParameter("bid", sf.getBranchId())
							.setParameter("fno", sf.getNo());
		q.executeUpdate();
	}

	public void deleteFact(SalFact sf) {
		deleteFactD(sf);
		deleteFactIx(sf);
		getHibernateTemplate().delete(sf);
	}

	public Integer[] getFactIx(SalFact sf) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sfix.id.item.id from SalFactIx sfix")
			.append(" where sfix.id.fact.id.branch.id = :bid and sfix.id.fact.id.no = :sfno")
			.append(" order by sfix.no");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", sf.getBranchId());
		q.setParameter("sfno", sf.getNo());
		List<Integer> r = q.list();
		return (Integer[]) r.toArray(new Integer[]{});
	}

	public void checkFact(SalFact sf) {
		try {
			Connection conn = getSession().connection();
			CallableStatement cs = conn.prepareCall("{call SP_HRSAL_FACT(?, ?)}");
			int index = 1;
			cs.setInt(index++, sf.getBranchId());
			cs.setString(index++, sf.getNo());
			cs.execute();
			cs.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	public void addFactD(SalFactD sfd) {
		Map params = new Hashtable();
		params.put("branch.id", sfd.getBranchId());
		params.put("fact.no", sfd.getHdNo());
		params.put("no", sfd.getNo());
		params.put("item.id", sfd.getItemId());
		params.put("depart.id", sfd.getFact().getDepartId());
		params.put("person.id", sfd.getPersonId());
		params.put("amount", sfd.getAmount());
		params.put("date", sfd.getFact().getDate());
		params.put("person.workerId", sfd.getPerson().getWorkerId());
		addFactD(params);
	}

	public void addFactD(Map params) {
		params.put("type", "1");
		spFactD(params);
	}

	protected void spFactD(Map params) {
		try {
			Connection conn = getSession().connection();
			CallableStatement cs = conn.prepareCall("{call SP_HRSAL_FACTDT(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			int branchId = ((Integer) params.get("branch.id")).intValue();
			String hdNo = (String) params.get("fact.no");
			int no = ((Integer) params.get("no")).intValue();
			int itemId = ((Integer) params.get("item.id")).intValue();
			int departId = ((Integer) params.get("depart.id")).intValue();
			int personId = ((Integer) params.get("person.id")).intValue();
			double amount = ((Double) params.get("amount")).doubleValue();
			Date date = new Date(ObjectUtil.toCalendar(params.get("date")).getTimeInMillis());
			String workerId = (String) params.get("person.workerId");
			String type = (String) params.get("type");
			int index = 1;
			cs.setInt(index++, branchId);
			cs.setString(index++, hdNo);
			cs.setInt(index++, no);
			cs.setInt(index++, itemId);
			cs.setInt(index++, departId);
			cs.setInt(index++, personId);
			cs.setDouble(index++, amount);
			cs.setDate(index++, date);
			cs.setString(index++, workerId);
			cs.setString(index++, type);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	public List<SalFactD> getFactDs(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sfd from SalFactD sfd")
			.append(" left outer join fetch sfd.id.fact")
			.append(" left outer join fetch sfd.id.fact.depart")
			.append(" left outer join fetch sfd.id.fact.issuer")
			.append(" left outer join fetch sfd.id.item")
			.append(" left outer join fetch sfd.person")
			.append(" where 1=1");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sfd.id.fact.id.branch.id = :bid");
			if (qo.containsKey("fact.no")) sb.append(" and sfd.id.fact.id.no = :no");
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) sb.append(" and sfd.id.fact.depart.id = :did");
			if (qo.containsKey("date_from")) sb.append(" and sfd.id.fact.date >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and sfd.id.fact.date <= :date_to");
			if (qo.containsKey("issueDate_from")) sb.append(" and sfd.id.fact.issueDate = :issueDate_from");
			if (qo.containsKey("issueDate_to")) sb.append(" and sfd.id.fact.issueDate = :issueDate_to");
			if (qo.containsKey("issueType")) sb.append(" and sfd.id.fact.issueType = :type");
			if (qo.containsKey("issuer.workerId")) sb.append(" and sfd.id.fact.issuer.workerId = :uid");
			if (qo.containsKey("summary")) sb.append(" and sfd.id.fact.summary = :summary");
			if (qo.containsKey("comment")) sb.append(" and sfd.id.fact.comment = :comment");
		}
		sb.append(" order by sfd.id.fact.id.no, sfd.id.no, sfd.id.item.no");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("fact.no")) q.setParameter("no", qo.get("fact.no"));
			if (qo.containsKey("depart.id") && !qo.get("depart.id").equals(0)) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
			if (qo.containsKey("issueDate_from")) q.setParameter("issueDate_from", ObjectUtil.toCalendar(qo.get("issueDate_from")));
			if (qo.containsKey("issueDate_to")) q.setParameter("issueDate_to", ObjectUtil.toCalendar(qo.get("issueDate_to")));
			if (qo.containsKey("issueType")) q.setParameter("type", qo.get("issueType"));
			if (qo.containsKey("issuer.workerId")) q.setParameter("uid", qo.get("issuer.workerId"));
			if (qo.containsKey("summary")) q.setParameter("summary", qo.get("summary"));
			if (qo.containsKey("comment")) q.setParameter("comment", qo.get("comment"));
		}
		return (List<SalFactD>) q.list();
	}
	
	public List<SalFactD> getFactDs2(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sfd, p.depart from SalFactD sfd")
		.append(" left outer join fetch sfd.id.fact")
		.append(" left outer join fetch sfd.id.fact.depart")
		.append(" left outer join fetch sfd.id.fact.issuer")
		.append(" left outer join fetch sfd.id.item")
		.append(" left outer join fetch sfd.person")
		.append(", PsnOnline p")
		.append(" where 1=1")
		.append(" and sfd.id.fact.id.branch.id = p.branch.id")
		.append(" and sfd.person.id = p.person.id")
		.append(" and p.onDate <= sfd.id.fact.issueDate")
		.append(" and p.downDate >= sfd.id.fact.issueDate");
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) sb.append(" and sfd.id.fact.id.branch.id = :bid");
			if (qo.containsKey("date_from") && qo.get("date_from") != null) sb.append(" and sfd.id.fact.issueDate >= :from");
			if (qo.containsKey("date_to") && qo.get("date_to") != null) sb.append(" and sfd.id.fact.issueDate <= :end");
			if (qo.containsKey("items.id")) sb.append(" and sfd.id.item.id in (:items)");
		}
		sb.append(" order by sfd.id.fact.depart.id, p.depart.id, sfd.person.workerId, sfd.id.fact.id.no, sfd.id.item.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id") && !qo.get("branch.id").equals(0)) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("date_from") && qo.get("date_from") != null) q.setParameter("from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to") && qo.get("date_to") != null) q.setParameter("end", ObjectUtil.toCalendar(qo.get("date_to")));
			if (qo.containsKey("items.id")) {
				List<SalItem> list = (List<SalItem>) qo.get("items.id");
				List<Integer> items = new ArrayList<Integer>();
				for (SalItem item : list) {
					if (items.indexOf(item.getId()) == -1) items.add(item.getId());
				}
				q.setParameterList("items", items);
			}
		}
		
		List l = q.list();
		List<SalFactD> ret = new ArrayList<SalFactD>();
		for (int i = 0; i < l.size(); i ++) {
			Object[] obj = (Object[]) l.get(i);
			SalFactD sal = (SalFactD) obj[0];
			sal.getPerson().setDepart((Department) obj[1]);
			ret.add(sal);
		}
		return ret;
	}

//==================================== Report ====================================

	/**
	 * 读取人员(全部)在薪资系统中有社保项目发生的月数
	 */
	public List<Object[]> getPaySocialInsSals(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C_PERSON, COUNT(*)")
			.append(" FROM (SELECT C_PERSON, C_HDNO, COUNT(*)")
			.append(" FROM T_HRSAL_FACTDT T1, T_HRSAL_ITEM T2, T_HRSAL_FACT T3")
			.append(" WHERE T1.C_BELONG = T2.C_BELONG AND T1.C_ITEM = T2.C_ID")
			.append(" AND T1.C_BELONG = T3.C_BELONG AND T1.C_HDNO = T3.C_NO")
			.append(" AND T2.C_TYPE = :typeSG AND T1.C_AMOUNT <> 0");
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" AND T1.C_BELONG = :bid");
			if (qo.containsKey("sal.depart.id")) sb.append(" AND T3.C_DEPART = :sdid");
			if (qo.containsKey("date_from")) sb.append(" AND T3.C_DATE >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" AND T3.C_DATE <= :date_to");
			if (qo.containsKey("issueDate_from")) sb.append(" AND T3.C_ISSUEDATE >= :issueDate_from");
			if (qo.containsKey("issueDate_to")) sb.append(" AND T3.C_ISSUEDATE >= :issueDate_to");
		}
		sb.append(" GROUP BY C_PERSON, C_HDNO) T")
			.append(" GROUP BY C_PERSON");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setParameter("typeSG", SalItem.TYPE_SG);
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("sal.depart.id")) q.setParameter("sdid", qo.get("sal.depart.id"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
			if (qo.containsKey("issueDate_from")) q.setParameter("issueDate_from", ObjectUtil.toCalendar(qo.get("issueDate_from")));
			if (qo.containsKey("issueDate_to")) q.setParameter("issueDate_to", ObjectUtil.toCalendar(qo.get("issueDate_to")));
		}
		return (List<Object[]>) q.list();
	}
}
