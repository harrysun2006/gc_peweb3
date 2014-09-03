package com.gc.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AccessInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object r = null;
		// auth.getPrincipal ==> SecurityUser or ROLE_ANONYMOUS
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			// String target = invocation.getThis().getClass().getName() + "." + invocation.getMethod().getName();
			// System.out.println(target + " calling...");
			r = invocation.proceed();
			// System.out.println(target + " called!");
		} catch (Throwable t) {
			throw t;
		}
		return r;
	}

}