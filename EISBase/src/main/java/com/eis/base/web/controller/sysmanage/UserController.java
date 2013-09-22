/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.base.web.controller.sysmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.repository.UserRepository;
import com.eis.core.model.repository.BaseRepository;
import com.eis.core.web.controller.BaseController;
 /**
 * Title: UserAction.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 11, 2013
 */
@Controller
@RequestMapping("/sysmanage/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IdentityService identityService;
	
	@Override
	protected void onSave(Map<String, Object> model) {
		String id = (String) model.get("id");
		User user = identityService.newUser(id);
		identityService.saveUser(user);
		this.onUpdate(model);
	}

	@Override
	protected void onUpdate(Map<String, Object> model) {
		String id = (String) model.get("id");
		String rolemenu = (String) model.get("userroles");
		
		if (rolemenu == null || rolemenu.length() == 0) {
			return;
		}
		String[] mids = StringUtils.split(rolemenu, ",");
		List<Map<String, String>> params = new ArrayList<Map<String, String>>(mids.length);
		
		for (String mid : mids) {
			Map<String, String> row = new HashMap<String, String>(2);
			row.put("userId", id);
			row.put("roleId", mid);
			params.add(row);
		}
		logger.debug("saveUserRole param is {}", params);
		userRepository.deleteUserRole(id);
		userRepository.saveUserRole(params);
	}
	
	@Override
	protected void onDelete(String id) {
		userRepository.deleteUserRole(id);
		identityService.deleteUser(id);
	}
	
	@Override
	protected String getGridPage() {
		return "sysmanage/user.ftl";
	}
	
	@RequestMapping("/role")
	@ResponseBody
	public List<Map<String, String>> findAllRole() {
		return userRepository.findAllRole();
	}

	@Override
	public BaseRepository getRepository() {
		return userRepository;
	}
}
