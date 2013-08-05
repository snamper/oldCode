<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hnapay.gateway.client.enums.CharsetTypeEnum"%>
<%@page import="com.hnapay.gateway.client.java.ClientSignature"%>
<%@ page import="pay_common.AcceptOrder"%> 
<%@ page import="common.*"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
//
AcceptOrder ao = new AcceptOrder(); 
String sError = ao.checkOrder(request);
//
String path = request.getScheme() + "://" + request.getServerName()	+ ":" + request.getServerPort();	// "http://190.10.2.33:7001/"; 
if (request.getServerPort() == 80) path = request.getScheme() + "://" + request.getServerName();
//String return_url = path + "/ZfwebApi/sdo_PostBack.jsp"; 		//支付完成后跳转返回的网址URL
//String return_url = "http://superapi.95588.tw:9180/busics/wynotice.jsp";
// 
String aliorder=ao.getAliorder();
String version = "2.6";
String submitTime = fc.getTime("yyyyMMddHHmmss");
String serialID = "tianyuan" + submitTime;
String failureTime = fc.getTime("yyyyMMddHHmmss", 60 * 24 * 365);
String customerIP = "";

String s = fc.getpv(request, "money");
if (s.equals("")) s = "0";
float  n = Float.valueOf(s).floatValue() * 100;
String totalAmount= "" + Math.round(n);

String orderDetails = fc.getpv(request, "orderid") + "," + totalAmount + ","+aliorder+",网银支付,1";
String type = "1000";
String buyerMarked = "gai213@163.com";  
String payType = "BANK_B2C";
String orgCode = fc.getpv(request, "bankid");
String currencyCode = "1";
String directFlag = "1";
String borrowingMarked = "0";
String couponFlag = "1";
String platformID = "";
//String returnUrl = path + "/busics/wynotice.jsp?orderID=" + fc.getpv(request, "orderid");
String returnUrl = path + "/ZfwebApi/xinsheng_success.jsp"; 
String noticeUrl = path + "/ZfwebApi/xinsheng_notify.jsp"; 		//通知接收URL(本地测试时，服务器返回无法测试)
//String partnerID = "10000254514";	                  //商户ID
//String remark = "95588";                  
String pkey =ao.getPkey();
String partnerID =ao.getPartnerID();
String remark =ao.getRemark();

String charset = "1";
String signType = "2";  
//
System.out.println("[新生网银]returnUrl=" + returnUrl + ",noticeUrl=" + noticeUrl);
//
String signMsg = 
	"version="+version  
	 +"&serialID="+serialID
	 +"&submitTime="+submitTime
	 +"&failureTime="+failureTime
	 +"&customerIP="+customerIP
	 +"&orderDetails="+orderDetails
	 +"&totalAmount="+totalAmount
	 +"&type="+type
	 + "&buyerMarked="+buyerMarked
	 +"&payType="+payType
	 +"&orgCode="+orgCode
	 +"&currencyCode="+currencyCode
	 +"&directFlag="+directFlag
	 +"&borrowingMarked="+borrowingMarked
	 +"&couponFlag="+couponFlag
	 +"&platformID="+platformID
	 +"&returnUrl="+returnUrl
	 +"&noticeUrl="+noticeUrl
	 +"&partnerID="+partnerID
	 +"&remark="+remark
	 +"&charset="+charset
	 +"&signType="+signType;

if (!sError.equals("")){ 
	response.sendRedirect("error.jsp?s=" + java.net.URLEncoder.encode(sError));
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新生支付收银台</title>
</head>
<body>
	<form id = "payform" name="payform" action="https://www.hnapay.com/website/pay.htm" method="post">
				<input type="hidden" name="URLtest" size="50" value="http://qaapp.hnapay.com/website/pay.htm">
				<input type="hidden" name="URL" size="50" value="https://www.hnapay.com/website/pay.htm">
				<input type="hidden" name="version" value="<%=version%>">
				<input type="hidden" name="serialID" value="<%=serialID %>">
				<input type="hidden" name="submitTime" value="<%=submitTime %>">
				<input type="hidden" name="failureTime" value="<%=failureTime%>">
				<input type="hidden" name="customerIP"  value="<%=customerIP%>">
				<input type="hidden" size="80" name="orderDetails" value="<%=orderDetails %>">
				<input type="hidden" id='totalAmount' name="totalAmount" value="<%=totalAmount %>">
				<input type="hidden" name="type" value='<%=type%>'>
				<input type="hidden" name="buyerMarked" value="<%=buyerMarked%>">
				<input type="hidden" name="payType" value='<%=payType%>'>
				<input type="hidden" name="orgCode" value="<%=orgCode %>">
				<input type="hidden" name="currencyCode" value='<%=currencyCode%>'>
				<input type="hidden" name="directFlag" value='<%=directFlag%>'>
				<input type="hidden" name="borrowingMarked" value='<%=borrowingMarked%>'> 
				<input type="hidden" name="couponFlag" value='<%=couponFlag%>'>
				<input type="hidden" name="platformID" value = "<%=platformID%>">
				<input type="hidden" name="returnUrl" value="<%=returnUrl%>">
				<input type="hidden" name="noticeUrl" value="<%=noticeUrl %>">
				<input type="hidden" name="partnerID" value="<%=partnerID %>">
				<input type="hidden" name="remark" value='<%=remark %>'>
				<input type="hidden" name="charset" value='<%=charset%>'>
				<input type="hidden" name="signType" value='<%=signType%>'>
			<%
			if("2".equals(signType.toString())){
				//String pkey = "30819f300d06092a864886f70d010101050003818d0030818902818100bcc16df8c3fa5f3ae062af8962a8c8f31c2a787a81eb6abb0015c07fdd0ab56a1c6b864ee0f9a897013d868ba10fc63aca14430a789fe5d9f461e4e601ee46945f9ee41265f8e3df710f2284b19c0d1f4cd04ad49b322c9a62fb8664108e6b4e2dcb1c44d2b6b1f8b4ef7b06e1c27cbe4351ec54726491c2512b7d2412f49fe50203010001";
				signMsg = signMsg+"&pkey="+pkey;
				System.out.print("signMsg1="+signMsg);
				signMsg = ClientSignature.genSignByMD5(signMsg,CharsetTypeEnum.UTF8);
				System.out.print("signMsg="+signMsg);
			}  
			if("1".equals(signType.toString())){
				signMsg = ClientSignature.genSignByRSA(signMsg,CharsetTypeEnum.UTF8); 
			}	
			%>
			 <input type="hidden" name="signMsg" value="<%=signMsg%>"> 
			 <script   language=javascript>   
			  document.payform.submit();   
	  		 </script>
	</form>
  </body>
</html>