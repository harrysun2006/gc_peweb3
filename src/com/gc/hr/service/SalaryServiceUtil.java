package com.gc.hr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.gc.Constants;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.hr.po.SalDeptPsn;
import com.gc.hr.po.SalFact;
import com.gc.hr.po.SalFactD;
import com.gc.hr.po.SalFactDPK;
import com.gc.hr.po.SalFixOnline;
import com.gc.hr.po.SalItem;
import com.gc.hr.po.SalTemplate;
import com.gc.hr.po.SalTemplateD;
import com.gc.util.SpringUtil;

/**
 * HR Salary ServiceUtil类
 * @author hsun
 *
 */
public class SalaryServiceUtil {

	public static final String BEAN_NAME = "hrSalaryServiceUtil";

	private SalaryService salaryService;

	public static SalaryService getSalaryService() {
		ApplicationContext ctx = SpringUtil.getContext();
		SalaryServiceUtil util = (SalaryServiceUtil) ctx.getBean(BEAN_NAME);
		SalaryService service = util.salaryService;
		return service;
	}

	public void setSalaryService(SalaryService salaryService) {
		this.salaryService = salaryService;
	}

//==================================== SalItem ====================================

	public static List<SalItem> getAllItems(Integer branchId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		return getItems(qo);
	}

	public static List<SalItem> getItems(Map qo) {
		return getSalaryService().getItems(qo);
	}

//==================================== SalDeptPsn & SalFixOnline ====================================

	public static List<SalDeptPsn> getDeptPsns(Integer branchId, Integer departId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		if (departId != null) qo.put("depart.id", departId);
		return getDeptPsns(qo);
	}

	public static List<SalDeptPsn> getDeptPsns(Map qo) {
		return getSalaryService().getDeptPsns(qo);
	}

	public static List getDeptPsnListA(Integer branchId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		return getDeptPsnListA(qo);
	}

	/**
	 * 返回部门发薪人员总表, 对象格式为: [{depart:d1, count:c1}, ..., {depart:dn, count:cn}]
	 * @param qo: 参数, branch.id
	 * @return
	 */
	public static List<Map> getDeptPsnListA(Map qo) {
		Map<Department, Long> data = getSalaryService().getDeptPsnStat(qo);
		List<Map> r = new ArrayList<Map>();
		Map entry;
		Department depart;
		Long count;
		for (Iterator<Department> it = data.keySet().iterator(); it.hasNext(); ) {
			depart = it.next();
			count = data.get(depart);
			entry = new Hashtable();
			entry.put("depart", depart);
			entry.put("count", count);
			r.add(entry);
		}
		return r;
	}

	public static Map getDeptPsnListB(Integer branchId, Integer departId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		if (departId != null) qo.put("depart.id", departId);
		return getDeptPsnListB(qo);
	}

	/**
	 * 返回部门发薪人员清单, 对象格式为: {items:items, data:[{sdp:sdp, sfos:[sfo1, ..., sfon]}], time:time}
	 * items: 所有固定项目中的工资项目(SalFixOnline.item)合集
	 * sdp: SalDeptPsn(fetched: depart, person, person.fkPosition, person.depart)
	 * sfos: SalFixOnline(fetched: item)
	 * time: 当前查询时的系统日期
	 * @param qo: 参数, branch.id, depart.id (=0全部)
	 * @return
	 */
	public static Map getDeptPsnListB(Map qo) {
		Map<SalDeptPsn, List<SalFixOnline>> data = getSalaryService().getDeptPsn1(qo);
		List<SalItem> items = (List<SalItem>) qo.get("#items");
		List d = new ArrayList();
		Map r = new Hashtable();
		r.put("items", items);
		r.put("data", d);
		r.put("time", qo.get("#time"));
		Map entry;
		SalDeptPsn sdp;
		List<SalFixOnline> sfos;
		for (Iterator<SalDeptPsn> it = data.keySet().iterator(); it.hasNext(); ) {
			sdp = it.next();
			sfos = data.get(sdp);
			entry = new Hashtable();
			entry.put("sdp", sdp);
			entry.put("sfos", sfos);
			d.add(entry);
		}
		return r;
	}

	/**
	 * 返回未指定发薪部门人员清单
	 * @param qo: 参数, branch.id
	 * @return
	 */
	public static List<Person> getDeptPsnListC(Integer branchId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		return getDeptPsnListC(qo);
	}

	public static List<Person> getDeptPsnListC(Map qo) {
		List<Person> l = getSalaryService().getDeptPsns0(qo);
		return l;
	}

	/**
	 * 返回外加发薪部门人数清单, 对象格式为: {items:items, data:[{sdp:sdp, sfos:[sfo1, ..., sfon]}], time:time}
	 * items: 所有固定项目中的工资项目(SalFixOnline.item)合集
	 * sdp: SalDeptPsn(fetched: depart, person, person.fkPosition, person.depart)
	 * sfos: SalFixOnline(fetched: item)
	 * time: 当前查询时的系统日期
	 * @param qo: 参数, branch.id
	 * @return
	 */
	public static Map getDeptPsnListD(Integer branchId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		return getDeptPsnListD(qo);
	}

	public static Map getDeptPsnListD(Map qo) {
		Map<SalDeptPsn, List<SalFixOnline>> data = getSalaryService().getDeptPsn2(qo);
		List<SalItem> items = (List<SalItem>) qo.get("#items");
		List d = new ArrayList();
		Map r = new Hashtable();
		r.put("items", items);
		r.put("data", d);
		r.put("time", qo.get("#time"));
		Map entry;
		SalDeptPsn sdp;
		List<SalFixOnline> sfos;
		for (Iterator<SalDeptPsn> it = data.keySet().iterator(); it.hasNext(); ) {
			sdp = it.next();
			sfos = data.get(sdp);
			entry = new Hashtable();
			entry.put("sdp", sdp);
			entry.put("sfos", sfos);
			d.add(entry);
		}
		return r;
	}

	/**
	 * 变更发薪部门, 注意此处需要更新主键: SalDeptPsn.id.depart
	 * @param osdps
	 * @param nsdps
	 */
	public static int changeDeptPsns(SalDeptPsn[] osdps, SalDeptPsn[] nsdps) {
		return getSalaryService().changeDeptPsns(osdps, nsdps);
	}

	public static int deleteDeptPsns(SalDeptPsn[] sdps) {
		return getSalaryService().deleteDeptPsns(sdps);
	}

	public static List<SalFixOnline> getFixOnlines(Map qo) {
		return getSalaryService().getFixOnlines(qo);
	}

	public static void addFixOnlines(SalFixOnline sfos[], String user) {
		getSalaryService().addFixOnlines(sfos, user);
	}

	public static void terminateFixOnlines(SalFixOnline sfos[], String user) {
		getSalaryService().terminateFixOnlines(sfos, user);
	}

	public static void changeFixOnlines(SalFixOnline sfos[], String user) {
		getSalaryService().changeFixOnlines(sfos, user);
	}

//==================================== SalTemplate ====================================

	public static List<SalTemplate> getTemplates(Integer branchId, Integer departId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		if (departId != null) qo.put("depart.id", departId);
		return getTemplates(qo);
	}

	public static List<SalTemplate> getTemplates(Map qo) {
		return getSalaryService().getTemplates(qo);
	}

	public static Map getTemplateItems(SalTemplate st) {
		Map qo = new Hashtable();
		if (st.getBranchId() != null) qo.put("branch.id", st.getBranchId());
		if (st.getDepartId() != null) qo.put("depart.id", st.getDepartId());
		if (st.getId() != null) qo.put("template.id", st.getId());
		qo.put("head", st);
		return getTemplateItems(qo);
	}

	public static Map getTemplateItems(Map qo) {
		SalTemplateD std;
		SalItem si;
		List<SalTemplateD> list = getSalaryService().getTemplateDetails(qo);
		List<SalItem> items = new ArrayList<SalItem>();

		for (Iterator<SalTemplateD> it = list.iterator(); it.hasNext(); ) {
			std = it.next();
			si = std.getItem();
			if (!items.contains(si)) items.add(si);
		}
		Map r = new Hashtable();
		r.put("items", items);
		r.put("head", qo.get("head"));
		return r;
	}

	/**
	 * 生成发薪模板, 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, stds:[std11, ..., std1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, stds:[stdn1, ..., stdnn], sfos:[sfon1, ..., sfonn]}]}
	 * persons: 部门发薪人员列表
	 * items: 所有模板明细中的项目(SalTemplateD.item)合集
	 * head: SalTemplate(fetched: depart)
	 * data:
	 * - person: Person
	 * - stds: SalTemplateD(fetched: id.template, person, item)
	 * - sfos: SalFixOnline(fetched: person, item), 使用depart, persons, items, downDate=9999-12-31条件过滤
	 * ix: 模板中薪资项目的顺序
	 * @param qo: 参数, branch.id, depart.id, person.id, template.id
	 * @return
	 */
	public static Map createTemplatePersonsAndItems(Map qo) {
		SalTemplate st = (SalTemplate) qo.get("head");
		SalTemplateD std;
		SalFixOnline sfo;
		SalItem si;
		Person p;
		Integer pid;
		List<SalTemplateD> stds;
		List<SalFixOnline> sfos;

		// 1. 取部门发薪人员列表
		Map qo1 = new Hashtable();
		SalDeptPsn sdp;
		qo1.put("branch.id", st.getBranchId());
		qo1.put("depart.id", st.getDepartId());
		List<SalDeptPsn> list1 = getDeptPsns(qo1);
		List<Integer> pids = new ArrayList<Integer>();
		for (Iterator<SalDeptPsn> it1 = list1.iterator(); it1.hasNext(); ) {
			sdp = it1.next();
			if (!pids.contains(sdp.getPersonId())) pids.add(sdp.getPersonId());
		}

		// 6. 取人员信息(可以根据qo.get("lis")中的项目取, 目前取人员的全部链接项目信息)
		Map qo6 = new Hashtable();
		if (qo.containsKey("branch.id")) qo6.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("lis")) qo6.put("lis", qo.get("lis"));
		qo6.put("sal.depart.id", st.getDepartId());
		qo6.put("persons", pids);
		List<Person> list6 = PersonalServiceUtil.getPersons(qo6);
		Map<Integer, Person> data6 = new LinkedHashMap<Integer, Person>();
		for (Iterator<Person> it6 = list6.iterator(); it6.hasNext(); ) {
			p = it6.next();
			data6.put(p.getId(), p);
		}

		// 2. 生成data.stds
		List<SalItem> items = (List<SalItem>) qo.get("items");
		List<Integer> iids = new ArrayList<Integer>();
		for (Iterator<SalItem> it2 = items.iterator(); it2.hasNext(); )
		{
			si=it2.next();
			if (!iids.contains(si.getId())) iids.add(si.getId());
		}
		Map<Integer, List<SalTemplateD>> data1 = new LinkedHashMap<Integer, List<SalTemplateD>>();
		for (Iterator<Integer> it2 = pids.iterator(); it2.hasNext(); )
		{
			pid = it2.next();
			stds=new ArrayList<SalTemplateD>();
			for (Iterator<SalItem> it3 = items.iterator(); it3.hasNext(); )
			{
				si=it3.next();
				std=new SalTemplateD(st.getBranch(), st, null, si);
				std.setPerson(data6.get(pid));
				std.setAmount(0.0);
				stds.add(std);
			}
			data1.put(pid, stds);
		}

		// 3. 取固定项目列表
		Map qo3 = new Hashtable();
		if (st.getBranchId() != null) qo3.put("branch.id", st.getBranchId());
		if (st.getDepartId() != null) qo3.put("depart.id", st.getDepartId());
		qo3.put("onDate_to", Constants.MAX_DATE);
		qo3.put("persons", pids);
		qo3.put("items", iids);
		qo3.put(Constants.PARAM_FETCH, "id.person");
		List<SalFixOnline> list3 = getSalaryService().getFixOnlines(qo3);

		// 4. 生成data.sfos
		Map<Integer, List<SalFixOnline>> data3 = new LinkedHashMap<Integer, List<SalFixOnline>>();
		for (Iterator<SalFixOnline> it3 = list3.iterator(); it3.hasNext(); ) {
			sfo = it3.next();
			pid = sfo.getPersonId();
			if (data3.containsKey(pid)) {
				sfos = data3.get(pid);
			} else {
				sfos = new ArrayList<SalFixOnline>();
				data3.put(pid, sfos);
			}
			sfos.add(sfo);
		}

		// 7. 模板中的默认项目顺序按薪资项目编号排序
		List<Integer> ix = new LinkedList<Integer>();
		for (Iterator<SalItem> it = items.iterator(); it.hasNext(); ) {
			si = it.next();
			ix.add(si.getId());
		}

		// 8. 拼装返回的数据对象
		List<Map> data = new ArrayList<Map>();
		Map entry;
		for (Iterator<Integer> it = data1.keySet().iterator(); it.hasNext(); )
		{
			pid=it.next();
			if (!data6.containsKey(pid)) continue;
			stds=data1.get(pid);
			sfos=data3.get(pid);
			if (sfos == null) sfos = new ArrayList<SalFixOnline>();
			entry=new Hashtable();
			entry.put("person", data6.get(pid));
			entry.put("stds", stds);
			entry.put("sfos", sfos);
			data.add(entry);
		}
		Map r = new Hashtable();
		r.put("persons", data6.values());
		r.put("items", items);
		r.put("head", st);
		r.put("data", data);
		r.put("ix", ix.toArray(new Integer[]{}));
		return r;
	}

	public static Map getTemplatePersonsAndItems(SalTemplate st) {
		Map qo = new Hashtable();
		if (st.getBranchId() != null) qo.put("branch.id", st.getBranchId());
		if (st.getDepartId() != null) qo.put("depart.id", st.getDepartId());
		if (st.getId() != null) qo.put("template.id", st.getId());
		qo.put("head", st);
		return getTemplatePersonsAndItems(qo);
	}

	/**
	 * 返回发薪模板明细, 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, stds:[std11, ..., std1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, stds:[stdn1, ..., stdnn], sfos:[sfon1, ..., sfonn]}]}
	 * persons: 部门发薪人员列表
	 * items: 所有模板明细中的项目(SalTemplateD.item)合集
	 * head: SalTemplate(fetched: depart)
	 * data:
	 * - person: Person
	 * - stds: SalTemplateD(fetched: id.template, person, item)
	 * - sfos: SalFixOnline(fetched: person, item), 使用depart, persons, items, downDate=9999-12-31条件过滤
	 * ix: 模板中薪资项目的顺序
	 * @param qo: 参数, branch.id, depart.id, person.id, template.id
	 * @return
	 */
	public static Map getTemplatePersonsAndItems(Map qo) {
		SalTemplate st = (SalTemplate) qo.get("head");
		if (!(qo.containsKey("branch.id")) && st.getBranchId() != null) qo.put("branch.id", st.getBranchId());
		if (!(qo.containsKey("depart.id")) && st.getDepartId() != null) qo.put("depart.id", st.getDepartId());
		if (!(qo.containsKey("template.id")) && st.getId() != null) qo.put("template.id", st.getId());

		SalTemplateD std;
		SalDeptPsn sdp;
		SalFixOnline sfo;
		SalItem si;
		Person p;
		Integer pid;
		List<SalTemplateD> stds;
		List<SalFixOnline> sfos;

		// 1. 取模板明细列表, 注意此处需要根据head来设置branch.id, depart.id, template.id参数, 否则会多取数据
		List<SalTemplateD> list1 = getSalaryService().getTemplateDetails(qo);

		// 2. 生成data.stds; 如果qo中没有传递items,则同时生成items
		boolean hasItems = true;
		List<SalItem> items = (List<SalItem>) qo.get("items");
		List<Integer> pids = new ArrayList<Integer>();
		List<Integer> iids = new ArrayList<Integer>();
		Map<Integer, List<SalTemplateD>> data1 = new LinkedHashMap<Integer, List<SalTemplateD>>();
		if (items == null) {
			items = new ArrayList<SalItem>();
			hasItems = false;
		}
		for (Iterator<SalItem> it = items.iterator(); it.hasNext(); ) {
			si = it.next();
			if (!iids.contains(si.getId())) iids.add(si.getId());
		}
		for (Iterator<SalTemplateD> it1 = list1.iterator(); it1.hasNext(); ) {
			std = it1.next();
			si = std.getItem();
			pid = std.getPersonId();
			if (!hasItems && !items.contains(si)) items.add(si);
			if (!pids.contains(pid)) pids.add(pid);
			if (!iids.contains(si.getId())) iids.add(si.getId());
			if (st == null && std.getTemplate() != null) st = std.getTemplate();
			if (data1.containsKey(pid)) {
				stds = data1.get(pid);
			} else {
				stds = new ArrayList<SalTemplateD>();
				data1.put(pid, stds);
			}
			stds.add(std);
		}
		
		// 3. 取固定项目列表
		Map qo3 = new Hashtable();
		if (qo.containsKey("branch.id")) qo3.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("depart.id")) qo3.put("depart.id", qo.get("depart.id"));
		qo3.put("onDate_to", Constants.MAX_DATE);
		qo3.put("persons", pids);
		qo3.put("items", iids);
		qo3.put(Constants.PARAM_FETCH, "id.person");
		List<SalFixOnline> list3 = getSalaryService().getFixOnlines(qo3);

		// 4. 生成data.sfos
		Map<Integer, List<SalFixOnline>> data3 = new LinkedHashMap<Integer, List<SalFixOnline>>();
		for (Iterator<SalFixOnline> it3 = list3.iterator(); it3.hasNext(); ) {
			sfo = it3.next();
			pid = sfo.getPersonId();
			if (data3.containsKey(pid)) {
				sfos = data3.get(pid);
			} else {
				sfos = new ArrayList<SalFixOnline>();
				data3.put(pid, sfos);
			}
			sfos.add(sfo);
		}

		// 5. 取部门发薪人员列表
		Map qo4 = new Hashtable();
		if (qo.containsKey("branch.id")) qo4.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("depart.id")) qo4.put("depart.id", qo.get("depart.id"));
		List<SalDeptPsn> list4 = getDeptPsns(qo4);
		List<Person> persons = new ArrayList<Person>();
		for (Iterator<SalDeptPsn> it4 = list4.iterator(); it4.hasNext(); ) {
			sdp = it4.next();
			if (!persons.contains(sdp.getPerson())) persons.add(sdp.getPerson());
		}

		// 6. 取人员信息(可以根据qo.get("lis")中的项目取, 目前取人员的全部链接项目信息)
		Map qo6 = new Hashtable();
		if (qo.containsKey("branch.id")) qo6.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("lis")) qo6.put("lis", qo.get("lis"));
		qo6.put("sal.template.id", st.getId());
		qo6.put("persons", pids);
		List<Person> list6 = PersonalServiceUtil.getPersons(qo6);
		Map<Integer, Person> data6 = new LinkedHashMap<Integer, Person>();
		for (Iterator<Person> it6 = list6.iterator(); it6.hasNext(); ) {
			p = it6.next();
			data6.put(p.getId(), p);
		}

		// 7. 模板中的默认项目顺序按薪资项目编号排序
		Integer[] ix = getTemplateIx(st);

		// 8. 拼装返回的数据对象
		List<Map> data = new ArrayList<Map>();
		Map entry;
		for (Iterator<Integer> it = data1.keySet().iterator(); it.hasNext(); )
		{
			pid=it.next();
			if (!data6.containsKey(pid)) continue;
			stds=data1.get(pid);
			sfos=data3.get(pid);
			if (sfos == null) sfos = new ArrayList<SalFixOnline>();
			entry=new Hashtable();
			entry.put("person", data6.get(pid));
			entry.put("stds", stds);
			entry.put("sfos", sfos);
			data.add(entry);
		}
		Map r = new Hashtable();
		r.put("persons", persons);
		r.put("items", items);
		r.put("head", st);
		r.put("data", data);
		r.put("ix", ix);
		return r;
	}

	public static void deleteTemplate(SalTemplate st) {
		getSalaryService().deleteTemplate(st);
	}

	public static Integer[] getTemplateIx(SalTemplate st) {
		return getSalaryService().getTemplateIx(st);
	}

	public static SalTemplate saveTemplate(SalTemplate ost, Integer[] oix, SalTemplateD[] ostds, SalTemplate nst, Integer[] nix, SalTemplateD[] nstds) {
		return getSalaryService().saveTemplate(ost, oix, ostds, nst, nix, nstds);
	}

//==================================== SalFact ====================================

	public static List<SalFact> getFacts(Integer branchId, Integer departId) {
		Map qo = new Hashtable();
		if (branchId != null) qo.put("branch.id", branchId);
		if (departId != null) qo.put("depart.id", departId);
		return getFacts(qo);
	}

	public static List<SalFact> getFacts(Map qo) {
		return getSalaryService().getFacts(qo);
	}

	/**
	 * 使用模板生成发薪凭证, 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, sfds:[sfd11, ..., sfd1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, sfds:[sfdn1, ..., sfdnn], sfos:[sfon1, ..., sfonn]}]}
	 * persons: 部门发薪人员列表
	 * items: 所有发薪凭证明细中的项目(SalFactD.item)合集
	 * head: SalFact(fetched: depart, issuer)
	 * data:
	 *  - person: Person
	 *  - sdp: SalDeptPsn
	 *  - sfds: SalFactD(fetched: id.fact, person, item)
	 *  - sfos: SalFixOnline(fetched: person, item), 使用depart, persons, items, onDate条件过滤
	 * template: SalTemplate, 发放使用的发薪模板
	 * ix: 凭证中薪资项目的顺序
	 * 注意: qo.get("template") == null, 薪资凭证中增加人员取固定项目和链接项目; 否则为发薪
	 * @param qo: 参数, branch.id, depart.id, fact.no, fact.date
	 * @return
	 */
	public static Map createFactPersonsAndItems(Map qo) {
		SalFact sf = (SalFact) qo.get("head");
		if (!(qo.containsKey("branch.id")) && sf.getBranchId() != null) qo.put("branch.id", sf.getBranchId());
		if (!(qo.containsKey("depart.id")) && sf.getDepartId() != null) qo.put("depart.id", sf.getDepartId());

		SalFactD sfd;
		SalFixOnline sfo;
		SalItem si;
		Person p;
		Integer pid;
		List<SalFactD> sfds;
		List<SalFixOnline> sfos;

		// 1. 使用模板明细生成发薪明细
		SalTemplate st = (SalTemplate) qo.get("template");
		List<SalFactD> list1 = new ArrayList<SalFactD>();
		List<SalTemplateD> list2 = new ArrayList<SalTemplateD>();
		List<Integer> pids = new ArrayList<Integer>();
		SalTemplateD std;
		if (st != null) {
			Map qo2 = new Hashtable();
			if (st.getBranchId() != null) qo2.put("branch.id", st.getBranchId());
			if (st.getDepartId() != null) qo2.put("depart.id", st.getDepartId());
			if (st.getId() != null) qo2.put("template.id", st.getId());
			list2 = getSalaryService().getTemplateDetails(qo2);
			for (Iterator<SalTemplateD> it2 = list2.iterator(); it2.hasNext(); ) {
				std = it2.next();
				pid = std.getPersonId();
				if (!pids.contains(pid)) pids.add(pid);
			}
		}

		// 6. 取人员信息(可以根据qo.get("lis")中的项目取, 目前取人员的全部链接项目信息)
		Map qo6 = new Hashtable();
		if (sf.getBranchId() != null) qo6.put("branch.id", sf.getBranchId());
		if (qo.containsKey("lis")) qo6.put("lis", qo.get("lis"));
		if (st != null && st.getId() != null && st.getId() != 0) qo6.put("sal.template.id", st.getId());
		if (qo.containsKey("pwids")) qo6.put("pwids", qo.get("pwids"));
		if (pids.size() > 0) qo6.put("persons", pids);
		qo6.put("date_to", sf.getDate());
		List<Person> list6 = PersonalServiceUtil.getPersons(qo6);
		Map<Integer, Person> data6 = new LinkedHashMap<Integer, Person>();
		for (Iterator<Person> it6 = list6.iterator(); it6.hasNext(); ) {
			p = it6.next();
			data6.put(p.getId(), p);
			if (!pids.contains(p.getId())) pids.add(p.getId());
		}

		for (Iterator<SalTemplateD> it2 = list2.iterator(); it2.hasNext(); ) {
			std = it2.next();
			sfd = new SalFactD();
			sfd.setId(new SalFactDPK(sf, std.getNo(), std.getItem()));
			sfd.setPerson(std.getPerson());
			sfd.setAmount(std.getAmount());
			list1.add(sfd);
		}

		// 2. 生成items, pids, iids及data.sfds
		List<SalItem> items = new ArrayList<SalItem>();
		List<Integer> iids = new ArrayList<Integer>();
		Map<Integer, List<SalFactD>> data1 = new LinkedHashMap<Integer, List<SalFactD>>();
		for (Iterator<SalFactD> it1 = list1.iterator(); it1.hasNext(); ) {
			sfd = it1.next();
			si = sfd.getItem();
			pid = sfd.getPersonId();
			if (!items.contains(si)) items.add(si);
			if (!iids.contains(si.getId())) iids.add(si.getId());
			if (sf == null && sfd.getFact() != null) sf = sfd.getFact();
			if (data1.containsKey(pid)) {
				sfds = data1.get(pid);
			} else {
				sfds = new ArrayList<SalFactD>();
				data1.put(pid, sfds);
			}
			sfds.add(sfd);
		}

		// 3. 取固定项目列表
		Map qo3 = new Hashtable();
		if (sf.getBranchId() != null) qo3.put("branch.id", sf.getBranchId());
		if (sf.getDepartId() != null) qo3.put("depart.id", sf.getDepartId());
		qo3.put("onDate_to", sf.getDate());
		qo3.put("downDate_from", sf.getDate());
		if (st != null && st.getId() != null && st.getId() != 0) qo3.put("sal.template.id", st.getId());
		if (qo.containsKey("pwids")) qo3.put("pwids", qo.get("pwids"));
		if (pids.size() > 0) qo3.put("persons", pids); // ORA-01795: 列表中的最大表达式数为 1000
		if (iids.size() > 0) qo3.put("items", iids);
		qo3.put(Constants.PARAM_FETCH, "id.person");
		List<SalFixOnline> list3 = getSalaryService().getFixOnlines(qo3);

		// 4. 生成data.sfos
		Map<Integer, List<SalFixOnline>> data3 = new LinkedHashMap<Integer, List<SalFixOnline>>();
		for (Iterator<SalFixOnline> it3 = list3.iterator(); it3.hasNext(); ) {
			sfo = it3.next();
			pid = sfo.getPersonId();
			if (data3.containsKey(pid)) {
				sfos = data3.get(pid);
			} else {
				sfos = new ArrayList<SalFixOnline>();
				data3.put(pid, sfos);
			}
			sfos.add(sfo);
		}

		// 5. 取部门发薪人员列表
		Map qo4 = new Hashtable();
		SalDeptPsn sdp;
		if (qo.containsKey("branch.id")) qo4.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("depart.id")) qo4.put("depart.id", qo.get("depart.id"));
		Map<Integer, SalDeptPsn> data4 = new Hashtable<Integer, SalDeptPsn>();
		List<Person> persons = new ArrayList<Person>();
		if (qo4.size() > 0) {
			List<SalDeptPsn> list4 = getDeptPsns(qo4);
			for (Iterator<SalDeptPsn> it4 = list4.iterator(); it4.hasNext(); ) {
				sdp = it4.next();
				if (!persons.contains(sdp.getPerson())) persons.add(sdp.getPerson());
				data4.put(sdp.getPersonId(), sdp);
			}
		}

		// 7. 发薪时的默认项目顺序按模板的薪资项目顺序
		Integer[] ix = (st == null) ? new Integer[] {} : getTemplateIx(st);

		// 8. 拼装返回的数据对象
		List<Map> data = new ArrayList<Map>();
		Map entry;
		// for (Iterator<Integer> it = data6.keySet().iterator(); it.hasNext(); )
		// for (Iterator<Integer> it = data1.keySet().iterator(); it.hasNext(); )
		for (Iterator<Integer> it = pids.iterator(); it.hasNext(); )
		{
			pid=it.next();
			if (!data6.containsKey(pid)) continue; // 模板中的人员在所属日期时尚未注册!!!
			sfds=data1.get(pid);
			sfos=data3.get(pid);
			sdp=data4.get(pid);
			entry=new Hashtable();
			entry.put("person", data6.get(pid));
			if (sdp != null) entry.put("sdp", sdp);
			if (sfds != null) entry.put("sfds", sfds);
			if (sfos != null) entry.put("sfos", sfos);
			data.add(entry);
		}
		Map r = new Hashtable();
		r.put("persons", persons);
		r.put("items", items);
		r.put("head", sf);
		r.put("data", data);
		if (st != null) r.put("template", st);
		r.put("ix", ix);
		return r;
	}

	public static Map getFactPersonsAndItems(SalFact sf) {
		Map qo = new Hashtable();
		if (sf.getBranchId() != null) qo.put("branch.id", sf.getBranchId());
		if (sf.getDepartId() != null) qo.put("depart.id", sf.getDepartId());
		if (sf.getNo() != null) qo.put("fact.no", sf.getNo());
		if (sf.getDate() != null) qo.put("fact.date", sf.getDate());
		qo.put("head", sf);
		return getFactPersonsAndItems(qo);
	}

	/**
	 * 返回发薪凭证明细, 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, sfds:[sfd11, ..., sfd1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, sfds:[sfdn1, ..., sfdnn], sfos:[sfon1, ..., sfonn]}]}
	 * persons: 部门发薪人员列表
	 * items: 所有发薪凭证明细中的项目(SalFactD.item)合集
	 * head: SalFact(fetched: depart, issuer)
	 * data:
	 * - person: Person
	 * - sdp: SalDeptPsn, 注意person可能没有对应的sdp, 即不属于本部门发薪人员
	 * - sfds: SalFactD(fetched: id.fact, person, item)
	 * - sfos: SalFixOnline(fetched: person, item), 注意depart, onDate条件
	 * ix: 凭证中薪资项目的顺序
	 * @param qo: 参数, branch.id, depart.id, fact.no, fact.date
	 * @return
	 */
	public static Map getFactPersonsAndItems(Map qo) {
		SalFact sf = (SalFact) qo.get("head");
		if (!(qo.containsKey("branch.id")) && sf.getBranchId() != null) qo.put("branch.id", sf.getBranchId());
		if (!(qo.containsKey("depart.id")) && sf.getDepartId() != null) qo.put("depart.id", sf.getDepartId());
		if (!(qo.containsKey("fact.no")) && sf.getNo() != null) qo.put("fact.no", sf.getNo());
		if (!(qo.containsKey("fact.date")) && sf.getDate() != null) qo.put("fact.date", sf.getDate());

		SalFactD sfd;
		SalDeptPsn sdp;
		SalFixOnline sfo;
		SalItem si;
		Person p;
		Integer pid;
		List<SalFactD> sfds;
		List<SalFixOnline> sfos;

		// 1. 取发薪凭证明细列表
		List<SalFactD> list1 = getSalaryService().getFactDetails(qo);

		// 2. 生成items, pids, iids及data.sfds
		List<SalItem> items = new ArrayList<SalItem>();
		List<Integer> pids = new ArrayList<Integer>();
		List<Integer> iids = new ArrayList<Integer>();
		Map<Integer, List<SalFactD>> data1 = new LinkedHashMap<Integer, List<SalFactD>>();
		for (Iterator<SalFactD> it1 = list1.iterator(); it1.hasNext(); ) {
			sfd = it1.next();
			si = sfd.getItem();
			pid = sfd.getPersonId();
			if (!items.contains(si)) items.add(si);
			if (!pids.contains(pid)) pids.add(pid);
			if (!iids.contains(si.getId())) iids.add(si.getId());
			if (sf == null && sfd.getFact() != null) sf = sfd.getFact();
			if (data1.containsKey(pid)) {
				sfds = data1.get(pid);
			} else {
				sfds = new ArrayList<SalFactD>();
				data1.put(pid, sfds);
			}
			sfds.add(sfd);
		}

		// 3. 取固定项目列表
		Map qo3 = new Hashtable();
		if (qo.containsKey("branch.id")) qo3.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("depart.id")) qo3.put("depart.id", qo.get("depart.id"));
		qo3.put("onDate_to", sf.getDate());
		qo3.put("downDate_from", sf.getDate());
		qo3.put("persons", pids);
		qo3.put("items", iids);
		qo3.put(Constants.PARAM_FETCH, "id.person");
		List<SalFixOnline> list3 = getSalaryService().getFixOnlines(qo3);

		// 4. 生成data.sfos
		Map<Integer, List<SalFixOnline>> data3 = new LinkedHashMap<Integer, List<SalFixOnline>>();
		for (Iterator<SalFixOnline> it3 = list3.iterator(); it3.hasNext(); ) {
			sfo = it3.next();
			pid = sfo.getPersonId();
			if (data3.containsKey(pid)) {
				sfos = data3.get(pid);
			} else {
				sfos = new ArrayList<SalFixOnline>();
				data3.put(pid, sfos);
			}
			sfos.add(sfo);
		}

		// 5. 取部门发薪人员列表
		Map qo4 = new Hashtable();
		if (qo.containsKey("branch.id")) qo4.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("depart.id")) qo4.put("depart.id", qo.get("depart.id"));
		List<SalDeptPsn> list4 = getDeptPsns(qo4);
		List<Person> persons = new ArrayList<Person>();
		Map<Integer, SalDeptPsn> data4 = new Hashtable<Integer, SalDeptPsn>();
		for (Iterator<SalDeptPsn> it4 = list4.iterator(); it4.hasNext(); ) {
			sdp = it4.next();
			if (!persons.contains(sdp.getPerson())) persons.add(sdp.getPerson());
			data4.put(sdp.getPersonId(), sdp);
		}

		// 6. 取人员信息(可以根据qo.get("lis")中的项目取, 目前取人员的全部链接项目信息)
		Map qo6 = new Hashtable();
		if (qo.containsKey("branch.id")) qo6.put("branch.id", qo.get("branch.id"));
		if (qo.containsKey("lis")) qo6.put("lis", qo.get("lis"));
		qo6.put("sal.fact.no", sf.getNo());
		qo6.put("persons", pids);
		qo6.put("date_to", sf.getDate());
		List<Person> list6 = PersonalServiceUtil.getPersons(qo6);
		Map<Integer, Person> data6 = new LinkedHashMap<Integer, Person>();
		for (Iterator<Person> it6 = list6.iterator(); it6.hasNext(); ) {
			p = it6.next();
			data6.put(p.getId(), p);
		}

		// 7. 发薪时的默认项目顺序按模板的薪资项目顺序
		Integer[] ix = getFactIx(sf);

		// 8. 拼装返回的数据对象
		List<Map> data = new ArrayList<Map>();
		Map entry;
		for (Iterator<Integer> it = data1.keySet().iterator(); it.hasNext(); )
		{
			pid=it.next();
			if (!data6.containsKey(pid)) continue;
			sfds=data1.get(pid);
			sfos=data3.get(pid);
			sdp=data4.get(pid);
			if (sfos == null) sfos = new ArrayList<SalFixOnline>();
			entry=new Hashtable();
			entry.put("person", data6.get(pid));
			entry.put("sdp", sdp == null ? "" : sdp);
			entry.put("sfds", sfds);
			entry.put("sfos", sfos);
			data.add(entry);
		}
		Map r = new Hashtable();
		r.put("persons", persons);
		r.put("items", items);
		r.put("head", sf);
		r.put("data", data);
		r.put("ix", ix);
		return r;
	}

	public static void deleteFact(SalFact sf) {
		getSalaryService().deleteFact(sf);
	}

	public static Integer[] getFactIx(SalFact sf) {
		return getSalaryService().getFactIx(sf);
	}

	public static SalFact saveFact(SalFact osf, Integer[] oix, SalFactD[] osfds, SalFact nsf, Integer[] nix, SalFactD[] nsfds) {
		return getSalaryService().saveFact(osf, oix, osfds, nsf, nix, nsfds);
	}

	/**
	 * 返回工资实发明细及凭证头
	 * @param arr [branchId, id.no, depart, month, issueDate, issueTypeValue, issuer.workerId, summary, comment]
	 * @return {facts: facts, factds: [salfact: items, ... salfact: items], list: [SalFactD, ... SalFactD]}<br/>
	 * 		facts: 实际发放凭证列表
	 * 		list : List<{@link SalFactD}><br/>
	 */
	public static Map getFactDs(SalFact fact) {
		Map qo = new HashMap();
		if (fact.getBranchId() != null) qo.put("branch.id", fact.getBranchId());
		if (fact.getNo() != null) qo.put("fact.no", fact.getNo());
		if (fact.getDepartId() != null) qo.put("depart.id", fact.getDepartId());
		if (fact.getIssueType() != null) qo.put("issueType", fact.getIssueType());
		if (fact.getIssuer() != null && fact.getIssuer().getWorkerId() != null) qo.put("issuer.workerId", fact.getIssuer().getWorkerId());
		if (fact.getSummary() != null) qo.put("summary", fact.getSummary());
		if (fact.getComment() != null) qo.put("comment", fact.getComment());
		return getFactDs(qo);
	}

	public static Map getFactDs(Map qo) {
		List<SalFactD> list = getSalaryService().getFactDs(qo);
		List<SalItem> ls = null;
		List<SalFact> facts = new ArrayList<SalFact>();
		Map<SalFact, List<SalItem>> factds=new LinkedHashMap<SalFact, List<SalItem>>();
		for (SalFactD salFactD : list) {
			if (!factds.containsKey(salFactD.getFact())) {
				ls = new ArrayList<SalItem>();
				ls.add(salFactD.getItem());
				factds.put(salFactD.getFact(), ls);
				facts.add(salFactD.getFact());
			} else {
				if (ls.indexOf(salFactD.getItem()) == -1) {
					ls.add(salFactD.getItem());
				}
			}
		}
		List<Map> data = new ArrayList<Map>();
		Map entry;
		SalFact s;
		List<SalItem> items;
		for (Iterator<SalFact> it = factds.keySet().iterator(); it.hasNext(); ) {
			s = it.next();
			items = factds.get(s);
			entry=new Hashtable();
			entry.put("salfact", s);
			entry.put("items", items);
			data.add(entry);
		}
		
		Map r = new Hashtable();
		r.put("facts", facts);
		r.put("factds", data);
		r.put("list", list);
		return r;
	}
	
	public static Map getFactDs2(Map obj) {
		List<SalFactD> list = getSalaryService().getFactDs2(obj);
		List<SalItem> ls = new ArrayList<SalItem>();
		for (SalFactD salFactD : list) {
			if (ls.indexOf(salFactD.getItem()) == -1) {
				ls.add(salFactD.getItem());
			}
		}
		Map r = new Hashtable();
		r.put("factds", list);
		r.put("items", ls);
		return r;
	}

//==================================== Report ====================================

	/**
	 * 查询人员在薪资系统中有社保项目(金额<>0)发生的月数
	 * @param qo: 查询条件:
	 *   branch.id: 机构ID
	 *   depart.id: 人员所属部门ID
	 *   sal.depart.id: 发薪部门ID
	 *   date_from, date_to: 所属日期区间
	 *   issueDate_from, issueDate_to: 发放日期区间
	 */
	public static List<Object[]> getPaySocialInsSals(Map qo) {
		return getSalaryService().getPaySocialInsSals(qo);
	}
}
