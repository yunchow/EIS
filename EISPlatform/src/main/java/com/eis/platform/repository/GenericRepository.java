/**
 * fileName: EISPlatform/com.eis.platform.repository/GenericRepository.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.platform.repository;

import org.springframework.stereotype.Repository;


 /**
 * Title: GenericRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@Repository
public interface GenericRepository<T> {

	/**
	 * create new entity
	 * @param entity
	 */
	void add(T entity);
	
	/**
	 * delete an entity from repository
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * update entity
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * find an entity by primary key
	 * @param id
	 * @return
	 */
	T findById(String id);
	
}
