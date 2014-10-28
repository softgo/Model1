<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.bruce.gogo.Constants"%>


 <html>
<head>	
 <script type="text/javascript" language="javascript">

		  var strArr = new Array();
		
		function checkName()
		{
			var id = $("[name='id']").val();
			if(id=='')
			{
				id = '-1';
			}
			$.ajax({
					type: "POST",
		      url: "Users!checkname.<%=Constants.ActionExt%>",
		      data: 'loginname=' + $("[name='loginname']").val() + '&id=' + id,
		      success: function(msg){		     	    
						strArr = msg.split('==');											
		        if(strArr[0]=='1')
		        {		        	
		        	alert(strArr[1]);
		        	$("#btnSubmit").attr("disabled","true");
		        } else {
		        	$("#btnSubmit").removeAttr("disabled");
		        }
		      }
			});
		}	
	function save()
	{
	       if($("[name='loginname']").val()=='')
			{
				alert('登陆名不能为空!');
				return false;
			}else if($("[name='loginname']").val().length>100){
			    alert('登陆名不能超过100个字符!');
				return false;
			}

			if($("[name='username']").val()=='')
			{
				alert('用户姓名不能为空!');
				return false;
			}else if($("[name='username']").val().length>100){
			    alert('用户姓名不能超过100个字符!');
				return false;
			}
		   if($("[name='notes']").val()!=''&&$("[name='notes']").val().length>50)
			{
				alert('备注不能超过50个字符!');
				return false;
			}
			
			var id = $("[name='id']").val();
			if(id=='')
			{
				id = '-1';
			}
			$.ajax({
					type: "POST",
		      url: "Users!checkname.<%=Constants.ActionExt%>",
		      data: 'loginname=' + $("[name='loginname']").val() + '&id=' + id,
		      success: function(msg){		     	    
				strArr = msg.split('==');											
		        if(strArr[0]=='1')
		            {		        	
		        	alert(strArr[1]);
		        	 } else {		          
		           $("#UsersForm").attr("action","Users!save.<%=Constants.ActionExt %>?");
	               $("#UsersForm").submit();
		        	
		        }
		      }
			});
					
	}
		$(document).ready(function(){
			$("#btnCancel").click(function(){//"取消"按钮单击事件
			var usertype = $("[name='type']").val();
			  if(usertype=='usertype'){
		       window.location='Users!userlist.<%=Constants.ActionExt %>';
		       }else{
				window.location='Users!list.<%=Constants.ActionExt %>';
				}
			});
			
			$("#btnSubmit").click(function(){ save();});//"提交"按钮单击事件
		});

</script>
	</head>
	<body>
		<s:form action="Users!save" name="UsersForm" id="UsersForm"	method="post" theme="simple">
			<!-- 右侧容器 --->
			<div id="right_container">
				<!-- 面包线 --->
				<div id="breadCrumb">
					<img src="images/zjm4.gif" width="27" height="26" />权限管理 > 用户管理 > 用户编辑
					
				</div>
				<!-- 中间内容 --->
				<div id="Users">
					<div id="UsersTop">
						<h2>
							编辑用户
						</h2>
					</div>
					<div id="UsersContent">
						<table border="0px" style="font-size:12px" width="630px">
						
							<tr>
								<td width="53" align="left">
									登陆名：
								</td>
								<td width="311" align="left">
									<input name="loginname" type="text" id="loginname" size="35" value='<s:property value="#request.userinfo.loginname"/>' />
									<input name="id" type="hidden" id="id" size="35" value="<s:property value="#request.userinfo.id"/>"/>
									<input name="type" type="hidden" id="type" size="35" value="<s:property value="#request.type"/>"/>
								</td>
								<td>
									<div id="loginnameTip" style="width:150px"></div>
								</td>
							</tr> 
				
							<tr>
								<td align="left">
									用户姓名：
								</td>
								<td align="left">
									<input name="username" type="text" id="username" size="35" value='<s:property value="#request.userinfo.username"/>'/>
								</td>
								<td>
									<div id="userNameTip" style="width:150px"></div>
								</td>
							</tr>
						
					
							<tr>
								<td align="left">
									备注：
								</td>
								<td align="left">
								<textarea id="notes" name="notes" rows="10" cols="40"><s:property value="#request.userinfo.notes"/></textarea>
								</td>
								<td>
									<div id="notesTip" style="width:150px"></div>
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
