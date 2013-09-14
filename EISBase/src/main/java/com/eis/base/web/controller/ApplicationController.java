/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.base.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eis.base.domain.repository.MenuRepository;
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
	
	@RequestMapping("/home")
	public String homeUI(@CookieValue(required = false) String theme, ModelMap model) {
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
