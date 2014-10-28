<%@ page contentType="text/html; charset=UTF-8" language="java" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ page language="java" %>
<%@ page import="com.bruce.gogo.Constants"%>
<div id="SetpageSize">
      一页显示条数：<a href="javascript:setSize(<%=Constants.DEFAULT_PAGE_SIZE%>);"><%=Constants.DEFAULT_PAGE_SIZE%>条</a>
				<a href="javascript:setSize(<%=Constants.SET_PAGE_SIZE_300%>);"><%=Constants.SET_PAGE_SIZE_300%>条</a> 
				<a href="javascript:setSize(<%=Constants.SET_PAGE_SIZE_500%>);"><%=Constants.SET_PAGE_SIZE_500%>条</a> 
				<a href="javascript:setSize(<%=Constants.SET_PAGE_SIZE_800%>);"><%=Constants.SET_PAGE_SIZE_800%>条</a>
				<a href="javascript:setSize(<%=Constants.SET_PAGE_SIZE_1000%>);"><%=Constants.SET_PAGE_SIZE_1000%>条</a>
				<a href="javascript:setSize(<%=Constants.SET_PAGE_SIZE_2000%>);"><%=Constants.SET_PAGE_SIZE_2000%>条</a>
		<s:if test="#request.paginator.currentPage > 1">
			<span style="cursor:hand;COLOR: #31659c"> 
		    <s:a href="javascript:void(0)" onclick="javacript:pageBean.yest_page()">上一页</s:a>&nbsp;&nbsp;		
			</span>
		</s:if>
		
		<s:if test="#request.paginator.currentPage < #request.paginator.pageCount">
			<span style="cursor:hand;COLOR: #31659c"> 	
		    <s:a href="javascript:void(0)" onclick="javacript:pageBean.next_page()">下一页</s:a>&nbsp;&nbsp;				
			</span>
		</s:if>	 
</div>	
