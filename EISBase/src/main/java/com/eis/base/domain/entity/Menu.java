/**
 * fileName: EISBase/com.eis.domain.entity/Menu.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 13, 2013
 */
package com.eis.base.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eis.platform.vo.Tree;

/**
 * Title: Menu.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 13, 2013
 */
public class Menu implements Tree, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String pid;
	private String name;
	/**
	 * 用于tree显示，与name值相同
	 */
	private String text;
	private String url;
	private String icon;
	private String status;
	private int seq;
	private String comment;
	private Menu parent;
	private List<Tree> children = new ArrayList<Tree>();

	public Menu() {
		
	}
	
	/**
	 * @param name
	 * @param url
	 * @param icon
	 * @param status
	 * @param comment
	 */
	public Menu(String name, String url, String icon, String status, String comment) {
		super();
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.status = status;
		this.comment = comment;
	}
	
	public void addChild(Tree menu) {
		children.add(menu);
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", url=" + url + ", icon=" + icon + ", status=" + status + ", comment=" + comment + ", parent=" + parent
				+ ", children=" + children + "]";
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
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
