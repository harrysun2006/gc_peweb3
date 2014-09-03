package com.gc.util;

import java.util.Hashtable;
import java.util.Map;

public class CacheUtil {
	private static Map cache = new Hashtable();

	public static Object get(String name) {
		return cache.get(name);
	}

	public static void put(String name, Object value) {
		put(name, value, true);
	}

	public static void put(String name, Object value, boolean force) {
		if (force || !cache.containsKey(name)) cache.put(name, value);
	}
}
