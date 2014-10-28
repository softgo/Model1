<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div id="container">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" /><font size=5>欢迎您光临Model1系统！</font></div>
 <!-- 中间内容 --->
    <div id="Users">
      <table border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td width="156"><img src="images/zjm12.gif" /></td>
    <td width="550">欢迎您： <s:property value="#request.loginName"/>　 登陆时间：<s:property value="#request.loginTime"/> </td>
  </tr>
</table>
</div>
  </div>