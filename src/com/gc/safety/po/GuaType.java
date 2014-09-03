package com.gc.safety.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;

/**
 * 
 * @author wuhua
 *
 */
public class GuaType {
	private Long id;
	private Branch branch;
	private String name;
	private String desc;
	
	public GuaType(){
		
	}
	public GuaType(Long id) {
		setId(id);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static GuaType getSafeObject(GuaType guaranteeType) {
		if (Hibernate.isInitialized(guaranteeType)) {
			if (guaranteeType instanceof HibernateProxy) return (GuaType) ((HibernateProxy) guaranteeType).getHibernateLazyInitializer().getImplementation();
			else return guaranteeType;
		} else {
			if (guaranteeType == null) return null;
			else return new GuaType(guaranteeType.getId());
		}
	}
	
}
