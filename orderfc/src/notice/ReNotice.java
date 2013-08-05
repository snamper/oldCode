package notice;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.TimerTask;

import allot.AllotThread;
import allot.ListenerAllot;

import common.DataConnect;
import common.fc;

public class ReNotice extends TimerTask {


	static DataConnect dc = new DataConnect("orderfill", false);
	static PayNotify pn = new PayNotify();

	String sRunDate = "";
	
	/*
	 * 从卡订单表中检查超时订单,进行通知及重发处理
	 */
	public void run() {
		// 
		try{
			//清除重发列表中过期订单
			String sql = "delete from CaReNotice where fEndTime < fOverTime ";
			int n = dc.execute(sql);
			if (n > 0) System.out.print("[超时重发]清除过期通知:" + n);
			
			//处理超时订单,一次处理20个
			sql = "select top 20 fid from CaOrder where fCreateTime > GETDATE() - 0.25 and fNoticeState = '0' and fState in ('4', '5')";
			ResultSet rs = dc.query(sql);
			if (rs!=null){
				int nn = 0;
				while (rs.next()){
					nn ++;
					String id = common.fc.getrv(rs, "fid", "");
					//重发
					System.out.print("[超时通知]重新通知:" + id);
					String s = pn.payNotify_(id, "", "", "");
					System.out.print("[超时通知]" + id + ">>" + s);
				}
				dc.CloseResultSet(rs);
				System.out.print("[超时通知]共处理" + nn + "个订单");
			}else
				System.out.print("[超时通知]查询时失败:" + sql );
		}catch(Exception e){
			System.out.print("[超时通知]处理时失败," + e.toString());
		}

	}


}
