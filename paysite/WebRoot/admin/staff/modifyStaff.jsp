<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
  <%@ include file="/admin/includeCheckLogin.jsp"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String staff[]=(String[])request.getAttribute("staff"); 
%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>admin/images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>admin/images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>admin/images/js/js_01/date.js"></script>
<script type="text/javascript" src="<%=basePath%>admin/images/js/scrollings.js"></script>
<script type="text/javascript" src="<%=basePath%>admin/images/js/showDateTime.js"></script>
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
<div id="subtitle">&nbsp;&nbsp;&nbsp;修改员工:</div>
<!--搜索开始-->
<!--表单开始-->
<div id="search_list_content">
<form action="<%=basePath%>staff.do?method=addStaff" method="post" name="form1">
<input type="hidden" name="fpopedomArr" id="fpopedomArr" value=""/>
<input type="hidden" name="fstate" id="fstate" value=""/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
 
   <tr >  
      <td height="30" width="150" align="left"  class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp; 登录账号: </td>
      <td align="left" class="bk02" width="200">
      <input  name="fid" id="fid" type="text" class="txt_05" size="25" value="<%=staff[0] %>" readonly/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"></span></td>
    </tr>
     <tr >  
      <td height="30" width="150" align="left"  class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp; 登录密码: </td>
      <td align="left" class="bk02" width="200">
      <input  name="fpassword" id="fpassword" type="text" class="txt_05" size="25" value="<%=staff[4] %>"/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"></span></td>
    </tr>
     <tr >  
      <td height="30" width="150" align="left"  class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp; 再次输入密码: </td>
      <td align="left" class="bk02" width="200">
      <input  name="fpassword2" id="fpassword2" type="text" class="txt_05" size="25" value="<%=staff[4] %>"/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"></span></td>
    </tr>
     <tr >  
      <td height="30" width="150" align="left"  class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;员工姓名: </td>
      <td align="left" class="bk02" width="200">
      <input  name="fname" id="fname" type="text" class="txt_05" size="25" value="<%=staff[3] %>"/></td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"></span></td>
    </tr> 
    <tr >  
      <td height="30" width="150" align="left"  class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;员工权限: </td>
      <td align="left" class="bk02" width="80%">
         <c:forEach items="${list}" var="lists" varStatus="count">
                  		      ${lists[1]}&nbsp;<input  name="fpopedom" id="fpopedom" type="checkbox" class="" size="25" value="${lists[0]}"/>&nbsp;&nbsp;
	     </c:forEach>     
     </td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"></span></td>
    </tr>   
     <tr >  
      <td height="30" width="150" align="left"  class="bk02" >&nbsp;&nbsp;&nbsp;&nbsp;员工状态: </td>
      <td align="left" class="bk02" width="200">
      <c:set var="s" value="<%=staff[6]%>" />
      <c:choose>
	      <c:when test="${s=='0'}">
	                      启用&nbsp;&nbsp;<input  name="radio" id="fstate" type="radio" class=""   value="0" checked/>&nbsp;&nbsp;&nbsp;&nbsp;
                                 禁用&nbsp;&nbsp;<input  name="radio" id="fstate" type="radio" class=""  value="1"/>
	       </c:when>
	       <c:otherwise>
	                     启用&nbsp;&nbsp;<input  name="radio" id="fstate" type="radio" class=""   value="0" />&nbsp;&nbsp;&nbsp;&nbsp;
                                 禁用&nbsp;&nbsp;<input  name="radio" id="fstate" type="radio" class=""  value="1" checked/>
	       </c:otherwise>

      
      </c:choose>  
       
      </td>
      <td class="bk02">&nbsp;&nbsp;<span class="font7"></span></td>
    </tr>  
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="10" colspan="2"></td>
    </tr>
    <tr>
      <td height="22" align="left" class="font8">&nbsp;&nbsp;&nbsp;&nbsp;
      <input name="Submit2422" type="button" class="button_09" onclick="checksub();" value="提交" />
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
checkLoad();
function checksub(){
	var fid= document.getElementById("fid").value;
	var fname= document.getElementById("fname").value;
	var pwd= document.getElementById("fpassword").value;
	var pwd2= document.getElementById("fpassword2").value;
	if(fid==""){alert("账号不能为空！"); return false;}
	
	if(pwd==""){alert("密码不能为空！"); return false;}
	if(pwd2==""){alert("重复密码不能为空！"); return false;}
	if(pwd!=pwd2){alert("两次输入密码不一致，请重新输入！"); return false;}
	if(fname==""){alert("员工姓名不能为空！"); return false;}
	
	var boxes = document.getElementsByName("fpopedom");
    var idArr = new Array();
    var j=0;
    for (var i = 0; i < boxes.length; i++)
    {
    	 if (boxes[i].checked)
 	     {
    		 idArr[j] = boxes[i].value;
    		 j++;
 	    }
     }
    var radio = document.getElementsByName("radio");
    var state="";
    for (var k = 0; k < radio.length; k++)
    {
    	 if (radio[k].checked)
 	     {   
     	     state=radio[k].value;
    		 document.getElementById("fstate").value=radio[k].value;  
 	    }
     }
    //
    if(idArr==""){
       alert("请选择权限！");
        return false;
    }else{
        document.getElementById("fpopedomArr").value=idArr;
    }
	new Ajax.Request('staff.do?method=updateStaff', {    
		asynchronous: true,
		method:'post',   
		parameters: "fid="+fid+"&fpassword="+pwd+"&fname="+fname+"&fpopedomArr="+idArr+"&fstate="+state,  
		onComplete: function(request) {
		 var id=request.responseText;
		 if(id.lastIndexOf("true")!=-1){
			 alert("修改成功！");
			 window.location.href="general.do?method=findStaffDataList";
		   }
		 
		  }  
		});
    //window.form1.submit();
}     

//加载页面时选中复选框
function checkLoad(){
	//权限
	var popedom='<%=staff[5]%>';
	
	var boxes = document.getElementsByName("fpopedom");
  
    for (var i = 0; i < boxes.length; i++)
    {
    	 if (popedom.lastIndexOf(boxes[i].value)!=-1)
 	     {   
    		 boxes[i].checked=true;
 	     }
     }
}
</script>