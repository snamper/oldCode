
<%
	/*
	���ƣ���������з�����֪ͨҳ��
	���ܣ�������֪ͨ���أ�������ֵ���������Ƽ�ʹ��
	�汾��2.0
	���ڣ�2008-12-31
	���ߣ�֧������˾���۲�����֧���Ŷ�
	��ϵ��0571-26888888
	��Ȩ��֧������˾
	 */
%>
<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="com.alipay.config.*"%>
<%@ page import="common.*"%>
<%@ page import="pay_common.*"%>  
<%
	//---------------------------------------
	String get_body = request.getParameter("body");
	System.out.print("[����֪ͨ]body=" + get_body);
	//
	String partner = "";
	String privateKey = "";
	if (get_body.equals("֧���ӿ�")){ 
		partner = AlipayConfig.partnerID2; //֧�����������id (�˻�����ȡ)
		privateKey = AlipayConfig.key2; //֧������ȫУ����(�˻�����ȡ)
	}else{
		partner = AlipayConfig.partnerID1; //֧�����������id (�˻�����ȡ) 
		privateKey = AlipayConfig.key1; //֧������ȫУ����(�˻�����ȡ) 
	}
//**********************************************************************************
//�������������֧��https����������ʹ��http����֤��ѯ��ַ
/*ע�������ע�ͣ�����ڲ��Ե�ʱ����response���ڿ�ֵ��������뽫����һ��ע�ͣ�������һ����֤���ӣ������鱾�ض˿ڣ�
  �뵲��80����443�˿�*/
//String alipayNotifyURL = "https://www.alipay.com/cooperate/gateway.do?service=notify_verify"
String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
		+ "partner="
		+ partner
		+ "&notify_id="
		+ request.getParameter("notify_id");
//**********************************************************************************

//��ȡ֧����ATN���ؽ����true����ȷ�Ķ�����Ϣ��false ����Ч��
String responseTxt = CheckURL.check(alipayNotifyURL);
System.out.print("[����֪ͨ]" + responseTxt);

Map params = new HashMap();
//���POST �����������õ��µ�params��
Map requestParams = request.getParameterMap();
for (Iterator iter = requestParams.keySet().iterator(); iter
		.hasNext();) {
	String name = (String) iter.next();
	String[] values = (String[]) requestParams.get(name);
	String valueStr = "";
	for (int i = 0; i < values.length; i++) {
		valueStr = (i == values.length - 1) ? valueStr + values[i]
				: valueStr + values[i] + ",";
	}
	/*����������δ����ڳ�������ʱʹ��,���ǲ�һ���ܽ�����е����⣬���Խ���д������ʵ�ֱ�����ơ�
	���mysign��sign�����Ҳ����ʹ����δ���ת��*/
	//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "GBK"); //������
	params.put(name, valueStr);
}

String mysign = com.alipay.util.SignatureHelper.sign(params,
		privateKey);
	
	//-----------------------------------------------
	
	if (mysign.equals(request.getParameter("sign"))
			&& responseTxt.equals("true")) {
		/*�����ڲ�ͬ״̬�»�ȡ������Ϣ�������̻����ݿ�ʹ����ͬ��*/
		AcceptResult ar = new AcceptResult();
		if (funcejb.getres(request, "trade_status").equals("WAIT_BUYER_PAY")) {//WAIT_BUYER_PAY  �ȴ���Ҹ���
			String s = ar.acceptResult(request, response);
			out.println(s);//�������һ��Ҫ�ǲ�����HTML���Ե�success 
		    System.out.print("[��������]" + s);
		}else
		if (funcejb.getres(request, "trade_status").equals("TRADE_FINISHED") || funcejb.getres(request, "trade_status").equals("TRADE_SUCCESS")) {//TRADE_FINISHED �������
			String s = ar.acceptResult(request, response);
			//���������д�����ݴ���,
			out.println(s);//�������һ��Ҫ�ǲ�����HTML���Ե�success
		    System.out.print("[��������]" + s);
		}else
			System.out.println("δ֪״̬:" + funcejb.getres(request, "trade_status"));	//aa.destroy();
	} else {
		out.println("fail" + "<br>");
		//��ӡ���յ���Ϣ�ȶ�sign�ļ������ʹ�������sign�Ƿ�ƥ��
		System.out.print(mysign + "--------------------"
				+ funcejb.getres(request, "sign") + "<br>");
	    System.out.print("[��������]fail");
	}
%>
