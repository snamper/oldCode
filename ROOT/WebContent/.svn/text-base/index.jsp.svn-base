<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="GB2312"%><%@ page import="convert.*"%><% 
String ip = "X." + request.getHeader("X-Client-Address");
if (ip.equals("X.") || ip.equals("X.null")) ip = request.getRemoteAddr(); 
System.out.print("[" + ip + "]" + request.getQueryString());
ConvertInterface ci = new ConvertInterface();
String s = ci.convertInterface(request, response);
System.out.print("[" + ip + "]" + s);
if ((s.indexOf("非法访问") == 0) || (s.equals("非法访问,URL路径含有保留字")))
	response.sendError(404);
else{
	out.print(s);
}
 %>
  