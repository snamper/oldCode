<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="headAndfoot.jsp"/> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${currentAgent.fname}</title>
<%--<link href="agentindex/ai_${currentAgent.fid}/images/style/master.css" rel="stylesheet" type="text/css" />--%>
<%--<link href="agentindex/ai_${currentAgent.fid}/images/style/layout.css" rel="stylesheet" type="text/css" />--%>
<link href="agentindex/ai_${currentAgent.fid}/images/style/login.css" rel="stylesheet" type="text/css" />
<script src="images_news/js/AC_RunActiveContent.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>admin/js/prototype.js"></script>  
<style type="text/css">
td {
	font-size: 13px;
	line-height: 1;
}
.font_title{
    font-size: 18px;
    font-weight:600;
}
.col_A_font6{
    font-size: 12px;
    color:dddddd;
    font-weight:600;
}
.col_A_font7{
    font-size: 12px;
    color:dddddd;
    font-weight:500;
}
<!--
.redColor {
	color: #F00;
}
-->
</style>
<script>
function isRegisterUser()
{
	var fid = document.getElementById("fid").value;
	var fname=document.getElementById("fname").value;
	var fpassword = document.getElementById("fpassword").value;
	var fpassword2 = document.getElementById("fpassword2").value;
	
	var femail = document.getElementById("femail").value;
	var fphone = document.getElementById("fphone").value;
	var fqq = document.getElementById("fqq").value;
	
	var checkcode_reg = document.getElementById("checkcode_reg").value;

	var patrnfaliasId=/^[A-Za-z0-9]+$/;
	var patrnfemail=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	var patrnftelphone=/^\d{n}$/;

	if(fid=="" || fid.length < 4 || fid.length>20 || !patrnfaliasId.exec(fid)){
		alert("��½�û�ID ΪӢ�Ļ�����,������4-20֮��");
		return false;
	}
	if(fname==""){
		alert("��������ʵ������");
		return false;
	}
	if(fpassword=="" || fpassword.length < 6 || fpassword.length>16){
		alert("��������Ϊ6-16��֮��");
		return false;
	}
	if(fpassword != fpassword2){
		alert("�����������벻һ��");
		return false;
	}

	if(femail==null || femail==""){
		alert("���䲻��Ϊ��");
		return false;
	}else{
       if(!patrnfemail.exec(femail)){
             alert("�����ʽ����ȷ");
             return false;
       }
	}
	if(fphone==null || fphone==""){
		alert("��ϵ�绰����Ϊ��");
		return false;
	}
	if(fqq==null || fqq==""){
		alert("QQ����Ϊ��");
		return false;  
	}
	if(checkcode_reg.length != 4){
		alert("��������ȷ��֤��");
		return false;
	}
	    new Ajax.Request('client.do?method=clientRegdit',{      
			asynchronous: true,
			method:'post',   
			parameters: "fid="+fid+"&fname="+fname+"&fpassword="+fpassword+"&femail="+femail+"&fphone="+fphone+"&fqq="+fqq+"&checkcode_reg="+checkcode_reg,  
			onComplete: function(request) {
			 var id=request.responseText;  
			    if(id.lastIndexOf("checkcode_error")!=-1){
				 alert("��֤�벻��ȷ��");
				   return;
			     }
			    if(id.lastIndexOf("fid_error")!=-1){
					 alert("�û��˺��Ѵ��ڣ����������룡");
					 return;
				  }
			   if(id.lastIndexOf("true")!=-1){
				    alert("ע��ɹ�������ϵ����Ϊ����ͨ��");
				    //����ע��ɹ���ʾ
				    document.getElementById("title_regdit").innerHTML="ע��ɹ�";
					document.getElementById("regdit").style.display="none";
					document.getElementById("regditok").style.display="block";
					document.getElementById("fidT").innerHTML=fid;
					document.getElementById("fnameT").innerHTML=fname;
					document.getElementById("fpasswordT").innerHTML=fpassword;
					document.getElementById("femailT").innerHTML=femail;
					document.getElementById("fphoneT").innerHTML=fphone;
					document.getElementById("fqqT").innerHTML=fqq;
			      } 
			    }  
			});
	//document.form1.submit();
}
</script>
</head>

<body>
<!--ҳü��ʼ-->
<%out.println(request.getAttribute("headers")); %>
<!--ҳü����-->
  <form action="agentPage.do?method=AddCilent" method="post" name="form1" >
<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td valign="bottom" class="line02"><img src="images_news/5_a.gif" width="276" height="44" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="30">&nbsp;</td>
    <td >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="20" colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td height="40" colspan="2" class="font_title" id="title_regdit">��дע����Ϣ</td>
      </tr>
    </table>
   <div id="regdit" style="display:block;font-size: 12px;color:#333333;">
        <table width="100%" border="0" cellspacing="15" cellpadding="0" >
          <tr>
            <td align="left" width="80" class="col_A_font6">��½�û�ID��</td>
             <td align="left"  width="180">
                  <input name="fid" id="fid" type="text" class="code_text" size="25" maxlength="40"/>
             </td>
              <td align="left" class="col_A_font7" id="fidDesc"><span class="redColor"></span>4~20���ַ���������ĸ�����֡��»��ߣ�����ĸ��ͷ����ĸ�����ֽ�β</td>
          </tr>
          
           <tr>
            <td align="left" width="100" class="col_A_font6">��ʵ������</td>
             <td align="left"  width="180">
                  <input name="fname" id="fname" type="text" class="code_text" size="15" maxlength="10"/>
             </td>
              <td align="left" class="col_A_font7"><span class="redColor"></span>����д������ʵ���˻�����</td>
           </tr>
          <tr>
            <td align="left" class="col_A_font6">���룺</td>
            <td align="left">
                 <input name="fpassword" id="fpassword" type="password" class="code_text" size="25" maxlength="16"/>
             </td>
             <td align="left" class="col_A_font7"><span class="redColor"></span>������6-16��Ӣ����ĸ�����ֻ�������</td>
          </tr>
          <tr>
              <td align="left" class="col_A_font6">ȷ�����룺</td>
              <td align="left">
                  <input name="fpassword2" id="fpassword2" type="password" class="code_text" size="25" maxlength="16"/>
              </td>
              <td align="left" class="col_A_font7"></td>
          </tr>
          <tr>
              <td align="left" class="col_A_font6">�����ַ��</td>
              <td align="left">
                  <input name="femail" id="femail" type="text" class="code_text"  />
              </td>
              <td align="left" class="col_A_font7">����д��ȷ�������ַ</td>
          </tr>
           <tr>
              <td align="left" class="col_A_font6">��ϵ�绰��</td>
              <td align="left">
                  <input name="fphone" id="fphone" type="text" class="code_text"  maxlength="15"/>
              </td>
              <td align="left" class="col_A_font7">����д��ʵ�绰���˵绰���ڽ�����һ����룬һ����д�����޸�</td>
          </tr>
           <tr>
              <td align="left" class="col_A_font6">QQ���룺</td>
              <td align="left">
                  <input name="fqq" id="fqq" type="text" class="code_text"  maxlength="15"/>
              </td>
              <td align="left" class="col_A_font7">����д��ʵQQ��</td>
          </tr>
          <tr>
             <td align="left" class="col_A_font6">��֤�룺</td>
             <td align="left">
                  <input name="checkcode_reg" id="checkcode_reg" type="text" class="code_text"  maxlength="6"/>
             </td>
             <td align="left" class="col_A_font7">
<%--                <img src="checkCode" width="60" height="20" onClick="this.src='checkcode?'+new Date().getTime();"; />--%>
                  <img src="checkCode" width="70" height="26" />
             </td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td align="left" class="col_A_font7"><input class="button_01" type="button" onclick="isRegisterUser();" value="���ע��"  /></td>
            <td height="20">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
      </table>
      </div>
<%--   ע��ɹ���ʾ   --%>
<div id="regditok" style="font-size:12px;color:#333333;display:none;">  
      <table width="100%" border="0" cellspacing="15" cellpadding="0" >
           <tr>
            <td align="left" width="80" class="col_A_font6">��½�û�ID��</td>
             <td align="left"  width="180" id="fidT">
             </td>
           </tr>
           <tr>
            <td align="left" width="100" class="col_A_font6">��ʵ������</td>
             <td align="left"  width="180" id="fnameT">
                 
             </td>
           </tr>
           <tr>
            <td align="left" class="col_A_font6">���룺</td>
            <td align="left" id="fpasswordT">
             </td>
           </tr>
           <tr>
              <td align="left" class="col_A_font6">�����ַ��</td>
              <td align="left" id="femailT">
              </td>
           </tr>
           <tr>
              <td align="left" class="col_A_font6">��ϵ�绰��</td>
              <td align="left" id="fphoneT">
              </td>
          </tr>
           <tr>
              <td align="left" class="col_A_font6">QQ���룺</td>
              <td align="left" id="fqqT">
              </td>
          </tr>
          <tr>
            <td height="20">&nbsp;</td>
            <td align="left" class="col_A_font7">
                 <input class="button_01" type="button" onclick="javascript:history.back(-1);" value="���ص�¼"  /></td>
            <td height="20">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
      </table>
      </div>
      </td>
    <td width="15">&nbsp;</td>
  </tr>
</table>
 </form>
</div>

<div style="display:none;" id="log">
<form id="form2" name="form2" method="post" action="loginServlet">
	<table width="1003" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="30">&nbsp;</td>
	    <td><table width="70%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20" height="20" colspan="2">&nbsp;</td>
	      </tr>
	      <tr>
	        <td height="40" colspan="2" class="col_A_font6">�û���¼</td>
	      </tr>
	    </table>
	        <table width="70%" border="0" cellspacing="15" cellpadding="0">
	          <tr>
	            <td align="left" class="col_A_font6">��½�ʺţ�
	             <input name="userName" id="username" type="text" class="code_text" size="35" /><span class="redColor">*</span>
	            </td>
	          </tr>
	          <tr>
	            <td align="left" class="col_A_font6">���롡����
		            <input name="passWord" id="password" type="password" class="code_text" size="35" />
		              <span class="redColor">*</span>
		              <input name="checkCode" id="checkCode" type="hidden" class="code_text" value="checkCode" size="35" />
	            </td>
	          </tr>
	          <tr>
	            <td align="left" class="col_A_font7"><input class="button_01" type="submit" value="��¼"  /></td>
	          </tr>
	      </table></td>
	     <td width="15">&nbsp;</td>
	    </tr>   
	  </table>
	</form>
</div>
<!--ҳ�ſ�ʼ-->
<%out.println(request.getAttribute("footers")); %>
<!--ҳ�Ž���-->  
<script>
<%
String resultInfo = request.getParameter("resultInfo");
if(resultInfo!=null){
	resultInfo=new String(resultInfo.getBytes("iso8859-1"), "utf-8");
}
if(!"".equals(resultInfo) && resultInfo != null){
%>
alert('<%=resultInfo%>');
<%}
String name = (String)request.getParameter("name");
String pwd = (String)request.getParameter("pwd");
if(!"".equals(name) && name!=null){
%>
document.getElementById("username").value='<%=name%>';
document.getElementById("password").value='<%=pwd%>'; 
document.getElementById("regdit").style.display="none";   
document.getElementById("log").style.display="block"; 
<%}%>
</script>
</body>
</html>
