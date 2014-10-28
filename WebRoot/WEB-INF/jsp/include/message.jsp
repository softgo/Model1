

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	
	//audit 审核失败重新审核时用，更新数据
	String audit = request.getAttribute("audit")==null?"":request.getAttribute("audit").toString();
	String path =  request.getAttribute("path")== null?"":request.getAttribute("path").toString();
	String testtype =  request.getAttribute("testtype")== null?"":request.getAttribute("testtype").toString();
	String message =  request.getAttribute("message")== null?"":request.getAttribute("message").toString();
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>权限查询</title>

<style type="text/css">
<!--
body {
scrollbar-face-color:#4791C5;
scrollbar-highlight-color:#ffffff;
scrollbar-3dlight-color:#ffffff;
scrollbar-darkshadow-color:#ffffff;
scrollbar-shadow-color:#ffffff;
scrollbar-arrow-color:#4791C5;
scrollbar-track-color:#ffffff;
	margin-top: 0px;
	margin-bottom: 0px;
	font-family: "Verdana", "Arial", "Helvetica", "sans-serif";
	font-size: 11px;
	line-height: 180%;
}
.style1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.style2 {font-family: Verdana, Arial, Helvetica, sans-serif}
.style3 {color: #FF0000}
-->
</style>
<SCRIPT LANGUAGE=javascript>

var position = 0;
var timer;
function scroller(){
position=document.body.scrollTop+1;
scroll(0,position);
timer=setTimeout("scroller()",10);
}
function scrollit(){
scroller();
}
function stopscroll(){
clearTimeout(timer);
}
document.ondblclick=scrollit;
document.onclick=stopscroll;

function ret(path){
	document.form1.action=path;
	document.form1.submit();
}
</SCRIPT>

</head>
<body style="border:0 px ">
<!-- 主题容器 --->
<div id="container">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"></div>
 <!-- 中间内容 --->
  <div id="Users">
  <form name="form1" method="post" action="">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="100%" align="center" valign="middle">
           <%
                           if(testtype.equals("testtype")){
                           %>
                           	<textarea name="buffstr" id="buffstr" cols="120" rows="35" readonly><s:property value="#request.message"/></textarea>
                        
                           <%}else {%>
		     <table width="300" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="80B4DB">
              <tr>
                <td width="100%" height="21" background="/images/tableheadbg.gif" align="center" class="style1">信息提示</td>
             </tr>
              <tr>
                <td height="21" align="center" bgcolor="#FFFFFF" class="style1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="160" align="center"><table width="65%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td height="60" align="center">
                          <%
                           if(audit.equals("audit")){
                           %>
                          <img src="<%=request.getContextPath() %>/images/no.gif" width="43" height="43"/>
                          <%}else{ %>
                          <img src="<%=request.getContextPath() %>/images/yes.gif" width="43" height="43"/>
                          <%} %>
                          </td>
                        </tr>
                        <tr>
                          <td height="30" id="msg" align="center"><%=message%></td>
                        </tr>
                    </table></td>
                   
                  </tr>
                  <tr></tr>
                </table></td>
                
              </tr>
               <%} %>
              <tr>
                <td height="45" align="center" bgcolor="#f6f6f6" class="style1">
                   <% if(path.equals("")){ %>
                    <input name="Submit2" type="button" value="返 回" onclick="history.go(-1)"/>
                  <% }else{ %>
                <input name="Submit1" type="button" value="确定" onclick="ret('<%=request.getAttribute("path") %>')"/>
                 <input name="Submit2" type="button" value="返 回" onclick="history.go(-1)"/>
                   <%} %>
                  &nbsp;
               </td>
              </tr>
            </table>
         
            
             
     </table>
     </form>
      </div>
             </div>

</body>
</html>
