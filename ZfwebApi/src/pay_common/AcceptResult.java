package pay_common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import common.*;

/*
 * 支付宝加款处理,订单退款处理
 */

/**
 * Servlet implementation class for Servlet: ArticleReturn
 *
 * @web.servlet
 *   name="ArticleReturn"
 *   display-name="ArticleReturn" 
 *
 * @web.servlet-mapping
 *   url-pattern="/ArticleReturn"
 *  
 */
 public class AcceptResult extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK";
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AcceptResult() {
		super();
	} 
   	
	public void destroy() { 
//		if (dc != null) {
//			dc.CloseConnect();
//			dc = null;
//		}
	}
	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.print(request.getParameter("sign"));
		System.out.print(request.getParameter("out_trade_no"));
		System.out.print(request.getParameter("total_fee"));
		System.out.print(request.getParameter("trade_status"));
		if ("JFJ45F674WR7UDY6U547R6JY48D7F93".equals(request.getParameter("sign"))
				&&(!"".equals(request.getParameter("out_trade_no"))) 
				&&(!"".equals(request.getParameter("total_fee"))) 
				&&(!"".equals(request.getParameter("trade_status"))))
			//只为测试加款
			out.print(acceptResult_hq(request, response));
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.print(request.getParameter("sign"));
		System.out.print(request.getParameter("out_trade_no"));
		System.out.print(request.getParameter("total_fee"));
		System.out.print(request.getParameter("trade_status"));
		if ("JFJ45F674WR7UDY6U547R6JY48D7F93".equals(request.getParameter("sign"))
				&&(!"".equals(request.getParameter("out_trade_no"))) 
				&&(!"".equals(request.getParameter("total_fee"))) 
				&&(!"".equals(request.getParameter("trade_status"))))
			//只为测试加款
			out.print(acceptResult_hq(request, response));
	}   	

	/*
	 * 支付宝加款通知处理,直接调用当前方法,未调用servlet, 
	 */
	public String acceptResult(HttpServletRequest request, HttpServletResponse response){
		try{
			//接收参数
			String s = "error,0"; //extra_common_param
			
			//接收支付宝参数------------------------------
			String subject = request.getParameter("subject");
			String body = request.getParameter("body");
			System.out.print("subject=" + subject + ",body=" + body);
			if (subject != null && body != null){
				System.out.print("[加款通知]支付宝:" + subject + "," + body);
				if (subject.equals("支付加款")) s = acceptResult_zfb(request, response);
				if (subject.equals("九易天下")) s = acceptResult_hq(request, response);
				if (subject.equals("瑞达加款")) s = acceptResult_rd(request, response);
			}
			
			//接收财付通参数------------------------------
			String attach = fc.getpv(request, "attach");
			System.out.print("attach=" + attach);
			if (attach.equals("dhtx1")){
				System.out.print("[加款通知]财付通1:" + attach);
				if (attach.equals("天下加款")) s = acceptResult_tx(request, response);
			}
			//接收财付通2参数------------------------------
			attach = fc.getpv(request, "attach");
			System.out.print("attach=" + attach);
			if (attach.equals("dhtx2")){
				System.out.print("[加款通知]财付通2:" + attach);
				if (attach.equals("天下加款")) s = acceptResult_tx(request, response);
			}
			
			//接收快钱参数----------------------------------
			String ext1 = fc.getpv(request, "ext1");
			String ext2 = fc.getpv(request, "ext2");
			System.out.print("ext1=" + ext1 + ",ext2=" + ext2);
			// 
			if (ext1 != null && ext2 != null && ext1!="" && ext2 !=""){
				System.out.print("[加款通知]快钱:" + ext1 + "," + ext2);
				if (ext1.equals("支付加款")) s = acceptResult_kuaiqian(request, response);
			}
			
			//接收盛付通参数--------------------------------
			String Remark1 = fc.getpv(request, "Remark1");
			String Remark2 = fc.getpv(request, "Remark2");
			System.out.print("Remark1=" + Remark1 + ",Remark2=" + Remark2);   
			// 
			if (Remark1 != null && Remark2 != null && Remark1!="" && Remark2!=""){
				
				System.out.print("[加款通知]盛付通:" + Remark1 + "," + Remark2);
				if (Remark1.equals("支付加款")) s = acceptResult_shengfutong(request, response);
			}
			
			//接收新生网银参数-------------------------------
			String remark = request.getParameter("remark");
			System.out.print("remark=" + remark);
			// 
			if (remark != null){
				System.out.print("[加款通知]新生:" + remark);
				if (remark.indexOf("95588") == 0) s = acceptResult_xinsheng(request, response);
				if (remark.indexOf("dhtx") == 0) s =  "success";
				if (remark.indexOf("runda") == 0) s = "success";
				if (remark.indexOf("diandou") == 0) s = "success";
				if (remark.indexOf("baidou") == 0) s = "success";
			}
			  
			//if (pt_type.equals("19pay")) s = acceptResult_19pay(request, response);

			//
			return s;
		}catch(Exception e){
			return "error,1";
		}
	}
	
	/*
	 * 新生网银加款通知处理:  支付加款
	 */
	private String acceptResult_xinsheng(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			//接收参数
			String orderNo = funcejb.getres(request, "orderNo");			//支付序列号
			String orderID = funcejb.getres(request, "orderID");			//商户订单号(含商户ID)
			String payAmount = funcejb.getres(request, "payAmount");		//实际支付金额
			String stateCode = funcejb.getres(request, "stateCode");		//处理状态,01代表 成功   11代表 失败		
			String ip = request.getRemoteAddr();							//处理ip
			//
			smg("[网银通知]新生:" + ip + "," + orderID + "," + payAmount + "," + stateCode);
			//
			if (orderID.equals("")) return "error,orderID";
			if (stateCode.equals("")) return "error,trade_status";
			try{
				payAmount = "" + Float.valueOf(payAmount).floatValue() / 100;
			}catch(Exception ee){
				return "error,total_fee";
			}
			
			//检查是否加款成功,写交易记录
			System.out.print("[网银通知]新生,收到消息:" + stateCode);
			String sState = "";
			if (stateCode.equals("0") || stateCode.equals("1")) return "success";	
			if (stateCode.equals("2")){
				return "success";
				//sState = "支付成功";	   
			}
						
			if (stateCode.equals("3"))	
				sState = "支付失败";			
			if (sState.equals("")) return "error,state_is_null";						//非处理完成,直接跳出
			
			//检查当前支付宝订单号是否已经处理完成 
			String sql = "update fillcard set version = '" + orderNo + "', fState='" + sState + "', fReturnSupState='XinSheng'," +
					" fMoney=" + payAmount + ", fFactMoney=" + payAmount + " * fBili / 100, fCheckState = '返馈中', " +
					" fFillTime=GETDATE(), fOverTime=GETDATE() where id='" + orderID+ "' and fState<>'支付成功'";
			DataConnect dc = new DataConnect("fillcard", false);
			int n = 0, m = 0;
			while (true){
				n = dc.execute(sql);
				m = m + 1;
				if (n > -1) break;
				if (m > 3) break;
				System.out.print("[网银通知]盛付通," + m + "次重试更新" + n + "," + orderID + "," + sState + "," + payAmount);
				Thread.sleep(2000);	//等待两秒
			}
			dc.CloseConnect();
			System.out.print("[网银通知]新生,接收完成:" + n + "," + orderID + "," + sState + "," + payAmount);
			if (n == 0 || n == 1){
				//线程通知
				if (sState.equals("支付成功") || sState.equals("支付失败")){ 
					Thread t1 = new Thread(new AllotThreadPayic(orderID, 8080)); 
					t1.start(); 
				}
				return "success";
			}else{
				System.out.print("sql:" + sql);
				return "error";
			}
		}catch(Exception e){
			return "error,system";
		}

	}

	/*
	 * 盛付通加款通知处理:  支付加款
	 */
	private String acceptResult_shengfutong(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			
			//接收参数
			
			String serialno = funcejb.getres(request, "serialno");			//支付序列号
			String out_trade_no = funcejb.getres(request, "OrderNo");		//商户订单号(含商户ID)
			String total_fee = funcejb.getres(request, "PayAmount");		//实际支付金额
			String trade_status = funcejb.getres(request, "Status");		//处理状态,01代表 成功   11代表 失败		
			String ip = request.getRemoteAddr();							//处理ip
			//
			smg("[网银通知]盛付通:" + ip + "," + out_trade_no + "," + total_fee + "," + trade_status);
			//
			if (out_trade_no.equals("")) return "error,out_trade_no";
			if (trade_status.equals("")) return "error,trade_status";
			try{
				total_fee = "" + Float.valueOf(total_fee).floatValue();// / 100;
			}catch(Exception ee){
				return "error,total_fee";
			}
			
			//检查是否加款成功,写交易记录
			System.out.print("[网银通知]盛付通,收到消息:" + trade_status);
			String sState = "";
			if (trade_status.equals("01"))	
				sState = "支付成功";			
			else
				sState = "支付失败";			
			if (sState.equals("")) return "success";						//非处理完成,直接跳出
			
			//检查当前支付宝订单号是否已经处理完成 
			String sql = "update fillcard set version = '" + serialno + "', fState='" + sState + "', fReturnSupState='Shengft', fMoney=" + total_fee + ", fFactMoney=" + total_fee + " * fBili / 100, fCheckState = '返馈中', " +
			" fFillTime=GETDATE(), fOverTime=GETDATE() where id='" + out_trade_no+ "' and fState<>'支付成功'";
			DataConnect dc = new DataConnect("fillcard", false);
			int n = 0, m = 0;
			while (true){
				n = dc.execute(sql);
				m = m + 1;
				if (n > -1) break;
				if (m > 3) break;
				System.out.print("[网银通知]盛付通," + m + "次重试更新" + n + "," + out_trade_no + "," + sState + "," + total_fee);
				Thread.sleep(2000);	//等待两秒
			}
			dc.CloseConnect();
			System.out.print("[网银通知]盛付通,接收完成:" + n + "," + out_trade_no + "," + sState + "," + total_fee);
			if (n == 0 || n == 1){
				//线程通知
				if (sState.equals("支付成功") || sState.equals("支付失败")){ 
					Thread t1 = new Thread(new AllotThreadPayic(out_trade_no, 8080)); 
					t1.start(); 
				}

				return "success";
			}else{
				System.out.print("sql:" + sql);
				return "error";
			}
		}catch(Exception e){
			return "error,system";
		}
	}

	/*
	 * 支付宝加款通知处理:  支付加款 
	 */
	private String acceptResult_zfb(HttpServletRequest request, HttpServletResponse response) {
		try{
			
			//接收参数
			String out_trade_no = funcejb.getres(request, "out_trade_no");	//商户订单号(含商户ID)
			String total_fee = funcejb.getres(request, "total_fee");		//加款金额
			String trade_status = funcejb.getres(request, "trade_status");	//处理状态		
			String ip = request.getRemoteAddr();							//处理ip
			//
			smg("[网银通知]支付宝:" + ip + "," + out_trade_no + "," + total_fee + "," + trade_status);
			//
			if (out_trade_no.equals("")) return "error,out_trade_no";
			if (trade_status.equals("")) return "error,trade_status";
			try{
				total_fee = "" + Float.valueOf(total_fee).floatValue();
			}catch(Exception ee){
				return "error,total_fee";
			}
			
			//检查是否加款成功,写交易记录
			System.out.print("[网银通知]收到消息:" + trade_status);
			String sState = "";
			if (trade_status.equals("TRADE_FINISHED"))	sState = "支付成功";			
			if (trade_status.equals("TRADE_SUCCESS"))	sState = "支付成功";			
			if (trade_status.equals("TRADE_CLOSED"))	sState = "支付失败";			
			if (sState.equals("")) return "success";						//非处理完成,直接跳出
			
			//检查当前支付宝订单号是否已经处理完成 
			String sql = "update fillcard set fState='" + sState + "', fReturnSupState='AliPay', fMoney='" + total_fee + "', fFactMoney=" + total_fee + " * fBili / 100, fCheckState = '返馈中', " +
			" fFillTime=GETDATE(), fOverTime=GETDATE() where id='" + out_trade_no+ "' and fState<>'支付成功'";
			DataConnect dc = new DataConnect("fillcard", false);
			int n = dc.execute(sql);
			dc.CloseConnect();
			System.out.print("[网银通知]接收完成:" + n + "," + out_trade_no + "," + sState + "," + total_fee);
			return "success";
		}catch(Exception e){
			return "error,system";
		}
	}

	/*
	 * 快钱加款通知处理:  支付加款 
	 */
	public String acceptResult_kuaiqian(HttpServletRequest request, HttpServletResponse response){
		try{
			
			//接收参数
			String out_trade_no = funcejb.getres(request, "orderId");		//商户订单号(含商户ID)
			String total_fee = funcejb.getres(request, "payAmount");		//实际支付金额
			String trade_status = funcejb.getres(request, "payResult");		//处理状态,10代表 成功   11代表 失败		
			String ip = request.getRemoteAddr();							//处理ip
			//
			smg("[网银通知]快钱:" + ip + "," + out_trade_no + "," + total_fee + "," + trade_status);
			//
			if (out_trade_no.equals("")) return "error,out_trade_no";
			if (trade_status.equals("")) return "error,trade_status";
			try{
				total_fee = "" + Float.valueOf(total_fee).floatValue() / 100;
			}catch(Exception ee){
				return "error,total_fee";
			}
			
			//检查是否加款成功,写交易记录
			System.out.print("[网银通知]收到消息:" + trade_status);
			String sState = "";
			if (trade_status.equals("10"))	sState = "支付成功";			
			if (trade_status.equals("11"))	sState = "支付失败";			
			if (sState.equals("")) return "success";						//非处理完成,直接跳出
			
			//检查当前支付宝订单号是否已经处理完成 
			String sql = "update fillcard set fState='" + sState + "', fReturnSupState='KuaiQian', fMoney='" + total_fee + "', fFactMoney=" + total_fee + " * fBili / 100, fCheckState = '返馈中', " +
			" fFillTime=GETDATE(), fOverTime=GETDATE() where id='" + out_trade_no+ "' and fState<>'支付成功'";
			DataConnect dc = new DataConnect("fillcard", false);
			int n = dc.execute(sql);
			dc.CloseConnect();
			System.out.print("[网银通知]接收完成:" + n + "," + out_trade_no + "," + sState + "," + total_fee);
			if (n == 0 || n == 1){
				//线程通知
				if (sState.equals("支付成功") || sState.equals("支付失败")){ 
					Thread t1 = new Thread(new AllotThreadPayic(out_trade_no, 8080)); 
					t1.start(); 
				}
				return "success";
			}else{
				System.out.print("sql:" + sql);
				return "error";
			}
		}catch(Exception e){
			return "error,system";
		}
	}
	
	/*
	 * 支付宝加款通知处理:  华奇加款 
	 */
	public String acceptResult_tx(HttpServletRequest request, HttpServletResponse response){
		try{
			
			//接收参数
			String out_trade_no = fc.getpv(request, "out_trade_no");	//商户订单号(含商户ID)
			String total_fee = fc.getpv(request, "total_fee");			//加款金额
			String trade_state = fc.getpv(request, "trade_state");	//处理状态
			String memo = fc.getpv(request, "memo");					//自定义备注
			String opereateid = fc.getpv(request, "opereateid");		//手工处理人员
			String ip = request.getRemoteAddr();						//处理ip
			smg("[加款通知]" + ip + "," + out_trade_no + "," + total_fee + "," + trade_state);
			
			try{
				total_fee = "" + Float.valueOf(total_fee).floatValue() / 100;
			}catch(Exception ee){
				return "error,total_fee";
			}

			//
			if (memo.equals("")) memo = "财付通";
			if (opereateid.equals("")) opereateid = "自动";
			
			//IP限制 
			
			//取客户ID
			String fClientID = "";
			int n = out_trade_no.indexOf("-");
			if (n > -1)
				fClientID = out_trade_no.substring(n + 1);			
			else
				return "error_out_trad_no";
			//初始化连接,用单独连接,保存事务单独运行
			DataConnect dc = new DataConnect("orderfill", false);
			
			//检查是否加款成功,写交易记录
			if (!trade_state.equals("0")){
//				//转换状态				
//				String sState = trade_status;
//				if (trade_status.equals("WAIT_BUYER_PAY")) sState = "10";
//				if (trade_status.equals("TRADE_CLOSED")) sState = "12";
//				//写转账记录
//				String sis = "";
//				sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("TL"));
//				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
//				sis = fc.SetSqlInsertStr(sis, "fUserID", fClientID);
//				sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
//				sis = fc.SetSqlInsertStr(sis, "fType", sState);			//等待加款,加款失败
//				sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
//				String sql = "insert into TransferLog " + sis;
//				int m = dc.execute(sql);
//				if (m != 1){
//					dc.CloseConnect();
//					return "error_接收失败";
//				}else{
//					dc.CloseConnect();
//					return "success";		//非加款成功,直接跳出
//				}
				return "error_" + trade_state;
			}
			
			//检查当前支付宝订单号是否已经处理完成	ok
			String sql = "select * from CoMoneyChange where fClientID = '" + fClientID + "' and fOrderID = '" + out_trade_no + "'";
			ResultSet rs = dc.query(sql);
			String sError = "";
			if (rs == null){
				sError = "error_连接失败";
			}else{
				if (rs.next()) sError = "success";
				dc.CloseResultSet(rs);
			}
			if (!sError.equals("")){
				dc.CloseConnect();
				return sError;
			}
			
			//取出账户金额
			String fClientMoney = "0", fAgentID = "";
			sql = "select c.fAgentID, cm.fMoney from CoClientMoney as cm ,CoClient as c where cm.fid = '" + fClientID + "' and cm.fid=c.fid";
			rs = dc.query(sql);
			if (rs.next()){
				fClientMoney = fc.getrv(rs, "fMoney", "0");
				fAgentID = fc.getrv(rs, "fAgentID", "0");
			}else{
				//没有客户账户余额记录时将自动增加
				sql = "insert into CoClientMoney (fid, fMoney)values('" + fClientID + "', 0)";
				dc.execute(sql);
			}
			dc.CloseResultSet(rs);

			//-----事务开始-----
			Statement stmt = null;
			try{
				//初始化
				stmt = dc.getConnect().createStatement();
				if (stmt == null){
					dc.CloseConnect();
					return "error_连接失败";
				}
				dc.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
				int m = 0;				
				//更新手工加款状态
//				if (!opereateid.equals("自动")){
//					String fid = fc.getString("~!@#$%^&*()" + out_trade_no, "~!@#$%^&*()", "-");
//					sql = "update tManualRefund set fmemo2 = '处理完成', fReturnTime = GETDATE() " +
//					" where fID = '" + fid + "'";
//					m = stmt.executeUpdate(sql);
//					if (m != 1){
//						dc.getConnect().rollback();	
//						stmt.close();
//						dc.CloseConnect();
//						return "error_加款状态失败";
//					}
//					
//				}
				
				//向客户账户中加款
				sql = "update CoClientMoney set fMoney = fMoney + " + total_fee +
						" where fID = '" + fClientID + "' and fMoney = " + fClientMoney;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "error_加款失败";
				}
				
				//写入资金变动表 OK
				String sis = "";
				sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID(""));
				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
				sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
				sis = fc.SetSqlInsertStr(sis, "fType", "3");
				sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
				sis = fc.SetSqlInsertStr(sis, "fBeforeMoney", fClientMoney);
				sis = fc.SetSqlInsertStr(sis, "fAfterMoney", fClientMoney + "+" + total_fee, false);
				sis = fc.SetSqlInsertStr(sis, "fOperateId", opereateid);
				sis = fc.SetSqlInsertStr(sis, "fAgentID", fAgentID);
				sis = fc.SetSqlInsertStr(sis, "fMemo", memo);
				sql = "insert into CoMoneyChange " + sis;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "error_计费失败";
				}
				
//				//写转账记录
//				sis = "";
//				sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("TL"));
//				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
//				sis = fc.SetSqlInsertStr(sis, "fUserID", fClientID);
//				sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
//				sis = fc.SetSqlInsertStr(sis, "fType", "11");	//加款成功
//				sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
//				sql = "insert into TransferLog " + sis;
//				m = stmt.executeUpdate(sql);
//				if (m != 1){
//					dc.getConnect().rollback();	
//					stmt.close();
//					dc.CloseConnect();
//					return "error_记录失败";
//				}
				
				//提交事务
				dc.getConnect().commit();
				stmt.close();
				dc.CloseConnect();
				return "success";  
			}catch(Exception e){
				try{
					dc.getConnect().rollback();
				}catch(Exception eee){
				}
				try{
					stmt.close();
				}catch(Exception eeee){
				}
				try{
					dc.CloseConnect();
				}catch(Exception eeee){
				}
				e.printStackTrace();
				return "error,事务失败";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return "error,异常错误";
		}
	}
	
	

	
	/*
	 * 支付宝加款通知处理:  华奇加款 
	 */
	public String acceptResult_hq(HttpServletRequest request, HttpServletResponse response){
		try{
			
			//接收参数
			String out_trade_no = fc.getpv(request, "out_trade_no");	//商户订单号(含商户ID)
			String total_fee = fc.getpv(request, "total_fee");			//加款金额
			String trade_status = fc.getpv(request, "trade_status");	//处理状态
			String memo = fc.getpv(request, "memo");					//自定义备注
			String opereateid = fc.getpv(request, "opereateid");		//手工处理人员
			String ip = request.getRemoteAddr();						//处理ip
			smg("[加款通知]" + ip + "," + out_trade_no + "," + total_fee + "," + trade_status);
			
			//
			if (memo.equals("")) memo = "支付宝";
			if (opereateid.equals("")) opereateid = "自动";
			
			//IP限制 
			
			//取客户ID
			String fClientID = "";
			int n = out_trade_no.indexOf("-");
			if (n > -1)
				fClientID = out_trade_no.substring(n + 1);			
			else
				return "error_out_trad_no";
			//初始化连接,用单独连接,保存事务单独运行
			DataConnect dc = new DataConnect("orderfill", false);
			
			//检查是否加款成功,写交易记录
			if (!trade_status.equals("TRADE_FINISHED")){
//				//转换状态				
//				String sState = trade_status;
//				if (trade_status.equals("WAIT_BUYER_PAY")) sState = "10";
//				if (trade_status.equals("TRADE_CLOSED")) sState = "12";
//				//写转账记录
//				String sis = "";
//				sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("TL"));
//				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
//				sis = fc.SetSqlInsertStr(sis, "fUserID", fClientID);
//				sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
//				sis = fc.SetSqlInsertStr(sis, "fType", sState);			//等待加款,加款失败
//				sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
//				String sql = "insert into TransferLog " + sis;
//				int m = dc.execute(sql);
//				if (m != 1){
//					dc.CloseConnect();
//					return "error_接收失败";
//				}else{
//					dc.CloseConnect();
//					return "success";		//非加款成功,直接跳出
//				}
				return "error_" + trade_status;
			}
			
			//检查当前支付宝订单号是否已经处理完成	ok
			String sql = "select * from CoMoneyChange where fClientID = '" + fClientID + "' and fOrderID = '" + out_trade_no + "'";
			ResultSet rs = dc.query(sql);
			String sError = "";
			if (rs == null){
				sError = "error_连接失败";
			}else{
				if (rs.next()) sError = "success";
				dc.CloseResultSet(rs);
			}
			if (!sError.equals("")){
				dc.CloseConnect();
				return sError;
			}
			
			//取出账户金额
			String fClientMoney = "0", fAgentID = "";
			sql = "select c.fAgentID, cm.fMoney from CoClientMoney as cm ,CoClient as c where cm.fid = '" + fClientID + "' and cm.fid=c.fid";
			rs = dc.query(sql);
			if (rs.next()){
				fClientMoney = fc.getrv(rs, "fMoney", "0");
				fAgentID = fc.getrv(rs, "fAgentID", "0");
			}else{
				//没有客户账户余额记录时将自动增加
				sql = "insert into CoClientMoney (fid, fMoney)values('" + fClientID + "', 0)";
				dc.execute(sql);
			}
			dc.CloseResultSet(rs);

			//-----事务开始-----
			Statement stmt = null;
			try{
				//初始化
				stmt = dc.getConnect().createStatement();
				if (stmt == null){
					dc.CloseConnect();
					return "error_连接失败";
				}
				dc.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
				int m = 0;				
				//更新手工加款状态
//				if (!opereateid.equals("自动")){
//					String fid = fc.getString("~!@#$%^&*()" + out_trade_no, "~!@#$%^&*()", "-");
//					sql = "update tManualRefund set fmemo2 = '处理完成', fReturnTime = GETDATE() " +
//					" where fID = '" + fid + "'";
//					m = stmt.executeUpdate(sql);
//					if (m != 1){
//						dc.getConnect().rollback();	
//						stmt.close();
//						dc.CloseConnect();
//						return "error_加款状态失败";
//					}
//					
//				}
				
				//向客户账户中加款
				sql = "update CoClientMoney set fMoney = fMoney + " + total_fee +
						" where fID = '" + fClientID + "' and fMoney = " + fClientMoney;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "error_加款失败";
				}
				
				//写入资金变动表 OK
				String sis = "";
				sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID(""));
				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
				sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
				sis = fc.SetSqlInsertStr(sis, "fType", "3");
				sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
				sis = fc.SetSqlInsertStr(sis, "fBeforeMoney", fClientMoney);
				sis = fc.SetSqlInsertStr(sis, "fAfterMoney", fClientMoney + "+" + total_fee, false);
				sis = fc.SetSqlInsertStr(sis, "fOperateId", opereateid);
				sis = fc.SetSqlInsertStr(sis, "fAgentID", fAgentID);
				sis = fc.SetSqlInsertStr(sis, "fMemo", memo);
				sql = "insert into CoMoneyChange " + sis;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc.getConnect().rollback();	
					stmt.close();
					dc.CloseConnect();
					return "error_计费失败";
				}
				
//				//写转账记录
//				sis = "";
//				sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("TL"));
//				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
//				sis = fc.SetSqlInsertStr(sis, "fUserID", fClientID);
//				sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
//				sis = fc.SetSqlInsertStr(sis, "fType", "11");	//加款成功
//				sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
//				sql = "insert into TransferLog " + sis;
//				m = stmt.executeUpdate(sql);
//				if (m != 1){
//					dc.getConnect().rollback();	
//					stmt.close();
//					dc.CloseConnect();
//					return "error_记录失败";
//				}
				
				//提交事务
				dc.getConnect().commit();
				stmt.close();
				dc.CloseConnect();
				return "success";  
			}catch(Exception e){
				try{
					dc.getConnect().rollback();
				}catch(Exception eee){
				}
				try{
					stmt.close();
				}catch(Exception eeee){
				}
				try{
					dc.CloseConnect();
				}catch(Exception eeee){
				}
				e.printStackTrace();
				return "error,事务失败";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return "error,异常错误";
		}
	}
	
	
	/*
	 * 支付宝加款通知处理:  瑞达加款 
	 */
	public String acceptResult_rd(HttpServletRequest request, HttpServletResponse response){
			try{
				
				//接收参数
				String out_trade_no = fc.getpv(request, "out_trade_no");	//商户订单号(含商户ID)
				String total_fee = fc.getpv(request, "total_fee");			//加款金额
				String trade_status = fc.getpv(request, "trade_status");	//处理状态
				String memo = fc.getpv(request, "memo");					//自定义备注
				String opereateid = fc.getpv(request, "opereateid");		//手工处理人员
				String ip = request.getRemoteAddr();						//处理ip
				smg("[加款通知]" + ip + "," + out_trade_no + "," + total_fee + "," + trade_status);
				
				//
				if (memo.equals("")) memo = "支付宝";
				if (opereateid.equals("")) opereateid = "自动";
				
				//IP限制
				
				//取客户ID
				String fClientID = "";
				int n = out_trade_no.indexOf("-");
				if (n > -1)
					fClientID = out_trade_no.substring(n + 1);			
				else
					return "error_out_trad_no";
				//初始化连接,用单独连接,保存事务单独运行
				DataConnect dc = new DataConnect("KZCZ2010", false); 
				
				//检查是否加款成功,写交易记录
				if (!trade_status.equals("TRADE_FINISHED")){
					//转换状态				
					String sState = trade_status;
					if (trade_status.equals("WAIT_BUYER_PAY")) sState = "10";
					if (trade_status.equals("TRADE_CLOSED")) sState = "12";
					//写转账记录
					String sis = "";
					sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("TL"));
					sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
					sis = fc.SetSqlInsertStr(sis, "fUserID", fClientID);
					sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
					sis = fc.SetSqlInsertStr(sis, "fType", sState);			//等待加款,加款失败
					sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
					String sql = "insert into TransferLog " + sis;
					int m = dc.execute(sql);
					if (m != 1){
						dc.CloseConnect();
						return "error_接收失败";
					}else{
						dc.CloseConnect();
						return "success";		//非加款成功,直接跳出
					}
				}
				
				//检查当前支付宝订单号是否已经处理完成
				String sql = "select * from MoneyChange where fClientID = '" + fClientID + "' and fOrderID = '" + out_trade_no + "'";
				ResultSet rs = dc.query(sql);
				String sError = "";
				if (rs == null){
					sError = "error_连接失败";
				}else{
					if (rs.next()) sError = "success";
					dc.CloseResultSet(rs);
				}
				if (!sError.equals("")){
					dc.CloseConnect();
					return sError;
				}
				
				//取出账户金额
				String fClientMoney = "0";
				sql = "select * from AccountMoney where fid = '" + fClientID + "'";
				rs = dc.query(sql);
				if (rs.next()){
					fClientMoney = fc.getrv(rs, "fMoney", "0");
				}else{
					//没有客户账户余额记录时将自动增加
					sql = "insert into AccountMoney (fid, fMoney)values('" + fClientID + "', 0)";
					dc.execute(sql);
				}
				dc.CloseResultSet(rs);

				//-----事务开始-----
				Statement stmt = null;
				try{
					//初始化
					stmt = dc.getConnect().createStatement();
					if (stmt == null){
						dc.CloseConnect();
						return "error_连接失败";
					}
					dc.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
					int m = 0;				
					//更新手工加款状态
					if (!opereateid.equals("自动")){
						String fid = fc.getString("~!@#$%^&*()" + out_trade_no, "~!@#$%^&*()", "-");
						sql = "update tManualRefund set fmemo2 = '处理完成', fReturnTime = GETDATE() " +
						" where fID = '" + fid + "'";
						m = stmt.executeUpdate(sql);
						if (m != 1){
							dc.getConnect().rollback();	
							stmt.close();
							dc.CloseConnect();
							return "error_加款状态失败";
						}
						
					}
					
					//向客户账户中加款
					sql = "update AccountMoney set fMoney = fMoney + " + total_fee +
							" where fID = '" + fClientID + "' and fMoney = " + fClientMoney;
					m = stmt.executeUpdate(sql);
					if (m != 1){
						dc.getConnect().rollback();	
						stmt.close();
						dc.CloseConnect();
						return "error_加款失败";
					}
					
					//写入资金变动表
					String sis = "";
					sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("MC"));
					sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
					sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
					sis = fc.SetSqlInsertStr(sis, "fType", "3");
					sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
					sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
					sis = fc.SetSqlInsertStr(sis, "fBeforeMoeny", fClientMoney);
					sis = fc.SetSqlInsertStr(sis, "fAfterMoney", fClientMoney + "+" + total_fee, false);
					sis = fc.SetSqlInsertStr(sis, "fOpereateId", opereateid);
					sis = fc.SetSqlInsertStr(sis, "fMemo", memo);
					sql = "insert into MoneyChange " + sis;
					m = stmt.executeUpdate(sql);
					if (m != 1){
						dc.getConnect().rollback();	
						stmt.close();
						dc.CloseConnect();
						return "error_计费失败";
					}
					
					//写转账记录
					sis = "";
					sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("TL"));
					sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
					sis = fc.SetSqlInsertStr(sis, "fUserID", fClientID);
					sis = fc.SetSqlInsertStr(sis, "fOrderID", out_trade_no);
					sis = fc.SetSqlInsertStr(sis, "fType", "11");	//加款成功
					sis = fc.SetSqlInsertStr(sis, "fMoney", total_fee);
					sql = "insert into TransferLog " + sis;
					m = stmt.executeUpdate(sql);
					if (m != 1){
						dc.getConnect().rollback();	
						stmt.close();
						dc.CloseConnect();
						return "error_记录失败";
					}
					
					//提交事务
					dc.getConnect().commit();
					stmt.close();
					dc.CloseConnect();
					return "success";  
				}catch(Exception e){
					try{
						dc.getConnect().rollback();
					}catch(Exception eee){
					}
					try{
						stmt.close();
					}catch(Exception eeee){
					}
					try{
						dc.CloseConnect();
					}catch(Exception eeee){
					}
					e.printStackTrace();
					return "error,事务失败";
				}
				
			}catch(Exception e){
				e.printStackTrace();
				return "error,异常错误";
			}
	}	
	 

	
	
	//信息输出
	public void smg(String s){
		System.out.print(s);
		return;
	}
}