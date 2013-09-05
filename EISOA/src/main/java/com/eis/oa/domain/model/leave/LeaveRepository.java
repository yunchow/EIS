/**
 * fileName: EISOA/com.eis.oa.domain.model.leave/LeaveRepository.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 3, 2013
 */
package com.eis.oa.domain.model.leave;

import org.springframework.stereotype.Repository;

import com.eis.core.model.repository.GenericRepository;
import com.eis.core.model.repository.decorator.GenericRepositoryDecorator;

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
public class LeaveRepository extends GenericRepositoryDecorator<LeaveForm> implements GenericRepository<LeaveForm> {

}
