/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 9, 2013
 */
package com.eis.oa.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eis.core.context.ActivitiAwareSupport;
import com.eis.core.helper.UUIDHelper;
import com.eis.oa.application.dto.LeaveFormDTO;
import com.eis.oa.domain.model.leave.LeaveFormEntity;
import com.eis.oa.domain.model.leave.LeaveRepository;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 9, 2013
 */
@Component
public class LeaveService extends ActivitiAwareSupport {
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	/**
	 * 我的请假待办
	 * @return
	 */
	public List<LeaveFormDTO> findPendingLeaveFormByUser(LeaveFormDTO leaveDto) {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("LeaveProcess").involvedUser(leaveDto.getApplicant()).list();
		ArrayList<LeaveFormDTO> list = new ArrayList<LeaveFormDTO>();
		for (ProcessInstance processInstance : processInstances) {
			LeaveFormDTO dto = new LeaveFormDTO();
			String leaveId = processInstance.getBusinessKey();
			dto.setLeaveId(leaveId);
			dto.setProcessDefinitionId(processInstance.getProcessDefinitionId());
			dto.setProcessInstanceId(processInstance.getProcessInstanceId());
			Map<String, Object> processVariables = processInstance.getProcessVariables();
			dto.setApplicant((String) processVariables.get("starter"));
			list.add(dto);
		}
		list.trimToSize();
		return list;
	}
	
	/**
	 * 我的请假已办
	 * @return
	 */
	public List<LeaveFormDTO> findHistoryLeaveFormByUser(LeaveFormDTO leaveDto) {
		List<HistoricProcessInstance> processInstances = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey("Leaveprocess").involvedUser(leaveDto.getApplicant()).list();
		ArrayList<LeaveFormDTO> list = new ArrayList<LeaveFormDTO>();
		for (HistoricProcessInstance processInstance : processInstances) {
			LeaveFormDTO dto = new LeaveFormDTO();
			String leaveId = processInstance.getBusinessKey();
			dto.setLeaveId(leaveId);
			dto.setStartTime(processInstance.getStartTime());
			dto.setEndTime(processInstance.getEndTime());
			dto.setProcessDefinitionId(processInstance.getProcessDefinitionId());
			Map<String, Object> processVariables = processInstance.getProcessVariables();
			dto.setApplicant((String) processVariables.get("starter"));
			list.add(dto);
		}
		list.trimToSize();
		return list;
	}
	
	/**
	 * 我的请假申请
	 * @return
	 */
	public List<LeaveFormDTO> findMyApplyLeaveList(LeaveFormDTO leaveDto) {
		List<LeaveFormEntity> leaveEntityList = leaveRepository.findLeaveByPage(leaveDto);
		ArrayList<LeaveFormDTO> leaveDtoList = new ArrayList<LeaveFormDTO>(leaveEntityList.size());
		for (LeaveFormEntity entity : leaveEntityList) {
			LeaveFormDTO dto = new LeaveFormDTO();
			BeanUtils.copyProperties(entity, dto);
			leaveDtoList.add(dto);
		}
		return leaveDtoList;
	}
	
	/**
	 * 提交请假申请，并自动处理第一个任务
	 * @param leaveDto
	 * @return
	 */
	@Transactional
	public Task doLeaveFor(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveManager.doLeaveFor");
		String id = UUIDHelper.uuid();
		Map<String, Object> variables = new HashMap<String, Object>(1);
		variables.put("starter", leaveDto.getApplicant());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LeaveProcess", id, variables);
		
		LeaveFormEntity leaveForm = new LeaveFormEntity();
		//leaveForm.setReason(model.get("reason"));
		BeanUtils.copyProperties(leaveDto, leaveForm);
		leaveForm.setId(id);
		leaveForm.setCreateTime(new Date());
		leaveForm.setProcessInstanceKey(processInstance.getProcessInstanceId());		
		int count = leaveRepository.save(leaveForm);
		logger.info("count = {}", count);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processInstanceId(processInstance.getProcessInstanceId()).singleResult();
		taskService.claim(task.getId(), leaveDto.getApplicant());
		taskService.complete(task.getId());
		
		task = taskQuery.processInstanceId(processInstance.getProcessInstanceId()).singleResult();
		
		logger.info("Exit LeaveManager.doLeaveFor");
		return task;
	}
}
