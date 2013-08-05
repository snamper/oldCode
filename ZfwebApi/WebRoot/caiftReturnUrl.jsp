<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ page import="com.tenpay.ResponseHandler"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//---------------------------------------------------------
//财付通支付应答（处理回调）示例，商户按照此文档进行开发即可
//---------------------------------------------------------
//密钥
String key = "7gtme5jm49coylbu2sircxccmbw2b46z";

//创建支付应答对象
ResponseHandler resHandler = new ResponseHandler(request, response);
resHandler.setKey(key);

//判断签名
if(resHandler.isTenpaySign()) {

        //通知id
	String notify_id = resHandler.getParameter("notify_id");
	
	//商户订单号
	String out_trade_no = resHandler.getParameter("out_trade_no");
	
	//财付通订单号
	String transaction_id = resHandler.getParameter("transaction_id");
	
	//金额,以分为单位
	String total_fee = resHandler.getParameter("total_fee");
	
	//如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
	String discount = resHandler.getParameter("discount");
	
	//支付结果
	String trade_state = resHandler.getParameter("trade_state");
	
	String trade_mode = resHandler.getParameter("trade_mode");
	
	if( "0".equals(trade_state) && "1".equals(trade_mode) ) {
		//------------------------------
		//处理业务开始
		//------------------------------ 
		
		//注意交易单不要重复处理
		//注意判断返回金额
		
		//------------------------------
		//处理业务完毕
		//------------------------------
	        out.println("支付成功");	
	} else {
		//当做不成功处理
		out.println("支付失败");
	}
	
} else {

	out.println("认证签名失败");
}
//获取debug信息,建议把debug信息写入日志，方便定位问题
String debuginfo = resHandler.getDebugInfo();
System.out.println("debuginfo:" + debuginfo);
%>
