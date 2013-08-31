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

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadDeployFile(@RequestParam("qqfile") MultipartFile file) {
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
		String fileName = file.getOriginalFilename();
		Deployment deployment = null;
		String extension = FilenameUtils.getExtension(fileName);
		
		if ("zip".equals(extension) || "rar".equals(extension)) {
			ZipInputStream zip = new ZipInputStream(is);
			deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
		} else {
			if (logger.isInfoEnabled()) {
				try {
					logger.info("file content is {}", new String(file.getBytes()));
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			deployment = repositoryService.createDeployment().addInputStream(fileName, is).deploy();
		}
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
		logger.info("deploy list is {}", list);
		result.put("success", list != null && !list.isEmpty());
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
