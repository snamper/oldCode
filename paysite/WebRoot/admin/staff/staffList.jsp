<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/admin/includeCheckLogin.jsp"%> 
<%    
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="images/style/global/master_02.css" rel="stylesheet" type="text/css" />
<link href="images/style/content/content_02.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="images/js/js_01/date.js"></script>
<script type="text/javascript" src="images/js/js_01/scrollings.js"></script>
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
	var s=0;//document.getElementById("key").value;
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
<div id="subtitle">员工列表:</div>
<!--搜索开始-->
<!--搜索结束-->
<!--表单开始-->
<div id="search_list">
  <div id="search_title">搜索结果</div>
  <div id="search_list_content">
  <!--嵌套搜索表单开始-->    
<!--表单开始-->
<form action="<%=path%>/general.do?method=findStaffDataList" method="post" onsubmit="return isSearch();"> 
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E5E5E5"  class="search_table_01">
  <tr>
    <td height="27" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">选择</td>
    <td height="27" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">序号</td>
    <td height="27" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">员工姓名</td>
<%--    <td height="30" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">类型</td>--%>
    <td height="27" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">员工状态</td>
    <td height="27" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">修改权限</td>
  </tr>  
   <c:forEach items="${staffs}" var="lists" varStatus="count">
	  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >
	    <td height="27" align="center" ><input type="checkbox" name="checkbox" value="${lists[0] }" /></td>
	    <td height="24" align="center">${(noPage-1)*15+count.index+1} </td>
	    <td align="center">${lists[1] } </td>
     <c:choose>
     <c:when test="${lists[3]=='0' }">  
        <td align="center">启用</td>
     </c:when>
     <c:otherwise>
        <td align="center">禁用</td>
     </c:otherwise>
     </c:choose>
	    <td align="center"><a href="staff.do?method=modifyStaff&fid=${lists[0]}">修改</a></td>  
	  </tr>
 </c:forEach>
</table>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/main/images/19.jpg" class="bk01">
             	<%
       				 String url = request.getContextPath() +"/general.do?method=findStaffDataList&nopage=";
        		%>  
                <tr>    
                  <td height="43" align="left" class="font02">&nbsp;&nbsp;共 <span class="font01">${noCount}</span>条记录  |  分<span class="font01">${countPage}</span>页  |  每页 <span class="font01">15 </span>条  |  当前第 <span class="font01">${noPage}</span>页</td>
                 
                  <td align="right" class="font02">
                  <label id="exportDataId"> 
                  
                                                   全选&nbsp;&nbsp;&nbsp;
        			<input type="checkbox" name="checkbox2" onclick="allckeck_();" id="checkbox2" value="checkbox" onclick="checkAll(this.form,'checkbox');"/>
        			&nbsp;&nbsp;&nbsp;&nbsp;
        			
        			  
        			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
        			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                     <input name="Submit243" type="button" class="button_06" value="添加" onclick="add();"/>
                     &nbsp;&nbsp;&nbsp;&nbsp; 
                     <input name="Submit242" type="button" class="button_06" value="删除" onclick="del();" />
                   </label>
                  </td>
                  <td align="right" width="200" class="font02">
                  <script>
				      function goIn(){ 
				    	  var aa = document.getElementById("switchnopage").value;
				    	  window.location.href='<%=url %>'+aa;
				      }    
     			  </script>  
     			          转到:
                    <input name="nopage" id="switchnopage" type="text" class="txt_04" size="4" />&nbsp;页  &nbsp;
                    <input name="Submit222" type="submit" class="button_01" value="跳转" onclick="goIn();"/>
                  </td>
                  <td width="200" align="right">&nbsp;
                  	<c:choose>
                  		<c:when  test="${noPage == 1}">
	                      <input name="Submit24" type="submit" class="button_02" value="  " disabled="disabled"/>
                  		</c:when>
                  		<c:otherwise>
		                    <input name="Submit24" type="button" class="button_02" value="  " onclick="javascript:window.location.href='<%=url%>1'" />
                  		</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == 1}">
	                    	<input name="Submit22" type="submit" class="button_03" value="  " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit22" type="button" class="button_03" value="  " onclick="javascript:window.location.href='<%=url %>${noPage-1}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == countPage}">
	                    	<input name="Submit23" type="submit" class="button_04" value=" " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit23" type="button" class="button_04" value=" " onclick="javascript:window.location.href='<%=url %>${noPage+1}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp;  
                    <c:choose>
                    	<c:when test="${noPage == countPage}">
	                    	<input name="Submit25" type="submit" class="button_05" value=" " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>   
		                    <input name="Submit25" type="button" class="button_05" value=" " onclick="javascript:window.location.href='<%=url %>${countPage}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp; </td>
                </tr>   
              </table>
              </form>
<!--表单结束-->
  <!--嵌套搜索表单结束-->
  </div>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>  
<SCRIPT LANGUAGE="JavaScript">  
function add(){
	window.location.href='<%=request.getContextPath()%>'+"/staff.do?method=findAddStaff";    
}
<%--function checkAll(str)    --%>
<%--{    --%>
<%--    var a = document.getElementsByName(str);    --%>
<%--    var n = a.length;    --%>
<%--    for (var i=0; i<n; i++)   { --%>
<%--       a[i].checked = window.event.srcElement.checked;   --%>
<%--    } --%>
<%--} --%>
function del(){
	
	var boxes = document.getElementsByName("checkbox");
    var idArr = new Array();
    var j=0;
    for (var i = 0; i < boxes.length; i++)
    {
    	 if (boxes[i].checked)
 	     {
    		 idArr[j] = "'"+boxes[i].value+"'";
    		 j++;
 	    }
     }
    new Ajax.Request('staff.do?method=deleteStaff',{      
		asynchronous: true,
		method:'post',   
		parameters: "fid="+idArr,  
		onComplete: function(request) {
		 var id=request.responseText;
		 if(id.lastIndexOf("true")!=-1){
			 alert("删除成功！");
			 window.location.href="general.do?method=findStaffDataList";
		    }
		  }  
	});
  }
<%--function checkAll(frm,name){--%>
<%--	   var es= frm.elements[name];  --%>
<%--	      for(var i=0,e;e = es[i],i<es.length;i++){--%>
<%--	         e.checked=1;--%>
<%--	   }--%>
<%--}--%>
function allckeck_(){
	var ck = document.getElementById("checkbox2");
	if(ck.checked==true){
		checkAll(1);
	}else{
		checkAll(0);
	}  
}

function checkAll(checkall){
	var checkdata = document.getElementsByName("checkbox");
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


</SCRIPT>    
