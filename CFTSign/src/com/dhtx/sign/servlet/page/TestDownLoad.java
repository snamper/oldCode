package com.dhtx.sign.servlet.page;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhtx.sign.dao.OrderDAO;
import com.dhtx.sign.pojo.Order;
import com.dhtx.sign.util.global.fc;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 类名称：TestDownLoad   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-12 上午11:50:40   
 * @version 1.0
 *  
 */
public class TestDownLoad extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestDownLoad() {
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

		synchronized (request) {
			
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			OutputStream fos = null;
			InputStream fis = null;
			String fileNameEnd = null;

			// 如果是从服务器上取就用这个获得系统的绝对路径方法。 String filepath =
			// servlet.getServletContext().getRealPath("/" + path);
			String filepath = null;
			
			
			File uploadFile = new File(filepath);
			fis = new FileInputStream(uploadFile);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			// 这个就是弹出下载对话框的关键代码
			
			
			InputStream imagein = new FileInputStream("d:/a/1.jpg");
			InputStream imagein2 = new FileInputStream("d:/a/2.png");
			BufferedImage image = ImageIO.read(imagein);
			BufferedImage image2 = ImageIO.read(imagein2);
			Graphics g = image.getGraphics();
			g.drawImage(image2, (image.getWidth() - image2.getWidth()) / 2, (image.getHeight() - image2.getHeight()) / 2, null);
			OutputStream out = new FileOutputStream("d:/a/11aaa.jpg");
			JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(out);
			enc.encode(image);
			imagein.close();
			imagein2.close();
			out.close();

			//如果是重命名后上传的，修改文件名称
			String trueFileName = "_" + fileNameEnd;
			
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
