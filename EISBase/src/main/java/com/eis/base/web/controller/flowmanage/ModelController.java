/**
 * fileName: EISBase/com.eis.base.web.controller/ModelController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 27, 2013
 */
package com.eis.base.web.controller.flowmanage;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang.StringUtils;
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
			@RequestParam("comment") String comment) {
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
		comment = StringUtils.defaultString(comment);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, comment);
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
	 */
	/*@RequestMapping(value = "deploy/{modelId}")
	public String deploy(@PathVariable("modelId") String modelId, RedirectAttributes redirectAttributes) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
			redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
		} catch (Exception e) {
			logger.error("根据模型部署流程失败：modelId={}", modelId, e);
		}
		return "redirect:/activiti/model/list";
	}*/

	/**
	 * 导出model的xml文件
	 *//*
	@RequestMapping(value = "export/{modelId}")
	public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
		try {
			Model modelData = repositoryService.getModel(modelId);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("导出model的xml文件失败：modelId={}", modelId, e);
		}
	}*/
}
