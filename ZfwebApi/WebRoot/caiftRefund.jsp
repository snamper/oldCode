<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<%@ page import="com.tenpay.RequestHandler" %>
<%@ page import="com.tenpay.client.ClientResponseHandler" %>    
<%@ page import="com.tenpay.client.TenpayHttpClient" %>
<%@ page import="java.io.File" %>
<%@ page import="common.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    //�̻��� 
    String partner = "1212013101";

    //��Կ 
    String key = "7gtme5jm49coylbu2sircxccmbw2b46z";
    
    //������ѯ�������
    RequestHandler reqHandler = new RequestHandler(null, null);
    //ͨ�Ŷ���
    TenpayHttpClient httpClient = new TenpayHttpClient();
    //Ӧ�����
    ClientResponseHandler resHandler = new ClientResponseHandler();
    
    //-----------------------------
    //�����������
    //-----------------------------
    reqHandler.init();
    reqHandler.setKey(key);
    reqHandler.setGateUrl("https://mch.tenpay.com/refundapi/gateway/refund.xml");
    
    
    //
    String out_trade_no = fc.getpv(request, "out_trade_no");		//�ڲ�������
    String transaction_id = fc.getpv(request, "transaction_id");	//�Ƹ�ͨ������
    String total_fee = fc.getpv(request, "total_fee");				//�ܽ�� 
    String refund_fee = fc.getpv(request, "refund_fee");			//�˿���
    //
    float  n = Float.valueOf(total_fee).floatValue() * 100;
    total_fee= "" + Math.round(n);
    float  m = Float.valueOf(refund_fee).floatValue() * 100;
    refund_fee= "" + Math.round(m);

    //-----------------------------
    //���ýӿڲ��� 
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
    //����ͨ�Ų���
    //-----------------------------
    String sPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes");
	sPath = fc.replace(sPath, "default\\.\\tmp", "default\\tmp");
    //�������󷵻صĵȴ�ʱ��
    httpClient.setTimeOut(5);	
    //����ca֤��
    httpClient.setCaInfo(new File(sPath + "/com/tenpay/client/cacert.pem"));
		
    //���ø���(�̻�)֤��
    httpClient.setCertInfo(new File(sPath + "/com/tenpay/client/1212013101_20111018103417.pfx"), partner);
    
    //���÷�������POST
    httpClient.setMethod("POST");     
    
    //������������
    String requestUrl = reqHandler.getRequestURL();
    httpClient.setReqContent(requestUrl);
    String rescontent = "null";

    //��̨����
    if(httpClient.call()) {
    	//���ý������
    	rescontent = httpClient.getResContent();
    	resHandler.setContent(rescontent);
    	resHandler.setKey(key);
    	   	
    	//��ȡ���ز���
    	String retcode = resHandler.getParameter("retcode");
    	
    	//�ж�ǩ�������
    	if(resHandler.isTenpaySign()&& "0".equals(retcode)) {
    		out.println("�˿�ɹ�</br>");
    		

    	} else {
    		//����ʱ�����ؽ��δǩ������¼retcode��retmsg��ʧ�����顣
    		System.out.println("��֤ǩ��ʧ�ܻ�ҵ�����");
    		System.out.println("retcode:" + resHandler.getParameter("retcode")+
    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
    	    	out.println("retcode:" + resHandler.getParameter("retcode")+
    	    	                    " retmsg:" + resHandler.getParameter("retmsg"));
    	}	
    } else {
    	System.out.println("��̨����ͨ��ʧ��");   	
    	System.out.println(httpClient.getResponseCode());
    	System.out.println(httpClient.getErrInfo());
    	//�п�����Ϊ����ԭ�������Ѿ�������δ�յ�Ӧ��
    }
    
    //��ȡdebug��Ϣ,���������Ӧ�����ݡ�debug��Ϣ��ͨ�ŷ�����д����־�����㶨λ����
    //System.out.println("http res:" + httpClient.getResponseCode() + "," + httpClient.getErrInfo());
    //System.out.println("req url:" + requestUrl);
    //System.out.println("req debug:" + reqHandler.getDebugInfo());
    //System.out.println("res content:" + rescontent);
    //System.out.println("res debug:" + resHandler.getDebugInfo());
    
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>�˿��̨����ʾ��</title>
</head>
<body>
</body>
</html>
