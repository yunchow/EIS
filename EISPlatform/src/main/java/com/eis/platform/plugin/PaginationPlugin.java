/**
 * fileName: EISPlatform/com.eis.platform.plugin/PaginationPlugin.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 19, 2013
 */
package com.eis.platform.plugin;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Title: PaginationPlugin.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 19, 2013
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginationPlugin implements InitializingBean, Interceptor {
	private final Logger logger = LoggerFactory.getLogger(PaginationPlugin.class);
	private String dialect;
	private Map<String, Dialect> dialectMap;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public Map<String, Dialect> getDialectMap() {
		return dialectMap;
	}

	public void setDialectMap(Map<String, Dialect> dialectMap) {
		this.dialectMap = dialectMap;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(dialect, "dialect must have length");
		Assert.notEmpty(dialectMap, "dialectMap must not be empty");
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();
		
		ObjectFactory objectFactory = new DefaultObjectFactory();
		ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, objectFactory, objectWrapperFactory);
		
	    MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
	    if (mappedStatement.getId().indexOf("ByPage") == -1) {
	    	return invocation.proceed();
	    }
	    
	    @SuppressWarnings("unchecked")
		Map<String, ?> param = (Map<String, ?>) boundSql.getParameterObject();
		String pageSizeParam = (String) param.get("rows");
		String pageNoParam = (String) param.get("page");
		Integer pageSize = Integer.valueOf(pageSizeParam);
		Integer pageNo = Integer.valueOf(pageNoParam);
		
		if (pageNo <= 0 || pageSize <= 0) {
			logger.warn("pagination sql need valid pager arguments");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("pageSize = " + pageSize);
			logger.debug("pageNo = " + pageNo);
		}
		
	    Dialect adialect = dialectMap.get(dialect);
	    String sql = adialect.buildPaginationSql(boundSql.getSql(), pageSize, pageNo);
	    
	    /*if (logger.isDebugEnabled()) {
	    	logger.debug("build pagination sql: " + sql.replace("\n", " "));
	    }*/
	    
	    metaStatementHandler.setValue("delegate.boundSql.sql", sql);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
