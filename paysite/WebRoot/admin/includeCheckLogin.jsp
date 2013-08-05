<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
String username = (String)session.getAttribute("username");
if(username == null){  
	out.println("<script>top.location='agentPage.do?method=findAgentByDomain';</script>");
	return;  
}  
%>        
   