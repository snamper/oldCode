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
<!--ҳü��ʼ-->
<%out.println(request.getAttribute("headers")); %>
<!--ҳü����-->

<!--������ ��ʼ-->
<div id="column_A">
<div id="charges_list">
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images_news/28.jpg" width="1000" height="177" /></td>
  </tr>
</table>
<table width="980" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td height="32" align="center" background="agentindex/ai_${currentAgent.fid}/images/layout/01.jpg" bgcolor="#f4f4f4" class="col_A_font1">��Ʒ���� </td>
    <td align="center" background="agentindex/ai_${currentAgent.fid}/images/layout/01.jpg" bgcolor="#f4f4f4" class="col_A_font1"> ���� </td>
    <td align="center" background="agentindex/ai_${currentAgent.fid}/images/layout/01.jpg" bgcolor="#f4f4f4" class="col_A_font1">��ʾ˵��</td>
  </tr>
<c:forEach var="charges" items="${listch}" varStatus="in">
            <tr>
              <td <c:if test="${in.index % 2 ==0}"> class="bg_02" </c:if>  <c:if test="${in.index % 2 ==1}"> class="bg_03" </c:if>

height="32" align="center">
               <c:choose>
                           <c:when test="${charges[2] == '01'}">�����г�ֵ��</c:when>
                           <c:when test="${charges[2] == '02'}">ʢ��һ��ͨ</c:when>
                           <c:when test="${charges[2] == '03'}">����һ��ͨ</c:when>
                           <c:when test="${charges[2] == '04'}">��ͨ��ֵ��</c:when>
                           <c:when test="${charges[2] == '05'}">Q�ҿ�</c:when>
                           <c:when test="${charges[2] == '06'}">��;��Ϸ��</c:when>
                           <c:when test="${charges[2] == '07'}">����һ��ͨ</c:when>
                           <c:when test="${charges[2] == '08'}">����һ��ͨ</c:when>
                           <c:when test="${charges[2] == '09'}">����һ��ͨ</c:when>
                           <c:when test="${charges[2] == '10'}">ħ������㿨</c:when>
                           <c:when test="${charges[2] == '11'}">�Ѻ�һ��ͨ</c:when>
                           <c:when test="${charges[2] == '12'}">���ų�ֵ��</c:when>
                           <c:when test="${charges[2] == '14'}">����һ��ͨ</c:when>                          
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
<!--������ ����--> 
<!--ҳ�ſ�ʼ-->
<%out.println(request.getAttribute("footers")); %>
<!--ҳ�Ž���-->
</body>
</html>

