/**
 * fileName: EISBase/com.eis.base.domain.repository/UserRepository.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eis.core.model.repository.BaseRepository;
/**
 * Title: UserRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 24, 2013
 */
@Repository
public interface UserRepository extends BaseRepository {
	
	List<Map<String, String>> findAllUser();
	
	List<Map<String, String>> findAllRole();
	
	void deleteUserRole(String id);
	
	void saveUserRole(List<Map<String, String>> params);
}
