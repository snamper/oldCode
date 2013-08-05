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
	<FRAMESET border=0 frameSpacing=0 rows=70,* frameBorder="no" cols=*>  
		     <FRAME src="top.jsp" name="iframepage"  scrolling=no  id="topFrame"/> 
		<FRAMESET id="fst" rows="*" cols="210,*"  framespacing="0.1" frameBorder="no" border="1" >
			 <FRAME src ="left.jsp"  scrolling="no"    name="left_menu" />
		     <FRAME src ="main.jsp"  name="main"    scrolling="yes" noresize="noresize"/>  
		</FRAMESET>
		<NOFRAMES>
			<body><p>此网页使用了框架，但您的浏览器不支持框架。</p></body>
		</NOFRAMES>
	</FRAMESET>