/**
 * fileName: EISPlatform/com.eis.platform.workflow.persistence/PKFactory.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 6, 2013
 */
package com.eis.platform.workflow.persistence;

import java.util.UUID;

 /**
 * Title: PKFactory.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 6, 2013
 */
public class PKFactory {

	/**
	 * @return uuid string
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(uuid());
	}
}
