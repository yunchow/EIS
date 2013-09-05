/**
 * fileName: EISBase/com.eis.base.web.controller/RoleController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.base.web.controller.sysmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.base.domain.repository.RoleRepository;
import com.eis.core.model.repository.BaseRepository;
import com.eis.core.web.controller.BaseController;
/**
 * Title: RoleController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 18, 2013
 */
@Controller
@RequestMapping("/sysmanage/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	protected void onSave(Map<String, Object> model) {
		String id = (String) model.get("id");
		String rolemenu = (String) model.get("rolemenus");
		if (rolemenu == null || rolemenu.length() == 0) {
			return;
		}
		String[] mids = StringUtils.split(rolemenu, ",");
		List<Map<String, String>> params = new ArrayList<Map<String, String>>(mids.length);
		
		for (String mid : mids) {
			Map<String, String> row = new HashMap<String, String>(2);
			row.put("rid", id);
			row.put("mid", mid);
			params.add(row);
		}
		logger.debug("saveRoleMenu param is {}", params);
		roleRepository.deleteRoleMenu(id);
		roleRepository.saveRoleMenu(params);
	}
	
	@Override
	protected void onUpdate(Map<String, Object> model) {
		this.onSave(model);
	}
	
	@Override
	protected void onDelete(String id) {
		roleRepository.deleteRoleMenu(id);
	}

	@Override
	protected String getGridPage() {
		return "sysmanage/role.ftl";
	}
	
	@RequestMapping("/menus")
	@ResponseBody
	public List<Map<String, String>> findAllMenu() {
		return roleRepository.findAllMenu();
	}

	@Override
	public BaseRepository getRepository() {
		return roleRepository;
	}
}
