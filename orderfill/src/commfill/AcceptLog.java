package commfill;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import accocheck.AccountCheckThread;
import accocheck.HunanQBThread;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: AcceptLog
 * 
 * @web.servlet name="AcceptLog" display-name="AcceptLog"
 * 
 * @web.servlet-mapping url-pattern="/AcceptLog"
 * 
 */
public class AcceptLog extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK"; 
	static DataConnect dc = new DataConnect("orderfill", false);

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AcceptLog() {
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
	 * 接收充值日志, 根据卡或号订单号, 查询订单内容, 再写充值记录. 接口尽量少参数. 
	 * 这里除了做手工确认处理外, 其它只做保存, 不做任何业务控制. 	 * 
	 */
	public String acceptResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//1.接收参数
			String sign = fc.getpv(request, "sign");					//fID + fFactMoney + fState + "5ht73h6edt544t6d4776rth34w4759ow"
			String fGiveID = fc.getpv(request, "giveid");				//分发平台
			String fFillID = fc.getpv(request, "fillid");				//充值订单号
			String fFactMoney = fc.getpv(request, "fillmoney");			//充值金额, 面值不符的,由平台指定是否手工处理或计算出
			String fFillMsg = fc.getpv(request, "msg");					//充值信息
			String AppId = fc.getpv(request, "appid");					//挂机ID
			String fFIllState = fc.getpv(request, "state");				//状态,0=成功,1=失败
			//其他
			String fAccountMoney = fc.getpv(request, "accomoney");		//账户余额
			
			//
			System.out.print("[" + AppId + "]充值记录:" + fGiveID + "," + fFillID + "," 
					+ fFactMoney + "," + fFillMsg + "," + AppId + "," + fAccountMoney);

			//护展信息
			String fInfo1 = fc.getpv(request, "fInfo1");	    
			String fInfo2 = fc.getpv(request, "fInfo2");	    
			String fInfo3 = fc.getpv(request, "fInfo3");	    
			String fInfo4 = fc.getpv(request, "fInfo4");	    
			String fInfo5 = fc.getpv(request, "fInfo5");
			
			//2.参数检查
//			if (sign.equals(fc.getMd5Str(fCardID + fAccoID + fFactMoney + "5ht73h6edt544t6d4776rth34w4759ow"))) 
//				return fc.getResultStr("acceptlog", "false", "md5校验失败");
			//if (fGiveID.equals("") && fAccoID.equals("")) return fc.getResultStr("acceptlog", "false", "giveid不能为空");
			if (fFillID.equals("")) return fc.getResultStr("acceptlog", "false", "fillid不能为空");
			//if (fCardID.equals("") && fAccoID.equals("")) return fc.getResultStr("acceptlog", "false", "cid和aid不能同时为空");
			//if (fFillMsg.equals("")) return fc.getResultStr("acceptlog", "false", "msg充值信息不能为空");
			if (fFillMsg.length() > 32) fFillMsg = fFillMsg.substring(0, 32);
			
			//充值金额
			if (!fFactMoney.equals("fullmoney") && !fFactMoney.equals("logmoney")){
				float nFactMoney = 0;
				try{
					nFactMoney = Float.valueOf(fFactMoney).floatValue();
				}catch(Exception e){
					return fc.getResultStr("acceptlog", "false", "fillmoney充值金额不是数值型");
				}
				fFactMoney = "" + nFactMoney;
			}


			//是否写重, 在号取卡或卡取号时, 这个订单号一定要送给被取方,以做为充值订单号
			boolean bIsLoadOrder = true;
			String sql = ""; 
			String sOrderType = ""; 
			ResultSet rs = null; 
//			sql = "select * from CoFillLog where fFillOrderID = '" + fFillID + "' ";
//			rs = dc.query(sql);
//			if (rs!=null && rs.next()){
//				String sOrderType = fc.getrv(rs, "sOrderType", "");
//			}
//			dc.CloseResultSet(rs);
			
			//3.取出卡订单信息, (暂时无法用充值订单号区分是否要查卡订单表,因为有互取动作.)
			String sCardTable = "CaOrder";												//正常卡订单
			String fCProductID = "", fCGiveID = "", fCRate = "", fCardID="", fCardNo = "", fCPrice = "", fCAccountID = "";
			if (bIsLoadOrder && !fFillID.equals("")){
				if (fFillID.substring(0, 1).equals("K")) sCardTable = "CaOrderKck";		//如果是库存卡订单
				//
				sql = "select top 1 * from " + sCardTable + " where fGiveOrderID = '" + fFillID + "' ";
				//System.out.print(sql);
				rs = dc.query(sql);
				if (rs!=null && rs.next()){
					fCardID = fc.getrv(rs, "fID", ""); 
					fCGiveID = fc.getrv(rs, "fGiveID", "");
					fCProductID = fc.getrv(rs, "fProductID", "");
					fCardNo = fc.getrv(rs, "fCardNo", "");
					fCRate = fc.getrv(rs, "fRate", "");
					fCPrice = fc.getrv(rs, "fPrice", "");
					fCAccountID = fc.getrv(rs, "fAccountID", "");
				}else{
					//dc.CloseResultSet(rs);
					//System.out.print("取卡订单信息时失败:" + sql);
				}
				dc.CloseResultSet(rs);
			}


			//4.取出号订单信息(暂时无法用充值订单号区分是否要查卡订单表,因为有互取动作.)
			String sPlayName = "", fAGiveID = "", fAccoID="", fAProductID = "", fAPrice = "", fARate = "", 
			fAProductType = "", fUserArea = "", fGiveTime = "";
			if (bIsLoadOrder && !fFillID.equals("")){
				sql = "select top 1 * from AcOrder where fGiveOrderID = '" + fFillID + "' ";
				//System.out.print(sql);
				rs = dc.query(sql);
				if (rs!=null && rs.next()){
					fAccoID = fc.getrv(rs, "fID", "");
					fAGiveID = fc.getrv(rs, "fGiveID", "");
					fAProductType = fc.getrv(rs, "fInsideID", "");
					fAProductID = fc.getrv(rs, "fProductID", "");
					sPlayName = fc.getrv(rs, "fPlayName", "");
					fARate = fc.getrv(rs, "fRate", "");
					fAPrice = fc.getrv(rs, "fLackMoney", "");
					fUserArea = fc.getrv(rs, "fUserArea", "");
					fGiveTime = fc.getrv(rs, "fGiveTime", "");
				}else{
					//dc.CloseResultSet(rs);
					//System.out.print("取号订单信息时失败:" + sql);
				}
				System.out.print("号订单信息:" + fAccoID + "," + fFillID + "," + fGiveTime);
				dc.CloseResultSet(rs);
			}

			//处理充值金额
			if (fFactMoney.equals("fullmoney") && (fAPrice.equals(""))) fFactMoney = fCPrice;
			if (fFactMoney.equals("fullmoney") && (fCPrice.equals(""))) fFactMoney = fAPrice;
			if (fFactMoney.equals("fullmoney")) fFactMoney = "0"; 
			float nFactMoney = Float.valueOf(fFactMoney).floatValue();

			//得到商品类型和充值订单号
			String fProductType = "";
			if (!fCProductID.equals("")) fProductType = fCProductID;
			if (!fAProductType.equals("")) fProductType = fAProductType;
			String fGiveOrderID = "";
			if (fProductType.equals("")){
				System.out.print("");
				return fc.getResultStr("acceptlog", "true", "订单状态已改变,拒绝接收");
			}
//			if (!fCGiveOrderID.equals("")) fGiveOrderID = fCGiveOrderID;
//			if (!fAGiveOrderID.equals("")) fGiveOrderID = fAGiveOrderID;
			//if (fGiveOrderID.equals("")) fGiveOrderID = fCardID;  		//兼容测试或已过时订单
			//if (fGiveOrderID.equals("")) fGiveOrderID = fAccoID;  
			
			
			//5.查询状态规则,获得订单状态:0=充值成功,非0充值失败(1=卡密失败,2=恢复充值,3=下一通道,4=手工确认,5=账号失败)
			String fCheckMoney = "1";	//不检查
			String fOrderState = "";
			
			if (!fCGiveID.equals("")) fGiveID = fCGiveID;
			if (!fAGiveID.equals("")) fGiveID = fAGiveID;
			
			//处理分发平台ID
			if (fGiveID.substring(fGiveID.length() - 3, fGiveID.length() - 2).equals("-"))
				fGiveID = fGiveID.substring(0, fGiveID.length() - 3);
			
			//如果不是成功,则去判断状态
			if (!fFIllState.equals("0")){
				//如果充值结果为空, 金额大于0,认为是成功, 等于0失败
				if (fFillMsg.equals("")){ 
					if (nFactMoney > 0){
						fOrderState = "0";	//充值成功 
					}else{
						fOrderState = "1";	//卡密失败
					}
				}else{
					sql = "select top 1 * from CoStates where fPlatformID = '" + fGiveID + "' and fProductID = '" + fProductType + "' and fState = '0' " +
					"and fMarkA = '" + fFillMsg + "' order by fLevel desc";
					//System.out.print(sql);
					rs = dc.query(sql);
					if (rs!=null && rs.next()){
						fOrderState = fc.getrv(rs, "fOrderState", "4");
						fCheckMoney = fc.getrv(rs, "fCheckMoney", "1");
						System.out.print("旧状态:" + fFillID + "," + fGiveID + "," + fProductType + "," + fFillMsg + "," + fOrderState);
					}else{
						//如果为空,写新记录到表,置订单为手工状态 
						System.out.print("新状态:" + fFillID + "," + fGiveID + "," + fProductType + "," + fFillMsg);
						sql = "";
						sql = fc.SetSqlInsertStr(sql, "fid", fc.getGUID6("")); 
						sql = fc.SetSqlInsertStr(sql, "fPlatformID", fGiveID);
						sql = fc.SetSqlInsertStr(sql, "fProductID", fProductType);
						sql = fc.SetSqlInsertStr(sql, "fMarkA", fFillMsg);
						sql = fc.SetSqlInsertStr(sql, "fLevel", "0");
						sql = fc.SetSqlInsertStr(sql, "fOrderState", "4");
						sql = fc.SetSqlInsertStr(sql, "fState", "0");
						sql = fc.SetSqlInsertStr(sql, "fCheckMoney", "1");
						sql = "insert into CoStates " + sql;
						dc.execute(sql);
						//
						fOrderState = "4";		//手工
					}
					dc.CloseResultSet(rs);
				}
			}else{
				fOrderState = "0";	//直接成功
			}
				
			System.out.print(fFillID + "," + fCGiveID + "," + fAGiveID + "," + fGiveID + "," + fProductType + "," + fFillMsg + "," + fOrderState);

			
			//6.是否检查账号余额, 计算两次账号余额的差值, 是否与充值金额相等(充值成功,账号订单号相同, 不是当前卡订单)
			if (fCheckMoney.equals("0") && !fAccountMoney.equals("")){
				sql = "select top 1 fAccountMoney from CoFillLog where fType = '" + fProductType + "' and fState = '0'" +
						" and fAcOrderID = '" + fAccoID + "' and fCaOrderID <> '" + fCardID + "'" +
						" and fAccountMoney < '" + fAccountMoney + "' order by fAccountMoney desc";
				rs = dc.query(sql);
				if (rs.next()){
					String sAccountMoney2 = fc.getrv(rs, "fAccountMoney", "");
					float f = Float.valueOf(fAccountMoney).floatValue() - Float.valueOf(sAccountMoney2).floatValue();
					if (f != Float.valueOf(nFactMoney).floatValue()){
						fOrderState = "4";
					}
				}
				dc.CloseResultSet(rs);
			}
			
			//处理外呼充值QB
			if (fAGiveID.equals("waihu-05")){
				if (fOrderState.equals("0") || fOrderState.equals("5")){
					String fState = "";
					if (fOrderState.equals("0")) fState = "0"; 
					if (fOrderState.equals("5")) fState = "1";
					Thread t1 = new Thread(new HunanQBThread(fAccoID, fInfo3, sPlayName, fFactMoney, fState, fFillMsg)); 
					t1.start(); 
				}
			}
			
			//7.写充值记录: 
			if (!fOrderState.equals("0")) fFactMoney = "0";
			if (fCRate.equals("")) fCRate = "null";
			if (fARate.equals("")) fARate = "null";
			if (fAPrice.equals("")) fAPrice = "null";
			if (fAccountMoney.equals("")) fAccountMoney = "null";
			if (fInfo1.equals("")) fInfo1 = "fInfo1"; else fInfo1 = "'" + fInfo1 + "'";
			if (fInfo2.equals("")) fInfo2 = "fInfo2"; else fInfo2 = "'" + fInfo2 + "'";
			if (fInfo3.equals("")) fInfo3 = "fInfo3"; else fInfo3 = "'" + fInfo3 + "'";
			if (fInfo4.equals("")) fInfo4 = "fInfo4"; else fInfo4 = "'" + fInfo4 + "'";
			if (fInfo5.equals("")) fInfo5 = "fInfo5"; else fInfo5 = "'" + fInfo5 + "'";
			sql = "update CoFillLog set " +
					"fTime=" + "GETDATE()" +
					",fMoney=" + fFactMoney +
					",fCaOrderID='" + fCardID + "'" +
					",fCardNo='" + fCardNo + "'" +
					",fCaRate=" + fCRate +
					",fAccountMoney=" + fAccountMoney +
					",fFillInfo='" + fFillMsg + "'" +
					",fState='" + fOrderState + "'" +
					",fAppID='" + AppId + "'" +
					",fInfo1=" + fInfo1 + "" +
					",fInfo2=" + fInfo2 + "" +
					",fInfo3=" + fInfo3 + "" +
					",fInfo4=" + fInfo4 + "" +
					",fInfo5=" + fInfo5 + "" +
					" where fFillOrderID = '" + fFillID + "' and (fstate = '7' or fstate = '" + fOrderState + "')";
			int m = dc.execute(sql);
			if (m == 0){
				if (fInfo1.equals("fInfo1")) fInfo1 = "''"; 
				if (fInfo2.equals("fInfo2")) fInfo2 = "''"; 
				if (fInfo3.equals("fInfo3")) fInfo3 = "''"; 
				if (fInfo4.equals("fInfo4")) fInfo4 = "''"; 
				if (fInfo5.equals("fInfo5")) fInfo5 = "''"; 
				if (sPlayName.equals("")) sPlayName = fCAccountID;
				sql = "";
				sql = fc.SetSqlInsertStr(sql, "fID", fc.getGUID6(""));
				sql = fc.SetSqlInsertStr(sql, "fTime", "GETDATE()", false);
				sql = fc.SetSqlInsertStr(sql, "fType", fProductType);
				sql = fc.SetSqlInsertStr(sql, "fPlatformID", fGiveID);
				sql = fc.SetSqlInsertStr(sql, "fCaOrderID", fCardID);
				sql = fc.SetSqlInsertStr(sql, "fCardNo", fCardNo);
				sql = fc.SetSqlInsertStr(sql, "fCaRate", fCRate, false);
				sql = fc.SetSqlInsertStr(sql, "fAcOrderID", fAccoID);
				sql = fc.SetSqlInsertStr(sql, "fPlayName", sPlayName);
				sql = fc.SetSqlInsertStr(sql, "fAcRate", fARate, false);
				sql = fc.SetSqlInsertStr(sql, "fAcProductID", fAProductID);
				sql = fc.SetSqlInsertStr(sql, "fPrice", fAPrice, false);
				sql = fc.SetSqlInsertStr(sql, "fMoney", fFactMoney);
				sql = fc.SetSqlInsertStr(sql, "fAccountMoney", fAccountMoney, false);
				sql = fc.SetSqlInsertStr(sql, "fFillOrderID", fFillID);
				sql = fc.SetSqlInsertStr(sql, "fIpArea", fUserArea);
				sql = fc.SetSqlInsertStr(sql, "fState", fOrderState);	//充值记录与状态规则中的定义是一致的. 在充值结果接口转成号或卡订单状态
				sql = fc.SetSqlInsertStr(sql, "fFillInfo", fFillMsg);
				sql = fc.SetSqlInsertStr(sql, "fAppID", AppId);
				sql = fc.SetSqlInsertStr(sql, "fInfo1", fInfo1, false);
				sql = fc.SetSqlInsertStr(sql, "fInfo2", fInfo2, false);
				sql = fc.SetSqlInsertStr(sql, "fInfo3", fInfo3, false);
				sql = fc.SetSqlInsertStr(sql, "fInfo4", fInfo4, false);
				sql = fc.SetSqlInsertStr(sql, "fInfo5", fInfo5, false);
				sql = "insert into CoFillLog " + sql;
				m = dc.execute(sql);
			}
			
			//如果是手工确认,则更新卡订单和号订单状态,使充值结果返回不发生作用. 
			int x = 0, y = 0;
			if (m != -1)
				if (fOrderState.equals("4")){
					x = dc.execute("update " + sCardTable + " set fState = '8' where fGiveOrderId = '" + fFillID + "'");
					y = dc.execute("update AcOrder set fState = '8' where fGiveOrderId = '" + fFillID + "'");
				}
			
			//8.返回结果
			if (m == -1) 
				return fc.getResultStr("acceptlog", "false", "接收失败(" + m + "," + x + "," + y + ")," + dc.getError());  
			else
				return fc.getResultStr("acceptlog", "true", "接收成功(" + m + "," + x + "," + y + ")");  
			//
		}catch(Exception e){
			return fc.getResultStr("acceptlog", "false", "服务器运行出错");
		}
	}

}