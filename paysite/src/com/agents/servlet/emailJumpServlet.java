    package com.agents.servlet;
	import java.io.IOException;
	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.agents.service.ClientService;

	public class emailJumpServlet extends HttpServlet {
		/**
		 * Constructor of the object.
		 */
		public emailJumpServlet() {
			super();
		}  
		public void destroy() {
			super.destroy(); // Just puts "destroy" string in log
			// Put your code here
		}
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			    String name = request.getParameter("key");
			    ClientService cService = new ClientService();
				boolean flag = cService.changeUserState(name);
				System.out.println("用户状态修改成功"+flag);  
				response.sendRedirect("agentPage.do?method=findAgentByDomain");  
			  
		}
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			this.doGet(request, response);
		}
		public void init() throws ServletException {
		}
	}
