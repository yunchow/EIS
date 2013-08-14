/**
 * fileName: EISPlatform/com.eis.platform.rep/EmployeeRep.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:08:48
 */
package com.eis.base.domain.entity;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eis.base.domain.repository.EmployeeRepository;
import com.eis.platform.Hello;


/**
 * <p>Please comment here</p>
 * 
 * @author nick.chow
 * @date: 2013年8月3日
 */
@Component
public class Employee {
	
	private long employeeId;
	private String no;
	private String name;
	private String gender;
	
	private EmployeeRepository employeeRep;

	public String say(String input) {
		return new Hello().say(input);
	}
	
	
	public void add() {
		employeeRep.add(this);
	}

	/**
	 * @return the employeeRep
	 */
	public EmployeeRepository getEmployeeRep() {
		return employeeRep;
	}

	/**
	 * @param employeeRep the employeeRep to set
	 */
	public void setEmployeeRep(EmployeeRepository employeeRep) {
		this.employeeRep = employeeRep;
	}
	
	public List<Employee> findAll() {
		return employeeRep.findAll();
	}

	/**
	 * @return the employeeId
	 */
	public long getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
