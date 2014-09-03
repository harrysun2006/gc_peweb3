package com.gc.common.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.Constants;
import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.EquOnline;
import com.gc.common.po.EquType;
import com.gc.common.po.Equipment;
import com.gc.common.po.EventLog;
import com.gc.common.po.Line;
import com.gc.common.po.Office;
import com.gc.common.po.SecurityGroup;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityUser;
import com.gc.common.po.Weather;
import com.gc.safety.service.CommonServiceUtil;

public class BaseDAOHibernate extends HibernateDaoSupport {

	/**
	 * 返回Hibernate定义的所有表中的所有记录
	 * @return
	 */
	public List getAllObjects() {
		return getSession().createQuery("select o from java.lang.Object o").list();
	}

	private String guessClass(String clazz) {
		SessionFactory sf = getSessionFactory();
		Map<String, ClassMetadata> cms = sf.getAllClassMetadata();
		String name;
		for (Iterator<String> it = cms.keySet().iterator(); it.hasNext(); ) {
			name = it.next();
			if (name.endsWith("." + clazz)) return name;
		}
		return null;
	}

	public String getIdentifierName(String clazz) {
		SessionFactory sf = getSessionFactory();
		ClassMetadata cm = sf.getClassMetadata(clazz);
		if (cm == null) cm = sf.getClassMetadata(guessClass(clazz));
		return (cm == null) ? null : cm.getIdentifierPropertyName();
	}

	private String guessProp(ClassMetadata cm, Class clazz) {
		Type[] types = cm.getPropertyTypes();
		for (int i = 0; i < types.length; i++) {
			if (clazz.equals(types[i].getReturnedClass())) {
				return cm.getPropertyNames()[i];
			}
		}
		return null;
	}

	public String getPropertyName(String clazz, Object params) {
		SessionFactory sf = getSessionFactory();
		ClassMetadata cm = sf.getClassMetadata(clazz);
		if (cm == null) cm = sf.getClassMetadata(guessClass(clazz));
		Class hClass = params.getClass();
		String r = null;
		// Check PK
		Type type = cm.getIdentifierType();
		Class pkClass = type.getReturnedClass();
		String id = cm.getIdentifierPropertyName();
		if (hClass.equals(pkClass)) r = id;
		// Check general properties
		if (r == null) r = guessProp(cm, hClass);
		// Check properties of PK
		if (r == null) {
			/** Better to use method, TODO: first character to lowerCase
			Method[] methods = pkClass.getDeclaredMethods();
			Method get=null, set=null;
			for (int i = 0; i < methods.length; i++) {
				if (hClass.equals(methods[i].getReturnType()) && methods[i].getName().startsWith("get"))
					get = methods[i];
				if (methods[i].getParameterTypes().length == 1 && hClass.equals(methods[i].getParameterTypes()[0]) && methods[i].getName().startsWith("set"))
					set = methods[i];
				if (get != null && set != null && get.getName().substring(1).equals(set.getName().substring(1))) {
					r = id + "." + get.getName().substring(3);
					break;
				}
			}
			**/
			Field[] fields = pkClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (hClass.equals(fields[i].getType())) {
					r = id + "." + fields[i].getName();
					break;
				}
			}
		}
		return r;
	}

	public int addObject(Object po) {
		getHibernateTemplate().save(po);
		return 1;
	}

	public int deleteObject(Object po) {
		getHibernateTemplate().delete(po);
		return 1;
	}

	public Object getObject(Class clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public int saveObject(Object po) {
		getHibernateTemplate().saveOrUpdate(po);
		return 1;
	}

	public int updateObject(Object po) {
		getHibernateTemplate().update(po);
		return 1;
	}

	public int deleteObjects(String clazz, Object params) {
		String name = getPropertyName(clazz, params);
		int count = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(clazz).append(" po where 1=1");
		if (name != null) {
			sb.append(" and po.").append(name).append(" = :header");
			count++;
		}
		else if (params instanceof Map) {
		}
		// count = 0 means no any condition, then return 0, AVOID to delete all data in a table!!!
		if (count == 0) return 0;
		Query q = getSession().createQuery(sb.toString());
		if (name != null) q.setParameter("header", params);
		else if (params instanceof Map) {
		}
		return q.executeUpdate();
	}

	public void flush() {
		getSession().flush();
	}

	public void evict(Object po) {
		getSession().evict(po);
	}

	public List getObjects(String clazz, Map params, boolean lock) {
		StringBuilder sb = new StringBuilder();
		sb.append("select po from ").append(clazz).append(" po where 1=1");
		Map p = new Hashtable();
		if (params != null) p.putAll(params);
		String order = (String) p.get(Constants.PARAM_ORDER);
		p.remove(Constants.PARAM_CLASS);
		p.remove(Constants.PARAM_ORDER);
		for (Iterator it = p.keySet().iterator(); it.hasNext(); ) {
			sb.append(" and po.").append(it.next()).append("=?");
		}
		if (order == null) order = getIdentifierName(clazz);
		String[] orders = order.split(",");
		if (orders.length > 0) {
			sb.append(" order by");
			for (int i = 0; i < orders.length; i++) sb.append(" po.").append(orders[i].trim()).append(", ");
			sb.delete(sb.length() - 2, sb.length());
		}
		Query q = getSession().createQuery(sb.toString());
		if (lock) q.setLockMode("po", LockMode.UPGRADE);
		int idx = 0;
		for (Iterator it = p.keySet().iterator(); it.hasNext(); ) {
			q.setParameter(idx++, p.get(it.next()));
		}
		return q.list();
	}

// ==================================== Branch ====================================

	public Branch getBranch(Integer id) {
		return (Branch) getHibernateTemplate().get(Branch.class, id);
	}

	/**
	 * 返回所有分公司列表
	 * @return
	 */
	public List<Branch> getBranches() {
		Query q = getSession().createQuery("select b from Branch b");
		return (List<Branch>) q.list();
	}

//==================================== Department ====================================

	public Department getDepartment(Integer id) {
		return (Department) getHibernateTemplate().get(Department.class, id);
	}

	/**
	 * 返回部门列表
	 * @return
	 */
	public List<Department> getDepartments(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select d from Department d where 1 = 1");
		if (branchId != null) sb.append(" and d.branch.id = :bid");
		sb.append(" order by d.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return (List<Department>) q.list();
	}

	/**
	 * 返回部门(含处室列表)列表
	 * @param branchId
	 * @return
	 */
	public List<Department> getDepartmentsAndOffices(Integer branchId, Integer departId) {
		// get depart list (branchId)
		StringBuilder sb = new StringBuilder();
		sb.append("select d from Department d where d.downDate >= :departDownDate");
		if (branchId != null && branchId != 0) sb.append(" and d.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and d.id = :did");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("departDownDate", Constants.MAX_DATE);
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		sb.append(" order by d.id");
		List<Department> dlist = q.list();
		// get office list (branchId)
		sb.delete(0, sb.length());
		sb.append("select o from Office o where 1 = 1");
		if (branchId != null) sb.append(" and o.id.branch.id = :bid");
		q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		sb.append(" order by o.id.name");
		List<Office> olist = q.list();
		// combine 2 lists
		Office o;
		for (Iterator<Office> it = olist.iterator(); it.hasNext(); ) {
			o = it.next();
			o.getId().getDepart().addOffice(o);
		}
		return dlist;
	}

	/**
	 * 返回部门(含处室/线路/车辆列表)列表
	 * @param branchId
	 * @return
	 */
	public List<Department> getDepartmentsAndOLEs(Integer branchId, Integer departId) {
		// get depart list (branchId)
		StringBuilder sb = new StringBuilder();
		sb.append("select d from Department d where d.downDate >= :departDownDate");
		if (branchId != null && branchId != 0) sb.append(" and d.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and d.id = :did");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("departDownDate", Constants.MAX_DATE);
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		sb.append(" order by d.id");
		List<Department> dlist = q.list();
		// get office list (branchId)
		sb.delete(0, sb.length());
		sb.append("select o from Office o where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and o.id.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and o.id.depart.id = :did");
		q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		sb.append(" order by o.id.name");
		List<Office> olist = q.list();
		// get line list (branchId)
		sb.delete(0, sb.length());
		sb.append("select l from Line l where l.downDate >= :lineDownDate");
		if (branchId != null && branchId != 0) sb.append(" and l.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and l.depart.id = :did");
		q = getSession().createQuery(sb.toString());
		q.setParameter("lineDownDate", Constants.MAX_DATE);
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		sb.append(" order by l.name");
		List<Line> llist = q.list();
		// get EquOnline list (branchId)
		sb.delete(0, sb.length());
		// filter EquOnline.downDate > selectedDate at flex
		sb.append("select eo.id, eo.downDate, e.id, e.authNo, eo.depart from EquOnline eo left outer join eo.equipment e where 1 = 1");
		if (branchId != null && branchId != 0) sb.append(" and eo.branch.id = :bid");
		if (departId != null && departId != 0) sb.append(" and eo.depart.id = :did");
		q = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) q.setParameter("bid", branchId);
		if (departId != null && departId != 0) q.setParameter("did", departId);
		sb.append(" order by e.authNo");
		List<Object[]> eolist = q.list();
		// combine 4 lists
		Office o;
		Line l;
		Object[] objs;
		EquOnline eo;
		Equipment e;
		Department d;
		for (Iterator<Office> it = olist.iterator(); it.hasNext(); ) {
			o = it.next();
			o.getId().getDepart().addOffice(o);
		}
		for (Iterator<Line> it = llist.iterator(); it.hasNext(); ) {
			l = it.next();
			l.getDepart().addLine(l);
		}
		for (Iterator<Object[]> it = eolist.iterator(); it.hasNext(); ) {
			objs = it.next();
			eo = new EquOnline((Integer) objs[0]);
			if (objs[2] != null) {
				e = new Equipment((Integer) objs[2]);
				// use T_ONLINE::downDate as T_EQUIPMENTE::downDate
				e.setDownDate((Calendar) objs[1]);
				e.setAuthNo((String) objs[3]);
			} else {
				e = null;
			}
			d = (Department) objs[4];
			eo.setEquipment(e);
			if (d != null) {
				d.addEquOnline(eo);
				if (e != null) d.addEquipment(e);
			}
		}
		return dlist;
	}

//==================================== Office ====================================

	public Office getOffice(Integer id) {
		return (Office) getHibernateTemplate().get(Office.class, id);
	}

//==================================== SecurityGroup ====================================

	public SecurityGroup getSecurityGroup(Integer id) {
		return (SecurityGroup) getHibernateTemplate().get(SecurityGroup.class, id);
	}

//==================================== SecurityUser ====================================

	public List<SecurityUser> getSecurityUsers(SecurityUser su) {
		StringBuilder sb = new StringBuilder();
		sb.append("select su, sl from SecurityUser su")
			.append(" left outer join fetch su.branch b1")
			.append(" left outer join fetch su.person p")
			.append(" left outer join fetch su.group sg")
			.append(", SecurityLimit sl left outer join fetch sl.id.branch b2")
			.append(" where sl.id.group.id = sg.id");
		if (su.getBranch() != null) {
			if (su.getBranchId() != null && su.getBranchId() != 0) sb.append(" and su.branch.id = :branchId1");
			if (su.getBranch().getUseId() != null && su.getBranch().getUseId() != "") sb.append(" and su.branch.useId = :branchUseId1");
		}
		if (su.getLimitBranch() != null) {
			if (su.getLimitBranch().getId() != null && su.getLimitBranch().getId() != 0) 
				sb.append(" and sl.id.branch.id = :branchId2");
			if (su.getLimitBranch().getUseId() != null && su.getLimitBranch().getUseId() != "")
				sb.append(" and sl.id.branch.useId = :branchUseId2");
		}
		if (su.getPerson() != null) {
			if (su.getPerson().getWorkerId() != null && su.getPerson().getWorkerId() != "")
				sb.append(" and p.workerId = :workerId");
		}
		if (su.getPersonDept() != null) {
			if (su.getPersonDept().getId() != null && su.getPersonDept().getId() != 0)
				sb.append(" and p.depart.id = :departId");
			if (su.getPersonDept().getName() != null && su.getPersonDept().getName() != "")
				sb.append(" and p.depart.name = :departName");
		}
		Query q = getSession().createQuery(sb.toString());
		if (su.getBranch() != null) {
			if (su.getBranchId() != null && su.getBranchId() != 0) 
				q.setParameter("branchId1", su.getBranchId());
			if (su.getBranch().getUseId() != null && su.getBranch().getUseId() != "") 
				q.setParameter("branchUseId1", su.getBranch().getUseId());
		}
		if (su.getLimitBranch() != null) {
			if (su.getLimitBranch().getId() != null && su.getLimitBranch().getId() != 0) 
				q.setParameter("branchId2", su.getLimitBranch().getId());
			if (su.getLimitBranch().getUseId() != null && su.getLimitBranch().getUseId() != "")
				q.setParameter("branchUseId2", su.getLimitBranch().getUseId());
		}
		if (su.getPerson() != null) {
			if (su.getPerson().getWorkerId() != null && su.getPerson().getWorkerId() != "")
				q.setParameter("workerId", su.getPerson().getWorkerId());
		}
		if (su.getPersonDept() != null) {
			if (su.getPersonDept().getId() != null && su.getPersonDept().getId() != 0)
				q.setParameter("departId", su.getPersonDept().getId());
			if (su.getPersonDept().getName() != null && su.getPersonDept().getName() != "")
				q.setParameter("departName", su.getPersonDept().getName());
		}
		List<Object[]> list = (List<Object[]>) q.list();
		List<SecurityUser> ulist = new ArrayList<SecurityUser>();
		Object[] data;
		SecurityUser u;
		SecurityLimit l;
		for (Iterator<Object[]> it = list.iterator(); it.hasNext(); ) {
			data = it.next();
			u = (SecurityUser) data[0];
			l = (SecurityLimit) data[1];
			if (u != null) {
				if (!u.getLimits().contains(l)) u.getGroup().addLimit(l);
				if (!ulist.contains(u)) ulist.add(u);
			}
		}
		return ulist;
	}

	public SecurityUser getSecurityUser(SecurityUser su) {
		return getSecurityUser2(su);
	}

	/**
	 * query 2 times
	 * @param su
	 * @return
	 */
	protected SecurityUser getSecurityUser1(SecurityUser su) {
		StringBuilder sb = new StringBuilder();
		sb.append("select su from SecurityUser su")
			.append(" left outer join fetch su.branch b")
			.append(" left outer join fetch su.group sg")
			.append(" left outer join fetch su.person p")
			.append(" where su.useId = :uid");
		Query q = getSession().createQuery(sb.toString()).setParameter("uid", su.getUseId());
		// List<SecurityUser> ulist = (List<SecurityUser>) q.list();
		// SecurityUser u = (ulist.size() > 0) ? ulist.get(0) : null;
		if (su != null) {
			sb = new StringBuilder();
			sb.append("select sl from SecurityLimit sl")
				.append(" left outer join fetch sl.id.branch b")
				.append(" left outer join fetch sl.id.group sg")
				.append(" where sl.id.group.id = :gid");
			q = getSession().createQuery(sb.toString()).setParameter("gid", su.getGroupId());
			List<SecurityLimit> llist = (List<SecurityLimit>) q.list();
			su.getGroup().setLimits(llist);
		}
		return su;
	}

	/**
	 * query an Object[SecurityUser, SecurityLimit] array & assemble manually
	 * @param su
	 * @return
	 */
	private SecurityUser getSecurityUser2(SecurityUser su) {
		StringBuilder sb = new StringBuilder();
		sb.append("select su, sl from SecurityUser su")
			.append(" left outer join fetch su.branch b1")
			.append(" left outer join fetch su.person p")
			.append(" left outer join fetch su.group sg")
			.append(", SecurityLimit sl left outer join fetch sl.id.branch b2")
			.append(" left outer join fetch sl.safetyLimitDepart d1")
			.append(" left outer join fetch sl.hrLimitDepart d2")
			.append(" where sl.id.group.id = sg.id");
		if (su.getUseId() != null && su.getUseId() != "") sb.append(" and su.useId = :useId");
		if (su.getBranch() != null) {
			if (su.getBranchId() != null && su.getBranchId() != 0) sb.append(" and su.branch.id = :branchId1");
			if (su.getBranch().getUseId() != null && su.getBranch().getUseId() != "") sb.append(" and su.branch.useId = :branchUseId1");
		}
		if (su.getLimitBranch() != null) {
			if (su.getLimitBranch().getId() != null && su.getLimitBranch().getId() != 0) 
				sb.append(" and sl.id.branch.id = :branchId2");
			if (su.getLimitBranch().getUseId() != null && su.getLimitBranch().getUseId() != "")
				sb.append(" and sl.id.branch.useId = :branchUseId2");
		}
		if (su.getPerson() != null) {
			if (su.getPerson().getWorkerId() != null && su.getPerson().getWorkerId() != "")
				sb.append(" and p.workerId = :workerId");
		}
		if (su.getPersonDept() != null) {
			if (su.getPersonDept().getId() != null && su.getPersonDept().getId() != 0)
				sb.append(" and p.depart.id = :departId");
			if (su.getPersonDept().getName() != null && su.getPersonDept().getName() != "")
				sb.append(" and p.depart.name = :departName");
		}
		Query q = getSession().createQuery(sb.toString());
		if (su.getUseId() != null && su.getUseId() != "") q.setParameter("useId", su.getUseId());
		if (su.getBranch() != null) {
			if (su.getBranchId() != null && su.getBranchId() != 0) 
				q.setParameter("branchId1", su.getBranchId());
			if (su.getBranch().getUseId() != null && su.getBranch().getUseId() != "") 
				q.setParameter("branchUseId1", su.getBranch().getUseId());
		}
		if (su.getLimitBranch() != null) {
			if (su.getLimitBranch().getId() != null && su.getLimitBranch().getId() != 0) 
				q.setParameter("branchId2", su.getLimitBranch().getId());
			if (su.getLimitBranch().getUseId() != null && su.getLimitBranch().getUseId() != "")
				q.setParameter("branchUseId2", su.getLimitBranch().getUseId());
		}
		if (su.getPerson() != null) {
			if (su.getPerson().getWorkerId() != null && su.getPerson().getWorkerId() != "")
				q.setParameter("workerId", su.getPerson().getWorkerId());
		}
		if (su.getPersonDept() != null) {
			if (su.getPersonDept().getId() != null && su.getPersonDept().getId() != 0)
				q.setParameter("departId", su.getPersonDept().getId());
			if (su.getPersonDept().getName() != null && su.getPersonDept().getName() != "")
				q.setParameter("departName", su.getPersonDept().getName());
		}
		List<Object[]> list = (List<Object[]>) q.list();
		Object[] data;
		SecurityUser u = null, ru = null;
		SecurityLimit l;
		for (Iterator<Object[]> it = list.iterator(); it.hasNext(); ) {
			data = it.next();
			u = (SecurityUser) data[0];
			l = (SecurityLimit) data[1];
			if (u == null) continue;
			else {
				if (ru == null) ru = u;
				if (u != ru) continue;
				if (!ru.getLimits().contains(l)) ru.getGroup().addLimit(l);
			}
		}
		return ru;
	}

	public List<Branch> getLimitBranches(SecurityUser su) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct sl.id.branch from SecurityUser su")
			.append(", SecurityLimit sl")
			.append(" where sl.id.group.id = su.group.id");
		if (su.getUseId() != null && su.getUseId() != "") sb.append(" and su.useId = :useId");
		if (su.getPerson() != null) {
			if (su.getPerson().getWorkerId() != null && su.getPerson().getWorkerId() != "")
				sb.append(" and su.person.workerId = :workerId");
		}
		Query q = getSession().createQuery(sb.toString());
		if (su.getUseId() != null && su.getUseId() != "") q.setParameter("useId", su.getUseId());
		if (su.getPerson() != null) {
			if (su.getPerson().getWorkerId() != null && su.getPerson().getWorkerId() != "")
				q.setParameter("workerId", su.getPerson().getWorkerId());
		}
		List<Branch> branches = (List<Branch>) q.list();
		return branches;
	}

	public SecurityUser getSecurityUser(Integer id) {
		return (SecurityUser) getHibernateTemplate().get(SecurityUser.class, id);
	}

//==================================== EventLog ====================================

	public EventLog getEventLog(Integer id) {
		return (EventLog) getHibernateTemplate().get(EventLog.class, id);
	}

//==================================== Equipment ====================================

	@SuppressWarnings("unchecked")
	public List<Equipment> getEquipmentsByBranchId(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select e from Equipment e")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and e.branch.id = :bid");
		sb.append(" order by e.authNo");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return (List<Equipment>) q.list();
	}

//==================================== Line ====================================

	@SuppressWarnings("unchecked")
	public List<Line> getLines(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select l from Line l where 1 = 1");
		if (branchId != null) sb.append(" and l.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return (List<Line>) q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Line> getLinesByBD(Integer branchId,Calendar onDate,Calendar downDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("select l from Line l where 1 = 1");
//		sb.append(" and l.onDate <= :onDate")
//			.append(" and (l.downDate >= :downDate or l.downDate is null)");
		if (branchId != null) sb.append(" and l.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
//		q.setParameter("onDate", onDate);
//		q.setParameter("downDate", downDate);
		return (List<Line>) q.list();
	}

//==================================== EquType ====================================
	
	@SuppressWarnings("unchecked")
	public List<EquType> getEquTypes() {
		StringBuilder sb = new StringBuilder();
		sb.append("select e from EquType e");
		Query q = getSession().createQuery(sb.toString());
		return (List<EquType>) q.list();
	}
	
//==================================== EquOnline ====================================

	@SuppressWarnings("unchecked")
	public List<EquOnline> getEquOnlines(Integer branchId, Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select e from EquOnline e")
		.append(" where 1 = 1");
		if (branchId != null) sb.append(" and e.branch.id = :bid");
		if (departId != null && departId != -1) sb.append(" and e.depart.id = :did");
		sb.append(" order by e.equipment, e.onDate");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (departId != null && departId != -1) q.setParameter("did", departId);
		return (List<EquOnline>) q.list();
	}
	
	//违章查询最后一次调动信息
	public List<EquOnline> getEquOnlineLasts(Integer branchId) {
//		Query query = getSession().getNamedQuery("EQUONLINE_LAST_ALL");
		StringBuilder sb = new StringBuilder();
//		sb.append("select eo.equipment.id,max(eo.onDate) from EquOnline eo")
//			.append(" where 1=1");
//		if(branchId != null && branchId != 0) sb.append(" and eo.branch.id = :bid");
//		sb.append(" group by eo.equipment.id");
//		Query q = getSession().createQuery(sb.toString());
//		if(branchId != null && branchId != 0) q.setParameter("bid", branchId);
//		List list = q.list();
//		List equIds = new ArrayList();
//		List onDates = new ArrayList();
//		Object[] objects = {};
//		for(int i=0;i<list.size(); i++) {
//			objects = (Object[])list.get(i);
//			equIds.add(objects[0]);
//			onDates.add(objects[1]);
//		}
		sb.append("select eo from EquOnline eo")
		.append(" left outer join fetch eo.equipment equ")
		.append(" left outer join fetch eo.depart d")
		.append(" left outer join fetch eo.line l")
		.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and eo.branch.id = :bid");
		sb.append(" order by equ.authNo");
		Query query = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query.setParameter("bid", branchId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<EquOnline> getEquOnlineList(Integer branchId, Calendar accDate, Integer departId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select eo from EquOnline eo")
		.append(" left outer join fetch eo.equipment equ")
		.append(" left outer join fetch equ.type t")
		.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and eo.branch.id = :bid");
		if (departId != null && departId != 0) {
			sb.append(" and eo.depart.id = :departId");
		}
		if(accDate != null){
			sb.append(" and eo.onDate <= :accDate")
			.append(" and (eo.downDate >= :accDate or eo.downDate is null)");
		}
		Query query = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) {
			query.setParameter("bid", branchId);
		}
		if (departId != null && departId != 0) {
			query.setParameter("departId", departId);
		}
		if(accDate != null){
			query.setParameter("accDate", accDate);
		}
		return (List<EquOnline>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<EquOnline> getEquOnlinesByBD(Integer branchId, Calendar onDate, Calendar downDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("select eo from EquOnline eo")
		.append(" left outer join fetch eo.equipment equ")
		.append(" left outer join fetch equ.type")
		.append(" left outer join fetch eo.line l")
		.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and eo.branch.id = :bid");
		sb.append(" and (")
		
			.append("(")
			.append("eo.onDate <= :onDate")
			.append(" and ((eo.downDate > :onDate and eo.downDate <= :downDate) or eo.downDate is null)")
			.append(")")
			.append(" or (")
			.append("eo.onDate >= :onDate")
			.append(" and (eo.downDate <= :downDate or eo.downDate is null)")
			.append(")")
			.append(" or (")
			.append("(eo.onDate >= :onDate and eo.onDate < :downDate)")
			.append(" and (eo.downDate >= :downDate or eo.downDate is null)")
			.append(")")
			
			.append(" or (")
			.append("eo.onDate >= :onDate")
			.append(" and ((eo.onDate < :downDate and eo.downDate >= :downDate) or eo.downDate is null)")
			.append(")")
			.append(" or (")
			.append("eo.onDate <= :onDate")
			.append(" and (eo.downDate >= :downDate or eo.downDate is null)")
			.append(")")
			.append(" or (")
			.append("(eo.onDate <= :onDate and (eo.downDate > :downDate or eo.downDate is null))")
			.append(" and (eo.downDate <= :downDate or eo.downDate is null)")
			.append(")")
			
			.append(")");
		sb.append(" order by equ.id");
		Query query = getSession().createQuery(sb.toString());
		if (branchId != null && branchId != 0) query.setParameter("bid", branchId);
		query.setParameter("onDate", onDate);
		query.setParameter("downDate", downDate);
		List<EquOnline> list =  query.list();
		if(list.size() == 0) return list;
		int id = 0;
		for(int i = list.size()-1; i >= 0; i--){
			if (id == list.get(i).getEquipmentId().intValue()) {
				list.remove(i);
				continue;
			}
			id = list.get(i).getEquipmentId().intValue();
		}
		return list;
	}
	
	/**
	 * 安全主页面的树(部门,线路,车辆)
	 * @param limit,orderColumns,thisYear
	 * @return
	 */
	@Deprecated
	public List getDeptsLinesBusesForSafetyTree(SecurityLimit limit,String[] orderColumns,Calendar dateFrom) {
		StringBuilder sb = new StringBuilder();
		sb.append("select eo from EquOnline eo")
			.append(" left outer join fetch eo.equipment equ")
			.append(" left outer join fetch eo.line l")
			.append(" left outer join fetch eo.depart d")
			.append(" where 1=1");
		sb.append(" and eo.branch.id = :branchId");
		sb.append(" and eo.onDate <= :now and (eo.downDate >= :now or eo.downDate is null)")
			.append(" and eo.planUse != :official");
		sb.append(" order by");
		if (orderColumns != null && orderColumns.length > 0) {
			for (int i = 0; i < orderColumns.length; i++) {
				sb.append(" eo.").append(orderColumns[i]).append(",");
			}
		}
		sb.append(" equ.useId");
		Query q1 = getSession().createQuery(sb.toString());
		q1.setParameter("branchId", limit.getBranchId());
		q1.setParameter("now", CommonServiceUtil.getNowDate());
		q1.setParameter("official", "公务");
		List<EquOnline> equOnlineList = (List<EquOnline>)q1.list();
		Query query = getSession().getNamedQuery("EQUONLINE_LAST");
		query.setParameter("branchId", limit.getBranchId());
		query.setParameter("now", CommonServiceUtil.getNowDate());
		query.setParameter("thisYear", dateFrom);
		List<EquOnline> olList = (List<EquOnline>)query.list();
		List<Integer> idList = new ArrayList<Integer>();
		for(EquOnline equOnline : olList){ 
			idList.add(equOnline.getId());
		}
		sb.delete(0, sb.length());
		sb.append("select eo from EquOnline eo")
			.append(" left outer join fetch eo.equipment equ")
			.append(" left outer join fetch eo.line l")
			.append(" left outer join fetch eo.depart d")
			.append(" where eo.branch.id = :branchId")
			.append(" and eo.id in (:idList)");
		Query query2 = getSession().createQuery(sb.toString());
		query2.setParameter("branchId", limit.getBranchId());
		query2.setParameterList("idList", idList);
		List<EquOnline> equList = (List<EquOnline>)query2.list();
		for(EquOnline equOnline : equList){
			equOnlineList.add(equOnline);
		}
		// 查询已报废的车的最后一次调度信息，没搞定，暂时使用sql
//		sb.delete(0, sb.length());
//		sb.append("select eol from EquOnline eol")
//			.append(" where eol.branch.id = :branchId")
//			.append(" and exists (")
//			.append("select eo.equipment.id,max(eo.onDate) from EquOnline eo")
//			.append(" where eol.equipment.id = eo.equipment.id and eol.onDate = eo.onDate")
//			.append(" and eo.branch.id = :branchId")
//			.append(" and (eo.equipment.id in (select e.id from Equipment e where e.downDate <= :now and e.downDate >= :thisYear))")
//			.append(" group by eo.equipment.id)");
//		Query q2 = getSession().createQuery(sb.toString());
//		q2.setParameter("branchId", limit.getLimitBranchId());
//		q2.setParameter("now", now);
//		q2.setParameter("thisYear", thisYear);
//		List downEquList = q2.list();
		
		//如果需要显示所有没非公务车和线路的部门，将下面的代码放出
		sb.delete(0, sb.length());
		sb.append("select d from Department d");
		Query q3 = getSession().createQuery(sb.toString());
		List<Department> departList = (List<Department>)q3.list();
		for(Department department : departList){
			int m = 0;
			for (EquOnline equOnline : equOnlineList){
				if(department.getId().equals(equOnline.getDepartId()) && equOnline.getEquipment() != null)
					m++;
			}
			if(m == 0){
				EquOnline eol = new EquOnline();
				Line l = new Line();
				eol.setLine(l);
				eol.setDepart(department);
				equOnlineList.add(eol);
			}
		}
		return equOnlineList;
	}
	
	/**
	 * 安全主页面的树基础数据(部门,线路,车辆)
	 * @param limit 权限,待扩展是否可以看见其他部门的数据
	 * @param dateFrom 起始日期
	 * @param order 排序数组
	 * @return 起始日期后的部门、线路及车辆的调动信息
	 */
	public List<EquOnline> getEquOnlinesForSafetyTree(SecurityLimit limit, Calendar dateFrom, String[] order) {
		StringBuilder sb = new StringBuilder();
		sb.append("select e from EquOnline e")
			.append(" left outer join fetch e.depart")
			.append(" left outer join fetch e.line")
			.append(" left outer join fetch e.equipment")
			.append(" where 1=1");
		if (limit != null) sb.append(" and e.branch.id = :bid");
		if (dateFrom != null) sb.append(" and (e.downDate >= :dateFrom or e.downDate is null)");
		sb.append(" and e.planUse != :official");
		sb.append(" order by");
		if (order != null && order.length > 0) {
			for (int i = 0; i < order.length; i++) {
				sb.append(" e.").append(order[i]).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		Query q = getSession().createQuery(sb.toString());
		if (limit != null) q.setParameter("bid", limit.getBranchId());
		if (dateFrom != null) q.setParameter("dateFrom", dateFrom);
		q.setParameter("official", Constants.DEFAULT_EQU_NATURE);
		List<EquOnline> eolList = new ArrayList<EquOnline>();
		eolList = (List<EquOnline>) q.list();
		//查询里程
		sb.delete(0, sb.length());
		sb.append("select eo.id,sum(m.reading) from MeterReading m")
			.append(", EquOnline eo")
			.append(" where 1=1");
		if (limit != null) sb.append(" and m.id.branch.id = :bid");
		if (limit != null) sb.append(" and eo.branch.id = :bid");
		sb.append(" and m.id.type = '1'")
			.append(" and m.id.equipment.id = eo.equipment.id");
		if (dateFrom != null) sb.append(" and m.id.readTime >= :dateFrom");
		sb.append(" and eo.onDate <= m.id.readTime")
			.append(" and (eo.downDate > m.id.readTime or eo.downDate is null)")
			.append(" and eo.planUse != :official");
		sb.append(" group by eo.id");
		Query q1 = getSession().createQuery(sb.toString());
		if (limit != null) q1.setParameter("bid", limit.getBranchId());
		if (dateFrom != null) q1.setParameter("dateFrom", dateFrom);
		q1.setParameter("official", Constants.DEFAULT_EQU_NATURE);
		Object[] meArr = new Object[]{};
		meArr = q1.list().toArray();
		for(EquOnline equOnline : eolList){
			for(int i=0; i < meArr.length; i++){
				Object[] obj2 = (Object[])meArr[i];
				if (equOnline.getId().equals(obj2[0])) {
					equOnline.setMileage((Double)obj2[1]);
					break;
				} else {
					equOnline.setMileage(0D);
				}
			}
		}
		return eolList;
	}
//==================================== Weather ====================================

	@SuppressWarnings("unchecked")
	public List<Weather> getWeathers(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select w from Weather w")
			.append(" left outer join fetch w.branch")
			.append(" where 1 = 1");
		if (branchId != null) sb.append(" and w.branch.id = :bid");
		sb.append(" order by w.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return (List<Weather>) q.list();
	}

}
