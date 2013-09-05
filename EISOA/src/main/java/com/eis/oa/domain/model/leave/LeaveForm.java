/**
 * fileName: EISOA/com.eis.oa.domain.model.leave/LeaveForm.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.oa.domain.model.leave;

import java.util.Date;

import com.eis.platform.model.Entity;
import com.eis.platform.model.support.EntitySupport;

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
	
	private Date createTime;

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
