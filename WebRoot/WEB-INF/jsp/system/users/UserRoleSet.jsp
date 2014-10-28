<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.bruce.gogo.Constants"%>
<head>
<title>权限管理</title>
<script type="text/javascript" language="javascript">

    function closes()
    {
	window.location.href="Users!list.<%=Constants.ActionExt%>";
    }
	function save()
	   {	   	   
	      if(!$("input[@type=radio][name='roleid'][@checked]").val())
			  { 
			
				alert('角色不能为空!');
				return false;
			 }		

	    $("#userinfoForm").attr("action","Users!saverole.<%=Constants.ActionExt %>");
	    $("#userinfoForm").submit();
					
					
	}
     $(document).ready(function(){
     $("#btnSubmit").click(function(){ save();});//"提交"按钮单击事件
     });

</script>
</head>
 <body>
 <s:form action="Users!saverole" name="userinfoForm" id="userinfoForm" theme="simple">
 
 <!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" />权限管理 >  用户管理 > 分配角色</div>
     <div id="Users">
        <div id="UsersTop">
        <h2> 分配</h2>
        </div>
         <div id="UsersContent"> 
 <s:actionmessage theme="agent0"/>
 <table border="0"  cellSpacing=0　cellPadding=2 bordercolor="#d1d1d1" style="border-collapse:collapse;">
     <tr>
    <td width="120" align="right">用户姓名：</td>
    <td width="311" align="left"><s:property value='#request.userinfo.username'/></td>
    <input type="hidden" name="id" id="id" value="<s:property value='#request.userinfo.id'/>"/>
    </tr>
    <tr>
    <td width="120" align="right">登陆名：</td>
    <td width="311" align="left"><s:property value='#request.userinfo.loginname'/></td>
    </tr>
  
    <tr>
    <td width="120" align="right" valign="top">角色：</td>
    <td align="left">
    <table width="100%" border="0" cellpadding="3" cellspacing="0">
    <s:radio list="roleValues" listKey="key" listValue="value" name="roleid" id="roleid" theme="agent0" value="#request.list"></s:radio>
    <!-- 
     <s:checkboxlist list="roleValues" listKey="key" listValue="value" name="roleid" id="roleid" theme="agent1" value="#request.list"></s:checkboxlist>
     -->
    
    </table>
    </td>
    </tr>
  <tr>
    <td colspan="2" align="left"><div align="center">
      <input type="button" class="newpapers_parm_table_td_a4" name="btnSubmit" value="提 交" id="btnSubmit" />
      <input type="button" class="newpapers_parm_table_td_a4" name="button4" id="button4" value="取消" onclick="closes();"/>
    </div></td>
    </tr>
</table> 
         </div>	
    </div>
    </div>
    </s:form>
</body>
 </html>