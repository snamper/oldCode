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
			window.onload=function(){aaab('container1');};
			function aaab(container) {
				//var container = "container2";
				//$("#container1").css('visibility','visible');
				//$("#"+container).removeAttr("style");
				chart = new Highcharts.Chart({
					chart: {
						renderTo: container,
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


			}

		</script>
<%} %>
  </head>
  <body style="FONT-FAMILY: 'arial'">
  <c:if test="${chartTpyeb}">
<table width="100%" height="40" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
	    <td width="5"><img src="images/0100.jpg" width="6" height="35" alt=""></td>
	    <td background="images/0101.jpg" style="PADDING-LEFT: 8px; COLOR: #000" height="35" class=font5 >${requestScope.busiInfo.ftitle}</td>
	    <td width="5"><img src="images/0102.jpg" width="6" height="35" alt=""></td>
  	  </tr>
</table>
</c:if>
<%if(nCount.intValue() > 0 && picList != null && picList.size() > 0){%>
<div id="container1" <c:if test="${chartTpyeb}">  style="width: 100%; height: 520px; margin: 0 auto" </c:if> <c:if test="${!chartTpyeb}">  style="width: 100%; height: 200px; margin: 0 auto" </c:if> ></div>
<%}else{ %>
<div style="margin: 30px;font-size: 12px;">没有查询到相应的数据,请检查查询语句是否正确!</div>
<%} %>
  </body>
</html>
