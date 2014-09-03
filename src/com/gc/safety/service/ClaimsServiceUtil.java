package com.gc.safety.service;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.gc.safety.po.Insurer;
import com.gc.util.SpringUtil;

public class ClaimsServiceUtil {
	public static final String BEAN_NAME = "safetyClaimsServiceUtil";
	
	private ClaimsService claimsService;

	public static ClaimsService getClaimsService() {
		ApplicationContext ctx = SpringUtil.getContext();
		ClaimsServiceUtil util = (ClaimsServiceUtil) ctx.getBean(BEAN_NAME);
		ClaimsService service = util.claimsService;
		return service;
	}

	public void setClaimsService(ClaimsService claimsService) {
		this.claimsService = claimsService;
	}

//==================================== Claims ====================================
	// ͨ���¹�id ���� ��������� �� ���������
	public static Integer getOutAndInGuaByAccId(Integer branchId,Integer accId) {
		return getClaimsService().getOutAndInGuaByAccId(branchId, accId);
	}
	
	// �����¹���������ƾ֤������ƾ֤��
	public static String saveAccOutGua(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccOutGua(oldList, newList, branchId);
	}
	
	// �����¹ʿ�������ƾ֤������ƾ֤��
	public static String saveAccInPsnGua(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccInPsnGua(oldList, newList, branchId);
	}
	
	// ��ѯ���޸�ƾ֤�б�,û���⸶��û�н��ʵ�����ƾ֤
	public static List getAccGuaForModify(Integer branchId) {
		return getClaimsService().getAccGuaForModify(branchId);
	}
	
	// ��ѯ��ǰ��������������ƾ֤
	public static List getAccGuaForQuery(Integer branchId) {
		return getClaimsService().getAccGuaForQuery(branchId);
	}
	
	// �⸶ҵ��ʱ��ѯ��������ƾ֤
	public static List getAccInPsnGua(Integer branchId, Insurer insurer) {
		return getClaimsService().getAccInPsnGua(branchId, insurer);
	}
	
	// �⸶ҵ��ʱ��ѯ��������ƾ֤
	public static List getAccOutGua(Integer branchId, Insurer insurer) {
		return getClaimsService().getAccOutGua(branchId, insurer);
	}
	
	/** ���������⸶ƾ֤������ƾ֤�� */
	public static String saveAccOutGuaPay(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccOutGuaPay(oldList, newList, branchId);
	}
	
	/** ��������⸶ƾ֤������ƾ֤�� */
	public static String saveAccInPsnGuaPay(Object[] oldList, Object[] newList, Integer branchId) {
		return getClaimsService().saveAccInPsnGuaPay(oldList, newList, branchId);
	}
	
	/** 
	 * ��ѯ���⸶ƾ֤
	 * @param branchId
	 * @return
	 */
	public static List getAccGuaPayForModify(Integer branchId) {
		return getClaimsService().getAccGuaPayForModify(branchId);
	}
	
	// ��ѯ��ǰ�����������⸶ƾ֤
	public static List getAccPayForQuery(Integer branchId) {
		return getClaimsService().getAccPayForQuery(branchId);
	}
}
