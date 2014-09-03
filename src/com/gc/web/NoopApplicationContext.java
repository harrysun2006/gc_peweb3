package com.gc.web;

import org.springframework.web.context.support.XmlWebApplicationContext;

public class NoopApplicationContext extends XmlWebApplicationContext {

	private final static String[] NOOP_CONFIG_LOCATIONS = {};

	public String[] getConfigLocations() {
		return NOOP_CONFIG_LOCATIONS;
	}

}
