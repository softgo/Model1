<%@ page contentType="text/html; charset=UTF-8" language="java" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>  
<%@ page import="com.bruce.gogo.utils.PageBean" %>

<script language="javascript">
function pageBean(url, formName, pageSize, zheForm, navigationItemCount)
{	
	zheForm.pageSize.value = pageSize;
	zheForm.navigationItemCount.value = navigationItemCount;
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
	
	this.goto_page = function (pageNo,pageCount)
	{
		
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
		document.getElementById("pageNo").value = pageNo;
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
	this.search_page_new = function ()
	{
		zheForm.actType.value = "first_page";
		zheForm.submit();
	}
	
}  
</script> 

<% 
	PageBean pageBean = (PageBean)request.getAttribute("paginator");
%>

<!-- ======================== Paginate ========================= -->      	  
<div id="UsersList_buttom"> 
	<div style="text-align: center;" class="lookPagejump">
		<table class="pages" cellspacing="0">
			<tbody>
				<tr>
					<td>第<s:property value="#request.paginator.currentPage" />页/共<s:property value="#request.paginator.pageCount" />页
				  		<s:hidden name="paginator.cacheKey" value="%{#request.paginator.cacheKey}"/>
		          <s:hidden name="paginator.currentPage" />
		          <s:hidden name="listSQL" value="%{#request.paginator.listSQL}" />
		          <s:hidden name="actionType" value="%{#request.paginator.actionType}" />
		          <s:hidden name="actType" value="%{#request.actType}" />
		          <s:hidden name="pageSize" value="%{#request.paginator.pageSize}" />
		          <s:hidden name="navigationItemCount" value="%{#request.paginator.navigationItemCount}" />
		          <s:hidden name="pageNo" value="1" />
          </td>
        </tr>
      </tbody>
    </table>
                             <ul class="pageno">
                         <s:if test="#request.paginator.pageCote > 0">
                             <li><a href="#"  onclick="javascript:pageBean.goto_page('1','<%=pageBean.pageCount %>')">|&lt;</a></li>
                         </s:if>
                         <s:if test="#request.paginator.currentPage != 1">
                             <li><a href="#" onclick="javacript:pageBean.yest_page()">&lt;</a></li>
                         </s:if>
                         <%
                         while(pageBean.pageStart <= pageBean.pageEnd){ 
                             if(pageBean.pageStart == pageBean.currentPage){
                         %>
                                 <li class="current"><%=pageBean.pageStart %></li>
                         <%
                             }else{
                         %>
                                 <li><a href="#"  onclick="javascript:pageBean.goto_page('<%=pageBean.pageStart %>','<%=pageBean.pageCount %>')"><%=pageBean.pageStart %></a></li>
                         <%
                             }
                             pageBean.pageStart++;
                         }
                         %>
                         <s:if test="#request.paginator.currentPage < #request.paginator.pageCount">
                             <li><a href="#" onclick="javacript:pageBean.next_page();">&gt;</a></li>
                         </s:if>
                         <s:if test="#request.paginator.pageCote < #request.paginator.pageCoteCount - 1">
                             <li><a href="#"  onclick="javascript:pageBean.goto_page('<%=pageBean.pageCount %>','<%=pageBean.pageCount %>')">&gt;|</a></li>
                         </s:if>
                             </ul>
  </div>
</div>
 		    <!-- ======================== Paginate End ========================= -->   