/**
 * fileName: EISPlatform/com.eis.platform.web.controller/TreeController.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 25, 2013
 */
package com.eis.platform.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eis.platform.model.repository.TreeRepository;
import com.eis.platform.util.TreeUtil;
import com.eis.platform.vo.Tree;


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
