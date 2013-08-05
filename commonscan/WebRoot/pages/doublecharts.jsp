<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="jinrl_exploit_common.fc" %>
<%@page import="jinrl_exploit_common.DataConnect"%>
<%@page import="java.sql.ResultSet"%>
<%
String id = fc.getpv(request,"chartID");
DataConnect dc = new DataConnect("sysdb", false);
String sql = "select * from tCharts where fid='"+id+"'";
ResultSet rs = dc.query(sql);

String fbusiInfoids = "";
String fRefreshTime = "";
if(rs!=null && rs.next()){
	fbusiInfoids = rs.getString("fbusiInfoids");
	fRefreshTime = rs.getString("fRefreshTime");
}
dc.CloseResultSet(rs);
dc.CloseConnect();
String[] busiInfoIDs = fbusiInfoids.split("\\|");

String busiInfos = "";
for(int i = 0; i < busiInfoIDs.length; i++){
	busiInfos += (",'"+busiInfoIDs[i]+"'");
}

if(busiInfos.length() > 0){
	busiInfos = busiInfos.substring(1);
}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>JRLIMP</title>
<script src="/commonscan/js/jquery-1.4.2.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript">
function reinitIframe(){

	 try{
	 var iframe = document.getElementById("ifm1");
	 var iframe2 = document.getElementById("ifm2");
	 //alert(document.body.clientHeight);
	 iframe.height = document.body.clientHeight;
	 iframe2.height = document.body.clientHeight;
	 
	 }catch (ex){}
	 }
$(function(){
	 //窗体改变大小时,重新设置
    $(window).resize(function(){
        //重新获取窗口的宽高
   	 reinitIframe();
   });
});

//var ids = new Array("21102121733533342321", "21102121733533342322", "21102121733533342323"); 
var ids = eval(new Array(<%=busiInfos%>)); 
var url1 = "/commonscan/GraphServlet?dch=1&busiInfoID=";
var currNum=0;//当前下标
var fa=1;//准备执行fun1

$(document).ready(function(){
	$('#ifm1').attr("src",url1+ids[0]);
	$('#ifm2').attr("src",url1+ids[1]);
	autoDO();
});

function autoDO(){
	var ls = ids.length;
	
	
	if(currNum >= ls){
		currNum=0;
	}

	if(fa==1){
		fun1(ids[currNum]);
		fa = 2;
	}else{
		fun2(ids[currNum]);
		fa = 1;
	}
	
	currNum++;
	setTimeout('autoDO()',1000 * <%=fRefreshTime%>);
}



function fun1(id){
	$('#ifm2').attr("src",url1+id);
	$("#d2").css('visibility','hidden');
	$("#d1").removeAttr("style");
}

function fun2(id){
	$('#ifm1').attr("src",url1+id);
	$("#d1").css('visibility','hidden');
	$("#d2").removeAttr("style");
}
</script>
</head>
<body>
<div id="d1">
<iframe align="right"  onload="reinitIframe()"  src ="" frameborder="0"   scrolling="auto" id="ifm1" name="ifm1"   width="100%"></iframe>
</div>
<div id="d2" style="visibility: hidden;">
<iframe align="right"  onload="reinitIframe()"  src ="" frameborder="0"   scrolling="auto" id="ifm2" name="ifm2"   width="100%"></iframe>
</div>
</body>
</html>
