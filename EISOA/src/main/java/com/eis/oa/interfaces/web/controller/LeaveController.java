/**
 * fileName: EISOA/com.eis.oa.interfaces.web.controller/LeaveController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.interfaces.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

 /**
 * Title: LeaveController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Sep 3, 2013
 */
@Controller
@RequestMapping("/oa/leave/")
public class LeaveController {

	@RequestMapping("/index")
	public String index() {
		return "leave/index.ftl";
	}
	
	@RequestMapping("/form")
	public String form() {
		return "leave/form.ftl";
	}
	
	@RequestMapping("/grid")
	public String grid() {
		return "leave/grid.ftl";
	}
	
	
}
