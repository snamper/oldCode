<%@ page language="java" import="com.tenpay.api.MicroBlogSendRequest" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
 <%
 MicroBlogSendRequest microBlogRequest = (MicroBlogSendRequest)request.getAttribute("microBlogRequest");
 String a = microBlogRequest.toHTML();
 out.println(microBlogRequest.toHTML());
%>