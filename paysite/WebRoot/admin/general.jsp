<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/admin/includeMain.jsp"%>
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
<script type="text/javascript" src="<%=basePath%>admin/images/js/prototype.js"></script> 
<style>


.all{
	height: 41px;
	width: auto;
}
.searchType{
	float: left;
	height: 41px;
	width: auto;
	line-height:41px;  
}
.second{
	float: left;
	height: 41px;
	width: auto;
	line-height:41px;
}
.second{
	float: left;
	height: 41px;
	width: auto;
	line-height:41px;
}
.dateField{
	float: left;
	height: 41px;
	width: auto;
	line-height:41px;
}
.btn{
	float: left;   
	height: 41px;
	width: auto;
	line-height:41px; 
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
	showToDate();
}
//日期控件是否显示
function showToDate(){
	//var dateField='<%=dateField%>';
	// var searchTypeId  ='<%=searchTypeId%>';
	 var exportDataId  ='<%=exportDataId%>';
	 
		//if(dateField=="0"){
	    //   document.getElementById("dateField").style.display="none";
		//}
	    // if(searchTypeId=="0"){
	    // document.getElementById("searchType").style.display="none";
		//}  
	    if(exportDataId=="0"){
		     document.getElementById("exportDataId").style.display="none";
	    }    
}
//校验搜索关键字 
function isSearch() 
{ 
	var s=document.getElementById("key").value;
	if(s!=""){
		var patrn=/^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,30}$/; 
		   if (!patrn.exec(s)){
			   alert("请不要输入特殊字符！");
			return false 
		   }
	}  
    return true 
    }   
function test(){
	var xmlfile='<%=xmlName%>';
	var starttime=document.getElementById("starttime").value;
	var endtime=document.getElementById("endtime").value;
	document.location="downloadfile?xmlfile="+xmlfile+"&starttime="+starttime+"&endtime="+endtime;  
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
<div id="search">
<form action="<%=path%>/general.do?method=findDataList&name=<%=xmlName%>" method="post" onsubmit="return isSearch();"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="bg_02">
          <table>
            <tr>
              <td> 
                     <%if(!searchTypeId.equals("0")){ %>
<%--                 <div class="searchType" id="searchType">  --%>
	                                              关键字:&nbsp; <input name="key" type="text" id="key" class="txt_04"  value="<%=key%>" size="19" />&nbsp;类型&nbsp;
	                                            <select name="type" class="txt_04">      
			          <option value="all">全部类型</option>
			          <%
			          for(int j=0;j<value.length;j++){
			        	  if(value[j].equals(type)){
			          %>
			          <option value="<%=value[j]%>" selected="selected"> <%=name[j] %></option>
			          <%}else{ %>
			          <option value="<%=value[j]%>" ><%=name[j] %></option>
			          <%
			            }
			        	  }
			        	  %>  
		             </select>
		             <%}%>
<%--                  </div>--%>
<%--                  <div class="second">--%>
		            <%   
			         Map elementsMap=(Map)request.getAttribute("elements");
					   for(int i=0;i<elementsMap.size();i++){  
						   Map element=(Map)elementsMap.get(Integer.toString(i));
							 String typeT=element.get("type").toString();
							 String nameT=element.get("name").toString();
							 String Class=element.get("class").toString();  
							 String tag=element.get("tag").toString();
							 String tip=element.get("tip").toString();
					      if(typeT.equals("select")){
						   String option=fieldMap.get(nameT).toString();
					       %>
					      <%=tag %>:
					      <select name="<%=nameT %>" id="<%=nameT %>" class="txt_04"  >  
					      <option value=""></option> 
					       <%
					       List list=(List)request.getAttribute(nameT+"list");
					       %>   
					       <c:set var="c" value="<%=option%>"></c:set>
					       <c:forEach items="<%=list%>" var="lists" varStatus="count">
					           <c:if test="${lists[0]==c}">
					              <option value="${lists[0]}" selected>${lists[1]}</option> 
					           </c:if>
					           <c:if test="${lists[0]!=c}">  
					             <option value="${lists[0]}">${lists[1]}</option>  
					           </c:if>  
					       </c:forEach>
					      </select>
				           <%=tip %> 
				       <%   
				       }else if(typeT.equals("selectT")) {
				    	   String optionValue=fieldMap.get(nameT).toString();
				    	   String option=element.get("option").toString();
					       %>
					          <%=tag%>:
						      <select name="<%=nameT %>" id="<%=nameT %>" class="txt_04">
						       <option value=""></option> 
						       <%
						         String optionArr[]=option.split(",");
						         for(int k=0;k<optionArr.length;k++){  
						        	 String valueArr[]=optionArr[k].split("=");
						        	 if(optionValue.equals(valueArr[1])){  
						       %>   
						         <option value="<%=valueArr[1]%>" selected><%=valueArr[0]%></option>    
						         <%}else{ %>
						          <option value="<%=valueArr[1]%>"><%=valueArr[0]%></option>  
						       <%}}%>  
						       </select> 
				          <%
				         }    
					  }     
				     %>  
<%--                  </div>--%>
<%--                  <div class="dateField" id="dateField">--%>
                    <%if(!dateField.equals("0")){ %>
                  	<input name="Submit2" type="submit" class="" value="  " />&nbsp; 时间：开始&nbsp;
			        <input name="starttime" type="text" class="txt_03" readonly id="starttime" onfocus='HS_setDate(this)' value="<%=starttime%>"  size="10" />
			        &nbsp;至&nbsp;  
			        <input name="endtime" type="text" class="txt_03" readonly id="endtime" onfocus='HS_setDate(this)' value="<%=endtime%>"  size="10" />
			        <%} else{%>
			         <input name="starttime" type="hidden"  id="starttime"  value="<%=starttime%>"   />
			        <input name="endtime" type="hidden"  id="endtime"  value="<%=endtime%>"   />  
			        <%}%>  
<%--                  </div>--%>
<%--                  <div class="btn">  --%>
                      &nbsp;<input name="Submit" type="submit" class="search_button_01" value=" " />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%--                  </div>--%>
<%--                  </div>--%>
              </td>
            </tr>
          </table>
      
        </td>
    </tr>
  </table>  
  </form>
</div>
<!--搜索结束-->
<!--表单开始-->
<div id="search_list">
  <div id="search_title">搜索结果</div>
  <div id="search_list_content">
  <!--嵌套搜索表单开始-->
<!--表单开始-->
<table width="100%" border="0" cellpadding="0" cellspacing="1"  bgcolor="#E5E5E5"  class="search_table_01">
  <tr>
<%--    <td height="30" align="center" background="/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">选择</td>--%>
  <td height="30" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4">序号</td>
    <%
    for(int i=0;i<menuName.length;i++){
    %>
    <td align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4"><%= menuName[i]%></td>
    <%}%>
  </tr>
   <c:forEach items="${list}" var="lists" varStatus="count">
	  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >
<%--	    <td height="27" align="center" ><input type="checkbox" name="checkbox" value="${lists[0] }" /></td>--%>
	    <td height="24" align="center">${(noPage-1)*15+count.index+1} </td>
	    <td align="center">${lists[0] } </td>
	    <td align="center">${lists[1] } </td>
	    <td align="center">${lists[2] } </td>
	    <td align="center">${lists[3] } </td>
	    <td align="center">${lists[4] }</td>
	    <td align="center">${lists[5] }</td>
	    <td align="center">${lists[6] }</td>  
	    <td align="center">${lists[7] }</td>
	    <td align="center">${lists[8] }</td>
	    <td align="center">${lists[9] }</td>
	  </tr>
 </c:forEach>
<%--  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >--%>
<%--    <td height="27" colspan="2" align="left" >&nbsp;--%>
<%--        <input type="checkbox" name="checkbox82" value="checkbox" onclick="checkAll('checkbox');"/>全选--%>
<%--      </td>--%>
<%--    <td colspan="7" align="center">&nbsp;</td>--%>
<%--    <td align="center"><input name="Submit242" type="button" class="button_06" value="删除" onclick="del();" /></td>--%>
<%--  </tr>--%>
</table>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/main/images/19.jpg" class="bk01">
             	<%
       				 String url   =request.getContextPath() + pageUrl+"&nopage=";
             	     String delUrl=request.getContextPath()+"/general.do?method=delete&name="+xmlName+"&id=";
        		%>  
                <tr>  
                  <td height="43" align="left" class="font02">&nbsp;&nbsp;共 <span class="font01">${noCount}</span>条记录  |  分<span class="font01">${countPage}</span>页  |  每页 <span class="font01">15 </span>条  |  当前第 <span class="font01">${noPage}</span>页</td>
                 
                  <td align="right" class="font02">
                  <label id="exportDataId"> 
                       <input name="button" type="button" class="button_01" onclick="test();" value="导出" />
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
		                    <input name="Submit24" type="submit" class="button_02" value="  " onclick="javascript:window.location.href='<%=url%>1'" />
                  		</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == 1}">
	                    	<input name="Submit22" type="submit" class="button_03" value="  " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit22" type="submit" class="button_03" value="  " onclick="javascript:window.location.href='<%=url %>${noPage-1}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == countPage}">
	                    	<input name="Submit23" type="submit" class="button_04" value=" " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit23" type="submit" class="button_04" value=" " onclick="javascript:window.location.href='<%=url %>${noPage+1}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp;
                    <c:choose>
                    	<c:when test="${noPage == countPage}">
	                    	<input name="Submit25" type="submit" class="button_05" value=" " disabled="disabled"/>
                    	</c:when>
                    	<c:otherwise>
		                    <input name="Submit25" type="submit" class="button_05" value=" " onclick="javascript:window.location.href='<%=url %>${countPage}'"/>
                    	</c:otherwise>
                    </c:choose>
                    &nbsp; </td>
                </tr>   
              </table>
<!--表单结束-->
  <!--嵌套搜索表单结束-->
  </div>
</div>

<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>  
<SCRIPT LANGUAGE="JavaScript">    
function checkAll(str)    
{    
    var a = document.getElementsByName(str);    
    var n = a.length;    
    for (var i=0; i<n; i++)   { 
    a[i].checked = window.event.srcElement.checked;   
    } 
} 
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
    window.location.href='<%=delUrl%>'+idArr;     
  }
</SCRIPT>
