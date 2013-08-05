<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.agents.pojo.CoAgent"%>
<%@page import="java.io.File"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
if(agent == null){     
	response.sendRedirect("agentPage.do?method=findAgentByDomain");
	return;
}    
String fid=agent.getFid();
String fname=agent.getFname();
String styleConfig=agent.getfStyleConfig();
//String uri = request.getServletPath();
//String fmasterCSS=agent.getFmasterCSS();
//String fleftCSS=agent.getFleftCSS();
//String ftopCSS=agent.getFtopCSS();
//String fphotoName=agent.getFphotoName();  
//String fjsName=agent.getFjsName();
//String fcontentCSS=agent.getFcontentCSS();
Object obj=session.getAttribute("username");
%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=fname %></title>
<link href="images_<%=fid %>/style/global/master.css" rel="stylesheet" type="text/css" />
<link href="images_<%=fid %>/style/top/top.css" rel="stylesheet" type="text/css" />
<link href="images_<%=fid %>/style/left/left.css" rel="stylesheet" type="text/css" />
<link href="images_<%=fid %>/style/content/content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images_<%=fid %>/js/js/date.js"></script>
<script type="text/javascript" src="images_<%=fid %>/js/js/scrollings.js"></script>
</head>

<body class="main_body" >   
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" class="main_top">
      <!--头部开始-->
      <iframe src="top.jsp" name="iframepage" frameBorder="0" scrolling=no  width="100%"  onload="Javascript:SetWinHeight(this)"> </iframe>
      <!--头部结束--></td>
  </tr>
  <tr>
  
    <td valign="top" class="main_left">
      <iframe src ="left.jsp" frameborder="0"  scrolling="no"   id="left_menu" name="left_menu"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
      <!--左侧结束-->
    </td>   
    
    <td valign="top" class="main_content">
      <!--内容开始-->
     <iframe src ="main.jsp" id="main" name="main" frameborder="0"   width="100%" scrolling=auto align=right onload="Javascript:SetWinHeight(this)"></iframe>
     <!--内容结束-->
     
  <!--扩展栏目1 通过js调用标签开始-->  
  <script type="text/javascript" src="images_<%=fid %>/js/js/expand_column01.js"></script>
  <!--扩展栏目1 通过js调用标签结束-->	  
    </td>
  </tr>
</table>
</body>
</html>