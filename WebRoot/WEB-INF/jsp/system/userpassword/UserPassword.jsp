<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.bruce.gogo.Constants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>密码管理</title>
    <script type="text/javascript" language="javascript">
       function save(){
          
	        if($("[name='oldpassword']").val()=='')
			{
				alert('原密码不能为空!');
				return false;
			}else if($("[name='newpassword']").val()=='')
			{
				alert('新密码不能为空!');
				return false;
			}else if($("[name='confirmnewpassword']").val()=='')
			{
				alert('请再次输入密码!');
				return false;
			}else if($("[name='confirmnewpassword']").val()!=$("[name='newpassword']").val()){
			
			    alert('两次输入的密码不一致!');
				return false;
			}else if(/^\w{4,16}$/g.test($("[name='newpassword']").val()) == false){
			 alert("只允许数字，字母，下划线，4-16位，不区分大小写");
	         return false;
			}
	         
	         
	         $.ajax({
			 type: "POST",
		      url: "UserPassword!check.<%=Constants.ActionExt%>",
		      data: 'id=' + $("[name='id']").val()+'&oldpassword='+ $("[name='oldpassword']").val(),
		      success: function(msg){	
		          	    
				strArr = msg.split('==');	
									
		        if(strArr[0]=='0')
		            {	
		            alert("修改成功!");
		            $("#UsersForm").attr("action","UserPassword!save.<%=Constants.ActionExt %>?");
	                $("#UsersForm").submit();	        	
		              
		        	 } else {		          
		         
		             alert(strArr[1]);
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

<s:form action="UserPassword!edit" name="UsersForm" id="UsersForm" method="post" theme="simple">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" />权限管理 ＞ 用户管理 ＞ 修改密码</div>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="28" background="images/tou_07.gif" style="padding-left:18px; padding-bottom:10px;"></td>
      </tr>
      
    </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" class="lin1">
        <tr>
          <td><table width="720" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
            <table width="720" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#666666">
            <tr>
            <input name="id" type="hidden" id="id" size="35" value="<s:property value="#request.id"/>"/>
              <td width="125" height="30" align="right" bgcolor="#FFFFFF"><font color="#ff0000">*</font>原 密 码：</td>
              <td width="592" height="25" bgcolor="#FFFFFF" style="padding-left:18px;">
              <input type="password" name="oldpassword" id="oldpassword" style="width:200px;"/></td>
              <input name="type" type="hidden" id="type" size="35" value="<s:property value="#request.type"/>"/>
              </tr>
            <tr>
              <td width="125" height="30" align="right" bgcolor="#FFFFFF"><font color="#ff0000">*</font>新 密 码：</td>
              <td height="25" bgcolor="#FFFFFF" style="padding-left:18px;">
              <input type="password" name="newpassword" id="newpassword" style="width:200px;"/>
             
              </td>
              </tr>
            
            
            
            <tr>
            <td height="30" align="right" bgcolor="#FFFFFF"><font color="#ff0000">*</font>确认密码：</td>
            <td height="30" bgcolor="#FFFFFF" style="padding-left:18px;">
            <input type="password" name="confirmnewpassword" id="confirmnewpassword" style="width:200px;"/>
              请保持重复输入，内容与新密码一致</td>
            </tr>
          </table>
            <table width="720" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td><table width="200" height="40" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td align="center"><input type="button" name="btnSubmit" id="btnSubmit" value="提交" style="width:80px;" /></td>
                      <td align="center"><input type="button" name="btnCancel" id="btnCancel" value="取消" style="width:80px;" /></td>
                    </tr>
                </table></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
    </div>
      
</s:form>
</body>
</html>