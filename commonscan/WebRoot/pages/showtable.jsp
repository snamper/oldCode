<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="jinrl_exploit_IService.IBusiFieldService"%>
<%@page import="jinrl_exploit_Po.TbusiInfo"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="jinrl_exploit_Po.TbusiField"%>
<%@page import="jinrl_exploit_common.Tools"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/path.jsp" %>
<%@ page import="jinrl_exploit_common.fc" %>
<%
//接收参数

//查询参数
//String[] updateDivResult = (String[])request.getAttribute("updateDivResult");
String dataidForUpLoad = fc.changNull(request.getParameter("dataidForUpLoad"));
String busiInfoForUpLoad = fc.changNull(request.getParameter("busiInfoForUpLoad"));
String forUpLoadUrl = "&dataidForUpLoad="+dataidForUpLoad+"&busiInfoForUpLoad="+busiInfoForUpLoad;
String pageSize = "&pageSize="+fc.changNull(request.getParameter("pageSize"));
String selType_on = "&selType_on=" + request.getAttribute("selType_on");
boolean controlUpLoad = false;
if("".equals(dataidForUpLoad)){controlUpLoad = true;}

//权限参数
String checkloginsign = fc.changNull(request.getParameter("checkloginsign"));
String currentUserid = (String)session.getAttribute("currentUserid");
String functionid = request.getParameter("functionid");
String sign = request.getParameter("sign");

//自定义group by
String[] agbs = (String[])request.getAttribute("activeGroupAllArray");
List activeGroupSelectList = (List)request.getAttribute("activeGroupSelectList");
String activeGroupURL = "";
if(activeGroupSelectList != null && !activeGroupSelectList.isEmpty()){
	for(int gb = 0; gb < activeGroupSelectList.size(); gb++){
		activeGroupURL= activeGroupURL + "&activeGroupBy_" + (String)activeGroupSelectList.get(gb) + "=true";
	}
}



//验证合法性
String a1 = "";
int ips = checkloginsign.lastIndexOf(".");
if(ips != -1){
	String serviceIPAddr = "http://"+checkloginsign.substring(0,ips);
	pageContext.setAttribute("serviceIPAddr",serviceIPAddr);
	String sL = serviceIPAddr +"/powermodule/checkIPUID?checkloginsign="+checkloginsign.substring(ips + 1);
	//System.out.println(sL);
	a1 = fc.SendDataViaPost(sL,"","GB2312", 5);
} 
if(!a1.equals("Y")){
	System.out.println("验证失败");
//	out.print("页面访问超时或验证失败，请重新登录或联系管理员");
	response.sendRedirect("/commonscan/pages/Error.jsp"); 
	return;
	}
//System.out.println("验证通过");
%>

<%
//管理员功能连接
String userid_functionid = "&currentUserid="+currentUserid+"&functionid="+functionid+"&sign="+sign;
userid_functionid = userid_functionid + pageSize + forUpLoadUrl + selType_on + "&checkloginsign=" + checkloginsign + "&quickSelcet=1" + activeGroupURL;
String manageTitleURL = "";
TbusiInfo busiInfo = (TbusiInfo)request.getAttribute("busiInfo");
String signforManageInfo = fc.getMd5Str(currentUserid + "16" + "H7F65E49JED5OIF4U4DE664C66D6EET3");
String signforManageField = fc.getMd5Str(currentUserid + "17" + "H7F65E49JED5OIF4U4DE664C66D6EET3");
manageTitleURL = "/commonscan/busiInfo.do?method=showUpdatePage&busiInfoid=22&dataid="+busiInfo.getFid()
+"&currentUserid="+currentUserid+"&functionid=16&sign=" + signforManageInfo;

List listFields = (List)request.getAttribute("list");
//分类字段隔行显示
String spaceField = fc.changNull(busiInfo.getFspacefield());
//存储值
String spaceValue = "";
if(!"".equals(spaceField)){
	for(int f=0;f<listFields.size();f++){
		TbusiField bfl = (TbusiField) listFields.get(f);
		if(bfl.getFfieldname().equals(spaceField)){
			spaceValue = bfl.getFshowname();
		}
	}
}
%>
<%
//保存状态
String systemid=(String)request.getAttribute("systemid_mode");
String fieldid=(String)request.getAttribute("fieldid_mode");
String pkfid=(String)request.getAttribute("pkfid_mode");
int selectgroup=((Integer)request.getAttribute("selectgroup_mode")).intValue();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="/commonscan/js/jquery-1.4.2.js" type="text/javascript" language="javascript"></script>
<script src="/commonscan/js/popup_layer.js" type="text/javascript" language="javascript"></script>
<script src="/commonscan/js/thickbox-compressed.js" type="text/javascript" language="javascript"></script>
<script src="/commonscan/js/writejs/notice.js" type="text/javascript" language="javascript"></script>

<link href="style/thickbox.css" type="text/css" rel="stylesheet"/>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
<link href="style/core.css" type="text/css" rel="stylesheet"/>
<link href="style/writecss.css" type="text/css" rel="stylesheet"/>

<meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
<title>${requestScope.busiInfo.ftitle}-jrlimp1.0</title>
<%if(!controlUpLoad){ %>
    <script language="javascript">
    $(document).ready(function() {
      //默认弹出层
      new PopupLayer({trigger:"#ele1",popupBlk:"#blk1",closeBtn:"#close1",
        offsets:{
        x:-400,
        y:-60
        }
      });
    });
  </script><%} %>
<link href="${path}/style/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${path}/js/NewTime.js"></script>
<script>

		//停用
		 function update_autofresh(){
			 if($("#selType_on").attr("checked")==true){
				 $("#autoFrush1").removeAttr("disabled");
			 }else{
				 $("#autoFrush1").removeAttr("checked");
				 $("#autoFrush1").attr('disabled',"true");
				 }
		}

function startAutoFrush(){
	 if($("#autoFrush1").attr("checked")==true){
		document.myfom1.submit();
	 }
}
	 setTimeout('startAutoFrush()',10000); //指定10秒刷新一次

</script>
<script language="javascript">

var check_num1 = 0;
var check_num2 = 0;
var exeNum = 0;
var exeError = "";
function check_somes_click(a,c){

	var checkdata = document.getElementsByName("check_somes");
	if(checkdata.length>0){
		for(var i=0;i<checkdata.length;i++)
 		{
			if(checkdata[i].checked==true){
				check_num1++;
				var b = checkdata[i].value;

				  if(c=='0')
				    {
					  check_somes_execute(a,b);
				    }
				  else if(c=='1')
				    {
				      window.location.href="/commonscan/ExecuteUrlServlet?urlnum="+a+"&dataid="+b+"&currentUserid=<%=currentUserid%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${url12345_fid}&functionid=<%=request.getParameter("functionid")%>";
				    }
				  else if(c=='2')
				    {
				      window.open("/commonscan/ExecuteUrlServlet?urlnum="+a+"&dataid="+b+"&currentUserid=<%=currentUserid%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${url12345_fid}&functionid=<%=request.getParameter("functionid")%>");
				    }
			}

 		}
 	}
}

function check_somes_execute(a,b){
	var params = "urlnum="+a+"&dataid="+b+"&currentUserid=<%=currentUserid%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${url12345_fid}&functionid=<%=request.getParameter("functionid")%>";

	 $.ajax({
	   url:'ExecuteUrlServlet', //后台处理程序
	   type:'post',         //数据发送方式
	   dataType:'text',     //接受数据格式
	   data:params,         //要传递的数据
	   success:backresult //回传函数(这里是函数名)
	 });
 }


function backresult (json) //回传函数实体，参数为XMLhttpRequest.responseText
{
	exeNum++;
	if(json.indexOf("操作完成") != -1 || json.indexOf("操作成功") != -1){
		check_num2++;
	}else{
		exeError += " ， " + json.substring(5);
	}

	if(exeNum == check_num1){
		if(check_num1 == check_num2){
			alert(" 全部执行成功！ ");
			self.location.reload();
		}else{
			alert("成功："+check_num2+"次;失败："+(check_num1 - check_num2)+"次;"+exeError.substring(2));
			self.location.reload();
		}
	}
}



//-----通知start

window.onload=function(){
	
	if(self!=top){
		getNotice();
	}
}

function getNotice(){

	 $.ajax({
	   url:'GetNoticeServlet', //后台处理程序
	   type:'post',         //数据发送方式
	   dataType:'text',     //接受数据格式
	   data:null,         //要传递的数据
	   success:backNoticeMessage //回传函数(这里是函数名)
	 });
 }

//var noticeClosetimer;
function backNoticeMessage (json) //回传函数实体，参数为XMLhttpRequest.responseText
{
	if(json!="_noNotice"){
		//alert(json);
		//$("#divContent").text(json);
		var notices = json.split(",a_A,");

		if(notices[3].length > 15) {
			notices[3] = notices[3].substring(0,15) + "...";
		}
		document.getElementById("notice_Title").innerHTML=notices[3];
		
		if(notices[4].length > 50) {
			notices[4] = notices[4].substring(0,50) + "...";
		}
		document.getElementById("divContent").innerHTML=notices[4];
		$("#notice_Title").attr("href", "/commonscan/GetNoticeServlet?fid="+notices[0]);
		
		$("#notice_Content").attr("href", "/commonscan/GetNoticeServlet?fid="+notices[0]);
		
		$("#msg_title_right").click();
		//noticeClosetimer = setTimeout('$("#msg_title_right").click()',5*1000);//设置自动关闭
		}
	setTimeout('getNotice()',8*1000);
}


//-----通知end



function allckeck_(){
	var ck = document.getElementById("allckeck_1");
	if(ck.checked==true){
		checkAll(1);
	}else{
		checkAll(0);
	}
}

function checkAll(checkall){
		var checkdata = document.getElementsByName("check_somes");
		if(checkall==1){	//全选
  		if(checkdata.length>0){
			for(var i=0;i<checkdata.length;i++)
	 		{
	 			checkdata[i].checked=true;
	 		}
	 	}
	}else if(checkall==0){	//全不选
		if(checkdata.length>0){
			for(var i=0;i<checkdata.length;i++)
	 		{
	 			checkdata[i].checked=false;
	 		}
	 	}
	}else{	//反选
		if(checkdata.length>0){
			for(var i=0;i<checkdata.length;i++)
	 		{
	 			if(checkdata[i].checked){
	 				checkdata[i].checked=false;
	 			}else{
	 				checkdata[i].checked=true;
	 			}

	 		}
	 	}

	}

	}

function goBIAction(){
  var matchnum=/^\+?[1-9][0-9]*$/;
  var nopage = document.getElementById('nopage').value;
  if(nopage==""){
    alert("请输入有效的页号！");
    return;
  }
  if(!matchnum.test(nopage)){
    alert("请输入非零的正整数！");
    document.getElementById('nopage').value="";
    document.getElementById('nopage').focus();
    return;
  }
  window.location.href="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${requestScope.busiInfo.fid}${requestScope.addURL}&pageno="+nopage+"<%=userid_functionid%>";
  }

function mouseinto(a)
{
  var s = "asdf" + a;

  document.getElementById(s).style.backgroundColor="#D7E7EE";

}

function mouseout(a)
{
  var s = "asdf" + a;
  document.getElementById(s).style.backgroundColor="#FFFFFF";

}

function checkfileUpLoaditem()
{
  var fulitem = document.getElementById('fileUpLoaditem').value;
  if(fulitem==""){
    alert("请选择一个要上传的文件");
    return false;
  }else{
  return true;
    }
}

</script>



<script>

var xmlHttpRequest;
function createXMLHttpRequest(){

  if(window.XMLHttpRequest){

    xmlHttpRequest = new XMLHttpRequest();
  }else if(window.ActiveXObject){
    try{
      xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
    }catch(e){
      xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
  }
}

function executeurl(a,b,c)
{
  if(c=='0')
    {
      var paramter = "urlnum="+a+"&dataid="+b+"&currentUserid=<%=currentUserid%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${url12345_fid}&functionid=<%=request.getParameter("functionid")%>&checkloginsign=<%=checkloginsign%>";
      sendRequest(paramter);
    }
  else if(c=='1')
    {
      window.location.href="/commonscan/ExecuteUrlServlet?urlnum="+a+"&dataid="+b+"&currentUserid=<%=currentUserid%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${url12345_fid}&functionid=<%=request.getParameter("functionid")%>&checkloginsign=<%=checkloginsign%>";
    }
  else if(c=='2')
    {
      window.open("/commonscan/ExecuteUrlServlet?urlnum="+a+"&dataid="+b+"&currentUserid=<%=currentUserid%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${url12345_fid}&functionid=<%=request.getParameter("functionid")%>&checkloginsign=<%=checkloginsign%>");
    }
}

function sendRequest(paramter){
  createXMLHttpRequest();

  xmlHttpRequest.open("POST","ExecuteUrlServlet",true);

  xmlHttpRequest.onreadystatechange=responseHandler;
  xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  xmlHttpRequest.send(paramter);
}



function responseHandler(){
  if(xmlHttpRequest.readyState==4){
    if(xmlHttpRequest.status==200){
      var aw=xmlHttpRequest.responseText;

      eval(aw);
      self.location.reload();
    }
  }
}



function getimages(pictrureabc){
  var paramter1 ="picturecode="+pictrureabc;
  sendRequest2(paramter1);
}

function sendRequest2(paramter){
  createXMLHttpRequest();

  xmlHttpRequest.open("POST","ImageServlet",true);

  xmlHttpRequest.onreadystatechange=responseHandler2;
  xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  xmlHttpRequest.send(paramter);
}

function responseHandler2(){
  if(xmlHttpRequest.readyState==4){
    if(xmlHttpRequest.status==200){
      document.getElementById("pictureslength").innerHTML=xmlHttpRequest.responseText;

    }
  }
}

function downloadsend(a){
var d = document.getElementById("downloadname").value;
if(d=='a_a_aQ'){
  alert('请选择一个下载模板');
  return;
}
var dlnuma = document.getElementById("Tdownloadnum").value;
if(dlnuma>60000){
  alert('记录总量超出6W的界定范围，不能生成Excel');
  return;
}
alert('正在生成文件,稍后请到下载列表中查看并下载');
  sendRequest3(a+"&downloadname="+d);
}

function sendRequest3(paramter){
  createXMLHttpRequest();

  xmlHttpRequest.open("POST","busiInfo.do",true);

  xmlHttpRequest.onreadystatechange=responseHandler3;

  xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  xmlHttpRequest.send(paramter);
}
function responseHandler3(){
  if(xmlHttpRequest.readyState==4){
    if(xmlHttpRequest.status==200){
      var fd = xmlHttpRequest.responseText;
      //alert(fd);

    }
  }
}
</script>
<%String delInfor = (String)request.getAttribute("deleteInfor"); %>
<%if("0".equals(delInfor)){ %><script>alert("删除成功！");</script><%} %>
<%if("1".equals(delInfor)){ %><script>alert("注意：删除失败！");</script><%} %>
<%if("2".equals(delInfor)){ %><script>alert("注意：删除失败！删除前检验失败");</script><%} %>
</head>



<body style="FONT-FAMILY: 'arial'">
<%if(controlUpLoad){ %>
<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
    <td width="5"><img src="${path}/images/0100.jpg" width="6" height="35" alt=""></td>
    <td background="${path}/images/0101.jpg" style="PADDING-LEFT: 8px; COLOR: #000" class=font5 ><c:if test="${!empty isManager}"><a href="<%=manageTitleURL%>"  class="link2" style="text-decoration: none" target="_blank" title="设置业务信息"></c:if>${requestScope.busiInfo.ftitle}<c:if test="${!empty isManager}"></a></c:if></td>
    <td width="5"><img src="${path}/images/0102.jpg" width="6" height="35" alt=""></td>
  </tr>
       <form action="/commonscan/busiInfo.do" method="get" name="myfom1">
       <input type="hidden" name="method" value="getBusiInfo" />
       <input type="hidden" name="tbusiInfoID" value="${requestScope.busiInfo.fid}"  />
       <input type="hidden" name="currentUserid" value="<%=currentUserid %>"  />
       <input type="hidden" name="functionid" value="<%=functionid %>"  />
       <input type="hidden" name="sign" value="<%=sign %>"  />
       <input type="hidden" name="selcetclick" value="1"  />
       <input type="hidden" name="quickSelcet" value="1"  />
       <input type="hidden" name="version" value="${remote_version}"  />
       <input type="hidden" name="checkloginsign" value="<%=checkloginsign %>"  />
      <tr>
      <td width="6" height="33" background="${path}/images/0103.jpg"></td>
        <td bgcolor="#F4F6F5" class=font8 style="line-height:28px; padding-top:3px; padding-bottom:3px;">
<%--这部分是时间的 --%>
<%List thinpsel =(List) request.getAttribute("threeTypeSel");
for(int t=0;t<thinpsel.size();t++){
  String [] threeinp =(String[])thinpsel.get(t);
%>
  <%=threeinp[0]%><input  class="search_txt"  size="18" onclick="setday(this);" type="text" name="<%=threeinp[1]%>_start" <%if(null!=fc.getpv(request, threeinp[1]+"_start") && !fc.getpv(request, threeinp[1]+"_start").equals("")){ %> value="<%= fc.getpv(request, threeinp[1]+"_start")%>" <%}else{ %> value="<%= fc.getstarttime((Long)request.getAttribute("defaultDays"))%>" <%} %> /><input class="search_txt"    size="18" onclick="setday(this);" <%if(null!=fc.getpv(request, threeinp[1]+"_end") && !"".equals(fc.getpv(request, threeinp[1]+"_end"))){ %>  value="<%= fc.getpv(request, threeinp[1]+"_end")%>"  <%}else{ %>  value="<%= fc.getendtime()%>"  <%} %> type="text" name="<%=threeinp[1]%>_end" />
<%} %>
<%--这部分是下拉选择的 --%>
        <%List zeroTypeSel = (List) request.getAttribute("zeroTypeSel"); %>
        <%for(int k=0;k<zeroTypeSel.size();k++){ %>
        <%List oneSel = (List)zeroTypeSel.get(k); %>
            <%if(k==0){%>选择<%}%><select name="rfy_<%=oneSel.get(0) %>" class="select_txt">
              <option value=""><%="<"+oneSel.get(1)+">" %></option>
              <%if(oneSel.size()==3 && "".equals(oneSel.get(2))){ %>
              <%}else{ %>
                  <%for(int i= 2;i<oneSel.size();i+=2){ %>
                    <option value="<%=oneSel.get(i) %>"
                    <%if (fc.getpv(request, "rfy_" + oneSel.get(0)).equals(oneSel.get(i))){%>
                    selected="selected"
                    <%}%>>
                    <%=oneSel.get(i+1) %></option>
                  <%} %>
              <%} %>
            </select>
    <%} %>
<%--这部分是选择查询项的 --%>
      <c:if test="${!empty requestScope.oneTypeSel}">
              <select name="yy_oneTypeSel" class="select_txt">
                <option value=""><选择查询项></option>

                <% List listone = (List)request.getAttribute("oneTypeSel");%>
                <%for(int n=0;n<listone.size();n++){ %>
                <%String[] otsl = (String[])listone.get(n); %>
                  <option value="rfy_<%=otsl[0] %>"
                    <%if (fc.getpv(request, "yy_oneTypeSel").equals("rfy_"+otsl[0])){%>
                    selected="selected"
                    <%}%>>
                    <%=otsl[1] %></option>
                  <%} %>

              </select><input class="search_txt"  <%if ( fc.getpv(request, "yy_oneTypeInp") == null || "".equals(fc.getpv(request, "yy_oneTypeInp"))){%><%}else{%> value="<%=fc.getpv(request, "yy_oneTypeInp")%>"<%} %> name="yy_oneTypeInp" type="text" class="search_txt" size="12" />
          </c:if>
<%--这部分是单个填写值的 --%>
        <%List inpsels =(List) request.getAttribute("twoTypeSel"); %>
        <%for(int k = 0;k<inpsels.size();k++){ %>
        <%String [] inpsel =(String[])inpsels.get(k); %>
   <span style="font-size: 12px"><%= inpsel[0]%></span><%if(fc.getpv(request, "rfy_"+inpsel[1]) == null || fc.getpv(request, "rfy_"+inpsel[1]).equals("")){ %><input class="search_txt"  name="rfy_<%= inpsel[1]%>" value="<%=inpsel[2]%>"  type="text" class="search_txt" size="12" /><%}else{ %><input class="search_txt"  name="rfy_<%= inpsel[1]%>" value="<%= fc.getpv(request, "rfy_"+inpsel[1])%>" type="text" class="search_txt" size="12" /><%} %><%} %>
<%--这部分是自定义分组的 --%>
<%
if(agbs != null && agbs.length > 0){
%>
按组:
<%for(int slc = 0; slc < agbs.length; slc++){
	for(int i = 0; i < listFields.size(); i++){
		TbusiField tf = (TbusiField)listFields.get(i);
		if(agbs[slc].equalsIgnoreCase(tf.getFfieldname())){
			
			//下个for循环用于检验是否是选中状态
			int flg = 1;
			for(int j = 0; j < activeGroupSelectList.size(); j++){
				String selectOne = (String)activeGroupSelectList.get(j);
				if(agbs[slc].equalsIgnoreCase(selectOne)){
					flg = 0;
					break;
				}
			}
%>
<input type="checkbox" name="activeGroupBy_<%=agbs[slc]%>" value="true" <%if(flg==0){ %> checked="checked" <%} %> /><%=tf.getFshowname() %>
<%}}}}%>
<input type="submit"  value=" 查询 " />&nbsp;&nbsp;<input type="checkbox" id="selType_on" name="selType_on" value="on" <c:if test="${selType_on=='on' }"> checked="checked" </c:if> />快速&nbsp;&nbsp;<input id="autoFrush1" type="checkbox" name="autoFrush1" value="on"  <c:if test="<%="on".equals(request.getParameter("autoFrush1")) %>"> checked="checked" </c:if> onclick="startAutoFrush();"  />刷新<c:if test="${!empty famount_show}">&nbsp;&nbsp;<input type="checkbox" name="setFamountOn" value="on" <c:if test="${setFamountOn=='on' }"> checked="checked" </c:if> />总计</c:if>
 </td>
<td width="6" background="${path}/images/0104.jpg"></td>
      </tr></form>
      <tr>
    <td><img src="images/0105.jpg" width="6" height="5" alt=""></td>
    <td background="images/0106.jpg"></td>
    <td><img src="images/0107.jpg" width="5" height="5" alt=""></td>
  </tr>
  <tr>
   <td height="5" colspan="3"></td>
  </tr>
</table><%}%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#D2DDE1" class="font6">
            <tr <%if(controlUpLoad){ %> class="bg_01"<%}%>>
            <td width="35" align="center" <%if(!controlUpLoad){ %>  background="images/20.jpg"  bgcolor="#FFFFFF" <%}else{%> class="font7" <%} %> ><input type="checkbox" id="allckeck_1" onclick="allckeck_();" /></td>
            <td  width="35" align="center"  <%if(!controlUpLoad){ %>  height="25"   background="images/20.jpg"  bgcolor="#FFFFFF" <%}else{%> class="font7" <%} %> >序号</td>
            <%
            	String agValueStr = (String)request.getAttribute("agValueStr");
            	String activegFlag = (String)request.getAttribute("activegFlag");
            %>
              <c:forEach items="${list}" var="datab">
              <%TbusiField bfieldag = (TbusiField)pageContext.getAttribute("datab");  %>
                  <td width="${datab.fdisplaywidth}" align="center"    <%if(!controlUpLoad){ %>  background="images/20.jpg"  bgcolor="#FFFFFF" <%}else{%> class="font7" <%} %> ><%if("true".equals(activegFlag) && !(agValueStr.indexOf(bfieldag.getFfieldname().toUpperCase()) > -1)){}else{%><c:if test="${!empty isManager}"><a  <%if(!controlUpLoad){ %>   class="link13" <%}else{%>  class="link12" style="font-size: 14px;" <%} %>    href="/commonscan/busiInfo.do?method=showUpdatePage&busiInfoid=23&dataid=${datab.fid}&currentUserid=<%=currentUserid%>&functionid=17&sign=<%=signforManageField %>"   target="_blank" title="设置业务字段"></c:if>${datab.fshowname}<c:if test="${!empty isManager}"></a></c:if><%}%></td>
              </c:forEach>
              <td align="center"   <%if(!controlUpLoad){ %>  background="images/20.jpg"  bgcolor="#FFFFFF" <%}else{%> class="font7" <%} %> >操作</td>
            </tr>
     <%
     List datalist = (List) request.getAttribute("datalist");
     Integer startRowNum = (Integer) request.getAttribute("startRowNum");
     for(int j=0;j<datalist.size();j++){
     String[] data = (String[])datalist.get(j);
     %>
     <% String  colorsa = "#FFFFFF"; boolean col = true; if(data[data.length-8] == null || "".equals(data[data.length-8])){ colorsa = "#FFFFFF";}else{colorsa = data[data.length-8]; col = false;}%>
     		<%
     		if(!"".equals(spaceField)){
     			String beforeEndString = (String)pageContext.getAttribute("beforeEndString");
     			String endString = data[((Integer)request.getAttribute("spaceNum")).intValue()];
     			if(!endString.equals(beforeEndString)){
     				pageContext.setAttribute("beforeEndString",endString);


     		%>
     		<tr bgcolor="#FFFFFF" >
     		<td colspan="<%=data.length-8+2+1 %>" <%if(controlUpLoad){ %> height="25"  <%}else{ %>  height="22"  <%} %>  ><strong><<%=spaceValue %>>: <%=endString %></strong>
     		</td>
     		</tr>
     		<%}} %>
            <tr  bgcolor="<%=colorsa %>" <%if(col){ %> onmouseout="mouseout('<%=j+1+startRowNum.intValue() %>');" onmouseover="mouseinto('<%=j+1+startRowNum.intValue() %>');" <%} %> id="asdf<%=j+1+startRowNum.intValue() %>">
            	<td align="center"><input type="checkbox" name="check_somes"    value="<%=data[data.length-1] %>" /></td>
                <td align="center" <%if(controlUpLoad){ %> height="25"  <%}else{ %>  height="22"  <%} %>  ><%=j+1+startRowNum.intValue() %></td>
          <%
          for(int i=0;i<data.length - 8;i++){
          %>
                  <!--    <%//if("0".equals(updateDivResult[i])){ %><td align="center" onclick=""><a href="/commonscan/index.jsp?height=200&width=300" class="thickbox" title="点击修改此值" ><%=data[i] %></td></a><%//}else{ %> -->
                    <td align="center"><%=data[i] %></td>
                    <%//} %>
      <%}%>
          <td align="center">

          <c:if test="${requestScope.busiInfo.fisupdate==0}">
		  <a class="thickbox"  href="${path}/busiInfo.do?method=showUpdatePage&busiInfoid=${requestScope.busiInfo.fid}&dataid=<%=data[data.length-1] %><%=userid_functionid%>&updatediv=true&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=520&width=1000" title=" "><span style="color: #1E6F1A">编辑</span></a>
          <a class="link11"  target="_blank"  href="${path}/busiInfo.do?method=showUpdatePage&busiInfoid=${requestScope.busiInfo.fid}&dataid=<%=data[data.length-1] %><%=userid_functionid%>&updatediv=false">详细</a>
          </c:if>
          <%if("0".equals(data[data.length-2])){ %><c:if test="${requestScope.busiInfo.fisdelete==0}">&nbsp;<a onclick="if(confirm('确认删除？')){window.location.href='${path}/busiInfo.do?method=deleteData&busiInfoid=${requestScope.busiInfo.fid}&dataid=<%=data[data.length-1] %>&tbusiInfoID=${requestScope.busiInfo.fid}&pageno=${requestScope.currentpage}${requestScope.addURL}<%=userid_functionid%>'}" style="cursor: pointer;">删除</a></c:if><%} %>
          <c:if test="${requestScope.busiInfo.fisdetail==0}">&nbsp;<a class="link11"  target="_blank" href="${serviceIPAddr}/powermodule/checkpower.do?method=loginSuccess2&showtypes=<%=systemid %>&rightshow=<%=systemid %>&fieldid=<%=fieldid %>&pkfid=<%=pkfid %>&pkvalue=<%=data[data.length-1] %>&selectgroup=<%=selectgroup %><%=userid_functionid%>">明细</a></c:if>
          <%if("0".equals(data[data.length-7])){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url1alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(1,'<%=data[data.length-1] %>','${requestScope.url1type}');">${requestScope.url1}</a><%} %>
          <%if("0".equals(data[data.length-6])){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url2alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(2,'<%=data[data.length-1] %>','${requestScope.url2type}');">${requestScope.url2}</a><%} %>
          <%if("0".equals(data[data.length-5])){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url3alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(3,'<%=data[data.length-1] %>','${requestScope.url3type}');">${requestScope.url3}</a><%} %>
          <%if("0".equals(data[data.length-4])){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url4alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(4,'<%=data[data.length-1] %>','${requestScope.url4type}');">${requestScope.url4}</a><%} %>
          <%if("0".equals(data[data.length-3])){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url5alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(5,'<%=data[data.length-1] %>','${requestScope.url5type}');">${requestScope.url5}</a><%} %></td>
        </tr>
  <%} %>
  <c:if test="${!empty amountlist}">
  <TR  bgcolor="#f1f1f1" onmouseover="mouseinto('zongji');" onMouseOut="mouseout('zongji');" id="asdfzongji" >
  <TD background="${path}/images/03.jpg"> </TD>
    <TD height=30  align=center background="${path}/images/03.jpg"><strong>总计</strong></TD>
    <%List amountlist = (List)request.getAttribute("amountlist"); %>
    <%int amountn = 0; %>
    <%for(int aml = 0;aml < amountlist.size(); aml++){ %>
<%if(!"".equals(amountlist.get(aml))  || aml == (amountlist.size()-1)) {%>
      <TD align=middle  background="${path}/images/03.jpg"  colspan="<%=amountn+1 %>"><strong><%=amountlist.get(aml) %></strong></TD>
   <%  amountn = 0;} else{
     if(!"".equals(amountlist.get(aml+1))){
       %><TD align=middle  background="${path}/images/03.jpg"  colspan="<%=amountn+1 %>"><strong><%=amountlist.get(aml) %></strong></TD> <%
     amountn=0;}else{
     amountn++;
     }}} %>
               <TD align=middle background="${path}/images/03.jpg">&nbsp;</TD>
  </TR>
  </c:if>
</table>
<table width="100%" border="0" align="center" cellpadding="0"  cellspacing="0" <%if(controlUpLoad){ %> class="bg_02"<%}else{ %>  style="border-right-width: 1px;
  border-bottom-width: 1px;border-left-width: 1px;border-right-style: solid;	border-bottom-style: solid;	border-left-style: solid;	border-right-color: #CCCCCC;	border-bottom-color: #CCCCCC;	border-left-color: #CCCCCC;"   bgcolor="#E0E0E0" <%} %>>
  <tr>
    <td  <%if(controlUpLoad){ %> <%}else{ %> bgcolor="#F1F7F7" <%} %>>
      <table width="100%" border="0" align="left" class="font7" <%if(controlUpLoad){ %>  height="35"  <%}else{ %>  height="21"  <%} %>>
        <tr>
           <td <%if(controlUpLoad){ %>  <%}else{ %> style=" color: #333333" <%} %>  width="" nowrap="nowrap" align="left">总记录数 : <span id="downloadnum"><input type="hidden" value="${requestScope.nCountNUM}" name="Tdownloadnum" id="Tdownloadnum" /> <c:if test="${selType_on=='on' && isFastSel}">大于</c:if>${requestScope.nCountNUM}&nbsp;&nbsp;&nbsp;&nbsp;总页数 : <c:if test="${selType_on=='on' && isFastSel}">大于</c:if>${requestScope.pagecount}</span></td>
           <c:if test="${requestScope.busiInfo.fisdownload==0}"><td width="" align="right"  nowrap="nowrap"><select name="downloadname" id="downloadname"  class="select_txt" >
          <option  <%if(controlUpLoad){ %>  <%}else{ %> style=" color: #333333" <%} %>  value="a_a_aQ">请选择下载模板</option>
          <c:forEach items="${kindofname}" var="td">
            <option value="${td.fnameid}">${td.fname}</option>
            </c:forEach>
          </select>
        <%String urle = URLEncoder.encode((String)request.getAttribute("downloadsql"),"GB2312"); %>
       <a onclick="downloadsend('method=createDownload<%=userid_functionid%>&downloadsql=<%=urle %>&busiinfoid=${requestScope.busiInfo.fid}');" style="cursor:pointer; <%if(controlUpLoad){ %>  <%}else{ %> color: #333333; <%} %> " >生成下载文件</a></td></c:if>
       		<c:if test="${check_somes_url1=='0'}"><td width="45" align="right"  nowrap="nowrap"><div class="anniu_auto"><ul><li><a href="javascript:void(0);" onclick="if(confirm('确认执行此操作？')){check_somes_click(1,'${requestScope.url1type}');}"><span>${requestScope.url1}</span></a></li></ul></div></td></c:if>
			<c:if test="${check_somes_url2=='0'}"><td width="45" align="right"  nowrap="nowrap"><div class="anniu_auto"><ul><li><a href="javascript:void(0);" onclick="if(confirm('确认执行此操作？')){check_somes_click(2,'${requestScope.url2type}');}"><span>${requestScope.url2}</span></a></li></ul></div></td></c:if>
			<c:if test="${check_somes_url3=='0'}"><td width="45" align="right"  nowrap="nowrap"><div class="anniu_auto"><ul><li><a href="javascript:void(0);" onclick="if(confirm('确认执行此操作？')){check_somes_click(3,'${requestScope.url3type}');}"><span>${requestScope.url3}</span></a></li></ul></div></td></c:if>
			<c:if test="${check_somes_url4=='0'}"><td width="45" align="right"  nowrap="nowrap"><div class="anniu_auto"><ul><li><a href="javascript:void(0);" onclick="if(confirm('确认执行此操作？')){check_somes_click(4,'${requestScope.url4type}');}"><span>${requestScope.url4}</span></a></li></ul></div></td></c:if>
			<c:if test="${check_somes_url5=='0'}"><td width="45" align="right"  nowrap="nowrap"><div class="anniu_auto"><ul><li><a href="javascript:void(0);" onclick="if(confirm('确认执行此操作？')){check_somes_click(5,'${requestScope.url5type}');}"><span>${requestScope.url5}</span></a></li></ul></div></td></c:if>
      <%if(!controlUpLoad && !"noUploadButten_12!@".equals(dataidForUpLoad)){ %> <td style="padding-top:8px;" width="40" align="right" valign="top" nowrap="nowrap"><div id="ele1" class="aniu01" style="cursor: pointer; <%if(controlUpLoad){ %>  <%}else{ %> color: #333333; <%} %>">上传</div></td><%} %>
          <c:if test="${requestScope.busiInfo.fisadd==0}"><td  width="40" align="right"  nowrap="nowrap"><div class="anniu_auto"><ul><li><a href="/commonscan/busiInfo.do?method=showAddDataPage&tbusiInfoID=${requestScope.busiInfo.fid}<%=userid_functionid%>" target="_blank" class="link12" <%if(controlUpLoad){ %>  <%}else{ %> style=" color: #333333" <%} %>  ><span  >添加</span></a></li></ul></div></td></c:if>
          <td width="190" nowrap="nowrap" align="right"><a   <%if(controlUpLoad){ %>  class="link10" <%}else{ %>  class="link6" <%} %>  href="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${requestScope.busiInfo.fid}&pageno=1${requestScope.addURL}<%=userid_functionid%>">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;
          <a   <%if(controlUpLoad){ %>  class="link10" <%}else{ %>  class="link6" <%} %>  href="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${requestScope.busiInfo.fid}&pageno=${requestScope.currentpage-1}${requestScope.addURL}<%=userid_functionid%>">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
          <a   <%if(controlUpLoad){ %>  class="link10" <%}else{ %>  class="link6" <%} %>   href="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${requestScope.busiInfo.fid}&pageno=${requestScope.currentpage+1}${requestScope.addURL}<%=userid_functionid%>">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
          <a   <%if(controlUpLoad){ %>  class="link10" <%}else{ %>  class="link6" <%} %>   href="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=${requestScope.busiInfo.fid}&pageno=${requestScope.pagecount}${requestScope.addURL}<%=userid_functionid%>">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
          <td width="93" nowrap="nowrap" align="left"  <%if(controlUpLoad){ %>  <%}else{ %> style=" color: #333333" <%} %> >页号<input  class="search_txt" style="height:15px;"  size="1" value="${requestScope.currentpage}" type="text" name="nopage" id="nopage" />&nbsp;<a style="cursor: pointer;" onclick="goBIAction()">跳转</a></td>
          </tr>
</table></td></tr>
</table>
<%if(controlUpLoad){ %>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="font999">
    <td height="30"  class="line01"> 2011 JRL-IMPv1.0   |  版权所有   </td>
  </tr>
</table><%} %>
<%if(!controlUpLoad){ %>
        <div id="blk1" class="blk" style="display:none;">
            <div class="head"><div class="head-right"></div></div>
              <div class="main2" align="center">
          <form action="FileUpLoadServlet" method="post" name="form5" id="form5" enctype="multipart/form-data" onsubmit="return checkfileUpLoaditem();">
            <table>
            <tr>
              <td><input name="fileUpLoaditem" type="file" id="fileUpLoaditem" size="42" />
                <input name="currentUserid" id="currentUserid" type="hidden" value="<%=currentUserid %>" />
                <input name="busiInfoid" id="busiInfoid" type="hidden" value="<%=busiInfoForUpLoad %>"  />
                <input name="dataid" id="dataid" type="hidden" value="<%=dataidForUpLoad %>"  /></td>
              <td><input type="submit" value="确认" /></td>
              <td><input type="button" value="取消" id="close1" /></td>
            </tr>
            </table>
          </form>
              </div>
            <div class="foot"><div class="foot-right"></div></div>
        </div>
<%} %>

<!-- 弹出通知的div -->

<div id="msg" style="visibility: hidden;"  class="divCss">
  <div id="msg_title">
    <div id="msg_title_left"></div>
    <div id="msg_title_right"><img src="images/0.gif" width="43" height="21" border="0" class="close" /></div>
    <div class="clear"></div>
  </div>
  <div id="msg_content">
    <h3><a id="notice_Title" href="#" target="_blank"></a></h3>
    <div id="msg_content_text"> 　　<a id="notice_Content" href="#"  target="_blank"><span id="divContent"></span></a></div>
  </div>
</div>

</body>
</html>
