package com.jinrl.powermodule.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jinrl.powermodule.common.MySessionContext;
import com.jinrl.powermodule.common.fc;

public class checkIPUseridServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public checkIPUseridServlet() {
		super();
	}

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
		PrintWriter out = response.getWriter();
		ServletContext application=this.getServletContext();

		String checkloginsign = fc.getpv(request, "checkloginsign");

			if(!"".equals(checkloginsign)){
				MySessionContext myc= MySessionContext.getInstance();
				HttpSession sess = myc.getSession(checkloginsign);

				//新增方式验证

				//接收
				String rouserid = null;
				if (sess != null) 
					rouserid = (String)sess.getAttribute("currentUserid");
				  if (rouserid != null) {
						out.print("Y");
					} else {
						out.print("N");
					}
					out.flush();
					out.close();

			}else{

				//原有方式验证

				String ip_userid = request.getParameter("ip_userid");

				//设置
				String sType = fc.getpv(request, "type");
				if (sType.equals("setipuserid")){
					application.setAttribute(ip_userid, "T");
					return;
				}

				//接收
				if ("T".equals((String)application.getAttribute(ip_userid))) {
					out.print("Y");
				} else {
					out.print("N");
				}
				out.flush();
				out.close();
			}
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
