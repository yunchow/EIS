/**
 * fileName: EISDomain/com.eis.domain.entity/EmployeeTest.java
 * copyright: EIS All rights reverved
 * author: zhouyun
 * date: 2013年8月3日 下午6:26:33
 */
package com.eis.domain.entity;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>Please comment here</p>
 * 
 * @author nick.chow
 * @date: 2013年8月3日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:conf/beans-*.xml")
public class EmployeeTestXXX {

	@Autowired
	private Employee employee;
	
	@Test
	public void testFindAll() {
		List<Employee> list = employee.findAll();
		System.out.println("######### list = " + list);
		Assert.assertNotNull(list);
		for (Employee emp : list) {
			System.out.println(emp.getEmployeeId() + ", " + emp.getNo() + ", " + emp.getName() + ", " + emp.getGender());
		}
		
	}
	
	@Test
	public void add() {
		employee.setEmployeeId(1);
		employee.setNo("1002");
		employee.setName("nick");
		employee.setGender("XX");
		employee.add();
	}

}
