package accept;

import java.sql.ResultSet;
import java.sql.Statement;

import common.*;

public final class OrderCharge {
	public synchronized static String orderCharge(String sguid, String agentID,
			String agentRate, String clientID, String merOrderNo,
			String cardType, String submitTime, String cardNo,
			String cardPassword, String money, String clientRate,
			String defState, String customizeA, String customizeB,
			String customizeC, String noticeURL, String noticePage,
			String SingleMoney) {
		//
		String sErrorNew = ""; 
		//-----事务开始-------------------------------------------------------------------------------
		Statement stmt = null;
		DataConnect dc = new DataConnect("orderfill", false);
		try{
			int m = 0;
			//1.初始化
			dc.checkConnect();
			stmt = dc.getConnect().createStatement();
			if (stmt == null){
				dc.CloseConnect();
				System.out.print("[OrderCharge]建立连接失败!");	
				sErrorNew = "ERROR,服务器忙,请稍候再试";
				return sErrorNew;
			}
			dc.getConnect().setAutoCommit(false);		//开始事务,更改JDBC事务的默认提交方式
			//2.从客户账户中扣费fChargeType,fSingleMoney
			String fClientMoney = "0";
			String sql = "";
			int i = 0;
			do{
				//重取账户金额一次
				sql = "select * from CoClientMoney where fid = '" + clientID + "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next())
					fClientMoney = fc.getrv(rs, "fMoney", "0");
				else{	//没有客户账户余额记录时将自动增加
					sql = "insert into CoClientMoney (fid, fMoney)values('" + clientID + "', 0)";
					dc.execute(sql);
				}
				rs.close();
				//扣费
				sql = "update CoClientMoney set fMoney = fMoney - " + SingleMoney + "" +
				" where fID = '" + clientID + "' and fMoney - " + SingleMoney + " >= 0 and fMoney = " + fClientMoney + "";
				m = stmt.executeUpdate(sql);
				if (m !=1 )	System.out.print("扣款失败,再次重试...");
				//重试计数
				i++;
			}while((m != 1)&&(i < 2));
			//
			if (m != 1){
				dc.getConnect().rollback();	
				stmt.close();
				dc.CloseConnect();
				System.out.print("[OrderCharge]扣款失败:" + sql);	
				sErrorNew = "ERROR,下单失败,扣款失败";
				return sErrorNew;
			}
			
			//3.写资金变动表
			String sis = "";
			sis = fc.SetSqlInsertStr(sis, "fID", fc.GetOrderID("MC"));
			sis = fc.SetSqlInsertStr(sis, "fTime", "GETDATE()", false);
			sis = fc.SetSqlInsertStr(sis, "fClientID", clientID);
			sis = fc.SetSqlInsertStr(sis, "fType", "0");						//扣款
			sis = fc.SetSqlInsertStr(sis, "fOrderID", sguid);
			sis = fc.SetSqlInsertStr(sis, "fMoney", SingleMoney, false);
			sis = fc.SetSqlInsertStr(sis, "fBeforeMoney", fClientMoney, false);
			sis = fc.SetSqlInsertStr(sis, "fAfterMoney", fClientMoney + "-" + SingleMoney , false);
			sis = fc.SetSqlInsertStr(sis, "fOpereateId", "自动");
			sql = "insert into CoMoneyChange " + sis;
			m = stmt.executeUpdate(sql);
			if (m != 1){
				dc.getConnect().rollback();	
				stmt.close();
				dc.CloseConnect();
				System.out.print("[OrderCharge]计费失败:" + sql);	
				sErrorNew = "ERROR,下单失败,计费失败";
				return sErrorNew;
			}
			
			//4.写订单记录
			String s  = "";
			s = fc.SetSqlInsertStr(s, "fID", sguid);
			s = fc.SetSqlInsertStr(s, "fCreateTime", "GETDATE()", false);
			s = fc.SetSqlInsertStr(s, "fAgentID", agentID);
			s = fc.SetSqlInsertStr(s, "fAgetnRate", agentRate);
			s = fc.SetSqlInsertStr(s, "fClientID", clientID);
			s = fc.SetSqlInsertStr(s, "fOrderID", merOrderNo);
			s = fc.SetSqlInsertStr(s, "fProductID", cardType);
			s = fc.SetSqlInsertStr(s, "fClientTime", submitTime, false);
			s = fc.SetSqlInsertStr(s, "fCardNo", cardNo);
			s = fc.SetSqlInsertStr(s, "fPassword", cardPassword);
			s = fc.SetSqlInsertStr(s, "fPrice", money); 
			s = fc.SetSqlInsertStr(s, "fRate", clientRate);
			s = fc.SetSqlInsertStr(s, "fGiveID", ""); 
			s = fc.SetSqlInsertStr(s, "fOverTime", "GETDATE()");
			s = fc.SetSqlInsertStr(s, "fErrorCount", "0");
			s = fc.SetSqlInsertStr(s, "fState", defState);
			s = fc.SetSqlInsertStr(s, "fNoticeState", "0");
			s = fc.SetSqlInsertStr(s, "fUserA", customizeA);
			s = fc.SetSqlInsertStr(s, "fUserB", customizeB); 
			s = fc.SetSqlInsertStr(s, "fUserC", customizeC);
			s = fc.SetSqlInsertStr(s, "fNoticeURL", noticeURL); 
			s = fc.SetSqlInsertStr(s, "fNoticePage", noticePage);
			s = fc.SetSqlInsertStr(s, "fLockID", "");
			sql = "INSERT INTO CaOrder " + s;
			m = stmt.executeUpdate(sql);
			if (m != 1){
				dc.getConnect().rollback();	
				stmt.close();
				dc.CloseConnect();
				System.out.print("[OrderCharge]写入数据库时失败:" + sql);	
				sErrorNew = "ERROR,系统忙,请稍后再试";
				return sErrorNew;
			}
			//5.提交事务
			dc.getConnect().commit();
			stmt.close();
			dc.CloseConnect();
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
			return "ERROR,系统忙,请稍后再试";
		}
		//------------------------------------------------------------------------------------------------
		return sErrorNew;

	}


}
