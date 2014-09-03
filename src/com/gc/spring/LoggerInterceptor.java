package com.gc.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.gc.common.po.EventLog;
import com.gc.common.po.SecurityUser;

public class LoggerInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Log _log = LogFactory.getLog(invocation.getThis().getClass());
		Object r = null;
		// SecurityUser user = ThreadSession.getUser();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser user = (auth != null && auth.getPrincipal() instanceof SecurityUser) ? (SecurityUser) auth.getPrincipal() : null;
		EventLog log = new EventLog();
		if (user != null) {
			log.setBranch(user.getBranch());
			log.setPerson(user.getPerson());
		}
		log.setClassName(invocation.getThis().getClass().getName());
		log.setMethodName(invocation.getMethod().getName());
		try {
			r = invocation.proceed();
			_log.info(log);
		} catch (Throwable t) {
			_log.error(log, t);
			throw t;
		}
		return r;
	}

}