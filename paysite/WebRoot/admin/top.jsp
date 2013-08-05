<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.agents.pojo.CoAgent"%>
<%@ page import="com.agents.util.Escape"%>
<%@ page import="java.io.File"%>
<%@ page import="com.agents.util.fc"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
if(agent == null){
	response.sendRedirect("agentPage.do?method=findAgentByDomain");
	return;
}

String styleConfig=agent.getfStyleConfig();
String fid=agent.getFid();
//String fmasterCSS=agent.getFmasterCSS();
//String ftopCSS=agent.getFtopCSS();
String fphotoName="img_"+styleConfig;
String flogin="login_"+styleConfig+".html";
Escape escape=new Escape();

String username=fc.changNull((String)session.getAttribute("fName")).toString();  
username=escape.escape(username);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images_<%=fid %>/style/global/master.css" rel="stylesheet" type="text/css" />
<link href="images_<%=fid %>/style/top/top.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--头部开始-->
<div id="header">   
  <div id="header_left"><img src="images_<%=fid %>/img/01.jpg"/></div>
  <div id="header_right">
    <!--登陆信息嵌套开始-->
<iframe src="calls_<%=fid %>/login/login.html?name=<%=username%>" name="iframepage" frameBorder="0" scrolling=no  width="100%"> </iframe>
    <!--登陆信息嵌套结束-->  
  </div>  
  <div class="clear"></div>
</div>
<!--头部结束-->
</body>
</html>
