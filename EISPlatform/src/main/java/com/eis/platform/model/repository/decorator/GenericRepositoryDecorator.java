/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.platform.model.repository.decorator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eis.platform.model.repository.GenericRepository;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public abstract class GenericRepositoryDecorator<T> implements GenericRepository<T> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int save(T entity) {
		logger.warn("method save not implemented");
		return -1;
	}

	@Override
	public int delete(String id) {
		logger.warn("method delete not implemented");
		return -1;
	}

	@Override
	public int delete(String... ids) {
		logger.warn("method delete... not implemented");
		return -1;
	}

	@Override
	public int update(T entity) {
		logger.warn("method update not implemented");
		return -1;
	}

	@Override
	public T findById(String id) {
		logger.warn("method findById not implemented");
		return null;
	}

	@Override
	public List<T> findById(String... ids) {
		logger.warn("method findById... not implemented");
		return Collections.emptyList();
	}

	@Override
	public List<T> findByPage(Map<String, Object> model) {
		logger.warn("method findByPage not implemented");
		return Collections.emptyList();
	}

}
