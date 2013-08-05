<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
String username = (String)session.getAttribute("username");
if(username == null){  
	out.println("<script>top.location='agentPage.do?method=findAgentByDomain';</script>");
	return;  
} 
Map pageMap=(Map)request.getAttribute("pageMap");


String subTitle=pageMap.get("subtitle").toString();
String xmlName=pageMap.get("xmlName").toString();
String pageUrl=pageMap.get("pageUrl").toString();
String menuName[]=pageMap.get("menuName").toString().split(",");
String value[]=pageMap.get("optionvalue").toString().split(",");
String name[]= pageMap.get("optionname").toString().split(",");
String dateField=pageMap.get("dateField").toString();  
String searchTypeId=pageMap.get("searchTypeId").toString();  

Map fieldMap=(Map)request.getAttribute("fieldMap");
String key=request.getAttribute("key").toString();
String type=request.getAttribute("type").toString();
String endtime=request.getAttribute("endtime").toString();
String starttime=request.getAttribute("starttime").toString();

String fields=request.getAttribute("fields").toString();
String fieldsValue=request.getAttribute("fieldsValue").toString();

String fieldsArr[]=fields.split(",");
String fieldsValueArr[]=fieldsValue.split(",");

String val="";
for(int i=0;i<fieldsArr.length;i++){
	val=val+"&"+fieldsArr[i]+"="+fieldsValueArr[i];
}  
pageUrl=pageUrl+"&key="+key+"&type="+type+"&endtime="+endtime+"&starttime="+starttime+val;
%>        
   