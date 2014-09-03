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

	// 方向：发放(1)、不计入总额(0)、扣回(-1)
	private Integer flag;

	// 固定类别：工资项目(WG)、社保项目(SG)、福利项目(WF)、代扣项目(DK)、对消项目(平帐项目PZ)
	private String type;
	public static final String TYPE_SG = "SG";

	// 打印：显示打印(1)、只显示(0)
	private String print;

	private String formula;

	// 编辑类型：普通项目可编辑(0)、带公式可编辑(1)、带公式不可编辑(2)、导入项目不可编辑(3)
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