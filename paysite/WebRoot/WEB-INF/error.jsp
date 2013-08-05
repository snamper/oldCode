<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="GB2312" import="java.util.*,java.text.SimpleDateFormat"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
String datetime = tempDate.format(new java.util.Date());
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>${currentAgent.fname}</title>
<link href="images/style/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
a:link {
	color: #5173A6;
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #5173A6;
}
a:hover {
	text-decoration: underline;
	color: #5173A6;
}
a:active {
	text-decoration: none;
	color: #5173A6;
}
.style1 {color: #333333}
.style2 {
	color: #415267;
	font-weight: bold;
}
-->
</style></head>

<body>
<jsp:include page="../top.jsp"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="218" valign="top" bgcolor="#F6FAFD" class="card_left">
 </td>
    <td valign="top" bgcolor="#F6FAFD">
    <form action="sendEmailServlet" method="post" name="myform">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="486" align="left" valign="top" bgcolor="#FFFFFF" class="card_center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="12" height="32" bgcolor="#F9FBFD">&nbsp;</td>
            <td bgcolor="#F9FBFD" ><span class="font8">错误页面</span></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="15" rowspan="13">&nbsp;</td>
              <td height="15" colspan="3">&nbsp;</td>
              <td width="15" rowspan="13">&nbsp;</td>
            </tr>
            <tr>
              <td height="47" colspan="3"><span class="font7">错误页面</span></td>
              </tr>
            <tr>
              <td height="11" colspan="3" background="images/18.jpg"></td>
            </tr>
            <tr>
              <td width="235" height="34" align="right">&nbsp;</td>
              <td width="11">&nbsp;</td>
              <td width="911">&nbsp;</td>
            </tr>
			<tr>
				<td height="528" align="center" >
					<img src="images/error.gif" alt="" />
				</td>
			</tr>
           <tr>
              <td height="0" align="right"></td>
              <td height="15" colspan="3" style="padding-left:10px;"></td>
            </tr>
            <tr>
              <td colspan="3" align="right">&nbsp;</td>
              </tr>
            <tr>
              <td colspan="3"></td>
            </tr>
          </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="15" rowspan="4">&nbsp;</td>
              <td height="15" colspan="2"></td>
              <td width="16" rowspan="4">&nbsp;</td>
            </tr>
            <tr>
              <td height="11" colspan="2" background="images/19.jpg"></td>
            </tr>
            <tr>
              <td width="247" align="right">&nbsp;</td>
              <td width="909">&nbsp;</td>
            </tr>
            <tr>
              <td height="5" colspan="2" align="right"></td>
            </tr>
          </table></td>
      </tr>
    </table>
    </form>
      <table width="100%" height="8" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="9"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>