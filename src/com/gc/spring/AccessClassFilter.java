package com.gc.spring;

import org.springframework.aop.ClassFilter;

public class AccessClassFilter implements ClassFilter {

	 public boolean matches(Class clazz) {
		 return true;
	 }

}

