package com.bruce.gogo.utils;

import java.util.Date;

public class SearchResultData 
{
	private String tblname;
	
	private String title;
	
	private String detail;
	
	private Date createdate;
	
	private String imageurl;
	
	private String msource;

	public SearchResultData(){}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getMsource() {
		return msource;
	}

	public void setMsource(String msource) {
		this.msource = msource;
	}

	public String getTblname() {
		return tblname;
	}

	public void setTblname(String tblname) {
		this.tblname = tblname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	};
	
}
