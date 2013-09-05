/**
 * fileName: EISBase/com.eis.base.web.controller/MenuController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.web.controller.sysmanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eis.base.domain.repository.MenuRepository;
import com.eis.core.model.repository.BaseRepository;
import com.eis.core.web.controller.TreeController;
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
public class MenuController extends TreeController {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	protected String getGridPage() {
		return "sysmanage/menu.ftl";
	}
	
	@Override
	public BaseRepository getRepository() {
		return menuRepository;
	}
	
}
