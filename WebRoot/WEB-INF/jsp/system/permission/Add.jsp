<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.bruce.gogo.Constants"%>


 <html>
<head>	
 <script type="text/javascript" language="javascript">

		  var strArr = new Array();
		
			
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
			if($("[name='ptype']").val()=='-1')
			{
				alert('所属分类不能为空!');
				return false;
			}
			
			var id = $("[name='id']").val();
			if(id=='')
			{
				id = '-1';
			}
			$.ajax({
					type: "POST",
					async:false,
		      url: "Permission!checkname.<%=Constants.ActionExt%>",
		      data: 'pename=' + $("[name='pename']").val() + '&id=' + id,
		      success: function(msg){
				strArr = msg.split('==');											
		        if(strArr[0]=='1')
		            {		        	
		        	alert(strArr[1]);
		        	 } else {	
		           $("#PermissionForm").attr("action","Permission!save.<%=Constants.ActionExt %>?");
	               $("#PermissionForm").submit();
		        	
		        }
		      }
			});
					
	}
		$(document).ready(function(){
			$("#btnCancel").click(function(){//"取消"按钮单击事件
				window.location='Permission!list.<%=Constants.ActionExt %>';
			});
			
			$("#btnSubmit").click(function(){ save();});//"提交"按钮单击事件
		});

</script>
	</head>
	<body>
		<s:form action="Permission!save" name="PermissionForm" id="PermissionForm"	method="post" theme="simple">
			<!-- 右侧容器 --->
			<div id="right_container">
				<!-- 面包线 --->
				<div id="breadCrumb">
					<img src="images/zjm4.gif" width="27" height="26" />系统设置 > 系统权限管理 > 权限新增
					
				</div>
				<!-- 中间内容 --->
				<div id="Users">
					<div id="UsersTop">
						<h2>
							添加权限
						</h2>
					</div>
					<div id="UsersContent">
						<table border="0px" style="font-size:12px" width="630px">
						
							<tr>
								<td width="53" align="left">
									名称：
								</td>
								
								<td width="311" align="left">
									<input name="pename" type="text" id="pename" size="35" />
									<s:hidden id="id" name="id" value=""></s:hidden>
								</td>
								<td>
									<div id="nameTip" style="width:150px"></div>
								</td>
							</tr>							  
											 
							<tr>
								<td align="left">
									是/否文件夹：
								</td>
								<td align="left">
									<input name="isfolder" type="checkbox" id="isfolder" size="35" />
								</td>
								<td>
									<div id="isfolderTip" style="width:150px"></div>
								</td>
							</tr>							
							<tr>
								<td align="left">
									父ID：
								</td>
								<td>
							       <div id="sort">
									<s:select headerKey="-1" headerValue="请选择所属父ID" list="perValues" name="parentid" id="parentid" listKey="key" listValue="value" ></s:select>

									</div>
								</td>			
							</tr>
							<tr>
								<td align="left">
									URL：
								</td>
								<td align="left">
									<input name="perlink" type="text" id="perlink" size="35" />
								</td>
								<td>
									<div id="linkTip" style="width:150px"></div>
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
										<option value="1">
											权限
										</option>
										<option value="2">
											功能菜单
										</option>
									</select>
								</td>								
								<td>
									<div id="ptypeTip" style="width:150px"></div>
								</td>
							</tr>
							<tr>
								<td align="left">
									序号：
								</td>
								<td align="left">
									<input name="ncom" type="text" id="ncom" size="35" onblur="if(!(/^[\d]+\.?\d*$/.test(this.value))){alert('您的输入有误');this.value='';return false;this.focus();}" />
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
									<s:textarea label="注释" name="content" />
								</td>
								<td>
									<div id="noteTip" style="width:150px"></div>
								</td>
							</tr>

							<tr>
								<td colspan="2" align="left">
									<div align="center">
										<input type="button" class="newpapers_parm_table_td_a4"
											name="btnSubmit" id="btnSubmit" value="提交" />
											<input type="reset" class="newpapers_parm_table_td_a4" name="btnCancel" id="btnCancel" value="取消" />
									</div>
								</td>
							</tr>
						</table>
						</div>
					</div>
				</div>
		  </s:form>
	

		<div id="Buttom"></div>
	</body>
</html>
