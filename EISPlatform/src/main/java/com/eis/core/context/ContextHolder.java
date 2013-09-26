/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 24, 2013
 */
package com.eis.core.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 24, 2013
 */
public class ContextHolder {
	private static ThreadLocal<Map<String, Object>> mapHolder = new ThreadLocal<Map<String, Object>>();
	
	protected static Map<String, Object> getContextMap() {
		Map<String, Object> map = mapHolder.get();
		if (map == null) {
			map = new ConcurrentHashMap<String, Object>();
			mapHolder.set(map);
		}
		return map;
	}
	
	public static String getString(String key) {
		return (String) getObject(key);
	}
	
	public static Integer getInt(String key) {
		return (Integer) getObject(key);
	}
	
	public static Long getLong(String key) {
		return (Long) getObject(key);
	}
	
	public static Double getDouble(String key) {
		return (Double) getObject(key);
	}
	
	public static Float getFloat(String key) {
		return (Float) getObject(key);
	}
	
	public static Byte getByte(String key) {
		return (Byte) getObject(key);
	}
	
	public static Character getChar(String key) {
		return (Character) getObject(key);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String key, T t) {
		return (T) getObject(key);
	}

	public static Object getObject(String key) {
		Assert.notNull(key, "key must not be null");
		return getContextMap().get(key);
	}
	
	public static void set(String key, Object value) {
		Assert.notNull(key, "key must not be null");
		Assert.notNull(value, "value must not be null");
		getContextMap().put(key, value);
	}
	
}
