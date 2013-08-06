/**
 * Copyright 2007-2008 非也
 * All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation。
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses. *
 */
package org.fireflow.engine;

import java.util.Date;
import java.util.Map;

import org.fireflow.kernel.KernelException;
import org.fireflow.model.WorkflowProcess;

/**
 * 流程实例接口<br>
 * 对流程实例的状态字段作如下规定：小于5的状态为“活动”状态，大于等于5的状态为“非活动”状态。<br>
 * 活动状态包括：INITIALIZED,RUNNING,SUSPENDED<br>
 * 非活动状态包括：COMPLETED,CANCELED
 * @author 非也,nychen2000@163.com
 *
 */
public interface IProcessInstance {

    /**
     * 初始化状态
     */
    public static final int INITIALIZED = 0;

    /**
     * 运行状态
     */
    public static final int RUNNING = 1;

    /**
     * 被挂起
     */
//    public static final int SUSPENDED = 3;

    /**
     * 已经结束
     */
    public static final int COMPLETED = 7;

    /**
     * 被撤销
     */
    public static final int CANCELED = 9;

    public void run() throws EngineException, KernelException;

    /**
     * return the process instance's Id.
     * @return
     */
    public String getId();

    /**
     * return the process instance's name,which equals to the workflow process's name
     * @return
     */
    public String getName();

    /**
     * return the process instance's display-name ，
     * which equals to the workflow process's  display-name.
     * @return
     */
    public String getDisplayName();

    /**
     * return the workflow process's id
     * @return
     */
    public String getProcessId();

    public Integer getState();

    /**
     * return the workflow process's version.
     * 
     * @return
     */
    public Integer getVersion();

    /**
     * 流程实例创建者ID
     * @return
     */
    public String getCreatorId();


    /**
     * 返回流程实例的创建时间
     * @return 流程实例的创建时间
     */
    public Date getCreatedTime();

    /**
     * 返回流程实例的启动时间，即执行IProcessInstance.run()的时间
     * @return
     */
    public Date getStartedTime();

    /**
     * 返回流程实例的结束时间
     * @return
     */
    public Date getEndTime();

    /**
     * 返回流程实例的到期时间
     * @return
     */
    public Date getExpiredTime();

    /**
     * Get the process instance variable,return null if the variable is not existing .
     * @param name the name of the variable
     * @return the value of the variable. It may be Integer,String,Boolean,java.util.Date or Float
     */
    public Object getProcessInstanceVariable(String name);

    /**
     * Save the process instance variable.If there is a variable with the same name ,it will be updated.
     * @param name
     * @param var The value of the variable. It may be Integer,String,Boolean,java.util.Date or Float
     */
    public void setProcessInstanceVariable(String name, Object var);

    /**
     * Get all the process instance variables. the key of the returned map is the variable's name
     * @return 
     */
    public Map getProcessInstanceVariables();

    /**
     * update the process instance variables batched.
     * @param vars
     */
    public void setProcessInstanceVariables(Map vars);

    /**
     * return the corresponding workflow process.
     * @return
     */
    public WorkflowProcess getWorkflowProcess() throws EngineException;

    /**
     * get the parent process instance's id , null if no parent process instance.
     * @return
     */
    public String getParentProcessInstanceId();

    /**
     * get the parent taskinstance's id ,null if no parent taskinstance.
     * @return
     */
    public String getParentTaskInstanceId();

    /**
     * 强行中止流程实例，不管是否达到终态。
     * @throws RuntimeException
     */
    public void abort() throws EngineException;

    /**
     * 挂起
     * @throws org.fireflow.engine.EngineException
     */
    public void suspend() throws EngineException;

    public Boolean isSuspended();

    /**
     * 从挂起状态恢复到挂起前的状态
     * @throws org.fireflow.engine.EngineException
     */
    public void restore() throws EngineException;
}
