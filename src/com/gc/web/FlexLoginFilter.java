package com.gc.web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityUser;
import com.gc.exception.CommonRuntimeException;

import flex.messaging.FlexContext;

public class FlexLoginFilter extends AuthenticationProcessingFilter {

	public void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException,
		ServletException {
		FlexContext.setThreadLocalHttpRequest(request);
		FlexContext.setThreadLocalHttpResponse(response);
		// super.doFilterHttp(request, response, chain);
		chain.doFilter(request, response);
	}

  protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      Authentication authResult) throws IOException {
		String branchUseId = request.getParameter("j_branchUseId");
		if (authResult.getPrincipal() instanceof SecurityUser) {
			SecurityUser su = (SecurityUser) authResult.getPrincipal();
			SecurityLimit limit;
			if (su.getLimits().size() < 1) {
				throw new CommonRuntimeException("login.error.user.has.no.limit.branch");
			}	else if (su.getLimits().size() == 1) {
				su.setLimit(su.getLimits().get(0));
			}	else {
				su.setLimit(su.getLimits().get(0));
				for (Iterator<SecurityLimit> it = su.getLimits().iterator(); it.hasNext(); ) {
					limit = it.next();
					if (limit.getBranchUseId().equals(branchUseId)) {
						su.setLimit(limit);
					}
				}
			}
		}
  }

}