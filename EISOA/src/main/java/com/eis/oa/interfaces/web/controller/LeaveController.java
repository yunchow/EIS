/**
 * fileName: EISOA/com.eis.oa.interfaces.web.controller/LeaveController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.interfaces.web.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.eis.base.util.CurrentUserUtil;
import com.eis.core.activiti.ProcessDiagramGenerator;
import com.eis.core.context.ActivitiAwareSupport;
import com.eis.oa.application.LeaveManager;
import com.eis.oa.domain.model.leave.LeaveFormEntity;
import com.eis.oa.domain.model.leave.LeaveRepository;
import com.eis.oa.domain.service.LeaveService;
import com.eis.oa.infrastructure.dto.LeaveFormDTO;
import com.eis.oa.interfaces.assembler.LeaveMapAssembler;

 /**
 * Title: LeaveController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Sep 3, 2013
 */
@Controller
@RequestMapping("/oa/leave/")
@SessionAttributes("user")
public class LeaveController extends ActivitiAwareSupport {
	
	@Autowired
	private LeaveManager leaveManager;

	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private LeaveRepository leaveRepository;

	@RequestMapping("/index")
	public String index() {
		return "leave/index.ftl";
	}
	
	@RequestMapping("/form")
	public String form(ModelMap model) {
		model.addAttribute("status", "new");
		return "leave/form.ftl";
	}
	
	@RequestMapping("/grid/{type}")
	public String grid(@PathVariable String type) {
		return "leave/grid_"+ type +".ftl";
	}
	
	@RequestMapping("/detail/{status}/{leaveId}")
	public String viewLeaveDetail(@PathVariable String status, @PathVariable String leaveId, 
			@RequestParam String taskId, ModelMap model) {
		LeaveFormEntity entity = leaveRepository.findById(leaveId);
		model.addAttribute("leaveForm", entity);
		//model.addAttribute("status", status);
		String executionId;
		
		if ("history".equals(status)) {
			HistoricTaskInstance  historicTaskInstance = getHistoricTaskInstanceByTaskId(taskId);
			model.addAttribute("task", historicTaskInstance);
			model.addAttribute("execution", historicTaskInstance);
			executionId = historicTaskInstance.getExecutionId();
		} else if ("list".equals(status)) {
			HistoricProcessInstance procInstance = getHistoricProcessInstanceByBusinessKey(leaveId);
			model.addAttribute("execution", procInstance);
			executionId = procInstance.getId(); // 这里有可能会有问题，应该为executionId才能保证一定正确吧？但正常情况下，二者却是相等，考虑子流程情况。。。
		} else {
			Execution execution = getExecutionBusinessKey(leaveId);
			model.addAttribute("execution", execution);
			
			Task task = getTaskByBusinessKey(leaveId);
			model.addAttribute("task", task);
			executionId = execution.getId();
		}
		
		List<HistoricTaskInstance> historyTasks = createHistoricTaskInstanceQueryByBusinessKey(leaveId).orderByHistoricTaskInstanceEndTime().desc().list();
		model.addAttribute("historyTasks", historyTasks);
		
		List<HistoricActivityInstance> activityHistoyList = createHistoricActivityInstanceQuery().processInstanceId(executionId).list();
		model.addAttribute("activityHistoyList", activityHistoyList);
		return "leave/form.ftl";
	}

	@RequestMapping("/task/complete")
	@ResponseBody
	public Map<String, Object> completeTask(@RequestParam String taskId, 
			@RequestParam boolean approve) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("approve", approve);
		try {
			taskService.complete(taskId, variables);
			map.put("result", true);
		} catch (ActivitiObjectNotFoundException e) {
			logger.error(e.getMessage(), e);
			map.put("result", false);
			map.put("message", e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("/proc/diagram/{processInstanceId}")
	public void generateProcessDiagram(@PathVariable String processInstanceId, HttpServletResponse response) throws Exception {
		String defid = getHistoricProcessInstanceById(processInstanceId).getProcessDefinitionId();
		List<HistoricActivityInstance> activitys = getHistoricActivitiesByProcessInstanceId(processInstanceId);
		if (processEngineConfiguration instanceof ProcessEngineConfigurationImpl) {
			Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl)processEngineConfiguration);
		}
		InputStream is = ProcessDiagramGenerator.generatePngDiagramFor(getBpmnModelByProcessDefinitionId(defid), activitys);
		IOUtils.copy(is, response.getOutputStream());
	}
	
	@RequestMapping("/task/claim/{taskId}")
	@ResponseBody
	public Map<String, Object> claimTaskBy(@PathVariable String taskId) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		try {
			taskService.claim(taskId, CurrentUserUtil.getUserId());
			map.put("result", true);
		} catch (ActivitiObjectNotFoundException e) {
			map.put("result", false);
			map.put("message", "任务不存在");
		} catch (ActivitiTaskAlreadyClaimedException ex) {
			map.put("result", false);
			map.put("message", "任务已签收");
		}
		return map;
	} 
	
	@RequestMapping("/my/pending")
	@ResponseBody
	public Map<String, Object> findPendingLeaveFormByUser(LeaveFormDTO leaveDto) {
		
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findPendingLeaveFormByUser(leaveDto));
	}
	
	@RequestMapping("/claimed/task")
	@ResponseBody
	public Map<String, Object> findClaimedLeaveTasks(LeaveFormDTO leaveDto) {
		
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findClaimedLeaveTasks(leaveDto));
	}
	
	@RequestMapping("/candidate/task")
	@ResponseBody
	public Map<String, Object> findCandidateLeaveTasks(LeaveFormDTO leaveDto) {
		
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findCandidateLeaveTasks(leaveDto));
	}
	
	@RequestMapping("/my/history")
	@ResponseBody
	public Map<String, Object> findHistoryLeaveFormByUser(LeaveFormDTO leaveDto) {
		
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findInvolvedHistoryLeave(leaveDto));
	}
	
	@RequestMapping("/my/list")
	@ResponseBody
	public Map<String, Object> findMyApplingLeaveList(LeaveFormDTO leaveDto) {
		
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findMyApplyLeaveList(leaveDto));
	}
	
	@RequestMapping("/do/apply")
	@ResponseBody
	public Map<String, Object> doApplyLeaveForm(LeaveFormDTO leaveDto) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Task task = leaveManager.doLeaveFor(leaveDto);
			result.put("result", true);
			result.put("nextTaskName", task.getName());
			result.put("nextAssignee", task.getAssignee());
		} catch (ActivitiException e) {
			result.put("result", false);
			result.put("message", "流程已被挂起，联系管理员");
		}
		return result;
	}
	
}
