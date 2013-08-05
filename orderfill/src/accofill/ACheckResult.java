package accofill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/*
 * 接收账号验证结果,更新订单信息
 */

/**
 * Servlet implementation class for Servlet: CheckResult
 *
 * @web.servlet
 *   name="ACheckResult"
 *   display-name="ACheckResult" 
 *
 * @web.servlet-mapping
 *   url-pattern="/ACheckResult" 
 *  
 */
 public class ACheckResult extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static int ConnectCount = 0;
	   static boolean isrerun = false;
	   static int AppCount = 0;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ACheckResult() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[poolfill.AcceptResult]新的实例,当前共有个" + ConnectCount + "实例");
//		if (dc == null){
//			dc = new DataConnect();		//公用一个连接
//			System.out.print("[poolfill.AcceptResult]第一个实例,初始化数据库连接");
//		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
			dc.CloseConnect();
			dc = null;
			System.out.print("[poolfill.AcceptResult]实例数为0,已关闭数据库连接");
		}
		super.destroy();
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(acceptResult(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(acceptResult(request, response));
	}   	  	    
	
	/*
	 * 
	 */
	public String acceptResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//1.接收参数
			String sign = fc.getpv(request, "sign");
			String sFillID = fc.getpv(request, "fillid");		//id
			String sArea = fc.getpv(request, "area");			//地区
			System.out.print("[" + sFillID + "]账号验证结果:" + sFillID + "," + sArea);

			//2.校验合法性
			//if (!sign.equals(fc.getMd5Str("A74B766AEAE474EAAEF8465A6A0A4885"))) return gr("false", "md5验证失败");

			//3.更新订单信息
			String s = "";
			int n = 0;
			if (!sArea.equals("")) s = ",fUserArea = '" + sArea + "'"; 
			if (!s.equals("")){
				String sql = "update AcOrder set " +
				" fGiveTime = GETDATE(), fLockId = '', " + 
				" fState = '1' " + s + 
				" where fGiveOrderID = '" + sFillID + "' ";
				n = dc.execute(sql);
			}
			
			//4.调用分发
			System.out.print("[" + sFillID + "]账号验证更新:" + n);
			
			return gr("true", "接收完成(" + n + ")");
		}catch(Exception e){
			e.printStackTrace();
			return gr("false", "意外错误:" + e.toString());
		}
		
		
	}

	private String gr(String sState, String sMsg) {
		return fc.getResultStr("getaccount", sState, sMsg);
	}

	
}