
<%
	/*
	���ܣ�19pay�����ύ
	 */
%>
<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ page import="common.*"%>
<%@ page import="java.sql.ResultSet"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gbk">
	</head>
	<title>19pay����֧��</title>
<body>
<%
	String sOrderID = request.getParameter("orderid");
	String sMoney = request.getParameter("money");
	String sError = "";
	String sign = request.getParameter("sign");
	if (sOrderID == null) sOrderID = "";
	if (sign == null) sign = "";
	
	//md5��֤
	if (!sign.equals(funcejb.getMd5Str(sOrderID + sMoney + "4CFN4WY9DH773W7895H6W366D386HS4436")) || (sOrderID.equals("")))
		sError = "�Ϸ�����֤ʧ��";
	else{
		//���ͻ�ID�Ƿ�Ϸ�
		DataConnect dc = new DataConnect("fillcard", false);
		String sql = "select id from fillcard where id = '" + sOrderID + "' and fState = 'δ��֤' ";
		ResultSet rs = dc.query(sql);
		if (rs != null){
			if (rs.next()){
				
			}else
				sError = "������֤ʧ��";
		}else
			sError = "������֤ʧ��";
		 
		dc.CloseResultSet(rs);
		dc.CloseConnect();
	}
	
	//�����˳�
	if (!sError.equals("")){
		out.print("������Ϣ������:" + sError);
		return;
	}


	//19PAY����
	String merchant_id = "7840";  
	String merchant_key = "123456789";
	String path = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort();	// "http://190.10.2.33:7001/"; 
	String url_r = path + "/ZfwebApi/WebPayResult";
	String url_n = path + "/ZfwebApi/WebPayNotice";;
	System.out.print("�ӿ�֪ͨ��ַ:" + url_n);
	String sURL = "http://114.255.7.208/page/bussOrder.do";
	String verifystring = "version_id=2.00&merchant_id=" + merchant_id + "&order_date=" + common.funcejb.getTime("yyyyMMdd") 
		+ "&order_id=" + sOrderID + "&amount=" + sMoney + "&currency=RMB&returl=" + url_r + "&pm_id=BC&pc_id=&merchant_key=" + merchant_key;
	String sParam = "version_id=2.00"
		+ "&merchant_id=" + merchant_id  								//�̻�����
		+ "&verifystring=" + common.funcejb.getMd5Str(verifystring)		//��֤ժҪ��
		+ "&order_date=" + common.funcejb.getTime("yyyyMMdd")			//��������
		+ "&order_id=" + sOrderID
		+ "&amount=" + sMoney
		+ "&currency=RMB"
		+ "&returl=" + url_r
		+ "&notify_url=" + url_n 
		+ "&pm_id=BC"
		+ "&pc_id="
		+ "&order_pname="
		+ "&user_name="
		+ "&user_phone="
		+ "&user_mobile=" 
		+ "&user_email=";
%>
			

<script>location.href='<%=sURL%>?<%=sParam%>';</script>";		

</body>
</html>
