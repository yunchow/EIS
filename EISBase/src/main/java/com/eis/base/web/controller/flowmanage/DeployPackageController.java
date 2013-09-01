/**
 * fileName: EISBase/com.eis.base.web.controller.flowmanage/DeployPackageController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 29, 2013
 */
package com.eis.base.web.controller.flowmanage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Title: DeployPackageController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 29, 2013
 */
@Controller
@RequestMapping("/flowmanage/deploy")
public class DeployPackageController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepositoryService repositoryService;

	@RequestMapping("/grid")
	public String prepare() {
		return "flowmanage/deploy_package_grid.ftl";
	}

	@RequestMapping("/dodeploy")
	public String dodeploy() {
		return "flowmanage/do_deploy.ftl";
	}

	@RequestMapping("/delete/{cacade}/{id}")
	@ResponseBody
	public String deleteDeploymentCacadeProcess(@PathVariable String cacade, @PathVariable String id) {
		logger.info("cacade = {}", cacade);
		repositoryService.deleteDeployment(id, "cacade".equals(cacade));
		return "200";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadDeployFile(@RequestParam("qqfile") MultipartFile file) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("error", "读取文件失败");
			return result;
		}
		
		if (file.getSize() ==0 || file.getSize() >= 5 * 1024 * 1024) {
			result.put("success", false);
			result.put("preventRetry", true);
			result.put("error", "文件大小超过最大限制：5MB");
			return result;
		}
		
		String fileName = file.getOriginalFilename();
		Deployment deployment = null;
		String extension = FilenameUtils.getExtension(fileName);
		
		if ("zip".equals(extension) || "bar".equals(extension)) {
			ZipInputStream zip = new ZipInputStream(is);
			deployment = repositoryService.createDeployment().name(file.getOriginalFilename()).addZipInputStream(zip).deploy();
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("file content is {}", new String(file.getBytes()));
			}
			deployment = repositoryService.createDeployment().name(file.getOriginalFilename()).addInputStream(fileName, is).deploy();
		}
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
		logger.info("deploy list is {}", list);
		boolean boo = list != null && !list.isEmpty();
		result.put("success", boo);
		if (!boo) {
			result.put("error", "部署失败：未成功生成流程定义文件");
		}
		return result;
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(@RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String name) {
		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
		Map<String, Object> result = new HashMap<String, Object>();
		if (name == null) {
			name = "%%";
		}
		result.put("total", deploymentQuery.deploymentNameLike(name).count());
		int firstResult = (page - 1) * rows;
		int maxResults = firstResult + rows;
		List<Deployment> deployments = deploymentQuery.deploymentNameLike(name).orderByDeploymenTime().desc().listPage(firstResult, maxResults);
		List<Map<String, Object>> deployList = new ArrayList<Map<String, Object>>(deployments.size());
		for (Deployment deployment : deployments) {
			Map<String, Object> deploy = new HashMap<String, Object>();
			deploy.put("id", deployment.getId());
			deploy.put("name", deployment.getName());
			deploy.put("category", deployment.getCategory());
			deploy.put("deploymentTime", deployment.getDeploymentTime());
			deployList.add(deploy);
		}
		result.put("rows", deployList);
		return result;
	}

}
