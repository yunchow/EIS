/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:08:48
 */
package com.eis.domain.rep;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eis.domain.entity.Employee;

/**
 * <p>Please comment here</p>
 * 
 * @author nick.chow
 * @date: 2013年8月3日
 */
@Repository
public interface EmployeeRep {

	/**
	 * @param employee
	 */
	void add(Employee employee);
	
	/**
	 * @param employeeId
	 */
	void delete(long employeeId);
	
	/**
	 * @param employee
	 */
	void update(Employee employee);
	
	/**
	 * @param employeeId
	 * @return
	 */
	Employee findById(long employeeId);
	
	/**
	 * @return all
	 */
	List<Employee> findAll();
}
