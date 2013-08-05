<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>密码修改</title>
<style type="text/css">
.font27 {
	color: #FFFFFF;
	font-size: 14px;
}
</style>
<script>
function check()
{
	var a1=document.getElementById("old").value;
	var a2=document.getElementById("new1").value;
	var a3=document.getElementById("new2").value;
	if(a1=="" || a2=="" || a3==""){alert("请输入值!");return false;}
	if(a2!=a3){alert("两次输入密码不一致!");return false;}
	return true;
}
</script>
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
        <td height="34" class="font5" style="font-size:14px">更改登录密码</td>
      </tr>
    </table></td>
    <td width="4"><img src="/powermodule/images/008.gif" width="4" height="68" /></td>
  </tr>
  <tr>
    <td height="5" colspan="3"></td>
  </tr>
</table>
<table width="508" height="72" align="center" cellpadding="0" cellspacing="1" bgcolor="#D2DDE1" class="font6">
<form action="/powermodule/user.do?method=updatePassword" method="post" name="form1" onSubmit=" return check();">
  <tr>
    <td width="79" align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 旧密码: </td>
    <td width="419"  bgcolor="#FFFFFF"><label>
      <input  type="password"  name="old" id="old"  style="border: 1px solid #666;"/>

    </label></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 新密码: </td>
    <td  bgcolor="#FFFFFF"><label>
      <input type="password" name="new1" id="new1" style="border: 1px solid #666;" />
    </label></td>
  </tr>
  <tr>
    <td align="right"  bgcolor="#80A0AC"  class="font27" height="35"> 确认新密码: </td>
    <td   bgcolor="#FFFFFF"><label>
      <input type="password"  name="new2" id="new2" style="border: 1px solid #666;" />
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
