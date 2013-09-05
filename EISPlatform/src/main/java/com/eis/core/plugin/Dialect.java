/**
 * fileName: EISPlatform/com.eis.platform.plugin/Dialect.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 19, 2013
 */
package com.eis.core.plugin;

 /**
 * Title: Dialect.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 19, 2013
 */
public interface Dialect {

	String buildPaginationSql(String sql, Integer pageSize, Integer pageNo);
	
}
