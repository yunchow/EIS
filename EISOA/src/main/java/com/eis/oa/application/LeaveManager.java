/**
 * fileName: EISOA/com.eis.oa.application/LeaveManager.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.application;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

 /**
 * Title: LeaveManager.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Sep 3, 2013
 */
@Component
public class LeaveManager {

	@Transactional
	public void doLeaveFor(Map<String, String> model) {
		
	}
}
