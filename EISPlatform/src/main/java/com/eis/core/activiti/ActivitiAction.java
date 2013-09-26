/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 24, 2013
 */
package com.eis.core.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 24, 2013
 */
public class ActivitiAction {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 将当前任务分配给指定人，如果指定人为空则分配给启动流程者
	 * @param task
	 */
	public void addCandidateUser(DelegateTask task, String user) {
		task.setAssignee(null);
		task.setOwner(null);
		task.addCandidateUser(user == null ? getStarter(task) : user);
	}
	
	/**
	 * 将当前任务分配给启动流程者
	 * @param task
	 */
	public void assignToStarter(DelegateTask task) {
		this.addCandidateUser(task, null);
	}
	
	/**
	 * 得到流程启动者
	 * @param task
	 * @return
	 */
	public String getStarter(DelegateTask task) {
		String user = (String) task.getVariable(ProcessConstants.STARTER);
		logger.info("task {} 's starter is {}", task.getId(), user);
		return user;
	}
	
	public void log(String message) {
		logger.info(message);
	}
	
	public void log(String message, Object... params) {
		logger.info(message, params);
	}
	
}
