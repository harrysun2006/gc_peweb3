package com.gc.hr.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.common.service.BaseServiceUtil;
import com.gc.hr.dao.SalaryDAOHibernate;
import com.gc.hr.po.SalDeptPsn;
import com.gc.hr.po.SalFact;
import com.gc.hr.po.SalFactD;
import com.gc.hr.po.SalFactIx;
import com.gc.hr.po.SalFixOnline;
import com.gc.hr.po.SalItem;
import com.gc.hr.po.SalTemplate;
import com.gc.hr.po.SalTemplateD;
import com.gc.hr.po.SalTemplateIx;
import com.gc.util.DateUtil;

/**
 * HR Salary Service类
 * @author hsun
 *
 */
class SalaryService {

	private SalaryDAOHibernate salaryDAO;

	public void setSalaryDAO(SalaryDAOHibernate salaryDAO) {
		this.salaryDAO = salaryDAO;
	}

//==================================== SalItem ====================================

	public List<SalItem> getItems(Map qo) {
		return salaryDAO.getItems(qo);
	}

//==================================== SalDeptPsn ====================================

	public Map<Department, Long> getDeptPsnStat(Map qo) {
		List<Object[]> list = salaryDAO.getDeptPsnStat(qo);
		Map<Department, Long> data = new LinkedHashMap<Department, Long>();
		Department depart;
		Long count;
		Object[] objs;
		for (Iterator<Object[]> it = list.iterator(); it.hasNext(); ) {
			objs = it.next();
			depart = (Department) objs[0];
			count = objs[1] == null ? 0 : (Long) objs[1];
			data.put(depart, count);
		}
		return data;
	}

	public List<SalDeptPsn> getDeptPsns(Map qo) {
		return salaryDAO.getDeptPsns1(qo);
	}

	/**
	 * 返回没有指定发薪部门的人员列表person-->depart(0)
	 * @param qo
	 * @return
	 */
	public List<Person> getDeptPsns0(Map qo) {
		return salaryDAO.getDeptPsns0(qo);
	}

	/**
	 * 返回指定>=1个发薪部门的人员列表person-->depart(>=1)
	 * @param qo
	 * @return
	 */
	public Map<SalDeptPsn, List<SalFixOnline>> getDeptPsn1(Map qo) {
		Calendar now = DateUtil.getBeginCal(Calendar.getInstance(), Calendar.DATE);
		qo.put("onDate_to", now);
		qo.put("downDate_from", now);
		List<SalDeptPsn> list1 = salaryDAO.getDeptPsns1(qo);
		List<SalFixOnline> list2 = salaryDAO.getFixOnlines(qo);
		Map<SalDeptPsn, List<SalFixOnline>> data = new LinkedHashMap<SalDeptPsn, List<SalFixOnline>>();
		SalDeptPsn sdp;
		SalFixOnline sfo;
		SalItem si;
		List<SalFixOnline> sfos = null;
		// 注意拼数据需要根据DAO的排序字段来比较id(person:workerId, deprt:id)!
		String pid0 = null, pid1 = null, pid2 = null;
		Integer did0 = null, did1 = null, did2 = null;
		int i = 0, j = 0;
		List<SalItem> items = new ArrayList<SalItem>();
		while (i < list1.size() && j < list2.size()) {
			sdp = list1.get(i);
			sfo = list2.get(j);
			si = sfo.getItem();
			if (!items.contains(si)) items.add(si);
			pid1 = sdp.getPersonWorkerId();
			did1 = sdp.getDepartId();
			if (!pid1.equals(pid0) || did1 != did0) {
				sfos = new ArrayList<SalFixOnline>();
				data.put(sdp, sfos);
				pid0 = pid1;
				did0 = did1;
			}
			pid2 = sfo.getPersonWorkerId();
			did2 = sfo.getDepartId();
			if (pid2 == null) {
				j++;
				continue;
			}
			if (pid2.equals(pid1)) {
				if (did2 == did1) sfos.add(sfo);
				if (did2 <= did1) j++;
				else i++;
			} else if (pid2.compareTo(pid1) > 0) {
				i++;
			} else {
				j++;
			}
		}
		while (i < list1.size()) {
			sdp = list1.get(i);
			if (!data.containsKey(sdp)) {
				sfos = new ArrayList<SalFixOnline>();
				data.put(sdp, sfos);
			}
			i++;
		}
		qo.put("#items", items);
		qo.put("#time", now);
		return data;
	}

	/**
	 * 返回指定>1个发薪部门的人员列表person-->depart(>1)
	 * @param qo
	 * @return
	 */
	public Map<SalDeptPsn, List<SalFixOnline>> getDeptPsn2(Map qo) {
		Calendar now = DateUtil.getBeginCal(Calendar.getInstance(), Calendar.DATE);
		qo.put("onDate_to", now);
		qo.put("downDate_from", now);
		List<SalDeptPsn> list1 = salaryDAO.getDeptPsns2(qo);
		List<Integer> persons = new ArrayList<Integer>();
		Integer personId = null;
		for (SalDeptPsn deptPsn : list1) {
			if (personId != deptPsn.getPersonId()) {
				personId = deptPsn.getPersonId();
				persons.add(personId);
			}
		}
		qo.put("persons", persons);
		List<SalFixOnline> list2 = salaryDAO.getFixOnlines(qo);
		Map<SalDeptPsn, List<SalFixOnline>> data = new LinkedHashMap<SalDeptPsn, List<SalFixOnline>>();
		SalDeptPsn sdp;
		SalFixOnline sfo;
		SalItem si;
		List<SalFixOnline> sfos = null;
		String pid0 = null, pid1 = null, pid2 = null;
		Integer did0 = null, did1 = null, did2 = null;
		int i = 0, j = 0;
		List<SalItem> items = new ArrayList<SalItem>();
		while (i < list1.size() && j < list2.size()) {
			sdp = list1.get(i);
			sfo = list2.get(j);
			si = sfo.getItem();
			if (!items.contains(si)) items.add(si);
			pid1 = sdp.getPersonWorkerId();
			did1 = sdp.getDepartId();
			if (!pid1.equals(pid0) || did1 != did0) {
				sfos = new ArrayList<SalFixOnline>();
				data.put(sdp, sfos);
				pid0 = pid1;
				did0 = did1;
			}
			pid2 = sfo.getPersonWorkerId();
			did2 = sfo.getDepartId();
			if (pid2.equals(pid1)) {
				if (did2 == did1) sfos.add(sfo);
				if (did2 <= did1) j++;
				else i++;
			} else if (pid2.compareTo(pid1) > 0) {
				i++;
			} else {
				j++;
			}
		}
		while (i < list1.size()) {
			sdp = list1.get(i);
			if (!data.containsKey(sdp)) {
				sfos = new ArrayList<SalFixOnline>();
				data.put(sdp, sfos);
			}
			i++;
		}
		qo.put("#items", items);
		qo.put("#time", now);
		return data;
	}

	public List<SalFixOnline> getFixOnlines(Map qo) {
		return salaryDAO.getFixOnlines(qo);
	}

	public int changeDeptPsn(SalDeptPsn osdp, SalDeptPsn nsdp) {
		return salaryDAO.changeDeptPsn(osdp, nsdp);
	}

	public int changeDeptPsns(SalDeptPsn[] osdps, SalDeptPsn[] nsdps) {
		if (osdps == null || nsdps == null || osdps.length != nsdps.length) return 0;
		for (int i = 0; i < nsdps.length; i++) {
			changeDeptPsn(osdps[i], nsdps[i]);
		}
		return nsdps.length;
	}

	public int deleteDeptPsns(SalDeptPsn[] sdps) {
		if (sdps == null) return 0;
		for (int i = 0; i < sdps.length; i++) {
			salaryDAO.deleteDeptPsn(sdps[i]);
		}
		return sdps.length;
	}

	public void addFixOnlines(SalFixOnline[] sfos, String user) {
		for (int i = 0; i < sfos.length; i++) {
			Map params = new HashMap();
			params.put("branch.id", sfos[i].getBranchId());
			params.put("depart.no", sfos[i].getDepartNo());
			params.put("person.no", sfos[i].getPersonWorkerId());
			params.put("item.no", sfos[i].getItemNo());
			params.put("date", sfos[i].getOnDate());
			params.put("value", sfos[i].getAmount());
			params.put("doPerson", user);
			params.put("doDate", Calendar.getInstance());
			salaryDAO.addFixOnline(params);
		}
	}

	public void terminateFixOnlines(SalFixOnline[] sfos, String user) {
		for (int i = 0; i < sfos.length; i++) {
			Map params = new HashMap();
			params.put("branch.id", sfos[i].getBranchId());
			params.put("depart.no", sfos[i].getDepartNo());
			params.put("person.no", sfos[i].getPersonWorkerId());
			params.put("item.no", sfos[i].getItemNo());
			params.put("date", sfos[i].getDownDate());
			params.put("value", sfos[i].getAmount());
			params.put("doPerson", user);
			params.put("doDate", Calendar.getInstance());
			salaryDAO.terminateFixOnline(params);
		}
	}

	public void changeFixOnlines(SalFixOnline[] sfos, String user) {
		for (int i = 0; i < sfos.length; i++) {
			Map params = new HashMap();
			params.put("branch.id", sfos[i].getBranchId());
			params.put("depart.no", sfos[i].getDepartNo());
			params.put("person.no", sfos[i].getPersonWorkerId());
			params.put("item.no", sfos[i].getItemNo());
			params.put("date", sfos[i].getOnDate());
			params.put("value", sfos[i].getAmount());
			params.put("doPerson", user);
			params.put("doDate", Calendar.getInstance());
			salaryDAO.terminateFixOnline(params);
			salaryDAO.addFixOnline(params);
		}
	}

//==================================== SalTemplate ====================================

	public List<SalTemplate> getTemplates(Map qo) {
		return salaryDAO.getTemplates(qo);
	}

	public List<SalTemplateD> getTemplateDetails(Map qo) {
		return salaryDAO.getTemplateDetails(qo);
	}

	public void deleteTemplate(SalTemplate st) {
		salaryDAO.deleteTemplate(st);
	}

	public Integer[] getTemplateIx(SalTemplate st) {
		return salaryDAO.getTemplateIx(st);
	}

	public void deleteTemplateIx(SalTemplate st) {
		salaryDAO.deleteTemplateIx(st);
	}

	public SalTemplate saveTemplate(SalTemplate ost, Integer[] oix, SalTemplateD[] ostds, SalTemplate nst, Integer[] nix, SalTemplateD[] nstds) {
		Integer nid = nst.getId();
		if (nid == null || nid == 0) BaseServiceUtil.addObject(nst);
		else BaseServiceUtil.updateObject(nst);
		if (ost != null) {
			// 删除薪资模板明细
			salaryDAO.deleteTemplateD(ost);
			// 删除薪资模板项目序号
			salaryDAO.deleteTemplateIx(ost);
		}
		BaseServiceUtil.flush();
		int i;
		SalTemplateIx stix;
		if (nstds.length == 0) { // 清空所有明细时删除模板
			BaseServiceUtil.deleteObject(nst);
			nst = null;
		} else { // 记录模板项目序号
			for (i = 0; i < nix.length; i++) {
				stix = new SalTemplateIx(nst.getBranchId(), nst.getId(), i+1, nix[i]);
				BaseServiceUtil.addObject(stix);
			}
		}
		BaseServiceUtil.flush();
		for (i = 0; i < nstds.length; i++) {
			nstds[i].getId().setTemplate(nst);
			BaseServiceUtil.addObject(nstds[i]);
		}
		return nst;
	}

//==================================== SalFact ====================================

	public List<SalFact> getFacts(Map qo) {
		return salaryDAO.getFacts(qo);
	}

	public List<SalFactD> getFactDetails(Map qo) {
		return salaryDAO.getFactDetails(qo);
	}

	public void deleteFact(SalFact sf) {
		salaryDAO.deleteFact(sf);
	}

	public Integer[] getFactIx(SalFact sf) {
		return salaryDAO.getFactIx(sf);
	}

	public void checkFact(SalFact sf) {
		salaryDAO.checkFact(sf);
	}

	/**
	 * 保存薪资凭证性能调优, 加入索引后性能有所提高, 但不明显; 后采用凭证级检查+索引方式.
	 * 加入的索引:
	 * CREATE INDEX IDX_HRSAL_FACTDT ON T_HRSAL_FACTDT (C_BELONG, C_HDNO, C_NO, C_PERSON) LOGGING NOPARALLEL;
	 * CREATE INDEX IDX_HRSAL_FIXONLINE ON T_HRSAL_FIXONLINE (C_BELONG, C_DEPART, C_PERSON, C_ITEM, C_ONDATE, C_DOWNDATE, C_AMOUNT) LOGGING NOPARALLEL;
	 * 测试结果(ms)  调优前  加入索引后  索引+凭证检查
	 * flex->java     5468       5469           5797
	 * java(db)      72703      51250           3641
	 * java->flex       16         16             16
	 * total         78187      56735           9453
	 * 主要瓶颈在原先的SP_HRSAL_FACTDT过程的调用, 500*20+的凭证将调用10000+次此过程!!!
	 * 保存时500*20+的凭证数据上传量大致在2MB左右, VPN环境下增加20s左右开销!!!
	 * @param osf: 薪资凭证(修改前)
	 * @param oix: 项目序号(修改前)
	 * @param osfds: 薪资凭证明细(修改前)
	 * @param nsf: 薪资凭证(修改后)
	 * @param nix: 项目序号(修改后)
	 * @param nsfds: 薪资凭证明细(修改后)
	 * @return
	 */
	public SalFact saveFact(SalFact osf, Integer[] oix, SalFactD[] osfds, SalFact nsf, Integer[] nix, SalFactD[] nsfds) {
		String nno = nsf.getNo();
		String no;
		if (nno == null) {
			no = CommonServiceUtil.getSalFactNo(nsf.getBranchId(), nsf.getIssueDate());
			nsf.getId().setNo(no);
		}
		if (nno == null) BaseServiceUtil.addObject(nsf);
		else BaseServiceUtil.updateObject(nsf);
		if (osf != null) {
			// BaseServiceUtil.deleteObjects(SalFactD.class.getName(), osf);
			// 删除薪资凭证明细
			salaryDAO.deleteFactD(osf);
			// 删除薪资凭证项目序号
			salaryDAO.deleteFactIx(osf);
		}
		BaseServiceUtil.flush();
		int i;
		SalFactIx sfix;
		if (nsfds.length == 0) { // 清空所有明细时删除薪资凭证
			BaseServiceUtil.deleteObject(nsf);
			nsf = null;
		} else { // 记录薪资凭证项目序号
			checkFact(nsf);
			for (i = 0; i < nix.length; i++) {
				sfix = new SalFactIx(nsf.getBranchId(), nsf.getNo(), i+1, nix[i]);
				BaseServiceUtil.addObject(sfix);
			}
		}
		BaseServiceUtil.flush();
		for (i = 0; i < nsfds.length; i++) {
			nsfds[i].getId().setFact(nsf);
			// addFactD(npos[i]);
			BaseServiceUtil.addObject(nsfds[i]);
		}
		return nsf;
	}

	public void addFactD(SalFactD sfd) {
		salaryDAO.addFactD(sfd);
	}

	public List<SalFactD> getFactDs(Map qo) {
		return salaryDAO.getFactDs(qo);
	}
	
	public List<SalFactD> getFactDs2(Map qo) {
		return salaryDAO.getFactDs2(qo);
	}

//==================================== Report ====================================

	public List<Object[]> getPaySocialInsSals(Map qo) {
		return salaryDAO.getPaySocialInsSals(qo);
	}
}
