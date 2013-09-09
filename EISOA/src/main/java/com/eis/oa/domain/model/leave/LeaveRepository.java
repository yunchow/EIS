/**
 * fileName: EISOA/com.eis.oa.domain.model.leave/LeaveRepository.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.domain.model.leave;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eis.core.model.repository.GenericRepository;
import com.eis.core.model.repository.decorator.GenericRepositoryDecorator;
import com.eis.oa.infrastructure.dto.LeaveFormDTO;

/**
 * Title: LeaveRepository.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Sep 3, 2013
 */
@Repository
public class LeaveRepository extends GenericRepositoryDecorator<LeaveFormEntity> implements GenericRepository<LeaveFormEntity> {

	/**
	 * 分页查找我的请假单
	 * @param model
	 * @return
	 */
	public List<LeaveFormEntity> findLeaveByPage(LeaveFormDTO leaveDto) {
		return getSqlSession().selectList(namespace + ".findLeaveByPage", leaveDto);
	}
}
