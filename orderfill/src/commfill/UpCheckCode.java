package commfill;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;

import com.jspsmart.upload.*;
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import common.*;

/*
 * 这理主要用于上传验证码图片,因为充值程序连的服务器是相对固定的
 */
/**
 * Servlet implementation class for Servlet: UpCheckCode
 * 
 * @web.servlet name="UpCheckCode" display-name="UpCheckCode"
 * 
 * @web.servlet-mapping url-pattern="/UpCheckCode"
 * 
 */
public class UpCheckCode extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UpCheckCode() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		BufferedInputStream inputImage = null;
		try {
			// 是取图片,还是取值
			String sType = fc.getpv(request, "type");
			String fID = fc.getpv(request, "fid");
			if (sType.equals("code")) {
				System.out.print("[读取验证码值]" + sType + "," + fID);
				String CONTENT_TYPE = "text/html; charset=GBK";
				response.setContentType(CONTENT_TYPE);
				PrintWriter out = response.getWriter();
				// 获取值 
				Common cm = new Common();
				String sql = "select top 1 * from CoCheckCode where fid='" + fID + "'";
				ResultSet rs = cm.dc.query(sql);
				String sCode = "";
				if (rs != null) {
					if (rs.next()) {
						sCode = fc.getrv(rs, "fCode", "");
//						if (!sCode.equals("")) {
//							sql = "delete from CoCheckCode where fid='" + fID + "'";
//							cm.dc.execute(sql);
//						}
					} else
						sCode = "";
				} else
					sCode = "";
				out.print(sCode);
				System.out.print("[读取验证码值]返回:" + sCode);
				cm.dc.CloseResultSet(rs);
			} else {
				// 接收参数,要显示的图片的ID
				System.out.print("[读取验证码图片]" + sType + "," + fID);
				if (!fID.equals("")) fID = " where fid = '" + fID + "'";

				// 获取结果集
				Common cm1 = new Common();
				String sql = "select top 1 * from CoCheckCode " + fID + " order by fCreateTime desc ";
				ResultSet rs = cm1.dc.query(sql);
				
				// 输出文件名
				byte[] buf = null;
				String filename = "", sImageType = "", htmText = "";
				if (rs.next()) {
					System.out.print("[读取验证码图片]查询到图片");
					filename = rs.getString("fid");
					buf = rs.getBytes("fImage");
					htmText = rs.getString("fHtmText");
					sImageType = fc.getrv(rs, "fImageType", "");
				} else
					buf = new byte[0];
				cm1.dc.CloseResultSet(rs);
				System.out.print("图片类型:" + sImageType);
				if (sImageType.equals("bmp")||(sImageType.equals(""))){
					// 告诉浏览器输出的是图片
					response.setContentType("image/" + sImageType);
					// 图片输出的输出流
					OutputStream out = response.getOutputStream();
					// 将缓冲区的输入输出到页面
					out.write(buf);
					// 输入完毕，清楚缓冲
					out.flush();
				}
				if (sImageType.equals("htm")){
					String CONTENT_TYPE = "text/html; charset=GBK";
					response.setContentType(CONTENT_TYPE);
					PrintWriter out = response.getWriter();
					out.print(htmText);
				}
				System.out.print("[读取验证码图片]返回图片");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String CONTENT_TYPE = "text/html; charset=GBK";
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();
			// 接收上传参数
			String fID = fc.GetOrderID("CC");		//订单号
			String fType = fc.getpv(request, "type");	//验证码类型	
			String fHtmText = fc.getpv(request, "htmtext");	//
			String fIp = fc.getpv(request, "appid");	//挂机ID			
			if (fIp.equals("")) fIp = request.getRemoteAddr();	//
			System.out.print("[" + fIp + "][上传验证码]" + request.getRemoteAddr() + "," + fID + "," + fIp + "," + fType);
			if (fType.equals("")) fType = "bmp";

			// 连接数据库
			Common cm = new Common();

			if (fType.equals("htm")){
				if (cm.dc != null) {
					String sql = "insert into CoCheckCode (fid, fOperateId, fCreateTime, fHtmText, fImageType) "
						+ " values('"+ fID	+ "', " +
								" '" + fIp + "', " +
								" GETDATE()," +
								" '" + fHtmText + "'"
								+ ",'" + fType + "'"
						+ ")";
					int n = cm.dc.execute(sql);
					System.out.print("[" + fIp + "][上传验证码]插入记录:" + n);
					out.print(fID);

				}
			}
			
			if (fType.equals("bmp")){
	
				// 计算文件上传个数
				int count = 0;
	
				// SmartUpload的初始化，使用这个jspsmart一定要在一开始就这样声明
				SmartUpload mySmartUpload = new com.jspsmart.upload.SmartUpload();
				JspFactory _jspxFactory = JspFactory.getDefaultFactory();
				javax.servlet.jsp.PageContext pageContext = _jspxFactory
						.getPageContext(this, request, response, null, true, 8192,
								true);
				mySmartUpload.initialize(pageContext);
	
	
					// 依据form的内容上传
					try{
						mySmartUpload.upload();
					}catch(Exception e){
						System.out.print("[" + fIp + "][上传验证码]上传图片失败");
						String sql = "insert into CoCheckCode (fid, fOperateId, fCreateTime, fHtmText, fImageType) "
							+ " values('"+ fID	+ "', " +
							" '" + fIp + "', " +
							" GETDATE()," +
							" '" + fHtmText + "'"
							+ ",'" + fType + "'"
							+ ")";
						int n = cm.dc.execute(sql);
						System.out.print("[" + fIp + "][上传验证码]插入记录:" + n);
						out.print(fID);
						return;
					}
					// 将上传的文件一个一个取出来处理
					for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
						// 取出一个文件
						System.out.print("[" + fIp + "][上传验证码]读取图片");
						com.jspsmart.upload.File myFile = mySmartUpload.getFiles()
								.getFile(i);
	
						// 如果文件存在，则做存档操作
						if (!myFile.isMissing()) {
							if (cm.dc != null) {
								String sql = "insert into CoCheckCode (fid, fOperateId, fCreateTime) "
										+ " values('"
										+ fID
										+ "', '"
										+ fIp
										+ "', "
										+ "GETDATE())";
								int n = cm.dc.execute(sql);
								System.out.print("[" + fIp + "][上传验证码]插入记录:" + n);
								Statement stmt = cm.dc.getConnect().createStatement(
										ResultSet.TYPE_SCROLL_SENSITIVE,
										ResultSet.CONCUR_UPDATABLE);
								sql = "select * from CoCheckCode where fid = '" + fID + "'";
								ResultSet rs = stmt.executeQuery(sql);
								if (rs != null) {
									if (rs.next()) {
										// 将文件存放到数据库字段中
										myFile.fileToField(rs, "fImage");
										rs.updateRow();
										System.out.print("[" + fIp + "][上传验证码]保存图片到字段");
										out.print(fID);
									}
								}
								stmt.close();
								if (rs != null)
									rs.close();
								count++;
							}
						}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}