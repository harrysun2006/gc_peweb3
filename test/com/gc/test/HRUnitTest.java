package com.gc.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.naming.ResourceRef;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.enhydra.jdbc.standard.StandardDataSource;
import org.enhydra.jdbc.standard.StandardXADataSource;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.gc.Constants;
import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.common.po.Position;
import com.gc.common.po.SecurityGroup;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityUser;
import com.gc.common.service.BaseServiceUtil;
import com.gc.hr.po.ChkFactD;
import com.gc.hr.po.ChkGroup;
import com.gc.hr.po.ChkLongPlan;
import com.gc.hr.po.ChkPlan;
import com.gc.hr.po.ChkPlanD;
import com.gc.hr.po.HrClose;
import com.gc.hr.po.People;
import com.gc.hr.po.PolParty;
import com.gc.hr.po.SalItem;
import com.gc.hr.service.CheckServiceUtil;
import com.gc.hr.service.CommonServiceUtil;
import com.gc.hr.service.PersonalServiceUtil;
import com.gc.hr.service.SalaryServiceUtil;
import com.gc.util.CommonUtil;
import com.gc.util.DataSourceUtil;
import com.gc.util.DateUtil;
import com.gc.util.PropsUtil;
import com.gc.util.SpringUtil;
import com.gc.web.UserServiceUtil;

public class HRUnitTest extends TestCase {

	private final static Log _log = LogFactory.getLog(HRUnitTest.class);

	protected void setUp() {
	}

	protected void tearDown() {
		// flex.messaging.MessageBrokerServlet
		// flex.messaging.services.remoting.adapters.JavaAdapter
		// org.springframework.orm.hibernate3.LocalSessionFactoryBean
	}

	/**
	 * ��������Դ�Ƿ���ȷʵ����
	 */
	protected static void test0() {
		String[] dataSources = PropsUtil.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);
		DataSource ds;
		String dsName;
		for (int i = 0; i < dataSources.length; i++) {
			try {
				dsName = dataSources[i].trim();
				ds = (DataSource) SpringUtil.getBean(dsName);
				assertTrue(ds instanceof DataSource);
				if (_log.isInfoEnabled()) {
					_log.info("\n" + DataSourceUtil.getDataSourceInfo(ds, dsName));
				}
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			} finally {
			}
		}
	}

	/**
	 * ����Spring, Hibernate�Ƿ���ȷ����
	 * һ��Hibernate����
	 */
	protected static void test1() {
		try {
			List<Branch> branches = BaseServiceUtil.getBranches();
			System.out.println("Total have " + branches.size() + " branches!");
			for(Iterator<Branch> it = branches.iterator(); it.hasNext(); ) {
				System.out.println(it.next());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * û��ʹ��left outer join, people.branch�������ֻ��idֵ
	 */
	protected static void test2A() {
		try {
			List<People> peoples = PersonalServiceUtil.getPeoples1(2);
			People people;
			System.out.println("Total have " + peoples.size() + " peoples!");
			for(Iterator<People> it = peoples.iterator(); it.hasNext(); ) {
				people = it.next();
				System.out.println(people);
				assertNotNull(people.getId().getBranch().getId());
				assertNull(people.getId().getBranch().getName());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * ʹ��left outer join, people.branch�������fetch
	 */
	protected static void test2B() {
		try {
			List<People> peoples = PersonalServiceUtil.getPeoples2(2);
			People people;
			System.out.println("Total have " + peoples.size() + " peoples!");
			for(Iterator<People> it = peoples.iterator(); it.hasNext(); ) {
				people = it.next();
				System.out.println(people);
				assertNotNull(people.getId().getBranch().getId());
				assertNotNull(people.getId().getBranch().getName());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * ����left outer join��ѯʱ�������ȡֵ
	 * SecurityUser -> SecurityGroup <- SecurityLimit
	 *              -> Person
	 *              -> Branch(PsnBelong)
	 * ������� + �������(��ѯ����branch=4�������û�)
	 */
	protected static void test3A() {
		try {
			SecurityUser su = new SecurityUser();
			su.setBranch(new Branch(4));
			List<SecurityUser> users = UserServiceUtil.getSecurityUsers(su);
			SecurityUser user;
			System.out.println("Total have " + users.size() + " users belong to branch(4)!");
			for(Iterator<SecurityUser> it = users.iterator(); it.hasNext(); ) {
				user = it.next();
				System.out.println(user);
				assertNotNull(user.getBranch().getName());
				assertNotNull(user.getPerson().getName());
				assertNotNull(user.getGroup().getUseId());
				assertTrue(user.getLimits().size() > 0);
				assertEquals(user.getLimits(), user.getGroup().getLimits());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * SecurityUser -> SecurityGroup <- SecurityLimit
	 *              -> Person
	 *              -> Branch(PsnBelong)
	 * ������� + �������(��ѯ��branch=4��Ȩ�޵������û�)
	 */
	protected static void test3B() {
		try {
			SecurityUser su = new SecurityUser();
			su.setLimit(new SecurityLimit(4, 0));
			List<SecurityUser> users = UserServiceUtil.getSecurityUsers(su);
			SecurityUser user;
			System.out.println("Total have " + users.size() + " users belong to branch(4)!");
			for(Iterator<SecurityUser> it = users.iterator(); it.hasNext(); ) {
				user = it.next();
				System.out.println(user);
				assertNotNull(user.getBranch().getName());
				assertNotNull(user.getPerson().getName());
				assertNotNull(user.getGroup().getUseId());
				assertTrue(user.getLimits().size() > 0);
				assertEquals(user.getLimits(), user.getGroup().getLimits());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	protected static void test3C() {
		try {
			SecurityUser user = new SecurityUser();
			user.setUseId("lk1");
			user = UserServiceUtil.getSecurityUser(user);
			System.out.println(user);
			assertNotNull(user.getBranch().getName());
			assertNotNull(user.getPerson().getName());
			assertNotNull(user.getGroup().getUseId());
			for (Iterator<SecurityLimit> it = user.getLimits().iterator(); it.hasNext(); ) {
				System.out.println(it.next());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * ���Ը������ֵ
	 */
	protected static void test4() {
		try {
			Person person = PersonalServiceUtil.getPerson(11);
			assertNotNull(person);
			System.out.println(person);
			// Branch branch = BaseServiceUtil.getBranch(4L);
			// assertNotNull(branch);
			// person.setBranch(branch);
			Position position = PersonalServiceUtil.getPosition(2, "4");
			assertNotNull(position);
			if ("1".equals(person.getPosition())) person.setPosition("4");
			else person.setPosition("1");
			BaseServiceUtil.saveObject(person);
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * ��ѯʹ���Զ�������ʵ�ֵ�ö������, SalItem.Type, SalItem.Flag
	 */
	protected static void test5() {
		try {
			List<SalItem> items = SalaryServiceUtil.getAllItems(2);
			System.out.println("Total have " + items.size() + " salary items!");
			for(Iterator<SalItem> it = items.iterator(); it.hasNext(); ) {
				System.out.println(it.next());
			}
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * �����Զ�������ʵ�ֵ�ö�����ͱ�������
	 */
	protected static void test6() {
	}

	/**
	 * ������־������LoggerInterceptor: �û�������¼
	 * ����HibernateAppender
	 */
	protected static void test7A() {
		try {
			SecurityUser user = new SecurityUser();
			user.setUseId("1188");
			user.setPassword1("8811");
			UserServiceUtil.authenticate(user);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			System.out.println(auth.getPrincipal());
		} catch (Throwable t) {
			_log.error("", t);
			fail();
		}
	}

	/**
	 * ������־������LoggerInterceptor: �û�������
	 * ����HibernateAppender
	 */
	protected static void test7B() {
		try {
			SecurityUser user = new SecurityUser();
			user.setUseId("1188x");
			user.setPassword1("8811");
			UserServiceUtil.authenticate(user);
			fail();
		} catch (Throwable t) {
			_log.error("", t);
		}
	}

	/**
	 * ������־������LoggerInterceptor: �������
	 * ����HibernateAppender
	 */
	protected static void test7C() {
		try {
			SecurityUser user = new SecurityUser();
			user.setUseId("1188");
			user.setPassword1("8811x");
			UserServiceUtil.authenticate(user);
			fail();
		} catch (Throwable t) {
			_log.error("", t);
		}
	}

	/**
	 * ��������������gcTransactionInterceptor����־������LoggerInterceptor
	 */
	protected static void test8A() throws Exception {
		try {
			SecurityGroup group = BaseServiceUtil.getSecurityGroup(1);
			assertNotNull(group);
			Branch branch = BaseServiceUtil.getBranch(2);
			assertNotNull(branch);
			Department depart = BaseServiceUtil.getDepartment(2);
			assertNotNull(depart);
			Person person = new Person();
			person.setBranch(branch);
			person.setWorkerId("6668");
			person.setName("May Hu");
			Calendar d1 = DateUtil.getCalendar(Constants.DEFAULT_DATE_FORMAT, "2009-5-1");
			Calendar d2 = DateUtil.getCalendar(Constants.DEFAULT_DATE_FORMAT, "9999-12-31");
			person.setOnDate(d1);
			person.setDownDate(d2);
			person.setUpgradeDate(d1);
			person.setAllotDate(d1);
			person.setType("��ͬ");
			person.setDepart(depart);
			SecurityUser user = new SecurityUser();
			user.setBranch(branch);
			user.setPerson(person);
			user.setGroup(group);
			user.setUseId("1188");
			// BaseServiceUtil.testTx(person, user);
		} catch (Throwable t) {
			_log.error("", t);
		}
	}

	/**
	 * ��������:
	 * ����SPRING��DAO��Service�������������, ��ServiceUtilû�������������, ����Service�����µ��ÿ��������ύ��ع�����:
	 * ...
	 * ServiceUtil.xxx
	 * ...
	 * ServiceUtil.yyy
	 * ...
	 */
	protected static void test9() {
		try {
			PersonalServiceUtil.testTx(new PolParty(2, "Ⱥ��"), new People(2, "����"));
			fail();
		} catch (Throwable t) {
			_log.error("", t);
		}
	}

	/**
	 * ����Flex�ͻ����ύ��������ʱ��̨�״�: 
	 * org.springframework.beans.factory.BeanDefinitionStoreException: Unexpected exception parsing XML document from class path resource [META-INF/gc-spring2.xml]; nested exception is org.springframework.beans.FatalBeanException: Class [org.springframework.beans.factory.xml.SimplePropertyNamespaceHandler] for namespace [http://www.springframework.org/schema/p] does not implement the [org.springframework.beans.factory.xml.NamespaceHandler] interface
	 * 1. ʹ��Application��ʽû������, ����ʹ��jetty WebApplicationʱ���״�(�������ݻᱣ�浽���ݿ�)
	 * 2. ��������jetty��ClassLoader����, һ��Ӧ�÷�����(WebApplication, ServletContext)�����Լ���ClassLoader,
	 * 		����ЩClassLoader��ɺ�ϵͳһ������: (�μ�HRTestWeb)
	 * 			wapp.setClassLoader(ClassLoader.getSystemClassLoader());
	 */
	protected static void test10() {
		People p = new People(2, "����");
		BaseServiceUtil.addObject(p);
	}

	/**
	 * ����Hibernate PO����unwrap
	 */
	protected static void test11() {
		List<People> peoples = PersonalServiceUtil.getPeoples(2);
		Class c;
		for (Iterator<People> it = peoples.iterator(); it.hasNext();) {
			c = it.next().getId().getBranch().getClass();
			System.out.println(c.getName());
			assertTrue(c.equals(Branch.class));
		}
	}

	/**
	 * ����ResourceBundle
	 */
	protected static void test12() {
		
	}

	/**
	 * ����getLimitBranches����: ȡSecurityUserӵ��Ȩ�޵�branches
	 */
	protected static void test13() {
		SecurityUser su = new SecurityUser();
		su.setUseId("lk1");
		List<Branch> branches = UserServiceUtil.getLimitBranches(su);
		for (Iterator<Branch> it = branches.iterator(); it.hasNext(); ) 
			System.out.println(it.next());
	}

	/**
	 * ���Բ�ͬ������Դ - Proxool
	 */
	protected static void test14A() throws Exception {
		ProxoolDataSource ds = new ProxoolDataSource("jdbc/GCPool");
		ds.setDriver("oracle.jdbc.driver.OracleDriver");
		ds.setDriverUrl("jdbc:oracle:thin:@localhost:1521:ORA9I");
		ds.setUser("PEADMIN");
		ds.setPassword("admin");
		ds.setMaximumConnectionCount(10);
		ds.setMaximumConnectionLifetime(300);
		if (_log.isInfoEnabled()) {
			_log.info("\n" + DataSourceUtil.getDataSourceInfo(ds));
		}
	}

	protected static void test14B() throws Exception {
		StandardDataSource ds = new StandardDataSource();
		ds.setDriverName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:ORA9I");
		ds.setUser("PEADMIN");
		ds.setPassword("admin");
	
		if (_log.isInfoEnabled()) {
			_log.info("\n" + DataSourceUtil.getDataSourceInfo(ds));
		}
	}

	protected static void test14C() throws Exception {
		StandardXADataSource ds = new StandardXADataSource();
		ds.setDriverName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:ORA9I");
		ds.setUser("PEADMIN");
		ds.setPassword("admin");
	
		if (_log.isInfoEnabled()) {
			_log.info("\n" + DataSourceUtil.getDataSourceInfo(ds));
		}
	}

	protected static void test14D() throws Exception {
		Reference ref = new ResourceRef("javax.sql.DataSource", null, null, null);
		ref.add(new StringRefAddr("driverClassName", "oracle.jdbc.driver.OracleDriver"));
		ref.add(new StringRefAddr("url", "jdbc:oracle:thin:@localhost:1521:ORA9I"));
		ref.add(new StringRefAddr("username", "PEADMIN"));
		ref.add(new StringRefAddr("password", "admin"));
		Properties props = new Properties();
		props.put("driverClassName", "oracle.jdbc.driver.OracleDriver");
		props.put("url", "jdbc:oracle:thin:@localhost:1521:ORA9I");
		props.put("username", "PEADMIN");
		props.put("password", "admin");
		DataSource ds = BasicDataSourceFactory.createDataSource(props);
		if (_log.isInfoEnabled()) {
			_log.info("\n" + DataSourceUtil.getDataSourceInfo(ds));
		}
	}

	protected static void test14E() throws Exception {
		Properties props = new Properties();
		props.put("user", "PEADMIN");
		props.put("password", "admin");
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:oracle:thin:@localhost:1521:ORA9I", props);
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		if (_log.isInfoEnabled()) {
			_log.info("\n" + DataSourceUtil.getDataSourceInfo(ds));
		}
	}

	protected static void test15() throws Exception {
		List<Department> dlist = BaseServiceUtil.getDepartmentsAndOffices(4, 0);
		System.out.println(dlist.size());
	}

	protected static void test16() throws Exception {
		Long l = -88L;
		System.out.println(Long.toBinaryString(l));
		System.out.println(Long.toBinaryString(~l));
	}

	protected static void test17() throws Exception {
		List<Person> persons = PersonalServiceUtil.getPersonsCard(new Integer[]{1141, 2545, 3969, 8288, 8290, 8291});
		Person p;
		byte[] photo;
		for (Iterator<Person> it = persons.iterator(); it.hasNext(); ) {
			p = it.next();
			photo = p.getPhoto();
			System.out.println("Person " + p 
					+ ", photo.size = " + ((photo == null) ? 0 : photo.length)
					+ ", graduateDate = " + CommonUtil.formatCalendar(Constants.DEFAULT_DATETIME_FORMAT, p.getGraduateDate())
					+ ", partyOnDate = " + CommonUtil.formatCalendar(Constants.DEFAULT_DATETIME_FORMAT, p.getPartyOnDate())
					);
		}
		System.out.println(persons.size());
	}

	protected static void test18() throws Exception {
		List<Department> departs = BaseServiceUtil.getDepartmentsAndOLEs(2, 0);
		System.out.println(departs.size());
	}

	/**
	 * ���Կ��ڼƻ������ڱ�
	 * @throws Exception
	 */
	protected static void test30() throws Exception {
		List<ChkPlanD> planDetails = CheckServiceUtil.getPlanDetails(2, "B09-000001");
		System.out.println(planDetails.size());
		List<ChkFactD> factDetails = CheckServiceUtil.getFactDetails(2, "C09-000001");
		System.out.println(factDetails.size());
	}

	/**
	 * ����equals��isAssignableFrom
	 * @throws Exception
	 */
	protected static void test31() throws Exception {
		ChkGroup g1 = new ChkGroup(1);
		ChkGroup g2 = new ChkGroup(1);
		System.out.println(g1.equals(g2));
		g1.setName("test");
		System.out.println(g1.equals(g2));
		System.out.println(Number.class.isAssignableFrom(Long.class));
	}

	/**
	 * ����BaseServiceUtil.getObjects
	 * @throws Exception
	 */
	protected static void test32() throws Exception {
		// String clazz = "com.gc.hr.po.People";
		String clazz = "People";
		System.out.println(BaseServiceUtil.getIdentifierName(clazz));
		Map params = new Hashtable();
		params.put(Constants.PARAM_CLASS, clazz);
		params.put(Constants.PARAM_ORDER, "no");
		params.put("id.branch.id", 2);
		List pos = BaseServiceUtil.getObjects(clazz, params, true);
		for (int i = 0; i < pos.size(); i++) {
			System.out.println(pos.get(i));
		}
	}

	/**
	 * ����IdentityComparator.compare����
	 */
	protected static void test33() throws Exception {
		ChkGroup g1 = new ChkGroup(1);
		ChkGroup g2 = new ChkGroup(1);
		g1.setName("test1");
		g2.setName("test2");
		System.out.println(Constants.ID_COMPARATOR.compare(g1, g2));
		People p1 = new People(2, "����");
		People p2 = new People(2, "����");
		p1.setNo(1.0);
		p2.setNo(2.0);
		System.out.println(Constants.ID_COMPARATOR.compare(p1, p2));
	}

	/**
	 * ��ϵͳ��id�Ķ������ʹ��<generator class="sequence-identity"/>, �����¼ʱ������id��ֵ
	 * @throws Exception
	 */
	protected static void test34() throws Exception {
		ChkGroup[] ops = new ChkGroup[6];
		Branch b = new Branch(2);
		Department d = new Department(2);
		for (int i = 0; i < ops.length; i++) {
			ops[i] = new ChkGroup();
			ops[i].setBranch(b);
			ops[i].setDepart(d);
			ops[i].setName("CHKGROUP" + i);
			ops[i].setNo(String.valueOf(i));
		}
		BaseServiceUtil.addObjects(ops);
	}

	/**
	 * ����BaseServiceUtil.saveObjects����
	 * @throws Exception
	 */
	protected static void test35() throws Exception {
		String clazz = ChkGroup.class.getName();
		Map params = new Hashtable();
		params.put(Constants.PARAM_CLASS, clazz);
		Object[] opos = BaseServiceUtil.getObjects(clazz, params).toArray();
		Object[] npos = new Object[opos.length + 1];
		ChkGroup g0, g1;
		Branch b = new Branch(2);
		Department d = new Department(2);
		for (int i = 0; i < opos.length; i++) {
			g0 = (ChkGroup) opos[i];
			g1 = new ChkGroup();
			BeanUtils.copyProperties(g0, g1);
			if ("0003".equals(g1.getNo())) { // delete "0003" & add a new one
				g1.setId(0);
				g1.setName("����4");
				g1.setComment("ADD1");
			} else if ("0006".equals(g1.getNo())) { // update
				g1.setComment("UPDATE6");
			}
			npos[i] = g1;
		}
		g1 = new ChkGroup(0);
		g1.setBranch(b);
		g1.setDepart(d);
		g1.setName("����5");
		g1.setComment("ADD2");
		g1.setNo("9002");
		npos[opos.length] = g1;
		BaseServiceUtil.saveObjects(opos, npos, params);
	}

	/**
	 * ����ƾ֤��: ��ٵ�
	 * @throws Exception
	 */
	protected static void test37() throws Exception {
		Calendar cal = Calendar.getInstance();
		System.out.println(CommonServiceUtil.getChkLongPlanNo(2, cal));
	}

	/**
	 * ����ȡ���½����б�
	 * @throws Exception
	 */
	protected static void test38() throws Exception {
		List<HrClose> l = CommonServiceUtil.getCloseList(2);
		System.out.println(l.size());
	}

	/**
	 * �������½���
	 * @throws Exception
	 */
	protected static void test39() throws Exception {
		Calendar cal = Calendar.getInstance();
		HrClose close = new HrClose(new Branch(2), cal);
		close.setComment("test transaction");
		// Date date = CommonServiceUtil.closeAcc(close, "lk1");
		// Date date = CommonServiceUtil.decloseAcc(close, "lk1");
		Date date = CommonServiceUtil.getLastCloseDate(2);
		System.out.println("HR new close date: " + CommonUtil.formatDate(Constants.DEFAULT_DATETIME_FORMAT, date));
	}

	/**
	 * ������ٵ�ƾ֤��������:
	 * ʹ����������PRAGMA AUTONOMOUS_TRANSACTION�ķ�ʽ, ����ʹSELECT FUN_GET_NEXT_HRNO(...) FROM DUAL������ִ��UPDATE/INSERT/DELETE������;
	 * ���ұ�֤���̼߳�Ļ���(�����¼)!!!����ȡƾ֤�ź������ͻ��˲�������ͬһ����, ���ַ�ʽ������.
	 * ʹ��RETURN SYS_REFCURSOR�ķ�ʽ, ͨ���洢�������ÿ���ʹȡƾ֤�ź�������������ͬһ����, �������������, ���޷���֤�����̵߳Ļ���.
	 * 1. ʹ����������PRAGMA AUTONOMOUS_TRANSACTION�ķ�ʽ, �����ڷ���ǰCOMMIT, ����Oracle�״�"ORA-06519: ��⵽��������������Ѿ�����"
	 * 2. ʹ��RETURN SYS_REFCURSOR�ķ�ʽ, ����ͳһ�ɿͻ����ύ��ع�, ���Դ洢�����в�Ҫʹ��COMMIT��ROLLBACK(�������Զ������)
	 * ע��:
	 * Service�п��Զ�ε�������ServiceUtil�ķ���, ������Գɹ��ύ��ع�!!!
	 * ����ServiceUtil�������ε�������ServiceUtil�ķ���, ÿ�εĵ��ò�����ͬһ��������!!!
	 * �ο�test9������
	 * @throws Exception
	 */
	protected static void test40() throws Exception {
		Calendar cal = Calendar.getInstance();
		ChkLongPlan po = new ChkLongPlan(2, null);
		po.setCheckDate(cal);
		System.out.println("Check LongPlan Created: " + CheckServiceUtil.saveLongPlan(po));
	}

	/**
	 * ȡ��ٵ��б�
	 * @throws Exception
	 */
	protected static void test41() throws Exception {
		Map qo = new Hashtable();
		qo.put("depart.id", 2);
		List<ChkLongPlan> list = CheckServiceUtil.getLongPlans(qo);
		for (Iterator<ChkLongPlan> it = list.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}

	/**
	 * ��������ɾ��
	 * @throws Exception
	 */
	protected static void test42() throws Exception {
		String clazz = "ChkPlanD";
		ChkPlan po = new ChkPlan(2, "B09-000002");
		// BaseServiceUtil.deleteObjects(null, null);	// �״�
		System.out.println("Deleted " + BaseServiceUtil.deleteObjects(clazz, po) + " records!");
	}

	/**
	 * ���Կ��ڼƻ�
	 * @throws Exception
	 */
	protected static void test43() throws Exception {
		Calendar cal = Calendar.getInstance();
		ChkPlan cp = new ChkPlan(2, null);
		cp.setDepart(new Department(2));
		cp.setOffice("��Ϣ������");
		cp.setDate(cal);
		List<ChkPlan> list = CheckServiceUtil.getPlans(cp);
		for (Iterator<ChkPlan> it = list.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		cp.setOffice("����");
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DATE, 9);
		list = CheckServiceUtil.getPlans(cp);
		for (Iterator<ChkPlan> it = list.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}

	/**
	 * ���Կ��ڻ��ܱ���
	 * @throws Exception
	 */
	protected static void test44() throws Exception {
		Map qo = new Hashtable();
		qo.put("branch.id", 2);
		qo.put("depart.id", 2);
		Calendar today = Calendar.getInstance();
		qo.put("date_from", DateUtil.getBeginCal(today, Calendar.YEAR));
		qo.put("date_to", DateUtil.getEndCal(today, Calendar.YEAR));
		List list = CheckServiceUtil.getOnlinePersonsAndCheckStat(qo);
		for (Iterator<ChkPlan> it = list.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}

	/**
	 * ���Բ��ŷ�н��Ա�ܱ�
	 * @throws Exception
	 */
	protected static void test60() throws Exception {
		List<Map> list = SalaryServiceUtil.getDeptPsnListA(2);
		for (Iterator<Map> it = list.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}

	/**
	 * ���Բ��ŷ�н��Ա�嵥
	 * @throws Exception
	 */
	protected static void test61() throws Exception {
		Map r = SalaryServiceUtil.getDeptPsnListB(2, 0);
		List<SalItem> items = (List<SalItem>) r.get("items");
		List data = (List) r.get("data");
		for (Iterator it = items.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		for (Iterator it = data.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}
	
	protected static void test62() throws Exception {
		Map r = SalaryServiceUtil.getDeptPsnListD(2);
		if (r == null) return;
		List<SalItem> items = (List<SalItem>) r.get("items");
		List data = (List) r.get("data");
		for (Iterator it = items.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
		for (Iterator it = data.iterator(); it.hasNext(); ) {
			System.out.println(it.next());
		}
	}

	protected static void test63() throws Exception {
		FileInputStream fis = new FileInputStream("README.TXT");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[512];
		int len;
		while ((len = fis.read(b)) > 0) {
			baos.write(b, 0, len);
		}
		fis.close();
		baos.close();
		FileOutputStream fos = new FileOutputStream("1.zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
		zos.setEncoding("GBK");
		// String name = new String("�����ļ�.TXT".getBytes(), "UTF-8");
		String name = "�����ļ�.TXT";
		ZipEntry ze = new ZipEntry(name);
		ze.setSize(baos.size());
		zos.putNextEntry(ze);
		zos.write(baos.toByteArray());
		zos.flush();
		zos.close();
		fos.close();
	}

	protected static void test64() throws Exception {
		int w[] = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1}, i, t;
		char c;
		String a[] = {"1","0","X","9","8","7","6","5","4","3","2"}, r, s;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
    	t = 0;
    	System.out.print("������15/18λ���֤����: ");
      s = in.readLine(); // 320503760308151 ==> 32050319760308151X
      if ("exit".equalsIgnoreCase(s) || "quit".equalsIgnoreCase(s)) break;
      if (s.length() == 18) r = s;
      else if (s.length() != 15) {r = s; continue;}
      else r = s.substring(0, 6) + "19" + s.substring(6);
      for (i=0; i < 17; i++)
      {
        c = r.charAt(i);
        if (c < '0' || c > '9') throw new Exception("�Ƿ����֤����(���з������ַ�)!!!");
        t += (c - '0') * w[i];
      }
      r = r.substring(0, 17) + a[t % 11];
      System.out.println("18λ���֤����Ϊ: " + r);
    }
	}

	public static void main(String args[]){
		// TestRunner.run(HRUnitTest.class);
		try {
			// test0();
			// test1();
			// test2A();
			// test2B();
			// test3A();
			// test3B();
			// test3C();
			// test4();
			// test5();
			// test6();
			// test7A();
			// test7B();
			// test7C();
			// test8A();
			// test9();
			// test10();
			// test11();
			// test12();
			// test13();
			// test14A();
			// test14B();
			// test14C();
			// test14D();
			// test14E();
			// test15();
			// test16();
			// test17();
			// test18();
			// test30();
			// test31();
			// test32();
			// test33();
			// test34();
			// test35();
			// test37();
			// test38();
			// test39();
			// test40();
			// test41();
			// test42();
			// test43();
			// test44();
			// test60();
			// test61();
			// test62();
			// test63();
			test64();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
}
