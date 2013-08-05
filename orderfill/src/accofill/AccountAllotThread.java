package accofill;

/*
 * 订单分发线程
 */
import allot.AccountAllot;
import common.*; 

public class AccountAllotThread implements Runnable {

	String m_sDefAisle = ""; 
	String m_sGUID = ""; 

	//初始化参�?
	public AccountAllotThread(String sID, String sToClient) { 
		m_sGUID = sID;
		m_sDefAisle = sToClient;
	}
	
	
	
	
	
	//执行
	public void run() { 
		try {  
			//分发
			System.out.print("[线程分发]" + m_sGUID + "," + m_sDefAisle);
			AccountAllot ps = new AccountAllot();
			String s = ps.paymentStd(m_sGUID, "", "");
			System.out.print("[线程分发]" + m_sGUID + ">>" + s); 
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

}
