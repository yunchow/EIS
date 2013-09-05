/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 5, 2-113
 */
package com.eis.core.model.repository.decorator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.eis.core.model.repository.BaseRepository;
/**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2-113
 */
public class BaseRepositoryDecorator implements BaseRepository {

	@Override
	public int save(Map<String, Object> model) {
		return -1;
	}

	@Override
	public int delete(String id) {
		return -1;
	}

	@Override
	public int update(Map<String, Object> model) {
		return -1;
	}

	@Override
	public Map<String, Object> findById(String id) {
		return null;
	}

	@Override
	public List<Map<String, Object>> findByPage(Map<String, Object> model) {
		return Collections.emptyList();
	}

	@Override
	public int findTotalCount(Map<String, Object> model) {
		return -1;
	}

}
