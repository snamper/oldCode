<%@ page contentType="text/html; charset=gb2312" %>
<%@ page import="common.*"%>
<%
String s = common.fc.getpv(request, "s");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����֧��</title>
<link href="/orderfc/images/style/error.css" rel="stylesheet" type="text/css" />


<!--���ݿ�ʼ-->
<div id="centent_error" >
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="298" rowspan="8">&nbsp;</td>
      <td height="96">&nbsp;</td>
    </tr>
    <tr>
      <td height="28" class="ft_01"><%=s %> </td>
    </tr>
    <tr>
      <td height="2" class="ft_02"></td>
    </tr>
    <tr>
      <td height="15">&nbsp;</td>
    </tr>
    <tr>
      <td height="20" class="ft_03">��ܰ��ʾ��</td>
    </tr>
    <tr>
      <td height="20" class="ft_03">�����κ����⣬����ϵƽ̨�ͷ��� ���������Ĳ��㾴���½⡣ </td>
    </tr>
    <tr>
      <td height="32">&nbsp;</td>
    </tr>
    <tr>
      <td height="28"><label>
        <input name="Submit" type="submit" class="lk" value=" " />
      </label></td>
    </tr>
  </table>
</div>
<!--���ݽ���-->

