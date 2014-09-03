package com.gc.hr.po;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

/**
 * ’˛÷Œ√Ê√≤
 * @author hsun
 *
 */
public class PolParty {

	private PolPartyPK id;

	private Double no;

	private Integer active;

	public PolParty() {
	}

	public PolParty(Integer branchId, String name) {
		this(new PolPartyPK(branchId, name));
	}

	public PolParty(PolPartyPK id) {
		setId(id);
	}

	public Integer getBranchId() {
		return (id == null) ? null : id.getBranchId();
	}

	public String getName() {
		return (id == null) ? null : id.getName();
	}

	public PolPartyPK getId() {
		return id;
	}

	public void setId(PolPartyPK id) {
		this.id = id;
	}

	public Double getNo() {
		return no;
	}

	public void setNo(Double no) {
		this.no = no;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public boolean equals(Object obj) {
		PolParty po = (obj instanceof PolParty) ? (PolParty) obj : null;
		return CommonUtil.equals(this, po);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Personal.PolParty{id=").append(id)
			.append(", no=").append(no).append("}");
		return sb.toString();
	}

	public static PolParty getSafeObject(PolParty po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (PolParty) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new PolParty(po.getId());
		}
	}
}
