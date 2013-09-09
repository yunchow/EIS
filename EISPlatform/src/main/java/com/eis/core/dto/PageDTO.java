/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 9, 2013
 */
package com.eis.core.dto;

import java.io.Serializable;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 9, 2013
 */
public class PageDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 当前页
	 */
	protected int page;
	
	/**
	 * 页大小
	 */
	protected int rows;
	
	/**
	 * 偏移量，与start相同
	 */
	protected int offset;
	
	/**
	 * 开始量，与offset相同
	 */
	protected int start;
	
	/**
	 * 结束量
	 */
	protected int end;
	
	protected void initPaginationInfo() {
		offset = (page - 1) * rows;
		start = offset;
		end = start + rows;
	}

	public int getOffset() {
		return offset;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		initPaginationInfo();
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		initPaginationInfo();
	}
	
}
