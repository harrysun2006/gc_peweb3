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
	 * 返回clazz映射类的主键名
	 * @param clazz: 映射类的类名, 支持长名(com.gc.hr.po.People)和短名(People)
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
	 * 返回查询结果列表
	 * @param clazz: 映射类的类名, 支持长名(com.gc.hr.po.People)和短名(People)
	 * @param params: 排序及查询条件参数, 如: @order="id", @branch.id=2
	 * @return
	 */
	public static List getObjects(String clazz, Map params) {
		return getObjects(clazz, params, false);
	}

	public static List getObjects(Class clazz, Map params, boolean lock) {
		return getObjects(clazz.getName(), params, lock);
	}

	/**
	 * 返回查询结果列表
	 * @param clazz: 映射类的类名, 支持长名(com.gc.hr.po.People)和短名(People)
	 * @param params: 排序及查询条件参数, 如: @order="id", @branch.id=2
	 * @param lock: 是否锁定返回结果记录集(SELECT ... FOR UPGRADE)
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
	 * 使用外键对象批量删除
	 * @param clazz: 需要删除的表对应的映射类名称, 支持长短名
	 * @param po: 外键对象
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
	 * 保存对象数组, 一般可以用来维护代码表
	 * batch process (add/update/delete) objects:
	 * @param opos: old objects before save
	 * @param npos: new objects 
	 * @param params: @class: object class name, @order: order by
	 */
	public static List saveObjects(Object[] opos, Object[] npos, Map params) {
		return getBaseService().saveObjects(opos, npos, params);
	}

	/**
	 * 保存业务凭证, 可以用来维护不带凭证明细的凭证表(1张表)或带凭证明细的凭证表(2张表)
	 * @param header: 原凭证头
	 * @param opos: 原凭证明细
	 * @param nheader: 新凭证头
	 * @param npos: 新凭证明细
	 * @param params: 其他参数:
	 * 		-@clear: 是否在保存凭证之前先清空凭证(删除所有凭证明细), 缺省为true; 如果凭证明细被引用(被其它表引用为外键), 此处需要指定为false.
	 * 		-@class: 凭证明细的映射类名, 支持长短名
	 * 		-@no: 凭证编号的字段名, 如ChkPlan的"no"
	 * 		-@generator: 凭证编号的生成类类名, 必须实现com.gc.NoGenerator接口
	 */
	public static Object saveVoucher(Object header, Object[] opos, Object nheader, Object[] npos, Map params) {
		return getBaseService().saveVoucher(header, opos, nheader, npos, params);
	}

	/**
	 * 导出数据到Excel文件
	 * @param data: 数据(二维对象数组)
	 * @param headers: 表头(对象数组, Map对象以后版本支持)
	 * @param formats: 格式(以后版本)
	 * @param response: HttpServletResponse对象, 输出excel文件到下载流
	 */
	public void export(Object[] data, Object headers, Object formats, HttpServletResponse response) {
		getBaseService().export(data, headers, formats, response);
	}

	/**
	 * 返回系统设置
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
	 * 返回部门列表
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
	 * 返回投保有效期内的Lines
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
	 * 返回投保有效期内的EquOnlines
	 * @param branchId
	 * @param onDate
	 * @param downDate
	 * @return
	 */
	public static List<EquOnline> getEquOnlinesByBD(Integer branchId, Calendar onDate, Calendar downDate) {
		return getBaseService().getEquOnlinesByBD(branchId, onDate, downDate);
	}
	
	/**
	 * 安全树用
	 * @param limit
	 * @param orderColumns
	 * @param dateFrom 查询起始日期
	 * @return
	 */
	@Deprecated
	public static List<EquOnline> getDeptsLinesBusesForSafetyTree(SecurityLimit limit,String[] orderColumns,Calendar dateFrom) {
		return getBaseService().getDeptsLinesBusesForSafetyTree(limit, orderColumns, dateFrom);
	}
	
	/**
	 * 安全主页面的树基础数据(部门,线路,车辆)
	 * @param limit 权限
	 * @param dateFrom 起始日期
	 * @param order 排序数组
	 * @return 起始日期后的部门、线路及车辆的调动信息
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
