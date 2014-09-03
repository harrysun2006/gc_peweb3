/**
 * 
 */
package com.gc.hr.po;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Administrator
 *
 */
public class PsnContractRpt implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3988775789520573826L;

	private String id;
	
	private String workId;
	
	private String name;
	
	private String sex;
	
	private Calendar barthday;
	
	private String pid;
	
	private Integer workLength=null;//进单位前工龄
	
	private String regBlong; //所属公司（部门）
	
	private String position;//所在岗位
	
	private String workType;//职位
	
	private String type;//用工类别
	
	private String party;//政治面貌
	
	private Integer totalContract=null;  
	
	private String contarctNo;
	
	private Calendar contractBegin;
	
	private Calendar contractEnd;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the workId
	 */
	public String getWorkId() {
		return workId;
	}

	/**
	 * @param workId the workId to set
	 */
	public void setWorkId(String workId) {
		this.workId = workId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the barthday
	 */
	public Calendar getBarthday() {
		return barthday;
	}

	/**
	 * @param barthday the barthday to set
	 */
	public void setBarthday(Calendar barthday) {
		this.barthday = barthday;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the workLength
	 */
	public Integer getWorkLength() {
		return workLength;
	}

	/**
	 * @param workLength the workLength to set
	 */
	public void setWorkLength(Integer workLength) {
		this.workLength = workLength;
	}

	/**
	 * @return the regBlong
	 */
	public String getRegBlong() {
		return regBlong;
	}

	/**
	 * @param regBlong the regBlong to set
	 */
	public void setRegBlong(String regBlong) {
		this.regBlong = regBlong;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the workType
	 */
	public String getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(String workType) {
		this.workType = workType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}

	/**
	 * @return the totalContract
	 */
	public Integer getTotalContract() {
		return totalContract;
	}

	/**
	 * @param totalContract the totalContract to set
	 */
	public void setTotalContract(Integer totalContract) {
		this.totalContract = totalContract;
	}

	/**
	 * @return the contarctNo
	 */
	public String getContarctNo() {
		return contarctNo;
	}

	/**
	 * @param contarctNo the contarctNo to set
	 */
	public void setContarctNo(String contarctNo) {
		this.contarctNo = contarctNo;
	}

	/**
	 * @return the contractBegin
	 */
	public Calendar getContractBegin() {
		return contractBegin;
	}

	/**
	 * @param contractBegin the contractBegin to set
	 */
	public void setContractBegin(Calendar contractBegin) {
		this.contractBegin = contractBegin;
	}

	/**
	 * @return the contractEnd
	 */
	public Calendar getContractEnd() {
		return contractEnd;
	}

	/**
	 * @param contractEnd the contractEnd to set
	 */
	public void setContractEnd(Calendar contractEnd) {
		this.contractEnd = contractEnd;
	}
}
