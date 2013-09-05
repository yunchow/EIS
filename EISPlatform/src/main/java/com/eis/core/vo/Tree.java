/**
 * fileName: EISPlatform/com.eis.platform.vo/Tree.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 23, 2013
 */
package com.eis.core.vo;


 /**
 * Title: Tree.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 23, 2013
 */
public interface Tree {

	String getId();
	
	String getPid();
	
	void addChild(Tree child);
	
}
