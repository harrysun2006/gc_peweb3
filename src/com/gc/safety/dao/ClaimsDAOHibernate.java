package com.gc.safety.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.Constants;
import com.gc.safety.po.AccInPsnGua;
import com.gc.safety.po.AccInPsnGuaPay;
import com.gc.safety.po.AccOutGua;
import com.gc.safety.po.AccOutGuaPay;
import com.gc.safety.po.AccOutObj;
import com.gc.safety.po.AccOutPsn;
import com.gc.safety.po.AccOutPsnPay;
import com.gc.safety.po.Insurer;

public class ClaimsDAOHibernate extends HibernateDaoSupport {

//==================================== Claims ====================================
	
	//通过事故id 来查 三则理赔表 和 客伤理赔表
	public Integer getOutAndInGuaByAccId(Integer branchId,Integer accId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select og from AccOutGua og")
				.append(" left outer join fetch og.accident a")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and og.id.branch.id = :branchId");
		if(accId != null && accId != 0) sBuilder.append(" and a.id.id = :accId");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accId != null && accId != 0) query.setParameter("accId", accId);
		Integer size1 = query.list().size();
		sBuilder.delete(0, sBuilder.length());
		sBuilder.append("select ig from AccInPsnGua ig")
				.append(" left outer join fetch ig.accident a")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and ig.id.branch.id = :branchId");
		if(accId != null && accId != 0) sBuilder.append(" and a.id.id = :accId");
		Query query2 = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query2.setParameter("branchId", branchId);
		if(accId != null && accId != 0) query2.setParameter("accId", accId);
		Integer size2 = query2.list().size();
		return (size1 + size2);
	}
	
	/**
	 * 查询当前机构的三责理赔凭证,没有赔付、没有结帐的理赔凭证
	 * 查询字段'三责'不能随意修改,前台偶合
	 * @param branchId
	 * @return 三责理赔列表
	 */
	public List getAccOutGuaForModify(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a, '三责', a.id.refNo as refNo from AccOutGua a")
			.append(" left outer join fetch a.insurer")
			.append(" left outer join fetch a.accident")
			.append(" left outer join fetch a.accident.bus")
			.append(" left outer join fetch a.accident.dept")
			.append(" left outer join fetch a.accident.driver")
			.append(" where ")
			.append(" not exists (")
			.append(" select b.fkAccOutGua from AccOutGuaPay b")
			// 凭证中已有赔付的不出来
			// .append(" where a = b.fkAccOutGua )")
			// 已有赔付的凭证中不出来
			.append(" where a.id.refNo = b.fkAccOutGua.id.refNo ")
			.append("  and a.id.branch.id = b.fkAccOutGua.id.branch.id)")
			.append(" and a.appDate >= ")
			.append(" (select nvl(max(c.id.date), :nulDate) from SafetyClose c)");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		q.setParameter("nulDate", Constants.MIN_DATE);
		return q.list();
	}
	
	/**
	 * 查询当前机构的客伤理赔凭证,没有赔付、没有结帐的理赔凭证
	 * 查询中的'客伤'不能随意修改,前台偶合
	 * @param branchId
	 * @return 客伤理赔列表
	 */
	public List getAccInPsnGuaForModify(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a, '客伤', a.id.refNo as refNo from AccInPsnGua a")
			.append(" left outer join fetch a.insurer")
			.append(" left outer join fetch a.accident")
			.append(" left outer join fetch a.accident.bus")
			.append(" left outer join fetch a.accident.dept")
			.append(" left outer join fetch a.accident.driver")
			.append(" where not exists")
			.append(" (select b.fkAccInPsnGua from AccInPsnGuaPay b")
			// 凭证中已有赔付的不出来
			// .append(" where a = b.fkAccInPsnGua )") 
			// 已有赔付的凭证中不出来
			.append(" where a.id.refNo = b.fkAccInPsnGua.id.refNo ")
			.append("  and a.id.branch.id = b.fkAccInPsnGua.id.branch.id)")
			.append(" and a.appDate >= ")
			.append(" (select nvl(max(c.id.date), :nulDate) from SafetyClose c)");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		q.setParameter("nulDate", Constants.MIN_DATE);
		return q.list();
	}
	
	/**
	 * 根据三责理赔凭证号查询对应的明细,并锁表,供判断数据是否已经被修改
	 * @param branchId
	 * @param refNo
	 * @param lock 是否锁定
	 * @return
	 */
	public List getAccOutGuaByRefNo(Integer branchId, String refNo, boolean lock) {
		if (refNo == null || refNo == "") return null;
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AccOutGua a")
			.append(" left outer join fetch a.id.branch")
			.append(" where a.id.refNo = :refNo");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("refNo", refNo);
		if (branchId != null) q.setParameter("bid", branchId);
		if (lock) q.setLockMode("a", LockMode.UPGRADE);
		return q.list();
	}
	
	/**
	 * 根据客伤理赔凭证号查询对应的明细,并锁表,供判断数据是否已经被修改
	 * @param branchId
	 * @param refNo
	 * @param lock 是否锁定
	 * @return
	 */
	public List getAccInPsnGuaByRefNo(Integer branchId, String refNo, boolean lock) {
		if (refNo == null) return null;
		StringBuilder sb = new StringBuilder();
		sb.append(" select a from AccInPsnGua a")
			.append(" left outer join fetch a.fkAccInPsn")
			.append(" where a.id.refNo = :refNo");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("refNo", refNo);
		if (branchId != null) q.setParameter("bid", branchId);
		if (lock) q.setLockMode("a", LockMode.UPGRADE);
		return q.list();
	}
	
	/**
	 * 根据客理赔付凭证号查询对应的明细并锁表,供判断数据是否已经被修改
	 * @param branchId
	 * @param refNo
	 * @param lock
	 * @return
	 */
	public List getAccInPsnGuaPayByRefNo(Integer branchId, String refNo, boolean lock) {
		if (refNo == null) return null;
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AccInPsnGuaPay a")
			.append(" left outer join fetch a.fkAccInPsnGua")
			.append(" where a.id.refNo = :refNo")
		;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("refNo", refNo);
		if (branchId != null) q.setParameter("bid", branchId);
		if (lock) q.setLockMode("a", LockMode.UPGRADE);
		return q.list();
	}
	
	public List getAccOutGuaPayByRefNo(Integer branchId, String refNo, boolean lock) {
		if (refNo == null) return null;
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AccOutGuaPay a")
			.append(" left outer join fetch a.fkAccOutGua")
			.append(" where a.id.refNo = :refNo")
		;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("refNo", refNo);
		if (branchId != null) q.setParameter("bid", branchId);
		if (lock) q.setLockMode("a", LockMode.UPGRADE);
		return q.list();
	}
	
	/**
	 * 查询当前机构所有理赔凭证及赔付情况
	 * 显示:凭证号、日期、险种、保险公司、状态-未处理、部分处理、全部处理
	 * @param branchId
	 * @return
	 */
	public List getAccOutGuaForQuery(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select a from AccOutGua a")
			.append(" left outer join fetch a.insurer")
			.append(" left outer join fetch a.accident")
			.append(" left outer join fetch a.accident.bus")
			.append(" left outer join fetch a.accident.dept")
			.append(" left outer join fetch a.accident.driver")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		sb.append(" order by a.id.branch.id, a.id.refNo, a.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		List<AccOutGua> accOutList = q.list();
		
		// 拼凭证列表
		List<String> guaList = new ArrayList<String>();
		for (int i = 0; i < accOutList.size(); i ++) {
			guaList.add(accOutList.get(i).getId().getRefNo());
		}
		
		// 取理赔凭证列表对应的三责赔付凭证
		StringBuilder sb2 = new StringBuilder();
		sb2.append(" select a from AccOutGuaPay a")
			.append(" left outer join fetch a.fkAccOutGua")
			.append(" where 1=1");
		if (branchId != null) sb2.append(" and a.id.branch.id = :bid");
		if (guaList.size() > 0) sb2.append(" and a.fkAccOutGua.id.refNo in (:list)");
		sb2.append(" order by a.fkAccOutGua.id.branch.id, a.fkAccOutGua.id.refNo, a.fkAccOutGua.id.no");
		Query q2 = getSession().createQuery(sb2.toString());
		if (branchId != null) q2.setParameter("bid", branchId);
		if (guaList.size() > 0) q2.setParameterList("list", guaList);
		List<AccOutGuaPay> accOutPayList = q2.list();
		
		List result = new ArrayList();
		int ps = 0, pe = 0;				// 赔付指针
		int gs = 0, ge = 0;				// 理赔指针
		String status = null;			// 状态
		String refNo = null;
		for (ge = 0; ge < accOutList.size(); ge++) {
			AccOutGua gua = accOutList.get(ge);
			if (!gua.getId().getRefNo().equals(refNo)) {
				if (refNo != null) {
					for (int i = gs; i < ge; i++) {
						List unit = new ArrayList();
						AccOutGua gua1 = accOutList.get(i);
						unit.add(gua1);
						unit.add("三责");
						if (status == null) {
							status = "未处理";
						} else if (status == "") {
							status = "全部处理";
						}
						unit.add(status);
						AccOutGuaPay aogp = null;
						if (accOutPayList.size() > 0) {
							for (int j = ps; j < pe + 1; j++) {
								AccOutGuaPay pay = accOutPayList.get(j);
								if (pay.getAppRefNo().equals(refNo) &&
										pay.getAppNo().equals(gua1.getId().getNo())) {
									aogp = pay;
									ps = pe + 1;	
									break;
								}
							}
						}
						unit.add(aogp);
						result.add(unit);
					}
					status = null;
					gs = ge;
				}
				refNo = gua.getId().getRefNo();
			}
			int row = -1;
			for (int j = ps; j < accOutPayList.size(); j++) {
				AccOutGuaPay pay = accOutPayList.get(j);
				if (pay.getAppRefNo().equals(refNo) &&
						pay.getAppNo().equals(gua.getId().getNo())) {
					row = j;
					break;
				}
			}
			if (row >= 0) {
				pe = row;
				if (status != null && status.equals("未处理")) {
					status = "部分处理";
				} else if (status == null || !status.equals("部分处理")) {
					status = "";
				}
			} else {
				if (status == "")
					status = "部分处理";
				else
					status = "未处理";
			}
		}
		for (int i = gs; i < ge; i++) {
			List unit = new ArrayList();
			AccOutGua gua = accOutList.get(i);
			unit.add(gua);
			unit.add("三责");
			if (status == "") status = "全部处理";
			unit.add(status);
			AccOutGuaPay aogp = null;
			if (accOutPayList.size() > 0) {
				for (int j = ps; j < pe + 1; j++) {
					AccOutGuaPay pay = accOutPayList.get(j);
					if (pay.getAppRefNo().equals(refNo) &&
							pay.getAppNo().equals(gua.getId().getNo())) {
						aogp = pay;
						break;
					}
				}
			}
			unit.add(aogp);
			result.add(unit);
		}
		
		return result;
	}
	
	public List getAccInGuaForQuery(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AccInPsnGua a")
			.append(" left outer join fetch a.insurer")
			.append(" left outer join fetch a.accident")
			.append(" left outer join fetch a.accident.bus")
			.append(" left outer join fetch a.accident.dept")
			.append(" left outer join fetch a.accident.driver")
			.append(" where 1=1")
		;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		Query q = getSession().createQuery(sb.toString());
		sb.append(" order by a.id.branch.id, a.id.refNo, a.id.no");
		if (branchId != null) q.setParameter("bid", branchId);
		List<AccInPsnGua> accInList = q.list();
		
		// 拼凭证列表
		List<String> guaList = new ArrayList<String>();
		for (int i = 0; i < accInList.size(); i ++) {
			guaList.add(accInList.get(i).getId().getRefNo());
		}
		
		// 查询客伤理赔凭证对应的赔付列表
		StringBuilder sb2 = new StringBuilder();
		sb2.append(" select a from AccInPsnGuaPay a")
			.append(" left outer join fetch a.fkAccInPsnGua")
			.append(" where 1=1");
		if (branchId != null) sb2.append(" and a.id.branch.id = :bid");
		if (guaList.size() > 0) sb2.append(" and a.fkAccInPsnGua.id.refNo in (:list)");
		sb2.append(" order by a.fkAccInPsnGua.id.branch.id, a.fkAccInPsnGua.id.refNo, a.fkAccInPsnGua.id.no");
		Query q2 = getSession().createQuery(sb2.toString());
		if (branchId != null) q2.setParameter("bid", branchId);
		if (guaList.size() > 0) q2.setParameterList("list", guaList);
		List<AccInPsnGuaPay> accInPayList =  q2.list();
		
		// 处理理赔凭证的处理状态
		List result = new ArrayList();	// 返回结果列表
		int ps = 0, pe = 0;				// 赔付指针
		int gs = 0, ge = 0;				// 理赔指针
		String status = null;			// 状态
		String refNo = null;
		for (ge = 0; ge < accInList.size(); ge++) {
			AccInPsnGua gua = accInList.get(ge);
			if (!gua.getId().getRefNo().equals(refNo)) {
				if (refNo != null) {
					for (int i = gs; i < ge; i++) {
						List unit = new ArrayList();
						AccInPsnGua gua1 = accInList.get(i);
						unit.add(gua1);
						unit.add("客伤");
						if (status == null) {
							status = "未处理";
						} else if (status == "") {
							status = "全部处理";
						}
						unit.add(status);
						AccInPsnGuaPay aigp = null;
						if (accInPayList.size() > 0) {
							for (int j = ps; j < pe + 1; j++) {
								AccInPsnGuaPay pay = accInPayList.get(j);
								if (pay.getAppRefNo().equals(refNo) &&
										pay.getAppNo().equals(gua1.getId().getNo())) {
									aigp = pay;
									ps = pe + 1;	
									break;
								}
							}
						}
						unit.add(aigp);
						result.add(unit);
					}
					status = null;
					gs = ge;
					// ps = pe + 1;
				}
				refNo = gua.getId().getRefNo();
			}
			// 从赔付里找有没有一致的
			int row = -1;
			for (int j = ps; j < accInPayList.size(); j++) {
				AccInPsnGuaPay pay = accInPayList.get(j);
				if (pay.getAppRefNo().equals(refNo) &&
						pay.getAppNo().equals(gua.getId().getNo())) {
					row = j;
					break;
				}
			}
			if (row >= 0) {
				pe = row;
				if (status != null && status.equals("未处理")) {
					status = "部分处理";
				} else if (status == null || !status.equals("部分处理")) {
					status = "";
				}
			} else {
				if (status == "")
					status = "部分处理";
				else
					status = "未处理";
			}
		}
		for (int i = gs; i < ge; i++) {
			List unit = new ArrayList();
			AccInPsnGua gua = accInList.get(i);
			unit.add(gua);
			unit.add("客伤");
			if (status == "") status = "全部处理";
			unit.add(status);
			AccInPsnGuaPay aigp = null;
			if (accInPayList.size() > 0) {
				for (int j = ps; j < pe + 1; j++) {
					AccInPsnGuaPay pay = accInPayList.get(j);
					if (pay.getAppRefNo().equals(gua.getId().getRefNo()) &&
							pay.getAppNo().equals(gua.getId().getNo())) {
						aigp = pay;
						break;
					}
				}
			}
			unit.add(aigp);
			result.add(unit);
		}
		return result;
	}
	
	/**
	 * 查询当前机构所有客伤赔付凭证
	 * @param branchId
	 * @return
	 */
	public List getAccInGuaPayForQuery(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select a from AccInPsnGuaPay a")
			.append(" left outer join fetch a.fkAccInPsnGua")
			.append(" left outer join fetch a.fkAccInPsnGua.accident")
			.append(" left outer join fetch a.fkAccInPsnGua.accident.dept")
			.append(" left outer join fetch a.fkAccInPsnGua.accident.bus")
			.append(" left outer join fetch a.fkAccInPsnGua.accident.driver")
			.append(" left outer join fetch a.fkAccInPsnGua.insurer")
			.append(" left outer join fetch a.fkAccInPsnGua.fkAccInPsn")
			.append(" left outer join fetch a.fkAccInPsnGua.fkGuaReport")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		sb.append(" order by a.id.branch, a.id.refNo, a.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		return q.list();
	}
	
	/**
	 * 查询当前机构所有三责赔付凭证
	 * @param branchId
	 * @return
	 */
	public List getAccOutGuaPayForQuery(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select a from AccOutGuaPay a")
			.append(" left outer join fetch a.fkAccOutGua")
			.append(" left outer join fetch a.fkAccOutGua.accident")
			.append(" left outer join fetch a.fkAccOutGua.accident.dept")
			.append(" left outer join fetch a.fkAccOutGua.accident.bus")
			.append(" left outer join fetch a.fkAccOutGua.accident.driver")
			.append(" left outer join fetch a.fkAccOutGua.insurer")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		sb.append(" order by a.id.branch, a.id.refNo, a.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		List<AccOutGuaPay> a = q.list();
		if (a.size() <= 0) return null;
		
		// 拼事故列表
		ArrayList<Integer> accIdList = new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i++) {
			if (accIdList.indexOf(a.get(i).getFkAccOutGua().getAccidentId().getId()) < 0) {
				accIdList.add(a.get(i).getFkAccOutGua().getAccidentId().getId());
			}
		}
		
		// 撞击对象列表
		StringBuilder sb2 = new StringBuilder();
		sb2.append("select aoo from AccOutObj aoo")
			.append(" where aoo.id.accId in (:accIds)");
		if (branchId != null) sb2.append(" and aoo.id.branch.id = :bid");
		sb2.append(" order by aoo.id.no");
		Query q2 = getSession().createQuery(sb2.toString());
		if (branchId != null) q2.setParameter("bid", branchId);
		q2.setParameterList("accIds", accIdList);
		List b = q2.list();

		// 撞击人员列表
		StringBuilder sb3 = new StringBuilder();
		sb3.append("select aop from AccOutPsn aop")
			.append(" where aop.id.accId in (:accIds)");
		if (branchId != null) sb3.append(" and aop.id.branch.id = :bid");
		sb3.append(" order by aop.id.no");
		Query q3 = getSession().createQuery(sb3.toString());
		if (branchId != null) q3.setParameter("bid", branchId);
		q3.setParameterList("accIds", accIdList);
		List c = q3.list();
		
		List result = new ArrayList();
		for (Iterator it = a.iterator(); it.hasNext();) {
			AccOutGuaPay obj = (AccOutGuaPay) it.next();
			
			// 支付的物损金额
			Double payObjSum = 0d;
			for (int i = 0; i < b.size(); i ++) {
				AccOutObj accOutObj = (AccOutObj) b.get(i);
				if (accOutObj.getId().getAccId().intValue() == obj.getFkAccOutGua().getAccId().intValue()) {
					payObjSum = payObjSum + accOutObj.getPayFee();
				}
			}

			// 支付的伤亡人员金额
			Double payMediFee = 0d;
			Double payOther1 = 0d;
			Double payOther2 = 0d;
			for (Iterator<AccOutPsn> itc = c.iterator(); itc.hasNext(); ) {
				AccOutPsn accOutPsn = itc.next();
				if (accOutPsn.getId().getAccId().intValue() == obj.getFkAccOutGua().getAccId().intValue()) {
					for (Iterator<AccOutPsnPay> itt = accOutPsn.getAccOutPsnPays().iterator(); itt.hasNext(); ) {
						AccOutPsnPay accOutPsnPay = itt.next();
						payMediFee += accOutPsnPay.getMediFee();
						payOther1 += accOutPsnPay.getOther1();
						payOther2 += accOutPsnPay.getOther2();
					}
				}
			}
			
			List unit = new ArrayList();
			unit.add(obj);
			unit.add(payObjSum);
			unit.add(payMediFee);
			unit.add(payOther1);
			unit.add(payOther2);
			result.add(unit);
		}
		return result;
	}
	
	/**
	 * 查询三责理赔凭证
	 * @param branchId
	 * @param insurer
	 * @return
	 */
	public List getAccOutGua(Integer branchId, Insurer insurer) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from  AccOutGua a")
			.append(" left outer join fetch a.accident")
			.append(" left outer join fetch a.accident.driver")
			.append(" left outer join fetch a.accident.dept")
			.append(" left outer join fetch a.accident.bus")
			.append(" left outer join fetch a.fkGuaReport")
			.append(" left outer join fetch a.insurer ins")
			.append(" where 1=1");
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		if (insurer != null) sb.append(" and ins.id = :insId");
		sb.append(" and not exists ( select aogp.id from AccOutGuaPay aogp" +
				" where a.id.refNo = aogp.appRefNo" +
				" and a.id.no = aogp.appNo" +
				" and a.id.branch.id = aogp.id.branch.id )");
		sb.append(" order by a.id.refNo, a.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (insurer != null) q.setParameter("insId", insurer.getId());
		List<AccOutGua> g = q.list();

		if (g.size() <= 0) return null;
		
		// 组织事故ID列表供取撞击对象、撞击人员用
		ArrayList<Integer> accIdList = new ArrayList<Integer>();
		for (Iterator it = g.iterator(); it.hasNext();) {
			AccOutGua obj = (AccOutGua) it.next();
			if (accIdList.indexOf(obj.getAccId()) < 0) {
				accIdList.add(obj.getAccId());
			}
		}
		
		// 撞击对象列表
		StringBuilder sb2 = new StringBuilder();
		sb2.append("select aoo from AccOutObj aoo")
			.append(" where aoo.id.accId in (:accIds)");
		if (branchId != null) sb2.append(" and aoo.id.branch.id = :bid");
		sb2.append(" order by aoo.id.no");
		Query q2 = getSession().createQuery(sb2.toString());
		if (branchId != null) q2.setParameter("bid", branchId);
		q2.setParameterList("accIds", accIdList);
		List b = q2.list();

		// 撞击人员列表
		StringBuilder sb3 = new StringBuilder();
		sb3.append("select aop from AccOutPsn aop")
			.append(" where aop.id.accId in (:accIds)");
		if (branchId != null) sb3.append(" and aop.id.branch.id = :bid");
		sb3.append(" order by aop.id.no");
		Query q3 = getSession().createQuery(sb3.toString());
		if (branchId != null) q3.setParameter("bid", branchId);
		q3.setParameterList("accIds", accIdList);
		List c = q3.list();
		
		List result = new ArrayList();
		for (Iterator it = g.iterator(); it.hasNext();) {
			AccOutGua obj = (AccOutGua) it.next();
			
			// 支付的物损金额
			Double payObjSum = 0d;
			for (int i = 0; i < b.size(); i ++) {
				AccOutObj accOutObj = (AccOutObj) b.get(i);
				if (accOutObj.getId().getAccId().intValue() == obj.getAccId().intValue()) {
					payObjSum = payObjSum + accOutObj.getPayFee();
				}
			}
			
			// 支付的伤亡人员金额
			Double payMediFee = 0d;
			Double payOther1 = 0d;
			Double payOther2 = 0d;
			for (Iterator<AccOutPsn> itc = c.iterator(); itc.hasNext(); ) {
				AccOutPsn accOutPsn = itc.next();
				if (accOutPsn.getId().getAccId().intValue() == obj.getAccId().intValue()) {
					for (Iterator<AccOutPsnPay> itt = accOutPsn.getAccOutPsnPays().iterator(); itt.hasNext(); ) {
						AccOutPsnPay accOutPsnPay = itt.next();
						payMediFee += accOutPsnPay.getMediFee();
						payOther1 += accOutPsnPay.getOther1();
						payOther2 += accOutPsnPay.getOther2();
					}
				}
			}
			
			List unit = new ArrayList<Object>();
			unit.add(obj);
			unit.add(payObjSum);
			unit.add(payMediFee);
			unit.add(payOther1);
			unit.add(payOther2);
			result.add(unit);
		}
		
		return result;
	}
	
	/**
	 * 查询客伤理赔凭证
	 * @param branchId
	 * @param insurer
	 * @return
	 */
	public List getAccInPsnGua(Integer branchId, Insurer insurer) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from  AccInPsnGua a")
			.append(" left outer join fetch a.accident")
			.append(" left outer join fetch a.accident.driver")
			.append(" left outer join fetch a.accident.dept")
			.append(" left outer join fetch a.accident.bus")
			.append(" left outer join fetch a.fkGuaReport")
			.append(" left outer join fetch a.fkAccInPsn")
			.append(" where 1=1")
		;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		if (insurer != null) sb.append(" and a.insurer = :insurer");
		sb.append(" and not exists(select aipgp.id from AccInPsnGuaPay aipgp" +
				" where a.id.refNo = aipgp.appRefNo" +
				" and a.id.no = aipgp.appNo" +
				" and a.id.branch.id = aipgp.id.branch.id)");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (insurer != null) q.setParameter("insurer", insurer);
		List<AccInPsnGua> aipgList = q.list();
		return aipgList;
	}
	
	/**
	 * 查询三责赔付凭证
	 * 没有结帐的赔付凭证(下拉窗信息：凭证号、日期、险种、保险公司)
	 * @param branchId
	 * @return
	 */
	public List getAccOutGuaPayForModify(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a, '三责', a.id.refNo as refNo from AccOutGuaPay a")
			.append(" left outer join fetch a.fkAccOutGua")
			.append(" left outer join fetch a.fkAccOutGua.accident")
			.append(" left outer join fetch a.fkAccOutGua.accident.driver")
			.append(" left outer join fetch a.fkAccOutGua.accident.dept")
			.append(" left outer join fetch a.fkAccOutGua.accident.bus")
			.append(" left outer join fetch a.fkAccOutGua.insurer")
			.append(" left outer join fetch a.fkAccOutGua.fkGuaReport")
			.append(" where a.payDate >= ")
			.append(" (select nvl(max(c.id.date), :nulDate) from SafetyClose c)")
		;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		sb.append(" order by a.id.branch, a.id.refNo, a.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		q.setParameter("nulDate", Constants.MIN_DATE);
		List list = q.list();
		if(list.size()<=0) return list;
		String refNo="";
		List list2 = new ArrayList();
		List list1 = new ArrayList();
		for (int i=0; i<list.size(); i++) {
			Object[] objs = (Object[])list.get(i);
			if (refNo.equals((String)objs[2])) {
				if(i==1)
				{
					list2.remove(0);
					list1.add(list.get(0));
				}
				list1.add(objs);
				if(i+1==list.size())
					list2.add(list1);
				continue;
			}
			refNo = (String)objs[2];
			list1.add(objs);
			list2.add(list1);
			list1.clear();
		}
		for (int j=0;j<list2.size();j++) {
			List list3 = (ArrayList)list2.get(j);
			int status = 0;
			for (int m = 0; m < list3.size(); m++) {
				Object[] objects = (Object[])list3.get(m);
				if (((AccOutGuaPay)objects[0]).getFkAccOutGua().getAccident().getStatus()==3)
					status++;
			}
			if (status==list3.size()) {
				for (int n = 0; n < list3.size(); n++) {
					list.remove(list3.get(n));
				}
			}
		}
		return list;
	}
	
	/**
	 * 查询客伤赔付凭证
	 * 没有结帐的赔付凭证(下拉窗信息：凭证号、日期、险种、保险公司)
	 * @param branchId
	 * @return
	 */
	public List getAccInPsnGuaPayForModify(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a, '客伤', a.id.refNo as refNo from AccInPsnGuaPay a")
			.append(" left outer join fetch a.fkAccInPsnGua")
			.append(" left outer join fetch a.fkAccInPsnGua.accident")
			.append(" left outer join fetch a.fkAccInPsnGua.accident.driver")
			.append(" left outer join fetch a.fkAccInPsnGua.accident.dept")
			.append(" left outer join fetch a.fkAccInPsnGua.accident.bus")
			.append(" left outer join fetch a.fkAccInPsnGua.insurer")
			.append(" left outer join fetch a.fkAccInPsnGua.fkGuaReport")
			.append(" left outer join fetch a.fkAccInPsnGua.fkAccInPsn")
			.append(" where a.payDate >= ")
			.append(" (select nvl(max(c.id.date), :nulDate) from SafetyClose c)")
		;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		sb.append(" order by a.id.branch, a.id.refNo, a.id.no");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		q.setParameter("nulDate", Constants.MIN_DATE);
		List list = q.list();
		if(list.size()<=0) return list;
		String refNo="";
		List list2 = new ArrayList();
		List list1 = new ArrayList();
		for (int i=0; i<list.size(); i++) {
			Object[] objs = (Object[])list.get(i);
			if (refNo.equals((String)objs[2])) {
				if(i==1)
				{
					list2.remove(0);
					list1.add(list.get(0));
				}
				list1.add(objs);
				if(i+1==list.size())
					list2.add(list1);
				continue;
			}
			refNo = (String)objs[2];
			list1.add(objs);
			list2.add(list1);
			list1=new ArrayList();
		}
		for (int j=0;j<list2.size();j++) {
			List list3 = (ArrayList)list2.get(j);
			int status = 0;
			for (int m = 0; m < list3.size(); m++) {
				Object[] objects = (Object[])list3.get(m);
				if (((AccInPsnGuaPay)objects[0]).getFkAccInPsnGua().getAccident().getStatus()==3)
					status++;
			}
			if (status==list3.size()) {
				for (int n = 0; n < list3.size(); n++) {
					list.remove(list3.get(n));
				}
			}
		}
		return list;
	}
	
	public void saveAccOutGua(Object po) {
		getHibernateTemplate().save(po);
	}
	
	public int deleteObject(Object po) {
		getHibernateTemplate().delete(po);
		return 1;
	}
	
	public int updateObject(Object po) {
		getHibernateTemplate().update(po);
		return 1;
	}
	
	public int addObject(Object po) {
		getHibernateTemplate().save(po);
		return 1;
	}
	
	public void flush() {
		getSession().flush();
	}

}
