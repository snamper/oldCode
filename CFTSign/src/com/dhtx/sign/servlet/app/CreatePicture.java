package com.dhtx.sign.servlet.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：CreatePicture   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-5 下午02:35:50   
 * @version 1.0
 *  
 */
public class CreatePicture extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CreatePicture() {
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
		try {
			OrderDAO odao = new OrderDAO();
			//获得一个订单
			Order order = odao.getOrderToApp();
			
			if(order != null){
				//锁定
				boolean isLocked =  odao.lockOrder(order.getFid());
				if(isLocked){
					StringBuffer sData = new StringBuffer();
					sData.append("<fid>" + order.getFid() +"</fid>");
					sData.append("<charTypeName>" + order.getfCharTypeName() +"</charTypeName>");
					sData.append("<fname>" + order.getfSignName() +"</fname>");
					sData.append("<fpictureName>" + order.getFpictureName() +"</fpictureName>");
					sData.append("<fpictureURL>" + order.getFpictureURL() +"</fpictureURL>");
					String message = fc.getResultStr("response", sData.toString(), "true", "获取订单成功,订单号:" + order.getFid());
					out.print(message);
					return;
				}else{
					String message = fc.getResultStr("response", "false", "获取订单失败,订单已被锁定，订单号:" + order.getFid());
					out.print(message);
				}
			}else{
				String message = fc.getResultStr("response", "false", "没有订单需要处理");
				out.print(message);
			}
		} catch (Exception e) {
			String message = fc.getResultStr("response", "false", "接口运行异常");
			out.print(message);
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
