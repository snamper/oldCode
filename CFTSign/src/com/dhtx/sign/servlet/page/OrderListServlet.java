package com.dhtx.sign.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：OrderListServlet   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-9 下午07:54:08   
 * @version 1.0
 *  
 */
public class OrderListServlet extends HttpServlet {

	
	private int pageSize = 10;
	
	/**
	 * Constructor of the object.
	 */
	public OrderListServlet() {
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
		
		String user_id = (String)request.getSession().getAttribute("user_id");
		String pageNum = fc.getpv(request, "pageNum");//请求的页号
		if("".equals(pageNum)){
			pageNum = "1";
		}
		OrderDAO odao = new OrderDAO();
		List<Order> orderAllList = odao.getAllOrderByClientID(user_id);
		int orderCount = orderAllList.size();//总条数
		int pageCount = 1;//总页数
		int endNum = orderCount % pageSize;
		if(endNum > 0){
			pageCount = (orderCount / pageSize) + 1;
		}else{
			pageCount = (orderCount / pageSize);
		}
		
		//计算下标开始值
		int startIndex = (Integer.parseInt(pageNum) - 1) * pageSize;
		if(startIndex > orderCount - 1){
			startIndex = 0;
		}
		
		request.setAttribute("pageCount",pageCount);
		request.setAttribute("pageNum",pageNum);
		request.setAttribute("startIndex",startIndex);
		request.setAttribute("pageSize",pageSize);
		
		request.setAttribute("orderAllList", orderAllList);
		request.getRequestDispatcher("orderAllList.jsp").forward(request, response);
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
