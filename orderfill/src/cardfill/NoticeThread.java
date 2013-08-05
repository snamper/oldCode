package cardfill;

/*
 * 订单分发线程
 */
import common.*; 

public class NoticeThread implements Runnable {

	String m_sDefAisle = ""; 
	String m_sGUID = ""; 

	//初始化参数
	public NoticeThread(String sID, String sToClient) { 
		m_sGUID = sID;
		m_sDefAisle = sToClient;
		}
	
	//执行
	public void run() { 
		try { 
			//分发
			System.out.print("[线程通知]" + m_sGUID + "," + m_sDefAisle);
			String sUrl = "http://127.0.0.1:8080/fcnotice/PayNotify?type=directly&id=" + m_sGUID + "&sign=" + fc.getMd5Str(m_sGUID + "44fecb4739dfae183e38ee990e70275b");
			String s = fc.SendDataViaGet(sUrl);
			System.out.print("[线程通知]" + m_sGUID + ">>" + s);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
