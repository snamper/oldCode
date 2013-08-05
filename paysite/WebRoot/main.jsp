<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%
String[] agentInfo = (String[])session.getAttribute("agentInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>未来支33</title>
<link href="images/style/global/<%=agentInfo[22] %>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/date.js"></script>
<script type="text/javascript" src="images/js/scrollings.js"></script>
</head>
<body class="main_body">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td colspan="2" class="main_top"><!--头部开始-->
      <iframe src="top.jsp" name="iframepage" frameBorder="0" scrolling=no  width="100%"  onload="Javascript:SetWinHeight(this)"> </iframe>
      <!--头部结束--></td>
  </tr>
  <tr>
    <td width="224" valign="top" class="main_left">
      <!--左侧开始-->
      <iframe src ="clientInfo.do?method=findLeftMenu" frameborder="0" height="600" scrolling="auto"   id="left_menu" name="left_menu"  width="100%" ></iframe>
      <!--左侧结束-->
    </td>
    <td valign="top" class="main_content">
      <!--内容开始-->
      <iframe src ="agent_index.jsp" frameborder="0" name="right_content" scrolling="auto" id="iframepage" width="100%" height="600" ></iframe>
      <!--内容结束-->
  <!--扩展栏目1 通过js调用标签开始-->
  <script type="text/javascript" src="images/js/<%=agentInfo[26] %>/expand_column01.js"></script>
  <!--扩展栏目1 通过js调用标签结束-->
    </td>
  </tr>
</table>
</body>
</html>
