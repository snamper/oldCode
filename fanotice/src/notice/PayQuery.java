package notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/**
 * Servlet implementation class for Servlet: PayQuery
 *
 * @web.servlet
 *   name="PayQuery"
 *   display-name="PayQuery" 
 *
 * @web.servlet-mapping
 *   url-pattern="/PayQuery"
 *  
 */
 public class PayQuery extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = null;	
	   static int ConnectCount = 0;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public PayQuery() {
		super();
		ConnectCount = ConnectCount + 1;
		System.out.print("[PayQuery]新的实例,当前共有个" + ConnectCount + "实例");
		if (dc == null){
			dc = new DataConnect("orderfill", false);		//公用一个连接
			System.out.print("[PayQuery]第一个实例,初始化数据库连接");
		}
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		ConnectCount = ConnectCount - 1;
		if (ConnectCount <= 0){
			dc.CloseConnect();
			dc = null;
			System.out.print("[PayQuery]实例数为0,已关闭数据库连接");
		}
		super.destroy();
	}   	
	  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payQuery(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payQuery(request, response));
	}   	  	  
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}   
	
	/*
	 * 重发处理: 1.锁定并返回通知列表, 2.更新订单状态
	 */
	public String payQuery(HttpServletRequest request, HttpServletResponse response){
		try{
			//1.操作参数
			String type = fc.getpv(request, "type");	//0订单, 1回写
			String appid = fc.getpv(request, "appid");	//挂机程序ID
			String source = fc.getpv(request, "source");	//0接口,1卡易售,2淘宝
			String sign = fc.getpv(request, "sign");	
			//
			if (type.equals("")) return "ERROR_type不能为空";
			if (!sign.equals("d89043hdn930984d3h984sj734265thsn"))
				return "ERROR_md5校验失败";
			
			//2.锁定并返回通知URL
			if (type.equals("0")){
				//if (appid.equals("")) return "ERROR_appid不能为空";
				//String notice = fc.getpv(request, "notice");	
				String count = fc.getpv(request, "count");	
				String order = fc.getpv(request, "order");	
				String client = fc.getpv(request, "client");
				if (source.equals("")) source = "0";
				if (count.equals("")) count = "10"; 
				if (!order.equals("")) order = " order by fid desc";
				if (!client.equals("")) client = " and fClientID = '" + client + "' ";
				String vsource = "";
				if (!source.equals("")){
					if (source.equals("0"))
						vsource = " and isnull(fSource,'0') = '" + source + "' ";
					else
						vsource = " and fSource = '" + source + "' ";
				}
				String time = "";
				if (!source.equals("0")) 
					time = "";
				else
					time = " and fOverTime <= getdate() ";
						
				//锁定
//				String sql = "update AcRenotice set fLockID = '" + appid + "' where " +
//				" fid in (select top " + count + " fid from AcRenotice where fOverTime <= getdate() and fLockID in ('', '" + appid + "'))";
//				int n = dc.execute(sql);
//				System.out.print("[" + appid + "]锁定:" + n);	//fLockID = '" + appid + "' and 
				//取回
				String sList = "";
				String sql = "select top " + count + " * from AcRenotice where 1=1 " + time + client + vsource + order;
				ResultSet rs = dc.query(sql);
				int n = 0;
				while (rs!=null && rs.next()){
					n = n + 1;
					sList = sList + DataAccess.getRowXml(rs, n);
				}
				dc.CloseResultSet(rs);
				//
				if (source.equals("2")) sList = sList.replace("&", "|");
				//
				return fc.getResultStr("renotice", sList, "<RowCount>" + n + "</RowCount>", "true", "取单成功");
			}
			
			//3.更新订单状态
			if (type.equals("1")){
				//取参数
				String fid = fc.getpv(request, "id");	
				String state = fc.getpv(request, "state");	//0成功,1失败
				String error = fc.getpv(request, "msg");	
				if (fid.equals("")) return "ERROR_id不能为空";
				if (state.equals("")) return "ERROR_state不能为空";
				//回写
				if (state.equals("0")){	
					//如果成功,更新主订单状态
					String sql = "update AcOrder set fNoticeState = '2' where fid = '" + fid + "'";
					int n = dc.execute(sql);
					if (n == 1){
						//从重发列表中清除
						sql = "delete from AcRenotice where fid = '" + fid + "'";
						dc.execute(sql);
						System.out.print(n);
					}
				}else{
					//如果失败,延时等待下次进行重发
					String sql = "update AcRenotice set flockid = '', fovertime = getdate() + 0.0000116 * fReInterval, fResult = '" + error + "' where fid = '" + fid + "'";
					dc.execute(sql);
				}
			}

			//返回
			return "OK";
		}catch(Exception e){
			return "ERROR,系统出错," + e.toString();
		}
	}
	
}