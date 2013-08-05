<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ include file="/path.jsp" %>
<%
String showNotice = (String)request.getAttribute("showNotice");
String[] notices = showNotice.split(",a_A,");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>最新消息</title>

<link href="${path}/style/showNotice.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-image: url(/commonscan/images/showNoticep.jpg);background-repeat: no-repeat;background-posit;	background-position: center top;">
<table width="80%" border="0"  align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="100%" height="32" class="font5"> </td>
                      <td align="right" class="font5"></td>
                      <td width="4"></td>
                    </tr>
                    <tr>
                      <td  height="19" colspan="3" class="line01">&nbsp;</td>
                    </tr>
                </table></td>
              </tr>
            </table>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="40" align="center" class="bg_04" style="border-bottom:#CCCCCC 1px "><span class="font14"><strong><%=notices[3] %></strong></span></td>
                    </tr>
                    <tr>
                      <td height="30" align="center" class="font22">作者：<%=notices[1] %>&nbsp;&nbsp;发布时间：<%=notices[2] %></td>
                    </tr>
                    <tr>
                      <td height="18" align="center" class="font11"></td>
                    </tr>
                    <tr>
                      <td height="30" class="font21"><p><%=notices[4] %></p></td>
                    </tr>
                  </table></td>
                  <td>&nbsp;</td>
                </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="font9">
    <td height="30"  class="line01"> 2011 JRL-IMPv1.0   |  版权所有   </td>
  </tr>
</table>
</body>
</html>
