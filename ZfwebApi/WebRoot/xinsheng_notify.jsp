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
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.hnapay.gateway.client.enums.CharsetTypeEnum"%>
<%@ page import="com.hnapay.gateway.client.java.ClientSignature"%>
<%@ page import="pay_common.AcceptResult"%> 
<%@ page import="common.*"%>
<%
//获取参数    
String orderID = request.getParameter("orderID");//订单金额   
String resultCode = request.getParameter("resultCode");//实际支付金额
String stateCode = request.getParameter("stateCode");//商户订单号
String orderAmount = request.getParameter("orderAmount");//支付序列号
String payAmount = request.getParameter("payAmount");//支付状态 "01"表示成功
String acquiringTime = request.getParameter("acquiringTime");//商户号
String completeTime = request.getParameter("completeTime");//实际支付渠道
String orderNo = request.getParameter("orderNo");//实际折扣率
String partnerID = request.getParameter("partnerID");//签名方式。1-RSA 2-Md5
String remark =   request.getParameter("remark");//支付时间
String charset =  request.getParameter("charset");//货币类型
String signType = request.getParameter("signType");//
String signMsg =  request.getParameter("signMsg");//
//
String  sSource = "orderID=" + orderID
		+ "&resultCode=" + resultCode 
		+ "&stateCode=" + stateCode 
		+ "&orderAmount=" + orderAmount 
		+ "&payAmount=" + payAmount 
		+ "&acquiringTime=" + acquiringTime 
		+ "&completeTime=" + completeTime 
		+ "&orderNo=" + orderNo 
		+ "&partnerID=" + partnerID 
		+ "&remark=" + remark 
		+ "&charset=" + charset 
		+ "&signType=" + signType; 
        System.out.print("[新生通知]" + sSource);
		// pkey为商户的RSA公钥
		String pkey ="";
		if(remark!=null){ 
			if(remark.equals("dhtx")){
				pkey = "30819f300d06092a864886f70d010101050003818d0030818902818100ae677e899841c16317d258103305734e5d180d033950634d86be40efbb01daa4d6b471694d6408ec6daa3d67534cb0589ed3c7db3c10e6f3c977a3bbd77050b30cd1d6a0b35c9a8abba8897b15ea5e1adfa8b11d0fa62f295fc73c416042b3d9cbd5fcee2ba9cdb9f2768fcbda5564f04f8e479322c65cf8cfe56aa5587eb0bb0203010001";	
			}else if(remark.equals("ruida")){
				pkey = "30819f300d06092a864886f70d010101050003818d0030818902818100bcc16df8c3fa5f3ae062af8962a8c8f31c2a787a81eb6abb0015c07fdd0ab56a1c6b864ee0f9a897013d868ba10fc63aca14430a789fe5d9f461e4e601ee46945f9ee41265f8e3df710f2284b19c0d1f4cd04ad49b322c9a62fb8664108e6b4e2dcb1c44d2b6b1f8b4ef7b06e1c27cbe4351ec54726491c2512b7d2412f49fe50203010001";
			}else if(remark.equals("diandou")){
				pkey = "30819f300d06092a864886f70d010101050003818d0030818902818100ae677e899841c16317d258103305734e5d180d033950634d86be40efbb01daa4d6b471694d6408ec6daa3d67534cb0589ed3c7db3c10e6f3c977a3bbd77050b30cd1d6a0b35c9a8abba8897b15ea5e1adfa8b11d0fa62f295fc73c416042b3d9cbd5fcee2ba9cdb9f2768fcbda5564f04f8e479322c65cf8cfe56aa5587eb0bb0203010001";
			}else if(remark.equals("baidou")){
				pkey = "30819f300d06092a864886f70d010101050003818d0030818902818100f0afd5975719794e9762981294cb39a78188ca8a2df5e2dcbee16c3d8fbe2d40b231082474550a37ff9657b5e923dbebab1e065a561e3d518512a7ae0a74d2c5aa37ea6591b2a49ed8e4c294dd6cd4ffeeb4f5f787b253c0db846b9f6954730f9c8d5d2f79f4e3f2f1e52b3d0091e50a9638f85a8e8e346646b82de012302df70203010001";
			}
		}
		sSource = sSource+"&pkey="+pkey;   
		String sMd5 = ClientSignature.genSignByMD5(sSource,CharsetTypeEnum.UTF8);
        if(sMd5.equals(signMsg)){
		    AcceptResult ar = new AcceptResult();
		    //接着进行支付结果判断
		    String s = ar.acceptResult(request, response);  
		    if (s.equals("success")){
				//报告给快钱处理结果，并提供将要重定向的地址。 
				System.out.print("success=========================================");
				out.print("支付成功");
		    }else{
				response.sendError(500, "error1");  
				return;
		  	}
		}else{
			System.out.print("success2=========================================");
			//response.sendError(500, "error2");
			return;
		}
%>