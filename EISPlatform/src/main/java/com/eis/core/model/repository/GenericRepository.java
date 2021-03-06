/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.core.model.repository;

import java.util.List;
import java.util.Map;

import com.eis.core.model.Entity;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public interface GenericRepository<T extends Entity<T>> {

	/**
	 * @param entity
	 * @return
	 */
	int save(T entity);
	
	/**
	 * @param id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * @param ids
	 * @return
	 */
	int deleteFor(String... ids);
	
	/**
	 * @param entity
	 * @return
	 */
	int update(T entity);
	
	/**
	 * @param id
	 * @return
	 */
	T findById(String id);
	
	/**
	 * @param ids
	 * @return
	 */
	List<T> findByIds(String... ids);
	
	/**
	 * query by page
	 * @param model must contain page and rows means pageNubmer and pageSize
	 * @return
	 */
	List<T> findByPage(Map<String, Object> model);
}
