<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
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
<div id="top"></div>
<!--ǩ����ʾ����ʼ-->
<div id="order_form">
  <div id="title"><span class="font-14"><strong>���������ǩ����</strong></span></div>
  <c:forEach items="${signList}" var="ord" begin="${startIndex}" end="${startIndex + pageSize - 1}">
  <dl>
    <dt>
      <p><strong>${ord.fSignName}</strong></p>
      <h2>
        <h3><span  class="light-gray">(${ord.fproductName})</span></h3>
        <a href="SignServlet?orderID=${ord.fid}"  class="ico-tag-prefer">�鿴</a></h2>
    </dt>
    <dd><span  class="light-gray">${ord.fcreateTime}</span></dd>
  </dl>
  </c:forEach>
</div>
<!--ǩ����ʾ������-->
<div id="page">
  <!-- ��ҳ[[ -->
  <div class="page page-align-right">
    <div class="page-wrap">
      <div class="page-links"> <a href="#" class="page-previous page-previous-disabled">��һҳ</a> <span class="page-number"> <a href="#" class="on">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a href="#">6</a> <a href="#">7</a> <a href="#">8</a> <a href="#">9</a> <a href="#">��</a> <a href="#">28</a> </span> <a href="#" class="page-next">��һҳ</a> </div>
    </div>
  </div>  
  <!-- ��ҳ]] -->
</div>
</body>
</html>
