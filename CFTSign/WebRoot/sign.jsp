<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page language="java" import="com.tenpay.api.MicroBlogSendRequest" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>����ǩ��</title>
<link href="images/style/master.css" rel="stylesheet" type="text/css" />
<link charset="utf-8" type="text/css" rel="stylesheet" href="https://img.tenpay.com/v2/res_mch_open/css/global/tid_min.css">
</head>
<body>
<!--���岿�ֿ�ʼ-->
<div id="top"></div>
<!--ǩ����ʾ����ʼ-->
<div id="my_design" style="background-image: url(/CFTSign/cftsignBGPicture/${pgPic.fname}.jpg);">
  <div id="name"><img src="/CFTSign/cftsign/${signDate}/${order.fpictureName}.png" /></div>
</div>
<!--ǩ����ʾ������-->
<!--ǩ�����ÿ�ʼ-->
<div id="name_ctrl">
  <div id="name_ctrl_left">
    <div id="weibo">
    <%
 MicroBlogSendRequest microBlogRequest = (MicroBlogSendRequest)request.getAttribute("microBlogRequest");
 out.println(microBlogRequest.toHTML());
%>
    </div>
    <div id="pic_ctrl"><span class="button_02"><a href="SignServlet?orderID=${order.fid}&bgPriNum=${bgPriNum+1}">�任����</a></span><br class="clear"/>
    </div>
    <div class="clear"></div>
  </div>
  <div id="name_ctrl_right"><a href="DownloadFileServlet?picType=signPicture&fileName=${order.fpictureName}&bgPicName=${pgPic.fname}">����ǩ��ͼ</a>&nbsp;&nbsp;<a href="DownloadFileServlet?picType=sign&fileName=${order.fpictureName}">����ǩ��</a>&nbsp;&nbsp;<a href="#">�������</a></div>
  <div class="clear"></div>
</div>
<!--ǩ�����ý���-->
<!--���岿�ֽ���-->
</body>
</html>