<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改个人资料</title>
<style type="text/css">
.font27 {
	color: #FFFFFF;
	font-size: 14px;
}
</style>
<script language="javascript" src="/powermodule/images/js/NewTime.js"></script>
</head>
<body>
<div style="margin-right:150px;">
<table width="508" height="68" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="4"><img src="/powermodule/images/007.gif" width="4" height="68" /></td>
    <td valign="top" background="/powermodule/images/009.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class="font5" style="padding-left:8px;">&nbsp;</td>
      </tr>
      <tr>
        <td height="34" class="font5" style="font-size:14px">修改个人资料</td>
      </tr>
    </table></td>
    <td width="4"><img src="/powermodule/images/008.gif" width="4" height="68" /></td>
  </tr>
  <tr>
    <td height="5" colspan="3"></td>
  </tr>
</table>
<table width="508" height="72" align="center" cellpadding="0" cellspacing="1" bgcolor="#D2DDE1" class="font6">
<form action="user.do?method=updateUser" method="post" name="form1">
  <tr>
    <td width="79" align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 用户名: </td>
    <td width="419"  bgcolor="#FFFFFF"><label>
      <input  type="text" value="${currentUser.fusername}" name="username" id="username" style="border: 1px solid #666;" />
    </label></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 性&nbsp;&nbsp;别: </td>
    <td  bgcolor="#FFFFFF"><label>
      <input type="text" value="${currentUser.fsex}" name="sex" id="sex" style="border: 1px solid #666;" />
    </label></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 生&nbsp;&nbsp;日: </td>
    <td   bgcolor="#FFFFFF"><label>
      <input type="text" value="${currentUser.fbirthday}" name="birthday" id="birthday" style="border: 1px solid #666;" onClick="setday(this,birthday);" />
    </label></td>
  </tr>
    <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 邮&nbsp;&nbsp;箱: </td>
    <td  bgcolor="#FFFFFF"><label>
      <input type="text" value="${currentUser.email}" name="email" id="email" style="border: 1px solid #666;" />
    </label></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35">Google日历: </td>
    <td  bgcolor="#FFFFFF"><label>
      <input type="text" value="${currentUser.fGoogleDayUrl}" name="fGoogleDayUrl" id="fGoogleDayUrl" style="border: 1px solid #666;" />
    </label></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35">自定义链接: </td>
    <td  bgcolor="#FFFFFF"><label>
      <input type="text" value="${currentUser.fcustomUrl}" name="fcustomUrl" id="fcustomUrl" style="border: 1px solid #666;" />
    </label></td>
  </tr>
  <tr>
    <td align="right"   bgcolor="#FFFFFF" class="font27" height="35">&nbsp;</td>
    <td  bgcolor="#FFFFFF"><label>
      <input type="submit"  value="提交">
    </label></td>
  </tr>
</form>
</table>
</div>
</body>
</html>
