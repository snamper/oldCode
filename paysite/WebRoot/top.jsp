<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%
String[] agentInfo = (String[])session.getAttribute("agentInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>δ��֧��ƽ̨</title>
<link href="images/style/global/<%=agentInfo[22] %>" rel="stylesheet" type="text/css" />
<link href="images/style/top/<%=agentInfo[24] %>" rel="stylesheet" type="text/css" />
</head>
<body>
<input type="hidden"  value="${username}" name="_userName" id="_userName" />
<!--ͷ����ʼ-->
<div id="header">
  <div id="header_left"><img src="images/<%=agentInfo[25] %>/01.jpg"/></div>
  <div id="header_right">
    <!--��½��ϢǶ�׿�ʼ-->
<iframe src="calls/login/<%=agentInfo[28] %>" name="iframepage" frameBorder="0" scrolling=no  width="100%"> </iframe>
    <!--��½��ϢǶ�׽���-->
  </div>
  <div class="clear"></div>
</div>
<!--ͷ������-->
</body>
</html>
