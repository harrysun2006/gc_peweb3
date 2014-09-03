package com.gc.hr.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.Constants;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.hr.po.ChkDisp;
import com.gc.hr.po.ChkExtra;
import com.gc.hr.po.ChkFact;
import com.gc.hr.po.ChkFactD;
import com.gc.hr.po.ChkGroup;
import com.gc.hr.po.ChkHoliday;
import com.gc.hr.po.ChkLongPlan;
import com.gc.hr.po.ChkPlan;
import com.gc.hr.po.ChkPlanD;
import com.gc.hr.po.ChkWork;
import com.gc.util.DateUtil;
import com.gc.util.ObjectUtil;

/**
 * HR Check DAO类
 * @author hsun
 *
 */
public class CheckDAOHibernate extends HibernateDaoSupport {

//==================================== ChkHoliday ====================================

	public List<ChkHoliday> getHolidays(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select h from ChkHoliday h")
			.append(" left outer join fetch h.branch b")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and h.branch.id = :branchId");
		sb.append(" order by h.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("branchId", branchId);
		return (List<ChkHoliday>) q.list();
	}

//==================================== ChkWork ====================================

	public List<ChkWork> getWorks(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select w from ChkWork w")
			.append(" left outer join fetch w.branch b")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and w.branch.id = :branchId");
		sb.append(" order by w.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("branchId", branchId);
		return (List<ChkWork>) q.list();
	}

//==================================== ChkExtra ====================================

	public List<ChkExtra> getExtras(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select e from ChkExtra e")
			.append(" left outer join fetch e.branch b")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and e.branch.id = :branchId");
		sb.append(" order by e.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("branchId", branchId);
		return (List<ChkExtra>) q.list();
	}

//==================================== ChkDisp ====================================

	public List<ChkDisp> getDisps(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select d from ChkDisp d")
			.append(" left outer join fetch d.branch b")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and d.branch.id = :branchId");
		sb.append(" order by d.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("branchId", branchId);
		return (List<ChkDisp>) q.list();
	}

//==================================== ChkGroup ====================================

	public List<ChkGroup> getGroups(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select g from ChkGroup g")
			.append(" left outer join fetch g.branch b")
			.append(" left outer join fetch g.depart d")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and g.branch.id = :branchId");
		sb.append(" order by g.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("branchId", branchId);
		return (List<ChkGroup>) q.list();
	}

	/**
	 * 返回部门(含考勤组)列表
	 * @param branchId
	 * @return
	 */
	public List<Department> getDepartmentsAndGroups(Integer departId) {
		List<ChkGroup> glist = getGroupsByDepart(departId);
		List<Department> list = new ArrayList<Department>();
		ChkGroup group;
		Department depart;
		for (Iterator<ChkGroup> it = glist.iterator(); it.hasNext(); ) {
			group = it.next();
			depart = group.getDepart();
			depart.addCheckGroup(group);
			if (!list.contains(depart)) list.add(depart);
		}
		return list;
	}

	public List<ChkGroup> getGroupsByDepart(Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cg from ChkGroup cg left outer join fetch cg.depart d where d.downDate >= :departDownDate");
		if (departId != null && departId != 0) sb.append(" and d.id = :did");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("departDownDate", Constants.MAX_DATE);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		sb.append(" order by cg.no");
		return (List<ChkGroup>) q.list();
	}

	public List<Person> getCheckPersonsByDepart(Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup cg")
			.append(" where p.downDate >= :downDate");
		if (departId != null && departId != 0) sb.append(" and cg.depart.id = :departId");
		else sb.append(" and cg.depart.id is not null");
		sb.append(" order by p.id");
		Query q = getSession().createQuery(sb.toString());
		if (departId != null && departId != 0) q.setParameter("departId", departId);
		q.setParameter("downDate", Constants.MAX_DATE);
		return (List<Person>) q.list();
	}

	public List<Person> getCheckPersonsByGroup(ChkGroup group) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup cg")
			.append(" where p.downDate >= :downDate");
		if (group != null) {
			if (group.getId() != null && group.getId() != 0) sb.append(" and cg.id = :gid");
			if (group.getBranchId() != null && group.getBranchId() != 0) sb.append(" and cg.branch.id = :bid");
			if (group.getNo() != null && group.getNo() != "") sb.append(" and cg.no = :gno");
			if (group.getName() != null && group.getName() != "") sb.append(" and cg.name = :gname");
			if (group.getDepartId() != null && group.getDepartId() != 0) sb.append(" and cg.depart.id = :did");
		}
		Query q = getSession().createQuery(sb.toString());
		if (group != null) {
			if (group.getId() != null && group.getId() != 0) q.setParameter("gid", group.getId());
			if (group.getBranchId() != null && group.getBranchId() != 0) q.setParameter("bid", group.getBranchId());
			if (group.getNo() != null && group.getNo() != "") q.setParameter("gno", group.getNo());
			if (group.getName() != null && group.getName() != "") q.setParameter("gname", group.getName());
			if (group.getDepartId() != null && group.getDepartId() != 0) q.setParameter("did", group.getDepartId());
		}
		q.setParameter("downDate", Constants.MAX_DATE);
		return (List<Person>) q.list();
	}

//==================================== ChkLongPlan ====================================
	
	public List<ChkLongPlan> getLongPlans(Map qo) {
		return getLongPlans2(qo);
	}

	protected List<ChkLongPlan> getLongPlans1(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select clp from ChkLongPlan clp")
			.append(" left outer join fetch clp.person p1")
			.append(" left outer join fetch clp.holiday ch")
			.append(" left outer join fetch clp.checker p3")
			.append(" left outer join fetch clp.lastModifier p4")
			.append(" where 1=1");
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and clp.id.branch.id = :branchId");
			if (qo.containsKey("depart.id")) 
				sb.append(" and p1.id in (")
					.append("select p2.id from Person p2 where p2.chkGroup.id in (")
					.append("select cg.id from ChkGroup cg where cg.depart.id = :departId")
					.append("))");
			if (qo.containsKey("person.name")) sb.append(" and p1.name like :personName");
			if (qo.containsKey("checkDate_from")) sb.append(" and clp.checkDate >= :checkDate_from");
			if (qo.containsKey("checkDate_to")) sb.append(" and clp.checkDate <= :checkDate_to");
			if (qo.containsKey("date_from")) sb.append(" and clp.endDate >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and clp.fromDate <= :date_to");
		}
		sb.append(" order by clp.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("branchId", qo.get("branch.id"));
			if (qo.containsKey("depart.id")) q.setParameter("departId", qo.get("depart.id"));
			if (qo.containsKey("person.name")) q.setParameter("personName", "%" + qo.get("person.name") + "%");
			if (qo.containsKey("checkDate_from")) q.setParameter("checkDate_from", ObjectUtil.toCalendar(qo.get("checkDate_from")));
			if (qo.containsKey("checkDate_to")) q.setParameter("checkDate_to", ObjectUtil.toCalendar(qo.get("checkDate_to")));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
		}
		return (List<ChkLongPlan>) q.list();
	}

	protected List<ChkLongPlan> getLongPlans2(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select clp from ChkLongPlan clp")
			.append(" inner join fetch clp.person p1")
			.append(" inner join fetch clp.holiday ch")
			.append(" inner join fetch clp.checker p2")
			.append(" inner join fetch clp.lastModifier p3")
			.append(" where 1=1");
		Integer did = qo.containsKey("depart.id") ? (Integer) qo.get("depart.id") : null;
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and clp.id.branch.id = :branchId");
			if (did != null && did != 0) sb.append(" and p1.chkGroup.depart.id = :departId");
			if (qo.containsKey("group.name")) sb.append(" and p1.chkGroup.name = :groupName");
			if (qo.containsKey("person.name")) sb.append(" and p1.name like :personName");
			if (qo.containsKey("checkDate_from")) sb.append(" and clp.checkDate >= :checkDate_from");
			if (qo.containsKey("checkDate_to")) sb.append(" and clp.checkDate <= :checkDate_to");
			if (qo.containsKey("date_from")) sb.append(" and clp.endDate >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and clp.fromDate <= :date_to");
		}
		sb.append(" order by clp.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("branchId", qo.get("branch.id"));
			if (did != null && did != 0) q.setParameter("departId", qo.get("depart.id"));
			if (qo.containsKey("group.name")) q.setParameter("groupName", qo.get("group.name"));
			if (qo.containsKey("person.name")) q.setParameter("personName", "%" + qo.get("person.name") + "%");
			if (qo.containsKey("checkDate_from")) q.setParameter("checkDate_from", ObjectUtil.toCalendar(qo.get("checkDate_from")));
			if (qo.containsKey("checkDate_to")) q.setParameter("checkDate_to", ObjectUtil.toCalendar(qo.get("checkDate_to")));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
		}
		return (List<ChkLongPlan>) q.list();
	}

	public List<ChkLongPlan> getLongPlansByGroup(ChkGroup group) {
		StringBuilder sb = new StringBuilder();
		sb.append("select clp from ChkLongPlan clp")
			.append(" left outer join fetch clp.person p1")
			.append(" left outer join fetch clp.holiday ch")
			.append(" where 1=1");
		if (group != null) {
			sb.append(" and p1.id in (")
				.append("select p2.id from Person p2 where p2.chkGroup.id in (")
				.append("select cg.id from ChkGroup cg where 1=1");
			if (group.getId() != null && group.getId() != 0) sb.append(" and cg.id = :gid");
			if (group.getBranchId() != null && group.getBranchId() != 0) sb.append(" and cg.branch.id = :bid");
			if (group.getNo() != null && group.getNo() != "") sb.append(" and cg.no = :gno");
			if (group.getName() != null && group.getName() != "") sb.append(" and cg.name = :gname");
			if (group.getDepartId() != null && group.getDepartId() != 0) sb.append(" and cg.depart.id = :did");
			sb.append("))");
		}
		Query q = getSession().createQuery(sb.toString());
		if (group != null) {
			if (group.getId() != null && group.getId() != 0) q.setParameter("gid", group.getId());
			if (group.getBranchId() != null && group.getBranchId() != 0) q.setParameter("bid", group.getBranchId());
			if (group.getNo() != null && group.getNo() != "") q.setParameter("gno", group.getNo());
			if (group.getName() != null && group.getName() != "") q.setParameter("gname", group.getName());
			if (group.getDepartId() != null && group.getDepartId() != 0) q.setParameter("did", group.getDepartId());
		}
		return (List<ChkLongPlan>) q.list();
	}

//==================================== ChkPlan ====================================

	public List<ChkPlan> getPlans(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cp from ChkPlan cp where 1 = 1");
		if (branchId != null) sb.append(" and cp.id.branch.id = :branchId");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("branchId", branchId);
		return (List<ChkPlan>) q.list();
	}

	public List<ChkPlan> getPlans(ChkPlan cp) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cp from ChkPlan cp")
			.append(" left outer join fetch cp.depart d")
			.append(" left outer join fetch cp.checker c")
			.append(" where cp.id.branch.id = :branchId")
			.append(" and d.id = :departId and cp.office = :office")
			.append(" and (select max(cpd1.date) from ChkPlanD cpd1 where cpd1.id.plan.id = cp.id) >= :date1")
			.append(" and (select min(cpd2.date) from ChkPlanD cpd2 where cpd2.id.plan.id = cp.id) <= :date2");
		sb.append(" order by cp.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("branchId", cp.getBranchId());
		q.setParameter("departId", cp.getDepartId());
		q.setParameter("office", cp.getOffice());
		q.setParameter("date1", DateUtil.getBeginCal(cp.getDate()));
		q.setParameter("date2", DateUtil.getEndCal(cp.getDate()));
		return (List<ChkPlan>) q.list();
	}

	public List<ChkPlanD> getPlanDetails(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cpd from ChkPlanD cpd")
			.append(" left outer join fetch cpd.person p")
			.append(" left outer join fetch cpd.id.plan cp")
			.append(" left outer join fetch cpd.holiday h")
			.append(" left outer join fetch cpd.work w")
			.append(" left outer join fetch cpd.extra e")
			.append(" where 1 = 1");
		Person person = null;
		if (qo != null) {
			if (qo.containsKey("plan.branch.id")) sb.append(" and cp.id.branch.id = :branchId");
			if (qo.containsKey("plan.no")) sb.append(" and cp.id.no = :no");
			if (qo.containsKey("plan.depart.id")) sb.append(" and cp.depart.id = :departId");
			if (qo.containsKey("plan.office")) sb.append(" and cp.office = :office");
			if (qo.containsKey("date_from")) sb.append(" and cpd.date >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and cpd.date <= :date_to");
			if (qo.containsKey("person")) person = (Person) qo.get("person");
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) sb.append(" and p.id = :personId");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "id";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" cpd.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("plan.branch.id")) q.setParameter("branchId", qo.get("plan.branch.id"));
			if (qo.containsKey("plan.no")) q.setParameter("no", qo.get("plan.no"));
			if (qo.containsKey("plan.depart.id")) q.setParameter("departId", qo.get("plan.depart.id"));
			if (qo.containsKey("plan.office")) q.setParameter("office", qo.get("plan.office"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) q.setParameter("personId", person.getId());
		}
		return (List<ChkPlanD>) q.list();
	}

//==================================== ChkFact ====================================
	
	public List<ChkFact> getFacts(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select f from ChkFact f where 1 = 1");
		if (branchId != null) sb.append(" and f.id.branch.id = :branchId");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("branchId", branchId);
		return (List<ChkFact>) q.list();
	}

	public List<ChkFact> getFacts(ChkFact cf) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cf from ChkFact cf")
			.append(" left outer join fetch cf.depart d")
			.append(" left outer join fetch cf.checker c")
			.append(" where cf.id.branch.id = :branchId")
			.append(" and d.id = :departId and cf.office = :office")
			.append(" and (select max(cfd1.date) from ChkFactD cfd1 where cfd1.id.fact.id = cf.id) >= :date1")
			.append(" and (select min(cfd2.date) from ChkFactD cfd2 where cfd2.id.fact.id = cf.id) <= :date2");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("branchId", cf.getBranchId());
		q.setParameter("departId", cf.getDepartId());
		q.setParameter("office", cf.getOffice());
		q.setParameter("date1", DateUtil.getBeginCal(cf.getDate()));
		q.setParameter("date2", DateUtil.getEndCal(cf.getDate()));
		return (List<ChkFact>) q.list();
	}

	public List<ChkFactD> getFactDetails(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select cfd from ChkFactD cfd")
			.append(" left outer join fetch cfd.person p")
			.append(" left outer join fetch cfd.id.fact cf")
			.append(" left outer join fetch cfd.holiday h")
			.append(" left outer join fetch cfd.work w")
			.append(" left outer join fetch cfd.extra e")
			.append(" left outer join fetch cfd.disp d");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch cfd.").append(fetchs[i].trim());
		}
		sb.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("fact.branch.id")) sb.append(" and cf.id.branch.id = :branchId");
			if (qo.containsKey("fact.no")) sb.append(" and cf.id.no = :no");
			if (qo.containsKey("fact.depart.id")) sb.append(" and cf.depart.id = :departId1");
			if (qo.containsKey("fact.office")) sb.append(" and cf.office = :office");
			if (qo.containsKey("date_from")) sb.append(" and cfd.date >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and cfd.date <= :date_to");
			if (qo.containsKey("person.id")) sb.append(" and p.id = :personId");
		}
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (order == null) order = "id";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" cfd.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("fact.branch.id")) q.setParameter("branchId", qo.get("fact.branch.id"));
			if (qo.containsKey("fact.no")) q.setParameter("no", qo.get("fact.no"));
			if (qo.containsKey("fact.depart.id")) q.setParameter("departId1", qo.get("fact.depart.id"));
			if (qo.containsKey("fact.office")) q.setParameter("office", qo.get("fact.office"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
			if (qo.containsKey("person.id")) q.setParameter("personId", qo.get("person.id"));
		}
		return (List<ChkFactD>) q.list();
	}

//==================================== Report ====================================

	public List<Person> getPersonsOnlineByDepart(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p where p.id in (select po.person.id from PsnOnline po where 1=1");
		String order = (String) qo.get(Constants.PARAM_ORDER);
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and po.branch.id = :branchId");
			if (qo.containsKey("depart.id")) sb.append(" and po.depart.id = :departId");
			if (qo.containsKey("date_from")) sb.append(" and po.downDate >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and po.onDate <= :date_to");
			if (qo.containsKey("onDate_from")) sb.append(" and po.onDate >= :onDate_from");
			if (qo.containsKey("onDate_to")) sb.append(" and po.onDate <= :onDate_to");
			if (qo.containsKey("downDate_from")) sb.append(" and po.downDate >= :downDate_from");
			if (qo.containsKey("downDate_to")) sb.append(" and po.downDate <= :downDate_to");
		}
		sb.append(")");
		if (order == null) order = "id";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" p.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("branchId", qo.get("branch.id"));
			if (qo.containsKey("depart.id")) q.setParameter("departId", qo.get("depart.id"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
			if (qo.containsKey("onDate_from")) q.setParameter("onDate_from", ObjectUtil.toCalendar(qo.get("onDate_from")));
			if (qo.containsKey("onDate_to")) q.setParameter("onDate_to", ObjectUtil.toCalendar(qo.get("onDate_to")));
			if (qo.containsKey("downDate_from")) q.setParameter("downDate_from", ObjectUtil.toCalendar(qo.get("downDate_from")));
			if (qo.containsKey("downDate_to")) q.setParameter("downDate_to", ObjectUtil.toCalendar(qo.get("downDate_to")));
		}
		return (List<Person>) q.list();
	}

	/**
	 * 返回的列表元素为对象数组: [po, cfd]
	 * po: PsnOnline(fetched: depart, person, person.fkPosition)
	 * cfd: ChkFactD(fetched: fact, holiday, work, extra, disp)
	 * @param qo
	 * @return
	 */
	public List<Object[]> getOnlinePersonsAndCFDs(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po, cfd from PsnOnline po left outer join fetch po.depart d")
			.append(" left outer join fetch po.person p")
			.append(" left outer join fetch p.fkPosition pp")
			.append(", ChkFactD cfd left outer join fetch cfd.id.fact cf")
			.append(" left outer join fetch cfd.holiday h")
			.append(" left outer join fetch cfd.work w")
			.append(" left outer join fetch cfd.extra e")
			.append(" left outer join fetch cfd.disp di")
			.append(" where po.person = cfd.person and cfd.date >= po.onDate and cfd.date <= po.downDate");
		Person person = null;
		Department depart = null;
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and po.branch.id = :branchId");
			if (qo.containsKey("person.id")) sb.append(" and p.id = :personId1");
			if (qo.containsKey("depart.id")) sb.append(" and d.id = :departId1");
			if (qo.containsKey("date_from")) sb.append(" and cfd.date >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and cfd.date <= :date_to");
			if (qo.containsKey("person")) person = (Person) qo.get("person");
			if (qo.containsKey("depart")) depart = (Department) qo.get("depart");
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) sb.append(" and p.id = :personId2");
		}
		if (depart != null) {
			if (depart.getId() != null && depart.getId() != 0) sb.append(" and d.id = :departId2");
		}
		sb.append(" order by cfd.person, cfd.date");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("branchId", qo.get("branch.id"));
			if (qo.containsKey("person.id")) q.setParameter("personId1", qo.get("person.id"));
			if (qo.containsKey("depart.id")) q.setParameter("departId1", qo.get("depart.id"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) q.setParameter("personId2", person.getId());
		}
		if (depart != null) {
			if (depart.getId() != null && depart.getId() != 0) q.setParameter("departId2", depart.getId());
		}
		return q.list();
	}

	/**
	 * 返回的列表元素为对象数组: [person.id, person.name, person.workerId, person.contractNo, work, holiday, extra, disp, count]
	 * person.id: Integer, 人员ID
	 * person.name: String, 姓名
	 * person.workerId: String, 工号
	 * person.contractNo: String, 合同号
	 * work: ChkWork
	 * holiday: ChkHoliday
	 * extra: ChkExtra
	 * disp: ChkDisp
	 * count: Long, 数量
	 * @param qo
	 * @return
	 */
	public List<Object[]> getOnlinePersonsAndCheckStat(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p.id, p.name, p.workerId, p.contractNo")
			.append(", w, h, e, d, count(*) from PsnOnline po")
			.append(" left outer join po.person p")
			.append(" left outer join p.fkPosition pp")
			.append(", ChkFactD cfd")
			.append(" left outer join cfd.holiday h")
			.append(" left outer join cfd.work w")
			.append(" left outer join cfd.extra e")
			.append(" left outer join cfd.disp d")
			.append(" where po.person = cfd.person and cfd.date >= po.onDate and cfd.date <= po.downDate");
		Person person = null;
		Department depart = null;
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and po.branch.id = :branchId");
			if (qo.containsKey("person.id")) sb.append(" and p.id = :personId1");
			if (qo.containsKey("depart.id")) sb.append(" and po.depart.id = :departId1");
			if (qo.containsKey("date_from")) sb.append(" and cfd.date >= :date_from");
			if (qo.containsKey("date_to")) sb.append(" and cfd.date <= :date_to");
			if (qo.containsKey("person")) person = (Person) qo.get("person");
			if (qo.containsKey("depart")) depart = (Department) qo.get("depart");
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) sb.append(" and p.id = :personId2");
		}
		if (depart != null) {
			if (depart.getId() != null && depart.getId() != 0) sb.append(" and po.depart.id = :departId2");
		}
		sb.append(" group by p.id, p.name, p.workerId, p.contractNo")
			.append(", w.id, w.branch, w.no, w.name, w.comment")
			.append(", h.id, h.branch, h.no, h.name, h.comment")
			.append(", e.id, e.branch, e.no, e.name, e.comment")
			.append(", d.id, d.branch, d.no, d.name, d.comment");
		sb.append(" order by p.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("branchId", qo.get("branch.id"));
			if (qo.containsKey("person.id")) q.setParameter("personId1", qo.get("person.id"));
			if (qo.containsKey("depart.id")) q.setParameter("departId1", qo.get("depart.id"));
			if (qo.containsKey("date_from")) q.setParameter("date_from", ObjectUtil.toCalendar(qo.get("date_from")));
			if (qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) q.setParameter("personId2", person.getId());
		}
		if (depart != null) {
			if (depart.getId() != null && depart.getId() != 0) q.setParameter("departId2", depart.getId());
		}
		return q.list();
	}
}
