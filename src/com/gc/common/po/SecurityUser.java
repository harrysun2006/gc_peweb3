package com.gc.common.po;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.gc.util.CommonUtil;

/**
 * T_SECURITY_USER表的映射类，定义有系统登录权限的用户。
 * @author hsun
 */
public class SecurityUser implements UserDetails {

	private Integer id = 0;

	private String useId;

	private Person person;

	private Branch branch;

	private SecurityGroup group;

	private String password1;

	private String password2;

	private String password3;

	private String description;

	private String comment;

	// none database column properties

	private String remoteAddr;

	private String remoteHost;

	private int remotePort;

	private String remoteUser;

	private Calendar systemTime;

	private GrantedAuthority[] roles;

	// query properties

	private SecurityLimit limit;

	public SecurityUser() {
	}

	public SecurityUser(Integer id) {
		setId(id);
	}

	public SecurityUser(String username, String password, String limitBranchUseId) {
		this.useId = username;
		this.password1 = password;
		this.limit = new SecurityLimit();
		this.limit.setId(new SecurityLimitPK());
		this.limit.getId().setBranch(new Branch());
		this.limit.getId().getBranch().setUseId(limitBranchUseId);
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

	public Integer getPersonId() {
		return (person == null) ? null : person.getId();
	}

	public Person getPerson() {
		return Person.getSafeObject(person);
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Department getPersonDept() {
		return (person == null) ? null : person.getDepart();
	}

	public String getBranchName() {
		Branch b = getBranch();
		return (b == null) ? "" : b.getName();
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

	public Integer getGroupId() {
		return (group == null) ? null : group.getId();
	}

	public SecurityGroup getGroup() {
		return SecurityGroup.getSafeObject(group);
	}

	public void setGroup(SecurityGroup group) {
		this.group = group;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword3() {
		return password3;
	}

	public void setPassword3(String password3) {
		this.password3 = password3;
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
		return (group == null) ? null : group.getLimits();
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	public Calendar getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Calendar systemTime) {
		this.systemTime = systemTime;
	}

	public SecurityLimit getLimit() {
		return limit;
	}

	public void setLimit(SecurityLimit limit) {
		this.limit = limit;
	}

	public Branch getLimitBranch() {
		return (limit == null || limit.getId() == null) ? null : limit.getId().getBranch();
	}

	public Integer getLimitBranchId() {
		Branch b = getLimitBranch();
		return (b == null) ? null : b.getId();
	}

	public String getLimitBranchUseId() {
		Branch b = getLimitBranch();
		return (b == null) ? null : b.getUseId();
	}

	public SecurityGroup getLimitGroup() {
		return (limit == null || limit.getId() == null) ? null : limit.getId().getGroup();
	}

	public boolean equals(Object obj) {
		SecurityUser po = (obj instanceof SecurityUser) ? (SecurityUser) obj : null;
		return CommonUtil.equals(this, po)
			&& CommonUtil.equals(getBranchId(), po.getBranchId())
			&& CommonUtil.equals(getGroupId(), po.getGroupId())
			&& CommonUtil.equals(getPersonId(), po.getPersonId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SecurityUser{id=").append(id)
			.append(", person=").append(getPersonId())
			.append(", belong=").append(getBranchId())
			.append(", group=").append(getGroupId())
			.append(", password1=").append(password1).append("}");
		return sb.toString();
	}

	public static SecurityUser getSafeObject(SecurityUser po) {
		if (Hibernate.isInitialized(po)) {
			if (po instanceof HibernateProxy) return (SecurityUser) ((HibernateProxy) po).getHibernateLazyInitializer().getImplementation();
			else return po;
		} else {
			if (po == null) return null;
			else return new SecurityUser(po.getId());
		}
	}

	public GrantedAuthority[] getAuthorities() {
		return roles;
	}

	public void setAuthorities(GrantedAuthority[] roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return (getPassword1() == null) ? "" : getPassword1();
	}

	public String getUsername() {
		return getUseId();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
