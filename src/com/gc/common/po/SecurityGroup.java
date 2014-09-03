package com.gc.common.po;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class SecurityGroup {

	private Integer id;

	private String useId;

	private Boolean supervise;

	private String description;

	private String comment;

	private List<SecurityLimit> limits = new ArrayList<SecurityLimit>();

	public SecurityGroup() {
	}

	public SecurityGroup(Integer id) {
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

	public Boolean getSupervise() {
		return supervise;
	}

	public void setSupervise(Boolean supervise) {
		this.supervise = supervise;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<SecurityLimit> getLimits() {
		return limits;
	}

	public void setLimits(List<SecurityLimit> limits) {
		this.limits = limits;
	}

	public void addLimit(SecurityLimit limit) {
		limits.add(limit);
	}

	public boolean equals(Object obj) {
		SecurityGroup po = (obj instanceof SecurityGroup) ? (SecurityGroup) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SecurityGroup{id=").append(id)
			.append(", useId=").append(useId)
			.append(", supervise=").append(supervise).append("}");
		return sb.toString();
	}

	public static SecurityGroup getSafeObject(SecurityGroup po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SecurityGroup) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SecurityGroup(po.getId());
		}
	}
}
