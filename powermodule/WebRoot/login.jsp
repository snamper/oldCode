<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="java.net.InetAddress"%>
<%@ include file="path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
Cookie[] cookies = request.getCookies();
String loginUserName = "";
if(cookies != null){
	for(int i = 0; i < cookies.length; i++){
		Cookie cookie = cookies[i];
		 if ("loginUserNameCookie".equals(cookie.getName())) {
			 loginUserName = cookie.getValue();
      }
	}
}
%>
<head>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>金瑞利-综合管理平台1.0</title>
<link href="images/style/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function document.onkeydown(){                //网页内按下回车触发
    if(event.keyCode==13)
    {
    	document.buttensubmit.onclick();
        return false;
    }
}

function setstate(){
		document.getElementById("v_usbcode").value = document.getElementById("axusbkey").getcode;
	}

</script>
<script>
function ab(){
	if(self!=top){

		window.parent.location.href="/powermodule/login.jsp";
	}
}

</script>
 <script type="text/javascript">

  var xmlHttpRequest;

function sendemail(){
	var username =document.getElementById("username").value;
	if(username=="")
	{
		alert("请输入用户名!");
		return;
	}
	var paramter ="username="+username+"&method=sendEmail";
	sendRequest(paramter);
}

function sendRequest(paramter){
	createXMLHttpRequest();

	xmlHttpRequest.open("POST","${path}/checkpower.do",true);

	xmlHttpRequest.onreadystatechange=responseHandler;
	xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpRequest.send(paramter);
}

function responseHandler(){
	if(xmlHttpRequest.readyState==4){
		if(xmlHttpRequest.status==200){
			document.getElementById("yanzhengmatup").innerHTML='<input type="hidden" name="foremail" value="abc" />';
			alert("请注意查收邮件，获取验证码");
		}
	}
}

function createXMLHttpRequest(){

	if(window.XMLHttpRequest){

		xmlHttpRequest = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		try{
			xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
}

</script>
</head>

<body onload="ab();">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="images/a1.jpg"><div align="right">IP:<%=request.getRemoteAddr() %>&nbsp;</div></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="top" background="images/a2.jpg"><table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="80" colspan="2">&nbsp;</td>
        </tr>
        <tr>
          <td width="582"><img src="images/a3.jpg" width="582" height="302" alt="" /></td>
          <td width="582">
          <form action="${path}/checkpower.do?method=isLogin" method="post" name="form1">
          <table width="421" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="53" rowspan="10">&nbsp;</td>
              <td height="44" colspan="3">&nbsp;</td>
            </tr>
            <tr>
              <td height="59" colspan="2"><img src="images/a0.gif"   /></td>
              <td width="126">&nbsp;</td>
            </tr>
            <tr>
              <td width="62"><img src="images/a6.jpg" width="62" height="29" alt="" /></td>
              <td><input name="username" type="text"  value="<%=loginUserName %>"  class="link2" id="username" size="12" maxlength="16"/></td>
              <td><a href="#"  style="cursor:pointer;  "  onclick="sendemail();">获取邮箱验证</a></td>
            </tr>
            <tr>
              <td height="15" colspan="3"></td>
            </tr>
            <tr>
              <td><img src="images/a7.jpg" width="62" height="29" alt="" /></td>
              <td><input name="password" type="password"  class="link3" id="textfield3" size="12" maxlength="14"/></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="5" colspan="3"></td>
            </tr>
            <tr>
              <td><img src="images/a8.jpg" width="62" height="29" alt="" /></td>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td align="left"><input name="checkcode" type="text"  class="link4" id="textfield13" size="12" maxlength="6"/></td>
                  <td align="right" id="yanzhengmatup">
<!--                   <img src="${path}/validatecode" alt="这是一个验证码" width="76" height="25" onClick="this.src='${path}/validatecode?'+new Date().getTime();";/> -->
                  <img src="${path}/jcaptcha.jpg" alt="这是一个验证码"  onClick="this.src='${path}/jcaptcha.jpg?'+new Date().getTime();";/>
                  </td>
                </tr>
              </table></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="5" colspan="3"></td>
            </tr>
            <tr>
              <td><img src="images/usb_key.jpg" width="62" height="29" alt="" /></td>
              <td><object
          classid="CLSID:ABE3C3F4-533C-4F16-BD19-426F831FA1D1"
          codebase="axusbkey.ocx"
          width="100%"
          height="30"
          hspace="0"
          vspace="0" align="middle"
          id = "axusbkey" >
  </object> </td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td height="15" colspan="3"></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td  width="62"></td>
              <td align="left"><img name="buttensubmit" style="cursor:hand" src="images/a10.jpg" alt="" width="91" height="29" border="0" onclick="setstate();javascript:document.form1.submit();"; /></td>
            </tr>
            <tr>
              <td>&nbsp;<input name="v_usbcode" id="v_usbcode" type="hidden" value="" /></td>
              <td height="40"></td>
              <td style="color: red;" align="left">${codeerror}</td>
            </tr>
          </table>

          </form>

          </td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="55" align="center" valign="top" class="font0" style="line-height:24px;">&nbsp;</td>
  </tr>
  <tr>
    <td height="50" align="center" valign="top" class="font0" style="line-height:24px;"><a href="#" class="link1">关于我们</a> | <a href="#" class="link1">公司简介</a> | <a href="#" class="link1">加入我们</a> | <a href="#" class="link1">服务条款</a><br>
    Copyright (C) 2009-2012 KAMIZHIFU Corporation, All Rights Reserved</td>
  </tr>
</table>

</body>
</html>