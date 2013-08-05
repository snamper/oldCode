<%
/**
 * Copyright (c) 2010 Shanda Corporation. All rights reserved.
 *
 * Created on 2010-12-3.
 *
 * @author lujian.jay
 * 
 * 订单接入网关页面
 * 此页面用于向盛付通网关提交表单数据
 *
 */
%>

<%@page contentType="text/html" pageEncoding="gb2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*" %>
<%@page import="com.sdo.pay.client.util.*" %>
<%@page import="java.text.*" %>
<%@page import="com.sdo.pay.client.config.*" %>
<%@ page import="pay_common.AcceptOrder"%> 
<%@ page import="common.*"%>

<%
if(Configuration.getError() != null && Configuration.getError().trim().length() != 0) {
	out.print(Configuration.getError());
	Configuration.clearError();
	
	return;
}
%>

<%
//
AcceptOrder ao = new AcceptOrder();
String sError = ao.checkOrder(request); 

//
//String _orderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
//String _amount = "0.01";
//String _bankCode = "SDTBNK";
String _orderNo = fc.getpv(request, "orderid"); //new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
String _amount = fc.getpv(request, "money");
String _bankCode = fc.getpv(request, "bankid");
String _remark1 = ao.alibody;
String _remark2 = ao.alibody;
String paymentGateWayURL_ = "", payChannel_ = "";
if (_bankCode.equals("")){ 
	payChannel_ = "27";
	paymentGateWayURL_ = "http://netpay.sdo.com/paygate/default.aspx";	//非直联
}else{
	payChannel_ = "04";
	paymentGateWayURL_ = "http://netpay.sdo.com/paygate/ibankpay.aspx";	//直联
}
//
String path = request.getScheme() + "://" + request.getServerName()	+ ":" + request.getServerPort();	// "http://190.10.2.33:7001/"; 
if (request.getServerPort() == 80) path = request.getScheme() + "://" + request.getServerName();
String bgUrl = path + "/ZfwebApi/sdo_Notify.jsp"; 		//通知接收URL(本地测试时，服务器返回无法测试)
//String return_url = path + "/ZfwebApi/sdo_PostBack.jsp"; 		//支付完成后跳转返回的网址URL
String return_url = path + "/busics/wynotice.jsp";
//String return_url = "http://superapi.95588.tw:9180/busics/wynotice.jsp";
Configuration.notifyURL = bgUrl;
Configuration.postBackURL = return_url;
//
String _merchantNo = Configuration.merchantNo;
String _merchantUserId = "";
String _orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
String _productNo = "";
String _productDesc = ""; 
String _productURL = ""; 
String _mac = Sign.makeSign(payChannel_ , _amount,_orderNo , _merchantNo
		,_merchantUserId , _orderTime , _productNo , _productDesc
		,_remark1,_remark2,_bankCode,_productURL);
//

if (!sError.equals("")){ 
	response.sendRedirect("error.jsp?s=" + java.net.URLEncoder.encode(sError));
	return;
}

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=gb2312">
        <title>sdo.com</title>
    </head>
    <body>
        <form id="paymentSubmit" name="paymentSubmit" action="<%= paymentGateWayURL_ %>" method="post">
      		<input type="hidden" name="Amount" value="<%=_amount%>"/>
      		<input type="hidden" name="MerchantUserId" value="<%=_merchantUserId%>"/>
      		<input type="hidden" name="OrderNo" value="<%=_orderNo%>"/>
      		 <input type="hidden" name="OrderTime" value="<%=_orderTime%>"/>
      		<input type="hidden" name="ProductNo" value="<%=_productNo%>"/>
      		<input type="hidden" name="ProductDesc" value="<%=_productDesc%>"/>
      		<input type="hidden" name="Remark1" value="<%=_remark1%>"/>
      		<input type="hidden" name="Remark2" value="<%=_remark2%>"/>
      		<input type="hidden" name="ProductURL" value="<%=_productURL%>"/>
      		<input type="hidden" name="BankCode" value="<%=_bankCode%>"/>
         	<input type="hidden" name="Version" value="<%=Configuration.version%>"/>
            <input type="hidden" name="MerchantNo" value="<%=Configuration.merchantNo%>"/>
            <input type="hidden" name="PayChannel" value="<%=payChannel_%>"/>
            <input type="hidden" name="PostBackURL" value="<%=Configuration.postBackURL%>"/>
            <input type="hidden" name="NotifyURL" value="<%=Configuration.notifyURL%>"/>
            <input type="hidden" name="BackURL" value="<%=Configuration.backURL%>"/>
            <input type="hidden" name="CurrencyType" value="<%=Configuration.currencyType%>"/>
            <input type="hidden" name="NotifyURLType" value="<%=Configuration.notifyURLType%>"/>
            <input type="hidden" name="SignType" value="<%=Configuration.signType%>"/>
            <input type="hidden" name="DefaultChannel" value="<%=payChannel_%>"/>
            <input type="hidden" name="MAC" value="<%=_mac%>"/>
         </form>
    </body>
	<script   language=javascript>   
	  document.paymentSubmit.submit();   
	</script>
</html>
