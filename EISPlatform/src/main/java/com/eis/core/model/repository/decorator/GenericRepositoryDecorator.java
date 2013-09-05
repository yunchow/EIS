/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.core.model.repository.decorator;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eis.core.model.Entity;
import com.eis.core.model.repository.GenericRepository;
/**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public abstract class GenericRepositoryDecorator<T extends Entity<T>> extends SqlSessionDaoSupport implements GenericRepository<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected String namespace;
	protected String saveStatement;
	protected String updateStatement;
	protected String deleteStatement;
	protected String deleteForStatement;
	protected String findByIdStatement;
	protected String findByIdsStatement;
	protected String findByPageStatement;

	protected GenericRepositoryDecorator() {
		namespace = getClass().getName();
		saveStatement = namespace + ".save";
		updateStatement = namespace + ".update";
		deleteStatement = namespace + ".delete";
		deleteForStatement = namespace + ".deleteFor";
		findByIdStatement = namespace + ".findById";
		findByIdsStatement = namespace + ".findByIds";
		findByPageStatement = namespace + ".findByPage";
	}
	
	@Override
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Override
	public int save(T entity) {
		return getSqlSession().insert(saveStatement, entity);
	}

	@Override
	public int delete(String id) {
		return getSqlSession().delete(deleteStatement, id);
	}

	@Override
	public int deleteFor(String... ids) {
		return getSqlSession().delete(deleteForStatement, ids);
	}

	@Override
	public int update(T entity) {
		return getSqlSession().update(updateStatement, entity);
	}

	@Override
	public T findById(String id) {
		return getSqlSession().selectOne(findByIdStatement, id);
	}

	@Override
	public List<T> findByIds(String... ids) {
		return getSqlSession().selectList(findByIdsStatement, ids);
	}

	@Override
	public List<T> findByPage(Map<String, Object> model) {
		return getSqlSession().selectList(findByIdsStatement, model);
	}

}
