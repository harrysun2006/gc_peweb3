package com.gc.spring;

import java.lang.reflect.Method;

import org.springframework.aop.MethodMatcher;

public class LoggerMethodMatcher implements MethodMatcher {

	public boolean isRuntime() {
		return false;
	}

	public boolean matches(Method method, Class clazz) {
		// String methodName = method.getName();
		return true;
	}

	public boolean matches(Method method, Class clazz, Object[] args) {
		return true;
	}

}