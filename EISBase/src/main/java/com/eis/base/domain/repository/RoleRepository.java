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

import com.eis.platform.model.repository.BaseRepository;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 5, 2013
 */
@Repository
public interface RoleRepository extends BaseRepository {
	
	List<Map<String, String>> findAllMenu();
	
	void deleteRoleMenu(String id);
	
	void saveRoleMenu(List<Map<String, String>> params);
}
