/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.base.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eis.base.domain.repository.MenuRepository;
import com.eis.platform.util.TreeUtil;
import com.eis.platform.vo.Tree;

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
	@Autowired
	private MenuRepository menuRepository;
	
	@RequestMapping("/home")
	public String homeUI() {
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
