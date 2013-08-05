package com.dhtx.sign.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.util.Config;
import com.tenpay.api.NotifyQueryRequest;
import com.tenpay.api.NotifyQueryResponse;
import com.tenpay.api.PayResponse;

/**
 * 类名称：PayOrderFinish   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 下午06:49:40   
 * @version 1.0
 *  
 */
public class PayOrderFinish extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PayOrderFinish() {
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

		try {
			//接收支付成功的重定向
			PayResponse res = new PayResponse(request, response,Config.TEST_SECRET_KEY);
			String notifyid = res.getNotifyId();
			NotifyQueryRequest req = new NotifyQueryRequest(Config.TEST_SECRET_KEY);
			// 设置在沙箱中运行，正式环境请设置为false
			req.setInSandBox(Config.INSANDBOX);
			// 设置财付通App-id: 财付通App注册时，由财付通分配
			req.setAppid(Config.TEST_APPID);
			//重定向信息只包含通知id，需要验证通知后得到订单号
			req.setParameter("notify_id", notifyid);
			NotifyQueryResponse notifyQueryRes = req.send();
			String out_trade_no = notifyQueryRes.getParameter("out_trade_no");
			request.setAttribute("orderid", out_trade_no);
			request.getRequestDispatcher("/pay_suc.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("PlanSignServlet").forward(request, response);
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
