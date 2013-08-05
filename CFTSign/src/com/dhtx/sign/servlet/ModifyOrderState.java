package com.dhtx.sign.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.Config;
import com.tenpay.api.NotifyQueryRequest;
import com.tenpay.api.NotifyQueryResponse;
import com.tenpay.api.PayResponse;

/**
 * 类名称：ModifyOrderState   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 下午06:48:37   
 * @version 1.0
 *  
 */
public class ModifyOrderState extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ModifyOrderState() {
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
		// Constants.TEST_SECRET_KEY 签名密钥: 开发者注册时，由财付通分配
		// 创建支付结果反馈响应对象：支付跳转接口为异步返回，用户在财付通完成支付后，财付通通过回调return_url和notify_url向财付通APP反馈支付结果。
		PayResponse res = new PayResponse(request, response, Config.TEST_SECRET_KEY);
		// 获取通知id:支付结果通知id，支付成功返回通知id，要获取订单详细情况需用此ID调用通知验证接口。
		String notifyid = res.getNotifyId();
		// 初始化通知验证请求:财付通APP接收到财付通的支付成功通知后，通过此接口查询订单的详细情况，以确保通知是从财付通发起的，没有被篡改过。
		NotifyQueryRequest req = new NotifyQueryRequest(Config.TEST_SECRET_KEY);
		// 是否在沙箱运行
		req.setInSandBox(Config.INSANDBOX);
		// 设置财付通App-id: 财付通App注册时，由财付通分配
		req.setAppid(Config.TEST_APPID);
		req.setParameter("notify_id", notifyid);
		NotifyQueryResponse notifyQueryRes = req.send();
		if (notifyQueryRes.isPayed()) {// 已经支付则更新数据库状态
			// 获取支付的订单号和金额
			String out_trade_no = notifyQueryRes.getParameter("out_trade_no");
			String total_fee = notifyQueryRes.getParameter("total_fee");
			
			System.out.println("收到CFT通知,订单号："+out_trade_no);
			//查询订单
			OrderDAO orderDao = new OrderDAO();
			Order order = orderDao.getOrderById(out_trade_no);
			if(Float.parseFloat(order.getFmoney()) * 100 == Float.parseFloat(total_fee)){
				
			// 以下为更新订单数据库状态
				boolean flag = orderDao.updateOrderState(out_trade_no);
				if(flag){
					request.setAttribute("orderid", out_trade_no);
					request.setAttribute("resultInfo", "支付成功");

					// 订单处理成功后，告知财付通通知发送成功
					// 如不加上下行代码会导致财付通不停通知财付通app，即不停里调用财付通app的notify_url进行通知
					res.acknowledgeSuccess();
				}else{
					System.out.println("error,更新订单状态失败");
				}
			}else{
				System.out.println("error,支付成功金额与订单金额不相同");
			}
			
		} else {// 未正常支付，或者调用异常，如调用超时、网络异常
			// 记录日志 不做任何操作
			System.out.println("支付未成功状态说明:" + notifyQueryRes.getPayInfo());
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
