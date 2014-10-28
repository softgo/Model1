<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.bruce.gogo.Constants"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>角色查询</title>
<script type="text/javascript" language="javascript">
      function del(id)
		{
		if(confirm('确实要删除此信息？')){
		if(id==1){			
			alert("您没有删除此记录的权限！！");
			return false;
			}else{
		$.ajax({
        type: "POST",
        url: "RolePermission!delete.<%=Constants.ActionExt%>",
        data: 'id=' + id,
        success: function(msg){
        	alert(msg);
        	pageBean.research_page();
            }
          }); 

		 }
		 }
	
		}		
		  function subm()
			{				
		   document.PermissionForm.action="Permission!list.<%=Constants.ActionExt%>";
		   pageBean.search_page();    		
			}
		$(document).ready(function(){
		$("#btnAdd").click(function(){//"新增"按钮单击事件
		window.location='RolePermission!add.<%=Constants.ActionExt%>';
		});
		$("#btnSearch").click(function(){ pageBean.search_page(); });//"查询"按钮单击事件	
		});				
</script>
</head>
<body>	
<s:form name="RolePermissionForm" method="post" theme="simple"  onsubmit="subm();">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" />权限管理 ＞ 角色管理 ＞ 角色列表</div>
 <!-- 中间内容 --->
    <div id="Users">
        <div id="UsersList"><div class="w936">
        	
	<div id="tb_" class="tb_">
			<ul>
				<li id="tb_1" class="hovertab" >搜索角色</li>
			</ul>
  </div>  
	<div class="ctt">
		<div class="dis" id="tbc_01">角色名：
		 <s:textfield label="权限名" name="modelname" value="%{#request.modelname}"/>		
        <span class="newpapers_parm_table_td_2">
        <input type="button" class="newpapers_parm_table_td_a4" name="btnSearch" id="btnSearch" value="查询"  />
        </span>
       
        </div>
        
	</div>
</div>
        </div>
        <div id="UsersListTop">
        <h2>角色列表</h2>
        <input type="button" class="newpapers_parm_table_td_a4" style="float:left;margin-top:0px;" name="btnAdd" id="btnAdd" value="新 增" />
      </div>
        <div id="UsersListTop_Content">     
        <table width=100% border="1" align=center  cellSpacing=0　cellPadding=2 bordercolor="#d1d1d1" style="border-collapse:collapse;">
        <tr>
          <td width="300" class="newpapers_parm_table_td_1">角色名称</td>                          
          <td width="140"  class="newpapers_parm_table_td_1">操作</td>
        </tr>
        
        <s:iterator value="#request.list">
        <tr onMouseOver="this.style.backgroundColor='#f9f9f9';return" onMouseOut="this.style.backgroundColor='#FFFFFF';return">

          <td class="newpapers_parm_table_td_2"><s:property value="modelname"/></td>          
         
         
          <td class="newpapers_parm_table_td_3"><a href='RolePermission!edit.<%=Constants.ActionExt%>?id=<s:property value="id"/>'>修改</a>&nbsp;
          <a href="#" onclick='del(<s:property value="id"/>)'>删除</a>
          </td>
        </tr>
        </s:iterator> 
        <tr>
          
          </tr>
      </table>
      </div>
       </div>
      <!-- 分页 -->
	  <jsp:include page="/WEB-INF/jsp/include/paginator.jsp" flush="true"/>				
	  <script language="javascript">
        var pageBean = new pageBean("RolePermission!list.<%=Constants.ActionExt%>", "RolePermissionForm", "<%=Constants.DEFAULT_PAGE_SIZE%>", document.RolePermissionForm,"<%=Constants.DEFAULT_PAGE_SIZE%>");
	  </script>
  
    
  </div>
</s:form>
</body>

</html>
