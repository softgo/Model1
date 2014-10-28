package com.bruce.gogo.utils;

import java.util.List;

import net.sf.json.JSONArray;




/**
 * <p>Title: iv HR</p>
 * <p>Description: iv HR System</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: iv.COM/iv.CN</p>
 * @author lhb
 * @version 1.0
 */
public class AjaxMessagesJsonArray {

	//private static final Log logger = LogFactory.getLog(AjaxMessagesJsonArray.class);
	private JSONArray jsonarray;
	
	public  void setMessageArray(List list)
	{
		this.jsonarray = JSONArray.fromObject(list);
		
	}
	
	public String getMessageArray()
	{
		return this.jsonarray.toString();
	}
}

