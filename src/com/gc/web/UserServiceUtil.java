package com.gc.web;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;

import com.gc.common.po.Branch;
import com.gc.common.po.SecurityUser;
import com.gc.util.SpringUtil;

public class UserServiceUtil {

	public static final String BEAN_NAME = "userServiceUtil";

	private UserService userService;

	public static UserService getUserService() {
		ApplicationContext ctx = SpringUtil.getContext();
		UserServiceUtil util = (UserServiceUtil) ctx.getBean(BEAN_NAME);
		UserService service = util.userService;
		return service;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

//==================================== SecurityUser ====================================

	public static List<SecurityUser> getSecurityUsers(SecurityUser su) {
		return getUserService().getSecurityUsers(su);
	}

	public static SecurityUser getSecurityUser(SecurityUser su) {
		return getUserService().getSecurityUser(su);
	}

	public static List<Branch>getLimitBranches(SecurityUser su) {
		// if (su.getUseId().equals("error")) throw new CommonRuntimeException("test");
		return getUserService().getLimitBranches(su);
	}

	public static SecurityUser authenticate(SecurityUser su) throws IOException, ServletException {
		return getUserService().authenticate(su);
	}

	/**
	 * 返回用户配置
	 * @param user
	 * @return
	 */
	public Map getProfile(SecurityUser user) {
		Map profile = new Hashtable();
		return profile;
	}

	/**
	 * 保存用户配置
	 * @param user
	 * @param profile
	 * @return
	 */
	public Object saveProfile(SecurityUser user, Map profile) {
		return new Object();
	}
}
