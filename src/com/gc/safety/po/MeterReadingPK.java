package com.gc.safety.po;

import java.io.Serializable;
import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.common.po.Branch;
import com.gc.common.po.Equipment;

public class MeterReadingPK implements Serializable{
	private Branch branch;
	
	private Equipment equipment;
	
	private String type;
	
	private Calendar readTime;
	
	public MeterReadingPK(){};
	
	public MeterReadingPK(Branch branch, Equipment equipment, String type, Calendar readTime){
		this.branch = branch;
		this.equipment = equipment;
		this.type = type;
		this.readTime = readTime;
	}
	
	public Branch getBranch() {
		return Branch.getSafeObject(branch);
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Equipment getEquipment() {
		return Equipment.getSafeObject(equipment);
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}
	
	public static MeterReadingPK getSafeObject(MeterReadingPK id) {
		if (Hibernate.isInitialized(id)) {
			if (id instanceof HibernateProxy) return (MeterReadingPK) ((HibernateProxy) id).getHibernateLazyInitializer().getImplementation();
			else return id;
		} else {
			if (id == null) return null;
			else return new MeterReadingPK(id.getBranch(), id.getEquipment(),id.getType(),id.getReadTime());
		}
	}
}
