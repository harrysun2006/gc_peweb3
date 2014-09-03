package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class SalFixOnline {

	private SalFixOnlinePK id;

	private Calendar downDate;

	private Double amount;

	private String comment;

	public SalFixOnline() {
	}

	public SalFixOnline(Integer branchId, Integer departId, Integer personId, Integer itemId, Calendar onDate) {
		this(new SalFixOnlinePK(branchId, departId, personId, itemId, onDate));
	}

	public SalFixOnline(SalFixOnlinePK id) {
		setId(id);
	}

	public SalFixOnlinePK getId() {
		return id;
	}

	public void setId(SalFixOnlinePK id) {
		this.id = id;
	}

	public Branch getBranch() {
		return (id == null) ? null : id.getBranch();
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Department getDepart() {
		return (id == null) ? null : id.getDepart();
	}

	public Integer getDepartId() {
		return (id == null) ? null : id.getDepartId();
	}

	public String getDepartNo() {
		return (id == null) ? null : id.getDepartNo();
	}

	public Person getPerson() {
		return (id == null) ? null : id.getPerson();
	}

	public Integer getPersonId() {
		return (id == null) ? null : id.getPersonId();
	}

	public String getPersonWorkerId() {
		return (id == null) ? null : id.getPersonWorkerId();
	}

	public SalItem getItem() {
		return (id == null) ? null : id.getItem();
	}

	public Integer getItemId() {
		return (id == null) ? null : id.getItemId();
	}

	public String getItemNo() {
		return (id == null) ? null : id.getItemNo();
	}

	public Calendar getOnDate() {
		return (id == null) ? null : id.getOnDate();
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		SalFixOnline po = (obj instanceof SalFixOnline) ? (SalFixOnline) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalFixOnline{id=").append(id)
			.append(", amount=").append(amount).append("}");
		return sb.toString();
	}


	public static SalFixOnline getSafeObject(SalFixOnline po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalFixOnline) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalFixOnline(po.getId());
		}
	}
}
