/**
 * fileName: EISOA/com.eis.oa.application/LeaveManager.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.application;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eis.core.context.ActivitiAwareSupport;
import com.eis.core.helper.UUIDHelper;
import com.eis.oa.domain.model.leave.LeaveForm;
import com.eis.oa.domain.model.leave.LeaveRepository;

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
public class LeaveManager extends ActivitiAwareSupport {
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Transactional
	public Task doLeaveFor(Map<String, String> model) {
		logger.info("Enter LeaveManager.doLeaveFor");
		String id = UUIDHelper.uuid();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveProcess", id);
		
		LeaveForm leaveForm = new LeaveForm();
		leaveForm.setReason(model.get("reason"));
		leaveForm.setId(id);
		leaveForm.setCreateTime(new Date());
		leaveForm.setProcessInstanceId(processInstance.getProcessInstanceId());		
		int count = leaveRepository.save(leaveForm);
		logger.info("count = {}", count);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processInstanceId(processInstance.getProcessInstanceId()).singleResult();
		taskService.claim(task.getId(), "user");
		taskService.complete(task.getId());
		
		task = taskQuery.processInstanceId(processInstance.getProcessInstanceId()).singleResult();
		
		logger.info("Exit LeaveManager.doLeaveFor");
		return task;
	}
}
