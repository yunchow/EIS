/**
 * fileName: EISPlatform/com.eis.platform.vo/PageVO.java
 * copyright: EIS All rights reverved
 * author: nick.chow
 * date: Aug 18, 2013
 */
package com.eis.core.vo;

import java.io.Serializable;
import java.util.List;

 /**
 * Title: PageVO.java
 * <p>
 * Please comment here
 * </p>
 * 
 * @author nick.chow
 * @date: Aug 18, 2013
 */
@Deprecated
public class PageVO<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 总记录数
	 */
	private int total;
	
	/**
	 * 当前显示哪一页
	 */
	private int pageNo;
	
	/**
	 * 每页条数
	 */
	private int pageSize;
	
	/**
	 * 偏移量
	 */
	private int offset;
	
	private List<T> rows;
	
	public PageVO() {
		
	}
	
	/**
	 * @param pageNo
	 * @param pageSize
	 */
	public PageVO(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		initOffset();
	}

	public void initOffset() {
		this.offset = (pageNo - 1) * pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		initOffset();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		initOffset();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
