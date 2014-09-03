package com.gc.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.SpringSecurityFilter;
import org.springframework.security.util.RedirectUtils;

import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityUser;

import flex.messaging.FlexContext;

public class AutoLoginFilter extends SpringSecurityFilter {

	private String defaultTargetUrl;

	/**
	 * 自动登录的URL形式为?username=&password=&branch=&module=hr
	 * limit branch的处理方式为:
	 * 1. 如果su.getLimits().size() < 1, return null, 失败
	 * 2. 如果su.getLimits().size() == 1, return su, 忽略传入的branch参数
	 * 3. 如果su.getLimits().size() > 1, 查找与branch匹配的limit, 如果找到设置为此limit, 否则设置第一个
	 */
	protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				chain.doFilter(request, response);
				return;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		FlexContext.setThreadLocalHttpRequest(request);
		FlexContext.setThreadLocalHttpResponse(response);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String branch = request.getParameter("branch");
		if (username == null || username == "" || password == null || password == "") {
			chain.doFilter(request, response);
			return;
		}
		SecurityUser su = new SecurityUser(username, password, branch);
		su = UserServiceUtil.authenticate(su);
		if (su == null) {
			chain.doFilter(request, response);
		} else {
			String targetUrl = determineTargetUrl(request);
			RedirectUtils.sendRedirect(request, response, targetUrl, false);
		}
	}

	protected String determineTargetUrl(HttpServletRequest request) {
  	String module = request.getParameter("module");
  	String targetUrl = "";
  	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  	if (auth != null) {
			SecurityUser su = (SecurityUser) auth.getPrincipal();
			SecurityLimit limit = su.getLimit();
	  	if (module != null && limit != null) {
	  		if (module.equals("hr") && limit.getHrLimit() > 0L) targetUrl = "/hr/index.html";
	  		else if (module.equals("safety") && limit.getSoftLimit() > 0L) targetUrl = "/safety/index.html";
	  		else targetUrl = "/index.html";
	  	}
  	}
  	return targetUrl;
	}
  
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public String getDefaultFilterProcessesUrl() {
		return "/*";
	}

	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return SecurityContextHolder.getContext().getAuthentication() == null;
	}

	public int getOrder() {
		return FilterChainOrder.AUTHENTICATION_PROCESSING_FILTER - 1;
	}

}
