<%
/*
 * Copyright (c) 2010 Shanda Corporation. All rights reserved.
 *
 * Created on 2010-12-3.
 *
 * @author lujian.jay
 * 
 * 服务器端通知
 *
 */
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.sdo.pay.client.util.*" %>
<%@ page import="com.sdo.pay.client.config.*" %>
<%@ page import="com.shanda.rsasign.*"  %>
<%@ page import="pay_common.AcceptResult"%> 
<%@ page import="common.*"%>
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
String _productDesc = new String(request.getParameter("ProductDesc").getBytes("ISO_8859_1"),"UTF-8");//产品描述
String _remark1 = fc.getpv(request, "Remark1");//new String(request.getParameter("Remark1").getBytes("ISO_8859_1"),"UTF-8");//产品备注1
String _remark2 = fc.getpv(request, "Remark2");//new String(request.getParameter("Remark2").getBytes("ISO_8859_1"),"UTF-8");//产品备注2
String _exInfo = new String(request.getParameter("ExInfo").getBytes("ISO_8859_1"),"UTF-8");//额外的返回信息
String _mac = request.getParameter("MAC");//签名字符串

boolean verifyResult=Sign.verifySign(_amount,_payAmount,_orderNo,_serialNo,_status
		,_merchantNo,_payChannel,_discount,_signType,_payTime,_currencyType
		,_productNo,_productDesc,_remark1,_remark2,_exInfo,_mac);

if(verifyResult){
	AcceptResult ar = new AcceptResult();

	///接着进行支付结果判断
	String s = ar.acceptResult(request, response);
	if (s.equals("success")){
		//报告给快钱处理结果，并提供将要重定向的地址。
		out.print("OK");
	}else{
		out.print("ERROR");
	}

//	if(_status.equals("01")){
//		//更新成功订单
//	}else{
//		//处理失败订单
//	}
}else{
	out.print("verify signature error,mac:"+_mac+"<br/>");
}
%>