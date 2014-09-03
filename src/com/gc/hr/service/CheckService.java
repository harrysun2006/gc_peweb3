package com.gc.hr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gc.Constants;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.common.po.PsnOnline;
import com.gc.common.service.BaseServiceUtil;
import com.gc.exception.CommonRuntimeException;
import com.gc.hr.dao.CheckDAOHibernate;
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
import com.gc.util.CommonUtil;

/**
 * HR Check Service¿‡
 * @author hsun
 *
 */
class CheckService {

	private CheckDAOHibernate checkDAO;

	public void setCheckDAO(CheckDAOHibernate checkDAO) {
		this.checkDAO = checkDAO;
	}

//==================================== ChkHoliday ====================================

	public List<ChkHoliday> getHolidays(Integer branchId) {
		return checkDAO.getHolidays(branchId);
	}

//==================================== ChkWork ====================================

	public List<ChkWork> getWorks(Integer branchId) {
		return checkDAO.getWorks(branchId);
	}

//==================================== ChkExtr ====================================

	public List<ChkExtra> getExtras(Integer branchId) {
		return checkDAO.getExtras(branchId);
	}

//==================================== ChkDisp ====================================

	public List<ChkDisp> getDisps(Integer branchId) {
		return checkDAO.getDisps(branchId);
	}

//==================================== ChkGroup ====================================

	public List<ChkGroup> getGroups(Integer branchId) {
		return checkDAO.getGroups(branchId);
	}

	public List<Department> getDepartmentsAndGroups(Integer departId) {
		return checkDAO.getDepartmentsAndGroups(departId);
	}

	public List<ChkGroup> getGroupsByDepart(Integer departId) {
		return checkDAO.getGroupsByDepart(departId);
	}

	public List<Person> getCheckPersonsByDepart(Integer departId) {
		return checkDAO.getCheckPersonsByDepart(departId);
	}

	public List<Person> getCheckPersonsByGroup(ChkGroup group) {
		return checkDAO.getCheckPersonsByGroup(group);
	}

//==================================== ChkLongPlan ====================================

	public List<ChkLongPlan> getLongPlans(Map qo) {
		return checkDAO.getLongPlans(qo);
	}

	public String saveLongPlan(ChkLongPlan po) {
		return saveLongPlan2(po);
	}

	protected String saveLongPlan1(ChkLongPlan po) {
		String no = CommonServiceUtil.getChkLongPlanNo(po.getBranchId(), po.getCheckDate());
		System.out.println("Get LongPlan NO: " + no);
		if (true) throw new RuntimeException("Hello!");
		return no;
	}

	protected String saveLongPlan2(ChkLongPlan po) {
		String no = po.getNo();
		Date lastCloseDate = CommonServiceUtil.getLastCloseDate(po.getBranchId());
		if (po.getCheckDate() != null 
				&& po.getCheckDate().getTime().compareTo(lastCloseDate) < 0)
				throw new CommonRuntimeException(CommonUtil.getString("error.beyond.closeDate", 
						new Object[]{
						CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, po.getCheckDate()),
						CommonUtil.formatDate(Constants.DEFAULT_DATE_FORMAT, lastCloseDate)}));
		if (no == null || no == "") {// add new ChkLongPlan
			no = CommonServiceUtil.getChkLongPlanNo(po.getBranchId(), po.getCheckDate());
			po.getId().setNo(no);
			BaseServiceUtil.addObject(po);
		} else {
			BaseServiceUtil.updateObject(po);
		}
		return no;
	}

	public List<Person> getCheckPersonsAndCLPs(Map qo) {
		ChkGroup group = new ChkGroup();
		group.setName((String) qo.get("group.name"));
		List<Person> list1 = checkDAO.getCheckPersonsByGroup(group);
		List<ChkLongPlan> list2 = checkDAO.getLongPlans(qo);
		ChkLongPlan clp;
		for(Iterator<ChkLongPlan> it = list2.iterator(); it.hasNext(); ) {
			clp = it.next();
			clp.getPerson().addChkLongPlan(clp);
		}
		return list1;
	}

//==================================== ChkPlan ====================================
	
	public List<ChkPlan> getPlans(Integer branchId) {
		return checkDAO.getPlans(branchId);
	}

	public List<ChkPlan> getPlans(ChkPlan cp) {
		return checkDAO.getPlans(cp);
	}

	public List<ChkPlanD> getPlanDetails(Map qo) {
		return checkDAO.getPlanDetails(qo);
	}

	public List<Person> getCheckPersonsAndCPDs(Map qo) {
		List<ChkPlanD> list1 = checkDAO.getPlanDetails(qo);
		List<Person> list2 = new ArrayList();
		ChkPlanD cpd;
		Person p;
		for(Iterator<ChkPlanD> it = list1.iterator(); it.hasNext(); ) {
			cpd = it.next();
			p = cpd.getPerson();
			p.addChkPlanD(cpd);
			if (!list2.contains(p)) list2.add(p);
		}
		return list2;
	}

//==================================== ChkFact ====================================
	
	public List<ChkFact> getFacts(Integer branchId) {
		return checkDAO.getFacts(branchId);
	}


	public List<ChkFact> getFacts(ChkFact cf) {
		return checkDAO.getFacts(cf);
	}

	public List<ChkFactD> getFactDetails(Map qo) {
		return checkDAO.getFactDetails(qo);
	}

	public List<Person> getCheckPersonsAndCFDs(Map qo) {
		List<ChkFactD> list1 = checkDAO.getFactDetails(qo);
		List<Person> list2 = new ArrayList();
		ChkFactD cfd;
		Person p;
		for(Iterator<ChkFactD> it = list1.iterator(); it.hasNext(); ) {
			cfd = it.next();
			p = cfd.getPerson();
			p.addChkFactD(cfd);
			if (!list2.contains(p)) list2.add(p);
		}
		return list2;
	}

//==================================== Report ====================================

	public List<Person> getPersonsOnlineByDepart(Map qo) {
		return checkDAO.getPersonsOnlineByDepart(qo);
	}

	public Map<PsnOnline, List<ChkFactD>> getOnlinePersonsAndCFDs(Map qo) {
		Person person = qo.containsKey("person") ? (Person) qo.get("person") : null;
		if (person != null) {
			Map q2 = new Hashtable();
			q2.put("id", person.getId());
			q2.put("workerId", person.getWorkerId());
			List<Person> persons = PersonalServiceUtil.findPersons(q2);
			if (persons.size() <= 0) throw new CommonRuntimeException(CommonUtil.getString("error.person.nonexistence", new Object[]{person.getWorkerId()}));
			person = persons.get(0);
			qo.put("person.id", person.getId());
			if (!qo.containsKey("depart.id")) qo.put("depart.id", person.getDepartId());
		}
		List<Object[]> list = checkDAO.getOnlinePersonsAndCFDs(qo);
		Map<PsnOnline, List<ChkFactD>> data = new Hashtable<PsnOnline, List<ChkFactD>>();
		PsnOnline po;
		ChkFactD cfd;
		List<ChkFactD> cfds;
		Object[] objs;
		for (Iterator<Object[]> it = list.iterator(); it.hasNext(); ) {
			objs = it.next();
			po = (PsnOnline) objs[0];
			cfd = (ChkFactD) objs[1];
			if (data.containsKey(po)) {
				cfds = data.get(po);
			} else {
				cfds = new ArrayList<ChkFactD>();
				data.put(po, cfds);
			}
			cfds.add(cfd);
		}
		return data;
	}

	public Map<Person, Map<Object, Long>> getOnlinePersonsAndCheckStat(Map qo) {
		List<Object[]> list1 = checkDAO.getOnlinePersonsAndCheckStat(qo);
		Map<Person, Map<Object, Long>> data = new LinkedHashMap<Person, Map<Object, Long>>();
		Map<Object, Long> stat = null;
		Object[] objs;
		Integer pid = null, cid;
		Person p;
		Long count;
		// list1: person.id, person.name, person.workerId, person.contractNo, holiday, work, extra, disp, count
		for (Iterator<Object[]> it = list1.iterator(); it.hasNext(); ) {
			objs = it.next();
			cid = (Integer) objs[0];
			if (!cid.equals(pid)) {// another person
				pid = cid;
				p = new Person();
				p.setId(cid);
				p.setName((String) objs[1]);
				p.setWorkerId((String) objs[2]);
				p.setContractNo((String) objs[3]);
				stat = new Hashtable<Object, Long>();
				data.put(p, stat);
			}
			for (int i = 4; i < 8 ; i++) {// holiday, work, extra, disp
				if (objs[i] != null) {
					count = stat.containsKey(objs[i]) ? stat.get(objs[i]) : 0;
					count += (Long) objs[8];
					stat.put(objs[i], count);
				}
			}
		}
		return data;
	}

}
