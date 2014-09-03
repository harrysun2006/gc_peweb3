package com.gc.spring;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;

public class LoggerAdvisor implements PointcutAdvisor {

	public Pointcut getPointcut() {
		return new LoggerPointcut();
	}

	public Advice getAdvice() {
		return new LoggerInterceptor();
	}

	public boolean isPerInstance() {
		return false;
	}

}
