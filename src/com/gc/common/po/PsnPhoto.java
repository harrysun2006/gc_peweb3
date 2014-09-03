package com.gc.common.po;

import java.util.Calendar;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.gc.util.CommonUtil;

public class PsnPhoto {

	private Integer id;

	private Person person;

	private Branch branch;

	private byte[] photo;

	private Calendar photoDate;

	private Person uploader;

	public PsnPhoto() {
	}

	public PsnPhoto(Integer id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return (person == null) ? null : person.getId();
	}

	public Person getPerson() {
		return Person.getSafeObject(person);
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Calendar getPhotoDate() {
		return photoDate;
	}

	public void setPhotoDate(Calendar photoDate) {
		this.photoDate = photoDate;
	}

	public Integer getUploaderId() {
		return (uploader == null) ? null : uploader.getId();
	}

	public Person getUploader() {
		return Person.getSafeObject(uploader);
	}

	public void setUploader(Person uploader) {
		this.uploader = uploader;
	}

	public boolean equals(Object obj) {
		PsnPhoto po = (obj instanceof PsnPhoto) ? (PsnPhoto) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId())
			&& CommonUtil.equals(getUploaderId(), po.getUploaderId());
	}

	public static PsnPhoto getSafeObject(PsnPhoto po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (PsnPhoto) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new PsnPhoto(po.getId());
		}
	}
}
