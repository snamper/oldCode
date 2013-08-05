package pay_common;

/*
 * 订单分发线程
 */
import common.*; 

public class AllotThreadPayic implements Runnable {

	String m_sGUID = ""; 
	int m_nPort = 80; 

	//初始化参数
	public AllotThreadPayic(String sID, int nPort) { 
		m_sGUID = sID;
		m_nPort = nPort;
		}
	
	//执行
	public void run() { 
		try { 
			//分发
			System.out.print("[线程通知]" + m_nPort + "," + m_sGUID);
			String sUrl = "http://99.99.99.84:" + m_nPort + "/busics/PayNotify";
			String sData = "type=auto&id=" + m_sGUID + "&sign=" + fc.getMd5Str(m_sGUID + "44fecb4739dfae183e38ee990e70275b").toUpperCase();
			String s = fc.SendDataViaPost(sUrl, sData, "");
			System.out.print("[线程通知]" + m_sGUID + ">>" + s);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
