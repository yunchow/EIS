/**
 * fileName: EISOA/com.eis.oa.domain.model.leave/LeaveForm.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.oa.domain.model.leave;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.eis.core.model.Entity;
import com.eis.core.model.support.EntitySupport;
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
	
	public LeaveFormEntity() {
		
	}
	
	public LeaveFormEntity(Map<String, ?> model) {
		try {
			BeanUtils.populate(this, model);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getProcessInstanceKey() {
		return processInstanceKey;
	}

	public void setProcessInstanceKey(String processInstanceKey) {
		this.processInstanceKey = processInstanceKey;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
