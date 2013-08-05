<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/js_01/scrollings.js"></script>
<script language="JavaScript">
oImg1=new Image();
oImg2=new Image();
oImg1.src="images/img_02/6.gif";
oImg2.src="images/img_02/6_a.gif";
</script>
</head>
<body>
<!--标题开始-->
<div id="title">
  <div id="title_right">
    <h4>2010年8月30日 星期一</h4>
    <h5>14:38:00</h5>
  </div>
  <div id="title_left"></div>
</div>
<!--标题结束-->
<div id="subtitle">订单查询:</div>
<!--搜索开始-->
<div id="search">
<form action="<%=path%>/order.do?method=findOrderList" method="post">  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="bg_02">关键字：&nbsp;
          <input name="key" type="text" class="txt_01" value="Search" size="19" />
        &nbsp;类型&nbsp;
        <select name="type" class="txt_02">
          <option value="all">全部类型</option>
          <option value="fOrderID">订单号</option>
          <option value="p.fname">产品名称</option>
          <option value="fAccountID">帐号</option>  
        </select>
        
        <input name="Submit2" type="submit" class="search_button_02" value="  " />&nbsp;注册时间：开始&nbsp;
        <input name="starttime" type="text" class="txt_03" id=times onfocus=HS_setDate(this) value="2010-10-11" size="10" />
        &nbsp;至&nbsp;
        <input name="endtime" type="text" class="txt_03" id=times onfocus=HS_setDate(this) value="2010-10-11" size="10" />
        <label></label>
        <label> &nbsp;
             <input name="Submit" type="submit" class="search_button_01" value=" " />
        </label></td>
    </tr>
  </table>  
  </form>
</div>
<!--搜索结束-->
<!--表单开始-->
<div id="search_list">
  <div id="search_title">搜索结果</div>
  <div id="search_list_content">
  <!--嵌套搜索表单开始-->
  
<!--表单开始-->
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E5E5E5"  class="search_table_01">
  <tr>
    <td height="30" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">选择</td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">编号</td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">商户订单号</td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">产品名称 </td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">金额</td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">卡号</td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">帐号</td>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">下单时间</td>
  
  </tr>
   <c:forEach items="${orders}" var="order" varStatus="count">
   
	  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >
	    <td height="27" align="center" ><input type="checkbox" name="checkbox" value="checkbox" /></td>
	    <td align="center">${count.index+1}</td>
	    <td align="center">${order[2] } </td>
	    <td align="center">${order[3] } </td>
	    <td align="center">${order[5] } </td>
	    <td align="center">${order[1] }</td>
	    <td align="center">${order[4] }</td>
	    <td align="center">${order[0] }</td>
	  </tr>
 </c:forEach>
 
  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >
    <td height="27" colspan="2" align="left" >&nbsp;
        <input type="checkbox" name="checkbox82" value="checkbox" />
      全选</td>
    <td colspan="7" align="center">&nbsp;</td>
    <td align="center"><input name="Submit242" type="submit" class="button_06" value="删除" /></td>
  </tr>
</table>

 <table width="100%" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/main/images/19.jpg" class="bk01">
             	<%
       				 String url = request.getContextPath() + "/order.do?method=findOrderList&nopage=";
        		%>
                <tr>
                  <td height="43" align="left" class="font02">&nbsp;&nbsp;共 <span class="font01">${noCount}</span>条记录  |  分<span class="font01">${countPage}</span>页  |  每页 <span class="font01">15 </span>条  |  当前第 <span class="font01">${noPage}</span>页</td>
                  <td align="right">&nbsp;</td>
                  <td align="right" class="font02">转到:</td>
                  <td width="16" class="font02"><input name="nopage" id="switchnopage" type="text" class="txt_04" size="4" /></td>
                  <td align="center" class="font02">页
                  <script>
				      function goIn(){ 
				    	  var aa = document.getElementById("switchnopage").value;
				    	  window.location.href='<%=url %>'+aa;
				      }
     			  </script>
                    <input name="Submit222" type="submit" class="button_01" value="跳转" onclick="goIn();"/></td>
                  <td width="260" align="right">&nbsp;
                  	<c:choose>
                  		<c:when  test="${noPage == 1}">
	                      <input name="Submit24" type="submit" class="button_02" value="  " disabled="disabled"/>
                  		</c:when>
                  		<c:otherwise>
		                    <input name="Submit24" type="submit" class="button_02" value="  " onclick="javascript:window.location.href='<%=url%>1'" disabled="disabled"/>
                  		</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == 1}">
	                    	<input name="Submit22" type="submit" class="button_03" value="  " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit22" type="submit" class="button_03" value="  " onclick="javascript:window.location.href='<%=url %>${noPage-1}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == countPage}">
	                    	<input name="Submit23" type="submit" class="button_04" value=" " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit23" type="submit" class="button_04" value=" " onclick="javascript:window.location.href='<%=url %>${noPage+1}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == countPage}">
	                    	<input name="Submit25" type="submit" class="button_05" value=" " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit25" type="submit" class="button_05" value=" " onclick="javascript:window.location.href='<%=url %>${countPage}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp; </td>
                </tr>
              </table>
<!--表单结束-->
  <!--嵌套搜索表单结束-->
  </div>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>
