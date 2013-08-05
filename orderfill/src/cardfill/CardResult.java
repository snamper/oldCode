package cardfill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: CardResult
 * 
 * @web.servlet name="CardResult" display-name="CardResult"
 * 
 * @web.servlet-mapping url-pattern="/CardResult"
 * 
 */
public class CardResult extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK"; 
	static DataConnect dc = new DataConnect("orderfill", false);

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CardResult() {
		super();
	}

	public void destroy() {
		if (dc != null) {
			dc.CloseConnect();
			dc = null;
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(acceptResult(request, response));
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(acceptResult(request, response));
	}
	
	
	/*
	 * 接收卡充值结果, 一定要先写充值记录, 再写充值结果. 因为保存充值记录时会检查是否手工确认.
	 */
	public String acceptResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//1.接收参数
			String sign = fc.getpv(request, "sign");					//fID + fFactMoney + fState + "mrtedngdslntvgttxgrbdnfegntdfr"
			//String fID = fc.getpv(request, "cid");						//*卡密订单号
			String fFactMoney = fc.getpv(request, "fillmoney");			//*充值金额, 面值不符的,由平台指定是否手工处理或计算出
			String fFillMsg = fc.getpv(request, "msg");					//*充值信息
			String fFillID = fc.getpv(request, "fillid");				//*充值订单号
			//内部(还不知道有没有用)
			String IsEdit = fc.getpv(request, "isedit");				//是否可以修改
			
			//
			System.out.print("[" + fFillID + "]写卡结果:" + fFillID + "," + fFactMoney + "," + fFillMsg);

			//2.参数检查
//			if (sign.equals(fc.getMd5Str(fID + fFactMoney + "tdn4y7w46tw3789eht5d3w56gte4348rg6d"))) 
//				return fc.getResultStr("acceptresult", "false", "md5校验失败");
			if (fFillID.equals("")) return fc.getResultStr("acceptresult", "false", "fillid不能为空");
			if (fFillMsg.length() > 32) fFillMsg = fFillMsg.substring(0, 32);
			
			//充值金额
			float nFactMoney = 0;
			try{
				nFactMoney = Float.valueOf(fFactMoney).floatValue();
			}catch(Exception e){
				return fc.getResultStr("cardresult", "false", "fillmoney充值金额不是数值型");
			}
			fFactMoney = "" + nFactMoney;
			
			//3.取出订单信息
			String sCardTable = "CaOrder";												//正常卡订单
			if (fFillID.substring(0, 1).equals("K")) sCardTable = "CaOrderKck";			//如果是库存卡订单
			//
			String fProductID = "", fID = "", fGiveID = "", fClientID = "", fState="", sErrorCount = "";
			String sql = "select * from " + sCardTable + " where fGiveOrderID = '" + fFillID + "'";
			ResultSet rs = dc.query(sql);
			if (rs!=null && rs.next()){
				fClientID = fc.getrv(rs, "fClientID", "");
				fProductID = fc.getrv(rs, "fProductID", "");
				fState = fc.getrv(rs, "fState", "");
				fGiveID = fc.getrv(rs, "fGiveID", "");
				fID = fc.getrv(rs, "fID", "");
				sErrorCount = fc.getrv(rs, "fErrorCount", "");
			}else{
				dc.CloseResultSet(rs);
				return fc.getResultStr("cardresult", "false", "取订单信息时失败");
			}
			dc.CloseResultSet(rs);
			// 
			if (fState.equals("8")){
				return fc.getResultStr("cardresult", "true", "处理完成,手工确认订单");
			} 
			if (!fState.equals("3")){
				return fc.getResultStr("cardresult", "true", "订单验证失败,拒决接收");
			} 
			
			
			//4.查询状态规则,获得订单状态:
			String fRuleState = "", fOrderState = "";
			//如果充值结果为空, 金额大于0,认为是成功, 等于0失败
			if (fFillMsg.equals("")){
				if (nFactMoney > 0){
					fOrderState = "4";	//充值成功
				}else{
					fOrderState = "5";	//卡密失败
				}
			}else{
				fGiveID = fGiveID.substring(0, fGiveID.length() - 3);
				sql = "select top 1 * from CoStates where fPlatformID = '" + fGiveID + "' and fProductID = '" + fProductID + "' and fState = '0' " +
				"and fMarkA = '" + fFillMsg + "' order by fLevel desc";
				rs = dc.query(sql);
				if (rs!=null && rs.next()){
					fRuleState = fc.getrv(rs, "fOrderState", "4");
				}else{
					//如果为空,写新记录到表,置订单为手工状态
					sql = "";
					sql = fc.SetSqlInsertStr(sql, "fid", fc.getGUID6(""));
					sql = fc.SetSqlInsertStr(sql, "fPlatformID", fGiveID);
					sql = fc.SetSqlInsertStr(sql, "fProductID", fProductID);
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
				//转换状态:0=支付成功,非0支付失败(1=卡密失败,2=恢复充值,3=下一通道,4=手工确认,5=账号失败,6=暂停充值)
				if (fRuleState.equals("0")) fOrderState = "4";
				if (fRuleState.equals("1")) fOrderState = "5";
				if (fRuleState.equals("2")) fOrderState = "2";
				if (fRuleState.equals("3")) fOrderState = "1";
				if (fRuleState.equals("4")) fOrderState = "8";
				if (fRuleState.equals("5")) fOrderState = "2";
				if (fRuleState.equals("6")) fOrderState = "2";
				//如果是本地卡,除了卡密失败和手工确认,其它均强制转为未充状态(0)
				if (fGiveID.indexOf("kck") > 0){
					if (!fRuleState.equals("0") && !fRuleState.equals("1") && !fRuleState.equals("4")){
						fOrderState = "0";
					}
				}
			}
			
			
			//5.是否重试处理,只要fErrorCount大于0,就要重试,在分发时控制其次数
			if (Float.valueOf(sErrorCount).floatValue() > 0){
				fOrderState = "2";					//恢复成等待充值
			}					
				
			
			//7.当等待充值或手工确认时, 不更新超时时间
			String fOverTime = "getdate()";
			if (fOrderState.equals("2")||(fOrderState.equals("8"))){
				fOverTime = "fOverTime";
			}
			
			//8.更新通知状态和出错次数
			String fNoticeState = "fNoticeState";
			String fErrorCount = "fErrorCount";
			if (fOrderState.equals("4") || fOrderState.equals("5")){
				fNoticeState = "'0'";
				fErrorCount = "0";						//重置通知出错次数(以后可能用不到,全都到列表中重发)
			}else{
				if (fOrderState.equals("2"))
					fErrorCount = "fErrorCount - 1";	//重试次数减1
				else
					fErrorCount = "fErrorCount";		//其它保存不变
			}
			
			//9.更新主表, 等待返馈
			sql = "";
			sql = "UPDATE " + sCardTable + " SET " + 
					"fLockID=''," +
					"fState='" + fOrderState + "'," +
					"fErrorCount=" + fErrorCount + "," +
					"fMoney='" + fFactMoney + "'," +	
					"fFillTime=getdate()," +
					"fOverTime=" + fOverTime + "," +
					"fNoticeState=" + fNoticeState + "," +
					"fFillMsg='" + fFillMsg + "'" +
					" WHERE fGiveOrderID = '" + fFillID + "' and (('" + fOrderState + "'='4') " +
					" or (fState <> '4') or ('" + IsEdit + "' = 'false'))";
			int m = dc.execute(sql);
			
		
			//10.返回结果
			if (m == -1) 
				return fc.getResultStr("cardresult", "false", "接收失败(" + m + ")," + dc.getError());  
			else{
				//线程通知
				if (sCardTable.equals("CaOrder"))
					if (fOrderState.equals("4") || fOrderState.equals("5")){
						Thread t1 = new Thread(new NoticeThread(fID, "")); 
						t1.start(); 
					}
				//
				return fc.getResultStr("cardresult", "true", "接收成功(" + m + ")");  
			}
			//
		}catch(Exception e){
			return fc.getResultStr("cardresult", "false", "服务器运行出错");
		}
	}

}