package com.bruce.gogo.utils;

import com.bruce.gogo.Constants;


public class PageBean 
{
	public int count = 0;   					// 总记录
	
	public int pageSize = Constants.DEFAULT_PAGE_SIZE; 	// 每页显示记录数

	public int pageCount = 0; 					// 总页数

	public int currentPage = 1; 				// 当前页数

	public String listSQL = "none";			// 得到查询记录sql语句

	public int actionType = 0;				// 是否是第一次进入到分页页面

	public int navigationItemCount = 10;        //导航栏显示导航总页数
	
	public int pageCote = 0;    //当前页处于第几栏分页
	
	public int pageCoteCount = 0;     //总分页栏
	
	public int pageStart = 1;                    //分页栏中起始页
	
	public int pageEnd = 1;                          //分页栏中终止页
	
	public String pagecountSQL = "";			//总记录数
	
	public PageBean(){
	}
	
	public PageBean(int pageSize){
		this.pageSize = pageSize;
	}
	
	
	
	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getListSQL() {
		return listSQL;
	}

	public void setListSQL(String listSQL) {
		this.listSQL = listSQL;
	}

	public int getPageCount() 
	{
        if(count % pageSize == 0) 
		    return count / pageSize; 
        else 
		    return count / pageSize + 1;  
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 取得在当前页之前的所有记录的数目 用来计算论坛的楼数
	 * @return
	 */
	public int getPreviousCount()
	{
		return (currentPage - 1)*pageSize;
	}

	public int getNavigationItemCount() {
		return navigationItemCount;
	}

	public void setNavigationItemCount(int navigationItemCount) {
		this.navigationItemCount = navigationItemCount;
	}

	public int getPageCote() {
		return pageCote;
	}

	public void setPageCote(int pageCote) {
		this.pageCote = pageCote;
	}

	public int getPageCoteCount() {
		return pageCoteCount;
	}

	public void setPageCoteCount(int pageCoteCount) {
		this.pageCoteCount = pageCoteCount;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public String getPagecountSQL() {
		return pagecountSQL;
	}

	public void setPagecountSQL(String pagecountSQL) {
		this.pagecountSQL = pagecountSQL;
	}
}
