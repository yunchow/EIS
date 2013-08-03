/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:08:48
 */
package com.eis.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Please comment here</p>
 * 
 * @author nick.chow
 * @date: 2013年8月3日
 */
@Controller
@RequestMapping("/employee")
public class EmployeeAction {
	
	
	@RequestMapping("/hello1")
	public String hello() {
		return "error.ftl";
	}

}
