package com.eis.core.activiti;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventGateway;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.InclusiveGateway;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.ManualTask;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.bpmn.model.ScriptTask;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * Class to generate an image based the diagram interchange information in a
 * BPMN 2.0 process.
 * 
 * @author nick.chow
 * @date: Sep 18, 2013
 */
public class ProcessDiagramGenerator {

	protected static final Map<Class<? extends BaseElement>, ActivityDrawInstruction> activityDrawInstructions = new HashMap<Class<? extends BaseElement>, ActivityDrawInstruction>();

	// The instructions on how to draw a certain construct is
	// created statically and stored in a map for performance.
	static {
		// start event
		activityDrawInstructions.put(StartEvent.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				StartEvent startEvent = (StartEvent) flowNode;
				if (startEvent.getEventDefinitions() != null && startEvent.getEventDefinitions().size() > 0) {
					if (startEvent.getEventDefinitions().get(0) instanceof TimerEventDefinition) {
						processDiagramCanvas.drawTimerStartEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
								(int) graphicInfo.getHeight());
					} else if (startEvent.getEventDefinitions().get(0) instanceof ErrorEventDefinition) {
						processDiagramCanvas.drawErrorStartEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
								(int) graphicInfo.getHeight());
					}
				} else {
					processDiagramCanvas.drawNoneStartEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
							(int) graphicInfo.getHeight());
				}
			}
		});

		// signal catch
		activityDrawInstructions.put(IntermediateCatchEvent.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				IntermediateCatchEvent intermediateCatchEvent = (IntermediateCatchEvent) flowNode;
				if (intermediateCatchEvent.getEventDefinitions() != null && intermediateCatchEvent.getEventDefinitions().size() > 0) {
					if (intermediateCatchEvent.getEventDefinitions().get(0) instanceof SignalEventDefinition) {
						processDiagramCanvas.drawCatchingSignalEvent(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
								(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), false);
					} else if (intermediateCatchEvent.getEventDefinitions().get(0) instanceof TimerEventDefinition) {
						processDiagramCanvas.drawCatchingTimerEvent(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
								(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), false);
					}
				}
			}
		});

		// signal throw
		activityDrawInstructions.put(ThrowEvent.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				ThrowEvent throwEvent = (ThrowEvent) flowNode;
				if (throwEvent.getEventDefinitions() != null && throwEvent.getEventDefinitions().size() > 0) {
					if (throwEvent.getEventDefinitions().get(0) instanceof SignalEventDefinition) {
						processDiagramCanvas.drawThrowingSignalEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
								(int) graphicInfo.getHeight());
					}
				} else {
					processDiagramCanvas.drawThrowingNoneEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
							(int) graphicInfo.getHeight());
				}
			}
		});

		// end event
		activityDrawInstructions.put(EndEvent.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				EndEvent endEvent = (EndEvent) flowNode;
				if (endEvent.getEventDefinitions() != null && endEvent.getEventDefinitions().size() > 0) {
					if (endEvent.getEventDefinitions().get(0) instanceof ErrorEventDefinition) {
						processDiagramCanvas.drawErrorEndEvent(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
								(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
					}
				} else {
					processDiagramCanvas.drawNoneEndEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
							(int) graphicInfo.getHeight());
				}
			}
		});

		// task
		activityDrawInstructions.put(Task.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// user task
		activityDrawInstructions.put(UserTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawUserTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// script task
		activityDrawInstructions.put(ScriptTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawScriptTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// service task
		activityDrawInstructions.put(ServiceTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawServiceTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// receive task
		activityDrawInstructions.put(ReceiveTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawReceiveTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// send task
		activityDrawInstructions.put(SendTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawSendTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// manual task
		activityDrawInstructions.put(ManualTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawManualTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// businessRuleTask task
		activityDrawInstructions.put(BusinessRuleTask.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawBusinessRuleTask(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// exclusive gateway
		activityDrawInstructions.put(ExclusiveGateway.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawExclusiveGateway((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// inclusive gateway
		activityDrawInstructions.put(InclusiveGateway.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawInclusiveGateway((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// parallel gateway
		activityDrawInstructions.put(ParallelGateway.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawParallelGateway((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// event based gateway
		activityDrawInstructions.put(EventGateway.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawEventBasedGateway((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		});

		// Boundary timer
		activityDrawInstructions.put(BoundaryEvent.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				BoundaryEvent boundaryEvent = (BoundaryEvent) flowNode;
				if (boundaryEvent.getEventDefinitions() != null && boundaryEvent.getEventDefinitions().size() > 0) {
					if (boundaryEvent.getEventDefinitions().get(0) instanceof TimerEventDefinition) {

						processDiagramCanvas.drawCatchingTimerEvent(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
								(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), boundaryEvent.isCancelActivity());

					} else if (boundaryEvent.getEventDefinitions().get(0) instanceof ErrorEventDefinition) {

						processDiagramCanvas.drawCatchingErrorEvent((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
								(int) graphicInfo.getHeight(), boundaryEvent.isCancelActivity());

					} else if (boundaryEvent.getEventDefinitions().get(0) instanceof SignalEventDefinition) {
						processDiagramCanvas.drawCatchingSignalEvent(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
								(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), boundaryEvent.isCancelActivity());
					}
				}

			}
		});

		// subprocess
		activityDrawInstructions.put(SubProcess.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				if (!graphicInfo.isExpanded()) {
					processDiagramCanvas.drawCollapsedSubProcess(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
							(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), false);
				} else {
					processDiagramCanvas.drawExpandedSubProcess(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
							(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), false);
				}
			}
		});

		// Event subprocess
		activityDrawInstructions.put(EventSubProcess.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				if (!graphicInfo.isExpanded()) {
					processDiagramCanvas.drawCollapsedSubProcess(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
							(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), true);
				} else {
					processDiagramCanvas.drawExpandedSubProcess(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
							(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), true);
				}
			}
		});

		// call activity
		activityDrawInstructions.put(CallActivity.class, new ActivityDrawInstruction() {

			public void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
				processDiagramCanvas.drawCollapsedCallActivity(flowNode.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(),
						(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
			}
		});

	}

	/**
	 * Generates a PNG diagram image of the given process definition, using the
	 * diagram interchange information of the process.
	 */
	public static InputStream generatePngDiagram(BpmnModel bpmnModel) {
		return generateDiagram(bpmnModel, "png", Collections.<String> emptyList());
	}
	
	public static InputStream generatePngDiagram(BpmnModel bpmnModel, List<String> highLightedActivities) {
		return generateDiagram(bpmnModel, "png", highLightedActivities);
	}
	
	// ########################################################################################################################################
	// cutomization start at 2013-9-18 by nick.chow
	// ########################################################################################################################################
		
	
	/**
	 * @param bpmnModel
	 * @param highLightedActivities
	 * @return
	 */
	public static InputStream generatePngDiagramFor(BpmnModel bpmnModel, List<HistoricActivityInstance> highLightedActivities) {
		return generateDiagramFor(bpmnModel, "png", highLightedActivities);
	}
	
	/**
	 * @param bpmnModel
	 * @param imageType
	 * @param highLightedActivities
	 * @return
	 */
	public static InputStream generateDiagramFor(BpmnModel bpmnModel, String imageType, List<HistoricActivityInstance> highLightedActivities) {
		ProcessDiagramCanvas canvas = generateDiagramFor(bpmnModel, highLightedActivities);
		return canvas.generateImage(imageType);
	}
	
	protected static ProcessDiagramCanvas generateDiagramFor(BpmnModel bpmnModel, List<HistoricActivityInstance> highLightedActivities) {
		ProcessDiagramCanvasExt processDiagramCanvas = initProcessDiagramCanvasExt(bpmnModel);

		// // Draw pool shape, if process is participant in collaboration
		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			processDiagramCanvas.drawPoolOrLane(pool.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
					(int) graphicInfo.getHeight());
		}

		// Draw lanes
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane lane : process.getLanes()) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
				processDiagramCanvas.drawPoolOrLane(lane.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		}

		// Highlight sequence flows
		Map<String, String> highLightedActivityMap = new HashMap<String, String>();
		for (HistoricActivityInstance activity : highLightedActivities) {
			highLightedActivityMap.put(activity.getActivityId(), activity.getActivityId());
		}
		
		List<String> highLightedFlowList = new ArrayList<String>();
		
		for (SequenceFlow sequenceFlow : bpmnModel.getProcesses().get(0).findFlowElementsOfType(SequenceFlow.class)) {
			if (highLightedActivityMap.containsKey(sequenceFlow.getSourceRef()) && highLightedActivityMap.containsKey(sequenceFlow.getTargetRef())) {
				if (sequenceFlow.getConditionExpression() == null) {
					highLightedFlowList.add(sequenceFlow.getId());
				}
			}
		}

		// Draw activities and their sequence-flows
		for (FlowNode flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(FlowNode.class)) {
			drawActivityFor(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlowList);
		}
		return processDiagramCanvas;
	}
	
	protected static void drawActivityFor(ProcessDiagramCanvasExt processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode, 
			List<HistoricActivityInstance> highLightedActivities, List<String> highLightedFlows) {
		ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(flowNode.getClass());
		if (drawInstruction != null) {

			drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);

			// Gather info on the multi instance marker
			boolean multiInstanceSequential = false, multiInstanceParallel = false, collapsed = false;
			if (flowNode instanceof Activity) {
				Activity activity = (Activity) flowNode;
				MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
				if (multiInstanceLoopCharacteristics != null) {
					multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
					multiInstanceParallel = !multiInstanceSequential;
				}
			}

			// Gather info on the collapsed marker
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
			if (flowNode instanceof SubProcess) {
				collapsed = !graphicInfo.isExpanded();
			} else if (flowNode instanceof CallActivity) {
				collapsed = true;
			}

			// Actually draw the markers
			processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
					(int) graphicInfo.getHeight(), multiInstanceSequential, multiInstanceParallel, collapsed);

			// Draw highlighted activities

			for (HistoricActivityInstance activity : highLightedActivities) {
				if (StringUtils.equals(activity.getActivityId(), flowNode.getId())) {
					drawHighLightFor(activity, processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
				}
			}
		}

		// Outgoing transitions of activity
		for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
			List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
			boolean drawedLabel = false;
			for (int i = 1; i < graphicInfoList.size(); i++) {

				GraphicInfo graphicInfo = graphicInfoList.get(i);
				GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);
				
				boolean highLighted = (highLightedFlows.contains(sequenceFlow.getId()));
				boolean drawConditionalIndicator = (i == 1) && sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);

				if (i < graphicInfoList.size() - 1) {
					processDiagramCanvas.drawSequenceflowWithoutArrow((int) previousGraphicInfo.getX(), (int) previousGraphicInfo.getY(),
							(int) graphicInfo.getX(), (int) graphicInfo.getY(), drawConditionalIndicator, highLighted);
				} else {
					processDiagramCanvas.drawSequenceflow((int) previousGraphicInfo.getX(), (int) previousGraphicInfo.getY(), (int) graphicInfo.getX(),
							(int) graphicInfo.getY(), drawConditionalIndicator, highLighted);
					if (!drawedLabel) {
						GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
						if (labelGraphicInfo != null) {
							int middleX = (int) (((previousGraphicInfo.getX() + labelGraphicInfo.getX()) + (graphicInfo.getX() + labelGraphicInfo.getX())) / 2);
							int middleY = (int) (((previousGraphicInfo.getY() + labelGraphicInfo.getY()) + (graphicInfo.getY() + labelGraphicInfo.getY())) / 2);
							middleX += 15;
							processDiagramCanvas.drawLabel(sequenceFlow.getName(), middleX, middleY, (int) labelGraphicInfo.getWidth(),
									(int) labelGraphicInfo.getHeight());
							drawedLabel = true;
						}
					}
				}
			}
		}

		// Nested elements
		if (flowNode instanceof FlowElementsContainer) {
			for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements()) {
				if (nestedFlowElement instanceof FlowNode) {
					drawActivityFor(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement, highLightedActivities, highLightedFlows);
				}
			}
		}
	}
	
	/**
	 * @param activity
	 * @param processDiagramCanvas
	 * @param graphicInfo
	 */
	private static void drawHighLightFor(HistoricActivityInstance activity, ProcessDiagramCanvasExt processDiagramCanvas, GraphicInfo graphicInfo) {
		Assert.notNull(activity, "activity must not be null");
		if (activity.getEndTime() == null) {
			processDiagramCanvas.drawRedHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
		} else {
			processDiagramCanvas.drawGreenHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
		}
	}
	
	protected static ProcessDiagramCanvasExt initProcessDiagramCanvasExt(BpmnModel bpmnModel) {

		// We need to calculate maximum values to know how big the image will be
		// in its entirety
		double minX = Double.MAX_VALUE;
		double maxX = 0;
		double minY = Double.MAX_VALUE;
		double maxY = 0;

		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			minX = graphicInfo.getX();
			maxX = graphicInfo.getX() + graphicInfo.getWidth();
			minY = graphicInfo.getY();
			maxY = graphicInfo.getY() + graphicInfo.getHeight();
		}

		List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
		for (FlowNode flowNode : flowNodes) {

			GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());

			// width
			if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
				maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
			}
			if (flowNodeGraphicInfo.getX() < minX) {
				minX = flowNodeGraphicInfo.getX();
			}
			// height
			if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
				maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
			}
			if (flowNodeGraphicInfo.getY() < minY) {
				minY = flowNodeGraphicInfo.getY();
			}

			for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
				List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
				for (GraphicInfo graphicInfo : graphicInfoList) {
					// width
					if (graphicInfo.getX() > maxX) {
						maxX = graphicInfo.getX();
					}
					if (graphicInfo.getX() < minX) {
						minX = graphicInfo.getX();
					}
					// height
					if (graphicInfo.getY() > maxY) {
						maxY = graphicInfo.getY();
					}
					if (graphicInfo.getY() < minY) {
						minY = graphicInfo.getY();
					}
				}
			}
		}

		int nrOfLanes = 0;
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane l : process.getLanes()) {

				nrOfLanes++;

				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
				// // width
				if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
					maxX = graphicInfo.getX() + graphicInfo.getWidth();
				}
				if (graphicInfo.getX() < minX) {
					minX = graphicInfo.getX();
				}
				// height
				if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
					maxY = graphicInfo.getY() + graphicInfo.getHeight();
				}
				if (graphicInfo.getY() < minY) {
					minY = graphicInfo.getY();
				}
			}
		}

		// Special case, see http://jira.codehaus.org/browse/ACT-1431
		if (flowNodes.size() == 0 && bpmnModel.getPools().size() == 0 && nrOfLanes == 0) {
			// Nothing to show
			minX = 0;
			minY = 0;
		}

		return new ProcessDiagramCanvasExt((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY);
	}
	
	// ########################################################################################################################################
	// cutomization end at 2013-9-18 by nick.chow
	// ########################################################################################################################################
	

	/**
	 * Generates a JPG diagram image of the given process definition, using the
	 * diagram interchange information of the process.
	 */
	public static InputStream generateJpgDiagram(BpmnModel bpmnModel) {
		return generateDiagram(bpmnModel, "jpg", Collections.<String> emptyList());
	}

	protected static ProcessDiagramCanvas generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities) {
		return generateDiagram(bpmnModel, highLightedActivities, Collections.<String> emptyList());
	}

	protected static ProcessDiagramCanvas generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities, List<String> highLightedFlows) {
		ProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnModel);

		// // Draw pool shape, if process is participant in collaboration
		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			processDiagramCanvas.drawPoolOrLane(pool.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
					(int) graphicInfo.getHeight());
		}

		// Draw lanes
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane lane : process.getLanes()) {
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
				processDiagramCanvas.drawPoolOrLane(lane.getName(), (int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
						(int) graphicInfo.getHeight());
			}
		}

		// Draw activities and their sequence-flows
		for (FlowNode flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(FlowNode.class)) {
			drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows);
		}
		return processDiagramCanvas;
	}

	public static InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities) {
		ProcessDiagramCanvas canvas = generateDiagram(bpmnModel, highLightedActivities, Collections.<String> emptyList());
		return canvas.generateImage(imageType);
	}

	public static InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows) {
		return generateDiagram(bpmnModel, highLightedActivities, highLightedFlows).generateImage(imageType);
	}

	protected static void drawActivity(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode, List<String> highLightedActivities,
			List<String> highLightedFlows) {

		ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(flowNode.getClass());
		if (drawInstruction != null) {

			drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);

			// Gather info on the multi instance marker
			boolean multiInstanceSequential = false, multiInstanceParallel = false, collapsed = false;
			if (flowNode instanceof Activity) {
				Activity activity = (Activity) flowNode;
				MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
				if (multiInstanceLoopCharacteristics != null) {
					multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
					multiInstanceParallel = !multiInstanceSequential;
				}
			}

			// Gather info on the collapsed marker
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
			if (flowNode instanceof SubProcess) {
				collapsed = !graphicInfo.isExpanded();
			} else if (flowNode instanceof CallActivity) {
				collapsed = true;
			}

			// Actually draw the markers
			processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(),
					(int) graphicInfo.getHeight(), multiInstanceSequential, multiInstanceParallel, collapsed);

			// Draw highlighted activities
			if (highLightedActivities.contains(flowNode.getId())) {
				drawHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
			}

		}

		// Outgoing transitions of activity
		for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
			List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
			boolean drawedLabel = false;
			for (int i = 1; i < graphicInfoList.size(); i++) {

				GraphicInfo graphicInfo = graphicInfoList.get(i);
				GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

				boolean highLighted = (highLightedFlows.contains(sequenceFlow.getId()));
				boolean drawConditionalIndicator = (i == 1) && sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);

				if (i < graphicInfoList.size() - 1) {
					processDiagramCanvas.drawSequenceflowWithoutArrow((int) previousGraphicInfo.getX(), (int) previousGraphicInfo.getY(),
							(int) graphicInfo.getX(), (int) graphicInfo.getY(), drawConditionalIndicator, highLighted);
				} else {
					processDiagramCanvas.drawSequenceflow((int) previousGraphicInfo.getX(), (int) previousGraphicInfo.getY(), (int) graphicInfo.getX(),
							(int) graphicInfo.getY(), drawConditionalIndicator, highLighted);
					if (!drawedLabel) {
						GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
						if (labelGraphicInfo != null) {
							int middleX = (int) (((previousGraphicInfo.getX() + labelGraphicInfo.getX()) + (graphicInfo.getX() + labelGraphicInfo.getX())) / 2);
							int middleY = (int) (((previousGraphicInfo.getY() + labelGraphicInfo.getY()) + (graphicInfo.getY() + labelGraphicInfo.getY())) / 2);
							middleX += 15;
							processDiagramCanvas.drawLabel(sequenceFlow.getName(), middleX, middleY, (int) labelGraphicInfo.getWidth(),
									(int) labelGraphicInfo.getHeight());
							drawedLabel = true;
						}
					}
				}
			}
		}

		// Nested elements
		if (flowNode instanceof FlowElementsContainer) {
			for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements()) {
				if (nestedFlowElement instanceof FlowNode) {
					drawActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement, highLightedActivities, highLightedFlows);
				}
			}
		}
	}

	private static void drawHighLight(ProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
		processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());

	}

	protected static ProcessDiagramCanvas initProcessDiagramCanvas(BpmnModel bpmnModel) {

		// We need to calculate maximum values to know how big the image will be
		// in its entirety
		double minX = Double.MAX_VALUE;
		double maxX = 0;
		double minY = Double.MAX_VALUE;
		double maxY = 0;

		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			minX = graphicInfo.getX();
			maxX = graphicInfo.getX() + graphicInfo.getWidth();
			minY = graphicInfo.getY();
			maxY = graphicInfo.getY() + graphicInfo.getHeight();
		}

		List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
		for (FlowNode flowNode : flowNodes) {

			GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());

			// width
			if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
				maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
			}
			if (flowNodeGraphicInfo.getX() < minX) {
				minX = flowNodeGraphicInfo.getX();
			}
			// height
			if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
				maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
			}
			if (flowNodeGraphicInfo.getY() < minY) {
				minY = flowNodeGraphicInfo.getY();
			}

			for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
				List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
				for (GraphicInfo graphicInfo : graphicInfoList) {
					// width
					if (graphicInfo.getX() > maxX) {
						maxX = graphicInfo.getX();
					}
					if (graphicInfo.getX() < minX) {
						minX = graphicInfo.getX();
					}
					// height
					if (graphicInfo.getY() > maxY) {
						maxY = graphicInfo.getY();
					}
					if (graphicInfo.getY() < minY) {
						minY = graphicInfo.getY();
					}
				}
			}
		}

		int nrOfLanes = 0;
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane l : process.getLanes()) {

				nrOfLanes++;

				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
				// // width
				if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
					maxX = graphicInfo.getX() + graphicInfo.getWidth();
				}
				if (graphicInfo.getX() < minX) {
					minX = graphicInfo.getX();
				}
				// height
				if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
					maxY = graphicInfo.getY() + graphicInfo.getHeight();
				}
				if (graphicInfo.getY() < minY) {
					minY = graphicInfo.getY();
				}
			}
		}

		// Special case, see http://jira.codehaus.org/browse/ACT-1431
		if (flowNodes.size() == 0 && bpmnModel.getPools().size() == 0 && nrOfLanes == 0) {
			// Nothing to show
			minX = 0;
			minY = 0;
		}

		return new ProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY);
	}

	protected static List<FlowNode> gatherAllFlowNodes(BpmnModel bpmnModel) {
		List<FlowNode> flowNodes = new ArrayList<FlowNode>();
		for (Process process : bpmnModel.getProcesses()) {
			flowNodes.addAll(gatherAllFlowNodes(process));
		}
		return flowNodes;
	}

	protected static List<FlowNode> gatherAllFlowNodes(FlowElementsContainer flowElementsContainer) {
		List<FlowNode> flowNodes = new ArrayList<FlowNode>();
		for (FlowElement flowElement : flowElementsContainer.getFlowElements()) {
			if (flowElement instanceof FlowNode) {
				flowNodes.add((FlowNode) flowElement);
			}
			if (flowElement instanceof FlowElementsContainer) {
				flowNodes.addAll(gatherAllFlowNodes((FlowElementsContainer) flowElement));
			}
		}
		return flowNodes;
	}

	protected interface ActivityDrawInstruction {

		void draw(ProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel, FlowNode flowNode);

	}

}
