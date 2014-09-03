package com.gc.hr.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.gc.common.po.Equipment;
import com.gc.common.po.Line;
import com.gc.common.po.Person;
import com.gc.common.po.Position;
import com.gc.common.po.PositionPK;
import com.gc.common.po.PsnChange;
import com.gc.common.po.PsnOnline;
import com.gc.common.po.PsnPhoto;
import com.gc.common.po.PsnStatus;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityUser;
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
import com.gc.hr.po.PsnContractRpt;
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
import com.gc.util.ObjectUtil;
import com.gc.util.StringUtil;

/**
 * 人员相关表查询及更新DB说明:
 * 1. 使用Connection conn = getSession().connection();进行原生SQL操作, 注意一定要在查询/更新执行
 *    完成之前关闭相关的Statement和ResultSet对象, 从而关闭Oracle数据库当前会话打开的游标; conn对象
 *    不用关闭, spring会进行管理。
 * 2. 增加缴金工龄从薪资系统取数, 如使用嵌套+关联查询开销比较大, 时间6125ms==>38000~ms(使用函数更慢!)。
 *    如果使用缓存机制, 需要增加的处理比较多(人员关联对象都需要做缓存, 否则分类查询会不准), 待以后
 *    再统一规划使用; 使用分次查询(改进SQL)+数据拼装方法, 查询时间可以降低到30000~ms。
 * @author hsun
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public class PersonalDAOHibernate extends HibernateDaoSupport {

//==================================== Person ====================================
	public int addPerson(final Person po) {
		try {
			Session session = getSession();
			Connection conn = session.connection();
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO T_PERSONAL(C_BELONG,C_WORKERID,C_PERSONALNAME,")
			.append("C_PID,C_SEX,C_PEOPLE,C_NATIVEPLACE,C_REGTYPE,C_REGADDRESS,C_BARTHDAY,C_MARRYSTATE,")
			.append("C_ANNUITIES,C_ACCUMULATION,C_PERSONALONDATE,C_PERSONALCURRENTSTATE,C_PERSONALDOWNDATE,")
			.append("C_DOWNREASON,C_UPGRADE_DATE,C_UPGRADE_REASON,C_TYPE,C_SALARYTYPE,C_POSITION,C_REGBELONG,C_PARTY,")
			.append("C_GRADE,C_SCHOOLHISTORY,C_ALLOTDATE,C_ALLOTREASON,C_DEPART,C_OFFICE,C_LINEID,C_BUSID,C_CERT2_NO,")
			.append("C_CERT2_NO_HEX,C_FILLTABLEDATE,C_COMMEND,C_WORKDATE,C_RETIREDATE,C_LENGTHSERVICE,C_INDATE,C_OUTDATE,")
			.append("C_WORKLENGTH,C_GROUPNO,C_CONTRACTNO,C_CONTR1_FROM,C_CONTR1_END,C_CONTRACTREASON,C_CONTR2_FROM,C_CONTR2_END,")
			.append("C_WORKTYPE,C_LEVEL,C_TECHLEVEL,C_RESPONSIBILITY,C_CERT1_NO,C_CERT1_NO_DATE,C_SERVICENO,C_SERVICENO_DATE,")
			.append("C_FRONT_WORKRESUME,C_FRONT_TRAININGRESUME,C_SPECIFICATION,C_DEGREE,C_GRADUATE,C_SKILL,C_LAN_COM,C_NATIONAL,")
			.append("C_STATE,C_CITY,C_ADDRESS,C_ZIP,C_TELEPNONENO,C_EMAIL,C_OFFICETEL,C_OFFICEEXT,C_OFFICEFAX,C_HRCHKGROUPID,")
			.append("C_LASTMODIFIER,C_COMMENT,C_PAYSOCIALINS_INIT,C_PAYSOCIALINS_ADJ)")
			.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps = conn.prepareStatement(sb.toString());
			int index = 1;
			ps.setInt(index++, po.getBranchId());
			ps.setString(index++, po.getWorkerId());
			ps.setString(index++, po.getName());
			ps.setString(index++, po.getPid());
			ps.setString(index++, po.getSex());
			ps.setString(index++, po.getPeople());
			ps.setString(index++, po.getNativePlace());
			ps.setString(index++, po.getRegType());
			ps.setString(index++, po.getRegAddress());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getBirthday()));

			ps.setString(index++, po.getMarryStatus());
			ps.setString(index++, po.getAnnuities());
			ps.setString(index++, po.getAccumulation());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getOnDate()));
			ps.setInt(index++, po.getStatus());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getDownDate()));
			ps.setString(index++, po.getDownReason());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getUpgradeDate()));
			ps.setString(index++, po.getUpgradeReason());
			ps.setString(index++, po.getType());

			ps.setString(index++, po.getSalaryType());
			ps.setString(index++, po.getPosition());
			ps.setString(index++, po.getRegBelong());
			ps.setString(index++, po.getParty());
			ps.setString(index++, po.getGrade());
			ps.setString(index++, po.getSchooling());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getAllotDate()));
			ps.setString(index++, po.getAllotReason());
			ps.setInt(index++, po.getDepartId());
			ps.setString(index++, po.getOffice());
			ps.setObject(index++, po.getLineId());
			ps.setObject(index++, po.getBusId());
			ps.setString(index++, po.getCert2No() != null ? po.getCert2No() : "-");
			ps.setString(index++, po.getCert2NoHex() != null ? po.getCert2NoHex() : "-");

			ps.setDate(index++, ObjectUtil.toSQLDate(po.getFillTableDate()));
			ps.setString(index++, po.getCommend());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getWorkDate()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getRetireDate()));
			ps.setString(index++, po.getServiceLength());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getInDate()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getOutDate()));

			ps.setString(index++, po.getWorkLength());
			ps.setString(index++, po.getGroupNo());
			ps.setString(index++, po.getContractNo());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr1From()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr1End()));
			ps.setString(index++, po.getContractReason());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr2From()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr2End()));
			ps.setString(index++, po.getWorkType());
			ps.setObject(index++, po.getLevel());

			ps.setString(index++, po.getTechLevel());
			ps.setString(index++, po.getResponsibility());
			ps.setString(index++, po.getCert1No());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getCert1NoDate()));
			ps.setString(index++, po.getServiceNo());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getServiceNoDate()));
			ps.setString(index++, po.getFrontWorkResume());
			ps.setString(index++, po.getFrontTrainingResume());
			ps.setString(index++, po.getSpecification());
			ps.setString(index++, po.getDegree());

			ps.setString(index++, po.getGraduate());
			ps.setString(index++, po.getSkill());
			ps.setString(index++, po.getLanCom());
			ps.setString(index++, po.getNational());
			ps.setString(index++, po.getState());
			ps.setString(index++, po.getCity());
			ps.setString(index++, po.getAddress());
			ps.setString(index++, po.getZip());
			ps.setString(index++, po.getTelephone());
			ps.setString(index++, po.getEmail());

			ps.setString(index++, po.getOfficeTel());
			ps.setString(index++, po.getOfficeExt());
			ps.setString(index++, po.getOfficeFax());
			ps.setObject(index++, po.getChkGroupId());
			ps.setObject(index++, po.getLastModifierId());
			ps.setString(index++, po.getComment());
			ps.setInt(index++, po.getPaySocialInsInit());
			ps.setInt(index++, po.getPaySocialInsAdj());

			ps.execute();
			session.flush();
			ps.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		}
		return 1;
	}
	
	public int allotPersonDepart(Person po) {
		Person lpo = (Person) getSession().get(Person.class, po.getId());
		lpo.setAllotDate(po.getAllotDate());
		lpo.setAllotReason(po.getAllotReason());
		lpo.setDepart(po.getDepart());
		lpo.setOffice(po.getOffice());
		lpo.setLine(null);
		lpo.setBus(null);
		getSession().update(lpo);
		return 1;
	}

	public int allotPersonLine(Person po) {
		Person lpo = (Person) getSession().get(Person.class, po.getId());
		lpo.setAllotDate(po.getAllotDate());
		lpo.setAllotReason(po.getAllotReason());
		lpo.setDepart(po.getDepart());
		lpo.setOffice(po.getOffice());
		lpo.setLine(po.getLine());
		lpo.setBus(po.getBus());
		getSession().update(lpo);
		return 1;
	}

	public int downPersons(Integer[] ids, Person person, Boolean down) {
		if (ids == null || ids.length <= 0) return 0;
		int k;
		for (k = 0; k < ids.length; k++) {
			final Person lpo = (Person) getSession().get(Person.class, ids[k]);
			Calendar cal = down ? person.getDownDate() : Constants.MAX_DATE;
			String reason = down ? person.getDownReason() : "";
			lpo.setDownDate(cal);
			lpo.setDownReason(reason);
			this.getSession().update(lpo);
		}
		return k--;
	}

	public Person getPerson(Person po) {
		return getPerson(po.getId());
	}

	public Person getPerson(Integer id) {
		return getPerson2(id);
	}

	protected Person getPerson1(Integer id) {
		return (Person) getHibernateTemplate().get(Person.class, id);
	}

	protected Person getPerson2(Integer id) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup g")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition pos")
			.append(" left outer join fetch p.line l")
			.append(" left outer join fetch p.bus e")
			.append(" where p.id = :id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", id);
		return (Person) q.list().get(0);
	}

	public Person getPersonByCert2(String cert2No) {
		if (cert2No == null) return null;
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup g")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition pos")
			.append(" left outer join fetch p.line l")
			.append(" left outer join fetch p.bus e")
			.append(" where p.cert2No = :cert2No");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("cert2No", cert2No);
		List<Person> l = q.list();
		return (l.size() > 0) ? l.get(0) : null;
	}

	public Person getPersonByWorkerId(Integer branchId, String workerId) {
		if (workerId == null) return null;
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup g")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition pos")
			.append(" left outer join fetch p.line l")
			.append(" left outer join fetch p.bus e")
			.append(" left outer join fetch p.psnPhoto pp")
			.append(" where p.branch.id = :bid and p.workerId = :wid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", branchId);
		q.setParameter("wid", workerId);
		List<Person> l = q.list();
		return (l.size() > 0) ? l.get(0) : null;
	}
	
	public PsnPhoto getPsnPhoto(Integer personId) {
		return (PsnPhoto) this.getHibernateTemplate().get(PsnPhoto.class, personId);
	}

	public List<Person> getPersons(Integer[] ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup g")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition pos")
			.append(" left outer join fetch p.line l")
			.append(" left outer join fetch p.bus e")
			.append(" where p.id in (:pids) order by p.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameterList("pids", ids);
		return (List<Person>) q.list();
	}

	/**
	 * 取人员列表, 包含Position, Department, Equipment, Line信息
	 * 查询条件:
	 *   branch.id - 所属机构id
	 *   sal.depart.id - 发薪部门id
	 *   sal.template.id - 薪资模板id
	 *   sal.fact.no - 薪资凭证no
	 *   date_to - 截止日期(需要关联查询T_PSN_ONLINE, T_PSN_STATUS)
	 *   lis - 链接信息, 暂时不用
	 *   persons - 人员id列表, List<Integer>
	 * @param qo
	 * @return
	 */
	public List<Person> getPersons(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT P.C_PERSONALID, P.C_WORKERID, P.C_PERSONALNAME, P.C_PID, P.C_SEX, P.C_OFFICE, P.C_PERSONALONDATE, P.C_PERSONALDOWNDATE,")
			.append(" D.C_DEPARTMENTID C_DEPARTID, D.C_NO C_DEPARTNO, D.C_DEPARTMENTNAME C_DEPARTNAME,")
			.append(" RL.C_ID C_LINEID, RL.C_NO C_LINENO, RL.C_NAME C_LINENAME,")
			.append(" E.C_EQUID C_BUSID, E.C_EQUUSEID C_BUSNO, E.C_EQUNAME C_BUSNAME, E.C_AUTHORIZATIONNO C_AUTHNO,")
			.append(" O.C_ONLINECONFIG,")
			.append(" POS.C_NO C_POSNO, POS.C_NAME C_POSNAME,")
			.append(" PS.C_TYPE C_TYPE")
			.append(" FROM T_PERSONAL P")
			.append(" LEFT OUTER JOIN T_PSN_ONLINE PO ON P.C_BELONG = PO.C_BELONG AND P.C_PERSONALID = PO.C_ONLINEPSN")
			.append(" AND PO.C_ONDATE <= :date_to AND PO.C_DOWNDATE >= :date_to")
			.append(" LEFT OUTER JOIN T_DEPARTMENT D ON PO.C_BELONG = D.C_BELONG AND PO.C_DEPART = D.C_DEPARTMENTID")
			.append(" LEFT OUTER JOIN T_RUNNING_LINE RL ON PO.C_BELONG = RL.C_BELONG AND PO.C_LINEID = RL.C_ID")
			.append(" LEFT OUTER JOIN T_EQUIPMENT E ON PO.C_BELONG = E.C_BELONG AND PO.C_BUSID = E.C_EQUID")
			.append(" LEFT OUTER JOIN T_ONLINE O ON E.C_BELONG = O.C_BELONG AND E.C_EQUID = O.C_ONLINEEQUID")
			.append(" LEFT OUTER JOIN T_PSN_STATUS PS ON P.C_BELONG = PS.C_BELONG AND P.C_PERSONALID = PS.C_STATUSPSN")
			.append(" AND PS.C_ONDATE <= :date_to AND PS.C_DOWNDATE >= :date_to")
			.append(" LEFT OUTER JOIN T_POSITION POS ON PS.C_BELONG = POS.C_BELONG AND PS.C_POSITION = POS.C_NO ")
			.append(" WHERE 1=1 ");
			/*
			.append(" FROM T_PERSONAL P, T_PSN_ONLINE PO, T_DEPARTMENT D, T_RUNNING_LINE RL,")
			.append(" T_EQUIPMENT E, T_ONLINE O, T_PSN_STATUS PS, T_POSITION POS")
			.append(" WHERE P.C_BELONG = PO.C_BELONG AND P.C_PERSONALID = PO.C_ONLINEPSN")
			.append(" AND P.C_BELONG = PS.C_BELONG AND P.C_PERSONALID = PS.C_STATUSPSN")
			.append(" AND PO.C_BELONG = D.C_BELONG AND PO.C_DEPART = D.C_DEPARTMENTID")
			.append(" AND PS.C_BELONG = POS.C_BELONG AND PS.C_POSITION = POS.C_NO")
			.append(" AND PO.C_BELONG = RL.C_BELONG(+) AND PO.C_LINEID = RL.C_ID(+)")
			.append(" AND PO.C_BELONG = E.C_BELONG(+) AND PO.C_BUSID = E.C_EQUID(+)")
			.append(" AND E.C_BELONG = O.C_BELONG(+) AND E.C_EQUID = O.C_ONLINEEQUID(+)");
			*/
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" AND P.C_BELONG = :bid");
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" AND P.C_PERSONALID IN (:persons)");
			if (qo.containsKey("pwids") && qo.get("pwids") != null && ((Object[]) qo.get("pwids")).length > 0) sb.append(" AND P.C_WORKERID IN (:pwids)");
		}
		// sb.append(" AND O.C_ONDATE <= :date_to AND (O.C_DOWNDATE >= :date_to OR O.C_DOWNDATE IS NULL)")
		sb.append(" AND NVL(O.C_ONDATE, TO_DATE('1901-01-01', 'yyyy-mm-dd')) <= :date_to")
			.append(" AND NVL(O.C_DOWNDATE, TO_DATE('9999-12-31', 'yyyy-mm-dd')) >= :date_to ");
		sb.append(" ORDER BY P.C_PERSONALID");
		Session session = getSession();
		Query q = session.createSQLQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q.setParameterList("persons", (List) qo.get("persons"));
			if (qo.containsKey("pwids") && qo.get("pwids") != null && ((Object[]) qo.get("pwids")).length > 0) q.setParameterList("pwids", (Object[]) qo.get("pwids"));
		}
		if (qo != null && qo.containsKey("date_to")) q.setParameter("date_to", ObjectUtil.toCalendar(qo.get("date_to")));
		else q.setParameter("date_to", Constants.MAX_DATE);
		List<Object[]> l = q.list();
		List<Person> r = new ArrayList<Person> ();
		Object[] o;
		Person p;
		Department d;
		Integer did;
		Line rl;
		Integer rlid;
		Equipment e;
		Integer eid;
		Position pp;
		String ppno;
		Map<Integer, Department> ds = new Hashtable<Integer, Department>();
		Map<Integer, Line> rls = new Hashtable<Integer, Line>();
		Map<Integer, Equipment> es = new Hashtable<Integer, Equipment>();
		Map<String, Position> pps = new Hashtable<String, Position>();
		int index;
		for (Iterator<Object[]> it = l.iterator(); it.hasNext(); ) {
			o = it.next();
			p = new Person();
			session.evict(p);
			index = 0;
			p.setId(ObjectUtil.toInt(o[index++]));
			p.setWorkerId((String) o[index++]);
			p.setName((String) o[index++]);
			p.setPid((String) o[index++]);
			p.setSex((String) o[index++]);
			p.setOffice((String) o[index++]);
			p.setOnDate(ObjectUtil.toCalendar(o[index++]));
			p.setDownDate(ObjectUtil.toCalendar(o[index++]));
			did = ObjectUtil.toInt(o[index++]);
			if (did == null) {
				d = null;
				index += 2;
			} else if (ds.containsKey(did)) {
				d = ds.get(did);
				index += 2;
			} else {
				d = new Department();
				session.evict(d);
				d.setId(did);
				d.setNo((String) o[index++]);
				d.setName((String) o[index++]);
				ds.put(did, d);
			}
			p.setDepart(d);
			rlid = ObjectUtil.toInt(o[index++]);
			if (rlid == null) {
				rl = null;
				index += 2;
			} else if (rls.containsKey(rlid)) {
				rl = rls.get(rlid);
				index += 2;
			} else {
				rl = new Line();
				session.evict(rl);
				rl.setId(rlid);
				rl.setNo((String) o[index++]);
				rl.setName((String) o[index++]);
				rls.put(rlid, rl);
			}
			p.setLine(rl);
			eid = ObjectUtil.toInt(o[index++]);
			if (eid == null) {
				e = null;
				index += 4;
			} else if (es.containsKey(eid)) {
				e = es.get(eid);
				index += 4;
			} else {
				e = new Equipment();
				session.evict(e);
				e.setId(eid);
				e.setUseId((String) o[index++]);
				e.setName((String) o[index++]);
				e.setAuthNo((String) o[index++]);
				e.setShift(ObjectUtil.toString(o[index++]));
			}
			p.setBus(e);
			ppno = ObjectUtil.toString(o[index++]);
			if (ppno == null) {
				pp = null;
				index += 1;
			} else if (pps.containsKey(ppno)) {
				pp = pps.get(ppno);
				index += 1;
			} else {
				pp = new Position();
				session.evict(pp);
				pp.setId(new PositionPK(0, ppno));
				pp.setName((String) o[index++]);
			}
			p.setFkPosition(pp);
			p.setType((String) o[index++]);
			r.add(p);
		}
		return r;
	}

	public List<Person> getPersons(SecurityLimit limit, Map qo, String[] orderColumns) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.chkGroup g")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition pos")
			.append(" left outer join fetch p.line l")
			.append(" left outer join fetch p.bus e")
			.append(" where p.branch.id = :bid");
		if (limit.getHrLimit() >= 6 && limit.getHrLimit() <= 9) sb.append(" and p.depart.id = :did");
		String key, k1, k2, k3;
		Object value;
		int m1, m2;
		Map<String, String> tables = new Hashtable<String, String>();
		tables.put("chkGroup", "g");
		tables.put("depart", "d");
		tables.put("fkPosition", "pos");
		tables.put("line", "l");
		tables.put("bus", "e");
		if (qo != null) {
			/**
			 * birthday_from ==> p.birthday >= :birthday_from
			 * workerId ==> p.workerId like :workerId
			 * chkGroup.id ==> g.id = :chkGroup_id
			 */
			for (Iterator it = qo.keySet().iterator(); it.hasNext(); ) {
				key = (String) it.next();
				value = qo.get(key);
				m1 = key.indexOf('.');
				m2 = key.indexOf('_');
				if (m1 > 0) { // Process foriegn key object alias
					k1 = key.substring(0, m1);
					k1 = tables.get(k1) + "." + key.substring(m1+1);
				} else {
					k1 = (m2 > 0) ? "p." + key.substring(0, m2) : "p." + key;
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
		sb.append(" order by");
		if (orderColumns != null && orderColumns.length > 0) {
			for (int i = 0; i < orderColumns.length; i++) {
				sb.append(" p.").append(orderColumns[i]).append(",");
			}
		}
		sb.append(" p.workerId");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", limit.getBranchId());
		if (limit.getHrLimit() >= 6 && limit.getHrLimit() <= 9) q.setParameter("did", limit.getHrLimitDepartId());
		if (qo != null) {
			for (Iterator it = qo.keySet().iterator(); it.hasNext(); ) {
				key = (String) it.next();
				value = qo.get(key);
				key = key.replace('.', '_');
				if (value instanceof Date) {
					value = ObjectUtil.toCalendar(value);
				} else if (value instanceof String) {
					value = "%" + value + "%";
				}
				q.setParameter(key, value);
			}
		}
		return (List<Person>) q.list();
	}

	public List<Person> getAllPersons(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.branch b")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and p.branch.id = :bid");
		sb.append(" order by p.workerId");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<Person>) q.list();
	}

	public List<Person> getPersonsByBranchId(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" where p.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", branchId);
		return (List<Person>) q.list();
	}

	public List<Person> getPersonsCard(Integer[] ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p")
			.append(", (select min(ps.onDate) from PsnStatus ps where ps.person.id = p.id and ps.schooling = p.schooling) as graudateDate")
			.append(", (select min(ps.onDate) from PsnStatus ps where ps.person.id = p.id and ps.party = p.party) as partyOnDate")
			.append(" from Person p left outer join fetch p.psnPhoto pp")
			.append(" left outer join fetch p.branch b")
			.append(" left outer join fetch p.fkPosition pos")
			/*
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.line l")
			.append(" left outer join fetch p.bus e")
			*/
			.append(" where p.id in (");
		for (int i = 0; i < ids.length; i++) {
			sb.append("? ,");
		}
		if (ids.length > 0) sb.delete(sb.length() - 2, sb.length());
		sb.append(") order by p.id");
		Query q = getSession().createQuery(sb.toString());
		int index = 0;
		for (int i = 0; i < ids.length; i++) {
			q.setParameter(index++, ids[i]);
		}
		Object[] v;
		Person p;
		List<Object[]> list = (List<Object[]>) q.list();
		List<Person> persons = new ArrayList<Person>();
		for (Iterator<Object[]> it = list.iterator(); it.hasNext(); ) {
			v = it.next();
			p = (Person) v[0];
			p.setGraduateDate((Calendar) v[1]);
			p.setPartyOnDate((Calendar) v[2]);
			persons.add(p);
		}
		return persons;
	}

	public int updatePersonCert2(Person po) {
		Person lpo = (Person) getSession().get(Person.class, po.getId());
		lpo.setAllotDate(po.getAllotDate());
		lpo.setCert2No(po.getCert2No());
		lpo.setCert2NoHex(po.getCert2NoHex());
		getSession().update(lpo);
		return 1;
	}

	public int updatePersonInfo(Person po) {
		getSession().update(po);
		return 1;
	}

	public int updatePersonInfo2(final Person po) {
		try {
			Session session = getSession();
			Connection conn = session.connection();
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE T_PERSONAL SET")
			.append(" C_WORKERID = ?, C_PERSONALNAME = ?, C_PID = ?, C_SEX = ?, C_PEOPLE = ?, C_NATIVEPLACE = ?,")
			.append(" C_REGTYPE = ?, C_REGADDRESS = ?, C_BARTHDAY = ?, C_MARRYSTATE = ?, C_PAYSOCIALINS_INIT = ?,")
			.append(" C_PAYSOCIALINS_ADJ = ?, C_ANNUITIES = ?, C_ACCUMULATION = ?, C_PERSONALONDATE = ?, C_PERSONALCURRENTSTATE = ?,")
			.append(" C_PERSONALDOWNDATE = ?, C_DOWNREASON = ?, C_UPGRADE_DATE = ?, C_UPGRADE_REASON = ?, C_TYPE = ?,")
			.append(" C_SALARYTYPE = ?, C_POSITION = ?, C_REGBELONG = ?, C_PARTY = ?, C_GRADE = ?, C_SCHOOLHISTORY = ?,")
			.append(" C_ALLOTDATE = ?, C_ALLOTREASON = ?, C_DEPART = ?, C_OFFICE = ?, C_LINEID = ?, C_BUSID = ?,")
			.append(" C_CERT2_NO = ?, C_CERT2_NO_HEX = ?, C_FILLTABLEDATE = ?, C_COMMEND = ?, C_WORKDATE = ?,")
			.append(" C_RETIREDATE = ?, C_LENGTHSERVICE = ?, C_INDATE = ?, C_OUTDATE = ?, C_WORKLENGTH = ?,")
			.append(" C_GROUPNO = ?, C_CONTRACTNO = ?, C_CONTR1_FROM = ?, C_CONTR1_END = ?, C_CONTRACTREASON = ?,")
			.append(" C_CONTR2_FROM = ?, C_CONTR2_END = ?, C_WORKTYPE = ?, C_LEVEL = ?, C_TECHLEVEL = ?, C_RESPONSIBILITY = ?,")
			.append(" C_CERT1_NO = ?, C_CERT1_NO_DATE = ?, C_SERVICENO = ?, C_SERVICENO_DATE = ?, C_FRONT_WORKRESUME = ?,")
			.append(" C_FRONT_TRAININGRESUME = ?, C_SPECIFICATION = ?, C_DEGREE = ?, C_GRADUATE = ?, C_SKILL = ?, C_LAN_COM = ?,")
			.append(" C_NATIONAL = ?, C_STATE = ?, C_CITY = ?, C_ADDRESS = ?, C_ZIP = ?, C_TELEPNONENO = ?, C_EMAIL = ?,")
			.append(" C_OFFICETEL = ?, C_OFFICEEXT = ?, C_OFFICEFAX = ?, C_HRCHKGROUPID = ?, C_LASTMODIFIER = ?, C_COMMENT = ?")
			.append(" WHERE C_BELONG = ? AND C_PERSONALID = ?");
			PreparedStatement ps = conn.prepareStatement(sb.toString());
			int index=1;
			ps.setString(index++, po.getWorkerId());
			ps.setString(index++, po.getName());
			ps.setString(index++, po.getPid());
			ps.setString(index++, po.getSex());
			ps.setString(index++, po.getPeople());
			ps.setString(index++, po.getNativePlace());
			ps.setString(index++, po.getRegType());
			ps.setString(index++, po.getRegAddress());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getBirthday()));
			ps.setString(index++, po.getMarryStatus());
			ps.setInt(index++, po.getPaySocialInsInit());
			ps.setInt(index++, po.getPaySocialInsAdj());
			ps.setString(index++, po.getAnnuities());
			ps.setString(index++, po.getAccumulation());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getOnDate()));
			ps.setInt(index++, po.getStatus());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getDownDate()));
			ps.setString(index++, po.getDownReason());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getUpgradeDate()));
			ps.setString(index++, po.getUpgradeReason());
			ps.setString(index++, po.getType());
			ps.setString(index++, po.getSalaryType());
			ps.setString(index++, po.getPosition());
			ps.setString(index++, po.getRegBelong());
			ps.setString(index++, po.getParty());
			ps.setString(index++, po.getGrade());
			ps.setString(index++, po.getSchooling());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getAllotDate()));
			ps.setString(index++, po.getAllotReason());
			ps.setInt(index++, po.getDepartId());
			ps.setString(index++, po.getOffice());
			ps.setObject(index++, po.getLineId());
			ps.setObject(index++, po.getBusId());
			ps.setString(index++, po.getCert2No() != null ? po.getCert2No() : "-");
			ps.setString(index++, po.getCert2NoHex() != null ? po.getCert2NoHex() : "-");
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getFillTableDate()));
			ps.setString(index++, po.getCommend());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getWorkDate()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getRetireDate()));
			ps.setString(index++, po.getServiceLength());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getInDate()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getOutDate()));
			ps.setString(index++, po.getWorkLength());
			ps.setString(index++, po.getGroupNo());
			ps.setString(index++, po.getContractNo());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr1From()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr1End()));
			ps.setString(index++, po.getContractReason());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr2From()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr2End()));
			ps.setString(index++, po.getWorkType());
			ps.setObject(index++, po.getLevel());
			ps.setString(index++, po.getTechLevel());
			ps.setString(index++, po.getResponsibility());
			ps.setString(index++, po.getCert1No());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getCert1NoDate()));
			ps.setString(index++, po.getServiceNo());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getServiceNoDate()));
			ps.setString(index++, po.getFrontWorkResume());
			ps.setString(index++, po.getFrontTrainingResume());
			ps.setString(index++, po.getSpecification());
			ps.setString(index++, po.getDegree());
			ps.setString(index++, po.getGraduate());
			ps.setString(index++, po.getSkill());
			ps.setString(index++, po.getLanCom());
			ps.setString(index++, po.getNational());
			ps.setString(index++, po.getState());
			ps.setString(index++, po.getCity());
			ps.setString(index++, po.getAddress());
			ps.setString(index++, po.getZip());
			ps.setString(index++, po.getTelephone());
			ps.setString(index++, po.getEmail());
			ps.setString(index++, po.getOfficeTel());
			ps.setString(index++, po.getOfficeExt());
			ps.setString(index++, po.getOfficeFax());
			ps.setObject(index++, po.getChkGroupId());
			ps.setObject(index++, po.getLastModifierId());
			ps.setString(index++, po.getComment());

			ps.setInt(index++, po.getBranchId());
			ps.setInt(index++, po.getId());
			ps.execute();
			session.flush();
			ps.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		};
		return 1;
	}
	
	// 导入更新
	public int updatePersonInfo3(final Person po) {
		try {
			Session session = getSession();
			Connection conn = session.connection();
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE T_PERSONAL SET")
			.append(" C_SEX = ?, C_BARTHDAY = ?, C_FILLTABLEDATE = ?, C_COMMEND = ?, C_INDATE = ?, C_GROUPNO = ?, C_CONTRACTNO = ?,")
			.append(" C_CONTR1_FROM = ?, C_CONTR1_END = ?, C_CERT1_NO = ?, C_CERT1_NO_DATE = ?, C_SERVICENO = ?, C_SERVICENO_DATE = ?")
			.append(" WHERE C_BELONG = ? AND C_WORKERID = ?");
			PreparedStatement ps = conn.prepareStatement(sb.toString());
			int index=1;
			ps.setString(index++, po.getSex());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getBirthday()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getFillTableDate()));
			ps.setString(index++, po.getCommend());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getInDate()));
			ps.setString(index++, po.getGroupNo());
			ps.setString(index++, po.getContractNo());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr1From()));
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getContr1End()));
			ps.setString(index++, po.getCert1No());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getCert1NoDate()));
			ps.setString(index++, po.getServiceNo());
			ps.setDate(index++, ObjectUtil.toSQLDate(po.getServiceNoDate()));

			ps.setInt(index++, po.getBranchId());
			ps.setString(index++, po.getWorkerId());
			ps.execute();
			session.flush();
			ps.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		}
		return 1;
	}

	public int updatePersonStatus(Person po) {
		Person lpo = (Person) getSession().get(Person.class, po.getId());
		lpo.setUpgradeDate(po.getUpgradeDate());
		lpo.setUpgradeReason(po.getUpgradeReason());
		lpo.setRegType(po.getRegType());
		lpo.setType(po.getType());
		lpo.setSalaryType(po.getSalaryType());
		lpo.setPosition(po.getPosition());
		lpo.setFkPosition(po.getFkPosition());
		lpo.setRegBelong(po.getRegBelong());
		lpo.setParty(po.getParty());
		lpo.setGrade(po.getGrade());
		lpo.setSchooling(po.getSchooling());
		lpo.setWorkType(po.getWorkType());
		lpo.setContractNo(po.getContractNo());
		lpo.setContr1From(po.getContr1From());
		lpo.setContr1End(po.getContr1End());
		lpo.setLastModifier(po.getLastModifier());
		getSession().update(lpo);
		return 1;
	}

	public int updatePersonContract(Person po) {
		Person lpo = (Person) getSession().get(Person.class, po.getId());	
		lpo.setUpgradeDate(po.getUpgradeDate());
		lpo.setUpgradeReason(po.getUpgradeReason());
		lpo.setContractNo(po.getContractNo());
//		lpo.setContr1From(po.getContr1From());
		lpo.setContr1End(po.getContr1End());
		lpo.setRegType(po.getRegType());
		lpo.setType(po.getType());
		lpo.setSalaryType(po.getSalaryType());
		lpo.setPosition(po.getPosition());
		lpo.setFkPosition(po.getFkPosition());
		lpo.setRegBelong(po.getRegBelong());
		lpo.setParty(po.getParty());
		lpo.setGrade(po.getGrade());
		lpo.setSchooling(po.getSchooling());
		lpo.setWorkType(po.getWorkType());
		lpo.setLastModifier(po.getLastModifier());
		getSession().update(lpo);
		return 1;
	}
	
	public List<Person> findPersons(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.depart d")
			.append(" where 1=1");
		if (qo != null) {
			if (qo.containsKey("workerId")) sb.append(" and p.workerId = :workerId");
		}
		sb.append(" order by p.id");
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("workerId")) q.setParameter("workerId", qo.get("workerId"));
		}
		return (List<Person>) q.list();
	}

//==================================== PsnPhoto ====================================

//==================================== PsnOnline ====================================

	public List<PsnOnline> getPsnOnlines(Integer branchId, Calendar accDate, Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select pol from PsnOnline pol")
			.append(" left outer join fetch pol.depart d")
			.append(" where 1=1");
		if (branchId != null && branchId != 0) sb.append(" and pol.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and pol.depart.id = :did");
		if(accDate != null){
			sb.append(" and pol.onDate <= :accDate")
				.append(" and (pol.downDate >= :accDate or pol.downDate is null)");
		}
		sb.append(" order by pol.person.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		if(accDate != null) q.setParameter("accDate", accDate);
		return (List<PsnOnline>) q.list();
	}

	/**
	 * 根据机构和部门取出调动历史
	 * @param branchId
	 * @param departId
	 * @return 
	 */
	public List<PsnOnline> getPsnOnlinesByDepart(Integer branchId, Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from PsnOnline po")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and po.branch.id = :bid");
		if (departId != null) sb.append(" and po.depart.id = :did");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (departId != null) q.setParameter("did", departId);
		return (List<PsnOnline>) q.list();
	}

	/**
	 * 取出当前机构的驾驶员调动历史
	 * @param branchId
	 */
	public List<PsnOnline> getDriverOnlines(Integer branchId, Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from PsnOnline po")
			.append(" left outer join fetch po.bus")
			.append(" where po.bus.id > 0");
		if (branchId != null) sb.append(" and po.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and po.depart.id = :did");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		return (List<PsnOnline>) q.list();
	}
	
	/**
	 * 违章取当前机构的驾驶员调动历史,其中车可以为空
	 * @param qo
	 * @return
	 */
	public List<PsnOnline> getDriverOnlines2(Integer branchId, Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from PsnOnline po")
			.append(" left outer join fetch po.person p")
			.append(" left outer join fetch po.line")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and po.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and po.depart.id = :did");
		sb.append(" order by p.workerId");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		return (List<PsnOnline>) q.list();
	}
	
	public List<PsnOnline> getPsnOnlineList(Map qo) {
		StringBuilder sb = new StringBuilder("select po from PsnOnline po");
		String order = (String) qo.get(Constants.PARAM_ORDER);
		Person person = null;
		if (qo != null) {
			if (qo.containsKey("person")) person = (Person) qo.get("person");
		}
		if (person != null) sb.append(" left outer join fetch po.person p");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch po.").append(fetchs[i].trim());
		}
		sb.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and po.branch.id = :bid");
			if (qo.containsKey("depart.id")) sb.append(" and po.depart.id = :did");
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" and po.person.id in (:persons)");
			if (qo.containsKey("onDate_from")) sb.append(" and po.onDate >= :onDate_from");
			if (qo.containsKey("onDate_to")) sb.append(" and po.onDate <= :onDate_to");
			if (qo.containsKey("downDate_from")) sb.append(" and po.downDate >= :downDate_from");
			if (qo.containsKey("downDate_to")) sb.append(" and po.downDate <= :downDate_to");
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) sb.append(" and p.id = :pid");
		}
		if (order == null) order = "person.id asc, onDate desc";
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" po.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id")) q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q.setParameterList("persons", (List) qo.get("persons"));
			if (qo.containsKey("onDate_from")) q.setParameter("onDate_from", ObjectUtil.toCalendar(qo.get("onDate_from")));
			if (qo.containsKey("onDate_to")) q.setParameter("onDate_to", ObjectUtil.toCalendar(qo.get("onDate_to")));
			if (qo.containsKey("downDate_from")) q.setParameter("downDate_from", ObjectUtil.toCalendar(qo.get("downDate_from")));
			if (qo.containsKey("downDate_to")) q.setParameter("downDate_to", ObjectUtil.toCalendar(qo.get("downDate_to")));
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) q.setParameter("pid", person.getId());
		}
		return (List<PsnOnline>) q.list();
	}

	public List<PsnOnline> getCert2Onlines(Map qo) {
		Calendar date = ObjectUtil.toCalendar(qo.get("date"));
		Person person = (Person) qo.get("person");
		String newCert2No = (String) qo.get("newCert2No");
		StringBuilder sb = new StringBuilder();
		sb.append("select po from PsnOnline po left outer join fetch po.person p")
			.append(" where 1 = 1")
			.append(" and (p.id = :pid ");
		if (person.getCert2No() != "" && person.getCert2No() != null && !person.getCert2No().equals("-"))
			sb.append(" or po.cert2No = :cert2No");
		sb.append(" or po.cert2No = :newCert2No)");
		sb.append(" and po.downDate >= :downDate)")
			.append(" order by po.person.id, po.onDate");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("pid", person.getId());
		if (person.getCert2No() != "" && person.getCert2No() != null && !person.getCert2No().equals("-"))
			q.setParameter("cert2No", person.getCert2No());
		q.setParameter("newCert2No", newCert2No)
			.setParameter("downDate", date);
		return (List<PsnOnline>) q.list();
	}

//==================================== PsnStatus ====================================

	public List<Person> getDrivers(Integer branchId, Calendar date) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ps.person from PsnStatus ps")
			.append(" where 1=1")
			.append(" and ps.position ='1' ");
		if (branchId != null && branchId != 0) sb.append(" and ps.branch.id = :bid");
		if(date != null) sb.append(" and ps.onDate <= :date and ps.downDate >= :date");
		sb.append(" order by ps.person.workerId");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if(date != null) q.setParameter("date", date);
		return (List<Person>) q.list();
	}

	public List<PsnStatus> getPsnStatusList(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ps from PsnStatus ps");
		Person person = null;
		if (qo != null) {
			if (qo.containsKey("person")) person = (Person) qo.get("person");
		}
		if (person != null) sb.append(" left outer join fetch ps.person p");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch ps.").append(fetchs[i].trim());
		}
		sb.append(" where 1 = 1 ");
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and ps.branch.id = :bid");
			if (qo.containsKey("depart.id")) sb.append(" and ps.depart.id = :did");
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" and ps.person.id in (:persons)");
			if (qo.containsKey("onDate_from")) sb.append(" and ps.onDate >= :onDate_from");
			if (qo.containsKey("onDate_to")) sb.append(" and ps.onDate <= :onDate_to");
			if (qo.containsKey("downDate_from")) sb.append(" and ps.downDate >= :downDate_from");
			if (qo.containsKey("downDate_to")) sb.append(" and ps.downDate <= :downDate_to");
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) sb.append(" and p.id = :pid");
		}
		sb.append(" order by ps.person.id asc, ps.onDate desc");
		Query q1 = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q1.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id")) q1.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q1.setParameterList("persons", (List) qo.get("persons"));
			if (qo.containsKey("onDate_from")) q1.setParameter("onDate_from", ObjectUtil.toCalendar(qo.get("onDate_from")));
			if (qo.containsKey("onDate_to")) q1.setParameter("onDate_to", ObjectUtil.toCalendar(qo.get("onDate_to")));
			if (qo.containsKey("downDate_from")) q1.setParameter("downDate_from", ObjectUtil.toCalendar(qo.get("downDate_from")));
			if (qo.containsKey("downDate_to")) q1.setParameter("downDate_to", ObjectUtil.toCalendar(qo.get("downDate_to")));
		}
		if (person != null) {
			if (person.getId() != null && person.getId() != 0) q1.setParameter("pid", person.getId());
		}
		List<PsnStatus> list1 = (List<PsnStatus>) q1.list();
		List<PsnStatus> r = list1;
		if (qo.containsKey("onDate_to")){
			sb.delete(0, sb.length());
//			sb.append("select * from t_psn_status t where t.c_statusid in( " +
//					"select c_statusid from t_psn_status where c_ismod_contract is not null " +
//					"and c_statuspsn in (:personsID) having  max(c_ondate) <= to_date(:onDate_to,'YYYY-MM-DD') " +
//					"group by c_statusid) order by c_ondate desc");  //按照时间升序排列对后面数据过滤有作用
			sb.append("SELECT * FROM T_PSN_STATUS T")
				.append(" WHERE 1=1");
			if (qo.containsKey("branch.id")) sb.append(" AND t.C_BELONG = :bid");
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" AND t.C_STATUSPSN IN (:persons)");
			sb.append(" and t.C_ONDATE = (")
				.append("SELECT MAX(B.C_ONDATE) FROM T_PSN_STATUS B WHERE B.C_BELONG = T.C_BELONG ")
				.append(" AND B.C_STATUSPSN = T.C_STATUSPSN AND B.C_ONDATE <= :onDate_to")
				.append(" AND B.C_ISMOD_CONTRACT IS NOT NULL)");

			Query q2 = getSession().createSQLQuery(sb.toString()).addEntity(PsnStatus.class);  //最近合同
			if (qo.containsKey("branch.id")) q2.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q2.setParameterList("persons", (List) qo.get("persons"));
			q2.setParameter("onDate_to", ObjectUtil.toDate(qo.get("onDate_to")));
			List list2 = q2.list();
			Map<Integer, PsnStatus> map = null;
			PsnStatus ps1, ps2;
			if (list1.size() > 0) {
				map = new HashMap<Integer, PsnStatus> ();
				for (int j = 0; j < list1.size(); j++) {
					ps1 = (PsnStatus) list1.get(j);
					if (list2.size() > 0) {
						for (int k = 0; k < list2.size(); k++) {	//找到小于最接近该状态时间的合同历史记录合并数据显示
							ps2 = (PsnStatus) list2.get(k);
							if(ps1.getPersonId().equals(ps2.getPersonId()) && ps1.getOnDate().after(ps2.getOnDate())){
								ps1.setOnDate(ps2.getOnDate());
								ps1.setIsModContract(ps2.getIsModContract());
								ps1.setContractEnd(ps2.getContractEnd());
								break;
							}
						}
					}
					map.put(ps1.getPersonId(), ps1);
				}
			}
			getSession().clear();
			if(map != null && !map.isEmpty()) r = new ArrayList<PsnStatus>(map.values());
			list2 = null;
			map = null;
		}
		return r;
	}

//==================================== Position ====================================

	public Position getPosition(Integer branchId, String no) {
		return (Position) getHibernateTemplate().get(Position.class, new PositionPK(branchId, no));
	}

	public List<Position> getPositions(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select pos from Position pos")
			.append(" left outer join fetch pos.id.branch b")
			.append(" where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and pos.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<Position>) q.list();
	}

//==================================== T_PSN_XXX ====================================

	/**
	 * People
	 * @param id
	 * @return
	 */
	public People getPeople(PeoplePK id) {
		return (People) getHibernateTemplate().get(People.class, id);
	}

	public List<People> getPeoples(Integer branchId) {
		return getPeoples2(branchId);
	}

	public List<People> getPeoples1(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from People p where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and p.id.branch.id = :bid");
		sb.append(" order by p.no, p.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<People>) q.list();
	}

	public List<People> getPeoples2(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from People p left outer join fetch p.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and p.id.branch.id = :bid");
		sb.append(" order by p.no, p.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<People>) q.list();
	}

	/**
	 * PolParty
	 * @param id
	 * @return
	 */
	public PolParty getPolParty(PolPartyPK id) {
		return (PolParty) getHibernateTemplate().get(PolParty.class, id);
	}

	public List<PolParty> getPolParties(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from PolParty po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<PolParty>) q.list();
	}

	/**
	 * HireType
	 * @param id
	 * @return
	 */
	public HireType getHireType(HireTypePK id) {
		return (HireType) getHibernateTemplate().get(HireType.class, id);
	}

	public List<HireType> getHireTypes(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from HireType po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<HireType>) q.list();
	}

	/**
	 * JobGrade
	 * @param id
	 * @return
	 */
	public JobGrade getJobGrade(JobGradePK id) {
		return (JobGrade) getHibernateTemplate().get(JobGrade.class, id);
	}

	public List<JobGrade> getJobGrades(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from JobGrade po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<JobGrade>) q.list();
	}

	/**
	 * JobSpec
	 * @param id
	 * @return
	 */
	public JobSpec getJobSpec(JobSpecPK id) {
		return (JobSpec) getHibernateTemplate().get(JobSpec.class, id);
	}

	public List<JobSpec> getJobSpecs(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from JobSpec po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<JobSpec>) q.list();
	}

	/**
	 * MarryStatus
	 * @param id
	 * @return
	 */
	public MarryStatus getMarryStatus(MarryStatusPK id) {
		return (MarryStatus) getHibernateTemplate().get(MarryStatus.class, id);
	}

	public List<MarryStatus> getMarryStatusList(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from MarryStatus po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<MarryStatus>) q.list();
	}

	/**
	 * NativePlace
	 * @param id
	 * @return
	 */
	public NativePlace getNativePlace(NativePlacePK id) {
		return (NativePlace) getHibernateTemplate().get(NativePlace.class, id);
	}

	public List<NativePlace> getNativePlaces(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from NativePlace po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<NativePlace>) q.list();
	}

	/**
	 * RegBranch
	 * @param id
	 * @return
	 */
	public RegBranch getRegBranch(RegBranchPK id) {
		return (RegBranch) getHibernateTemplate().get(RegBranch.class, id);
	}

	public List<RegBranch> getRegBranches(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from RegBranch po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<RegBranch>) q.list();
	}

	/**
	 * SchDegree
	 * @param id
	 * @return
	 */
	public SchDegree getSchDegree(SchDegreePK id) {
		return (SchDegree) getHibernateTemplate().get(SchDegree.class, id);
	}

	public List<SchDegree> getSchDegrees(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from SchDegree po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<SchDegree>) q.list();
	}

	/**
	 * SchGraduate
	 * @param id
	 * @return
	 */
	public SchGraduate getSchGraduate(SchGraduatePK id) {
		return (SchGraduate) getHibernateTemplate().get(SchGraduate.class, id);
	}

	public List<SchGraduate> getSchGraduates(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from SchGraduate po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<SchGraduate>) q.list();
	}

	/**
	 * Schooling
	 * @param id
	 * @return
	 */
	public Schooling getSchooling(SchoolingPK id) {
		return (Schooling) getHibernateTemplate().get(Schooling.class, id);
	}

	public List<Schooling> getSchoolings(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from Schooling po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<Schooling>) q.list();
	}

	/**
	 * WorkType
	 * @param id
	 * @return
	 */
	public WorkType getWorkType(WorkTypePK id) {
		return (WorkType) getHibernateTemplate().get(WorkType.class, id);
	}

	public List<WorkType> getWorkTypes(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from WorkType po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<WorkType>) q.list();
	}

	/**
	 * RegType
	 * @param id
	 * @return
	 */
	public RegType getRegType(RegTypePK id) {
		return (RegType) getHibernateTemplate().get(RegType.class, id);
	}

	public List<RegType> getRegTypes(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from RegType po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<RegType>) q.list();
	}

	/**
	 * SalaryType
	 * @param id
	 * @return
	 */
	public SalaryType getSalaryType(SalaryTypePK id) {
		return (SalaryType) getHibernateTemplate().get(SalaryType.class, id);
	}

	public List<SalaryType> getSalaryTypes(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from SalaryType po left outer join fetch po.id.branch b where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and po.id.branch.id = :bid");
		sb.append(" order by po.no, po.id.name");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		return (List<SalaryType>) q.list();
	}

//==================================== Report ====================================

	public void prepareChangeData(Map qo) {
		try {
			SecurityUser user = (SecurityUser) qo.get("user");
			Connection conn = getSession().connection();
			CallableStatement cs = conn.prepareCall("{call SP_PSN_CHANGE(?)}");
			int index = 1;
			cs.setInt(index++, user.getBranchId());
			cs.execute();
			cs.close();
		} catch (Exception e) {
			throw new HibernateException(e);
		}
	}

	public Long getPersonCount(Map qo) {
		/*
		Long r2 = getPersonCount2(qo);
		Long r3 = getPersonCount3(qo);
		System.err.println("count2="+r2+", count3="+r3+(r2.equals(r3) ? "...OK!" : "...NG!"));
		return r3;
		*/
		return getPersonCount3(qo);
	}

	protected Long getPersonCount1(Map qo) {
		StringBuilder sb = new StringBuilder();
		Integer a = (Integer) qo.get("a");
		Integer b = (Integer) qo.get("b");
		Integer c = (Integer) qo.get("c");
		Department depart = (Department) qo.get("depart");
		RegBranch regBelong = (RegBranch) qo.get("regBelong");
		Position position = (Position) qo.get("position");
		if (a == 11 && b == 21 && c == 31) // T_PERSONAL
		{
			sb.append("SELECT COUNT(*) FROM T_PERSONAL P")
				.append(" WHERE P.C_PERSONALONDATE <= :date AND P.C_PERSONALDOWNDATE >= :date");
		}
		else if (a == 12)
		{
			if (b == 21 && c == 31) // T_PSN_ONLINE + T_PERSONAL
			{
				sb.append("SELECT COUNT(*) FROM T_PERSONAL P, T_PSN_ONLINE O")
					.append(" WHERE O.C_ONLINEPSN = P.C_PERSONALID")
					.append(" AND P.C_PERSONALONDATE <= :date AND P.C_PERSONALDOWNDATE >= :date")
					.append(" AND O.C_ONDATE <= :date AND O.C_DOWNDATE >= :date");
			}
			else // T_PSN_STATUS + T_PSN_ONLINE + T_PERSONAL
			{
				sb.append("SELECT COUNT(*) FROM T_PSN_STATUS S, T_PERSONAL P, T_PSN_ONLINE O")
					.append(" WHERE S.C_STATUSPSN = P.C_PERSONALID AND O.C_ONLINEPSN = P.C_PERSONALID")
					.append(" AND P.C_PERSONALONDATE <= :date AND P.C_PERSONALDOWNDATE >= :date")
					.append(" AND O.C_ONDATE <= :date AND O.C_DOWNDATE >= :date")
					.append(" AND S.C_ONDATE <= :date AND S.C_DOWNDATE >= :date");
			}
		}
		else // T_PSN_STATUS + T_PERSONAL
		{
			sb.append("SELECT COUNT(*) FROM T_PSN_STATUS S, T_PERSONAL P")
				.append(" WHERE S.C_STATUSPSN = P.C_PERSONALID")
				.append(" AND P.C_PERSONALONDATE <= :date AND P.C_PERSONALDOWNDATE >= :date")
				.append(" AND S.C_ONDATE <= :date AND S.C_DOWNDATE >= :date");
		}
		if (a == 12) sb.append (" AND O.C_DEPART = :depart");
		if (a == 13) sb.append(" AND S.C_REGBELONG = :regBelong");
		if (b == 22) sb.append(" AND S.C_TYPE = :zszg");
		else if (b == 23) sb.append(" AND S.C_TYPE <> :zszg");
		if (c == 32) sb.append(" AND S.C_POSITION = :position");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setParameter("date", qo.get("date"));
		if (a == 12) q.setParameter("depart", depart.getId());
		else if (a == 13) q.setParameter("regBelong", regBelong.getName());
		if (b == 22 || b == 23) q.setParameter("zszg", "正式职工");
		if (c == 32) q.setParameter("position", position.getNo());
		BigDecimal r = (BigDecimal) q.uniqueResult();
		return (Long) r.longValue();
	}

	protected Long getPersonCount2(Map qo) {
		StringBuilder sb = new StringBuilder();
		SecurityUser user = (SecurityUser) qo.get("user");
		Integer a = (Integer) qo.get("a");
		Integer b = (Integer) qo.get("b");
		Integer c = (Integer) qo.get("c");
		Department depart = (Department) qo.get("depart");
		RegBranch regBelong = (RegBranch) qo.get("regBelong");
		Position position = (Position) qo.get("position");
		if (a == 11 && b == 21 && c == 31) // T_PERSONAL
		{
			sb.append("select count(*) from Person p")
				.append(" where p.branch.id = :bid and p.onDate <= :date and p.downDate > :date");
		}
		else if (a == 12)
		{
			if (b == 21 && c == 31) // T_PSN_ONLINE + T_PERSONAL
			{
				sb.append("select count(*) from Person p, PsnOnline po")
					.append(" where p.branch.id = :bid and po.person.id = p.id")
					.append(" and p.onDate <= :date and p.downDate > :date")
					.append(" and po.onDate <= :date and po.downDate >= :date");
			}
			else // T_PSN_STATUS + T_PSN_ONLINE + T_PERSONAL
			{
				sb.append("select count(*) from PsnStatus ps, Person p, PsnOnline po")
					.append(" where p.branch.id = :bid and ps.person.id = p.id and po.person.id = p.id")
					.append(" and p.onDate <= :date and p.downDate > :date")
					.append(" and po.onDate <= :date and po.downDate >= :date")
					.append(" and ps.onDate <= :date and ps.downDate >= :date");
			}
		}
		else // T_PSN_STATUS + T_PERSONAL
		{
			sb.append("select count(*) from PsnStatus ps, Person p")
				.append(" where p.branch.id = :bid and ps.person.id = p.id")
				.append(" and p.onDate <= :date and p.downDate > :date")
				.append(" and ps.onDate <= :date and ps.downDate >= :date");
		}
		if (a == 12) sb.append (" and po.depart.id = :depart");
		if (a == 13) sb.append(" and ps.regBelong = :regBelong");
		if (b == 22) sb.append(" and ps.type = :zszg");
		else if (b == 23) sb.append(" and ps.type <> :zszg");
		if (c == 32) sb.append(" and ps.position = :position");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bid", user.getBranchId());
		q.setParameter("date", qo.get("date"));
		if (a == 12) q.setParameter("depart", depart.getId());
		else if (a == 13) q.setParameter("regBelong", regBelong.getName());
		if (b == 22 || b == 23) q.setParameter("zszg", "正式职工");
		if (c == 32) q.setParameter("position", position.getNo());
		return (Long) q.uniqueResult();
	}

	protected Long getPersonCount3(Map qo) {
		StringBuilder sb = new StringBuilder();
		Integer a = (Integer) qo.get("a");
		Integer b = (Integer) qo.get("b");
		Integer c = (Integer) qo.get("c");
		Department depart = (Department) qo.get("depart");
		RegBranch regBelong = (RegBranch) qo.get("regBelong");
		Position position = (Position) qo.get("position");

		sb.append("SELECT COUNT(*) FROM TMP_PSN_CHANGE T1, ")
			.append("(SELECT MAX(C_ID) AS C_ID FROM TMP_PSN_CHANGE WHERE C_DODATE <= :date GROUP BY C_PERSON) T2")
			.append(" WHERE T1.C_ID = T2.C_ID AND T1.C_CHANGE <> 9");
	
		if (a == 12) sb.append(" AND T1.N_DEPART = :depart");
		else if (a == 13) sb.append(" AND T1.N_REGBELONG = :regBelong");
		if (b == 22) sb.append(" AND T1.N_TYPE = :zszg");
		else if (b == 23) sb.append(" AND T1.N_TYPE <> :zszg");
		if (c == 32) sb.append(" AND T1.N_POSITION = :position");
		Query q = getSession().createSQLQuery(sb.toString());
		q.setParameter("date", qo.get("date"));
		if (a == 12) q.setParameter("depart", depart.getId());
		else if (a == 13) q.setParameter("regBelong", regBelong.getName());
		if (b == 22 || b == 23) q.setParameter("zszg", "正式职工");
		if (c == 32) q.setParameter("position", position.getNo());
		BigDecimal r = (BigDecimal) q.uniqueResult();
		return (Long) r.longValue();
	}

	public List<PsnChange> getIncreasedPersons(Map qo) {
		StringBuilder sb = new StringBuilder();
		Integer a = (Integer) qo.get("a");
		Integer b = (Integer) qo.get("b");
		Integer c = (Integer) qo.get("c");
		Department depart = (Department) qo.get("depart");
		RegBranch regBelong = (RegBranch) qo.get("regBelong");
		Position position = (Position) qo.get("position");
		StringBuilder t = new StringBuilder();

		sb.append("select pc from PsnChange pc")
			.append(" left outer join fetch pc.person p")
			.append(" left outer join fetch pc.oldDepart od")
			.append(" left outer join fetch pc.newDepart nd")
			.append(" left outer join fetch pc.oldPosition op")
			.append(" left outer join fetch pc.newPosition np")
			.append(" where pc.date >= :bdate and pc.date <= :edate");
	
		if (a == 11 && b == 21 && c == 31) sb.append(" and pc.change = 0");
		if (a == 12) {
			sb.append(" and pc.newDepart.id = :depart");
			t.append("pc.changedDepart = 1 or ");
		}
		else if (a == 13) {
			sb.append(" and pc.newRegBelong = :regBelong");
			t.append("pc.changedRegBelong = 1 or ");
		}
		if (b == 22) {
			sb.append(" and pc.newType = :zszg");
			t.append("pc.changedType = 1 or ");
		}
		else if (b == 23) {
			sb.append(" and pc.newType <> :zszg");
			t.append("pc.changedType = 1 or ");
		}
		if (c == 32) {
			sb.append(" and pc.newPosition.id.no = :position");
			t.append("pc.changedPosition = 1 or ");
		}
		if (t.length() > 0) sb.append(" and (").append(t).append("1=0)");
		sb.append(" order by p.workerId");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bdate", qo.get("bcal"));
		q.setParameter("edate", qo.get("ecal"));
		if (a == 12) q.setParameter("depart", depart.getId());
		else if (a == 13) q.setParameter("regBelong", regBelong.getName());
		if (b == 22 || b == 23) q.setParameter("zszg", "正式职工");
		if (c == 32) q.setParameter("position", position.getNo());
		return (List<PsnChange>) q.list();
	}

	public List<PsnChange> getDecreasedPersons(Map qo) {
		StringBuilder sb = new StringBuilder();
		Integer a = (Integer) qo.get("a");
		Integer b = (Integer) qo.get("b");
		Integer c = (Integer) qo.get("c");
		Department depart = (Department) qo.get("depart");
		RegBranch regBelong = (RegBranch) qo.get("regBelong");
		Position position = (Position) qo.get("position");
		StringBuilder t = new StringBuilder();

		sb.append("select pc from PsnChange pc")
			.append(" left outer join fetch pc.person p")
			.append(" left outer join fetch pc.oldDepart od")
			.append(" left outer join fetch pc.newDepart nd")
			.append(" left outer join fetch pc.oldPosition op")
			.append(" left outer join fetch pc.newPosition np")
			.append(" where pc.date >= :bdate and pc.date <= :edate");
	
		if (a == 11 && b == 21 && c == 31) sb.append(" and pc.change = 9");
		if (a == 12) {
			sb.append(" and pc.oldDepart.id = :depart");
			t.append("pc.changedDepart = 1 or ");
		}
		else if (a == 13) {
			sb.append(" and pc.oldRegBelong = :regBelong");
			t.append("pc.changedRegBelong = 1 or ");
		}
		if (b == 22) {
			sb.append(" and pc.oldType = :zszg");
			t.append("pc.changedType = 1 or ");
		}
		else if (b == 23) {
			sb.append(" and pc.oldType <> :zszg");
			t.append("pc.changedType = 1 or ");
		}
		if (c == 32) {
			sb.append(" and pc.oldPosition.id.no = :position");
			t.append("pc.changedPosition = 1 or ");
		}
		if (t.length() > 0) sb.append(" and (").append(t).append("1=0)");
		sb.append(" order by p.workerId");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bdate", qo.get("bcal"));
		q.setParameter("edate", qo.get("ecal"));
		if (a == 12) q.setParameter("depart", depart.getId());
		else if (a == 13) q.setParameter("regBelong", regBelong.getName());
		if (b == 22 || b == 23) q.setParameter("zszg", "正式职工");
		if (c == 32) q.setParameter("position", position.getNo());
		return (List<PsnChange>) q.list();
	}

	public List<PsnChange> getChangedPersons(Map qo) {
		StringBuilder sb = new StringBuilder();
		String value = (String) qo.get("change.value");
		sb.append("select pc from PsnChange pc")
			.append(" left outer join fetch pc.person p")
			.append(" left outer join fetch pc.oldDepart od")
			.append(" left outer join fetch pc.newDepart nd")
			.append(" left outer join fetch pc.oldLine ol")
			.append(" left outer join fetch pc.newLine nl")
			.append(" left outer join fetch pc.oldBus ob")
			.append(" left outer join fetch pc.newBus nb")
			.append(" left outer join fetch pc.oldPosition op")
			.append(" left outer join fetch pc.newPosition np")
			.append(" where p.id in (")
			.append(" select pc2.person.id from PsnChange pc2")
			.append(" where pc2.date >= :bdate and pc2.date <= :edate");

		if (value.equals("all"));
		else if (value.equals("on")) sb.append(" and pc2.change = 0");
		else if (value.equals("down")) sb.append(" and pc2.change = 9");
		else {
			sb.append(" and (pc2.change = 1 or pc2.change = 2 or pc2.change = 3)");
			if (value.equals("depart")) sb.append(" and pc2.changedDepart = 1");
			if (value.equals("office")) sb.append(" and pc2.changedOffice = 1");
			if (value.equals("line")) sb.append(" and pc2.changedLine = 1");
			if (value.equals("bus")) sb.append(" and pc2.changedBus = 1");
			if (value.equals("cert2")) sb.append(" and pc2.changedCert2No = 1");
			if (value.equals("regType")) sb.append(" and pc2.changedRegType = 1");
			if (value.equals("type")) sb.append(" and pc2.changedType = 1");
			if (value.equals("salaryType")) sb.append(" and pc2.changedSalaryType = 1");
			if (value.equals("position")) sb.append(" and pc2.changedPosition = 1");
			if (value.equals("workType")) sb.append(" and pc2.changedWorkType = 1");
			if (value.equals("regBelong")) sb.append(" and pc2.changedRegBelong = 1");
			if (value.equals("party")) sb.append(" and pc2.changedParty = 1");
			if (value.equals("grade")) sb.append(" and pc2.changedGrade = 1");
			if (value.equals("schooling")) sb.append(" and pc2.changedSchooling = 1");
			if (value.equals("contract")) sb.append(" and (pc2.changedContractNo = 1 or pc2.changedContr1End = 1)");
		}
		sb.append(" group by pc2.person.id) order by p.workerId, pc.date");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("bdate", qo.get("bcal"));
		q.setParameter("edate", qo.get("ecal"));
		return (List<PsnChange>) q.list();
	}
	
	public List<Person> getWorkLengths(Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p from Person p")
			.append(" left outer join fetch p.depart d")
			.append(" left outer join fetch p.fkPosition f")
			.append(" where 1 = 1");
		if (qo != null) {
			if (qo.containsKey("branch.id")) sb.append(" and p.branch.id = :bid");
			if (qo.containsKey("depart.id"))	sb.append(" and p.depart.id = :did");
			if (qo.containsKey("person.id"))	sb.append(" and p.id = :pid");
			if (qo.containsKey("date_to")){
				sb.append(" and (p.inDate <= :pInDate or p.inDate is null) ");  //系统注册时间
				sb.append(" and (p.outDate >= :pOutDate or p.outDate is null) "); //调出日期
				sb.append(" and p.downDate >= :pDownDate");   //系统注销日期
			}
		}
		sb.append(" order by p.workerId");  //and p.status='1' 
		Query q = getSession().createQuery(sb.toString());
		if (qo != null) {
			if (qo.containsKey("branch.id")) q.setParameter("bid", qo.get("branch.id"));
			if (qo.containsKey("depart.id"))	q.setParameter("did", qo.get("depart.id"));
			if (qo.containsKey("person.id"))	q.setParameter("pid", qo.get("person.id"));
			if (qo.containsKey("date_to")){
				Calendar cal = ObjectUtil.toCalendar(qo.get("date_to"));
				q.setParameter("pInDate", cal);
				q.setParameter("pOutDate", cal);
				q.setParameter("pDownDate", cal);
			}
		}
		return (List<Person>) q.list();
	}
	
//===============================contract历史合同状态记录表================================================================================	
	/**
	 * map :changeDate 合同变更日期   changeNo 合同变更文号(历史合同号)  endDate 合同终止日期
	 *
	 */
	public List<PsnStatus> getContractList(Map qo){
		if (qo == null) return null;
		StringBuilder sb = new StringBuilder();
		sb.append(" select a.C_BELONG c_belong,"
				+" a.C_STATUSID C_STATUSID,"
				+" a.C_UPGRADE_REASON C_UPGRADE_REASON,"
				+" a.C_DOWNDATE C_DOWNDATE,"
				+" a.c_statuspsn c_statuspsn,"
				+" a.c_regtype c_regtype,"
				+" a.c_type c_type,"
				+" a.c_salarytype c_salarytype,"
				+" a.c_position c_position,"
				+" a.c_worktype c_worktype,"
				+" a.c_regbelong c_regbelong,"
				+" a.c_party c_party,"
				+" a.c_grade c_grade,"
				+" a.c_schoolhistory c_schoolhistory,"
				+" a.c_dopsn c_dopsn,"
				+" (select c_ismod_contract"
				+" from t_psn_status s"
				+" WHERE s.C_BELONG = a.c_belong"
				+" AND s.c_statuspsn = a.c_statuspsn"
				+" AND s.c_ondate ="
				+" (SELECT MAX(t.C_ONDATE)"
				+" FROM t_psn_status t"
				+" WHERE t.C_BELONG = s.C_BELONG"
				+" AND t.c_statuspsn = s.c_statuspsn"
				+" AND t.C_ONDATE <= :changeDate"
				+" AND (t.c_ismod_contract!='-' and t.c_ismod_contract is not null) )) c_ismod_contract,"
				+" (select c_ondate"
				+" from t_psn_status s"
				+" WHERE s.C_BELONG = a.c_belong"
				+" AND s.c_statuspsn = a.c_statuspsn"
				+" AND s.c_ondate ="
				+" (SELECT MAX(t.C_ONDATE)"
				+"  FROM t_psn_status t"
				+" WHERE t.C_BELONG = s.C_BELONG"
				+"   AND t.c_statuspsn = s.c_statuspsn"
				+"   AND t.C_ONDATE <= :changeDate"
				+"   AND (t.c_ismod_contract!='-' and t.c_ismod_contract is not null) )) c_ondate,"
				+" (select c_contract_end"
				+" from t_psn_status s"
				+" WHERE s.C_BELONG = a.c_belong"
				+" AND s.c_statuspsn = a.c_statuspsn"
				+" AND s.c_ondate ="
				+" (SELECT MAX(t.C_ONDATE)"
				+"  FROM t_psn_status t"
				+" WHERE t.C_BELONG = s.C_BELONG"
				+"   AND t.c_statuspsn = s.c_statuspsn"
				+"   AND t.C_ONDATE <= :changeDate"
				+"   AND (t.c_ismod_contract!='-' and t.c_ismod_contract is not null) )) c_contract_end"
				+" from t_psn_status a"
				+" where a.C_BELONG = :bid"
				+" AND a.c_statuspsn in (:persons)"
				+" AND a.c_ondate ="
				+" (SELECT MAX(t.C_ONDATE)"
				+" FROM t_psn_status t"
				+" WHERE t.C_BELONG = a.C_BELONG"
				+" AND t.c_statuspsn = a.c_statuspsn and (t.c_ismod_contract!='-' and t.c_ismod_contract is not null) "
				+" AND t.C_ONDATE <= :changeDate)");
//		if(qo.containsKey("persons")){
//			personList=new ArrayList();
//			String persons=qo.get("persons").toString().replace("[", "").replace("]", "");
//			String[] personString=persons.split(",");
//			for(int i=0; i<personString.length;i++)
//				personList.add(Integer.valueOf(personString[i].trim()));
//		}
		Query q = getSession().createSQLQuery(sb.toString()).addEntity(PsnStatus.class);
		q.setParameter("bid", qo.get("branch.id"));
		q.setParameterList("persons", (List) qo.get("persons"));
		q.setParameter("changeDate", ObjectUtil.toDate(qo.get("changeDate")));
		return (List<PsnStatus>)q.list();
	}
	
	public Map getLateDatePsnStatusTotle(Map qo){
		if(qo==null)
			return null;
		StringBuffer sb=new StringBuffer();
		sb.append("select ps.person.name from PsnStatus ps");
		Person person=null;
		if (qo != null) {
			if (qo.containsKey("person")) person = (Person) qo.get("person");
		}
		if (person != null) sb.append(" left outer join fetch ps.person p");
		String fetch = (String) qo.get(Constants.PARAM_FETCH);
		if (fetch != null) {
			String[] fetchs = fetch.split(",");
			for (int i = 0; i < fetchs.length; i++) sb.append(" left outer join fetch ps.").append(fetchs[i].trim());
		}
		sb.append(" where 1=1 ");
		if(qo!=null){
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) sb.append(" and ps.person.id in (:persons)");
			if(qo.containsKey("changeDate")) sb.append(" and ps.onDate > :changeDate");  //合同变更日期
			if(qo.containsKey("onDate_to")) sb.append(" and ps.onDate > :onDate_to");  //状态变更日期
			if(qo.containsKey("type")) sb.append(" and ps.isModContract != :type"); 
		} 
		Query q=getSession().createQuery(sb.toString());
		if(qo!=null){
			if (qo.containsKey("persons") && qo.get("persons") != null && ((List) qo.get("persons")).size() > 0) q.setParameterList("persons", (List) qo.get("persons"));
			if(qo.containsKey("changeDate")) q.setParameter("changeDate", ObjectUtil.toCalendar(qo.get("changeDate")));
			if(qo.containsKey("onDate_to")) q.setParameter("onDate_to", ObjectUtil.toCalendar(qo.get("onDate_to")));  //状态变更日期
			if(qo.containsKey("type")) q.setParameter("type", qo.get("type"));
		}
		List contractList=q.list();
		Map<String,Integer> retMap=null;
		if(contractList!=null && contractList.size()>0){
			retMap=new HashMap<String,Integer>();
			Integer value;
			for(int i=0;i<contractList.size();i++){
				if(retMap.containsKey((String)contractList.get(i)))
					value=retMap.get((String)contractList.get(i))+1;
				else
					value=1;
				retMap.put((String)contractList.get(i), value);
			}
		}
		return retMap;
	}

	public List<PsnContractRpt> getContractReportList(Map qo) {
		StringBuffer sb = new StringBuffer();
		List<PsnContractRpt> crList = null;
		sb.append("select distinct a.c_personalid id, "
				+"a.c_workerid workId,a.c_personalname name,a.c_sex sex,a.c_pid pid,a.c_worklength workLength, "
				+"f.c_regbelong regBlong,(select g.c_name from t_position g where g.c_no=f.c_position) position, "
				+"f.c_worktype workType,f.c_type type,f.c_party party,f.totle totalContract,"
				+"f.c_ismod_contract contarctNo,f.c_ondate contractBegin,f.c_contract_end contractEnd "
				+"from t_personal a, ");
		sb.append("(select e.totle,c.c_statuspsn,c.c_regbelong,c.c_position,c.c_worktype,c.c_type,c.c_party, "
				+"c.c_ismod_contract,c.c_ondate,c.c_contract_end from t_psn_status c, "
				+"(select count(d.c_statuspsn) totle,d.c_statuspsn from t_psn_status d where "
				+"(trim(d.c_ismod_contract)!='-' and d.c_ismod_contract is not null)  group by d.c_statuspsn) e where " 
				+"e.c_statuspsn = c.c_statuspsn and c.c_ismod_contract is not null and trim(c.c_ismod_contract)!='-' ) f "
				+"where f.c_statuspsn = a.c_personalid ");
		if(qo.containsKey("reportBegin")){
			String begin = (new SimpleDateFormat("yyyy-MM-dd")).format(qo.get("reportBegin"));
			sb.append(" and a.c_contr1_end >=to_date('"+begin+"', 'YYYY-MM-DD') ");
		}
		if(qo.containsKey("reportEnd")){
			String end = (new SimpleDateFormat("yyyy-MM-dd")).format(qo.get("reportEnd"));
			sb.append(" AND a.c_contr1_end <=to_date('"+end+"', 'YYYY-MM-DD') ");
		}
		if(qo.containsKey("position")){
			String postionId = ((Position)qo.get("position")).getId().getNo();
			sb.append(" and  b.c_position='"+postionId+"' ");
		}
		if(qo.containsKey("depart")){
			int departId = ((Department)qo.get("depart")).getId();
			sb.append(" and a.c_depart='"+departId+"' ");
		}
		if(qo.containsKey("type")){
			String type = qo.get("type").toString();
			if(!type.equals("正式职工"))
				sb.append(" and a.c_type!='正式职工'");
			else
				sb.append(" and a.c_type='"+type+"'");
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = getSession().connection();
			stmt = conn.prepareStatement(sb.toString());
			rs = stmt.executeQuery();
			crList = new ArrayList();
			PsnContractRpt lastCot = new PsnContractRpt();
			while (rs.next()) {
				PsnContractRpt cot = new PsnContractRpt();
				boolean idEqual = StringUtil.equals(lastCot.getId(),rs.getString("ID"));
				if (!idEqual) {  //ID不相等的时候做
					cot.setId(rs.getString("ID"));
					cot.setWorkId(rs.getString("WORKID"));
					cot.setName(rs.getString("NAME"));
					cot.setSex(rs.getString("SEX"));
					//						cot.setBarthday(new Calendar().getTime(rs.getDate("BARTHDAY")));
					cot.setPid(rs.getString("PID"));
					cot.setWorkLength(Integer.valueOf(rs.getInt("WORKLENGTH")));
					cot.setTotalContract(Integer.valueOf(rs.getInt("TOTALCONTRACT")));

					lastCot.setId(rs.getString("ID"));
					lastCot.setWorkId(rs.getString("WORKID"));
					lastCot.setName(rs.getString("NAME"));
					lastCot.setSex(rs.getString("SEX"));
					//						cot.setBarthday(new Calendar().getTime(rs.getDate("BARTHDAY")));
					lastCot.setPid(rs.getString("PID"));
					lastCot.setWorkLength(rs.getInt("WORKLENGTH"));
					lastCot.setTotalContract(Integer.valueOf(rs.getInt("TOTALCONTRACT")));
				} else {
					cot.setTotalContract(null);
				}
				if (idEqual && StringUtil.equals(lastCot.getRegBlong() , rs.getString("REGBLONG"))
						&& StringUtil.equals(lastCot.getPosition() , rs.getString("POSITION"))
						&& StringUtil.equals(lastCot.getWorkType() , rs.getString("WORKTYPE"))
						&& StringUtil.equals(lastCot.getType() , rs.getString("TYPE"))
						&& StringUtil.equals(lastCot.getParty() , rs.getString("PARTY"))) {
					//全相等不做任何操作
				} else {
					cot.setRegBlong(rs.getString("REGBLONG"));
					cot.setPosition(rs.getString("POSITION"));
					cot.setWorkType(rs.getString("WORKTYPE"));
					cot.setType(rs.getString("TYPE"));
					cot.setParty(rs.getString("PARTY"));	
					lastCot.setRegBlong(rs.getString("REGBLONG"));
					lastCot.setPosition(rs.getString("POSITION"));
					lastCot.setWorkType(rs.getString("WORKTYPE"));
					lastCot.setType(rs.getString("TYPE"));
					lastCot.setParty(rs.getString("PARTY"));
				}
				cot.setContarctNo(rs.getString("CONTARCTNO"));
				if (rs.getDate("CONTRACTBEGIN") != null) {
					Calendar calBegin= Calendar.getInstance();
					calBegin.setTime(rs.getDate("CONTRACTBEGIN"));
					cot.setContractBegin(calBegin);
				} else {
					cot.setContractBegin(null);
				}
				if (rs.getDate("CONTRACTEND") != null) {
					Calendar calEnd= Calendar.getInstance();
					calEnd.setTime(rs.getDate("CONTRACTEND"));
					cot.setContractEnd(calEnd);
				} else {
					cot.setContractEnd(null);
				}
				crList.add(cot);				
			}
			rs.close();
			stmt.close();
		} catch(Exception e) {
			throw new HibernateException(e);
		}
		return crList;
	}
	
}
