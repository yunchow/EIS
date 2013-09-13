/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 6, 2013
 */
package com.eis.oa.infrastructure.dto;

import java.io.Serializable;
import java.util.Date;

import com.eis.core.dto.PageDTO;

/**
 * <p>
 * Please comment here
 * 
 * @author nick.chow
 * @date: Sep 6, 2013
 */
public class LeaveFormDTO extends PageDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String leaveId;
	private String processDefinitionId;
	private String processInstanceId;

	/**
	 * 请假类型
	 */
	private String type;

	/**
	 * 请假理由
	 */
	private String reason;
	private String applicant;
	private Date startTime;
	private Date endTime;
	private Date createTime;

	/**
	 * 流程开始和结束时间，与业务时间不同，只有历史流程才会有这2个字段
	 */
	private Date processStartTime;
	private Date processEndTime;
	
	/**
	 * Task 相关属性
	 */
	private String taskId;
	private String owner;
	private String assignee;
	private String parentTaskId;
	private String name;
	private String description;
	private int priority;
	private Date taskCreateTime; // The time when the task has been created
	private Date dueDate;
	private boolean suspend;
	
	private int leaveDays;

	public int getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}

	public boolean isSuspend() {
		return suspend;
	}

	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getProcessStartTime() {
		return processStartTime;
	}

	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}

	public Date getProcessEndTime() {
		return processEndTime;
	}

	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
