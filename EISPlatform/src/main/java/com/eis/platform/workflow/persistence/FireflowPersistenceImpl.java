/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月4日 下午4:08:48
 */
package com.eis.platform.workflow.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.ibatis.session.SqlSessionFactory;
import org.fireflow.engine.IProcessInstance;
import org.fireflow.engine.ITaskInstance;
import org.fireflow.engine.IWorkItem;
import org.fireflow.engine.definition.WorkflowDefinition;
import org.fireflow.engine.impl.ProcessInstance;
import org.fireflow.engine.impl.TaskInstance;
import org.fireflow.engine.impl.WorkItem;
import org.fireflow.engine.persistence.IPersistenceService;
import org.fireflow.kernel.IJoinPoint;
import org.fireflow.kernel.IToken;
import org.fireflow.kernel.impl.JoinPoint;
import org.fireflow.kernel.impl.Token;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
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
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * Save processInstance
	 * 
	 * @param processInstance
	 */
	// FIXME if need to save process instance variables
	public void saveOrUpdateProcessInstance(IProcessInstance processInstance) {
		Assert.notNull(processInstance);
		if (StringUtils.hasLength(processInstance.getId())) {
			getSqlSession().update("FireflowPersistence.updateProcessInstance", processInstance);
		} else {
			getSqlSession().insert("FireflowPersistence.insertProcessInstance", processInstance);
		}
		
	}

	/**
	 * Save joinpoint
	 * 
	 * @param joinPoint
	 */
	public void saveOrUpdateJoinPoint(IJoinPoint joinPoint) {
		Assert.notNull(joinPoint);
		JoinPoint joinPointLocal = (JoinPoint) joinPoint;
		if (StringUtils.hasLength(joinPointLocal.getId())) {
			getSqlSession().update("FireflowPersistence.updateJoinPoint", joinPointLocal);
		} else {
			getSqlSession().insert("FireflowPersistence.insertJoinPoint", joinPointLocal);
		}
	}

	public void saveOrUpdateTaskInstance(ITaskInstance taskInstance) {
		Assert.notNull(taskInstance);
		if (StringUtils.hasLength(taskInstance.getId())) {
			getSqlSession().update("FireflowPersistence.updateTaskInstance", taskInstance);
		} else {
			getSqlSession().insert("FireflowPersistence.insertTaskInstance", taskInstance);
		}
	}

	public void saveOrUpdateWorkItem(IWorkItem workitem) {
		Assert.notNull(workitem);
		if (StringUtils.hasLength(workitem.getId())) {
			getSqlSession().update("FireflowPersistence.updateWorkItem", workitem);
		} else {
			getSqlSession().insert("FireflowPersistence.insertWorkItem", workitem);
		}
	}

	public void saveOrUpdateToken(IToken token) {
		Assert.notNull(token);
		if (StringUtils.hasLength(token.getId())) {
			getSqlSession().update("FireflowPersistence.updateToken", token);
		} else {
			getSqlSession().insert("FireflowPersistence.insertToken", token);
		}
	}

	public List<IJoinPoint> findJoinPointsForProcessInstance(final String processInstanceId, final String synchronizerId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("synchronizerId", synchronizerId);
		return getSqlSession().selectList("FireflowPersistence.findJoinPointsForProcessInstance", paramMap);
	}

	public List<ITaskInstance> findTaskInstancesForProcessInstance(final String processInstanceId, final String activityId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("activityId", activityId);
		return getSqlSession().selectList("FireflowPersistence.findTaskInstancesForProcessInstance", paramMap);
	}

	public List<IToken> findTokensForProcessInstance(final String processInstanceId, final String nodeId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("processInstanceId", processInstanceId);
		paramMap.put("nodeId", nodeId);
		return getSqlSession().selectList("FireflowPersistence.findTokensForProcessInstance", paramMap);
	}

	public IWorkItem findWorkItemById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findWorkItemById", id);
	}

	public ITaskInstance findTaskInstanceById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findTaskInstanceById", id);
	}

	public List<IWorkItem> findWorkItemsForTaskInstance(
			final String taskInstanceId) {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0.createCriteria(WorkItem.class);
						criteria.add(Expression.eq("taskInstance.id",
								taskInstanceId));
						List<IWorkItem> _result = criteria.list();

						return _result;
					}
				});
		return result;

	}

	public List<IWorkItem> findWorkItemForTask(final String taskid) {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0.createCriteria(WorkItem.class);
						criteria.add(Expression.eq("taskInstance.taskId",
								taskid));
						List<IWorkItem> _result = criteria.list();

						return _result;
					}
				});
		return result;
	}

	public List<IProcessInstance> findProcessInstanceByProcessId(
			final String processId) {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0
								.createCriteria(ProcessInstance.class);

						criteria.add(Expression.eq("processId", processId));
						List<IProcessInstance> _result = criteria.list();

						return _result;
					}
				});
		return result;
	}

	public IProcessInstance findProcessInstanceById(String id) {
		return (IProcessInstance) this.getHibernateTemplate().get(
				ProcessInstance.class, id);
	}

	public IJoinPoint findJoinPointById(String id) {
		return getSqlSession().selectOne("FireflowPersistence.findJoinPointById", id);
	}

	public void saveOrUpdateWorkflowDefinition(WorkflowDefinition workflowDef) {
		if (workflowDef.getId() == null || workflowDef.getId().equals("")) {
			Integer latestVersion = findTheLatestVersionOfWorkflowDefinition(workflowDef
					.getProcessId());
			if (latestVersion != null) {
				workflowDef
						.setVersion(new Integer(latestVersion.intValue() + 1));
			} else {
				workflowDef.setVersion(new Integer(1));
			}
		}
		this.getHibernateTemplate().saveOrUpdate(workflowDef);
	}

	public Integer findTheLatestVersionOfWorkflowDefinition(
			final String processId) {
		// 鍙栧緱褰撳墠鏈�澶х殑version鍊�
		Integer result = (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Query q = arg0
								.createQuery("select max(m.version) from WorkflowDefinition m");
						Object obj = q.uniqueResult();
						if (obj != null) {
							Integer latestVersion = (Integer) obj;
							return latestVersion;
						} else {
							return null;
						}
					}
				});
		return result;
	}

	public WorkflowDefinition findWorkflowDefinitionById(String id) {
		return (WorkflowDefinition) this.getHibernateTemplate().get(
				WorkflowDefinition.class, id);
	}

	public WorkflowDefinition findWorkflowDefinitionByProcessIdAndVersion(
			final String processId, final int version) {
		WorkflowDefinition workflowDef = (WorkflowDefinition) this
				.getHibernateTemplate().execute(new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria c = arg0
								.createCriteria(WorkflowDefinition.class);
						c.add(Expression.eq("processId", processId));
						c.add(Expression.eq("version", version));
						return (WorkflowDefinition) c.uniqueResult();
					}
				});
		return workflowDef;
	}

	public WorkflowDefinition findLatestVersionOfWorkflowDefinitionByProcessId(
			String processId) {
		Integer latestVersion = this
				.findTheLatestVersionOfWorkflowDefinition(processId);
		return this.findWorkflowDefinitionByProcessIdAndVersion(processId,
				latestVersion);
	}

	public List<WorkflowDefinition> findWorkflowDefinitionByProcessId(
			final String processId) {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria c = arg0
								.createCriteria(WorkflowDefinition.class);
						c.add(Expression.eq("processId", processId));
						return c.list();
					}
				});

		return result;
	}

	public List<WorkflowDefinition> findAllLatestVersionOfWorkflowDefinition() {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						String hql = "select distinct model.processId from WorkflowDefinition model ";
						Query query = arg0.createQuery(hql);
						List processIdList = query.list();
						List _result = new Vector<WorkflowDefinition>();
						for (int i = 0; i < processIdList.size(); i++) {
							WorkflowDefinition wfDef = findLatestVersionOfWorkflowDefinitionByProcessId((String) processIdList
									.get(i));
							_result.add(wfDef);
						}
						return _result;
					}
				});
		return result;
	}

	public List<IWorkItem> findTodoWorkItems(final String actorId) {
		return findTodoWorkItems(actorId, null);
	}

	public List<IWorkItem> findTodoWorkItems(final String actorId,
			final String processInstanceId) {

		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0.createCriteria(WorkItem.class);

						Criterion cri1 = Expression.eq("state", new Integer(0));
						Criterion cri2 = Expression.eq("state", new Integer(1));
						Criterion cri_or = Expression.or(cri1, cri2);

						if (actorId != null && !actorId.trim().equals("")) {
							Criterion cri0 = Expression.eq("actorId", actorId);
							Criterion cri_and = Expression.and(cri0, cri_or);
							criteria.add(cri_and);
						} else {
							criteria.add(cri_or);
						}

						criteria.createAlias("taskInstance", "taskInstance");
						if (processInstanceId != null
								&& !processInstanceId.trim().equals("")) {
							criteria.add(Expression.eq(
									"taskInstance.processInstanceId",
									processInstanceId));
						}

						return criteria.list();
					}
				});
		return result;
	}

	public List<IWorkItem> findTodoWorkItems(final String actorId,
			final String processId, final String taskId) {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0.createCriteria(WorkItem.class);

						Criterion cri1 = Expression.eq("state", new Integer(0));
						Criterion cri2 = Expression.eq("state", new Integer(1));
						Criterion cri_or = Expression.or(cri1, cri2);

						if (actorId != null && !actorId.trim().equals("")) {
							Criterion cri0 = Expression.eq("actorId", actorId);
							Criterion cri_and = Expression.and(cri0, cri_or);
							criteria.add(cri_and);
						} else {
							criteria.add(cri_or);
						}

						criteria.createAlias("taskInstance", "taskInstance");
						if (processId != null && !processId.trim().equals("")) {
							criteria.add(Expression.eq(
									"taskInstance.processId", processId));
						}

						if (taskId != null && !taskId.trim().equals("")) {
							criteria.add(Expression.eq("taskInstance.taskId",
									taskId));
						}
						return criteria.list();

					}
				});
		return result;
	}

	public List<IWorkItem> findHaveDoneWorkItems(final String actorId) {
		return findHaveDoneWorkItems(actorId, null);
	}

	public List<IWorkItem> findHaveDoneWorkItems(final String actorId,
			final String processInstanceId) {

		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0.createCriteria(WorkItem.class);

						Criterion cri1 = Expression.eq("state", new Integer(2));
						Criterion cri2 = Expression
								.eq("state", new Integer(-1));
						Criterion cri_or = Expression.or(cri1, cri2);

						if (actorId != null && !actorId.trim().equals("")) {
							Criterion cri0 = Expression.eq("actorId", actorId);
							Criterion cri_and = Expression.and(cri0, cri_or);
							criteria.add(cri_and);
						} else {
							criteria.add(cri_or);
						}

						criteria.createAlias("taskInstance", "taskInstance");
						if (processInstanceId != null
								&& !processInstanceId.trim().equals("")) {
							criteria.add(Expression.eq(
									"taskInstance.processInstanceId",
									processInstanceId));
						}

						return criteria.list();
					}
				});
		return result;
	}

	public List<IWorkItem> findHaveDoneWorkItems(final String actorId,
			final String processId, final String taskId) {
		List result = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Criteria criteria = arg0.createCriteria(WorkItem.class);

						Criterion cri1 = Expression.eq("state", new Integer(2));
						Criterion cri2 = Expression
								.eq("state", new Integer(-1));
						Criterion cri_or = Expression.or(cri1, cri2);

						if (actorId != null && !actorId.trim().equals("")) {
							Criterion cri0 = Expression.eq("actorId", actorId);
							Criterion cri_and = Expression.and(cri0, cri_or);
							criteria.add(cri_and);
						} else {
							criteria.add(cri_or);
						}

						criteria.createAlias("taskInstance", "taskInstance");
						if (processId != null && !processId.trim().equals("")) {
							criteria.add(Expression.eq(
									"taskInstance.processId", processId));
						}

						if (taskId != null && !taskId.trim().equals("")) {
							criteria.add(Expression.eq("taskInstance.taskId",
									taskId));
						}
						return criteria.list();

					}
				});
		return result;
	}

	public void deleteWorkItemsInInitializedState(final String taskInstanceId) {
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				String hql = "delete from org.fireflow.engine.impl.WorkItem as model where model.taskInstance.id=? and model.state=0";
				Query query = arg0.createQuery(hql);
				query.setString(0, taskInstanceId);
				return query.executeUpdate();
			}
		});
	}
}
