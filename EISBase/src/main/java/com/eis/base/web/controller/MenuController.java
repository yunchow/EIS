/**
 * fileName: EISBase/com.eis.base.web.controller/MenuController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.entity.Menu;
import com.eis.base.domain.repository.MenuRepository;
import com.eis.platform.helper.UUIDHelper;

 /**
 * Title: MenuController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@Controller
@RequestMapping("/sysmanage/menu")
public class MenuController {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@RequestMapping("/{type}")
	@ResponseBody
	public String saveOrUpdateMenu(@PathVariable String type,
			@RequestParam String id,
			@RequestParam String name,
			@RequestParam String url,
			@RequestParam String icon,
			@RequestParam String status,
			@RequestParam(value = "parent[id]", required = false) String pid,
			@RequestParam String comment) {
		Menu menu = new Menu(name, url, icon, status, comment);
		Menu parent = new Menu();
		parent.setId(pid);
		menu.setParent(parent);
		if ("add".equals(type)) {
			menu.setId(UUIDHelper.uuid());
			menuRepository.add(menu);
		} else if ("update".equals(type)) {
			menu.setId(id);
			menuRepository.update(menu);
		}
		return "failed";
	}

	@RequestMapping("/page")
	public String menuUI() {
		return "sysmanage/menu_setting.ftl";
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}
	
}
