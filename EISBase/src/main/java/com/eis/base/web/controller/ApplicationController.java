/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.base.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/home")
	public String homeUI() {
		return "home.ftl";
	}

	@RequestMapping("/system/manage/menu")
	public String menuUI() {
		return "system_manage/menuSetting.ftl";
	}
	
}
