package com.dhtx.sign.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.dao.ProductDAO;
import com.dhtx.sign.pojo.BGPicture;
import com.dhtx.sign.pojo.CharType;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.pojo.Product;
import com.dhtx.sign.util.Tools;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：CreateOrder   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-4 上午11:17:00   
 * @version 1.0
 *  
 */
public class CreateOrder extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CreateOrder() {
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
		String user_id = (String)request.getSession().getAttribute("user_id");
		String name = fc.getpv(request, "name");
		String productID = fc.getpv(request, "radiobutton");
		
		System.out.println(name + productID);
		ProductDAO proDao = new ProductDAO();
		Product pro = proDao.getProductById(productID);
		if(pro == null){
			request.setAttribute("errorInfo", "商品不存在");
			request.getRequestDispatcher("PlanSignServlet").forward(request, response);
			return;
		}
		
		//获取所有当前商品的字体
		List<CharType> charTypeList = proDao.getAllProCharType(productID);
		//随即抽取一个字体
		CharType ct = (CharType)Tools.getRandomObj(charTypeList);
		
//		//获取所有当前商品的背景
//		List<BGPicture> bgPicList = proDao.getAllProBGPicture(productID);
//		//随即抽取一个背景
//		BGPicture bgp = (BGPicture) Tools.getRandomObj(bgPicList);
		
		//创建订单
		Order order = new Order();
		String orderID = fc.getGUID6("");
		order.setFid(orderID);
		order.setFclientID(user_id);
		order.setfSignName(name);
		order.setFproductName(pro.getFname());
		order.setFmoney(pro.getFpayMoney());
		order.setfCostMoney(pro.getFpayMoney());
//		order.setFbgPictureID(bgp.getFid());
		order.setfCharTypeName(ct.getFname());
		
		OrderDAO orderDao = new OrderDAO();
		boolean orderResult = orderDao.addOrder(order);
		System.out.println(orderResult);
		if(orderResult){
			request.setAttribute("orderid", orderID);
			request.setAttribute("goodname", pro.getFname());
			request.setAttribute("price", pro.getFpayMoney());
			request.getRequestDispatcher("PayOrder").forward(request, response);
		}else{
			System.out.println("下单失败,请重新下单");
			request.setAttribute("errorInfo", "下单失败,请重新下单");
			request.getRequestDispatcher("PlanSignServlet").forward(request, response);
			return;
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
