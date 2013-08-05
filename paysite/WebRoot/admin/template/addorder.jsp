<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/admin/template/includeModifyMain.jsp"%>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String clientId=session.getAttribute("username").toString();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/scrollings.js"></script>
<script type="text/javascript" src="images/js/showDateTime.js"></script>

<script type="text/javascript" src="<%=basePath%>admin/js/prototype.js"></script>  
<style>
.menuT{
	height: 30px;
	width: auto;
	display:none;
}
.tagT{
	float: left;
	height: 30px;
	width: 100px;
	line-height:30px;
}
.selectT{
	float: left;
	height: 24px;
	width: 200px;
	padding-top: 6px;
}

</style>
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
<form action="<%=basePath %>general.do?method=updateData" method="post" id="form1" name="form1">
<input type="hidden" value="<%=fileName %>" name="name" id=""/>  
<input type="hidden" value="0" name="urlpara" id="urlpara"/>  
<input type="hidden"  name="id" id="id" value="<%=id%>"/>  
<input type="hidden"  name="clientId" id="clientId" value="<%=clientId%>"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">  
   <%   
   for(int i=0;i<elementsMap.size();i++){  
	   Map element=(Map)elementsMap.get(Integer.toString(i));
		 String type=element.get("type").toString();
		 String name=element.get("name").toString();
		 String Class=element.get("class").toString();  
		 String tag=element.get("tag").toString();
		 String tip=element.get("tip").toString();
		 String value=element.get("value").toString();
	if(type.equals("text")){  
		String onKeypress=element.get("onKeypress").toString();    
   %>
    <tr >  
      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
      <td align="left" class="bk02" width="200">
      <input  name="<%=name %>" id="<%=name %>" type="<%=type %>" class="txt_05"  value="<%=value %>" size="25" /></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
    </tr>
     <%}else if(type.equals("textT")){ 
          %>
     <tr >  
      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
      <td align="left" class="bk02" width="200">
      <input  name="<%=name %>" id="<%=name %>" type="text"  class="txt_05" style="background-color:#F0EFEF;" readonly size="25" value="<%=value %>"/></td>  
      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
    </tr>
    
    <%}else if(type.equals("select")){ %>
	    <tr >  
	      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
	      <td align="left" class="bk02" width="200">
	      <select name="<%=name %>" id="<%=name %>">
	       <%
	       List list=(List)request.getAttribute(name+"list");
	       %>   
	       <c:forEach items="<%=list%>" var="lists" varStatus="count">
	         <option value="${lists[0]}">${lists[1]}</option>  
	       </c:forEach>
	      </select>
	      </td>
	      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
	    </tr>
    <%   
       }else if(type.equals("selectRelation")) {
    	   String option=element.get("option").toString();
       %>
       <tr >  
	      <td height="30" width="100" align="left" class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;<%=tag %>: </td>
	      <td align="left" class="bk02" width="200">
	      <select name="<%=name %>" id="<%=name %>" onchange="selectR('<%=name%>');">
	        <option value=""></option>    
	       <%
	         String optionArr[]=option.split(",");
	         for(int k=0;k<optionArr.length;k++){
	       %>   
	         <option value="<%=optionArr[k]%>"><%=optionArr[k]%></option>    
	       <%}%>   
	      </select>
	      </td>
	      <td class="bk02">&nbsp;&nbsp;<span class="font7"><%=tip %> </span></td>
	    </tr>
   <%}    
	 }
    %>

	    
    
  <tr>
      <td height="30" colspan="3" align="left" >
     <div class="menuT" id="menuT" >
		  <div class="tagT" id="tagT"></div>
		  <div class="selectT" id="selectT"></div>
		  <div class="clear"></div>
	   </div>
      </td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8">&nbsp;&nbsp;&nbsp;&nbsp;<input name="Submit2422" type="button" class="button_09" onclick="checksubmit();" value="<%=buttonname %>" />
      &nbsp;&nbsp;&nbsp;&nbsp;
       <input name="Submit2422" type="button" class="button_09" onclick="javascript:history.back(-1);" value="返回" />
      </td>
      <td align="right">&nbsp;</td>
    </tr>
    <tr>
      <td height="10" colspan="2"></td>  
    </tr>   
  </table>
  <br /><br />
  </form>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>
<script >
if('<%=massage%>'=="success"){
	alert('修改成功！');
}else if('<%=massage%>'==""){
	
}else{
	alert('修改失败！');  
}
  
function checkpwd(){  
	  if(window.confirm("确定要提交吗？"))
	  {
		  var url=<%=urlpara%>;  
		  document.getElementById("urlpara").value=<%=urlpara%>;
		  document.form1.submit();
	  }
	  else   
	  {
	    return false;
	  }      
}
function selectR(s){
	
	var condition=document.getElementById(s).value;  
	new Ajax.Request('general.do?method=secondRelationMenu', { //获得第二级关联菜单 
		asynchronous: true,  
		method:'post',   
		parameters: "relationField="+s+"&condition="+condition,
		onComplete: function(request) {
		 var id=request.responseText;
		 //返回ID为fSelectField@-@FtagName@-@fValue

		 createSelect(id);
		  }
		});
}
function createSelect(id)
{        
	    var varl=id.split("@-@");
	    if(varl.length!=3){
		    return false;
	    }
	    document.getElementById("tagT").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;"+varl[1]+":";
	    document.getElementById("menuT").style.display="block";
	    document.getElementById("selectT").innerHTML="";//清空
        var select1 = document.createElement("select");
        select1.id=varl[0]; //select id 赋值
        select1.name=varl[0];  
        var ooption = new Array();
        var valueArr=varl[2].split(",");
        for(var i=0;i<valueArr.length;i++){
        	ooption[i] = valueArr[i];
        	select1.options[i] = new Option(ooption[i], ooption[i]);
        }
        document.getElementById("selectT").appendChild(select1);
}
function checksubmit(){  
	  if(window.confirm("确定要提交吗？"))
	  {
		var xmlFile='<%=fileName%>';
		var clientId='<%=clientId%>';
		var productId='<%=id%>';
		var fPlayName=document.getElementById('fPlayName').value;
		var fPrice= document.getElementById('fPrice').value;
		var fNumber= document.getElementById('fNumber').value;
		var fPlayName2= document.getElementById('fPlayName2').value;
	//正整数   
		  var   r   =   /^\+?[1-9][0-9]*$/;　
		  if(!r.test(fNumber)){
            alert("数量请输入整数类型！");
            return false;
		  }
		
	    if(fPlayName==""){
		     alert("充值账号不能为空！");
             return false;
	    }if(fPlayName!=fPlayName2){
		     alert("两次输入的充值账号不同请重新输入！");
             return false;
	    }
		var fFillArea="";
		var fFillServer="";
		
		if(document.getElementById('fFillArea')) {
			fFillArea=document.getElementById('fFillArea').value;
		}
		if(document.getElementById('fFillServer')) {  
			fFillArea=document.getElementById('fFillServer').value;
		}
		new Ajax.Request('general.do?method=addOrderUrlAjax', {  
				asynchronous: true,
				method:'post',   
				parameters: "xmlFile="+xmlFile+"&clientId="+clientId+"&productId="+productId+"&fPlayName="+fPlayName+"&fPrice="+fPrice+"&fNumber="+fNumber+"&fFillArea="+fFillArea+"&fFillServer="+fFillServer,
				onComplete: function(request) {
				 var id=request.responseText;
				     alert(id);
				     //  if(id.lastIndexOf("失败")==-1){
					     //window.location.href="general.do?method=findDataList&amp;name=jiuyi_productList";
					     history.back(-1);      
				    // }  
				}
		});
	  }  
	  else
	 {
	    return false;
	  }  
}
</script>


