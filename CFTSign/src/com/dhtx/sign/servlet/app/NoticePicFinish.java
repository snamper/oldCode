package com.dhtx.sign.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：NoticePicFinish   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-5 下午03:46:04   
 * @version 1.0
 *  
 */
public class NoticePicFinish extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public NoticePicFinish() {
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
		PrintWriter out = response.getWriter();
		String orderID = fc.getpv(request, "orderID");
		String sign = fc.getpv(request, "sign");
		//验证MD5
		if("".equals(orderID) || !sign.equals(fc.getMd5Str(orderID + "C6g32jL2gJ7ZgxOdKgPorSG5GKd6THbT"))){
			System.out.println("app通知MD5验证失败,IP:"+ request.getRemoteAddr());
			out.print("请求非法，你的IP已被记录");
		}else{
			OrderDAO odao = new OrderDAO();
			boolean flag = odao.appUpdateState(orderID);
			if(flag){
				String message = fc.getResultStr("response", orderID, "true", "app通知成功,订单号:" + orderID);
				out.print(message);
			}else{
				String message = fc.getResultStr("response", "false", "app通知失败,订单号:" + orderID);
				out.print(message);
			}
		}
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
