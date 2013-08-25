/**
 * fileName: EISBase/com.eis.base.domain.repository/OrganizationRepository.java
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
 * Title: OrganizationRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@Repository
public interface OrganizationRepository extends BaseRepository {
	/**
	 * @return orgnizationList
	 */
	public List<Map<String, String>> findAll();

}
