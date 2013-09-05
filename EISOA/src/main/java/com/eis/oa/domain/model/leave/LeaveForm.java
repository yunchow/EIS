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
public class LeaveForm extends EntitySupport<LeaveForm> implements Entity<LeaveForm> {
	private static final long serialVersionUID = 1L;

	/**
	 * 请假类型
	 */
	private String type;
	
	/**
	 * 请假理由
	 */
	private String reason;
	
	private String processInstanceId;
	
	private Date createTime;
	
	public LeaveForm() {
		
	}
	
	public LeaveForm(Map<String, ?> model) {
		try {
			BeanUtils.populate(this, model);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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
