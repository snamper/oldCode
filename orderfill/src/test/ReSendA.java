package test;


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
	 * 充值模拟测试
	 */
	public void run() {
//		Listener.isrun = true;
		if (ListenerFillTest.isrun) return ;
		ListenerFillTest.isrun = true;
		// 
		try{
			//取单
			String sUrl = "http://127.0.0.1:8080/orderfill/GetAccount?" +
					"giveid=waihu-01,nmgkc-01,lianlian-01,yibao-01" +
					"&minprice=1&appid=" + this.hashCode() + "&queue=" + m_ids;
			String sData = fc.SendDataViaGet(sUrl);
			System.out.print("[" + this.hashCode() + "]取单:" + sData);
			//
			String state = "";
			String fillid = fc.getString(sData, "<fillid>", "</fillid>");
			String money = fc.getString(sData, "<money>", "</money>");
			String giveid = fc.getString(sData, "<giveid>", "</giveid>");
			if (Float.valueOf(fc.ran(0, 10)).intValue() % 2 == 0){
				state = "0";
				money = money;
			}else{
				state = "1";
				money = "0";
			}
			//写充值日志
			if (!fillid.equals("")){
				sUrl = "http://127.0.0.1:8080/orderfill/AcceptLog?" +
						"giveid=" + giveid + "&appid=" + this.hashCode() +
						"&fillmoney=" + money + "&fillid=" + fillid + "&msg=&state=" + state;
				sData = fc.SendDataViaGet(sUrl);
				System.out.print("[" + this.hashCode() + "]日志:" + sData);
				//写充值结果
				sUrl = "http://127.0.0.1:8080/orderfill/AccountResult?" +
						"fillmoney=" + money + "&fillid=" + fillid + "&msg=&state=" + state;
				sData = fc.SendDataViaGet(sUrl);
				System.out.print("[" + this.hashCode() + "]结果:" + sData);
			}
			
			
		}catch(Exception e){
			System.out.print("[超时订单]处理时失败," + e.toString());
		}
		ListenerFillTest.isrun = false;
	}

}
