<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%
	//String sInfo = new String((request.getParameter("e") + "").getBytes("iso8859-1"), "utf-8");
	String sInfo = request.getParameter("e");
	//sInfo = new   String(sInfo.getBytes( "ISO8859_1 "),   "GBK");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />

<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>页面超时</title>
<link href="style/css.css" rel="stylesheet" type="text/css" />
<link href="style/desktop_ltr.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="js/NewTime.js"></script>



<script>
</script>

</head>

<body style="FONT-FAMILY: 'arial'">
<div align="center">
  <table width="100%" height="100%" border="0" align="center">
    <tr>
      <td height="400" align="center" valign="middle"><table width="434" height="207" border="0">
        <tr>
          <td align="right" valign="bottom" background="/commonscan/images/error_.jpg"><table width="257" height="99" border="0" align="right">
            <tr>
              <td width="224" valign="top"><div align="center"><span
style='font-size:14.0pt; font-family:微软雅黑; color: #000000; font-weight: bold;'>&nbsp;页面访问超时或验证失败，请重新登录!</span></div></td>
              <td width="23" valign="top">&nbsp;</td>
            </tr>
          </table></td>
        </tr>
      </table>        </td>
    </tr>
  </table>
</div>
</body>
</html>
