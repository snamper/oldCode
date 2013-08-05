package cost;


import java.sql.ResultSet;
import java.util.TimerTask;

import common.*;

public class ReSend extends TimerTask {

	static DataConnect dc = new DataConnect("orderfill", false);
	static int nTime = 0;
	static int nCount = 0;
	String sRunDate = "";

	/*
	 * 超时订单处理
	 */
	public void run() {
//		Listener.isrun = true;
		if (ListenerCost.isrun) return ;
		ListenerCost.isrun = true;
		// 
		try{
			//检查需要退款处理的订单: 失败并已扣款的
			String sql = "update AcOrder set fCostState = '2' where fLockID='' and (" +
			" (fOverTime < GETDATE() and fState = '5' and fCostState = '1')) ";
			dc.execute(sql);
			
			//处理退款订单,一次处理20个
			sql = "select top 20 fid from AcOrder where fLockID='' and (" +
				" (fOverTime < GETDATE() and fCostState = '2')) ";
			ResultSet rs = dc.query(sql);
			if (rs!=null){
				int count = 0;
				while (rs.next()){
					count++;
					String id = fc.getrv(rs, "fid", "");
					//退款
					AccountCost ac = new AccountCost();
					ac.accountCostMoney(id, "", "");
				} 
				dc.CloseResultSet(rs);
				nCount = nCount + 1;
				if (nCount % 10 == 1) System.out.print("[账号退款]暂无订单处理" );
			}else
				System.out.print("[账号退款]查询时失败" );
			
			//清除超时退款锁定
			
			//检查24小时部分退款的订单: 24小时前,部分成功订单
			sql = "select top 20 fid from AcOrder WITH (NOLOCK) where ((fstate = '4' and fLackMoney > 0) " +
					" or (fstate = '5')) and (fCostState <> '3' and fcostState <> '4') " +
					" and fcreatetime <= getdate() - 1";
			rs = dc.query(sql);
			if (rs!=null){
				int count = 0;
				while (rs.next()){
					count++;
					String id = fc.getrv(rs, "fid", "");
					//退款AccountCost?operateid=[user()]&id=[fid]&ishand=true&sign=md5([fid]75349hfi3yhn837dyh4675r89)
					AccountCost ac = new AccountCost();
					String s = ac.accountCostMoney(id, "true", "24H自动");
					System.out.print("[账号退款]" + id + ":" + s);
				} 
				dc.CloseResultSet(rs);
				nCount = nCount + 1;
				if (nCount % 10 == 1) System.out.print("[账号退款]暂无订单处理" );
			}else
				System.out.print("[账号退款]查询时失败" );
			

		}catch(Exception e){
			System.out.print("[账号退款]处理时失败," + e.toString());
		}
		ListenerCost.isrun = false;
	}
	
	

}
