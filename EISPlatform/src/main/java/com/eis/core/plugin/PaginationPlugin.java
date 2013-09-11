/**
 * fileName: EISPlatform/com.eis.platform.plugin/PaginationPlugin.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 19, 2013
 */
package com.eis.core.plugin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
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
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.eis.core.dto.PageDTO;

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
	private SqlSessionTemplate sqlSessionTemplate;
	private SqlSessionFactory sqlSessionFactory;
	private Map<String, Dialect> dialectMap;
	private ObjectFactory objectFactory = new DefaultObjectFactory();
	private ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

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
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(dialect, "dialect must have length");
		Assert.notEmpty(dialectMap, "dialectMap must not be empty");
	}
	
	protected String getTotalCountStatementId(final MappedStatement mappedStatement) {
		String pageStatementId = mappedStatement.getId();
		/*String ns = "";
		int index = pageStatementId.lastIndexOf(".");
		if (index >= 0) {
			ns = pageStatementId.substring(0, index);
		}
		return ns + ".findTotalCount";
		*/
		logger.info("find page list statement id is {}", pageStatementId);
		String totalCountStatement = pageStatementId.substring(0, pageStatementId.indexOf("ByPage")) + "TotalCount";
		logger.info("find page total count statement id is {}", totalCountStatement);
		return totalCountStatement;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> getParamMap(BoundSql boundSql) {
		return (Map<String, Object>) boundSql.getParameterObject();
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		final BoundSql boundSql = statementHandler.getBoundSql();
		
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, objectFactory, objectWrapperFactory);
	    final MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
	    
	    if (mappedStatement.getId().indexOf("ByPage") == -1) {
	    	return invocation.proceed();
	    }
	    
		Map<String, Object> param = null;
		PageDTO pageDto = null;
		Integer pageSize = 0;
		Integer pageNo = 0;
	    
	    Object paramObject = boundSql.getParameterObject();
	    if (paramObject instanceof Map) {
	    	param = getParamMap(boundSql);
			pageSize = Integer.valueOf((String) param.get("rows"));
			pageNo = Integer.valueOf((String) param.get("page"));
			param.put("offset", (pageNo - 1) * pageSize);
			param.put("pageSize", pageSize);
	    } else if (paramObject instanceof PageDTO) {
	    	pageDto = (PageDTO) paramObject;
	    	pageSize = pageDto.getRows();
			pageNo = pageDto.getPage();
	    }
		
		if (pageNo <= 0 || pageSize <= 0) {
			logger.warn("pagination don't need becuase pageNo <= 0 || pageSize <= 0");
			return invocation.proceed();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("pageSize = {}", pageSize);
			logger.debug("pageNo = {}", pageNo);
		}
		
		String totalCountStatementId = getTotalCountStatementId(mappedStatement);
		final Configuration configuration = mappedStatement.getConfiguration();
		
		// query for records total count
		final MetaObject recordMetaObject = MetaObject.forObject(boundSql, objectFactory, objectWrapperFactory);
		@SuppressWarnings("unchecked")
		List<ParameterMapping> parameterMappings = (List<ParameterMapping>) recordMetaObject.getValue("parameterMappings");
		MappedStatement totalMappedStatement = null;
		
		try {
			totalMappedStatement = configuration.getMappedStatement(totalCountStatementId);
		} catch (Exception e) {
			logger.warn("cannot find target statement: {}", totalCountStatementId);
		}
		
		if (totalMappedStatement == null) {
			logger.error("statement [{}] is required", totalCountStatementId);
			throw new IllegalStateException(String.format("cannot find required statement: {%s}", totalCountStatementId));
			// createMappedStatement(mappedStatement);
		}
		else {
			logger.debug("findTotalCount param = {}", param);
			logger.debug("findTotalCount pageDto = {}", pageDto);
			Object totalParam = param == null ? pageDto : param;
			
			Object total = sqlSessionTemplate.selectOne(totalCountStatementId, totalParam);
			logger.debug("total = {}", total);
			Assert.notNull(total, "Please confirm mapper file is configured correctly: " + totalCountStatementId);
			if (param != null) {
				param.put("total", total);
			} else if (pageDto != null) {
				pageDto.setTotal((Long) total);
			}
		}
		
		recordMetaObject.setValue("parameterMappings", rebuildParameterMappings(configuration, parameterMappings));
		
	    Dialect adialect = dialectMap.get(dialect);
	    String sql = adialect.buildPaginationSql(boundSql.getSql(), pageSize, pageNo);
	    metaStatementHandler.setValue("delegate.boundSql.sql", sql);
	    
	    return invocation.proceed();		
	}

	/**
	 * @param configuration
	 * @param parameterMappings
	 * @return
	 */
	protected List<ParameterMapping> rebuildParameterMappings(final Configuration configuration, List<ParameterMapping> parameterMappings) {
		Assert.notNull(parameterMappings);
		List<ParameterMapping> paramList = new ArrayList<ParameterMapping>(parameterMappings.size() + 2);
		for (ParameterMapping paramMapping : parameterMappings) {
			ParameterMapping paramMappingLocal = new ParameterMapping.Builder(configuration, paramMapping.getProperty(), paramMapping.getJavaType()).build();
			paramList.add(paramMappingLocal);
		}	
		ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, "offset", Integer.class).build();
		paramList.add(parameterMapping);
		parameterMapping = new ParameterMapping.Builder(configuration, "rows", Integer.class).build();
		paramList.add(parameterMapping);
		return paramList;
	}
	
	/**
	 * crate dynamic find total count mapped statement
	 * @param mappedStatement
	 */
	protected void createMappedStatement(final MappedStatement mappedStatement) {
		/*logger.warn("cannot find findTotalCount statement, default sql will be used");
		SqlSource sqlSource = new SqlSource() {
			
			@Override
			public BoundSql getBoundSql(Object parameterObject) {
				String sql = "select count(*) " + boundSql.getSql().substring(boundSql.getSql().indexOf("from"));
				int index = sql.indexOf("order");
				if (index >= 0) {
					sql = sql.substring(0, index);
				}
				BoundSql totalBoundSql = new BoundSql(configuration, sql, new ArrayList<ParameterMapping>(0), parameterObject);
				MetaObject totalMetaObject = MetaObject.forObject(totalBoundSql, objectFactory, objectWrapperFactory);
				List<ParameterMapping> paramList = new ArrayList<ParameterMapping>(parameterMappings.size());
				for (ParameterMapping paramMapping : parameterMappings) {
					ParameterMapping param = new ParameterMapping.Builder(configuration, paramMapping.getProperty(), paramMapping.getJavaType()).build();
					paramList.add(param);
				}
				totalMetaObject.setValue("parameterMappings", paramList);
				return totalBoundSql;
			}
		};
		Builder builder = new Builder(configuration, totalCountStatementId, sqlSource, SqlCommandType.SELECT);
		ResultMap resultMap = new ResultMap.Builder(configuration, "_ID_" + System.currentTimeMillis(), Integer.class, new ArrayList<ResultMapping>(0)).build();
		List<ResultMap> resultMaps = new ArrayList<ResultMap>(1);
		resultMaps.add(resultMap);
		builder.resultMaps(resultMaps);
		totalMappedStatement = builder.build();
		configuration.addMappedStatement(totalMappedStatement);*/
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
