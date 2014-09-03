package com.gc.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gc.Constants;
import com.gc.common.po.EquOnline;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityLimitPK;
import com.gc.common.service.BaseServiceUtil;
import com.gc.safety.po.Insurer;
import com.gc.safety.service.AccidentServiceUtil;
import com.gc.safety.service.ClaimsServiceUtil;
import com.gc.safety.service.CommonServiceUtil;
import com.gc.safety.service.TransServiceUtil;
import com.gc.util.CommonUtil;
import com.gc.util.DataSourceUtil;
import com.gc.util.PropsUtil;
import com.gc.util.SpringUtil;

public class SafetyUnitTest extends TestCase {
	private final static Log _log = LogFactory.getLog(SafetyUnitTest.class);
	
	public void setUp() {
		
	}

	public void tearDown() {
		
	}
	
	public final static void test0() {
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
	
	public final static void test1() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("accNo", "");
		map.put("transDate1", "");
		map.put("transDate2", "");
		map.put("deptName", "");
		map.put("lineNo", "");
		map.put("busNo", "");
		map.put("authNo", "");
		map.put("workNo", "");
		map.put("psnName", "");
		map.put("typeNo", "");
		map.put("point", "");
		map.put("penalty", "");
		map.put("dealDate1", "");
		map.put("dealDate2", "");
		map.put("doPsn", "");
		map.put("branchId", "4");
		List<?> list = TransServiceUtil.getTransInfoList(map);
		System.out.println(list.size());
	}
	
	/**
	 * 测试客伤列表
	 */
	public final static void test2() {
		Insurer insurer = new Insurer(2L);
		List list = AccidentServiceUtil.getAccAndInPsnListByInsurer(4, insurer);
		System.out.println(list.size());
	}
	
	/**
	 * 测试三责列表
	 */
	public final static void test3() {
		Insurer insurer = new Insurer(2L);
		List list = AccidentServiceUtil.getAccAndOutByInsurer(4, insurer);
		System.out.println(list.size());
	}
	
	/**
	 * 测试理赔凭证查询
	 */
	public final static void test5() {
		List list = ClaimsServiceUtil.getAccGuaForModify(4);
		System.out.println(list.size());
	}
	
	/**
	 * 测试赔付凭证查询
	 */
	public final static void test6() {
		List list = ClaimsServiceUtil.getAccGuaPayForModify(4);
		System.out.println(list.size());
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public final static void test4() {
		ClaimsServiceUtil.getAccGuaForModify(new Integer(4));
	}

	/** 测试查询赔付凭证结果 */
	public static final void test8() {
		List a = ClaimsServiceUtil.getAccPayForQuery(new Integer(4));
		System.out.println("acc pay list: " + a.size());
	}
	
	public static final void test9() {
		String HD_TRANSGRESS = "A";
		String head = CommonUtil.formatCalendar("'" + HD_TRANSGRESS + "'yy'-'", Calendar.getInstance());
		String ret = CommonServiceUtil.getSafetyAccNo(4, HD_TRANSGRESS, head);
		System.out.println("transgress' ref no: " + ret);
		System.out.println(CommonServiceUtil.class.getSimpleName());
		List ret1 = CommonServiceUtil.getCloseList(4);
		System.out.println(ret1.size());
	}
	
	public static final void test10() {
		SecurityLimit limit = new SecurityLimit();
		limit.setId(new SecurityLimitPK(4, 8));
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 0, 1, 0, 0, 0);
		cal.set(Calendar.HOUR, -12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		System.out.println("date from :" + CommonUtil.formatCalendar("yyyy-MM-dd", cal));
		Date date = cal.getTime();
		System.out.println("date :"+date);
		String[] order = {"depart.id", "line.id", "equipment.id", "downDate"};
		List a = BaseServiceUtil.getEquOnlinesForSafetyTree(limit, cal, order);
		System.out.println("EquOnlines List size: " + a.size());
		for (int i=0; i < a.size(); i++) {
			EquOnline obj = (EquOnline) a.get(i);
			if (obj.getLine()==null) {
				System.out.println("dept:"+obj.getDepart().getName()+" bus:"+obj.getEquipment().getUseId()
						+" ondate:"+CommonUtil.formatCalendar("yyyy-MM-dd", obj.getOnDate())+" downdate:"+CommonUtil.formatCalendar("yyyy-MM-dd", obj.getDownDate()));
				
			} else {
				System.out.println("dept:"+obj.getDepart().getName()+" line:"+ obj.getLine().getNo()+" bus:"
						+obj.getEquipment().getUseId()+" ondate:"+CommonUtil.formatCalendar("yyyy-MM-dd", obj.getOnDate())+" downdate:"+CommonUtil.formatCalendar("yyyy-MM-dd", obj.getDownDate()));
			}
		}
	}
	
	public static final void test11() {
		List a = ClaimsServiceUtil.getAccInPsnGua(4, new Insurer(1L));
		System.out.println(a.size());
	}
	
	public static final void test12()
	{
		List list = BaseServiceUtil.getEquOnlineLasts(4); 
		System.out.println("getEquOnlineLasts list size : " + list.size()); 
	}
	
	public static final void main(String[] args) {
		try {
//			test1();
//			test2();
//			test3();
//			test4();
//			test5();
//			test6();
//			test7();
//			test8();
//			test9();
//			test10();
//			test11();
			test12();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
}
