
<%
	/*
	���ƣ���������ת��ҳ��
	���ܣ�ͬ���������ع��ܣ������׳��ֵ���.
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

		<%
			String get_order = "";
			//			
			String get_body = fc.getpv(request, "body");
			System.out.print("[����֪ͨ]body=" + get_body);

			//
			String partner = "";
			String privateKey = "";
			if (get_body.equals("֧���ӿ�")){ 
				partner = AlipayConfig.partnerID2; 	//֧�����������id (�˻�����ȡ)
				privateKey = AlipayConfig.key2; 	//֧������ȫУ����(�˻�����ȡ)
			}else{
				partner = AlipayConfig.partnerID1; 	//֧�����������id (�˻�����ȡ)
				privateKey = AlipayConfig.key1; 	//֧������ȫУ����(�˻�����ȡ)
			}
				
			//**********************************************************************************
			//�������������֧��https����������ʹ��http����֤��ѯ��ַ
			/*ע�������ע�ͣ�����ڲ��Ե�ʱ����response���ڿ�ֵ��������뽫����һ��ע�ͣ�������һ����֤���ӣ������鱾�ض˿ڣ�
			 �뵲��80����443�˿�*/
			//String alipayNotifyURL = "https://www.alipay.com/cooperate/gateway.do?service=notify_verify";
			String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
					+ "partner="
					+ partner
					+ "&notify_id="
					+ request.getParameter("notify_id");
			//**********************************************************************************
			//��ȡ֧�������ܴ�
			String sign = request.getParameter("sign");
			//��ȡ֧����ATN���ؽ����true����ȷ�Ķ�����Ϣ��false ����Ч��
			String responseTxt = CheckURL.check(alipayNotifyURL);

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
				//����������δ����ڳ�������ʱʹ�á����mysign��sign�����Ҳ����ʹ����δ���ת���������Ѿ�ʹ�ã�
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}

			String mysign = com.alipay.util.SignatureHelper_return.sign(params,
					privateKey);

			if (mysign.equals(request.getParameter("sign"))
					&& responseTxt.equals("true")) {
				//������������첽����һ����д�����ݴ���
				//�����������ʱ���ã�����ɾ��
				get_order = request.getParameter("out_trade_no");
				String get_total_fee = request.getParameter("total_fee");
				String get_subject = new String(request.getParameter("subject")
						.getBytes("ISO-8859-1"), "gbk");
				get_body = new String(request.getParameter("body")
						.getBytes("ISO-8859-1"), "gbk");
				//
				
				if (get_body.equals("֧���ӿ�")){ 
					
					%>
					
					<script>location.href='http://superapi.95588.tw:9180/busics/wynotice.jsp?orderID=<%=get_order%>'</script>
					<% 
					return;
				}

				//��ʱ��������Ҫ�жϸ������׵�״̬----WAIT_BUYER_PAY -->TRADE_FINISHED�����Բο�notify��
				if (request.getParameter("trade_status").equals(
						"TRADE_FINISHED")
						|| request.getParameter("trade_status").equals(
								"TRADE_SUCCESS")) {
					// ����Ѿ����������ɣ�����Ķ���״̬
					//�˷��ط�ʽ�� ֻ���ٿͻ�����ɹ�֮�󷵻ء�
				%>
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title></title>
	</head>
	<body>
		<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>

		<table width="450" height="300" border="0" align="center">
			<tr>
				<td valign="top" background="images/AliPay.gif">
					<table width="444" border="0">
						<tr>
							<td height="42">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">���Ķ�����Ϊ:<B
									style="FONT-SIZE: 18px"> <%=get_order%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">�����۸�:<B
									style="FONT-SIZE: 18px"> <%=get_total_fee%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">��Ʒ����:<B
									style="FONT-SIZE: 18px"> <%=get_subject%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">��Ʒ����:<B
									style="FONT-SIZE: 18px"> <%=get_body%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<div align="center">
									<span class="style1"><font size="+3">����ɹ�!</font>
									</span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%
				}
			} else {
				//����ʱ���ã�����ɾ��
				//out.println("��ʾ������Ϣ");
				//��ӡ���յ���Ϣ�ȶ�sign�ļ������ʹ�������sign�Ƿ�ƥ��
				//out.println(mysign + "--------------------" + sign + "<br>");
				//out.println("responseTxt=" + responseTxt + "<br>");
				//out.println("����ʧ��");
				%>
		<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>

		<table width="450" height="300" border="0" align="center">
			<tr>
				<td valign="top" background="images/AliPay.gif">
					<table width="444" border="0">
						<tr>
							<td height="42">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">mysign=<B
									style="FONT-SIZE: 18px"> <%=mysign%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">sign=<B
									style="FONT-SIZE: 18px"> <%=sign%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">responseTxt=<B
									style="FONT-SIZE: 18px"> <%=responseTxt%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<div align="center">
									<span class="style1"><font size="+3">����ʧ��!</font>
									</span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%
			}

			
			
			
			
			
			
			
			
			
			
		%>




	</body>
</html>