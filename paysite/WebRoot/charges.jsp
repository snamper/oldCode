<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<jsp:include page="headAndfoot.jsp"/>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>${currentAgent.fname}</title>

<link href="agentindex/ai_${currentAgent.fid}/images/style/master.css" rel="stylesheet" type="text/css" />
<link href="agentindex/ai_${currentAgent.fid}/images/style/layout.css" rel="stylesheet" type="text/css" />
<script src="images_news/js/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
<!--页眉开始-->
<%out.println(request.getAttribute("headers")); %>
<!--页眉结束-->

<!--内容区 开始-->
<div id="column_A">
<div id="charges_list">
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images_news/28.jpg" width="1000" height="177" /></td>
  </tr>
</table>
<table width="980" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td height="32" align="center" background="agentindex/ai_${currentAgent.fid}/images/layout/01.jpg" bgcolor="#f4f4f4" class="col_A_font1">商品类型 </td>
    <td align="center" background="agentindex/ai_${currentAgent.fid}/images/layout/01.jpg" bgcolor="#f4f4f4" class="col_A_font1"> 费率 </td>
    <td align="center" background="agentindex/ai_${currentAgent.fid}/images/layout/01.jpg" bgcolor="#f4f4f4" class="col_A_font1">显示说明</td>
  </tr>
<c:forEach var="charges" items="${listch}" varStatus="in">
            <tr>
              <td <c:if test="${in.index % 2 ==0}"> class="bg_02" </c:if>  <c:if test="${in.index % 2 ==1}"> class="bg_03" </c:if>

height="32" align="center">
               <c:choose>
                           <c:when test="${charges[2] == '01'}">神州行充值卡</c:when>
                           <c:when test="${charges[2] == '02'}">盛大一卡通</c:when>
                           <c:when test="${charges[2] == '03'}">骏网一卡通</c:when>
                           <c:when test="${charges[2] == '04'}">联通充值卡</c:when>
                           <c:when test="${charges[2] == '05'}">Q币卡</c:when>
                           <c:when test="${charges[2] == '06'}">征途游戏卡</c:when>
                           <c:when test="${charges[2] == '07'}">久游一卡通</c:when>
                           <c:when test="${charges[2] == '08'}">完美一卡通</c:when>
                           <c:when test="${charges[2] == '09'}">网易一卡通</c:when>
                           <c:when test="${charges[2] == '10'}">魔兽世界点卡</c:when>
                           <c:when test="${charges[2] == '11'}">搜狐一卡通</c:when>
                           <c:when test="${charges[2] == '12'}">电信充值卡</c:when>
                           <c:when test="${charges[2] == '14'}">蓝港一卡通</c:when>                          
              </c:choose>       
              </td>
              <td <c:if test="${in.index % 2 ==0}"> class="bg_02" </c:if>  <c:if test="${in.index % 2 ==1}"> class="bg_03" </c:if>

align="center">${charges[3]}</td>
              <td <c:if test="${in.index % 2 ==0}"> class="bg_02" </c:if>  <c:if test="${in.index % 2 ==1}"> class="bg_03" </c:if>

align="center">desc</td>    
            </tr>
            </c:forEach>
</table>
<div class="clear"></div>
</div>
</div>
<!--内容区 结束--> 
<!--页脚开始-->
<%out.println(request.getAttribute("footers")); %>
<!--页脚结束-->
</body>
</html>

