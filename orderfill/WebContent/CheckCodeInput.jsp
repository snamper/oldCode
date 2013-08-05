<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="GB2312"%>
<%@ page import="commfill.*"%>
<%@ page import="common.*"%>
<%
//
commfill.CheckCode cc = new commfill.CheckCode();
//接收
String state = fc.getpv(request, "state"); 
String fid = fc.getpv(request, "fid"); 
if (!state.equals("")){
	String sCode = fc.getpv(request, "text_code");
	cc.inputCheckCode(fid, sCode);
}
//显示
String s = cc.getCheckCodeID(1, 3);
int nRowCount = 0;
int nRecordCount = 0;
try{
	nRowCount = Integer.valueOf(cc.getStat(s, "RowCount")).intValue();
	nRecordCount = Integer.valueOf(cc.getStat(s, "RecordCount")).intValue();
}catch(Exception e){
	System.out.print("读取出错:" + s);		
}
	
%>
<head>
<style type="text/css">
<!--
body,td,th {
	font-family: 宋体;
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.style1 {color: #666666}
-->
</style>
<%if (nRowCount > 0) {%>
	<bgsound src="pegconn.wav" loop=3>
	<meta   http-equiv= "refresh"   content= "30">   
<script type="text/javascript">
var t=false;
setInterval("flash_title()",300);
function flash_title(){
if(t){
document.title='☆有新订单☆';
t=false;
}else{
document.title='★有新订单★';
t=true;
}
}
</script>
<%}else{%>
		<meta   http-equiv= "refresh"   content= "2">   
<% }%>
<title>验证码输入页面</title>
</head>
<body>
<table width="100%"  border="0" cellpadding="0" cellspacing="1" bgcolor="#E0E0E0">
  <tr bgcolor="#FFFFFF">
    <td height="18" bgcolor="#F7F7FF" ><div align="center" class="style1">当前有<%=nRecordCount%>个验证码等待输入(自动刷新)</div></td>
  </tr>  
  <%
  for (int i=1; i<=nRowCount; i++){
		 fid = cc.getData(i, "fid"); 
		 String ftype = cc.getData(i, "fimagetype"); 
  %>
  <tr bgcolor="#FFFFFF">
    <td >
      <table width="100%"  border="0" cellspacing="0" cellpadding="2">
        <form name="form" action="" method="get">
        <tr>
          <td valign="middle"><div align="right">
          <%if (ftype.equals("bmp")||(ftype.equals(""))){ %>
          <img src="/orderfill/UpCheckCode?fid=<%=fid %>">
          <%}
			if (ftype.equals("htm")){ %>
          <iframe src="/orderfill/UpCheckCode?fid=<%=fid %>" frameBorder="0" width="150" scrolling="no" height="50"></iframe>
          <%} %>
          <br><%=fid %></td>
          <td width="50%" valign="middle"><input id="code" name="text_code" type="text" size="8">
            <input type="submit" name="Submit" value="提交">
            <input name="fid" type="hidden" id="fid" value="<%=fid%>">
            <input name="state" type="hidden" id="state" value="<%=fid%>"></td>
            </tr>
        </form>
      </table>
	</td>
  </tr>  
<%
  }
%>
</table>
</body>
<%if (nRowCount > 0) {%>
 <script>document.getElementById("code").focus()</script>
 <% }%>