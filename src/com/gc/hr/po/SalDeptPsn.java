package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Department;
import com.gc.common.po.Person;
import com.gc.util.CommonUtil;


public class SalDeptPsn {

	private SalDeptPsnPK id;

	private String bank;

	private String bankCard;

	private String comment;

	public SalDeptPsn() {
	}

	public SalDeptPsn(Integer branchId, Integer departId, Integer personId) {
		this(new SalDeptPsnPK(branchId, departId, personId));
	}

	public SalDeptPsn(SalDeptPsnPK id) {
		setId(id);
	}

	public SalDeptPsnPK getId() {
		return id;
	}

	public void setId(SalDeptPsnPK id) {
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

	public Person getPerson() {
		return (id == null) ? null : id.getPerson();
	}

	public Integer getPersonId() {
		return (id == null) ? null : id.getPersonId();
	}

	public String getPersonWorkerId() {
		return (id == null) ? null : id.getPersonWorkerId();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		SalDeptPsnPK po = (obj instanceof SalDeptPsnPK) ? (SalDeptPsnPK) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalDeptPsn{id=").append(id)
			.append(", bank=").append(bank)
			.append(", card=").append(bankCard).append("}");
		return sb.toString();
	}

	public static SalDeptPsn getSafeObject(SalDeptPsn po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalDeptPsn) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalDeptPsn(po.getId());
		}
	}
}
