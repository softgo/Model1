<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.bruce.gogo.system.model.*"%>
<%@ page import="com.bruce.gogo.Constants"%>
<%@ page import="java.util.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>权限查询</title>
<script type="text/javascript" src="js/list.js"></script>
<script src="js/jquery_last.js" type="text/javascript"></script>
<script src="js/formValidator_min.js" type="text/javascript"></script>
<script src="js/formValidatorRegex.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
function del(id)
		{
		if(confirm('确实要删除此信息？')){
		$.ajax({
        type: "POST",
        url: "Permission!delete.<%=Constants.ActionExt%>",
        data: 'id=' + id,
        success: function(msg){
        	alert(msg);
        	pageBean.research_page();
         }
      }); 

	 }
	
		}		
		$(document).ready(function(){
		$("#btnAdd").click(function(){//"新增"按钮单击事件
						window.location='Permission!add.<%=Constants.ActionExt%>';
					});
		$("#btnSearch").click(function(){ pageBean.search_page(); });//"查询"按钮单击事件	
		});			

		function subm()
			{				
		   document.PermissionForm.action="Permission!list.<%=Constants.ActionExt%>";
		   pageBean.search_page();    		
			}
</script>
</head>
<body>	
<s:form action='Permission!list.<%=Constants.ActionExt%>' name="PermissionForm" id="PermissionForm" method="post" theme="simple" onsubmit="subm();">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" />系统设置 >系统权限管理 > 权限列表</div>
 <!-- 中间内容 --->
    <div id="Users">
        <div id="UsersList"><div class="w936">
       
	<div id="tb_" class="tb_">
			<ul>
				<li id="tb_1" class="hovertab" >搜索权限</li>
			</ul>
  </div>
	<div class="ctt">
		<div class="dis" id="tbc_01">权限名：
		 <s:textfield label="权限名" name="keyword" value="%{#request.keyword}"/>
		 
        <span class="newpapers_parm_table_td_2">        
        <input type="button" class="newpapers_parm_table_td_a4" name="btnSearch" id="btnSearch" value="查询" />
        </span></div>
	</div>
</div>
        </div>
        <div id="UsersListTop">
        <h2>权限列表</h2>
         <input type="button" class="newpapers_parm_table_td_a4"  style="float:left;margin-top:0px;margin-left:10px;" name="btnAdd" id="btnAdd" value="新增" />
      </div>
        <div id="UsersListTop_Content">     
        <table width=100% border="1" align=center  cellSpacing=0　cellPadding=2 bordercolor="#d1d1d1" style="border-collapse:collapse;">
        <tr>
          <td width="113" class="newpapers_parm_table_td_1">名称</td>
          <td width="143" class="newpapers_parm_table_td_1">语言键</td>   
          
          <td width="103" class="newpapers_parm_table_td_1">父ID</td>         
          <td width="89" class="newpapers_parm_table_td_1">分类</td>          
          <td width="89" class="newpapers_parm_table_td_1">操作</td>
        </tr>
       
        <%Map parentidmap = (Map)request.getAttribute("parentidmap"); %>
        
        <%List list = (List)request.getAttribute("list"); %>
        <%int i = 0; %>
        <%int k = 0; %>
        <%int j= 0; %>
        <s:iterator value="#request.list" id="list">
        <tr onMouseOver="this.style.backgroundColor='#f9f9f9';return" onMouseOut="this.style.backgroundColor='#FFFFFF';return">

          <td class="newpapers_parm_table_td_2"><s:property value="pename"/></td>          
          
       <td class="newpapers_parm_table_td_2"><s:property value="kname"/></td>   
           
          <td class="newpapers_parm_table_td_2">          
          <%Stmkinfo permiss =(Stmkinfo)list.get(k);      
            if(permiss.getParentid()==-1){      
          %>
          根目录
          <%} else{%>
          <%=parentidmap.get(permiss.getParentid()) %>
          <%} %>
          <%k++; %>          
          </td>           
            <td class="newpapers_parm_table_td_2">
             <s:if test="#list.ptype==1"> 权限</s:if>
             <s:elseif test="#list.ptype==2">功能菜单</s:elseif>           
            </td>   
          <td class="newpapers_parm_table_td_3"><a href='Permission!edit.<%=Constants.ActionExt%>?id=<s:property value="id"/>'>修改</a>&nbsp;
<%--          <a href="#" onclick="del('<s:property value="id"/>')">删除</a>--%>
          </td>
        </tr>
        </s:iterator> 
        <tr>
          <td colspan="6" class="newpapers_parm_table_td_2">
            
          </td>
          </tr>
      </table>
      </div>
       </div>
      <!-- 分页 -->
    
	  <jsp:include page="/WEB-INF/jsp/include/paginator.jsp" flush="true"/>				
	  <script language="javascript">
         var pageBean = new pageBean("Permission!list.<%=Constants.ActionExt%>", "PermissionForm", "<%=Constants.DEFAULT_PAGE_SIZE%>", document.PermissionForm,"<%=Constants.DEFAULT_PAGE_SIZE%>");
	  </script>

  
    
  </div>
</s:form>
</body>

</html>
