<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ page import="pay_kuaiqian.MD5Util"%> 
<%@ page import="pay_common.AcceptOrder"%> 
<%@ page import="common.*"%> 
<%
/**
 * @Description: ��Ǯ�����֧�����ؽӿڷ���
 * @Copyright (c) �Ϻ���Ǯ��Ϣ�������޹�˾
 * @version 2.0
 */

//����������˻���
///���¼��Ǯϵͳ��ȡ�û���ţ��û���ź��01��Ϊ����������˻��š�
  String merchantAcctId="1002136583301"; //"1002095991201";
//String merchantAcctId="1002112641401";


//�����������Կ
///���ִ�Сд.�����Ǯ��ϵ��ȡ
String key="XDKHS78XC4HT72AA"; 
//String key="52BYQZ3QNK3YUMCI"; 
//String key="BWZXU54DGMWZE5YG"; 

//�ַ���.�̶�ѡ��ֵ����Ϊ�ա�
///ֻ��ѡ��1��2��3.
///1����UTF-8; 2����GBK; 3����gb2312
///Ĭ��ֵΪ1
String inputCharset="3";


//����������֧������ĺ�̨��ַ.��[pageUrl]����ͬʱΪ�ա������Ǿ��Ե�ַ��
///��Ǯͨ�����������ӵķ�ʽ�����׽�����͵�[bgUrl]��Ӧ��ҳ���ַ�����̻�������ɺ������<result>���Ϊ1��ҳ���ת��<redirecturl>��Ӧ�ĵ�ַ��
///�����Ǯδ���յ�<redirecturl>��Ӧ�ĵ�ַ����Ǯ����֧�����GET��[pageUrl]��Ӧ��ҳ�档
String path = request.getScheme() + "://" + request.getServerName()	+ ":" + request.getServerPort();	// "http://190.10.2.33:7001/"; 
if (request.getServerPort() == 80) path = request.getScheme() + "://" + request.getServerName();
String bgUrl = path + "/ZfwebApi/kuaiqian_receive.jsp"; 		//֪ͨ����URL(���ز���ʱ�������������޷�����)
String return_url = path + "/ZfwebApi/kuaiqian_show.jsp"; 		//֧����ɺ���ת���ص���ַURL
	
//���ذ汾.�̶�ֵ
///��Ǯ����ݰ汾�������ö�Ӧ�Ľӿڴ������
///������汾�Ź̶�Ϊv2.0
String version="v2.0";

//��������.�̶�ѡ��ֵ��
///ֻ��ѡ��1��2��3
///1�������ģ�2����Ӣ��
///Ĭ��ֵΪ1
String language="1";

//ǩ������.�̶�ֵ
///1����MD5ǩ��
///��ǰ�汾�̶�Ϊ1
String signType="1";
   
//֧��������
///��Ϊ���Ļ�Ӣ���ַ�
String payerName="";

//֧������ϵ��ʽ����.�̶�ѡ��ֵ
///ֻ��ѡ��1
///1����Email
String payerContactType="1";

//֧������ϵ��ʽ
///ֻ��ѡ��Email���ֻ���
String payerContact="";

//�̻�������
///����ĸ�����֡���[-][_]���
//String orderId=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
String orderId= fc.getpv(request, "orderid");//new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

//�������
///�Է�Ϊ��λ����������������
///�ȷ�2������0.02Ԫ
String s = fc.getpv(request, "money");
float n = Float.valueOf(s).floatValue() * 100;
String orderAmount= "" + Math.round(n);
String bankId = fc.getpv(request, "bankid");
//�����ύʱ��
///14λ���֡���[4λ]��[2λ]��[2λ]ʱ[2λ]��[2λ]��[2λ]
///�磻20080101010101
String orderTime=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

//��Ʒ����
///��Ϊ���Ļ�Ӣ���ַ�
String productName="";

//��Ʒ����
///��Ϊ�գ��ǿ�ʱ����Ϊ����
String productNum="";

//��Ʒ����
///��Ϊ�ַ���������
String productId="";

//��Ʒ����
String productDesc="";
	
AcceptOrder ao = new AcceptOrder();
String sError = ao.checkOrder(request); 

//��չ�ֶ�1
///��֧��������ԭ�����ظ��̻�
String ext1=ao.aliorder;

//��չ�ֶ�2
///��֧��������ԭ�����ظ��̻�
String ext2=ao.alibody; 
	
//֧����ʽ.�̶�ѡ��ֵ
///ֻ��ѡ��00��10��11��12��13��14
///00�����֧��������֧��ҳ����ʾ��Ǯ֧�ֵĸ���֧����ʽ���Ƽ�ʹ�ã�10�����п�֧��������֧��ҳ��ֻ��ʾ���п�֧����.11���绰����֧��������֧��ҳ��ֻ��ʾ�绰֧����.12����Ǯ�˻�֧��������֧��ҳ��ֻ��ʾ��Ǯ�˻�֧����.13������֧��������֧��ҳ��ֻ��ʾ����֧����ʽ��
String payType="10";


//ͬһ������ֹ�ظ��ύ��־
///�̶�ѡ��ֵ�� 1��0
///1����ͬһ������ֻ�����ύ1�Σ�0��ʾͬһ��������û��֧���ɹ���ǰ���¿��ظ��ύ��Ρ�Ĭ��Ϊ0����ʵ�ﹺ�ﳵ�������̻�����0�������Ʒ���̻�����1
String redoFlag="0";

//��Ǯ�ĺ��������˻���
///��δ�Ϳ�Ǯǩ���������Э�飬����Ҫ��д������
String pid="";


	//���ɼ���ǩ����
	///����ذ�������˳��͹�����ɼ��ܴ���
	String signMsgVal="";
	signMsgVal=appendParam(signMsgVal,"inputCharset",inputCharset);  
	signMsgVal=appendParam(signMsgVal,"bgUrl",bgUrl);
	signMsgVal=appendParam(signMsgVal,"version",version);
	signMsgVal=appendParam(signMsgVal,"language",language);
	signMsgVal=appendParam(signMsgVal,"signType",signType);
	signMsgVal=appendParam(signMsgVal,"merchantAcctId",merchantAcctId);
	signMsgVal=appendParam(signMsgVal,"payerName",payerName);
	signMsgVal=appendParam(signMsgVal,"payerContactType",payerContactType);
	signMsgVal=appendParam(signMsgVal,"payerContact",payerContact);
	signMsgVal=appendParam(signMsgVal,"orderId",orderId);
	signMsgVal=appendParam(signMsgVal,"orderAmount",orderAmount);
	signMsgVal=appendParam(signMsgVal,"orderTime",orderTime);
	signMsgVal=appendParam(signMsgVal,"productName",productName);
	signMsgVal=appendParam(signMsgVal,"productNum",productNum);
	signMsgVal=appendParam(signMsgVal,"productId",productId);
	signMsgVal=appendParam(signMsgVal,"productDesc",productDesc);
	signMsgVal=appendParam(signMsgVal,"ext1",ext1);
	signMsgVal=appendParam(signMsgVal,"ext2",ext2);
	signMsgVal=appendParam(signMsgVal,"payType",payType);
	signMsgVal=appendParam(signMsgVal,"bankId",bankId);
	signMsgVal=appendParam(signMsgVal,"redoFlag",redoFlag);
	signMsgVal=appendParam(signMsgVal,"pid",pid);
	signMsgVal=appendParam(signMsgVal,"key",key);

String signMsg=MD5Util.md5Hex(signMsgVal.getBytes("gb2312")).toUpperCase();

%>
<%!
	//���ܺ�����������ֵ��Ϊ�յĲ�������ַ���
	public String appendParam(String returnStr,String paramId,String paramValue)
	{
			if(!returnStr.equals(""))
			{
				if(!paramValue.equals(""))
				{
					returnStr=returnStr+"&"+paramId+"="+paramValue;
				}
			}
			else
			{
				if(!paramValue.equals(""))
				{
				returnStr=paramId+"="+paramValue;
				}
			}	
			return returnStr;
	}
	//���ܺ�����������ֵ��Ϊ�յĲ�������ַ���������

%>


<!doctype html public "-//w3c//dtd html 4.0 transitional//en" >
<html>
	<head>
		<title>��ӭʹ������֧��,���Ժ�...</title>
		<meta http-equiv="content-type" content="text/html; charset=gb2312" >
	</head>
	
<BODY>
	

	<div align="center" style="font-size=12px;font-weight: bold;color=red;">
		<form name="kqPay" id="kqPay" action="https://www.99bill.com/gateway/recvMerchantInfoAction.htm" method="post">
			<input type="hidden" name="inputCharset" value="<%=inputCharset %>"/>
			<input type="hidden" name="bgUrl" value="<%=bgUrl %>"/>
			<input type="hidden" name="version" value="<%=version %>"/>
			<input type="hidden" name="language" value="<%=language %>"/>
			<input type="hidden" name="signType" value="<%=signType %>"/>
			<input type="hidden" name="signMsg" value="<%=signMsg %>"/>
			<input type="hidden" name="merchantAcctId" value="<%=merchantAcctId %>"/>
			<input type="hidden" name="payerName" value="<%=payerName %>"/>
			<input type="hidden" name="payerContactType" value="<%=payerContactType %>"/>
			<input type="hidden" name="payerContact" value="<%=payerContact %>"/>
			<input type="hidden" name="orderId" value="<%=orderId %>"/>
			<input type="hidden" name="orderAmount" value="<%=orderAmount %>"/>
			<input type="hidden" name="orderTime" value="<%=orderTime %>"/>
			<input type="hidden" name="productName" value="<%=productName %>"/>
			<input type="hidden" name="productNum" value="<%=productNum %>"/>
			<input type="hidden" name="productId" value="<%=productId %>"/>
			<input type="hidden" name="productDesc" value="<%=productDesc %>"/>
			<input type="hidden" name="ext1" value="<%=ext1 %>"/>
			<input type="hidden" name="ext2" value="<%=ext2 %>"/>
			<input type="hidden" name="payType" value="<%=payType %>"/>
			<input type="hidden" name="redoFlag" value="<%=redoFlag %>"/>
			<input type="hidden" name="pid" value="<%=pid %>"/>
			<input type="hidden" name="bankId" value="<%=bankId %>"/>
		</form>		
	</div>
	<%
		if (sError.equals("")){ %>
					
			<script   language=javascript>   
			  document.kqPay.submit();   
	  		</script>
		<%}else {%>					   <span class="font2">
				������Ϣ������:<%=sError %></span>												
		<%} %>
</BODY>
</HTML>