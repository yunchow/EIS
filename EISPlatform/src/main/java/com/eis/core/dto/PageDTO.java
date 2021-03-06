/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 9, 2013
 */
package com.eis.core.dto;

import java.io.Serializable;

import com.eis.core.model.support.DataTransferObjectSupport;

 /**
 * <p>Please comment here
 * 
 * @author nick.chow
 * @date: Sep 9, 2013
 */
public class PageDTO extends DataTransferObjectSupport implements Serializable {
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
	
	/**
	 * 记录条数
	 */
	protected long total;
	
	protected void initPaginationInfo() {
		offset = (page - 1) * rows;
		start = offset;
		end = start + rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
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
