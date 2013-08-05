<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/admin/template/includeDetailMain.jsp"%>    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/scrollings.js"></script>
<script type="text/javascript" src="images/js/showDateTime.js"></script>
<script language="JavaScript">
oImg1=new Image();
oImg2=new Image();
oImg1.src="images/img_02/6.gif";
oImg2.src="images/img_02/6_a.gif";

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
<div id="subtitle"><%=subTitle%>:</div>
<!--搜索开始-->
<!--表单开始-->
<div id="search_list_content">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <%   
   for(int i=0;i<elementsMap.size();i++){  
	   Map element=(Map)elementsMap.get(Integer.toString(i));
		 String type=element.get("type").toString();
		 String name=element.get("name").toString();
		 String Class=element.get("class").toString();  
		 String tag=element.get("tag").toString();
		 String tip=element.get("tip").toString();
		 String value=element.get("value").toString();
		   
	if(type.equals("text")){  
		String size=element.get("size").toString();
   %>
    <tr >  
      <td height="30" width="120" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
      <td align="left" class="bk02" width="200">
      <input  name="<%=name %>" id="<%=name %>" type="<%=type %>"  style="background-color:#F0EFEF;" class="txt_05" readonly value="<%=value %>" size="<%=size %>" /></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
    </tr>
    <%}else if(type.equals("select")){ %>
	    <tr >  
	      <td height="30" width="120" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
	      <td align="left" class="bk02" width="200">
	      <select name="<%=name %>" id="<%=name %>">
	       <%
	       List list=(List)request.getAttribute(name+"list");
	       %>   
	       <c:forEach items="<%=list%>" var="lists" varStatus="count">
	         <option value="${lists[0]}">${lists[1]}</option>  
	       </c:forEach>
	      </select>
	      </td>
	      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
	    </tr>
    <%   
       }else if(type.equals("selectT")) {
    	   String option=element.get("option").toString();
       %>
       <tr >  
	      <td height="30" width="120" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
	      <td align="left" class="bk02" width="200">
	      <select name="<%=name %>" id="<%=name %>">
	       <%
	         String optionArr[]=option.split(",");
	         for(int k=0;k<optionArr.length;k++){
	        	 String valueArr[]=optionArr[k].split("=");
	       %>   
	         <option value="<%=valueArr[1]%>"><%=valueArr[0]%></option>  
	       <%}%>   
	      </select>
	      </td>
	      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
	    </tr>
   <%}    
	 }
    %>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8">&nbsp;&nbsp;&nbsp;&nbsp;
      <input name="Submit2422" type="button" class="button_09" onclick="javascript:history.back(-1);" value="<%=buttonname %>" />
      </td>
      <td align="right">&nbsp;</td>
    </tr>
    <tr>
      <td height="10" colspan="2"></td>  
    </tr>   
  </table>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>