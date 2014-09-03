package com.gc.safety.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.gc.Constants;
import com.gc.exception.CommonRuntimeException;
import com.gc.safety.dao.ClaimsDAOHibernate;
import com.gc.safety.po.AccInPsnGua;
import com.gc.safety.po.AccInPsnGuaPay;
import com.gc.safety.po.AccOutGua;
import com.gc.safety.po.AccOutGuaPay;
import com.gc.safety.po.Insurer;
import com.gc.util.CommonUtil;

class ClaimsService {

	private ClaimsDAOHibernate claimsDAO;

	public void setClaimsDAO(ClaimsDAOHibernate claimsDAO) {
		this.claimsDAO = claimsDAO;
	}

//==================================== Claims ====================================
	public Integer getOutAndInGuaByAccId(Integer branchId,Integer accId) {
		return claimsDAO.getOutAndInGuaByAccId(branchId, accId);
	}
	
	public String saveAccOutGua(Object[] oldList, Object[] newList, Integer branchId) {
		if (oldList == null || oldList.length == 0)
			return saveAccOut1(newList, branchId);
		else
			return saveAccOut2(oldList, newList, branchId);
	}
	
	/**
	 * 新建三责理赔凭证
	 * @param npos
	 * @param branchId
	 * @return
	 */
	protected String saveAccOut1(Object[] npos, Integer branchId) {
//		String accHead = 'C' + String.valueOf(Calendar.getInstance().get(1)).substring(2) + '-';
//		String accNo = CommonServiceUtil.getSafetyAccNo(branchId, "C", accHead);
		String accNo = CommonServiceUtil.getGuaNo(branchId, Calendar.getInstance());
		for (int i = 0; i < npos.length; i++) {
			AccOutGua b = (AccOutGua) npos[i];
			b.getId().setRefNo(accNo);
			addObject(b);
		}
		return accNo;
	}
	
	/**
	 * 修改客伤理赔凭证
	 * @param opos
	 * @param npos
	 * @param branchId
	 * @return
	 */
	protected String saveAccOut2(Object[] opos, Object[] npos, Integer branchId) {
//		if (npos == null) return null;
		String refNo = ((AccOutGua) opos[0]).getId().getRefNo();
		String clazz = "AccOutGua";
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
		List pos = claimsDAO.getAccOutGuaByRefNo(branchId, refNo, true);
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
		for (int k = 0; k < uo.length && k < un.length; k++) {
			if (uo[k] < 0 || un[k] < 0) break;
			BeanUtils.copyProperties(npos[un[k]], opos[uo[k]], new String[]{"id"});
			updateObject(opos[uo[k]]);
		}
		flush();
		for (int k = 0; k < a.length; k++) {
			if (a[k] < 0) break;
			addObject(npos[a[k]]);
		}
		
		return refNo;
	}
	
	public void deleteObject(Object po) {
		claimsDAO.deleteObject(po);
	}
	
	public void updateObject(Object po) {
		claimsDAO.updateObject(po);
	}
	
	public void addObject(Object po) {
		claimsDAO.addObject(po);
	}
	
	public void flush() {
		claimsDAO.flush();
	}
	
	/**
	 * 保存客伤理赔凭证
	 * @param oldList
	 * @param newList
	 * @param branchId
	 * @return
	 */
	public String saveAccInPsnGua(Object[] oldList, Object[] newList, Integer branchId) {
		if (oldList == null || oldList.length == 0)
			return saveAccInPsnGua1(newList, branchId);
		else
			return saveAccInPsnGua2(oldList, newList, branchId);
	}
	
	/**
	 * 新建客伤理赔凭证
	 * @param newList
	 * @param branchId
	 * @return
	 */
	protected String saveAccInPsnGua1(Object[] newList, Integer branchId) {
//		String accHead = 'C' + String.valueOf(Calendar.getInstance().get(1)).substring(2) + '-';
//		String accNo = CommonServiceUtil.getSafetyAccNo(branchId, "C", accHead);
		String accNo = CommonServiceUtil.getGuaNo(branchId, Calendar.getInstance());
		for (int i = 0; i < newList.length; i ++) {
			AccInPsnGua po = (AccInPsnGua) newList[i];
			po.getId().setRefNo(accNo);
			addObject(po);
		}
		return accNo;
	}
	
	/**
	 * 修改客伤理赔凭证
	 * @param opos
	 * @param npos
	 * @param branchId
	 * @return
	 */
	protected String saveAccInPsnGua2(Object[] opos, Object[] npos, Integer branchId) {
//		if (npos == null) return null;
		String refNo = ((AccInPsnGua) opos[0]).getId().getRefNo();
		String clazz = "AccInPsnGua";
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
		List pos = claimsDAO.getAccInPsnGuaByRefNo(branchId, refNo, true);
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
		for (int k = 0; k < uo.length && k < un.length; k++) {
			if (uo[k] < 0 || un[k] < 0) break;
			BeanUtils.copyProperties(npos[un[k]], opos[uo[k]], new String[]{"id"});
			updateObject(opos[uo[k]]);
		}
		flush();
		for (int k = 0; k < a.length; k++) {
			if (a[k] < 0) break;
			addObject(npos[a[k]]);
		}
		return refNo;
	}

	/**
	 * 查询事故理赔凭证
	 * 没有赔付、没有结帐的理赔凭证 (凭证号、日期、险种、保险公司)
	 */
	public List getAccGuaForModify(Integer branchId) {
		List accOutGua = claimsDAO.getAccOutGuaForModify(branchId);
		List accInPsnGua = claimsDAO.getAccInPsnGuaForModify(branchId);
		List ret = new ArrayList();
		if (accInPsnGua != null) {
			for (int i = 0; i < accInPsnGua.size(); i ++) {
				ret.add(accInPsnGua.get(i));
			}
		}
		if (accOutGua != null) {
			for (int i = 0; i < accOutGua.size(); i ++) {
				ret.add(accOutGua.get(i));
			}
		}
		return ret;
	}
	
	/**
	 * 根据凭证号查询对应的明细,供判断数据是否已经被修改
	 * @param branchId
	 * @param refNo
	 * @param clazz 表名
	 * @return
	 */
	protected List getAccOutGuaByRefNo(Integer branchId, String refNo, boolean lock) {
		return claimsDAO.getAccOutGuaByRefNo(branchId, refNo, lock);
	}
	
	/**
	 * 查询当前机构所有理赔凭证及赔付情况
	 * @param branchId
	 * @return
	 */
	public List getAccGuaForQuery(Integer branchId) {
		List accOutGua = claimsDAO.getAccOutGuaForQuery(branchId);
		List accInGua = claimsDAO.getAccInGuaForQuery(branchId);
		List ret = new ArrayList();
		if (accInGua != null) {
			for (int i = 0; i < accInGua.size(); i ++) {
				ret.add(accInGua.get(i));
			}
		}
		if (accOutGua != null) {
			for (int i = 0; i < accOutGua.size(); i ++) {
				ret.add(accOutGua.get(i));
			}
		}
		return ret;
	}
	
	/**
	 * 查询当前机构所有赔付凭证
	 * @param branchId
	 * @return
	 */
	public List getAccPayForQuery(Integer branchId) {
		List accOutGuaPay = claimsDAO.getAccOutGuaPayForQuery(branchId);
		List accInPsnGuaPay = claimsDAO.getAccInGuaPayForQuery(branchId);
		List ret = new ArrayList();
		if (accInPsnGuaPay != null) {
			for (int i = 0; i < accInPsnGuaPay.size(); i++) {
				ret.add(accInPsnGuaPay.get(i));
			}
		}
		if (accOutGuaPay != null) {
			for (int i = 0; i < accOutGuaPay.size(); i++) {
				ret.add(accOutGuaPay.get(i));
			}
		}
		return ret;
	}
	
	/**
	 * 查询三责理赔凭证
	 * @param branchId
	 * @param insurer
	 * @return
	 */
	public List getAccOutGua(Integer branchId, Insurer insurer) {
		return claimsDAO.getAccOutGua(branchId, insurer);
	}
	
	/**
	 * 查询客伤理赔凭证
	 * @param branchId
	 * @param insurer
	 * @return
	 */
	public List getAccInPsnGua(Integer branchId, Insurer insurer) {
		return claimsDAO.getAccInPsnGua(branchId, insurer);
	}
	
	/**
	 * 保存三责赔付凭证并返回凭证号
	 * @param oldList
	 * @param newList
	 * @param branchId
	 * @return
	 */
	public String saveAccOutGuaPay(Object[] oldList, Object[] newList, Integer branchId) {
		if (oldList == null || oldList.length == 0)
			return saveAccOutPay1(newList, branchId);
		else
			return saveAccOutPay2(oldList, newList, branchId);
	}
	
	protected String saveAccOutPay1(Object[] npos, Integer branchId) {
//		String accHead = 'D' + String.valueOf(Calendar.getInstance().get(1)).substring(2) + '-';
//		String accNo = CommonServiceUtil.getSafetyAccNo(branchId, "D", accHead);
		String accNo = CommonServiceUtil.getPayNo(branchId, Calendar.getInstance());
		for (int i = 0; i < npos.length; i++) {
			AccOutGuaPay b = (AccOutGuaPay) npos[i];
			b.getId().setRefNo(accNo);
			addObject(b);
		}
		return accNo;
	}
	
	protected String saveAccOutPay2(Object[] opos, Object[] npos, Integer branchId) {
		String refNo = ((AccOutGuaPay) opos[0]).getId().getRefNo();
		String clazz = "AccOutGuaPay";
		Arrays.sort(opos, Constants.ID_COMPARATOR);
		Arrays.sort(npos, Constants.ID_COMPARATOR);
		
		List pos = claimsDAO.getAccOutGuaPayByRefNo(branchId, refNo, true);
		Collections.sort(pos, Constants.ID_COMPARATOR);
		String error;
		if (pos.size() != opos.length || !(CommonUtil.equals(pos.toArray(), opos))) {
			error = CommonUtil.getString("error.dirty.data", new Object[]{clazz});
			throw new CommonRuntimeException(error);
		}
		opos = pos.toArray();
		for (int k = 0; k < opos.length; k++) {
			deleteObject(opos[k]);
		}
		flush();
		for (int k = 0; k < npos.length; k ++) {
			addObject(npos[k]);
		}
		return refNo;
	}
	
	/**
	 * 保存客伤赔付凭证并返回凭证号
	 * @param oldList
	 * @param newList
	 * @param branchId
	 * @return
	 */
	public String saveAccInPsnGuaPay(Object[] oldList, Object[] newList, Integer branchId) {
		if (oldList == null || oldList.length == 0)
			return saveAccInPsnGuaPay1(newList, branchId);
		else
			return saveAccInPsnGuaPay2(oldList, newList, branchId);
	}
	
	protected String saveAccInPsnGuaPay1(Object[] newList, Integer branchId) {
//		String accHead = 'D' + String.valueOf(Calendar.getInstance().get(1)).substring(2) + '-';
//		String accNo = CommonServiceUtil.getSafetyAccNo(branchId, "D", accHead);
		String accNo = CommonServiceUtil.getPayNo(branchId, Calendar.getInstance());
		for (int i = 0; i < newList.length; i ++) {
			AccInPsnGuaPay po = (AccInPsnGuaPay) newList[i];
			po.getId().setRefNo(accNo);
			addObject(po);
		}
		return accNo;
	}
	
	protected String saveAccInPsnGuaPay2(Object[] opos, Object[] npos, Integer branchId) {
//		if (npos == null) return null;
		String refNo = ((AccInPsnGuaPay) opos[0]).getId().getRefNo();
		String clazz = "AccInPsnGuaPay";
		Arrays.sort(opos, Constants.ID_COMPARATOR);
		Arrays.sort(npos, Constants.ID_COMPARATOR);
		
		List pos = claimsDAO.getAccInPsnGuaPayByRefNo(branchId, refNo, true);
		Collections.sort(pos, Constants.ID_COMPARATOR);
		String error;
		if (pos.size() != opos.length || !(CommonUtil.equals(pos.toArray(), opos))) {
			error = CommonUtil.getString("error.dirty.data", new Object[]{clazz});
			throw new CommonRuntimeException(error);
		}
		opos = pos.toArray();
		for (int k = 0; k < opos.length; k++) {
			deleteObject(opos[k]);
		}
		flush();
		for (int k = 0; k < npos.length; k ++) {
			addObject(npos[k]);
		}
		return refNo;
	}
	
	/**
	 * 查询事故赔付凭证
	 * @param branchId
	 * @return
	 */
	public List getAccGuaPayForModify(Integer branchId) {
		List accOutGuaPay = claimsDAO.getAccOutGuaPayForModify(branchId);
		List accInPsnGuaPay = claimsDAO.getAccInPsnGuaPayForModify(branchId);
		for (int i = 0; i < accInPsnGuaPay.size(); i ++) {
			accOutGuaPay.add(accInPsnGuaPay.get(i));
		}
		return accOutGuaPay;
	}
	
}
