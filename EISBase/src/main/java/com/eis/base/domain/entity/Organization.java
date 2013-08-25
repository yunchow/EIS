/**
 * fileName: EISBase/com.eis.base.domain.entity/Organization.java
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Aug 25, 2013
 */
package com.eis.base.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eis.platform.vo.Tree;

 /**
 * Title: Organization.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 25, 2013
 */
public class Organization implements Tree, Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String pid;
	private String name;
	/**
	 * 用于tree显示，与name值相同
	 */
	private String text;
	private String organizer;
	private String comment;

	private List<Tree> children = new ArrayList<Tree>();
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getPid() {
		return pid;
	}

	@Override
	public void addChild(Tree child) {
		children.add(child);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", pid=" + pid + ", name=" + name + ", text=" + text + ", organizer=" + organizer + ", comment=" + comment
				+ ", children=" + children + "]";
	}

}
