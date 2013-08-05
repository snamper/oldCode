<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="com.jinrl.powermodule.common.*"%>
<%@page import="com.jinrl.powermodule.pojo.Tfunction"%>
<%@page import="com.jinrl.powermodule.pojo.Tmoduletype"%>

<%
  Tmoduletype mt = (Tmoduletype)request.getAttribute("moduleType");
  Tfunction function = (Tfunction)request.getAttribute("funtcion");
  String currentUserid = (String)session.getAttribute("currentUserid");
  String functionURL = (String)request.getAttribute("showfunctionURL");
  if(functionURL==null)functionURL="";

  String ip = request.getRemoteAddr();
  String ip_userid =ip+"_"+currentUserid;
  int sport = request.getLocalPort();
  String s = "http://localhost:"+sport+"/powermodule/checkIPUID?type=setipuserid&ip_userid="+ip_userid;
  String a1 = fc.SendDataViaPost(s,"","GB2312");


  if(function.getFbusiinfoid() == null || "".equals(function.getFbusiinfoid())){
	 String randomID = fc.GetOrderID("");
	 String fkey = function.getFkey();
	 String sign = fc.getMd5Str(currentUserid + randomID + fkey);
	 if(functionURL.indexOf("?") != -1){
		 response.sendRedirect(functionURL + "&currentUserid=" + currentUserid +"&randomid=" + randomID +"&sign=" + sign);
	 }else{
		 response.sendRedirect(functionURL + "?currentUserid=" + currentUserid +"&randomid=" + randomID +"&sign=" + sign);
	 }
  }else{
	  String se = session.getId();
	  String serviceIPPort = GetConfigValue.newInstance().serviceIPPort();
	  String checkLoginVersion = serviceIPPort + "." + se;


	  String sMd5 =currentUserid + function.getFid() + "H7F65E49JED5OIF4U4DE664C66D6EET3";
	  sMd5 = fc.getMd5Str(sMd5);

	  String s_url = "currentUserid=" + currentUserid +"&functionid=" + function.getFid() +"&sign=" + sMd5 + "&version=" + function.getFversion();

		if(mt.getFip() != null && !"".equals(mt.getFip().trim())){
			functionURL = "http://" + mt.getFip() + functionURL;
		}



		 if(functionURL.indexOf("?") != -1){
			 response.sendRedirect(functionURL + "&" + s_url + "&checkloginsign=" + checkLoginVersion);
		 }else{
			 response.sendRedirect(functionURL + "?" + s_url + "&checkloginsign=" + checkLoginVersion);
		 }
  }
%>

