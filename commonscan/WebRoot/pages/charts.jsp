<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="jinrl_exploit_common.fc"%>
<%@page import="jinrl_exploit_Po.TbusiInfo"%>
<%@page import="jinrl_exploit_Po.TbusiField"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="/path.jsp" %>
<%

//String[][][] results = (String[][][])request.getAttribute("results");
List typeList = (List)request.getAttribute("typeList");
List itemList = (List)request.getAttribute("itemList");
List tableList = (List)request.getAttribute("tableList");
List picList = (List)request.getAttribute("picList");
List XandY = (List)request.getAttribute("XandY");
String pictype = (String)request.getAttribute("pictype");
//String[] chartsFields = (String[])request.getAttribute("chartsFields");
TbusiInfo busiInfo = (TbusiInfo)request.getAttribute("busiInfo");
String chartType = request.getParameter("charttype");
Boolean chartTpyeb = new Boolean(true);
if("single".equals(chartType)){
	chartTpyeb = new Boolean(false);
}
pageContext.setAttribute("chartTpyeb",chartTpyeb);
Integer nCount = (Integer)request.getAttribute("nCount");

float[] columncell = new float[itemList.size() + 1];

String currentUserid = request.getParameter("currentUserid");
String functionid = request.getParameter("functionid");
String sign = request.getParameter("sign");
String userid_functionid = "&currentUserid="+currentUserid+"&functionid="+functionid+"&sign="+sign;

String manageTitleURL = "";
String signforManageInfo = fc.getMd5Str(currentUserid + "16" + "H7F65E49JED5OIF4U4DE664C66D6EET3");
String signforManageField = fc.getMd5Str(currentUserid + "17" + "H7F65E49JED5OIF4U4DE664C66D6EET3");
manageTitleURL = "/commonscan/busiInfo.do?method=showUpdatePage&busiInfoid=22&dataid="+busiInfo.getFid()
+"&currentUserid="+currentUserid+"&functionid=16&sign=" + signforManageInfo;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
    <title>${requestScope.busiInfo.ftitle}-jrlimp1.0</title>
<script src="/commonscan/js/popup_layer.js" type="text/javascript" language="javascript"></script>
<script src="/commonscan/js/jquery-1.4.2.js" type="text/javascript" language="javascript"></script>
<script  src="/commonscan/js/highcharts.js" type="text/javascript" language="javascript"></script>
<link rel="shortcut icon" type="image/ico" href="favicon.ico" />
<link href="/commonscan/style/core.css" type="text/css" rel="stylesheet"/>
<link href="/commonscan/style/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/commonscan/js/NewTime.js"></script>
<%if(nCount.intValue() > 0 && picList != null && picList.size() > 0){%>
<script type="text/javascript">

			var chart;
			$(document).ready(function() {
				chart = new Highcharts.Chart({
					chart: {
						renderTo: 'container',
						defaultSeriesType: '<%=pictype%>',
						marginRight: 130,
						marginBottom: 35,
						marginTop: 35
					},
					title: {
						text: '',
						x: -20 //center
					},
					subtitle: {
						text: '',
						x: -20
					},
					xAxis: {
					title: {
							text: '<%=((TbusiField)XandY.get(0)).getFshowname()%>'
						},
						categories: [
						<%
						String s = "";
						for(int i = 0; i < itemList.size(); i++){
							if(i == 0){
								s += "'" + itemList.get(i) + "'";
							}else{
								s += ",'" + itemList.get(i) + "'";
							}
						}%>
						<%=s%>
						]
					},
					yAxis: {
						title: {
							text: '<%=((TbusiField)XandY.get(2)).getFshowname()%>'
						},
						plotLines: [{
							value: 0,
							width: 1,
							color: '#808080'
						}]
					},
					tooltip: {
						formatter: function() {
				                return '<b>'+ this.series.name +'</b><br/>'+
								<%if("pie".equals(pictype)){%> this.point.name +': '+ this.y <%}else{%> this.x +': '+ this.y; <%}%>
						}
					},
					legend: {
						layout: 'vertical',
						align: 'right',
						verticalAlign: 'top',
						x: -10,
						y: 100,
						borderWidth: 0
					},
					series: [
					<%
					int innersize = 10;
					int types = picList.size();
					int jump = (100-innersize) / types;
					for(int i = 0; i < types; i++){
					String[] line = (String[])picList.get(i);
					if(i != 0){
					%>
					,
					<%}%>
					{
						name:'<%=typeList.get(i)%>',
						size: '<%=innersize + jump%>%',
						innerSize: '<%=innersize%>%',
						data:[
						<%for(int j = 0; j < line.length; j++){
						String a = "0";
						if(!"-".equals(line[j])){
							a = line[j];
						}
						if(j != 0){
						%>
						,
						<%}%>
						{ name: '<%=itemList.get(j)%>', y: <%=a%>}
						<%}%>
						],
						<%if(i == types - 1){%>
						dataLabels: {
							enabled: <%if("pie".equals(pictype)){%>true<%}else{%>false<%}%>,
							color: '#000000',
							connectorColor: '#000000'
						}
						<%}else{%>
						dataLabels: {
							enabled: false
						}
						<%}%>

					}
					<%innersize += jump;}%>
					]
				});


			});

		</script>
<%} %>
  </head>
  <body style="FONT-FAMILY: 'arial'">
  <c:if test="${chartTpyeb}">
<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
    <td width="5"><img src="images/0100.jpg" width="6" height="35" alt=""></td>
    <td background="images/0101.jpg" style="PADDING-LEFT: 8px; COLOR: #000" class=font5 ><c:if test="${!empty isManager}"><a href="<%=manageTitleURL%>"  class="link2" style="text-decoration: none" target="_blank" title="设置业务信息"></c:if>${requestScope.busiInfo.ftitle}<c:if test="${!empty isManager}"></a></c:if></td>
    <td width="5"><img src="images/0102.jpg" width="6" height="35" alt=""></td>
  </tr>
       <form action="/commonscan/GraphServlet" method="get" name="form1">
       <input type="hidden" name="method" value="getBusiInfo" />
       <input type="hidden" name="busiInfoID" value="${requestScope.busiInfo.fid}"  />
       <input type="hidden" name="currentUserid" value="<%=currentUserid %>"  />
       <input type="hidden" name="functionid" value="<%=functionid %>"  />
       <input type="hidden" name="sign" value="<%=sign %>"  />
       <input type="hidden" name="selcetclick" value="1"  />
      <tr>
      <td width="6" height="33" background="images/0103.jpg"></td>
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
                    <%
                    if (fc.getpv(request, "rfy_" + oneSel.get(0)).equals(oneSel.get(i))){%>
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

              </select><input  <%if ( fc.getpv(request, "yy_oneTypeInp") == null || "".equals(fc.getpv(request, "yy_oneTypeInp"))){%><%}else{%> value="<%=fc.getpv(request, "yy_oneTypeInp")%>"<%} %> name="yy_oneTypeInp" type="text" size="12" />
          </c:if>
<%--这部分是单个填写值的 --%>
        <%List inpsels =(List) request.getAttribute("twoTypeSel"); %>
        <%for(int k = 0;k<inpsels.size();k++){ %>
        <%String [] inpsel =(String[])inpsels.get(k); %>
   <span style="font-size: 12px"><%= inpsel[0]%></span><%if(fc.getpv(request, "rfy_"+inpsel[1]) == null || fc.getpv(request, "rfy_"+inpsel[1]).equals("")){ %><input name="rfy_<%= inpsel[1]%>"  type="text"  size="12" /><%}else{ %><input   name="rfy_<%= inpsel[1]%>" value="<%= fc.getpv(request, "rfy_"+inpsel[1])%>" type="text"  size="12" /><%} %><%} %>

 <input type="submit"  value="查询" />

 &nbsp;&nbsp;切换<select name="pictypes" class="select_txt" onchange="javascript:document.form1.submit();">
              <option value="线型图" <c:if test="${pictype=='line'}"> selected="selected" </c:if> >线型图</option>
              <option value="柱状图" <c:if test="${pictype=='column'}"> selected="selected" </c:if> >柱状图</option>
              <option value="饼状图" <c:if test="${pictype=='pie'}"> selected="selected" </c:if> >饼状图</option>
            </select>
 </td>
<td width="6" background="images/0104.jpg"></td>
      </tr></form>
      <tr>
    <td><img src="images/0105.jpg" width="6" height="5" alt=""></td>
    <td background="images/0106.jpg"></td>
    <td><img src="images/0107.jpg" width="5" height="5" alt=""></td>
  </tr>
  <tr>
   <td height="5" colspan="3"></td>
  </tr>
</table>
</c:if>
<%if(nCount.intValue() > 0 && picList != null && picList.size() > 0){%>
<div id="container" <c:if test="${chartTpyeb}">  style="width: 100%; height: 520px; margin: 0 auto" </c:if> <c:if test="${!chartTpyeb}">  style="width: 100%; height: 200px; margin: 0 auto" </c:if> ></div>
<div style="margin-bottom: 30px;" ></div>
<c:if test="${chartTpyeb}">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#D2DDE1" class="font6">
  <tr  class="bg_01">
    <td align="center"  class="font7" nowrap="nowrap"><%=((TbusiField)XandY.get(1)).getFshowname()%>\<%=((TbusiField)XandY.get(0)).getFshowname() %></td>
    <c:forEach items="<%=itemList %>" var="item">
     <td align="center" class="font7">${item}</td>
    </c:forEach>
     <td align="center" class="font7"><strong>合计</strong></td>
  </tr>
  <%for(int i = 0; i < tableList.size(); i++){ %>
  <tr bgcolor="#FFFFFF">
    <td  align="center"  height="25" ><%=typeList.get(i) %></td>
    <%
    String[] line = (String[])tableList.get(i);
    float lineSum = 0f;
    for(int j = 0; j < line.length; j++){
    	float onecell = 0f;
    	try{
    		onecell = Float.parseFloat(line[j]);
    	}catch(Exception e){
    	}
    	lineSum += onecell;//行的总计
    	columncell[j] += onecell;//列的总计
    %>
    <td><%=line[j] %></td>
    <%} columncell[itemList.size()] += lineSum;%>
    <td><strong><%=lineSum %></strong></td>
  </tr>
  <%}%>
  <tr bgcolor="#FFFFFF">
  	<td  align="center" ><strong>合计</strong></td>
	<%for(int i = 0; i <columncell.length; i++){ %>
	<td><strong><%=columncell[i] %></strong></td>
	<%} %>
  </tr>
  <tr   height="35">
    <td  colspan="<%=columncell.length + 1 %>"  class="bg_02"   height="35"> </td>
   </tr>
   </table>
</c:if>
<%}else{ %>
<div style="margin: 30px;font-size: 12px;">没有查询到相应的数据,请检查查询条件是否正确!</div>
<%} %>
<c:if test="${chartTpyeb}">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr class="font999">
    <td height="30"  class="line01"> 2010 JRL-IMPv1.0   |  版权所有   </td>
  </tr>
</table>
</c:if>
  </body>
</html>
