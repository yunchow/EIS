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

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.eis.base.util.CurrentUserUtil;
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
	 * 根据用户查等待签收的任务
	 * @return
	 */
	public List<LeaveFormDTO> findCandidateLeaveTasks(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findPendingLeaveFormByUser");
		logger.info("leaveDto = {}", leaveDto);
		Assert.notNull(leaveDto, "LeaveFormDTO must not be null");

		long count = createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY).taskCandidateUser(CurrentUserUtil.getUserId()).count();
		leaveDto.setTotal(count);
		List<Task> tasks = createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY).taskCandidateUser(leaveDto.getApplicant())
				.includeProcessVariables().orderByTaskPriority().desc().orderByTaskCreateTime().desc().listPage(leaveDto.getOffset(), leaveDto.getRows());
		
		ArrayList<LeaveFormDTO> resultList = new ArrayList<LeaveFormDTO>(tasks.size());

		for (Task task : tasks) {
			LeaveFormDTO dto = asDtoFrom(task);
			resultList.add(dto);
		}
		logger.info("resultList = {}", resultList);
		logger.info("Exit LeaveService.findPendingLeaveFormByUser");
		return resultList;
	}
	
	/**
	 * 根据用户查询已签收的任务
	 * @return
	 */
	public List<LeaveFormDTO> findClaimedLeaveTasks(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findPendingLeaveFormByUser");
		logger.info("leaveDto = {}", leaveDto);
		Assert.notNull(leaveDto, "LeaveFormDTO must not be null");

		long count = createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY).taskAssignee(CurrentUserUtil.getUserId()).count();
		leaveDto.setTotal(count);
		List<Task> tasks = createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY).taskAssignee(leaveDto.getApplicant())
				.includeProcessVariables().orderByTaskPriority().desc().orderByTaskCreateTime().desc().listPage(leaveDto.getOffset(), leaveDto.getRows());
		
		ArrayList<LeaveFormDTO> resultList = new ArrayList<LeaveFormDTO>(tasks.size());

		for (Task task : tasks) {
			LeaveFormDTO dto = asDtoFrom(task);
			resultList.add(dto);
		}
		logger.info("resultList = {}", resultList);
		logger.info("Exit LeaveService.findPendingLeaveFormByUser");
		return resultList;
	}

	/**
	 * @param task
	 * @return
	 */
	protected LeaveFormDTO asDtoFrom(Task task) {
		LeaveFormDTO dto = new LeaveFormDTO();
		dto.setProcessDefinitionId(task.getProcessDefinitionId());
		dto.setProcessInstanceId(task.getProcessInstanceId());
		
		Map<String, Object> processVariables = task.getProcessVariables();
		dto.setApplicant((String) processVariables.get("starter"));
		
		Object day = processVariables.get("leaveDays");
		if (day == null) {
			dto.setLeaveDays(0);
		} else {
			dto.setLeaveDays(Double.valueOf("" + day));
		}
		
		// N + 1 查询问题，有空再优化
		String leaveId = createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		dto.setLeaveId(leaveId);
		
		// task properties
		dto.setAssignee(task.getAssignee());
		dto.setOwner(task.getOwner());
		dto.setName(task.getName());
		dto.setDescription(task.getDescription());
		dto.setTaskCreateTime(task.getCreateTime());
		dto.setPriority(task.getPriority());
		dto.setTaskId(task.getId());
		dto.setDueDate(task.getDueDate());
		dto.setSuspend(task.isSuspended());
		return dto;
	}

	/**
	 * 我的请假待办，包括未签收和已签收但未处理的申请单
	 * 
	 * 只查等签收的任务
	 * @return
	 */
	public List<LeaveFormDTO> findPendingLeaveFormByUser(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findPendingLeaveFormByUser");
		logger.info("leaveDto = {}", leaveDto);
		Assert.notNull(leaveDto, "LeaveFormDTO must not be null");
		
		List<Group> groups = CurrentUserUtil.getUser().getGroups();
		List<String> candidateGroups = new ArrayList<String>(groups.size());
		for (Group group : groups) {
			candidateGroups.add(group.getId());
		}
		
		TaskQuery taskQuery = createTaskQuery().processDefinitionKey(LEAVE_PROCESS_KEY)
				.taskInvolvedUser(CurrentUserUtil.getUserName()).taskCandidateGroupIn(candidateGroups);
		leaveDto.setTotal(taskQuery.count());
		List<Task> tasks = taskQuery.orderByTaskPriority().desc().orderByTaskCreateTime().desc()
				.includeProcessVariables().listPage(leaveDto.getOffset(), leaveDto.getRows());
		
		ArrayList<LeaveFormDTO> resultList = new ArrayList<LeaveFormDTO>(tasks.size());

		for (Task task : tasks) {
			LeaveFormDTO dto = asDtoFrom(task);
			resultList.add(dto);
		}
		logger.info("resultList = {}", resultList);
		logger.info("Exit LeaveService.findPendingLeaveFormByUser");
		return resultList;
	}
	
	/**
	 * 已办任务
	 * @return
	 */
	public List<LeaveFormDTO> findInvolvedHistoryLeave(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.findInvolvedHistoryLeaveForm");
		logger.info("leaveDto = {}", leaveDto);
		Assert.notNull(leaveDto, "LeaveFormDTO must not be null");
		
		HistoricTaskInstanceQuery historicTaskInstanceQuery = createHistoricTaskInstanceQuery().finished()
				.taskInvolvedUser(CurrentUserUtil.getUserId()).processDefinitionKey(LEAVE_PROCESS_KEY);
		leaveDto.setTotal(historicTaskInstanceQuery.count());
		List<HistoricTaskInstance> historyTasks = historicTaskInstanceQuery
				.orderByHistoricTaskInstanceStartTime().desc().listPage(leaveDto.getOffset(), leaveDto.getRows());
		List<LeaveFormDTO> resultList = new ArrayList<LeaveFormDTO>(historyTasks.size());
		
		for (HistoricTaskInstance task : historyTasks) {
			LeaveFormDTO dto = new LeaveFormDTO();
			dto.setStartTime(task.getStartTime());
			dto.setEndTime(task.getEndTime());
			dto.setAssignee(task.getAssignee());
			dto.setOwner(task.getOwner());
			dto.setName(task.getName());
			dto.setDescription(task.getDescription());
			dto.setPriority(task.getPriority());
			dto.setTaskId(task.getId());
			dto.setDueDate(task.getDueDate());
			
			// N + 1 查询问题，有空再优化
			/*String leaveId = createHistoricProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
			dto.setLeaveId(leaveId);*/
			
			Map<String, Object> processVariables = task.getProcessVariables();
			dto.setApplicant((String) processVariables.get("starter"));
			
			Object day = processVariables.get("leaveDays");
			if (day == null) {
				dto.setLeaveDays(0);
			} else {
				dto.setLeaveDays(Double.valueOf("" + day));
			}				
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
			dto.setProcessInstanceId(entity.getProcKey());
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
	public Task doLeaveFor(LeaveFormDTO leaveDto) {
		logger.info("Enter LeaveService.doLeaveFor");
		logger.info("leaveDto = {}", leaveDto);
		String id = UUIDHelper.uuid();
		long start = leaveDto.getStartTime().getTime();
		long end = leaveDto.getEndTime().getTime();
		double days = (end - start) / 1000 / 3600 / 24;
		
		Map<String, Object> variables = new HashMap<String, Object>(2);
		variables.put("starter", CurrentUserUtil.getUserId());
		variables.put("leaveDays", days);
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
