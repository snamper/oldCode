<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
  <meta http-equiv="refresh" content="120">
    <title>资金监控台-jrlimp1.0</title>
<script src="/commonscan/js/popup_layer.js" type="text/javascript" language="javascript"></script>
<script src="/commonscan/js/jquery-1.4.2.js" type="text/javascript" language="javascript"></script>
<script  src="/commonscan/js/highcharts.js" type="text/javascript" language="javascript"></script>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
<link href="/commonscan/style/core.css" type="text/css" rel="stylesheet"/>
<link href="/commonscan/style/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/commonscan/js/NewTime.js"></script>
<script>
function autoGo(){
	window.location.reload(true);
	setTimeout('autoGo()',80000);
	}

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
</script>
  </head>
  <body style="FONT-FAMILY: 'arial'">
  <table width="100%">
  <tr>
  	<td><iframe src="/commonscan/GraphServlet?busiInfoID=21102121733533342001&charttype=single" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
  	</td>
  	<td><iframe src="/commonscan/GraphServlet?busiInfoID=21102121733533342002&charttype=single" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
  	</td>
  </tr>
  <tr>
  	<td><iframe src="/commonscan/GraphServlet?busiInfoID=21102121733533342003&charttype=single" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
  	</td>
  	<td><iframe src="/commonscan/GraphServlet?busiInfoID=21102121733533342004&charttype=single" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
  	</td>
  </tr>
  <tr>
  	<td><iframe src="/commonscan/GraphServlet?busiInfoID=21102121733533342005&charttype=single" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
  	</td>
  	<td><iframe src="/commonscan/GraphServlet?busiInfoID=21102121733533342006&charttype=single" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
  	</td>
  </tr>
  </table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="font999">
    <td height="30"  class="line01"> 2010 JRL-IMPv1.0   |  版权所有   </td>
  </tr>
</table>
  </body>
</html>
