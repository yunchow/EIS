/**
 * fileName: EISPlatform/com.eis.platform.util/TreeUtil.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 23, 2013
 */
package com.eis.platform.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.eis.platform.vo.Tree;



 /**
 * Title: TreeUtil.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 23, 2013
 */
public final class TreeUtil {
	
	private TreeUtil() {
		
	}

	/**
	 * 将列表数据转换成父子关系的树形数据
	 * @param treeList
	 */
	public static void buildJsonTreeFor(List<? extends Tree> treeList) {
		Assert.notNull(treeList, "cannot build a null collection");
		Map<String, Tree> map = new HashMap<String, Tree>(treeList.size());
		for (Tree tree : treeList) {
			map.put(tree.getId(), tree);
		}
		Iterator<?> iter = treeList.iterator();
		while (iter.hasNext()) {
			Tree tree = (Tree) iter.next();
			if (tree.getPid() != null) {
				Tree ptree = map.get(tree.getPid());
				if (ptree != null) {
					ptree.addChild(tree);
					iter.remove();
				}
			}
		}
	}
	
}
