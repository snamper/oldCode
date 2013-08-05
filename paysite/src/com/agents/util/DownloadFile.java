package com.agents.util;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.Element;

import com.agents.pojo.CoAgent;
import com.agents.service.GeneralService;
public class DownloadFile extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setContentType("text/html");
	javax.servlet.ServletOutputStream out = response.getOutputStream();
	String filepath=request.getRealPath("/");
	//String filename=new String(request.getParameter("filename").getBytes("utf-8"),"GBk").toString();
	
	String xmlfile=request.getParameter("xmlfile");//对应配置文件
	String starttime=request.getParameter("starttime");//日期条件
	String endtime=request.getParameter("endtime");
	
	 //代理商id
	 HttpSession session = request.getSession();
	 CoAgent agent = (CoAgent)session.getAttribute("currentAgent");
	 String agentId=agent.getFid();
	 Element element=Config.getConfigElement(agentId,xmlfile);
     String sql=element.getChild("exportData").getChildTextTrim("sql");
     String menuName=element.getChild("exportData").getChildTextTrim("menuName");
     String dateField=Config.getChildValue(agentId,xmlfile,"dateField");  
     if (sql.lastIndexOf("{agentId}")!=-1) {
    	 sql=sql.replace("{agentId}", agentId);     
	 }  
     String userId=(String) session.getAttribute("username");
	 if (sql.lastIndexOf("{clientId}")!=-1) {
		 sql=sql.replace("{clientId}", userId);            
	 }
	 
      //sql 添加日期条件
      sql=sql+" and "+dateField+" >= '"+starttime+" 00:00:00.000' and "+dateField+" <= '"+endtime+" 23:59:59.000'";   
      System.out.println("导出Sql==="+sql);
      GeneralService gs=new GeneralService();
      String create=gs.createExcel(filepath,menuName,sql,xmlfile,agentId);    
      
	String filename="数据.xls";
	//System.out.println("DownloadFile filename:" + filename);
	java.io.File file = new java.io.File(filepath + filename);
	if (!file.exists()) {
	System.out.println(file.getAbsolutePath() + " 文件不存在!");
	return;
}
//	String filepath=request.getRealPath("/");
//	String filename="usercert/"+new String(request.getParameter("filename").getBytes("utf-8"),"GBk").toString();
//	

	

// 读取文件流
java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
// 下载文件
// 设置响应头和下载保存的文件名
if (filename != null && filename.length() > 0) {
response.setContentType("application/x-msdownload");
response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"),"iso8859-1") + "");
if (fileInputStream != null) {
int filelen = fileInputStream.available();
//文件太大时内存不能一次读出,要循环
byte a[] = new byte[filelen];  
fileInputStream.read(a);
out.write(a);
}
fileInputStream.close();
out.close();    
  }
}

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/html");
PrintWriter out = response.getWriter();
out.println("<!DOCTYPE HTML PUBLIC -//W3C//DTD HTML 4.01 Transitional//EN>");
out.println("<HTML>");
out.println(" <HEAD><TITLE>A Servlet</TITLE></HEAD>");
out.println(" <BODY>");
out.print(" This is ");
out.print(this.getClass().getName());
out.println(", using the POST method");
out.println(" </BODY>");
out.println("</HTML>");
out.flush();
out.close();
} 
}