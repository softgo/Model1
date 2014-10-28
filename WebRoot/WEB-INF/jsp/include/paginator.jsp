

<%@ page contentType="text/html; charset=UTF-8" language="java" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ page language="java" %>
<%@ page import="com.bruce.gogo.utils.PageBean" %>





<script language="javascript">
function pageBean(url, formName, pageSize, zheForm)
{	
	zheForm.pageSize.value = pageSize;
	this.first_page = function ()
	{
		zheForm.action = url;
		zheForm.actType.value = "first_page";
		zheForm.submit();
	}
	
	this.yest_page = function ()
	{
		zheForm.action = url;
		zheForm.actType.value = "yest_page";
		zheForm.submit();
	}
	
	this.next_page = function ()
	{
		zheForm.action = url;
		zheForm.actType.value = "next_page";
		zheForm.submit();
	}
	
	this.last_page = function ()
	{
		zheForm.action = url;
		zheForm.actType.value = "last_page";
		zheForm.submit();
	}
	
	this.goto_page = function (pageCount)
	{
		var pageNo = document.getElementById("pageNo").value;
		
		if(!is_int(pageNo))
		{
			alert('输入不正确!');
			document.getElementById("pageNo").value = 1;
			return false;
		}
					
		if(!(pageNo >= 1))
		{
			alert('输入不正确!');
			document.getElementById("pageNo").value = 1;
			return false;
		}
	
		pageNo = eval(pageNo);
	
		if(pageNo > pageCount)
		{
			alert('输入不能超过总页数!');
			document.getElementById("pageNo").value = 1;
			return false;
		}

		zheForm.action = url;
		zheForm.actType.value = "goto_page";
		zheForm.submit();
	}
	
	this.search_page = function ()
	{
		zheForm.action = url;
		zheForm.actType.value = "search_page";
		zheForm.actionType.value = "none";
		zheForm.listSQL.value = "none";
		zheForm.submit();
	} 

	this.research_page = function ()
	{
		zheForm.action = url;
		zheForm.actType.value = "search_page";
		zheForm.actionType.value = "count";
		zheForm.submit();
	} 

	
}  

</script> 

<% 
	PageBean aPageBean = (PageBean)request.getAttribute("paginator");
%>

<!-- ======================== Paginate ========================= -->      	  
	 <table cellSpacing="0" cellPadding="0" width="95%" border="0" class="text">
		<tbody>
				<s:hidden name="paginator.cacheKey" value="%{#request.paginator.cacheKey}"/>
		        <s:hidden name="paginator.currentPage" />
		        <s:hidden name="listSQL" value="%{#request.paginator.listSQL}" />
		        <s:hidden name="actionType" value="%{#request.paginator.actionType}" />
		        <s:hidden name="actType" value="%{#request.actType}" />
		        <s:hidden name="pageSize" value="%{#request.paginator.pageSize}" />
		<td width="90%" align="right"><span class="tdhand" >
		<span class="text">
			 总计&nbsp;&nbsp;<span class="txtred"><strong><s:property value="#request.paginator.count" /></strong></span>&nbsp;&nbsp;条，
			 分&nbsp;&nbsp;<span class="txtred"><strong><s:property value="#request.paginator.pageCount" /></strong></span>&nbsp;&nbsp;页显示，
			 当前第&nbsp;&nbsp;<span class="txtred"><strong><s:property value="#request.paginator.currentPage" /></strong></span>&nbsp;
			 页	&nbsp;&nbsp;
		</span>
		
		<s:if test="#request.paginator.currentPage > 1">
			<span style="cursor:hand;COLOR: #31659c">
		        <s:a onclick="javacript:pageBean.first_page()" href="javascript:void(0)">首页</s:a>&nbsp;&nbsp;|  				
			</span>
		</s:if>
		<s:if test="#request.paginator.currentPage < 2">
			<span style="COLOR: #696969">
			<a>首页</a>&nbsp;&nbsp;|
			</span>
		</s:if>
		
		<s:if test="#request.paginator.currentPage > 1">
			<span style="cursor:hand;COLOR: #31659c"> 
		    <s:a onclick="javacript:pageBean.yest_page()" href="javascript:void(0)">上一页</s:a>&nbsp;&nbsp;|		
			</span>
		</s:if>
		<s:if test="#request.paginator.currentPage < 2">
			<span style="COLOR: #696969"> 
			<a>上一页</a>&nbsp;&nbsp;|
			</span>
		</s:if>	
		
		<s:if test="#request.paginator.currentPage < #request.paginator.pageCount">
			<span style="cursor:hand;COLOR: #31659c"> 	
		    <s:a onclick="javacript:pageBean.next_page()" href="javascript:void(0)">下一页</s:a>&nbsp;&nbsp;|				
			</span>
		</s:if>	 
		<s:if test="#request.paginator.currentPage > (#request.paginator.pageCount - 1)">		
			<span style="COLOR: #696969"> 
			<a>下一页</a>&nbsp;&nbsp;|
			</span>
		</s:if>	

		<s:if test="#request.paginator.currentPage < #request.paginator.pageCount">
			<span style="cursor:hand;COLOR: #31659c"> 
		    <s:a onclick="javacript:pageBean.last_page()" href="javascript:void(0)">末页</s:a>			
			</span>
		</s:if>	 
		<s:if test="#request.paginator.currentPage > (#request.paginator.pageCount - 1)">				
			<span style="COLOR: #696969"> 
			<a>末页</a>
			</span>
		</s:if>	

		<s:if test="#request.paginator.count > 0">								
			  <span class="text">&nbsp;&nbsp;
			  	<s:textfield size="1" id="pageNo" name="pageNo" value='%{#request.paginator.currentPage}' theme="simple"  onkeypress="if(event.keyCode==13)return false;"/>页  		  
			    <span style="cursor:hand;COLOR: #31659c"><a onclick="javascript:pageBean.goto_page('<%= aPageBean.getPageCount() %>')">[ Go ]</a></span>	
			  </span>
		</s:if>
		
		</span>
		</td>
		 <td class="tdhand" width="100%" height="18">
			<s:if test="#request.paginator.currentPage > 1">	
			  <span style="cursor:hand;COLOR: #31659c">
			    </span></s:if>	
			    </td>
		 
		  <tr>	
			<td class="td"  align="center" width="100%" height="18">
			</td>
						
		</tr>		
	  </tbody>
	</table>
 		    <!-- ======================== Paginate End ========================= -->   
	
