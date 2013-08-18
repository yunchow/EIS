/**
 * fileName: EISBase/com.eis.base.web.controller/DataDicController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.base.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eis.base.domain.repository.DataDicRepository;
import com.eis.platform.repository.GenericRepository;

 /**
 * Title: DataDicController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 18, 2013
 */
@Controller
@RequestMapping("/sysmanage/datadic")
public class DataDicController extends BaseController {

	@Autowired
	private DataDicRepository dataDicRepository;
	
	@RequestMapping("/page")
	public String preparePage() {
		return "sysmanage/datadic.ftl";
	}

	@Override
	public GenericRepository getRepository() {
		return dataDicRepository;
	}
}
