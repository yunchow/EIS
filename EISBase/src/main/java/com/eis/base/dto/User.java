/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 24, 2013
 */
package com.eis.base.dto;

import java.io.Serializable;
import java.util.List;

import org.activiti.engine.identity.Group;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 24, 2013
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String trueName;
	private String password;
	
	private List<Group> groups;

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	
}
