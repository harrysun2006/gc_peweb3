package com.gc.spring;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

public class LoggerPointcut implements Pointcut {

	public ClassFilter getClassFilter() {
		return new LoggerClassFilter();
	}

	public MethodMatcher getMethodMatcher() {
		return new LoggerMethodMatcher();
	}

}
