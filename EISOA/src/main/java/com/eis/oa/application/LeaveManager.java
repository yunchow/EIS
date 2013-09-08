/**
 * fileName: EISOA/com.eis.oa.application/LeaveManager.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eis.core.context.ActivitiAwareSupport;
import com.eis.core.helper.UUIDHelper;
import com.eis.oa.domain.model.leave.LeaveForm;
import com.eis.oa.domain.model.leave.LeaveRepository;
import com.eis.oa.interfaces.dto.LeaveFormDTO;

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
	
	public List<LeaveFormDTO> findPendingLeaveFormByUser() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("LeaveProcess").variableValueEquals("starter", "user").list();
		ArrayList<LeaveFormDTO> list = new ArrayList<LeaveFormDTO>();
		for (ProcessInstance processInstance : processInstances) {
			LeaveFormDTO dto = new LeaveFormDTO();
			list.add(dto);
			String leaveId = processInstance.getBusinessKey();
			
		}
		list.trimToSize();
		return list;
	}
	
	/**
	 * 我的请假申请
	 * @return
	 */
	public List<LeaveFormDTO> findMyApplyLeaveList() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("LeaveProcess").variableValueEquals("starter", "user").list();
		ArrayList<LeaveFormDTO> list = new ArrayList<LeaveFormDTO>();
		
		return list;
	}
	
	@Transactional
	public Task doLeaveFor(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveManager.doLeaveFor");
		String id = UUIDHelper.uuid();
		Map<String, Object> variables = new HashMap<String, Object>(1);
		variables.put("starter", "user");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveProcess", id, variables);
		
		LeaveForm leaveForm = new LeaveForm();
		//leaveForm.setReason(model.get("reason"));
		BeanUtils.copyProperties(leaveDto, leaveForm);
		leaveForm.setId(id);
		leaveForm.setCreateTime(new Date());
		leaveForm.setProcessInstanceKey(processInstance.getProcessInstanceId());		
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
