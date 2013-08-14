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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.entity.Menu;
import com.eis.base.domain.repository.MenuRepository;

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
