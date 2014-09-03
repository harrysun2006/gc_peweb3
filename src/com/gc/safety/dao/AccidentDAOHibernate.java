package com.gc.safety.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.Constants;
import com.gc.common.po.Department;
import com.gc.common.po.SecurityLimit;
import com.gc.safety.po.AccDuty;
import com.gc.safety.po.AccExtent;
import com.gc.safety.po.AccInPsn;
import com.gc.safety.po.AccInPsnGua;
import com.gc.safety.po.AccInPsnGuaPay;
import com.gc.safety.po.AccInPsnPK;
import com.gc.safety.po.AccInPsnPay;
import com.gc.safety.po.AccInPsnPayPK;
import com.gc.safety.po.AccLevel;
import com.gc.safety.po.AccObject;
import com.gc.safety.po.AccOutGua;
import com.gc.safety.po.AccOutGuaPay;
import com.gc.safety.po.AccOutObj;
import com.gc.safety.po.AccOutObjPK;
import com.gc.safety.po.AccOutPsn;
import com.gc.safety.po.AccOutPsnPK;
import com.gc.safety.po.AccOutPsnPay;
import com.gc.safety.po.AccOutPsnPayPK;
import com.gc.safety.po.AccProcessor;
import com.gc.safety.po.AccType;
import com.gc.safety.po.Accident;
import com.gc.safety.po.GuaReport;
import com.gc.safety.po.Guarantee;
import com.gc.safety.po.Insurer;
import com.gc.util.ObjectUtil;

public class AccidentDAOHibernate extends HibernateDaoSupport {

//==================================== Accident ====================================
	public void mergeObject(Object po) {
		getHibernateTemplate().merge(po);
	}
	
	/**
	 * <b>建立理赔凭证时获取三责事故列表</b><br/>
	 * 条件:营运部审核状态、在指定保险公司报过案的三责事故列表
	 */
	public List getAccAndOutByInsurer(Integer branchId, Insurer insurer) {
		StringBuilder sb1 = new StringBuilder();
		sb1.append("select a, g from Accident a")
			.append(" left outer join fetch a.dept")
			.append(" left outer join fetch a.bus")
			.append(" left outer join fetch a.driver")
			.append(", GuaReport g")								// 报案表
			.append(", Insurer ins")								// 保险公司
			.append(" where a.status = 2")							// 营运部审核状态
			.append(" and a.id.branch.id = g.id.branch.id")			// 报过案
			.append(" and a.id.id = g.id.accId")
			.append(" and g.id.branch.id = ins.branch.id")			// 指定保险公司
			.append(" and g.id.insurer.id = ins.id")
		;
		if (branchId != null) sb1.append(" and a.id.branch.id = :bid");
		if (insurer != null) sb1.append(" and g.id.insurer.id = :insurer");
		sb1.append(" order by a.no");
		Query q1 = getSession().createQuery(sb1.toString());
		if (branchId != null) q1.setParameter("bid", branchId);
		if (insurer != null) q1.setParameter("insurer", insurer.getId());
		List a = q1.list();
		if (a.size() <= 0) return null;
		
		// organize accident no list
		ArrayList<String> accNoList = new ArrayList<String>();
		ArrayList<Integer> accIdList = new ArrayList<Integer>();
		// organize accident bus list
		ArrayList<Integer> busList = new ArrayList<Integer>();
		for (Iterator it = a.iterator(); it.hasNext(); ) {
			Object[] ob = (Object[]) it.next();
			if (accNoList.indexOf(((Accident) ob[0]).getNo()) < 0) {
				accNoList.add(((Accident) ob[0]).getNo());
				accIdList.add(((Accident) ob[0]).getId().getId());
			}
			if (busList.indexOf(((Accident) ob[0]).getBus().getId()) < 0) {
				busList.add(((Accident) ob[0]).getBus().getId());
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
		
		// 有无指定保险公司的三责保单
		StringBuilder sb4 = new StringBuilder();
		sb4.append("select gu.fkGuarantee from GuarInfo gu")
			.append(" where gu.bus.id in (:busIds)")
			.append(" and gu.fkGuarantee.type.name <> '客伤险'");					// 三责险
		if (branchId != null) sb4.append(" and gu.bus.branch.id = :bid")
			.append(" and gu.id.branch.id = :bid");
		if (insurer != null) sb4.append(" and gu.fkGuarantee.insurer.id = :insurer");
		sb4.append(" order by gu.fkGuarantee.insurer");
		Query q4 = getSession().createQuery(sb4.toString());
		q4.setParameterList("busIds", busList.toArray());
		if (branchId != null) q4.setParameter("bid", branchId);
		if (insurer != null) q4.setParameter("insurer", insurer.getId());
		List d = q4.list();
		
		// select related accident's gua
		StringBuilder sb5 = new StringBuilder();
		sb5.append("select ao from AccOutGua ao")
			.append(" where ao.accident.no in (:accs)");
		if (branchId != null) sb5.append(" and ao.id.branch.id = :bid");
		if (insurer != null) sb5.append(" and ao.insurer.id = :insurer");
		Query q5 = getSession().createQuery(sb5.toString());
		q5.setParameterList("accs", accNoList.toArray());
		if (branchId != null) q5.setParameter("bid", branchId);
		if (insurer != null) q5.setParameter("insurer", insurer.getId());
		List e = q5.list();
		
		List result = new ArrayList();
		for (Iterator it = a.iterator(); it.hasNext(); ) {
			Object[] ob = (Object[]) it.next();
			
			// 添加对应保险公司三责保单(有/无)
			boolean hasGuarInfo = false;
			Calendar accDate = ((Accident) ob[0]).getDate();
			for (int i = 0; i < d.size(); i ++) {
				Guarantee gua = (Guarantee) d.get(i);
				if (gua.getInsurer().getId().longValue() == insurer.getId().longValue() && 
						(gua.getOnDate().before(accDate) || gua.getOnDate().equals(accDate)) &&
						(gua.getDownDate().after(accDate) || gua.getDownDate().equals(accDate))) {
					hasGuarInfo = true;
					break;
				}
			}
			
			// 添加三责理赔凭证
			boolean hasAccOutGua = false;
			for (int i = 0; i < e.size(); i ++) {
				AccOutGua ao = (AccOutGua) e.get(i);
				if (ao.getAccident().getNo() == ((Accident) ob[0]).getNo() &&
						ao.getAccident().getId().getBranchId() == ((Accident) ob[0]).getId().getBranchId()) {
					hasAccOutGua = true;
					break;
				}
			}
			// 支付的物损金额
			Double payObjSum = 0d;
			for (int i = 0; i < b.size(); i ++) {
				AccOutObj accOutObj = (AccOutObj) b.get(i);
				if (accOutObj.getId().getAccId().intValue() == ((Accident) ob[0]).getId().getId().intValue()) {
					payObjSum = payObjSum + accOutObj.getPayFee();
				}
			}
			
			// 支付的伤亡人员金额
			Double payMediFee = 0d;
			Double payOther1 = 0d;
			Double payOther2 = 0d;
			for (Iterator<AccOutPsn> itc = c.iterator(); itc.hasNext(); ) {
				AccOutPsn accOutPsn = itc.next();
				if (accOutPsn.getId().getAccId().intValue() == ((Accident) ob[0]).getId().getId().intValue()) {
					for (Iterator<AccOutPsnPay> itt = accOutPsn.getAccOutPsnPays().iterator(); itt.hasNext(); ) {
						AccOutPsnPay accOutPsnPay = itt.next();
						payMediFee += accOutPsnPay.getMediFee();
						payOther1 += accOutPsnPay.getOther1();
						payOther2 += accOutPsnPay.getOther2();
					}
				}
			}
			
			List unit = new ArrayList<Object>();
			unit.add((Accident) ob[0]);				// Accident
			unit.add((GuaReport) ob[1]);			// GuaReport
			unit.add(payObjSum);
			unit.add(payMediFee);
			unit.add(payOther1);
			unit.add(payOther2);
			unit.add(hasGuarInfo);					// hasGuarInfo
			unit.add(hasAccOutGua);					// hasAccOutGua
			result.add(unit);
		}
		
		return result;
	}
	
	
	
	/**
	 * <b>建立理赔凭证时获取客伤事故列表</b><br/>
	 * 条件:营运部审核状态、在指定保险公司报过案的客伤事故列表
	 */
	public List getAccAndInPsnListByInsurer(Integer branchId, Insurer insurer) {
		StringBuilder sb1 = new StringBuilder();
		sb1.append("select a, aip, g from Accident a")
			.append(" left outer join fetch a.dept")
			.append(" left outer join fetch a.bus")
			.append(" left outer join fetch a.driver")
			.append(", AccInPsn aip")								// 客伤人员
			.append(", GuaReport g")								// 报案表
			.append(", Insurer ins")								// 保险公司
			.append(" where a.status = 2")					// 营运部审核状态
			.append(" and a.id.branch.id = g.id.branch.id")			// 报过案
			.append(" and a.id.id = g.id.accId")
			.append(" and g.id.branch.id = ins.branch.id")			// 指定保险公司
			.append(" and g.id.insurer.id = ins.id")
			.append(" and a.id.branch.id = aip.id.branch.id")		// 客伤人员表
			.append(" and a.id.id = aip.id.accId");
		if (branchId != null) sb1.append(" and a.id.branch.id = :bid");
		if (insurer != null) sb1.append(" and g.id.insurer.id = :insurer");
		sb1.append(" order by a.no");
		Query q1 = getSession().createQuery(sb1.toString());
		if (branchId != null) q1.setParameter("bid", branchId);
		if (insurer != null) q1.setParameter("insurer", insurer.getId());
		List a = q1.list();
		if (a.size() <= 0) return null;
		
		// organize accident no list
		ArrayList<String> accNoList = new ArrayList<String>();
		// organize accident bus list
		ArrayList<Integer> busList = new ArrayList<Integer>();
		for (Iterator it = a.iterator(); it.hasNext(); ) {
			Object[] ob = (Object[]) it.next();
			if (accNoList.indexOf(((Accident) ob[0]).getNo()) < 0) {
				accNoList.add(((Accident) ob[0]).getNo());
			}
			if (busList.indexOf(((Accident) ob[0]).getBus().getId()) < 0) {
				busList.add(((Accident) ob[0]).getBus().getId());
			}
		}

		StringBuilder sb2 = new StringBuilder();
		sb2.append("select gu.fkGuarantee from GuarInfo gu")
			.append(" where gu.bus.id in (:busIds)")
			.append(" and gu.fkGuarantee.type.name='客伤险'")				// 客伤险
		;
		if (branchId != null) sb2.append(" and gu.bus.branch.id = :bid")
			.append(" and gu.id.branch.id = :bid");
		if (insurer != null) sb2.append(" and gu.fkGuarantee.insurer.id = :insurer");
		sb2.append(" order by gu.fkGuarantee.insurer");
		Query q2 = getSession().createQuery(sb2.toString());
		q2.setParameterList("busIds", busList.toArray());
		if (branchId != null) q2.setParameter("bid", branchId);
		if (insurer != null) q2.setParameter("insurer", insurer.getId());

		// select related accident's gua
		StringBuilder sb3 = new StringBuilder();
		sb3.append("select ai from AccInPsnGua ai")
			.append(" where ai.accident.no in (:accs)");
		if (branchId != null) sb3.append(" and ai.id.branch.id = :bid");
		if (insurer != null) sb3.append(" and ai.insurer.id = :insurer");
		Query q3 = getSession().createQuery(sb3.toString());
		q3.setParameterList("accs", accNoList.toArray());
		if (branchId != null) q3.setParameter("bid", branchId);
		if (insurer != null) q3.setParameter("insurer", insurer.getId());
		List b = q2.list();
		List c = q3.list();
		
		List result = new ArrayList();
		for (Iterator it = a.iterator(); it.hasNext(); ) {
			Object[] ob = (Object[]) it.next();
			List unit = new ArrayList<Object>();
			unit.add((Accident) ob[0]);				// [0]: Accident list
			unit.add((AccInPsn) ob[1]);				// [1]: AccInPsn list
			unit.add((GuaReport) ob[2]);			// [2]: GuaReport
			
			// 添加对应保险公司客伤保单(有/无)
			boolean hasGuarInfo = false;
			Calendar accDate = ((Accident) ob[0]).getDate();
			for (int i = 0; i < b.size(); i ++) {
				Guarantee gua = (Guarantee) b.get(i);
				if (gua.getInsurer().getId().longValue() == insurer.getId().longValue() &&
						(gua.getOnDate().before(accDate) || gua.getOnDate().equals(accDate)) &&
						(gua.getDownDate().after(accDate) || gua.getDownDate().equals(accDate))) {
					hasGuarInfo = true;
					break;
				}
			}
			unit.add(hasGuarInfo);
			
			// 添加客伤理赔凭证(有/无)
			boolean hasAccInPsnGua = false;
			for (int i = 0; i < c.size(); i ++) {
				AccInPsnGua ai = (AccInPsnGua) c.get(i);
				if (ai.getAccident().getNo() == ((Accident) ob[0]).getNo() &&
						ai.getAccident().getId().getBranchId() == ((Accident) ob[0]).getId().getBranchId()) {
					hasAccInPsnGua = true;
					break;
				}
			}
			unit.add(hasAccInPsnGua);
			
			result.add(unit);
		}
		
		return result;
	}
	
	/**
	 * <b>建立理赔凭证时获取凭证号</b><br/>
	 * 条件:营运部审核状态、在指定保险公司报过案的事故列表
	 */
	@SuppressWarnings("unchecked")
	public List<Accident> getAccidentListByInsurerForClaims(Integer branchId, Insurer insurer) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a, aip from Accident a")
			.append(" left outer join fetch a.dept")
			.append(" left outer join fetch a.bus")
			.append(" left outer join fetch a.driver")
			.append(" ,")
			.append(" GuaReport g")									// 报案表
			.append(", AccInPsn aip")								// 客伤人员
			.append(", Guarantee gu")								// 投保凭证
			.append(", AccInPsnGua ing")							// 客伤理赔凭证
			.append(" where a.status = 3")							// 营运部审核状态
			.append(" and a.id.branch.id = g.id.branch.id")			// 报过案
			.append(" and a.id.id = g.id.accId")
			
			.append(" and a.id.branch.id = aip.id.branch.id")		// 客伤人员表
			.append(" and a.id.id = aip.id.accId")
			
			.append(" and g.id.branch.id = gu.id.branch.id")		// 考虑保单日期
			.append(" and g.id.insurer.id = gu.insurer.id")
			.append(" and gu.type.name='客伤险'")							// 客伤险
			
//			.append(" and ")
			;
		if (branchId != null) sb.append(" and a.id.branch.id = :bid");
		if (insurer != null) sb.append(" and g.id.insurer.id = :insurer");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null) q.setParameter("bid", branchId);
		if (insurer != null) q.setParameter("insurer", insurer.getId());
		return (List<Accident>) q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Accident> getAccidentByBranchId(Integer branchId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select a from Accident a").append(
				" left outer join fetch a.bus").append(
				" left outer join fetch a.bus.type").append(
				" left outer join fetch a.dept")
				.append(" left outer join fetch a.line").append(
						" left outer join fetch a.driver").append(
						" left outer join fetch a.extent").append(
						" left outer join fetch a.type").append(
						" left outer join fetch a.level").append(
						" left outer join fetch a.processor").append(
						" left outer join fetch a.initor").append(
						" left outer join fetch a.deptor").append(
						" left outer join fetch a.compor").append(
						" left outer join fetch a.weather").append(" where 1 = 1").append(
						" and a.id.branch.id = :branchId").append(" order by a.status");
		Query query = getSession().createQuery(sBuilder.toString());
		query.setParameter("branchId", branchId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Accident> getAccidentList(Accident accident) {
//		StringBuilder sBuilder = new StringBuilder();
//		sBuilder.append("select a from Accident a").append(
//				" left outer join fetch a.bus").append(
//				" left outer join fetch a.bus.type").append(
//				" left outer join fetch a.dept")
//				.append(" left outer join fetch a.line").append(
//						" left outer join fetch a.driver").append(
//						" left outer join fetch a.extent").append(
//						" left outer join fetch a.type").append(
//						" left outer join fetch a.level").append(
//						" left outer join fetch a.processor").append(
//						" left outer join fetch a.initor").append(
//						" left outer join fetch a.deptor").append(
//						" left outer join fetch a.compor").append(
//						" left outer join fetch a.weather").append(" where 1 = 1").append(
//						" and a.id.branch.id = :branchId");
////		if (accident.getId().getNo() != null && accident.getId().getNo() != "") {
////			sBuilder.append(" and a.id.no like :no");
////		}
//		if (accident.getDept() != null && accident.getDept().getName() != null) {
//			sBuilder.append(" and a.dept.name like :deptName");
//		}
//		if (accident.getDateFrom() != null) {
//			sBuilder.append(" and a.date >= :dateFrom");
//		}
//		if (accident.getDateTo() != null) {
//			sBuilder.append(" and a.date <= :dateTo");
//		}
//		if (accident.getDriver() != null
//				&& accident.getDriver().getWorkerId() != null) {
//			sBuilder.append(" and a.driver.workerId like :workerId");
//		}
//		if (accident.getLine() != null && accident.getLine().getName() != null) {
//			sBuilder.append(" and a.line.name like :lineName");
//		}
//		if (accident.getBus() != null && accident.getBus().getUseId() != null) {
//			sBuilder.append(" and a.bus.useId like :useId");
//		}
//		if (accident.getAddress() != null && accident.getAddress() != "") {
//			sBuilder.append(" and a.address like :address");
//		}
//		if (accident.getWeather() != null
//				&& accident.getWeather().getName() != null) {
//			sBuilder.append(" and a.weather.name like :weatherName");
//		}
//		if (accident.getDesc1() != null && accident.getDesc1() != "") {
//			sBuilder.append(" and a.desc1 like :desc1");
//		}
//		if (accident.getDesc2() != null && accident.getDesc2() != "") {
//			sBuilder.append(" and a.desc2 like :desc2");
//		}
//		if (accident.getDesc3() != null && accident.getDesc3() != "") {
//			sBuilder.append(" and a.desc3 like :desc3");
//		}
//		if (accident.getRoadFacility() != null && accident.getRoadFacility() != "") {
//			sBuilder.append(" and a.roadFacility like :roadFacility");
//		}
//		if (accident.getReason() != null && accident.getReason() != "") {
//			sBuilder.append(" and a.reason like :reason");
//		}
//		if (accident.getType() != null && accident.getType().getName() != null) {
//			sBuilder.append(" and a.type.nmae like :typeName");
//		}
//		if (accident.getDuty() != null && accident.getDuty().getName() != null) {
//			sBuilder.append(" and a.duty.name like :dutyName");
//		}
//		if (accident.getLevel() != null && accident.getLevel().getName() != null) {
//			sBuilder.append(" and a.level.name like :levelName");
//		}
//		if (accident.getProcessor() != null
//				&& accident.getProcessor().getName() != null) {
//			sBuilder.append(" and a.processor.name like :processorName");
//		}
//		if (accident.getPoliceNo() != null && accident.getPoliceNo() != "") {
//			sBuilder.append(" and a.policeNo like :policeNo");
//		}
//		if (accident.getInitor().getName() != null
//				&& accident.getInitor().getName() != "") {
//			sBuilder.append(" and a.initor.name like :initorName");
//		}
//		if (accident.getInitDateFrom() != null) {
//			sBuilder.append(" and a.initDate >= :initDateFrom");
//		}
//		if (accident.getInitDateTo() != null) {
//			sBuilder.append(" and a.initDate <= :initDateTo");
//		}
//		if (accident.getDeptor().getName() != null
//				&& accident.getDeptor().getName() != "") {
//			sBuilder.append(" and a.deptor.name like :deptorName");
//		}
//		if (accident.getDeptDateFrom() != null) {
//			sBuilder.append(" and a.deptDate >= :deptDateFrom");
//		}
//		if (accident.getDeptDateTo() != null) {
//			sBuilder.append(" and a.deptDate <= :deptDateTo");
//		}
//		if (accident.getCompor().getName() != null
//				&& accident.getCompor().getName() != "") {
//			sBuilder.append(" and a.compor.name like :comporName");
//		}
//		if (accident.getCompDateFrom() != null) {
//			sBuilder.append(" and a.compDate >= :compDateFrom");
//		}
//		if (accident.getCompDateTo() != null) {
//			sBuilder.append(" and a.compDate <= :compDateTo");
//		}
//		if (accident.getLostFrom().intValue() != 0) {
//			sBuilder.append(" and a.lost >= :lostFrom");
//		}
//		if (accident.getLostTo().intValue() != 0) {
//			sBuilder.append(" and a.lost <= :lostTo");
//		}
//		sBuilder.append(" order by a.status");
//		Query query = getSession().createQuery(sBuilder.toString());
//		query.setParameter("branchId", accident.getId().getBranch().getId());
////		if (accident.getId().getNo() != null && accident.getId().getNo() != "") {
////			query.setParameter("no", "%" + accident.getId().getNo() + "%");
////		}
//		if (accident.getDept() != null && accident.getDept().getName() != null) {
//			query.setParameter("deptName", "%" + accident.getDept().getName() + "%");
//		}
//		if (accident.getDateFrom() != null) {
//			query.setParameter("dateFrom", accident.getDateFrom());
//		}
//		if (accident.getDateTo() != null) {
//			query.setParameter("dateTo", accident.getDateTo());
//		}
//		if (accident.getDriver() != null
//				&& accident.getDriver().getWorkerId() != null) {
//			query.setParameter("workerId", "%" + accident.getDriver().getWorkerId()
//					+ "%");
//		}
//		if (accident.getLine() != null && accident.getLine().getName() != null) {
//			query.setParameter("lineName", "%" + accident.getLine().getName() + "%");
//		}
//		if (accident.getBus() != null && accident.getBus().getUseId() != null) {
//			query.setParameter("useId", "%" + accident.getBus().getUseId() + "%");
//		}
//		if (accident.getAddress() != null && accident.getAddress() != "") {
//			query.setParameter("address", "%" + accident.getAddress() + "%");
//		}
//		if (accident.getWeather() != null
//				&& accident.getWeather().getName() != null) {
//			query.setParameter("weatherName", "%" + accident.getWeather().getName()
//					+ "%");
//		}
//		if (accident.getDesc1() != null && accident.getDesc1() != "") {
//			query.setParameter("desc1", "%" + accident.getDesc1() + "%");
//		}
//		if (accident.getDesc2() != null && accident.getDesc2() != "") {
//			query.setParameter("desc2", "%" + accident.getDesc2() + "%");
//		}
//		if (accident.getDesc3() != null && accident.getDesc3() != "") {
//			query.setParameter("desc3", "%" + accident.getDesc3() + "%");
//		}
//		if (accident.getRoadFacility() != null && accident.getRoadFacility() != "") {
//			query
//					.setParameter("roadFacility", "%" + accident.getRoadFacility() + "%");
//		}
//		if (accident.getReason() != null && accident.getReason() != "") {
//			query.setParameter("reason", "%" + accident.getReason() + "%");
//		}
//		if (accident.getType() != null && accident.getType().getName() != null) {
//			query.setParameter("typeName", "%" + accident.getType().getName() + "%");
//		}
//		if (accident.getDuty() != null && accident.getDuty().getName() != null) {
//			query.setParameter("dutyName", "%" + accident.getDuty().getName() + "%");
//		}
//		if (accident.getLevel() != null && accident.getLevel().getName() != null) {
//			query
//					.setParameter("levelName", "%" + accident.getLevel().getName() + "%");
//		}
//		if (accident.getProcessor() != null
//				&& accident.getProcessor().getName() != null) {
//			query.setParameter("processorName", "%"
//					+ accident.getProcessor().getName() + "%");
//		}
//		if (accident.getPoliceNo() != null && accident.getPoliceNo() != "") {
//			query.setParameter("policeNo", "%" + accident.getPoliceNo() + "%");
//		}
//		if (accident.getInitor().getName() != null
//				&& accident.getInitor().getName() != "") {
//			query.setParameter("initorName", "%" + accident.getInitor().getName()
//					+ "%");
//		}
//		if (accident.getInitDateFrom() != null) {
//			query.setParameter("initDateFrom", accident.getDateFrom());
//		}
//		if (accident.getInitDateTo() != null) {
//			query.setParameter("initDateTo", accident.getInitDateTo());
//		}
//		if (accident.getDeptor().getName() != null
//				&& accident.getDeptor().getName() != "") {
//			query.setParameter("deptorName", "%" + accident.getDeptor().getName()
//					+ "%");
//		}
//		if (accident.getDeptDateFrom() != null) {
//			query.setParameter("deptDateFrom", accident.getDeptDateFrom());
//		}
//		if (accident.getDeptDateTo() != null) {
//			query.setParameter("deptDateTo", accident.getDeptDateTo());
//		}
//		if (accident.getCompor().getName() != null
//				&& accident.getCompor().getName() != "") {
//			query.setParameter("comporName", "%" + accident.getCompor().getName()
//					+ "%");
//		}
//		if (accident.getCompDateFrom() != null) {
//			query.setParameter("compDateFrom", accident.getCompDateFrom());
//		}
//		if (accident.getCompDateTo() != null) {
//			query.setParameter("compDateTo", accident.getCompDateTo());
//		}
//		if (accident.getLostFrom().intValue() != 0) {
//			query.setParameter("lostFrom", accident.getLostFrom());
//		}
//		if (accident.getLostTo().intValue() != 0) {
//			query.setParameter("lostTo", accident.getLostTo());
//		}
//		return (List<Accident>) query.list();
		return null;
	}

//	@SuppressWarnings("unchecked")
//	public List<Accident> getAccidents(SecurityLimit limit, Integer isArchive) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select a from Accident a")
//				.append(" left outer join fetch a.bus").append(
//						" left outer join fetch a.bus.type").append(
//						" left outer join fetch a.dept").append(
//						" left outer join fetch a.line").append(
//						" left outer join fetch a.driver").append(
//						" left outer join fetch a.extent").append(
//						" left outer join fetch a.type").append(
//						" left outer join fetch a.level").append(
//						" left outer join fetch a.processor").append(
//						" left outer join fetch a.initor").append(
//						" left outer join fetch a.deptor").append(
//						" left outer join fetch a.compor").append(
//						" left outer join fetch a.weather").append(" where 1 = 1");
//		if (limit != null)
//			sb.append(" and a.id.branch.id = :bid");
//		sb.append(" order by a.id.branch, a.id.no");
//		Query q = getSession().createQuery(sb.toString());
//		if (limit != null)
//			q.setParameter("bid", limit.getLimitBranchId());
//
//		List<Accident> accident = (List<Accident>) q.list();
//		if (accident.size() == 0)
//			return accident;
//
//		List<Accident> result = new ArrayList<Accident>();
//
//		Iterator<Accident> it = accident.iterator();
//		while (it.hasNext()) {
//			Accident tmp = it.next();
//			Integer dept = tmp.getDept().getId();
//			long status = tmp.getStatus().longValue();
//
//			// 0 无权限
//			// 1 车队安全员1 （分公司参数） 事故报告及修改
//			// 2 车队安全员2 （分公司参数） 事故报告及修改、违章录入（导入）
//			// 3 车队审核1（分公司参数） 事故报告及修改、车队审核、违章录入（导入）
//			// 4 车队审核2（分公司参数） 车队审核
//			// 5 基础信息管理
//			// 6 营运部审核
//			// 7 投保管理/提交理赔/赔付录入
//			// 8 投保管理/提交理赔/赔付录入、违章录入
//			// 9 投保管理/提交理赔/赔付录入、基础信息管理
//			// 10 投保管理/提交理赔/赔付录入、基础信息管理、违章录入
//			// 11 投保管理/提交理赔/赔付录入、基础信息管理、违章录入、营运部审核
//			// 12 全部
//			if (limit.getSafetyLimit() >= 1 || limit.getSafetyLimit() <= 2) {
//				if (status == 0 && status == 1 && status == 2) {
//					if (limit.getSafetyLimitDepartId().intValue() == dept.intValue()) {
//						result.add(tmp);
//					}
//				}
//			} else if (limit.getSafetyLimit() == 3) {
//				if (status == 0 || status == 1 || status == 2 || status == 3
//						|| status == 4) {
//					if (limit.getSafetyLimitDepartId().intValue() == dept.intValue())
//						result.add(tmp);
//				}
//			} else if (limit.getSafetyLimit() == 4) {
//				if (status == 1 || status == 2 || status == 3 || status == 4)
//					if (limit.getSafetyLimitDepartId().intValue() == dept.intValue()) {
//						result.add(tmp);
//					}
//			} else if (limit.getSafetyLimit() == 6 || limit.getSafetyLimit() == 11
//					|| limit.getSafetyLimit() == 12) {
//				// 0-archived;1-not archived;2-unlimited
//				if (isArchive == 0) {
//					if (status == 6)
//						result.add(tmp);
//				} else if (isArchive == 1) {
//					if (status == 3 || status == 4 || status == 5)
//						result.add(tmp);
//				} else if (isArchive == 2) {
//					if (status == 3 || status == 4 || status == 5 || status == 6)
//						result.add(tmp);
//				} else {
//					throw new CommonRuntimeException("存档状态发生错误！");
//				}
//			} else {
//				throw new CommonRuntimeException("权限错误！");
//			}
//		}
//
//		return result;
//	}

	/**
	 * 
	 * @param branchId
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public List<Accident> getAccidentsAndPayObject(Integer branchId) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select a from Accident a").append(
//				" left outer join fetch a.dept").append(" left outer join fetch a.bus")
//				.append(" left outer join fetch a.duty").append(
//						" left outer join fetch a.backPsn").append(" where 1 = 1");
//		if (branchId != null)
//			sb.append(" and a.id.branch.id = :bid");
//		Query q = getSession().createQuery(sb.toString());
//		if (branchId != null)
//			q.setParameter("bid", branchId);
//
//		return (List<Accident>) q.list();
//	}

	public void addAccident(Accident accident) {
		getHibernateTemplate().save(accident);
	}

	public void saveAccident(Accident accident) {
		getHibernateTemplate().saveOrUpdate(accident);
//		getHibernateTemplate().merge(accident);
	}
	
	public void deleteAccident(Accident accident) {
		getHibernateTemplate().delete(accident);
	}

	public void updateAccident(Accident accident) {
		getHibernateTemplate().update(accident);
	}
	
	//通过事故编号查id
	public List<Accident> getAccidentByNo(Integer branchId,String no) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select a from Accident a")
				.append(" left outer join fetch a.dept")
				.append(" left outer join fetch a.bus")
				.append(" left outer join fetch a.bus.type")
				.append(" left outer join fetch a.line")
				.append(" left outer join fetch a.driver")
				.append(" left outer join fetch a.extent")
				.append(" left outer join fetch a.type")
				.append(" left outer join fetch a.duty")
				.append(" left outer join fetch a.level")
				.append(" left outer join fetch a.processor")
				.append(" left outer join fetch a.initor")
				.append(" left outer join fetch a.weather")
				.append(" left outer join fetch a.deptor")
				.append(" left outer join fetch a.compor")
				.append(" left outer join fetch a.archor")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and a.id.branch.id = :branchId");
		if(no != null && no != "") sBuilder.append(" and a.no = :no");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(no != null && no != "") query.setParameter("no", no);
		return query.list();
	}
	
	// 下 -------------------------------------
	// 通过 事故 id 查三则理赔
	public List<AccOutGua> getOutGuasByAccId(Integer branchId, Integer accId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select aog from AccOutGua aog")
				.append(" left outer join fetch aog.accident a")
				.append(" left outer join fetch aog.fkGuaReport fkgr")
				.append(" left outer join fetch aog.appPsn")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and aog.id.branch.id = :branchId");
		if(accId != null && accId != 0) sBuilder.append(" and a.id.id = :accId")
												.append(" order by a.id.id");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accId != null && accId != 0) query.setParameter("accId", accId);
		return (List<AccOutGua>)query.list();
	}
	
	// 通过 理赔凭证号 和 序号 查三则 赔付
	public List<AccOutGuaPay> getOutGuaPaysByAppRefNo(Integer branchId, String appRefNo, String appNo) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select ogp from AccOutGuaPay ogp")
				.append(" left outer join fetch ogp.payPsn")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and ogp.id.branch.id = :branchId");
		if(appRefNo != null && appRefNo != "") sBuilder.append(" and ogp.appRefNo = :appRefNo");
		if(appNo != null && appNo != "") sBuilder.append(" and ogp.appNo = :appNo");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(appRefNo != null && appRefNo != "") query.setParameter("appRefNo", appRefNo);
		if(appNo != null && appNo != "") query.setParameter("appNo", appNo);
		return (List<AccOutGuaPay>)query.list();
	}
	
	//	通过 事故 id 查 客伤理赔
	public List<AccInPsnGua> getInPsnGuasByAccId(Integer branchId, Integer accId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select ipg from AccInPsnGua ipg")
				.append(" left outer join fetch ipg.accident a")
				.append(" left outer join fetch ipg.fkGuaReport fkgr")
				.append(" left outer join fetch ipg.appPsn")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and ipg.id.branch.id = :branchId");
		if(accId != null && accId != 0) sBuilder.append(" and a.id.id = :accId")
												.append(" order by a.id.id");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accId != null && accId != 0) query.setParameter("accId", accId);
		return (List<AccInPsnGua>)query.list();
	}
	
	// 通过 理赔凭证号 和 序号 查 客伤 赔付
	public List<AccInPsnGuaPay> getInPsnGuaPaysByAppRefNo(Integer branchId, String appRefNo, String appNo) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select ipgp from AccInPsnGuaPay ipgp")
				.append(" left outer join fetch ipgp.payPsn")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and ipgp.id.branch.id = :branchId");
		if(appRefNo != null && appRefNo != "") sBuilder.append(" and ipgp.appRefNo = :appRefNo");
		if(appNo != null && appNo != "") sBuilder.append(" and ipgp.appNo = :appNo");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(appRefNo != null && appRefNo != "") query.setParameter("appRefNo", appRefNo);
		if(appNo != null && appNo != "") query.setParameter("appNo", appNo);
		return (List<AccInPsnGuaPay>)query.list();
	}
	// 上 --------------------------------------
	
	//根据部门取出事故然后  前台再根据不同的事故编号过滤
	public List<Accident> getAccsByStatus01(Integer branchId,Department dept) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select a from Accident a")
				.append(" left outer join fetch a.dept")
				.append(" left outer join fetch a.bus")
				.append(" left outer join fetch a.bus.type")
				.append(" left outer join fetch a.line")
				.append(" left outer join fetch a.driver")
				.append(" left outer join fetch a.extent")
				.append(" left outer join fetch a.type")
				.append(" left outer join fetch a.duty")
				.append(" left outer join fetch a.level")
				.append(" left outer join fetch a.processor")
				.append(" left outer join fetch a.initor")
				.append(" left outer join fetch a.weather")
				.append(" left outer join fetch a.deptor")
				.append(" left outer join fetch a.compor")
				.append(" where 1 = 1");
		if(branchId != null && branchId != 0) sBuilder.append(" and a.id.branch.id = :branchId");
		if(dept != null){
			if(dept.getId() != 0) sBuilder.append(" and a.dept.id = :deptId");
		}
//		if (statuss != null) {
//			if(statuss.size() > 0) sBuilder.append(" and a.status in (:statuss)");
//		}
		sBuilder.append(" order by a.status,a.id.id");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(dept != null){
			if(dept.getId() != 0) query.setParameter("deptId", dept.getId());
		}
//		if (statuss != null) {
//			if(statuss.size() > 0) query.setParameterList("statuss", statuss);
//		}
		return query.list();
	}
	
	/** 查询只审核且无理赔申请的	和	理赔、赔付信息完整的事故
	 *  需要的信息：事故、三责(支付、理赔、赔付)、客伤(支付、理赔、赔付)
	 * @param branchId, accNo
	 * @return
	 */
	public List<?> getAccsAndObjPsnGuas(Integer branchId,Calendar closeDate) {
		//accOutGuaPay list1
		StringBuilder sBuilder = new StringBuilder ();
		sBuilder.append("select aogp from AccOutGuaPay aogp")
				.append(" left outer join fetch aogp.fkAccOutGua fkaog")
				.append(" left outer join fetch fkaog.accident a")
				.append(" left outer join fetch a.dept")
				.append(" left outer join fetch a.bus")
				.append(" left outer join fetch a.bus.type")
				.append(" left outer join fetch a.line")
				.append(" left outer join fetch a.driver")
//				.append(" left outer join fetch a.extent")    加上这些会查找的很慢
//				.append(" left outer join fetch a.type")
//				.append(" left outer join fetch a.duty")
//				.append(" left outer join fetch a.level")
//				.append(" left outer join fetch a.processor")
//				.append(" left outer join fetch a.initor")
//				.append(" left outer join fetch a.weather")
//				.append(" left outer join fetch a.deptor")
//				.append(" left outer join fetch a.compor")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and aogp.id.branch.id = :bid");
//		if(accNo != null && accNo != "") sBuilder.append(" and a.no = :accNo");
		sBuilder.append(" and (a.status = 2 or (a.status = 3");
		if(closeDate != null) sBuilder.append(" and a.archDate > :closeDate");
		sBuilder.append(")) order by a.id.id");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("bid", branchId);
//		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
		if(closeDate != null) query.setParameter("closeDate", closeDate);
		List<AccOutGuaPay> list1 = new ArrayList<AccOutGuaPay>();
		list1 = (List<AccOutGuaPay>)query.list();
		
		// accInPsnGuaPay list2
		StringBuilder sBuilder2 = new StringBuilder();
		sBuilder2.append("select apgp from AccInPsnGuaPay apgp")
				.append(" left outer join fetch apgp.fkAccInPsnGua fkapg")
				.append(" left outer join fetch fkapg.accident a")
				.append(" left outer join fetch a.dept")
				.append(" left outer join fetch a.bus")
				.append(" left outer join fetch a.bus.type")
				.append(" left outer join fetch a.line")
				.append(" left outer join fetch a.driver")
//				.append(" left outer join fetch a.extent")
//				.append(" left outer join fetch a.type")
//				.append(" left outer join fetch a.duty")
//				.append(" left outer join fetch a.level")
//				.append(" left outer join fetch a.processor")
//				.append(" left outer join fetch a.initor")
//				.append(" left outer join fetch a.weather")
//				.append(" left outer join fetch a.deptor")
//				.append(" left outer join fetch a.compor")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder2.append(" and apgp.id.branch.id = :bid");
//		if(accNo != null && accNo != "") sBuilder2.append(" and a.no = :accNo");
		sBuilder2.append(" and (a.status = 2 or (a.status = 3");
		if(closeDate != null) sBuilder2.append("  and a.archDate > :closeDate");
		sBuilder2.append(")) order by a.id.id");
		Query query1 = getSession().createQuery(sBuilder2.toString());
		if(branchId != null && branchId != 0) query1.setParameter("bid", branchId);
//		if(accNo != null && accNo != "") query1.setParameter("accNo", accNo);
		if(closeDate != null) query1.setParameter("closeDate", closeDate);
		List<AccInPsnGuaPay> list2 = new ArrayList<AccInPsnGuaPay>();
		list2 = (List<AccInPsnGuaPay>)query1.list();
		
		// accOutGua list3
		StringBuilder sBuilder3 = new StringBuilder();
		sBuilder3.append("select aog from AccOutGua aog")
				.append(" left outer join fetch aog.accident ao")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder3.append(" and aog.id.branch.id = :bid");
		sBuilder3.append(" and (ao.status = 2 or (ao.status = 3");
		if(closeDate != null) sBuilder3.append(" and ao.archDate > :closeDate");
		sBuilder3.append(")) order by ao.id.id");
		Query query2 = getSession().createQuery(sBuilder3.toString());
		if(branchId != null && branchId != 0) query2.setParameter("bid", branchId);
		if(closeDate != null) query2.setParameter("closeDate", closeDate);
		List<AccOutGua> list3 = new ArrayList<AccOutGua>();
		list3 = (List<AccOutGua>)query2.list();
		
		// accInPsnGua list4
		StringBuilder sBuilder5 = new StringBuilder();
		sBuilder5.append("select apg from AccInPsnGua apg")
				.append(" left outer join fetch apg.accident ap")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder5.append(" and apg.id.branch.id = :bid");
		sBuilder5.append(" and (ap.status = 2 or (ap.status = 3");
		if(closeDate != null) sBuilder5.append(" and ap.archDate > :closeDate");
		sBuilder5.append(")) order by ap.id.id");
		Query query4 = getSession().createQuery(sBuilder5.toString());
		if(branchId != null && branchId != 0) query4.setParameter("bid", branchId);
		if(closeDate != null) query4.setParameter("closeDate", closeDate);
		List<AccInPsnGua> list4 = new ArrayList<AccInPsnGua>();
		list4 = (List<AccInPsnGua>)query4.list();
		
		// accident status = 2 , 3  list5
		StringBuilder sBuilder4 = new StringBuilder();
		sBuilder4.append("select a from Accident a")
				.append(" left outer join fetch a.dept")
				.append(" left outer join fetch a.bus")
				.append(" left outer join fetch a.bus.type")
				.append(" left outer join fetch a.line")
				.append(" left outer join fetch a.driver")
//				.append(" left outer join fetch a.extent")
//				.append(" left outer join fetch a.type")
//				.append(" left outer join fetch a.duty")
//				.append(" left outer join fetch a.level")
//				.append(" left outer join fetch a.processor")
//				.append(" left outer join fetch a.initor")
//				.append(" left outer join fetch a.weather")
//				.append(" left outer join fetch a.deptor")
//				.append(" left outer join fetch a.compor")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder4.append(" and a.id.branch.id = :bid");
//		if(accNo != null && accNo != "") sBuilder4.append(" and a.no = :accNo");
		sBuilder4.append(" and (a.status = 2 or (a.status = 3");
		if(closeDate != null) sBuilder4.append(" and a.archDate > :closeDate");
		sBuilder4.append(")) order by a.id.id");
		Query query3 = getSession().createQuery(sBuilder4.toString());
		if(branchId != null && branchId != 0) query3.setParameter("bid", branchId);
//		if(accNo != null && accNo != "") query3.setParameter("accNo", accNo);
		if(closeDate != null) query3.setParameter("closeDate", closeDate);
		List<Accident> list5 = new ArrayList<Accident>();
		list5 = (List<Accident>)query3.list();
		
		// 组装数据 AccOutGuaPay(list1),AccInPsnGuaPay(list2),AccOutGua(list3),AccInPsnGua(list4),Accident(list5)
		List<Accident> accList = new ArrayList<Accident>();
		List<AccOutGuaPay> aogpList = new ArrayList<AccOutGuaPay>();
		List<AccInPsnGuaPay> aipgpList = new ArrayList<AccInPsnGuaPay>();
		for (Accident accident : list5) {
			boolean boolean1 = false;
			List<AccOutGuaPay> aogpList2 = new ArrayList<AccOutGuaPay>();
			List<AccInPsnGuaPay> aipgpList2 = new ArrayList<AccInPsnGuaPay>();
			for (AccOutGua accOutGua : list3) 
			{
				if (accident.getAccidentId().equals(accOutGua.getAccId())) 
				{
					boolean1 = true;
					for (AccOutGuaPay accOutGuaPay : list1) 
					{
						if (accOutGua.getId().getNo().equals(accOutGuaPay.getAppNo())
								&& accOutGua.getId().getRefNo().equals(
										accOutGuaPay.getAppRefNo())) {
							boolean1 = false;
							aogpList2.add(accOutGuaPay);
							break;
						}
					}
					if (boolean1)
						break;
				}
			}
			if(boolean1)
				continue;
			for (AccInPsnGua accInPsnGua : list4) 
			{
				if (accident.getAccidentId().equals(accInPsnGua.getAccId())) 
				{
					boolean1 = true;
					for (AccInPsnGuaPay accInPsnGuaPay : list2) 
					{
						if (accInPsnGua.getId().getNo().equals(accInPsnGuaPay.getAppNo())
								&& accInPsnGua.getId().getRefNo().equals(
										accInPsnGuaPay.getAppRefNo())) {
							boolean1 = false;
							aipgpList2.add(accInPsnGuaPay);
							break;
						}
					}
					if (boolean1)
						break;
				}
			}
			if(boolean1)
				continue;
//			ArrayUtils.addAll(aogpList.toArray(), aogpList2.toArray());
//			ArrayUtils.addAll(aipgpList.toArray(), aipgpList2.toArray());
			for(AccOutGuaPay accOutGuaPay : aogpList2)
				aogpList.add(accOutGuaPay);
			for(AccInPsnGuaPay accInPsnGuaPay : aipgpList2)
				aipgpList.add(accInPsnGuaPay);
			accList.add(accident);
		}
		List aopList = new ArrayList();
		aopList.add(aogpList);
		aopList.add(aipgpList);
		aopList.add(accList);
		return aopList;
	}
	
	/**
	 * 事故树1,old
	 * @param branchId
	 * @return
	 */
	public List<Accident> getAccidents(SecurityLimit limit, String[] orderColumns) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Accident a")
						.append(" left outer join fetch a.dept d")
						.append(" left outer join fetch a.bus b")
						.append(" left outer join fetch a.bus.type")
						.append(" left outer join fetch a.line")
						.append(" left outer join fetch a.driver")
						.append(" left outer join fetch a.extent")
						.append(" left outer join fetch a.type")
						.append(" left outer join fetch a.duty")
						.append(" left outer join fetch a.level")
						.append(" left outer join fetch a.processor")
						.append(" left outer join fetch a.initor")
						.append(" left outer join fetch a.weather")
						.append(" left outer join fetch a.deptor")
						.append(" left outer join fetch a.compor")
						.append(" where 1 = 1");
		sb.append(" and a.id.branch.id = :branchId");
//		if(limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 5) sb.append(" and d.id = :did");
		sb.append(" order by");
		if (orderColumns != null && orderColumns.length > 0) {
			for (int i = 0; i < orderColumns.length; i++) {
				sb.append(" a.").append(orderColumns[i]).append(",");
			}
		}
		sb.append(" b.useId");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("branchId", limit.getBranchId());
//		if(limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 5) q.setParameter("did", limit.getSafetyLimitDepartId());
		return (List<Accident>)q.list();
	}
	
	/**
	 * 事故树2
	 * @param limit,dateFrom
	 * @return
	 */
	public List getAccsByDateFrom(SecurityLimit limit, Calendar dateFrom) {
//		Long time1 = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Accident a")
			.append(" left outer join fetch a.dept d")
			.append(" left outer join fetch a.bus b")
			.append(" left outer join fetch a.line l")
			.append(" left outer join fetch a.driver")
			.append(" where 1=1");
		sb.append(" and a.id.branch.id = :branchId")
			.append(" and a.date >= :dateFrom");
		Query query = getSession().createQuery(sb.toString());
		query.setParameter("branchId", limit.getBranchId());
		query.setParameter("dateFrom", dateFrom);
		List<Accident> list = query.list();
//		System.out.println("事故列表数据库查询时间："+(System.currentTimeMillis()-time1));
		return list;
//		for(EquOnline equOnline : equOnlineList){
//			for(Accident accident : accList){
//				if(equOnline.getEquipmentId() == accident.getBusId()
//							&& equOnline.getDepartId() == accident.getDeptId()
//							&& (equOnline.getLine() == null 
//							|| (equOnline.getLine() != null && equOnline.getLineId() == accident.getLineId())))
//					equOnline.addAcc(accident);
//			}
//		}
//		return equOnlineList;
	}
	/**
	 * get 所有0状态的事故撞击人员，客伤人员，撞击对象列表
	 * @return
	 */
	public List getPOPs(SecurityLimit limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Accident a")
			.append(" where 1=1");
		if(limit != null) sb.append(" and a.id.branch.id = :branchId");
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) sb.append(" and a.dept.id = :did");
		sb.append(" and (a.status = 0 or a.status = 1)");
		sb.append(" order by a.no");
		Query q = getSession().createQuery(sb.toString());
		if(limit != null) q.setParameter("branchId", limit.getBranchId());
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) q.setParameter("did", limit.getSafetyLimitDepartId());
		List<Accident> accs = (List<Accident>)q.list();
		//组织对象列表
		List<AccOutPsn> aops = new ArrayList<AccOutPsn>();
		List<AccInPsn> aips = new ArrayList<AccInPsn>();
		List<AccOutObj> aoos = new ArrayList<AccOutObj>();
		AccOutPsn accOutPsn;
		AccInPsn accInPsn;
		AccOutObj accOutObj;
		for(Accident accident : accs){
			if(accident.getAccOutPsns().size() <= 0) {
				accOutPsn = new AccOutPsn();
				accOutPsn.setId(new AccOutPsnPK());
				accOutPsn.setFkAccident(accident);
				aops.add(accOutPsn);
			} else {
				for(AccOutPsn outPsn : accident.getAccOutPsns()){
					outPsn.setFkAccident(accident);
					aops.add(outPsn);
				}
			}
			if (accident.getAccInPsns().size() <= 0) {
				accInPsn = new AccInPsn();
				accInPsn.setId(new AccInPsnPK());
				accInPsn.setFkAccident(accident);
				aips.add(accInPsn);
			} else {
				for(AccInPsn inPsn : accident.getAccInPsns()){
					inPsn.setFkAccident(accident);
					aips.add(inPsn);
				}
			}
			if (accident.getAccOutObjs().size() <= 0) {
				accOutObj = new AccOutObj();
				accOutObj.setId(new AccOutObjPK());
				accOutObj.setFkAccident(accident);
				aoos.add(accOutObj);
			} else {
				for(AccOutObj outObj : accident.getAccOutObjs()){
					outObj.setFkAccident(accident);
					aoos.add(outObj);
				}
			}
		}
		List list = new ArrayList();
		Collections.sort(aops, Constants.ID_COMPARATOR);
		Collections.sort(aips, Constants.ID_COMPARATOR);
		Collections.sort(aoos, Constants.ID_COMPARATOR);
		list.add(aops);
		list.add(aips);
		list.add(aoos);
		return list;
	}
	
	/**
	 * get 人员赔偿列表、撞击对象赔偿列表、事故车辆自损赔偿列表，accident.status = 0\1,
	 * @param branchId
	 * @return
	 */
	public List getPayList(SecurityLimit limit) {
		StringBuilder sb = new StringBuilder();
//		sb.append("select aopp from AccOutPsnPay aopp")
//			.append(" left outer join fetch aopp.fkAccOutPsn aop")
//			.append(" left outer join fetch aop.fkAccident a")
//			.append(" where 1=1");
//		if(branchId != null && branchId != 0) sb.append(" and aopp.id.branch.id = :branchId");
//		sb.append(" and a.status = 0 or a.status = 1")
//			.append(" order by a.no, aop.id.no, aopp.id.payDate");
		sb.append("select aop from AccOutPsn aop")
			.append(" left outer join fetch aop.fkAccident a")
			.append(" where 1=1");
		if(limit != null) sb.append(" and aop.id.branch.id = :branchId");
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) sb.append(" and a.dept.id = :did");
		sb.append(" and (a.status = 0 or a.status = 1)")
			.append(" order by a.no, aop.id.no");
		Query query = getSession().createQuery(sb.toString());
		if(limit != null) query.setParameter("branchId", limit.getBranchId());
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) query.setParameter("did", limit.getSafetyLimitDepartId());
		List<AccOutPsn> aops = (List<AccOutPsn>)query.list();
		List<AccOutPsnPay> aopps = new ArrayList<AccOutPsnPay>();
		AccOutPsnPay accOutPsnPay;
		for (AccOutPsn accOutPsn : aops) {
			if (accOutPsn.getAccOutPsnPays().size() <= 0) {
				accOutPsnPay = new AccOutPsnPay();
				accOutPsnPay.setId(new AccOutPsnPayPK());
				accOutPsnPay.setFkAccOutPsn(accOutPsn);
				aopps.add(accOutPsnPay);
			} else {
				for (AccOutPsnPay accOutPsnPay2 : accOutPsn.getAccOutPsnPays()) {
					accOutPsnPay2.setFkAccOutPsn(accOutPsn);
					aopps.add(accOutPsnPay2);
				}
			}
		}
		sb.delete(0, sb.length());
//		sb.append("select aipp from AccInPsnPay aipp")
//			.append(" left outer join fetch aipp.fkAccInPsn aip")
//			.append(" left outer join fetch aip.fkAccident a")
//			.append(" where 1=1");
//		if(branchId != null && branchId != 0) sb.append(" and aipp.id.branch.id = :branchId");
//		sb.append(" and a.status = 0 or a.status = 1")
//			.append(" order by a.no, aip.id.no, aipp.id.payDate");
		sb.append("select aip from AccInPsn aip")
			.append(" left outer join fetch aip.fkAccident a")
			.append(" where 1=1");
		if(limit != null) sb.append(" and aip.id.branch.id = :branchId");
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) sb.append(" and a.dept.id = :did");
		sb.append(" and (a.status = 0 or a.status = 1)")
			.append(" order by a.no, aip.id.no");
		Query query1 = getSession().createQuery(sb.toString());
		if(limit != null) query1.setParameter("branchId", limit.getBranchId());
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) query1.setParameter("did", limit.getSafetyLimitDepartId());
		List<AccInPsn> aips = (List<AccInPsn>)query1.list();
		List<AccInPsnPay> aipps = new ArrayList<AccInPsnPay>();
		AccInPsnPay accInPsnPay;
		for(AccInPsn accInPsn : aips){
			if (accInPsn.getAccInPsnPays().size() <= 0) {
				accInPsnPay = new AccInPsnPay();
				accInPsnPay.setId(new AccInPsnPayPK());
				accInPsnPay.setFkAccInPsn(accInPsn);
				aipps.add(accInPsnPay);
			} else {
				for(AccInPsnPay accInPsnPay2 : accInPsn.getAccInPsnPays()){
					accInPsnPay2.setFkAccInPsn(accInPsn);
					aipps.add(accInPsnPay2);
				}
			}
		}
		sb.delete(0, sb.length());
		sb.append("select aoo from AccOutObj aoo")
			.append(" left outer join fetch aoo.fkAccident a")
			.append(" where 1=1");
		if(limit != null) sb.append(" and aoo.id.branch.id = :branchId");
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) sb.append(" and a.dept.id = :did");
		sb.append(" and (a.status = 0 or a.status = 1)")
			.append(" order by a.no, aoo.id.no, aoo.payDate");
		Query query2 = getSession().createQuery(sb.toString());
		if(limit != null) query2.setParameter("branchId", limit.getBranchId());
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) query2.setParameter("did", limit.getSafetyLimitDepartId());
		List<AccOutObj> aoos = (List<AccOutObj>)query2.list();
		sb.delete(0, sb.length());
		sb.append("select a from Accident a")
			.append(" left outer join fetch a.bus b")
			.append(" where 1=1");
		if(limit != null) sb.append(" and a.id.branch.id = :branchId");
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) sb.append(" and a.dept.id = :did");
		sb.append(" and (a.status = 0 or a.status = 1)")
			.append(" order by a.no");
		Query query3 = getSession().createQuery(sb.toString());
		if(limit != null) query3.setParameter("branchId", limit.getBranchId());
		if(limit != null && limit.getSafetyLimit() >= 1 && limit.getSafetyLimit() <= 3) query3.setParameter("did", limit.getSafetyLimitDepartId());
		List<Accident> accs = (List<Accident>)query3.list();
		List list = new ArrayList();
		list.add(aopps);
		list.add(aipps);
		list.add(aoos);
		list.add(accs);
		return list;
	}
	
	/**
	 * 单车、单人 事故查询
	 * @param branchId
	 * @return
	 */
	public List getAccsByUIdOrWId(Integer branchId, String authNo, String workerId, Calendar dateFrom, Calendar dateTo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Accident a")
						.append(" left outer join fetch a.dept d")
						.append(" left outer join fetch a.bus b")
						.append(" left outer join fetch a.bus.type")
						.append(" left outer join fetch a.line")
						.append(" left outer join fetch a.driver dr")
						.append(" left outer join fetch a.extent")
						.append(" left outer join fetch a.type")
						.append(" left outer join fetch a.duty")
						.append(" left outer join fetch a.level")
						.append(" left outer join fetch a.processor")
						.append(" left outer join fetch a.initor")
						.append(" left outer join fetch a.weather")
						.append(" left outer join fetch a.deptor")
						.append(" left outer join fetch a.compor")
						.append(" where 1 = 1");
		if(branchId != null && branchId != 0)sb.append(" and a.id.branch.id = :branchId");
		if(authNo != null && authNo != "") sb.append(" and b.authNo = :authNo");
		if(workerId != null && workerId != "") sb.append(" and dr.workerId = :workerId");
		if(dateFrom != null) sb.append(" and a.date >= :dateFrom");
		if(dateTo != null) sb.append(" and a.date <= :dateTo");
		sb.append(" order by a.no,a.date");
		Query q = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) q.setParameter("branchId", branchId);
		if(authNo != null && authNo != "") q.setParameter("authNo", authNo);
		if(workerId != null && workerId != "") q.setParameter("workerId", workerId);
		if(dateFrom != null) q.setParameter("dateFrom", dateFrom);
		if(dateTo != null) q.setParameter("dateTo", dateTo);
		List<Accident> accList = q.list();
		List list = new ArrayList();
		list.add(accList);
		if(accList.size() <= 0)
			return list;
		//事故idList
		List<Integer> accIdList = new ArrayList<Integer>();
		for(Accident accident : accList){
			accIdList.add(accident.getAccidentId());
		}
		
	// 三责理赔，accOutGua aogList
		sb.delete(0, sb.length());
		sb.append("select aog from AccOutGua aog")
				.append(" left outer join fetch aog.accident ao")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and aog.id.branch.id = :bid");
		sb.append(" and ao.id.id in (:accIdList)");
		Query query2 = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query2.setParameter("bid", branchId);
		query2.setParameterList("accIdList", accIdList);
		List<AccOutGua> aogList = new ArrayList<AccOutGua>();
		aogList = (List<AccOutGua>)query2.list();
//		//三责理赔凭证号aogRefNoList
//		List<String> aogRefNoList = new ArrayList<String>();
//		//三责理赔凭证序号aogNoList
//		List<String> aogNoList = new ArrayList<String>();
//		for(AccOutGua accOutGua : aogList){
//			aogRefNoList.add(accOutGua.getId().getRefNo());
//			aogNoList.add(accOutGua.getId().getNo());
//		}
		// 客伤理赔 ，accInPsnGua aipgList
		sb.delete(0, sb.length());
		sb.append("select apg from AccInPsnGua apg")
				.append(" left outer join fetch apg.accident ap")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and apg.id.branch.id = :bid");
		sb.append(" and ap.id.id in (:accIdList)");
		Query query4 = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query4.setParameter("bid", branchId);
		query4.setParameterList("accIdList", accIdList);
		List<AccInPsnGua> aipgList = new ArrayList<AccInPsnGua>();
		aipgList = (List<AccInPsnGua>)query4.list();
//		//客伤理赔凭证号
//		List<String> aipgRefNoList = new ArrayList<String>();
//		//客伤理赔凭证序号
//		List<String> aipgNoList = new ArrayList<String>();
//		for(AccInPsnGua accInPsnGua : aipgList){
//			aipgRefNoList.add(accInPsnGua.getId().getRefNo());
//			aipgNoList.add(accInPsnGua.getId().getNo());
//		}
		//三责赔付查询,aogpList
		sb.delete(0, sb.length());
		sb.append("select aogp from AccOutGuaPay aogp")
				.append(" left outer join fetch aogp.fkAccOutGua fkaog")
				.append(" left outer join fetch fkaog.accident a")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and aogp.id.branch.id = :bid");
		sb.append(" and a.id.id in (:accIdList)");
		Query query = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query.setParameter("bid", branchId);
		query.setParameterList("accIdList", accIdList);
		List<AccOutGuaPay> aogpList = new ArrayList<AccOutGuaPay>();
		aogpList = (List<AccOutGuaPay>)query.list();
		
		// 客伤赔付查询 accInPsnGuaPay aipgpList
		sb.delete(0, sb.length());
		sb.append("select apgp from AccInPsnGuaPay apgp")
				.append(" left outer join fetch apgp.fkAccInPsnGua fkapg")
				.append(" left outer join fetch fkapg.accident a")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and apgp.id.branch.id = :bid");
		sb.append(" and a.id.id in (:accIdList)");
		Query query1 = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query1.setParameter("bid", branchId);
		query1.setParameterList("accIdList", accIdList);
		List<AccInPsnGuaPay> aipgpList = new ArrayList<AccInPsnGuaPay>();
		aipgpList = (List<AccInPsnGuaPay>)query1.list();
		
		list.add(aogList);
		list.add(aipgList);
		list.add(aogpList);
		list.add(aipgpList);
		return list;
	}
	
	/**
	 * 事故所有条件查询 
	 * @param branchId
	 * @return
	 */
	public List getAccsByAll(SecurityLimit limit, Map qo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Accident a")
						.append(" left outer join fetch a.dept d")
						.append(" left outer join fetch a.bus b")
						.append(" left outer join fetch a.bus.type")
						.append(" left outer join fetch a.line l")
						.append(" left outer join fetch a.driver dr")
						.append(" left outer join fetch a.extent ex")
						.append(" left outer join fetch a.type ty")
						.append(" left outer join fetch a.duty du")
						.append(" left outer join fetch a.level le")
						.append(" left outer join fetch a.processor pr")
						.append(" left outer join fetch a.initor i")
						.append(" left outer join fetch a.weather w")
						.append(" left outer join fetch a.deptor do")
						.append(" left outer join fetch a.compor co")
						.append(" where a.id.branch.id = :branchId");
		String key, k1, k2, k3;
		Object value;
		int m1, m2;
		Map<String, String> tables = new Hashtable<String, String>();
		tables.put("dept", "d");
		tables.put("bus", "b");
		tables.put("line", "l");
		tables.put("driver", "dr");
		tables.put("extent", "ex");
		tables.put("type", "ty");
		tables.put("duty", "du");
		tables.put("level", "le");
		tables.put("processor", "pr");
		tables.put("initor", "i");
		tables.put("weather", "w");
		tables.put("deptor", "do");
		tables.put("compor", "co");
		Integer maim=4;
		if (qo != null) {
			/**
			 * birthday_from ==> p.birthday >= :birthday_from
			 * workerId ==> p.workerId like :workerId
			 * chkGroup.id ==> g.id = :chkGroup_id
			 */
			if(qo.containsKey("maim") && qo.get("maim") != null)
			{
				maim = Integer.valueOf(qo.get("maim").toString());
				qo.remove("maim");
			}
				
			for (Iterator it = qo.keySet().iterator(); it.hasNext(); ) {
				key = (String) it.next();
				value = qo.get(key);
				if(value == null) continue;
				m1 = key.indexOf('.');
				m2 = key.indexOf('_');
				if (m1 > 0) { // Process foriegn key object alias
					k1 = key.substring(0, m1);
					k1 = tables.get(k1) + "." + key.substring(m1+1);
				} else {
					k1 = (m2 > 0) ? "a." + key.substring(0, m2) : "a." + key;
				}
				if (m2 > 0 && value instanceof Date) {
					k2 = key.substring(m2+1);
					k2 = "from".equals(k2) ? " >= " : " <= ";
				} else if (value instanceof String) {
					k2 = " like ";
				} else if (value instanceof Number) {
					k2 = " = ";
				} else {
					k2 = " = ";
				}
				k3 = key.replace('.', '_');
				sb.append(" and ").append(k1).append(k2).append(":").append(k3);
			}
		}
		sb.append(" order by a.id.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("branchId", limit.getBranchId());
		if (qo != null) {
			for (Iterator it = qo.keySet().iterator(); it.hasNext(); ) {
				key = (String) it.next();
				value = qo.get(key);
				if(value == null) continue;
				key = key.replace('.', '_');
				if (value instanceof Date) {
					value = ObjectUtil.toCalendar(value);
				}
				else if (value instanceof String) {
					value = "%" + value + "%";
				}
				q.setParameter(key, value);
			}
		}
		List<Accident> accList = q.list();
		List<Accident> accList2 = new ArrayList<Accident>();
		for (Accident accident : accList) {
			accList2.add(accident);
		}
		List<Accident> accs = new ArrayList<Accident>();
		if(maim == 0 || maim == 1){
			for(Accident accident : accList)
			{
				boolean status = false;
				for(AccOutPsn accOutPsn : accident.getAccOutPsns())
				{
					if (accOutPsn.getStatus().equals(maim)) {
						status = true;
						break;
					}
				}
				if (status) {
					accs.add(accident);
					continue;
				}
				for(AccInPsn accInPsn : accident.getAccInPsns())
				{
					if (accInPsn.getStatus().equals(maim)) {
						status = true;
						break;
					}
				}
				if (status) {
					accs.add(accident);
					continue;
				}
			}
			accList.clear();
			for(Accident accident : accs)
				accList.add(accident);
		}
		if(maim == 2)
		{
			accList.clear();
			for (Accident accident : accList2) {
				if (accident.getAccOutPsns().size() <= 0 && accident.getAccInPsns().size() <= 0) {
					accList.add(accident);
				}
			}
		}
		List list = new ArrayList();
		list.add(accList);
		if(accList.size() <= 0)
			return list;
		//事故idList
		List<Integer> accIdList = new ArrayList<Integer>();
		for(Accident accident : accList){
			accIdList.add(accident.getAccidentId());
		}
		//三责赔付查询,aogpList
		sb.delete(0, sb.length());
		sb.append("select aogp from AccOutGuaPay aogp")
				.append(" left outer join fetch aogp.fkAccOutGua fkaog")
				.append(" left outer join fetch fkaog.accident a")
				.append(" where 1=1");
		sb.append(" and aogp.id.branch.id = :bid");
		sb.append(" and a.id.id in (:accIdList)");
		Query query = getSession().createQuery(sb.toString());
		query.setParameter("bid", limit.getBranchId());
		query.setParameterList("accIdList", accIdList);
		List<AccOutGuaPay> aogpList = new ArrayList<AccOutGuaPay>();
		aogpList = (List<AccOutGuaPay>)query.list();
		
		// 客伤赔付查询 accInPsnGuaPay aipgpList
		sb.delete(0, sb.length());
		sb.append("select apgp from AccInPsnGuaPay apgp")
				.append(" left outer join fetch apgp.fkAccInPsnGua fkapg")
				.append(" left outer join fetch fkapg.accident a")
				.append(" where 1=1");
		sb.append(" and apgp.id.branch.id = :bid");
		sb.append(" and a.id.id in (:accIdList)");
		Query query1 = getSession().createQuery(sb.toString());
		query1.setParameter("bid", limit.getBranchId());
		query1.setParameterList("accIdList", accIdList);
		List<AccInPsnGuaPay> aipgpList = new ArrayList<AccInPsnGuaPay>();
		aipgpList = (List<AccInPsnGuaPay>)query1.list();
		
		list.add(aogpList);
		list.add(aipgpList);
		
		return list;
	}
//==================================== AccType ====================================

	@SuppressWarnings("unchecked")
	public List<AccType> getAccTypes(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AccType a where 1 = 1");
		if (branchId != null)
			sb.append(" and a.branch.id = :bid");
		sb.append(" order by a.branch, a.id");
		Query q = getSession().createQuery(sb.toString());
		if (branchId != null)
			q.setParameter("bid", branchId);
		return (List<AccType>) q.list();
	}

	public void addAccType(AccType accType) {
		getHibernateTemplate().save(accType);
	}

	public void saveAccType(AccType accType) {
		getHibernateTemplate().saveOrUpdate(accType);
	}

	public void updateAccType(AccType accType) {
		getHibernateTemplate().update(accType);
	}

	public void deleteAccType(AccType accType) {
		getHibernateTemplate().delete(accType);
	}

//==================================== AccLevel ====================================

	@SuppressWarnings("unchecked")
	public List<AccLevel> getAccLevels(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select l from AccLevel l").append(
				" left outer join fetch l.branch b").append(
				" where b.id = :id");
		sb.append(" order by l.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<AccLevel>)q.list();
	}
	
	public void addAccLevel(AccLevel accLevel){
		getHibernateTemplate().save(accLevel);
	}
	
	public void saveAccLevel(AccLevel accLevel){
		getHibernateTemplate().saveOrUpdate(accLevel);
	}
	
	public void updateAccLevel(AccLevel accLevel){
		getHibernateTemplate().update(accLevel);
	}
	
	public void deleteAccLevel(AccLevel accLevel){
		getHibernateTemplate().delete(accLevel);
	}

//==================================== AccDuty ====================================

	@SuppressWarnings("unchecked")
	public List<AccDuty> getAccDutys(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select l from AccDuty l").append(
				" left outer join fetch l.branch b").append(
				" where b.id = :id");
		sb.append(" order by l.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<AccDuty>)q.list();
	}
	
	public void addAccDuty(AccDuty accDuty){
		getHibernateTemplate().save(accDuty);
	}
	
	public void saveAccDuty(AccDuty accDuty){
		getHibernateTemplate().saveOrUpdate(accDuty);
	}
	
	public void updateAccDuty(AccDuty accDuty){
		getHibernateTemplate().update(accDuty);
	}
	
	public void deleteAccDuty(AccDuty accDuty){
		getHibernateTemplate().delete(accDuty);
	}

//==================================== AccExtent ====================================

	@SuppressWarnings("unchecked")
	public List<AccExtent> getAccExtents(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select l from AccExtent l").append(
				" left outer join fetch l.branch b").append(
				" where b.id = :id");
		sb.append(" order by l.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<AccExtent>)q.list();
	}
	
	public void addAccExtent(AccExtent accExtent){
		getHibernateTemplate().save(accExtent);
	}
	
	public void saveAccExtent(AccExtent accExtent){
		getHibernateTemplate().saveOrUpdate(accExtent);
	}
	
	public void updateAccExtent(AccExtent accExtent){
		getHibernateTemplate().update(accExtent);
	}
	
	public void deleteAccExtent(AccExtent accExtent){
		getHibernateTemplate().delete(accExtent);
	}

//==================================== AccPsn ====================================

//	@SuppressWarnings("unchecked")
//	public List<AccPsn> getAccPsns(Integer branchId, String accidentNo) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(" select a from AccPsn a").append(
//				" left outer join fetch a.branch").append(" where 1 = 1");
//		if (branchId != null)
//			sb.append(" and a.branch.id = :bid");
//		if (accidentNo != null)
//			sb.append(" and a.no = :no");
//		sb.append(" order by a.branch, a.no");
//		Query q = getSession().createQuery(sb.toString());
//		if (branchId != null)
//			q.setParameter("bid", branchId);
//		if (accidentNo != null)
//			q.setParameter("no", accidentNo);
//		return (List<AccPsn>) q.list();
//	}

//	@SuppressWarnings("unchecked")
//	public List getAccPsnsAndAccident(Integer branchId) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select a, b from AccPsn a")
//			.append(" , Accident b")
//			.append(" left outer join fetch b.dept")
//			.append(" left outer join fetch b.bus")
//			.append(" where a.branch = b.id.branch")
//			.append(" and a.no = b.id.no")
//			.append(" and a.status <> 3")		// 3撞伤属于三责
//			.append(" and a.status <> 4")		// 4撞死属于三责
//			.append(" and b.status = 3")		// 3:车队审批通过
//		;
//		if (branchId != null) sb.append(" and a.branch.id = :bid");
//		Query q = getSession().createQuery(sb.toString());
//		if (branchId != null) q.setParameter("bid", branchId);
//		return q.list();
//	}

//	public void addAccPsn(AccPsn accPsn) {
//		getHibernateTemplate().save(accPsn);
//	}
//
//	public void saveAccPsn(AccPsn accPsn) {
//		getHibernateTemplate().saveOrUpdate(accPsn);
//	}
//
//	public void updateAccPsn(AccPsn accPsn) {
//		getHibernateTemplate().update(accPsn);
//	}
//
//	public void deleteAccPsn(AccPsn accPsn) {
//		getHibernateTemplate().delete(accPsn);
//	}

//==================================== AccPayPsn ====================================

//	public void addAccPayPsn(AccPayPsn accPayPsn) {
//		getHibernateTemplate().save(accPayPsn);
//	}
//
//	public void saveAccPayPsn(AccPayPsn accPayPsn) {
//		getHibernateTemplate().saveOrUpdate(accPayPsn);
//	}
//
//	public void updateAccPayPsn(AccPayPsn accPayPsn) {
//		getHibernateTemplate().update(accPayPsn);
//	}
//
//	public void deleteAccPayPsn(AccPayPsn accPayPsn) {
//		getHibernateTemplate().delete(accPayPsn);
//	}

//==================================== AccObject ====================================

	@SuppressWarnings("unchecked")
	public List<AccObject> getAccObjects(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select l from AccObject l").append(
				" left outer join fetch l.branch b").append(
				" where b.id = :id");
		sb.append(" order by l.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<AccObject>)q.list();
	}
	
	public void addAccObject(AccObject accObject){
		getHibernateTemplate().save(accObject);
	}
	
	public void saveAccObject(AccObject accObject){
		getHibernateTemplate().saveOrUpdate(accObject);
	}
	
	public void updateAccObject(AccObject accObject){
		getHibernateTemplate().update(accObject);
	}
	
	public void deleteAccObject(AccObject accObject){
		getHibernateTemplate().delete(accObject);
	}

//==================================== AccPayObject ====================================

//	@SuppressWarnings("unchecked")
//	public List<AccPayObject> getAccPayObjects(Integer branchId) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select apo from AccPayObject apo")
//			.append(" left outer join apo.branch")
//			.append(" where 1 = 1")
//		;
//		if (branchId != null) sb.append(" and apo.branch.id = :bid");
//		Query q = getSession().createQuery(sb.toString());
//		if (branchId != null) q.setParameter("bid", branchId);
//		return (List<AccPayObject>) q.list();
//	}
	
//	@SuppressWarnings("unchecked")
//	public List getAccPayObjectsAndAccident(Integer branchId) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select a, b from AccPayObject a")
//			.append(" , Accident b")
//			.append(" left outer join fetch b.dept")
//			.append(" left outer join fetch b.bus")
//			.append(" where a.branch = b.id.branch")
//			.append(" and a.no = b.id.no")
//			.append(" and b.status = 3")		// 3:车队审批通过
//		;
//		if (branchId != null) sb.append(" and a.branch.id = :bid");
//		Query q = getSession().createQuery(sb.toString());
//		if (branchId != null) q.setParameter("bid", branchId);
//		return q.list();
//	}
	
//	public void addAccPayObject(AccPayObject apo) {
//		getSession().save(apo);
//	}
//	
//	public void saveAccPayObject(AccPayObject apo) {
//		getSession().saveOrUpdate(apo);
//	}
//	
//	public void deleteAccPayObject(AccPayObject apo) {
//		getSession().delete(apo);
//	}

//==================================== AccProcessor ====================================

	@SuppressWarnings("unchecked")
	public List<AccProcessor> getAccProcessors(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select l from AccProcessor l").append(
				" left outer join fetch l.branch b").append(
				" where b.id = :id");
		sb.append(" order by l.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<AccProcessor>)q.list();
	}
	
	public void addAccProcessor(AccProcessor accProcessor){
		getHibernateTemplate().save(accProcessor);
	}
	
	public void saveAccProcessor(AccProcessor accProcessor){
		getHibernateTemplate().saveOrUpdate(accProcessor);
	}
	
	public void updateAccProcessor(AccProcessor accProcessor){
		getHibernateTemplate().update(accProcessor);
	}
	
	public void deleteAccProcessor(AccProcessor accProcessor){
		getHibernateTemplate().delete(accProcessor);
	}
//=================================== AccOutPsn ============================================
	public void addAccOutPsn(AccOutPsn accOutPsn) {
		getHibernateTemplate().save(accOutPsn);
	}
	
	public void saveAccOutPsn(AccOutPsn accOutPsn) {
		getHibernateTemplate().saveOrUpdate(accOutPsn);
	}
	
	public void deleteAccOutPsn(AccOutPsn accOutPsn) {
		getHibernateTemplate().delete(accOutPsn);
	}
	
	/**支付管理
	 * get accoutpsn by accid and no
	 * @param accOutObj
	 */
	public List<AccOutPsn> getAOPByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		StringBuilder sb = new StringBuilder();
		sb.append("select aop from AccOutPsn aop")
			.append(" left outer join fetch aop.fkAccident a")
			.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and aop.id.branch.id = :branchId");
		sb.append(" and (a.status = 0 or a.status = 1)");
		if(accNo != null && accNo != "") sb.append(" and a.no = :accNo");
		if(no != null) sb.append(" and aop.id.no = :no");
		Query query = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
		if(no != null) query.setParameter("no", no);
		return query.list();
	}
	
	//================================== AccOutPsnPay =====================================
	public void addAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		getHibernateTemplate().save(accOutPsnPay);
	}
	
	public void saveAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		getHibernateTemplate().saveOrUpdate(accOutPsnPay);
	}
	
	public void deleteAccOutPsnPay(AccOutPsnPay accOutPsnPay) {
		getHibernateTemplate().delete(accOutPsnPay);
	}
//================================= AccOutObj =============================================
	public void addAccOutObj(AccOutObj accOutObj) {
		getHibernateTemplate().save(accOutObj);
	}
	
	public void saveAccOutObj(AccOutObj accOutObj) {
		getHibernateTemplate().saveOrUpdate(accOutObj);
	}
	
	public void deleteAccOutObj(AccOutObj accOutObj) {
		getHibernateTemplate().delete(accOutObj);
	}
	
	/**支付管理
	 * get accoutpsn by accid and no
	 * @param accOutObj
	 */
	public List<AccOutObj> getAOOByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		StringBuilder sb = new StringBuilder();
		sb.append("select aoo from AccOutObj aoo")
			.append(" left outer join fetch aoo.fkAccident a")
			.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and aoo.id.branch.id = :branchId");
		sb.append(" and (a.status = 0 or a.status = 1)");
		if(accNo != null && accNo != "") sb.append(" and a.no = :accNo");
		if(no != null) sb.append(" and aoo.id.no = :no");
		Query query = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
		if(no != null) query.setParameter("no", no);
		return query.list();
	}

//=================================== AccInPsn ===============================================
	public void addAccInPsn(AccInPsn accInPsn) {
		getHibernateTemplate().save(accInPsn);
	}
	
	public void saveAccInPsn(AccInPsn accInPsn) {
		getHibernateTemplate().saveOrUpdate(accInPsn);
	}
	
	public void deleteAccInPsn(AccInPsn accInPsn) {
		getHibernateTemplate().delete(accInPsn);
	}
	
	/**支付管理
	 * get accinpsn by accid and no
	 * @param accOutObj
	 */
	public List<AccInPsn> getAIPByAccIdAndNo(Integer branchId, String accNo, Integer no) {
		StringBuilder sb = new StringBuilder();
		sb.append("select aip from AccInPsn aip")
			.append(" left outer join fetch aip.fkAccident a")
			.append(" where 1=1");
		if(branchId != null && branchId != 0) sb.append(" and aip.id.branch.id = :branchId");
		sb.append(" and (a.status = 0 or a.status = 1)");
		if(accNo != null && accNo != "") sb.append(" and a.no = :accNo");
		if(no != null) sb.append(" and aip.id.no = :no");
		Query query = getSession().createQuery(sb.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
		if(no != null) query.setParameter("no", no);
		return query.list();
	}
	
//=================================== AccInPsnPay ===============================================
	public void addAccInPsnPay(AccInPsnPay accInPsnPay) {
		getHibernateTemplate().save(accInPsnPay);
	}
	
	public void saveAccInPsnPay(AccInPsnPay accInPsnPay) {
		getHibernateTemplate().saveOrUpdate(accInPsnPay);
	}
	
	public void deleteAccInPsnPay(AccInPsnPay accInPsnPay) {
		getHibernateTemplate().delete(accInPsnPay);
	}
//================================= GuaReport =======================================
	public void addGuaReport(GuaReport guaReport) {
		getHibernateTemplate().save(guaReport);
	}
	
	public void saveGuaReport(GuaReport guaReport) {
		getHibernateTemplate().saveOrUpdate(guaReport);
	}
	
	public void deleteGuaReport(GuaReport guaReport) {
		getHibernateTemplate().delete(guaReport);
	}
}
