/**
 * fileName: EISBase/com.eis.base.web.controller/ModelController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 27, 2013
 */
package com.eis.base.web.controller.flowmanage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/flowmanage/model/")
public class ModelController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RepositoryService repositoryService; 
	
	@RequestMapping("/grid")
	public String prepare() {
		return "flowmanage/model_grid.ftl";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(required = false) String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		ModelQuery modelQuery = repositoryService.createModelQuery();
		if (name == null) {
			name = "%%";
		}
		result.put("total", modelQuery.modelNameLike(name).count());
		int firstResult = (page - 1) * rows;
		int maxResults = firstResult + rows;
		result.put("rows", modelQuery.modelNameLike(name).orderByModelName().asc().listPage(firstResult, maxResults));
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String saveModel(
			@RequestParam("name") String name, 
			@RequestParam("key") String key, 
			@RequestParam("description") String description) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode editorNode = objectMapper.createObjectNode();
		editorNode.put("id", "canvas");
		editorNode.put("resourceId", "canvas");
		ObjectNode stencilSetNode = objectMapper.createObjectNode();
		stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editorNode.put("stencilset", stencilSetNode);
		Model modelData = repositoryService.newModel();

		ObjectNode modelObjectNode = objectMapper.createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, StringUtils.defaultString(description));
		modelData.setMetaInfo(modelObjectNode.toString());
		modelData.setName(name);
		modelData.setKey(StringUtils.defaultString(key));
		
		byte[] bytes = null;
		try {
			bytes = editorNode.toString().getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		}

		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(), bytes);
		return modelData.getId();
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable String id) {
		repositoryService.deleteModel(id);
		return "0";
	}

	/**
	 * 根据Model部署流程
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "/deploy/{modelId}")
	@ResponseBody
	public String deploy(@PathVariable("modelId") String modelId) throws JsonProcessingException, IOException {
		Model modelData = repositoryService.getModel(modelId);
		ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));

		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
		return deployment.getId();
	}

	/**
	 * 导出model的xml文件
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "/export/{modelId}")
	public void downloadFlowFile(@PathVariable("modelId") String modelId, HttpServletResponse response) throws JsonProcessingException, IOException {
		Model modelData = repositoryService.getModel(modelId);
		BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
		JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
		
		BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
		BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
		byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
		ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
		
		String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		//response.setContentType("text/xml; charset=utf-8");
		//response.setContentLength(bpmnBytes.length);
		IOUtils.copy(in, response.getOutputStream());
		response.flushBuffer();
	}
}
