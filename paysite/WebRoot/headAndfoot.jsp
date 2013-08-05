<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="com.agents.pojo.CoAgent"%>
<%@page import="java.io.File"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.agents.util.Tools"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.IOException"%>
<%@page import="com.agents.util.fc"%>
<%   
CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
if(agent == null){
	response.sendRedirect("agentPage.do?method=findAgentByDomain");
	return;
}
String indexPage = agent.getFindexPage();
String realPath = request.getRealPath("/");
File file = new File(realPath + "\\agentindex\\ai_" + agent.getFid() + "\\index.html");
BufferedReader reader = null;
String headers = null;   
String footers = null;
try {
	String tempString = null;
	reader = new BufferedReader(new FileReader(file));
	StringBuffer allContent = new StringBuffer();
	while ((tempString = reader.readLine()) != null) {
			tempString = fc.replace(tempString," src=\"images/"," src=\"agentindex/ai_"+agent.getFid()+"/images/");
			tempString = fc.replace(tempString, "亿通支付平台", agent.getFname());
			tempString = fc.replace(tempString, "亿通支付卡", agent.getFname());
			tempString = fc.replace(tempString, "京ICP证XXXXXX号", agent.getFicp());
			tempString = fc.replace(tempString, "GmLong.com",  agent.getFdomainName());
			if(tempString.indexOf("out$phone") != -1){
				tempString = fc.replace(tempString, Tools.divString(tempString, "out$phone"), agent.getFservicePhone());
			}
		allContent.append(tempString);
	}
	headers = Tools.getDivHtml(allContent.toString(), "mark$header");
	footers = Tools.getDivHtml(allContent.toString(), "mark$footer");

	//
	reader.close();
} catch (IOException e) {
	e.printStackTrace();
} finally {
	if (reader != null) {
		try {
			reader.close();
		} catch (IOException e1) {
		}
	}
}
request.setAttribute("headers",headers);
request.setAttribute("footers",footers);
/**
Agents agent = (Agents)session.getAttribute("currentAgent");
String indexPage = agent.getFindexPage();
String headers = "agentindex/ai_" + agent.getFid() + "/header.jsp";
String footers = "agentindex/ai_" + agent.getFid() + "/footer.jsp";
request.setAttribute("headers",headers);
request.setAttribute("footers",footers);
**/
%>