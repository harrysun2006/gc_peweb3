package com.gc.safety.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gc.common.po.Equipment;
import com.gc.common.po.SecurityLimit;
import com.gc.exception.CommonRuntimeException;
import com.gc.safety.po.AccInPsnGua;
import com.gc.safety.po.AccInPsnGuaPay;
import com.gc.safety.po.AccOutGua;
import com.gc.safety.po.AccOutGuaPay;
import com.gc.safety.po.Accident;
import com.gc.safety.po.GuaType;
import com.gc.safety.po.GuarInfo;
import com.gc.safety.po.Guarantee;
import com.gc.safety.po.Insurer;
import com.gc.safety.service.CommonServiceUtil;

/**
 * 
 * @author wuhua
 *
 */
public class GuaranteeDAOHibernate extends HibernateDaoSupport {
	
//==================================== Guarantee ====================================

	/**
	 * @return Trans
	 */
	@SuppressWarnings("unchecked")
	public List<Guarantee> getGuarantee(Integer branchId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from Guarantee t").append(
				" left outer join fetch t.branch tb").append(
				" where tb.id = :id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<Guarantee>) q.list();
	}

	public Guarantee getGuaranteeById(Long id) {
		return (Guarantee)getHibernateTemplate().get(Guarantee.class, id);
	}

	public void addGuarantee(Guarantee guarantee) {
		getHibernateTemplate().save(guarantee);
	}

	public void saveGuarantee(Guarantee guarantee) {
		getHibernateTemplate().saveOrUpdate(guarantee);
	}

	public void updateGuarantee(Guarantee guarantee) {
		getHibernateTemplate().update(guarantee);
	}
	
	public void deleteGuarantee(Guarantee guarantee) {
		getHibernateTemplate().delete(guarantee);
	}
	
	//查询保单凭证号和头信息
	@SuppressWarnings("unchecked")
	public List findGuarList(Map obj) {
		String putDate1 = obj.get("putDate1").toString();
		String putDate2 = obj.get("putDate2").toString();
		String insuName = obj.get("insuName").toString();
		String guarType = obj.get("guarType").toString();
		String num = obj.get("num").toString();
		String line = obj.get("line").toString();
		String busNo = obj.get("busNo").toString();
		String prodData = obj.get("prodData").toString();
		String authNo = obj.get("authNo").toString();
		String zwNum = obj.get("zwNum").toString();
		String powerNo = obj.get("powerNo").toString();
		String archNo = obj.get("archNo").toString();
		String fee = obj.get("fee").toString();
		String ondate1 = obj.get("ondate1").toString();
		String ondate2 = obj.get("ondate2").toString();
		String downdate1 = obj.get("downdate1").toString();
		String downdate2 = obj.get("downdate2").toString();
		String psn2 = obj.get("psn2").toString();
		String psn3 = obj.get("psn3").toString();
		String psn4 = obj.get("psn4").toString();
		String branchId = obj.get("branchId").toString();
				
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select distinct g.id.accNo,g,i.name,t.name")
				.append(" from Guarantee g,GuaType t,Insurer i,GuarInfo gi")
				.append(" where g.insurer.id=i.id")
				.append(" and g.guaranteeType.id=t.id")
				.append(" and g.id.accNo=gi.id.accNo")
				.append(" and g.id.branch=gi.id.branch")
				.append(" and g.id.branch.id=:branchId");
				
				if (insuName != "") {
					sBuilder.append(" and g.insurer.name = '"+insuName+"'");
				}
				if (guarType != "") {
					sBuilder.append(" and g.guaranteeType.name = '"+guarType+"'");
				}
				if (num != "") {
					sBuilder.append(" and gi.num = '"+num+"'");
				}
				if (line != "") {
					sBuilder.append(" and gi.line = '"+line+"'");
				}
				if (busNo != "") {
					sBuilder.append(" and gi.bus.useId = '"+busNo+"'");
				}
				if (prodData != "") {
					sBuilder.append(" and gi.prodData = '"+prodData+"'");
				}
				if (authNo != "") {
					sBuilder.append(" and gi.authNo = '"+authNo+"'");
				}
				if (zwNum != "") {
					sBuilder.append(" and gi.outPower1 ='"+zwNum+"'");
				}
				if (powerNo != "") {
					sBuilder.append(" and gi.powerNo ='"+powerNo+"'");
				}
				if (archNo != "") {
					sBuilder.append(" and gi.archNo ='"+archNo+"'");
				}
				if (fee != "") {
					sBuilder.append(" and gi.fee ='"+fee+"'");
				}
				if (psn2 != "") {
					sBuilder.append(" and g.psn2 = '"+psn2+"'");
				}
				if (psn3 != "") {
					sBuilder.append(" and g.psn3 = '"+psn3+"'");
				}
				if (psn4 != "") {
					sBuilder.append(" and g.psn4 = '"+psn4+"'");
				}
				if (putDate1 != "" && putDate2 != "") {
					sBuilder.append(" and g.putDate >= :putDate1");
					sBuilder.append(" and g.putDate <= :putDate2");
				} else if (putDate1 != "" && "" == putDate2) {
					sBuilder.append(" and g.putDate >= :putDate1");
				} else if ("" == putDate1 && "" != putDate2) {
					sBuilder.append(" and g.putDate <= :putDate2");
				}
				if (ondate1 != "" && ondate2 != "") {
					sBuilder.append(" and g.ondate >= :ondate1");
					sBuilder.append(" and g.ondate <= :ondate2");
				} else if (ondate1 != "" && "" == ondate2) {
					sBuilder.append(" and g.ondate >= :ondate1");
				} else if ("" == ondate1 && "" != ondate2) {
					sBuilder.append(" and g.ondate <= :ondate2");
				}
				if (downdate1 != "" && downdate2 != "") {
					sBuilder.append(" and g.downdate >= :downdate1");
					sBuilder.append(" and g.downdate <= :downdate2");
				} else if (downdate1 != "" && "" == downdate2) {
					sBuilder.append(" and g.downdate >= :downdate1");
				} else if ("" == downdate1 && "" != downdate2) {
					sBuilder.append(" and g.downdate <= :downdate2");
				}
				sBuilder.append(" order by g.id.accNo");
				
		Query query = getSession().createQuery(sBuilder.toString());
		query.setParameter("branchId", Integer.parseInt(branchId));
		
		if ("" != putDate1) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(putDate1));
				query.setParameter("putDate1", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的投保日期1格式错误！");
			}
		}
		if ("" != putDate2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(putDate2));
				query.setParameter("putDate2", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的投保日期2格式错误！");
			}
		}
		if ("" != ondate1) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(ondate1));
				query.setParameter("ondate1", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的生效日期1格式错误！");
			}
		}
		if ("" != ondate2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(ondate2));
				query.setParameter("ondate2", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的生效日期2格式错误！");
			}
		}
		if ("" != downdate1) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(downdate1));
				query.setParameter("downdate1", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的失效日期1格式错误！");
			}
		}
		if ("" != downdate2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(downdate2));
				query.setParameter("downdate2", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的失效日期2格式错误！");
			}
		}
		return query.list();
	}
	
	/**
	 * 
	 * 通过凭证号和条件查询投保信息
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findGuarAndInfoList(Map obj1,Map obj2) {
		String putDate1 = obj2.get("putDate1").toString();
		String putDate2 = obj2.get("putDate2").toString();
		String insuName = obj2.get("insuName").toString();
		String guarType = obj2.get("guarType").toString();
		String num = obj2.get("num").toString();
		String line = obj2.get("line").toString();
		String busNo = obj2.get("busNo").toString();
		String prodData = obj2.get("prodData").toString();
		String authNo = obj2.get("authNo").toString();
		String zwNum = obj2.get("zwNum").toString();
		String powerNo = obj2.get("powerNo").toString();
		String archNo = obj2.get("archNo").toString();
		String fee = obj2.get("fee").toString();
		String ondate1 = obj2.get("ondate1").toString();
		String ondate2 = obj2.get("ondate2").toString();
		String downdate1 = obj2.get("downdate1").toString();
		String downdate2 = obj2.get("downdate2").toString();
		String psn2 = obj2.get("psn2").toString();
		String psn3 = obj2.get("psn3").toString();
		String psn4 = obj2.get("psn4").toString();
		String accNo = obj1.get("accNo").toString();
		String branchId = obj1.get("branchId").toString();
				
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select g,gi,i.name,t.name,gi.bus.useId,gi.id.accNo,gi.id.no")
				.append(" from Guarantee g,GuaType t,Insurer i,GuarInfo gi")
				.append(" where g.insurer.id=i.id")
				.append(" and g.guaranteeType.id=t.id")
				.append(" and g.id.accNo=gi.id.accNo")
				.append(" and g.id.branch=gi.id.branch")
				.append(" and g.id.accNo = '"+accNo+"'")
				.append(" and g.id.branch.id=:branchId");
				
				if (insuName != "") {
					sBuilder.append(" and g.insurer.name = '"+insuName+"'");
				}
				if (guarType != "") {
					sBuilder.append(" and g.guaranteeType.name = '"+guarType+"'");
				}
				if (num != "") {
					sBuilder.append(" and gi.num = '"+num+"'");
				}
				if (line != "") {
					sBuilder.append(" and gi.line = '"+line+"'");
				}
				if (busNo != "") {
					sBuilder.append(" and gi.bus.useId = '"+busNo+"'");
				}
				if (prodData != "") {
					sBuilder.append(" and gi.prodData = '"+prodData+"'");
				}
				if (authNo != "") {
					sBuilder.append(" and gi.authNo = '"+authNo+"'");
				}
				if (zwNum != "") {
					sBuilder.append(" and gi.outPower1 ='"+zwNum+"'");
				}
				if (powerNo != "") {
					sBuilder.append(" and gi.powerNo ='"+powerNo+"'");
				}
				if (archNo != "") {
					sBuilder.append(" and gi.archNo ='"+archNo+"'");
				}
				if (fee != "") {
					sBuilder.append(" and gi.fee ='"+fee+"'");
				}
				if (psn2 != "") {
					sBuilder.append(" and g.psn2 = '"+psn2+"'");
				}
				if (psn3 != "") {
					sBuilder.append(" and g.psn3 = '"+psn3+"'");
				}
				if (psn4 != "") {
					sBuilder.append(" and g.psn4 = '"+psn4+"'");
				}
				if (putDate1 != "" && putDate2 != "") {
					sBuilder.append(" and g.putDate >= :putDate1");
					sBuilder.append(" and g.putDate <= :putDate2");
				} else if (putDate1 != "" && "" == putDate2) {
					sBuilder.append(" and g.putDate >= :putDate1");
				} else if ("" == putDate1 && "" != putDate2) {
					sBuilder.append(" and g.putDate <= :putDate2");
				}
				if (ondate1 != "" && ondate2 != "") {
					sBuilder.append(" and g.ondate >= :ondate1");
					sBuilder.append(" and g.ondate <= :ondate2");
				} else if (ondate1 != "" && "" == ondate2) {
					sBuilder.append(" and g.ondate >= :ondate1");
				} else if ("" == ondate1 && "" != ondate2) {
					sBuilder.append(" and g.ondate <= :ondate2");
				}
				if (downdate1 != "" && downdate2 != "") {
					sBuilder.append(" and g.downdate >= :downdate1");
					sBuilder.append(" and g.downdate <= :downdate2");
				} else if (downdate1 != "" && "" == downdate2) {
					sBuilder.append(" and g.downdate >= :downdate1");
				} else if ("" == downdate1 && "" != downdate2) {
					sBuilder.append(" and g.downdate <= :downdate2");
				}
				sBuilder.append(" order by gi.id.accNo,gi.id.no");
				
		Query query = getSession().createQuery(sBuilder.toString());
		query.setParameter("branchId", Integer.parseInt(branchId));
		
		if ("" != putDate1) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(putDate1));
				query.setParameter("putDate1", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的投保日期1格式错误！");
			}
		}
		if ("" != putDate2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(putDate2));
				query.setParameter("putDate2", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的投保日期2格式错误！");
			}
		}
		if ("" != ondate1) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(ondate1));
				query.setParameter("ondate1", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的生效日期1格式错误！");
			}
		}
		if ("" != ondate2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(ondate2));
				query.setParameter("ondate2", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的生效日期2格式错误！");
			}
		}
		if ("" != downdate1) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(downdate1));
				query.setParameter("downdate1", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的失效日期1格式错误！");
			}
		}
		if ("" != downdate2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.valueOf(downdate2));
				query.setParameter("downdate2", cal);
			} catch (Exception e) {
				throw new CommonRuntimeException("你输入的失效日期2格式错误！");
			}
		}
		
		return query.list();
	}
	
	
	/**
	 * 通过凭证号
	 * @param accNo
	 */
	@SuppressWarnings("unchecked")
	public List findList(String accNo,Integer branchId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select g,gi,i.name,t.name,gi.bus.useId,gi.id.accNo,gi.id.no")
				.append(" from Guarantee g,GuaType t,Insurer i,GuarInfo gi")
				.append(" where g.insurer.id=i.id")
				.append(" and g.guaranteeType.id=t.id")
				.append(" and g.id.accNo=gi.id.accNo")
				.append(" and g.id.accNo = '"+accNo+"'")
				.append(" and g.id.branch.id=:branchId");
		sBuilder.append(" order by gi.id.accNo,gi.id.no");
		Query query = getSession().createQuery(sBuilder.toString());
		query.setParameter("branchId", branchId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Guarantee> getGuasByBCloseD(Integer branchId, Calendar closeDate) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select g from Guarantee g")
				.append(" left outer join fetch g.type t")
				.append(" left outer join fetch g.insurer i")
				.append(" left outer join fetch g.doPsn p")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and g.id.branch.id = :branchId");
//		if(closeDate != null) sBuilder.append(" and g.inputDate >= :closeDate");
		sBuilder.append(" order by g.id.accNo");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
//		if(closeDate != null) query.setParameter("closeDate", closeDate);
		return (List<Guarantee>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Guarantee> getGuaByAccNo(Integer branchId, String accNo, Calendar closeDate) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select g from Guarantee g")
				.append(" left outer join fetch g.type t")
				.append(" left outer join fetch g.insurer i")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and g.id.branch.id = :branchId");
		if(accNo != null && accNo != "") sBuilder.append(" and g.id.accNo = :accNo");
//		if(closeDate != null) sBuilder.append(" and g.inputDate >= :closeDate");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
//		if(closeDate != null) query.setParameter("closeDate", closeDate);
		return query.list();
	}
	
//==================================== GuarInfo ====================================

	@SuppressWarnings("unchecked")
	public List<GuarInfo> getGuarInfo(Integer branchId) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select g from GuarInfo g")
				.append(" left outer join fetch g.branch b")
				.append(" where b.id = :id");
		Query query = getSession().createQuery(sBuilder.toString());
		query.setParameter("id", branchId);
		return (List<GuarInfo>)query.list();
	}
	
	public void addGuarInfo(GuarInfo guarInfo) {
		getHibernateTemplate().save(guarInfo);
	}
	
	public void saveGuarInfo(GuarInfo guarInfo) {
		getHibernateTemplate().saveOrUpdate(guarInfo);
	}
	
	public void updateGuarInfo(GuarInfo guarInfo) {
		getHibernateTemplate().update(guarInfo);
	}
	
	public void deleteGuarInfo(GuarInfo guarInfo) {
		getHibernateTemplate().delete(guarInfo);
	}
	
	@SuppressWarnings("unchecked")
	public List<GuarInfo> getGuarInfoByBN(Integer branchId, String accNo) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select gi from GuarInfo gi")
				.append(" left outer join fetch gi.bus b")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and gi.id.branch.id = :branchId");
		if(accNo != null && accNo != "") sBuilder.append(" and gi.id.accNo = :accNo");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
		return (List<GuarInfo>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<GuarInfo> getGuarInfoList(Integer branchId, Integer busId,Calendar accDate){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select gi from GuarInfo gi")
				.append(" left outer join fetch gi.fkGuarantee guar")
				.append(" left outer join fetch guar.insurer insu")
				.append(" left outer join fetch guar.guaranteeType type")
				.append(" where 1 = 1");
		if(branchId != null && branchId != 0) sBuilder.append(" and gi.id.branch.id = :branchId");
		if(busId != null && busId !=0) sBuilder.append(" and gi.bus.id = :busId");
		if (accDate != null) {
			sBuilder.append(" and gi.fkGuarantee.ondate <= :accDate")
					.append(" and gi.fkGuarantee.downdate >= :accDate");
		}
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(busId != null && busId !=0) query.setParameter("busId", busId);
		if (accDate != null) {
			query.setParameter("accDate", accDate);
		}
		return (List<GuarInfo>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<GuarInfo> getGuarInfoByBND(Integer branchId, String accNo, Calendar closeDate) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select gi from GuarInfo gi")
				.append(" left outer join fetch gi.bus b")
				.append(" left outer join fetch gi.fkGuarantee fkg")
				.append(" left outer join fetch fkg.insurer insu")
				.append(" left outer join fetch fkg.guaranteeType type")
				.append(" where 1=1");
		if(branchId != null && branchId != 0) sBuilder.append(" and gi.id.branch.id = :branchId");
		if(accNo != null && accNo != "") sBuilder.append(" and gi.id.accNo = :accNo");
//		if(closeDate != null) sBuilder.append(" and g.inputDate >= :closeDate");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(accNo != null && accNo != "") query.setParameter("accNo", accNo);
//		if(closeDate != null) query.setParameter("closeDate", closeDate);
		return (List<GuarInfo>)query.list();
	}
	
	//将要过去保单 
	@SuppressWarnings("unchecked")
	public List<GuarInfo> getMatureGuas(Integer branchId, Calendar date1, Calendar date2) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select gi from GuarInfo gi")
		.append(" left outer join fetch gi.fkGuarantee fkg")
		.append(" left outer join fetch gi.bus b")
		.append(" left outer join fetch fkg.insurer i")
		.append(" left outer join fetch fkg.type t")
		.append(" where 1 = 1");
		sBuilder.append(" and gi.id.branch.id = :branchId");
		sBuilder.append(" and fkg.downDate >= :date1");
		sBuilder.append(" and fkg.downDate <= :date2");
		sBuilder.append(" order by gi.id.accNo,gi.id.no");
		Query query = getSession().createQuery(sBuilder.toString());
		query.setParameter("branchId", branchId);
		query.setParameter("date1", date1);
		query.setParameter("date2", date2);
		return (List<GuarInfo>)query.list();
	}
	
	//通过车 和 事故日期 查保单
	@SuppressWarnings("unchecked")
	public List<GuarInfo> getGIsByBusIdAndAccDate(Integer branchId, Integer busId, Calendar accDate) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select gi from GuarInfo gi")
		.append(" left outer join fetch gi.fkGuarantee fkg")
		.append(" left outer join fetch gi.bus b")
		.append(" left outer join fetch fkg.insurer i")
		.append(" left outer join fetch fkg.type t")
		.append(" where 1 = 1");
		if(branchId != null && branchId != 0) sBuilder.append(" and gi.id.branch.id = :branchId");
		if(busId != null && busId != 0) sBuilder.append(" and gi.bus.id = :busId");
		if (accDate != null) {
			sBuilder.append(" and fkg.onDate <= :accDate");
			sBuilder.append(" and fkg.downDate >= :accDate");
		}
		sBuilder.append(" order by gi.id.accNo,gi.id.no");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(busId != null && busId != 0) query.setParameter("busId", busId);
		if (accDate != null) query.setParameter("accDate", accDate);
		return (List<GuarInfo>)query.list();
	}
	
	/**
	 * 保单树，查询所有保单
	 */
	public List<GuarInfo> getGuaInfosByDateFrom(SecurityLimit limit,String[] orderColumns, Calendar dateFrom) {
//		Long time1 = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("select gi from GuarInfo gi")
			.append(" left outer join fetch gi.fkGuarantee fkg")
			.append(" left outer join fetch gi.bus b")
			.append(" left outer join fetch fkg.insurer i")
			.append(" left outer join fetch fkg.type t")
			.append(" where 1 = 1")
			.append(" and gi.id.branch.id = :branchId")
			.append(" and fkg.onDate <= :now")
			.append(" and fkg.downDate >= :dateFrom");
		sb.append(" order by");
		if (orderColumns != null && orderColumns.length > 0) {
			for (int i = 0; i < orderColumns.length; i++) {
				sb.append(" gi.").append(orderColumns[i]).append(",");
			}
		}
		sb.append(" b.useId");
		Query q1 = getSession().createQuery(sb.toString());
		q1.setParameter("branchId", limit.getBranchId());
		q1.setParameter("now", CommonServiceUtil.getNowDate());
		q1.setParameter("dateFrom", dateFrom);
		List<GuarInfo> giList = (List<GuarInfo>)q1.list();
		if (giList.size() > 0) {
		//查询理赔、赔付信息
			List<Integer> equIdList = new ArrayList<Integer>();
			for(GuarInfo gInfo : giList){
				if(equIdList.indexOf(gInfo.getBus().getId()) < 0)
					equIdList.add(gInfo.getBus().getId());
			}
			//事故列表
			sb.delete(0, sb.length());
			sb.append("select a from Accident a")
				.append(" where a.id.branch.id = :branchId")
				.append(" and a.bus.id in (:equIdList)")
				.append(" order by a.bus.id");
			Query q3 = getSession().createQuery(sb.toString());
			q3.setParameter("branchId", limit.getBranchId());
			q3.setParameterList("equIdList", equIdList);
			List<Accident> accList = (List<Accident>)q3.list();
			List<Integer> accIdList = new ArrayList<Integer>();
			for(Accident accident : accList){
				accIdList.add(accident.getAccidentId());
			}
			if (accIdList.size() > 0) {
			//三责理赔列表
				sb.delete(0, sb.length());
				sb.append("select aog from AccOutGua aog")
					.append(" left outer join fetch aog.accident a")
					.append(" where aog.id.branch.id = :branchId")
					.append(" and a.id.id in (:accIdList)")
					.append(" order by a.bus.id");
				Query q4 = getSession().createQuery(sb.toString());
				q4.setParameter("branchId", limit.getBranchId());
				q4.setParameterList("accIdList", accIdList);
				List<AccOutGua> aogList = (List<AccOutGua>)q4.list();
				//三责赔付列表
				sb.delete(0, sb.length());
				sb.append("select aogp from AccOutGuaPay aogp")
					.append(" where aogp.id.branch.id = :branchId");
				Query q5 = getSession().createQuery(sb.toString());
				q5.setParameter("branchId", limit.getBranchId());
				List<AccOutGuaPay> aogpList = (List<AccOutGuaPay>)q5.list();
				//客伤理赔列表
				sb.delete(0, sb.length());
				sb.append("select apg from AccInPsnGua apg")
					.append(" left outer join fetch apg.accident a")
					.append(" where apg.id.branch.id = :branchId")
					.append(" and a.id.id in (:accIdList)")
					.append(" order by a.bus.id");
				Query q6 = getSession().createQuery(sb.toString());
				q6.setParameter("branchId", limit.getBranchId());
				q6.setParameterList("accIdList", accIdList);
				List<AccInPsnGua> apgList = (List<AccInPsnGua>)q6.list();
				//客伤赔付列表
				sb.delete(0, sb.length());
				sb.append("select apgp from AccInPsnGuaPay apgp")
					.append(" where apgp.id.branch.id = :branchId");
				Query q7 = getSession().createQuery(sb.toString());
				q7.setParameter("branchId", limit.getBranchId());
				List<AccInPsnGuaPay> apgpList = (List<AccInPsnGuaPay>)q7.list();
				
//				Long time2 = System.currentTimeMillis();
//				System.out.println("保单树 事故、理赔、赔付数据库查询时间："+(time2-time1));
				
				//组织理赔、赔付信息-----------------
				for(GuarInfo guarInfo1 : giList) {
					//TODO:用保单类型名作为关键字
					if (guarInfo1.getFkGuarantee().getType().getName()=="客伤险") {
						for(AccInPsnGua apg : apgList){
							if (guarInfo1.getBus().getId().equals(apg.getAccident().getBusId())
									&& (guarInfo1.getFkGuarantee().getOnDate().before(apg.getAccident().getDate())
											|| guarInfo1.getFkGuarantee().getOnDate().equals(apg.getAccident().getDate()))
											&& (guarInfo1.getFkGuarantee().getDownDate().after(apg.getAccident().getDate())
													|| guarInfo1.getFkGuarantee().getDownDate().equals(apg.getAccident().getDate()))) {
								guarInfo1.addag(apg);
								for(AccInPsnGuaPay apgp : apgpList) {
									if (apg.getId().getRefNo().equals(apgp.getAppRefNo())
											&& apg.getId().getNo().equals(apgp.getAppNo()))
										guarInfo1.addapg(apgp);
								}
							}
						}
					} else {
						for(AccOutGua aog : aogList) {
							if (guarInfo1.getBus().getId().equals(aog.getAccident().getBusId())
									&& (guarInfo1.getFkGuarantee().getOnDate().before(aog.getAccident().getDate())
											|| guarInfo1.getFkGuarantee().getOnDate().equals(aog.getAccident().getDate()))
											&& (guarInfo1.getFkGuarantee().getDownDate().after(aog.getAccident().getDate())
													|| guarInfo1.getFkGuarantee().getDownDate().equals(aog.getAccident().getDate()))) {
								guarInfo1.addag(aog);
								for(AccOutGuaPay aogp : aogpList) {
									if (aog.getId().getRefNo().equals(aogp.getAppRefNo())
											&& aog.getId().getNo().equals(aogp.getAppNo()))
										guarInfo1.addapg(aogp);
								}
							}
						}
					}
				}
			}
			for(GuarInfo guarInfo : giList) {
				for(GuarInfo guarInfo2 : giList) {
					if(guarInfo.getFkGuarantee().getType().getId().equals(guarInfo2.getFkGuarantee().getType().getId())
							&& guarInfo.getFkGuarantee().getDownDate().equals(guarInfo2.getFkGuarantee().getDownDate())
							&& guarInfo.getBus().getId().equals(guarInfo2.getBus().getId()))
						guarInfo.addGi(guarInfo2);
				}
			}
		}

		sb.delete(0, sb.length());
		sb.append("select e from Equipment e")
			.append(" where e.branch.id = :branchId")
			.append(" order by e.useId");
		Query q2 = getSession().createQuery(sb.toString());
		q2.setParameter("branchId", limit.getBranchId());
		List<Equipment> equList = (List<Equipment>)q2.list();
		Guarantee guarantee;
		GuarInfo guarInfo;
		GuaType type;
		List<GuarInfo> gis = new ArrayList<GuarInfo>();
		GuarInfo gi = null;
		Object[] objs = new Object[]{};
		objs = ArrayUtils.clone(giList.toArray());
		for (int i = 0; i < objs.length; i++) {
			gi = new GuarInfo();
			gi = (GuarInfo)objs[i];
			gis.add(gi);
		}
		if (gis.size() > 0) {
			for (int i = gis.size()-1; i > 0; i--) {
				int j = i -1;
				if (gis.get(i).getFkGuarantee().getType().getId().equals(gis.get(j).getFkGuarantee().getType().getId())
						&& gis.get(i).getFkGuarantee().getDownDate().equals(gis.get(j).getFkGuarantee().getDownDate())
						&& gis.get(i).getBus().getId().equals(gis.get(j).getBus().getId())) {
					giList.remove(gis.get(j));
				}
			}
		}
		for(Equipment equipment : equList) {
			int m = 0;
			for (GuarInfo guarInfo2 : gis) {
				if (guarInfo2.getBus().getId().equals(equipment.getId())) {
					m++;
					break;
				}
			}
			if (m == 0) {
				guarantee = new Guarantee();
				guarInfo = new GuarInfo();
				type = new GuaType();
				guarantee.setType(type);
				guarInfo.setFkGuarantee(guarantee);
				guarInfo.setBus(equipment);
				giList.add(guarInfo);
			}
		}
//		System.out.println("组织理赔、赔付包括之前的查询时间："+(System.currentTimeMillis()-time1));
		return giList;
	}
	
	public List<GuarInfo> getGIsByUid(Integer branchId,String authNo,Calendar dateFrom,Calendar dateTo) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("select gi from GuarInfo gi")
		.append(" left outer join fetch gi.fkGuarantee fkg")
		.append(" left outer join fetch gi.bus b")
		.append(" left outer join fetch fkg.insurer i")
		.append(" left outer join fetch fkg.type t")
		.append(" left outer join fetch fkg.doPsn p")
		.append(" where 1 = 1");
		if(branchId != null && branchId != 0) sBuilder.append(" and gi.id.branch.id = :branchId");
		if(authNo != null && authNo != "") sBuilder.append(" and b.authNo = :authNo");
		if (dateFrom != null) sBuilder.append(" and fkg.onDate >= :dateFrom");
		if (dateTo != null)	sBuilder.append(" and fkg.onDate <= :dateTo");
		sBuilder.append(" order by gi.id.accNo");
		Query query = getSession().createQuery(sBuilder.toString());
		if(branchId != null && branchId != 0) query.setParameter("branchId", branchId);
		if(authNo != null && authNo != "") query.setParameter("authNo", authNo);
		if (dateFrom != null) query.setParameter("dateFrom", dateFrom);
		if (dateTo != null) query.setParameter("dateTo", dateTo);
		return query.list();
	}
//==================================== GuaType ====================================

	/**
	 * 返回投保信息类型
	 * @return GuaType
	 * @author wuhua
	 */
	@SuppressWarnings("unchecked")
	public List<GuaType> getGuaTypes(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select g from GuaType g").append(
				" left outer join fetch g.branch b").append(
				" where b.id = :id");
		sb.append(" order by g.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<GuaType>)q.list();
	}
	
	/**
	 * 添加投保类型
	 */
	public void addGuaType(GuaType guaranteeType){
		getHibernateTemplate().save(guaranteeType);
	}
	
	public void saveGuaType(GuaType guaranteeType){
		getHibernateTemplate().saveOrUpdate(guaranteeType);
	}
	
	public void updateGuaType(GuaType guaranteeType){
		getHibernateTemplate().update(guaranteeType);
	}
	
	public void deleteGuaType(GuaType guaranteeType){
		getHibernateTemplate().delete(guaranteeType);
	}

//==================================== Insurer ====================================

	@SuppressWarnings("unchecked")
	public List<Insurer> getInsurers(Integer branchId){
		StringBuilder sb = new StringBuilder();
		sb.append("select i from Insurer i").append(
				" left outer join fetch i.branch b").append(
				" where b.id=:id");
		sb.append(" order by i.id");
		Query q = getSession().createQuery(sb.toString());
		q.setParameter("id", branchId);
		return (List<Insurer>)q.list();
	}
	
	/**
	 * 添加投保公司信息
	 */
	public void addInsurer(Insurer insurer) {
		getHibernateTemplate().save(insurer);
	}

	public void saveInsurer(Insurer insurer) {
		getHibernateTemplate().saveOrUpdate(insurer);
	}

	public void updateInsurer(Insurer insurer) {
		getHibernateTemplate().update(insurer);
	}
	
	public void deleteInsurer(Insurer insurer) {
		getHibernateTemplate().delete(insurer);
	}

}
