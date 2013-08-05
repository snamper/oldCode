<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.agents.pojo.CoAgent"%>
<%@page import="java.lang.*"%>
<%@page import="com.agents.util.Config"%>
<%@page import="java.io.File"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
if(agent == null){
	response.sendRedirect("agentPage.do?method=findAgentByDomain");
	return;
}
String fname=agent.getFname();
String fid=agent.getFid();  
String styleConfig=agent.getfStyleConfig();
String fphotoName="img_"+styleConfig;
String fmenuConfig=agent.getfMenuConfig();
//加载菜单  
   Config c=new Config();
	    System.out.println("left 菜单加载:"+c.getFirstlevelMenu(fid,fmenuConfig));  
	    Map firstMenu=c.firstMenu;
	    Map secondMenu=c.secondMenu;  
  String userType=(String)session.getAttribute("usertype");
  String fPopedom="";
  //如果为员式登录，则加载权限
  if(userType.equals("staff")){
	  fPopedom=(String)session.getAttribute("fPopedom");
  }  
%>
<html>  
<style>
.content{
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=fname %></title>
<link href="images_<%=fid %>/style/global/master.css" rel="stylesheet" type="text/css" />
<link href="images_<%=fid %>/style/left/left.css" rel="stylesheet" type="text/css" />

<script src="js/prototype.lite.js" type="text/javascript"></script>
<script src="js/moo.fx.js" type="text/javascript"></script>
<script src="js/moo.fx.pack.js" type="text/javascript"></script>

</head>
<body class="left_body">
<!--左侧开始-->
<div id="left">
  <!--扩展栏目2 通过js调用标签开始-->
  <script type="text/javascript" src="admin/images_<%=fid %>/js/js/expand_column02.js"></script>
  <!--扩展栏目2 通过js调用标签结束-->
  <div id="sidebar">
    <ul>   
    <%
    for (int i = 0; i < firstMenu.size(); i++) {
    	String title=firstMenu.get(Integer.toString(i)).toString();
    %>
      <li class="type"><img src="images_<%=fid %>/img/sidebar_01.gif" /><a href="javascript:void(0)"><%=title%></a><br class="clear"/> </li>
    	 <div class="content">
	       <%
				 Map mapT=(Map)secondMenu.get(Integer.toString(i));
					for (int j = 0; j < mapT.size(); j++) {
						String Str[]=mapT.get(Integer.toString(j)).toString().split("@");
						String title2=Str[0];
						String href=Str[1];
						String id=Str[2];
	       %>
	           <dl id="<%=id%>" >  
			        <dt><img src="images_<%=fid %>/img/sidebarIcon_01.gif" /></dt>
			        <dd ><a href="<%=path%>/<%=href %>" target="main"><%=title2%></a></dd>
		       </dl>
	      <%}%>
     	</div>
     <%}%> 
    </ul>   
  </div>   
</div>   
<!--左侧结束-->
</body>
</html>
   <script type="text/javascript">
   checkPopedom();
		var contents = document.getElementsByClassName('content');
		var toggles = document.getElementsByClassName('type');
		var myAccordion = new fx.Accordion(
			toggles, contents, {opacity: true, duration: 400}
		);
		myAccordion.showThisHideOpen(contents[0]);
		// 判断员工权限
		function checkPopedom(){
		   var  usertype='<%=userType%>';
		   var  fPopedom='<%=fPopedom%>';
		  
		   if(usertype=="staff"){
			   var popedomArr=fPopedom.split(",");  
			   var dls=document.getElementsByTagName("dl");
			   for(var k=0;k<dls.length;k++){
				   dls[k].style.display="none";  
			   }
			   for(var i=0;i<popedomArr.length;i++){
				   document.getElementById(popedomArr[i]).style.display="block";  
			   }
		   }
		}
	</script>
