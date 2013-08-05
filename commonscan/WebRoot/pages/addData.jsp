<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="jinrl_exploit_Po.TbusiField"%>
<%@page import="jinrl_exploit_common.fc"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="jinrl_exploit_common.DataConnect"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>添加-${requestScope.busiInfo.ftitle}-jrlimp1.0</title>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
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
	<c:if test="${requestScope.showInfotrue==1}"><label style="color:#<%=new java.util.Random().nextInt(999999)%>;">添加成功！</label></c:if>
    <c:if test="${requestScope.showInfotrue==0}"><label style="color: red;">注意：添加失败！ ${errorMessage}</label></c:if>
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
<form action="${path}/busiInfo.do?method=addData" method="post" onsubmit="return totals(sb);">
              <input type="hidden" value="${requestScope.busiInfo.fid}" name="busiInfoID" />
              <input type="hidden" value="<%=request.getParameter("currentUserid") %>" name="currentUserid" />
              <input type="hidden" name="sign" value="<%=request.getParameter("sign") %>" />
	 		  <input type="hidden" name="functionid" value="<%=request.getParameter("functionid") %>" />
           <c:forEach items="${requestScope.list}" var="field">
           	<c:if test="${field.faddhidden!=true}">
              <tr>
                <td align="right"  bgcolor="#80A0AC"  class="font7" height="35">${field.fshowname}:</td>
                <td  bgcolor="#FFFFFF">
                <c:if test="${empty field.fchangevalue}">
                <% TbusiField bfield = (TbusiField)pageContext.getAttribute("field"); %>
                <%String disp = bfield.getFdisplaytype();
                if(null ==disp || "".equals(disp)){%>
				<input <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if> value="${field.fdefaultvalue}"  name="${field.ffieldname}"  size="${field.finputlength}" type="text" />${field.fsuggestinfo}
                <%}else{
					String[] dis = disp.split("\\|"); %>

					  <% if("textarea".equals(dis[0]) || "html".equals(dis[0])){%><script>sb=sb+"ctext('${field.fshowname}','${field.ffieldname}',<%=dis[3] %>,<%=dis[4] %>);"+"|";</script>
					 <textarea   <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if>  id="${field.ffieldname}"  name="${field.ffieldname}" cols="<%=dis[1] %>" rows="<%=dis[2] %>"  <%if("html".equals(dis[0])){ %> class="bodytext" <%} %> >${field.fdefaultvalue}</textarea>${field.fsuggestinfo}
					<%}else if("password".equals(dis[0])){ %><script>sb=sb+"cpassword('${field.fshowname}','${field.ffieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
					<input  <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if> id="${field.ffieldname}"  value="${field.fdefaultvalue}" name="${field.ffieldname}"  size="${field.finputlength}" type="password" />${field.fsuggestinfo}
					<%}else if("datetime".equals(dis[0])){ %><script>sb=sb+"cdatetime('${field.fshowname}','${field.ffieldname}');"+"|";</script>
					<input   onclick="setday(this);" <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if>  id="${field.ffieldname}" value="${field.fdefaultvalue}" name="${field.ffieldname}"  size="${field.finputlength}" type="text" />${field.fsuggestinfo}
					<%}else if("char".equals(dis[0])){ %><script>sb=sb+"cchar('${field.fshowname}','${field.ffieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
					<input  <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if>  id="${field.ffieldname}" value="${field.fdefaultvalue}" name="${field.ffieldname}"  size="${field.finputlength}" type="text" />${field.fsuggestinfo}
					 <%}else if("int".equals(dis[0])){ %><script>sb=sb+"cint('${field.fshowname}','${field.ffieldname}',<%=dis[1] %>,<%=dis[2] %>);"+"|";</script>
					 <input  <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if>  id="${field.ffieldname}" value="${field.fdefaultvalue}" name="${field.ffieldname}"  size="${field.finputlength}" type="text" />${field.fsuggestinfo}
					 <%}else if("decimal".equals(dis[0])){ %><script>sb=sb+"cdecimal('${field.fshowname}','${field.ffieldname}')"+"|";</script>
					 <input  <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if> value="${field.fdefaultvalue}"  id="${field.ffieldname}" name="${field.ffieldname}"  size="${field.finputlength}" type="text" />${field.fsuggestinfo}
					 <%}else if("select".equals(dis[0])){ %>
					 <input  <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if>  id="${field.ffieldname}" value="${field.fdefaultvalue}" name="${field.ffieldname}"  size="${field.finputlength}" type="text" /><input type="button" value="选择" onclick="sendtoSelectAjaxServlet_${field.ffieldname}();" id="${field.ffieldname}_open_dFD34D" />${field.fsuggestinfo}
					   <script type="text/javascript">
								  function sendtoSelectAjaxServlet_${field.ffieldname}(){
								     var params="connDatabase=<%=dis[3]%>&sql=<%=dis[4]%>";
								     $.ajax({
								       url:'/commonscan/SelectAjaxServlet', //后台处理程序
								       type:'post',         //数据发送方式
								       dataType:'text',     //接受数据格式
								       data:params,         //要传递的数据
								       success:backpage_${field.ffieldname} //回传函数(这里是函数名)
								     });
								   }

									function backpage_${field.ffieldname} (json) //回传函数实体，参数为XMLhttpRequest.responseText
									{
										var contents = "";
										var oneCBs = json.split("|");
										for(var i=0;i<oneCBs.length;i++)
											{
											var oneCB = oneCBs[i].split(":");
											contents+=("<li style='width:<%=dis[1]%>;'><input type='checkbox' value='"+oneCB[0]+"' />"+oneCB[1]+"</li>");
											}
										//alert(contents);
									$("#${field.ffieldname}_F9ASd73").html(contents);
									}

					  </script>
						     <script language="javascript">
							    $(document).ready(function() {
							      //默认弹出层
							      new PopupLayer({trigger:"#${field.ffieldname}_open_dFD34D",popupBlk:"#${field.ffieldname}_SHOWDIV_4dD3D",closeBtn:"#${field.ffieldname}_close_d342d2S23D",useOverlay:true});});
							</script>
					         <div id="${field.ffieldname}_SHOWDIV_4dD3D" class="blk" style="display:none;">
					            <div class="head"><div class="head-right"></div></div>
					                         <div class="main" id="${field.ffieldname}_op_kjk77">
								                <ul id="${field.ffieldname}_F9ASd73">
								                </ul>
								                <input type="button" value="确认" id=""  onclick="addMem('${field.ffieldname}','<%=dis[2]%>');document.getElementById('${field.ffieldname}_close_d342d2S23D').click();" />
								                &nbsp;&nbsp;&nbsp;&nbsp;
								                <input type="button" value="关闭" id="${field.ffieldname}_close_d342d2S23D" />
								            </div>
					            <div class="foot"><div class="foot-right"></div></div>
					        </div>
					 <%}else{ %>
					 <input <c:if test="${field.freadonly==true}">readonly="readonly" style="border-left:0px;border-top:0px;border-right:0px;border-bottom:0px solid #fff" </c:if> value="${field.fdefaultvalue}" name="${field.ffieldname}"  size="${field.finputlength}" type="text" />${field.fsuggestinfo}
					  <%} %>


                <%} %>
                </c:if>
                <c:if test="${!empty field.fchangevalue}">
                <%
				//------------替换值中使用SQL语句，动态替换---------------------
				TbusiField f = (TbusiField)pageContext.getAttribute("field");
                String changeValue = f.getFchangevalue();
				if(changeValue.indexOf("SQL:") == 0){
					StringBuffer newchangevalue = new StringBuffer();
					String changeSQL = changeValue.substring(4);
					DataConnect dc = new DataConnect((String)request.getAttribute("conndatabase"), false);
					ResultSet changers = dc.query(changeSQL);
					if(changers != null){
						while(changers.next()){
							//columnIndex the first column is 1, the second is 2, ...
							String s1 = changers.getString(1);
							String s2 = changers.getString(2);
							newchangevalue.append("|"+s1.trim()+":"+s2.trim());
						}
						changeValue = newchangevalue.substring(1).toString();
						dc.CloseResultSet(changers);
						dc.CloseConnect();
					}else{
						changeValue = "";
						System.out.println("执行替换的SQL语句出现问题");
					}
				}

				//----------------------------------------------------
                %>
                <c:set var="deva" value="${field.fdefaultvalue}"></c:set>
                <%	String[] cvs = changeValue.split("\\|"); %>
                <select name="${field.ffieldname}">
                <option value="@!#$qweM123!@#@!"></option>
				<%	for(int a=0;a<cvs.length;a++){   %>
				<%String cv = cvs[a]; %>
				<%		int in  = cv.indexOf(":");
						if(in != -1){
							String key = cv.substring(0, in);
							String va = cv.substring(in+1);

				%>
				<option value="<%=key %>"  <%if(key.equals(pageContext.getAttribute("deva"))){ %>selected="selected"<%} %>><%=va %></option>
				<%}%>
			<%}%>


				</select>${field.fsuggestinfo}
                </c:if>
                </td>
              </tr>
              </c:if>
           </c:forEach>

              <tr>
                <td align="right"  bgcolor="#80A0AC"  class="font7" height="35"></td>
                <td  bgcolor="#FFFFFF"><input value="添加" type="submit" /></td>
              </tr>
              <c:if test="${busiInfo.fattachment=='0' and !empty datafid}">
              <tr>
              <td align="right"  bgcolor="#80A0AC"  class="font7"  height="35">附件</td>
              <td><div align="center">
<%
String sMd5 =currentUserid + "1008181829553596867H7F65E49JED5OIF4U4DE664C66D6EET3";
sMd5 = fc.getMd5Str(sMd5);
%>
<iframe src="/commonscan/busiInfo.do?method=getBusiInfo&tbusiInfoID=COPY31644&currentUserid=<%=currentUserid %>&functionid=1008181829553596867&sign=<%=sMd5 %>&dataidForUpLoad=${requestScope.datafid}&busiInfoForUpLoad=${requestScope.busiInfo.fid}" frameborder="0" height="100" scrolling="no"  width="100%" onload="Javascript:SetWinHeight(this)" ></iframe>
</div></td>
              </tr></c:if>
</form>
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
