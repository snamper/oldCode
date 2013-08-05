<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String[] currentClient = (String[])session.getAttribute("currentClient");
%>

<html xmlns="http://www.w3.org/1999/xhtml">   
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>未来支付平台</title>
<link href="images/style/global/master_01.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_01.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/scrollings.js"></script>
<script type="text/javascript" src="images/js/showDateTime.js"></script>
<script>
window.onload=function(){
	getCurDate("curDate");
	getCurTime("curTime");
	setInterval("getCurTime('curTime')",100);
}

  
</script>
</head>
<body>
<!--标题开始-->
<div id="title">
  <div id="title_right">
    <h4 id="curDate"></h4>
    <h5 id="curTime"></h5>
  </div>
  <div id="title_left">位置：修改基本信息</div>   
</div>
<!--表单开始-->
<div id="search_list_content">
<form action="<%=basePath %>client.do?method=updateClient" method="post">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100" height="30" align="left" class="bk02" >用户名: </td>
      <td align="center" class="bk02" ><input name="fname" type="text" class="txt_05" value="<%=currentClient[3] %>" size="25" />
      </td>
      <td class="bk02">&nbsp;</td>  
    </tr>
    <tr >
      <td height="30" align="left" class="bk02" >登录密码: </td>
      <td align="center" class="bk02"><input name="fpassword" type="text" class="txt_05" size="25" value="<%=currentClient[2] %>"/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
 

    <tr >
      <td height="30" align="left" class="bk02" >联系电话: </td>
      <td align="center" class="bk02"><input name="ftelphone" type="text" class="txt_05" size="25" value="<%=currentClient[12] %>"/></td>
      <td class="bk02">&nbsp;</td>
    </tr>
   
    <tr >
      <td height="30" align="left" class="bk02" >Email: </td>
      <td align="center" class="bk02"><input name="femail" type="text" class="txt_05" size="25" value="<%=currentClient[10] %>"/></td>
      <td class="bk02">&nbsp;</td>
    </tr>
    <tr >
      <td height="30" align="left" class="bk02" >QQ或MSN: </td>
      <td align="center" class="bk02"><input name="fqq" type="text" class="txt_05" size="25" value="<%=currentClient[11] %>"/></td>
      <td class="bk02">&nbsp;</td>
    </tr>
    <tr >
      <td height="30" align="left" class="bk02" >客户密钥: </td>  
      <td align="center" class="bk02"><input name="fkey" type="text" class="txt_05" size="25" value="<%=currentClient[7] %>"/></td>
      <td class="bk02">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8"><input name="Submit2422" type="submit" class="button_09" value="保存" />
      </td>
      <td align="right">&nbsp;</td>
    </tr>
    <tr>
      <td height="10" colspan="2"></td>  
    </tr>
  </table>
  </form>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>
