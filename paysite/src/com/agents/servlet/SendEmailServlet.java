package com.agents.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.agents.util.fc;
  
public class SendEmailServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SendEmailServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			HttpSession session = request.getSession();
			String username=(String)session.getAttribute("username");
			if(username!=null && username!=""){
				String sError = "";
				SimpleDateFormat tempDates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datetimes = tempDates.format(new java.util.Date());
				String jinbao="提现申请";
				String cardInfo = "商户:\r"+username+"\r在:\r"+datetimes+"\r申请提现！";
//	    		funcejb.sendEmail("zhaoliang@jinrl.com", jinbao, cardInfo);//当久游卡号长度为16位，并且卡号以9开始的发送短信警报
	    		//funcejb.sendEmail("ouyang@jinrl.com", jinbao, cardInfo);//当久游卡号长度为16位，并且卡号以9开始的发送短信警报
//	    		funcejb.sendEmail("jiangyong@jinrl.com", jinbao, cardInfo);//当久游卡号长度为16位，并且卡号以9开始的发送短信警报

	    		fc.sendEmail("zhaoliang@jinrl.com", jinbao, cardInfo);
	    		fc.sendEmail("jiangyong@jinrl.com", jinbao, cardInfo);
	    		sError=java.net.URLEncoder.encode("已通知财务人员进行提现处理,请稍等... " ,"utf-8");
	    		response.sendRedirect("page4_2.jsp?sError="+sError);
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
