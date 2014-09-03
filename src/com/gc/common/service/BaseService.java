package com.gc.common.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.BeanUtils;

import com.gc.Constants;
import com.gc.Predecessor;
import com.gc.common.dao.BaseDAOHibernate;
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
import com.gc.exception.CommonRuntimeException;
import com.gc.util.CommonUtil;

class BaseService {

	private BaseDAOHibernate baseDAO;

	public void setBaseDAO(BaseDAOHibernate baseDAO) {
		this.baseDAO = baseDAO;
	}

	public String getIdentifierName(String clazz) {
		return baseDAO.getIdentifierName(clazz);
	}

	public Object getIdentifierValue(Object po) {
		if (po == null) return null;
		String clazz = po.getClass().getName();
		String id = getIdentifierName(clazz);
		try {
			Field f = po.getClass().getDeclaredField(id);
			f.setAccessible(true);
			return f.get(po);
		} catch (Exception e) {
		}
		return null;
	}

	public String getPropertyName(String clazz, Object po) {
		return baseDAO.getPropertyName(clazz, po);
	}

	public int addObject(Object po) {
		return baseDAO.addObject(po);
	}

	public int deleteObject(Object po) {
		return baseDAO.deleteObject(po);
	}

	public Object getObject(Class clazz, Serializable id) {
		return baseDAO.getObject(clazz, id);
	}

	public int saveObject(Object po) {
		Object id = getIdentifierValue(po);
		if (id == null || id.equals("") || id.equals(0) || id.equals(0L)) return addObject(po);
		else return updateObject(po);
	}

	public int updateObject(Object po) {
		return baseDAO.updateObject(po);
	}

	public void flush() {
		baseDAO.flush();
	}

	public void evict(Object po) {
		baseDAO.evict(po);
	}

	public List getObjects(String clazz, Map params, boolean lock) {
		return baseDAO.getObjects(clazz, params, lock);
	}

	public int addObjects(Object[] pos) {
		if (pos == null) return 0;
		for (int i = 0; i < pos.length; i++) {
			addObject(pos[i]);
		}
		return pos.length;
	}

	public int deleteObjects(Object[] pos) {
		if (pos == null) return 0;
		for (int i = 0; i < pos.length; i++) {
			deleteObject(pos[i]);
		}
		return pos.length;
	}

	public int deleteObjects(String clazz, Object po) {
		return baseDAO.deleteObjects(clazz, po);
	}

	/**
	 * batch process (add/update/delete) objects:
	 * @param opos: old objects before save
	 * @param npos: new objects 
	 * @param params: @class: object class name, @order: order by
	 */
	public List saveObjects(Object[] opos, Object[] npos, Map params) {
		return saveObjects2(opos, npos, params);
	}

	/**
	 * batch process (add/update/delete) objects:
	 * 1. sort old/new objects by id
	 * 2. get objects from database and LOCK(lock update/delete on this records in table) these objects
	 * 3. sort db list by id
	 * 4. compare the db and old objects, if not equal then throw error and return
	 * 5. compare and process (add/update/delete) each item in old & new objects array 
	 * @param opos: old objects before save
	 * @param npos: new objects 
	 * @param params: @class: object class name, @order: order by
	 * @deprecated
	 */
	protected void saveObjects1(Object[] opos, Object[] npos, Map params) {
		if (opos == null || npos == null) return;
		String clazz = (String) params.get(Constants.PARAM_CLASS);
		Arrays.sort(opos, Constants.ID_COMPARATOR);
		Arrays.sort(npos, Constants.ID_COMPARATOR);
		List pos = baseDAO.getObjects(clazz, params, true);
		Collections.sort(pos, Constants.ID_COMPARATOR);
		String error;
		if (pos.size() != opos.length || !(CommonUtil.equals(pos.toArray(), opos))) {
			error = CommonUtil.getString("error.dirty.data", new Object[]{clazz});
			throw new CommonRuntimeException(error);
		}
		opos = pos.toArray();
		int i = 0, j = 0, r;
		while (i < opos.length && j < npos.length) {
			r = Constants.ID_COMPARATOR.compare(opos[i], npos[j]);
			if (r < 0) deleteObject(opos[i++]);
			else if (r == 0) {
				BeanUtils.copyProperties(npos[j], opos[i]);
				updateObject(opos[i]);
				i++;
				j++;
			}
			else addObject(npos[j++]);
		}
		while (i < opos.length) deleteObject(opos[i++]);
		while (j < npos.length) addObject(npos[j++]);
	}

	/**
	 * batch process (add/update/delete) objects:
	 * 1. sort old/new objects by id
	 * 2. create 3 arrays indicate process operation (add/update/delete) for each item in old & new objects
	 * 3. get objects from database and LOCK(lock update/delete on this records in table) these objects
	 * 4. sort db list by id
	 * 5. compare the db and old objects, if not equal then throw error and return
	 * 6. use the indication arrays to process each item in old/new objects array
	 * 7. process order: delete-->update-->add
	 * @param opos: old objects before save
	 * @param npos: new objects 
	 * @param params: @class: object class name, @order: order by
	 */
	protected List saveObjects2(Object[] opos, Object[] npos, Map params) {
		if (opos == null || npos == null) return null;
		List data = new ArrayList();
		Collections.addAll(data, npos);
		String clazz = (String) params.get(Constants.PARAM_CLASS);
		if (clazz == null) clazz = opos[0].getClass().getName();
		String[] id = {getIdentifierName(clazz)};
		Arrays.sort(opos, Constants.ID_COMPARATOR);
		Arrays.sort(npos, Constants.ID_COMPARATOR);
		int[] a = new int[npos.length];
		int[] d = new int[opos.length];
		int[] uo = new int[npos.length];
		int[] un = new int[npos.length];
		int i = 0, j = 0, ka = 0, kd = 0, ku = 0, r;
		while (i < opos.length && j < npos.length) {
			r = Constants.ID_COMPARATOR.compare(opos[i], npos[j]);
			if (r < 0) d[kd++] = i++;
			else if (r == 0) {
				uo[ku] = i++;
				un[ku] = j++;
				ku++;
			} else a[ka++] = j++;
		}
		while (i < opos.length) d[kd++] = i++;
		while (j < npos.length) a[ka++] = j++;
		while (kd < opos.length) d[kd++] = -1;
		while (ka < npos.length) a[ka++] = -1;
		while (ku < npos.length) {
			uo[ku] = un[ku] = -1;
			ku++;
		}
		List pos = getObjects(clazz, params, true);
		Collections.sort(pos, Constants.ID_COMPARATOR);
		String error;
		if (pos.size() != opos.length || !(CommonUtil.equals(pos.toArray(), opos))) {
			error = CommonUtil.getString("error.dirty.data", new Object[]{clazz});
			throw new CommonRuntimeException(error);
		}
		opos = pos.toArray();
		for (int k = 0; k < d.length; k++) {
			if (d[k] < 0) break;
			deleteObject(opos[d[k]]);
		}
		flush();
		for (int k = 0; k < uo.length && k < un.length; k++) {
			if (uo[k] < 0 || un[k] < 0) break;
			// 此处需要注意: Hibernate中主键不能更改, 复合主键更改后会抛异常
			BeanUtils.copyProperties(npos[un[k]], opos[uo[k]], id);
			updateObject(opos[uo[k]]);
		}
		flush();
		for (int k = 0; k < a.length; k++) {
			if (a[k] < 0) break;
			addObject(npos[a[k]]);
		}
		return data;
	}

	private Object getInstance(String name) throws CommonRuntimeException {
		try {
			Class clazz = Class.forName(name);
			return clazz.newInstance();
		} catch (Throwable t) {
			throw new CommonRuntimeException(t.getMessage(), t);
		}
	}

	public Object saveVoucher(Object header, Object[] opos, Object nheader, Object[] npos, Map params) {
		Boolean clear = (Boolean) params.get(Constants.PARAM_CLEAR);
		String clazz = (String) params.get(Constants.PARAM_CLASS);
		String pno = (String) params.get(Constants.PARAM_NO);
		String pre = (String) params.get(Constants.PARAM_PREDECESSOR);
		// 前置处理器实例
		Predecessor predecessor = (pre == null) ? null : (Predecessor) getInstance(pre);
		// 凭证在凭证明细映射类中的属性名, 如ChkPlanD的"id.plan"
		String name = getPropertyName(clazz, nheader);
		if (clear == null) clear = true;
		if (clazz == null) clazz = npos[0].getClass().getName();
		Object value = CommonUtil.getValue(nheader, pno);
		if (predecessor != null) predecessor.process(nheader, params);
		if (value == null || value.equals(0)) addObject(nheader);
		else updateObject(nheader);
		if (clear && header != null) {
			deleteObjects(clazz, header);
		} else {
		}
		for (int i = 0; i < npos.length; i++) {
			CommonUtil.setValue(npos[i], name, nheader);
			addObject(npos[i]);
		}
		if (npos.length == 0) {
			deleteObject(nheader);
			return null;
		}
		return nheader;
	}

	private int writeRow(WritableSheet ws, Object data, int col, int row, CellFormat format) throws Exception
	{
		int j;
		int ret = row + 1;		// 返回已经处理到的行
		if (data instanceof Object[]) {
			Object[] values = (Object[]) data;
			for (j = 0; j < values.length; j++, col++) {
				if (values[j] instanceof Map) {
					Map map = (Map) values[j];
					int colspan = map.containsKey("colspan") ? ((Integer) map.get("colspan")).intValue() : 1;
					int rowspan = map.containsKey("rowspan") ? ((Integer) map.get("rowspan")).intValue() : 1;
					Object text = map.containsKey("text") ? map.get("text") : "";
					Object children = map.containsKey("children") ? map.get("children") : null;
					ws.addCell(new Label(col, row, text == null ? "" : text.toString(), format));
					ws.mergeCells(col, row, col + colspan - 1, row + rowspan - 1);
					if (ret < row + rowspan) ret = row + rowspan;
					if (children != null) {
						int k = writeRow(ws, children, col, row + rowspan, format);
						if (ret < k) ret = k;
						col = col + colspan - 1;
					}
				} else {					
					ws.addCell(new Label(j, row, (values[j] == null) ? "" : values[j].toString(), format));
				}
			}
		} else if (data instanceof String[]) {
			String[] ss = (String[]) data;
			for (j = 0; j < ss.length; j++) {
				ws.addCell(new Label(j, 0, (ss[j] == null) ? "" : ss[j], format));
			}
		} else if (data instanceof Map) {
			Map m = (Map) data;
			Object[] values = m.values().toArray();
			for (j = 0; j < values.length; j++) {
				ws.addCell(new Label(j, 0, (values[j] == null) ? "" : values[j].toString(), format));
			}
		}
		return ret;
	}

	public void export(Object[] data, Object headers, Object formats, HttpServletResponse response) {
		try {
			int i;
			WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			if (data == null) data = new Object[]{};
			WritableFont normal= new WritableFont(WritableFont.ARIAL, 12);
			WritableFont bold = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			WritableCellFormat f1 = new WritableCellFormat(normal);
			WritableCellFormat f2 = new WritableCellFormat(bold);
			f2.setAlignment(Alignment.CENTRE);
			f2.setVerticalAlignment(VerticalAlignment.CENTRE);
			int row = writeRow(ws, headers, 0, 0, f2);
			for (i = 0; i < data.length; i++) {
				writeRow(ws, data[i], 0, i + row, f1);
			}
			wwb.write();
			wwb.close();
			response.setContentType("application/vnd.ms-excel");
			response.flushBuffer();
		} catch (Exception e) {
			throw new CommonRuntimeException(CommonUtil.getString("error.export.list"), e);
		}
	}
	
//==================================== Branch ====================================

	public Branch getBranch(Integer id) {
		return baseDAO.getBranch(id);
	}

	public List<Branch> getBranches() {
		return baseDAO.getBranches();
	}

//==================================== Department ====================================

	public Department getDepartment(Integer id) {
		return baseDAO.getDepartment(id);
	}

	/**
	 * 返回部门列表
	 * @return
	 */
	public List<Department> getDepartments(Integer branchId) {
		return baseDAO.getDepartments(branchId);
	}

	public List<Department> getDepartmentsAndOffices(Integer branchId, Integer departId) {
		return baseDAO.getDepartmentsAndOffices(branchId, departId);
	}

	public List<Department> getDepartmentsAndOLEs(Integer branchId, Integer departId) {
		return baseDAO.getDepartmentsAndOLEs(branchId, departId);
	}

//==================================== Office ====================================

	public Office getOffice(Integer id) {
		return baseDAO.getOffice(id);
	}

//==================================== SecurityGroup ====================================

	public SecurityGroup getSecurityGroup(Integer id) {
		return baseDAO.getSecurityGroup(id);
	}

//==================================== Equipment ====================================

	public List<Equipment> getEquipmentsByBranchId(Integer branchId) {
		return baseDAO.getEquipmentsByBranchId(branchId);
	}

//==================================== Line ====================================

	public List<Line> getLines(Integer branchId) {
		return baseDAO.getLines(branchId);
	}

	public List<Line> getLinesByBD(Integer branchId,Calendar onDate,Calendar downDate) {
		return baseDAO.getLinesByBD(branchId, onDate, downDate);
	}

//==================================== EquType ====================================

	public List<EquType> getEquTypes() {
		return baseDAO.getEquTypes();
	}
	
//==================================== EquOnline ====================================

	public List<EquOnline> getEquOnlines(Integer branchId, Integer departId) {
		return baseDAO.getEquOnlines(branchId, departId);
	}
	
	public List<EquOnline> getEquOnlineList(Integer branchId,Calendar accDate,Integer departId) {
		return baseDAO.getEquOnlineList(branchId,accDate,departId);
	}

	public List<EquOnline> getEquOnlinesByBD(Integer branchId, Calendar onDate, Calendar downDate) {
		return baseDAO.getEquOnlinesByBD(branchId, onDate, downDate);
	}
	
	@Deprecated
	public List<EquOnline> getDeptsLinesBusesForSafetyTree(SecurityLimit limit,String[] orderColumns,Calendar dateFrom) {
		return baseDAO.getDeptsLinesBusesForSafetyTree(limit, orderColumns,dateFrom);
	}
	
	public List<EquOnline> getEquOnlinesForSafetyTree(SecurityLimit limit, Calendar dateFrom, String[] order) {
		return baseDAO.getEquOnlinesForSafetyTree(limit, dateFrom, order);
	}
	
	public List<EquOnline> getEquOnlineLasts(Integer branchId) {
		return baseDAO.getEquOnlineLasts(branchId);
	}
//==================================== Weather ====================================

	public List<Weather> getWeathers(Integer branchId) {
		return baseDAO.getWeathers(branchId);
	}

}
