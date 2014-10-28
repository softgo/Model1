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
		
		function confirmpassword(){
	        if($("#password").attr("value") != $("#confirmpassword").attr("value")){
		      return "两次输入的密码不一致,请重新输入";
            }
	     return true;
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
			
			if($("[name='password']").val()=='')
			{
				alert('密码不能为空!');
				return false;
			}else if($("[name='confirmpassword']").val()=='')
			{
				alert('请再次输入密码!');
				return false;
			}else if($("[name='confirmpassword']").val()!=$("[name='password']").val()){
			
			    alert('两次输入的密码不一致!');
				return false;
			}else if(/^\w{4,16}$/g.test($("[name='password']").val()) == false){
			 alert("密码只允许数字，字母，下划线，4-16位，不区分大小写");
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
					<img src="images/zjm4.gif" width="27" height="26" />权限管理 > 用户管理 > 用户新增
					
				</div>
				<!-- 中间内容 --->
				<div id="Users">
					<div id="UsersTop">
						<h2>
							添加用户
						</h2>
					</div>
					<div id="UsersContent">
						<table border="0px" style="font-size:12px" width="630px">
						
							<tr>
								<td width="53" align="left">
									登陆名：
								</td>
								<td width="311" align="left">
									<input name="loginname" type="text" id="loginname" size="35" />
									<s:hidden id="id" name="id" value=""></s:hidden>
									<input name="type" type="hidden" id="type" size="35"/>
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
									<input name="username" type="text" id="username" size="35" />
								</td>
								<td>
									<div id="usernameTip" style="width:150px"></div>
								</td>
							</tr>
						
							<tr>
								<td align="left">
									密码：
								</td>
								<td align="left">
									<s:password name="password" id="password" value=""/>
								</td>
								<td>
									<div id="passwordberip" style="width:150px"></div>
								</td>
							</tr>
							<tr>
								<td align="left">
									重新输入密码：
								</td>
								<td align="left">
									<s:password name="confirmpassword" id="confirmpassword" value=""/>
								</td>
								<td>
									<div id="confirmpasswordberip" style="width:150px"></div>
								</td>
							</tr>
							<tr>
								<td align="left">
									备注：
								</td>
								<td align="left">
									<textarea id="notes" name="notes" rows="10" cols="40"></textarea>
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
