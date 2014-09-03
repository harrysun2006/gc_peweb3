package com.gc.web;

import org.springframework.web.context.support.XmlWebApplicationContext;

import com.gc.Constants;
import com.gc.util.PropsUtil;
import com.gc.util.SpringUtil;

public class PropertiesApplicationContext extends XmlWebApplicationContext {

	public String[] getConfigLocations() {
		String[] configs = PropsUtil.getArray(Constants.PROP_SPRING_CONFIG_FILES);
		for (int i = 0; i < configs.length; i++) configs[i] = configs[i].trim();
		return configs;
	}

	protected void onRefresh() {
		super.onRefresh();
		SpringUtil.setContext(this);
	}
}
