<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>�鿴��������</title>
<link href="/powermodule/images/style/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="margin-right:150px; margin-top:15px;">
<table width="508" height="68" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="4"><img src="/powermodule/images/007.gif" width="4" height="68" /></td>
    <td valign="top" background="/powermodule/images/009.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class="font5" style="padding-left:8px;">&nbsp;</td>
      </tr>
      <tr>
        <td height="34" class="font5" style="font-size:14px">�鿴��������</td>
      </tr>
    </table></td>
    <td width="4"><img src="/powermodule/images/008.gif" width="4" height="68" /></td>
  </tr>
  <tr>
    <td height="5" colspan="3"></td>
  </tr>
</table>
<table width="508" height="72" align="center" cellpadding="0" cellspacing="1" bgcolor="#D2DDE1" class="font6">
  <tr>
    <td width="79" align="right"  bgcolor="#80A0AC"  class="font27" height="35"> �û���: </td>
    <td width="419"  bgcolor="#FFFFFF" style="color:#000">${currentUser.fusername}</td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> ��&nbsp;&nbsp;��: </td>
    <td  bgcolor="#FFFFFF" style="color:#000">${currentUser.fsex}</td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> ��&nbsp;&nbsp;��: </td>
    <td   bgcolor="#FFFFFF" style="color:#000">${currentUser.fbirthday}</td>
  </tr>
   <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> ��&nbsp;&nbsp;��: </td>
    <td   bgcolor="#FFFFFF" style="color:#000">${currentUser.email}</td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> ��&nbsp;&nbsp;λ: </td>
    <td  bgcolor="#FFFFFF" style="color:#000"><c:forEach items="${currentPos}" var="position">
		${position.fpositionname}&nbsp;
    </c:forEach></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35">Google����: </td>
    <td   bgcolor="#FFFFFF" style="color:#000">${currentUser.fGoogleDayUrl}</td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35">�Զ�������: </td>
    <td   bgcolor="#FFFFFF" style="color:#000">${currentUser.fcustomUrl}</td>
  </tr>
</table>
</div>
</body>
</html>
