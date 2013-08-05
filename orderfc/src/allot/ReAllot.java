package allot;


import java.net.InetAddress;
import java.sql.ResultSet;
import java.util.TimerTask;

import common.*;

public class ReAllot extends TimerTask {

	static DataConnect dc = new DataConnect("orderfill", false);
	String sRunDate = "";
	/*
	 * 超时订单处理
	 */
	public void run() {
		if (ListenerAllot.isrun) return ;
		ListenerAllot.isrun = true; 
		// 
		try{
			String hostAddress = InetAddress.getLocalHost().toString();
			hostAddress = hostAddress.substring(hostAddress.length() - 7);
			//System.out.print(hostAddress);
			//if (1==1)return; 
			//处理超时订单,一次处理20个
			String sql = "select top 20 fid from CaOrder where  fCreateTime > GETDATE() - 0.25 and fOverTime <= GETDATE() " +
					" and fState in ('1','2','6') and fLockID = ''";
			ResultSet rs = dc.query(sql);
			if (rs!=null){
				while (rs.next()){
					String id = common.fc.getrv(rs, "fid", "");
					//线程重发
					System.out.print("[超时订单]重新分发:" + id);
					Thread t1 = new Thread(new AllotThread(id)); 
					t1.start(); 
				}
				dc.CloseResultSet(rs);
			}else
				System.out.print("[超时订单]查询时失败:" + sql );
		}catch(Exception e){
			System.out.print("[超时订单]处理时失败," + e.toString());
		}
		ListenerAllot.isrun = false;
	}

}
