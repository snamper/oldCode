package com.dhtx.sign.servlet.page;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.servlet.app.WaterMark;
import com.dhtx.sign.util.global.fc;

/**
 * 类名称：DownloadFileServlet   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-10 下午06:23:28   
 * @version 1.0
 *  
 */
public class DownloadFileServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DownloadFileServlet() {
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
		response.setContentType("text/text;charset=gb2312");
		//禁止缓存
		response.setHeader("Pragma","No-Cache");
		response.setHeader("Cache-Control","No-Cache");
		response.setDateHeader("Expires",0);

//			String currentUserid = request.getParameter("currentUserid");
			String picType = fc.getpv(request, "picType");
			String fileName = fc.getpv(request, "fileName");
			String bgPicName = fc.getpv(request, "bgPicName");
			OrderDAO odao = new OrderDAO();
			Order order = odao.getOrderById(fileName);
			String picURL = order.getFpictureURL();
			String signName = picURL + "/" + fileName + ".png";//只签名图片
			
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
			String fileNameEnd = null;

			// 如果是从服务器上取就用这个获得系统的绝对路径方法。 String filepath =
			// servlet.getServletContext().getRealPath("/" + path);
			String filepath = null;
			
			if("sign".equals(picType)){
				fileNameEnd = fileName + ".png";
				filepath = signName;
			}else{
				//合并图片
				String bgPicNameUrl = "c:/cftsignBGPicture/" + bgPicName + ".jpg";
				String outPicName = "c:/cftsigntemp/" + fileName + ".jpg";
				WaterMark.createSignPic(signName, bgPicNameUrl, outPicName);
				//
				fileNameEnd = fileName + ".jpg";
				filepath = outPicName;
			}
			
			File uploadFile = new File(filepath);
			fis = new FileInputStream(uploadFile);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			// 这个就是弹出下载对话框的关键代码

			//如果是重命名后上传的，修改文件名称
			String trueFileName = picType + "_" + fileNameEnd;
			
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(trueFileName, "utf-8"));

			int bytesRead = 0;
			// 用输入流进行先读，然后用输出流去写，用的是缓冲输入输出流
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
			return;
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
