/**
 * fileName: EISBase/com.eis.base.domain.repository/MenuRepository.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eis.base.domain.entity.Menu;
import com.eis.platform.repository.BaseRepository;
 /**
 * Title: MenuRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@Repository
public interface MenuRepository extends BaseRepository {
	/**
	 * @return
	 */
	public List<Menu> findAll();

	/**
	 * @param menu
	 */
	public void add(Menu menu);

	/**
	 * @param menu
	 */
	public void update(Menu menu);
}
