/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2013
 */
package com.eis.platform.model.repository;

import java.util.List;
import java.util.Map;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
public interface GenericRepository<T> {

	/**
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * @param ids
	 */
	void delete(String... ids);
	
	/**
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * @param id
	 * @return
	 */
	T findById(String id);
	
	/**
	 * @param ids
	 * @return
	 */
	List<T> findById(String... ids);
	
	/**
	 * query by page
	 * @param model must contain page and rows means pageNubmer and pageSize
	 * @return
	 */
	List<T> findByPage(Map<String, Object> model);
}
