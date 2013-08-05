package pay_19pay;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.funcejb;

/**
 * Servlet implementation class for Servlet: WebPayNotice
 *
 * @web.servlet
 *   name="WebPayNotice"
 *   display-name="WebPayNotice" 
 *
 * @web.servlet-mapping
 *   url-pattern="/WebPayNotice"
 *  
 */
 public class WebPayNotice extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = null;		//公用一个连接

   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public WebPayNotice() {
		super();
		if (dc == null) dc = new DataConnect("fillcard", false);
	}   	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */ 
	public void destroy() {
		if (dc != null){
			System.out.print("[PayRequest]关闭数据库连接");
			dc.CloseConnect();
			dc = null;
		}

		super.destroy();
	} 
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(webPayResult(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(webPayResult(request, response));
	}   
	
	/*
	 * 接收网银订单通知
	 */
	public String webPayResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//接收参数
			String version_id = funcejb.getres(request, "version_id");
			String merchant_id = funcejb.getres(request, "merchant_id");
			String verifystring = funcejb.getres(request, "verifystring");
			String order_date = funcejb.getres(request, "order_date");
			String order_id = funcejb.getres(request, "order_id");
			String amount = funcejb.getres(request, "amount");
			String currency = funcejb.getres(request, "currency");
			String pay_sq = funcejb.getres(request, "pay_sq");
			String pay_date = funcejb.getres(request, "pay_date");
			String pm_id = funcejb.getres(request, "pm_id");
			String pc_id = funcejb.getres(request, "pc_id");
			String pay_cardno = funcejb.getres(request, "pay_cardno");
			String pay_cardpwd = funcejb.getres(request, "pay_cardpwd");
			String result = funcejb.getres(request, "result");
			//
			System.out.print("[WEB通知]" + version_id + "," + merchant_id + "," + verifystring + "," + order_date + "," + order_id
					+ "," + amount + "," + currency + "," + pay_sq + "," + pay_date + "," + pm_id + "," + pc_id 
					+ "," + pay_cardno + "," + pay_cardpwd + "," + result);	
			
			//md5验证
			String s = "version_id=" + version_id + "&merchant_id=" + merchant_id + "&order_id=" + order_id 
					+ "&result=" + result + "&order_date=" + order_date + "&amount=" + amount + "&currency=" + currency 
					+ "&pay_sq=" + pay_sq + "&pay_date=" + pay_date + "&pc_id=" + pc_id + "&merchant_key=" + Config_19pay.merchant_key;
			if (!common.funcejb.getMd5Str(s).toLowerCase().equals(verifystring.toLowerCase())){
				System.out.print("[WEB回应]md5出错," + "N"); 
				return "N";
			}
			
			//转换状态
			String sState = result;
			if (result.equals("Y")) sState = "支付成功";
			if (result.equals("N")) sState = "支付失败";
			
			//写订单状态
			String sql = "update fillcard set fState='" + sState + "', fMoney='" + amount + "', " +
				" fFactMoney=" + amount + " * fBili / 100, fCheckState = '返馈中', fReturnSupState='19Pay'," +
				" fFillTime=GETDATE(), fOverTime=GETDATE() where id='" + order_id+ "' and fState<>'支付成功'";
			int n = dc.execute(sql);
			
			//返回
			String re = "";
			if (n==0 || n==1 ) 
				re = "Y";
			else
				re = "N";
			System.out.print("[WEB回应]" + re); 
			return re;
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR000,系统忙,请稍候再试!";
		}
	}
	
}