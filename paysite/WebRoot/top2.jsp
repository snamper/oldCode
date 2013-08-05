<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="GB2312" import="java.util.*,java.text.SimpleDateFormat"%>
<%
	HttpSession sessions=request.getSession();
	String username=(String)sessions.getAttribute("username");
	String staffsname = (String)sessions.getAttribute("isStaffsLogin");
	if(staffsname != null && !"".equals(staffsname)){
		username = staffsname;
	}
	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	String datetime = tempDate.format(new java.util.Date());
 %>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="FILTER:  progid:DXImageTransform.Microsoft.Gradient (gradientType=0,startColorStr=${currentAgent.fstartColor},endColorStr=${currentAgent.fendColor})" >
  <tr>
    <td width="15" >&nbsp;</td>
    <td width="342"><a href="index.jsp"><img src="agentindex/ai_${currentAgent.fid}/images/logo.png" alt="" border="0" /></a></td>
    <td >&nbsp;</td>
    <td width="573" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr style="color:#000;font-size: 12px;">
        <td height="35" align="right">尊敬的   <strong><%=username %></strong> 您已经成功登录!</td>
        <td width="30" align="right"><img src="images/1.gif" width="17" height="20" /></td>
        <td width="96" align="right" class="font1"><a href="LoginOutServlet" class="link3"><span style="color: #000">退出</span></a> | <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${currentAgent.fserviceQq}&site=qq&menu=yes" class="link3"><span style="color: #000">联系客服</span></a></td>
      </tr>
      <tr>
        <td height="30" colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td height="23" colspan="3" align="right"><table width="256" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="167" align="right" class="font4">时间 :<span id="clock"><%=datetime %></span></td>
            <td width="38" height="23"><img src="images/2.gif" width="35" height="23" /></td>
            <td width="58" align="left"><a href="agentPage.do?method=findAgentByDomain" class="link1">网站首页</a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="15" >&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="9" background="images/6.jpg"></td>
  </tr>
</table>