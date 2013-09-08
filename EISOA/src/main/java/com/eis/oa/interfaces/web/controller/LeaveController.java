/**
 * fileName: EISOA/com.eis.oa.interfaces.web.controller/LeaveController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.interfaces.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.oa.application.LeaveManager;
import com.eis.oa.interfaces.dto.LeaveFormDTO;

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

	@RequestMapping("/index")
	public String index() {
		return "leave/index.ftl";
	}
	
	@RequestMapping("/form")
	public String form() {
		return "leave/form.ftl";
	}
	
	@RequestMapping("/grid")
	public String grid() {
		return "leave/grid.ftl";
	}
	
	@RequestMapping("/current/pending")
	@ResponseBody
	public List<LeaveFormDTO> findPendingLeaveFormByUser(LeaveFormDTO leaveDto) {
		return null;
	}
	
	@RequestMapping("/apply/list")
	@ResponseBody
	public List<LeaveFormDTO> findMyApplingLeaveList() {
		return null;
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
