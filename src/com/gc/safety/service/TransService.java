package com.gc.safety.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import com.gc.Constants;
import com.gc.common.po.SecurityLimit;
import com.gc.exception.CommonRuntimeException;
import com.gc.safety.dao.TransDAOHibernate;
import com.gc.safety.po.TransInfo;
import com.gc.safety.po.TransType;
import com.gc.util.CommonUtil;
import com.gc.util.FlexUtil;

class TransService {
	
	private TransDAOHibernate transDAO;
	
	public void setTransDAO(TransDAOHibernate transDAO) {
		this.transDAO = transDAO;
	}
	
	public void flush() {
		transDAO.flush();
	}

//==================================== TransInfo ====================================

	public List<TransInfo> getCurrentTransInfo(Integer branchId) {
		return transDAO.getTransInfos(branchId);
	}

	public void addTransInfo(TransInfo transInfo) {
		transDAO.addTransInfo(transInfo);
	}
	
	public String saveTransInfos(List<TransInfo> oldLists, List<TransInfo> lists) {
		if (lists.size() <= 0 && oldLists.size() <= 0) return null;
		Calendar sysdate = Calendar.getInstance();
		String accNo;
		if (oldLists != null) {
			accNo = oldLists.get(0).getId().getAccNo();
			// 修改凭证先删除老凭证
			Iterator<TransInfo> it = oldLists.iterator();
			while (it.hasNext()) {
				transDAO.deleteTransInfo(it.next());
			}
		} else {
			Integer branchId = lists.get(0).getId().getBranchId();
//			String accHead = 'A' + String.valueOf(sysdate.get(1)).substring(2) + '-';
//			accNo = CommonServiceUtil.getSafetyAccNo(branchId, 'A', accHead);
			accNo = CommonServiceUtil.getTransGressNo(branchId, sysdate);
		}
		Iterator<TransInfo> it = lists.iterator();
		TransInfo transInfo;
		while (it.hasNext()) {
			transInfo = it.next();
			transInfo.getId().setAccNo(accNo);
			transDAO.addTransInfo(transInfo);
		}
		return accNo;
	}
	
	/**
	 * 违章修改保存
	 * @param transInfo
	 */
	protected String saveTransInfos2(Object[] opos, Object[] npos, Integer departId, Calendar closeDate) {
		String accNo = ((TransInfo)opos[0]).getId().getAccNo();
		Integer branchId = ((TransInfo)opos[0]).getBranchId();
	String clazz = "TransInfo";
	Arrays.sort(opos, Constants.ID_COMPARATOR);
	Arrays.sort(npos, Constants.ID_COMPARATOR);
	List pos = null;
	if(departId != -1)
		pos = TransServiceUtil.getTransListByDeptCloseD(branchId, departId, closeDate);
	else
		pos = TransServiceUtil.getTransInfosForModify(branchId, accNo, null, null, closeDate);
	Collections.sort(pos, Constants.ID_COMPARATOR);
	String error;
	if (pos.size() != opos.length || !(CommonUtil.equals(pos.toArray(), opos))) {
		error = CommonUtil.getString("error.dirty.data", new Object[]{clazz});
		throw new CommonRuntimeException(error);
	}
	opos = pos.toArray();
	for (int k = 0; k < opos.length; k++) {
		deleteTransInfo((TransInfo)opos[k]);
	}
	flush();
	for (int k = 0; k < npos.length; k ++) {
		addTransInfo((TransInfo)npos[k]);
	}
	return accNo;
}
	
	// 老程序,待删除
	public void saveTransInfo(TransInfo transInfo) {
		transDAO.saveTransInfo(transInfo);
	}
	
	public void updateTransInfo(TransInfo transInfo) {
		transDAO.updateTransInfo(transInfo);
	}

	@SuppressWarnings("unchecked")
	public void uploadTransInfo(File[] files, HttpServletResponse response) {
		response.setContentType("text/plain");
		List list = new ArrayList();
		Workbook workbook = null;
		try {
			if (files == null || files.length <= 0) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("ERROR")));
				response.getWriter().flush();
				return;
			}
			File f = files[0];
			String cellValueString;
			Double cellValueNumber;
			Date cellValueDate;
			workbook = Workbook.getWorkbook(f);
			if (workbook == null) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("ERROR")));
				response.getWriter().flush();
				return;
			}
			Sheet[] sheets = workbook.getSheets();
			Sheet sheet;
			if (sheets != null && sheets.length > 0) {
				// 对每个工作表进行循环
				for (int i = 0; i < sheets.length; i++) {
					sheet = sheets[i];
					// 得到当前工作表的行数
					int rowNum = sheet.getRows();
					for (int j = 0; j < rowNum; j++) {
						// 得到当前行的所有单元格
						Cell[] cells = sheet.getRow(j);
						if (cells != null & cells.length > 0) {
							// 对每个单元格进行循环
							for (int k = 0; k < cells.length; k++) {
								// 读取当前单元格的值
								if (cells[k].getType() == CellType.LABEL) {
									LabelCell labelCell = (LabelCell) cells[k];
									cellValueString = labelCell.getString();
									list.add(cellValueString);
								} else if (cells[k].getType() == CellType.NUMBER) {
									NumberCell numberCell = (NumberCell) cells[k];
									cellValueNumber = numberCell.getValue();
									list.add(cellValueNumber);
								} else if (cells[k].getType() == CellType.DATE) {
									DateCell dateCell = (DateCell) cells[k];
									cellValueDate = dateCell.getDate();
									SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
									String dateString = sdf.format(
											cellValueDate).toString();
									list.add(dateString);
								} else if (cells[k].getType() == CellType.EMPTY) {
									list.add("");
								}
							}
						}
					}
				}
			}
			response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject(list)));
			response.getWriter().flush();
		} catch (Throwable t) {
			try {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("ERROR")));
				response.getWriter().flush();
			} catch (Throwable t1) {}
			throw new CommonRuntimeException("Error", t);
		} finally {
			if (workbook != null) workbook.close();
		}
	}

	public void deleteTransInfo(TransInfo transInfo) {
		transDAO.deleteTransInfo(transInfo);
	}
	
	@SuppressWarnings("unchecked")
	public List getTransInfoList(Map obj) {
		return transDAO.getTransInfoList(obj);
	}
	
	public List<TransInfo> getTransInfosForModify(Integer branchId, String accNo, String doDate, String dealDate, Calendar closeDate) {
		return transDAO.getTransInfosForModify(branchId, accNo, doDate, dealDate, closeDate);
	}

	//单车违章查询
	public List<TransInfo> getTransByUIdOrWId(Integer branchId, String useId, String workerId, Calendar dateFrom, Calendar dateTo) {
		return transDAO.getTransByUIdOrWId(branchId, useId, workerId, dateFrom, dateTo);
	}
	
	//违章处理、部门、结账日期 查询
	public List<TransInfo> getTransListByDeptCloseD(Integer branchId,Integer departId,Calendar closeDate) {
		return transDAO.getTransListByDeptCloseD(branchId, departId, closeDate);
	}
	
	/**
	 * 违章所有条件查询
	 * @param obj
	 * @return
	 */
	public List getTransListByAll(SecurityLimit limit, Map qo)
	{
		return transDAO.getTransListByAll(limit, qo);
	}
//==================================== TransType ====================================

	public List<TransType> getTransTypes(Integer branchId) {
		return transDAO.getTransTypes(branchId);
	}
	
	public List<TransType> getTransTypeByName(Integer branchId, String name) {
		return transDAO.getTransTypeByName(branchId, name);
	}
	
	public List getTransInfoForSafetyTree(SecurityLimit limit, Calendar dateFrom) {
		return transDAO.getTransInfoForSafetyTree(limit, dateFrom);
	}
	
	public List<TransType> getTransType1(Integer branchId) {
		return transDAO.getTransType1(branchId);
	}
	
	public List<TransType> getTransType2(Integer branchId) {
		return transDAO.getTransType2(branchId);
	}
	
	public void saveTransType(TransType transType) {
		if (transType.getId() == 0) {
			transDAO.addTransType(transType);
		} else {
			transDAO.saveTransType(transType);
		}
	}
	
	public void addTransType(TransType transType) {
		transDAO.addTransType(transType);
	}
	
	public void updateTransType(TransType transType) {
		transDAO.updateTransType(transType);
	}
	
	public void deleteTransType(TransType transType) {
		transDAO.deleteTransType(transType);
	}

}
