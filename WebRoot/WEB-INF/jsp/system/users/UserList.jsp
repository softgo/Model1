<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page import="com.bruce.gogo.Constants"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户查询</title>
<script type="text/javascript" src="/js/list.js"></script>
<script type="text/javascript" language="javascript">
     	  function subm()
			{				
		   document.PermissionForm.action="Users!list.<%=Constants.ActionExt%>";
		   pageBean.search_page();    		
			}
				
</script>
</head>
<body>	
<s:form  name="UsersForm" method="post" theme="simple"  onsubmit="subm();">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" />权限管理 ＞ 用户管理 ＞ 用户列表</div>
 <!-- 中间内容 --->
    <div id="Users">
        <div id="UsersList"><div class="w936">

</div>
        </div>
        <div id="UsersListTop">
        <h2>用户列表</h2>
      </div>
        <div id="UsersListTop_Content">     
        <table width=100% border="1" align=center  cellSpacing=0　cellPadding=2 bordercolor="#d1d1d1" style="border-collapse:collapse;">
        <tr>
          <td width="150" class="newpapers_parm_table_td_1">用户姓名</td>   
          <td width="150" class="newpapers_parm_table_td_1">登陆名</td> 
          <td width="150" class="newpapers_parm_table_td_1">角色</td>      
          <td width="300" class="newpapers_parm_table_td_1">备注</td>                   
          <td width="400"  class="newpapers_parm_table_td_1">操作</td>
        </tr>
        
        <s:iterator value="#request.list" id="list" status="st">
        <tr onMouseOver="this.style.backgroundColor='#f9f9f9';return" onMouseOut="this.style.backgroundColor='#FFFFFF';return">

          <td class="newpapers_parm_table_td_2"><s:property value="username"/></td>          
          <td class="newpapers_parm_table_td_2"><s:property value="loginname"/></td>  
          <td class="newpapers_parm_table_td_2"><s:property value="#request.rolemap.get(#st.count-1)"/></td>  
          <td class="newpapers_parm_table_td_2"><s:property value="notes"/></td>  
            
         
          <td class="newpapers_parm_table_td_3">
          <s:if test="#list.id!=1"> 
          <a href='Users!edit.<%=Constants.ActionExt%>?id=<s:property value="id"/>&type=usertype'>修改</a>&nbsp;
          <a href='UserPassword!enter.<%=Constants.ActionExt%>?id=<s:property value="id"/>&type=usertype'>修改密码</a>&nbsp;
         
          </s:if>
          
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
        var pageBean = new pageBean("Users!list.<%=Constants.ActionExt%>", "UsersForm", "<%=Constants.DEFAULT_PAGE_SIZE%>", document.UsersForm,"<%=Constants.DEFAULT_PAGE_SIZE%>");
	  </script>
  
    
  </div>
</s:form>
</body>

</html>
