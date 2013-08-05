<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="jinrl_exploit_Po.CustomObj"%>
<%@page import="jinrl_exploit_common.fc"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>修改-${requestScope.busiInfo.ftitle}-jrlimp1.0</title>
<link href="style/core.css" type="text/css" rel="stylesheet"/>
<link href="${path}/style/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${path}/js/NewTime.js"></script>
<script language="javascript"  type="text/jscript" src="${path}/js/checks.js"></script>
<script type="text/javascript" src="${path}/tiny_mce/tiny_mce.js"></script>
<script src="/commonscan/js/jquery-1.4.2.js" type="text/javascript" language="javascript"></script>
<script src="/commonscan/js/popup_layer.js" type="text/javascript" language="javascript"></script>
<script>
function SetWinHeight(obj)
{
    var win=obj;
    if (document.getElementById)
    {
        if (win && !window.opera)
        {
            if (win.contentDocument && win.contentDocument.body.offsetHeight)

                win.height = win.contentDocument.body.offsetHeight;
            else if(win.Document && win.Document.body.scrollHeight)
                win.height = win.Document.body.scrollHeight;
        }
    }
}

function totalsAndsubmit(sb){
	if(totals(sb)){
		document.form1.submit();
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
			var paramter = "urlnum="+a+"&dataid="+b+"&currentUserid=<%=request.getParameter("currentUserid")%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${requestScope.busiInfo.fid}&checkloginsign=${checkloginsign}";
			sendRequest(paramter);
		}
	else if(c=='1')
		{
			window.location.href="/commonscan/ExecuteUrlServlet?urlnum="+a+"&dataid="+b+"&currentUserid=<%=request.getParameter("currentUserid")%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${requestScope.busiInfo.fid}&checkloginsign=${checkloginsign}";
		}
	else if(c=='2')
		{
			window.open("/commonscan/ExecuteUrlServlet?urlnum="+a+"&dataid="+b+"&currentUserid=<%=request.getParameter("currentUserid")%>&busiInfoid=${requestScope.busiInfo.fid}&url12345_fid=${requestScope.busiInfo.fid}&checkloginsign=${checkloginsign}");
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

</script>
<script type=text/javascript>
	function addMem(fieldname,a){
		var f_str='';
		$("#"+fieldname+"_op_kjk77 input[type='checkbox']").each(function(){
		if($(this).attr("checked")==true){
			f_str += $(this).attr("value")+a;
		 }
		})
		f_str= f_str.substring(0,f_str.length-a.length);
		$("#"+fieldname).val(f_str);
	}
</script>
</head>
<%
String currentUserid = request.getParameter("currentUserid");
String ip = request.getRemoteAddr();
String ip_userid =ip+"_"+currentUserid;
int sport = request.getLocalPort();
String sL = "http://localhost:"+sport+"/powermodule/checkIPUID?ip_userid="+ip_userid;
String a1 = fc.SendDataViaPost(sL,"","GB2312");
if(a1.equals("N")){response.sendRedirect("/powermodule/login.jsp");}
%>
<body>
<table width="993" height="68" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="4"><img src="${path}/images/007.gif" width="4" height="68" /></td>
    <td valign="top" background="${path}/images/009.gif">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="33" class="font5" style="padding-left:8px; color:#000">${requestScope.busiInfo.ftitle}</td>
      </tr>
      <tr>
        <td height="34" class="font8">
	<c:if test="${requestScope.updateinformation==1}"><script type="text/javascript">alert("提交成功！");</script><label style="color:#<%=new java.util.Random().nextInt(999999)%>;">提交成功！</label></c:if>
	<c:if test="${requestScope.updateinformation==0}"><script type="text/javascript">alert("注意：提交失败！${updateerrorInfo}");</script><label style="color: red;">注意：提交失败！${updateerrorInfo}</label></c:if>
	<c:if test="${requestScope.executeUpdateSQL==false}"><label style="color: red;">注意：提交前验证未通过，不允许提交！</label></c:if></td>
      </tr>
    </table></td>
    <td width="4"><img src="${path}/images/008.gif" width="4" height="68" /></td>
  </tr>
  <tr>
	 <td height="5" colspan="3"></td>
  </tr>
</table>
	<table width="993" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#D2DDE1" class="font6">
	<script>var sb="";</script>
    <form action="${path}/busiInfo.do?method=updateData" method="post" name="form1">
   	<input type="hidden" name="dataidForUpLoad" value="<%=request.getParameter("dataidForUpLoad") %>" />
    <input type="hidden" name="busiInfoForUpLoad" value="<%=request.getParameter("busiInfoForUpLoad") %>" />
    <input type="hidden" name="attachment" value="<%=request.getParameter("attachment") %>" />
    <input type="hidden" name="sign" value="<%=request.getParameter("sign") %>" />
    <input type="hidden" name="functionid" value="<%=request.getParameter("functionid") %>" />
    <input type="hidden" value="${requestScope.busiInfo.fid}" name="busiInfoID" />
    <input type="hidden" name="currentUserid" value='<%=request.getParameter("currentUserid") %>'  />
    <input type="hidden" value="${requestScope.datafid}" name="datafid" />
     <input type="hidden" value="${updatediv}" name="updatediv" />
    <%int ff=0;int fa=0; %>
<c:if test="${busiInfo.fisform!='0'}">
           <c:forEach items="${requestScope.datalist}" var="cobj">
           <c:if test="${cobj.fisshow == '0'}">
           <c:choose>
           <c:when test="${cobj.readonly=='true' && updatediv}"></c:when>
           <c:otherwise>

           <% fa=1; %>
              <tr>
                <td align="right"  bgcolor="#80A0AC"  class="font7" height="35">${cobj.showname}:</td>
                <c:if test="${cobj.type==0}">
                <td  bgcolor="#FFFFFF">
                <%CustomObj ctobj = (CustomObj) pageContext.getAttribute("cobj");
               	  String disp =  ctobj.getDisplaytype();
                  if(null ==disp || "".equals(disp)){%>
				<c:if test="${empty cobj.ffollowcontent}"><input <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly" disabled="disabled"  style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>  id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" type="text"  /></c:if>
				<c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
               <% }else{
                  	String[] dis = disp.split("\\|");
                %>
			                 	  <% if("textarea".equals(dis[0]) || "html".equals(dis[0])){%><script>sb=sb+"ctext('${cobj.showname}','${cobj.fieldname}',<%=dis[3] %>,<%=dis[4] %>);"+"|";</script>
			                 <c:if test="${empty cobj.ffollowcontent}"><textarea  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly" disabled="disabled" </c:if>   id="${cobj.fieldname}"  name="${cobj.fieldname}" cols="<%=dis[1] %>" rows="<%=dis[2] %>"  <%if("html".equals(dis[0])){ %> class="bodytext" <%} %> >${cobj.value}</textarea></c:if>
			                 <c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
			                 <%}else if("password".equals(dis[0])){ %><script>sb=sb+"cpassword('${cobj.showname}','${cobj.fieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
			                 <c:if test="${empty cobj.ffollowcontent}"><input  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"  disabled="disabled"    style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>  id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" type="password" /></c:if>
			                 <c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
			                 <%}else if("datetime".equals(dis[0])){ %><script>sb=sb+"cdatetime('${cobj.showname}','${cobj.fieldname}');"+"|";</script>
			                 <input  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"  disabled="disabled"    style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>  id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}"  onclick="setday(this);"  />${cobj.suggestInfo}</td>
			                 <%}else if("char".equals(dis[0])){ %><script>sb=sb+"cchar('${cobj.showname}','${cobj.fieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
			                 <c:if test="${empty cobj.ffollowcontent}"><input  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"  disabled="disabled"    style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>   id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" /></c:if>
			                 <c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
			                 <%}else if("int".equals(dis[0])){ %><script>sb=sb+"cint('${cobj.showname}','${cobj.fieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
			                 <c:if test="${empty cobj.ffollowcontent}"><input  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"   disabled="disabled"   style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>   id="${cobj.fieldname}" name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" /></c:if>
			                 <c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
			                 <%}else if("decimal".equals(dis[0])){ %><script>sb=sb+"cdecimal('${cobj.showname}','${cobj.fieldname}');"+"|";</script>
			                 <c:if test="${empty cobj.ffollowcontent}"><input  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"  disabled="disabled"    style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>  id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" /></c:if>
			                 <c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
			                 <%}else if("select".equals(dis[0])){ %>
			                 <input  <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"  disabled="disabled"    style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>   id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" /><c:if test="${cobj.readonly!='true'}"><input type="button" value="选择" onclick="sendtoSelectAjaxServlet_${cobj.fieldname}();" id="${cobj.fieldname}_open_dFD34D" /></c:if>${cobj.suggestInfo}</td>
			                  <script type="text/javascript">
								  function sendtoSelectAjaxServlet_${cobj.fieldname}(){
								     var params="connDatabase=<%=dis[3]%>&sql=<%=dis[4]%>";
								     $.ajax({
								       url:'/commonscan/SelectAjaxServlet', //后台处理程序
								       type:'post',         //数据发送方式
								       dataType:'text',     //接受数据格式
								       data:params,         //要传递的数据
								       success:backpage_${cobj.fieldname} //回传函数(这里是函数名)
								     });
								   }

									function backpage_${cobj.fieldname} (json) //回传函数实体，参数为XMLhttpRequest.responseText
									{
										var contents = "";
										var oneCBs = json.split("|");
										for(var i=0;i<oneCBs.length;i++)
											{
											var oneCB = oneCBs[i].split(":");
											contents+=("<li style='width:<%=dis[1]%>;'><input type='checkbox' value='"+oneCB[0]+"' />"+oneCB[1]+"</li>");
											}
										//alert(contents);
									$("#${cobj.fieldname}_F9ASd73").html(contents);
									}

					  </script>
						     <script language="javascript">
							    $(document).ready(function() {
							      //默认弹出层
							      new PopupLayer({trigger:"#${cobj.fieldname}_open_dFD34D",popupBlk:"#${cobj.fieldname}_SHOWDIV_4dD3D",closeBtn:"#${cobj.fieldname}_close_d342d2S23D",useOverlay:true});});
							</script>
					         <div id="${cobj.fieldname}_SHOWDIV_4dD3D" class="blk" style="display:none;">
					            <div class="head"><div class="head-right"></div></div>
					                         <div class="main" id="${cobj.fieldname}_op_kjk77">
								                <ul id="${cobj.fieldname}_F9ASd73">
								                </ul>
								                <input type="button" value="确认" id=""  onclick="addMem('${cobj.fieldname}','<%=dis[2]%>');document.getElementById('${cobj.fieldname}_close_d342d2S23D').click();" />
								                &nbsp;&nbsp;&nbsp;&nbsp;
								                <input type="button" value="关闭" id="${cobj.fieldname}_close_d342d2S23D" />
								            </div>
					            <div class="foot"><div class="foot-right"></div></div>
					        </div>

			                 <%}else{ %>
			                 <c:if test="${empty cobj.ffollowcontent}"><input <c:if test="${cobj.readonly=='true'}"><%ff++; %>readonly="readonly"  disabled="disabled"    style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff"    </c:if>  id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" type="text"  /></c:if>
			                 <c:if test="${!empty cobj.ffollowcontent && updatediv}"><iframe src ="/commonscan/FollowContentServlet?followContent=${cobj.ffollowcontent}&fieldValue=${cobj.value}" class="link1" frameborder="0"  scrolling="no" id="left11" name="left11"  height="100"   onload="Javascript:SetWinHeight(this)"   width="100%" ></iframe></c:if>${cobj.suggestInfo}</td>
			                 <%} %>
                  <%}%>

			 </c:if>

				<c:if test="${cobj.type==1}">
				<c:set var="val1" value="${cobj.value}"></c:set>
                <td  bgcolor="#FFFFFF">
                <select name="${cobj.fieldname}"   <c:if test="${cobj.readonly=='true'}"><%ff++; %>disabled="disabled"</c:if>>
                <option value="@!#$qweM123!@#@!"></option>
                <c:set var="map" value="${cobj.map}" scope="page"></c:set>
                <%Map map1 = (Map)pageContext.getAttribute("map"); %>
                <%Set set1 = map1.keySet(); %>
                <%Set treeset = new TreeSet(set1); %>
                <%for(Iterator it = treeset.iterator();it.hasNext();){ %>
                <%Object obj1 = it.next(); %>
                <option value="<%=obj1 %>" <% if(obj1.equals(pageContext.getAttribute("val1"))){%>selected="selected"<%} %>><%=map1.get(obj1) %></option>
                <%} %>
                </select>

              </c:if>
              </tr>
              </c:otherwise>
          	  </c:choose>
              </c:if>
           </c:forEach>
</c:if>
<c:if test="${busiInfo.fisform=='0'}">
<c:set var="pagehtmform" value="${busiInfo.fhtmform}"></c:set>
	<tr>
		<td align="center"  bgcolor="#FFFFFF" colspan="2">
<%
fa=1;
String htmform = (String)pageContext.getAttribute("pagehtmform");
List datalist = (List)request.getAttribute("datalist");
while(htmform.indexOf("$[") != -1){
	String sFieldName = "";
	String sFieldSource = fc.getString(htmform, "$[", "]");
	String s = fc.delHtml(sFieldSource);//清除HTML标签
	String[] ss = null;
	if(s.indexOf(",") != -1){
		ss = s.split(",");
	}else{
		ss = new String[]{s,"N"};
	}

	String beforeString = htmform.substring(0,htmform.indexOf("$["));
	htmform = htmform.substring(htmform.indexOf("$[") + sFieldSource.length() + 3);
	%>
	<%=beforeString%>
	<%
	for(int i = 0; i<datalist.size(); i++){
		CustomObj cobj = (CustomObj)datalist.get(i);
		pageContext.setAttribute("cobj",cobj);
		//将没有设置字段“R”，“W”权限的根据字段原有权限修改
		if(ss[1].equals("N")){
			if("true".equals(cobj.getReadonly())){
				ss[1] = "R";
			}else{
				ss[1] = "W";
			}
		}

		if(cobj.getFieldname().toLowerCase().equals(ss[0].toLowerCase())){
			if("R".equals(ss[1])){
			ff++;%>
				<%=cobj.getValue()%>
			<%break;}else{%>
			<c:if test="${cobj.type==0}">
				<%String disp = cobj.getDisplaytype();
				if(null ==disp || "".equals(disp)){%>
<input style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"   id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" type="text"  />
				<%}else{
					String[] dis = disp.split("\\|");
				%>
								  <% if("textarea".equals(dis[0]) || "html".equals(dis[0])){%><script>sb=sb+"ctext('${cobj.showname}','${cobj.fieldname}',<%=dis[3] %>,<%=dis[4] %>);"+"|";</script>
			                  <textarea  style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"   id="${cobj.fieldname}"  name="${cobj.fieldname}" cols="<%=dis[1] %>" rows="<%=dis[2] %>"  <%if("html".equals(dis[0])){ %> class="bodytext" <%} %> >${cobj.value}</textarea>
			                 <%}else if("password".equals(dis[0])){ %><script>sb=sb+"cpassword('${cobj.showname}','${cobj.fieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
			                 <input   style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"    id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" type="password" />
			                 <%}else if("datetime".equals(dis[0])){ %><script>sb=sb+"cdatetime('${cobj.showname}','${cobj.fieldname}');"+"|";</script>
			                 <input   style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"     id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}"  onclick="setday(this);"  />
			                 <%}else if("char".equals(dis[0])){ %><script>sb=sb+"cchar('${cobj.showname}','${cobj.fieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
			                 <input   style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"    id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" />
			                 <%}else if("int".equals(dis[0])){ %><script>sb=sb+"cint('${cobj.showname}','${cobj.fieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
			                 <input   style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"     id="${cobj.fieldname}" name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" />
			                 <%}else if("decimal".equals(dis[0])){ %><script>sb=sb+"cdecimal('${cobj.showname}','${cobj.fieldname}');"+"|";</script>
			                 <input   style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: 100%;width: 100%;"     id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" />
			                 <%}else if("select".equals(dis[0])){ %>
			                 <input   style="font-family:微软雅黑; margin: 0px;padding: 0px;border: 1px dashed #CCCCCC;background-color: #F5FCF3;height: auto;width: auto;"    id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" /><c:if test="${cobj.readonly!='true'}"><input type="button" value="选择" id="${cobj.fieldname}_open_dFD34D" /></c:if>
			                  <script type="text/javascript">
								  function sendtoSelectAjaxServlet_${cobj.fieldname}(){
								     var params="connDatabase=<%=dis[3]%>&sql=<%=dis[4]%>";
								     $.ajax({
								       url:'/commonscan/SelectAjaxServlet', //后台处理程序
								       type:'post',         //数据发送方式
								       dataType:'text',     //接受数据格式
								       data:params,         //要传递的数据
								       success:backpage_${cobj.fieldname} //回传函数(这里是函数名)
								     });
								   }

									function backpage_${cobj.fieldname} (json) //回传函数实体，参数为XMLhttpRequest.responseText
									{
										var contents = "";
										var oneCBs = json.split("|");
										for(var i=0;i<oneCBs.length;i++)
											{
											var oneCB = oneCBs[i].split(":");
											contents+=("<li style='width:<%=dis[1]%>;'><input type='checkbox' value='"+oneCB[0]+"' />"+oneCB[1]+"</li>");
											}
										//alert(contents);
									$("#${cobj.fieldname}_F9ASd73").html(contents);
									}

					  </script>
						     <script language="javascript">
							    $(document).ready(function() {
							      //默认弹出层
							    new PopupLayer({trigger:"#${cobj.fieldname}_open_dFD34D",popupBlk:"#${cobj.fieldname}_SHOWDIV_4dD3D",closeBtn:"#${cobj.fieldname}_close_d342d2S23D",useOverlay:true,offsets:{
									x:0,
									y:0
								},onBeforeStart:function(){sendtoSelectAjaxServlet_${cobj.fieldname}();}});
								});
							</script>
					         <div id="${cobj.fieldname}_SHOWDIV_4dD3D" class="blk" style="display:none;">
					            <div class="head"><div class="head-right"></div></div>
					                         <div class="main" id="${cobj.fieldname}_op_kjk77">
								                <ul id="${cobj.fieldname}_F9ASd73">
								                </ul>
								                <input type="button" value="确认" id=""  onclick="addMem('${cobj.fieldname}','<%=dis[2]%>');document.getElementById('${cobj.fieldname}_close_d342d2S23D').click();" />
								                &nbsp;&nbsp;&nbsp;&nbsp;
								                <input type="button" value="关闭" id="${cobj.fieldname}_close_d342d2S23D" />
								            </div>
					            <div class="foot"><div class="foot-right"></div></div>
					        </div>

			                 <%}else{ %>
			                 <input   id="${cobj.fieldname}"  name="${cobj.fieldname}" value="${cobj.value}" size="${cobj.size}" type="text"  />
			                 <%} %>
				<%}
			%>
			</c:if>
				<c:if test="${cobj.type==1}">
				<c:set var="val1" value="${cobj.value}"></c:set>
                <select name="${cobj.fieldname}">
                <option value="@!#$qweM123!@#@!"></option>
                <c:set var="map" value="${cobj.map}" scope="page"></c:set>
                <%Map map1 = (Map)pageContext.getAttribute("map"); %>
                <%Set set1 = map1.keySet(); %>
                <%Set treeset = new TreeSet(set1); %>
                <%for(Iterator it = treeset.iterator();it.hasNext();){ %>
                <%Object obj1 = it.next(); %>
                <option value="<%=obj1 %>" <% if(obj1.equals(pageContext.getAttribute("val1"))){%>selected="selected"<%} %>><%=map1.get(obj1) %></option>
                <%} %>
                </select>

              </c:if>
			<%break;}
		}
	}
}
%>
		</td>
	</tr>
</c:if>
              <tr>
                <td align="right"  bgcolor="#80A0AC"  class="font7"  height="35"></td>
                <td  bgcolor="#FFFFFF"><%if(fa==1 && ff!=((Integer)request.getAttribute("datalistsize")).intValue()){ %><input value="提交" type="button" onclick="if(confirm('是否要保存当前提交的信息?')){totalsAndsubmit(sb);}" />&nbsp;&nbsp;&nbsp;<input type="button"  value="重置" onclick="if(confirm('是否要取消当前修改的信息?')){document.form1.reset();}" />&nbsp;&nbsp;<%} %><%if(fa==0){ %>无任何字段可以编辑！<%} %><c:if test="${!updatediv}"><input type="button" onclick="javascript:window.close();" value="关闭" /></c:if>
    				<%if("0".equals((String)request.getAttribute("cheackURL1"))){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url1alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(1,'${datafid}','${requestScope.url1type}');"><c:if test="${!empty requestScope.url1}">&nbsp;&nbsp;<input value="${requestScope.url1}" type="button" /></c:if></a><%} %>
					<%if("0".equals((String)request.getAttribute("cheackURL2"))){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url2alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(2,'${datafid}','${requestScope.url2type}');"><c:if test="${!empty requestScope.url2}">&nbsp;&nbsp;<input value="${requestScope.url2}" type="button" /></c:if></a><%} %>
					<%if("0".equals((String)request.getAttribute("cheackURL3"))){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url3alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(3,'${datafid}','${requestScope.url3type}');"><c:if test="${!empty requestScope.url3}">&nbsp;&nbsp;<input value="${requestScope.url3}" type="button" /></c:if></a><%} %>
					<%if("0".equals((String)request.getAttribute("cheackURL4"))){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url4alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(4,'${datafid}','${requestScope.url4type}');"><c:if test="${!empty requestScope.url4}">&nbsp;&nbsp;<input value="${requestScope.url4}" type="button" /></c:if></a><%} %>
					<%if("0".equals((String)request.getAttribute("cheackURL5"))){ %><a target="_blank" style="cursor: pointer"  onclick="<%if("0".equals((String)request.getAttribute("url5alert"))){%>if(confirm('确认执行此操作？'))<%}%>executeurl(5,'${datafid}','${requestScope.url5type}');"><c:if test="${!empty requestScope.url5}">&nbsp;&nbsp;<input value="${requestScope.url5}" type="button" /></c:if></a><%} %>
    </td>
              </tr>
              </form>
              <c:if test="${busiInfo.fattachment=='0' && !updatediv}">
              <tr>
              <td align="right"  bgcolor="#80A0AC"  class="font7"  height="35">附件</td>
              <td><div align="center">
<%
String sMd5 =currentUserid + "1008181829553596867H7F65E49JED5OIF4U4DE664C66D6EET3";
sMd5 = fc.getMd5Str(sMd5);
%>
<iframe src="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=COPY31644&currentUserid=<%=currentUserid %>&functionid=1008181829553596867&sign=<%=sMd5 %>&dataidForUpLoad=${requestScope.datafid}&busiInfoForUpLoad=${requestScope.busiInfo.fid}&checkloginsign=<%=request.getParameter("checkloginsign") %>" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
</div></td>
              </tr></c:if>
	</table>
<table width="993" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="8"></td>
  </tr>
</table>
</body>
<script type="text/javascript">
tinyMCE.init({
	// General options
	mode : 'specific_textareas',
	editor_selector :  'bodytext',
	theme : "advanced",
	plugins : "safari,spellchecker,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,imagemanager,filemanager",

	// Theme options
	theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
	theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
	theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
	theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,spellchecker,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,blockquote,pagebreak,|,insertfile,insertimage",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_statusbar_location : "bottom",
	theme_advanced_resizing : true,

	// Example content CSS (should be your site CSS)
	content_css : "css/example.css",

	// Drop lists for link/image/media/template dialogs
	template_external_list_url : "js/template_list.js",
	external_link_list_url : "js/link_list.js",
	external_image_list_url : "js/image_list.js",
	media_external_list_url : "js/media_list.js",

	// Replace values for the template plugin
	template_replace_values : {
		username : "Some User",
		staffid : "991234"
	}
});
</script>
</html>
