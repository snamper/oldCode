package pay_19pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/**
 * Servlet implementation class for Servlet: WebPayResult
 *
 * @web.servlet
 *   name="WebPayResult"
 *   display-name="WebPayResult" 
 *
 * @web.servlet-mapping
 *   url-pattern="/WebPayResult"
 *  
 */
 public class WebPayResult extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = null;		//公用一个连接

   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public WebPayResult() {
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
			System.out.print("[WEB结果]" + version_id + "," + merchant_id + "," + verifystring + "," + order_date + "," + order_id
					+ "," + amount + "," + currency + "," + pay_sq + "," + pay_date + "," + pm_id + "," + pc_id 
					+ "," + pay_cardno + "," + pay_cardpwd + "," + result);	

			//md5验证
			String s = "version_id=" + version_id + "&merchant_id=" + merchant_id + "&order_date=" + order_date 
					+ "&order_id=" + order_id + "&amount=" + amount + "&currency=" + currency + "&pay_sq=" + pay_sq 
					+ "&pay_date=" + pay_date + "&pc_id=" + pc_id + "&result=" + result + "&merchant_key=" + Config_19pay.merchant_key;
			if (!common.funcejb.getMd5Str(s).toLowerCase().equals(verifystring.toLowerCase())){
				System.out.print("[WEB返回]md5出错," + "N"); 
				return "N";
			}
			
			//转换状态
			String sState = result;
			if (result.equals("Y")) sState = "支付成功";
			if (result.equals("N")) sState = "支付失败";
			
			//写订单状态
			String sql = "update fillcard set fState='" + sState + "', fMoney='" + amount + "', " +
				" fFactMoney=" + amount + " * fBili / 100, fCheckState = '返馈中', fReturnSupState='19Pay', " +
				" fFillTime=GETDATE(), fOverTime=GETDATE() where id='" + order_id+ "' and fState<>'支付成功'";
			int n = dc.execute(sql);
			
			//查询必要数据
			sql = "select c.id as cid, c.fkey as ckey, fc.fuser_urlb, fc.forderid, convert(varchar(32), fc.fcardid) as sCardNo," +
					" fc.fcardtypeid, fc.fusera, fc.fuserb, fc.fuserc, fc.ffilltime, fc.ffillmsg " +
					"from fillcard fc ,client c where fc.id = '" + order_id + "' and c.fName = fc.fClient ";
			ResultSet rs = dc.query(sql);
			String NoticePage = "", MerOrderNo = "", MerchantID = "", CardType = "", KEY = "", CardNo = "";
			String CustomizeA = "", CustomizeB = "", CustomizeC = "", PayTime = "", ErrorMsg = "";
			if (rs != null){
				if (rs.next()){
					KEY = funcejb.gets(rs, "ckey", "");
					MerOrderNo = funcejb.gets(rs, "forderid", "");
					CardNo = funcejb.gets(rs, "sCardNo", "");
					CardType = funcejb.gets(rs, "fCardTypeID", "");
					CustomizeA = funcejb.gets(rs, "fUserA", "");
					CustomizeB = funcejb.gets(rs, "fUserB", "");
					CustomizeC = funcejb.gets(rs, "fUserC", "");
					PayTime = funcejb.gets(rs, "fFillTime", "");
					ErrorMsg = funcejb.gets(rs, "fFillMsg", "");
				}
				dc.CloseResultSet(rs);
			}
			//组成html通知页面
			sState = "treat";
			if (result.equals("Y")) sState = "true";
			if (result.equals("N")) sState = "false";
			//
			String sSign = funcejb.getMd5Str(order_id + MerchantID + MerOrderNo + CardNo + CardType + amount + sState
					+ CustomizeA + CustomizeB + CustomizeC + PayTime + KEY).toUpperCase();
			//
			//
			String re = "";
			re = "<form action=\"" + NoticePage + "\" method=\"post\" name=\"postform\">";
			re += "<input type=\"hidden\" name=\"PayOrderNo\" value=\"" + order_id + "\" />";
			re += "<input type=\"hidden\" name=\"MerchantID\" value=\"" + MerchantID + "\" />";
			re += "<input type=\"hidden\" name=\"MerOrderNo\" value=\"" + MerOrderNo + "\" />";
			re += "<input type=\"hidden\" name=\"CardNo\" value=\"" + order_id + "\" />";
			re += "<input type=\"hidden\" name=\"CardType\" value=\"" + CardType + "\" />";
			re += "<input type=\"hidden\" name=\"FactMoney\" value=\"" + amount + "\" />";
			re += "<input type=\"hidden\" name=\"PayResult\" value=\"" + sState + "\" />";
			re += "<input type=\"hidden\" name=\"CustomizeA\" value=\"" + CustomizeA + "\" />";
			re += "<input type=\"hidden\" name=\"CustomizeB\" value=\"" + CustomizeB + "\" />";
			re += "<input type=\"hidden\" name=\"CustomizeC\" value=\"" + CustomizeC + "\" />";
			re += "<input type=\"hidden\" name=\"PayTime\" value=\"" + URLEncoder.encode(PayTime) + "\" />";
			re += "<input type=\"hidden\" name=\"ErrorMsg\" value=\"" + URLEncoder.encode(ErrorMsg) + "\" />";
			re += "<input type=\"hidden\" name=\"sign\" value=\"" + sSign + "\" />";
			if (!NoticePage.equals("")){
				re += "<input type=\"submit\" value=\"请点击跳转\" />";
				re += "	跳转中......";
			}
			re += "</form>";
			if (!NoticePage.equals("")){ 
				re += "<script type=\"text/javascript\">";
				re += "document.postform.submit();";
				re += "</script>";
			}else{
				re += "<input type=\"submit\" value=\"点击关闭\" onClick=\"window.close()\">";
				re += "操作已完成,未指定跳转页面,请手工关闭!";
			}

			System.out.print("[WEB返回]" + sState); 
			return re;
	
		}catch(Exception e){
			e.printStackTrace();
			return "ERROR000,系统忙,请稍候再试!";
		}
	}
	
}