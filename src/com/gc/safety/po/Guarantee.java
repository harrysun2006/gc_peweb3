package com.gc.safety.po;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Person;
import com.gc.util.CommonUtil;

public class Guarantee {
	private GuaranteePK id;

	private Calendar inputDate;

	private GuaType type;

	private Insurer insurer;

	private Calendar onDate;

	private Calendar downDate;

	private Person doPsn;

	private String insurerPsn;

	private String desc;
	
	private Set<GuarInfo> guarInfos = new HashSet<GuarInfo>(0);

	public Guarantee() {}
	
	public Guarantee(GuaranteePK id) {
		setId(id);
	}

	public GuaranteePK getId() {
		return GuaranteePK.getSafeObject(id);
	}

	public void setId(GuaranteePK id) {
		this.id = id;
	}

	public Calendar getInputDate() {
		return inputDate;
	}

	public void setInputDate(Calendar inputDate) {
		this.inputDate = inputDate;
	}

	public GuaType getType() {
		return GuaType.getSafeObject(type);
	}

	public void setType(GuaType type) {
		this.type = type;
	}

	public Insurer getInsurer() {
		return Insurer.getSafeObject(insurer);
	}

	public void setInsurer(Insurer insurer) {
		this.insurer = insurer;
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

	public Person getDoPsn() {
		return Person.getSafeObject(doPsn);
	}

	public void setDoPsn(Person doPsn) {
		this.doPsn = doPsn;
	}

	public String getInsurerPsn() {
		return insurerPsn;
	}

	public void setInsurerPsn(String insurerPsn) {
		this.insurerPsn = insurerPsn;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Set<GuarInfo> getGuarInfos() {
		return guarInfos;
	}

	public void setGuarInfos(Set<GuarInfo> guarInfos) {
		this.guarInfos = guarInfos;
	}
	
	public boolean equals(Object obj) {
		Guarantee pk = (obj instanceof Guarantee) ? (Guarantee) obj : null;
		return CommonUtil.equals(getId().getBranchId(), pk.getId().getBranchId());
	}
	
	public static Guarantee getSafeObject(Guarantee id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (Guarantee) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			 else return id;
		} else {
			if (id == null) return null;
			else return new Guarantee(id.getId());
		}
	}

}
