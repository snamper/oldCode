package accofill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.*;

/*
 * 接收充值结果,更新数据到记录
 */

/**
 * Servlet implementation class for Servlet: AccountResult
 * 
 * @web.servlet
 *   name="AccountResult" 
 *   display-name="AccountResult" 
 *
 * @web.servlet-mapping
 *   url-pattern="/AccountResult"
 *  
 */
 public class AccountResult extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static int ConnectCount = 0;
	   static boolean isrerun = false;
	   static int AppCount = 0;
	   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AccountResult() {
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
			String sFactMoney = fc.getpv(request, "fillmoney"); //实际金额, 数值或fullmoney
			//
			String sFillID = fc.getpv(request, "fillid");		//充值订单号,
			String fFillMsg = fc.getpv(request, "msg");   		//说明
			String fFillState = fc.getpv(request, "state");   	//状态,如果为成功(0)则不查状态表, 如果为失败(1),则查状态表
			System.out.print("[" + sFillID + "]账号结果:" + sFillID + "," + sFactMoney + "," + sFillID + "," + fFillMsg);
			//


			//2.校验合法性
			//if (!sign.equals(fc.getMd5Str("A74B766AEAE474EAAEF8465A6A0A4885"))) return gr("false", "md5验证失败");
			//if (sID.equals("")) return gr("false", "aid不能为空");
			if (sFactMoney.equals("")) return gr("false", "fillmoney不能为空");
			if (sFillID.equals("")) return gr("false", "fillid不能为空");
			fFillMsg = fFillMsg.replace("'", "''");
			if (fFillMsg.length() > 32) fFillMsg = fFillMsg.substring(0, 32);
			//
			if (sFactMoney.equals("fullmoney")){
				sFactMoney = "fLackMoney";
			}else{
				//检查充值金额是否合法
				if (!fc.isPlusFloat(sFactMoney)) return gr("false", "fillmoney充值金额不是数值型");
			}

			//3.取出订单信息
			String fFactMoney = "0", fID = "";
			String fProductID = "", fGiveID = "", fInsideID = "", fGiveOrderID = "", fState="", sErrorCount = "", fLackMoney = "";
			String sql = "select top 1 * from AcOrder where fGiveOrderID = '" + sFillID + "'";
			ResultSet rs = dc.query(sql);
			if (rs!=null && rs.next()){
				fID = fc.getrv(rs, "fID", "");
				fInsideID = fc.getrv(rs, "fInsideID", "");
				fProductID = fc.getrv(rs, "fProductID", "");
				fGiveID = fc.getrv(rs, "fGiveID", "");
				fState = fc.getrv(rs, "fState", "");
				fLackMoney = fc.getrv(rs, "fLackMoney", "");
				fFactMoney = fc.getrv(rs, "fFactMoney", "");
				fGiveOrderID = fc.getrv(rs, "fGiveOrderID", "");
				sErrorCount = fc.getrv(rs, "fErrorCount", "");
			}else{
				dc.CloseResultSet(rs);
				return gr("false", "取订单信息时失败");
			}
			dc.CloseResultSet(rs);
			// 
			if (fState.equals("8")){
				return fc.getResultStr("getaccount", "true", "处理完成,手工确认订单");
			} 
			if (!fState.equals("3")){
				return fc.getResultStr("getaccount", "true", "订单验证失败,拒决接收");
			} 
			
			//4.得到订值结果,sFactMoney值如果为fullmoney, 表示对方接口不返回充值金额,全额成功
			String fRuleState = "", fOrderState = "";
			if ((!fFillState.equals("0"))&&(!fFillMsg.equals(""))){
				fGiveID = fGiveID.substring(0, fGiveID.length() - 3);
				sql = "select * from CoStates where  fPlatformID = '" + fGiveID + "' and fProductID = '" + fInsideID + "' and fState = '0' " +
				"and fMarkA = '" + fFillMsg + "' order by fLevel desc";
				rs = dc.query(sql);
				if (rs!=null && rs.next()){
					fRuleState = fc.getrv(rs, "fOrderState", "4");
					System.out.print("旧状态:" + sFillID + "," + fGiveID + "," + fInsideID + "," + fFillMsg + "," + fRuleState);
				}else{
					//如果为空,写新记录到表,置订单为手工状态
					System.out.print("新状态:" + sFillID + "," + fGiveID + "," + fInsideID + "," + fFillMsg);
					sql = "";
					sql = fc.SetSqlInsertStr(sql, "fid", fc.getGUID6(""));
					sql = fc.SetSqlInsertStr(sql, "fPlatformID", fGiveID);
					sql = fc.SetSqlInsertStr(sql, "fProductID", fInsideID);
					sql = fc.SetSqlInsertStr(sql, "fMarkA", fFillMsg);
					sql = fc.SetSqlInsertStr(sql, "fLevel", "0");
					sql = fc.SetSqlInsertStr(sql, "fOrderState", "4");
					sql = fc.SetSqlInsertStr(sql, "fState", "0");
					sql = fc.SetSqlInsertStr(sql, "fCheckMoney", "1");
					sql = "insert into CoStates " + sql;
					dc.execute(sql);
					//
					fRuleState = "4";		//手工
				}
				dc.CloseResultSet(rs);
				//转换状态:0=支付成功,非0支付失败(1=卡密失败,2=恢复充值,3=下一通道,4=手工确认,5=账号失败,6=手工确认)
				if (fRuleState.equals("0")) fOrderState = "2";
				if (fRuleState.equals("1")) fOrderState = "2";
				if (fRuleState.equals("2")) fOrderState = "2";
				if (fRuleState.equals("3")) fOrderState = "1";
				if (fRuleState.equals("4")) fOrderState = "8";
				if (fRuleState.equals("5")) fOrderState = "5";
				if (fRuleState.equals("6")) fOrderState = "9";
				//
				if (!fRuleState.equals("0")) sFactMoney = "0";


			}else{
				fOrderState = "2";	//直接成功
			}
			
			//5.是否重试处理,只要fErrorCount大于0,就要重试,在分发时控制其次数
			if (Float.valueOf(sErrorCount).floatValue() > 0){
				fOrderState = "2";					//恢复成等待充值
			}								
			
			//6.当等待充值或手工确认时, 不更新超时时间
			String fOverTime = "getdate()";
			if (fOrderState.equals("2")||(fOrderState.equals("8"))){
				fOverTime = "fOverTime";
			}
			
			//7.1处理充值金额及状态
			//如果是全额, nLackMoney=0,nFillMoney=x, fOrderState=空/不空
			//如果是有值, nLackMoney=x,nFillMoney=x, fOrderState=x/4
			float nFillMoney = 0;
			float nLackMoney = Float.valueOf(fLackMoney).floatValue();
			if (!sFactMoney.equals("fLackMoney")){
				nFillMoney = Float.valueOf(sFactMoney).floatValue();
			}else
				nFillMoney = Float.valueOf(fLackMoney).floatValue();	//全额
			
			//7.2处理充值信息为空的情况
			if (nLackMoney <= 0) fOrderState = "4";  
			if (fFillMsg.equals("")&&(fOrderState.equals(""))){
				if (nLackMoney - nFillMoney  <= 0){
					fOrderState = "4";	//充值成功
				}else{
					fOrderState = "2";	//等待充值
				}
			}else{
				if (fOrderState.equals("2") && (nLackMoney - nFillMoney  <= 0)) 
					fOrderState = "4";  
				else
					fOrderState = fOrderState;
			}
			//System.out.print("判断值:" + sFillID + "," + fGiveID + "," + fFactMoney + "," + fOrderState);
			try{
				if ((Float.valueOf(fFactMoney).floatValue() > 0)&&(fOrderState.equals("5"))) fOrderState = "4";  
			}catch(Exception ee){
				System.out.print(ee.toString());
			}
			//System.out.print("判断值:" + sFillID + "," + fGiveID + "," + fFactMoney + "," + fOrderState);
			
			//8.更新通知状态和出错次数
			String fNoticeState = "0";
			String fErrorCount = "fErrorCount";
			if (fOrderState.equals("4") || fOrderState.equals("5")){
				fOrderState = "1";						//成功或失败状态时, 转为等待分发状态, 否则订单会给客户通知
				fErrorCount = "0";						//重置通知出错次数(以后可能用不到,全都到列表中重发)
			}else{
				if (fOrderState.equals("2"))
					fErrorCount = "fErrorCount - 1";	//重试次数减1
				else
					fErrorCount = "fErrorCount";		//其它保存不变
			}
			
			//9.更新数据
			sql = "update AcOrder set " +
					" fLockId = '', " +
					" fErrorCount = " + fErrorCount + ", " +
					" fGiveOrderID = '', " +
					" fFactMoney = fFactMoney + " + sFactMoney + "," +
					" fLackMoney = fLackMoney - " + sFactMoney + ", " +
					" fFillTime = GETDATE(),  " +
					" fFillMsg = '" + fFillMsg + "', " +
					" fState = '" + fOrderState + "', " +
					" fNoticeState = '" + fNoticeState + "' " + 
					" where fGiveOrderID = '" + sFillID + "' ";
			int n = dc.execute(sql);

			//分发
			if (fOrderState.equals("1")){
				Thread t1 = new Thread(new AccountAllotThread(fID, "")); 
				t1.start(); 
			}
			//
			if ((n == 1)||(n == 0)){ 
				return gr("true", "接收完成(" + n + ")");
			}else{
				return gr("false", "更新失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			return gr("false", "意外错误:" + e.toString());
		}
		
		
	}

	private String gr(String sState, String sMsg) {
		return fc.getResultStr("getaccount", sState, sMsg);
	}

	
}