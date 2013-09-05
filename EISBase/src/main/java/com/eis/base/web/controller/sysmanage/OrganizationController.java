/**
 * fileName: EISBase/com.eis.base.web.controller/OrganizationController.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.web.controller.sysmanage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eis.base.domain.repository.OrganizationRepository;
import com.eis.core.model.repository.BaseRepository;
import com.eis.core.web.controller.TreeController;
/**
 * Title: OrganizationController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@Controller
@RequestMapping("/sysmanage/organization")
public class OrganizationController extends TreeController {
	
	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	protected String getGridPage() {
		return "sysmanage/organization.ftl";
	}
	
	@Override
	public BaseRepository getRepository() {
		return organizationRepository;
	}
	
}
