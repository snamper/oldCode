<%@ page language="java" contentType="text/html; charset=gb2312"  pageEncoding="GB18030"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.*"%>  
<%@ page import="cardfill.*"%>
     
<%

  String currentUserid = fc.getpv(request, "currentUserid");
  AddLocalCard alc = new AddLocalCard();
  String s = alc.addLocalCard(request);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0309)http://192.168.13.102/commonscan/busiInfo.do -->
<HTML 
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>���-���ؿ�濨-jrlimp1.0</TITLE>
<META content="text/html; charset=gb2312" http-equiv=Content-Type><LINK 
rel="shortcut icon" type=image/ico href="favicon.ico"><LINK rel=stylesheet 
type=text/css href="image/core.css"><LINK rel=stylesheet type=text/css 
href="image/css.css">


<META name=GENERATOR content="MSHTML 9.00.8112.16421"></HEAD>
<BODY>
<TABLE border=0 cellSpacing=0 cellPadding=0 width=993 align=center height=68>
  <TBODY>
  <TR>
    <TD width=4><IMG src="image/007.gif" width=4 height=68></TD>
    <TD vAlign=top background=image/009.gif>
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
        <TR>
          <TD style="PADDING-LEFT: 8px; COLOR: #000" class=font5 
            height=33>���-���ؿ�濨</TD>
        </TR>
        <TR>
          <TD class=font8 height=34></TD></TR></TBODY></TABLE></TD>
    <TD width=4><IMG src="image/008.gif" width=4 height=68></TD></TR>
  <TR>
    <TD height=5 colSpan=3></TD></TR></TBODY></TABLE>
    <FORM  method=post  action=>
<TABLE class=font6 border=0 cellSpacing=1 cellPadding=0 width=993 
bgColor=#d2dde1 align=center>
  <SCRIPT>var sb="";</SCRIPT>

  
  <INPUT name=busiInfoID value=21101111641018893681 type=hidden> 
  <INPUT name=currentUserid  value="<%=currentUserid %>"  type="hidden">
   <INPUT name=sign value=e615a6506f1f2624b9d1b05af0cc5fff type=hidden> 
   <INPUT name=functionid value=FC1101111643195712 type=hidden> 
  <TBODY>
  <TR>
    <TD class=font7 bgColor=#80a0ac height=35 align=right>����:</TD>
    <TD bgColor=#ffffff><INPUT name=ClientID type=text id="ClientID" size=40 value=<%=fc.getTime("yyyyMMddHHmmss") %>>
    * ����, ���������ܵ���Դ������</TD>
  </TR>
  <TR>
    <TD class=font7 bgColor=#80a0ac height=35 align=right>������:</TD>
    <TD bgColor=#ffffff>
	<SELECT name=CardType id="CardType">
      <OPTION selected value=""></OPTION>
      <%=alc.getProductType() %>       
    </SELECT>
	* ���� 
	<input name="checkcard" type="checkbox" id="checkcard" value="true" checked>
���&quot;����+����&quot;�Ƿ����,�����������������.</TD>
  </TR>
  <TR>
    <TD class=font7 bgColor=#80a0ac height=35 align=right>���:</TD>
    <TD bgColor=#ffffff><INPUT name=Price type=text id="Price" size=40>
* ����    </TD>
  </TR>
  <TR>
    <TD class=font7 bgColor=#80a0ac height=35 align=right>�ۿ�:</TD>
    <TD bgColor=#ffffff><INPUT name=Bili type=text id="Bili" value=1.00 size=40>
      * ����</TD>
  </TR>
  <TR>
    <TD class=font7 bgColor=#80a0ac height=35 align=right>���ź�����:</TD>
    <TD bgColor=#ffffff>
      <SCRIPT></SCRIPT>
<TEXTAREA id=CardInfo cols=100 rows=20 name=CardInfo><%=s %></TEXTAREA> 
����,����,������ (֮���ö��ŷָ�)</TD>
  </TR>
  <TR>
    <TD class=font7 bgColor=#80a0ac height=35 align=right></TD>
    <TD 
bgColor=#ffffff><INPUT value=��� namd=Add type=submit> </TD>
  </TR></TBODY></TABLE> </FORM>
<TABLE border=0 cellSpacing=0 cellPadding=0 width=993 align=center>
  <TBODY>
  <TR>
    <TD height=8></TD></TR></TBODY></TABLE>
</BODY></HTML>
