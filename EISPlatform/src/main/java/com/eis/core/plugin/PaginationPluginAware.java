/**
 * fileName: EISPlatform/com.eis.platform.plugin/PaginationAware.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 19, 2013
 */
package com.eis.core.plugin;

import org.apache.ibatis.session.SqlSessionFactory;

import com.eis.core.plugin.PaginationPlugin;

/**
 * Title: PaginationAware.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 19, 2013
 */
public class PaginationPluginAware {
	private PaginationPlugin paginationPlugin;
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * @param paginationPlugin
	 * @param sqlSessionFactory
	 */
	public PaginationPluginAware(PaginationPlugin paginationPlugin, SqlSessionFactory sqlSessionFactory) {
		super();
		this.paginationPlugin = paginationPlugin;
		this.sqlSessionFactory = sqlSessionFactory;
		this.paginationPlugin.setSqlSessionFactory(sqlSessionFactory);
	}

	public PaginationPlugin getPaginationPlugin() {
		return paginationPlugin;
	}

	public void setPaginationPlugin(PaginationPlugin paginationPlugin) {
		this.paginationPlugin = paginationPlugin;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
