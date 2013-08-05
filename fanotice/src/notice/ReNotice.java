package notice;

import java.sql.ResultSet;
import java.util.TimerTask;

import common.DataConnect;

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
			String sql = "delete from AcReNotice where fEndTime < fOverTime ";
			int n = dc.execute(sql);
			if (n > 0) System.out.print("[超时重发]清除过期通知:" + n);
			
			//处理超时订单,一次处理20个
			sql = "select top 20 fid from AcOrder where fCreateTime > GETDATE() - 0.5 and fNoticeState = '0' and fState in ('4', '5')";
			ResultSet rs = dc.query(sql);
			if (rs!=null){
				while (rs.next()){
					String id = common.fc.getrv(rs, "fid", "");
					//重发
					//System.out.print("[超时通知]重新通知:" + id);
					String s = pn.payNotify_(id, "", "", "");
					System.out.print("[超时通知]" + id + ">>" + s);
				}
				dc.CloseResultSet(rs);
			}else
				System.out.print("[超时通知]查询时失败:" + sql );
		}catch(Exception e){
			System.out.print("[超时通知]处理时失败," + e.toString());
		}

	}


}
