<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.agents.pojo.CoAgent"%>
<%@ include file="/admin/includeCheckLogin.jsp"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String[] clientInfo = (String[])session.getAttribute("clientInfo");
for(int i=0;i<clientInfo.length;i++){
	if(clientInfo[i]==null || clientInfo[i].equals("null") || clientInfo[i].equals("NULL")){
		clientInfo[i]="";  
	}
}
%>
  
<html xmlns="http://www.w3.org/1999/xhtml">   
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
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
   <div id="title_left"></div>
</div>
<!--标题结束-->  
  <div id="subtitle">&nbsp;&nbsp;&nbsp;详细资料</div>
</div>
<!--表单开始-->
<div id="search_list_content">
<form action="<%=basePath %>client.do?method=updateUserInfo" method="post" name="form1">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr >  
      <td height="30"  width="150" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;用户名称: </td>
      <td align="center" class="bk02" width="200" ><input  style="background-color:#F0EFEF;" name="fName" id="fName" type="text" readonly  class="txt_05" size="25" value="<%=clientInfo[0] %>"  readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
    <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;邮箱: </td>
      <td align="center" class="bk02" > <input style="background-color:#F0EFEF;" name="fEmail" id="fEmail" type="text"  readonly class="txt_05" size="25" value="<%=clientInfo[1] %>"  readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
     <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;银行ID: </td>
      <td align="center" class="bk02"><input name="fBankID" style="background-color:#F0EFEF;" id="fBankID" type="text"  class="txt_05" size="25" value="<%=clientInfo[4] %>"  readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
     <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;开户行名称: </td>
      <td align="center" class="bk02"><input name="fBankSeat" style="background-color:#F0EFEF;" id="fBankSeat" type="text"  class="txt_05" size="25" value="<%=clientInfo[5] %>"  readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
     <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;银行账户名称: </td>
      <td align="center" class="bk02"><input name="fBankName" style="background-color:#F0EFEF;" id="fBankName" type="text"   class="txt_05" size="25" value="<%=clientInfo[6] %>"  readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
     <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;银行卡号: </td>
      <td align="center" class="bk02"><input name="fBankNum" style="background-color:#F0EFEF;" id="fBankNum" type="text"   class="txt_05" size="25" value="<%=clientInfo[7] %>"  readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
     <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;联系人: </td>
      <td align="center" class="bk02"><input name="fContact"  id="fContact" type="text" class="txt_05" size="25" value="<%=clientInfo[8] %>"  /></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
    <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;联系电话: </td>
      <td align="center" class="bk02"><input name="fPhone" id="fPhone" type="text" class="txt_05" size="25" value="<%=clientInfo[3] %>"  /></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
   <tr >
      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;QQ号码: </td>
      <td align="center" class="bk02"><input name="fQQ" id="fQQ" type="text" class="txt_05" size="25" value="<%=clientInfo[2] %>"  /></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>
    </tr>
<%--    <tr >--%>
<%--      <td height="30" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;硬件绑定状态: </td>--%>
<%--      <td align="" class="bk02">--%>
<%--      <%if(clientInfo[9]==null || clientInfo[9].equals("1")){ %>--%>
<%--                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未绑定--%>
<%--      <%}else{ %>--%>
<%--                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已绑定--%>
<%--      <%} %>--%>
<%--      --%>
<%--      </td>--%>
<%--      <td class="bk02">&nbsp;&nbsp;<span class="font7"> </span></td>--%>
<%--    </tr>--%>
    
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8">&nbsp;&nbsp;&nbsp;&nbsp;<input name="Submit2422" type="submit" class="button_09" onclick="" value="修改" />
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
<script type="text/javascript">

<%

String resultInfo = (String)session.getAttribute("message");    
if(resultInfo!=null){      
	resultInfo=new String(resultInfo.getBytes("iso8859-1"), "utf-8");   
}
if(!"".equals(resultInfo) && resultInfo != null){
	if("true".equals(resultInfo)){
		resultInfo="修改成功！";  
		%>
		alert('<%=resultInfo %>');
   
<%}}%>
</script>

