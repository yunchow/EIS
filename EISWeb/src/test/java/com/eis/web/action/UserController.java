/**
 * fileName: EISWeb/com.eis.web.action/UserAction.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 11, 2013
 */
package com.eis.web.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/user")
public class UserController {
	
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

	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "helloxx";
	}
	
}
