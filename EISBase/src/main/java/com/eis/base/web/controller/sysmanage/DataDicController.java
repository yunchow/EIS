/**
 * fileName: EISBase/com.eis.base.web.controller/DataDicController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.base.web.controller.sysmanage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.repository.DataDicRepository;
import com.eis.platform.model.repository.BaseRepository;
import com.eis.platform.web.controller.BaseController;

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
	
	
	@Override
	protected String getGridPage() {
		return "sysmanage/datadic.ftl";
	}
	
	@RequestMapping("/{type}")
	@ResponseBody
	public List<Map<String, String>> findByType(@PathVariable String type) {
		return dataDicRepository.findByType(type);
	} 

	@Override
	public BaseRepository getRepository() {
		return dataDicRepository;
	}
}
