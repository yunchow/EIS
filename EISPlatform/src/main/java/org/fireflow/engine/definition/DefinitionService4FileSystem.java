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
package org.fireflow.engine.definition;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fireflow.engine.EngineException;
import org.fireflow.engine.RuntimeContext;
import org.fireflow.model.io.FPDLParserException;
import org.fireflow.model.WorkflowProcess;
import org.fireflow.model.io.Dom4JFPDLParser;

/**
 * 从文件系统读取流程定义文件，该类忽略流程定义文件的版本，主要用于开发阶段<br>
 * When the workflow process is stored in the file system ,use this implementation.
 * 
 * @author 非也,nychen2000@163.com
 * 
 */
public class DefinitionService4FileSystem implements IDefinitionService {
    protected RuntimeContext rtCtx = null;

    protected HashMap<String, WorkflowDefinition> workflowDefinitionMap = new HashMap<String, WorkflowDefinition>();// 流程名到流程定义的id
    protected HashMap<String, String> latestVersionKeyMap = new HashMap<String, String>();
    
    
    
    
    /*
     * (non-Javadoc)
     * 
     * @see org.fireflow.engine.definition.IDefinitionService#getWorkflowProcess(java.lang.String)
     */

//    public WorkflowProcess getWorkflowProcessByName(String name) {
//        // TODO Auto-generated method stub
//        return workflowProcessMap.get(name);
//    }

    /*
     * (non-Javadoc)
     * 
     * @see org.fireflow.engine.definition.IDefinitionService#setDefinitionFiles(java.util.List)
     */
    public void setDefinitionFiles(List<String> workflowProcessFileNames)
            throws IOException, FPDLParserException,EngineException {
        if (workflowProcessFileNames != null) {
            Dom4JFPDLParser parser = new Dom4JFPDLParser();
//            JAXP_FPDL_Parser parser = new JAXP_FPDL_Parser();
            for (int i = 0; i < workflowProcessFileNames.size(); i++) {
                InputStream inStream = this.getClass().getResourceAsStream(
                        workflowProcessFileNames.get(i).trim());
                if (inStream == null) {
                    throw new IOException("没有找到名称为" + workflowProcessFileNames.get(i) + "的流程定义文件");
                }
                
                WorkflowProcess workflowProcess = parser.parse(inStream);

                WorkflowDefinition workflowDef = new WorkflowDefinition();
                workflowDef.setVersion(new Integer(1));

                workflowDef.setWorkflowProcess(workflowProcess);

                String latestVersionKey = workflowProcess.getId() + "_V_" + workflowDef.getVersion();
                workflowDefinitionMap.put(latestVersionKey, workflowDef);
                latestVersionKeyMap.put(workflowProcess.getId(), latestVersionKey);
//                workflowProcessMap.put(workflowProcess.getName(), workflowProcess);
//
//                List<Activity> activities = workflowProcess.getActivities();
//                for (int k = 0; activities != null && k < activities.size(); k++) {
//                    Activity activity = activities.get(k);
//                    activityMap.put(activity.getId(), activity);
//
//                    List<Task> tasks = activity.getTasks();
//                    for (int j = 0; tasks != null && j < tasks.size(); j++) {
//                        Task task = tasks.get(j);
//                        taskMap.put(task.getId(), task);
//                    }
//                }
//
//                List<Transition> transitions = workflowProcess.getTransitions();
//                for (int k = 0; transitions != null && k < transitions.size(); k++) {
//                    Transition trans = transitions.get(k);
//                    transitionMap.put(trans.getId(), trans);
//                }
//
//                List<DataField> datafields = workflowProcess.getDataFields();
//                for (int k = 0; datafields != null && k < datafields.size(); k++) {
//                    DataField df = datafields.get(k);
//                    dataFieldMap.put(df.getId(), df);
//                }

            }
        }

    }

    public List<WorkflowDefinition> getAllLatestVersionsOfWorkflowDefinition() {
        return new ArrayList(workflowDefinitionMap.values());
    }

    public WorkflowDefinition getWorkflowDefinitionByProcessIdAndVersionNumber(String processId, Integer version) {
        return this.workflowDefinitionMap.get(processId + "_V_" + version);
    }

    public WorkflowDefinition getTheLatestVersionOfWorkflowDefinition(String processId) {
        return this.workflowDefinitionMap.get(this.latestVersionKeyMap.get(processId));
    }

    public void setRuntimeContext(RuntimeContext ctx) {
        this.rtCtx = ctx;
    }
    public RuntimeContext getRuntimeContext(){
        return this.rtCtx;
    } 
//    public List<WorkflowProcess> getAllWorkflowProcesses() {
//        return new ArrayList(workflowProcessMap.values());
//    }
//
//    public WorkflowProcess getWorkflowProcessById(String id) {
//        return workflowProcessMap.get(id);
//    }

//    public Activity getActivityById(String id) {
//        return activityMap.get(id);
//    }
//
//    public Task getTaskById(String id) {
//        return taskMap.get(id);
//    }
//
//    public Transition getTransitionById(String id) {
//        return transitionMap.get(id);
//    }
//
//    public DataField getDataFieldById(String id) {
//        return dataFieldMap.get(id);
//    }
}
