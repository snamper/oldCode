<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/admin/template/includeAddMain.jsp"%>
  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//String sendPost=fc.SendDataViaGet("http://127.0.0.1:8080/ZfwebApi/ArticleReturn?sign=JFJ45F674WR7UDY6U547R6JY48D7F93&out_trade_no=1108231742547758-11122233&total_fee=555&trade_status=TRADE_FINISHED&memo=手工加款&opereateid=gai213");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/scrollings.js"></script>
<script type="text/javascript" src="images/js/showDateTime.js"></script>
<script type="text/javascript" src="<%=basePath%>admin/js/prototype.js"></script>  
<script language="JavaScript">
oImg1=new Image();
oImg2=new Image();
oImg1.src="images/img_02/6.gif";  
oImg2.src="images/img_02/6_a.gif";

window.onload=function(){  
	getCurDate("curDate");
	getCurTime("curTime");
	setInterval("getCurTime('curTime')",100);
}
//校验搜索关键字 
function isSearch() 
{ 
	var s=document.getElementById("key").value;
	if(s!=""){
		var patrn=/^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,19}$/; 
		   if (!patrn.exec(s)){
			   alert("请不要输入特殊字符！");
			return false 
		   }
	}  
      return true 
    } 

</script>
</head>
<body>
<!--标题开始-->
<div id="title">
  <div id="title_right">
    <h4 id="curDate"></h4>
    <h5 id="curTime"></h5> 
  </div>  
   <div id="title_left"></div>
</div>
<!--标题结束-->  
<div id="subtitle"><%=subTitle%>:</div>
<!--搜索开始-->
<!--表单开始-->
<div id="search_list_content">
<form action="<%=basePath%>general.do?method=addData" method="post" name="form1">
<input type="hidden" value="<%=fileName %>" name="name" id=""/>  
<input type="hidden" value="0" name="urlpara" id="urlpara"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <%   
   for(int i=0;i<elementsMap.size();i++){  
	   Map element=(Map)elementsMap.get(Integer.toString(i));
		 String type=element.get("type").toString();
		 String name=element.get("name").toString();
		 String Class=element.get("class").toString();  
		 String tag=element.get("tag").toString();
		 String tip=element.get("tip").toString();
	if(type.equals("text")){
		String onKeypress=element.get("onKeypress").toString();    
   %>
    <tr >  
      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
      <td align="left" class="bk02" width="200">
      <input  name="<%=name %>" id="<%=name %>" type="<%=type %>" class="txt_05"  size="25" value=""/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
    </tr>
    
     <%}else if(type.equals("textT")){ 
          String valueT=element.get("value").toString();  
          %>
     <tr >  
      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
      <td align="left" class="bk02" width="200">
      <input  name="<%=name %>" id="<%=name %>" type="<%=type %>" class="txt_05" style="background-color:#F0EFEF;" readonly size="25" value="<%=valueT %>"/></td>  
      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
    </tr>
    
    <%}else if(type.equals("select")){ %>
	    <tr >  
	      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
	      <td align="left" class="bk02" width="200">
	       <select name="<%=name %>" id="<%=name %>" class="txt_04">
	       <%
	       List list=(List)request.getAttribute(name+"list");
	       %>   
	       <c:forEach items="<%=list%>" var="lists" varStatus="count">
	         <option value="${lists[0]}" >${lists[1]}</option>  
	       </c:forEach> 
	      </select>
	      </td>
	      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
	    </tr>
    <%
    }else if(type.equals("enumerate")){ %>
    <tr >  
    <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
    <td align="left" class="bk02" width="200">
     <select name="<%=name %>" id="<%=name %>" class="txt_04">
     <%
     List list=(List)request.getAttribute(name+"list");
     %>   
     <c:if test="${!empty currentClient.fAddMoneyType}">
     <c:forEach items="<%=list%>" var="lists" varStatus="count">
       <c:if test="${fn:contains(currentClient.fAddMoneyType, lists[0])}"><option value="${lists[0]}" >${lists[1]}</option></c:if>
     </c:forEach>
     </c:if>
     <c:if test="${empty currentClient.fAddMoneyType}">
     <option value="0" >鼎恒支付宝</option>
     </c:if>
    </select>
    </td>
    <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
  </tr>
<%
} else if(type.equals("selectAjax")){ %>
    <tr >  
    <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
    <td align="left" class="bk02" width="200">
    <select name="<%=name %>" id="<%=name %>" onchange="select('<%=name%>');" class="txt_04">
     <%
     List list=(List)request.getAttribute(name+"list");
     %>   
	     <c:forEach items="<%=list%>" var="lists" varStatus="count">
	       <option value="${lists[0]}" >${lists[1]}</option>  
	     </c:forEach> 
	    </select>
	    </td>
	    <td class="bk02">&nbsp;&nbsp;<span class="font7"> <label id="product"><%=tip %></label></span></td>
	     </tr>
    <%
       }  
	 }
    %>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8">&nbsp;&nbsp;&nbsp;&nbsp;<input name="Submit2422" type="button" class="button_09" onclick="checkpwd();" value="<%=buttonname %>" />
     &nbsp;&nbsp;&nbsp;&nbsp;
       <input name="Submit2422" type="button" class="button_09" onclick="javascript:history.back(-1);" value="返回" />
      </td>
      <td align="right">&nbsp;</td>
    </tr>
    <tr>
      <td height="10" colspan="2"></td>  
    </tr>   
  </table>
  </form>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>
<script >

if('<%=massage%>'=="success"){
	alert('加款成功！');
}else if('<%=massage%>'==""){
	
}else{
	alert('加款失败！');
}

  
function checkpwd(){  
	  if(window.confirm("确定要提交吗？"))
	  {
		  
		  var fmoney=document.getElementById("fmoney").value;
		  //var   r   =   /^\+?[1-9][0-9]*$/;　　//正整数  
		  var   r   =   /^(?:[1-9][0-9]*(?:\.[0-9]+)?|0\.(?!0+$)[0-9]+)$/;　
		  
		  if(!r.test(fmoney)){
             alert("加款金额请输入正确数字类型！");
             return false;
		  }
		  
		  var url=<%=urlpara%>;  
		  document.getElementById("urlpara").value=<%=urlpara%>;
		  
		  if((<%=urlpara%>).lastIndexOf("@-@")!=-1){
		  new Ajax.Request('general.do?method=addMoneyUrlAjax', {  
				asynchronous: true,
				method:'post',   
				parameters: "name="+'<%=fileName%>'+"&urlpara="+<%=urlpara%>,
				onComplete: function(request) {
				  var id=request.responseText;
				  if(id.lastIndexOf("faceDomain_null")!=-1){
					 alert("代理商信息不完整，请与管理员联系！");
					 return false;
				  }else{
				      window.open (id, '', 'height=1000, width=1400');
				   }
				  }
		});}else{
			
			document.form1.submit();
		}
		  
		  
	  }  
	  else
	  {
	    return false;
	  }  
}
function select(s){
	var fid=document.getElementById(s).value;  
	new Ajax.Request('general.do?method=findProductInfo', {  
		asynchronous: true,
		method:'post',   
		parameters: "id="+fid,
		onComplete: function(request) {
		 var id=request.responseText;
		 alert(id);
		   $("product").innerHTML=id;  
		  }
		});
}
</script>