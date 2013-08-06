/**
 * Copyright 2003-2008 非也
 * All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
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
package org.fireflow.model.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.fireflow.model.DataField;
import org.fireflow.model.Duration;
import org.fireflow.model.EventListener;
import org.fireflow.model.FormTask;
import org.fireflow.model.IWFElement;
import org.fireflow.model.SubflowTask;
import org.fireflow.model.Task;
import org.fireflow.model.TaskRef;
import org.fireflow.model.ToolTask;
import org.fireflow.model.WorkflowProcess;
import org.fireflow.model.net.Activity;
import org.fireflow.model.net.EndNode;
import org.fireflow.model.net.Loop;
import org.fireflow.model.net.Node;
import org.fireflow.model.net.StartNode;
import org.fireflow.model.net.Synchronizer;
import org.fireflow.model.net.Transition;
import org.fireflow.model.resource.Application;
import org.fireflow.model.resource.Form;
import org.fireflow.model.resource.Participant;
import org.fireflow.model.resource.SubWorkflowProcess;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author chennieyun
 */
public class JAXP_FPDL_Parser implements IFPDLParser {

    public WorkflowProcess parse(InputStream in) throws IOException, FPDLParserException {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setValidating(false);
            docBuilderFactory.setNamespaceAware(true);

            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new EntityResolver() {

                String emptyDtd = "";
                ByteArrayInputStream bytels = new ByteArrayInputStream(emptyDtd.getBytes());

                public InputSource resolveEntity(String publicId,
                        String systemId) throws SAXException, IOException {
                    return new InputSource(bytels);
                }
            });

            Document w3cDoc = docBuilder.parse(in);
            return parse(w3cDoc);
        } catch (SAXException ex) {
            Logger.getLogger(JAXP_FPDL_Parser.class.getName()).log(Level.SEVERE, null, ex);
            throw new FPDLParserException(ex.getMessage());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(JAXP_FPDL_Parser.class.getName()).log(Level.SEVERE, null, ex);
            throw new FPDLParserException(ex.getMessage());
        }
    }

    protected WorkflowProcess parse(Document document) throws FPDLParserException {

        Element workflowProcessElement = document.getDocumentElement();

        WorkflowProcess wp = new WorkflowProcess(workflowProcessElement.getAttribute(NAME));
        wp.setSn(UUID.randomUUID().toString());
        wp.setDescription(Util4JAXPParser.elementAsString(workflowProcessElement,
                DESCRIPTION));
        wp.setDisplayName(workflowProcessElement.getAttribute(DISPLAY_NAME));
        wp.setResourceFile(workflowProcessElement.getAttribute(RESOURCE_FILE));
        wp.setResourceManager(workflowProcessElement.getAttribute(RESOURCE_MANAGER));

        wp.setTaskInstanceCreator(workflowProcessElement.getAttribute(TASK_INSTANCE_CREATOR));

        wp.setFormTaskInstanceRunner(workflowProcessElement.getAttribute(FORM_TASK_INSTANCE_RUNNER));

        wp.setToolTaskInstanceRunner(workflowProcessElement.getAttribute(TOOL_TASK_INSTANCE_RUNNER));

        wp.setSubflowTaskInstanceRunner(workflowProcessElement.getAttribute(SUBFLOW_TASK_INSTANCE_RUNNER));

        wp.setFormTaskInstanceCompletionEvaluator(workflowProcessElement.getAttribute(FORM_TASK_INSTANCE_COMPLETION_EVALUATOR));

        wp.setToolTaskInstanceCompletionEvaluator(workflowProcessElement.getAttribute(TOOL_TASK_INSTANCE_COMPLETION_EVALUATOR));
        wp.setSubflowTaskInstanceCompletionEvaluator(workflowProcessElement.getAttribute(SUBFLOW_TASK_INSTANCE_COMPLETION_EVALUATOR));


        this.loadDataFields(wp, wp.getDataFields(), Util4JAXPParser.child(
                workflowProcessElement, DATA_FIELDS));

        this.loadTasks(wp, wp.getTasks(), Util4JAXPParser.child(
                workflowProcessElement, TASKS));

        loadStartNode(wp, Util4JAXPParser.child(workflowProcessElement, START_NODE));
        loadActivities(wp, wp.getActivities(), Util4JAXPParser.child(
                workflowProcessElement, ACTIVITIES));
        loadSynchronizers(wp, wp.getSynchronizers(), Util4JAXPParser.child(
                workflowProcessElement, SYNCHRONIZERS));
        loadEndNodes(wp, wp.getEndNodes(), Util4JAXPParser.child(
                workflowProcessElement, END_NODES));
        loadTransitions(wp, Util4JAXPParser.child(workflowProcessElement,
                TRANSITIONS));
        loadLoops(wp, Util4JAXPParser.child(workflowProcessElement,
                LOOPS));
        loadEventListeners(wp.getEventListeners(), Util4JAXPParser.child(workflowProcessElement, EVENT_LISTENERS));

        Map<String, String> extAttrs = wp.getExtendedAttributes();
        loadExtendedAttributes(extAttrs, Util4JAXPParser.child(
                workflowProcessElement, EXTENDED_ATTRIBUTES));

        return wp;

    }

    protected void loadEventListeners(List listeners, Element element) {
        listeners.clear();
        if (element == null) {
            return;
        }
        if (element == null) {
            return;
        }
        List listenerElms = Util4JAXPParser.children(element, EVENT_LISTENER);
        Iterator iter = listenerElms.iterator();
        while (iter.hasNext()) {
            Element elm = (Element) iter.next();
            EventListener listener = new EventListener();
            listener.setClassName(elm.getAttribute(CLASS_NAME));

            listeners.add(listener);
        }
    }

    protected void loadStartNode(WorkflowProcess wp, Element element)
            throws FPDLParserException {
        if (element == null) {
            return;
        }
        StartNode startNode = new StartNode(wp);
        startNode.setSn(UUID.randomUUID().toString());
        startNode.setDescription(Util4JAXPParser.elementAsString(element,
                DESCRIPTION));
        startNode.setDisplayName(element.getAttribute(DISPLAY_NAME));
        loadExtendedAttributes(startNode.getExtendedAttributes(), Util4JAXPParser.child(element, EXTENDED_ATTRIBUTES));
        wp.setStartNode(startNode);
    }

    protected void loadEndNodes(WorkflowProcess wp, List<EndNode> endNodes,
            Element element) throws FPDLParserException {
        endNodes.clear();
        if (element == null) {
            return;
        }
        List endNodesElms = Util4JAXPParser.children(element, END_NODE);
        Iterator iter = endNodesElms.iterator();
        while (iter.hasNext()) {
            Element elm = (Element) iter.next();
            EndNode endNode = new EndNode(wp, elm.getAttribute(NAME));
            endNode.setSn(UUID.randomUUID().toString());
            endNode.setDescription(Util4JAXPParser.elementAsString(element,
                    DESCRIPTION));
            endNode.setDisplayName(element.getAttribute(DISPLAY_NAME));
            loadExtendedAttributes(endNode.getExtendedAttributes(), Util4JAXPParser.child(elm, EXTENDED_ATTRIBUTES));
            endNodes.add(endNode);
        }
    }

    protected void loadSynchronizers(WorkflowProcess wp, List<Synchronizer> synchronizers,
            Element element) throws FPDLParserException {
        synchronizers.clear();
        if (element == null) {
            return;
        }
        List synchronizerElms = Util4JAXPParser.children(element, SYNCHRONIZER);
        Iterator iter = synchronizerElms.iterator();
        while (iter.hasNext()) {
            Element elm = (Element) iter.next();
            Synchronizer synchronizer = new Synchronizer(wp, elm.getAttribute(NAME));
            synchronizer.setSn(UUID.randomUUID().toString());
            synchronizer.setDescription(Util4JAXPParser.elementAsString(element,
                    DESCRIPTION));
            synchronizer.setDisplayName(element.getAttribute(DISPLAY_NAME));

            loadExtendedAttributes(synchronizer.getExtendedAttributes(),
                    Util4JAXPParser.child(elm, EXTENDED_ATTRIBUTES));

            synchronizers.add(synchronizer);
        }
    }

    protected void loadActivities(WorkflowProcess wp, List<Activity> activities,
            Element element) throws FPDLParserException {
        if (element == null) {
            // log.debug("Activites element was null");
            return;
        }

        List activitElements = Util4JAXPParser.children(element, ACTIVITY);
        activities.clear();
        Iterator iter = activitElements.iterator();
        while (iter.hasNext()) {
            Element activityElement = (Element) iter.next();

            Activity activity = new Activity(wp, activityElement.getAttribute(NAME));
            activity.setSn(UUID.randomUUID().toString());
            activity.setDisplayName(activityElement.getAttribute(DISPLAY_NAME));
            activity.setDescription(Util4JAXPParser.elementAsString(
                    activityElement, DESCRIPTION));
            activity.setCompletionStrategy(activityElement.getAttribute(COMPLETION_STRATEGY));
            loadEventListeners(activity.getEventListeners(), Util4JAXPParser.child(activityElement, EVENT_LISTENERS));
            loadExtendedAttributes(activity.getExtendedAttributes(),
                    Util4JAXPParser.child(activityElement, EXTENDED_ATTRIBUTES));

            loadTasks(activity, activity.getInlineTasks(), Util4JAXPParser.child(
                    activityElement, TASKS));
            loadTaskRefs((WorkflowProcess) activity.getParent(), activity, activity.getTaskRefs(), Util4JAXPParser.child(activityElement, TASKREFS));
            activities.add(activity);
        }
    }

    protected void loadTaskRefs(WorkflowProcess workflowProcess, IWFElement parent, List<TaskRef> taskRefs, Element taskRefsElement) {
        taskRefs.clear();
        if (taskRefsElement == null) {
            return;
        }
        List taskRefElems = Util4JAXPParser.children(taskRefsElement, TASKREF);
        Iterator iter = taskRefElems.iterator();
        while (iter.hasNext()) {
            Element elm = (Element) iter.next();
            String taskId = elm.getAttribute(REFERENCE);
            Task task = (Task) workflowProcess.findWFElementById(taskId);
            if (task != null) {
                TaskRef taskRef = new TaskRef(parent, task);
                taskRef.setSn(UUID.randomUUID().toString());
                taskRefs.add(taskRef);
            }
        }
    }

    protected void loadTasks(IWFElement parent, List<Task> tasks, Element element)
            throws FPDLParserException {
        tasks.clear();
        if (element == null) {
            return;
        }
        List tasksElms = Util4JAXPParser.children(element, TASK);
        Iterator iter = tasksElms.iterator();
        while (iter.hasNext()) {
            Element elm = (Element) iter.next();
            tasks.add(createTask(parent, elm));
        }
    }

    protected Task createTask(IWFElement parent, Element taskElement)
            throws FPDLParserException {
        Task task = null;
        String type = taskElement.getAttribute(TYPE);
        if (Task.FORM.equals(type)) {
            task = new FormTask(parent, taskElement.getAttribute(NAME));
            ((FormTask) task).setAssignmentStrategy(taskElement.getAttribute(COMPLETION_STRATEGY));
            ((FormTask) task).setDefaultView(taskElement.getAttribute(DEFAULT_VIEW));

            ((FormTask) task).setPerformer(createPerformer(Util4JAXPParser.child(taskElement,
                    PERFORMER)));
            ((FormTask) task).setEditForm(createForm(Util4JAXPParser.child(taskElement, EDIT_FORM)));
            ((FormTask) task).setViewForm(createForm(Util4JAXPParser.child(taskElement, VIEW_FORM)));
            ((FormTask) task).setListForm(createForm(Util4JAXPParser.child(taskElement, LIST_FORM)));

        } else if (Task.TOOL.equals(type)) {
            task = new ToolTask(parent, taskElement.getAttribute(NAME));

            ((ToolTask) task).setApplication(createApplication(Util4JAXPParser.child(taskElement,
                    APPLICATION)));
        } else if (Task.SUBFLOW.equals(type)) {
            task = new SubflowTask(parent, taskElement.getAttribute(NAME));
            ((SubflowTask) task).setSubWorkflowProcess(createSubWorkflowProcess(Util4JAXPParser.child(taskElement, SUB_WORKFLOW_PROCESS)));

        } else {
            return null;
        }

        task.setSn(UUID.randomUUID().toString());
        task.setDisplayName(taskElement.getAttribute(DISPLAY_NAME));
        task.setDescription(Util4JAXPParser.elementAsString(taskElement, DESCRIPTION));
        String sPriority = taskElement.getAttribute(PRIORITY);
        int priority = 0;
        if (sPriority != null) {
            try {
                priority = Integer.parseInt(sPriority);
            } catch (Exception e) {
            }
        }
        task.setPriority(priority);

        task.setDuration(createDuration(Util4JAXPParser.child(taskElement, DURATION)));
        task.setTaskInstanceCreator(taskElement.getAttribute( TASK_INSTANCE_CREATOR));
        task.setTaskInstanceRunner(taskElement.getAttribute( TASK_INSTANCE_RUNNER));
        task.setTaskInstanceCompletionEvaluator(taskElement.getAttribute( TASK_INSTANCE_COMPLETION_EVALUATOR));
        task.setLoopStrategy(taskElement.getAttribute(LOOP_STRATEGY));
        
        loadEventListeners(task.getEventListeners(), Util4JAXPParser.child(taskElement, EVENT_LISTENERS));
        loadExtendedAttributes(task.getExtendedAttributes(),
                Util4JAXPParser.child(taskElement, EXTENDED_ATTRIBUTES));
        return task;

    }

    protected Participant createPerformer(Element performerElement) {
        if (performerElement == null) {
            return null;
        }
        Participant part = new Participant(performerElement.getAttribute(NAME));
        part.setDisplayName(performerElement.getAttribute(DISPLAY_NAME));
        part.setDescription(Util4JAXPParser.elementAsString(performerElement,
                DESCRIPTION));
        part.setAssignmentHandler(Util4JAXPParser.elementAsString(performerElement,
                ASSIGNMENT_HANDLER));
        return part;
    }

    protected SubWorkflowProcess createSubWorkflowProcess(Element subFlowElement) {
        if (subFlowElement == null) {
            return null;
        }

        SubWorkflowProcess subFlow = new SubWorkflowProcess(subFlowElement.getAttribute(NAME));
        subFlow.setDisplayName(subFlowElement.getAttribute(DISPLAY_NAME));
        subFlow.setDescription(Util4JAXPParser.elementAsString(subFlowElement,
                DESCRIPTION));
        subFlow.setWorkflowProcessId(Util4JAXPParser.elementAsString(subFlowElement,
                WORKFLOW_PROCESS_ID));

        return subFlow;
    }

    protected Application createApplication(Element applicationElement) {
        if (applicationElement == null) {
            return null;
        }
        Application app = new Application(applicationElement.getAttribute(NAME));
        app.setDisplayName(applicationElement.getAttribute(DISPLAY_NAME));
        app.setDescription(Util4JAXPParser.elementAsString(applicationElement,
                DESCRIPTION));
        app.setHandler(Util4JAXPParser.elementAsString(applicationElement,
                HANDLER));
        return app;
    }

    protected Form createForm(Element formElement) {
        if (formElement == null) {
            return null;
        }
        Form form = new Form(formElement.getAttribute(NAME));
        form.setDisplayName(formElement.getAttribute(DISPLAY_NAME));
        form.setDescription(Util4JAXPParser.elementAsString(formElement,
                DESCRIPTION));
        form.setUri(Util4JAXPParser.elementAsString(formElement, URI));
        return form;
    }

    protected Duration createDuration(Element durationElement) {
        if (durationElement == null) {
            return null;
        }
        String sValue = durationElement.getAttribute(VALUE);
        String sIsBusTime = durationElement.getAttribute(IS_BUSINESS_TIME);
        boolean isBusinessTime = true;
        int value = 1;
        if (sValue != null) {
            try {
                value = Integer.parseInt(sValue);
                isBusinessTime = Boolean.parseBoolean(sIsBusTime);
            } catch (Exception ex) {
                return null;
            }
        }
        Duration duration = new Duration(value, durationElement.getAttribute(UNIT));
        duration.setBusinessTime(isBusinessTime);
        return duration;
    }

    protected Loop createLoop(WorkflowProcess wp, Element loopElement)
            throws FPDLParserException {
        String fromNodeId = loopElement.getAttribute(FROM);
        String toNodeId = loopElement.getAttribute(TO);
        Synchronizer fromNode = (Synchronizer) wp.findWFElementById(fromNodeId);
        Synchronizer toNode = (Synchronizer) wp.findWFElementById(toNodeId);

        Loop loop = new Loop(wp,
                loopElement.getAttribute(NAME), fromNode,
                toNode);
        loop.setSn(UUID.randomUUID().toString());

        loop.setDisplayName(loopElement.getAttribute(DISPLAY_NAME));
        loop.setDescription(Util4JAXPParser.elementAsString(loopElement,
                DESCRIPTION));
        Element conditionElement = Util4JAXPParser.child(loopElement, CONDITION);
        loop.setCondition(conditionElement == null ? ""
                : conditionElement.getTextContent());

        // load extended attributes
        Map<String, String> extAttrs = loop.getExtendedAttributes();
        loadExtendedAttributes(extAttrs, Util4JAXPParser.child(loopElement,
                EXTENDED_ATTRIBUTES));

        return loop;
    }

    protected void loadLoops(WorkflowProcess wp, Element loopsElement) throws FPDLParserException {
        if (loopsElement == null) {
            return;
        }
        List loopElementList = Util4JAXPParser.children(loopsElement, LOOP);

        List<Loop> loops = wp.getLoops();



        loops.clear();

        Iterator iter = loopElementList.iterator();
        while (iter.hasNext()) {
            Element loopElement = (Element) iter.next();
            Loop loop = createLoop(wp, loopElement);
            loops.add(loop);



            Synchronizer fromNode = (Synchronizer) loop.getFromNode();
            Synchronizer toNode = (Synchronizer) loop.getToNode();

            fromNode.getLeavingLoops().add(loop);
            toNode.getEnteringLoops().add(loop);
        }
    }

    protected void loadTransitions(WorkflowProcess wp, Element element)
            throws FPDLParserException {

        if (element == null) {
            return;
        }

        loadTransitions(wp, Util4JAXPParser.children(element, TRANSITION));
    }

    protected void loadTransitions(WorkflowProcess wp, List<Element> elements)
            throws FPDLParserException {
        List<Transition> transitions = wp.getTransitions();

        HashMap<String, IWFElement> nodeMap = new HashMap<String, IWFElement>();
        if (wp.getStartNode() != null) {
            nodeMap.put(wp.getStartNode().getId(), wp.getStartNode());
        }
        List activityList = wp.getActivities();
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = (Activity) activityList.get(i);
            nodeMap.put(activity.getId(), activity);
        }
        List synchronizerList = wp.getSynchronizers();
        for (int i = 0; i < synchronizerList.size(); i++) {
            Synchronizer syn = (Synchronizer) synchronizerList.get(i);
            nodeMap.put(syn.getId(), syn);
        }
        List endNodeList = wp.getEndNodes();
        for (int i = 0; i < endNodeList.size(); i++) {
            EndNode endNode = (EndNode) endNodeList.get(i);
            nodeMap.put(endNode.getId(), endNode);

        }

        transitions.clear();

        Iterator iter = elements.iterator();
        while (iter.hasNext()) {
            Element transitionElement = (Element) iter.next();
            Transition transition = createTransition(wp, transitionElement, nodeMap);
            transitions.add(transition);
            Node fromNode = transition.getFromNode();
            Node toNode = transition.getToNode();
            if (fromNode != null && (fromNode instanceof Activity)) {
                ((Activity) fromNode).setLeavingTransition(transition);
            } else if (fromNode != null && (fromNode instanceof Synchronizer)) {
                ((Synchronizer) fromNode).getLeavingTransitions().add(
                        transition);
            }
            if (toNode != null && (toNode instanceof Activity)) {
                ((Activity) toNode).setEnteringTransition(transition);
            } else if (toNode != null && (toNode instanceof Synchronizer)) {
                ((Synchronizer) toNode).getEnteringTransitions().add(transition);
            }
        }
    }

    protected Transition createTransition(WorkflowProcess wp, Element element, Map nodeMap)
            throws FPDLParserException {
        String fromNodeId = element.getAttribute(FROM);
        String toNodeId = element.getAttribute(TO);
        Node fromNode = (Node) nodeMap.get(fromNodeId);
        Node toNode = (Node) nodeMap.get(toNodeId);

        Transition transition = new Transition(wp,
                element.getAttribute(NAME), fromNode,
                toNode);
        transition.setSn(UUID.randomUUID().toString());

        transition.setDisplayName(element.getAttribute(DISPLAY_NAME));
        transition.setDescription(Util4JAXPParser.elementAsString(element,
                DESCRIPTION));
        Element conditionElement = Util4JAXPParser.child(element, CONDITION);
        transition.setCondition(conditionElement == null ? ""
                : conditionElement.getTextContent());

        // load extended attributes
        Map<String, String> extAttrs = transition.getExtendedAttributes();
        loadExtendedAttributes(extAttrs, Util4JAXPParser.child(element,
                EXTENDED_ATTRIBUTES));

        return transition;
    }

    protected void loadDataFields(WorkflowProcess wp, List<DataField> dataFields,
            Element element) throws FPDLParserException {

        if (element == null) {
            return;
        }

        List datafieldsElement = Util4JAXPParser.children(element, DATA_FIELD);
        dataFields.clear();
        Iterator iter = datafieldsElement.iterator();
        while (iter.hasNext()) {
            Element dataFieldElement = (Element) iter.next();
            dataFields.add(createDataField(wp, dataFieldElement));
        }
    }

    protected DataField createDataField(WorkflowProcess wp, Element element)
            throws FPDLParserException {
        if (element == null) {
            return null;
        }
        DataField dataField = new DataField(wp, element.getAttribute(NAME),
                element.getAttribute(DATA_TYPE));
        dataField.setSn(UUID.randomUUID().toString());

        dataField.setDisplayName(element.getAttribute(DISPLAY_NAME));
        dataField.setInitialValue(element.getAttribute(INITIAL_VALUE));
        dataField.setDescription(Util4JAXPParser.elementAsString(element,
                DESCRIPTION));

        loadExtendedAttributes(dataField.getExtendedAttributes(), Util4JAXPParser.child(element, EXTENDED_ATTRIBUTES));

        return dataField;
    }

    protected void loadExtendedAttributes(Map<String, String> extendedAttributes,
            Element element) throws FPDLParserException {

        if (element == null) {
            return;
        }
        extendedAttributes.clear();
        List extendAttributeElementsList = Util4JAXPParser.children(element,
                EXTENDED_ATTRIBUTE);
        Iterator iter = extendAttributeElementsList.iterator();
        while (iter.hasNext()) {
            Element extAttrElement = (Element) iter.next();
            String name = extAttrElement.getAttribute(NAME);
            String value = extAttrElement.getAttribute(VALUE);

            extendedAttributes.put(name, value);

        }
    }
}
