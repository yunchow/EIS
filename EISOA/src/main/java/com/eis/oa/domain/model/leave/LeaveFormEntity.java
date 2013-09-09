/**
 * fileName: EISOA/com.eis.oa.domain.model.leave/LeaveForm.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.oa.domain.model.leave;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.eis.core.model.Entity;
import com.eis.core.model.support.EntitySupport;
import com.eis.oa.infrastructure.dto.LeaveFormDTO;
/**
 * Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public class LeaveFormEntity extends EntitySupport<LeaveFormEntity> implements Entity<LeaveFormEntity> {
	private static final long serialVersionUID = 1L;

	/**
	 * 请假类型
	 */
	private String type;
	
	/**
	 * 请假理由
	 */
	private String reason;
	
	private String applicant;
	
	private String processInstanceKey;
	
	private Date startTime;
	private Date endTime;
	private Date createTime;
	
	public LeaveFormEntity(String id, String processInstanceKey, LeaveFormDTO leaveDto) {
		this.createTime = new Date();
		this.id = id;
		this.processInstanceKey = processInstanceKey;
		BeanUtils.copyProperties(leaveDto, this);
	}
	
	public LeaveFormDTO copy() {
		LeaveFormDTO dto = new LeaveFormDTO();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}

	public String getType() {
		return type;
	}

	public String getReason() {
		return reason;
	}

	public String getApplicant() {
		return applicant;
	}

	public String getProcessInstanceKey() {
		return processInstanceKey;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

}
