/**
 * fileName: EISBase/com.eis.base.domain.repository/MenuRepository.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eis.platform.repository.BaseRepository;
 /**
 * Title: DataDicRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
/**
 * Title: DataDicRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@Repository
public interface RoleRepository extends BaseRepository {
	
	List<Map<String, String>> findAllMenu();
	
	void deleteRoleMenu(String id);
	
	void saveRoleMenu(List<Map<String, String>> params);
}
