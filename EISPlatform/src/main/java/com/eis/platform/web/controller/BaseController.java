/**
 * fileName: EISBase/com.eis.base.web.controller/BasicController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.platform.web.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.platform.repository.BaseRepository;
 /**
 * Title: BasicController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 18, 2013
 */
public abstract class BaseController {
	
	/**
	 * child class must override this method
	 * @return
	 */
	public abstract BaseRepository getRepository();

	@RequestMapping("/save")
	@ResponseBody
	public int save(@RequestParam Map<String, Object> model) {
		return getRepository().add(model);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public int update(@RequestParam Map<String, Object> model) {
		return getRepository().update(model);
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public int delete(@PathVariable String id) {
		return getRepository().delete(id);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(@RequestParam Map<String, Object> model) {
		model.put("rows", getRepository().findByPage(model));
		return model;
	}
}
