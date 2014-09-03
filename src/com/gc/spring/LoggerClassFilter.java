package com.gc.spring;

import org.springframework.aop.ClassFilter;

public class LoggerClassFilter implements ClassFilter {

	 public boolean matches(Class clazz) {
		 return true;
	 }

}

