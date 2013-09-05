/**
 * fileName: EISPlatform/com.eis.platform.helper/UUIDHelper.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 15, 2013
 */
package com.eis.core.helper;

import java.util.UUID;

 /**
 * Title: UUIDHelper.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 15, 2013
 */
public class UUIDHelper {

	public static String uuid() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("\\-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(uuid());
	}
}
