<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'planSign.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>

  <body>
  <div align="center">
    <table width="530" border="0" cellpadding="3" cellspacing="0">
      <tr>
        <td width="438" valign="bottom"><img src="img/openapiimg_06.jpg" width="17" height="20"> <span class="STYLE23">个性签名</span></td>
        <td width="80" valign="bottom"><span class="STYLE21"><a href="servlet/QueryOrder">订单管理</a></span></td>
      </tr>
      <tr>
        <td height="1" colspan="2"><hr size="1" noshade></td>
      </tr>
    </table>
    <br>
    <span class="STYLE2">订单信息<br>
    <br>
    </span>
    <table width="356" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
      <tr>
        <td width="160" align="right" bgcolor="#F5F5F5"><span class="STYLE17">订单号：</span></td>
        <td width="173" align="left" bgcolor="#FFFFFF"><span class="STYLE17"><%=request.getAttribute("orderid")%></span></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#F5F5F5"><span class="STYLE17">商品名称：</span></td>
        <td align="left" bgcolor="#FFFFFF"><span class="STYLE17"><%=request.getAttribute("goodname")%></span></td>
      </tr>
      <tr>
        <td align="right" bgcolor="#F5F5F5"><span class="STYLE17">订单总额： </span></td>
        <td align="left" bgcolor="#FFFFFF"><span class="STYLE17"><%=request.getAttribute("price")%></span></td>
      </tr>
      <tr>
        <td colspan="2" align="center" bgcolor="#FFFFFF"><span class="STYLE17"><a href="PayOrder?orderid=<%=request.getAttribute("orderid")%>"><img src="img/openapiimg_07.jpg" width="95" height="32" border="0"></a>&nbsp;<a href="servlet/QueryGoods">返回上一步</a></span></td>
      </tr>
    </table>
    <br>
  </div>
  </body>
</html>
