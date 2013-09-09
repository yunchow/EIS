/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 6, 2013
 */
package com.eis.oa.application.dto;

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

	/**
	 * 流程开始和结束时间，与业务时间不同，只有历史流程才会有这2个字段
	 */
	private Date processStartTime;
	private Date processEndTime;

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
