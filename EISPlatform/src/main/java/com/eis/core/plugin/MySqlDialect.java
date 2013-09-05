/**
 * fileName: EISPlatform/com.eis.platform.plugin/MySqlDialect.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 19, 2013
 */
package com.eis.core.plugin;

import com.eis.core.plugin.Dialect;

 /**
 * Title: MySqlDialect.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 19, 2013
 */
public class MySqlDialect implements Dialect {

	@Override
	public String buildPaginationSql(String sql, Integer pageSize, Integer pageNo) {
		return sql + String.format(" limit ?, ?", (pageNo - 1) * pageSize, pageSize);
	}
	
}
