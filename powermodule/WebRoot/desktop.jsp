<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="path.jsp" %>
<%@ taglib uri="/WEB-INF/WarnTag.tld" prefix="w"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>我的桌面-jrlimp1.0</title>
<script language="javascript" src="images/js/jquery-1.4.2.js" type="text/javascript"></script>
<link href="images/style/css.css" rel="stylesheet" type="text/css" />
<script src="images/js/scrollings.js" type=text/javascript></script>
<script src="images/js/cards.js" type=text/javascript></script>
 <script type="text/javascript">

 function reinitIframe(){

 try{
 var iframe = document.getElementById("ifm");
 var iframe2 = document.getElementById("left");
 //alert(document.documentElement.clientHeight);
 iframe.height = document.documentElement.clientHeight-80;
 iframe2.height = document.documentElement.clientHeight-80;
 
 }catch (ex){}

 }

 $(function(){
	 //窗体改变大小时,重新设置
     $(window).resize(function(){
         //重新获取窗口的宽高
    	 reinitIframe();
    });
 });
 
  var xmlHttpRequest;

function refreshWarn(){
	sendRequest();
	setTimeout('refreshWarn()',10000);
}

function sendRequest(paramter){
	createXMLHttpRequest();

	xmlHttpRequest.open("POST","RefreshWarnServlet",true);

	xmlHttpRequest.onreadystatechange=responseHandler;
	xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlHttpRequest.send();
}

function responseHandler(){
	if(xmlHttpRequest.readyState==4){
		if(xmlHttpRequest.status==200){
			document.getElementById("warnrefresh").innerHTML=xmlHttpRequest.responseText;
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
<script>

function changefontsize(fontsize){
	document.getElementById("0").style.fontSize=12;
	document.getElementById("0").style.fontWeight=400;

	<c:forEach items="${allmt}" var="moduletype">
	document.getElementById(${moduletype.fid}).style.fontSize=12;
	document.getElementById(${moduletype.fid}).style.fontWeight=400;
	</c:forEach>
	document.getElementById(fontsize).style.fontSize=14;
	document.getElementById(fontsize).style.fontWeight=700;
	window.frames["left"].document.getElementById("showDivs_"+fontsize).click();
	changewidthold();
}

window.onload=function(){
	<c:if test="${!empty showtypes}">
	document.getElementById("${showtypes}").style.fontSize=14;
	document.getElementById("${showtypes}").style.fontWeight=700;
	</c:if>
	<c:if test="${empty showtypes}">
	document.getElementById("0").style.fontSize=14;
	document.getElementById("0").style.fontWeight=700;
	</c:if>
	<c:if test="${showStatistic=='0'}">
    refreshWarn();
    </c:if>
	}
</script>
<script>
	function changewidth()
	{
		document.getElementById("left").style.width=0;
		document.getElementById("lefttd").style.width=0;
		document.getElementById("kaiqizcd").innerHTML='<a onclick="changewidthold();"  href="#" class="link1">显示</a>';
	}
	function changewidthold()
	{
		document.getElementById("left").style.width='100%';
		document.getElementById("lefttd").style.width=160;
		document.getElementById("kaiqizcd").innerHTML='<a onclick="changewidth();"  href="#" class="link1">隐藏</a>';
	}
</script>
<script language="JavaScript">
var fkac;
function autoGo(){
ifm.location.reload(true);
document.getElementById("zidongshuaxin").innerHTML='<div onclick="closeGo();" style="cursor:pointer"><input name="sdfgdsfg" checked type="checkbox" />自动刷新</div>';
fkac=setTimeout('autoGo()',8000);
}
function closeGo()
{
	document.getElementById("zidongshuaxin").innerHTML='<div onclick="autoGo();" style="cursor:pointer"><input name="sdfgdsfg" type="checkbox" />自动刷新</div>';
	clearTimeout(fkac);
}

function doubleOnclick(a){
	window.open(a);
}


</script>
</head>
<body style="background-image: url(images/0.jpg);background-repeat: no-repeat;background-posit;	background-position: center top;">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>

    <td id="f24" width="492" class="font0"><div style="margin-left:20px;">
        <c:forEach items="${allmt}" var="moduletype" varStatus="in"><c:if test="${in.index!=0}">  |  </c:if><a ondblclick="doubleOnclick('/powermodule/checkpower.do?method=loginSuccess&userid=${currentUserid}&showtypes=${moduletype.fid}')" title="单击显示菜单项,双击打开新页面" style="cursor: pointer;"  class="link1" id="${moduletype.fid}" onclick=changefontsize("${moduletype.fid}");>${moduletype.fmodulename}</a>
        </c:forEach>
      </div></td>
    <td height="32" class="font0">&nbsp;</td>
    <td width="620" align="right" class="font0"><span class="font4">用户名: ${sessionScope.user.fusername}</span>  <span class="font4">角色:
    <c:forEach items="${sessionScope.listposition}" var="position">
${position.fpositionname}
    </c:forEach>
</span> | <a id="closeWinID" onclick="javascript:window.location.href='/commonscan/busiField.do?method=quit';" class="link1" style="cursor:pointer;">安全退出</a>&nbsp;&nbsp;&nbsp;</td>
  </tr>

</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="250"><div style="margin-left:10px;"><img src="images/logo.gif" width="249" height="47" alt="" /></div></td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1192" height="23" class="font1"><table width="100%" border="0" style="font-weight:400">
		<tr>
        <td width="15"><img src="images/calendar1.gif" width="15" height="15" alt=""   /></td>
        <td width="45" class="font2" id="kaiqizcd"><a onclick="changewidth();" href="#" class="link1">隐藏</a></td>

        <td  width="986" rowspan="2" align="left" valign="top"  class="font2">
	<c:if test="${showStatistic=='0'}">
          <div align="left" id="warnrefresh">
            <w:warns />
            </div>
           </c:if>
        </td>
      </tr>
		<tr>
		  <td><a href="http://mail.dhtx.me" target="_blank"><img src="images/gmail1.gif" width="15" height="15" alt="" /></a></td>
		  <td nowrap="nowrap" class="font2"><a href="http://mail.dhtx.me" target="_blank"  class="link1">邮箱</a></td>
		  </tr>
        </table></td>
<td width="10">&nbsp; </td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="100" valign="top" id="lefttd">
    <c:set value="${moduletype.fid}" var="moduletypefid"></c:set>
    <iframe src ="${path}/checkpower.do?method=changeleft&userid=${currentUserid}&showtypes=0&functionid=${functionid}&sign=${sign}" class="link1" frameborder="0"  onload="reinitIframe()"  scrolling="auto" id="left"  name="left"   width="160"></iframe></td>
    <td  valign="top" align="right" id="rigthtd">
    <c:if test="${empty rightshow}">
    <iframe align="right" <c:if test="${!empty user.fcustomUrl}">src ="${user.fcustomUrl}"</c:if> <c:if test="${empty user.fcustomUrl}"> src ="right2.jsp" </c:if>  frameborder="0"  onload="reinitIframe()"  scrolling="auto" id="ifm" name="ifm"     width="100%"></iframe>
    </c:if>
    <c:if test="${!empty rightshow}">
    <iframe align="right"  <c:if test="${selectgroup==1}">src ="${getHttpUrl_1}/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${currid}&yy_oneTypeSel=rfy_${pkfid}&yy_oneTypeInp=${pkvalue}&currentUserid=${currentUserid_1}&functionid=${functionid_1}&sign=${sign_1}&checkloginsign=${checkloginsign_1}"</c:if> <c:if test="${selectgroup==2 or selectgroup==0}">src ="${getHttpUrl_1}/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${currid}&rfy_${pkfid}=${pkvalue}&currentUserid=${currentUserid_1}&functionid=${functionid_1}&sign=${sign_1}&checkloginsign=${checkloginsign_1}"</c:if> frameborder="0"  scrolling="auto" id="ifm" name="ifm"  onload="reinitIframe()"   width="100%"></iframe>
    </c:if>
    </td>
  </tr>
</table>

</body>
</html>
