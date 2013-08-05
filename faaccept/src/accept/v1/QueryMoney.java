package accept.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/*
 * 接收客户下单请求 
 */

/**
 */
 public class QueryMoney extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   private static DataConnect dc = new DataConnect("orderfill", false);
	   static int m_count = 0;
	   static String IsCache_CoClient = "";
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public QueryMoney() {
		super();
		IsCache_CoClient = "";
	} 
   	
	public void destroy() { 
		if (dc != null) {
			////dc.CloseConnect();
			dc = null;
		}
	}
	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(queryMoney(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(queryMoney(request, response));
	}   	
	


	/*
	 * 取订单
	 */
	public String queryMoney(HttpServletRequest request, HttpServletResponse response){
		try{
			int n1 = (int)System.currentTimeMillis();
			//1.接收参数,必须
			String sign = fc.getpv(request, "sign").toUpperCase();
			String fClientID = fc.getpv(request, "cid");		//客户ID
			//
			String sClientIp = request.getRemoteAddr(); 
			smg("[" + fClientID + "]查询金额:" + fClientID + sign);

			//2.参数合法性检查
			if (sign.equals("")) return fc.getResultStr("query", "false", "[md5签名]参数有误");		
			if (fClientID.equals("")) return fc.getResultStr("query", "false", "[客户ID]参数有误");		
			
			//3.检查md5
			if (!sign.equals(fc.getMd5Str(fClientID + "2mZbHsip9GIe7jrVX3O16JTAwgzFd4CY").toUpperCase())){
				System.out.print("通知:" + fClientID + ">>MD5验证失败" );
				return fc.getResultStr("query", "false", "MD5验证失败");		//md5有误
			}
			
			//4.取出客户密钥ok
			String fMoney = "查询失败!";
			String sql = "select fMoney from CoClientMoney where fId = '" + fClientID + "'";
			ResultSet rs = dc.query(sql);
			if ((rs != null) && (rs.next())){
				fMoney = fc.getrv(rs, "fMoney", "");			
			} 
			dc.CloseResultSet(rs);
			//
			return fc.getResultStr("query", "true", "账户余额:" + fMoney);  
		}catch(Exception e){
			return fc.getResultStr("query", "false", "查询失败,接口运行异常");
		}
	}
	//信息输出
	public void smg(String s){
		System.out.print(s);
		return;
	}
}