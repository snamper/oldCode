<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<jsp:include page="headAndfoot.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>${currentAgent.fname}</title>
<link href="agentindex/ai_${currentAgent.fid}/images/style/master.css" rel="stylesheet" type="text/css" />
<link href="agentindex/ai_${currentAgent.fid}/images/style/layout.css" rel="stylesheet" type="text/css" />
<script src="images_news/js/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
<!--ҳü��ʼ-->
<%out.println(request.getAttribute("headers")); %>
<!--ҳü����-->
<div id="column_A">
<table width="990" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" bgcolor="#FDFDFD" class="bk"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="font3">
      <tr>
        <td height="45"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50" valign="top"></td>
            <td align="left" class="link12">�����ڵ�λ�ã���ҳ &gt; �������� </td>
          </tr>
        </table></td>
      </tr>
    </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="50" align="center" bgcolor="#F8F8F8" class="col_A_font9">���Ƶ���Ϸ��Ӫ�̷���</td>
          </tr>
          <tr>
            <td align="center"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="line_02">
              <tr>
                <td width="15" height="25">&nbsp;</td>
                <td width="400">&nbsp;</td>
                <td align="right">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td height="20"></td>
          </tr>
        </table>
        <table width="96%" border="0" align="center" cellpadding="12" cellspacing="0" class="service_tab_content">
          <tr>
            <td align="left" valign="top" class="col_A_font10"><p >              1.��ݽ��� ע���̻��ɰ�Сʱ�ύ�������룬���㵱ǰδ�����ʵ�ʿ����ʱ�䣺1Сʱ���������ʹ�÷�:����1Ԫ</p>
              <p>2.��Ч�ͷ� ƽ̨��ŵ���ǵĿͷ�QQ��7*24Сʱ����Ϊ���ṩ���񡢽�����ʡ���������5�����ڽ�����������10�����ڽ�����������30�����ڽ�������ɿ������س��⣩��ͬʱ�û�������ʱ�������ǵĿͷ�ר�߽�����ѯ</p>
              <p>3.���ݱ��� ƽ̨���ڵڶ���Ϊ�û��ṩ��ϸ�����ݱ����������������������֧���ɹ���ʧ�ܵľ���ʱ���˵��</p>
              <p>4.��ѯϵͳ ƽ̨��ŵ���û��ṩ���Ƶ�ҳ���ѯϵͳ�������û���ʱ��ѯ֧�����</p>
              <p>5.�۸���ϵ ƽ̨��ʹ�ø����ļ۸���ϵ</p>
              <p>6.��ȫרҵ ƽ̨����������֧��ϵͳ��������רҵ�ļ������飬��֤���׻����İ�ȫ���ȶ���Ч��</p>
              <p><br />
              </p>
              <p>֧��ƽ̨������ǿ��ļ����Ŷӣ�������򵥻������ݻ���֧�����񣬺̵ܶ�ʱ������ɶԶ����Ĵ������û�����ȷ����Ϣ��ͬʱ�Ը��ȶ�����ȫ�ļ����ֶ����̼�����Ϸ��ӪѸ�ٷ�չ����Խ��Խ�������½�ʡ�̼ҵ�ʱ���뾫�����ﵽ�����Ӯ���� </p>
              <p>֧��ƽ̨��ŵ�û����ǵĿ���֧����ͬ��ƽ̨�н��������ߵģ�ͬʱƽ̨���᲻�ϵ��Ƴ�����֧����ʽΪ�û��ṩ����Ч�ķ���<br />
              </p></td>
          </tr>
        </table>
        <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="30" colspan="4" align="right"><a href="javascript:void(0);" class="col_A_link3" onclick="javascript:window.close();">���رմ�ҳ�桿</a></td>
          </tr>
      </table></td>
  </tr>
</table>
</div>

<!--������ ��ʼ-->

<!--������ ����-->

<!--ҳ�ſ�ʼ-->
<%out.println(request.getAttribute("footers")); %>
<!--ҳ�Ž���-->


</body>
</html>
