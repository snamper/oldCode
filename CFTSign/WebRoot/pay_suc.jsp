<%@ page language="java" import="java.util.*" pageEncoding="gb2312" contentType="text/html;charset=gb2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>app demo</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
<!--
.STYLE2 {
	font-size: 16px;
	font-weight: bold;
}
.STYLE15 {color: #000000; font-weight: bold; font-size: 12px; }
.STYLE17 {color: #000000; font-size: 12px; }
.STYLE19 {color: #0292DF; font-size: 12px; }
.STYLE21 {color: #FF0000; font-size: 12px; }
-->
    </style>
</head>
  
  <body>
  <div align="center"><br>
    <span class="STYLE2">支付完成<br>
    <br>
    </span>
    <table width="356" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
      <tr>
        <td width="160" align="right" bgcolor="#F5F5F5"><span class="STYLE17">订单号：<%=request.getAttribute("orderid")%></span></td>
      </tr>
      <tr>
        <td width="160" align="right" bgcolor="#F5F5F5"><span class="STYLE17">请到订单管理中查询订单状态</span></td>
      </tr>
      <tr>
        <td align="center" bgcolor="#FFFFFF"><span class="STYLE17"><a href="servlet/QueryOrder">订单管理</a></span></td>
      </tr>
    </table>
    <br>
  </div>
  </body>
</html>
