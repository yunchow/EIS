/**
 * fileName: EISBase/com.eis.base.web.controller.flowmanage/DeployPackageController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 29, 2013
 */
package com.eis.base.web.controller.flowmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@Autowired
	private RepositoryService repositoryService;

	@RequestMapping("/grid")
	public String prepare() {
		return "flowmanage/deploy_package_grid.ftl";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(required = false) String name) {
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
