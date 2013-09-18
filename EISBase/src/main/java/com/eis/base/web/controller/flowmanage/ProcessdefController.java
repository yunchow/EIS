/**
 * fileName: EISBase/com.eis.base.web.controller/ModelController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 27, 2013
 */
package com.eis.base.web.controller.flowmanage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

 /**
 * Title: ModelController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 27, 2013
 */
@Controller
@RequestMapping("/flowmanage/processdef/")
public class ProcessdefController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RepositoryService repositoryService; 
	
	@RequestMapping("/grid")
	public String prepare() {
		return "flowmanage/processdef_grid.ftl";
	}
	
	/**
	 * 将流程定义转换为模型
	 * @param processDefinitionId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws XMLStreamException
	 */
	@RequestMapping(value = "/convert2/model/{processDefinitionId}")
	@ResponseBody
	public String convertToModel(@PathVariable String processDefinitionId) throws UnsupportedEncodingException, XMLStreamException {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
		XMLStreamReader xtr = xif.createXMLStreamReader(in);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

		BpmnJsonConverter converter = new BpmnJsonConverter();
		ObjectNode modelNode = converter.convertToJson(bpmnModel);
		
		Model modelData = repositoryService.newModel();
		modelData.setKey(processDefinition.getKey());
		modelData.setName(processDefinition.getResourceName());
		modelData.setCategory(processDefinition.getDeploymentId());

		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
		modelData.setMetaInfo(modelObjectNode.toString());

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
		return "ok";
	}
	
	@RequestMapping(value = "/{resourceType}/{pdid}")
	public void downloadResourceBy(@PathVariable String resourceType, @PathVariable String pdid, 
			HttpServletResponse response) throws JsonProcessingException, IOException {
		logger.info("process definition id is {}", pdid);
		InputStream is = null;
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
		
		if (processDefinition == null) {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().println("未查到流程定义文件，ID = " + pdid);
			return;
		}
		
		if ("xml".equals(resourceType)) {
			is = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
		}
		else if ("img".equals(resourceType)) {
			is = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
		}
		
		if (is != null) {
			IOUtils.copy(is, response.getOutputStream());
		} else {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().println("未查到相关资源，ID = " + pdid);
		}
	}
	
	@RequestMapping(value = "/suspend", method = RequestMethod.POST)
	@ResponseBody
	public String suspend(@RequestParam String id) {
		try {
			repositoryService.suspendProcessDefinitionById(id);
		}
		catch (ActivitiException e) {
			logger.error(e.getMessage(), e);
			return "alreadySuspended";
		}
		return "200";
	}
	
	@RequestMapping(value = "/activate", method = RequestMethod.POST)
	@ResponseBody
	public String activate(@RequestParam String id) {
		try {
			repositoryService.activateProcessDefinitionById(id);
		}
		catch (ActivitiObjectNotFoundException e) {
			logger.error(e.getMessage(), e);
			return "alreadyActive";
		}
		return "200";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(required = false) String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		ProcessDefinitionQuery processdefQuery = repositoryService.createProcessDefinitionQuery();
		if (name == null) {
			name = "%%";
		}
		result.put("total", processdefQuery.processDefinitionResourceNameLike(name).count());
		int firstResult = (page - 1) * rows;
		int maxResults = firstResult + rows;
		
		List<ProcessDefinition> defList = processdefQuery.processDefinitionResourceNameLike(name).orderByProcessDefinitionKey().asc()
				.orderByProcessDefinitionVersion().desc().listPage(firstResult, maxResults);
		List<Map<String, Object>> processDefList = new ArrayList<Map<String, Object>>(defList.size());
		for (ProcessDefinition def : defList) {
			Map<String, Object> defMap = new HashMap<String, Object>();
			defMap.put("id", def.getId());
			defMap.put("key", def.getKey());
			defMap.put("name", def.getName());
			defMap.put("deploymentId", def.getDeploymentId());
			defMap.put("description", def.getDescription());
			defMap.put("resourceName", def.getResourceName());
			defMap.put("diagramResourceName", def.getDiagramResourceName());
			defMap.put("version", def.getVersion());
			defMap.put("category", def.getCategory());
			defMap.put("suspend", def.isSuspended());
			processDefList.add(defMap);
		}
		result.put("rows", processDefList);
		
		logger.info("process def page result is {}", result);
		return result;
	}
	
}
