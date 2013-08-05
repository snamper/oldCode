package cost;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DataConnect;
import common.fc;

/**
 * Servlet implementation class for Servlet: AccountCost
 *
 * @web.servlet
 *   name="AccountCost"
 *   display-name="AccountCost" 
 *
 * @web.servlet-mapping 
 *   url-pattern="/AccountCost"
 *  
 */
 public class AccountCost extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	   static final long serialVersionUID = 1L;
	   private static final String CONTENT_TYPE = "text/html; charset=GBK"; 
	   static DataConnect dc = new DataConnect("orderfill", false);
	   static String m_sDate = "";
	   static String m_AutoIP = ""; 
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AccountCost() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payment2(request, response));
	}  	
	
	/* (non-Java-doc) 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		out.print(payment2(request, response));
	}   	  	    
	
	public String payment2(HttpServletRequest request, HttpServletResponse response){
		//接收参数OK
		String id = fc.getpv(request, "id");
		String ishand = fc.getpv(request, "ishand");
		String operateid = fc.getpv(request, "operateid");
		String sign = fc.getpv(request, "sign").toUpperCase();
		System.out.println("[退款] id=" + id);
		if (!sign.equals(fc.getMd5Str(id + "75349hfi3yhn837dyh4675r89").toUpperCase())) return "ERROR,md5签名不正确";	
		return accountCostMoney(id, ishand, operateid);
	}
	
	/*  
	 * 退款
	 */
	public String accountCostMoney(String fid, String ishand, String operateid){
		try{
			System.out.println("[退款] fid=" + fid);
			//1.验证md5及参数合法性OK
			if (fid.equals("")) return "ERROR,订单号不能为空";
			 
			if (dc == null) dc = new DataConnect("orderfill", false);

			//3.锁定订单
			String fOperateId = "";
			if (operateid.equals("")) operateid = "手工";
			String fCostState = "";
			if (ishand.equals("true")){
				fOperateId = operateid;
				if (fOperateId.equals("24H自动"))
					fCostState = "1";
				else
					fCostState = "5";
			}else{
				fCostState = "2"; 
				fOperateId = "自动";
			}
			//	
			int n = 0;
			String ss = "AC_" + fc.getTime("HHmmss_") + fc.ran(100000, 999999);
			if (fOperateId.equals("24H自动"))
				n = dc.execute("update AcOrder set fLockID = '" + ss + "' where fid = '" + fid + "'");// and fCostState = '" + fCostState + "'");
			else
				n = dc.execute("update AcOrder set fLockID = '" + ss + "' where fid = '" + fid + "' and fLockID = ''");// and fCostState = '" + fCostState + "'");
			if (n<=0){
				System.out.print("[退款]" + fid + ",锁定失败:订单号或退款状态无效");
				return "ERROR,锁定失败:订单号或退款状态无效";
			}
 
			//2.查询订单信息
			//smg("查询订单信息");
			String sql = "select top 1 * from AcOrder where fLockID = '" + ss + "'";
			ResultSet rs = dc.query(sql);
			if (rs == null) return "ERROR,查询订单失败";
			if (!rs.next()){
				dc.CloseResultSet(rs);
				System.out.print("[分发]" + fid + ",订单号不存在或已处理完成");
				return "OK,订单号不存在或已处理完成";							
			}
			

			//4.取出订单信息
			//smg("取出订单信息OK");
			fid = fc.getrv(rs, "fid", "");									//订单号
			String fClientID = fc.getrv(rs, "fClientID", "");				//客户ID
			String fAgentID = fc.getrv(rs, "fAgentID", "");					//代理ID
			String fFillRate = fc.getrv(rs, "fRate", "");				//充值折扣
//			String sProductID = fc.getrv(rs, "fProductId", "");				//商品编号
			String fMoney = fc.getrv(rs, "fMoney", "0");					//金额 
			String fLackMoney = fc.getrv(rs, "fLackMoney", "0");			//未充金额 
			String fFactMoney = fc.getrv(rs, "fFactMoney", "0");			//已充金额 
			String fCostMoney = fc.getrv(rs, "fCostMoney", "");			//结算金额,如果全额充值失败,才会自动退款 
			String fState = fc.getrv(rs, "fState", "");				    		//订单状态
			
			
			double dRate = Double.parseDouble(fc.getrv(rs, "fRate", "0"));		//转换为小数费率
//			String fRate = Double.toString(dRate); 							 	//此单费率
			float nFillRate = Float.valueOf(fFillRate).floatValue();			//充值折扣
			float nMoney = Float.valueOf(fMoney).floatValue();					//金额
			float nLackMoney = Float.valueOf(fLackMoney).floatValue();			//未充金额
			float nFactMoney = Float.valueOf(fFactMoney).floatValue();			//已充金额
			float nCostMoney = 0;
			if (fCostMoney.equals(""))
				nCostMoney = nFillRate * nMoney;								//结算金额
			else
				nCostMoney = Float.valueOf(fCostMoney).floatValue();			//结算金额
			//
			dc.CloseResultSet(rs);
			// 
			
			//再次检查退款状态 
			if (!((fState.equals("4")&&(nLackMoney > 0)) || (fState.equals("5")) || (nMoney == nLackMoney + nFactMoney))){
				dc.CloseResultSet(rs);
				//dc.CloseConnect();
				String sError = "ERROR,订单状态不符合退款要求";
				smg(sError);
				return sError;
			}
			
			//根据金额与折扣检查是否与结算金额相同,如果不同且是部分充值则不退款(代充值部分充值不自动退款).
			if (!fCostState.equals("6"))	//非手工退款订单
			if ( fCostMoney.equals("") && (nCostMoney != nFillRate * (nLackMoney + nFactMoney))&&(nFactMoney > 0)){
				return "ERROR,代充订单部分充值不能自动退款";
			}
			
			if(nMoney == nFactMoney){
				return "ERROR,订单状态不符合退款要求";
			}

			//确定退款状态
			fCostState = "3";	//全额退款
			String fCostInfo = "全额退款";	
			if (nFactMoney > 0){
				fCostState = "4";		//已冲金额大于0时, 部分退款
				fCostInfo = "部分退款";
			}

			//是否已有当前订单的退款记录 OK
			String sError = "";
			sql = "select * from CoMoneyChange where fOrderID = '" + fid + "' and fType = '1'";
			rs = dc.query(sql);
			if (rs != null){
				if (rs.next()){
					n = dc.execute("update AcOrder set fLockId = '', fCostState = '" + fCostState + "' " +
							" where fid = '" + fid + "'");
					if (n == 1)
						sError = "OK,退款完成,状态已更新";
					else 
						sError = "ERROR,退款状态更新失败";
					dc.CloseResultSet(rs);
					//dc.CloseConnect();
					smg(sError);
					return sError;
				}
				dc.CloseResultSet(rs);
			}

			//是否已有当前订单的扣款记录 OK
			sError = "";
			
			//2011年12月22日,renfy修改
//			String ChangeMoney = fRate + "*" + fLackMoney;
			String ChangeMoney = "";
			if(nLackMoney > 0){
				ChangeMoney = String.valueOf(nLackMoney * nCostMoney / nMoney );
			}else{
				ChangeMoney = String.valueOf(nCostMoney);
			}
			
			sql = "select * from CoMoneyChange where fOrderID = '" + fid + "' and fType = '0'";
			rs = dc.query(sql);
			if (rs != null){
				if (!rs.next()){
					n = dc.execute("update AcOrder set fLockId = '', fCostState = '0' " +
							" where fid = '" + fid + "'");
					if (n == 1)
						sError = "OK,未扣款,不进行退款";
					else 
						sError = "ERROR,退款状态更新失败";
					dc.CloseResultSet(rs);
					//////dc.CloseConnect();
					smg(sError);
					return sError;
				}
				//ChangeMoney = fc.getrv(rs, "fMoney", "");
				dc.CloseResultSet(rs);
			}
			
			
			//-----事务开始-----
			DataConnect dc2 = new DataConnect("orderfill", false);
			Statement stmt = null;
			try{
				//初始化
				dc2.checkConnect();
				stmt = dc2.getConnect().createStatement();
				if (stmt == null){
					dc2.CloseConnect();
					smg("ERROR,连接失败,请稍后再试");
					return "ERROR,连接失败,请稍后再试";
				}
				dc2.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
				int m = 0;				
				
				//取出账户金额 OK
				String fClientMoney = "0";
				sql = "select * from CoClientMoney where fid = '" + fClientID + "'";
				rs = dc2.query(sql);
				if (rs != null){
					if (rs.next()){
						fClientMoney = fc.getrv(rs, "fMoney", "0");
					}else{
						dc2.getConnect().rollback();	
						stmt.close();
						dc2.CloseConnect();
						smg("ERROR,未查询到账户ID,请稍后重试");
						return "ERROR,未查询到账户ID,请稍后重试";
					}
				}else{
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					smg("ERROR,查询账户时失败,请稍后重试");
					return "ERROR,查询账户时失败,请稍后重试";
				}
				dc.CloseResultSet(rs);

				//向客户账户中加款 OK
				sql = "update CoClientMoney set fMoney = fMoney + " + ChangeMoney +
						" where fID = '" + fClientID + "' and fMoney = " + fClientMoney;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					smg("ERROR,退款失败,系统忙请稍后重试");
					return "ERROR,退款失败,系统忙请稍后重试";
				}
				
				//写入资金变动表 OK
				String sis = "";
				sis = fc.SetSqlInsertStr(sis, "fID", fc.getGUID6(""));
				sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
				sis = fc.SetSqlInsertStr(sis, "fClientID", fClientID);
				sis = fc.SetSqlInsertStr(sis, "fAgentID", fAgentID);
				sis = fc.SetSqlInsertStr(sis, "fOrderID", fid);
				sis = fc.SetSqlInsertStr(sis, "fType", "1");	//0扣款1退款2提现3加款 
				sis = fc.SetSqlInsertStr(sis, "fMoney", ChangeMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fBeforeMoney", fClientMoney);
				sis = fc.SetSqlInsertStr(sis, "fAfterMoney", fClientMoney + "+" + ChangeMoney, false);
				sis = fc.SetSqlInsertStr(sis, "fOperateId", fOperateId);
				sis = fc.SetSqlInsertStr(sis, "fMemo", fCostInfo);
				sql = "insert into CoMoneyChange " + sis;
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					smg("ERROR,记录资金变动时失败");
					return "ERROR,记录资金变动时失败";
				}

				//更新订单表: 扣款状态及订单状态 
				sql = "update AcOrder set fLockID = '', fCostState = '" + fCostState + "' " +
						" where fID = '" + fid + "'";
				m = stmt.executeUpdate(sql);
				if (m != 1){
					dc2.getConnect().rollback();	
					stmt.close();
					dc2.CloseConnect();
					smg("ERROR,更新订单退款状态时失败");
					return "ERROR,更新订单退款状态时失败";
				}

				//提交事务
				dc2.getConnect().commit();
				stmt.close();
				dc2.CloseConnect();
				smg("OK,退款成功," + (nLackMoney * dRate));
				return "OK,退款成功," + (nLackMoney * dRate);
			}catch(Exception e){
				try{
					dc2.getConnect().rollback();
				}catch(Exception eee){
				}
				try{
					stmt.close();
				}catch(Exception eeee){
				}
				try{
					dc2.CloseConnect();
				}catch(Exception eee){
				}
				e.printStackTrace();
				smg("ERROR,执行事务失败");
				return "ERROR,执行事务失败";
			}
		}catch(Exception e){
			return "ERROR,异常错误";
		}
		
	}

	
	
	private void smg(String s) {
		System.out.print(s);
	}
	
}