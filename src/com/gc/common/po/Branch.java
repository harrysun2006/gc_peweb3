package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.Constants;
import com.gc.util.CommonUtil;

public class Branch {

	private Integer id;

	private String useId;

	private String name;

	private Integer level;

	private Integer superId;

	private Calendar onDate;

	private Integer status;

	private Calendar downDate;

	private String national;

	private String state;

	private String city;

	private String zip;

	private String address;

	private String contact;

	private String telephone;

	private String email;

	private String comment;

	private Department manDepart;

	public Branch() {
	}

	public Branch(Integer id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSuperId() {
		return superId;
	}

	public void setSuperId(Integer superId) {
		this.superId = superId;
	}

	public Calendar getOnDate() {
		return onDate;
	}

	public void setOnDate(Calendar onDate) {
		this.onDate = onDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Calendar getDownDate() {
		return downDate;
	}

	public void setDownDate(Calendar downDate) {
		this.downDate = downDate;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getManDepartId() {
		return (manDepart == null) ? null : manDepart.getId();
	}

	public Department getManDepart() {
		return Department.getSafeObject(manDepart);
	}

	public void setManDepart(Department manDepart) {
		this.manDepart = manDepart;
	}

	public boolean equals(Object obj) {
		Branch po = (obj instanceof Branch) ? (Branch) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getManDepartId(), po.getManDepartId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Branch{id=").append(id)
			.append(", useId=").append(useId)
			.append(", name=").append(name)
			// 此处需要注意循环引用问题
			// .append(", manDepartment=").append(getManDepart())
			.append(", onDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, onDate))
			.append(", downDate=").append(CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, downDate)).append("}");
		return sb.toString();
	}

	/**
	 * NOTE: 需要兼顾考虑以下几个问题:
	 * 1. Hibernate加载此对象, 且此对象为proxy对象
	 * 2. Hibernate加载此对象, 且此对象为原始对象
	 * 3. Hibernate没有加载此对象, 此对象做为外键对象键值为NULL
	 * 4. Hibernate没有加载此对象, 此对象做为外键对象键值不为NULL
	 * TODO: 研究是否可以做统一配置或拦截
	 * @return
	 */
	public static Branch getSafeObject(Branch po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Branch) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Branch(po.getId());
		}
		/*
		return (Hibernate.isInitialized(branch) && branch instanceof HibernateProxy) ? 
			(Branch) ((HibernateProxy) branch).getHibernateLazyInitializer().getImplementation() 
			: new Branch(branch.getId());
		*/ 
	}
}
