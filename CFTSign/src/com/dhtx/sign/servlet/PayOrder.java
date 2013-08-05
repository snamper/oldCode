package com.dhtx.sign.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.Config;
import com.dhtx.sign.util.Tools;
import com.tenpay.api.PayRequest;

/**
 * 类名称：PayOrder   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 下午05:31:26   
 * @version 1.0
 *  
 */
public class PayOrder extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PayOrder() {
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

		doPost(request, response);
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

		String orderid = (String)request.getAttribute("orderid");
		OrderDAO orderDao = new OrderDAO();
		Order order = orderDao.getOrderById(orderid);
		if(order == null){
			request.setAttribute("errorInfo", "此订单不能被支付");
			request.getRequestDispatcher("planSign.jsp").forward(request, response);
			return;
		}else{
			// 创建支付请求对象
			PayRequest req = new PayRequest(Config.TEST_SECRET_KEY);
			// 设置在沙箱中运行，正式环境请设置为false
			req.setInSandBox(Config.INSANDBOX);
			// 设置财付通App-id: 财付通App注册时，由财付通分配
			req.setAppid(Config.TEST_APPID);
			// *************************以下业务参数名称参考开放平台sdk文档-JAVA****************************
			// 设置用户客户端ip:用户IP，指用户浏览器端IP，不是财付通APP服务器IP
			req.setParameter("spbill_create_ip", request.getRemoteAddr());
			// 设置商户系统订单号：财付通APP系统内部的订单号,32个字符内、可包含字母,确保在财付通APP系统唯一
			req.setParameter("out_trade_no", order.getFid());
			// 设置商品名称:商品描述，会显示在财付通支付页面上
			req.setParameter("body", order.getFproductName());
			// 设置通知url：接收财付通后台通知的URL，用户在财付通完成支付后，财付通会回调此URL，向财付通APP反馈支付结果。
			// 此URL可能会被多次回调，请正确处理，避免业务逻辑被多次触发。需给绝对路径，例如：http://wap.isv.com/notify.asp
			req.setParameter("notify_url", Tools.getBasePath(request) + "ModifyOrderState");
			// 设置返回url：用户完成支付后跳转的URL，财付通APP应在此页面上给出提示信息，引导用户完成支付后的操作。
			// 财付通APP不应在此页面内做发货等业务操作，避免用户反复刷新页面导致多次触发业务逻辑造成不必要的损失。
			// 需给绝对路径，例如：http://wap.isv.com/after_pay.asp，通过该路径直接将支付结果以Get的方式返回
			req.setParameter("return_url", Tools.getBasePath(request) + "PayOrderFinish");
			String totalFee = String.valueOf(Math.round(Float.parseFloat(order.getFmoney()) * 100));
			// 设置订单总金额，单位为分
			req.setParameter("total_fee", totalFee);
			req.setParameter("request_token", (String)request.getSession().getAttribute("request_token"));
			String payUrl = req.getURL();
			System.out.println("----pay request: " + payUrl);
			// 跳转到支付url
			response.sendRedirect(payUrl);
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
