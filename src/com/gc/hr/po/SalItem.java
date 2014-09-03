package com.gc.hr.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.util.CommonUtil;

public class SalItem {

	private Integer id;

	private Branch branch;

	private String no;

	private String name;

	private Calendar onDate;

	private Calendar downDate;

	private String accountDebit;

	private String accountCredit;

	// ���򣺷���(1)���������ܶ�(0)���ۻ�(-1)
	private Integer flag;

	// �̶���𣺹�����Ŀ(WG)���籣��Ŀ(SG)��������Ŀ(WF)��������Ŀ(DK)��������Ŀ(ƽ����ĿPZ)
	private String type;
	public static final String TYPE_SG = "SG";

	// ��ӡ����ʾ��ӡ(1)��ֻ��ʾ(0)
	private String print;

	private String formula;

	// �༭���ͣ���ͨ��Ŀ�ɱ༭(0)������ʽ�ɱ༭(1)������ʽ���ɱ༭(2)��������Ŀ���ɱ༭(3)
	private String editType;

	private String comment;

	public SalItem() {
	}

	public SalItem(Integer id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBranchId() {
		return (branch == null) ? null : branch.getId();
	}

	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getOnDate() {
		return onDate;
	}

	public void setOnDate(Calendar onDate) {
		this.onDate = onDate;
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public String getAccountDebit() {
		return accountDebit;
	}

	public void setAccountDebit(String accountDebit) {
		this.accountDebit = accountDebit;
	}

	public String getAccountCredit() {
		return accountCredit;
	}

	public void setAccountCredit(String accountCredit) {
		this.accountCredit = accountCredit;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		SalItem po = (obj instanceof SalItem) ? (SalItem) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HrSalItem{id=").append(id)
			.append(", no=").append(no)
			.append("}");
		return sb.toString();
	}

	public static SalItem getSafeObject(SalItem po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SalItem) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SalItem(po.getId());
		}
	}

}