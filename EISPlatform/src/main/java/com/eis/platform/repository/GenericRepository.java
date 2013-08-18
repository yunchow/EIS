/**
 * fileName: EISPlatform/com.eis.platform.repository/GenericRepository.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.platform.repository;

import java.util.List;
import java.util.Map;

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
public interface GenericRepository {

	/**
	 * create new entity
	 * 
	 * @param entity
	 */
	int add(Map<String, Object> model);

	/**
	 * delete an entity from repository
	 * 
	 * @param id
	 */
	int delete(String id);

	/**
	 * update entity
	 * 
	 * @param entity
	 */
	int update(Map<String, Object> model);

	/**
	 * find an entity by primary key
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> findById(String id);

	/**
	 * @param model
	 * @return
	 */
	public List<Map<String, Object>> findByPage(Map<String, Object> model);

	/**
	 * @param model
	 * @return
	 */
	public int findTotalCount(Map<String, Object> model);

}
