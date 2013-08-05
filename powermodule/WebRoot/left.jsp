<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="com.jinrl.powermodule.pojo.Tfunction"%>
<%@page import="com.jinrl.powermodule.pojo.Tmoduletype"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<script src="/powermodule/images/js/jquery-1.4.2.js" type="text/javascript" language="javascript"></script>
<link href="images/style/css.css" rel="stylesheet" type="text/css" />

<script>
	function showHide(abc,xyz)
	{
		if(document.getElementById(abc).style.display=="block")
		{
					document.getElementById(abc).style.display="none";
		}
		else
		{
					var alldd = $("#"+xyz+" > dd");
					for(var a = 0;a<alldd.length;a++)
					{
						alldd[a].style.display="none";

					}
					document.getElementById(abc).style.display="block";
		}
	}

	function openFirst()
	{
		var alldd = document.getElementsByTagName("dd");
		alldd[0].style.display="block";
	}
</script>
<script>
$(document).ready(function(){
	$("ul>li>a").click(function(){
	  if(self!=top){
			window.parent.document.title=$(this).html()+"-jrlimp1.0";
		}
	});
});


	function hideDivandShow(a){
		$(".nav").hide();
		$("#showDivs_"+a).show();
	}

</script>
</head>

<body onload="openFirst();hideDivandShow(0);">
<br />
<table width="140" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="140" valign="top">
<%
List resultlistsAll = (List)session.getAttribute("resultlistsAll");
List allmt = (List)session.getAttribute("allmt");
for(int k = 0; k < resultlistsAll.size(); k++){

	List resultlists = (List)resultlistsAll.get(k);
	Tmoduletype dt = (Tmoduletype)allmt.get(k);
	Integer k2 = dt.getFid();

%>
	<div class="nav" id="showDivs_<%=k2 %>" onclick="hideDivandShow('<%=k2 %>')"  >

<%if(k==0){ %>
    <dl class='bitem'>
	<dt><p><img src="images/3.jpg" width="23" height="24" /></p><h4 style="cursor:pointer;" onClick='showHide("items1_1","showDivs_<%=k2 %>")'>基本设置</h4></dt>
	<dd style='display:none' class='sitem' id='items1_1'>
	<ul>
	<li><a href="user.do?method=getCurrentUser" style="font-size: 14px"  target="ifm">查看个人资料</a></li>
	<li><a href="user.do?method=getCurrentUser&updateuser=1" style="font-size: 14px"  target="ifm">修改个人资料</a></li>
	<li><a href="userpages/updatepassword.jsp" style="font-size: 14px"  target="ifm">更改登录密码</a></li>
	</ul>
	</dd>
	</dl>
<%} %>

<%--<c:if test="${notShowAny==0}">--%>
<%

for(int i=0;i<resultlists.size();i++){
List resultlist=(List)resultlists.get(i);
%>
	<dl class='bitem'>
	<dt><p><img src="menu_img_ico/m0.gif" width="23" height="24" /></p><h4 style="cursor:pointer;"  onClick='showHide("<%=resultlist.get(0) + "" + k2  %>","showDivs_<%=k2 %>")' ><%=resultlist.get(0) %></h4></dt>
<%List listfunction = (List)resultlist.get(1); request.setAttribute("listfunction",listfunction); %>
	<dd style='display:none' class='sitem' id='<%=resultlist.get(0) + "" + k2 %>'>
	<ul>
	<c:forEach items="${listfunction}" var="function">
		<li><a style="font-size: 14px" href="${path}/checkpower.do?method=functionurl&functionid=${function.fid}" target="ifm">${function.ffunctionname}</a></li>
	</c:forEach>
	</ul>
	</dd>
	</dl>
<%} %>
<%--</c:if>--%>

<%--

<c:if test="${logotype==0}">
	<dl class='bitem'>
	<dt><p><img src="images/4.jpg" width="23" height="23" /></p><h4 style="cursor:pointer;" onClick='showHide("items3_1")'>内部消息</h4></dt>
	<dd style='display:none' class='sitem' id='items3_1'>
	<ul>
	<li><a href="#" style="font-size: 12px" >修改现有文章</a></li>
	<li><a href="#" style="font-size: 12px" >撰写最新文章</a></li>
	<li><a href="#" style="font-size: 12px" >短消息</a></li>
	</ul>
	</dd>
	</dl>
</c:if>
--%>
	</div>
	<%} %>
	</td>
  </tr>
</table>
</body>
</html>

