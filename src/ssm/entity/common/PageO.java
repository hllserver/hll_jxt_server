package ssm.entity.common;

import java.io.Serializable;
/**
 * 用于分页查询 
 * @author liaoyun
 * 2016-3-13
 */
public class PageO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5094303759335684899L;
	private int currentPage=1;     //当前页
	private int pageSize=7;        //页面显示条数
	private int totalPage;         //数据总页数
	private int totalRecords;      //数据总条数
	public PageO() {
		super();
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
