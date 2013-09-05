/**
 * fileName: EISPlatform/com.eis.platform.helper/NullObject.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.core.helper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

 /**
 * Title: NullObject.java
 * <p>
 * Null object pattern implementation
 * </p>
 * read Uncle Bob's book named refactor for more information
 * @author nick.chow
 * @date: Sep 3, 2013
 */
public abstract class NullObject {
	
	/**
	 * @param list
	 * @return actual value, if it's not null, or a safe value
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> safe(List<E> list) {
		return safe(list, Collections.EMPTY_LIST);
	}
	
	/**
	 * @param actual actual value
	 * @param safe a null-safe value
	 * @return actual value, if it's not null, or safe value if the actual value is null.
	 */
	public static <E> List<E> safe(List<E> actual, List<E> safe) {
		return actual == null ? safe : actual;
	}
	
	/**
	 * @param set
	 * @return actual value, if it's not null, or a safe value
	 */
	@SuppressWarnings("unchecked")
	public static <E> Set<E> safe(Set<E> set) {
		return safe(set, Collections.EMPTY_SET);
	}
	
	/**
	 * @param actual actual value
	 * @param safe a null-safe value
	 * @return actual value, if it's not null, or safe value if the actual value is null.
	 */
	public static <E> Set<E> safe(Set<E> actual, Set<E> safe) {
		return actual == null ? safe : actual;
	}
	
	/**
	 * @param map
	 * @return actual value, if it's not null, or a safe value
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> safe(Map<K, V> map) {
		return safe(map, Collections.EMPTY_MAP);
	}
	
	/**
	 * @param actual actual value
	 * @param safe a null-safe value
	 * @return actual value, if it's not null, or safe value if the actual value is null.
	 */
	public static <K, V> Map<K, V> safe(Map<K, V> actual, Map<K, V> safe) {
		return actual == null ? safe : actual;
	}
	
	/**
	 * @param actual actual value
	 * @param safe a null-safe value
	 * @param <T> type
	 * @return actual value, if it's not null, or safe value if the actual value is null.
	 */
	public static <T> T safe(T actual, T safe) {
		return actual == null ? safe : actual;
	}

	/**
	 * Prevent instantiation.
	 */
	private NullObject() {
		
	}
}
