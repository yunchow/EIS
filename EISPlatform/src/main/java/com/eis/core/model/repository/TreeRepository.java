/**
 * fileName: EISPlatform/com.eis.platform.repository/TreeRepository.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 25, 2013
 */
package com.eis.core.model.repository;

import java.util.List;

import com.eis.core.model.repository.BaseRepository;
import com.eis.core.vo.Tree;

/**
 * Title: TreeRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 25, 2013
 */
public interface TreeRepository extends BaseRepository {

	/**
	 * 返回所有树形数据
	 * @return
	 */
	List<? extends Tree> findAll();
}
