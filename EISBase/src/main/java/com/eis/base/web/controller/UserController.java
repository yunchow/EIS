/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.base.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.repository.UserRepository;
import com.eis.platform.repository.BaseRepository;
import com.eis.platform.web.controller.BaseController;

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
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(
						@RequestParam String userName,
						@RequestParam String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("password", password);
		map.put("success", true);
		return map;
	}
	
	@Override
	protected void onSave(Map<String, Object> model) {
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
	protected void onUpdate(Map<String, Object> model) {
		onSave(model);
	}
	
	@Override
	protected void onDelete(String id) {
		userRepository.deleteUserRole(id);
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
