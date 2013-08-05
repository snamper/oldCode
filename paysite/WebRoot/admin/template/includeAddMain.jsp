<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
String username = (String)session.getAttribute("username");
if(username == null){  
	out.println("<script>top.location='agentPage.do?method=findAgentByDomain';</script>");
	return;    
} 
Map pageMap=(Map)request.getAttribute("pageMap");
Map elementsMap=(Map)request.getAttribute("elements");

String subTitle=pageMap.get("subtitle").toString();
String buttonname=pageMap.get("buttonname").toString();
String urlpara=pageMap.get("urlpara").toString();
String fileName=pageMap.get("fileName").toString();
String massage=request.getAttribute("massage").toString();
%>        
   