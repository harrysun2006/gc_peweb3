package com.gc.common.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;


public class Weather {
	private Long id;
	
	private Branch branch;
	
	private String name;
	
	private String desc;
	
	public Weather() {
		
	}
	
	public Weather(Long id) {
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

	public boolean equals(Object obj) {
		Weather d = (obj instanceof Weather) ? (Weather) obj : null;
		return CommonUtil.equals(this, d)
			&& CommonUtil.equals(getBranchId(), d.getBranchId());
	}

	public static Weather getSafeObject(Weather weather) {
		if (Hibernate.isInitialized(weather)) {
			if (weather instanceof HibernateProxy) return (Weather) ((HibernateProxy) weather).getHibernateLazyInitializer().getImplementation();
			else return weather;
		} else {
			if (weather == null) return null;
			else return new Weather(weather.getId());
		}
	}

}
