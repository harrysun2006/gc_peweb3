package com.gc.common.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class EquType {

	private Integer id;
	
	private String name;
	
	private String comment;
	
	public EquType() {
	}
	
	public EquType(Integer id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		EquType po = (obj instanceof EquType) ? (EquType) obj : null;
		return CommonUtil.equals(this, po);
	}

	public static EquType getSafeObject(EquType po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (EquType) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new EquType(po.getId());
		}
	}

}
