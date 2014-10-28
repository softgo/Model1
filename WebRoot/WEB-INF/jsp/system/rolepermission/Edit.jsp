<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.bruce.gogo.system.model.*"%>
<%@ page import="com.bruce.gogo.Constants"%>
<%@ page import="java.util.*"%>
<% 

 String id=(String)request.getAttribute("id");

 Modleinfo roleinfo=(Modleinfo)request.getAttribute("roleinfo"); 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>角色编辑</title>

<script type="text/javascript" language="javascript">

    //进入页面自动运行
     window.onload   =function (){
		$.ajax({
			  type: "POST",
			  async:false,
		      url: "RolePermission!importeditselect.<%=Constants.ActionExt%>",
		      data: '&id='+<%=id%>,
		      success: function(msg){		    										 
					$("#permission").html(msg);					
		      }
			});     
      }	
     var strArr = new Array();		
		function save()
	    {    
	       if($("[name='name']").val()=='')
			{
				alert('权限名称不能为空!');
				return false;
			}else if($("[name='name']").val().length>50){
			    alert('权限名不能超过50个字符!');
				return false;
			}
			
			var flag = $("input[@name='chose'][@checked]").val();
			if(!flag)
			{
				alert('权限不能为空!');
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
		      url: "RolePermission!checkname.<%=Constants.ActionExt%>",
		      data: 'name=' + $("[name='name']").val() + '&id=' + id,
		      success: function(msg){		     	    
						strArr = msg.split('==');											
		        if(strArr[0]=='1')
		        {		        	
		        	alert(strArr[1]);
		        	
		        } else {
		       $("#RolePermissionForm").attr("action","RolePermission!save.<%=Constants.ActionExt %>?");
	           $("#RolePermissionForm").submit();
		        }
		      }
			});	
			
			
	  }

		$(document).ready(function(){
			$("#btnCancel").click(function(){//"取消"按钮单击事件
				window.location='RolePermission!list.<%=Constants.ActionExt %>';
			});
			
			$("#btnSubmit").click(function(){ save();});//"提交"按钮单击事件
		});	
		function choice(choname){
		
			 if($("[id='chose_"+choname+"']").attr("checked")==true)
            {
            
            $("input[@id^='chose_"+choname+"_']").attr("checked",true);//全选            
            $("input[@id^='chose_"+choname+"_']").attr("disabled",false);
          
            }else 
            {
             $("input[@id^='chose_"+choname+"_']").attr("checked",false);//取消全选
             $("input[@id^='chose_"+choname+"_']").attr("disabled",true);
            }
		}		
		function dis(choname)   
      {    
          if($("[id='chose_"+choname+"']").attr("checked")==true)
            {
            
            $("input[@id^='chose_"+choname+"_']").attr("checked",true);//全选
            $("input[@id^='chose_"+choname+"_']").attr("disabled",false);      
            
            
            }else 
            {
             $("input[@id^='chose_"+choname+"_']").attr("checked",false);//取消全选
             $("input[@id^='chose_"+choname+"_']").attr("disabled",true);
             
            }

     }   
		
</script>
<style type="text/css">
.div{ border:1px #06F solid;height:50px;width:100px;display:none;}
a { display:block}
</style>

</head>
<body>	

  <s:form action="RolePermission!save" name="RolePermissionForm" id="RolePermissionForm" method="post" theme="simple">
<!-- 右侧容器 --->
  <div id="right_container">
  <!-- 面包线 --->
    <div id="breadCrumb"><img src="images/zjm4.gif" width="27" height="26" />权限管理 ＞ 角色管理 ＞ 编辑角色</div>
 <!-- 中间内容 --->
    <div id="Users">
		<div id="UsersListTop">
        <h2>角色编辑页</h2><span style="color:#009900;margin-left:30px;">角色必须是选择了上级才能选择其下级的功能角色，否则是不能直接选择下级角色的</span>
      </div>
       <div id="UsersListTop_Content">
		   <table width=100% style="border:1px solid #d1d1d1;border-collapse:collapse;text-align:center;">
			<tr>
			  <td style="border:none;text-indent:60px;" align="left">角色名称：<input type="text" name="name" value="<%=roleinfo.getModelname() %>" id="name" /></td>
			</tr>
			
			<tr>
			  <td style="border:none;text-indent:60px;" align="left">选择角色：</td>
			</tr>
			<tr>                       
                     
				<td	colspan="2" style="border:none;" align="center">
				<input name="id" type="hidden" id="id" size="35" value="<s:property value="#request.id"/>"/>

					<div class="menuwrap" id="permission">
						
					</div>
					<p	align="center"><input type="button" class="newpapers_parm_table_td_a4" name="btnSubmit" value="提交" id="btnSubmit" /> <input type="reset" class="newpapers_parm_table_td_a4" name="btnCancel" value="取消" id="btnCancel"/></p>
				</td>
			</tr>
			 
		  </table class="menucont">
      </div>
	  <script type="text/javascript">
	  <!--
		var lastIndex = -1;
		function showMenu(menuIndex)
		{
		
<%--		   if($("[id='chose_"+menuIndex+"']").attr("checked")==true)--%>
<%--            {--%>
<%--            --%>
<%--            $("input[@id^='chose_"+menuIndex+"_']").attr("checked",true);//全选--%>
<%--            $("input[@id^='chose_"+menuIndex+"_']").attr("disabled",false);      --%>
<%--            --%>
<%--            --%>
<%--            }else --%>
<%--            {--%>
<%--             $("input[@id^='chose_"+menuIndex+"_']").attr("checked",false);//取消全选--%>
<%--             $("input[@id^='chose_"+menuIndex+"_']").attr("disabled",true);--%>
<%--             --%>
<%--            }--%>
			if(menuIndex != lastIndex)
			{
				document.getElementById('menucont'+menuIndex).style.display = 'block';
				lastIndex >= 0?document.getElementById('menucont'+lastIndex).style.display = 'none':void(0);
				
			}
			if(lastIndex==menuIndex)
			{
				document.getElementById('menucont'+menuIndex).style.display=='block'?document.getElementById('menucont'+menuIndex).style.display='none':document.getElementById('menucont'+menuIndex).style.display='block';
			}
			lastIndex = menuIndex;
			
		}
	  //-->
	  </script>
    </div>
  </div>
</body>
</s:form>
   
</html>
