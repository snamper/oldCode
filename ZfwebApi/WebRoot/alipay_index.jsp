
<%
	/*
	���ܣ�֧��������ҳ�棬���ҳ����Լ��ɵ��̻���վ��ʵ�ʲ����Ĵ�����Ը���ҵ�����
	�ӿ����ƣ���׼��ʱ���˽ӿ�
	�汾��2.0
	���ڣ�2008-12-31
	���ߣ�֧������˾���۲�����֧���Ŷ�
	��ϵ��0571-26888888
	��Ȩ��֧������˾
	 */
%>
<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="common.*"%>
<%@ page import="pay_common.*"%>
<%@ page import="java.sql.ResultSet"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<link href="images/style/css.css" rel="stylesheet" type="text/css" />
<link href="images/style/menu.css" rel="stylesheet" type="text/css" />
		<title>֧����֧��--��ʱ֧���ӿ�</title>
		<script language="JavaScript">
		function alipayClick(){
			for(var   i=0;   i<alipay_direct.paymethod_1.length;   i++)     
      		{   
          	  if(alipay_direct.paymethod_1[i].checked){
			     alipay_direct.paymethod.value=alipay_direct.paymethod_1[i].value
			  }   
      		} 
			for(var   i=0;   i<alipay_direct.defaultbank_1.length;   i++)     
      		{   
          	  if(alipay_direct.defaultbank_1[i].checked){
			     alipay_direct.defaultbank.value=alipay_direct.defaultbank_1[i].value
			  }   
      		} 
		}
	    </script>
	    <style type="text/css">
<!--
.style1 {color: #990000}
-->
        </style>
	</head>
	<body>
 
		<form name="alipay_direct" action="alipayto.jsp" method="post" >
			<table width="100%" border="0">
				<tr>
					<th height="70" colspan="2" scope="col">
						<a href="http://www.alipay.com" target="_blank"><img
								src="images/logo.gif" border="0" align='left' /> </a>
					</th>
				</tr>
				<tr>
					<td colspan="2" height="2" bgcolor="#ff7300"></td>
				</tr>
				<tr>
                  <td height="10" colspan="2" align="left" nowrap>&nbsp;</td>
              </tr>
				<tr>
                  <td align="left" nowrap class="font17">���׶����ţ� </td>
                  <td>
                    <%
		                    AcceptOrder ao = new AcceptOrder();
                    String sError = ao.checkOrder(request); 
						%>
                    <input name="out_orderNo" type="text" class="link7" id="out_orderNo"
							value="<%=ao.out_orderNo%>" size="25" readonly />
                  </td>
			  </tr>
				<tr>
				  <td align="left" nowrap class="font17">���׽� </td>
					<td width="587" >
						<input readonly name="alimoney" type="text" class="link7" id="alimoney"  size="15" value="<%=ao.alimoney %>" >
					</td>
				</tr>
				<tr>
				  <td width="162" align="left" nowrap class="font17">�������ƣ� </td> 
					<td width="587">
						<input name="aliorder" type="text" class="link7" id="aliorder" value="<%=ao.aliorder %>" readonly>
					</td>
				</tr>
				<tr>
				  <td align="left" nowrap class="font17">���������� </td>
					<td width="587">
						<input name="alibody" type="text" class="link7" id="alibody" value="<%=ao.alibody %>" size="40" >
					</td>
				</tr>
				<tr>
				  <td align="left" nowrap class="font17"></td>
					<td width="587">
						<input type="hidden" name="show_good_url" id="show_good_url">
						<span style="color: #ff7300;"></span>
					</td>
				</tr>
				<tr>
				  <td align="left" nowrap class="font17">ѡ��Ĭ��֧��ѡ��� </td>
					<td width="587">
						<table class="font17">
							<tr>
								<td>
									<input type="radio" name="paymethod_1" value="directPay">
									֧�������֧��
								</td>
								<td>
									<input type="radio" name="paymethod_1" value="bankPay" checked>
									����֧��
								</td>
								<td>
									<input type="radio" name="paymethod_1" value="cartoon">
									֧������֧ͨ��
								</td>
							</tr>
						</table>
						<input type="hidden" name="paymethod" id="paymethod" />
					</td>
				</tr>

				<tr>
				  <td align="left" nowrap class="font17">ѡ��Ĭ�����У� </td>
					<td width="587">
						<table>
							<tr class="link7">
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="CMB" checked>
							  <img src="images/icon_zsyh_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="ICBCB2C">
							  <img src="images/icon_zggsyh_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="CCB">
							  <img src="images/icon_ccb_s.gif" border="0" />							  </td>
							</tr>
							<tr class="link7">
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="ABC">
							  <img src="images/icon_abc_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="SPDB">
							  <img src="images/icon_spdb_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="POSTGC">
							  <img src="images/POSTGC.jpg" border="0" />							  </td>
							</tr>
							<tr class="link7">
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="CITIC">
							  <img src="images/icon_itic_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="CIB">
							  <img src='images/index_38.gif' border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="GDB">
							  <img src="images/icon_gdb_s.gif" border="0" />							  </td>
							</tr>
							<tr class="link7">
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="SDB">
							  <img src="images/icon_sdb_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="CMBC">
							  <img src="images/icon_cmbc_s.gif" border="0" />							  </td>
								<td height="30" nowrap>
									<input type="radio" name="defaultbank_1" value="COMM">
							  <img src="images/icon_comm_s.gif" border="0" />							  </td>
							</tr>
					  </table>
						<input type="hidden" name="defaultbank" id="defaultbank" />
					</td>
				</tr>

				<tr>
				  <td height="50" align="left" nowrap class="font17">������ </td>
					<td>
					<%
					if (sError.equals("")){ %>
						<input name="submit" type="image" class="font17" 
							onClick="alipayClick()" value="֧������ʱ֧��" src="images/8_1.jpg" width="131" height="26" />					 
		<script   language=javascript>   
	  document.alipay_direct.submit();   
  		</script>
						<%}else {%>					   <span class="font2">
						������Ϣ������:<%=sError %></span>												
						<%} %>
					</td>
				</tr>
		  </table>
		</form>
		
	</body>
</html>
