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
<script type="text/javascript" src="images_news/js/date.js"></script>
<script language="JavaScript">
oImg1=new Image();
oImg2=new Image();
oImg1.src="agentindex/ai_${currentAgent.fid}/images/layout/1.gif";
oImg2.src="agentindex/ai_${currentAgent.fid}/images/layout/1_a.gif";
</script>
</head>

<body>
<!--ҳü��ʼ-->
<%out.println(request.getAttribute("headers")); %>
<!--ҳü����-->
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="14">&nbsp;</td>
    <td valign="bottom" class="line02"><img src="images_news/3_a.gif" width="256" height="44" /></td>
    <td class="line02"><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
    <form action="agentPage.do?method=openSerch" method="post" name="form1">
      <tr>
        <td width="20" align="center" class="col_A_font2"></td>
        <td>&nbsp;</td>
        <td width="40" align="center" class="col_A_font2">����</td>
        <td><input name="datatime" type="text" class="search_txt1" id="textfield23262" onfocus="HS_setDate(this)" value="${datatime}" size="10" /></td>
        <td width="9"></td>
        <td align="right"><select name="selectItem" class="login_txt1">
          <option value="fid">ϵͳ������</option>
          <option value="forderid" <c:if test="${selectItem=='forderid'}"> selected="selected" </c:if> >�̻�������</option>
        </select></td>
        <td width="9"></td>
        <td><input name="selectItemValue" value="${selectItemValue}" type="text" class="search_txt3" maxlength="25" /></td>
        <td width="9"></td>
        <td><input name="fcardid" type="text"  class="search_txt3" onfocus="if(value=='�����뿨�ţ�') {value=''}" onblur="if
(value=='') {value='�����뿨�ţ�'}"  <c:if test="${!empty fcardid}"> value="${fcardid}" </c:if>  <c:if test="${empty fcardid}"> value="�����뿨�ţ�" </c:if>  maxlength="30" /></td>
        <td width="9"></td>
        <td><input type="image" src="images_news/1_11.jpg" width="67" height="33" alt="" /></td>
      </tr>
      </form>
    </table></td>
    <td width="14">&nbsp;</td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20">&nbsp;</td>
  </tr>
</table>
<table width="963" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#77A0AB" class="font8">
  <tr>
    <td height="30" align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">���</td>
    <td align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">�ύʱ��</td>
    <td align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">����</td>
<%--    <td align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">������</td>--%>
    <td align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">��ֵ</td>
    <td align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">ʵ�ʽ��</td>
    <td align="center" background="images_news/25.jpg" bgcolor="#FFFFFF" class="font7">����״̬</td>
  </tr>
  <c:forEach items="${list}" var="fcard" varStatus="in">
  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >
    <td height="27" align="center">${in.index + 1}</td>
    <td align="center">${fcard[1]}</td>
    <td align="center">${fcard[2]}</td>
<%--    <td align="center">${fcard[3]}</td>--%>
    <td align="center">${fcard[3]}</td>
    <td align="center">${fcard[4]}</td>   
    <td align="center">
     <c:choose>
               <c:when test="${fcard[5] == '0'}">��濨</c:when>
                <c:when test="${fcard[5] == '1'}">�ȴ��ַ�</c:when>
                 <c:when test="${fcard[5] == '2'}">�ȴ���ֵ</c:when>
                  <c:when test="${fcard[5] == '3'}">�ַ��ɹ�</c:when>
                   <c:when test="${fcard[5] == '4'}">֧���ɹ�</c:when>
                    <c:when test="${fcard[5] == '5'}">֧��ʧ��</c:when>
                     <c:when test="${fcard[5] == '7'}">�ȴ���֤</c:when>
                      <c:when test="${fcard[5] == '8'}">�ֹ�ȷ��</c:when>
     </c:choose>
    </td>
    </tr>
  </c:forEach>
</table>

<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20">&nbsp;</td>
  </tr>
</table>

<%out.println(request.getAttribute("footers")); %>
<!--ҳ�Ž���-->


</body>
</html>
