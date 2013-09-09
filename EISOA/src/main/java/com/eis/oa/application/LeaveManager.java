/**
 * fileName: EISOA/com.eis.oa.application/LeaveManager.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.application;

import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eis.oa.domain.service.LeaveService;
import com.eis.oa.infrastructure.dto.LeaveFormDTO;

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
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LeaveService LeaveService;
	
	@Transactional
	public Task doLeaveFor(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveManager.doLeaveFor");
		try {
			return LeaveService.doLeaveFor(leaveDto);
		} finally {
			logger.info("Exit LeaveManager.doLeaveFor");
		}
	}
}
