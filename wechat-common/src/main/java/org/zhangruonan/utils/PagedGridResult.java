package org.zhangruonan.utils;

import java.util.List;

/**
 * 返回分页数据工具类
 *
 * @author qinhao
 * @email coderqin@foxmail.com
 * @date 2025-03-15 13:42:50
 */
public class PagedGridResult {

	/**
	 * 当前页数
	 */
	private long page;
	/**
	 * 总页数
	 */
	private long total;
	/**
	 * 总记录数
	 */
	private long records;
	/**
	 * 每行显示的内容
	 */
	private List<?> rows;

	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
