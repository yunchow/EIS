/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 6, 2013
 */
package com.eis.oa.interfaces.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Please comment here
 * 
 * @author nick.chow
 * @date: Sep 6, 2013
 */
public class LeaveFormDTO implements Serializable {
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
	private Date startTime;
	private Date endTime;

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
