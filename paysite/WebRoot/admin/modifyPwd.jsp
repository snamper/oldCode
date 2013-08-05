<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.agents.pojo.CoAgent"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String username = (String)session.getAttribute("username");
if(username == null){  
	//response.sendRedirect("agentPage.do?method=findAgentByDomain");
	out.println("<script>top.location='agentPage.do?method=findAgentByDomain';</script>");
	return;    
} 
//String[] currentClient = (String[])session.getAttribute("currentClient");
%>

<html xmlns="http://www.w3.org/1999/xhtml">   
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css"></link>
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/scrollings.js"></script>
<script type="text/javascript" src="images/js/showDateTime.js"></script>
<script>
window.onload=function(){
	getCurDate("curDate");
	getCurTime("curTime");
	setInterval("getCurTime('curTime')",100);
}
function checkpwd(){
   var oldpwd=document.getElementById("oldPassword").value;
   var newpwd=document.getElementById("newPassword").value;
   var newpwd2=document.getElementById("newPassword2").value;
   if(oldpwd=="" || oldpwd==null){alert("旧密码不能为空，请重新输入！"); document.getElementById("oldPassword").focus(); return false;}
   if(newpwd=="" || newpwd==null){alert("新密码不能为空，请重新输入！"); document.getElementById("newPassword").focus(); return false;}
   if(newpwd2=="" || newpwd2==null){alert("密码不能为空，请重新输入！"); return false;}
   if(newpwd!=newpwd2){alert("两次输入密码不一致，请重新输入！"); document.getElementById("newPassword").focus(); return false;}
   
   document.form1.submit();
}
</script>  
</head>
<body>
<!--标题开始-->
<div id="title">
  <div id="title_right">
    <h4 id="curDate"></h4>
    <h5 id="curTime"></h5> 
  </div>  
   <div id="title_left"></div>
</div>
<!--标题结束-->  
  <div id="subtitle">&nbsp;&nbsp;&nbsp;修改密码</div>
</div>
<!--表单开始-->
<div id="search_list_content">
<form action="<%=basePath %>client.do?method=updatePwd" method="post" name="form1">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   
    <tr >  
      <td height="30" width="150" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;旧密码: </td>
      <td align="center" class="bk02" width="200"><input name="oldPassword" id="oldPassword" type="password" class="txt_05" size="25" value=""/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
    
    <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;请输入新密码: </td>
      <td align="center" class="bk02"><input name="newPassword" id="newPassword" type="password" class="txt_05" size="25" value=""/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
    <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;再次输入新密码: </td>
      <td align="center" class="bk02"><input name="newPassword2" id="newPassword2" type="password" class="txt_05" size="25" value=""/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
 

  
   
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8">&nbsp;&nbsp;&nbsp;&nbsp;<input name="Submit2422" type="button" class="button_09" onclick="checkpwd();" value="修改" />
      </td>
      <td align="right">&nbsp;</td>
    </tr>
    <tr>
      <td height="10" colspan="2"></td>  
    </tr>   
  </table>
  </form>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>
<script type="text/javascript">
<%
String resultInfo = request.getParameter("Message");
if(resultInfo!=null){  
	resultInfo=new String(resultInfo.getBytes("iso8859-1"), "utf-8");   
}
if(!"".equals(resultInfo) && resultInfo != null){
	if("chenggong".equals(resultInfo)){
		resultInfo="修改成功！";
	}
	else if("shibai".equals(resultInfo)){
		resultInfo="修改失败！";
	}
	else if("oldpwdshibai".equals(resultInfo)){
		resultInfo="旧密码输入错误！";
	}
%>
alert('<%=resultInfo %>'); 
<%}%>
</script>

