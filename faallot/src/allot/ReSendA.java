package allot;


import java.sql.ResultSet;
import java.util.TimerTask;

import common.*;

public class ReSendA extends TimerTask {

	static DataConnect dc = new DataConnect("orderfill", false);
	static int nTime = 0;
	String sRunDate = "";
	String m_ids = "", m_isdesc = "";

	public ReSendA(String sids, String isdesc) {
		m_ids = sids;
		m_isdesc = isdesc;
	}

	/*
	 * 超时订单处理
	 */
	public void run() {
//		Listener.isrun = true;
		if (ListenerAllot.isrun) return ;
		ListenerAllot.isrun = true;
		// 
		try{
			if (m_ids.equals("1")){
				//清除黑名单
				int n = dc.execute("delete from AcBlacklist where fOutTime < GETDATE() ");
				if (n > 0) 
					System.out.print("[过期黑名单]清除" + n + "个记录");
				//清除地区维护
				n = dc.execute("delete from AcAreaRule where fOutTime < GETDATE() ");
				if (n > 0) 
					System.out.print("[过期地区维护]清除" + n + "个记录");
			}
			
			//处理超时订单,一次处理10个
			if (m_isdesc.equals("desc")) 
				m_isdesc = " order by fCreateTime desc ";
			else
				m_isdesc = " order by fCreateTime ";
			String sql = "select top 20 fid from AcOrder where fQueueID = '" + m_ids + "' and fLockID='' and (" +
				" (fGiveTime >= GETDATE() - 1 and fState = '1')) " + m_isdesc ;
			ResultSet rs = dc.query(sql);
			if (rs!=null){
				int count = 0;
				while (rs.next()){
					count++;
					String id = fc.getrv(rs, "fid", "");
					AccountAllot ps = new AccountAllot();
					String s = ps.paymentStd(id, "", "");
				} 
				dc.CloseResultSet(rs);
				//System.out.print("[超时订单]暂无订单处理" );
			}else
				System.out.print("[超时订单]查询时失败" );
			
		}catch(Exception e){
			System.out.print("[超时订单]处理时失败," + e.toString());
		}
		ListenerAllot.isrun = false;
	}

}
