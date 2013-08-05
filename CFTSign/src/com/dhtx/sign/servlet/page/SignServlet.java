package com.dhtx.sign.servlet.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.dao.ProductDAO;
import com.dhtx.sign.pojo.BGPicture;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.pojo.Product;
import com.dhtx.sign.util.Config;
import com.dhtx.sign.util.global.fc;
import com.tenpay.api.MicroBlogSendRequest;

/**
 * 类名称：SignServlet   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-10 上午11:31:03   
 * @version 1.0
 *  
 */
public class SignServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SignServlet() {
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

		String orderID = fc.getpv(request, "orderID");
		String bgPriNumStr = fc.getpv(request, "bgPriNum");
		int bgPriNum = 1;//背景图片序号
		try {bgPriNum = Integer.parseInt(bgPriNumStr);} catch (Exception e) {bgPriNum = 1;}
		
		OrderDAO odao = new OrderDAO();
		Order order = odao.getOrderById(orderID);
		BGPicture pgPic = null;
		List<BGPicture> bgPicList = null;
		ProductDAO pdao = new ProductDAO();
		Product product = pdao.getProductByName(order.getFproductName());
		if(product != null){
			bgPicList = pdao.getAllProBGPicture(product.getFid());
		}
		if(bgPicList != null && !bgPicList.isEmpty()){
			
			if(bgPriNum > bgPicList.size()){
				bgPriNum = 1;
			}
			pgPic = bgPicList.get(bgPriNum - 1);
		}
		
//		createSignPicture(order,pgPic);
		String pictureURL = order.getFpictureURL();
		String signDate = pictureURL.substring(11);
		
		request.setAttribute("signDate", signDate);
		request.setAttribute("pgPic", pgPic);
		request.setAttribute("order", order);
		request.setAttribute("bgPriNum", bgPriNum);
		
		//weibo
		//构造函数
		MicroBlogSendRequest microBlogRequest = new MicroBlogSendRequest(Config.TEST_SECRET_KEY ,request);
		//设置是否为沙箱环境
		microBlogRequest.setInSandBox(Config.INSANDBOX);
		//设置转发的微博内容
		microBlogRequest.setParameter("content", "微博测试");
		//设置编码
		microBlogRequest.setParameter("input_charset", "GB2312");
		//设置APPID
		microBlogRequest.setAppid(Config.TEST_APPID);
		//将micrkBlogRequest放入request中。
		request.setAttribute("microBlogRequest", microBlogRequest);
		
		request.getRequestDispatcher("sign.jsp").forward(request, response);
	}


	/**
	 * 方法名称: copyBGPic 
	 * 方法描述: 复制背景图片到临时目录
	 * 创建人: renfy
	 * 创建时间: 2012-1-10 下午03:56:31
	 * @param pgPic
	 * @version 1.0
	 * 
	 */ 
	private void copyBGPic(Order order,BGPicture pgPic) {
		File file = new File("c:/cftsignBGPicture/"+pgPic.getFname()+".jpg");
		File targetFile = new File("c:/cftsigntemp/"+order.getFpictureName()+".jpg");
		copyFile(targetFile, file);
	}
	
	public void copyFile(File targetFile, File file) {
		
//		System.out.println("复制文件" + file.getAbsolutePath() + "到" + targetFile.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				fos.write(buffer);
			}
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
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
