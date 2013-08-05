<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/admin/includeStatMain.jsp"%>  
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
 var dateField='<%=dateField%>';
 var searchTypeId  ='<%=searchTypeId%>'
	if(dateField=="0"){
       document.getElementById("dateField").style.display="none";
	}
 if(searchTypeId=="0"){
     document.getElementById("searchType").style.display="none";
	}   
	
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
<div id="search">
<form action="<%=path%>/general.do?method=findStatList&name=<%=xmlName%>" method="post" onsubmit="return isSearch();"> 
 
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="bg_02">
        <table>
          <tr>
             <td id="searchType">
                                         关键字：&nbsp; <input name="key" type="text" id="key" class="txt_01"  value="<%=key%>" size="19" />&nbsp;类型&nbsp;
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
             </td>
             <td>
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
				    	   String option=element.get("option").toString();
				       %>
				          <%=tag %>:
					      <select name="<%=nameT %>" id="<%=nameT %>" class="txt_04" >
					       <option value=""></option> 
					       <%
					         String optionArr[]=option.split(",");
					         for(int k=0;k<optionArr.length;k++){  
					        	 String valueArr[]=optionArr[k].split("=");
					       %>   
					         <option value="<%=valueArr[1]%>"><%=valueArr[0]%></option>  
					       <%}%>   
				     <%}    
					 }     
				     %>    
             </td>
             <td id="dateField">
                <input name="Submit2" type="submit"  class="search_button_02" value="  " />&nbsp; 时间：开始&nbsp;
		        <input name="starttime" type="text" readonly class="txt_03" id=times onfocus=HS_setDate(this) value="<%=starttime%>"  size="10" />
		        &nbsp;至&nbsp;
		        <input name="endtime" type="text" readonly class="txt_03" id=times onfocus=HS_setDate(this) value="<%=endtime%>"  size="10" />
             </td>
             <td>
	             &nbsp;
	             <input name="Submit" type="submit" class="search_button_01" value=" " />
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
<!--表单开始--><br /><br /><br />
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E5E5E5"  class="search_table_01">
  <tr  >
    <%
    for(int i=0;i<menuName.length;i++){
    %>
    <td height="30" align="center" background="<%=basePath %>admin/images/img_02/17.jpg" bgcolor="#FFFFFF" class="font4"><%= menuName[i]%></td>
    <%}%>
  </tr>
   <c:forEach items="${list}" var="lists" varStatus="count">
	  <tr bgcolor="#ffffff" onmouseover="this.style.background='url('+oImg1.src+')'" onmouseout="this.style.background='url('+oImg2.src+')'"  >
	    <td height="30" align="center">${lists[0] } </td>
	    <td align="center">${lists[1] } </td>
	    <td align="center">${lists[2] } </td>
	    <td align="center">${lists[3] } </td>
	    <td align="center">${lists[4] }</td>
	    <td align="center">${lists[5] }</td>
	    <td align="center">${lists[6] }</td>  
	    <td align="center">${lists[7] }</td>
	  </tr>
 </c:forEach>
</table>
  </div>
</div>
<div id="search_list01"></div>
<!--表单结束-->
</body>
</html>  