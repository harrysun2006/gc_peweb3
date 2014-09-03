package com.gc.common.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class Office {

	private OfficePK id;

	private String duty;

	private Person leader;

	private String comment;

	public Office() {
	}

	public Office(Integer branchId, Integer departId, String name) {
		this(new OfficePK(branchId, departId, name));
	}

	public Office(OfficePK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public Integer getDepartId() {
		return (id == null) ? null : id.getDepartId();
	}

	public OfficePK getId() {
		return id;
	}

	public void setId(OfficePK id) {
		this.id = id;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Integer getLeaderId() {
		return (leader == null) ? null : leader.getId();
	}

	public Person getLeader() {
		return Person.getSafeObject(leader);
	}

	public void setLeader(Person leader) {
		this.leader = leader;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean equals(Object obj) {
		Office po = (obj instanceof Office) ? (Office) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getLeaderId(), po.getLeaderId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Office{id=").append(id).append("}");
		return sb.toString();
	}

	public static Office getSafeObject(Office po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (Office) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new Office(po.getId());
		}
	}
}
