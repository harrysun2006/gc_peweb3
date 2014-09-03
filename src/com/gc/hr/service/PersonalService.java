package com.gc.hr.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gc.Constants;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.common.po.Position;
import com.gc.common.po.PsnChange;
import com.gc.common.po.PsnOnline;
import com.gc.common.po.PsnPhoto;
import com.gc.common.po.PsnStatus;
import com.gc.common.po.SecurityLimit;
import com.gc.common.service.BaseServiceUtil;
import com.gc.hr.dao.PersonalDAOHibernate;
import com.gc.hr.po.HireType;
import com.gc.hr.po.HireTypePK;
import com.gc.hr.po.JobGrade;
import com.gc.hr.po.JobGradePK;
import com.gc.hr.po.JobSpec;
import com.gc.hr.po.JobSpecPK;
import com.gc.hr.po.MarryStatus;
import com.gc.hr.po.MarryStatusPK;
import com.gc.hr.po.NativePlace;
import com.gc.hr.po.NativePlacePK;
import com.gc.hr.po.People;
import com.gc.hr.po.PeoplePK;
import com.gc.hr.po.PolParty;
import com.gc.hr.po.PolPartyPK;
import com.gc.hr.po.RegBranch;
import com.gc.hr.po.RegBranchPK;
import com.gc.hr.po.RegType;
import com.gc.hr.po.RegTypePK;
import com.gc.hr.po.SalaryType;
import com.gc.hr.po.SalaryTypePK;
import com.gc.hr.po.SchDegree;
import com.gc.hr.po.SchDegreePK;
import com.gc.hr.po.SchGraduate;
import com.gc.hr.po.SchGraduatePK;
import com.gc.hr.po.Schooling;
import com.gc.hr.po.SchoolingPK;
import com.gc.hr.po.WorkType;
import com.gc.hr.po.WorkTypePK;
import com.gc.util.CommonUtil;

/**
 * HR Personal Service类
 * @author hsun
 *
 */
class PersonalService {

	private PersonalDAOHibernate personalDAO;

	public void setPersonalDAO(PersonalDAOHibernate personalDAO) {
		this.personalDAO = personalDAO;
	}

//==================================== Person ====================================

	public int addPersons(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			persons[i].setId(i);
			BaseServiceUtil.addObject(persons[i]);
		}
		return persons.length;
	}
	
	public int addPersons2(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			persons[i].setId(i);
			personalDAO.addPerson(persons[i]);
		}
		return persons.length;
	}

	public int allotPersonsDepart(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.allotPersonDepart(persons[i]);
		}
		return persons.length;
	}

	public int allotPersonsLine(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.allotPersonLine(persons[i]);
		}
		return persons.length;
	}

	public int downPersons(Integer[] ids, Person person, Boolean down) {
		return personalDAO.downPersons(ids, person, down);
	}

	public Person getPerson(Integer id) {
		return personalDAO.getPerson(id);
	}

	public Person getPersonByCert2(String cert2No) {
		return personalDAO.getPersonByCert2(cert2No);
	}

	public Person getPersonByWorkerId(Integer branchId, String workerId) {
		return personalDAO.getPersonByWorkerId(branchId, workerId);
	}

	public List<Person> getPersons(Integer[] ids) {
		return personalDAO.getPersons(ids);
	}

	public List<Person> getPersons(Map qo) {
		return personalDAO.getPersons(qo);
	}

	public List<Person> getAllPersons(Integer branchId) {
		return personalDAO.getAllPersons(branchId);
	}

	public List<Person> getPersonsByBranchId(Integer branchId) {
		return personalDAO.getPersonsByBranchId(branchId);
	}

	/**
	 * 根据limit中的权限查询列表(按orderColumns排序)
	 */
	public List<Person> getPersons(SecurityLimit limit, Map qo, String[] orderColumns) {
		return personalDAO.getPersons(limit, qo, orderColumns);
	}

	public List<Person> getPersonsCard(Integer[] ids) {
		return personalDAO.getPersonsCard(ids);
	}

	public int updatePersonsCert2(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			if (persons[i] != null) personalDAO.updatePersonCert2(persons[i]);
		}
		return persons.length;
	}

	public int updatePersonsInfo(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.updatePersonInfo(persons[i]);
		}
		return persons.length;
	}
	
	public int updatePersonsInfo2(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.updatePersonInfo2(persons[i]);
		}
		return persons.length;
	}
	
	public int updatePersonsInfo3(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.updatePersonInfo3(persons[i]);
		}
		return persons.length;
	}

	public int updatePersonsStatus(Person[] persons) {
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.updatePersonStatus(persons[i]);
		}
		return persons.length;
	}
	
	public int updatePersonsContract(Person[] persons){
		if (persons == null) return 0;
		for (int i = 0; i < persons.length; i++) {
			personalDAO.updatePersonContract(persons[i]);
		}
		return persons.length;
	}

	public List<Person> findPersons(Map qo) {
		return personalDAO.findPersons(qo);
	}

//==================================== PsnPhoto ====================================

	public PsnPhoto getPsnPhoto(Integer personId) {
		return personalDAO.getPsnPhoto(personId);
	}

//==================================== PsnOnline ====================================

	public List<PsnOnline> getPsnOnlines(Integer branchId, Calendar accDate, Integer departId) {
		return personalDAO.getPsnOnlines(branchId, accDate, departId);
	}

	public List<PsnOnline> getPsnOnlinesByDepart(Integer branchId, Integer departId) {
		return personalDAO.getPsnOnlinesByDepart(branchId, departId);
	}

	public List<PsnOnline> getDriverOnlines(Integer branchId, Integer departId) {
		return personalDAO.getDriverOnlines(branchId,departId);
	}
	
	public List<PsnOnline> getDriverOnlines2(Integer branchId, Integer departId) {
		return personalDAO.getDriverOnlines2(branchId,departId);
	}

	public List<PsnOnline> getPsnOnlineList(Map qo) {
		return personalDAO.getPsnOnlineList(qo);
	}

	public List<PsnOnline> getCert2Onlines(Map qo) {
		return personalDAO.getCert2Onlines(qo);
	}

//==================================== PsnStatus ====================================

	public List<Person> getDrivers(Integer branchId, Calendar date) {
		return personalDAO.getDrivers(branchId, date);
	}

	public List<PsnStatus> getPsnStatusList(Map qo) {
		return personalDAO.getPsnStatusList(qo);
	}

//==================================== Position ====================================

	public Position getPosition(Integer branchId, String no) {
		return personalDAO.getPosition(branchId, no);
	}

	public List<Position> getPositions(Integer branchId) {
		return personalDAO.getPositions(branchId);
	}

//==================================== T_PSN_XXX ====================================

	/**
	 * People
	 * @param id
	 * @return
	 */
	public People getPeople(PeoplePK id) {
		return personalDAO.getPeople(id);
	}

	public List<People> getPeoples(Integer branchId) {
		return personalDAO.getPeoples(branchId);
	}

	public List<People> getPeoples1(Integer branchId) {
		return personalDAO.getPeoples1(branchId);
	}

	public List<People> getPeoples2(Integer branchId) {
		return personalDAO.getPeoples2(branchId);
	}

	/**
	 * PolParty
	 * @param id
	 * @return
	 */
	public PolParty getPolParty(PolPartyPK id) {
		return personalDAO.getPolParty(id);
	}

	public List<PolParty> getPolParties(Integer branchId) {
		return personalDAO.getPolParties(branchId);
	}

	/**
	 * HireType
	 * @param id
	 * @return
	 */
	public HireType getHireType(HireTypePK id) {
		return personalDAO.getHireType(id);
	}

	public List<HireType> getHireTypes(Integer branchId) {
		return personalDAO.getHireTypes(branchId);
	}

	/**
	 * JobGrade
	 * @param id
	 * @return
	 */
	public JobGrade getJobGrade(JobGradePK id) {
		return personalDAO.getJobGrade(id);
	}

	public List<JobGrade> getJobGrades(Integer branchId) {
		return personalDAO.getJobGrades(branchId);
	}

	/**
	 * JobSpec
	 * @param id
	 * @return
	 */
	public JobSpec getJobSpec(JobSpecPK id) {
		return personalDAO.getJobSpec(id);
	}

	public List<JobSpec> getJobSpecs(Integer branchId) {
		return personalDAO.getJobSpecs(branchId);
	}

	/**
	 * MarryStatus
	 * @param id
	 * @return
	 */
	public MarryStatus getMarryStatus(MarryStatusPK id) {
		return personalDAO.getMarryStatus(id);
	}

	public List<MarryStatus> getMarryStatusList(Integer branchId) {
		return personalDAO.getMarryStatusList(branchId);
	}

	/**
	 * NativePlace
	 * @param id
	 * @return
	 */
	public NativePlace getNativePlace(NativePlacePK id) {
		return personalDAO.getNativePlace(id);
	}

	public List<NativePlace> getNativePlaces(Integer branchId) {
		return personalDAO.getNativePlaces(branchId);
	}

	/**
	 * RegBranch
	 * @param id
	 * @return
	 */
	public RegBranch getRegBranch(RegBranchPK id) {
		return personalDAO.getRegBranch(id);
	}

	public List<RegBranch> getRegBranches(Integer branchId) {
		return personalDAO.getRegBranches(branchId);
	}

	/**
	 * SchDegree
	 * @param id
	 * @return
	 */
	public SchDegree getSchDegree(SchDegreePK id) {
		return personalDAO.getSchDegree(id);
	}

	public List<SchDegree> getSchDegrees(Integer branchId) {
		return personalDAO.getSchDegrees(branchId);
	}

	/**
	 * SchGraduate
	 * @param id
	 * @return
	 */
	public SchGraduate getSchGraduate(SchGraduatePK id) {
		return personalDAO.getSchGraduate(id);
	}

	public List<SchGraduate> getSchGraduates(Integer branchId) {
		return personalDAO.getSchGraduates(branchId);
	}

	/**
	 * Schooling
	 * @param id
	 * @return
	 */
	public Schooling getSchooling(SchoolingPK id) {
		return personalDAO.getSchooling(id);
	}

	public List<Schooling> getSchoolings(Integer branchId) {
		return personalDAO.getSchoolings(branchId);
	}

	/**
	 * WorkType
	 * @param id
	 * @return
	 */
	public WorkType getWorkType(WorkTypePK id) {
		return personalDAO.getWorkType(id);
	}

	public List<WorkType> getWorkTypes(Integer branchId) {
		return personalDAO.getWorkTypes(branchId);
	}

	/**
	 * RegType
	 * @param id
	 * @return
	 */
	public RegType getRegType(RegTypePK id) {
		return personalDAO.getRegType(id);
	}

	public List<RegType> getRegTypes(Integer branchId) {
		return personalDAO.getRegTypes(branchId);
	}

	/**
	 * SalaryType
	 * @param id
	 * @return
	 */
	public SalaryType getSalaryType(SalaryTypePK id) {
		return personalDAO.getSalaryType(id);
	}

	public List<SalaryType> getSalaryTypes(Integer branchId) {
		return personalDAO.getSalaryTypes(branchId);
	}

//==================================== Report ====================================

	/**
	 * 从业人员人数变动情况表, 对象格式为: {bdate:d1, edate:d2, period:p, user:u, aa:aa, bb:bb, cc:cc, bcount:c1, ecount:c2, increase:[...], decrease:[...]}
	 * bdate: 起始日期
	 * edate: 截止日期
	 * period: 报告期
	 * user: 查询用户
	 * aa: 填报单位
	 * bb: 人员类别
	 * cc: 岗位类别
	 * bcount: 期初人数
	 * ecount: 期末人数
	 * increase: 本期增加, 元素为PsnChange对象
	 * decrease: 本期减少, 元素为PsnChange对象
	 * @param qo: 参数, a, b, c, depart, regBelong, position, bdate, edate
	 * @return
	 */
	public Map reportP01(Map qo) {
		// 1.bdate, edate, period, user, aa, bb, cc
		Date bdate = (Date) qo.get("bdate");
		Date edate = (Date) qo.get("edate");
		String period = CommonUtil.getString("report.p01.period", new Object[]{CommonUtil.formatDate(Constants.DEFAULT_DATE_FORMAT, bdate), 
					CommonUtil.formatDate(Constants.DEFAULT_DATE_FORMAT, edate)});
		Integer a = (Integer) qo.get("a");
		Integer b = (Integer) qo.get("b");
		Integer c = (Integer) qo.get("c");
		String aa = "", bb = "", cc = "";
		Department ad = (Department) qo.get("depart");
		RegBranch arb = (RegBranch) qo.get("regBelong");
		Position cp = (Position) qo.get("position");
		if (a == 11) aa = CommonUtil.getString("report.p01.select.a1");
		else if (a == 12) aa = CommonUtil.getString("report.p01.select.a2", new Object[]{ad.getName()});
		else if (a == 13) aa = CommonUtil.getString("report.p01.select.a3", new Object[]{arb.getName()});
		if (b == 21) bb = CommonUtil.getString("report.p01.select.b1");
		else if (b == 22) bb = CommonUtil.getString("report.p01.select.b2");
		else if (b == 23) bb = CommonUtil.getString("report.p01.select.b3");
		if (c == 31) cc = CommonUtil.getString("report.p01.select.c1");
		else if (c == 32) cc = CommonUtil.getString("report.p01.select.c2", new Object[]{cp.getName()});

		Calendar bcal = Calendar.getInstance();
		bcal.setTime(bdate);
		Calendar ecal = Calendar.getInstance();
		ecal.setTime(edate);
		qo.put("bcal", bcal);
		qo.put("ecal", ecal);

		personalDAO.prepareChangeData(qo);

		// 2.bcount, ecount
		Calendar pcal = Calendar.getInstance();
		pcal.setTime(bdate);
		pcal.add(Calendar.DATE, -1);
		Map qo2 = new Hashtable();
		qo2.putAll(qo);
		qo2.put("date", pcal);
		Long bcount = personalDAO.getPersonCount(qo2);
		
		Map qo3 = new Hashtable();
		qo3.putAll(qo);
		qo3.put("date", ecal);
		Long ecount = personalDAO.getPersonCount(qo3);

		// 3.increase, decrease
		List<PsnChange> ilist = personalDAO.getIncreasedPersons(qo);

		List<PsnChange> dlist = personalDAO.getDecreasedPersons(qo);

		/*
		System.err.print(bcount.intValue()+"+"+ilist.size()+"-"+dlist.size()+"="+(bcount.intValue() + ilist.size() - dlist.size())+"\t"+ecount.intValue());
		if (bcount.intValue() + ilist.size() - dlist.size() == ecount.intValue()) System.err.println("...OK!");
		else System.err.println("...NG!");
		*/

		// 4.整理数据
		Map r = new HashMap();
		r.put("bdate", bdate);
		r.put("edate", edate);
		r.put("period", period);
		r.put("user", qo.get("user"));
		r.put("aa", aa);
		r.put("bb", bb);
		r.put("cc", cc);
		r.put("bcount", bcount);
		r.put("ecount", ecount);
		r.put("increase", ilist);
		r.put("decrease", dlist);
		return r;
	}

	/**
	 * 从业人员人数变动明细表, 对象格式为: {bdate:d1, edate:d2, period:p, user:u, change:{value:.., label:..}, data:[...], total:{...}}
	 * bdate: 起始日期
	 * edate: 截止日期
	 * period: 报告期
	 * user: 查询用户
	 * change: 变动类型
	 * data: 变动数据, 元素为PsnChange对象
	 * total: 小计
	 * @param qo: 参数, change, bdate, edate
	 * @return
	 */
	public Map reportP02(Map qo) {
		// 1.bdate, edate, period, user, change
		Date bdate = (Date) qo.get("bdate");
		Date edate = (Date) qo.get("edate");
		String period = CommonUtil.getString("report.p01.period", new Object[]{CommonUtil.formatDate(Constants.DEFAULT_DATE_FORMAT, bdate), 
					CommonUtil.formatDate(Constants.DEFAULT_DATE_FORMAT, edate)});
		Map change = (Map) qo.get("change");
		qo.put("change.value", change.get("value"));

		Calendar bcal = Calendar.getInstance();
		bcal.setTime(bdate);
		Calendar ecal = Calendar.getInstance();
		ecal.setTime(edate);
		qo.put("bcal", bcal);
		qo.put("ecal", ecal);
		personalDAO.prepareChangeData(qo);

		// 2.data
		List<PsnChange> list = personalDAO.getChangedPersons(qo);
		List<PsnChange> data = new ArrayList<PsnChange>();

		// 3.total
		Map total = new Hashtable();
		PsnChange pc;
		int[] c = new int[18];
		for (Iterator<PsnChange> it = list.iterator(); it.hasNext(); ) {
			pc = it.next();
			BaseServiceUtil.evict(pc);
			if (pc.getChange() == 0) c[0]++;
			else if (pc.getChange() == 9) {
				if (pc.getDate().getTimeInMillis() - Constants.MAX_DATE.getTimeInMillis() >= 0) continue;
				else c[1]++;
			}
			else {
				if (pc.getChangedDepart() == 1) c[2]++;
				else pc.setNewDepart(null);
				if (pc.getChangedOffice() == 1) c[3]++;
				else pc.setNewOffice(null);
				if (pc.getChangedLine() == 1) c[4]++;
				else pc.setNewLine(null);
				if (pc.getChangedBus() == 1) c[5]++;
				else pc.setNewBus(null);
				if (pc.getChangedCert2No() == 1) c[6]++;
				else pc.setNewCert2No(null);
				if (pc.getChangedRegType() == 1) c[7]++;
				else pc.setNewRegType(null);
				if (pc.getChangedType() == 1) c[8]++;
				else pc.setNewType(null);
				if (pc.getChangedSalaryType() == 1) c[9]++;
				else pc.setNewSalaryType(null);
				if (pc.getChangedPosition() == 1) c[10]++;
				else pc.setNewPosition(null);
				if (pc.getChangedWorkType() == 1) c[11]++;
				else pc.setNewWorkType(null);
				if (pc.getChangedRegBelong() == 1) c[12]++;
				else pc.setNewRegBelong(null);
				if (pc.getChangedParty() == 1) c[13]++;
				else pc.setNewParty(null);
				if (pc.getChangedGrade() == 1) c[14]++;
				else pc.setNewGrade(null);
				if (pc.getChangedSchooling() == 1) c[15]++;
				else pc.setNewSchooling(null);
				if (pc.getChangedContractNo() == 1 || pc.getChangedContr1End() == 1) {
					c[16]++;
					c[17]++;
				} else {
					pc.setNewContractNo(null);
					pc.setNewContr1End(null);
				}
			}
			data.add(pc);
		}
		for (int i = 0; i < c.length; i++) total.put("c"+i, c[i]);

		// 4.整理数据
		Map r = new HashMap();
		r.put("bdate", bdate);
		r.put("edate", edate);
		r.put("period", period);
		r.put("user", qo.get("user"));
		r.put("change", change);
		r.put("data", data);
		r.put("total", total);
		return r;
	}

	/**
	 * 工龄表查询
	 * @param party
	 * @param people
	 */
	public List<Person> getWorkLengths(Map qo) {
		return personalDAO.getWorkLengths(qo);
	}

//==================================== TEST ====================================

	public void testTx(PolParty party, People people) {
		BaseServiceUtil.addObject(party);
		BaseServiceUtil.addObject(people);
	}

	//===============================contract历史合同状态记录表================================================================================	
	/**
	 * map :changeDate 合同变更日期   changeNo 合同变更文号(历史合同号)  isLimitCnt 合同类别（固定：有传值 非固定：不传值） endDate 合同终止日期
	 */
	public List<PsnStatus> getContractList(Map qo){
		return personalDAO.getContractList(qo);
	}

	public Map getLateDatePsnStatusTotle(Map qo){
		return personalDAO.getLateDatePsnStatusTotle(qo);
	}

	/**
	 * 合同到期人员清单
	 * @param qo
	 * @return
	 */
	public List getContractReportList(Map qo){
		return personalDAO.getContractReportList(qo);
	}
	
}
