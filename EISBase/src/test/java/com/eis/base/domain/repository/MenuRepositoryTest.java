/**
 * fileName: EISBase/com.eis.base.domain.repository/MenuRepositoryTest.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.domain.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eis.base.domain.entity.Menu;

 /**
 * Title: MenuRepositoryTest.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:conf/beans-*.xml")
public class MenuRepositoryTest {

	@Autowired
	private MenuRepository mr;
	
	/**
	 * Test method for {@link com.eis.base.domain.repository.MenuRepository#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<Menu> ms = mr.findAll();
		System.out.println(ms);
		Assert.assertNotNull(ms);
	}

	/**
	 * Test method for {@link com.eis.platform.repository.BaseRepository#save(java.lang.Object)}.
	 */
	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eis.platform.repository.BaseRepository#delete(java.lang.String)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eis.platform.repository.BaseRepository#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.eis.platform.repository.BaseRepository#findById(java.lang.String)}.
	 */
	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

}
