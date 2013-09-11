/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 9, 2013
 */
package com.eis.oa.domain.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.eis.core.context.ActivitiAwareSupport;
import com.eis.core.helper.UUIDHelper;
import com.eis.oa.domain.model.leave.LeaveFormEntity;
import com.eis.oa.domain.model.leave.LeaveRepository;
import com.eis.oa.infrastructure.dto.LeaveFormDTO;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 9, 2013
 */
@Component
public class LeaveService extends ActivitiAwareSupport {
	/**
	 * 请假流程ID
	 */
	public static final String LEAVE_PROCESS_KEY = "LeaveProcess";
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	/**
	 * 我的请假待办，包括未签收和已签收但未处理的申请单
	 * @return
	 */
	public List<LeaveFormDTO> findPendingLeaveFormByUser(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findPendingLeaveFormByUser");
		logger.info("leaveDto = {}", leaveDto);
		Assert.notNull(leaveDto, "LeaveFormDTO must not be null");
		//long count = taskService.createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY).taskCandidateUser(leaveDto.getApplicant()).count();
		//leaveDto.setTotal(count);
		//List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY).taskCandidateUser(leaveDto.getApplicant()).listPage(leaveDto.getOffset(), leaveDto.getRows());
		
		final String sql = "from " + managementService.getTableName(Task.class) + " where OWNER_=#{applicant} or ASSIGNEE_=#{applicant}";
		logger.info("sql = {}", sql);
		
		NativeTaskQuery nativeTaskQueryCount = taskService.createNativeTaskQuery().sql("select count(*) " + sql).parameter("applicant", leaveDto.getApplicant());;
		NativeTaskQuery nativeTaskQueryList = taskService.createNativeTaskQuery().sql("select * " + sql).parameter("applicant", leaveDto.getApplicant());;
		leaveDto.setTotal(nativeTaskQueryCount.count());
		List<Task> tasks = nativeTaskQueryList.listPage(leaveDto.getOffset(), leaveDto.getRows());
		
		ArrayList<LeaveFormDTO> resultList = new ArrayList<LeaveFormDTO>(tasks.size());
		for (Task task : tasks) {
			LeaveFormDTO dto = new LeaveFormDTO();
			dto.setProcessDefinitionId(task.getProcessDefinitionId());
			dto.setProcessInstanceId(task.getProcessInstanceId());
			Map<String, Object> processVariables = task.getProcessVariables();
			dto.setApplicant((String) processVariables.get("starter"));
			resultList.add(dto);
		}
		logger.info("resultList = {}", resultList);
		logger.info("Exit LeaveService.findPendingLeaveFormByUser");
		return resultList;
		/*List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey(LEAVE_PROCESS_KEY).involvedUser(leaveDto.getApplicant()).list();
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
		return list;*/
	}
	
	/**
	 * 我参与过的历史请假已办
	 * @return
	 */
	public List<LeaveFormDTO> findInvolvedHistoryLeave(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findInvolvedHistoryLeaveForm");
		logger.info("leaveDto = {}", leaveDto);
		Assert.notNull(leaveDto, "LeaveFormDTO must not be null");
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(LEAVE_PROCESS_KEY).involvedUser(leaveDto.getApplicant());
		
		leaveDto.setTotal(historicProcessInstanceQuery.count());
		List<HistoricProcessInstance> processInstances = historicProcessInstanceQuery.listPage(leaveDto.getOffset(), leaveDto.getRows());
		ArrayList<LeaveFormDTO> resultList = new ArrayList<LeaveFormDTO>(processInstances.size());
		
		for (HistoricProcessInstance processInstance : processInstances) {
			LeaveFormDTO dto = new LeaveFormDTO();
			String leaveId = processInstance.getBusinessKey();
			dto.setLeaveId(leaveId);
			dto.setStartTime(processInstance.getStartTime());
			dto.setEndTime(processInstance.getEndTime());
			dto.setProcessDefinitionId(processInstance.getProcessDefinitionId());
			Map<String, Object> processVariables = processInstance.getProcessVariables();
			dto.setApplicant((String) processVariables.get("starter"));
			resultList.add(dto);
		}
		logger.info("returned resultList = {}", resultList);
		logger.info("Exit LeaveService.findInvolvedHistoryLeaveForm");
		return resultList;
	}
	
	/**
	 * 我的请假申请
	 * @return
	 */
	public List<LeaveFormDTO> findMyApplyLeaveList(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findMyApplyLeaveList");
		logger.info("leaveDto = {}", leaveDto);
		List<LeaveFormEntity> leaveEntityList = leaveRepository.findLeaveByPage(leaveDto);
		ArrayList<LeaveFormDTO> leaveDtoList = new ArrayList<LeaveFormDTO>(leaveEntityList.size());
		for (LeaveFormEntity entity : leaveEntityList) {
			LeaveFormDTO dto = new LeaveFormDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setLeaveId(entity.getId());
			leaveDtoList.add(dto);
		}
		logger.info("returned leaveDtoList = {}", leaveDtoList);
		logger.info("Exit LeaveService.findMyApplyLeaveList");
		return leaveDtoList;
	}
	
	/**
	 * 提交请假申请，并自动处理第一个任务
	 * @param leaveDto
	 * @return
	 */
	@Transactional
	public Task doLeaveFor(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.doLeaveFor");
		logger.info("leaveDto = {}", leaveDto);
		String id = UUIDHelper.uuid();
		Map<String, Object> variables = new HashMap<String, Object>(1);
		variables.put("starter", leaveDto.getApplicant());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(LEAVE_PROCESS_KEY, id, variables);
		
		LeaveFormEntity leaveForm = new LeaveFormEntity(id, processInstance.getProcessInstanceId(), leaveDto);
		int count = leaveRepository.save(leaveForm);
		logger.info("count = {}", count);
		
		TaskQuery taskQuery = taskService.createTaskQuery();
		Task task = taskQuery.processInstanceId(processInstance.getProcessInstanceId()).singleResult();
		taskService.claim(task.getId(), leaveDto.getApplicant());
		taskService.complete(task.getId());
		
		task = taskQuery.processInstanceId(processInstance.getProcessInstanceId()).singleResult();

		logger.info("Next task = {}", task);
		logger.info("Exit LeaveService.doLeaveFor");
		return task;
	}
}
