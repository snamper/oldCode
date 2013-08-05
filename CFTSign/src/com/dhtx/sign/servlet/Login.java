package com.dhtx.sign.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.util.Config;
import com.tenpay.api.ShareLoginState;

/**
 * 类名称：Login   
 * 类描述：从小钱包跳入财付通app处理类,接收登录用户信息
 * 创建人: renfy   
 * 创建时间：2012-1-4 上午10:47:56   
 * @version 1.0
 *  
 */
public class Login extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Login() {
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

		// 务必设置此header,否则将导致iframe中无法获取cookie,无法跟踪session
		response.setHeader(
				"P3P",
		"CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
		
		String user_id = (String)request.getSession().getAttribute("user_id");
		if (user_id == null) {
			ShareLoginState state = new ShareLoginState(request,Config.TEST_SECRET_KEY);
			// 获取用户id
			request.getSession().setAttribute("user_id", state.getUserId());
			request.getSession().setAttribute("request_token", state.getParameter("request_token"));
			System.out.println("request_token:"+state.getParameter("request_token"));
			System.out.println("user_id:" + state.getUserId());
		}
		
		request.getRequestDispatcher("PlanSignServlet").forward(request, response);
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
		doGet(request, response);
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
