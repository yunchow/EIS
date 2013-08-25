/**
 * fileName: EISBase/com.eis.base.web.controller/BasicController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.platform.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/grid")
	public String prepare() {
		return getGridPage();
	}
	
	protected String getGridPage() {
		return null;
	}
	
	/**
	 * child class must override this method
	 * @return
	 */
	public abstract BaseRepository getRepository();

	@RequestMapping("/save")
	@ResponseBody
	public int save(@RequestParam Map<String, Object> model) {
		int result = getRepository().save(model);
		this.onSave(model);
		return result;
	}
	
	protected void onSave(Map<String, Object> model) {
		
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public int update(@RequestParam Map<String, Object> model) {
		this.onUpdate(model);
		return getRepository().update(model);
	}
	
	protected void onUpdate(Map<String, Object> model) {
		
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public int delete(@PathVariable String id) {
		this.onDelete(id);
		return getRepository().delete(id);
	}
	
	protected void onDelete(String id) {
		
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> findByPage(@RequestParam Map<String, Object> model) {
		model.put("rows", getRepository().findByPage(model));
		return model;
	}
}
