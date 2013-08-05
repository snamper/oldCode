<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="/powermodule/images/style/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/powermodule/images/js/jquery-1.4.2.js" type="text/javascript"></script>
<script>
function SetWinHeight(obj)
{
    var win=obj;
    if (document.getElementById)
    {
        if (win && !window.opera)
        {
            if (win.contentDocument && win.contentDocument.body.offsetHeight)

                win.height = win.contentDocument.body.offsetHeight;
            else if(win.Document && win.Document.body.scrollHeight)
                win.height = win.Document.body.scrollHeight;
        }
    }
}

$(document).ready(function(){
	var loadUrl = "/ReadNotice/NoticeServlet?nmid=1&"+Math.random();
	$("#divLoadNotices").load(loadUrl);
});

</script>
</head>
<body>
<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="406" height="32" class="font5"> 我的桌面  </td>
                  <td align="right" class="font5"> </td>
                  <td width="4"></td>
                </tr>
                <tr>
                  <td height="1" colspan="3" class="line01">&nbsp;</td>
                </tr>
            </table></td>
          </tr>
        </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="" valign="top">
            <c:forEach items="${listdt}" var="dt">
            <div style=" margin:4px 0px 0px 0px;"><div style=" padding:10px 0px 10px 0px;">${dt.ftitle }</div><iframe onload="Javascript:SetWinHeight(this)"  width="100%" scrolling="no" height="100" frameborder="0" src="${dt.furl}&dataidForUpLoad=noUploadButten_12!@"></iframe></div>
            </c:forEach>
            </td>
            <td>&nbsp;</td>
            <td width="250" valign="top" class="bg"><div id="divLoadNotices"></div></td>
          </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
