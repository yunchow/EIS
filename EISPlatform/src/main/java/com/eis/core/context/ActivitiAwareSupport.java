/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.core.context;

import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public abstract class ActivitiAwareSupport {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected IdentityService identityService;

	@Autowired
	protected HistoryService historyService;
	
	@Autowired
	protected TaskService taskService;
	
	@Autowired
	protected RuntimeService runtimeService;
	
	@Autowired
	protected ManagementService managementService;
	
	@Autowired
	protected ProcessEngine processEngine;
	
	@Autowired
	protected ProcessEngineConfiguration processEngineConfiguration;
	
	public HistoricTaskInstanceQuery createHistoricTaskInstanceQueryByBusinessKey(String businessKey) {
		return createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey);
	}
	
	public Execution getExecutionBusinessKey(String businessKey) {
		return createExecutionQuery().processInstanceBusinessKey(businessKey).singleResult();
	}
	
	public Task getTaskByBusinessKey(String businessKey) {
		return createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
	}
	
	public HistoricProcessInstance getHistoricProcessInstanceByBusinessKey(String businessKey) {
		return createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
	}
	
	public HistoricTaskInstanceQuery createHistoricTaskInstanceQuery() {
		return historyService.createHistoricTaskInstanceQuery();
	}
	
	public HistoricTaskInstance getHistoricTaskInstanceByTaskId(String taskId) {
		return createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
	}
	
	public BpmnModel getBpmnModelByProcessDefinitionId(String processDefinitionId) {
		return repositoryService.getBpmnModel(processDefinitionId);
	}
	
	public List<HistoricActivityInstance> getHistoricActivitiesByProcessInstanceId(String processInstanceId) {
		return createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
	}
	
	public HistoricActivityInstanceQuery createHistoricActivityInstanceQuery() {
		return historyService.createHistoricActivityInstanceQuery();
	}
	
	public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
		return createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}
	
	public HistoricProcessInstanceQuery createHistoricProcessInstanceQuery() {
		return historyService.createHistoricProcessInstanceQuery();
	}
	
	public TaskQuery createTaskQuery() {
		return taskService.createTaskQuery();
	}
	
	public ProcessInstanceQuery createProcessInstanceQuery() {
		return runtimeService.createProcessInstanceQuery();
	}
	
	public ExecutionQuery createExecutionQuery() {
		return runtimeService.createExecutionQuery();
	}
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public ProcessEngineConfiguration getProcessEngineConfiguration() {
		return processEngineConfiguration;
	}

	public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
		this.processEngineConfiguration = processEngineConfiguration;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}

}
