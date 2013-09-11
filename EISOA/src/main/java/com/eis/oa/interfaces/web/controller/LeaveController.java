/**
 * fileName: EISOA/com.eis.oa.interfaces.web.controller/LeaveController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.interfaces.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.oa.application.LeaveManager;
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
public class LeaveController {
	
	@Autowired
	private LeaveManager leaveManager;

	@Autowired
	private LeaveService leaveService;

	@RequestMapping("/index")
	public String index() {
		return "leave/index.ftl";
	}
	
	@RequestMapping("/form")
	public String form() {
		return "leave/form.ftl";
	}
	
	@RequestMapping("/grid/{type}")
	public String grid(@PathVariable String type) {
		return "leave/grid_"+ type +".ftl";
	}
	
	@RequestMapping("/my/pending")
	@ResponseBody
	public Map<String, Object> findPendingLeaveFormByUser(LeaveFormDTO leaveDto) {
		leaveDto.setApplicant("user");
		return LeaveMapAssembler.asMap(leaveDto, leaveService.findPendingLeaveFormByUser(leaveDto));
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
