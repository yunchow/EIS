/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.base.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.repository.MenuRepository;
import com.eis.base.domain.repository.UserRepository;
import com.eis.base.dto.User;
import com.eis.core.util.TreeUtil;
import com.eis.core.vo.Tree;
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
public class ApplicationController {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(HttpSession session, User user) {
		User localUser = userRepository.findUserByNameAndPassword(user);
		if (localUser == null) {
			localUser = new User();
			localUser.setId(user.getName());
			localUser.setName(user.getName());
			localUser.setTrueName(user.getName());
			localUser.setGroups(new ArrayList<Group>(0));
		}
		session.setAttribute("user", localUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		return map;
	}
	
	@RequestMapping("/logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("index.html");
	}
	
	@RequestMapping("/home")
	public String homeUI(@CookieValue(required = false) String theme, ModelMap model) throws IOException {
		logger.info("theme = {}", theme);
		model.addAttribute("theme", theme);
		return "home/home.ftl";
	}
	
	@RequestMapping("/home/menu/list")
	public String loadLeftMenuPage(ModelMap model) {
		List<? extends Tree> menuList = menuRepository.findAll();
		TreeUtil.buildJsonTreeFor(menuList);
		model.addAttribute("menus", menuList);
		return "home/menu_list.ftl";
	}

}
