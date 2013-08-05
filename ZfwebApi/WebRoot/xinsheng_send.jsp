<%@page import="com.hnapay.gateway.client.enums.CharsetTypeEnum"%>
<%@page import="com.hnapay.gateway.client.java.ClientSignature"%>
<%@ page import="common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pay Page</title>
</head>
<body>
<form action="${param.URL}" method="post" name="orderForm">
	<input type="hidden" name="version"  value="${param.version}">
	<input type="hidden" name="serialID"  value="${param.serialID}">
	<input type="hidden" name="submitTime"  value="${param.submitTime}">
	<input type="hidden" name="failureTime"  value="${param.failureTime}">
	<input type="hidden" name="customerIP"  value="${param.customerIP}">
	<input type="hidden" name="orderDetails"  value="${param.orderDetails}">
	<input type="hidden" name="totalAmount"  value="${param.totalAmount}">
	<input type="hidden" name="type"  value="${param.type}">
	<input type="hidden" name="buyerMarked"  value="${param.buyerMarked}">
	<input type="hidden" name="payType"  value="${param.payType}">
	<input type="hidden" name="orgCode"  value="${param.orgCode}">
	<input type="hidden" name="currencyCode"  value="${param.currencyCode}">
	<input type="hidden" name="directFlag"  value="${param.directFlag}">
	<input type="hidden" name="borrowingMarked"  value="${param.borrowingMarked}">
	<input type="hidden" name="couponFlag"  value="${param.couponFlag}">
	<input type="hidden" name="platformID"  value="${param.platformID}">
	<input type="hidden" name="returnUrl"  value="${param.returnUrl}">
	<input type="hidden" name="noticeUrl"  value="${param.noticeUrl}">
	<input type="hidden" name="partnerID"  value="${param.partnerID}">
	<input type="hidden" name="remark"  value="${param.remark}">
	<input type="hidden" name="charset"  value="${param.charset}">
	
	<%

			String signType = request.getParameter("signType");
			String signMsg = request.getParameter("signMsg");
			if("2".equals(request.getParameter("signType").toString())){
				// pkey为商户的RSA公钥
				String pkey = "30819f300d06092a864886f70d010101050003818d0030818902818100bcc16df8c3fa5f3ae062af8962a8c8f31c2a787a81eb6abb0015c07fdd0ab56a1c6b864ee0f9a897013d868ba10fc63aca14430a789fe5d9f461e4e601ee46945f9ee41265f8e3df710f2284b19c0d1f4cd04ad49b322c9a62fb8664108e6b4e2dcb1c44d2b6b1f8b4ef7b06e1c27cbe4351ec54726491c2512b7d2412f49fe50203010001";
				signMsg = signMsg+"&pkey="+pkey;
				signMsg = ClientSignature.genSignByMD5(signMsg,CharsetTypeEnum.UTF8);
				//signMsg = common.fc.getMd5Str(signMsg, "utf-8");  
				
			}
			
			if("1".equals(request.getParameter("signType").toString())){
				/** RSA网关私钥加签   */
			signMsg = ClientSignature.genSignByRSA(signMsg,CharsetTypeEnum.UTF8); 
			}			
    %>
	<input type="hidden" name="signType"  value="<%= signType %>">
	<input type="hidden" name="signMsg"  value="<%= signMsg %>">
	
</form>
<script type="text/javascript">


document.orderForm.submit();

</script>
</body>
</html>