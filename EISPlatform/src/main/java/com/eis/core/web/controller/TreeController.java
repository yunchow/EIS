/**
 * fileName: EISPlatform/com.eis.platform.web.controller/TreeController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 25, 2013
 */
package com.eis.core.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.core.model.repository.TreeRepository;
import com.eis.core.util.TreeUtil;
import com.eis.core.vo.Tree;


 /**
 * Title: TreeController.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 25, 2013
 */
public abstract class TreeController extends BaseController {
	
	@RequestMapping("/tree")
	@ResponseBody
	public List<? extends Tree> findAll() {
		TreeRepository treeRepository = (TreeRepository) getRepository();
		List<? extends Tree> treeList = treeRepository.findAll();
		TreeUtil.buildJsonTreeFor(treeList);
		return treeList;
	}
	
}
