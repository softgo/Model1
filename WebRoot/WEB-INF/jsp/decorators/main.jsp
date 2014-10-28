<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%> 
<%@ page import="java.util.*" %>
<%@ page import="com.bruce.gogo.Constants" %>
<%@ page import="com.bruce.gogo.utils.*" %>
<%@ page import="com.bruce.gogo.system.model.Stmkinfo" %>

<%!
	public String getUrlStr(List permList,Integer parentId)//得到顶部菜单的链接
	{
		String urlStr = "";
		for(int i=0;i<permList.size();i++)
 	   	{
			Stmkinfo permission = (Stmkinfo)permList.get(i);
			if(permission.getPtype().equals("2"))//2表示"菜单"
			{
				if(permission.getParentid().equals(parentId))
				{
					if(permission.getIsfolder().equals("Y"))
					{
						urlStr = getUrlStr(permList,permission.getId());
					} else if(permission.getPerlink()!=null && !permission.getPerlink().equals("")) {
						urlStr = permission.getPerlink();
					}
					break;
				}		
			}
 	   	}
		return urlStr;
	}
%>
<%
	SessionInfo sessionInfo = (SessionInfo)session.getAttribute(Constants.sessioninfo);
	String trueName = "";
	String username = "";
	List menuList = new ArrayList();
	List parents = new ArrayList();
	List children = new ArrayList();
	Integer parentId = null;
	String parentName = "";
	Integer childId = null;
	if(sessionInfo != null)
	{
		trueName = sessionInfo.getLoginName();
		username = sessionInfo.getUserName();
		parentId = sessionInfo.getParentid();
		childId = sessionInfo.getChildid();
		if(parentId == null)
		{
			parentId = new Integer(-1);
		}
		menuList = (List)sessionInfo.getMenu();
		if(menuList == null){
			menuList = new ArrayList();
		}
		if(menuList!=null)
		{
			for(int i=0;i<menuList.size();i++)
			{
				Stmkinfo permission = (Stmkinfo)menuList.get(i);
				if(permission.getPtype().equals("2"))//2表示"菜单"
				{
					if(permission.getParentid().equals(new Integer(-1)))
					{
						parents.add(permission);
					} else if(permission.getParentid().equals(parentId)){
						children.add(permission);	
					}	
					if(permission.getId().equals(parentId))
					{
						parentName = permission.getPename();
					}
				}
			}
		}
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title><decorator:title default="管理后台"/></title>
	<link href="<%=request.getContextPath()%>/css/calendar-blue.css" title="winter" rel="stylesheet" type="text/css" media="all"  />
	<script src="<%=request.getContextPath()%>/js/Calendar.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/calendar_zh.js" type="text/javascript"></script>
	<link href="css/css.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/list.js"></script>
	<script type="text/javascript" src="js/select.js"></script>
	<script src="js/jquery-1.7.js" type="text/javascript"></script>
    <script src="js/jquery-1.2.6.js" type="text/javascript"></script>
	<script src="js/formValidator_min.js" type="text/javascript"></script>
	<script src="js/select.js" type="text/javascript"></script>
	<script src="js/formValidatorRegex.js" type="text/javascript"></script>
	<script src="js/province.js" type="text/javascript"></script>
	<decorator:head></decorator:head>
</head>
<body>
<!-- 页头 --->
<div id="header">
  <div id="subNav">
	  <div id="subNav_left"></div>
	  <div id="subNav_right">
	    <div id="subNav_nav1">
	      <dl>
	        <dt id="subNav_nav1_Btn1">你好! <%=trueName %>(<%=username %>)  </dt>
	        <dd id="subNav_nav1_Btn2"><a href="Login!logout.<%=Constants.ActionExt %>">[退出]</a></dd>
	      </dl>
	    </div>
	  	
	    <div id="subNav_Link">
	           <%
	           for(int i = 0;i < parents.size();i++){
	        	   Stmkinfo permissioninfo = (Stmkinfo)parents.get(i);
	        	   String className = "linkdown";
	        	   String linkUrl = "";
	        	   if(parentId.equals(permissioninfo.getId()) && request.getRequestURI().indexOf("Login!welcome.action") < 0)
	        	   {
	        	    className = "linkno";
	        	   }
	        	   //linkUrl = getUrlStr(permissionMap,permissioninfo.getId());
	        	   linkUrl = getUrlStr(menuList,permissioninfo.getId());
	           %>
	           	    <div class="<%=className %>">
	           	    	<%
	           	    		if(!linkUrl.equals(""))
	           	    	  { 
	           	    	%>
	           	    		<a href="<%=linkUrl %>?parentId=<%=permissioninfo.getId() %>"><%=permissioninfo.getPename() %></a>
	           	    	<%}else {
	           	    	%>
	           	    		<a href="javascript:;"><%=permissioninfo.getPename() %></a>
	           	    	<%}%>	
	           	    </div> 
	           <%} %>
	    </div>
	  </div>
	</div>
</div>
<%
	Integer currentParentId = new Integer("-1");
  //得到当前父id
	for(int i=0;i<menuList.size();i++)
	{
		Stmkinfo permission = (Stmkinfo)menuList.get(i);
		if(permission.getPtype().equals("2"))//2表示"菜单"
		{
			if(permission.getId().equals(childId))
			{
				currentParentId = permission.getParentid();
				break;
			}		
		}
  }
%>
<!-- 主题容器 --->
<div id="container">
  <decorator:body></decorator:body>
  <!-- 左侧容器 --->
  <div id="left_container">
  	<!--左恻内容--->
    <div id="productCate" <%if(request.getRequestURI().indexOf("Login!welcome.action") >= 0){%>style="display:none"<%}%>>
      <div id="productCateTop">
        <h2><%=parentName %></h2>
      </div>
      <div id="proCateContent">
           <%			        
           for(int i = 0;i < children.size();i++)
           {
        	   Stmkinfo permissioninfo = (Stmkinfo)children.get(i);
           %>
            <div class="proCateContent_a">
            	<h2>
            	<%
            		if(permissioninfo.getIsfolder().equals("Y"))
            		{
            	%>
            		<a href="javascript:void(0)" onclick="showChild(<%=permissioninfo.getId() %>,this)"><%=permissioninfo.getPename() %></a>
            	<%}else{ 
            	%>
            		<a href="<%=permissioninfo.getPerlink() %>?parentId=<%=parentId %>&childId=<%=permissioninfo.getId() %>"><%=permissioninfo.getPename() %></a>
            	<%} %>
            	</h2>
            </div>
            <%
            	if(permissioninfo.getIsfolder().equals("Y"))
            	{
            %>
            <div class="childmenu" id="<%=permissioninfo.getId() %>" <%if(currentParentId.equals(permissioninfo.getId())) {%>style="display:block;"<%} %>>
            	<ul>
            		<%
	            		 for(int j=0;j<menuList.size();j++)
			        	   {
											Stmkinfo permission = (Stmkinfo)menuList.get(j);
											if(permission.getPtype().equals("2"))//2表示"菜单"
											{
												if(permission.getParentid().equals(permissioninfo.getId()))
												{
								%>
									<li><a href="<%=permission.getPerlink() %>?parentId=<%=parentId %>&childId=<%=permission.getId() %>" title=""><%=permission.getPename() %></a></li>
								<%
												}		
											}
			        	   }
            		%>
							</ul>
            </div>
            <%} %>
          <%} %>
      </div>
      <div id="proCateButtom"></div>
    </div>
  </div>
  <div id="con_buttom"></div>
</div>
<script type="text/javascript">
<!--//for menu
	var nowMenuIndex = -1;
	var nowMenuObj = '';
	function showChild(Id,obj)
	{
		if (Id != nowMenuIndex)
		{
			obj.parentNode.parentNode.className="proCateContent_a menuon";
			!!nowMenuObj?nowMenuObj.parentNode.parentNode.className = "proCateContent_a":void(0);
			nowMenuObj = obj;
			document.getElementById(Id).style.display='block';
			!!document.getElementById(nowMenuIndex)?document.getElementById(nowMenuIndex).style.display = 'none':void(0);
			nowMenuIndex = Id;
		}
	    else
		{
			if(document.getElementById(Id).style.display=='block')
			{
				document.getElementById(Id).style.display='none'
				obj.parentNode.parentNode.className="proCateContent_a";
			}
			else
			{
				document.getElementById(Id).style.display='block';
				obj.parentNode.parentNode.className="proCateContent_a menuon";
			}
		}
	}
//-->
</script>

</body>
</html>
