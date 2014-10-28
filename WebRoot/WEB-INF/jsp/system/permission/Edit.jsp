<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.bruce.gogo.system.model.*"%>
<%@ page import="com.bruce.gogo.Constants"%>

<%
	
	Stmkinfo perinfo = (Stmkinfo) request.getAttribute("Permissioninfo");
%>

<html>
	<head>
		<title></title>		
		<style type="text/css">
<!--
body {
	text-align: center;
}
-->
</style>
		<script type="text/javascript"> 
		
		function check()
		{		
         var id = $("input[@name='id']").val();         
         
         var pid= $("#parentid").val();     
          
         if(pid==id)
         {
         alert("不能选择本身做为父目录!请重新选择！");
         return false;
         }
         }
  function save()
	{
	       if($("[name='pename']").val()=='')
			{
				alert('权限名称不能为空!');
				return false;
			}else if($("[name='pename']").val().length>50){
			    alert('权限名不能超过50个字符!');
				return false;
			}
		
			if($("[name='parentid']").val()=='')
			{
				alert('所属父ID不能为空!');
				return false;
			}
			if($("[name='perlink']").val()=='')
			{
				alert('链接地址不能为空!');
				return false;
			}else if($("[name='perlink']").val().length>250){
			    alert('链接地址不能超过250个字符!');
				return false;
			}
			if($("[name='ncom']").val()=='')
			{
				alert('序号不能为空!');
				return false;
			}
	     var id = $("input[@name='id']").val();
         
         var pid= $("#parentid").val();       
         
         if(pid==id){
         
         alert("不能选择本身做为父目录!请重新选择！");
         
         return false;
         }
			if($("[name='ptype']").val()=='-1')
			{
				alert('所属分类不能为空!');
				return false;
			}
		$("#PermissionForm").attr("action","Permission!save.<%=Constants.ActionExt%>?");
	    $("#PermissionForm").submit();
					
	}
		$(document).ready(function(){
			$("#btnCancel").click(function(){//"取消"按钮单击事件
				window.location='Permission!list.<%=Constants.ActionExt%>';
			});
			
			$("#btnSubmit").click(function(){ save();});//"提交"按钮单击事件
		});

</script>
	</head>
	<body>
		<form  name="PermissionForm" id="PermissionForm" method="post" theme="simple">
			<!-- 右侧容器 --->
			<div id="right_container">
				<!-- 面包线 --->
				<div id="breadCrumb">
					<img src="images/zjm4.gif" width="27"
						height="26" />
					系统设置 >系统权限管理 > 权限修改
				</div>
				<!-- 中间内容 --->
				<div id="Users">
					<div id="UsersTop">
						<h2>
							编辑权限
						</h2>
					</div>
					<div id="UsersContent">
						<table border="0" cellSpacing=0　cellPadding=2
							bordercolor="#d1d1d1" style="border-collapse:collapse;">
							<tr>
								<input name="id" type="hidden" class="input398intxt" id="id"	value="<s:property value="#request.Permissioninfo.id"/>" />
								<td width="53" align="left">
									名称：
								</td>
								<td width="311" align="left">
									<input name="pename" type="text" id="pename" size="35"
										value="<s:property value="#request.Permissioninfo.pename"/>" />
								</td>
							</tr>					
							<tr>
								<td align="left">
									是/否文件夹：
								</td>
								<td align="left">
									<input name="isfolder" type="checkbox" id="isfolder" size="35"
										<%
	                                     if (perinfo.getIsfolder().toString().equals("Y")) {												
                                        %>
										checked <%} %> />
								</td>
							</tr>
							<tr>
								<td align="left">
									父ID：
								</td>
								<td align="left">
									<div id="sort">
									<s:select headerKey="-1" headerValue="请选择所属父ID" list="perValues" name="parentid" id="parentid" listKey="key" listValue="value" onchange="check();"></s:select>

									</div>
								</td>
							</tr>
							<tr>
								<td align="left">
									URL：
								</td>
								<td align="left">
									<input name="perlink" type="text" id="perlink" size="35"
										value="<s:property value="#request.Permissioninfo.perlink"/>" />
								</td>
							</tr>
							<tr>
								<td align="left">
									分类：
								</td>
								<td align="left">
									<select name="ptype">
										<option value="-1">
											－－请选择所属分类－－
										</option>
										<option value="1"  <s:if test="#request.Permissioninfo.ptype==1"> selected</s:if> >
											权限
										</option>
										<option value="2" <s:if test="#request.Permissioninfo.ptype==2"> selected</s:if> >
											功能菜单
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="left">
									序号：
								</td>
								<td align="left">
									<input name="ncom" type="text" id="ncom" size="35" value="<s:property value="#request.Permissioninfo.ncom"/>" onblur="if(!(/^[\d]+\.?\d*$/.test(this.value))){alert('您的输入有误');this.value='';return false;this.focus();}"/>
								</td>
								<td>
									<div id="numberip" style="width:150px"></div>
								</td>
							</tr>
							<tr>
								<td align="left">
									注释：
								</td>

								<td align="left">
									<s:textarea label="注释" name="content"
										value="%{#request.Permissioninfo.content}" />
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left">
									<div align="center">
										<input type="button" class="newpapers_parm_table_td_a4"
											name="btnSubmit" id="btnSubmit" value="提交" />
										<input type="reset" class="newpapers_parm_table_td_a4"
											name="btnCancel" id="btnCancel" value="取消" />
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				</div>
		</form>

		<div id="Buttom"></div>
	</body>
</html>
