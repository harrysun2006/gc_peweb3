package com.gc.spring;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

public class AccessAdvisor implements PointcutAdvisor {

	public Pointcut getPointcut() {
		return new AccessPointcut();
	}

	public Advice getAdvice() {
		return new AccessInterceptor();
	}

	public boolean isPerInstance() {
		return false;
	}

}
