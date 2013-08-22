/**
 * fileName: EISBase/com.eis.base.web.controller/RoleController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.base.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eis.base.domain.repository.RoleRepository;
import com.eis.platform.repository.BaseRepository;
import com.eis.platform.web.controller.BaseController;

 /**
 * Title: RoleController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 18, 2013
 */
@Controller
@RequestMapping("/sysmanage/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping("/page")
	public String preparePage() {
		return "sysmanage/role.ftl";
	}

	@Override
	public BaseRepository getRepository() {
		return roleRepository;
	}
}
