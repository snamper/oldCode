<%
/*
 * Copyright (c) 2010 Shanda Corporation. All rights reserved.
 *
 * Created on 2010-12-3.
 *
 * @author lujian.jay
 * 
 * 页面返回
 *
 * 此页面用于在用户支付后显示支付信息，例如成功或者失败
 */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.sdo.pay.client.util.*" %>
<%@ page import="com.sdo.pay.client.config.*" %>
<%@ page import="com.shanda.rsasign.*"  %>
<%
//获取参数
String _amount = request.getParameter("Amount");//订单金额
String _payAmount = request.getParameter("PayAmount");//实际支付金额
String _orderNo = request.getParameter("OrderNo");//商户订单号
String _serialNo = request.getParameter("serialno");//支付序列号
String _status = request.getParameter("Status");//支付状态 "01"表示成功
String _merchantNo = request.getParameter("MerchantNo");//商户号
String _payChannel = request.getParameter("PayChannel");//实际支付渠道
String _discount = request.getParameter("Discount");//实际折扣率
String _signType = request.getParameter("SignType");//签名方式。1-RSA 2-Md5
String _payTime = request.getParameter("PayTime");//支付时间
String _currencyType = request.getParameter("CurrencyType");//货币类型
String _productNo = request.getParameter("ProductNo");//产品编号
String _productDesc = new String(request.getParameter("ProductDesc").getBytes("ISO_8859_1"),"GBK");//产品描述
String _remark1 = new String(request.getParameter("Remark1").getBytes("ISO_8859_1"),"GBK");//产品备注1
String _remark2 = new String(request.getParameter("Remark2").getBytes("ISO_8859_1"),"GBK");//产品备注2
String _exInfo = new String(request.getParameter("ExInfo").getBytes("ISO_8859_1"),"GBK");//额外的返回信息
String _mac = request.getParameter("MAC");//签名字符串

boolean verifyResult=Sign.verifySign(_amount,_payAmount,_orderNo,_serialNo,_status
		,_merchantNo,_payChannel,_discount,_signType,_payTime,_currencyType
		,_productNo,_productDesc,_remark1,_remark2,_exInfo,_mac);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>支付结果</title>
        
        <style type="text/css">
	        body{text-align:center;}
	        .container{text-align:left;margin:2px auto;width:800px;}
	        input{display:block;}
	        .center{text-align:center;}
	        label{display:block;}
        </style>
    </head>
    <body>
    <div class = "container">
	<%
	if(verifyResult){
		if(_status.equals("01")){
			//更新成功订单
		}else{
			//处理失败订单
		}
	%>
	<h1>支付成功，请确认！</h1>
	<table align="center" width="350" cellpadding="5" cellspacing="0">
        <tr>
            <td class="font_content" align="right">订单号：</td>
            <td class="font_content" align="left"><%=_orderNo%></td>
        </tr>
		<tr>
           <td class="font_content" align="right">盛付通订单号：</td>
           <td class="font_content" align="left"><%=_serialNo%></td>
        </tr>
        <tr>
           <td class="font_content" align="right">实际付款总金额：</td>
           <td class="font_content" align="left"><%=_payAmount%></td>
        </tr>
		<tr>
           <td class="font_content" align="right">支付时间：</td>
           <td class="font_content" align="left"><%=_payTime%></td>
        </tr>
    </table>
	<%
	}else{
		out.print("Verify MAC failed, MAC:"+_mac+"<br/>");
	%>
		<h1>支付失败</h1>
	<%
	}
	%>
	</div>
    </body>
</html>
