package com.gc.common.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import com.gc.Constants;
import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.EquOnline;
import com.gc.common.po.EquType;
import com.gc.common.po.Equipment;
import com.gc.common.po.Line;
import com.gc.common.po.Office;
import com.gc.common.po.SecurityGroup;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.Weather;
import com.gc.util.SpringUtil;

public class BaseServiceUtil {

	public static final String BEAN_NAME = "commonBaseServiceUtil";

	private BaseService baseService;

	public static BaseService getBaseService() {
		ApplicationContext ctx = SpringUtil.getContext();
		BaseServiceUtil util = (BaseServiceUtil) ctx.getBean(BEAN_NAME);
		BaseService service = util.baseService;
		return service;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	/**
	 * ����clazzӳ�����������
	 * @param clazz: ӳ���������, ֧�ֳ���(com.gc.hr.po.People)�Ͷ���(People)
	 * @return
	 */
	public static String getIdentifierName(String clazz) {
		return getBaseService().getIdentifierName(clazz);
	}

	public static Object getIdentifierValue(Object po) {
		return getBaseService().getIdentifierValue(po);
	}

	public static String getPropertyName(String clazz, Object po) {
		return getBaseService().getPropertyName(clazz, po);
	}

	public static int addObject(Object po) {
		return getBaseService().addObject(po);
	}

	public static int deleteObject(Object po) {
		return getBaseService().deleteObject(po);
	}

	public static Object getObject(Class clazz, Serializable id) {
		return getBaseService().getObject(clazz, id);
	}

	public static int saveObject(Object po) {
		return getBaseService().saveObject(po);
	}

	public static int updateObject(Object po) {
		return getBaseService().updateObject(po);
	}

	public static List getObjects(Class clazz, Map params) {
		return getObjects(clazz.getName(), params, false);
	}

	/**
	 * ���ز�ѯ����б�
	 * @param clazz: ӳ���������, ֧�ֳ���(com.gc.hr.po.People)�Ͷ���(People)
	 * @param params: ���򼰲�ѯ��������, ��: @order="id", @branch.id=2
	 * @return
	 */
	public static List getObjects(String clazz, Map params) {
		return getObjects(clazz, params, false);
	}

	public static List getObjects(Class clazz, Map params, boolean lock) {
		return getObjects(clazz.getName(), params, lock);
	}

	/**
	 * ���ز�ѯ����б�
	 * @param clazz: ӳ���������, ֧�ֳ���(com.gc.hr.po.People)�Ͷ���(People)
	 * @param params: ���򼰲�ѯ��������, ��: @order="id", @branch.id=2
	 * @param lock: �Ƿ��������ؽ����¼��(SELECT ... FOR UPGRADE)
	 * @return
	 */
	public static List getObjects(String clazz, Map params, boolean lock) {
		return getBaseService().getObjects(clazz, params, lock);
	}

	public static int addObjects(Object[] pos) {
		return getBaseService().addObjects(pos);
	}

	public static int deleteObjects(Object[] pos) {
		return getBaseService().deleteObjects(pos);
	}

	/**
	 * ʹ�������������ɾ��
	 * @param clazz: ��Ҫɾ���ı��Ӧ��ӳ��������, ֧�ֳ�����
	 * @param po: �������
	 * @return
	 */
	public static int deleteObjects(String clazz, Object po) {
		return getBaseService().deleteObjects(clazz, po);
	}

	public static void flush() {
		getBaseService().flush();
	}

	public static void evict(Object po) {
		getBaseService().evict(po);
	}

	/**
	 * �����������, һ���������ά�������
	 * batch process (add/update/delete) objects:
	 * @param opos: old objects before save
	 * @param npos: new objects 
	 * @param params: @class: object class name, @order: order by
	 */
	public static List saveObjects(Object[] opos, Object[] npos, Map params) {
		return getBaseService().saveObjects(opos, npos, params);
	}

	/**
	 * ����ҵ��ƾ֤, ��������ά������ƾ֤��ϸ��ƾ֤��(1�ű�)���ƾ֤��ϸ��ƾ֤��(2�ű�)
	 * @param header: ԭƾ֤ͷ
	 * @param opos: ԭƾ֤��ϸ
	 * @param nheader: ��ƾ֤ͷ
	 * @param npos: ��ƾ֤��ϸ
	 * @param params: ��������:
	 * 		-@clear: �Ƿ��ڱ���ƾ֤֮ǰ�����ƾ֤(ɾ������ƾ֤��ϸ), ȱʡΪtrue; ���ƾ֤��ϸ������(������������Ϊ���), �˴���Ҫָ��Ϊfalse.
	 * 		-@class: ƾ֤��ϸ��ӳ������, ֧�ֳ�����
	 * 		-@no: ƾ֤��ŵ��ֶ���, ��ChkPlan��"no"
	 * 		-@generator: ƾ֤��ŵ�����������, ����ʵ��com.gc.NoGenerator�ӿ�
	 */
	public static Object saveVoucher(Object header, Object[] opos, Object nheader, Object[] npos, Map params) {
		return getBaseService().saveVoucher(header, opos, nheader, npos, params);
	}

	/**
	 * �������ݵ�Excel�ļ�
	 * @param data: ����(��ά��������)
	 * @param headers: ��ͷ(��������, Map�����Ժ�汾֧��)
	 * @param formats: ��ʽ(�Ժ�汾)
	 * @param response: HttpServletResponse����, ���excel�ļ���������
	 */
	public void export(Object[] data, Object headers, Object formats, HttpServletResponse response) {
		getBaseService().export(data, headers, formats, response);
	}

	/**
	 * ����ϵͳ����
	 * @return
	 */
	public Map getSettings() {
		return Constants.SETTINGS;
	}

//==================================== Branch ====================================

	public static Branch getBranch(Integer id) {
		return getBaseService().getBranch(id);
	}

	public static List<Branch> getBranches() {
		return getBaseService().getBranches();
	}

//==================================== Department ====================================

	public static Department getDepartment(Integer id) {
		return getBaseService().getDepartment(id);
	}

	/**
	 * ���ز����б�
	 * @return
	 */
	public static List<Department> getDepartments(Integer branchId) {
		return getBaseService().getDepartments(branchId);
	}

	public static List<Department> getDepartmentsAndOffices(Integer branchId, Integer departId) {
		return getBaseService().getDepartmentsAndOffices(branchId, departId);
	}

	public static List<Department> getDepartmentsAndOLEs(Integer branchId, Integer departId) {
		return getBaseService().getDepartmentsAndOLEs(branchId, departId);
	}

//==================================== Office ====================================

	public static Office getOffice(Integer id) {
		return getBaseService().getOffice(id);
	}

//==================================== SecurityGroup ====================================

	public static SecurityGroup getSecurityGroup(Integer id) {
		return getBaseService().getSecurityGroup(id);
	}

//==================================== Equipement ====================================

	public static List<Equipment> getEquipmentsByBranchId(Integer branchId) {
		return getBaseService().getEquipmentsByBranchId(branchId);
	}

//==================================== Line ====================================
	public static List<Line> getLines(Integer branchId) {
		return getBaseService().getLines(branchId);
	}

	/**
	 * ����Ͷ����Ч���ڵ�Lines
	 * @param branchId
	 * @param onDate
	 * @param downDate
	 * @return
	 */
	public static List<Line> getLinesByBD(Integer branchId,Calendar onDate,Calendar downDate) {
		return getBaseService().getLinesByBD(branchId, onDate, downDate);
	}

//==================================== EquType ====================================
	public static List<EquType> getEquTypes() {
		return getBaseService().getEquTypes();
	}
	
//==================================== EquOnline ====================================
	public static List<EquOnline> getEquOnlines(Integer branchId, Integer departId) {
		return getBaseService().getEquOnlines(branchId, departId);
	}
	
	public static List<EquOnline> getEquOnlineList(Integer branchId, Calendar accDate, Integer departId) {
		return getBaseService().getEquOnlineList(branchId, accDate, departId);
	}

	/**
	 * ����Ͷ����Ч���ڵ�EquOnlines
	 * @param branchId
	 * @param onDate
	 * @param downDate
	 * @return
	 */
	public static List<EquOnline> getEquOnlinesByBD(Integer branchId, Calendar onDate, Calendar downDate) {
		return getBaseService().getEquOnlinesByBD(branchId, onDate, downDate);
	}
	
	/**
	 * ��ȫ����
	 * @param limit
	 * @param orderColumns
	 * @param dateFrom ��ѯ��ʼ����
	 * @return
	 */
	@Deprecated
	public static List<EquOnline> getDeptsLinesBusesForSafetyTree(SecurityLimit limit,String[] orderColumns,Calendar dateFrom) {
		return getBaseService().getDeptsLinesBusesForSafetyTree(limit, orderColumns, dateFrom);
	}
	
	/**
	 * ��ȫ��ҳ�������������(����,��·,����)
	 * @param limit Ȩ��
	 * @param dateFrom ��ʼ����
	 * @param order ��������
	 * @return ��ʼ���ں�Ĳ��š���·�������ĵ�����Ϣ
	 */
	public static List<EquOnline> getEquOnlinesForSafetyTree(SecurityLimit limit, Calendar dateFrom, String[] order) {
		return getBaseService().getEquOnlinesForSafetyTree(limit, dateFrom, order);
	}
	
	public static List<EquOnline> getEquOnlineLasts(Integer branchId) {
		return getBaseService().getEquOnlineLasts(branchId);
	}
//==================================== Weather ====================================

	public static List<Weather> getWeathers(Integer branchId) {
		return getBaseService().getWeathers(branchId);
	}

}
