package com.dhtx.tborder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.dao.ClientDAO;
import com.dhtx.util.fc;

/**
 * 类名称：LoginServlet   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2011-12-13 下午05:13:59   
 * @version 1.0
 *  
 */
public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
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
		request.setCharacterEncoding("GB2312");
		response.setContentType("text/html;charset=GB2312");
		PrintWriter out = response.getWriter();
		System.out.println(request.getRemoteHost());
		
		String userName = fc.getpv(request, "username").trim();
		String passWord = fc.getpv(request, "password").trim();
		String sign = fc.getpv(request, "sign").trim();
		//
		String clientMoneyStr = fc.getpv(request, "colientmoney").trim();
		
		//信息不全，返回失败
		if("".equals(userName) || "".equals(passWord) || "".equals(sign)){
			String result = fc.getResultStr("response", "false", "登录信息不完全");
			out.print(result);
			return;
		}
		
		//验证DM5
		if(!checkMD5(userName,passWord,sign)){
			String result = fc.getResultStr("response", "false", "MD5验证失败");
			out.print(result);
			return;
		}
		
		//检验用户名密码
		ClientDAO clientDao = new ClientDAO();
		Map<String,String> clientMap = clientDao.checkLogin(userName,passWord);
		String fFaceID = clientMap.get("fFaceID");
		String fKey = clientMap.get("fKey");
		if(fFaceID == null || fKey == null){
			//用户密码错误，或者接口ID为空，或者key为空
			String result = fc.getResultStr("response", "false", "商户信息错误");
			out.print(result);
			return;
		}
		
		//查询客户余额，并返回
		if("1".equals(clientMoneyStr)){
			String clientMoeny = clientDao.getClientMoney(userName);
			String result = fc.getResultStr("response", clientMoeny, "true", "获取商户余额成功");
			out.print(result);
			return;
		}
		
		//获取客户商品
		List<String[]> productList = clientDao.getClientProduct(userName);
		if(productList != null && !productList.isEmpty()){
			StringBuffer data = new StringBuffer();
			data.append("<faceid>" + fFaceID + "</faceid>");
			data.append("<key>" + fKey + "</key>");
			data.append("<products>");
			for(String[] pro : productList){
				data.append("<product>");
				
				data.append("<productid>" + pro[0] + "</productid>");
				data.append("<productname>" + pro[1] + "</productname>");
				data.append("<productprice>" + pro[2] + "</productprice>");
				
				data.append("</product>");
			}
			data.append("</products>");
			String result = fc.getResultStr("response", data.toString(), "true", "获取商品成功");
			out.print(result);
			return;
		}else{
			String result = fc.getResultStr("response", "false", "商户没有可用商品");
			out.print(result);
			return;
		}
		
	}
	


	/**
	 * 方法名称: checkMD5 
	 * 方法描述: 
	 * 创建人: renfy
	 * 创建时间: 2011-12-13 下午05:34:17
	 * @param userName
	 * @param passWord
	 * @param sign
	 * @return
	 * @version 1.0
	 * 
	 */ 
	private boolean checkMD5(String userName, String passWord, String sign) {
		String value = fc.getMd5Str(passWord + userName + "AXWzjunt0gG07cpMUnmw71AMNUqdiN6b");
		if(value != null && !"".equals(value) && value.equals(sign)){
			return true;
		}
		return false;
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
