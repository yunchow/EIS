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

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		Task task = createTaskQuery().taskId(taskId).singleResult();
		model.addAttribute("task", task);
		return "leave/form.ftl";
	}
	
	@RequestMapping("/runtime/image/{executionId}")
	public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response) throws Exception {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		//if (processEngineConfiguration instanceof ProcessEngineConfigurationImpl) {
			//Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl)processEngineConfiguration);
		//}
		InputStream is = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
		IOUtils.copy(is, response.getOutputStream());
	}
	
	@RequestMapping("/task/claim/{taskId}")
	@ResponseBody
	public Map<String, Object> claimTaskBy(@PathVariable String taskId) {
		String userId = "manager";
		Map<String, Object> map = new HashMap<String, Object>(2);
		try {
			taskService.claim(taskId, userId);
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
		leaveDto.setApplicant("manager");
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findPendingLeaveFormByUser(leaveDto));
	}
	
	@RequestMapping("/claimed/task")
	@ResponseBody
	public Map<String, Object> findClaimedLeaveTasks(LeaveFormDTO leaveDto) {
		leaveDto.setApplicant("manager");
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findClaimedLeaveTasks(leaveDto));
	}
	
	@RequestMapping("/candidate/task")
	@ResponseBody
	public Map<String, Object> findCandidateLeaveTasks(LeaveFormDTO leaveDto) {
		leaveDto.setApplicant("manager");
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findCandidateLeaveTasks(leaveDto));
	}
	
	@RequestMapping("/my/history")
	@ResponseBody
	public Map<String, Object> findHistoryLeaveFormByUser(LeaveFormDTO leaveDto) {
		leaveDto.setApplicant("user");
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findInvolvedHistoryLeave(leaveDto));
	}
	
	@RequestMapping("/my/list")
	@ResponseBody
	public Map<String, Object> findMyApplingLeaveList(LeaveFormDTO leaveDto) {
		leaveDto.setApplicant("user");
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findMyApplyLeaveList(leaveDto));
	}
	
	@RequestMapping("/do/apply")
	@ResponseBody
	public Map<String, Object> doApplyLeaveForm(LeaveFormDTO leaveDto) {
		leaveDto.setApplicant("user");
		Map<String, Object> result = new HashMap<String, Object>();
		Task task = leaveManager.doLeaveFor(leaveDto);
		result.put("result", true);
		result.put("nextTaskName", task.getName());
		result.put("nextAssignee", task.getAssignee());
		return result;
	}
	
}
