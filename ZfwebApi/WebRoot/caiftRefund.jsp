<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ page import="com.tenpay.RequestHandler" %>
<%@ page import="com.tenpay.client.ClientResponseHandler" %>    
<%@ page import="com.tenpay.client.TenpayHttpClient" %>
<%@ page import="java.io.File" %>
<%@ page import="common.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    //商户号 
    String partner = "1212013101";

    //密钥 
    String key = "7gtme5jm49coylbu2sircxccmbw2b46z";
    
    //创建查询请求对象
    RequestHandler reqHandler = new RequestHandler(null, null);
    //通信对象
    TenpayHttpClient httpClient = new TenpayHttpClient();
    //应答对象
    ClientResponseHandler resHandler = new ClientResponseHandler();
    
    //-----------------------------
    //设置请求参数
    //-----------------------------
    reqHandler.init();
    reqHandler.setKey(key);
    reqHandler.setGateUrl("https://mch.tenpay.com/refundapi/gateway/refund.xml");
    
    
    //
    String out_trade_no = fc.getpv(request, "out_trade_no");		//内部订单号
    String transaction_id = fc.getpv(request, "transaction_id");	//财付通订单号
    String total_fee = fc.getpv(request, "total_fee");				//总金额 
    String refund_fee = fc.getpv(request, "refund_fee");			//退款金额
    //
    float  n = Float.valueOf(total_fee).floatValue() * 100;
    total_fee= "" + Math.round(n);
    float  m = Float.valueOf(refund_fee).floatValue() * 100;
    refund_fee= "" + Math.round(m);

    //-----------------------------
    //设置接口参数 
    //-----------------------------
    reqHandler.setParameter("partner", partner);	
    reqHandler.setParameter("out_trade_no", out_trade_no);	
    reqHandler.setParameter("transaction_id", transaction_id);
    reqHandler.setParameter("out_refund_no", transaction_id);	
    reqHandler.setParameter("total_fee", total_fee);	
    reqHandler.setParameter("refund_fee", refund_fee);
    reqHandler.setParameter("op_user_id", partner);	
    reqHandler.setParameter("op_user_passwd", "7reH6NBv");	 
    reqHandler.setParameter("rec_acc_truename", "");	
    reqHandler.setParameter("recv_user_id", "");	
    reqHandler.setParameter("reccv_user_name", "");
    //-----------------------------
    //设置通信参数
    //-----------------------------
    String sPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");
	sPath = fc.replace(sPath, "default\\.\\tmp", "default\\tmp");
    //设置请求返回的等待时间
    httpClient.setTimeOut(5);	
    //设置ca证书
    httpClient.setCaInfo(new File(sPath + "/com/tenpay/client/cacert.pem"));
		
    //设置个人(商户)证书
    httpClient.setCertInfo(new File(sPath + "/com/tenpay/client/1212013101_20111018103417.pfx"), partner);
    
    //设置发送类型POST
    httpClient.setMethod("POST");     
    
    //设置请求内容
    String requestUrl = reqHandler.getRequestURL();
    httpClient.setReqContent(requestUrl);
    String rescontent = "null";

    //后台调用
    if(httpClient.call()) {
    	//设置结果参数
    	rescontent = httpClient.getResContent();
    	resHandler.setContent(rescontent);
    	resHandler.setKey(key);
    	   	
    	//获取返回参数
    	String retcode = resHandler.getParameter("retcode");
    	
    	//判断签名及结果
    	if(resHandler.isTenpaySign()&& "0".equals(retcode)) {
    		out.println("退款成功</br>");
    		

    	} else {
    		//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
    		System.out.println("验证签名失败或业务错误");
    		System.out.println("retcode:" + resHandler.getParameter("retcode")+
    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
    	    	out.println("retcode:" + resHandler.getParameter("retcode")+
    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
    	}	
    } else {
    	System.out.println("后台调用通信失败");   	
    	System.out.println(httpClient.getResponseCode());
    	System.out.println(httpClient.getErrInfo());
    	//有可能因为网络原因，请求已经处理，但未收到应答。
    }
    
    //获取debug信息,建议把请求、应答内容、debug信息，通信返回码写入日志，方便定位问题
    //System.out.println("http res:" + httpClient.getResponseCode() + "," + httpClient.getErrInfo());
    //System.out.println("req url:" + requestUrl);
    //System.out.println("req debug:" + reqHandler.getDebugInfo());
    //System.out.println("res content:" + rescontent);
    //System.out.println("res debug:" + resHandler.getDebugInfo());
    
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>退款后台调用示例</title>
</head>
<body>
</body>
</html>
