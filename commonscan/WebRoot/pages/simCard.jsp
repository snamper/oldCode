<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="jinrl_exploit_common.fc"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ include file="/path.jsp"%>
<%
List resultList = (List)request.getAttribute("resultList");
List selectList = (List)request.getAttribute("selectList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>设备换卡-jrlimp1.0</title>
		<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
		<link href="style/core.css" type="text/css" rel="stylesheet" />
		<link href="${path}/style/css.css" rel="stylesheet" type="text/css" />
		<script src="/commonscan/js/jquery-1.4.2.js" type="text/javascript"
			language="javascript"></script>
		<script>
		function mouseinto(a)
		{
		  var s = a;

		  document.getElementById(s).style.backgroundColor="#D7E7EE";

		}
		function mouseout(a)
		{
		  var s = a;
		  document.getElementById(s).style.backgroundColor="#FFFFFF";

		}

		function updateSIMinfo(i){
			var key = i;
			var fid = $("#fid"+i).val();
			var fDevice = $("#fDevice"+i).val();
			var fComPort = $("#fComPort"+i).val();
			var params = "fid="+fid+"&fDevice="+fDevice+"&fComPort="+fComPort+"&key="+key;

			 $.ajax({
			   url:'UpdateSimCard', //后台处理程序
			   type:'post',         //数据发送方式
			   dataType:'text',     //接受数据格式
			   data:params,         //要传递的数据
			   success:backresult1 //回传函数(这里是函数名)
			 });
		 }

		function backresult1(json) //回传函数实体，参数为XMLhttpRequest.responseText
		{
			var reta = json.split("|");
			if(reta[0] == 'true'){
				$("#signCor"+reta[1]).css("background","green");	
			}else{
				$("#signCor"+reta[1]).css("background","red");
				if(reta[2]=='1'){$("#message"+reta[1]).text('不存在此卡:'+reta[3]);}
				if(reta[2]=='2'){$("#message"+reta[1]).text('此卡状态错误:'+reta[3]);}
				if(reta[2]=='3'){$("#message"+reta[1]).text('更新失败:'+reta[3]);}
			}
		}

		function cancelSIMinfo(i){
			var key = i;
			var fid = $("#fid"+i).val();
			var params = "fid="+fid+"&key="+key;

			 $.ajax({
			   url:'CancelSimCard', //后台处理程序
			   type:'post',         //数据发送方式
			   dataType:'text',     //接受数据格式
			   data:params,         //要传递的数据
			   success:backresult2 //回传函数(这里是函数名)
			 });
		 }

		function backresult2 (json) //回传函数实体，参数为XMLhttpRequest.responseText
		{
			var reta = json.split("|");
			if(reta[0] == 'true'){
				$("#signCor"+reta[1]).css("background","green");	
			}else{
				$("#signCor"+reta[1]).css("background","red");
			}
		}

		function batchGO(batchStr)
		{
			var bss = batchStr.split("|");
			for(var a = 0; a < bss.length; a++)
				{
					cancelSIMinfo(bss[a]);
				}
		}

		 //回车的键值为13  
		  function document.onkeydown(){  
		       if (event.keyCode == 13) {  
		          //js 监听对应的id  
		    	  event.keyCode=9;
		       }  
		   } 
		
		</script>

	</head>
	<body style="FONT-FAMILY: 'arial'">

		<table width="100%" height="68" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="5">
					<img src="/commonscan/images/0100.jpg" width="6" height="35" alt="">
				</td>
				<td background="/commonscan/images/0101.jpg"
					style="PADDING-LEFT: 8px; COLOR: #000" class=font5>
					设备换卡
				</td>
				<td width="5">
					<img src="/commonscan/images/0102.jpg" width="6" height="35" alt="">
				</td>
			</tr>
			<form action="/commonscan/SelectSimCard" method="get" name="myfom1">
				<tr>
					<td width="6" height="33" background="/commonscan/images/0103.jpg"></td>
					<td bgcolor="#F4F6F5" class=font8
						style="line-height: 28px; padding-top: 3px; padding-bottom: 3px;">
						<span style="font-size: 12px">设备ID</span>
						<select class="select_txt" name="rfy_fDevice">
						<c:forEach var="sl" items="${selectList}">
						<option <c:if test="${sl==defaultDevice}"> selected="selected" </c:if> value="${sl}">${sl}</option>
						</c:forEach>
						</select>
						<input type="submit" value=" 刷新 " />
						
					</td>
					<td width="6" background="/commonscan/images/0104.jpg"></td>
				</tr>
			</form>
			<tr>
				<td>
					<img src="images/0105.jpg" width="6" height="5" alt="">
				</td>
				<td background="images/0106.jpg"></td>
				<td>
					<img src="images/0107.jpg" width="5" height="5" alt="">
				</td>
			</tr>
			<tr>
				<td height="5" colspan="3"></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#D2DDE1" class="font6">
			<tr class="bg_01">
				<td width="35" align="center" class="font7">
					序号
				</td>
				<td width="" align="center" class="font7">
					设备ID
				</td>

				<td width="" align="center" class="font7">
					COM端口
				</td>

				<td width="" align="center" class="font7">
					状态
				</td>
				
				<td width="" align="center" class="font7">
					面值
				</td>
				
				<td width="" align="center" class="font7">
					未充余额
				</td>

				<td width="" align="center" class="font7"  >
					手机号
				</td>

				<td width="250" align="center" class="font7"  >
					备注
				</td>

			</tr>


<%
String batchStr = "";
for(int i=0; i < resultList.size(); i++){ 
String[] oneResult = (String[])resultList.get(i);
%>
<input type="hidden" id="fDevice<%=i %>" value="<%=oneResult[0] %>" />
<input type="hidden" id="fComPort<%=i %>" value="<%=oneResult[1] %>" />
			<tr bgcolor="#FFFFFF" onmouseout="mouseout('asdf<%=i %>');"
				onmouseover="mouseinto('asdf<%=i %>');" id="asdf<%=i %>">
				<td align="center" height="25" id="signCor<%=i %>">
					<%=i+1 %>
				</td>

				<td align="center">
					<%=oneResult[0] %>
				</td>

				<td align="center">
					<%=oneResult[1] %>
				</td>

				<td align="center">
					<%=oneResult[2] %>
				</td>
				
				<td align="center">
					<%=oneResult[4] %>
				</td>
				
				<td align="center">
					<%=oneResult[5] %>
				</td>

				<td align="center">
				<%
					int flag1 = 0;
					if("正常".equals(oneResult[2])){
						flag1  = 1;
					}
					
					int flag2 = 0;
					if(!"使用中".equals(oneResult[2])){
						flag2  = 1;
					}
					
				%>
					<input tabindex="<%=i+1 %>" id="fid<%=i %>" value="<%=oneResult[3] %>" type="text" size="11" <%if(flag1==1){ %> onblur="updateSIMinfo(<%=i %>);" <%} else{%> disabled="disabled" <%} %> />
					<input tabindex="888<%=i+1 %>" type="button" value="插卡" <%if(flag1==1){ %> onclick="updateSIMinfo(<%=i %>);" <%} else{%> disabled="disabled"<%} %>  />&nbsp;&nbsp;&nbsp;&nbsp;
					<input tabindex="999<%=i+1 %>" type="button" value="取消" <%if(flag2==1){ batchStr = batchStr +"|"+i; %> onclick="cancelSIMinfo(<%=i %>);" <%} else{%> disabled="disabled"<%} %>   />
				</td>

				<td align="center" id="message<%=i %>">
					
				</td>
			</tr>
<%} %>

		</table>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="bg_02">
			<tr>
				<td>
					<table width="100%" border="0" align="left" class="font7"
						height="35">
						<tr>
							<td width="" nowrap="nowrap" align="left"></td>
							<td width="40" align="right" nowrap="nowrap"></td>
							<td width="190" nowrap="nowrap" align="right"></td>
							<td width="93" nowrap="nowrap" align="left"><%if(resultList != null && resultList.size() > 0){ %><input value="批量取消" type="button" onclick="batchGO('<%=batchStr.substring(1) %>');" /><%} %></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr class="font999">
				<td height="30" class="line01">
					2011 JRL-IMPv1.0 | 版权所有
				</td>
			</tr>
		</table>

	</body>

</html>
