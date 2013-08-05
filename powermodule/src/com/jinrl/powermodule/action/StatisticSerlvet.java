package com.jinrl.powermodule.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class StatisticSerlvet extends HttpServlet implements ServletContextListener {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
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
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		System.out.println(" 不再使用此类，删除web.xml中的配置 ");
		/**
		 *
<listener>
  <listener-class>com.jinrl.powermodule.action.StatisticSerlvet</listener-class>
 </listener>
		 */
	}

	private Timer timer = null;

	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer();
//		timer.scheduleAtFixedRate(new ExcuteStatistic(), 1000, 10 * 1000);
//		timer.scheduleAtFixedRate(new SendWarnEmail(), 2000, 60 * 1000);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();

	}
}
