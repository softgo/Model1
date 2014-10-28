<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.bruce.gogo.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>登录页</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript">   
            function changeImage() {       
                document.getElementById('kaptchaImage').src = 'checkcode?captchaId=' + Math.random();
            }   
</script>
</head>
<body onload="document.forms[0].elements[0].focus()">
<!-- 页头 --->
<div id="header">
  <div id="subNav">
  <div id="subNav_nav"></div>
  
  </div>
 </div>
  <!-- 主题容器 --->  
 <div id="banner">
   <div id="banner1">
    
      <div id="banner_midde"> </div>
<form name="login" action="Login!login.<%=Constants.ActionExt%>" method="post">


      <div id="banner_right"> 
      <table border="0" align=center  cellSpacing=0　cellPadding=2 bordercolor="#d1d1d1" style="border-collapse:collapse;">
      <tr> <span class="text2">
	              	<font size=5>
	              <strong>管理后台登陆
	              </strong></font>
	            　</span>
	            </tr>
  <tr>
    <td width="57" align="left">用户名：</td>
    <td colspan="3" align="left"><input name="username" type="text" id="username" value="<s:property value="#request.username"/>" size="25" /></td>
    </tr>
  <tr>
    <td align="left">密码：</td>
    <td colspan="3" align="left"><input name="password" type="password" id="password" size="25" /></td>
    </tr>
  
  
  <tr>
    <td align="left">&nbsp;</td>
    <td colspan="2" align="left"><input type=image src="images/aniu.jpg"/></td>
   
    </tr>
</table>
</div>
</form> 
 
   </div>
 </div>
 
</body>
</html>
