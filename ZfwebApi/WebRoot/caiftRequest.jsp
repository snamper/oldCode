<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.tenpay.util.TenpayUtil" %>
<%@ page import="com.tenpay.RequestHandler"%> 
<%@ page import="pay_common.*"%> 
<%@ page import="common.*"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//---------------------------------------------------------
//�Ƹ�ͨ����֧������ʾ�����̻����մ��ĵ����п�������
//---------------------------------------------------------
//�̻���
//String partner = "1212013101";
//��Կ
//String key = "7gtme5jm49coylbu2sircxccmbw2b46z";

AcceptOrder ao = new AcceptOrder(); 
String sError = ao.checkOrder(request);

String partner = ao.getPartnerID();
String key = ao.getPkey();

//ȡ�÷��ʵ�ַ
String path = request.getScheme() + "://" + request.getServerName()	+ ":" + request.getServerPort();	// "http://190.10.2.33:7001/"; 
if (request.getServerPort() == 80) path = request.getScheme() + "://" + request.getServerName();

//������ɺ���ת��URL
String return_url = path + "/ZfwebApi/caiftReturnUrl.jsp";
//���ղƸ�֪ͨͨ��URL
String notify_url = path + "/ZfwebApi/caiftNotifyUrl.jsp";

//---------------���ɶ����� ��ʼ------------------------
//��ǰʱ�� yyyyMMddHHmmss
String currTime = TenpayUtil.getCurrTime();
//8λ����
//String strTime = currTime.substring(8, currTime.length());
//��λ�����
//String strRandom = TenpayUtil.buildRandom(4) + "";
//10λ���к�,�������е�����
//String strReq = strTime + strRandom;
//�����ţ��˴���ʱ�����������ɣ��̻������Լ����������ֻҪ����ȫ��Ψһ����
//String out_trade_no = strReq;
//---------------���ɶ����� ����------------------------

//����֧���������
RequestHandler reqHandler = new RequestHandler(request, response);
reqHandler.init();
//������Կ
reqHandler.setKey(key);
reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");

//-----------------------------
//����֧������
//-----------------------------
//ת�����
String s = fc.getpv(request, "money");
float  n = Float.valueOf(s).floatValue() * 100;
String totalAmount= "" + Math.round(n);
//
System.out.println("[��������]out_orderNo=" + ao.out_orderNo + ",totalAmount=" + totalAmount + ",returnUrl=" + return_url + ",noticeUrl=" + notify_url);

//
reqHandler.setParameter("partner", partner);		        //�̻���
reqHandler.setParameter("out_trade_no", ao.out_orderNo);		//�̼Ҷ�����
reqHandler.setParameter("total_fee", totalAmount);			        //��Ʒ���,�Է�Ϊ��λ
reqHandler.setParameter("return_url", return_url);		    //������ɺ���ת��URL
reqHandler.setParameter("notify_url", notify_url);		    //���ղƸ�֪ͨͨ��URL
reqHandler.setParameter("body", ao.alibody);	            //��Ʒ����
reqHandler.setParameter("bank_type", "DEFAULT");		    //��������
reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());   //�û��Ĺ���ip
reqHandler.setParameter("fee_type", "1"); 

//ϵͳ��ѡ����
reqHandler.setParameter("sign_type", "MD5"); 
reqHandler.setParameter("service_version", "1.0");
reqHandler.setParameter("input_charset", "GBK");
reqHandler.setParameter("sign_key_index", "1");

//ҵ���ѡ����
reqHandler.setParameter("attach", ao.getRemark());
reqHandler.setParameter("product_fee", totalAmount);
reqHandler.setParameter("transport_fee", "0");
reqHandler.setParameter("time_start", currTime);
reqHandler.setParameter("time_expire", "");

reqHandler.setParameter("buyer_id", "");
reqHandler.setParameter("goods_tag", "");
//reqHandler.setParameter("agentid", "");
//reqHandler.setParameter("agent_type", "");

//�����url
String requestUrl = reqHandler.getRequestURL();

//��ȡdebug��Ϣ,����������debug��Ϣд����־�����㶨λ����
String debuginfo = reqHandler.getDebugInfo();

System.out.println("requestUrl:" + requestUrl);
System.out.println("debuginfo:" + debuginfo);

%>

<script>location.href='<%=requestUrl%>';</script>		  		

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�Ƹ�ͨ��ʱ���ʳ���...</title>
</head>
<body>
<br/><a target="_blank" href="<%=requestUrl %>">�Ƹ�֧ͨ��</a>
</body>
</html>
