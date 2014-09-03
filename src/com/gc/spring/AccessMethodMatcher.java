package com.gc.spring;

import java.lang.reflect.Method;

import org.springframework.aop.MethodMatcher;

public class AccessMethodMatcher implements MethodMatcher {

	public boolean isRuntime() {
		return false;
	}

	public boolean matches(Method method, Class clazz) {
		// String methodName = method.getName();
		return true;
	}

	public boolean matches(Method arg0, Class arg1, Object[] arg2) {
		return false;
	}

}