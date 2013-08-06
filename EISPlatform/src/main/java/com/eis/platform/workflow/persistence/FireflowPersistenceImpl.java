/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月4日 下午4:08:48
 */
package com.eis.platform.workflow.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.fireflow.engine.IProcessInstance;
import org.fireflow.engine.ITaskInstance;
import org.fireflow.engine.IWorkItem;
import org.fireflow.engine.RuntimeContext;
import org.fireflow.engine.definition.WorkflowDefinition;
import org.fireflow.engine.impl.ProcessInstance;
import org.fireflow.engine.impl.ProcessInstanceTrace;
import org.fireflow.engine.impl.TaskInstance;
import org.fireflow.engine.impl.WorkItem;
import org.fireflow.engine.persistence.IPersistenceService;
import org.fireflow.kernel.IJoinPoint;
import org.fireflow.kernel.IToken;
import org.fireflow.kernel.impl.JoinPoint;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 4, 2013
 */
@Repository
public class FireflowPersistenceImpl extends SqlSessionDaoSupport implements IPersistenceService {

    protected RuntimeContext rtCtx = null;

    @Autowired
    public void setRuntimeContext(RuntimeContext ctx) {
        this.rtCtx = ctx;
    }

    public RuntimeContext getRuntimeContext() {
        return this.rtCtx;
    }
    
    @Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

    // FIXME if need to save process instance variables
    public void saveOrUpdateProcessInstance(IProcessInstance processInstance) {
		Assert.notNull(processInstance);
		if (StringUtils.hasLength(processInstance.getId())) {
			getSqlSession().update("FireflowPersistence.updateProcessInstance", processInstance);
		} else {
			((ProcessInstance)processInstance).setId(PKFactory.uuid());
			getSqlSession().insert("FireflowPersistence.insertProcessInstance", processInstance);
		}
		
	}

	@Deprecated
	public void saveOrUpdateJoinPoint(IJoinPoint joinPoint) {
		Assert.notNull(joinPoint);
		JoinPoint joinPointLocal = (JoinPoint) joinPoint;
		if (StringUtils.hasLength(joinPointLocal.getId())) {
			getSqlSession().update("FireflowPersistence.updateJoinPoint", joinPointLocal);
		} else {
			joinPointLocal.setId(PKFactory.uuid());
			getSqlSession().insert("FireflowPersistence.insertJoinPoint", joinPointLocal);
		}
	}

	public void saveOrUpdateTaskInstance(ITaskInstance taskInstance) {
		Assert.notNull(taskInstance);
		if (StringUtils.hasLength(taskInstance.getId())) {
			getSqlSession().update("FireflowPersistence.updateTaskInstance", taskInstance);
		} else {
			((TaskInstance)taskInstance).setId(PKFactory.uuid());
			getSqlSession().insert("FireflowPersistence.insertTaskInstance", taskInstance);
		}
	}
	
	public void saveOrUpdateWorkItem(IWorkItem workitem) {
		Assert.notNull(workitem);
		if (StringUtils.hasLength(workitem.getId())) {
			getSqlSession().update("FireflowPersistence.updateWorkItem", workitem);
		} else {
			((WorkItem)workitem).setId(PKFactory.uuid());
			getSqlSession().insert("FireflowPersistence.insertWorkItem", workitem);
		}
	}

	public void saveOrUpdateToken(IToken token) {
		Assert.notNull(token);
		if (StringUtils.hasLength(token.getId())) {
			getSqlSession().update("FireflowPersistence.updateToken", token);
		} else {
			token.setId(PKFactory.uuid());
			getSqlSession().insert("FireflowPersistence.insertToken", token);
		}
	}

	@Deprecated
	public List<IJoinPoint> findJoinPointsForProcessInstance(final String processInstanceId, final String synchronizerId) {
		Map<String, String> paramMap = new HashMap<String, String>(2);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("synchronizerId", synchronizerId);
		return getSqlSession().selectList("FireflowPersistence.findJoinPointsForProcessInstance", paramMap);
	}

    public Integer getAliveTokenCountForNode(final String processInstanceId, final String nodeId) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("nodeId", nodeId);
		paramMap.put("alive", true);
    	return getSqlSession().selectOne("FireflowPersistence.getAliveTokenCountForNode", paramMap);
    }

    public Integer getCompletedTaskInstanceCountForTask(final String processInstanceId, final String taskId) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("taskId", taskId);
		paramMap.put("state", ITaskInstance.COMPLETED);
    	return getSqlSession().selectOne("FireflowPersistence.getCompletedTaskInstanceCountForTask", paramMap);
    }

    public Integer getAliveTaskInstanceCountForActivity(final String processInstanceId, final String activityId) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("activityId", activityId);
    	return getSqlSession().selectOne("FireflowPersistence.getAliveTaskInstanceCountForActivity", paramMap);
    }

    public List<ITaskInstance> findTaskInstancesForProcessInstance(final String processInstanceId, final String activityId) {
		Map<String, String> paramMap = new HashMap<String, String>(2);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("activityId", activityId);
		return getSqlSession().selectList("FireflowPersistence.findTaskInstancesForProcessInstance", paramMap);
	}

    public List<ITaskInstance> findTaskInstancesForProcessInstanceByStepNumber(final String processInstanceId, final Integer stepNumber) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("stepNumber", stepNumber);
		return getSqlSession().selectList("FireflowPersistence.findTaskInstancesForProcessInstanceByStepNumber", paramMap);
    }
    
    // FIXME 是否会有性能问题，待优化
    public void lockTaskInstance(String taskInstanceId){
    	getSqlSession().selectOne("FireflowPersistence.lockTaskInstance", taskInstanceId);
    }
   
    public IToken findTokenById(String id) {
    	return getSqlSession().selectOne("FireflowPersistence.findTokenById", id);
    }

    public void deleteTokensForNodes(final String processInstanceId, @SuppressWarnings("rawtypes") final List nodeIds) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("nodeIds", nodeIds);
		getSqlSession().delete("FireflowPersistence.deleteTokensForNodes", paramMap);
    }

    public void deleteTokensForNode(final String processInstanceId, final String nodeId) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("nodeId", nodeId);
		getSqlSession().delete("FireflowPersistence.deleteTokensForNode", paramMap);
    }

    public void deleteToken(IToken token) {
    	Assert.notNull(token);
        getSqlSession().delete("FireflowPersistence.deleteToken", token.getId());
    }

	public List<IToken> findTokensForProcessInstance(final String processInstanceId, final String nodeId) {
		Map<String, String> paramMap = new HashMap<String, String>(2);
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("nodeId", nodeId);
		return getSqlSession().selectList("FireflowPersistence.findTokensForProcessInstance", paramMap);
	}

	public IWorkItem findWorkItemById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findWorkItemById", id);
	}

    public ITaskInstance findAliveTaskInstanceById(final String id) {
        return getSqlSession().selectOne("FireflowPersistence.findAliveTaskInstanceById", id);
    }

    public ITaskInstance findTaskInstanceById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findTaskInstanceById", id);
	}

    public void abortTaskInstance(final TaskInstance taskInstance) {
    	Assert.notNull(taskInstance);
    	Date now = rtCtx.getCalendarService().getSysDate();
    	taskInstance.setState(ITaskInstance.CANCELED);
        taskInstance.setEndTime(now);
        taskInstance.setCanBeWithdrawn(Boolean.FALSE);
        this.saveOrUpdateTaskInstance(taskInstance);

        Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("state", IWorkItem.CANCELED);
		paramMap.put("endTime", now);
		paramMap.put("taskInstanceId", taskInstance.getId());
        getSqlSession().update("FireflowPersistence.updateWorkItemForAbortTaskInstance", paramMap);
    }

    public Integer getAliveWorkItemCountForTaskInstance(final String taskInstanceId) {
        return getSqlSession().selectOne("FireflowPersistence.getAliveWorkItemCountForTaskInstance", taskInstanceId);
    }

    public List<IWorkItem> findCompletedWorkItemsForTaskInstance(final String taskInstanceId) {
        return getSqlSession().selectList("FireflowPersistence.findCompletedWorkItemsForTaskInstance", taskInstanceId);
    }
    
    public List<IWorkItem> findWorkItemsForTaskInstance(final String taskInstanceId) {
		return getSqlSession().selectList("FireflowPersistence.findWorkItemsForTaskInstance", taskInstanceId);
	}

    public List<IWorkItem> findWorkItemForTask(final String taskid) {
		return getSqlSession().selectList("FireflowPersistence.findWorkItemForTask", taskid);
	}

    public List<IProcessInstance> findProcessInstanceByProcessId(final String processId) {
		return getSqlSession().selectList("FireflowPersistence.findProcessInstanceByProcessId", processId);
	}

    public List<IProcessInstance> findProcessInstancesByProcessIdAndVersion(final String processId, final Integer version) {
    	Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("processId", processId);
		paramMap.put("version", version);
        return getSqlSession().selectList("FireflowPersistence.findProcessInstancesByProcessIdAndVersion", paramMap);
    }

    public IProcessInstance findProcessInstanceById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findProcessInstanceById", id);
	}

    public IProcessInstance findAliveProcessInstanceById(final String id) {
        return getSqlSession().selectOne("FireflowPersistence.findAliveProcessInstanceById", id);
    }

	public void saveOrUpdateWorkflowDefinition(WorkflowDefinition workflowDef) {
		Assert.notNull(workflowDef);
		if (!StringUtils.hasLength(workflowDef.getId())) {
			Integer latestVersion = findTheLatestVersionNumberIgnoreState(workflowDef.getProcessId());
			if (latestVersion != null) {
				workflowDef.setVersion(new Integer(latestVersion.intValue() + 1));
			} else {
				workflowDef.setVersion(new Integer(1));
			}
			workflowDef.setId(PKFactory.uuid());
			getSqlSession().insert("FireflowPersistence.insertWorkFlowDef", workflowDef);
		} else {
			getSqlSession().update("FireflowPersistence.updateWorkFlowDef", workflowDef);
		}
	}

    public Integer findTheLatestVersionNumberIgnoreState(final String processId){
        return getSqlSession().selectOne("FireflowPersistence.findTheLatestVersionNumberIgnoreState", processId);  	
    }

    public WorkflowDefinition findWorkflowDefinitionById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findWorkflowDefinitionById", id);
	}

    public WorkflowDefinition findWorkflowDefinitionByProcessIdAndVersion(final String processId, final int version) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("processId", processId);
		param.put("version", version);
		return getSqlSession().selectOne("FireflowPersistence.findWorkflowDefinitionByProcessIdAndVersion", param);
	}

    public WorkflowDefinition findTheLatestVersionOfWorkflowDefinitionByProcessId(String processId) {
        Integer latestVersion = this.findTheLatestVersionNumber(processId);
        return this.findWorkflowDefinitionByProcessIdAndVersionNumber(processId, latestVersion);
    }

    public List<WorkflowDefinition> findWorkflowDefinitionByProcessId(final String processId) {
		return getSqlSession().selectList("FireflowPersistence.findWorkflowDefinitionByProcessId", processId);
	}

    public List<IWorkItem> findTodoWorkItems(final String actorId) {
        return findTodoWorkItems(actorId, null);
    }

    public List<IWorkItem> findTodoWorkItems(final String actorId, final String processInstanceId) {
		Map<String, String> param = new HashMap<String, String>(2);
		param.put("actorId", actorId);
		param.put("processInstanceId", processInstanceId);
		return getSqlSession().selectList("FireflowPersistence.findTodoWorkItems", param);
	}

    public List<IWorkItem> findTodoWorkItems(final String actorId, final String processId, final String taskId) {
		Map<String, String> param = new HashMap<String, String>(3);
		param.put("actorId", actorId);
		param.put("processId", processId);
		param.put("taskId", taskId);
		return getSqlSession().selectList("FireflowPersistence.findTodoWorkItemsFor", param);
	}

    public List<IWorkItem> findHaveDoneWorkItems(final String actorId) {
        return findHaveDoneWorkItems(actorId, null);
    }

    public List<IWorkItem> findHaveDoneWorkItems(final String actorId, final String processInstanceId) {
		Map<String, String> param = new HashMap<String, String>(2);
		param.put("actorId", actorId);
		param.put("processInstanceId", processInstanceId);
		return getSqlSession().selectList("FireflowPersistence.findHaveDoneWorkItems", param);
	}

    public List<IWorkItem> findHaveDoneWorkItems(final String actorId, final String processId, final String taskId) {
		Map<String, String> param = new HashMap<String, String>(3);
		param.put("actorId", actorId);
		param.put("processId", processId);
		param.put("taskId", taskId);
		return getSqlSession().selectList("FireflowPersistence.findHaveDoneWorkItemsFor", param);
	}

    public void deleteWorkItemsInInitializedState(final String taskInstanceId) {
		getSqlSession().delete("FireflowPersistence.deleteWorkItemsInInitializedState", taskInstanceId);
	}

    public Integer getAliveProcessInstanceCountForParentTaskInstance(final String taskInstanceId) {
        return getSqlSession().selectOne("FireflowPersistence.getAliveProcessInstanceCountForParentTaskInstance", taskInstanceId);
    }

    public void suspendProcessInstance(final ProcessInstance processInstance) {
        processInstance.setSuspended(Boolean.TRUE);
        this.saveOrUpdateProcessInstance(processInstance);
        getSqlSession().update("FireflowPersistence.suspendProcessInstance", processInstance.getId());
    }

    public void restoreProcessInstance(final ProcessInstance processInstance) {
        processInstance.setSuspended(Boolean.FALSE);
        this.saveOrUpdateProcessInstance(processInstance);
        getSqlSession().update("FireflowPersistence.restoreProcessInstance", processInstance.getId());
    }

    public void abortProcessInstance(final ProcessInstance processInstance) {
        Assert.notNull(processInstance);
        Date now = rtCtx.getCalendarService().getSysDate();
        processInstance.setState(IProcessInstance.CANCELED);
        processInstance.setEndTime(now);
        this.saveOrUpdateProcessInstance(processInstance);
        
        Map<String, Object> param = new HashMap<String, Object>();
		param.put("state", IWorkItem.CANCELED);
		param.put("endTime", now);
		param.put("processInstanceId", processInstance.getId());
		
		// FIXME 这里的更新顺序与FireFlow默认实现不一样，应该是先更新workItem，再更新taskInstance
		// 否则对workItem的更新将不会生效
		getSqlSession().update("FireflowPersistence.abortProcessInstanceForWorkItem", param);
		getSqlSession().update("FireflowPersistence.abortProcessInstanceForTask", param);
		getSqlSession().delete("FireflowPersistence.deleteTokenByProcessInstanceId", processInstance.getId());
    }

    public void saveOrUpdateProcessInstanceTrace(ProcessInstanceTrace processInstanceTrace) {
        Assert.notNull(processInstanceTrace);
        if (StringUtils.hasLength(processInstanceTrace.getId())) {
        	getSqlSession().update("FireflowPersistence.updateProcessInstanceTrace", processInstanceTrace);
        } else {
        	processInstanceTrace.setId(PKFactory.uuid());
        	getSqlSession().insert("FireflowPersistence.insertProcessInstanceTrace", processInstanceTrace);
        }
    }
    
    @SuppressWarnings("rawtypes")
	public List findProcessInstanceTraces(final String processInstanceId){
		return getSqlSession().selectList("FireflowPersistence.findProcessInstanceTraces", processInstanceId);
    }

    public List<IProcessInstance> findProcessInstancesByProcessId(final String processId) {
        return getSqlSession().selectList("FireflowPersistence.findProcessInstancesByProcessId", processId);
    }

    public List<IWorkItem> findWorkItemsForTask(final String taskid) {
        return getSqlSession().selectList("FireflowPersistence.findWorkItemsForTask", taskid);
    }

    public WorkflowDefinition findWorkflowDefinitionByProcessIdAndVersionNumber(final String processId, final int version) {
        Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("version", version);
		param.put("processId", processId);
		return getSqlSession().selectOne("FireflowPersistence.findWorkflowDefinitionByProcessIdAndVersionNumber", param);
    }
    
    public List<WorkflowDefinition> findWorkflowDefinitionsByProcessId(final String processId) {
        return getSqlSession().selectList("FireflowPersistence.findWorkflowDefinitionsByProcessId", processId);
    }

    public List<WorkflowDefinition> findAllTheLatestVersionsOfWorkflowDefinition() {
		List<String> processIdList = getSqlSession().selectList("FireflowPersistence.findAllProcessIdOfWorkflowDef");
		Assert.notNull(processIdList, "processIdList must not be null");
		List<WorkflowDefinition> workflowDefinitionList = new ArrayList<WorkflowDefinition>(processIdList.size());
		for (String processId : processIdList) {
			WorkflowDefinition wfDef = findTheLatestVersionOfWorkflowDefinitionByProcessId(processId);
			workflowDefinitionList.add(wfDef);
		}
		return workflowDefinitionList;
	}

    public Integer findTheLatestVersionNumber(final String processId) {
        return getSqlSession().selectOne("FireflowPersistence.findTheLatestVersionNumber");
    }

}
