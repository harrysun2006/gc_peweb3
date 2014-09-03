package com.gc.spring;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

public class AccessPointcut implements Pointcut {

	public ClassFilter getClassFilter() {
		return new AccessClassFilter();
	}

	public MethodMatcher getMethodMatcher() {
		return new AccessMethodMatcher();
	}

}
